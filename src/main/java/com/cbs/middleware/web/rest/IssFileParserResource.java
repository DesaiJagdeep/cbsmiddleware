package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.MasterDataCacheService;
import com.cbs.middleware.domain.AccountDetails;
import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.Activities;
import com.cbs.middleware.domain.ActivityRows;
import com.cbs.middleware.domain.ApplicationLog;
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
import com.cbs.middleware.domain.SubmitBatchObj;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.repository.CropMasterRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.service.IssFileParserQueryService;
import com.cbs.middleware.service.IssFileParserService;
import com.cbs.middleware.service.ResponceService;
import com.cbs.middleware.service.criteria.IssFileParserCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
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
    IssPortalFileRepository issPortalFileRepository;

    @Autowired
    ResponceService responceService;

    @Autowired
    ApplicationLog applicationLog;

    @Autowired
    ApplicationLogRepository applicationLogRepository;

    @Autowired
    CropMasterRepository cropMasterRepository;

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
     *
     * @throws Exception
     */

    @PostMapping("/fileParser")
    public Object excelReader1(@RequestParam("file") MultipartFile files, RedirectAttributes redirectAttributes) {
        if (!"xlsx".equalsIgnoreCase(FilenameUtils.getExtension(files.getOriginalFilename()))) {
            return responceService.failure(400, "Invalid file type");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(5); // Get the current row

            if (
                getCellValue(row.getCell(0)) != null &&
                !getCellValue(row.getCell(0)).contains("Financial") &&
                !getCellValue(row.getCell(0)).contains("Year")
            ) {
                return responceService.failure(400, "Invalid file type");
            }
        } catch (Exception e1) {}

        IssPortalFile issPortalFile = new IssPortalFile();
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

                    if (
                        getCellValue(row.getCell(4)) != null &&
                        getCellValue(row.getCell(4)).contains(".") &&
                        getCellValue(row.getCell(4)).matches("^[0-9.]+$")
                    ) {
                        issFileParser.setBranchCode("" + Math.round(Double.parseDouble(getCellValue(row.getCell(4)))));
                    } else {
                        issFileParser.setBranchCode(getCellValue(row.getCell(4)));
                    }

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

                    if (
                        getCellValue(row.getCell(25)) != null &&
                        getCellValue(row.getCell(25)).contains(".") &&
                        getCellValue(row.getCell(25)).matches("^[0-9.]+$")
                    ) {
                        issFileParser.setVillageCode("" + Math.round(Double.parseDouble(getCellValue(row.getCell(25)))));
                    } else {
                        issFileParser.setVillageCode(getCellValue(row.getCell(25)));
                    }

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

    @PostMapping("/validateFile")
    public Set<ApplicationLog> validateFile(@RequestBody IssPortalFile issPortalFile) {
        List<IssFileParser> findAllByIssPortalFile = issFileParserRepository.findAllByIssPortalFile(issPortalFile);
        Set<ApplicationLog> applicationLogList = new HashSet<>();

        Set<IssFileParser> issFileParserSet = new HashSet<>();

        // Filter invalid Financial Year
        List<IssFileParser> invalidFinancialYearList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateFinancialYear(person.getFinancialYear()))
            .collect(Collectors.toList());
        for (IssFileParser issFileParser : invalidFinancialYearList) {
            issFileParserSet.add(issFileParser);
            applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Financial Year is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid Aadhaar numbers
        List<IssFileParser> invalidAadhaarList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateAadhaarNumber(person.getAadharNumber()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidAadhaarList) {
            issFileParserSet.add(issFileParser);
            applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("adhar number is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid Mobile number
        List<IssFileParser> invalidMobileNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateMobileNumber(person.getMobileNo()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidMobileNumberList) {
            issFileParserSet.add(issFileParser);
            applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Mobile number is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid Sat Bara number
        List<IssFileParser> invalidSatBaraNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateSatBaraNumber(person.getSatBaraSubsurveyNo()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidSatBaraNumberList) {
            issFileParserSet.add(issFileParser);
            applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Sat Bara number is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid Loan Sanction Amount
        List<IssFileParser> invalidLoanSanctionAmountList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateAmount(person.getLoanSanctionAmount()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidLoanSanctionAmountList) {
            issFileParserSet.add(issFileParser);
            applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Loan Sanction Amount is incorrect format");
            applicationLogList.add(applicationLog);
        }

        Set<ApplicationLog> applicationLogList1 = new HashSet<>();
        for (IssFileParser issFileParser : issFileParserSet) {
            StringBuilder str = new StringBuilder();

            List<ApplicationLog> collect = applicationLogList
                .stream()
                .filter(a -> a.getIssFileParser().equals(issFileParser))
                .collect(Collectors.toList());
            int index = 0;
            for (ApplicationLog applicationLog : collect) {
                str = str.append(applicationLog.getErrorMessage());
                index = index + 1;
                if (collect.size() != index) {
                    str = str.append(", ");
                }
            }

            applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("" + str);
            applicationLog.setSevierity("HIGH");
            applicationLog.setExpectedSolution("Provide correct Loan Sanction Amount");
            applicationLog.setStatus("ERROR");

            applicationLogList1.add(applicationLog);
        }

        if (!applicationLogList1.isEmpty()) {
            applicationLogRepository.saveAll(applicationLogList1);
        }

        return applicationLogList1;
    }

    @PostMapping("/submitBatch")
    public List<CBSMiddleareInputPayload> submitBatch(@RequestBody SubmitBatchObj submitBatchObj) {
        List<CBSMiddleareInputPayload> cbsMiddleareInputPayloadList = new ArrayList<>();
        List<IssFileParser> issFileParserList = issFileParserRepository.findAllByFinancialYear(submitBatchObj.getFinancialYear());

        int batchSize = 1000;

        for (int i = 0; i < issFileParserList.size(); i += batchSize) {
            List<IssFileParser> batch = issFileParserList.subList(i, Math.min(i + batchSize, issFileParserList.size()));
            CBSMiddleareInputPayload processBatch = processBatch(batch);
            cbsMiddleareInputPayloadList.add(processBatch);
        }

        List<String> list = new ArrayList<>();

        for (CBSMiddleareInputPayload cbsMiddleareInputPayload : cbsMiddleareInputPayloadList) {
            /*
             * // Set the request URL String url =
             * applicationProperties.getCBSMiddlewareBaseURL() + "/submitbatcdfdfsh";
             * System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
             * url); // Set the request headers HttpHeaders headers = new HttpHeaders();
             * headers.setContentType(MediaType.APPLICATION_JSON);
             *
             * JSONObject jsonObject = new JSONObject(cbsMiddleareInputPayload); // Create
             * the HttpEntity object with headers and body HttpEntity<String> requestEntity
             * = new HttpEntity<>(jsonObject.toString(), headers);
             *
             * // Make the HTTP POST request ResponseEntity<String> responseEntity =
             * restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
             *
             * String responseBody = responseEntity.getBody();
             *
             * // Process the response data as needed list.add(responseBody);
             *
             * System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
             * responseBody);
             */
        }
        return cbsMiddleareInputPayloadList;
    }

    private CBSMiddleareInputPayload processBatch(List<IssFileParser> issFileParserList) {
        // Batch ID (14 Digits): <6 character DDMMYY>+<3 character Bank code as per
        // NCIP>+< 5 digit
        // running number>
        // If Today’s date is 29 Aug 2022 the Batch id can be 29082202300001
        List<ApplicationPayload> applicationsList = new ArrayList<>();
        BatchData batchData = new BatchData();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String formattedDate = dateFormat.format(currentDate);

        // String batchId = formattedDate + issFileParserList.get(0).getBankCode() +
        // generateRandomNumber();
        String batchId = "12072315672610";
        batchData.setBatchId(batchId);
        batchData.setFinancialYear(issFileParserList.get(0).getFinancialYear());

        for (IssFileParser issFileParser : issFileParserList) {
            ApplicationPayload application = new ApplicationPayload();
            // application.setUniqueId(batchData.getBatchId() + generateRandomNumber());
            application.setUniqueId("1207231567261098695");

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

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + batchData);

        String encryption = encryption(batchData);

        CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
        cbsMiddleareInputPayload.setAuthCode("68f18c5f-a0e2-4d35-930f-5da9b947f5e1");
        cbsMiddleareInputPayload.setData(encryption);
        System.out.println("?????????????????????????????????????????????" + cbsMiddleareInputPayload);
        return cbsMiddleareInputPayload;
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

    @PutMapping("/issFileErrorResolve")
    public Object issFileErrorResolve(@RequestBody IssFileParser issFileParser) throws URISyntaxException {
        log.debug("REST request to update IssFileParser : {}", issFileParser);
        if (issFileParser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!issFileParserRepository.existsById(issFileParser.getId())) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Set<ApplicationLog> validateOneFileDataObject = validateOneFileDataObject(issFileParser);

        if (validateOneFileDataObject.isEmpty()) {
            IssFileParser result = issFileParserService.update(issFileParser);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issFileParser.getId().toString()))
                .body(result);
        } else {
            return ResponseEntity.badRequest().body(validateOneFileDataObject);
        }
    }

    public Set<ApplicationLog> validateOneFileDataObject(IssFileParser issFileParser) {
        Set<ApplicationLog> applicationLogList = new HashSet<>();

        // Filter invalid Financial Year
        if (!validateFinancialYear(issFileParser.getFinancialYear())) {
            applicationLogList.add(
                generateApplicationLog(
                    "Financial Year is in incorrect format",
                    "Provide correct Financial Year in format yyyy/yyyy",
                    "HIGH"
                )
            );
        }

        // beneficiaryName
        if (StringUtils.isBlank(issFileParser.getFarmerName())) {
            applicationLogList.add(
                generateApplicationLog("Beneficiary Name is in incorrect format", "Provide correct Beneficiary Name", "HIGH")
            );
        }

        // Filter invalid Mobile number
        if (!validateMobileNumber(issFileParser.getMobileNo())) {
            applicationLogList.add(generateApplicationLog("Mobile number is in incorrect format", "Provide correct Mobile number", "HIGH"));
        }

        // Filter date of birth
        if (!validateDate(issFileParser.getDateofBirth())) {
            applicationLogList.add(
                generateApplicationLog("Date Of Birth is in incorrect format", "Provide date in yyyy-mm-dd format", "HIGH")
            );
        }

        // gender
        if (!validateGender(issFileParser.getGender())) {
            applicationLogList.add(generateApplicationLog("Incorect Gender", "Provide correcr Gender", "HIGH"));
        }

        // socialCategory
        if (!validateSocialCategory(issFileParser.getSocialCategory())) {
            applicationLogList.add(
                generateApplicationLog("socialCategory is in incorrect format", "Provide correct Social Category", "HIGH")
            );
        }

        // farmerCategory
        if (!validateFarmerCategory(issFileParser.getFarmersCategory())) {
            applicationLogList.add(
                generateApplicationLog("Farmer Category is in incorrect format", "Provide correct Farmer Category", "HIGH")
            );
        }

        // farmerType
        if (!validateFarmerType(issFileParser.getFarmerType())) {
            applicationLogList.add(generateApplicationLog("Farmer Type is in incorrect format", "Provide correct Farmer Type", "HIGH"));
        }

        // primaryOccupation
        if (!validatePrimaryOccupation(issFileParser.getPrimaryOccupation())) {
            applicationLogList.add(
                generateApplicationLog("Primary Occupation is in incorrect format", "Provide correct Primary Occupation", "HIGH")
            );
        }

        // relativeType
        if (!validateRelativeType(issFileParser.getRelativeType())) {
            applicationLogList.add(generateApplicationLog("Relative Type is in incorrect format", "Provide correct Relative Type", "HIGH"));
        }

        // relativeName
        if (StringUtils.isBlank(issFileParser.getRelativeName())) {
            applicationLogList.add(generateApplicationLog("Relative Name is in incorrect format", "Provide correct Relative Name", "HIGH"));
        }

        // residentialPincode
        if (!issFileParser.getPinCode().matches("(\\d{6})")) {
            applicationLogList.add(
                generateApplicationLog("Residential Pin Code is in incorrect format", "Provide correct Residential Pin Code", "HIGH")
            );
        }

        // accountNumber
        if (!issFileParser.getAccountNumber().matches("\\d+")) {
            applicationLogList.add(
                generateApplicationLog("Account Number is in incorrect format", "Provide correct Account Number", "HIGH")
            );
        }

        // branchCode
        if (!issFileParser.getBranchCode().matches("\\d+")) {
            applicationLogList.add(generateApplicationLog("Branch Code is in incorrect format", "Provide correct Branch Code", "HIGH"));
        }

        // ifsc
        if (!issFileParser.getIfsc().matches("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$")) {
            applicationLogList.add(generateApplicationLog("IFSC is in incorrect format", "Provide correct IFSC", "HIGH"));
        }

        // accountHolder
        if (!validateAccountHolder(issFileParser.getAccountHolderType())) {
            applicationLogList.add(
                generateApplicationLog("Account Holder is in incorrect format", "Provide correct Account Holder", "HIGH")
            );
        }

        // jointAccountHolders name
        /*
         * if (StringUtils.isBlank(issFileParser.getRelativeName())) {
         * applicationLogList.add(
         * generateApplicationLog("Joint Account Holders is in incorrect format",
         * "Provide correct Joint Account Holders ", "HIGH")); }
         */

        // jointAccountHolders Aadhaar number

        /*
         * if (!issFileParser.getAadharNumber().matches("^[2-9]{1}[0-9]{11}$")) {
         * applicationLogList.add(
         * generateApplicationLog("Aadhhar Number is incorrect format",
         * "Provide correct Aadhhar Number", "HIGH")); }
         */

        // kccLoanSanctionedDate
        if (!validateDate(issFileParser.getLoanSactionDate())) {
            applicationLogList.add(
                generateApplicationLog(
                    "kcc Loan Sanctioned Date is in incorrect format",
                    "Provide correct kcc Loan Sanctioned Date",
                    "HIGH"
                )
            );
        }

        // kccLoanSanctionedAmount
        // kccDrawingLimitforFY
        if (!validateAmount(issFileParser.getLoanSanctionAmount())) {
            applicationLogList.add(
                generateApplicationLog(
                    "kcc Loan Sanctioned Amount is in incorrect format",
                    "Provide correct kcc Loan Sanctioned Amount",
                    "HIGH"
                )
            );
        }

        // activityType

        /*
         * if (!validateRelativeName(issFileParser.activityType())) {
         * applicationLogList.add(
         * generateApplicationLog("Farmer Category is in incorrect format",
         * "Provide Farmer Category", "HIGH")); }
         */

        // loanSanctionedDate
        if (!validateDate(issFileParser.getLoanSactionDate())) {
            applicationLogList.add(generateApplicationLog("Farmer Category is in incorrect format", "Provide Farmer Category", "HIGH"));
        }

        // loanSanctionedAmount

        if (!validateAmount(issFileParser.getLoanSanctionAmount())) {
            applicationLogList.add(
                generateApplicationLog("Loan Sanction Amount is incorrect format", "Provide correct Loan Sanction Amount", "HIGH")
            );
        }

        // landVillage
        if (!issFileParser.getVillageCode().matches("\\d+")) {
            applicationLogList.add(generateApplicationLog("Land Village is in incorrect format", "Provide correct Land Village", "HIGH"));
        }

        // cropCode
        if (!cropMasterRepository.existsByCropName(issFileParser.getCropName())) {
            applicationLogList.add(generateApplicationLog("Crop Code is in incorrect format", "Provide correct Crop Code", "HIGH"));
        }

        // surveyNumber
        if (!issFileParser.getSurveyNo().matches("^[0-9/]+$")) {
            applicationLogList.add(generateApplicationLog("Survey Number is in incorrect format", "Provide correct Survey Number", "HIGH"));
        }

        // khataNumber
        if (!validateSatBaraNumber(issFileParser.getSatBaraSubsurveyNo())) {
            applicationLogList.add(
                generateApplicationLog("Sat Bara number is in incorrect format", "Provide correct Sat Bara Number", "HIGH")
            );
        }

        // landArea
        if (!validateAmount(issFileParser.getAreaHect())) {
            applicationLogList.add(generateApplicationLog("Land Area is in incorrect format", "Provide correct Land Area", "HIGH"));
        }

        // landType
        if (!validateLandType(issFileParser.getLandType())) {
            applicationLogList.add(generateApplicationLog("Land Type is in incorrect format", "Provide correct Land Type", "HIGH"));
        }

        // season
        if (!validateSeasonName(issFileParser.getSeasonName())) {
            applicationLogList.add(generateApplicationLog("Season Name is in incorrect format", "Provide correct Season Name", "HIGH"));
        }

        // plantationCode

        // plantationArea

        // liveStockType

        // liveStockCode

        // unitCount

        // inlandType

        // totalUnits

        // totalArea

        // marineType

        return applicationLogList;
    }

    private boolean validateSeasonName(String seasonName) {
        boolean flag = false;
        if (seasonName == null || "".equalsIgnoreCase(seasonName)) {
            return flag;
        }

        switch (seasonName.toLowerCase()) {
            case "kharif":
                flag = true;
                break;
            case "rabi":
                flag = true;
                break;
            case "summer":
                flag = true;
                break;
            case "zaid":
                flag = true;
                break;
            case "others":
                flag = true;
                break;
            case "summer/zaid/others":
                flag = true;
                break;
            default:
                flag = true;
                break;
        }

        return flag;
    }

    private boolean validateLandType(String landType) {
        boolean flag = false;
        if (landType == null || "".equalsIgnoreCase(landType)) {
            return flag;
        }

        switch (landType.toLowerCase()) {
            case "irrigated":
                flag = true;
                break;
            case "non-irrigated":
                flag = true;
                break;
            default:
                flag = true;
                break;
        }

        return flag;
    }

    private boolean validateAmount(String loanSanctionAmount) {
        boolean flag = false;
        if (loanSanctionAmount == null || "".equalsIgnoreCase(loanSanctionAmount)) {
            return flag;
        }
        Pattern patternLongAmount = Pattern.compile("\\d+\\.\\d+");
        Pattern patternDoubleAmount = Pattern.compile("\\d+");

        if (patternLongAmount.matcher(loanSanctionAmount).matches()) { // yyyy-MM-dd
            flag = true;
        } else if (patternDoubleAmount.matcher(loanSanctionAmount).matches()) { // dd/mm/yyyy
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    private boolean validateAccountHolder(String accountHolderType) {
        boolean flag = false;
        if (accountHolderType == null || "".equalsIgnoreCase(accountHolderType)) {
            return flag;
        }

        switch (accountHolderType.toLowerCase()) {
            case "single":
                flag = true;
                break;
            case "joint":
                flag = true;
                break;
            default:
                flag = true;
                break;
        }

        return flag;
    }

    private boolean validateRelativeType(String relativeType) {
        boolean flag = false;
        if (relativeType == null || "".equalsIgnoreCase(relativeType)) {
            return flag;
        }

        switch (relativeType.toLowerCase()) {
            case "son of":
                flag = true;
                break;
            case "daughter of":
                flag = true;
                break;
            case "care of":
                flag = true;
                break;
            case "wife of":
                flag = true;
                break;
            default:
                flag = true;
                break;
        }

        return flag;
    }

    private boolean validatePrimaryOccupation(String primaryOccupation) {
        boolean flag = false;
        if (primaryOccupation == null || "".equalsIgnoreCase(primaryOccupation)) {
            return flag;
        }

        switch (primaryOccupation.toLowerCase()) {
            case "farmer":
                flag = true;
                break;
            case "fishries":
                flag = true;
                break;
            case "animal husbandary":
                flag = true;
                break;
            default:
                flag = false;
                break;
        }

        return flag;
    }

    private boolean validateFarmerType(String farmerType) {
        boolean flag = false;
        if (farmerType == null || "".equalsIgnoreCase(farmerType)) {
            return flag;
        }

        switch (farmerType.toLowerCase()) {
            case "small":
                flag = true;
                break;
            case "other":
                flag = true;
                break;
            case "marginal":
                flag = true;
                break;
            default:
                flag = false;
                break;
        }

        return flag;
    }

    private boolean validateFarmerCategory(String farmersCategory) {
        boolean flag = false;
        if (farmersCategory == null || "".equalsIgnoreCase(farmersCategory)) {
            return flag;
        }

        switch (farmersCategory.toLowerCase()) {
            case "owner":
                flag = true;
                break;
            case "sharecropper":
                flag = true;
                break;
            case "tenant":
                flag = true;
                break;
            default:
                flag = false;
                break;
        }

        return flag;
    }

    private boolean validateSocialCategory(String socialCategory) {
        boolean flag = false;
        if (socialCategory == null || "".equalsIgnoreCase(socialCategory)) {
            return flag;
        }

        switch (socialCategory.toLowerCase()) {
            case "sc":
                flag = true;
                break;
            case "st":
                flag = true;
                break;
            case "gen":
                flag = true;
                break;
            case "obc":
                flag = true;
                break;
            default:
                flag = false;
                break;
        }

        return flag;
    }

    private boolean validateGender(String gender) {
        boolean flag = false;
        if (gender == null || "".equalsIgnoreCase(gender)) {
            return flag;
        }

        switch (gender.toLowerCase()) {
            case "male":
                flag = true;
                break;
            case "female":
                flag = true;
                break;
            case "other":
                flag = true;
                break;
            default:
                flag = false;
                break;
        }

        return flag;
    }

    private boolean validateDate(String dateofBirth) {
        Pattern patternYYYYMMDD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

        if (patternYYYYMMDD.matcher(dateofBirth).matches()) { // yyyy-MM-dd
            return true;
        }
        return false;
    }

    private boolean validateSatBaraNumber(String satBaraSubsurveyNo) {
        String satBaraSubsurveyNoPattern = "^[0-9]+$";
        return satBaraSubsurveyNo.matches(satBaraSubsurveyNoPattern);
    }

    private boolean validateMobileNumber(String mobileNo) {
        boolean flag = false;
        if ("".equalsIgnoreCase(mobileNo)) {
            return flag;
        }
        String regex = "^(\\+\\d{1,3})?\\d{10}$";
        String regex1 = "^\\d{10}$";
        if (mobileNo.matches(regex)) {
            flag = true;
        } else if (mobileNo.matches(regex1)) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    private boolean validateFinancialYear(String financialYear) {
        String financialYearPattern = "^\\d{4}/\\d{4}$";
        return financialYear.matches(financialYearPattern);
    }

    private boolean validateAadhaarNumber(String aadharNumber) {
        String aadhaarPattern = "^[2-9]{1}[0-9]{11}$";
        return aadharNumber.matches(aadhaarPattern);
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

    // @PostMapping("/encryption")
    public String encryption(BatchData encDecObject) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String cellValue = "";

        if (cell == null) {
            cellValue = "";
        } else if (cell.getCellType() == CellType.STRING) {
            Pattern patternYYYYMMDD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            Pattern patternDDMMYYYY = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

            if (patternYYYYMMDD.matcher(cell.getStringCellValue()).matches()) { // yyyy-MM-dd
                LocalDate date = LocalDate.parse(cell.getStringCellValue());
                cellValue = date.format(formatter);
            } else if (patternDDMMYYYY.matcher(cell.getStringCellValue()).matches()) { // dd/mm/yyyy
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(cell.getStringCellValue(), inputFormatter);
                cellValue = date.format(formatter);
            } else {
                cellValue = cell.getStringCellValue();
            }
        } else if (cell.getCellType() == CellType.NUMERIC) {
            LocalDate date = LocalDate.of(1900, 1, 1).plusDays((long) cell.getNumericCellValue() - 2);
            cellValue = date.format(formatter);
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

    ApplicationLog generateApplicationLog(String ErrorMsg, String expectedSolution, String sevierity) {
        applicationLog = new ApplicationLog();
        applicationLog.setErrorMessage(ErrorMsg);
        applicationLog.setSevierity(sevierity);
        applicationLog.setExpectedSolution(expectedSolution);
        applicationLog.setErrorType("Validation Error");
        applicationLog.setStatus("ERROR");
        return applicationLog;
    }

    @GetMapping("/issPortalFile/{idISP}/issFileParsers")
    public ResponseEntity<List<IssFileParser>> getAllIssFileParsers1(
        @PathVariable Long idISP,
        IssFileParserCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get IssFileParsers by issPortalFile id: {}", idISP);

        if (idISP == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<IssPortalFile> isIssPortalFile = issPortalFileRepository.findById(idISP);
        if (!isIssPortalFile.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Page<IssFileParser> page = issFileParserQueryService.findByIssPortalCriteria(isIssPortalFile.get(), criteria, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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
