package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.KMPUpload;
import com.cbs.middleware.domain.KarkhanaVasuli;
import com.cbs.middleware.repository.KarkhanaVasuliRepository;
import com.cbs.middleware.service.KarkhanaVasuliQueryService;
import com.cbs.middleware.service.KarkhanaVasuliService;
import com.cbs.middleware.service.criteria.KarkhanaVasuliCriteria;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.KarkhanaVasuli}.
 */
@RestController
@RequestMapping("/api")
public class KarkhanaVasuliResource {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliResource.class);

    private static final String ENTITY_NAME = "karkhanaVasuli";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KarkhanaVasuliService karkhanaVasuliService;

    private final KarkhanaVasuliRepository karkhanaVasuliRepository;

    private final KarkhanaVasuliQueryService karkhanaVasuliQueryService;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    @Autowired
    TranslationServiceUtility translationServiceUtility;

    public KarkhanaVasuliResource(
        KarkhanaVasuliService karkhanaVasuliService,
        KarkhanaVasuliRepository karkhanaVasuliRepository,
        KarkhanaVasuliQueryService karkhanaVasuliQueryService
    ) {
        this.karkhanaVasuliService = karkhanaVasuliService;
        this.karkhanaVasuliRepository = karkhanaVasuliRepository;
        this.karkhanaVasuliQueryService = karkhanaVasuliQueryService;
    }

    /**
     * {@code POST  /karkhana-vasulis} : Create a new karkhanaVasuli.
     *
     * @param karkhanaVasuli the karkhanaVasuli to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new karkhanaVasuli, or with status {@code 400 (Bad Request)} if the karkhanaVasuli has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/karkhana-vasuli-file-upload")
    public ResponseEntity<List<KarkhanaVasuli>> uploadKarkhanaVasuliFileUploadFile(
        @RequestParam("file") MultipartFile files,
        RedirectAttributes redirectAttributes
    ) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());

        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        if (karkhanaVasuliRepository.existsByFileName(files.getOriginalFilename())) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(2); // Get the current row
            boolean flagForLabel = false;
            String karkhanaName = getCellValue(row.getCell(0));

            if (StringUtils.isNotBlank(karkhanaName)) {
                karkhanaName = karkhanaName.trim().replace("\n", " ").toLowerCase();
                if (!karkhanaName.contains("karkhana") || !karkhanaName.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String khataNumber = getCellValue(row.getCell(5));
            if (StringUtils.isNoneBlank(khataNumber)) {
                khataNumber = khataNumber.trim().replace("\n", " ").toLowerCase();
                if (!khataNumber.contains("khata") || !khataNumber.contains("number")) {
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

        File originalFileDir = new File(Constants.KARKHANA_VASULI_FILE_PATH);
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
        List<KarkhanaVasuli> karkhanaUploadList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                KarkhanaVasuli KarkhanaVasuli = new KarkhanaVasuli();
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
                        KarkhanaVasuli.setFileName(files.getOriginalFilename());
                        KarkhanaVasuli.setUniqueFileName(uniqueName + "." + fileExtension);
                        falgForFileName = true;
                    }

                    String karkhanaNAme = getCellValue(row.getCell(0));
                    if (StringUtils.isNotEmpty(karkhanaNAme)) {
                        // english
                        KarkhanaVasuli.setKarkhanaNameEn(karkhanaNAme);

                        // marathi
                        KarkhanaVasuli.setKarkhanaName(translationServiceUtility.translationText(karkhanaNAme));
                    }

                    // add logic for skipping records if exists
                    String societyName = getCellValue(row.getCell(1));
                    if (StringUtils.isNotBlank(societyName)) {
                        // english
                        KarkhanaVasuli.setSocietyNameEn(societyName);

                        // marathi
                        KarkhanaVasuli.setSocietyName(translationServiceUtility.translationText(societyName));
                    }

                    String talukaName = getCellValue(row.getCell(2));
                    if (StringUtils.isNotBlank(talukaName)) {
                        // english
                        KarkhanaVasuli.setTalukaNameEn(talukaName);
                        // marathi
                        KarkhanaVasuli.setTalukaName(translationServiceUtility.translationText(talukaName));
                    }

                    String branchName = getCellValue(row.getCell(3));
                    if (StringUtils.isNotBlank(branchName)) {
                        // english
                        KarkhanaVasuli.setBranchNameEn(branchName);
                        // marathi
                        KarkhanaVasuli.setBranchName(translationServiceUtility.translationText(branchName));
                    }

                    String defaulterName = getCellValue(row.getCell(4));
                    if (StringUtils.isNotBlank(talukaName)) {
                        // english
                        KarkhanaVasuli.setDefaulterNameEn(defaulterName);
                        // marathi
                        KarkhanaVasuli.setDefaulterName(translationServiceUtility.translationText(defaulterName));
                    }

                    String khataNumber = getCellValue(row.getCell(5));
                    if (StringUtils.isNotBlank(talukaName)) {
                        // english
                        KarkhanaVasuli.setKhataNumberEn(khataNumber);
                        // marathi
                        KarkhanaVasuli.setKhataNumber(translationServiceUtility.translationText(khataNumber));
                    }

                    karkhanaUploadList.add(KarkhanaVasuli);
                }
            }

            if (!karkhanaUploadList.isEmpty()) {
                karkhanaVasuliRepository.saveAll(karkhanaUploadList);

                if (karkhanaUploadList.get(0) != null) {
                    try {
                        notificationDataUtility.notificationData(
                            "Kamal Marayada Patrak file uploaded",
                            "Kamal Marayada Patrak file : " + files.getOriginalFilename() + " uploaded",
                            false,
                            karkhanaUploadList.get(0).getCreatedDate(),
                            "KamalMarayadaPatrakFileUploaded" // type
                        );
                    } catch (Exception e) {}
                }

                return ResponseEntity.ok().body(karkhanaUploadList);
            } else {
                throw new BadRequestAlertException("File is already parsed", ENTITY_NAME, "FileExist");
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
    }

    /**
     * {@code POST  /karkhana-vasulis} : Create a new karkhanaVasuli.
     *
     * @param karkhanaVasuli the karkhanaVasuli to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new karkhanaVasuli, or with status {@code 400 (Bad Request)} if the karkhanaVasuli has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/karkhana-vasulis")
    public ResponseEntity<KarkhanaVasuli> createKarkhanaVasuli(@RequestBody KarkhanaVasuli karkhanaVasuli) throws URISyntaxException {
        log.debug("REST request to save KarkhanaVasuli : {}", karkhanaVasuli);
        if (karkhanaVasuli.getId() != null) {
            throw new BadRequestAlertException("A new karkhanaVasuli cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KarkhanaVasuli result = karkhanaVasuliService.save(karkhanaVasuli);
        return ResponseEntity
            .created(new URI("/api/karkhana-vasulis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /karkhana-vasulis/:id} : Updates an existing karkhanaVasuli.
     *
     * @param id the id of the karkhanaVasuli to save.
     * @param karkhanaVasuli the karkhanaVasuli to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated karkhanaVasuli,
     * or with status {@code 400 (Bad Request)} if the karkhanaVasuli is not valid,
     * or with status {@code 500 (Internal Server Error)} if the karkhanaVasuli couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/karkhana-vasulis/{id}")
    public ResponseEntity<KarkhanaVasuli> updateKarkhanaVasuli(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KarkhanaVasuli karkhanaVasuli
    ) throws URISyntaxException {
        log.debug("REST request to update KarkhanaVasuli : {}, {}", id, karkhanaVasuli);
        if (karkhanaVasuli.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, karkhanaVasuli.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!karkhanaVasuliRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KarkhanaVasuli result = karkhanaVasuliService.update(karkhanaVasuli);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, karkhanaVasuli.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /karkhana-vasulis/:id} : Partial updates given fields of an existing karkhanaVasuli, field will ignore if it is null
     *
     * @param id the id of the karkhanaVasuli to save.
     * @param karkhanaVasuli the karkhanaVasuli to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated karkhanaVasuli,
     * or with status {@code 400 (Bad Request)} if the karkhanaVasuli is not valid,
     * or with status {@code 404 (Not Found)} if the karkhanaVasuli is not found,
     * or with status {@code 500 (Internal Server Error)} if the karkhanaVasuli couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/karkhana-vasulis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KarkhanaVasuli> partialUpdateKarkhanaVasuli(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KarkhanaVasuli karkhanaVasuli
    ) throws URISyntaxException {
        log.debug("REST request to partial update KarkhanaVasuli partially : {}, {}", id, karkhanaVasuli);
        if (karkhanaVasuli.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, karkhanaVasuli.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!karkhanaVasuliRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KarkhanaVasuli> result = karkhanaVasuliService.partialUpdate(karkhanaVasuli);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, karkhanaVasuli.getId().toString())
        );
    }

    /**
     * {@code GET  /karkhana-vasulis} : get all the karkhanaVasulis.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of karkhanaVasulis in body.
     */
    @GetMapping("/karkhana-vasulis")
    public ResponseEntity<List<KarkhanaVasuli>> getAllKarkhanaVasulis(
        KarkhanaVasuliCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KarkhanaVasulis by criteria: {}", criteria);
        Page<KarkhanaVasuli> page = karkhanaVasuliQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /karkhana-vasulis/count} : count all the karkhanaVasulis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/karkhana-vasulis/count")
    public ResponseEntity<Long> countKarkhanaVasulis(KarkhanaVasuliCriteria criteria) {
        log.debug("REST request to count KarkhanaVasulis by criteria: {}", criteria);
        return ResponseEntity.ok().body(karkhanaVasuliQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /karkhana-vasulis/:id} : get the "id" karkhanaVasuli.
     *
     * @param id the id of the karkhanaVasuli to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the karkhanaVasuli, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/karkhana-vasulis/{id}")
    public ResponseEntity<KarkhanaVasuli> getKarkhanaVasuli(@PathVariable Long id) {
        log.debug("REST request to get KarkhanaVasuli : {}", id);
        Optional<KarkhanaVasuli> karkhanaVasuli = karkhanaVasuliService.findOne(id);
        return ResponseUtil.wrapOrNotFound(karkhanaVasuli);
    }

    /**
     * {@code DELETE  /karkhana-vasulis/:id} : delete the "id" karkhanaVasuli.
     *
     * @param id the id of the karkhanaVasuli to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/karkhana-vasulis/{id}")
    public ResponseEntity<Void> deleteKarkhanaVasuli(@PathVariable Long id) {
        log.debug("REST request to delete KarkhanaVasuli : {}", id);
        karkhanaVasuliService.delete(id);
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
