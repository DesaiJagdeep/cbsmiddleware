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
import com.cbs.middleware.repository.*;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
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
    RetryBatchTransactionRepository retryBatchTransactionRepository;

    @Autowired
    RetryBatchTransactionDetailsRepository retryBatchTransactionDetailsRepository;

    @Autowired
    RBAControl rbaControl;

    public CronJobResource() {}

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

        log.debug("Crown Job started at"+ indianTime);
        log.info("Crown Job started at"+ indianTime);
        log.info("batchTransactionList size =  "+batchTransactionList.size());
        if (!batchTransactionList.isEmpty()) {
            log.info("Batch transaction list not empty");
            for (BatchTransaction batchTransaction : batchTransactionList) {
                log.info("Batch transaction found "+batchTransaction.getId());
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
                        log.info("Batch transaction response cbsResponceString = "+cbsResponceString);
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
                                    Application applicationByUniqueId = applicationRepository.findOneByUniqueId(
                                        applicationsByBatchAckId.getUniqueId()
                                    );

                                    applicationByUniqueId.setApplicationStatus(applicationsByBatchAckId.getApplicationStatus());
                                    applicationByUniqueId.setRecipientUniqueId(applicationsByBatchAckId.getRecipientUniqueID());

                                    if (applicationsByBatchAckId.getApplicationStatus() == 1) {
                                        applicationByUniqueId.setKccStatus(1l);
                                        applicationByUniqueId.setApplicationNumber(applicationsByBatchAckId.getApplicationNumber());
                                        applicationByUniqueId.setFarmerId(applicationsByBatchAckId.getFarmerId());
                                    } else {
                                        applicationByUniqueId.setKccStatus(0l);

                                        try {
                                            if (applicationsByBatchAckId.getErrors() != null) {
                                                applicationByUniqueId.setApplicationErrors(applicationsByBatchAckId.getErrors());
                                            } else {
                                                applicationByUniqueId.setApplicationErrors("CBS Portal not provided fail case information");
                                            }
                                        } catch (Exception e) {
                                            log.error("Exception in cron job", e);
                                        }

                                        kccApplErrCount = kccApplErrCount + 1l;

                                        // setting kscc error count in portal file object
                                        Optional<IssPortalFile> findById = issPortalFileRepository.findById(
                                            applicationByUniqueId.getIssFilePortalId()
                                        );
                                        if (findById.isPresent()) {
                                            IssPortalFile issPortalFile = findById.get();
                                            if (issPortalFile.getKccErrorRecordCount() == null) {
                                            	issPortalFile.setKccErrorRecordCount(1);
                                            }
                                            else
                                            {
                                            	issPortalFile.setKccErrorRecordCount(issPortalFile.getKccErrorRecordCount() + 1);
                                            }

                                            issPortalFileRepository.save(issPortalFile);
                                        }

                                        // moving application log to history if exist
                                        ApplicationLog applicationLog = new ApplicationLog();
                                        Optional<ApplicationLog> applicationLogSaved = applicationLogRepository.findOneByIssFileParser(
                                            applicationByUniqueId.getIssFileParser()
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
                                        applicationLog.setIssFileParser(applicationByUniqueId.getIssFileParser());

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
                                        applicationLog.setIssPortalId(applicationByUniqueId.getIssFileParser().getIssPortalFile().getId());
                                        applicationLog.setFileName(
                                            applicationByUniqueId.getIssFileParser().getIssPortalFile().getFileName()
                                        );
                                        applicationLogListToSave.add(applicationLog);
                                    }

                                    applicationListSave.add(applicationByUniqueId);
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
                    log.error("Error in cronjob: " + e);
                }
            }
        }
    }



    //Ashvini
    @GetMapping("/cronJobToUpdateRetryBatch")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public void updateRecordsInRetryBatchTran(@RequestParam("batchId") String batchId) {

        List<RetryBatchTransaction> retryBatchTransactionList = new ArrayList<>();
        if(batchId.equals("0000"))
        {
            retryBatchTransactionList = retryBatchTransactionRepository.findAllByStatus(Constants.SUBMITTED);

        }
        else {

        RetryBatchTransaction retryBatch =  retryBatchTransactionRepository.findRetryBatchTransactionByBatchId(batchId);
            retryBatchTransactionList.add(retryBatch);
        }

        System.out.println("RetryTransactionList: " + retryBatchTransactionList.get(0).getBatchId());
        System.out.println("RetryTransactionList size: " + retryBatchTransactionList.size());

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        String indianTime = dateFormat.format(new Date());

        if (!retryBatchTransactionList.isEmpty()) {
            log.info("Retry Batch transaction list not empty");
            System.out.println("Retry Batch transaction list not empty");
            for (RetryBatchTransaction retryBatchTransaction : retryBatchTransactionList) {
                log.info("Retry Batch transaction found "+retryBatchTransaction.getId());
                System.out.println("Retry Batch transaction found "+retryBatchTransaction.getId());

    List<Application> applicationListSave = new ArrayList<>();
    List<RetryBatchTransactionDetails> retryBatchTransactionapplicationListSave = new ArrayList<>();

    String cbsResponceString = "";
    BatchAckId batchAckId = new BatchAckId();
    batchAckId.setBatchAckId(retryBatchTransaction.getBatchAckId());

    String encryption = encryption(batchAckId);

    List<ApplicationLog> applicationLogListToSave = new ArrayList<>();

    Integer totalApplicationCount = 0;
    // Making input payload
    CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
    cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
    cbsMiddleareInputPayload.setData(encryption);

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
            log.info("Retry Batch transaction response cbsResponceString = "+cbsResponceString);
            System.out.println("Retry Batch transaction response cbsResponceString = "+cbsResponceString);
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

                totalApplicationCount=dataByBatchAckId.getApplications().size();


                Long kccApplErrCount = 0l;
                if (!dataByBatchAckId.getApplications().isEmpty()) {
                    for (ApplicationsByBatchAckId applicationsByBatchAckId : dataByBatchAckId.getApplications()) {

                        System.out.println("Application from batch ack result: " +applicationsByBatchAckId);

                        //application_transaction
                        Application applicationByUniqueId = applicationRepository.findOneByUniqueId(
                            applicationsByBatchAckId.getUniqueId()
                        );

                        //application_log
                        Optional<ApplicationLog> applicationLog = applicationLogRepository.findOneByIssFileParser(
                            applicationByUniqueId.getIssFileParser()
                        );

                        //retryBatchTransactionDetails
                        RetryBatchTransactionDetails retryBatchTransactionDetailsByUniqueId = retryBatchTransactionDetailsRepository.findOneByUniqueId(
                            applicationsByBatchAckId.getUniqueId()
                        );

                        applicationByUniqueId.setApplicationStatus(applicationsByBatchAckId.getApplicationStatus());
                        applicationByUniqueId.setRecipientUniqueId(applicationsByBatchAckId.getRecipientUniqueID());

                        if (applicationsByBatchAckId.getApplicationStatus() == 1) {
                            System.out.println("application status is 1");
                            applicationByUniqueId.setKccStatus(1l);
                            applicationByUniqueId.setApplicationNumber(applicationsByBatchAckId.getApplicationNumber());
                            applicationByUniqueId.setFarmerId(applicationsByBatchAckId.getFarmerId());

                            //update applicationLog based on result
                            applicationLog.get().setStatus(Constants.FIXED);

                            //Update application in retry batch transaction details as Accepted
                            retryBatchTransactionDetailsByUniqueId.setStatus("1");


                        } else {
                            System.out.println("application status is 0");
                            applicationByUniqueId.setKccStatus(0l);

                            try {
                                if (applicationsByBatchAckId.getErrors() != null) {
                                    applicationByUniqueId.setApplicationErrors(applicationsByBatchAckId.getErrors());
                                } else {
                                    applicationByUniqueId.setApplicationErrors("CBS Portal not provided fail case information");
                                }
                            } catch (Exception e) {
                                log.error("Exception in Retry Batch cron job ", e);
                            }

                            kccApplErrCount = kccApplErrCount + 1l;

                            // setting kscc error count in portal file object
                            Optional<IssPortalFile> findById = issPortalFileRepository.findById(
                                applicationByUniqueId.getIssFilePortalId()
                            );
                            if (findById.isPresent()) {
                                IssPortalFile issPortalFile = findById.get();
                                if (issPortalFile.getKccErrorRecordCount() == null) {
                                    issPortalFile.setKccErrorRecordCount(1);
                                }
                                else
                                {
                                    issPortalFile.setKccErrorRecordCount(issPortalFile.getKccErrorRecordCount() + 1);
                                }

                                issPortalFileRepository.save(issPortalFile);
                            }

                            // moving application log to history if exist
                            ApplicationLog applicationLog1 = new ApplicationLog();
                            Optional<ApplicationLog> applicationLogSaved = applicationLogRepository.findOneByIssFileParser(
                                applicationByUniqueId.getIssFileParser()
                            );
                            if (applicationLogSaved.isPresent()) {
                                applicationLog1 = applicationLogSaved.get();
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
                            applicationLog1.setIssFileParser(applicationByUniqueId.getIssFileParser());

                            try {
                                if (applicationsByBatchAckId.getErrors() != null) {
                                    applicationLog1.setErrorMessage(applicationsByBatchAckId.getErrors());
                                } else {
                                    applicationLog1.setErrorMessage("CBS Portal not provided fail case information");
                                }
                            } catch (Exception e) {
                                log.error("Exception in Retry Batch cron job ", e);
                            }

                            applicationLog1.setSevierity(Constants.HighSevierity);
                            applicationLog1.setExpectedSolution("Provide correct information");
                            applicationLog1.setStatus(Constants.ERROR);
                            applicationLog1.setErrorType(Constants.kccError);
                            applicationLog1.setIssPortalId(applicationByUniqueId.getIssFileParser().getIssPortalFile().getId());
                            applicationLog1.setFileName(
                                applicationByUniqueId.getIssFileParser().getIssPortalFile().getFileName()
                            );
                            applicationLogListToSave.add(applicationLog1);
                        }
                        applicationListSave.add(applicationByUniqueId);
                        applicationLogListToSave.add(applicationLog.get());
                        retryBatchTransactionapplicationListSave.add(retryBatchTransactionDetailsByUniqueId);
                    }


                    if (!applicationListSave.isEmpty()) {
                        applicationRepository.saveAll(applicationListSave);
                    }
                    if (!applicationLogListToSave.isEmpty()) {
                        applicationLogRepository.saveAll(applicationLogListToSave);
                    }
                    if (!retryBatchTransactionapplicationListSave.isEmpty()) {
                        retryBatchTransactionDetailsRepository.saveAll(retryBatchTransactionapplicationListSave);
                    }

                    System.out.println("applicationListSave: "+applicationListSave);
                    System.out.println("applicationLogListToSave: "+applicationLogListToSave);
                    System.out.println("retryBatchTransactionapplicationListSave: "+retryBatchTransactionapplicationListSave);
                }
            } else {
                retryBatchTransaction.setBatchErrors("Batch is not processed yet");
            }

            /* Retrieve RetryBatchTransactionDetails record with status 1*/
            Integer batchTransactionCount=retryBatchTransactionDetailsRepository.countByStatus(retryBatchTransaction.getBatchAckId());

            //total application count is equal batch transaction count, set batch status as PROCESSED
            if(totalApplicationCount==batchTransactionCount)
            {
                retryBatchTransaction.setStatus(Constants.PROCESSED);
            }
            retryBatchTransactionRepository.save(retryBatchTransaction);
        }
    } catch (Exception e) {
        e.printStackTrace();
        log.error("Error in Retry Job cronjob: " + e);
    }


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
            System.err.println("Error in coversion of objectToBytes" + e);
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
            log.error("Error while decrypting: " + e);
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


    //remove
    @GetMapping("/cronJb")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public void updateRecordsInBatchTrans() {
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAllByStatus(Constants.NEW);

        if (!batchTransactionList.isEmpty()) {
            for (BatchTransaction batchTransaction : batchTransactionList) {
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
                                    Application applicationByUniqueId = applicationRepository.findOneByUniqueId(
                                        applicationsByBatchAckId.getUniqueId()
                                    );

                                    applicationByUniqueId.setApplicationStatus(applicationsByBatchAckId.getApplicationStatus());
                                    applicationByUniqueId.setRecipientUniqueId(applicationsByBatchAckId.getRecipientUniqueID());

                                    if (applicationsByBatchAckId.getApplicationStatus() == 1) {
                                        applicationByUniqueId.setKccStatus(1l);
                                        applicationByUniqueId.setApplicationNumber(applicationsByBatchAckId.getApplicationNumber());
                                        applicationByUniqueId.setFarmerId(applicationsByBatchAckId.getFarmerId());
                                    } else {
                                        applicationByUniqueId.setKccStatus(0l);

                                        try {
                                            if (applicationsByBatchAckId.getErrors() != null) {
                                                applicationByUniqueId.setApplicationErrors(applicationsByBatchAckId.getErrors());
                                            } else {
                                                applicationByUniqueId.setApplicationErrors("CBS Portal not provided fail case information");
                                            }
                                        } catch (Exception e) {
                                            log.error("Exception in cron job", e);
                                        }

                                        kccApplErrCount = kccApplErrCount + 1l;

                                        // setting kscc error count in portal file object
                                        Optional<IssPortalFile> findById = issPortalFileRepository.findById(
                                            applicationByUniqueId.getIssFilePortalId()
                                        );
                                        if (findById.isPresent()) {
                                            IssPortalFile issPortalFile = findById.get();
                                            if (issPortalFile.getKccErrorRecordCount() == null) {
                                            	issPortalFile.setKccErrorRecordCount(1);
                                            }
                                            else
                                            {
                                            	issPortalFile.setKccErrorRecordCount(issPortalFile.getKccErrorRecordCount() + 1);
                                            }

                                            issPortalFileRepository.save(issPortalFile);
                                        }

                                        // moving application log to history if exist
                                        ApplicationLog applicationLog = new ApplicationLog();
                                        Optional<ApplicationLog> applicationLogSaved = applicationLogRepository.findOneByIssFileParser(
                                            applicationByUniqueId.getIssFileParser()
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
                                        applicationLog.setIssFileParser(applicationByUniqueId.getIssFileParser());

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
                                        applicationLog.setIssPortalId(applicationByUniqueId.getIssFileParser().getIssPortalFile().getId());
                                        applicationLog.setFileName(
                                            applicationByUniqueId.getIssFileParser().getIssPortalFile().getFileName()
                                        );
                                        applicationLogListToSave.add(applicationLog);
                                    }

                                    applicationListSave.add(applicationByUniqueId);
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
                    log.error("Error in cronjob: " + e);
                }
            }
        }
    }

}
