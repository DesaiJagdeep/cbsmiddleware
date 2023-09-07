package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.CourtCaseQueryService;
import com.cbs.middleware.service.CourtCaseService;
import com.cbs.middleware.service.criteria.CourtCaseCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
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
 * REST controller for managing {@link com.cbs.middleware.domain.CourtCase}.
 */
@RestController
@RequestMapping("/api")
public class CourtCaseResource {

    private final Logger log = LoggerFactory.getLogger(CourtCaseResource.class);

    private static final String ENTITY_NAME = "courtCase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourtCaseService courtCaseService;

    private final CourtCaseRepository courtCaseRepository;

    private final CourtCaseQueryService courtCaseQueryService;

    @Autowired
    NotificationRepository notificationRepository;

    public CourtCaseResource(
        CourtCaseService courtCaseService,
        CourtCaseRepository courtCaseRepository,
        CourtCaseQueryService courtCaseQueryService
    ) {
        this.courtCaseService = courtCaseService;
        this.courtCaseRepository = courtCaseRepository;
        this.courtCaseQueryService = courtCaseQueryService;
    }

    /**
     * {@code POST  /court-cases} : Create a new courtCase.
     *
     * @param courtCase the courtCase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new courtCase, or with status {@code 400 (Bad Request)} if
     *         the courtCase has already an ID.
     * @throws Exception
     */
    @PostMapping("/court-case-file")
    public ResponseEntity<List<CourtCase>> createCourtCaseFile(
        @RequestParam("file") MultipartFile files,
        RedirectAttributes redirectAttributes
    ) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());

        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        if (courtCaseRepository.existsByFileName(files.getOriginalFilename())) {
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

        File originalFileDir = new File(Constants.ORIGINAL_FILE_PATH);
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
        List<CourtCase> courtCaseList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                CourtCase courtCase = new CourtCase();
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
                        courtCase.setFileName(files.getOriginalFilename());
                        courtCase.setUniqueFileName(uniqueName + "." + fileExtension);
                        falgForFileName = true;
                    }

                    String srNo = getCellValue(row.getCell(0));
                    if (srNo.matches("\\d+")) {
                        courtCase.setSrNo(srNo);
                    }

                    // add logic for skipping records if exists

                    courtCase.setAccountNo(getCellValue(row.getCell(1)));
                    courtCase.setNameOfDefaulter(getCellValue(row.getCell(2)));
                    courtCase.setAddress(getCellValue(row.getCell(3)));
                    courtCase.setLoanType(getCellValue(row.getCell(4)));
                    courtCase.setLoanAmount(getCellAmountValue(row.getCell(5)));
                    courtCase.setLoanDate(getDateCellValue(row.getCell(6)));
                    courtCase.setTermOfLoan(getCellValue(row.getCell(7)));
                    courtCase.setInterestRate(getCellAmountValue(row.getCell(8)));
                    courtCase.setInstallmentAmount(getCellAmountValue(row.getCell(9)));
                    courtCase.setTotalCredit(getCellAmountValue(row.getCell(10)));
                    courtCase.setBalance(getCellAmountValue(row.getCell(11)));
                    courtCase.setInterestPaid(getCellAmountValue(row.getCell(12)));
                    courtCase.setPenalInterestPaid(getCellAmountValue(row.getCell(13)));
                    courtCase.setDueAmount(getCellAmountValue(row.getCell(14)));
                    courtCase.setDueDate(getDateCellValue(row.getCell(15)));
                    courtCase.setDueInterest(getCellAmountValue(row.getCell(16)));
                    courtCase.setDuePenalInterest(getCellAmountValue(row.getCell(17)));
                    courtCase.setDueMoreInterest(getCellAmountValue(row.getCell(18)));
                    courtCase.setInterestRecivable(getCellAmountValue(row.getCell(19)));
                    courtCase.setGaurentorOne(getCellValue(row.getCell(20)));
                    courtCase.setGaurentorOneAddress(getCellValue(row.getCell(21)));
                    courtCase.setGaurentorTwo(getCellValue(row.getCell(22)));
                    courtCase.setGaurentorTwoAddress(getCellValue(row.getCell(23)));
                    courtCase.setFirstNoticeDate(getDateCellValue(row.getCell(24)));
                    courtCase.setSecondNoticeDate(getDateCellValue(row.getCell(25)));

                    courtCaseList.add(courtCase);
                }
            }

            if (!courtCaseList.isEmpty()) {
                courtCaseRepository.saveAll(courtCaseList);

                if (courtCaseList.get(0) != null) {
                    Notification notification = new Notification(
                        "Court Case record file uploaded",
                        "Court Case record file : " + files.getOriginalFilename() + " uploaded",
                        false,
                        courtCaseList.get(0).getCreatedDate(),
                        "", // recipient
                        courtCaseList.get(0).getCreatedBy(), // sender
                        "CourtCaseRecordFileUploaded" // type
                    );
                    notificationRepository.save(notification);
                }

                return ResponseEntity.ok().body(courtCaseList);
            } else {
                throw new BadRequestAlertException("File is already parsed", ENTITY_NAME, "FileExist");
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
    }

    /**
     * {@code POST  /court-cases} : Create a new courtCase.
     *
     * @param courtCase the courtCase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new courtCase, or with status {@code 400 (Bad Request)} if
     *         the courtCase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/court-cases")
    public ResponseEntity<CourtCase> createCourtCase(@RequestBody CourtCase courtCase) throws URISyntaxException {
        log.debug("REST request to save CourtCase : {}", courtCase);
        if (courtCase.getId() != null) {
            throw new BadRequestAlertException("A new courtCase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourtCase result = courtCaseService.save(courtCase);
        return ResponseEntity
            .created(new URI("/api/court-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /court-cases/:id} : Updates an existing courtCase.
     *
     * @param id        the id of the courtCase to save.
     * @param courtCase the courtCase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated courtCase, or with status {@code 400 (Bad Request)} if
     *         the courtCase is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the courtCase couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/court-cases/{id}")
    public ResponseEntity<CourtCase> updateCourtCase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCase courtCase
    ) throws URISyntaxException {
        log.debug("REST request to update CourtCase : {}, {}", id, courtCase);
        if (courtCase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourtCase result = courtCaseService.update(courtCase);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCase.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /court-cases/:id} : Partial updates given fields of an existing
     * courtCase, field will ignore if it is null
     *
     * @param id        the id of the courtCase to save.
     * @param courtCase the courtCase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated courtCase, or with status {@code 400 (Bad Request)} if
     *         the courtCase is not valid, or with status {@code 404 (Not Found)} if
     *         the courtCase is not found, or with status
     *         {@code 500 (Internal Server Error)} if the courtCase couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/court-cases/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourtCase> partialUpdateCourtCase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCase courtCase
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourtCase partially : {}, {}", id, courtCase);
        if (courtCase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourtCase> result = courtCaseService.partialUpdate(courtCase);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCase.getId().toString())
        );
    }

    /**
     * {@code GET  /court-cases} : get all the courtCases.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of courtCases in body.
     */
    @GetMapping("/court-cases")
    public ResponseEntity<List<CourtCase>> getAllCourtCases(
        CourtCaseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourtCases by criteria: {}", criteria);
        Page<CourtCase> page = courtCaseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /court-cases/count} : count all the courtCases.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/court-cases/count")
    public ResponseEntity<Long> countCourtCases(CourtCaseCriteria criteria) {
        log.debug("REST request to count CourtCases by criteria: {}", criteria);
        return ResponseEntity.ok().body(courtCaseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /court-cases/:id} : get the "id" courtCase.
     *
     * @param id the id of the courtCase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the courtCase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/court-cases/{id}")
    public ResponseEntity<CourtCase> getCourtCase(@PathVariable Long id) {
        log.debug("REST request to get CourtCase : {}", id);
        Optional<CourtCase> courtCase = courtCaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courtCase);
    }

    /**
     * {@code DELETE  /court-cases/:id} : delete the "id" courtCase.
     *
     * @param id the id of the courtCase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/court-cases/{id}")
    public ResponseEntity<Void> deleteCourtCase(@PathVariable Long id) {
        log.debug("REST request to delete CourtCase : {}", id);
        courtCaseService.delete(id);
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
