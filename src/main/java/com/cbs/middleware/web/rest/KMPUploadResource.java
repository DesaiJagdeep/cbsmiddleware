package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.KMPUpload;
import com.cbs.middleware.repository.KMPUploadRepository;
import com.cbs.middleware.service.KMPUploadService;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
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
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.KMPUpload}.
 */
@RestController
@RequestMapping("/api")
public class KMPUploadResource {

    private final Logger log = LoggerFactory.getLogger(KMPUploadResource.class);

    private static final String ENTITY_NAME = "kMPUpload";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KMPUploadService kMPUploadService;

    private final KMPUploadRepository kMPUploadRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    @Autowired
    TranslationServiceUtility translationServiceUtility;

    public KMPUploadResource(KMPUploadService kMPUploadService, KMPUploadRepository kMPUploadRepository) {
        this.kMPUploadService = kMPUploadService;
        this.kMPUploadRepository = kMPUploadRepository;
    }

    /**
     * {@code POST  /kmp-uploads} : Create a new kMPUpload.
     *
     * @param kMPUpload the kMPUpload to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kMPUpload, or with status {@code 400 (Bad Request)} if the kMPUpload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/kmp-file-upload")
    public ResponseEntity<List<KMPUpload>> createKMPFile(@RequestParam("file") MultipartFile files, RedirectAttributes redirectAttributes)
        throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());

        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        if (kMPUploadRepository.existsByFileName(files.getOriginalFilename())) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(2); // Get the current row
            boolean flagForLabel = false;
            String srNo = getCellValue(row.getCell(0));

            if (StringUtils.isNotBlank(srNo)) {
                srNo = srNo.trim().replace("\n", " ").toLowerCase();
                if (!srNo.contains("sr") || !srNo.contains("no")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String secondNoticeDate = getCellValue(row.getCell(25));
            if (StringUtils.isNoneBlank(secondNoticeDate)) {
                secondNoticeDate = secondNoticeDate.trim().replace("\n", " ").toLowerCase();
                if (!secondNoticeDate.contains("second") || !secondNoticeDate.contains("notice") || !secondNoticeDate.contains("date")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String taluka = getCellValue(row.getCell(27));
            if (StringUtils.isNoneBlank(taluka)) {
                taluka = taluka.trim().replace("\n", " ").toLowerCase();
                if (!taluka.contains("taluka") || !taluka.contains("taluka") || !taluka.contains("taluka")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            if (flagForLabel) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }
        } catch (BadRequestAlertException e) {
            throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (ForbiddenAuthRequestAlertException e) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (UnAuthRequestAlertException e) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (Exception e) {
            throw new Exception("Exception", e);
        }

        File originalFileDir = new File(Constants.KMP_FILE_PATH);
        if (!originalFileDir.isDirectory()) {
            originalFileDir.mkdirs();
        }

        String filePath = originalFileDir.toString();
        boolean falgForFileName = false;
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

        int startRowIndex = 3; // Starting row index
        List<KMPUpload> kmpUploadList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                KMPUpload kmpUpload = new KMPUpload();
                if (row != null) {
                    if (
                        StringUtils.isBlank(getCellValue(row.getCell(0))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(1))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(2))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(3)))
                    ) {
                        break;
                    }

                    if (!falgForFileName) {
                        kmpUpload.setFileName(files.getOriginalFilename());
                        kmpUpload.setUniqueFileName(uniqueName + "." + fileExtension);
                        falgForFileName = true;
                    }

                    String srNo = getCellValue(row.getCell(0));
                    if (srNo.matches("\\d+")) {
                        //courtCase.setSrNo(srNo);
                    }

                    // add logic for skipping records if exists

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(1)))) {
                        // english
                        // courtCase.setAccountNoEn(getCellValue(row.getCell(1)));

                        // marathi
                        // courtCase.setAccountNo(translationServiceUtility.translationText(getCellValue(row.getCell(1))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(2)))) {
                        // english
                        // courtCase.setNameOfDefaulterEn(getCellValue(row.getCell(2)));
                        // marathi
                        // courtCase.setNameOfDefaulter(translationServiceUtility.translationText(getCellValue(row.getCell(2))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(3)))) {
                        // english
                        // courtCase.setAddressEn(getCellValue(row.getCell(3)));

                        // marathi
                        // courtCase.setAddress(translationServiceUtility.translationText(getCellValue(row.getCell(3))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(4)))) {
                        // english
                        // courtCase.setLoanTypeEn(getCellValue(row.getCell(4)));

                        // marathi
                        // courtCase.setLoanType(translationServiceUtility.translationText(getCellValue(row.getCell(4))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(5)))) {
                        // english
                        // courtCase.setLoanAmountEn(getCellAmountValue(row.getCell(5)));

                        // marathi
                        // courtCase.setLoanAmount(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(5))));
                    }

                    if (getDateCellValue(row.getCell(6)) != null) {
                        // courtCase.setLoanDate(getDateCellValue(row.getCell(6)));
                    }
                    if (StringUtils.isNotBlank(getCellValue(row.getCell(7)))) {
                        // english
                        // courtCase.setTermOfLoanEn(getCellValue(row.getCell(7)));

                        // marathi
                        // courtCase.setTermOfLoan(translationServiceUtility.translationText(getCellValue(row.getCell(7))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(8)))) {
                        // english
                        // courtCase.setInterestRateEn(getCellAmountValue(row.getCell(8)));

                        // marathi
                        // courtCase.setInterestRate(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(8))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(9)))) {
                        // english
                        // courtCase.setInstallmentAmountEn(getCellAmountValue(row.getCell(9)));

                        // marathi
                        // courtCase.setInstallmentAmount(
                        //    translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(9)))
                        //);
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(10)))) {
                        // english
                        // courtCase.setTotalCreditEn(getCellAmountValue(row.getCell(10)));

                        // marathi
                        // courtCase.setTotalCredit(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(10))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(11)))) {
                        // english
                        // courtCase.setBalanceEn(getCellAmountValue(row.getCell(11)));

                        // marathi
                        // courtCase.setBalance(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(11))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(12)))) {
                        // english
                        // courtCase.setInterestPaidEn(getCellAmountValue(row.getCell(12)));

                        // marathi
                        // courtCase.setInterestPaid(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(12))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(13)))) {
                        // english
                        // courtCase.setPenalInterestPaidEn(getCellAmountValue(row.getCell(13)));

                        // marathi
                        // courtCase.setPenalInterestPaid(
                        //    translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(13)))
                        // );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(14)))) {
                        // english
                        // courtCase.setDueAmountEn(getCellAmountValue(row.getCell(14)));

                        // marathi
                        // courtCase.setDueAmount(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(14))));
                    }

                    if (getDateCellValue(row.getCell(15)) != null) {
                        // courtCase.setDueDate(getDateCellValue(row.getCell(15)));
                    }
                    if (StringUtils.isNotBlank(getCellValue(row.getCell(16)))) {
                        // english
                        // courtCase.setDueInterestEn(getCellAmountValue(row.getCell(16)));

                        // marathi
                        // courtCase.setDueInterest(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(16))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(17)))) {
                        // english
                        // courtCase.setDuePenalInterestEn(getCellAmountValue(row.getCell(17)));
                        // marathi
                        // courtCase.setDuePenalInterest(
                        //    translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(17)))
                        //);
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(18)))) {
                        // english
                        // courtCase.setDueMoreInterestEn(getCellAmountValue(row.getCell(18)));
                        // marathi
                        // courtCase.setDueMoreInterest(
                        //    translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(18)))
                        // );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(19)))) {
                        // english
                        // courtCase.setInterestRecivableEn(getCellAmountValue(row.getCell(19)));
                        // marathi
                        // courtCase.setInterestRecivable(
                        //     translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(19)))
                        //);
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(20)))) {
                        // english
                        // courtCase.setGaurentorOneEn(getCellValue(row.getCell(20)));
                        // marathi
                        // courtCase.setGaurentorOne(translationServiceUtility.translationText(getCellValue(row.getCell(20))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(21)))) {
                        // english
                        // courtCase.setGaurentorOneAddressEn(getCellValue(row.getCell(21)));
                        // marathi
                        // courtCase.setGaurentorOneAddress(translationServiceUtility.translationText(getCellValue(row.getCell(21))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(22)))) {
                        // english
                        // courtCase.setGaurentorTwoEn(getCellValue(row.getCell(22)));
                        // marathi
                        // courtCase.setGaurentorTwo(translationServiceUtility.translationText(getCellValue(row.getCell(22))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(23)))) {
                        // english
                        // courtCase.setGaurentorTwoAddressEn(getCellValue(row.getCell(23)));
                        // marathi
                        // courtCase.setGaurentorTwoAddress(translationServiceUtility.translationText(getCellValue(row.getCell(23))));
                    }

                    if (getDateCellValue(row.getCell(24)) != null) {
                        // courtCase.setFirstNoticeDate(getDateCellValue(row.getCell(24)));
                    }
                    if (getDateCellValue(row.getCell(25)) != null) {
                        // courtCase.setSecondNoticeDate(getDateCellValue(row.getCell(25)));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(26)))) {
                        // english
                        // courtCase.setBankNameEn(getCellValue(row.getCell(26)));
                        // marathi
                        // courtCase.setBankName(translationServiceUtility.translationText(getCellValue(row.getCell(26))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(27)))) {
                        // english
                        // courtCase.setTalukaNameEn(getCellValue(row.getCell(27)));
                        // marathi
                        // courtCase.setTalukaName(translationServiceUtility.translationText(getCellValue(row.getCell(27))));
                    }

                    kmpUploadList.add(kmpUpload);
                }
            }

            if (!kmpUploadList.isEmpty()) {
                kMPUploadRepository.saveAll(kmpUploadList);

                if (kmpUploadList.get(0) != null) {
                    try {
                        notificationDataUtility.notificationData(
                            "Kamal Marayada Patrak file uploaded",
                            "Kamal Marayada Patrak file : " + files.getOriginalFilename() + " uploaded",
                            false,
                            kmpUploadList.get(0).getCreatedDate(),
                            "KamalMarayadaPatrakFileUploaded" // type
                        );
                    } catch (Exception e) {}
                }

                return ResponseEntity.ok().body(kmpUploadList);
            } else {
                throw new BadRequestAlertException("File is already parsed", ENTITY_NAME, "FileExist");
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
    }

    /**
     * {@code POST  /kmp-uploads} : Create a new kMPUpload.
     *
     * @param kMPUpload the kMPUpload to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kMPUpload, or with status {@code 400 (Bad Request)} if the kMPUpload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/kmp-uploads")
    public ResponseEntity<KMPUpload> createKMPUpload(@RequestBody KMPUpload kMPUpload) throws URISyntaxException {
        log.debug("REST request to save KMPUpload : {}", kMPUpload);
        if (kMPUpload.getId() != null) {
            throw new BadRequestAlertException("A new kMPUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KMPUpload result = kMPUploadService.save(kMPUpload);
        return ResponseEntity
            .created(new URI("/api/kmp-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kmp-uploads/:id} : Updates an existing kMPUpload.
     *
     * @param id the id of the kMPUpload to save.
     * @param kMPUpload the kMPUpload to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kMPUpload,
     * or with status {@code 400 (Bad Request)} if the kMPUpload is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kMPUpload couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kmp-uploads/{id}")
    public ResponseEntity<KMPUpload> updateKMPUpload(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KMPUpload kMPUpload
    ) throws URISyntaxException {
        log.debug("REST request to update KMPUpload : {}, {}", id, kMPUpload);
        if (kMPUpload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kMPUpload.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kMPUploadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KMPUpload result = kMPUploadService.update(kMPUpload);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kMPUpload.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kmp-uploads/:id} : Partial updates given fields of an existing kMPUpload, field will ignore if it is null
     *
     * @param id the id of the kMPUpload to save.
     * @param kMPUpload the kMPUpload to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kMPUpload,
     * or with status {@code 400 (Bad Request)} if the kMPUpload is not valid,
     * or with status {@code 404 (Not Found)} if the kMPUpload is not found,
     * or with status {@code 500 (Internal Server Error)} if the kMPUpload couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kmp-uploads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KMPUpload> partialUpdateKMPUpload(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KMPUpload kMPUpload
    ) throws URISyntaxException {
        log.debug("REST request to partial update KMPUpload partially : {}, {}", id, kMPUpload);
        if (kMPUpload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kMPUpload.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kMPUploadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KMPUpload> result = kMPUploadService.partialUpdate(kMPUpload);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kMPUpload.getId().toString())
        );
    }

    /**
     * {@code GET  /kmp-uploads} : get all the kMPUploads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kMPUploads in body.
     */
    @GetMapping("/kmp-uploads")
    public ResponseEntity<List<KMPUpload>> getAllKMPUploads(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of KMPUploads");
        Page<KMPUpload> page = kMPUploadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kmp-uploads/:id} : get the "id" kMPUpload.
     *
     * @param id the id of the kMPUpload to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kMPUpload, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kmp-uploads/{id}")
    public ResponseEntity<KMPUpload> getKMPUpload(@PathVariable Long id) {
        log.debug("REST request to get KMPUpload : {}", id);
        Optional<KMPUpload> kMPUpload = kMPUploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kMPUpload);
    }

    /**
     * {@code DELETE  /kmp-uploads/:id} : delete the "id" kMPUpload.
     *
     * @param id the id of the kMPUpload to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kmp-uploads/{id}")
    public ResponseEntity<Void> deleteKMPUpload(@PathVariable Long id) {
        log.debug("REST request to delete KMPUpload : {}", id);
        kMPUploadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
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

    private static Double getCellAmountValue(Cell cell) {
        Double cellValue = 0.0;

        if (cell == null) {
            cellValue = 0.0;
        } else if (cell.getCellType() == CellType.STRING) {
            String cellValueInString = cell.getStringCellValue().trim();

            try {
                cellValue = Double.parseDouble(cellValueInString);
            } catch (Exception e) {}
        } else if (cell.getCellType() == CellType.NUMERIC) {
            double numericValue = cell.getNumericCellValue();
            // Convert it to a Double
            cellValue = numericValue;
        } else if (cell.getCellType() == CellType.BLANK) {
            cellValue = 0.0;
        }

        return cellValue;
    }

    private static String getCellAmountValueInString(Cell cell) {
        String cellValue = "0.0";

        if (cell == null) {
            return cellValue;
        } else if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            double numericValue = cell.getNumericCellValue();
            // Convert it to a Double
            return "" + numericValue;
        } else if (cell.getCellType() == CellType.BLANK) {
            return cellValue;
        } else {
            return cellValue;
        }
    }

    private static LocalDate getDateCellValue(Cell cell) {
        LocalDate localDate = null;

        if (cell == null) {
            localDate = null;
        } else if (cell.getCellType() == CellType.STRING) {
            Pattern patternYYYYMMDD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            Pattern patternDDMMYYYY = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
            if (patternYYYYMMDD.matcher(cell.getStringCellValue()).matches()) { // yyyy-MM-dd
                localDate = LocalDate.parse(cell.getStringCellValue());
            } else if (patternDDMMYYYY.matcher(cell.getStringCellValue()).matches()) { // dd/mm/yyyy
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                localDate = LocalDate.parse(cell.getStringCellValue(), inputFormatter);
            }
        } else if (cell.getCellType() == CellType.NUMERIC) {
            localDate = LocalDate.of(1900, 1, 1).plusDays((long) cell.getNumericCellValue() - 2);
        } else if (cell.getCellType() == CellType.FORMULA) {
            localDate = LocalDate.of(1900, 1, 1).plusDays((long) cell.getNumericCellValue() - 2);
        } else if (cell.getCellType() == CellType.BLANK) {
            localDate = null;
        }

        return localDate;
    }
}
