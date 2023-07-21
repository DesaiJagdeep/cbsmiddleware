package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.ApplicationsByBatchAckId;
import com.cbs.middleware.domain.BatchAckId;
import com.cbs.middleware.domain.BatchData;
import com.cbs.middleware.domain.BatchTransaction;
import com.cbs.middleware.domain.CBSResponce;
import com.cbs.middleware.domain.DataByBatchAckId;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.repository.BatchTransactionRepository;
import com.cbs.middleware.repository.CastCategoryMasterRepository;
import com.cbs.middleware.repository.CropMasterRepository;
import com.cbs.middleware.repository.DesignationMasterRepository;
import com.cbs.middleware.repository.FarmerCategoryMasterRepository;
import com.cbs.middleware.repository.FarmerTypeMasterRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.repository.LandTypeMasterRepository;
import com.cbs.middleware.repository.OccupationMasterRepository;
import com.cbs.middleware.repository.RelativeMasterRepository;
import com.cbs.middleware.repository.SeasonMasterRepository;
import com.cbs.middleware.security.RBAControl;
import com.cbs.middleware.service.ResponceService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssFileParser}.
 */
@RestController
@RequestMapping("/api")
public class CronJobResource {

    private final Logger log = LoggerFactory.getLogger(CronJobResource.class);

    private static final String ENTITY_NAME = "issFileParser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    DesignationMasterRepository designationMasterRepository;

    @Autowired
    SeasonMasterRepository seasonMasterRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    IssPortalFileRepository issPortalFileRepository;

    @Autowired
    ResponceService responceService;

    @Autowired
    ApplicationLogRepository applicationLogRepository;

    @Autowired
    CropMasterRepository cropMasterRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    LandTypeMasterRepository landTypeMasterRepository;

    @Autowired
    AccountHolderMasterRepository accountHolderMasterRepository;

    @Autowired
    RelativeMasterRepository relativeMasterRepository;

    @Autowired
    OccupationMasterRepository occupationMasterRepository;

    @Autowired
    FarmerTypeMasterRepository farmerTypeMasterRepository;

    @Autowired
    FarmerCategoryMasterRepository farmerCategoryMasterRepository;

    @Autowired
    CastCategoryMasterRepository castCategoryMasterRepository;

    @Autowired
    BatchTransactionRepository batchTransactionRepository;

    @Autowired
    RBAControl rbaControl;

    public CronJobResource() {}

    /**
     * Customize code
     *
     * @throws Exception
     */

    @GetMapping("/cronJob")
    public void updateRecordsInBatchTran() {
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAllByStatus("New");
        for (BatchTransaction batchTransaction : batchTransactionList) {
            String cbsResponceString = "";
            BatchAckId batchAckId = new BatchAckId();
            batchAckId.setBatchAckId(batchTransaction.getBatchAckId());

            String encryption = encryption(batchAckId);

            // Making input payload
            CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
            cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
            cbsMiddleareInputPayload.setData(encryption);

            try {
                // Set the request URL
                String url = applicationProperties.getCBSMiddlewareBaseURL() + "/databybatchackid";
                // Set the request headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                // Create the HttpEntity object with headers and body
                HttpEntity<Object> requestEntity = new HttpEntity<>(cbsMiddleareInputPayload, headers);
                // Make the HTTP POST request
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    cbsResponceString = responseEntity.getBody();

                    CBSResponce convertValue = null;
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    convertValue = objectMapper.readValue(cbsResponceString, CBSResponce.class);

                    if (convertValue.isStatus()) {
                        if (StringUtils.isNotBlank(convertValue.getError())) {
                            batchTransaction.setBatchDetails("Batch is not processed yet");
                            batchTransactionRepository.save(batchTransaction);
                        } else {
                            String decryption = decryption("" + convertValue.getData());
                            objectMapper = new ObjectMapper();
                            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            DataByBatchAckId dataByBatchAckId = objectMapper.readValue(decryption, DataByBatchAckId.class);

                            if (dataByBatchAckId.getStatus() == 0) {
                                batchTransaction.setStatus("Discarded");
                            } else if (dataByBatchAckId.getStatus() == 1) {
                                batchTransaction.setStatus("Pending for processing");
                            } else if (dataByBatchAckId.getStatus() == 2) {
                                batchTransaction.setStatus("Processing");
                            } else if (dataByBatchAckId.getStatus() == 3) {
                                batchTransaction.setStatus("Processed");
                            }

                            batchTransactionRepository.save(batchTransaction);

                            for (ApplicationsByBatchAckId applicationsByBatchAckId : dataByBatchAckId.getApplications()) {
                                Application findOneByUniqueId = applicationRepository.findOneByUniqueId(
                                    applicationsByBatchAckId.getUniqueId()
                                );

                                findOneByUniqueId.setApplicationNumber(findOneByUniqueId.getApplicationNumber());
                                findOneByUniqueId.setApplicationStatus(1l);
                                findOneByUniqueId.setApplicationStatus(0l);
                                findOneByUniqueId.setFarmerId(findOneByUniqueId.getFarmerId());
                                findOneByUniqueId.setRecipientUniqueId(findOneByUniqueId.getRecipientUniqueId());
                                applicationRepository.save(findOneByUniqueId);
                            }
                        }
                    } else {}
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public static byte[] objectToBytes(BatchData encDecObject) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(encDecObject);
            objectOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encryption(Object encDecObject) {
        try {
            SecretKey secretKey = generateSecretKey(applicationProperties.getSecretKey(), applicationProperties.getKeySizeBits());
            IvParameterSpec ivParameterSpec = new IvParameterSpec(applicationProperties.getIv().getBytes(StandardCharsets.UTF_8));

            Cipher cipher = Cipher.getInstance(applicationProperties.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            JSONObject jsonObject = new JSONObject(encDecObject);

            byte[] encryptedBytes = cipher.doFinal(jsonObject.toString().getBytes());
            return Hex.encodeHexString(encryptedBytes);
        } catch (Exception e) {
            return "error in encryption: " + e;
        }
    }

    public String encryptionStrings(String encDecObject) {
        try {
            SecretKey secretKey = generateSecretKey(applicationProperties.getSecretKey(), applicationProperties.getKeySizeBits());
            IvParameterSpec ivParameterSpec = new IvParameterSpec(applicationProperties.getIv().getBytes(StandardCharsets.UTF_8));

            Cipher cipher = Cipher.getInstance(applicationProperties.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(encDecObject.getBytes());
            return Hex.encodeHexString(encryptedBytes);
        } catch (Exception e) {
            return "error in encryption: " + e;
        }
    }

    public String decryption(String encDecObject) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(applicationProperties.getIv().getBytes(StandardCharsets.UTF_8));
        final byte[] keyParse = applicationProperties.getSecretKey().getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec key = new SecretKeySpec(keyParse, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] val = new byte[encDecObject.length() / 2];
            for (int i = 0; i < val.length; i++) {
                int index = i * 2;
                int j = Integer.parseInt(encDecObject.substring(index, index + 2), 16);
                val[i] = (byte) j;
            }
            return new String(cipher.doFinal(val));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    private static SecretKey generateSecretKey(String secretKey, int keySize) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] truncatedKey = new byte[keySize / 8];
        System.arraycopy(keyBytes, 0, truncatedKey, 0, truncatedKey.length);
        return new SecretKeySpec(truncatedKey, "AES");
    }

    ApplicationLog generateApplicationLog(String ErrorMsg, String expectedSolution, String sevierity) {
        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setErrorMessage(ErrorMsg);
        applicationLog.setSevierity(sevierity);
        applicationLog.setExpectedSolution(expectedSolution);
        applicationLog.setErrorType("Validation Error");
        applicationLog.setStatus("ERROR");
        return applicationLog;
    }
}
