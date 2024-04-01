package com.cbs.middleware.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.cbs.middleware.domain.IssChildPortalFile;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.domain.LandTypeMaster;
import com.cbs.middleware.domain.OccupationMaster;
import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.repository.BatchTransactionRepository;
import com.cbs.middleware.repository.CastCategoryMasterRepository;
import com.cbs.middleware.repository.CropMasterRepository;
import com.cbs.middleware.repository.DesignationMasterRepository;
import com.cbs.middleware.repository.FarmerCategoryMasterRepository;
import com.cbs.middleware.repository.FarmerTypeMasterRepository;
import com.cbs.middleware.repository.IssChildPortalFileRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.repository.LandTypeMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.OccupationMasterRepository;
import com.cbs.middleware.repository.RelativeMasterRepository;
import com.cbs.middleware.repository.SeasonMasterRepository;
import com.cbs.middleware.security.RBAControl;
import com.cbs.middleware.service.ResponceService;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssFileParser}.
 */
@RestController
@RequestMapping("/api")
public class IssChildFileParserResource {

    private static final String ENTITY_NAME = "issFileParser";
    private final Logger log = LoggerFactory.getLogger(IssChildFileParserResource.class);
    private final IssFileParserRepository issFileParserRepository;
    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    DesignationMasterRepository designationMasterRepository;
    @Autowired
    SeasonMasterRepository seasonMasterRepository;
    @Autowired
    NotificationDataUtility notificationDataUtility;
    @Autowired
    IssChildPortalFileRepository issChildPortalFileRepository;
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
    NotificationRepository notificationRepository;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public IssChildFileParserResource(IssFileParserRepository issFileParserRepository) {
        this.issFileParserRepository = issFileParserRepository;
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

    private static String getCellValue(Cell cell) {
        String cellValue = "";

        if (cell == null) {
            return cellValue;
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
            return cellValue;
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

    /**
     * Customize code
     *
     * @return
     * @throws Exception
     * @throws Exception
     */

    @PostMapping("/child-file-Parser")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('FILE_UPLOAD','UPLOAD')")
    public ResponseEntity<Set<ApplicationLog>> excelReader1(
        @RequestParam("file") MultipartFile files,
        RedirectAttributes redirectAttributes
    ) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());

        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(0); // Get the current row

            String applicationErrorLable = getCellValue(row.getCell(63));
            String applicationIdLable = getCellValue(row.getCell(64));

            if (StringUtils.isNotBlank(applicationErrorLable) && StringUtils.isNotBlank(applicationIdLable)) {
                if (!"Application Error".equalsIgnoreCase(applicationErrorLable) && !"ID".equalsIgnoreCase(applicationIdLable)) {
                    throw new BadRequestAlertException("Invalid file", ENTITY_NAME, "fileInvalid");
                }
            } else {
                throw new BadRequestAlertException("Invalid file", ENTITY_NAME, "fileInvalid");
            }

            if (
                getCellValue(row.getCell(0)) != null &&
                    !getCellValue(row.getCell(0)).contains("Financial") &&
                    !getCellValue(row.getCell(0)).contains("Year")
            ) {
                throw new BadRequestAlertException("Invalid file", ENTITY_NAME, "fileInvalid");
            }

            row = sheet.getRow(1);

            String bankCode = getCellValue(row.getCell(2));
            String schemeWiseBranchCode = getCellValue(row.getCell(5));
            String packsCode = getCellValue(row.getCell(32));

            if (StringUtils.isBlank(bankCode) || StringUtils.isBlank(schemeWiseBranchCode) || StringUtils.isBlank(packsCode)) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            } else if (
                StringUtils.isNotBlank(bankCode) && StringUtils.isNotBlank(schemeWiseBranchCode) && StringUtils.isNotBlank(packsCode)
            ) {
                if (!bankCode.matches("\\d+") && !bankCode.matches("\\d+") && !bankCode.matches("\\d+")) {
                    throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
                }
            }

            rbaControl.authenticateByCode(bankCode, schemeWiseBranchCode, packsCode, ENTITY_NAME);
        } catch (BadRequestAlertException e) {
            throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (UnAuthRequestAlertException e) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (ForbiddenAuthRequestAlertException e) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (EncryptedDocumentException e1) {
            throw new EncryptedDocumentException("EncryptedDocumentException");
        } catch (IOException e1) {
            throw new IOException("IOException");
        }

        File originalFileDir = new File(Constants.CHILD_FILE_PATH);
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

        int startRowIndex = 1; // Starting row index
        List<IssFileParser> issFileParserList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                IssFileParser issFileParser = new IssFileParser();
                if (row != null) {
                    String idInFIle = decryption(getCellValue(row.getCell(64)));
                    if (StringUtils.isNotBlank(idInFIle)) {
                        Optional<IssFileParser> issFileParseInChildFile = issFileParserRepository.findById(Long.parseLong(idInFIle));

                        if (issFileParseInChildFile.isPresent()) {
                            issFileParser.setId(issFileParseInChildFile.get().getId());

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
                            String farmerName = getCellValue(row.getCell(8));

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

                            issFileParser.setSeasonName(getCellValue(row.getCell(44)));

                            issFileParser.setAccountType(getCellValue(row.getCell(45)));

                            issFileParser.setAreaHect(getCellValue(row.getCell(46)));

                            issFileParser.setLandType(getCellValue(row.getCell(47)));

                            issFileParser.setDisbursementDate(getDateCellValue(row.getCell(48)));

                            issFileParser.setDisburseAmount(getCellValue(row.getCell(49)));

                            issFileParser.setMaturityLoanDate(getDateCellValue(row.getCell(50)));

                            issFileParser.setRecoveryAmountPrinciple(getCellValue(row.getCell(51)));
                            issFileParser.setRecoveryAmountInterest(getCellValue(row.getCell(52)));
                            issFileParser.setRecoveryDate(getDateCellValue(row.getCell(53)));


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


                            issFileParser.setIssPortalFile(issFileParseInChildFile.get().getIssPortalFile());

                            issFileParserList.add(issFileParser);
                        }
                    }
                }
            }

            if (!issFileParserList.isEmpty()) {
                IssChildPortalFile issChildPortalFile = new IssChildPortalFile();
                issChildPortalFile.setFileName(files.getOriginalFilename());
                issChildPortalFile.setUniqueName(uniqueName + "." + fileExtension);
                issChildPortalFile.setFileExtension(fileExtension);

                issChildPortalFile = issChildPortalFileRepository.save(issChildPortalFile);
                Set<ApplicationLog> validateFile = validateFile(issFileParserList, issChildPortalFile);

                if (!issFileParserList.isEmpty() && issFileParserList.get(0) != null) {
                    try {
                        notificationDataUtility.notificationData(
                            "Error correction file uploaded",
                            "Error correction file : " + files.getOriginalFilename() + " uploaded",
                            false,
                            issFileParserList.get(0).getCreatedDate(),
                            "ErrorCorrectionFileUploaded" //type
                        );
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

                return ResponseEntity.ok().body(validateFile);
            } else {
                throw new BadRequestAlertException("No data saved from file, File was not in format", ENTITY_NAME, "nullColumn");
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
    }

    public Set<ApplicationLog> validateFile(List<IssFileParser> issFileParserList, IssChildPortalFile issChildPortalFile) {

        //------------for KCC ERROR -------------------------------------------
        for (IssFileParser issFileParser : issFileParserList) {

            Optional<ApplicationLog> findOneByIssFileParser = applicationLogRepository.findOneByIssFileParser(issFileParser);
            if (findOneByIssFileParser.isPresent()) {
                if (Constants.kccError.equalsIgnoreCase(findOneByIssFileParser.get().getErrorType()) && findOneByIssFileParser.get().getStatus().equals("ERROR")) {

                    if (!findOneByIssFileParser.get().getErrorMessage().contains("eixsts") &&
                        !findOneByIssFileParser.get().getErrorMessage().contains("already") &&
                        !findOneByIssFileParser.get().getErrorMessage().contains("Batch") &&
                        !findOneByIssFileParser.get().getErrorMessage().contains("proper") &&
                        !findOneByIssFileParser.get().getErrorMessage().contains("token") &&
                        !findOneByIssFileParser.get().getErrorMessage().contains("preUniqueId")
                    ) {
                        applicationRepository.deleteByIssFileParser(issFileParser);

                        Application application = new Application();
                        application.setRecordStatus(Constants.COMPLETE_FARMER_DETAIL_AND_LOAN_DETAIL);
                        application.setApplicationStatus(Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD);
                        application.setIssFileParser(issFileParser);
                        application.setBankCode(Long.parseLong(issFileParser.getBankCode()));
                        application.setSchemeWiseBranchCode(Long.parseLong(issFileParser.getSchemeWiseBranchCode()));
                        application.setPacksCode(Long.parseLong(issFileParser.getPacsNumber()));
                        application.setFinancialYear(issFileParser.getFinancialYear());
                        application.setIssFilePortalId(issFileParser.getIssPortalFile().getId());
                        applicationRepository.save(application);

                        // adding status fixed in application log
                        ApplicationLog applicationLog = findOneByIssFileParser.get();
                        applicationLog.setStatus(Constants.FIXED);
                        applicationLog.setSevierity("");
                        applicationLog.setErrorRecordCount(0L);
                        applicationLogRepository.save(applicationLog);
                    }

                }
            }
        }
        //-----------------------------------------------------------------

        List<Application> findAllByIssFilePortalId = applicationRepository.findAllByIssFilePortalId(
            issFileParserList.get(0).getIssPortalFile().getId()
        );

        List<IssFileParser> issFileParserPresentInTrans = findAllByIssFilePortalId
            .stream()
            .map(Application::getIssFileParser)
            .collect(Collectors.toList());

       // issFileParserList.removeAll(issFileParserPresentInTrans);
        Set<ApplicationLog> applicationLogList = new HashSet<>();
        Set<ApplicationLog> applicationLogListToSaveError = new HashSet<>();
        Set<IssFileParser> issFileParserValidationErrorSet = new HashSet<>();
        if (!issFileParserList.isEmpty()) {


            IssPortalFile issPortalFile = issFileParserList.get(0).getIssPortalFile();

            // Filter invalid Financial Year
            List<IssFileParser> invalidFinancialYearList = issFileParserList
                .stream()
                .filter(person -> !validateFinancialYear(person.getFinancialYear()))
                .collect(Collectors.toList());
            for (IssFileParser issFileParser : invalidFinancialYearList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Financial Year is not in yyyy-yyyy format", issFileParser));
            }

            // Filter invalid Aadhaar numbers
            List<IssFileParser> invalidAadhaarList = issFileParserList
                .stream()
                .filter(person -> !validateAadhaarNumber(person.getAadharNumber()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidAadhaarList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Aadhar number is incorrect format", issFileParser));
            }

            // Filter invalid beneficiary Name and beneficiary Passbook Name and
            // accountHolder

            List<IssFileParser> invalidBeneficiaryNameList = issFileParserList
                .stream()
                .filter(person -> StringUtils.isBlank(person.getFarmerName()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidBeneficiaryNameList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Beneficiary Name is incorrect format", issFileParser));
            }

            // Filter invalid Mobile number

            List<IssFileParser> invalidMobileNumberList = issFileParserList
                .stream()
                .filter(person -> !validateMobileNumber(person.getMobileNo()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidMobileNumberList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Mobile number is incorrect format", issFileParser));
            }

            // Filter invalid dob

            List<IssFileParser> invalidDOBList = issFileParserList
                .stream()
                .filter(person -> StringUtils.isBlank(person.getDateofBirth()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidDOBList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Date of birth is not in yyyy-mm-dd format", issFileParser));
            }

            // Filter invalid gender

            List<IssFileParser> invalidGenderList = issFileParserList
                .stream()
                .filter(person -> !validateGender(person.getGender()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidGenderList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Gender is incorrect format", issFileParser));
            }

            // Filter invalid socialCategory

            List<IssFileParser> invalidSocialCategoryList = issFileParserList
                .stream()
                .filter(person -> !validateSocialCategory(person.getSocialCategory()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidSocialCategoryList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Social Category is not in SC, ST, OBC, GEN format", issFileParser));
            }

            // Filter invalid farmerCategory
            List<IssFileParser> invalidFarmerCategoryList = issFileParserList
                .stream()
                .filter(person -> !validateFarmerCategory(person.getFarmersCategory()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidFarmerCategoryList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Farmer Category is not in OWNER, SHARECROPPER, TENANT format", issFileParser));
            }

            // Filter invalid farmerType
            List<IssFileParser> invalidFarmerTypeList = issFileParserList
                .stream()
                .filter(person -> !validateFarmerType(person.getFarmerType()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidFarmerTypeList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Farmer type is not in SMALL, OTHER, MARGINAL format", issFileParser));
            }

            // Filter invalid primaryOccupation

            List<IssFileParser> invalidPrimaryOccupationList = issFileParserList
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
            List<IssFileParser> invalidRelativeTypeList = issFileParserList
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

            List<IssFileParser> invalidRelativeNameList = issFileParserList
                .stream()
                .filter(person -> StringUtils.isBlank(person.getRelativeName()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidRelativeNameList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Relative name is incorrect format", issFileParser));
            }

            // Filter invalid residentialPincode
            List<IssFileParser> invalidResidentialPincodeList = issFileParserList
                .stream()
                .filter(person -> !person.getPinCode().matches("^[0-9]{6}$"))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidResidentialPincodeList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Residential pin code is incorrect format", issFileParser));
            }

            // Filter invalid accountNumber
            List<IssFileParser> invalidAccountNumberList = issFileParserList
                .stream()
                .filter(person -> !person.getAccountNumber().matches("\\d+"))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidAccountNumberList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Account Number is incorrect format", issFileParser));
            }

            // Filter invalid Scheme Wise Branch Code
            List<IssFileParser> invalidBranchCodeList = issFileParserList
                .stream()
                .filter(person -> !person.getSchemeWiseBranchCode().matches("^[0-9]{6}$"))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidBranchCodeList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Scheme Wise Branch Code is incorrect format", issFileParser));
            }

            // Filter invalid ifsc
            List<IssFileParser> invalidIFSCCodeList = issFileParserList
                .stream()
                .filter(person -> !person.getIfsc().matches("^[A-Za-z]{4}0[A-Z0-9a-z]{6}$"))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidIFSCCodeList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("IFSC Code is incorrect format", issFileParser));
            }

            // Filter invalid kccLoanSanctionedDate //loanSanctionedDate
            List<IssFileParser> invalidKccLoanSanctionedDateList = issFileParserList
                .stream()
                .filter(person -> StringUtils.isBlank(person.getLoanSactionDate()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidKccLoanSanctionedDateList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Loan Sanctioned Date is incorrect format", issFileParser));
            }

            // Filter invalid kccLoanSanctionedAmount // kccDrawingLimitforFY // Loan
            // Sanction Amount
            List<IssFileParser> invalidkccLoanSanctionedAmountList = issFileParserList
                .stream()
                .filter(person -> !validateAmount(person.getLoanSanctionAmount()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidkccLoanSanctionedAmountList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Loan Sanction Amount is incorrect format", issFileParser));
            }

            // Filter invalid landVillage
            List<IssFileParser> invalidLandVillageList = issFileParserList
                .stream()
                .filter(person -> !person.getVillageCode().matches("^[0-9]{6}$"))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidLandVillageList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Land Village Code is incorrect format", issFileParser));
            }

            // Filter invalid cropCode
            List<IssFileParser> invalidCropCodeList = issFileParserList
                .stream()
                .filter(person -> StringUtils.isBlank(person.getCropName()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidCropCodeList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Crop Name is incorrect format", issFileParser));
            }

            // Filter invalid surveyNumber
            List<IssFileParser> invalidSurveyNumberList = issFileParserList
                .stream()
                .filter(person -> (!person.getSurveyNo().matches("^[0-9,\\/ ]+$")))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidSurveyNumberList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Survey Number is incorrect format", issFileParser));
            }

            // Filter invalid khataNumber or Sat Bara number
            List<IssFileParser> invalidSatBaraNumberList = issFileParserList
                .stream()
                .filter(person -> !validateSatBaraNumber(person.getSatBaraSubsurveyNo()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidSatBaraNumberList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Sat Bara number is incorrect format", issFileParser));
            }

            // Filter invalid landArea
            List<IssFileParser> invalidLandAreaList = issFileParserList
                .stream()
                .filter(person -> !validateAmount(person.getAreaHect()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidLandAreaList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Land Area Hect is incorrect format", issFileParser));
            }

            // Filter invalid landType
            List<IssFileParser> invalidLandTypeList = issFileParserList
                .stream()
                .filter(person -> !validateLandType(person.getLandType()))
                .collect(Collectors.toList());

            for (IssFileParser issFileParser : invalidLandTypeList) {
                issFileParserValidationErrorSet.add(issFileParser);
                applicationLogList.add(new ApplicationLog("Land Type is not in IRRIGATED, NON-IRRIGATED format", issFileParser));
            }

            // Filter invalid season //activityType
            List<IssFileParser> invalidSeasonList = issFileParserList
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
                applicationLog.setErrorRecordCount(Long.valueOf(collect.size()));
                applicationLog.setIssPortalId(issFileParser.getIssPortalFile().getId());
                applicationLog.setFileName(issFileParser.getIssPortalFile().getFileName());
                applicationLogListToSaveError.add(applicationLog);
            }

            if (!applicationLogListToSaveError.isEmpty()) {
                applicationLogRepository.saveAll(applicationLogListToSaveError);
            }

            List<IssFileParser> correctedRecordsInFile = issFileParserList
                .stream()
                .filter(c1 -> issFileParserValidationErrorSet.stream().noneMatch(c2 -> c1.getId() == c2.getId()))
                .collect(Collectors.toList());

            List<Application> applicationList = new ArrayList<>();
            if (!correctedRecordsInFile.isEmpty()) {
                Set<ApplicationLog> applicationLogListMarkedFix = new HashSet<>();
                for (IssFileParser issFileParser : correctedRecordsInFile) {

                    //change made in existing application_transaction
                    Optional<Application> application = applicationRepository.findOneByIssFileParser(issFileParser);
                    application.get().setRecordStatus(Constants.COMPLETE_FARMER_DETAIL_AND_LOAN_DETAIL);
                    application.get().setApplicationStatus(Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD);
                    application.get().setIssFileParser(issFileParser);
                    application.get().setIssFilePortalId(issFileParser.getIssPortalFile().getId());
                    application.get().setFinancialYear(issFileParser.getFinancialYear());
                    applicationList.add(application.get());

                    ApplicationLog applicationLog = new ApplicationLog();
                    Optional<ApplicationLog> applicationLogSaved = applicationLogRepository.findOneByIssFileParser(issFileParser);
                    if (applicationLogSaved.isPresent()) {
                        applicationLog = applicationLogSaved.get();
                    }

                    applicationLog.setIssFileParser(issFileParser);
                    applicationLog.setStatus(Constants.FIXED);
                    applicationLog.setSevierity("");
                    applicationLog.setErrorRecordCount(Long.valueOf(0));
                    applicationLog.setIssPortalId(issPortalFile.getId());
                    applicationLog.setFileName(issPortalFile.getFileName());
                    applicationLogListMarkedFix.add(applicationLog);
                }

                applicationRepository.saveAll(applicationList);
                applicationLogRepository.saveAll(applicationLogListMarkedFix);

                issPortalFile.setErrorRecordCount(
                    issPortalFile.getApplicationCount() - applicationRepository.countByIssFilePortalId(issPortalFile.getId())
                );
                issPortalFileRepository.save(issPortalFile);
            }
            issFileParserRepository.saveAll(issFileParserList);

            issChildPortalFile.setApplicationCount("" + issFileParserList.size());
            issChildPortalFile.setFinancialYear(issFileParserList.get(0).getFinancialYear());
            issChildPortalFile.setSchemeWiseBranchCode(Math.round(Double.parseDouble(issFileParserList.get(0).getSchemeWiseBranchCode())));
            issChildPortalFile.setPacsCode(Long.parseLong(issFileParserList.get(0).getPacsNumber()));
            issChildPortalFile.setPacsName(issFileParserList.get(0).getPacsName());
            issChildPortalFile.setBranchName(issFileParserList.get(0).getBranchName());

            issChildPortalFileRepository.save(issChildPortalFile);

        }

        return applicationLogListToSaveError;
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

        System.out.println("?????????????????????????????" + castCategoryName);
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
        String satBaraSubsurveyNoPattern = ".*[^a-zA-Z].*";
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
}
