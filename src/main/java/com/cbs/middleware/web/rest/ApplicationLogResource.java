package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.config.MasterDataCacheService;
import com.cbs.middleware.domain.*;
import com.cbs.middleware.repository.*;
import com.cbs.middleware.service.ApplicationLogQueryService;
import com.cbs.middleware.service.ApplicationLogService;
import com.cbs.middleware.service.ApplicationService;
import com.cbs.middleware.service.criteria.ApplicationLogCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.BankBranchPacksCodeGet;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST controller for managing
 * {@link com.cbs.middleware.domain.ApplicationLog}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationLogResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationLogResource.class);

    private static final String ENTITY_NAME = "applicationLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationLogService applicationLogService;

    private final ApplicationService applicationService;

    private final ApplicationLogRepository applicationLogRepository;

    private final ApplicationLogQueryService applicationLogQueryService;

    @Autowired
    IssFileParserRepository issFileParserRepository;

    @Autowired
    BatchTransactionRepository batchTransactionRepository;

    @Autowired
    RetryBatchTransactionRepository retryBatchTransactionRepository;

    @Autowired
    BankBranchPacksCodeGet bankBranchPacksCodeGet;


    @Autowired
    IssPortalFileRepository issPortalFileRepository;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private RestTemplate restTemplate;
    private final ApplicationRepository applicationRepository;
    @Autowired
    private RetryBatchTransactionDetailsRepository retryBatchTransactionDetailsRepository;

    public ApplicationLogResource(
        ApplicationLogService applicationLogService,
        ApplicationService applicationService,
        ApplicationLogRepository applicationLogRepository,
        ApplicationRepository applicationRepository,
        ApplicationLogQueryService applicationLogQueryService
    ) {
        this.applicationLogService = applicationLogService;
        this.applicationService=applicationService;
        this.applicationLogRepository = applicationLogRepository;
        this.applicationRepository = applicationRepository;
        this.applicationLogQueryService = applicationLogQueryService;
    }

    /**
     * {@code POST  /application-logs} : Create a new applicationLog.
     *
     * @param applicationLog the applicationLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     * body the new applicationLog, or with status {@code 400 (Bad Request)}
     * if the applicationLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-logs")
    public ResponseEntity<ApplicationLog> createApplicationLog(@RequestBody ApplicationLog applicationLog) throws URISyntaxException {
        log.debug("REST request to save ApplicationLog : {}", applicationLog);
        if (applicationLog.getId() != null) {
            throw new BadRequestAlertException("A new applicationLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationLog result = applicationLogService.save(applicationLog);
        return ResponseEntity
            .created(new URI("/api/application-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-logs/:id} : Updates an existing applicationLog.
     *
     * @param id             the id of the applicationLog to save.
     * @param applicationLog the applicationLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated applicationLog, or with status {@code 400 (Bad Request)}
     * if the applicationLog is not valid, or with status
     * {@code 500 (Internal Server Error)} if the applicationLog couldn't be
     * updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-logs/{id}")
    public ResponseEntity<ApplicationLog> updateApplicationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationLog applicationLog
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationLog : {}, {}", id, applicationLog);
        if (applicationLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationLog result = applicationLogService.update(applicationLog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /application-logs/:id} : Partial updates given fields of an
     * existing applicationLog, field will ignore if it is null
     *
     * @param id             the id of the applicationLog to save.
     * @param applicationLog the applicationLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated applicationLog, or with status {@code 400 (Bad Request)}
     * if the applicationLog is not valid, or with status
     * {@code 404 (Not Found)} if the applicationLog is not found, or with
     * status {@code 500 (Internal Server Error)} if the applicationLog
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/application-logs/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<ApplicationLog> partialUpdateApplicationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationLog applicationLog
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicationLog partially : {}, {}", id, applicationLog);
        if (applicationLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationLog> result = applicationLogService.partialUpdate(applicationLog);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationLog.getId().toString())
        );
    }

    /**
     * {@code GET  /application-logs} : get all the applicationLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of applicationLogs in body.
     */
    @GetMapping("/application-logs")
    public ResponseEntity<List<ApplicationLog>> getAllApplicationLogs(
        ApplicationLogCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApplicationLogs by criteria: {}", criteria);
        Page<ApplicationLog> page = null;
        Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            System.out.println("........................");
            page = applicationLogQueryService.findByCriteria(criteria, pageable);
            //			page = applicationLogQueryService.findByCriteriaCountByPacsCode(
            //					Long.parseLong(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY)), pageable);
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY))) {
            page = applicationLogQueryService.findByCriteria(criteria, pageable);
            //			page = applicationLogQueryService.findByCriteriaCountByBranchCode(
            //					Long.parseLong(branchOrPacksNumber.get(Constants.BRANCH_CODE_KEY)), criteria, pageable);
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            page = applicationLogQueryService.findByCriteria(criteria, pageable);
        } else {
            throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/application-error-records/{IPId}")
    public ResponseEntity<List<IssFileParser>> getAllApplicationLogs(@PathVariable Long IPId) {
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAllByIssPortalIdAndStatus(IPId, "ERROR");

        List<IssFileParser> issFileParserList = new ArrayList<>();
        for (ApplicationLog applicationLog : applicationLogList) {
            issFileParserList.add(applicationLog.getIssFileParser());
        }

        return ResponseEntity.ok().body(issFileParserList);
    }

    /**
     * {@code GET  /application-logs/count} : count all the applicationLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     * in body.
     */
    @GetMapping("/application-logs/count")
    public ResponseEntity<Long> countApplicationLogs(ApplicationLogCriteria criteria) {
        log.debug("REST request to count ApplicationLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-logs/:id} : get the "id" applicationLog.
     *
     * @param id the id of the applicationLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the applicationLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-logs/{id}")
    public ResponseEntity<ApplicationLog> getApplicationLog(@PathVariable Long id) {
        log.debug("REST request to get ApplicationLog : {}", id);
        Optional<ApplicationLog> applicationLog = applicationLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationLog);
    }

    /**
     * {@code DELETE  /application-logs/:id} : delete the "id" applicationLog.
     *
     * @param id the id of the applicationLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-logs/{id}")
    public ResponseEntity<Void> deleteApplicationLog(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationLog : {}", id);
        applicationLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
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
    private int generateRandomNumber() {
        Random random = new Random();
        int min = 10000;
        int max = 99999;

        return random.nextInt(max - min + 1) + min;
    }

    //Ashvini
    @PostMapping("/addloandetails")
    public CBSResponce getApplicationsWithKCCErrorDuplicateAccNo() {

        System.out.println("Get Applications with KCC Error Duplicate Number");
        CBSResponce cbsResponce = null;

        //get distinct iss_portal_id from application_transaction with kcc_status = 0
       List<Long> applicationsPortalIds = applicationService.findRejectedApplicationsWithErrorDuplicateNo();

       System.out.println("Application portalIds size:" + applicationsPortalIds.size());

       //loop through applications
       for(Long applicationsPortalId: applicationsPortalIds) {

           //get application log records with iss_portal_id
           List<ApplicationLog> rejectedApplications = applicationLogRepository.findAllByStatusError(applicationsPortalId);

           System.out.println("rejectedApplications size:" + rejectedApplications.size());

           List<String> numbers = null;
           List<Application> applicationTransactionList = new ArrayList<>();
           List<Application> rejectedApplicationTransactionList = new ArrayList<>();

           //Extract ids from error_message
           for (ApplicationLog appLog : rejectedApplications) {

               //Pattern to extract batch & unique id
               Pattern p = Pattern.compile("-?\\d+");
               Matcher m = p.matcher(appLog.getErrorMessage());
               numbers = new ArrayList<String>();
               while (m.find()) {
                   numbers.add(m.group());

               }

<<<<<<< Updated upstream
               //Get application_transaction record by unique id to get the farmerId
               Application applicationByUniqueId = applicationRepository.findOneByUniqueId(
                   numbers.get(1)
               );
=======
                if (!applicationByUniqueId.isEmpty()) {
>>>>>>> Stashed changes

               System.out.println("Check if application is exists or not in database");

               if (applicationByUniqueId != null) {

                   applicationTransactionList.add(applicationByUniqueId);

                   //get rejected record from application transaction by IssFileParserId
                   Application rejectedApp = applicationRepository.findRejectedApplicatonsByParserId(appLog.getIssFileParser().getId());
                   rejectedApplicationTransactionList.add(rejectedApp);
               }
           }
           List<CBSResponce> cbsResponceStringList = new ArrayList<>();

<<<<<<< Updated upstream
=======
            if (!applicationTransactionList.isEmpty() && !rejectedApplicationTransactionList.isEmpty()) {
                System.out.println("Call add loan details method");
              //  cbsResponce = addloandetails(applicationTransactionList, rejectedApplicationTransactionList);
                cbsResponceStringList.add(cbsResponce);
                System.out.println("applicationTransactionList:" + applicationTransactionList);
                System.out.println("rejectedApplicationTransactionList:" + rejectedApplicationTransactionList);
                System.out.println("cbsResponse list: " + cbsResponceStringList);
>>>>>>> Stashed changes

           if (!applicationTransactionList.isEmpty() && !rejectedApplicationTransactionList.isEmpty()) {
               System.out.println("Call add loan details method");
               cbsResponce = addloandetails(applicationTransactionList, rejectedApplicationTransactionList);
               cbsResponceStringList.add(cbsResponce);
               System.out.println("applicationTransactionList:" + applicationTransactionList);
               System.out.println("rejectedApplicationTransactionList:" + rejectedApplicationTransactionList);
              System.out.println("cbsResponse list: " + cbsResponceStringList);

           }

       }

        return cbsResponce;
    }

    private CBSResponce addloandetails(List<Application> applicationTransactionList,List<Application>rejectedApplicationTransactionList) {

        String cbsResponceString = "";
        List<ApplicationPayload> applicationsList = new ArrayList<>();
        BatchData batchData = new BatchData();
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String formattedDate = dateFormat.format(currentDate);

        String batchId = formattedDate + rejectedApplicationTransactionList.get(0).getIssFileParser().getBankCode() + generateRandomNumber();
        batchData.setBatchId(batchId);
        System.out.println("batchId in add loan details:" + batchId);

        batchData.setFinancialYear(rejectedApplicationTransactionList.get(0).getIssFileParser().getFinancialYear());

        Long IssPortalId = rejectedApplicationTransactionList.get(0).getIssFilePortalId();

        List<Application> applicationTransactionListSave = new ArrayList<>();

        for (int i = 0; i < rejectedApplicationTransactionList.size(); i++) {

             String uniqueId = batchData.getBatchId() + generateRandomNumber();

            Application rejectedApplicationTransaction = rejectedApplicationTransactionList.get(i);
            IssFileParser issFileParser = rejectedApplicationTransaction.getIssFileParser();

            ApplicationPayload applicationPayload = new ApplicationPayload();
            applicationPayload.setUniqueId(uniqueId);
            applicationPayload.setRecordStatus(Constants.LOAN_DETAIL.longValue());

            Pattern patternYYYY_MM_DD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");


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
            if (activities.getActivityType().equals(1l)) {
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
                activityRows.setKhataNumber(issFileParser.getSatBaraSubsurveyNo());
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


            } else if (activities.getActivityType().equals(2l)) {

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
                activityRows.setKhataNumber(issFileParser.getSatBaraSubsurveyNo());
                activityRows.setPlantationArea(Float.parseFloat(issFileParser.getAreaHect()));

                activityRowsList.add(activityRows);
                activities.setActivityRows(activityRowsList);

                activityList.add(activities);

            }
            applicationPayload.setPreuniqueId(applicationTransactionList.get(i).getUniqueId());
            applicationPayload.setFarmerId(applicationTransactionList.get(i).getFarmerId());
            applicationPayload.setActivities(activityList);
            applicationsList.add(applicationPayload);

            rejectedApplicationTransaction.setBatchId(batchId);
            rejectedApplicationTransaction.setUniqueId(uniqueId);
            rejectedApplicationTransaction.setPacksCode(Long.parseLong(rejectedApplicationTransaction.getIssFileParser().getPacsNumber()));
            rejectedApplicationTransaction.setSchemeWiseBranchCode(Long.parseLong(rejectedApplicationTransaction.getIssFileParser().getSchemeWiseBranchCode()));
            rejectedApplicationTransaction.setBankCode(Long.parseLong(rejectedApplicationTransaction.getIssFileParser().getBankCode()));

            applicationTransactionListSave.add(rejectedApplicationTransaction);

        }
        batchData.setApplications(applicationsList);

        System.out.println("batchData: " + batchData);

        String encryption = encryptObject(batchData);

        // Making input payload
        CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
        cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
        cbsMiddleareInputPayload.setData(encryption);

        CBSResponce cbsResponce = null;

        // call fasalrin submit api
        try {
            // Set the request URL
            String url = applicationProperties.getCBSMiddlewareBaseURL() + Constants.addloandetails;
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
                    Date triggeredDate = new Date();
                    if (cbsResponce.isStatus()) {

                        applicationRepository.saveAll(applicationTransactionListSave);
                        String decryption = decryption("" + cbsResponce.getData());
                        objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        submitApiRespDecryption = objectMapper.readValue(decryption, SubmitApiRespDecryption.class);

                        RetryBatchTransaction retryBatchTransaction = new RetryBatchTransaction();
                        retryBatchTransaction.setBatchId(batchId);
                        retryBatchTransaction.setStatus("Submitted");
                        retryBatchTransaction.setBatchAckId(submitApiRespDecryption.getBatchAckId());
                        retryBatchTransaction.setTriggeredDate(triggeredDate);
                        retryBatchTransaction.setIssPortalId(IssPortalId);
                        retryBatchTransactionRepository.save(retryBatchTransaction);

                        saveRetryBatchTransactionDetails(applicationTransactionListSave, retryBatchTransaction);

                        try {
                            IssPortalFile issPortalFile = applicationTransactionListSave.get(0).getIssFileParser().getIssPortalFile();
                            issPortalFile.setAppSubmitedToKccCount(issPortalFile.getAppSubmitedToKccCount() + (long) applicationTransactionListSave.size());
                            issPortalFileRepository.save(issPortalFile);

                        } catch (Exception e) {

                        }


                    } else {
                        System.out.println("Batch is not processed yet");

                        // RetryBatchTransaction retryBatchTransaction = new RetryBatchTransaction();
                        // retryBatchTransaction.setBatchId(batchId);
                        // retryBatchTransaction.setStatus("Discarded");
                        // retryBatchTransaction.setTriggeredDate(triggeredDate);
                        //  retryBatchTransaction.setBatchErrors(cbsResponce.getError());
                        //  String fileName = "";
                        // try {
                        //fileName = applicationTransactionListSave.get(0).getIssFileParser().getIssPortalFile().getFileName();
                        //} catch (Exception e) {
                        // TODO: handle exception
                        // }


                        //  retryBatchTransactionRepository.save(retryBatchTransaction);
                    }
                } catch (Exception e) {
                    log.error("Error in conversion of object duplicate account number: ", e);
                }
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                cbsResponce = objectMapper.readValue(cbsResponceString, CBSResponce.class);
                cbsResponce.setBatchId(batchId);
            }
        } catch (Exception e) {
            log.error("Error in sending data to fasalrin duplicate account number: " + cbsMiddleareInputPayload);
        }

        return cbsResponce;


    }
    public void saveRetryBatchTransactionDetails(List<Application> applicationTransactionList, RetryBatchTransaction retryBatchTransaction) {

        System.out.println("application transaction list:"+applicationTransactionList);
        System.out.println("Retry batch transaction:"+retryBatchTransaction);
        for (Application applicationTransaction : applicationTransactionList) {

            RetryBatchTransactionDetails retryBatchTransactionDetails = new RetryBatchTransactionDetails();
            retryBatchTransactionDetails.setUniqueId(applicationTransaction.getUniqueId());
            retryBatchTransactionDetails.setISSFileParserId(applicationTransaction.getIssFileParser().getId());
            retryBatchTransactionDetails.setRetryBatchTransaction(retryBatchTransaction);
            retryBatchTransactionDetailsRepository.save(retryBatchTransactionDetails);

        }
    }

}
