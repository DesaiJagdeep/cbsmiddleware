package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.MasterDataCacheService;
import com.cbs.middleware.domain.AccountDetails;
import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.Activities;
import com.cbs.middleware.domain.ActivityRows;
import com.cbs.middleware.domain.ApplicationPayload;
import com.cbs.middleware.domain.BasicDetails;
import com.cbs.middleware.domain.BatchData;
import com.cbs.middleware.domain.CastCategoryMaster;
import com.cbs.middleware.domain.EncDecObject;
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
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.service.IssFileParserQueryService;
import com.cbs.middleware.service.IssFileParserService;
import com.cbs.middleware.service.ResponceService;
import com.cbs.middleware.service.criteria.IssFileParserCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssFileParser}.
 */
@RestController
@RequestMapping("/api")
public class IssFileParserResource {

    private final Logger log = LoggerFactory.getLogger(IssFileParserResource.class);

    private static final String ENTITY_NAME = "issFileParser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    ApplicationProperties applicationProperties;

    private final IssFileParserService issFileParserService;

    private final IssFileParserRepository issFileParserRepository;

    private final IssFileParserQueryService issFileParserQueryService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    IssPortalFile issPortalFile;

    @Autowired
    IssPortalFileRepository issPortalFileRepository;

    @Autowired
    ResponceService responceService;

    public IssFileParserResource(
        IssFileParserService issFileParserService,
        IssFileParserRepository issFileParserRepository,
        IssFileParserQueryService issFileParserQueryService
    ) {
        this.issFileParserService = issFileParserService;
        this.issFileParserRepository = issFileParserRepository;
        this.issFileParserQueryService = issFileParserQueryService;
    }

    /**
     * Customize code
     * @throws Exception
     */

    @PostMapping("/fileParser")
    public Object excelReader1(@RequestParam("file") MultipartFile files, RedirectAttributes redirectAttributes) {
        if (!"xlsx".equalsIgnoreCase(FilenameUtils.getExtension(files.getOriginalFilename()))) {
            return responceService.failure(400, "Invalid file type");
        }

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String formattedDate = dateFormat.format(currentDate);
        issPortalFile.setFileName(files.getOriginalFilename());
        issPortalFile.setFileExtension(FilenameUtils.getExtension(files.getOriginalFilename()));
        issPortalFile.setFromDisbursementDate(null);
        issPortalFile.setToDisbursementDate(null);
        issPortalFile.setStatus("");
        issPortalFile.setNotes("");

        issPortalFile = issPortalFileRepository.save(issPortalFile);

        int startRowIndex = 6; // Starting row index
        List<IssFileParser> issFileParserList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex < lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                IssFileParser issFileParser = new IssFileParser();
                if (row != null) {
                    issFileParser.setFinancialYear(getCellValue(row.getCell(0)));
                    issFileParser.setBankName(getCellValue(row.getCell(1)));
                    issFileParser.setBankCode(getCellValue(row.getCell(2)));
                    issFileParser.setBranchName(getCellValue(row.getCell(3)));
                    issFileParser.setBranchCode(getCellValue(row.getCell(4)));
                    issFileParser.setSchemeWiseBranchCode(getCellValue(row.getCell(5)));
                    issFileParser.setIfsc(getCellValue(row.getCell(6)));
                    issFileParser.setLoanAccountNumberkcc(getCellValue(row.getCell(7)));
                    issFileParser.setFarmerName(getCellValue(row.getCell(8)));
                    issFileParser.setGender(getCellValue(row.getCell(9)));
                    issFileParser.setAadharNumber(getCellValue(row.getCell(10)));
                    issFileParser.setDateofBirth(getDateCellValue(row.getCell(11)));
                    issFileParser.setAgeAtTimeOfSanction(getCellValue(row.getCell(12)));
                    issFileParser.setMobileNo(getCellValue(row.getCell(13)));
                    issFileParser.setFarmersCategory(getCellValue(row.getCell(14)));
                    issFileParser.setFarmerType(getCellValue(row.getCell(15)));
                    issFileParser.setSocialCategory(getCellValue(row.getCell(16)));
                    issFileParser.setRelativeType(getCellValue(row.getCell(17)));
                    issFileParser.setRelativeName(getCellValue(row.getCell(18)));
                    issFileParser.setStateName(getCellValue(row.getCell(19)));
                    issFileParser.setStateCode(getCellValue(row.getCell(20)));
                    issFileParser.setDistrictName(getCellValue(row.getCell(21)));
                    issFileParser.setDistrictCode(getCellValue(row.getCell(22)));
                    issFileParser.setBlockCode(getCellValue(row.getCell(23)));
                    issFileParser.setBlockName(getCellValue(row.getCell(24)));
                    issFileParser.setVillageCode(getCellValue(row.getCell(25)));
                    issFileParser.setVillageName(getCellValue(row.getCell(26)));
                    issFileParser.setAddress(getCellValue(row.getCell(27)));
                    issFileParser.setPinCode(getCellValue(row.getCell(28)));
                    issFileParser.setAccountType(getCellValue(row.getCell(29)));
                    issFileParser.setAccountNumber(getCellValue(row.getCell(30)));
                    issFileParser.setPacsName(getCellValue(row.getCell(31)));
                    issFileParser.setPacsNumber(getCellValue(row.getCell(32)));
                    issFileParser.setAccountHolderType(getCellValue(row.getCell(33)));
                    issFileParser.setPrimaryOccupation(getCellValue(row.getCell(34)));
                    issFileParser.setLoanSactionDate(getDateCellValue(row.getCell(35)));
                    issFileParser.setLoanSanctionAmount(getCellValue(row.getCell(36)));
                    issFileParser.setTenureOFLoan(getCellValue(row.getCell(37)));
                    issFileParser.setDateOfOverDuePayment(getDateCellValue(row.getCell(38)));
                    issFileParser.setCropName(getCellValue(row.getCell(39)));
                    issFileParser.setSurveyNo(getCellValue(row.getCell(40)));
                    issFileParser.setSatBaraSubsurveyNo(getCellValue(row.getCell(41)));
                    issFileParser.setSeasonName(getCellValue(row.getCell(42)));
                    issFileParser.setAreaHect(getCellValue(row.getCell(43)));
                    issFileParser.setLandType(getCellValue(row.getCell(44)));
                    issFileParser.setDisbursementDate(getDateCellValue(row.getCell(45)));
                    issFileParser.setDisburseAmount(getCellValue(row.getCell(46)));
                    issFileParser.setMaturityLoanDate(getDateCellValue(row.getCell(47)));
                    issFileParser.setRecoveryAmountPrinciple(getCellValue(row.getCell(48)));
                    issFileParser.setRecoveryAmountInterest(getCellValue(row.getCell(49)));
                    issFileParser.setRecoveryDate(getDateCellValue(row.getCell(50)));
                    issFileParser.setIssPortalFile(issPortalFile);
                    issFileParserList.add(issFileParser);
                }
            }

            issPortalFile.setApplicationCount("" + issFileParserList.size());
            issPortalFile.setFinancialYear(issFileParserList.get(0).getFinancialYear());
            issPortalFile.setBranchCode(Math.round(Double.parseDouble(issFileParserList.get(0).getBranchCode())));
            issPortalFile.setPacsCode(Long.parseLong(issFileParserList.get(0).getPacsNumber()));
            issPortalFileRepository.save(issPortalFile);

            issFileParserRepository.saveAll(issFileParserList);

            return responceService.success(200, "File parsed successfully", issFileParserList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responceService.success(200, "File parsed successfully", issFileParserList);
    }

    @PostMapping("/submitBatch")
    public BatchData submitBatch() {
        BatchData batchData = new BatchData();
        List<ApplicationPayload> applicationsList = new ArrayList<>();
        List<IssFileParser> IssFileParserList = issFileParserRepository.findAllByFinancialYear("2022/2023");
        // Batch ID (14 Digits): <6 character DDMMYY>+<3 character Bank code as per
        // NCIP>+< 5 digit
        // running number>
        // If Today’s date is 29 Aug 2022 the Batch id can be 29082202300001

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String formattedDate = dateFormat.format(currentDate);

        String batchId = formattedDate + IssFileParserList.get(0).getBankCode() + generateRandomNumber();
        batchData.setBatchId(batchId);
        batchData.setFinancialYear(IssFileParserList.get(0).getFinancialYear());

        for (IssFileParser issFileParser : IssFileParserList) {
            ApplicationPayload application = new ApplicationPayload();
            application.setUniqueId(batchData.getBatchId() + generateRandomNumber());
            application.setRecordStatus(applicationProperties.getRecordStatusForFarmerAndLoan());

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
            basicDetails.setDob("" + issFileParser.getDateofBirth());

            if ("male".equalsIgnoreCase(issFileParser.getGender())) {
                basicDetails.setGender(1);
            } else if ("female".equalsIgnoreCase(issFileParser.getGender())) {
                basicDetails.setGender(2);
            } else {
                basicDetails.setGender(3);
            }

            Optional<Integer> CastCategoryCode = MasterDataCacheService.CastCategoryMasterList
                .stream()
                .filter(c -> c.getCastCategoryName().equalsIgnoreCase(issFileParser.getSocialCategory()))
                .map(CastCategoryMaster::getCastCategoryCode)
                .findFirst();

            if (CastCategoryCode.isPresent()) {
                basicDetails.setSocialCategory(CastCategoryCode.get());
            }

            Optional<Integer> farmerCategoryCode = MasterDataCacheService.FarmerCategoryMasterList
                .stream()
                .filter(f -> f.getFarmerCategory().equalsIgnoreCase(issFileParser.getFarmersCategory()))
                .map(FarmerCategoryMaster::getFarmerCategoryCode)
                .findFirst();

            if (farmerCategoryCode.isPresent()) {
                basicDetails.setFarmerCategory(farmerCategoryCode.get());
            }

            Optional<Integer> farmerTypeCode = MasterDataCacheService.FarmerTypeMasterList
                .stream()
                .filter(f -> f.getFarmerType().equalsIgnoreCase(issFileParser.getFarmerType()))
                .map(FarmerTypeMaster::getFarmerTypeCode)
                .findFirst();

            if (farmerTypeCode.isPresent()) {
                basicDetails.setFarmerType(farmerTypeCode.get());
            }

            Optional<Integer> occupationMasterCode = MasterDataCacheService.OccupationMasterList
                .stream()
                .filter(f -> f.getOccupationName().equalsIgnoreCase(issFileParser.getPrimaryOccupation()))
                .map(OccupationMaster::getOccupationCode)
                .findFirst();

            if (occupationMasterCode.isPresent()) {
                basicDetails.setPrimaryOccupation(occupationMasterCode.get());
            }

            Optional<Integer> relativeMasterCode = MasterDataCacheService.RelativeMasterList
                .stream()
                .filter(f -> f.getRelativeName().equalsIgnoreCase(issFileParser.getRelativeType()))
                .map(RelativeMaster::getRelativeCode)
                .findFirst();

            if (relativeMasterCode.isPresent()) {
                basicDetails.setRelativeType(relativeMasterCode.get());
            }

            basicDetails.setRelativeName(issFileParser.getRelativeName().trim());

            application.setBasicDetails(basicDetails);

            // "residentialDetails": {
            // "residentialVillage": "557043",
            // "residentialAddress": "A BIRANGUDI PO KALASTAL INDAPUR DIST PUNE",
            // "residentialPincode": "413105"
            // },

            ResidentialDetails residentialDetails = new ResidentialDetails();

            residentialDetails.setResidentialVillage("" + issFileParser.getVillageCode());
            residentialDetails.setResidentialAddress(issFileParser.getAddress());
            residentialDetails.setResidentialPincode("" + issFileParser.getPinCode());

            application.setResidentialDetails(residentialDetails);

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
            accountDetails.setBranchCode(issFileParser.getBranchCode());

            // add account holder code from account honder type

            Optional<Integer> accountHolderMasterCode = MasterDataCacheService.AccountHolderMasterList
                .stream()
                .filter(f -> f.getAccountHolder().equalsIgnoreCase(issFileParser.getAccountHolderType()))
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

            application.setAccountDetails(accountDetails);

            // "loanDetails": {
            // "kccLoanSanctionedDate": "2022-04-26",
            // "kccLimitSanctionedAmount": 60800,
            // "kccDrawingLimitForFY": 60800
            // },

            LoanDetails loanDetails = new LoanDetails();
            loanDetails.setKccLoanSanctionedDate("" + issFileParser.getLoanSactionDate());
            loanDetails.setKccLimitSanctionedAmount(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));
            loanDetails.setKccDrawingLimitForFY(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));
            application.setLoanDetails(loanDetails);
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

            // add incremental number inside for
            activities.setActivityType(null);
            activities.setLoanSanctionedDate("" + issFileParser.getLoanSactionDate());
            activities.setLoanSanctionedAmount(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));

            ActivityRows activityRows = new ActivityRows();
            List<ActivityRows> activityRowsList = new ArrayList<>();
            activityRows.setLandVillage("" + issFileParser.getVillageCode());

            // add crop code from crop name
            activityRows.setCropCode(issFileParser.getCropName());

            activityRows.setSurveyNumber(issFileParser.getSurveyNo());
            activityRows.setKhataNumber(issFileParser.getSatBaraSubsurveyNo());
            activityRows.setLandArea(Float.parseFloat(issFileParser.getAreaHect()));

            // add land type code and season code from land type and season name
            Optional<Integer> landTypeMasterCode = MasterDataCacheService.LandTypeMasterList
                .stream()
                .filter(f -> f.getLandType().equalsIgnoreCase(issFileParser.getLandType()))
                .map(LandTypeMaster::getLandTypeCode)
                .findFirst();

            if (landTypeMasterCode.isPresent()) {
                activityRows.setLandType(landTypeMasterCode.get());
            }

            Optional<Integer> seasonMasterCode = MasterDataCacheService.SeasonMasterList
                .stream()
                .filter(f -> f.getSeasonName().equalsIgnoreCase(issFileParser.getSeasonName()))
                .map(SeasonMaster::getSeasonCode)
                .findFirst();

            if (seasonMasterCode.isPresent()) {
                activityRows.setSeason(seasonMasterCode.get());
            }

            activityRowsList.add(activityRows);
            activities.setActivityRows(activityRowsList);

            activityList.add(activities);

            application.setActivities(activityList);

            applicationsList.add(application);
        }

        batchData.setApplications(applicationsList);

        return batchData;
    }

    @PostMapping("/submitBatchToCBS")
    public ResponseEntity<String> submitBatchToCBS(@RequestBody CBSMiddleareInputPayload cBSMiddleareInputPayload) {
        // fetch from CBS Middleware portal
        String authCode = "68f18c5f-a0e2-4d35-930f-5da9b947f5e1";

        cBSMiddleareInputPayload.setAuthCode(authCode);

        // Set the request URL
        String url = applicationProperties.getCBSMiddlewareBaseURL() + "/submitbatch";

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HttpEntity object with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(cBSMiddleareInputPayload.toString(), headers);

        // Make the HTTP POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return responseEntity;
    }

    @PostMapping("/encryption")
    public String encryption(@RequestBody EncDecObject encDecObject) {
        try {
            SecretKey secretKey = generateSecretKey(applicationProperties.getSecretKey(), applicationProperties.getKeySizeBits());
            IvParameterSpec ivParameterSpec = new IvParameterSpec(applicationProperties.getIv().getBytes(StandardCharsets.UTF_8));

            Cipher cipher = Cipher.getInstance(applicationProperties.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(encDecObject.getEncDecString().getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(encryptedBytes);
        } catch (Exception e) {
            return "error in encryption: " + e;
        }
    }

    @PostMapping("/decryption")
    public String decryption(@RequestBody EncDecObject encDecObject) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(applicationProperties.getIv().getBytes(StandardCharsets.UTF_8));
        final byte[] keyParse = applicationProperties.getSecretKey().getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec key = new SecretKeySpec(keyParse, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] val = new byte[encDecObject.getEncDecString().length() / 2];
            for (int i = 0; i < val.length; i++) {
                int index = i * 2;
                int j = Integer.parseInt(encDecObject.getEncDecString().substring(index, index + 2), 16);
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

    private static String getCellValue(Cell cell) {
        String cellValue = "";

        if (cell == null) {
            cellValue = "";
        } else if (cell.getCellType() == CellType.STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            cellValue = String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.BLANK) {
            cellValue = "";
        }

        return cellValue;
    }

    private static String getDateCellValue(Cell cell) {
        String cellValue = "";

        if (cell == null) {
            cellValue = "";
        } else if (cell.getCellType() == CellType.STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            if ("-".contains(String.valueOf(cell.getNumericCellValue()))) {
                LocalDate date = LocalDate.of(1900, 1, 1).plusDays((long) cell.getNumericCellValue() - 2);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                cellValue = date.format(formatter);
            } else {
                cellValue = String.valueOf(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == CellType.BLANK) {
            cellValue = "";
        }

        return cellValue;
    }

    private int generateRandomNumber() {
        Random random = new Random();
        int min = 10000;
        int max = 99999;

        return random.nextInt(max - min + 1) + min;
    }

    /**
     * {@code POST  /iss-file-parsers} : Create a new issFileParser.
     *
     * @param issFileParser the issFileParser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new issFileParser, or with status {@code 400 (Bad Request)}
     *         if the issFileParser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/iss-file-parsers")
    public ResponseEntity<IssFileParser> createIssFileParser(@RequestBody IssFileParser issFileParser) throws URISyntaxException {
        log.debug("REST request to save IssFileParser : {}", issFileParser);
        if (issFileParser.getId() != null) {
            throw new BadRequestAlertException("A new issFileParser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssFileParser result = issFileParserService.save(issFileParser);
        return ResponseEntity
            .created(new URI("/api/iss-file-parsers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /iss-file-parsers/:id} : Updates an existing issFileParser.
     *
     * @param id            the id of the issFileParser to save.
     * @param issFileParser the issFileParser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated issFileParser, or with status {@code 400 (Bad Request)}
     *         if the issFileParser is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the issFileParser couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/iss-file-parsers/{id}")
    public ResponseEntity<IssFileParser> updateIssFileParser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssFileParser issFileParser
    ) throws URISyntaxException {
        log.debug("REST request to update IssFileParser : {}, {}", id, issFileParser);
        if (issFileParser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issFileParser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issFileParserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IssFileParser result = issFileParserService.update(issFileParser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issFileParser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /iss-file-parsers/:id} : Partial updates given fields of an
     * existing issFileParser, field will ignore if it is null
     *
     * @param id            the id of the issFileParser to save.
     * @param issFileParser the issFileParser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated issFileParser, or with status {@code 400 (Bad Request)}
     *         if the issFileParser is not valid, or with status
     *         {@code 404 (Not Found)} if the issFileParser is not found, or with
     *         status {@code 500 (Internal Server Error)} if the issFileParser
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/iss-file-parsers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IssFileParser> partialUpdateIssFileParser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssFileParser issFileParser
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssFileParser partially : {}, {}", id, issFileParser);
        if (issFileParser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issFileParser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issFileParserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IssFileParser> result = issFileParserService.partialUpdate(issFileParser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issFileParser.getId().toString())
        );
    }

    /**
     * {@code GET  /iss-file-parsers} : get all the issFileParsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of issFileParsers in body.
     */
    @GetMapping("/iss-file-parsers")
    public ResponseEntity<List<IssFileParser>> getAllIssFileParsers(
        IssFileParserCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get IssFileParsers by criteria: {}", criteria);
        Page<IssFileParser> page = issFileParserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /iss-file-parsers/count} : count all the issFileParsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/iss-file-parsers/count")
    public ResponseEntity<Long> countIssFileParsers(IssFileParserCriteria criteria) {
        log.debug("REST request to count IssFileParsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(issFileParserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /iss-file-parsers/:id} : get the "id" issFileParser.
     *
     * @param id the id of the issFileParser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the issFileParser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/iss-file-parsers/{id}")
    public ResponseEntity<IssFileParser> getIssFileParser(@PathVariable Long id) {
        log.debug("REST request to get IssFileParser : {}", id);
        Optional<IssFileParser> issFileParser = issFileParserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issFileParser);
    }

    /**
     * {@code DELETE  /iss-file-parsers/:id} : delete the "id" issFileParser.
     *
     * @param id the id of the issFileParser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/iss-file-parsers/{id}")
    public ResponseEntity<Void> deleteIssFileParser(@PathVariable Long id) {
        log.debug("REST request to delete IssFileParser : {}", id);
        issFileParserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
