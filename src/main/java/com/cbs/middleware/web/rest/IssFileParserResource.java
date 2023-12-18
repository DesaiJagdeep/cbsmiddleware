package com.cbs.middleware.web.rest;

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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.config.MasterDataCacheService;
import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.ActivityType;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.BatchData;
import com.cbs.middleware.domain.CastCategoryMaster;
import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.domain.FarmerCategoryMaster;
import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.domain.FileParseConf;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.domain.LandTypeMaster;
import com.cbs.middleware.domain.OccupationMaster;
import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.repository.BankBranchMasterRepository;
import com.cbs.middleware.repository.BatchTransactionRepository;
import com.cbs.middleware.repository.CastCategoryMasterRepository;
import com.cbs.middleware.repository.CropMasterRepository;
import com.cbs.middleware.repository.DesignationMasterRepository;
import com.cbs.middleware.repository.FarmerCategoryMasterRepository;
import com.cbs.middleware.repository.FarmerTypeMasterRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.repository.LandTypeMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.OccupationMasterRepository;
import com.cbs.middleware.repository.RelativeMasterRepository;
import com.cbs.middleware.repository.SeasonMasterRepository;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.security.RBAControl;
import com.cbs.middleware.service.IssFileParserQueryService;
import com.cbs.middleware.service.IssFileParserService;
import com.cbs.middleware.service.MailService;
import com.cbs.middleware.service.ResponceService;
import com.cbs.middleware.service.criteria.IssFileParserCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.BankBranchPacksCodeGet;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssFileParser}.
 */
@RestController
@RequestMapping("/api")
public class IssFileParserResource {

    private static final String ENTITY_NAME = "issFileParser";
    private final Logger log = LoggerFactory.getLogger(IssFileParserResource.class);
    private final IssFileParserService issFileParserService;
    private final IssFileParserRepository issFileParserRepository;
    private final IssFileParserQueryService issFileParserQueryService;
    @Autowired
    ApplicationProperties applicationProperties;
    @Autowired
    NotificationDataUtility notificationDataUtility;
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
    @Autowired
    BankBranchPacksCodeGet bankBranchPacksCodeGet;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BankBranchMasterRepository bankBranchMasterRepository;
    @Autowired
    MailService mailService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public IssFileParserResource(
        IssFileParserService issFileParserService,
        IssFileParserRepository issFileParserRepository,
        IssFileParserQueryService issFileParserQueryService
    ) {
        this.issFileParserService = issFileParserService;
        this.issFileParserRepository = issFileParserRepository;
        this.issFileParserQueryService = issFileParserQueryService;
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

    private static SecretKey generateSecretKey(String secretKey, int keySize) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] truncatedKey = new byte[keySize / 8];
        System.arraycopy(keyBytes, 0, truncatedKey, 0, truncatedKey.length);
        return new SecretKeySpec(truncatedKey, "AES");
    }

    private static String getStringCellValue(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else {
            return null;
        }
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
            cellValue =
                String.valueOf(cell.getNumericCellValue());

            if (cellValue.contains(".0")) {
                cellValue = cellValue.substring(0,
                    cellValue.indexOf("."));
            }
        } else if (cell.getCellType() == CellType.BLANK) {
            cellValue = "";
        }

        return cellValue;
    }

    private static String getFloatCellValue(Cell cell) {
        String cellValue = "";

        if (cell == null) {
            cellValue = "";
        } else if (cell.getCellType() == CellType.STRING) {
            cellValue = cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            cellValue = String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            cellValue = String.valueOf(cell.getNumericCellValue());
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


            Pattern patternYYYY_MM_DD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            Pattern patternYYYYMMDD = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");
            Pattern patternDDMMYYYY = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
            Pattern patternDD_MM_YYYY = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

            if (patternYYYY_MM_DD.matcher(cell.getStringCellValue()).matches()) { // yyyy-MM-dd
                LocalDate date = LocalDate.parse(cell.getStringCellValue());
                cellValue = date.format(formatter).trim();
            } else if (patternYYYYMMDD.matcher(cell.getStringCellValue()).matches()) { // dd/mm/yyyy
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/dd/MM");
                LocalDate date = LocalDate.parse(cell.getStringCellValue(), inputFormatter);
                cellValue = date.format(formatter).trim();
            } else if (patternDDMMYYYY.matcher(cell.getStringCellValue()).matches()) { // dd/mm/yyyy
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(cell.getStringCellValue(), inputFormatter);
                cellValue = date.format(formatter).trim();
            } else if (patternDD_MM_YYYY.matcher(cell.getStringCellValue()).matches()) { // dd/mm/yyyy
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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

    /**
     * Customize code
     *
     * @return
     * @throws Exception
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
        if (!"xlsx".equalsIgnoreCase(fileExtension) && !"xls".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        if (issPortalFileRepository.existsByFileNameAndFinancialYear(files.getOriginalFilename(), financialYear)) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int filecount = 0;
            Row row = null;
            for (int i = 0; i < 10; i++) {
                row = sheet.getRow(i); // Get the current row
                String financialYearInFile = getCellValue(row.getCell(0));
                if (StringUtils.isNotBlank(financialYearInFile)) {
                    financialYearInFile = financialYearInFile.trim().replace("\n", " ").toLowerCase();
                    if (financialYearInFile.contains("financial") && financialYearInFile.contains("year")) {
                        filecount = i;
                        break;
                    }
                }
            }

            row = sheet.getRow(filecount); // Get the current row
            boolean flag = false;

            String financialYearInFileConf = getCellValue(row.getCell(0));
            if (StringUtils.isNotBlank(financialYearInFileConf)) {
                financialYearInFileConf = financialYearInFileConf.trim().replace("\n", " ").toLowerCase();
                if (!financialYearInFileConf.contains("financial") && !financialYearInFileConf.contains("year")) {
                    throw new BadRequestAlertException("Missing Financial Year Column", ENTITY_NAME, "fileInvalid");
                    //flag = true;
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Financial Year Column", ENTITY_NAME, "fileInvalid");

            }

            // validating excel heading column name
            String bankName = getCellValue(row.getCell(1));
            if (StringUtils.isNotBlank(bankName)) {
                bankName = bankName.trim().replace("\n", " ").toLowerCase();
                if (!bankName.contains("bank") && !bankName.contains("name")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Bank Name Column", ENTITY_NAME, "fileInvalid");

                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Bank Name Column", ENTITY_NAME, "fileInvalid");

            }
            String bankCodeColumn = getCellValue(row.getCell(2));
            if (StringUtils.isNotBlank(bankCodeColumn)) {
                bankCodeColumn = bankCodeColumn.trim().replace("\n", " ").toLowerCase();
                if (!bankCodeColumn.contains("bank") && !bankCodeColumn.contains("code")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Bank Code Column", ENTITY_NAME, "fileInvalid");

                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Bank Code Column", ENTITY_NAME, "fileInvalid");

            }

            String branchNameColumn = getCellValue(row.getCell(3));
            if (StringUtils.isNotBlank(branchNameColumn)) {
                branchNameColumn = branchNameColumn.trim().replace("\n", " ").toLowerCase();
                if (!branchNameColumn.contains("branch") && !branchNameColumn.contains("name")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Branch Name Column", ENTITY_NAME, "fileInvalid");

                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Branch Name Column", ENTITY_NAME, "fileInvalid");

            }

            String branchCodeColumn = getCellValue(row.getCell(4));
            if (StringUtils.isNotBlank(branchCodeColumn)) {
                branchCodeColumn = branchCodeColumn.trim().replace("\n", " ").toLowerCase();
                if (!branchCodeColumn.contains("branch") && !branchCodeColumn.contains("code")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Branch Code Column", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Branch Code Column", ENTITY_NAME, "fileInvalid");

            }

            String kccBranchCodeColumn = getCellValue(row.getCell(5));
            if (StringUtils.isNotBlank(kccBranchCodeColumn)) {
                kccBranchCodeColumn = kccBranchCodeColumn.trim().replace("\n", " ").toLowerCase();
                if (!kccBranchCodeColumn.contains("kcc") && !kccBranchCodeColumn.contains("branch")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing KCC Branch Column", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing KCC Branch Column", ENTITY_NAME, "fileInvalid");

            }
            String ifscCodeColumn = getCellValue(row.getCell(6));
            if (StringUtils.isNotBlank(ifscCodeColumn)) {
                ifscCodeColumn = ifscCodeColumn.trim().replace("\n", " ").toLowerCase();
                if (!ifscCodeColumn.contains("ifsc")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Ifsc Column", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Ifsc Column", ENTITY_NAME, "fileInvalid");
            }
            String loanAccountNumberKcc = getCellValue(row.getCell(7));
            if (StringUtils.isNotBlank(loanAccountNumberKcc)) {
                loanAccountNumberKcc = loanAccountNumberKcc.trim().replace("\n", " ").toLowerCase();
                if (!loanAccountNumberKcc.contains("loan") && !loanAccountNumberKcc.contains("account")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Loan Account Column", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Loan Account Column", ENTITY_NAME, "fileInvalid");
            }
            String farmerName = getCellValue(row.getCell(8));
            if (StringUtils.isNotBlank(farmerName)) {
                farmerName = farmerName.trim().replace("\n", " ").toLowerCase();
                if (!farmerName.contains("farmer") && !farmerName.contains("name")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Farmer Name Column", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Farmer Name Column ", ENTITY_NAME, "fileInvalid");
            }
            String gender = getCellValue(row.getCell(9));
            if (StringUtils.isNotBlank(gender)) {
                gender = gender.trim().replace("\n", " ").toLowerCase();
                if (!gender.contains("gender")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Gender Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Gender Column ", ENTITY_NAME, "fileInvalid");
            }

            String aadharNumber = getCellValue(row.getCell(10));
            if (StringUtils.isNotBlank(aadharNumber)) {
                aadharNumber = aadharNumber.trim().replace("\n", " ").toLowerCase();
                if (!aadharNumber.contains("aadhar") && !aadharNumber.contains("number")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Aadhar Number Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Aadhar Number Column ", ENTITY_NAME, "fileInvalid");
            }
            String dateOfBirth = getCellValue(row.getCell(11));
            if (StringUtils.isNotBlank(dateOfBirth)) {
                dateOfBirth = dateOfBirth.trim().replace("\n", " ").toLowerCase();
                if (!dateOfBirth.contains("date") && !dateOfBirth.contains("birth")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Date of Birth Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Date of Birth Column ", ENTITY_NAME, "fileInvalid");
            }
            String AGE_AT_THE_TIME_OF_SANCTION = getCellValue(row.getCell(12));
            if (StringUtils.isNotBlank(AGE_AT_THE_TIME_OF_SANCTION)) {
                AGE_AT_THE_TIME_OF_SANCTION = AGE_AT_THE_TIME_OF_SANCTION.trim().replace("\n", " ").toLowerCase();
                if (!AGE_AT_THE_TIME_OF_SANCTION.contains("age") && !AGE_AT_THE_TIME_OF_SANCTION.contains("sanction")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Age at sanction Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Age at sanction Column ", ENTITY_NAME, "fileInvalid");
            }
            String MOBILE_NUMBER = getCellValue(row.getCell(13));
            if (StringUtils.isNotBlank(MOBILE_NUMBER)) {
                MOBILE_NUMBER = MOBILE_NUMBER.trim().replace("\n", " ").toLowerCase();
                if (!MOBILE_NUMBER.contains("mobile") && !MOBILE_NUMBER.contains("number")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Mobile Number Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Mobile Number Column ", ENTITY_NAME, "fileInvalid");
            }
            String FARMERS_CATEGORY = getCellValue(row.getCell(14));
            if (StringUtils.isNotBlank(FARMERS_CATEGORY)) {
                FARMERS_CATEGORY = FARMERS_CATEGORY.trim().replace("\n", " ").toLowerCase();
                if (!FARMERS_CATEGORY.contains("farmers") && !FARMERS_CATEGORY.contains("category")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Mobile Number Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Mobile Number Column ", ENTITY_NAME, "fileInvalid");
            }
            String FARMER_TYPE = getCellValue(row.getCell(15));
            if (StringUtils.isNotBlank(FARMER_TYPE)) {
                FARMER_TYPE = FARMER_TYPE.trim().replace("\n", " ").toLowerCase();
                if (!FARMER_TYPE.contains("farmer") && !FARMER_TYPE.contains("type")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Farmer Type Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Farmer Type Column ", ENTITY_NAME, "fileInvalid");
            }
            String SOCIAL_CATEGORY = getCellValue(row.getCell(16));
            if (StringUtils.isNotBlank(SOCIAL_CATEGORY)) {
                SOCIAL_CATEGORY = SOCIAL_CATEGORY.trim().replace("\n", " ").toLowerCase();
                if (!SOCIAL_CATEGORY.contains("social") && !SOCIAL_CATEGORY.contains("category")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Social Category Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Social Category Column ", ENTITY_NAME, "fileInvalid");
            }
            String RELATIVE_TYPE = getCellValue(row.getCell(17));
            if (StringUtils.isNotBlank(RELATIVE_TYPE)) {
                RELATIVE_TYPE = RELATIVE_TYPE.trim().replace("\n", " ").toLowerCase();
                if (!RELATIVE_TYPE.contains("relative") && !RELATIVE_TYPE.contains("type")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Relative Type Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Relative Type Column ", ENTITY_NAME, "fileInvalid");
            }
            String RELATIVE_NAME = getCellValue(row.getCell(18));
            if (StringUtils.isNotBlank(RELATIVE_NAME)) {
                RELATIVE_NAME = RELATIVE_NAME.trim().replace("\n", " ").toLowerCase();
                if (!RELATIVE_NAME.contains("relative") && !RELATIVE_NAME.contains("name")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Relative Name Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Relative Name Column ", ENTITY_NAME, "fileInvalid");
            }
            String STATE_NAME = getCellValue(row.getCell(19));
            if (StringUtils.isNotBlank(STATE_NAME)) {
                STATE_NAME = STATE_NAME.trim().replace("\n", " ").toLowerCase();
                if (!STATE_NAME.contains("state") && !STATE_NAME.contains("name")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing State Name Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing State Name Column ", ENTITY_NAME, "fileInvalid");
            }
            String STATE_CODE = getCellValue(row.getCell(20));
            if (StringUtils.isNotBlank(STATE_CODE)) {
                STATE_CODE = STATE_CODE.trim().replace("\n", " ").toLowerCase();
                if (!STATE_CODE.contains("state") && !STATE_CODE.contains("code")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing State Code Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing State Code Column ", ENTITY_NAME, "fileInvalid");
            }
            String DISTRICT_NAME = getCellValue(row.getCell(21));
            if (StringUtils.isNotBlank(DISTRICT_NAME)) {
                DISTRICT_NAME = DISTRICT_NAME.trim().replace("\n", " ").toLowerCase();
                if (!DISTRICT_NAME.contains("district") && !DISTRICT_NAME.contains("name")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing District Name Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing District Name Column ", ENTITY_NAME, "fileInvalid");
            }
            String DISTRICT_CODE = getCellValue(row.getCell(22));
            if (StringUtils.isNotBlank(DISTRICT_CODE)) {
                DISTRICT_CODE = DISTRICT_CODE.trim().replace("\n", " ").toLowerCase();
                if (!DISTRICT_CODE.contains("district") && !DISTRICT_CODE.contains("code")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing District Code Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing District Code Column ", ENTITY_NAME, "fileInvalid");
            }
            String BLOCK_CODE = getCellValue(row.getCell(23));
            if (StringUtils.isNotBlank(BLOCK_CODE)) {
                BLOCK_CODE = BLOCK_CODE.trim().replace("\n", " ").toLowerCase();
                if (!BLOCK_CODE.contains("block") && !BLOCK_CODE.contains("code")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Block Code Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Block Code Column ", ENTITY_NAME, "fileInvalid");
            }
            String BLOCK_NAME = getCellValue(row.getCell(24));
            if (StringUtils.isNotBlank(BLOCK_NAME)) {
                BLOCK_NAME = BLOCK_NAME.trim().replace("\n", " ").toLowerCase();
                if (!BLOCK_NAME.contains("block") && !BLOCK_NAME.contains("name")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Block Code Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Block Code Column ", ENTITY_NAME, "fileInvalid");
            }
            String VILLAGE_CODE = getCellValue(row.getCell(25));
            if (StringUtils.isNotBlank(VILLAGE_CODE)) {
                VILLAGE_CODE = VILLAGE_CODE.trim().replace("\n", " ").toLowerCase();
                if (!VILLAGE_CODE.contains("village") && !VILLAGE_CODE.contains("code")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Village Code Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Village Code Column ", ENTITY_NAME, "fileInvalid");
            }
            String VILLAGE_NAME = getCellValue(row.getCell(26));
            if (StringUtils.isNotBlank(VILLAGE_NAME)) {
                VILLAGE_NAME = VILLAGE_NAME.trim().replace("\n", " ").toLowerCase();
                if (!VILLAGE_NAME.contains("village") && !VILLAGE_NAME.contains("name")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Village Name Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Village Name Column ", ENTITY_NAME, "fileInvalid");
            }
            String ADDRESS = getCellValue(row.getCell(27));
            if (StringUtils.isNotBlank(ADDRESS)) {
                ADDRESS = ADDRESS.trim().replace("\n", " ").toLowerCase();
                if (!ADDRESS.contains("address")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Address Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Address Column ", ENTITY_NAME, "fileInvalid");
            }
            String PIN_CODE = getCellValue(row.getCell(28));
            if (StringUtils.isNotBlank(PIN_CODE)) {
                PIN_CODE = PIN_CODE.trim().replace("\n", " ").toLowerCase();
                if (!PIN_CODE.contains("pin") && !PIN_CODE.contains("code")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Pin Code Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Pin Code Column ", ENTITY_NAME, "fileInvalid");
            }
            String Account_TYPE = getCellValue(row.getCell(29));
            if (StringUtils.isNotBlank(Account_TYPE)) {
                Account_TYPE = Account_TYPE.trim().replace("\n", " ").toLowerCase();
                if (!Account_TYPE.contains("account") && !Account_TYPE.contains("type")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Account Type Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Account Type Column ", ENTITY_NAME, "fileInvalid");
            }
            String ACCOUNT_NUMBER = getCellValue(row.getCell(30));
            if (StringUtils.isNotBlank(ACCOUNT_NUMBER)) {
                ACCOUNT_NUMBER = ACCOUNT_NUMBER.trim().replace("\n", " ").toLowerCase();
                if (!ACCOUNT_NUMBER.contains("account") && !ACCOUNT_NUMBER.contains("number")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Account Number Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Account Number Column ", ENTITY_NAME, "fileInvalid");
            }
            String PACS_NAME = getCellValue(row.getCell(31));
            if (StringUtils.isNotBlank(PACS_NAME)) {
                PACS_NAME = PACS_NAME.trim().replace("\n", " ").toLowerCase();
                if (!PACS_NAME.contains("pacs") && !PACS_NAME.contains("name")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Pacs Name Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Pacs Name Column ", ENTITY_NAME, "fileInvalid");
            }
            String PACS_NUMBER = getCellValue(row.getCell(32));
            if (StringUtils.isNotBlank(PACS_NUMBER)) {
                PACS_NUMBER = PACS_NUMBER.trim().replace("\n", " ").toLowerCase();
                if (!PACS_NUMBER.contains("pacs") && !PACS_NUMBER.contains("number")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Pacs Number Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Pacs Number Column ", ENTITY_NAME, "fileInvalid");
            }
            String ACCOUNT_HOLDER_TYPE = getCellValue(row.getCell(33));
            if (StringUtils.isNotBlank(ACCOUNT_HOLDER_TYPE)) {
                ACCOUNT_HOLDER_TYPE = ACCOUNT_HOLDER_TYPE.trim().replace("\n", " ").toLowerCase();
                if (!ACCOUNT_HOLDER_TYPE.contains("account") && !ACCOUNT_HOLDER_TYPE.contains("holder")) {
                    //  flag = true;
                    throw new BadRequestAlertException("Missing Account Holder Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Account Holder Column ", ENTITY_NAME, "fileInvalid");
            }
            String PRIMARY_OCCUPATION = getCellValue(row.getCell(34));
            if (StringUtils.isNotBlank(PRIMARY_OCCUPATION)) {
                PRIMARY_OCCUPATION = PRIMARY_OCCUPATION.trim().replace("\n", " ").toLowerCase();
                if (!PRIMARY_OCCUPATION.contains("primary") && !PRIMARY_OCCUPATION.contains("occupation")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Primary Occupation Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Primary Occupation Column ", ENTITY_NAME, "fileInvalid");
            }
            String LOAN_SANCTION_DATE = getCellValue(row.getCell(35));
            if (StringUtils.isNotBlank(LOAN_SANCTION_DATE)) {
                LOAN_SANCTION_DATE = LOAN_SANCTION_DATE.trim().replace("\n", " ").toLowerCase();
                if (!LOAN_SANCTION_DATE.contains("loan") && !LOAN_SANCTION_DATE.contains("date")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Loan Date Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //  flag = true;
                throw new BadRequestAlertException("Missing Loan Date Column ", ENTITY_NAME, "fileInvalid");
            }
            String LOAN_SANCTION_AMOUNT = getCellValue(row.getCell(36));
            if (StringUtils.isNotBlank(LOAN_SANCTION_AMOUNT)) {
                LOAN_SANCTION_AMOUNT = LOAN_SANCTION_AMOUNT.trim().replace("\n", " ").toLowerCase();
                if (!LOAN_SANCTION_AMOUNT.contains("loan") && !LOAN_SANCTION_AMOUNT.contains("amount")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Loan Amount Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Loan Amount Column ", ENTITY_NAME, "fileInvalid");
            }
            String TENURE_OF_LOAN = getCellValue(row.getCell(37));
            if (StringUtils.isNotBlank(TENURE_OF_LOAN)) {
                TENURE_OF_LOAN = TENURE_OF_LOAN.trim().replace("\n", " ").toLowerCase();
                if (!TENURE_OF_LOAN.contains("tenure") && !TENURE_OF_LOAN.contains("loan")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Tenure Loan Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Tenure Loan Column ", ENTITY_NAME, "fileInvalid");
            }
            String DATE_OF_OVERDUE_PAYMENT = getCellValue(row.getCell(38));
            if (StringUtils.isNotBlank(DATE_OF_OVERDUE_PAYMENT)) {
                DATE_OF_OVERDUE_PAYMENT = DATE_OF_OVERDUE_PAYMENT.trim().replace("\n", " ").toLowerCase();
                if (!DATE_OF_OVERDUE_PAYMENT.contains("date") && !DATE_OF_OVERDUE_PAYMENT.contains("overdue")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Date Overdue Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Date Overdue Column ", ENTITY_NAME, "fileInvalid");
            }
            String KCCISS_CROP_CODE = getCellValue(row.getCell(39));
            if (StringUtils.isNotBlank(KCCISS_CROP_CODE)) {
                KCCISS_CROP_CODE = KCCISS_CROP_CODE.trim().replace("\n", " ").toLowerCase();
                if (!KCCISS_CROP_CODE.contains("crop") && !KCCISS_CROP_CODE.contains("code")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Crop Code Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Crop Code Column ", ENTITY_NAME, "fileInvalid");
            }
            String KCCISS_CROP_NAME = getCellValue(row.getCell(40));
            if (StringUtils.isNotBlank(KCCISS_CROP_NAME)) {
                KCCISS_CROP_NAME = KCCISS_CROP_NAME.trim().replace("\n", " ").toLowerCase();
                if (!KCCISS_CROP_NAME.contains("crop") && !KCCISS_CROP_NAME.contains("name")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Crop Name Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Crop Name Column ", ENTITY_NAME, "fileInvalid");
            }
            String CROP_NAME = getCellValue(row.getCell(41));
            if (StringUtils.isNotBlank(CROP_NAME)) {
                CROP_NAME = CROP_NAME.trim().replace("\n", " ").toLowerCase();
                if (!CROP_NAME.contains("crop") && !CROP_NAME.contains("name")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Crop Name Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Crop Name Column ", ENTITY_NAME, "fileInvalid");
            }

            String SURVEY_NO = getCellValue(row.getCell(42));
            if (StringUtils.isNoneBlank(SURVEY_NO)) {
                SURVEY_NO = SURVEY_NO.trim().replace("\n", " ").toLowerCase();

                if (!SURVEY_NO.contains("survey") && !SURVEY_NO.contains("no")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Survey No Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Survey No Column ", ENTITY_NAME, "fileInvalid");
            }
            String SAT_BARA_SUBSURVEY_No = getCellValue(row.getCell(43));
            if (StringUtils.isNoneBlank(SAT_BARA_SUBSURVEY_No)) {
                SAT_BARA_SUBSURVEY_No = SAT_BARA_SUBSURVEY_No.trim().replace("\n", " ").toLowerCase();

                if (!SAT_BARA_SUBSURVEY_No.contains("sat") && !SAT_BARA_SUBSURVEY_No.contains("bara")) {
                    //  flag = true;
                    throw new BadRequestAlertException("Missing Sat bara Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Sat bara Column ", ENTITY_NAME, "fileInvalid");
            }

            String SEASON_NAME = getCellValue(row.getCell(44));
            if (StringUtils.isNoneBlank(SEASON_NAME)) {
                SEASON_NAME = SEASON_NAME.trim().replace("\n", " ").toLowerCase();

                if (!SEASON_NAME.contains("season") && !SEASON_NAME.contains("name")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Season Name Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Season Name Column ", ENTITY_NAME, "fileInvalid");
            }

            String ACTIVITY_TYPE = getCellValue(row.getCell(45));
            if (StringUtils.isNoneBlank(ACTIVITY_TYPE)) {
                ACTIVITY_TYPE = ACTIVITY_TYPE.trim().replace("\n", " ").toLowerCase();

                if (!ACTIVITY_TYPE.contains("activity") && !ACTIVITY_TYPE.contains("type")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Activity Type Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Activity Type Column ", ENTITY_NAME, "fileInvalid");
            }

            String AREA_HECT = getCellValue(row.getCell(46));
            if (StringUtils.isNoneBlank(AREA_HECT)) {
                AREA_HECT = AREA_HECT.trim().replace("\n", " ").toLowerCase();

                if (!AREA_HECT.contains("area") && !AREA_HECT.contains("hect")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Area Hect Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Area Hect Column ", ENTITY_NAME, "fileInvalid");
            }

            String LAND_TYPE = getCellValue(row.getCell(47));
            if (StringUtils.isNoneBlank(LAND_TYPE)) {
                LAND_TYPE = LAND_TYPE.trim().replace("\n", " ").toLowerCase();

                if (!LAND_TYPE.contains("land") && !LAND_TYPE.contains("type")) {
                    //flag = true;
                    throw new BadRequestAlertException("Missing Land Type Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Land Type Column ", ENTITY_NAME, "fileInvalid");
            }
            String DISBURSEMENT_DATE = getCellValue(row.getCell(48));
            if (StringUtils.isNoneBlank(DISBURSEMENT_DATE)) {
                DISBURSEMENT_DATE = DISBURSEMENT_DATE.trim().replace("\n", " ").toLowerCase();

                if (!DISBURSEMENT_DATE.contains("disbursement") && !DISBURSEMENT_DATE.contains("date")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Disbursement Date Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Disbursement Date Column ", ENTITY_NAME, "fileInvalid");
            }

            String DISBURSE_AMOUNT = getCellValue(row.getCell(49));
            if (StringUtils.isNoneBlank(DISBURSE_AMOUNT)) {
                DISBURSE_AMOUNT = DISBURSE_AMOUNT.trim().replace("\n", " ").toLowerCase();

                if (!DISBURSE_AMOUNT.contains("disburse") && !DISBURSE_AMOUNT.contains("amount")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Disbursement amount Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Disbursement amount Column ", ENTITY_NAME, "fileInvalid");
            }

            String MATURITY_LOAN_DATE = getCellValue(row.getCell(50));
            if (StringUtils.isNoneBlank(MATURITY_LOAN_DATE)) {
                MATURITY_LOAN_DATE = MATURITY_LOAN_DATE.trim().replace("\n", " ").toLowerCase();

                if (!MATURITY_LOAN_DATE.contains("maturity") && !MATURITY_LOAN_DATE.contains("date")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Maturity Date Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Maturity Date Column ", ENTITY_NAME, "fileInvalid");
            }
            String RECOVERY_AMOUNT_PRINCIPLE = getCellValue(row.getCell(51));
            if (StringUtils.isNoneBlank(RECOVERY_AMOUNT_PRINCIPLE)) {
                RECOVERY_AMOUNT_PRINCIPLE = RECOVERY_AMOUNT_PRINCIPLE.trim().replace("\n", " ").toLowerCase();

                if (!RECOVERY_AMOUNT_PRINCIPLE.contains("recovery") && !RECOVERY_AMOUNT_PRINCIPLE.contains("amount")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Recovery Amount Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Recovery Amount Column ", ENTITY_NAME, "fileInvalid");
            }
            String RECOVERY_AMOUNT_INTEREST = getCellValue(row.getCell(52));
            if (StringUtils.isNoneBlank(RECOVERY_AMOUNT_INTEREST)) {
                RECOVERY_AMOUNT_INTEREST = RECOVERY_AMOUNT_INTEREST.trim().replace("\n", " ").toLowerCase();

                if (!RECOVERY_AMOUNT_INTEREST.contains("recovery") && !RECOVERY_AMOUNT_INTEREST.contains("interest")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Recovery interest Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                //flag = true;
                throw new BadRequestAlertException("Missing Recovery interest Column ", ENTITY_NAME, "fileInvalid");
            }

            String recoveryDate = getCellValue(row.getCell(53));
            if (StringUtils.isNoneBlank(recoveryDate)) {
                recoveryDate = recoveryDate.trim().replace("\n", " ").toLowerCase();

                if (!recoveryDate.contains("recovery") && !recoveryDate.contains("date")) {
                    // flag = true;
                    throw new BadRequestAlertException("Missing Recovery Date Column ", ENTITY_NAME, "fileInvalid");
                }
            } else {
                // flag = true;
                throw new BadRequestAlertException("Missing Recovery Date Column ", ENTITY_NAME, "fileInvalid");
            }

            if (flag) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }

            row = sheet.getRow(filecount + 1);
            String bankCode = getCellValue(row.getCell(2));
            String schemeWiseBranchCode = getCellValue(row.getCell(5));
            String packsCode = getCellValue(row.getCell(32));

            if (StringUtils.isBlank(bankCode) || StringUtils.isBlank(schemeWiseBranchCode) || StringUtils.isBlank(packsCode)) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            } else if (
                StringUtils.isNotBlank(bankCode) && StringUtils.isNotBlank(schemeWiseBranchCode) && StringUtils.isNotBlank(packsCode)
            ) {
                if (!bankCode.matches("\\d+") && !schemeWiseBranchCode.matches("\\d+") && !packsCode.matches("\\d+")) {
                    throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
                }
            }

            //checking if user is authenticated or not
            rbaControl.authenticateByCode(bankCode, schemeWiseBranchCode, packsCode, ENTITY_NAME);

            //checking 1st file column data
            boolean flagForData = false;
            String fYear = getCellValue(row.getCell(0));


            if (StringUtils.isBlank(fYear)) {
                flagForData = true;
            } else if (StringUtils.isNotBlank(fYear) && !fYear.matches("\\d{4}/\\d{4}") && !fYear.matches("\\d{4}-\\d{4}")) {
                flagForData = true;
            }


            String bankNameValue = getCellValue(row.getCell(1));
            if (StringUtils.isNotBlank(bankNameValue) && !bankNameValue.equalsIgnoreCase("PUNE DISTRICT CENTRAL CO.OP BANK LTD")) {
                flagForData = true;
            }

            String bankCodeValue = getCellValue(row.getCell(2));
            if (StringUtils.isNotBlank(bankCodeValue) && !bankCodeValue.matches("\\d+")) {
                flagForData = true;
            }


            String branchNameValue = getCellValue(row.getCell(3));
            if (StringUtils.isNotBlank(branchNameValue)) {

                if (bankBranchMasterRepository.findOneByBranchName(branchNameValue) == null) {
                    flagForData = true;
                }

            }

            String bankBranchCode = getCellValue(row.getCell(4));
            if (StringUtils.isNotBlank(bankBranchCode) && !bankBranchCode.matches("\\d+")) {
                flagForData = true;
            }


            String kccIssBranchCode = getCellValue(row.getCell(5));
            if (StringUtils.isBlank(kccIssBranchCode)) {
                flagForData = true;
            }
            if (StringUtils.isNotBlank(kccIssBranchCode) && !kccIssBranchCode.matches("\\d+")) {
                flagForData = true;
            }


            String ifsc = getCellValue(row.getCell(6));
            if (StringUtils.isNotBlank(ifsc) && !ifsc.matches("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$")) {
                flagForData = true;
            }

            String aadharNumberValue = getCellValue(row.getCell(10));
            if (StringUtils.isNotBlank(aadharNumberValue) && !validateAadhaarNumber(aadharNumberValue)) {
                flagForData = true;
            }

            String villageCode = getCellValue(row.getCell(25));
            if (StringUtils.isNotBlank(villageCode) && !villageCode.matches("^[0-9.]+$") && !villageCode.matches("^[0-9]+$")) {
                flagForData = true;
            }
            String pinCode = getCellValue(row.getCell(28));
            if (StringUtils.isNotBlank(pinCode) && !pinCode.matches("^[0-9]{6}$")) {
                flagForData = true;
            }

            String accountNumber = getCellValue(row.getCell(30));
            if (StringUtils.isNotBlank(accountNumber) && !pinCode.matches("\\d+")) {
                flagForData = true;
            }
            String landtype = getCellValue(row.getCell(47));
            if (
                StringUtils.isNotBlank(landtype) && !landtype.equalsIgnoreCase("irrigated") && !landtype.equalsIgnoreCase("non-irrigated")
            ) {
                flagForData = true;
            }

            if (flagForData) {
                throw new BadRequestAlertException("Invalid file Or File does not contain data in correct format", ENTITY_NAME, "fileInvalid");
            }

            fileParseConf.setBankName(getCellValue(row.getCell(1)));
            fileParseConf.setBankCode(bankCode);
            fileParseConf.setBranchName(getCellValue(row.getCell(3)));
            fileParseConf.setBranchCode(getCellValue(row.getCell(4)));
            fileParseConf.setSchemeWiseBranchCode(schemeWiseBranchCode);
            fileParseConf.setPacsName(getCellValue(row.getCell(31)));
            fileParseConf.setPacsCode(packsCode);
            return fileParseConf;
        } catch (BadRequestAlertException e) {
            e.printStackTrace();
            throw e;
            //throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (ForbiddenAuthRequestAlertException e) {
            throw new ForbiddenAuthRequestAlertException("Unauthorized Operation", ENTITY_NAME, "unAuthorized");
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
        if (!"xlsx".equalsIgnoreCase(fileExtension) && !"xls".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        if (issPortalFileRepository.existsByFileNameAndFinancialYear(files.getOriginalFilename(), financialYear)) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }
        int filecount = 0;
        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet

            Row row = null;
            for (int i = 0; i < 10; i++) {
                row = sheet.getRow(i); // Get the current row
                String financialYearInFile = getCellValue(row.getCell(0));
                if (StringUtils.isNotBlank(financialYearInFile)) {
                    financialYearInFile = financialYearInFile.trim().replace("\n", " ").toLowerCase();
                    if (financialYearInFile.contains("financial") && financialYearInFile.contains("year")) {
                        filecount = i;
                        break;
                    }
                }
            }

            row = sheet.getRow(filecount); // Get the current row
            boolean flagForLabel = false;

            String financialYearInFileConf = getCellValue(row.getCell(0));
            if (StringUtils.isNotBlank(financialYearInFileConf)) {
                financialYearInFileConf = financialYearInFileConf.trim().replace("\n", " ").toLowerCase();
                if (!financialYearInFileConf.contains("financial") && !financialYearInFileConf.contains("year")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            // validating excel heading column name
            String bankName = getCellValue(row.getCell(1));
            if (StringUtils.isNotBlank(bankName)) {
                bankName = bankName.trim().replace("\n", " ").toLowerCase();
                if (!bankName.contains("bank") && !bankName.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String bankCodeColumn = getCellValue(row.getCell(2));
            if (StringUtils.isNotBlank(bankCodeColumn)) {
                bankCodeColumn = bankCodeColumn.trim().replace("\n", " ").toLowerCase();
                if (!bankCodeColumn.contains("bank") && !bankCodeColumn.contains("code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String branchNameColumn = getCellValue(row.getCell(3));
            if (StringUtils.isNotBlank(branchNameColumn)) {
                branchNameColumn = branchNameColumn.trim().replace("\n", " ").toLowerCase();
                if (!branchNameColumn.contains("branch") && !branchNameColumn.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String branchCodeColumn = getCellValue(row.getCell(4));
            if (StringUtils.isNotBlank(branchCodeColumn)) {
                branchCodeColumn = branchCodeColumn.trim().replace("\n", " ").toLowerCase();
                if (!branchCodeColumn.contains("branch") && !branchCodeColumn.contains("code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String kccBranchCodeColumn = getCellValue(row.getCell(5));
            if (StringUtils.isNotBlank(kccBranchCodeColumn)) {
                kccBranchCodeColumn = kccBranchCodeColumn.trim().replace("\n", " ").toLowerCase();
                if (!kccBranchCodeColumn.contains("kcc") && !kccBranchCodeColumn.contains("branch")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String ifscCodeColumn = getCellValue(row.getCell(6));
            if (StringUtils.isNotBlank(ifscCodeColumn)) {
                ifscCodeColumn = ifscCodeColumn.trim().replace("\n", " ").toLowerCase();
                if (!ifscCodeColumn.contains("ifsc")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String loanAccountNumberKcc = getCellValue(row.getCell(7));
            if (StringUtils.isNotBlank(loanAccountNumberKcc)) {
                loanAccountNumberKcc = loanAccountNumberKcc.trim().replace("\n", " ").toLowerCase();
                if (!loanAccountNumberKcc.contains("loan") && !loanAccountNumberKcc.contains("account")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String farmerName = getCellValue(row.getCell(8));
            if (StringUtils.isNotBlank(farmerName)) {
                farmerName = farmerName.trim().replace("\n", " ").toLowerCase();
                if (!farmerName.contains("farmer") && !farmerName.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String gender = getCellValue(row.getCell(9));
            if (StringUtils.isNotBlank(gender)) {
                gender = gender.trim().replace("\n", " ").toLowerCase();
                if (!gender.contains("gender")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String aadharNumber = getCellValue(row.getCell(10));
            if (StringUtils.isNotBlank(aadharNumber)) {
                aadharNumber = aadharNumber.trim().replace("\n", " ").toLowerCase();
                if (!aadharNumber.contains("aadhar") && !aadharNumber.contains("number")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String dateOfBirth = getCellValue(row.getCell(11));
            if (StringUtils.isNotBlank(dateOfBirth)) {
                dateOfBirth = dateOfBirth.trim().replace("\n", " ").toLowerCase();
                if (!dateOfBirth.contains("date") && !dateOfBirth.contains("birth")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String AGE_AT_THE_TIME_OF_SANCTION = getCellValue(row.getCell(12));
            if (StringUtils.isNotBlank(AGE_AT_THE_TIME_OF_SANCTION)) {
                AGE_AT_THE_TIME_OF_SANCTION = AGE_AT_THE_TIME_OF_SANCTION.trim().replace("\n", " ").toLowerCase();
                if (!AGE_AT_THE_TIME_OF_SANCTION.contains("age") && !AGE_AT_THE_TIME_OF_SANCTION.contains("sanction")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String MOBILE_NUMBER = getCellValue(row.getCell(13));
            if (StringUtils.isNotBlank(MOBILE_NUMBER)) {
                MOBILE_NUMBER = MOBILE_NUMBER.trim().replace("\n", " ").toLowerCase();
                if (!MOBILE_NUMBER.contains("mobile") && !MOBILE_NUMBER.contains("number")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String FARMERS_CATEGORY = getCellValue(row.getCell(14));
            if (StringUtils.isNotBlank(FARMERS_CATEGORY)) {
                FARMERS_CATEGORY = FARMERS_CATEGORY.trim().replace("\n", " ").toLowerCase();
                if (!FARMERS_CATEGORY.contains("farmers") && !FARMERS_CATEGORY.contains("category")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String FARMER_TYPE = getCellValue(row.getCell(15));
            if (StringUtils.isNotBlank(FARMER_TYPE)) {
                FARMER_TYPE = FARMER_TYPE.trim().replace("\n", " ").toLowerCase();
                if (!FARMER_TYPE.contains("farmer") && !FARMER_TYPE.contains("type")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String SOCIAL_CATEGORY = getCellValue(row.getCell(16));
            if (StringUtils.isNotBlank(SOCIAL_CATEGORY)) {
                SOCIAL_CATEGORY = SOCIAL_CATEGORY.trim().replace("\n", " ").toLowerCase();
                if (!SOCIAL_CATEGORY.contains("social") && !SOCIAL_CATEGORY.contains("category")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String RELATIVE_TYPE = getCellValue(row.getCell(17));
            if (StringUtils.isNotBlank(RELATIVE_TYPE)) {
                RELATIVE_TYPE = RELATIVE_TYPE.trim().replace("\n", " ").toLowerCase();
                if (!RELATIVE_TYPE.contains("relative") && !RELATIVE_TYPE.contains("type")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String RELATIVE_NAME = getCellValue(row.getCell(18));
            if (StringUtils.isNotBlank(RELATIVE_NAME)) {
                RELATIVE_NAME = RELATIVE_NAME.trim().replace("\n", " ").toLowerCase();
                if (!RELATIVE_NAME.contains("relative") && !RELATIVE_NAME.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String STATE_NAME = getCellValue(row.getCell(19));
            if (StringUtils.isNotBlank(STATE_NAME)) {
                STATE_NAME = STATE_NAME.trim().replace("\n", " ").toLowerCase();
                if (!STATE_NAME.contains("state") && !STATE_NAME.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String STATE_CODE = getCellValue(row.getCell(20));
            if (StringUtils.isNotBlank(STATE_CODE)) {
                STATE_CODE = STATE_CODE.trim().replace("\n", " ").toLowerCase();
                if (!STATE_CODE.contains("state") && !STATE_CODE.contains("code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String DISTRICT_NAME = getCellValue(row.getCell(21));
            if (StringUtils.isNotBlank(DISTRICT_NAME)) {
                DISTRICT_NAME = DISTRICT_NAME.trim().replace("\n", " ").toLowerCase();
                if (!DISTRICT_NAME.contains("district") && !DISTRICT_NAME.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String DISTRICT_CODE = getCellValue(row.getCell(22));
            if (StringUtils.isNotBlank(DISTRICT_CODE)) {
                DISTRICT_CODE = DISTRICT_CODE.trim().replace("\n", " ").toLowerCase();
                if (!DISTRICT_CODE.contains("district") && !DISTRICT_CODE.contains("code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String BLOCK_CODE = getCellValue(row.getCell(23));
            if (StringUtils.isNotBlank(BLOCK_CODE)) {
                BLOCK_CODE = BLOCK_CODE.trim().replace("\n", " ").toLowerCase();
                if (!BLOCK_CODE.contains("block") && !BLOCK_CODE.contains("code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String BLOCK_NAME = getCellValue(row.getCell(24));
            if (StringUtils.isNotBlank(BLOCK_NAME)) {
                BLOCK_NAME = BLOCK_NAME.trim().replace("\n", " ").toLowerCase();
                if (!BLOCK_NAME.contains("block") && !BLOCK_NAME.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String VILLAGE_CODE = getCellValue(row.getCell(25));
            if (StringUtils.isNotBlank(VILLAGE_CODE)) {
                VILLAGE_CODE = VILLAGE_CODE.trim().replace("\n", " ").toLowerCase();
                if (!VILLAGE_CODE.contains("village") && !VILLAGE_CODE.contains("code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String VILLAGE_NAME = getCellValue(row.getCell(26));
            if (StringUtils.isNotBlank(VILLAGE_NAME)) {
                VILLAGE_NAME = VILLAGE_NAME.trim().replace("\n", " ").toLowerCase();
                if (!VILLAGE_NAME.contains("village") && !VILLAGE_NAME.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String ADDRESS = getCellValue(row.getCell(27));
            if (StringUtils.isNotBlank(ADDRESS)) {
                ADDRESS = ADDRESS.trim().replace("\n", " ").toLowerCase();
                if (!ADDRESS.contains("address")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String PIN_CODE = getCellValue(row.getCell(28));
            if (StringUtils.isNotBlank(PIN_CODE)) {
                PIN_CODE = PIN_CODE.trim().replace("\n", " ").toLowerCase();
                if (!PIN_CODE.contains("pin") && !PIN_CODE.contains("code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String Account_TYPE = getCellValue(row.getCell(29));
            if (StringUtils.isNotBlank(Account_TYPE)) {
                Account_TYPE = Account_TYPE.trim().replace("\n", " ").toLowerCase();
                if (!Account_TYPE.contains("account") && !Account_TYPE.contains("type")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String ACCOUNT_NUMBER = getCellValue(row.getCell(30));
            if (StringUtils.isNotBlank(ACCOUNT_NUMBER)) {
                ACCOUNT_NUMBER = ACCOUNT_NUMBER.trim().replace("\n", " ").toLowerCase();
                if (!ACCOUNT_NUMBER.contains("account") && !ACCOUNT_NUMBER.contains("number")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String PACS_NAME = getCellValue(row.getCell(31));
            if (StringUtils.isNotBlank(PACS_NAME)) {
                PACS_NAME = PACS_NAME.trim().replace("\n", " ").toLowerCase();
                if (!PACS_NAME.contains("pacs") && !PACS_NAME.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String PACS_NUMBER = getCellValue(row.getCell(32));
            if (StringUtils.isNotBlank(PACS_NUMBER)) {
                PACS_NUMBER = PACS_NUMBER.trim().replace("\n", " ").toLowerCase();
                if (!PACS_NUMBER.contains("pacs") && !PACS_NUMBER.contains("number")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String ACCOUNT_HOLDER_TYPE = getCellValue(row.getCell(33));
            if (StringUtils.isNotBlank(ACCOUNT_HOLDER_TYPE)) {
                ACCOUNT_HOLDER_TYPE = ACCOUNT_HOLDER_TYPE.trim().replace("\n", " ").toLowerCase();
                if (!ACCOUNT_HOLDER_TYPE.contains("account") && !ACCOUNT_HOLDER_TYPE.contains("holder")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String PRIMARY_OCCUPATION = getCellValue(row.getCell(34));
            if (StringUtils.isNotBlank(PRIMARY_OCCUPATION)) {
                PRIMARY_OCCUPATION = PRIMARY_OCCUPATION.trim().replace("\n", " ").toLowerCase();
                if (!PRIMARY_OCCUPATION.contains("primary") && !PRIMARY_OCCUPATION.contains("occupation")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String LOAN_SANCTION_DATE = getCellValue(row.getCell(35));
            if (StringUtils.isNotBlank(LOAN_SANCTION_DATE)) {
                LOAN_SANCTION_DATE = LOAN_SANCTION_DATE.trim().replace("\n", " ").toLowerCase();
                if (!LOAN_SANCTION_DATE.contains("loan") && !LOAN_SANCTION_DATE.contains("date")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String LOAN_SANCTION_AMOUNT = getCellValue(row.getCell(36));
            if (StringUtils.isNotBlank(LOAN_SANCTION_AMOUNT)) {
                LOAN_SANCTION_AMOUNT = LOAN_SANCTION_AMOUNT.trim().replace("\n", " ").toLowerCase();
                if (!LOAN_SANCTION_AMOUNT.contains("loan") && !LOAN_SANCTION_AMOUNT.contains("amount")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String TENURE_OF_LOAN = getCellValue(row.getCell(37));
            if (StringUtils.isNotBlank(TENURE_OF_LOAN)) {
                TENURE_OF_LOAN = TENURE_OF_LOAN.trim().replace("\n", " ").toLowerCase();
                if (!TENURE_OF_LOAN.contains("tenure") && !TENURE_OF_LOAN.contains("loan")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String DATE_OF_OVERDUE_PAYMENT = getCellValue(row.getCell(38));
            if (StringUtils.isNotBlank(DATE_OF_OVERDUE_PAYMENT)) {
                DATE_OF_OVERDUE_PAYMENT = DATE_OF_OVERDUE_PAYMENT.trim().replace("\n", " ").toLowerCase();
                if (!DATE_OF_OVERDUE_PAYMENT.contains("date") && !DATE_OF_OVERDUE_PAYMENT.contains("overdue")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String KCCISS_CROP_CODE = getCellValue(row.getCell(39));
            if (StringUtils.isNotBlank(KCCISS_CROP_CODE)) {
                KCCISS_CROP_CODE = KCCISS_CROP_CODE.trim().replace("\n", " ").toLowerCase();
                if (!KCCISS_CROP_CODE.contains("crop") && !KCCISS_CROP_CODE.contains("code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String KCCISS_CROP_NAME = getCellValue(row.getCell(40));
            if (StringUtils.isNotBlank(KCCISS_CROP_NAME)) {
                KCCISS_CROP_NAME = KCCISS_CROP_NAME.trim().replace("\n", " ").toLowerCase();
                if (!KCCISS_CROP_NAME.contains("crop") && !KCCISS_CROP_NAME.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String CROP_NAME = getCellValue(row.getCell(41));
            if (StringUtils.isNotBlank(CROP_NAME)) {
                CROP_NAME = CROP_NAME.trim().replace("\n", " ").toLowerCase();
                if (!CROP_NAME.contains("crop") && !CROP_NAME.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String SURVEY_NO = getCellValue(row.getCell(42));
            if (StringUtils.isNoneBlank(SURVEY_NO)) {
                SURVEY_NO = SURVEY_NO.trim().replace("\n", " ").toLowerCase();

                if (!SURVEY_NO.contains("survey") && !SURVEY_NO.contains("no")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String SAT_BARA_SUBSURVEY_No = getCellValue(row.getCell(43));
            if (StringUtils.isNoneBlank(SAT_BARA_SUBSURVEY_No)) {
                SAT_BARA_SUBSURVEY_No = SAT_BARA_SUBSURVEY_No.trim().replace("\n", " ").toLowerCase();

                if (!SAT_BARA_SUBSURVEY_No.contains("sat") && !SAT_BARA_SUBSURVEY_No.contains("bara")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String SEASON_NAME = getCellValue(row.getCell(44));
            if (StringUtils.isNoneBlank(SEASON_NAME)) {
                SEASON_NAME = SEASON_NAME.trim().replace("\n", " ").toLowerCase();

                if (!SEASON_NAME.contains("season") && !SEASON_NAME.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String ACTIVITY_TYPE = getCellValue(row.getCell(45));
            if (StringUtils.isNoneBlank(ACTIVITY_TYPE)) {
                ACTIVITY_TYPE = ACTIVITY_TYPE.trim().replace("\n", " ").toLowerCase();

                if (!ACTIVITY_TYPE.contains("activity") && !ACTIVITY_TYPE.contains("type")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String AREA_HECT = getCellValue(row.getCell(46));
            if (StringUtils.isNoneBlank(AREA_HECT)) {
                AREA_HECT = AREA_HECT.trim().replace("\n", " ").toLowerCase();

                if (!AREA_HECT.contains("area") && !AREA_HECT.contains("hect")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String LAND_TYPE = getCellValue(row.getCell(47));
            if (StringUtils.isNoneBlank(LAND_TYPE)) {
                LAND_TYPE = LAND_TYPE.trim().replace("\n", " ").toLowerCase();

                if (!LAND_TYPE.contains("land") && !LAND_TYPE.contains("type")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String DISBURSEMENT_DATE = getCellValue(row.getCell(48));
            if (StringUtils.isNoneBlank(DISBURSEMENT_DATE)) {
                DISBURSEMENT_DATE = DISBURSEMENT_DATE.trim().replace("\n", " ").toLowerCase();

                if (!DISBURSEMENT_DATE.contains("disbursement") && !DISBURSEMENT_DATE.contains("date")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String DISBURSE_AMOUNT = getCellValue(row.getCell(49));
            if (StringUtils.isNoneBlank(DISBURSE_AMOUNT)) {
                DISBURSE_AMOUNT = DISBURSE_AMOUNT.trim().replace("\n", " ").toLowerCase();

                if (!DISBURSE_AMOUNT.contains("disburse") && !DISBURSE_AMOUNT.contains("amount")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String MATURITY_LOAN_DATE = getCellValue(row.getCell(50));
            if (StringUtils.isNoneBlank(MATURITY_LOAN_DATE)) {
                MATURITY_LOAN_DATE = MATURITY_LOAN_DATE.trim().replace("\n", " ").toLowerCase();

                if (!MATURITY_LOAN_DATE.contains("maturity") && !MATURITY_LOAN_DATE.contains("date")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String RECOVERY_AMOUNT_PRINCIPLE = getCellValue(row.getCell(51));
            if (StringUtils.isNoneBlank(RECOVERY_AMOUNT_PRINCIPLE)) {
                RECOVERY_AMOUNT_PRINCIPLE = RECOVERY_AMOUNT_PRINCIPLE.trim().replace("\n", " ").toLowerCase();

                if (!RECOVERY_AMOUNT_PRINCIPLE.contains("recovery") && !RECOVERY_AMOUNT_PRINCIPLE.contains("amount")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            String RECOVERY_AMOUNT_INTEREST = getCellValue(row.getCell(52));
            if (StringUtils.isNoneBlank(RECOVERY_AMOUNT_INTEREST)) {
                RECOVERY_AMOUNT_INTEREST = RECOVERY_AMOUNT_INTEREST.trim().replace("\n", " ").toLowerCase();

                if (!RECOVERY_AMOUNT_INTEREST.contains("recovery") && !RECOVERY_AMOUNT_INTEREST.contains("interest")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String recoveryDate = getCellValue(row.getCell(53));
            if (StringUtils.isNoneBlank(recoveryDate)) {
                recoveryDate = recoveryDate.trim().replace("\n", " ").toLowerCase();

                if (!recoveryDate.contains("recovery") && !recoveryDate.contains("date")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            if (flagForLabel) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }

            filecount = filecount + 1;
            row = sheet.getRow(filecount);
            if (
                StringUtils.isBlank(getCellValue(row.getCell(2))) &&
                    StringUtils.isBlank(getCellValue(row.getCell(4))) &&
                    StringUtils.isBlank(getCellValue(row.getCell(32)))
            ) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }

            String bankCode = getCellValue(row.getCell(2));
            String schemeWiseBranchCode = getCellValue(row.getCell(5));
            String packsCode = getCellValue(row.getCell(32));

            if (StringUtils.isNotBlank(bankCode) && StringUtils.isNotBlank(schemeWiseBranchCode) && StringUtils.isNotBlank(packsCode)) {
                if (!bankCode.matches("\\d+") && !schemeWiseBranchCode.matches("\\d+") && !packsCode.matches("\\d+")) {
                    throw new BadRequestAlertException("Invalid financial year in file", ENTITY_NAME, "financialYearInvalid");
                }
            }
            rbaControl.authenticateByCode(bankCode, schemeWiseBranchCode, packsCode, ENTITY_NAME);
            boolean flag = false;
            String fYear = getCellValue(row.getCell(0));

            if (StringUtils.isNotBlank(fYear) && !fYear.matches("\\d{4}/\\d{4}") && !fYear.matches("\\d{4}-\\d{4}")) {
                flag = true;
            }

            String ifsc = getCellValue(row.getCell(6));
            if (StringUtils.isNotBlank(ifsc) && !ifsc.matches("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$")) {
                flag = true;
            }

            String aadharNumberValue = getCellValue(row.getCell(10));
            if (StringUtils.isNotBlank(aadharNumberValue) && !validateAadhaarNumber(aadharNumberValue)) {
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
            if (StringUtils.isBlank(accountNumber)) {
                flag = true;
            }
            String landtype = getCellValue(row.getCell(47));
            if (
                StringUtils.isNotBlank(landtype) && !landtype.equalsIgnoreCase("irrigated") && !landtype.equalsIgnoreCase("non-irrigated")
            ) {
                flag = true;
            }

            if (flag) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }
        } catch (BadRequestAlertException e) {
            e.printStackTrace();
            throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (ForbiddenAuthRequestAlertException e) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
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
            log.error("file not saved successfully", e);

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

        boolean isSavePortalObj = false;

        int startRowIndex = filecount; // Starting row index
        List<IssFileParser> issFileParserList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                IssFileParser issFileParser = new IssFileParser();
                if (row != null) {
                    if (
                        StringUtils.isBlank(getCellValue(row.getCell(0))) &&
                            StringUtils.isBlank(getCellValue(row.getCell(1))) &&
                            StringUtils.isBlank(getCellValue(row.getCell(2))) &&
                            StringUtils.isBlank(getCellValue(row.getCell(3)))
                    ) {
                        break;
                    }

                    String fYear = getCellValue(row.getCell(0));
                    if (fYear.matches("\\d{4}/\\d{4}")) {
                        issFileParser.setFinancialYear(fYear.replace("/", "-"));
                        fYear = fYear.replace("/", "-");
                    } else {
                        issFileParser.setFinancialYear(fYear);
                    }

                    //skipping records if exists

                    String AccountNumber = getCellValue(row.getCell(30));
                    String LoanSactionDate = getDateCellValue(row.getCell(35));
                    String KccIssCropCode = getCellValue(row.getCell(39));
                    String DisbursementDate = getDateCellValue(row.getCell(48));
                    String maturityLoanDate = getDateCellValue(row.getCell(50));

                    if (
                        !issFileParserRepository
                            .findOneByFinancialYearAndAccountNumberAndLoanSactionDateAndKccIssCropCodeAndDisbursementDateAndMaturityLoanDate(
                                fYear,
                                AccountNumber,
                                LoanSactionDate,
                                KccIssCropCode,
                                DisbursementDate,
                                maturityLoanDate
                            )
                            .isPresent()
                    ) {

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

                        issFileParser.setKccIssCropCode(getCellValue(row.getCell(39)));

                        issFileParser.setKccIssCropName(getCellValue(row.getCell(40)));

                        issFileParser.setCropName(getCellValue(row.getCell(41)));

                        issFileParser.setSurveyNo(getCellValue(row.getCell(42)));

                        issFileParser.setSatBaraSubsurveyNo(getCellValue(row.getCell(43)));

                        //seasonName as per DisbursementDate
                        // 01-03 to 30-09 (KHARIP)
                        // 01-10 to 31-03 (RABBI)
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date disbursementDate = dateFormat.parse(getDateCellValue(row.getCell(48)));
                        String seasonName=seasonNameAsPerDisbursementDate(disbursementDate);
                        issFileParser.setSeasonName(seasonName);

                        issFileParser.setActivityType(getStringCellValue(row.getCell(45)));

                        issFileParser.setAreaHect(getFloatCellValue(row.getCell(46)));

                        issFileParser.setLandType(getCellValue(row.getCell(47)));

                        issFileParser.setDisbursementDate(getDateCellValue(row.getCell(48)));

                        issFileParser.setDisburseAmount(getCellValue(row.getCell(49)));

                        issFileParser.setMaturityLoanDate(getDateCellValue(row.getCell(50)));

                        issFileParser.setRecoveryAmountPrinciple(getCellValue(row.getCell(51)));

                        issFileParser.setRecoveryAmountInterest(getCellValue(row.getCell(52)));

                        issFileParser.setRecoveryDate(getDateCellValue(row.getCell(53)));
                        //installment recovery details
                        try {
                            //second time
                            issFileParser.setSecondRecoveryAmountPrinciple(getCellValue(row.getCell(54)));
                            issFileParser.setSecondRecoveryAmountInterest(getCellValue(row.getCell(55)));
                            issFileParser.setSecondRecoveryDate(getDateCellValue(row.getCell(56)));

                            //third time
                            issFileParser.setThirdRecoveryAmountPrinciple(getCellValue(row.getCell(57)));
                            issFileParser.setThirdRecoveryAmountInterest(getCellValue(row.getCell(58)));
                            issFileParser.setThirdRecoveryDate(getDateCellValue(row.getCell(59)));

                            //fourth time
                            issFileParser.setFourthRecoveryAmountPrinciple(getCellValue(row.getCell(60)));
                            issFileParser.setFourthRecoveryAmountInterest(getCellValue(row.getCell(61)));
                            issFileParser.setFourthRecoveryDate(getDateCellValue(row.getCell(62)));
                        } catch (Exception e) {
                        }

                        if (isSavePortalObj) {
                            issPortalFile = issPortalFileRepository.save(issPortalFile);
                            isSavePortalObj = true;
                        }

                        issFileParser.setIssPortalFile(issPortalFile);

                        issFileParserList.add(issFileParser);
                    }
                }
            }

            if (!issFileParserList.isEmpty()) {
                issPortalFile.setApplicationCount(issFileParserList.size());
                issPortalFile.setFinancialYear(issFileParserList.get(0).getFinancialYear());
                issPortalFile.setSchemeWiseBranchCode(Math.round(Double.parseDouble(issFileParserList.get(0).getSchemeWiseBranchCode())));
                issPortalFile.setPacsCode(Long.parseLong(issFileParserList.get(0).getPacsNumber()));
                issPortalFile.setPacsName(issFileParserList.get(0).getPacsName());
                issPortalFile.setBranchName(issFileParserList.get(0).getBranchName());

                //removing same application if present

                //				List<IssFileParser> duplicatesAccountNumber = issFileParserList.stream().filter(
                //						person -> issFileParserList.stream().filter(p -> p != person).anyMatch(person::isDuplicateApp))
                //						.collect(Collectors.toList());
                //
                //				if (duplicatesAccountNumber.size() > 1) {
                //
                //					List<IssFileParser> manageUniqueList = new ArrayList<>();
                //					for (IssFileParser issFileParser : duplicatesAccountNumber) {
                //
                //						if (manageUniqueList.isEmpty()) {
                //							manageUniqueList.add(issFileParser);
                //						}
                //
                //						else if (!manageUniqueList.stream().anyMatch(
                //								a -> a.getAccountNumber().equalsIgnoreCase(issFileParser.getAccountNumber()))) {
                //							manageUniqueList.add(issFileParser);
                //						}
                //
                //					}
                //
                //				}

                IssPortalFile issPortalFilesave = issPortalFileRepository.save(issPortalFile);

                issFileParserRepository.saveAll(issFileParserList);

                try {
                    validateFileFile(issPortalFilesave);
                } catch (Exception e) {
                }

                if (issFileParserList.get(0) != null) {
                    try {
                        notificationDataUtility.notificationData(
                            "Application record file uploaded",
                            "Application record file uploaded : " + files.getOriginalFilename() + " uploaded",
                            false,
                            issFileParserList.get(0).getCreatedDate(),
                            "ApplicationRecordFileUploaded" //type
                        );
                    } catch (Exception e) {
                    }
                }

                return ResponseEntity.ok().body(issFileParserList);
            } else {
                throw new BadRequestAlertException("File is already parsed", ENTITY_NAME, "FileExist");
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
    }

    private String seasonNameAsPerDisbursementDate(Date disbursementDate) {
        // Note: Month is 0-based in Date class
        int month = disbursementDate.getMonth() + 1;
        if(month >= 4 && month <= 9) {
            return "KHARIP";
        }else return "RABBI";
    }

    private void validateFileFile(IssPortalFile issPortalFile) {
        List<IssFileParser> findAllByIssPortalFile = issFileParserRepository.findAllByIssPortalFile(issPortalFile);

        if (!findAllByIssPortalFile.isEmpty()) {
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

            //duplicate kcc loan account number
            List<IssFileParser> duplicatesAccountNumber = findAllByIssPortalFile
                .stream()
                .filter(person -> findAllByIssPortalFile.stream().filter(p -> p != person).anyMatch(person::isDuplicate))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : duplicatesAccountNumber) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Duplicate KCC Loan Account Number found.", issFileParser));
            }

            // Filter invalid dob

            List<IssFileParser> invalidDOBList = findAllByIssPortalFile
                .stream()
                .filter(person -> !validateDate(person.getDateofBirth()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidDOBList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Date of birth is not in dd-mm-yyyy format", issFileParser));
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


            // Filter invalid bank code
            List<IssFileParser> invalidBankCodeList = findAllByIssPortalFile
                .stream()
                .filter(person -> !person.getBankCode().matches("^[0-9]{3}$"))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidBankCodeList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Bank Code is incorrect format", issFileParser));
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
                .filter(person -> !validateCropCode(person.getKccIssCropCode()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidCropCodeList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Crop code is incorrect format", issFileParser));
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

            // Filter invalid season
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


            // Filter invalid activityType
            List<IssFileParser> invalidActivityTypeList = findAllByIssPortalFile
                .stream()
                .filter(person -> !validateActivityType(person.getActivityType()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidActivityTypeList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(
                    new ApplicationLog("Activity type is not in AGRI CROP,  HORTI AND VEG CROPS", issFileParser)
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

            List<IssFileParser> correctedRecordsInFile = findAllByIssPortalFile
                .stream()
                .filter(c1 -> issFileParserValidationErrorSet.stream().noneMatch(c2 -> c1.getId() == c2.getId()))
                .collect(Collectors.toList());

            if (!applicationLogListToSave.isEmpty()) {
                applicationLogRepository.saveAll(applicationLogListToSave);
                issPortalFile.setErrorRecordCount(applicationLogListToSave.size());

            }


            List<Application> applicationList = new ArrayList<>();
            if (!correctedRecordsInFile.isEmpty()) {
                issPortalFile.setAppPendingToSubmitCount((long) correctedRecordsInFile.size());
                for (IssFileParser issFileParser : correctedRecordsInFile) {
                    if (!applicationRepository.findOneByIssFileParser(issFileParser).isPresent()) {
                        Application application = new Application();
                        application.setRecordStatus(Constants.COMPLETE_FARMER_DETAIL_AND_LOAN_DETAIL);
                        application.setApplicationStatus(Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD);
                        application.setIssFileParser(issFileParser);
                        application.setBankCode(Long.parseLong(issFileParser.getBankCode()));
                        application.setSchemeWiseBranchCode(Long.parseLong(issFileParser.getSchemeWiseBranchCode()));
                        application.setPacksCode(Long.parseLong(issFileParser.getPacsNumber()));
                        application.setFinancialYear(issFileParser.getFinancialYear());
                        application.setIssFilePortalId(issFileParser.getIssPortalFile().getId());
                        applicationList.add(application);
                    }
                }

                applicationRepository.saveAll(applicationList);
            }

            try {
                issPortalFileRepository.save(issPortalFile);
                notificationDataUtility.notificationData(
                    "Application record file validated",
                    "Application record file : " + issPortalFile.getFileName() + " validated",
                    false,
                    Instant.now(),
                    "ApplicationRecordFileValidated" //type
                );
            } catch (Exception e) {
            }
        }
    }

    //need to remove
    // @PostMapping("/validateFile")
    //  @PreAuthorize("@authentication.hasPermision('',#issPortalFile.id,'','FILE_VALIDATE','VALIDATE')")
    private ResponseEntity<Set<ApplicationLog>> validateFile(@RequestBody IssPortalFile issPortalFile) {
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
            .filter(person -> !validateCropCode(person.getKccIssCropCode()))
            .collect(Collectors.toList());

        for (IssFileParser issFileParser : invalidCropCodeList) {
            issFileParserValidationErrorSet.add(issFileParser);
            applicationLogList.add(new ApplicationLog("Crop code is incorrect format", issFileParser));
        }

        //        List<IssFileParser> invalidCropCodeList = findAllByIssPortalFile
        //            .stream()
        //            .filter(person -> StringUtils.isBlank(person.getCropName()))
        //            .collect(Collectors.toList());
        //
        //        for (IssFileParser issFileParser : invalidCropCodeList) {
        //            issFileParserValidationErrorSet.add(issFileParser);
        //            applicationLogList.add(new ApplicationLog("Crop Name is incorrect format", issFileParser));
        //        }

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

        try {
            notificationDataUtility.notificationData(
                "Application record file validated",
                "Application record file : " + findById.get().getFileName() + " validated",
                false,
                Instant.now(),
                "ApplicationRecordFileValidated" //type
            );
        } catch (Exception e) {
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
        if (validateOneFileDataObject != null && validateOneFileDataObject.getStatus().equalsIgnoreCase(Constants.ERROR)) {
            applicationLogRepository.save(validateOneFileDataObject);

            IssPortalFile issPortalFileSave = issPortalFileRepository.save(issPortalFile);

            // saving iss file data after validating
            issFileParser.setIssPortalFile(issPortalFileSave);
            issFileParser = issFileParserService.update(issFileParser);
        } else {
            if (Constants.validationError.equalsIgnoreCase(findOneByIssFileParser.get().getErrorType())) {
                issPortalFile.setErrorRecordCount(issPortalFile.getErrorRecordCount() - 1);
                issPortalFile.setAppPendingToSubmitCount(issPortalFile.getAppAcceptedByKccCount() + 1);
            } else if (Constants.kccError.equalsIgnoreCase(findOneByIssFileParser.get().getErrorType())) {
                issPortalFile.setKccErrorRecordCount(issPortalFile.getKccErrorRecordCount() - 1);
                issPortalFile.setAppPendingToSubmitCount(issPortalFile.getAppAcceptedByKccCount() + 1);

                applicationRepository.deleteByIssFileParser(issFileParser);
            }

            IssPortalFile issPortalFileSave = issPortalFileRepository.save(issPortalFile);


            // saving iss file data after validating
            issFileParser.setIssPortalFile(issPortalFileSave);
            issFileParser = issFileParserService.update(issFileParser);

            // adding file data entry to application tracking table
            Application application = new Application();
            application.setRecordStatus(Constants.COMPLETE_FARMER_DETAIL_AND_LOAN_DETAIL);
            application.setApplicationStatus(Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD);
            application.setIssFileParser(issFileParser);
            application.setBankCode(Long.parseLong(issFileParser.getBankCode()));
            application.setSchemeWiseBranchCode(Long.parseLong(issFileParser.getSchemeWiseBranchCode()));
            application.setPacksCode(Long.parseLong(issFileParser.getPacsNumber()));
            application.setFinancialYear(issFileParser.getFinancialYear());
            application.setIssFilePortalId(issPortalFileSave.getId());
            applicationRepository.save(application);

            // adding status fixed in application log
            ApplicationLog applicationLog = findOneByIssFileParser.get();
            applicationLog.setStatus(Constants.FIXED);
            applicationLog.setSevierity("");
            applicationLog.setErrorRecordCount(0);
            applicationLogRepository.save(applicationLog);

            if (issFileParser != null) {
                try {
                    notificationDataUtility.notificationData(
                        "Application Updated",
                        "Application by farmer name : " + issFileParser.getFarmerName() + " is updated",
                        false,
                        issFileParser.getCreatedDate(),
                        "ActivityTypeMasterUpdated" //type
                    );
                } catch (Exception e) {
                }
            }

        }


        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issFileParser.getId().toString()))
            .body(issFileParser);
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
            validationErrorBuilder.append("Date Of Birth is not in dd-mm-yyyy format. ");
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


        // Scheme Wise Branch Code
        if (!issFileParser.getSchemeWiseBranchCode().matches("^[0-9]{6}$")) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Branch Code is in incorrect format. ");
        }

        // bank code
        if (!issFileParser.getBankCode().matches("^[0-9]{3}$")) {
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
            validationErrorBuilder.append("kcc Loan Sanctioned Date is not in dd-mm-yyyy format.");
        }

        // kccLoanSanctionedAmount
        if (!validateAmount(issFileParser.getLoanSanctionAmount())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("kcc Loan Sanctioned Amount is in incorrect format. ");
        }

        // activityType
        if (!validateActivityType(issFileParser.getActivityType())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Activity Type must be AGRI CROP, HORTI AND VEG CROPS ");
        }

        // loanSanctionedDate
        if (!validateDate(issFileParser.getLoanSactionDate())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Loan Sanction Date is not in dd-mm-yyyy format.");
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

        // cropName
        if (!cropMasterRepository.existsByCropName(issFileParser.getKccIssCropName())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Crop Name is in incorrect format. ");
        }

        // cropCode
        if (!cropMasterRepository.existsByCropCode(issFileParser.getKccIssCropCode())) {
            errorCount = errorCount + 1;
            validationErrorBuilder.append("Crop code is in incorrect format. ");
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

        if (StringUtils.isBlank(validationErrorBuilder)) {
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
        if (StringUtils.isNotBlank(seasonName) &&
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

    private boolean validateActivityType(String activityType) {
        if (StringUtils.isNotBlank(activityType) &&
            MasterDataCacheService.ActivityTypeMasterList
                .stream()
                .filter(f -> f.getActivityType().toLowerCase().contains(activityType.toLowerCase()))
                .map(ActivityType::getActivityType)
                .findFirst()
                .isPresent()
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateLandType(String landType) {
        if (StringUtils.isNotBlank(landType) &&
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
            return false;
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

    private boolean validateCropCode(String cropCode) {

        if (StringUtils.isNotBlank(cropCode) &&
            MasterDataCacheService.CropMasterList
                .stream()
                .filter(f -> f.getCropCode().equals(cropCode))
                .map(CropMaster::getCropCode)
                .findFirst()
                .isPresent()
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateAccountHolder(String accountHolder) {
        if (StringUtils.isNotBlank(accountHolder) &&
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
        if (StringUtils.isNotBlank(occupationName) &&
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
        if (StringUtils.isNotBlank(farmerType) &&
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
        if (StringUtils.isNotBlank(farmerCategory) &&
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
        if (StringUtils.isNotBlank(castCategoryName) &&
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
        if (StringUtils.isBlank(gender)) {
            return false;
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
        if (StringUtils.isBlank(dateofBirth)) {
            return false;
        }

        Pattern patternYYYYMMDD = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");
        Pattern patternYYYY_MM_DD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        Pattern patternDDMMYYYY = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
        Pattern patternDD_MM_YYYY = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

        if (
            patternYYYYMMDD.matcher(dateofBirth).matches() ||
                patternDDMMYYYY.matcher(dateofBirth).matches() ||
                patternDD_MM_YYYY.matcher(dateofBirth).matches() ||
                patternYYYY_MM_DD.matcher(dateofBirth).matches()
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateSatBaraNumber(String satBaraSubsurveyNo) {
        if (StringUtils.isBlank(satBaraSubsurveyNo)) {
            return false;
        }
        String satBaraSubsurveyNoPattern = ".*[^a-zA-Z].*";
        return satBaraSubsurveyNo.matches(satBaraSubsurveyNoPattern);
    }

    private boolean validateMobileNumber(String mobileNo) {
        boolean flag = false;

        if (StringUtils.isBlank(mobileNo)) {
            return false;
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
        if (StringUtils.isBlank(financialYear)) {
            return false;
        }
        String financialYearPattern = "^\\d{4}-\\d{4}$";
        return financialYear.matches(financialYearPattern);
    }

    private boolean validateAadhaarNumber(String aadharNumber) {
        if (StringUtils.isBlank(aadharNumber)) {
            return false;
        }
        String aadhaarPattern = "^[2-9]{1}[0-9]{11}$";
        return aadharNumber.matches(aadhaarPattern);
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
     * body the new issFileParser, or with status {@code 400 (Bad Request)}
     * if the issFileParser has already an ID.
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
     * the updated issFileParser, or with status {@code 400 (Bad Request)}
     * if the issFileParser is not valid, or with status
     * {@code 500 (Internal Server Error)} if the issFileParser couldn't be
     * updated.
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
     * the updated issFileParser, or with status {@code 400 (Bad Request)}
     * if the issFileParser is not valid, or with status
     * {@code 404 (Not Found)} if the issFileParser is not found, or with
     * status {@code 500 (Internal Server Error)} if the issFileParser
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/iss-file-parsers/{id}", consumes = {"application/json", "application/merge-patch+json"})
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
     * of issFileParsers in body.
     */

    @GetMapping("/iss-file-parsers")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('VIEW_RECORD','VIEW')")
    public ResponseEntity<List<IssFileParser>> getAllIssFileParsers(
        IssFileParserCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get IssFileParsers by criteria: {}", criteria);

        Page<IssFileParser> page = null;
        Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            page =
                issFileParserQueryService.findByCriteriaPackNumber(criteria, pageable, branchOrPacksNumber.get(Constants.PACKS_CODE_KEY));
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY))) {
            page =
                issFileParserQueryService.findByCriteriaSchemeWiseBranchCode(
                    criteria,
                    pageable,
                    branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY)
                );
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            page = issFileParserQueryService.findByCriteria(criteria, pageable);
        } else {
            throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /iss-file-parsers/count} : count all the issFileParsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     * in body.
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
     * the issFileParser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/iss-file-parsers/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('VIEW_RECORD','VIEW')")
    public ResponseEntity<IssFileParser> getIssFileParser(@PathVariable Long id) {
        log.debug("REST request to get IssFileParser : {}", id);
        Optional<IssFileParser> issFileParser = null;
        Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            issFileParser = issFileParserRepository.findOneByIdAndPacsNumber(id, branchOrPacksNumber.get(Constants.PACKS_CODE_KEY));
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY))) {
            issFileParser =
                issFileParserRepository.findOneByIdAndSchemeWiseBranchCode(id, branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY));
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            issFileParser = issFileParserRepository.findOneByIdAndBankCode(id, branchOrPacksNumber.get(Constants.BANK_CODE_KEY));
        } else {
            throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
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
//        Optional<IssFileParser> issFileParser = issFileParserRepository.findById(id);
//        Optional<Application> applicationTx = applicationRepository.findOneByIssFileParser(issFileParser.get());
//        Optional<ApplicationLog> applicationLog = applicationLogRepository.findOneByIssFileParser(issFileParser.get());
//        if (issFileParserRepository.existsById(id)) {
//            if (applicationTx.isPresent()) {
//                applicationLogRepository.deleteByIssFileParser(id);
//                applicationRepository.deleteByIssFileParserId(id);
//                issFileParserRepository.deleteById(id);
//            }
//        }

        issFileParserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
