package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.config.MasterDataCacheService;
import com.cbs.middleware.domain.*;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.repository.CenterReportJuneRepository;
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
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    CenterReportJuneRepository centerReportJuneRepository;

    public SubmitClaimBatchResource(ApplicationRepository applicationRepository, CenterReportJuneRepository centerReportJuneRepository) {
        this.applicationRepository = applicationRepository;
        this.centerReportJuneRepository = centerReportJuneRepository;
    }

    @PostMapping("/submit-claim-batch")
    public CBSResponce submitClaimBatch(@RequestBody InterestSubventionDTO interestSubventionDTO) {
        CBSResponce cbsResponce = null;
     //find distinct farmers by pacs
        List<String> distinctAadhars=  centerReportJuneRepository.DistinctAadharCenterReportJune(interestSubventionDTO.getPacsNumber(),interestSubventionDTO.getFinancialYear());

        //Loop through addhars
        for(String aadharNo:distinctAadhars){
            //find distinct parser id(loan applications) by aadhar

            List<Long> distinctParsers= centerReportJuneRepository.DistinctIssFileParserIdByAadhar("901423243268");

            processBatch(distinctParsers,interestSubventionDTO);

        }

        return cbsResponce;
    }

    private CBSResponce processBatch(List<Long> issFileParsers,InterestSubventionDTO interestSubventionDTO) {
        // Batch ID (14 Digits): <6 character DDMMYY>+<3 character Bank code as per
        // NCIP>+< 5 digit
        // running number>
        // If Today’s date is 29 Aug 2022 the Batch id can be 29082202300001
        String cbsResponceString = "";
        List<ClaimApplicationPayload> applicationsList = new ArrayList<>();
        ClaimBatchData claimBatchData = new ClaimBatchData();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String formattedDate = dateFormat.format(currentDate);

        List<Application> applicationTransactionListSave = new ArrayList<>();
        claimBatchData.setFinancialYear(interestSubventionDTO.getFinancialYear());
        for (Long IssFileParserId:issFileParsers){

            //Check if application is accepted by KCC or not
            List<Application> applicationList= applicationRepository.findRejectedApplicatonsByParserId(IssFileParserId);
            Application application= applicationList.get(0);
            if (applicationList.get(0)!= null){

                if (claimBatchData.getBatchId() != null){
                    String batchId = formattedDate + application.getIssFileParser().getBankCode() + generateRandomNumber();
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
                        subType=0;
                    }

                    for (CenterReportJune centerReportJune:centerReportJuneList) {
                        if (centerReportJune.getIsRecover()==1) {

                            //calculate IS amount: 3%
                            Double applicableISAmt = centerReportJune.getInterestStateFirst3() + centerReportJune.getInterestStateSecond3();
                            ClaimApplicationPayload claimApplicationPayload = new ClaimApplicationPayload();
                            claimApplicationPayload.setUniqueId(application.getUniqueId());
                            claimApplicationPayload.setLoanApplicationNumber(application.getApplicationNumber());
                            claimApplicationPayload.setClaimType("IS");
                            claimApplicationPayload.setSubmissionType(subType);
                            claimApplicationPayload.setFirstLoanDisbursalDate(String.valueOf(centerReportJuneList.get(0).getDisbursementDate()));
                            claimApplicationPayload.setLoanRepaymentDate(String.valueOf(centerReportJune.getRecoveryDate()));
                            claimApplicationPayload.setMaxWithdrawalAmount(centerReportJune.getDisburseAmount());
                            claimApplicationPayload.setApplicableISAmount(applicableISAmt);
                            applicationsList.add(claimApplicationPayload);
                        }

                        //PRI : 1.5% & 2.5%
                        ClaimApplicationPayload claimApplicationPayload = new ClaimApplicationPayload();
                        Double applicablePRIAmt = centerReportJune.getInterestFirst15() + centerReportJune.getInterestSecond15();
                        claimApplicationPayload.setUniqueId("");
                        claimApplicationPayload.setLoanApplicationNumber(application.getApplicationNumber());
                        claimApplicationPayload.setClaimType("PRI");
                        claimApplicationPayload.setSubmissionType(subType);
                        claimApplicationPayload.setFirstLoanDisbursalDate(String.valueOf(centerReportJune.getDisbursementDate()));
                        claimApplicationPayload.setLoanRepaymentDate(String.valueOf(centerReportJune.getRecoveryDate()));
                        claimApplicationPayload.setMaxWithdrawalAmount(centerReportJune.getDisburseAmount());
                        claimApplicationPayload.setIsEligibleForPRI(1);
                        claimApplicationPayload.setApplicablePRIAmount(applicablePRIAmt);
                        applicationsList.add(claimApplicationPayload);
                    }


                }


                    }


                }




//        for (Application applicationTransaction : applicationTransactionList) {
//            IssFileParser issFileParser = applicationTransaction.getIssFileParser();
//
//            ClaimApplicationPayload claimApplicationPayload = new ClaimApplicationPayload();
//
//
//            claimApplicationPayload.setUniqueId(applicationTransaction.getUniqueId());
//            claimApplicationPayload.setLoanApplicationNumber(applicationTransaction.getApplicationNumber());
//            claimApplicationPayload.setClaimType("IS");
//            claimApplicationPayload.setSubmissionType(1);
//            claimApplicationPayload.setFirstLoanDisbursalDate(applicationTransactionList.get(0).getIssFileParser().getDisbursementDate());
//            claimApplicationPayload.setLoanRepaymentDate(applicationTransactionList.get(0).getIssFileParser().getRecoveryDate());
//            claimApplicationPayload.setMaxWithdrawalAmount(Math.round(Double.parseDouble(applicationTransactionList.get(0).getIssFileParser().getLoanSanctionAmount())));
//            claimApplicationPayload.setApplicableISAmount(Math.round(Double.parseDouble(applicationTransactionList.get(0).getIssFileParser().getDisburseAmount())));
//
//
//            applicationsList.add(claimApplicationPayload);
//
//            applicationTransaction.setBatchId(batchId);
//
//            applicationTransaction.setPacksCode(Long.parseLong(applicationTransaction.getIssFileParser().getPacsNumber()));
//            applicationTransaction.setSchemeWiseBranchCode(Long.parseLong(applicationTransaction.getIssFileParser().getSchemeWiseBranchCode()));
//            applicationTransaction.setBankCode(Long.parseLong(applicationTransaction.getIssFileParser().getBankCode()));
//            applicationTransactionListSave.add(applicationTransaction);
//
//        }

        claimBatchData.setApplications(applicationsList);
        System.out.println("batchData: " + claimBatchData);
        String encryption = encryptObject(claimBatchData);

        // Making input payload
        CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
        cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
        cbsMiddleareInputPayload.setData(encryption);

        CBSResponce cbsResponce = null;

        // call fasalrin submit api
        /*try {
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
                        applicationRepository.saveAll(applicationTransactionListSave);
                        String decryption = decryption("" + cbsResponce.getData());
                        objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        submitApiRespDecryption = objectMapper.readValue(decryption, SubmitApiRespDecryption.class);

                        BatchTransaction batchTransaction = new BatchTransaction();
                        batchTransaction.setApplicationCount((long) applicationTransactionListSave.size());
                        batchTransaction.setPacksCode(applicationTransactionListSave.get(0).getPacksCode());
                        batchTransaction.setSchemeWiseBranchCode(applicationTransactionListSave.get(0).getSchemeWiseBranchCode());
                        batchTransaction.setBankCode(applicationTransactionListSave.get(0).getBankCode());

                        batchTransaction.setBatchId(batchId);
                        batchTransaction.setStatus("New");
                        batchTransaction.setBatchAckId(submitApiRespDecryption.getBatchAckId());
                      //  batchTransactionRepository.save(batchTransaction);

                        try
                        {
                            IssPortalFile issPortalFile = applicationTransactionListSave.get(0).getIssFileParser().getIssPortalFile();
                            issPortalFile.setAppSubmitedToKccCount(issPortalFile.getAppSubmitedToKccCount()+(long)applicationTransactionListSave.size());
                            //issPortalFileRepository.save(issPortalFile);

                        }catch (Exception e) {

                        }


                    } else {
                        BatchTransaction batchTransaction = new BatchTransaction();
                        batchTransaction.setApplicationCount((long) applicationTransactionListSave.size());
                        batchTransaction.setPacksCode(applicationTransactionListSave.get(0).getPacksCode());
                        batchTransaction.setSchemeWiseBranchCode(applicationTransactionListSave.get(0).getSchemeWiseBranchCode());
                        batchTransaction.setBankCode(applicationTransactionListSave.get(0).getBankCode());
                        batchTransaction.setBatchId(batchId);
                        batchTransaction.setStatus("Discarded");
                        batchTransaction.setBatchErrors(cbsResponce.getError());
                        String fileName="";
                        try
                        {
                            fileName=applicationTransactionListSave.get(0).getIssFileParser().getIssPortalFile().getFileName();
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
        }*/

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
