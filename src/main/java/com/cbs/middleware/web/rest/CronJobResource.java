package com.cbs.middleware.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.cbs.middleware.domain.*;
import org.apache.commons.codec.binary.Hex;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
import com.cbs.middleware.repository.ApplicationLogHistoryRepository;
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
import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.security.RBAControl;
import com.cbs.middleware.service.ResponceService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssFileParser}.
 */
@RestController
@RequestMapping("/api")
public class CronJobResource {

    private static final String ENTITY_NAME = "issFileParser";
    private final Logger log = LoggerFactory.getLogger(CronJobResource.class);
    @Autowired
    ApplicationProperties applicationProperties;
    @Autowired
    DesignationMasterRepository designationMasterRepository;
    @Autowired
    SeasonMasterRepository seasonMasterRepository;
    @Autowired
    IssPortalFileRepository issPortalFileRepository;
    @Autowired
    ResponceService responceService;
    @Autowired
    ApplicationLogRepository applicationLogRepository;
    @Autowired
    ApplicationLogHistoryRepository applicationLogHistoryRepository;
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
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    private RestTemplate restTemplate;

    public CronJobResource() {
    }

    public static byte[] objectToBytes(BatchData encDecObject) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(encDecObject);
            objectOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.err.println("Error in coversion of objectToBytes" + e);
        }
        return null;
    }

    private static SecretKey generateSecretKey(String secretKey, int keySize) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] truncatedKey = new byte[keySize / 8];
        System.arraycopy(keyBytes, 0, truncatedKey, 0, truncatedKey.length);
        return new SecretKeySpec(truncatedKey, "AES");
    }

    /**
     * Customize code
     *
     * @throws Exception
     */

    @Scheduled(cron = "0 0 6 * * ?")
    public void updateRecordsInBatchTran() {

        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAllByStatus(Constants.NEW);
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        String indianTime = dateFormat.format(new Date());

        log.debug("cron Job started at" + indianTime);
        log.info("cron Job started at" + indianTime);
        log.info("batchTransactionList size =  " + batchTransactionList.size());
        for (BatchTransaction batchTransaction:batchTransactionList) {
            System.out.println("Batch Send to KCC with ID: "+batchTransaction.getBatchId() +"at :"+indianTime);
        }

        if (!batchTransactionList.isEmpty()) {
            log.info("Batch transaction list not empty");
            for (BatchTransaction batchTransaction : batchTransactionList) {
                log.info("Batch transaction found " + batchTransaction.getId());
                List<Application> applicationListSave = new ArrayList<>();
                String cbsResponceString = "";
                BatchAckId batchAckId = new BatchAckId();
                batchAckId.setBatchAckId(batchTransaction.getBatchAckId());
                String encryption = encryption(batchAckId);

                List<ApplicationLog> applicationLogListToSave = new ArrayList<>();

                // Making input payload
                CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
                cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
                cbsMiddleareInputPayload.setData(encryption);
                System.out.println("CBS PAY LOAD: " + cbsMiddleareInputPayload.toString());

                try {
                    // Set the request URL
                    String url = applicationProperties.getCBSMiddlewareBaseURL() + Constants.databybatchackid;
                    // Set the request headers
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    // Create the HttpEntity object with headers and body
                    HttpEntity<Object> requestEntity = new HttpEntity<>(cbsMiddleareInputPayload, headers);
                    // Make the HTTP POST request
                    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);


                    if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                        cbsResponceString = responseEntity.getBody();
                        log.info("Batch transaction response cbsResponceString = " + cbsResponceString);
                        CBSResponce convertValue = null;
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        objectMapper.registerModule(new JavaTimeModule());
                        convertValue = objectMapper.readValue(cbsResponceString, CBSResponce.class);

                        if (convertValue.isStatus()) {
                            String decryption = decryption("" + convertValue.getData());
                            objectMapper = new ObjectMapper();
                            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            objectMapper.registerModule(new JavaTimeModule());
                            DataByBatchAckId dataByBatchAckId = objectMapper.readValue(decryption, DataByBatchAckId.class);

                            if (dataByBatchAckId.getStatus() == Constants.DISCARDED_BATCH_STATUS_CODE) {
                                batchTransaction.setStatus(Constants.DISCARDED);
                                batchTransaction.setBatchDetails("Batch is discarded");
                            } else if (dataByBatchAckId.getStatus() == Constants.PENDING_FOR_PROCESSING_BATCH_STATUS_CODE) {
                                batchTransaction.setStatus(Constants.PENDING_FOR_PROCESSING);
                                batchTransaction.setBatchDetails("Batch is pending for processing");
                            } else if (dataByBatchAckId.getStatus() == Constants.PROCESSING_BATCH_STATUS_CODE) {
                                batchTransaction.setStatus(Constants.PROCESSING);
                                batchTransaction.setBatchDetails("Batch is processing");
                            } else if (dataByBatchAckId.getStatus() == Constants.PROCESSED_BATCH_STATUS_CODE) {
                                batchTransaction.setStatus(Constants.PROCESSED);
                                batchTransaction.setBatchDetails("Batch is processed");
                            }

                            Long kccApplErrCount = 0l;
                            if (!dataByBatchAckId.getApplications().isEmpty()) {
                                for (ApplicationsByBatchAckId applicationsByBatchAckId : dataByBatchAckId.getApplications()) {
//                                    Application applicationByUniqueId = applicationRepository.findOneByUniqueId(
//                                        applicationsByBatchAckId.getUniqueId()
//                                    );

                                    List<Application> applicationListFromKcc = applicationRepository.findAllByUniqueId(
                                        applicationsByBatchAckId.getUniqueId()
                                    );

                                    if (applicationListFromKcc.size() == 1) {
                                        Application applicationFromKcc = applicationListFromKcc.get(0);
                                        applicationFromKcc.setApplicationStatus(applicationsByBatchAckId.getApplicationStatus());
                                        applicationFromKcc.setRecipientUniqueId(applicationsByBatchAckId.getRecipientUniqueID());

                                        if (applicationsByBatchAckId.getApplicationStatus() == 1) {
                                            applicationFromKcc.setKccStatus(1l);
                                            applicationFromKcc.setApplicationNumber(applicationsByBatchAckId.getApplicationNumber());
                                            applicationFromKcc.setFarmerId(applicationsByBatchAckId.getFarmerId());
                                        } else {
                                            applicationFromKcc.setKccStatus(0l);

                                            try {
                                                if (applicationsByBatchAckId.getErrors() != null) {
                                                    applicationFromKcc.setApplicationErrors(applicationsByBatchAckId.getErrors());
                                                } else {
                                                    applicationFromKcc.setApplicationErrors("CBS Portal not provided fail case information");
                                                }
                                            } catch (Exception e) {
                                                log.error("Exception in cron job", e);
                                            }

                                            kccApplErrCount = kccApplErrCount + 1l;

                                            // setting kscc error count in portal file object
                                            Optional<IssPortalFile> findById = issPortalFileRepository.findById(
                                                applicationFromKcc.getIssFilePortalId()
                                            );
                                            if (findById.isPresent()) {
                                                IssPortalFile issPortalFile = findById.get();
                                                if (issPortalFile.getKccErrorRecordCount() == null) {
                                                    issPortalFile.setKccErrorRecordCount(1);
                                                } else {
                                                    issPortalFile.setKccErrorRecordCount(issPortalFile.getKccErrorRecordCount() + 1);
                                                }

                                                issPortalFileRepository.save(issPortalFile);
                                            }

                                            // moving application log to history if exist
                                            ApplicationLog applicationLog = new ApplicationLog();
                                            Optional<ApplicationLog> applicationLogSaved = applicationLogRepository.findOneByIssFileParser(
                                                applicationFromKcc.getIssFileParser()
                                            );
                                            if (applicationLogSaved.isPresent()) {
                                                applicationLog = applicationLogSaved.get();
                                                JSONObject jsonObject = new JSONObject(applicationLog);
                                                ObjectMapper applicationLogHistoryObjMap = new ObjectMapper();
                                                applicationLogHistoryObjMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                                applicationLogHistoryObjMap.registerModule(new JavaTimeModule());
                                                ApplicationLogHistory applicationLogHistory = applicationLogHistoryObjMap.readValue(
                                                    jsonObject.toString(),
                                                    ApplicationLogHistory.class
                                                );

                                                applicationLogHistoryRepository.save(applicationLogHistory);
                                            }

                                            // updating new error log entry
                                            applicationLog.setIssFileParser(applicationFromKcc.getIssFileParser());

                                            try {
                                                if (applicationsByBatchAckId.getErrors() != null) {
                                                    applicationLog.setErrorMessage(applicationsByBatchAckId.getErrors());
                                                } else {
                                                    applicationLog.setErrorMessage("CBS Portal not provided fail case information");
                                                }
                                            } catch (Exception e) {
                                                log.error("Exception in cron job", e);
                                            }

                                            applicationLog.setSevierity(Constants.HighSevierity);
                                            applicationLog.setExpectedSolution("Provide correct information");
                                            applicationLog.setStatus(Constants.ERROR);
                                            applicationLog.setErrorType(Constants.kccError);
                                            applicationLog.setIssPortalId(applicationFromKcc.getIssFileParser().getIssPortalFile().getId());
                                            applicationLog.setFileName(
                                                applicationFromKcc.getIssFileParser().getIssPortalFile().getFileName()
                                            );
                                            applicationLogListToSave.add(applicationLog);
                                        }

                                        applicationListSave.add(applicationFromKcc);
                                    }

                                    //if Size is greater than one
                                    else if (applicationListFromKcc.size() > 1) {

                                        for (Application applicationFromKcc : applicationListFromKcc) {
                                            String recipientUniqueId = applicationFromKcc.getRecipientUniqueId();
                                            log.error("**************************************************");
                                            log.error("RecipientUniquId " + recipientUniqueId);
                                            log.error("UniqueId " + applicationFromKcc.getUniqueId());
                                            log.error("#########################################################");

                                            //https://fasalrin.gov.in/v1/issintegration/databyrecipientuniqueids

//                                            Request(HTTP POST):
//                                            {
//                                                "authCode":"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
//                                                "data":{
//                                                "batchAckId":"13102200000014",
//                                                    "recipientUniqueIds":[
//                                                        “recipientUniqueId”
//                                                    ]
//                                            }
//                                            }

                                            //RESPONSE:

/*
                                            basicDetails":{
                                            "beneficiaryName, adharNo,
                                            accountDetails":{
                                           "accountNumber":"
                                            loanDetails":{
                                            "kccLoanSanctionedDate":"2022-06-17",
                                                "kccLimitSanctionedAmount

                                                hit query in issFileParser with above parameter
                                                if filepsrerId == applicationTx_ >>file parser Id
*/

                                        }

                                    }

                                }

                                if (!applicationListSave.isEmpty()) {
                                    applicationRepository.saveAll(applicationListSave);
                                }
                                if (!applicationLogListToSave.isEmpty()) {
                                    applicationLogRepository.saveAll(applicationLogListToSave);
                                }
                            }

                        } else {
                            batchTransaction.setBatchDetails("Batch is not processed yet");
                        }
                        batchTransactionRepository.save(batchTransaction);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error in cronjobs: " + e);
                }
            }
        }

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
            log.error("Error while decrypting: " + e);
        }
        return null;
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


    //remove
    @GetMapping("/cronJb")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public void updateRecordsInBatchTrans() {

        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAllByStatus(Constants.NEW);
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        String indianTime = dateFormat.format(new Date());

        log.debug("cron Job started at" + indianTime);
        log.info("cron Job started at" + indianTime);
        log.info("batchTransactionList size =  " + batchTransactionList.size());
        for (BatchTransaction batchTransaction:batchTransactionList) {
            System.out.println("Batch Send to KCC with ID: "+batchTransaction.getBatchId());
        }

        if (!batchTransactionList.isEmpty()) {
            log.info("Batch transaction list not empty");
            for (BatchTransaction batchTransaction : batchTransactionList) {
                log.info("Batch transaction found " + batchTransaction.getId());
                List<Application> applicationListSave = new ArrayList<>();
                String cbsResponceString = "";
                BatchAckId batchAckId = new BatchAckId();
                batchAckId.setBatchAckId(batchTransaction.getBatchAckId());
                String encryption = encryption(batchAckId);

                List<ApplicationLog> applicationLogListToSave = new ArrayList<>();

                // Making input payload
                CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
                cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
                cbsMiddleareInputPayload.setData(encryption);
                System.out.println("CBS PAY LOAD: " + cbsMiddleareInputPayload.toString());

                try {
                    // Set the request URL
                    String url = applicationProperties.getCBSMiddlewareBaseURL() + Constants.databybatchackid;
                    // Set the request headers
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    // Create the HttpEntity object with headers and body
                    HttpEntity<Object> requestEntity = new HttpEntity<>(cbsMiddleareInputPayload, headers);
                    // Make the HTTP POST request
                    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);


                    if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                        cbsResponceString = responseEntity.getBody();
                        log.info("Batch transaction response cbsResponceString = " + cbsResponceString);
                        CBSResponce convertValue = null;
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        objectMapper.registerModule(new JavaTimeModule());
                        convertValue = objectMapper.readValue(cbsResponceString, CBSResponce.class);

                        if (convertValue.isStatus()) {

                            String decryption = decryption("" + convertValue.getData());
                            objectMapper = new ObjectMapper();
                            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            objectMapper.registerModule(new JavaTimeModule());
                            DataByBatchAckId dataByBatchAckId = objectMapper.readValue(decryption, DataByBatchAckId.class);

                            if (dataByBatchAckId.getStatus() == Constants.DISCARDED_BATCH_STATUS_CODE) {
                                batchTransaction.setStatus(Constants.DISCARDED);
                                batchTransaction.setBatchDetails("Batch is discarded");
                            } else if (dataByBatchAckId.getStatus() == Constants.PENDING_FOR_PROCESSING_BATCH_STATUS_CODE) {
                                batchTransaction.setStatus(Constants.PENDING_FOR_PROCESSING);
                                batchTransaction.setBatchDetails("Batch is pending for processing");
                            } else if (dataByBatchAckId.getStatus() == Constants.PROCESSING_BATCH_STATUS_CODE) {
                                batchTransaction.setStatus(Constants.PROCESSING);
                                batchTransaction.setBatchDetails("Batch is processing");
                            } else if (dataByBatchAckId.getStatus() == Constants.PROCESSED_BATCH_STATUS_CODE) {
                                batchTransaction.setStatus(Constants.PROCESSED);
                                batchTransaction.setBatchDetails("Batch is processed");
                            }

                            Long kccApplErrCount = 0l;
                            if (!dataByBatchAckId.getApplications().isEmpty()) {
                                for (ApplicationsByBatchAckId applicationsByBatchAckId : dataByBatchAckId.getApplications()) {
//                                    Application applicationByUniqueId = applicationRepository.findOneByUniqueId(
//                                        applicationsByBatchAckId.getUniqueId()
//                                    );

                                    List<Application> applicationListFromKcc = applicationRepository.findAllByUniqueId(
                                        applicationsByBatchAckId.getUniqueId()
                                    );

                                    if (applicationListFromKcc.size() == 1) {
                                        Application applicationFromKcc = applicationListFromKcc.get(0);
                                        applicationFromKcc.setApplicationStatus(applicationsByBatchAckId.getApplicationStatus());
                                        applicationFromKcc.setRecipientUniqueId(applicationsByBatchAckId.getRecipientUniqueID());

                                        if (applicationsByBatchAckId.getApplicationStatus() == 1) {
                                            applicationFromKcc.setKccStatus(1l);
                                            applicationFromKcc.setApplicationNumber(applicationsByBatchAckId.getApplicationNumber());
                                            applicationFromKcc.setFarmerId(applicationsByBatchAckId.getFarmerId());
                                        } else {
                                            applicationFromKcc.setKccStatus(0l);

                                            try {
                                                if (applicationsByBatchAckId.getErrors() != null) {
                                                    applicationFromKcc.setApplicationErrors(applicationsByBatchAckId.getErrors());
                                                } else {
                                                    applicationFromKcc.setApplicationErrors("CBS Portal not provided fail case information");
                                                }
                                            } catch (Exception e) {
                                                log.error("Exception in cron job", e);
                                            }

                                            kccApplErrCount = kccApplErrCount + 1l;

                                            // setting kscc error count in portal file object
                                            Optional<IssPortalFile> findById = issPortalFileRepository.findById(
                                                applicationFromKcc.getIssFilePortalId()
                                            );
                                            if (findById.isPresent()) {
                                                IssPortalFile issPortalFile = findById.get();
                                                if (issPortalFile.getKccErrorRecordCount() == null) {
                                                    issPortalFile.setKccErrorRecordCount(1);
                                                } else {
                                                    issPortalFile.setKccErrorRecordCount(issPortalFile.getKccErrorRecordCount() + 1);
                                                }

                                                issPortalFileRepository.save(issPortalFile);
                                            }

                                            // moving application log to history if exist
                                            ApplicationLog applicationLog = new ApplicationLog();
                                            Optional<ApplicationLog> applicationLogSaved = applicationLogRepository.findOneByIssFileParser(
                                                applicationFromKcc.getIssFileParser()
                                            );
                                            if (applicationLogSaved.isPresent()) {
                                                applicationLog = applicationLogSaved.get();
                                                JSONObject jsonObject = new JSONObject(applicationLog);
                                                ObjectMapper applicationLogHistoryObjMap = new ObjectMapper();
                                                applicationLogHistoryObjMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                                applicationLogHistoryObjMap.registerModule(new JavaTimeModule());
                                                ApplicationLogHistory applicationLogHistory = applicationLogHistoryObjMap.readValue(
                                                    jsonObject.toString(),
                                                    ApplicationLogHistory.class
                                                );

                                                applicationLogHistoryRepository.save(applicationLogHistory);
                                            }

                                            // updating new error log entry
                                            applicationLog.setIssFileParser(applicationFromKcc.getIssFileParser());

                                            try {
                                                if (applicationsByBatchAckId.getErrors() != null) {
                                                    applicationLog.setErrorMessage(applicationsByBatchAckId.getErrors());
                                                } else {
                                                    applicationLog.setErrorMessage("CBS Portal not provided fail case information");
                                                }
                                            } catch (Exception e) {
                                                log.error("Exception in cron job", e);
                                            }

                                            applicationLog.setSevierity(Constants.HighSevierity);
                                            applicationLog.setExpectedSolution("Provide correct information");
                                            applicationLog.setStatus(Constants.ERROR);
                                            applicationLog.setErrorType(Constants.kccError);
                                            applicationLog.setIssPortalId(applicationFromKcc.getIssFileParser().getIssPortalFile().getId());
                                            applicationLog.setFileName(
                                                applicationFromKcc.getIssFileParser().getIssPortalFile().getFileName()
                                            );
                                            applicationLogListToSave.add(applicationLog);
                                        }

                                        applicationListSave.add(applicationFromKcc);
                                    }

                                    //if Size is greater than one
                                    else if (applicationListFromKcc.size() > 1) {

                                        for (Application applicationFromKcc : applicationListFromKcc) {
                                            String recipientUniqueId = applicationFromKcc.getRecipientUniqueId();
                                            log.error("**************************************************");
                                            log.error("RecipientUniquId " + recipientUniqueId);
                                            log.error("UniqueId " + applicationFromKcc.getUniqueId());
                                            log.error("#########################################################");

                                            //https://fasalrin.gov.in/v1/issintegration/databyrecipientuniqueids

//                                            Request(HTTP POST):
//                                            {
//                                                "authCode":"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
//                                                "data":{
//                                                "batchAckId":"13102200000014",
//                                                    "recipientUniqueIds":[
//                                                        “recipientUniqueId”
//                                                    ]
//                                            }
//                                            }

                                            //RESPONSE:

/*
                                            basicDetails":{
                                            "beneficiaryName, adharNo,
                                            accountDetails":{
                                           "accountNumber":"
                                            loanDetails":{
                                            "kccLoanSanctionedDate":"2022-06-17",
                                                "kccLimitSanctionedAmount

                                                hit query in issFileParser with above parameter
                                                if filepsrerId == applicationTx_ >>file parser Id
*/

                                        }

                                    }

                                }

                                if (!applicationListSave.isEmpty()) {
                                    applicationRepository.saveAll(applicationListSave);
                                }
                                if (!applicationLogListToSave.isEmpty()) {
                                    applicationLogRepository.saveAll(applicationLogListToSave);
                                }
                            }

                        } else {
                            batchTransaction.setBatchDetails("Batch is not processed yet");
                        }
                        batchTransactionRepository.save(batchTransaction);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error in cronjobs: " + e);
                }
            }
        }

    }

}
