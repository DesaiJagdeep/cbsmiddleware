package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.config.MasterDataCacheService;
import com.cbs.middleware.domain.*;
import com.cbs.middleware.repository.*;
import com.cbs.middleware.service.dto.InterestSubventionDTO;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api")
public class SubmitClaimBatchResource {

    private final Logger log = LoggerFactory.getLogger(SubmitClaimBatchResource.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ApplicationProperties applicationProperties;
    private final ApplicationRepository applicationRepository;

    @Autowired
    CenterReportJuneRepository centerReportJuneRepository;
    @Autowired
    SubmitBatchResource submitBatchResource;
    public SubmitClaimBatchResource(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @PostMapping("/submit-claim-batch")
    public CBSResponce submitClaimBatch(@RequestBody InterestSubventionDTO interestSubventionDTO) {
        CBSResponce cbsResponce = null;
     //find distinct farmers by pacs
        List<String> distinctAadhars=  centerReportJuneRepository.DistinctAadharCenterReportJune(interestSubventionDTO.getPacsNumber(),interestSubventionDTO.getFinancialYear());

        //Loop through addhars
//        for(String aadharNo:distinctAadhars){
            //find distinct parser id(loan applications) by aadhar
            List<Long> distinctParsers= centerReportJuneRepository.DistinctIssFileParserIdByAadhar("416380656067");

         cbsResponce=   processBatch(distinctParsers,interestSubventionDTO);
//        }

        return cbsResponce;
    }

    private CBSResponce processBatch(List<Long> distinctParsers,InterestSubventionDTO interestSubventionDTO) {
        // Batch ID (14 Digits): <6 character DDMMYY>+<3 character Bank code as per
        // NCIP>+< 5 digit
        // running number>
        // If Today’s date is 29 Aug 2022 the Batch id can be 29082202300001
        String cbsResponceString = "";
        String batchId="";
        List<ClaimApplicationPayload> applicationsList = new ArrayList<>();
        ClaimBatchData claimBatchData = new ClaimBatchData();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String formattedDate = dateFormat.format(currentDate);

        List<Application> applicationTransactionListSave = new ArrayList<>();
        claimBatchData.setFinancialYear(interestSubventionDTO.getFinancialYear());
        Application application =new Application();

for (Long IssFileParserId:distinctParsers){
    //Check if application is accepted by KCC or not
    application= applicationRepository.findApplicatonsByParserId(IssFileParserId);

    if (application != null){

        if (claimBatchData.getBatchId() == null){
            batchId = formattedDate + application.getBankCode() + generateRandomNumber();
            claimBatchData.setBatchId(batchId);
        }

        //find by parser id
        List<CenterReportJune> centerReportJuneList = centerReportJuneRepository.SelectFromCenterReportJuneByParserId(IssFileParserId);

        Integer subType;
        if (!centerReportJuneList.isEmpty()){
            //Only one recovery: COMPLETE
            if (centerReportJuneList.size()==1 && centerReportJuneList.get(0).getRecoveryAmount()>0) {
                //SubmissionType=1
                subType = 1;
            }
            //More than one recovery:PARTIAL
            else {
                subType=2;
            }

            for (CenterReportJune centerReportJune:centerReportJuneList) {
                if (centerReportJune.getIsRecover()==1) {
                    String uniqueId = claimBatchData.getBatchId() + generateRandomNumber();

                    //calculate IS amount: First & Second 3%
                    Double applicableISAmt = centerReportJune.getInterestStateFirst3() + centerReportJune.getInterestStateSecond3();
                    ClaimApplicationPayload claimApplicationPayload = new ClaimApplicationPayload();
                    claimApplicationPayload.setUniqueId(uniqueId);
                    claimApplicationPayload.setLoanApplicationNumber(application.getApplicationNumber());
                    claimApplicationPayload.setClaimType("IS");
                    claimApplicationPayload.setSubmissionType(subType);
                    claimApplicationPayload.setFirstLoanDisbursalDate(String.valueOf(centerReportJuneList.get(0).getDisbursementDate()));
                    claimApplicationPayload.setLoanRepaymentDate(String.valueOf(centerReportJune.getRecoveryDate()));
                    claimApplicationPayload.setMaxWithdrawalAmount(centerReportJune.getRecoveryAmount());
                    claimApplicationPayload.setApplicableISAmount(applicableISAmt);
                    applicationsList.add(claimApplicationPayload);
                }
                String uniqueId = claimBatchData.getBatchId() + generateRandomNumber();
                //PRI : First and Second 1.5%
                ClaimApplicationPayload claimApplicationPayload = new ClaimApplicationPayload();
                Double applicablePRIAmt = centerReportJune.getInterestFirst15() + centerReportJune.getInterestSecond15();
                claimApplicationPayload.setUniqueId(uniqueId);
                claimApplicationPayload.setLoanApplicationNumber(application.getApplicationNumber());
                claimApplicationPayload.setClaimType("PRI");
                claimApplicationPayload.setSubmissionType(subType);
                claimApplicationPayload.setFirstLoanDisbursalDate(String.valueOf(centerReportJuneList.get(0).getDisbursementDate()));
                claimApplicationPayload.setLoanRepaymentDate(String.valueOf(centerReportJune.getRecoveryDate()));
                claimApplicationPayload.setMaxWithdrawalAmount(centerReportJune.getRecoveryAmount());
                claimApplicationPayload.setIsEligibleForPRI(1);
                claimApplicationPayload.setApplicablePRIAmount(applicablePRIAmt);
                applicationsList.add(claimApplicationPayload);
            }


        }


    }

}

        claimBatchData.setApplications(applicationsList);
        System.out.println("batchData: " + claimBatchData);
        String encryption = encryptObject(claimBatchData);

        // Making input payload
        CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
        cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
        cbsMiddleareInputPayload.setData(encryption);

        CBSResponce cbsResponce = null;

        // call fasalrin submit api
        try {
            // Set the request URL
            String url = applicationProperties.getCBSMiddlewareBaseURL() + Constants.submitbatch;
            // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Create the HttpEntity object with headers and body
            HttpEntity<Object> requestEntity = new HttpEntity<>(cbsMiddleareInputPayload, headers);
            // Make the HTTP POST request
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                cbsResponceString = responseEntity.getBody();

                SubmitApiRespDecryption submitApiRespDecryption = null;

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    cbsResponce = objectMapper.readValue(cbsResponceString, CBSResponce.class);
                    cbsResponce.setBatchId(batchId);
                    if (cbsResponce.isStatus()) {

                      //  applicationRepository.saveAll(applicationTransactionListSave);
                        String decryption = decryption("" + cbsResponce.getData());
                        objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        submitApiRespDecryption = objectMapper.readValue(decryption, SubmitApiRespDecryption.class);
                        System.out.println("submitApiRespDecryption.getBatchAckId()"+submitApiRespDecryption.getBatchAckId());
                        BatchTransaction batchTransaction = new BatchTransaction();
                        batchTransaction.setApplicationCount((long) applicationTransactionListSave.size());
                        batchTransaction.setPacksCode(application.getPacksCode());
                        batchTransaction.setSchemeWiseBranchCode(application.getSchemeWiseBranchCode());
                        batchTransaction.setBankCode(application.getBankCode());
                        batchTransaction.setBatchId(batchId);
                        batchTransaction.setStatus("New");
                        batchTransaction.setBatchAckId(submitApiRespDecryption.getBatchAckId());
                      //  batchTransactionRepository.save(batchTransaction);

                        try
                        {
                            IssPortalFile issPortalFile = application.getIssFileParser().getIssPortalFile();
                            issPortalFile.setAppSubmitedToKccCount(issPortalFile.getAppSubmitedToKccCount()+(long)applicationTransactionListSave.size());
                            //issPortalFileRepository.save(issPortalFile);

                        }catch (Exception e) {

                        }


                    } else {
                        BatchTransaction batchTransaction = new BatchTransaction();
                        batchTransaction.setApplicationCount((long) applicationTransactionListSave.size());
                        batchTransaction.setPacksCode(application.getPacksCode());
                        batchTransaction.setSchemeWiseBranchCode(application.getSchemeWiseBranchCode());
                        batchTransaction.setBankCode(application.getBankCode());
                        batchTransaction.setBatchId(batchId);
                        batchTransaction.setStatus("Discarded");
                        batchTransaction.setBatchErrors(cbsResponce.getError());
                        String fileName="";
                        try
                        {
                            fileName=application.getIssFileParser().getIssPortalFile().getFileName();
                        }catch (Exception e) {
                            // TODO: handle exception
                        }

                        batchTransaction.setNotes("Resubmit batch after some time for file name:"+fileName);
                       // batchTransactionRepository.save(batchTransaction);
                    }
                } catch (Exception e) {
                    log.error("Error in conversion of object: ", e);
                }
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                cbsResponce = objectMapper.readValue(cbsResponceString, CBSResponce.class);
                cbsResponce.setBatchId(batchId);
            }
        } catch (Exception e) {
            log.error("Error in sending data to fasalrin: " + cbsMiddleareInputPayload);
        }

        return cbsResponce;

    }
    public String encryptObject(Object encDecObject) {
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

    private int generateRandomNumber() {
        Random random = new Random();
        int min = 10000;
        int max = 99999;
        return random.nextInt(max - min + 1) + min;
    }

}
