package com.cbs.middleware.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.config.MasterDataCacheService;
import com.cbs.middleware.domain.AccountDetails;
import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.Activities;
import com.cbs.middleware.domain.ActivityRows;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.ApplicationPayload;
import com.cbs.middleware.domain.BasicDetails;
import com.cbs.middleware.domain.BatchData;
import com.cbs.middleware.domain.BatchTransaction;
import com.cbs.middleware.domain.CBSMiddleareInputPayload;
import com.cbs.middleware.domain.CBSResponce;
import com.cbs.middleware.domain.CastCategoryMaster;
import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.domain.DesignationMaster;
import com.cbs.middleware.domain.FarmerCategoryMaster;
import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.domain.JointAccountHolders;
import com.cbs.middleware.domain.LandTypeMaster;
import com.cbs.middleware.domain.LoanDetails;
import com.cbs.middleware.domain.OccupationMaster;
import com.cbs.middleware.domain.RelativeMaster;
import com.cbs.middleware.domain.ResidentialDetails;
import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.domain.SubmitApiRespDecryption;
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
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssFileParser}.
 */
@RestController
@RequestMapping("/api")
public class SubmitBatchResource {

    private final Logger log = LoggerFactory.getLogger(SubmitBatchResource.class);

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


    public SubmitBatchResource() {}

    /**
     * Customize code
     *
     * @throws Exception
     */

    @PostMapping("/submit-batch")
    @PreAuthorize("@authentication.hasPermision('',#issPortalFile.id,'','SUBMIT_BATCH','SUBMIT')")
    public List<CBSResponce> submitBatch(@RequestBody IssPortalFile issPortalFile) {

    	if (issPortalFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

		Optional<IssPortalFile> issPortalFileObj = issPortalFileRepository.findById(issPortalFile.getId());

		if (!issPortalFileObj.isPresent()) {
			   throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}


        List<Application> applicationList = applicationRepository.findAllByBatchIdAndApplicationStatusAndIssFilePortalId(
            null,
            Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD,
            issPortalFileObj.get().getId()
        );

        int batchSize = 999;
        List<CBSResponce> cbsResponceStringList = new ArrayList<>();
        for (int i = 0; i < applicationList.size(); i += batchSize) {
            List<Application> batch = applicationList.subList(i, Math.min(i + batchSize, applicationList.size()));
            CBSResponce cbsResponce = processBatch(batch);
            cbsResponceStringList.add(cbsResponce);
        }

        return cbsResponceStringList;
    }

    private CBSResponce processBatch(List<Application> applicationTransactionList) {
        // Batch ID (14 Digits): <6 character DDMMYY>+<3 character Bank code as per
        // NCIP>+< 5 digit
        // running number>
        // If Today’s date is 29 Aug 2022 the Batch id can be 29082202300001
        String cbsResponceString = "";
        List<ApplicationPayload> applicationsList = new ArrayList<>();
        BatchData batchData = new BatchData();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String formattedDate = dateFormat.format(currentDate);

        String batchId = formattedDate + applicationTransactionList.get(0).getIssFileParser().getBankCode() + generateRandomNumber();

        // this code added for just maintaining batch id after poc done remove
        // ----------
        DesignationMaster designationMaster = new DesignationMaster();
        designationMaster.setDesignationCode(batchId);
        designationMasterRepository.save(designationMaster);
        // -------------------------------------

        batchData.setBatchId(batchId);
        batchData.setFinancialYear(applicationTransactionList.get(0).getIssFileParser().getFinancialYear());
        List<Application> applicationTransactionListSave = new ArrayList<>();
        for (Application applicationTransaction : applicationTransactionList) {
            IssFileParser issFileParser = applicationTransaction.getIssFileParser();

            ApplicationPayload applicationPayload = new ApplicationPayload();
            String uniqueId = batchData.getBatchId() + generateUniqueId(issFileParser.getId());

            applicationPayload.setUniqueId(uniqueId);
            // application.setUniqueId("1207231567261098695");

            applicationPayload.setRecordStatus(applicationProperties.getRecordStatusForFarmerAndLoan());

            // "basicDetails": {
            // "beneficiaryName": "SANGALE LATA BAPURAO",
            // "aadhaarNumber": "731934678958",
            // "beneficiaryPassbookName": "SANGALE LATA BAPURAO",
            // "mobile": "9325498957",
            // "dob": "1987-01-01",
            // "gender": 2,
            // "socialCategory": 4,
            // "farmerCategory": 1,
            // "farmerType": 3,
            // "primaryOccupation": 1,
            // "relativeType": 4,
            // "relativeName": "SANGLE BAPURAO"
            // },

            BasicDetails basicDetails = new BasicDetails();
            basicDetails.setBeneficiaryName(issFileParser.getFarmerName().trim());
            basicDetails.setAadhaarNumber(issFileParser.getAadharNumber());
            basicDetails.setBeneficiaryPassbookName(issFileParser.getFarmerName());
            basicDetails.setMobile(issFileParser.getMobileNo());
            //            basicDetails.setDob("" + issFileParser.getDateofBirth());
            Pattern patternYYYY_MM_DD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            if (patternYYYY_MM_DD.matcher(issFileParser.getDateofBirth()).matches()) {
                basicDetails.setDob(issFileParser.getDateofBirth());
            } else {
                basicDetails.setDob(dateInYYYYMMDD(issFileParser.getDateofBirth()));
            }

            if (Constants.MALE.equalsIgnoreCase(issFileParser.getGender())) {
                basicDetails.setGender(1);
            } else if (Constants.FEMAIL.equalsIgnoreCase(issFileParser.getGender())) {
                basicDetails.setGender(2);
            } else {
                basicDetails.setGender(3);
            }

            Optional<Integer> CastCategoryCode = MasterDataCacheService.CastCategoryMasterList
                .stream()
                .filter(c -> c.getCastCategoryName().toLowerCase().contains(issFileParser.getSocialCategory().toLowerCase()))
                .map(CastCategoryMaster::getCastCategoryCode)
                .findFirst();

            if (CastCategoryCode.isPresent()) {
                basicDetails.setSocialCategory(CastCategoryCode.get());
            } else {
                basicDetails.setSocialCategory(4);
            }

            Optional<Integer> farmerCategoryCode = MasterDataCacheService.FarmerCategoryMasterList
                .stream()
                .filter(f -> f.getFarmerCategory().toLowerCase().contains(issFileParser.getFarmersCategory().toLowerCase()))
                .map(FarmerCategoryMaster::getFarmerCategoryCode)
                .findFirst();

            if (farmerCategoryCode.isPresent()) {
                basicDetails.setFarmerCategory(farmerCategoryCode.get());
            }

            Optional<Integer> farmerTypeCode = MasterDataCacheService.FarmerTypeMasterList
                .stream()
                .filter(f -> f.getFarmerType().toLowerCase().contains(issFileParser.getFarmerType().toLowerCase()))
                .map(FarmerTypeMaster::getFarmerTypeCode)
                .findFirst();

            if (farmerTypeCode.isPresent()) {
                basicDetails.setFarmerType(farmerTypeCode.get());
            }

            Optional<Integer> occupationMasterCode = MasterDataCacheService.OccupationMasterList
                .stream()
                .filter(f -> f.getOccupationName().toLowerCase().contains(issFileParser.getPrimaryOccupation().toLowerCase()))
                .map(OccupationMaster::getOccupationCode)
                .findFirst();

            if (occupationMasterCode.isPresent()) {
                basicDetails.setPrimaryOccupation(occupationMasterCode.get());
            }

            String relativeType = issFileParser.getRelativeType();

            if (
                StringUtils.isNotBlank(issFileParser.getRelativeType()) &&
                issFileParser.getRelativeType().contains("Mother") ||
                relativeType.contains("Father") ||
                relativeType.contains("Wife") ||
                relativeType.contains("Husband") ||
                relativeType.contains("Son") ||
                relativeType.contains("Other")
            ) {
                switch (issFileParser.getRelativeType().toLowerCase()) {
                    case "mother":
                        if (
                            StringUtils.isNotBlank(issFileParser.getGender()) && issFileParser.getGender().equalsIgnoreCase(Constants.MALE)
                        ) {
                            basicDetails.setRelativeType(1);
                        } else if (
                            StringUtils.isNotBlank(issFileParser.getGender()) &&
                            issFileParser.getGender().equalsIgnoreCase(Constants.FEMAIL)
                        ) {
                            basicDetails.setRelativeType(2);
                        }

                        break;
                    case "father":
                        if (
                            StringUtils.isNotBlank(issFileParser.getGender()) && issFileParser.getGender().equalsIgnoreCase(Constants.MALE)
                        ) {
                            basicDetails.setRelativeType(1);
                        } else if (
                            StringUtils.isNotBlank(issFileParser.getGender()) &&
                            issFileParser.getGender().equalsIgnoreCase(Constants.FEMAIL)
                        ) {
                            basicDetails.setRelativeType(2);
                        }
                        break;
                    case "wife":
                        basicDetails.setRelativeType(3);
                        break;
                    case "husband":
                        basicDetails.setRelativeType(4);
                        break;
                    case "son":
                        if (
                            StringUtils.isNotBlank(issFileParser.getGender()) && issFileParser.getGender().equalsIgnoreCase(Constants.MALE)
                        ) {
                            basicDetails.setRelativeType(1);
                        } else if (
                            StringUtils.isNotBlank(issFileParser.getGender()) &&
                            issFileParser.getGender().equalsIgnoreCase(Constants.FEMAIL)
                        ) {
                            basicDetails.setRelativeType(2);
                        }
                        break;
                    case "brother":
                        basicDetails.setRelativeType(3);
                        break;
                    case "other":
                        basicDetails.setRelativeType(3);
                        break;
                    default:
                        basicDetails.setRelativeType(3);
                        break;
                }
            } else {
                Optional<Integer> relativeMasterCode = MasterDataCacheService.RelativeMasterList
                    .stream()
                    .filter(f -> f.getRelativeName().toLowerCase().contains(relativeType))
                    .map(RelativeMaster::getRelativeCode)
                    .findFirst();

                if (relativeMasterCode.isPresent()) {
                    basicDetails.setRelativeType(relativeMasterCode.get());
                } else {
                    basicDetails.setRelativeType(3);
                }
            }

            basicDetails.setRelativeName(issFileParser.getRelativeName().trim());

            applicationPayload.setBasicDetails(basicDetails);

            // "residentialDetails": {
            // "residentialVillage": "557043",
            // "residentialAddress": "A BIRANGUDI PO KALASTAL INDAPUR DIST PUNE",
            // "residentialPincode": "413105"
            // },

            ResidentialDetails residentialDetails = new ResidentialDetails();

            residentialDetails.setResidentialVillage("" + issFileParser.getVillageCode());
            residentialDetails.setResidentialAddress(issFileParser.getAddress());
            residentialDetails.setResidentialPincode("" + issFileParser.getPinCode());

            applicationPayload.setResidentialDetails(residentialDetails);

            // "accountDetails": {
            // "accountNumber": "198001700004317",
            // "ifsc": "HDFC0CPDCCB",
            // "branchCode": "198",
            // "accountHolder": 1,
            // "jointAccountHolders": []
            // },

            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setAccountNumber("" + issFileParser.getAccountNumber());
            accountDetails.setIfsc(issFileParser.getIfsc());
            accountDetails.setBranchCode(issFileParser.getSchemeWiseBranchCode());

            // add account holder code from account honder type

            Optional<Integer> accountHolderMasterCode = MasterDataCacheService.AccountHolderMasterList
                .stream()
                .filter(f -> f.getAccountHolder().toLowerCase().contains(issFileParser.getAccountHolderType().toLowerCase()))
                .map(AccountHolderMaster::getAccountHolderCode)
                .findFirst();

            if (accountHolderMasterCode.isPresent()) {
                accountDetails.setAccountHolder(accountHolderMasterCode.get());
            }

            // fields need to map in xcel
            JointAccountHolders jointAccountHolders = new JointAccountHolders();
            jointAccountHolders.setName(null);
            jointAccountHolders.setAadhaarNumber(null);
            List<JointAccountHolders> JointAccountHoldersList = new ArrayList<JointAccountHolders>();
            accountDetails.setJointAccountHolders(JointAccountHoldersList);

            applicationPayload.setAccountDetails(accountDetails);

            // "loanDetails": {
            // "kccLoanSanctionedDate": "2022-04-26",
            // "kccLimitSanctionedAmount": 60800,
            // "kccDrawingLimitForFY": 60800
            // },

            LoanDetails loanDetails = new LoanDetails();
            //loanDetails.setKccLoanSanctionedDate("" + issFileParser.getLoanSactionDate());

            if (patternYYYY_MM_DD.matcher(issFileParser.getLoanSactionDate()).matches()) {
                loanDetails.setKccLoanSanctionedDate(issFileParser.getLoanSactionDate());
            } else {
                loanDetails.setKccLoanSanctionedDate(dateInYYYYMMDD(issFileParser.getLoanSactionDate()));
            }

            loanDetails.setKccLimitSanctionedAmount(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));
            loanDetails.setKccDrawingLimitForFY(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));
            applicationPayload.setLoanDetails(loanDetails);
            // "activities": [
            // {
            // "activityType": 1,
            // "loanSanctionedDate": "2022-04-26",
            // "loanSanctionedAmount": 60800,
            // "activityRows": [
            // {
            // "landVillage": "557043",
            // "cropCode": "011507800",
            // "surveyNumber": "265/1",
            // "khataNumber": "1247", //satBaraSubsurveyNo
            // "landArea": 0.19, //"areaHect": 0.19,
            // "landType": 1, // "landType": "Irrigated",
            // "season": 3
            // }
            // ]
            // }
            // ]
            // }
            List<Activities> activityList = new ArrayList<>();
            Activities activities = new Activities();


			String kccCropCode = issFileParser.getKccIssCropCode();

			Optional<String> activityTypeBYKccCropCode = MasterDataCacheService.CropMasterList.stream()
					.filter(f -> f.getCropCode().contains(kccCropCode)).map(CropMaster::getCategoryName).findFirst();

			if (activityTypeBYKccCropCode.isPresent()) {

				String activityCode = activityTypeBYKccCropCode.get().toLowerCase().trim();
				if ("horit and veg crops".equalsIgnoreCase(activityCode)
						|| "horti and veg crops".equalsIgnoreCase(activityCode)
						|| "horti & veg crops".equalsIgnoreCase(activityCode)) {
					// added activity type code as on Activity Type
					activities.setActivityType(2l);
				} else if ("agri crop".equalsIgnoreCase(activityCode) || "sugarcane".equalsIgnoreCase(activityCode)
						|| "agri crops".equalsIgnoreCase(activityCode)

				) {
					activities.setActivityType(1l);

				}

			} else {
				activities.setActivityType(1l);
			}



            // activities.setLoanSanctionedDate("" + issFileParser.getLoanSactionDate());
            if (patternYYYY_MM_DD.matcher(issFileParser.getLoanSactionDate()).matches()) {
                activities.setLoanSanctionedDate(issFileParser.getLoanSactionDate());
            } else {
                activities.setLoanSanctionedDate(dateInYYYYMMDD(issFileParser.getLoanSactionDate()));
            }

            activities.setLoanSanctionedAmount(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));

            List<ActivityRows> activityRowsList = new ArrayList<>();
            //setting activity row as per activity type
            if(activities.getActivityType().equals(1l))
            {
            	// adding activities row
                ActivityRows activityRows = new ActivityRows();
                activityRows.setLandVillage("" + issFileParser.getVillageCode());

                // add crop code from crop name
//                Optional<String> cropNameMasterCode = MasterDataCacheService.CropMasterList
//                    .stream()
//                    .filter(f -> f.getCropName().toLowerCase().contains(issFileParser.getCropName().toLowerCase()))
//                    .map(CropMaster::getCropCode)
//                    .findFirst();
//                if (cropNameMasterCode.isPresent()) {
//                    activityRows.setCropCode(cropNameMasterCode.get());
//                }

                activityRows.setCropCode(issFileParser.getKccIssCropCode());

                activityRows.setSurveyNumber(issFileParser.getSurveyNo());
                String satBaraSubsurveyNo = issFileParser.getSatBaraSubsurveyNo();
                String satBaraSubsurveyNo30Char = satBaraSubsurveyNo.substring(0, Math.min(satBaraSubsurveyNo.length(), 30));

                activityRows.setKhataNumber(satBaraSubsurveyNo30Char);
                activityRows.setLandArea(Float.parseFloat(issFileParser.getAreaHect()));

                // add land type code and season code from land type and season name
                Optional<Integer> landTypeMasterCode = MasterDataCacheService.LandTypeMasterList
                    .stream()
                    .filter(f -> f.getLandType().toLowerCase().contains(issFileParser.getLandType().toLowerCase()))
                    .map(LandTypeMaster::getLandTypeCode)
                    .findFirst();

                if (landTypeMasterCode.isPresent()) {
                    activityRows.setLandType(landTypeMasterCode.get());
                }

                Optional<Integer> seasonMasterCode = MasterDataCacheService.SeasonMasterList
                    .stream()
                    .filter(f -> f.getSeasonName().toLowerCase().contains(issFileParser.getSeasonName().toLowerCase()))
                    .map(SeasonMaster::getSeasonCode)
                    .findFirst();

                if (seasonMasterCode.isPresent()) {
                    activityRows.setSeason(seasonMasterCode.get());
                } else {
                    activityRows.setSeason(3);
                }

                activityRowsList.add(activityRows);
                activities.setActivityRows(activityRowsList);

                activityList.add(activities);


            }
            else if(activities.getActivityType().equals(2l))
            {

            	// adding activities row
                ActivityRows activityRows = new ActivityRows();
                activityRows.setLandVillage("" + issFileParser.getVillageCode());

                // add crop code from crop name
//                Optional<String> cropNameMasterCode = MasterDataCacheService.CropMasterList
//                    .stream()
//                    .filter(f -> f.getCropName().toLowerCase().contains(issFileParser.getCropName().toLowerCase()))
//                    .map(CropMaster::getCropCode)
//                    .findFirst();
//
//                if (cropNameMasterCode.isPresent()) {
//                    activityRows.setPlantationCode(cropNameMasterCode.get());
//                }

                activityRows.setPlantationCode(issFileParser.getKccIssCropCode());

                activityRows.setSurveyNumber(issFileParser.getSurveyNo());
                String satBaraSubsurveyNo = issFileParser.getSatBaraSubsurveyNo();
                String satBaraSubsurveyNo30Char = satBaraSubsurveyNo.substring(0, Math.min(satBaraSubsurveyNo.length(), 30));
                activityRows.setKhataNumber(satBaraSubsurveyNo30Char);
                activityRows.setPlantationArea(Float.parseFloat(issFileParser.getAreaHect()));

                activityRowsList.add(activityRows);
                activities.setActivityRows(activityRowsList);

                activityList.add(activities);

            }



            applicationPayload.setActivities(activityList);

            applicationsList.add(applicationPayload);

            applicationTransaction.setBatchId(batchId);
            applicationTransaction.setUniqueId(uniqueId);
            applicationTransaction.setPacksCode(Long.parseLong(applicationTransaction.getIssFileParser().getPacsNumber()));
            applicationTransaction.setSchemeWiseBranchCode(Long.parseLong(applicationTransaction.getIssFileParser().getSchemeWiseBranchCode()));
            applicationTransaction.setBankCode(Long.parseLong(applicationTransaction.getIssFileParser().getBankCode()));
            applicationTransactionListSave.add(applicationTransaction);
        }

        batchData.setApplications(applicationsList);

        String encryption = encryptObject(batchData);

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
                        batchTransactionRepository.save(batchTransaction);

                        try
                        {
                        	IssPortalFile issPortalFile = applicationTransactionListSave.get(0).getIssFileParser().getIssPortalFile();
                        	issPortalFile.setAppSubmitedToKccCount(issPortalFile.getAppSubmitedToKccCount()+(long)applicationTransactionListSave.size());
                        	issPortalFileRepository.save(issPortalFile);

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
                        batchTransactionRepository.save(batchTransaction);
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

    public String generateUniqueId(Long id) {
        String toReturn = id.toString();
        String uniqueId = "";
        if (toReturn.length() > 5) {
            uniqueId = toReturn.substring(toReturn.length() - 5);
        } else{
          for (int i=0; i < (5-toReturn.length()); i++){
              uniqueId += "0";
          }
            uniqueId += toReturn;
        }
        return uniqueId;
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

    ApplicationLog generateApplicationLog(String ErrorMsg, String expectedSolution, String sevierity) {
        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setErrorMessage(ErrorMsg);
        applicationLog.setSevierity(sevierity);
        applicationLog.setExpectedSolution(expectedSolution);
        applicationLog.setErrorType("Validation Error");
        applicationLog.setStatus("ERROR");
        return applicationLog;
    }

    // -----------------------------------------------testing
    // api--------------------------------------------

    @PostMapping("/submit-batch-test")
    @PreAuthorize("@authentication.hasPermision('',#issPortalFile.id,'','SUBMIT_BATCH','SUBMIT')")
    public List<CBSMiddleareInputPayload> submitBatchTest(@RequestBody IssPortalFile issPortalFile) {
        List<CBSMiddleareInputPayload> cbsResponceStringList = new ArrayList<>();

        List<Application> applicationList = applicationRepository.findAllByBatchIdAndApplicationStatusAndIssFilePortalId(
            null,
            Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD,
            issPortalFile.getId()
        );

        int batchSize = 999;

        for (int i = 0; i < applicationList.size(); i += batchSize) {
            List<Application> batch = applicationList.subList(i, Math.min(i + batchSize, applicationList.size()));
            CBSMiddleareInputPayload cbsResponce = processBatchTest(batch);
            cbsResponceStringList.add(cbsResponce);
        }

        return cbsResponceStringList;
    }

    private CBSMiddleareInputPayload processBatchTest(List<Application> applicationTransactionList) {
        // Batch ID (14 Digits): <6 character DDMMYY>+<3 character Bank code as per
        // NCIP>+< 5 digit
        // running number>
        // If Today’s date is 29 Aug 2022 the Batch id can be 29082202300001
        List<ApplicationPayload> applicationsList = new ArrayList<>();
        BatchData batchData = new BatchData();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String formattedDate = dateFormat.format(currentDate);

        String batchId = formattedDate + applicationTransactionList.get(0).getIssFileParser().getBankCode() + generateRandomNumber();

        // this code added for just maintaining batch id after poc done remove
        // ----------
        DesignationMaster designationMaster = new DesignationMaster();
        designationMaster.setDesignationCode(batchId);
        designationMasterRepository.save(designationMaster);
        // -------------------------------------

        batchData.setBatchId(batchId);
        batchData.setFinancialYear(applicationTransactionList.get(0).getIssFileParser().getFinancialYear());
        List<Application> applicationTransactionListSave = new ArrayList<>();
        for (Application applicationTransaction : applicationTransactionList) {
            IssFileParser issFileParser = applicationTransaction.getIssFileParser();

            ApplicationPayload applicationPayload = new ApplicationPayload();
            String uniqueId = batchData.getBatchId() + generateRandomNumber();

            applicationPayload.setUniqueId(uniqueId);
            // application.setUniqueId("1207231567261098695");

            applicationPayload.setRecordStatus(applicationProperties.getRecordStatusForFarmerAndLoan());

            // "basicDetails": {
            // "beneficiaryName": "SANGALE LATA BAPURAO",
            // "aadhaarNumber": "731934678958",
            // "beneficiaryPassbookName": "SANGALE LATA BAPURAO",
            // "mobile": "9325498957",
            // "dob": "1987-01-01",
            // "gender": 2,
            // "socialCategory": 4,
            // "farmerCategory": 1,
            // "farmerType": 3,
            // "primaryOccupation": 1,
            // "relativeType": 4,
            // "relativeName": "SANGLE BAPURAO"
            // },

            BasicDetails basicDetails = new BasicDetails();
            basicDetails.setBeneficiaryName(issFileParser.getFarmerName().trim());
            basicDetails.setAadhaarNumber(issFileParser.getAadharNumber());
            basicDetails.setBeneficiaryPassbookName(issFileParser.getFarmerName());
            basicDetails.setMobile(issFileParser.getMobileNo());

            Pattern patternYYYY_MM_DD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            if (patternYYYY_MM_DD.matcher(issFileParser.getDateofBirth()).matches()) {
                basicDetails.setDob(issFileParser.getDateofBirth());
            } else {
                basicDetails.setDob(dateInYYYYMMDD(issFileParser.getDateofBirth()));
            }

            if (Constants.MALE.equalsIgnoreCase(issFileParser.getGender())) {
                basicDetails.setGender(1);
            } else if (Constants.FEMAIL.equalsIgnoreCase(issFileParser.getGender())) {
                basicDetails.setGender(2);
            } else {
                basicDetails.setGender(3);
            }

            Optional<Integer> CastCategoryCode = MasterDataCacheService.CastCategoryMasterList
                .stream()
                .filter(c -> c.getCastCategoryName().toLowerCase().contains(issFileParser.getSocialCategory().toLowerCase()))
                .map(CastCategoryMaster::getCastCategoryCode)
                .findFirst();

            if (CastCategoryCode.isPresent()) {
                basicDetails.setSocialCategory(CastCategoryCode.get());
            }

            Optional<Integer> farmerCategoryCode = MasterDataCacheService.FarmerCategoryMasterList
                .stream()
                .filter(f -> f.getFarmerCategory().toLowerCase().contains(issFileParser.getFarmersCategory().toLowerCase()))
                .map(FarmerCategoryMaster::getFarmerCategoryCode)
                .findFirst();

            if (farmerCategoryCode.isPresent()) {
                basicDetails.setFarmerCategory(farmerCategoryCode.get());
            }

            Optional<Integer> farmerTypeCode = MasterDataCacheService.FarmerTypeMasterList
                .stream()
                .filter(f -> f.getFarmerType().toLowerCase().contains(issFileParser.getFarmerType().toLowerCase()))
                .map(FarmerTypeMaster::getFarmerTypeCode)
                .findFirst();

            if (farmerTypeCode.isPresent()) {
                basicDetails.setFarmerType(farmerTypeCode.get());
            }

            Optional<Integer> occupationMasterCode = MasterDataCacheService.OccupationMasterList
                .stream()
                .filter(f -> f.getOccupationName().toLowerCase().contains(issFileParser.getPrimaryOccupation().toLowerCase()))
                .map(OccupationMaster::getOccupationCode)
                .findFirst();

            if (occupationMasterCode.isPresent()) {
                basicDetails.setPrimaryOccupation(occupationMasterCode.get());
            }

            String relativeType = issFileParser.getRelativeType();

            if (
                StringUtils.isNotBlank(issFileParser.getRelativeType()) &&
                issFileParser.getRelativeType().contains("Mother") ||
                relativeType.contains("Father") ||
                relativeType.contains("Wife") ||
                relativeType.contains("Husband") ||
                relativeType.contains("Son") ||
                relativeType.contains("Other")
            ) {
                switch (issFileParser.getRelativeType().toLowerCase()) {
                    case "mother":
                        if (
                            StringUtils.isNotBlank(issFileParser.getGender()) && issFileParser.getGender().equalsIgnoreCase(Constants.MALE)
                        ) {
                            basicDetails.setRelativeType(1);
                        } else if (
                            StringUtils.isNotBlank(issFileParser.getGender()) &&
                            issFileParser.getGender().equalsIgnoreCase(Constants.FEMAIL)
                        ) {
                            basicDetails.setRelativeType(2);
                        }

                        break;
                    case "father":
                        if (
                            StringUtils.isNotBlank(issFileParser.getGender()) && issFileParser.getGender().equalsIgnoreCase(Constants.MALE)
                        ) {
                            basicDetails.setRelativeType(1);
                        } else if (
                            StringUtils.isNotBlank(issFileParser.getGender()) &&
                            issFileParser.getGender().equalsIgnoreCase(Constants.FEMAIL)
                        ) {
                            basicDetails.setRelativeType(2);
                        }
                        break;
                    case "wife":
                        basicDetails.setRelativeType(3);
                        break;
                    case "husband":
                        basicDetails.setRelativeType(4);
                        break;
                    case "son":
                        if (
                            StringUtils.isNotBlank(issFileParser.getGender()) && issFileParser.getGender().equalsIgnoreCase(Constants.MALE)
                        ) {
                            basicDetails.setRelativeType(1);
                        } else if (
                            StringUtils.isNotBlank(issFileParser.getGender()) &&
                            issFileParser.getGender().equalsIgnoreCase(Constants.FEMAIL)
                        ) {
                            basicDetails.setRelativeType(2);
                        }
                        break;
                    case "brother":
                        basicDetails.setRelativeType(3);
                        break;
                    case "other":
                        basicDetails.setRelativeType(3);
                        break;
                    default:
                        basicDetails.setRelativeType(3);
                        break;
                }
            } else {
                Optional<Integer> relativeMasterCode = MasterDataCacheService.RelativeMasterList
                    .stream()
                    .filter(f -> f.getRelativeName().toLowerCase().contains(relativeType))
                    .map(RelativeMaster::getRelativeCode)
                    .findFirst();

                if (relativeMasterCode.isPresent()) {
                    basicDetails.setRelativeType(relativeMasterCode.get());
                } else {
                    basicDetails.setRelativeType(3);
                }
            }

            basicDetails.setRelativeName(issFileParser.getRelativeName().trim());

            applicationPayload.setBasicDetails(basicDetails);

            // "residentialDetails": {
            // "residentialVillage": "557043",
            // "residentialAddress": "A BIRANGUDI PO KALASTAL INDAPUR DIST PUNE",
            // "residentialPincode": "413105"
            // },

            ResidentialDetails residentialDetails = new ResidentialDetails();

            residentialDetails.setResidentialVillage("" + issFileParser.getVillageCode());
            residentialDetails.setResidentialAddress(issFileParser.getAddress());
            residentialDetails.setResidentialPincode("" + issFileParser.getPinCode());

            applicationPayload.setResidentialDetails(residentialDetails);

            // "accountDetails": {
            // "accountNumber": "198001700004317",
            // "ifsc": "HDFC0CPDCCB",
            // "branchCode": "198",
            // "accountHolder": 1,
            // "jointAccountHolders": []
            // },

            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setAccountNumber("" + issFileParser.getAccountNumber());
            accountDetails.setIfsc(issFileParser.getIfsc());
            accountDetails.setBranchCode(issFileParser.getSchemeWiseBranchCode());

            // add account holder code from account honder type

            Optional<Integer> accountHolderMasterCode = MasterDataCacheService.AccountHolderMasterList
                .stream()
                .filter(f -> f.getAccountHolder().toLowerCase().contains(issFileParser.getAccountHolderType().toLowerCase()))
                .map(AccountHolderMaster::getAccountHolderCode)
                .findFirst();

            if (accountHolderMasterCode.isPresent()) {
                accountDetails.setAccountHolder(accountHolderMasterCode.get());
            }

            // fields need to map in xcel
            JointAccountHolders jointAccountHolders = new JointAccountHolders();
            jointAccountHolders.setName(null);
            jointAccountHolders.setAadhaarNumber(null);
            List<JointAccountHolders> JointAccountHoldersList = new ArrayList<JointAccountHolders>();
            accountDetails.setJointAccountHolders(JointAccountHoldersList);

            applicationPayload.setAccountDetails(accountDetails);

            // "loanDetails": {
            // "kccLoanSanctionedDate": "2022-04-26",
            // "kccLimitSanctionedAmount": 60800,
            // "kccDrawingLimitForFY": 60800
            // },

            LoanDetails loanDetails = new LoanDetails();

            if (patternYYYY_MM_DD.matcher(issFileParser.getLoanSactionDate()).matches()) {
                loanDetails.setKccLoanSanctionedDate(issFileParser.getLoanSactionDate());
            } else {
                loanDetails.setKccLoanSanctionedDate(dateInYYYYMMDD(issFileParser.getLoanSactionDate()));
            }

            loanDetails.setKccLimitSanctionedAmount(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));
            loanDetails.setKccDrawingLimitForFY(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));
            applicationPayload.setLoanDetails(loanDetails);
            // "activities": [
            // {
            // "activityType": 1,
            // "loanSanctionedDate": "2022-04-26",
            // "loanSanctionedAmount": 60800,
            // "activityRows": [
            // {
            // "landVillage": "557043",
            // "cropCode": "011507800",
            // "surveyNumber": "265/1",
            // "khataNumber": "1247", //satBaraSubsurveyNo
            // "landArea": 0.19, //"areaHect": 0.19,
            // "landType": 1, // "landType": "Irrigated",
            // "season": 3
            // }
            // ]
            // }
            // ]
            // }
            List<Activities> activityList = new ArrayList<>();
            Activities activities = new Activities();


            String kccCropCode=issFileParser.getKccIssCropCode();

            Optional<String> activityTypeBYKccCropCode = MasterDataCacheService.CropMasterList
                    .stream()
                    .filter(f -> f.getCropCode().contains(kccCropCode))
                    .map(CropMaster::getCategoryName)
                    .findFirst();

			if (activityTypeBYKccCropCode.isPresent()) {

				String activityCode=activityTypeBYKccCropCode.get().toLowerCase().trim();
				if ("horit and veg crops".equalsIgnoreCase(activityCode)
						|| "horti and veg crops".equalsIgnoreCase(activityCode)||
						"horti & veg crops".equalsIgnoreCase(activityCode)
						) {
					// added activity type code as on Activity Type
					activities.setActivityType(2l);
				} else if ("agri crop".equalsIgnoreCase(activityCode)
						|| "sugarcane".equalsIgnoreCase(activityCode)
						|| 	"agri crops".equalsIgnoreCase(activityCode)

						) {
					activities.setActivityType(1l);

				}

			} else {
				activities.setActivityType(1l);
			}


            // activities.setLoanSanctionedDate("" + issFileParser.getLoanSactionDate());
            if (patternYYYY_MM_DD.matcher(issFileParser.getLoanSactionDate()).matches()) {
                activities.setLoanSanctionedDate(issFileParser.getLoanSactionDate());
            } else {
                activities.setLoanSanctionedDate(dateInYYYYMMDD(issFileParser.getLoanSactionDate()));
            }

            activities.setLoanSanctionedAmount(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));

            List<ActivityRows> activityRowsList = new ArrayList<>();
            //setting activity row as per activity type
            if(activities.getActivityType().equals(1l))
            {
            	// adding activities row
                ActivityRows activityRows = new ActivityRows();
                activityRows.setLandVillage("" + issFileParser.getVillageCode());

                // add crop code from crop name
				/*
				 * Optional<String> cropNameMasterCode = MasterDataCacheService.CropMasterList
				 * .stream() .filter(f ->
				 * f.getCropName().toLowerCase().contains(issFileParser.getCropName().
				 * toLowerCase())) .map(CropMaster::getCropCode) .findFirst(); if
				 * (cropNameMasterCode.isPresent()) {
				 * activityRows.setCropCode(cropNameMasterCode.get()); }
				 */

                activityRows.setCropCode(issFileParser.getKccIssCropCode());

                activityRows.setSurveyNumber(issFileParser.getSurveyNo());

                String satBaraSubsurveyNo = issFileParser.getSatBaraSubsurveyNo();
                String satBaraSubsurveyNo30Char = satBaraSubsurveyNo.substring(0, Math.min(satBaraSubsurveyNo.length(), 30));
                activityRows.setKhataNumber(satBaraSubsurveyNo30Char);
                activityRows.setLandArea(Float.parseFloat(issFileParser.getAreaHect()));

                // add land type code and season code from land type and season name
                Optional<Integer> landTypeMasterCode = MasterDataCacheService.LandTypeMasterList
                    .stream()
                    .filter(f -> f.getLandType().toLowerCase().contains(issFileParser.getLandType().toLowerCase()))
                    .map(LandTypeMaster::getLandTypeCode)
                    .findFirst();

                if (landTypeMasterCode.isPresent()) {
                    activityRows.setLandType(landTypeMasterCode.get());
                }

                Optional<Integer> seasonMasterCode = MasterDataCacheService.SeasonMasterList
                    .stream()
                    .filter(f -> f.getSeasonName().toLowerCase().contains(issFileParser.getSeasonName().toLowerCase()))
                    .map(SeasonMaster::getSeasonCode)
                    .findFirst();

                if (seasonMasterCode.isPresent()) {
                    activityRows.setSeason(seasonMasterCode.get());
                } else {
                    activityRows.setSeason(3);
                }

                activityRowsList.add(activityRows);
                activities.setActivityRows(activityRowsList);

                activityList.add(activities);


            }
            else if(activities.getActivityType().equals(2l))
            {

            	// adding activities row
                ActivityRows activityRows = new ActivityRows();
                activityRows.setLandVillage("" + issFileParser.getVillageCode());

                // add crop code from crop name
//                Optional<String> cropNameMasterCode = MasterDataCacheService.CropMasterList
//                    .stream()
//                    .filter(f -> f.getCropName().toLowerCase().contains(issFileParser.getCropName().toLowerCase()))
//                    .map(CropMaster::getCropCode)
//                    .findFirst();
//
//                if (cropNameMasterCode.isPresent()) {
//                    activityRows.setPlantationCode(cropNameMasterCode.get());
//                }

                activityRows.setPlantationCode(issFileParser.getKccIssCropCode());

                activityRows.setSurveyNumber(issFileParser.getSurveyNo());
                String satBaraSubsurveyNo = issFileParser.getSatBaraSubsurveyNo();
                String satBaraSubsurveyNo30Char = satBaraSubsurveyNo.substring(0, Math.min(satBaraSubsurveyNo.length(), 30));
                activityRows.setKhataNumber(satBaraSubsurveyNo30Char);
                activityRows.setPlantationArea(Float.parseFloat(issFileParser.getAreaHect()));

                activityRowsList.add(activityRows);
                activities.setActivityRows(activityRowsList);

                activityList.add(activities);

            }


            applicationPayload.setActivities(activityList);

            applicationsList.add(applicationPayload);

            applicationTransaction.setBatchId(batchId);
            applicationTransaction.setUniqueId(uniqueId);
            applicationTransactionListSave.add(applicationTransaction);
        }

        batchData.setApplications(applicationsList);

        // String encryption = encryption(batchData);

        // Making input payload
        CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
        cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
        cbsMiddleareInputPayload.setData(batchData);

        return cbsMiddleareInputPayload;
    }

    public String dateInYYYYMMDD(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Pattern patternYYYYMMDD = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");
        Pattern patternYYYY_MM_DD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        Pattern patternDDMMYYYY = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
        Pattern patternDD_MM_YYYY = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

        if (patternYYYYMMDD.matcher(date).matches()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            date = localDate.format(formatter).trim();
        }

        if (patternYYYY_MM_DD.matcher(date).matches()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            date = localDate.format(formatter).trim();
        }

        if (patternDDMMYYYY.matcher(date).matches()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            date = localDate.format(formatter).trim();
        }

        if (patternDD_MM_YYYY.matcher(date).matches()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            date = localDate.format(formatter).trim();
        }

        return date;
    }
}
