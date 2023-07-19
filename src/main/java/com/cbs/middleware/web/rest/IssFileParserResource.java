package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.config.MasterDataCacheService;
import com.cbs.middleware.domain.AccountDetails;
import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.Activities;
import com.cbs.middleware.domain.ActivityRows;
import com.cbs.middleware.domain.ActivityType;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.ApplicationPayload;
import com.cbs.middleware.domain.BasicDetails;
import com.cbs.middleware.domain.BatchData;
import com.cbs.middleware.domain.BatchTransaction;
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
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.repository.LandTypeMasterRepository;
import com.cbs.middleware.repository.OccupationMasterRepository;
import com.cbs.middleware.repository.RelativeMasterRepository;
import com.cbs.middleware.repository.SeasonMasterRepository;
import com.cbs.middleware.service.IssFileParserQueryService;
import com.cbs.middleware.service.IssFileParserService;
import com.cbs.middleware.service.ResponceService;
import com.cbs.middleware.service.criteria.IssFileParserCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    DesignationMasterRepository designationMasterRepository;

    private final IssFileParserService issFileParserService;

    private final IssFileParserRepository issFileParserRepository;

    private final IssFileParserQueryService issFileParserQueryService;

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
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());

        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
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

        File originalFileDir = new File(Constants.ORIGINAL_FILE_PATH);
        if (!originalFileDir.isDirectory()) {
            originalFileDir.mkdirs();
        }

        String filePath = originalFileDir.toString();

        Calendar cal = new GregorianCalendar();
        String uniqueName =
            "" +
            cal.get(Calendar.YEAR) +
            cal.get(Calendar.MONTH) +
            cal.get(Calendar.DAY_OF_MONTH) +
            cal.get(Calendar.HOUR) +
            cal.get(Calendar.MINUTE) +
            cal.get(Calendar.SECOND) +
            cal.get(Calendar.MILLISECOND) +
            cal.get(Calendar.MINUTE) +
            cal.get(Calendar.MILLISECOND) +
            cal.get(Calendar.MONTH) +
            cal.get(Calendar.DAY_OF_MONTH) +
            cal.get(Calendar.HOUR) +
            cal.get(Calendar.SECOND) +
            cal.get(Calendar.MILLISECOND);

        Path path = Paths.get(filePath + File.separator + uniqueName + "." + fileExtension);
        try {
            byte[] imgbyte = null;
            imgbyte = files.getBytes();
            Files.write(path, imgbyte);
        } catch (IOException e) {
            return responceService.failure(400, "file not saved successfully");
        }

        IssPortalFile issPortalFile = new IssPortalFile();
        issPortalFile.setFileName(files.getOriginalFilename());
        issPortalFile.setFileExtension(fileExtension);
        issPortalFile.setFromDisbursementDate(null);
        issPortalFile.setToDisbursementDate(null);
        issPortalFile.setStatus("Uploaded");
        issPortalFile.setNotes("");

        issPortalFile = issPortalFileRepository.save(issPortalFile);

        int startRowIndex = 6; // Starting row index
        List<IssFileParser> issFileParserList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                IssFileParser issFileParser = new IssFileParser();
                if (row != null) {
                    String fYear = getCellValue(row.getCell(0));
                    if (fYear.matches("\\d{4}/\\d{4}")) {
                        issFileParser.setFinancialYear(fYear.replace("/", "-"));
                    } else {
                        issFileParser.setFinancialYear(fYear);
                    }

                    issFileParser.setBankName(getCellValue(row.getCell(1)));

                    String bankCode = getCellValue(row.getCell(2));
                    if (StringUtils.isNotBlank(bankCode) && bankCode.contains(".")) {
                        issFileParser.setBankCode(bankCode.substring(0, bankCode.indexOf(".")));
                    } else {
                        issFileParser.setBankCode(bankCode);
                    }

                    issFileParser.setBranchName(getCellValue(row.getCell(3)));

                    String branchCode = getCellValue(row.getCell(4));
                    if (StringUtils.isNotBlank(branchCode) && branchCode.contains(".")) {
                        issFileParser.setBranchCode(branchCode.substring(0, branchCode.indexOf(".")));
                    } else {
                        issFileParser.setBranchCode(branchCode);
                    }

                    String schemeWiseBranchCode = getCellValue(row.getCell(5));
                    if (StringUtils.isNotBlank(schemeWiseBranchCode) && schemeWiseBranchCode.contains(".")) {
                        issFileParser.setSchemeWiseBranchCode(schemeWiseBranchCode.substring(0, schemeWiseBranchCode.indexOf(".")));
                    } else {
                        issFileParser.setSchemeWiseBranchCode(schemeWiseBranchCode);
                    }

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

                    String villageCode = getCellValue(row.getCell(25));
                    if (StringUtils.isNotBlank(villageCode) && villageCode.contains(".")) {
                        issFileParser.setVillageCode(villageCode.substring(0, villageCode.indexOf(".")));
                    } else {
                        issFileParser.setVillageCode(villageCode);
                    }

                    issFileParser.setVillageName(getCellValue(row.getCell(26)));

                    issFileParser.setAddress(getCellValue(row.getCell(27)));

                    String pinCode = getCellValue(row.getCell(28));
                    if (StringUtils.isNotBlank(pinCode) && pinCode.contains(".")) {
                        issFileParser.setPinCode(pinCode.substring(0, pinCode.indexOf(".")));
                    } else {
                        issFileParser.setPinCode(pinCode);
                    }

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

                    String satBara = getCellValue(row.getCell(41));
                    if (StringUtils.isNotBlank(satBara) && satBara.contains(".")) {
                        issFileParser.setSatBaraSubsurveyNo(satBara.substring(0, satBara.indexOf(".")));
                    } else {
                        issFileParser.setSatBaraSubsurveyNo(satBara);
                    }

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
            return ResponseEntity.ok().body(issFileParserList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responceService.success(200, "File parsed successfully", issFileParserList);
    }

    @PostMapping("/validateFile")
    public ResponseEntity<Set<ApplicationLog>> validateFile(@RequestBody IssPortalFile issPortalFile) {
        if (issPortalFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<IssPortalFile> findById = issPortalFileRepository.findById(issPortalFile.getId());
        if (!findById.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        issPortalFile = findById.get();
        List<IssFileParser> findAllByIssPortalFile = issFileParserRepository.findAllByIssPortalFile(issPortalFile);

        if (findAllByIssPortalFile.isEmpty()) {
            throw new BadRequestAlertException("Iss file data Entity not found", ENTITY_NAME, "idnotfound");
        }

        Set<ApplicationLog> applicationLogList = new HashSet<>();
        Set<ApplicationLog> applicationLogListToSave = new HashSet<>();
        Set<IssFileParser> issFileParserValidationErrorSet = new HashSet<>();

        // Filter invalid Financial Year
        List<IssFileParser> invalidFinancialYearList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateFinancialYear(person.getFinancialYear()))
            .collect(Collectors.toList());
        for (IssFileParser issFileParser : invalidFinancialYearList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
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
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("adhar number is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid beneficiary Name and beneficiary Passbook Name and
        // accountHolder

        List<IssFileParser> invalidBeneficiaryNameList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getFarmerName()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidBeneficiaryNameList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("beneficiary Name is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid Mobile number
        List<IssFileParser> invalidMobileNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateMobileNumber(person.getMobileNo()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidMobileNumberList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Mobile number is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid dob

        List<IssFileParser> invalidDOBList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getDateofBirth()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidDOBList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Date of birth is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid gender

        List<IssFileParser> invalidGenderList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateGender(person.getGender()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidGenderList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Gender is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid socialCategory

        List<IssFileParser> invalidSocialCategoryList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateSocialCategory(person.getSocialCategory()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidSocialCategoryList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Social Category is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid farmerCategory
        List<IssFileParser> invalidFarmerCategoryList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateFarmerCategory(person.getFarmersCategory()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidFarmerCategoryList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Farmer Category is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid farmerType
        List<IssFileParser> invalidFarmerTypeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateFarmerType(person.getFarmerType()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidFarmerTypeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Farmer type is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid primaryOccupation

        List<IssFileParser> invalidPrimaryOccupationList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validatePrimaryOccupation(person.getPrimaryOccupation()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidPrimaryOccupationList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Farmer primary occupation is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid relativeType
        List<IssFileParser> invalidRelativeTypeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateRelativeType(person.getRelativeType()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidRelativeTypeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Relative Type is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid relativeName

        List<IssFileParser> invalidRelativeNameList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getRelativeName()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidRelativeNameList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Relative name is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid residentialPincode
        List<IssFileParser> invalidResidentialPincodeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getPinCode().matches("(\\d{6})"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidResidentialPincodeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Residential pin code is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid accountNumber
        List<IssFileParser> invalidAccountNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getAccountNumber().matches("\\d+"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidAccountNumberList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Account Number is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid Scheme Wise Branch Code
        List<IssFileParser> invalidBranchCodeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getSchemeWiseBranchCode().matches("(\\d{6})"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidBranchCodeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Scheme Wise Branch Code is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid ifsc
        List<IssFileParser> invalidIFSCCodeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getIfsc().matches("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidIFSCCodeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("IFSC Code is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid kccLoanSanctionedDate //loanSanctionedDate
        List<IssFileParser> invalidKccLoanSanctionedDateList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getLoanSactionDate()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidKccLoanSanctionedDateList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Loan Sanctioned Date is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid kccLoanSanctionedAmount // kccDrawingLimitforFY // Loan
        // Sanction Amount
        List<IssFileParser> invalidkccLoanSanctionedAmountList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateAmount(person.getLoanSanctionAmount()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidkccLoanSanctionedAmountList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Loan Sanction Amount is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid landVillage
        List<IssFileParser> invalidLandVillageList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getVillageCode().matches("(\\d{6})"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidLandVillageList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Land Village Code is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid cropCode
        List<IssFileParser> invalidCropCodeList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getCropName()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidCropCodeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Crop Name is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid surveyNumber
        List<IssFileParser> invalidSurveyNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getSurveyNo()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidSurveyNumberList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Survey Number is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid khataNumber or Sat Bara number
        List<IssFileParser> invalidSatBaraNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateSatBaraNumber(person.getSatBaraSubsurveyNo()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidSatBaraNumberList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Sat Bara number is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid landArea
        List<IssFileParser> invalidLandAreaList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateAmount(person.getAreaHect()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidLandAreaList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Land Area Hect is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid landType
        List<IssFileParser> invalidLandTypeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateLandType(person.getLandType()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidLandTypeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Land Type is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // Filter invalid season //activityType
        List<IssFileParser> invalidSeasonList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateSeasonName(person.getSeasonName()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidSeasonList) {
            issFileParserValidationErrorSet.add(issFileParser);
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("Season is incorrect format");
            applicationLogList.add(applicationLog);
        }

        // --------------------------------------------------------------

        for (IssFileParser issFileParser : issFileParserValidationErrorSet) {
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
            ApplicationLog applicationLog = new ApplicationLog();
            Optional<ApplicationLog> applicationLogSaved = applicationLogRepository.findOneByIssFileParser(issFileParser);
            if (applicationLogSaved.isPresent()) {
                applicationLog = applicationLogSaved.get();
            }
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("" + str);
            applicationLog.setSevierity("HIGH");
            applicationLog.setExpectedSolution("Provide correct information");
            applicationLog.setStatus("ERROR");
            applicationLog.setErrorType("Validation Error");
            applicationLog.setErrorRecordCount(collect.size());
            applicationLogListToSave.add(applicationLog);
        }

        if (!applicationLogListToSave.isEmpty()) {
            applicationLogRepository.saveAll(applicationLogListToSave);

            issPortalFile.setErrorRecordCount(applicationLogListToSave.size());
            issPortalFileRepository.save(issPortalFile);
        }

        List<IssFileParser> correctedRecordsInFile = findAllByIssPortalFile
            .stream()
            .filter(c1 -> issFileParserValidationErrorSet.stream().noneMatch(c2 -> c1.getId() == c2.getId()))
            .collect(Collectors.toList());

        List<Application> applicationList = new ArrayList<>();
        for (IssFileParser issFileParser : correctedRecordsInFile) {
            Application application = new Application();
            application.setApplicationStatus(0l);
            application.recordStatus(1l);
            application.setIssFileParser(issFileParser);
            application.setFinancialYear(issFileParser.getFinancialYear());
            applicationList.add(application);
        }

        if (!applicationList.isEmpty()) {
            applicationRepository.saveAll(applicationList);
        }

        return ResponseEntity.ok().body(applicationLogListToSave);
    }

    @GetMapping("/submit-batch")
    public List<String> submitBatch() {
        List<CBSMiddleareInputPayload> cbsMiddleareInputPayloadList = new ArrayList<>();
        List<String> cbsResponceStringList = new ArrayList<>();
        Set<String> finantialYearList = applicationRepository.findUniqueFinancialYear();
        for (String finantialYear : finantialYearList) {
            List<Application> applicationList = applicationRepository.findAllByBatchIdAndApplicationStatusAndFinancialYear(
                null,
                0l,
                finantialYear
            );

            int batchSize = 1000;

            for (int i = 1; i < applicationList.size(); i += batchSize) {
                List<Application> batch = applicationList.subList(i, Math.min(i + batchSize, applicationList.size()));
                String processBatch = processBatch(batch);
                cbsResponceStringList.add(processBatch);
            }
        }

        return cbsResponceStringList;
    }

    private String processBatch(List<Application> applicationTransactionList) {
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

            Optional<Integer> relativeMasterCode = MasterDataCacheService.RelativeMasterList
                .stream()
                .filter(f -> f.getRelativeName().toLowerCase().contains(issFileParser.getRelativeType().toLowerCase()))
                .map(RelativeMaster::getRelativeCode)
                .findFirst();

            if (relativeMasterCode.isPresent()) {
                basicDetails.setRelativeType(relativeMasterCode.get());
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
            loanDetails.setKccLoanSanctionedDate("" + issFileParser.getLoanSactionDate());
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

            // added activity type code as on season name
            Optional<Integer> activityTypeCode = MasterDataCacheService.ActivityTypeMasterList
                .stream()
                .filter(f -> f.getActivityType().toLowerCase().contains(issFileParser.getSeasonName().toLowerCase()))
                .map(ActivityType::getActivityTypeCode)
                .findFirst();

            if (activityTypeCode.isPresent()) {
                activities.setActivityType((long) activityTypeCode.get());
            }

            activities.setLoanSanctionedDate("" + issFileParser.getLoanSactionDate());
            activities.setLoanSanctionedAmount(Math.round(Double.parseDouble(issFileParser.getLoanSanctionAmount())));

            ActivityRows activityRows = new ActivityRows();
            List<ActivityRows> activityRowsList = new ArrayList<>();
            activityRows.setLandVillage("" + issFileParser.getVillageCode());

            // add crop code from crop name
            Optional<String> cropNameMasterCode = MasterDataCacheService.CropMasterList
                .stream()
                .filter(f -> f.getCropName().toLowerCase().contains(issFileParser.getCropName().toLowerCase()))
                .map(CropMaster::getCropCode)
                .findFirst();

            /*
             * String findCropCodeByCropNameIsContaining = cropMasterRepository
             * .findCropCodeByCropNameIsContaining(issFileParser.getCropName());
             *
             * activityRows.setCropCode(findCropCodeByCropNameIsContaining);
             */

            if (cropNameMasterCode.isPresent()) {
                activityRows.setCropCode(cropNameMasterCode.get());
            }

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
            }

            activityRowsList.add(activityRows);
            activities.setActivityRows(activityRowsList);

            activityList.add(activities);

            applicationPayload.setActivities(activityList);

            applicationsList.add(applicationPayload);

            applicationTransaction.setBatchId(batchId);
            applicationTransaction.setUniqueId(uniqueId);

            applicationRepository.save(applicationTransaction);
        }

        batchData.setApplications(applicationsList);

        String encryption = encryption(batchData);

        //Making input payload
        CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
        cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
        cbsMiddleareInputPayload.setData(encryption);

        //call fasalrin submit api
        try {
            // fetch from CBS Middleware portal
            cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
            // Set the request URL
            String url = applicationProperties.getCBSMiddlewareBaseURL() + "/submitbatchs";
            // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Create the HttpEntity object with headers and body
            HttpEntity<Object> requestEntity = new HttpEntity<>(cbsMiddleareInputPayload, headers);
            // Make the HTTP POST request
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            cbsResponceString = responseEntity.getBody();

            SubmitApiRespDecryption submitApiRespDecryption = null;
            CBSResponce convertValue = null;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                convertValue = objectMapper.readValue(cbsResponceString, CBSResponce.class);

                if (convertValue.isStatus()) {
                    String decryption = decryption("" + convertValue.getData());
                    objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    submitApiRespDecryption = objectMapper.readValue(decryption, SubmitApiRespDecryption.class);

                    BatchTransaction batchTransaction = new BatchTransaction();
                    batchTransaction.setBatchId(batchId);
                    batchTransaction.setBatchAckId(submitApiRespDecryption.getBatchAckId());
                    batchTransaction.setStatus("Processing");

                    batchTransactionRepository.save(batchTransaction);
                } else {
                    BatchTransaction batchTransaction = new BatchTransaction();
                    batchTransaction.setBatchId(batchId);
                    batchTransaction.setStatus("Discarded");
                    batchTransaction.setBatchDetails(convertValue.getError());
                    batchTransactionRepository.save(batchTransaction);
                }
            } catch (Exception e) {
                System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + e);
            }
        } catch (Exception e) {
            log.error("Error in sending data to fasalrin: " + cbsMiddleareInputPayload);
        }

        return cbsResponceString;
    }

    @GetMapping("/testApi")
    public Object test() {
        SubmitApiRespDecryption submitApiRespDecryption = null;
        CBSResponce convertValue = null;
        String cbsResponceString =
            "{\r\n" +
            "    \"status\": true,\r\n" +
            "    \"data\": \"3e90f0b81ba90788e513015c561924ef40531bed68a253c69668532d31ddb0b4e362fddf73fe3a3368ca8ee672de94a8b9128b2afbdd7c6bbb59177b89e57c6c\",\r\n" +
            "    \"error\": \"\"\r\n" +
            "}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            convertValue = objectMapper.readValue(cbsResponceString, CBSResponce.class);

            if (convertValue.isStatus()) {
                String decryption = decryption("" + convertValue.getData());
                objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                submitApiRespDecryption = objectMapper.readValue(decryption, SubmitApiRespDecryption.class);
            }
        } catch (Exception e) {
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + e);
        }
        return submitApiRespDecryption;
    }

    @PostMapping("/submitBatchToCBS")
    public String submitBatchToCBS(@RequestBody CBSMiddleareInputPayload cBSMiddleareInputPayload) {
        // fetch from CBS Middleware portal

        cBSMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);

        // Set the request URL
        String url = applicationProperties.getCBSMiddlewareBaseURL() + "/submitbatch";

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HttpEntity object with headers and body
        HttpEntity<Object> requestEntity = new HttpEntity<>(cBSMiddleareInputPayload, headers);

        // Make the HTTP POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return responseEntity.getBody();
    }

    @PutMapping("/iss-file-parsers/{id}")
    public Object issFileErrorResolve(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssFileParser issFileParser
    ) throws URISyntaxException {
        log.debug("REST request to update IssFileParser : {}", issFileParser);
        if (issFileParser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<IssFileParser> findById = issFileParserRepository.findById(issFileParser.getId());

        if (!findById.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if (StringUtils.isNotBlank(issFileParser.getFinancialYear()) && issFileParser.getFinancialYear().matches("\\d{4}/\\d{4}")) {
            issFileParser.setFinancialYear(issFileParser.getFinancialYear().replace("/", "-"));
        }

        Set<ApplicationLog> validateOneFileDataObject = validateOneFileDataObject(issFileParser);

        if (validateOneFileDataObject.isEmpty()) {
            // fetching iss portal file and updating error record count
            IssPortalFile issPortalFile = findById.get().getIssPortalFile();
            if (issPortalFile.getErrorRecordCount() != null && issPortalFile.getErrorRecordCount() != 0) {
                issPortalFile.setErrorRecordCount(issPortalFile.getErrorRecordCount() - 1);
            }

            Optional<ApplicationLog> findOneByIssFileParser = applicationLogRepository.findOneByIssFileParser(issFileParser);
            if (findOneByIssFileParser.isPresent() && !findOneByIssFileParser.get().getStatus().equalsIgnoreCase("Fixed")) {
                IssPortalFile issPortalFileSave = issPortalFileRepository.save(issPortalFile);

                // saving iss file data after validating
                issFileParser.setIssPortalFile(issPortalFileSave);
                IssFileParser result = issFileParserService.update(issFileParser);

                // adding file data entry to application tracking table
                Application application = new Application();
                application.setApplicationStatus(0l);
                application.recordStatus(1l);
                application.setIssFileParser(result);
                application.setFinancialYear(result.getFinancialYear());
                applicationRepository.save(application);

                ApplicationLog applicationLog = findOneByIssFileParser.get();
                applicationLog.setStatus("Fixed");
                applicationLogRepository.save(applicationLog);
                return ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issFileParser.getId().toString()))
                    .body(result);
            } else {
                ApplicationLog applicationLog = new ApplicationLog();
                applicationLog.setStatus("Fixed");
                applicationLog.setErrorMessage("Given application not have any error");
                Set<ApplicationLog> applicationLogList = new HashSet<ApplicationLog>();
                applicationLogList.add(applicationLog);

                return ResponseEntity.badRequest().body(applicationLogList);
            }
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
                    "Provide correct Financial Year in format yyyy-yyyy",
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

        if (seasonMasterRepository.findBySeasonNameIsContaining(seasonName).isEmpty()) {
            return true;
        } else {
            return true;
        }
    }

    private boolean validateLandType(String landType) {
        boolean flag = false;
        if (landType == null || "".equalsIgnoreCase(landType)) {
            return flag;
        }
        if (landTypeMasterRepository.findByLandTypeIsContaining(landType).isEmpty()) {
            return true;
        } else {
            return true;
        }
    }

    private boolean validateAmount(String amount) {
        boolean flag = false;
        if (amount == null || "".equalsIgnoreCase(amount)) {
            return flag;
        }
        Pattern patternLongAmount = Pattern.compile("\\d+\\.\\d+");
        Pattern patternDoubleAmount = Pattern.compile("\\d+");

        if (patternLongAmount.matcher(amount).matches()) { // yyyy-MM-dd
            flag = true;
        } else if (patternDoubleAmount.matcher(amount).matches()) { // dd/mm/yyyy
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    private boolean validateAccountHolder(String accountHolder) {
        boolean flag = false;
        if (accountHolder == null || "".equalsIgnoreCase(accountHolder)) {
            return flag;
        }

        if (accountHolderMasterRepository.findByAccountHolderIsContaining(accountHolder).isEmpty()) {
            return true;
        } else {
            return true;
        }
    }

    private boolean validateRelativeType(String relativeName) {
        boolean flag = false;
        if (relativeName == null || "".equalsIgnoreCase(relativeName)) {
            return flag;
        }

        if (relativeMasterRepository.findByRelativeNameIsContaining(relativeName).isEmpty()) {
            return true;
        } else {
            return true;
        }
    }

    private boolean validatePrimaryOccupation(String occupationName) {
        boolean flag = false;
        if (occupationName == null || "".equalsIgnoreCase(occupationName)) {
            return flag;
        }

        if (occupationMasterRepository.findByOccupationNameIsContaining(occupationName).isEmpty()) {
            return true;
        } else {
            return true;
        }
    }

    private boolean validateFarmerType(String farmerType) {
        boolean flag = false;
        if (farmerType == null || "".equalsIgnoreCase(farmerType)) {
            return flag;
        }

        if (farmerTypeMasterRepository.findByFarmerTypeIsContaining(farmerType).isEmpty()) {
            return true;
        } else {
            return true;
        }
    }

    private boolean validateFarmerCategory(String farmerCategory) {
        boolean flag = false;
        if (farmerCategory == null || "".equalsIgnoreCase(farmerCategory)) {
            return flag;
        }

        if (farmerCategoryMasterRepository.findByFarmerCategoryIsContaining(farmerCategory).isEmpty()) {
            return true;
        } else {
            return true;
        }
    }

    private boolean validateSocialCategory(String castCategoryName) {
        boolean flag = false;
        if (castCategoryName == null || "".equalsIgnoreCase(castCategoryName)) {
            return flag;
        }

        if (castCategoryMasterRepository.findByCastCategoryNameIsContaining(castCategoryName).isEmpty()) {
            return true;
        } else {
            return true;
        }
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
        String financialYearPattern = "^\\d{4}-\\d{4}$";
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

    // @PostMapping("/decryption")
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

    private static String getCellValue(Cell cell) {
        String cellValue = "";

        if (cell == null) {
            cellValue = "";
        } else if (cell.getCellType() == CellType.STRING) {
            cellValue = cell.getStringCellValue().trim();

            if (cellValue.contains(".0")) {
                cellValue = cellValue.substring(0, cellValue.indexOf("."));
            }
        } else if (cell.getCellType() == CellType.NUMERIC) {
            cellValue = String.valueOf(cell.getNumericCellValue());

            if (cellValue.contains(".0")) {
                cellValue = cellValue.substring(0, cellValue.indexOf("."));
            }
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
                cellValue = date.format(formatter).trim();
            } else if (patternDDMMYYYY.matcher(cell.getStringCellValue()).matches()) { // dd/mm/yyyy
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(cell.getStringCellValue(), inputFormatter);
                cellValue = date.format(formatter).trim();
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
        ApplicationLog applicationLog = new ApplicationLog();
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
    @PutMapping("/iss-file-parserss/{id}")
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
