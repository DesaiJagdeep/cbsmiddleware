package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.config.MasterDataCacheService;
import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.BatchData;
import com.cbs.middleware.domain.CastCategoryMaster;
import com.cbs.middleware.domain.FarmerCategoryMaster;
import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.domain.FileParseConf;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.domain.LandTypeMaster;
import com.cbs.middleware.domain.OccupationMaster;
import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.domain.User;
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
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.security.RBAControl;
import com.cbs.middleware.service.IssFileParserQueryService;
import com.cbs.middleware.service.IssFileParserService;
import com.cbs.middleware.service.ResponceService;
import com.cbs.middleware.service.criteria.IssFileParserCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserRepository userRepository;

    @Autowired
    RBAControl rbaControl;

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
     *
     * @throws Exception
     */

    @PostMapping("/file-parse-conf/{financialYear}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('FILE_UPLOAD','UPLOAD')")
    public Object excelReaderConfirmation(
        @RequestParam("file") MultipartFile files,
        @PathVariable String financialYear,
        RedirectAttributes redirectAttributes
    ) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());
        FileParseConf fileParseConf = new FileParseConf();
        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        if (issPortalFileRepository.existsByFileNameAndFinancialYear(files.getOriginalFilename(), financialYear)) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(5); // Get the current row

            if (
                getCellValue(row.getCell(4)) != null &&
                !getCellValue(row.getCell(4)).contains("Branch") &&
                !getCellValue(row.getCell(4)).contains("Code")
            ) {
                throw new BadRequestAlertException("Invalid file", ENTITY_NAME, "fileInvalid");
            }

            row = sheet.getRow(6);

            String bankCode = getCellValue(row.getCell(2));
            String branchCode = getCellValue(row.getCell(4));
            String packsCode = getCellValue(row.getCell(32));

            if (StringUtils.isNotBlank(bankCode) && StringUtils.isNotBlank(branchCode) && StringUtils.isNotBlank(packsCode)) {
                if (!bankCode.matches("\\d+") && !bankCode.matches("\\d+") && !bankCode.matches("\\d+")) {
                    throw new BadRequestAlertException("Invalid financial year in file", ENTITY_NAME, "financialYearInvalid");
                }
            }

            rbaControl.authenticateByCode(bankCode, branchCode, packsCode, ENTITY_NAME);
        } catch (BadRequestAlertException e) {
            throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (UnAuthRequestAlertException e) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (EncryptedDocumentException e1) {
            throw new EncryptedDocumentException("EncryptedDocumentException");
        } catch (IOException e1) {
            throw new IOException("IOException");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(6); // Get the current row

            String fYear = getCellValue(row.getCell(0));
            if (StringUtils.isNoneBlank(fYear) && fYear.matches("\\d{4}/\\d{4}")) {
                fYear = fYear.replace("/", "-");

                if (!fYear.equalsIgnoreCase(financialYear)) {
                    throw new BadRequestAlertException("Invalid financial year in file", ENTITY_NAME, "financialYearInvalid");
                }
            } else {
                throw new BadRequestAlertException("Invalid financial year in file", ENTITY_NAME, "financialYearInvalid");
            }

            fileParseConf.setBankName(getCellValue(row.getCell(1)));
            fileParseConf.setBankCode(getCellValue(row.getCell(2)));
            fileParseConf.setBranchName(getCellValue(row.getCell(3)));
            fileParseConf.setBranchCode(getCellValue(row.getCell(4)));
            fileParseConf.setPacsName(getCellValue(row.getCell(31)));
            fileParseConf.setPacsCode(getCellValue(row.getCell(32)));
            return fileParseConf;
        } catch (BadRequestAlertException e) {
            throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (UnAuthRequestAlertException e) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (EncryptedDocumentException e1) {
            throw new EncryptedDocumentException("EncryptedDocumentException");
        } catch (IOException e1) {
            throw new IOException("IOException");
        }
    }

    @PostMapping("/fileParser/{financialYear}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('FILE_UPLOAD','UPLOAD')")
    public Object excelReader1(
        @RequestParam("file") MultipartFile files,
        @PathVariable String financialYear,
        RedirectAttributes redirectAttributes
    ) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());

        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        if (issPortalFileRepository.existsByFileNameAndFinancialYear(files.getOriginalFilename(), financialYear)) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(5); // Get the current row
            if (
                getCellValue(row.getCell(0)) != null &&
                !getCellValue(row.getCell(0)).contains("Financial") &&
                !getCellValue(row.getCell(0)).contains("Year")
            ) {
                throw new BadRequestAlertException("Invalid file", ENTITY_NAME, "fileInvalid");
            }

            row = sheet.getRow(6);
            if (
                StringUtils.isBlank(getCellValue(row.getCell(2))) &&
                StringUtils.isBlank(getCellValue(row.getCell(4))) &&
                StringUtils.isBlank(getCellValue(row.getCell(32)))
            ) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }

            String bankCode = getCellValue(row.getCell(2));
            String branchCode = getCellValue(row.getCell(4));
            String packsCode = getCellValue(row.getCell(32));

            if (StringUtils.isNotBlank(bankCode) && StringUtils.isNotBlank(branchCode) && StringUtils.isNotBlank(packsCode)) {
                if (!bankCode.matches("\\d+") && !bankCode.matches("\\d+") && !bankCode.matches("\\d+")) {
                    throw new BadRequestAlertException("Invalid financial year in file", ENTITY_NAME, "financialYearInvalid");
                }
            }
            rbaControl.authenticateByCode(bankCode, branchCode, packsCode, ENTITY_NAME);
            boolean flag = false;
            String fYear = getCellValue(row.getCell(0));

            if (StringUtils.isNotBlank(fYear) && !fYear.matches("\\d{4}/\\d{4}") && !fYear.matches("\\d{4}-\\d{4}")) {
                flag = true;
            }

            String ifsc = getCellValue(row.getCell(6));
            if (StringUtils.isNotBlank(ifsc) && !ifsc.matches("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$")) {
                flag = true;
            }

            String aadharNumber = getCellValue(row.getCell(10));
            if (StringUtils.isNotBlank(aadharNumber) && !validateAadhaarNumber(aadharNumber)) {
                flag = true;
            }

            String villageCode = getCellValue(row.getCell(25));
            if (StringUtils.isNotBlank(villageCode) && !villageCode.matches("^[0-9.]+$") && !villageCode.matches("^[0-9]+$")) {
                flag = true;
            }
            String pinCode = getCellValue(row.getCell(28));
            if (StringUtils.isNotBlank(pinCode) && !pinCode.matches("^[0-9]{6}$")) {
                flag = true;
            }

            String accountNumber = getCellValue(row.getCell(30));
            if (StringUtils.isNotBlank(accountNumber) && !pinCode.matches("\\d+")) {
                flag = true;
            }
            String landtype = getCellValue(row.getCell(44));

            if (StringUtils.isNotBlank(landtype) && landtype.equalsIgnoreCase("irrigated") && landtype.equalsIgnoreCase("non-irrigated")) {
                flag = true;
            }
            if (flag) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }
        } catch (BadRequestAlertException e) {
            throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (UnAuthRequestAlertException e) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (EncryptedDocumentException e1) {
            throw new EncryptedDocumentException("EncryptedDocumentException");
        } catch (IOException e1) {
            throw new IOException("IOException");
        }

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
            throw new BadRequestAlertException("file not saved successfully", ENTITY_NAME, "fileInvalid");
        }

        IssPortalFile issPortalFile = new IssPortalFile();
        issPortalFile.setFileName(files.getOriginalFilename());
        issPortalFile.setUniqueName(uniqueName + "." + fileExtension);
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

                    if (
                        StringUtils.isNotBlank(issFileParser.getFinancialYear()) &&
                        StringUtils.isNotBlank(issFileParser.getBankName()) &&
                        StringUtils.isNotBlank(issFileParser.getBankCode()) &&
                        StringUtils.isNotBlank(issFileParser.getBranchCode())
                    ) {
                        issFileParserList.add(issFileParser);
                    }
                }
            }

            issPortalFile.setApplicationCount("" + issFileParserList.size());
            issPortalFile.setFinancialYear(issFileParserList.get(0).getFinancialYear());
            issPortalFile.setBranchCode(Math.round(Double.parseDouble(issFileParserList.get(0).getBranchCode())));
            issPortalFile.setPacsCode(Long.parseLong(issFileParserList.get(0).getPacsNumber()));
            issPortalFile.setPacsName(issFileParserList.get(0).getPacsName());
            issPortalFile.setBranchName(issFileParserList.get(0).getBranchName());

            issPortalFileRepository.save(issPortalFile);

            issFileParserRepository.saveAll(issFileParserList);
            return ResponseEntity.ok().body(issFileParserList);
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
    }

    @PostMapping("/validateFile")
    @PreAuthorize("@authentication.hasPermision('',#issPortalFile.id,'','FILE_VALIDATE','VALIDATE')")
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
            applicationLogList.add(new ApplicationLog("Financial Year is not in yyyy-yyyy format", issFileParser));
        }

        // Filter invalid Aadhaar numbers
        List<IssFileParser> invalidAadhaarList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateAadhaarNumber(person.getAadharNumber()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidAadhaarList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Aadhar number is incorrect format", issFileParser));
        }

        // Filter invalid beneficiary Name and beneficiary Passbook Name and
        // accountHolder

        List<IssFileParser> invalidBeneficiaryNameList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getFarmerName()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidBeneficiaryNameList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Beneficiary Name is incorrect format", issFileParser));
        }

        // Filter invalid Mobile number
        List<IssFileParser> invalidMobileNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateMobileNumber(person.getMobileNo()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidMobileNumberList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Mobile number is incorrect format", issFileParser));
        }

        // Filter invalid dob

        List<IssFileParser> invalidDOBList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getDateofBirth()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidDOBList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Date of birth is not in yyyy-mm-dd format", issFileParser));
        }

        // Filter invalid gender

        List<IssFileParser> invalidGenderList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateGender(person.getGender()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidGenderList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Gender is incorrect format", issFileParser));
        }

        // Filter invalid socialCategory

        List<IssFileParser> invalidSocialCategoryList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateSocialCategory(person.getSocialCategory()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidSocialCategoryList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Social Category is not in SC, ST, OBC, GEN format", issFileParser));
        }

        // Filter invalid farmerCategory
        List<IssFileParser> invalidFarmerCategoryList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateFarmerCategory(person.getFarmersCategory()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidFarmerCategoryList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Farmer Category is not in OWNER, SHARECROPPER, TENANT format", issFileParser));
        }

        // Filter invalid farmerType
        List<IssFileParser> invalidFarmerTypeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateFarmerType(person.getFarmerType()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidFarmerTypeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Farmer type is not in SMALL, OTHER, MARGINAL format", issFileParser));
        }

        // Filter invalid primaryOccupation

        List<IssFileParser> invalidPrimaryOccupationList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validatePrimaryOccupation(person.getPrimaryOccupation()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidPrimaryOccupationList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(
                new ApplicationLog("Farmer primary occupation is not in FARMER, FISHRIES, ANIMAL HUSBANDARY format", issFileParser)
            );
        }

        // Filter invalid relativeType
        List<IssFileParser> invalidRelativeTypeList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getRelativeType()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidRelativeTypeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(
                new ApplicationLog("Relative Type is not in SON OF, DAUGHTER OF, CARE OF, WIFE OF format", issFileParser)
            );
        }

        // Filter invalid relativeName

        List<IssFileParser> invalidRelativeNameList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getRelativeName()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidRelativeNameList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Relative name is incorrect format", issFileParser));
        }

        // Filter invalid residentialPincode
        List<IssFileParser> invalidResidentialPincodeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getPinCode().matches("^[0-9]{6}$"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidResidentialPincodeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Residential pin code is incorrect format", issFileParser));
        }

        // Filter invalid accountNumber
        List<IssFileParser> invalidAccountNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getAccountNumber().matches("\\d+"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidAccountNumberList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Account Number is incorrect format", issFileParser));
        }

        // Filter invalid Scheme Wise Branch Code
        List<IssFileParser> invalidBranchCodeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getSchemeWiseBranchCode().matches("^[0-9]{6}$"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidBranchCodeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Scheme Wise Branch Code is incorrect format", issFileParser));
        }

        // Filter invalid ifsc
        List<IssFileParser> invalidIFSCCodeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getIfsc().matches("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidIFSCCodeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("IFSC Code is incorrect format", issFileParser));
        }

        // Filter invalid kccLoanSanctionedDate //loanSanctionedDate
        List<IssFileParser> invalidKccLoanSanctionedDateList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getLoanSactionDate()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidKccLoanSanctionedDateList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Loan Sanctioned Date is incorrect format", issFileParser));
        }

        // Filter invalid kccLoanSanctionedAmount // kccDrawingLimitforFY // Loan
        // Sanction Amount
        List<IssFileParser> invalidkccLoanSanctionedAmountList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateAmount(person.getLoanSanctionAmount()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidkccLoanSanctionedAmountList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Loan Sanction Amount is incorrect format", issFileParser));
        }

        // Filter invalid landVillage
        List<IssFileParser> invalidLandVillageList = findAllByIssPortalFile
            .stream()
            .filter(person -> !person.getVillageCode().matches("^[0-9]{6}$"))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidLandVillageList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Land Village Code is incorrect format", issFileParser));
        }

        // Filter invalid cropCode
        List<IssFileParser> invalidCropCodeList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getCropName()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidCropCodeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Crop Name is incorrect format", issFileParser));
        }

        // Filter invalid surveyNumber
        List<IssFileParser> invalidSurveyNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> StringUtils.isBlank(person.getSurveyNo()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidSurveyNumberList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Survey Number is incorrect format", issFileParser));
        }

        // Filter invalid khataNumber or Sat Bara number
        List<IssFileParser> invalidSatBaraNumberList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateSatBaraNumber(person.getSatBaraSubsurveyNo()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidSatBaraNumberList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Sat Bara number is incorrect format", issFileParser));
        }

        // Filter invalid landArea
        List<IssFileParser> invalidLandAreaList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateAmount(person.getAreaHect()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidLandAreaList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Land Area Hect is incorrect format", issFileParser));
        }

        // Filter invalid landType
        List<IssFileParser> invalidLandTypeList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateLandType(person.getLandType()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidLandTypeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Land Type is not in IRRIGATED, NON-IRRIGATED format", issFileParser));
        }

        // Filter invalid season //activityType
        List<IssFileParser> invalidSeasonList = findAllByIssPortalFile
            .stream()
            .filter(person -> !validateSeasonName(person.getSeasonName()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidSeasonList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(
                new ApplicationLog("Season is not in KHARIF, RABI, SUMMER/ZAID/OTHERS, HORTICULTURE, SUGARCANE format", issFileParser)
            );
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
                    str = str.append(". ");
                }
            }
            ApplicationLog applicationLog = new ApplicationLog();
            Optional<ApplicationLog> applicationLogSaved = applicationLogRepository.findOneByIssFileParser(issFileParser);
            if (applicationLogSaved.isPresent()) {
                applicationLog = applicationLogSaved.get();
            }
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("" + str);
            applicationLog.setSevierity(Constants.HighSevierity);
            applicationLog.setExpectedSolution("Provide correct information");
            applicationLog.setStatus(Constants.ERROR);
            applicationLog.setErrorType(Constants.validationError);
            applicationLog.setErrorRecordCount(collect.size());
            applicationLog.setIssPortalId(issPortalFile.getId());
            applicationLog.setFileName(issPortalFile.getFileName());
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
        if (!correctedRecordsInFile.isEmpty()) {
            for (IssFileParser issFileParser : correctedRecordsInFile) {
                if (!applicationRepository.findOneByIssFileParser(issFileParser).isPresent()) {
                    Application application = new Application();
                    application.setRecordStatus(Constants.COMPLETE_FARMER_DETAIL_AND_LOAN_DETAIL);
                    application.setApplicationStatus(Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD);
                    application.setIssFileParser(issFileParser);
                    application.setIssFilePortalId(issFileParser.getIssPortalFile().getId());
                    applicationList.add(application);
                }
            }

            applicationRepository.saveAll(applicationList);
        }

        return ResponseEntity.ok().body(applicationLogListToSave);
    }

    @PutMapping("/iss-file-parsers/{id}")
    @PreAuthorize("@authentication.hasPermision('','',#id,'UPDATE_RECORD','EDIT')")
    public Object issFileErrorResolve(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssFileParser issFileParser
    ) throws URISyntaxException {
        log.debug("REST request to update IssFileParser : {}", issFileParser);
        if (issFileParser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<IssFileParser> issFileParserOptional = issFileParserRepository.findById(issFileParser.getId());

        if (!issFileParserOptional.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationLog> findOneByIssFileParser = applicationLogRepository.findOneByIssFileParser(issFileParser);

        if (!findOneByIssFileParser.isPresent()) {
            throw new BadRequestAlertException("Application log not found", ENTITY_NAME, "applognotfound");
        }

        if (findOneByIssFileParser.isPresent() && findOneByIssFileParser.get().getStatus().equalsIgnoreCase(Constants.FIXED)) {
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setStatus(Constants.FIXED);
            applicationLog.setErrorMessage("Given application not have any error");

            return ResponseEntity.badRequest().body(applicationLog);
        }

        ApplicationLog validateOneFileDataObject = getValidateOneFileDataObject(issFileParser, findOneByIssFileParser.get());

        // fetching iss portal file and updating error record count
        IssPortalFile issPortalFile = issFileParser.getIssPortalFile();
        if (
            issPortalFile.getErrorRecordCount() != null &&
            Constants.validationError.equalsIgnoreCase(validateOneFileDataObject.getErrorType()) &&
            issPortalFile.getErrorRecordCount() != 0
        ) {
            issPortalFile.setErrorRecordCount(issPortalFile.getErrorRecordCount() - 1);
        } else if (
            issPortalFile.getKccErrorRecordCount() != null &&
            Constants.kccError.equalsIgnoreCase(validateOneFileDataObject.getErrorType()) &&
            issPortalFile.getErrorRecordCount() != 0
        ) {
            issPortalFile.setErrorRecordCount(issPortalFile.getKccErrorRecordCount() - 1);
        }

        IssPortalFile issPortalFileSave = issPortalFileRepository.save(issPortalFile);

        // saving iss file data after validating
        issFileParser.setIssPortalFile(issPortalFileSave);
        IssFileParser result = issFileParserService.update(issFileParser);

        // adding file data entry to application tracking table
        Application application = new Application();
        application.setRecordStatus(Constants.COMPLETE_FARMER_DETAIL_AND_LOAN_DETAIL);
        application.setApplicationStatus(Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD);
        application.setIssFileParser(result);
        application.setIssFilePortalId(issPortalFileSave.getId());
        applicationRepository.save(application);

        // adding status fixed in application log
        ApplicationLog applicationLog = findOneByIssFileParser.get();
        applicationLog.setStatus(Constants.FIXED);
        applicationLog.setSevierity("");
        applicationLog.setErrorRecordCount(0);
        applicationLogRepository.save(applicationLog);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issFileParser.getId().toString()))
            .body(result);
    }

    public ApplicationLog getValidateOneFileDataObject(IssFileParser issFileParser, ApplicationLog applicationLog) {
        StringBuilder validationErrorBuilder = new StringBuilder();
        int errorCount = 0;

        // Filter invalid Financial Year

        if (StringUtils.isNotBlank(issFileParser.getFinancialYear()) && issFileParser.getFinancialYear().matches("\\d{4}/\\d{4}")) {
            issFileParser.setFinancialYear(issFileParser.getFinancialYear().replace("/", "-"));
        }

        if (!validateFinancialYear(issFileParser.getFinancialYear())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Financial Year is not in yyyy-yyyy format. ");
        }

        // beneficiaryName
        if (StringUtils.isBlank(issFileParser.getFarmerName())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Beneficiary Name is Empty. ");
        }

        // Filter invalid Mobile number
        if (!validateMobileNumber(issFileParser.getMobileNo())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Mobile number is in incorrect format. ");
        }

        // Filter date of birth
        if (!validateDate(issFileParser.getDateofBirth())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Date Of Birth is not in yyyy-mm-dd format. ");
        }

        // gender
        if (!validateGender(issFileParser.getGender())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Incorect Gender:Correct as male or female. ");
        }

        // socialCategory
        if (!validateSocialCategory(issFileParser.getSocialCategory())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("socialCategory is in incorrect format: Correct as SC or ST or OBC or GEN. ");
        }

        // farmerCategory
        if (!validateFarmerCategory(issFileParser.getFarmersCategory())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Farmer Category is in incorrect format: Correct as OWNER or SHARECROPPER or TENANT. ");
        }

        // farmerType
        if (!validateFarmerType(issFileParser.getFarmerType())) {
            validationErrorBuilder.append("Farmer Type is in incorrect format: Correct as  SMALL or OTHER or MARGINAL. ");
        }

        // primaryOccupation
        if (!validatePrimaryOccupation(issFileParser.getPrimaryOccupation())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append(
                "Primary Occupation is in incorrect format: Correct as FARMER or FISHRIES or ANIMAL HUSBANDARY. "
            );
        }

        // relativeType
        if (StringUtils.isBlank(issFileParser.getRelativeType())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Relative Type is in incorrect format: Correct as SON OF or DAUGHTER OF or CARE OF or WIFE OF. ");
        }

        // relativeName
        if (StringUtils.isBlank(issFileParser.getRelativeName())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Relative Name is in empty format. ");
        }

        // residentialPincode
        if (!issFileParser.getPinCode().matches("^[0-9]{6}$")) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Residential Pin Code is in incorrect format. ");
        }

        // accountNumber
        if (!issFileParser.getAccountNumber().matches("\\d+")) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Account Number is in incorrect format. ");
        }

        // branchCode
        if (!issFileParser.getBranchCode().matches("\\d+")) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Branch Code is in incorrect format. ");
        }

        // ifsc
        if (!issFileParser.getIfsc().matches("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$")) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("IFSC is in incorrect format. ");
        }

        // accountHolder
        if (!validateAccountHolder(issFileParser.getAccountHolderType())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Account Holder is in incorrect format: Correct as SINGLE or JOINT. ");
        }

        // jointAccountHolders name
        /*
         * if (StringUtils.isBlank(issFileParser.getRelativeName())) {
         * applicationLogList.add(
         * generateApplicationLog("Joint Account Holders is in incorrect format",
         * "Provide correct Joint Account Holders ", Constants.HighSevierity)); }
         */

        // jointAccountHolders Aadhaar number

        // add logic for joint account holder

        // kccLoanSanctionedDate
        if (!validateDate(issFileParser.getLoanSactionDate())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("kcc Loan Sanctioned Date is in incorrect format. ");
        }

        // kccLoanSanctionedAmount
        // kccDrawingLimitforFY
        if (!validateAmount(issFileParser.getLoanSanctionAmount())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("kcc Loan Sanctioned Amount is in incorrect format. ");
        }

        // activityType

        // loanSanctionedDate
        if (!validateDate(issFileParser.getLoanSactionDate())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Farmer Category is in incorrect format. ");
        }

        // loanSanctionedAmount

        if (!validateAmount(issFileParser.getLoanSanctionAmount())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Loan Sanction Amount is incorrect format. ");
        }

        // landVillage
        if (!issFileParser.getVillageCode().matches("\\d+")) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Land Village is in incorrect format. ");
        }

        // cropCode
        if (!cropMasterRepository.existsByCropName(issFileParser.getCropName())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Crop Name is in incorrect format. ");
        }

        // surveyNumber
        if (!issFileParser.getSurveyNo().matches("^[0-9/]+$")) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Survey Number is in incorrect format. ");
        }

        // khataNumber
        if (!validateSatBaraNumber(issFileParser.getSatBaraSubsurveyNo())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Sat Bara number is in incorrect format. ");
        }

        // landArea
        if (!validateAmount(issFileParser.getAreaHect())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Land Area is in incorrect format. ");
        }

        // landType
        if (!validateLandType(issFileParser.getLandType())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Land Type is in incorrect format. ");
        }

        // season
        if (!validateSeasonName(issFileParser.getSeasonName())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append(
                "Season Name is in incorrect format: Correct as KHARIF or RABI or SUMMER/ZAID/OTHERS or HORTICULTURE or SUGARCANE. "
            );
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

        if (validationErrorBuilder == null || validationErrorBuilder.toString().equals("")) {
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setStatus(Constants.FIXED);
            applicationLog.setIssPortalId(issFileParser.getIssPortalFile().getId());
            applicationLog.setFileName(issFileParser.getIssPortalFile().getFileName());
        } else {
            applicationLog.setIssFileParser(issFileParser);
            applicationLog.setErrorMessage("" + validationErrorBuilder);
            applicationLog.setSevierity(Constants.HighSevierity);
            applicationLog.setExpectedSolution("Provide correct information");
            applicationLog.setStatus(Constants.ERROR);
            applicationLog.setErrorType(Constants.validationError);
            applicationLog.setErrorRecordCount(errorCount);
            applicationLog.setIssPortalId(issFileParser.getIssPortalFile().getId());
            applicationLog.setFileName(issFileParser.getIssPortalFile().getFileName());
        }

        return applicationLog;
    }

    private boolean validateSeasonName(String seasonName) {
        boolean flag = false;
        if (StringUtils.isBlank(seasonName)) {
            return flag;
        }

        if (
            MasterDataCacheService.SeasonMasterList
                .stream()
                .filter(f -> f.getSeasonName().toLowerCase().contains(seasonName.toLowerCase()))
                .map(SeasonMaster::getSeasonCode)
                .findFirst()
                .isPresent()
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateLandType(String landType) {
        boolean flag = false;
        if (StringUtils.isBlank(landType)) {
            return flag;
        }
        if (
            MasterDataCacheService.LandTypeMasterList
                .stream()
                .filter(f -> f.getLandType().toLowerCase().contains(landType.toLowerCase()))
                .map(LandTypeMaster::getLandTypeCode)
                .findFirst()
                .isPresent()
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateAmount(String amount) {
        boolean flag = false;
        if (StringUtils.isBlank(amount)) {
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
        if (StringUtils.isBlank(accountHolder)) {
            return flag;
        }

        if (
            MasterDataCacheService.AccountHolderMasterList
                .stream()
                .filter(f -> f.getAccountHolder().toLowerCase().contains(accountHolder.toLowerCase()))
                .map(AccountHolderMaster::getAccountHolderCode)
                .findFirst()
                .isPresent()
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePrimaryOccupation(String occupationName) {
        boolean flag = false;
        if (StringUtils.isBlank(occupationName)) {
            return flag;
        }

        if (
            MasterDataCacheService.OccupationMasterList
                .stream()
                .filter(f -> f.getOccupationName().toLowerCase().contains(occupationName.toLowerCase()))
                .map(OccupationMaster::getOccupationCode)
                .findFirst()
                .isPresent()
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateFarmerType(String farmerType) {
        boolean flag = false;
        if (StringUtils.isBlank(farmerType)) {
            return flag;
        }

        if (
            MasterDataCacheService.FarmerTypeMasterList
                .stream()
                .filter(f -> f.getFarmerType().toLowerCase().contains(farmerType.toLowerCase()))
                .map(FarmerTypeMaster::getFarmerTypeCode)
                .findFirst()
                .isPresent()
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateFarmerCategory(String farmerCategory) {
        boolean flag = false;
        if (StringUtils.isBlank(farmerCategory)) {
            return flag;
        }

        if (
            MasterDataCacheService.FarmerCategoryMasterList
                .stream()
                .filter(f -> f.getFarmerCategory().toLowerCase().contains(farmerCategory.toLowerCase()))
                .map(FarmerCategoryMaster::getFarmerCategoryCode)
                .findFirst()
                .isPresent()
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateSocialCategory(String castCategoryName) {
        boolean flag = false;
        if (StringUtils.isBlank(castCategoryName)) {
            return flag;
        }

        if (
            MasterDataCacheService.CastCategoryMasterList
                .stream()
                .filter(c -> c.getCastCategoryName().toLowerCase().contains(castCategoryName.toLowerCase()))
                .map(CastCategoryMaster::getCastCategoryCode)
                .findFirst()
                .isPresent()
        ) {
            return true;
        } else {
            return false;
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
            BigDecimal bigDecimal = new BigDecimal(cellValue);
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat decimalFormat = new DecimalFormat("0", symbols);

            cellValue = decimalFormat.format(bigDecimal);
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            cellValue = String.valueOf(cell.getNumericCellValue());

            if (cellValue.contains(".0")) {
                cellValue = cellValue.substring(0, cellValue.indexOf("."));
            }
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
        } else if (cell.getCellType() == CellType.FORMULA) {
            LocalDate date = LocalDate.of(1900, 1, 1).plusDays((long) cell.getNumericCellValue() - 2);
            cellValue = date.format(formatter);
        } else if (cell.getCellType() == CellType.BLANK) {
            cellValue = "";
        }

        return cellValue;
    }

    ApplicationLog generateApplicationLog(String ErrorMsg, String expectedSolution, String sevierity) {
        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setErrorMessage(ErrorMsg);
        applicationLog.setSevierity(sevierity);
        applicationLog.setExpectedSolution(expectedSolution);
        applicationLog.setErrorType(Constants.validationError);
        applicationLog.setStatus(Constants.ERROR);
        return applicationLog;
    }

    @GetMapping("/issPortalFile/{idISP}/issFileParsers")
    @PreAuthorize("@authentication.hasPermision('','',#idISP,'VIEW_RECORD','VIEW')")
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
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
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
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
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
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
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
    @PreAuthorize("@authentication.onDatabaseRecordPermission('VIEW_RECORD','VIEW')")
    public ResponseEntity<List<IssFileParser>> getAllIssFileParsers(
        IssFileParserCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get IssFileParsers by criteria: {}", criteria);

        Page<IssFileParser> page = null;
        Map<String, String> branchOrPacksNumber = getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            page =
                issFileParserQueryService.findByCriteriaPackNumber(criteria, pageable, branchOrPacksNumber.get(Constants.PACKS_CODE_KEY));
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BRANCH_CODE_KEY))) {
            page =
                issFileParserQueryService.findByCriteriaBranchNumber(
                    criteria,
                    pageable,
                    branchOrPacksNumber.get(Constants.BRANCH_CODE_KEY)
                );
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            page = issFileParserQueryService.findByCriteria(criteria, pageable);
        } else {
            throw new UnAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }

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
    @PreAuthorize("@authentication.onDatabaseRecordPermission('VIEW_RECORD','VIEW')")
    public ResponseEntity<IssFileParser> getIssFileParser(@PathVariable Long id) {
        log.debug("REST request to get IssFileParser : {}", id);
        Optional<IssFileParser> issFileParser = null;
        Map<String, String> branchOrPacksNumber = getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            issFileParser = issFileParserRepository.findOneByIdAndPacsNumber(id, branchOrPacksNumber.get(Constants.PACKS_CODE_KEY));
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BRANCH_CODE_KEY))) {
            issFileParser = issFileParserRepository.findOneByIdAndBranchCode(id, branchOrPacksNumber.get(Constants.BRANCH_CODE_KEY));
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            issFileParser = issFileParserRepository.findOneByIdAndBankCode(id, branchOrPacksNumber.get(Constants.BANK_CODE_KEY));
        } else {
            throw new UnAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }
        return ResponseUtil.wrapOrNotFound(issFileParser);
    }

    /**
     * {@code DELETE  /iss-file-parsers/:id} : delete the "id" issFileParser.
     *
     * @param id the id of the issFileParser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/iss-file-parsers/{id}")
    @PreAuthorize("@authentication.hasPermision('','',#id,'DELETE_RECORD','DELETE')")
    public ResponseEntity<Void> deleteIssFileParser(@PathVariable Long id) {
        log.debug("REST request to delete IssFileParser : {}", id);
        issFileParserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Function for get bank code, branch code and packs code from user token
     *
     * @return
     */
    private Map<String, String> getCodeNumber() {
        Map<String, String> branchOrPacksNumber = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        if (StringUtils.isNotBlank(optUser.get().getPacsNumber())) {
            branchOrPacksNumber.put(Constants.PACKS_CODE_KEY, optUser.get().getPacsNumber());
        } else if (StringUtils.isNotBlank(optUser.get().getBranchCode())) {
            branchOrPacksNumber.put(Constants.BRANCH_CODE_KEY, optUser.get().getBranchCode());
        } else if (StringUtils.isNotBlank(optUser.get().getBankCode())) {
            branchOrPacksNumber.put(Constants.BANK_CODE_KEY, optUser.get().getBankCode());
        } else {
            throw new UnAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }
        return branchOrPacksNumber;
    }
}
