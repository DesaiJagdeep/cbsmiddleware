package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.TalukaMaster;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.TalukaMasterRepository;
import com.cbs.middleware.service.TalukaMasterService;
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
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.TalukaMaster}.
 */
@RestController
@RequestMapping("/api")
public class TalukaMasterResource {

    private final Logger log = LoggerFactory.getLogger(TalukaMasterResource.class);

    private static final String ENTITY_NAME = "talukaMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TalukaMasterService talukaMasterService;

    private final TalukaMasterRepository talukaMasterRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    TranslationServiceUtility translationServiceUtility;

    public TalukaMasterResource(TalukaMasterService talukaMasterService, TalukaMasterRepository talukaMasterRepository) {
        this.talukaMasterService = talukaMasterService;
        this.talukaMasterRepository = talukaMasterRepository;
    }

    /**
     * {@code POST  /taluka-masters} : Create a new talukaMaster.
     *
     * @param talukaMaster the talukaMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new talukaMaster, or with status {@code 400 (Bad Request)} if the talukaMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taluka-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<TalukaMaster> createTalukaMaster(@RequestBody TalukaMaster talukaMaster) throws URISyntaxException {
        log.debug("REST request to save TalukaMaster : {}", talukaMaster);
        if (talukaMaster.getId() != null) {
            throw new BadRequestAlertException("A new talukaMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }

        try {
            talukaMaster.setTalukaNameMr(translationServiceUtility.translationText(talukaMaster.getTalukaName()));
        } catch (Exception e) {}

        TalukaMaster result = talukaMasterService.save(talukaMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Taluka Master Created",
                    "Taluka Master: " + result.getTalukaName() + " Created",
                    false,
                    result.getCreatedDate(),
                    "TalukaMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .created(new URI("/api/taluka-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taluka-masters/:id} : Updates an existing talukaMaster.
     *
     * @param id the id of the talukaMaster to save.
     * @param talukaMaster the talukaMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated talukaMaster,
     * or with status {@code 400 (Bad Request)} if the talukaMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the talukaMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taluka-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<TalukaMaster> updateTalukaMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TalukaMaster talukaMaster
    ) throws URISyntaxException {
        log.debug("REST request to update TalukaMaster : {}, {}", id, talukaMaster);
        if (talukaMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, talukaMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!talukaMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        try {
            System.out.println(
                ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + translationServiceUtility.translationText(talukaMaster.getTalukaName())
            );
            talukaMaster.setTalukaNameMr(translationServiceUtility.translationText(talukaMaster.getTalukaName()));
        } catch (Exception e) {}

        TalukaMaster result = talukaMasterService.update(talukaMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Taluka Master Updated",
                    "Taluka Master: " + result.getTalukaName() + " Updated",
                    false,
                    result.getCreatedDate(),
                    "TalukaMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, talukaMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taluka-masters/:id} : Partial updates given fields of an existing talukaMaster, field will ignore if it is null
     *
     * @param id the id of the talukaMaster to save.
     * @param talukaMaster the talukaMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated talukaMaster,
     * or with status {@code 400 (Bad Request)} if the talukaMaster is not valid,
     * or with status {@code 404 (Not Found)} if the talukaMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the talukaMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taluka-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<TalukaMaster> partialUpdateTalukaMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TalukaMaster talukaMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update TalukaMaster partially : {}, {}", id, talukaMaster);
        if (talukaMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, talukaMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!talukaMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TalukaMaster> result = talukaMasterService.partialUpdate(talukaMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, talukaMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /taluka-masters} : get all the talukaMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of talukaMasters in body.
     */
    @GetMapping("/taluka-masters")
    public ResponseEntity<List<TalukaMaster>> getAllTalukaMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TalukaMasters");
        Page<TalukaMaster> page = talukaMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /taluka-masters/:id} : get the "id" talukaMaster.
     *
     * @param id the id of the talukaMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the talukaMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taluka-masters/{id}")
    public ResponseEntity<TalukaMaster> getTalukaMaster(@PathVariable Long id) {
        log.debug("REST request to get TalukaMaster : {}", id);
        Optional<TalukaMaster> talukaMaster = talukaMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(talukaMaster);
    }

    /**
     * {@code DELETE  /taluka-masters/:id} : delete the "id" talukaMaster.
     *
     * @param id the id of the talukaMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taluka-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteTalukaMaster(@PathVariable Long id) {
        log.debug("REST request to delete TalukaMaster : {}", id);

        Optional<TalukaMaster> talukaMaster = talukaMasterService.findOne(id);
        if (!talukaMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TalukaMaster result = talukaMaster.get();
        talukaMasterService.delete(id);

        try {
            notificationDataUtility.notificationData(
                "Taluka Master Deleted",
                "Taluka Master: " + result.getTalukaName() + " Deleted",
                false,
                result.getCreatedDate(),
                "TalukaMasterDeleted" //type
            );
        } catch (Exception e) {}

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/taluka-file-upload")
    public ResponseEntity<List<TalukaMaster>> createTalukaFile(
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
            boolean flagForLabel = false;
            String talukacode = getCellValue(row.getCell(0));

            if (StringUtils.isNoneBlank(talukacode)) {
                talukacode = talukacode.trim().replace("\n", " ").toLowerCase();
                if (!talukacode.contains("taluka_code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String taluka = getCellValue(row.getCell(1));
            if (StringUtils.isNoneBlank(taluka)) {
                taluka = taluka.trim().replace("\n", " ").toLowerCase();
                if (!taluka.contains("taluka_name")) {
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
        List<TalukaMaster> talukaUploadList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                TalukaMaster talukaMaster = new TalukaMaster();
                if (row != null) {
                    if (StringUtils.isBlank(getCellValue(row.getCell(0))) && StringUtils.isBlank(getCellValue(row.getCell(1)))) {
                        break;
                    }

                    String talukaName = getCellValue(row.getCell(1));

                    if (StringUtils.isNotBlank(talukaName) && !talukaMasterRepository.existsByTalukaName(talukaName)) {
                        talukaMaster.setDistrictCode("12");
                        // english
                        talukaMaster.setTalukaName(talukaName);

                        // marathi
                        talukaMaster.setTalukaNameMr(translationServiceUtility.translationText(talukaName));

                        String talukaCode = getCellValue(row.getCell(0));

                        if (StringUtils.isNotBlank(talukaCode)) {
                            // english
                            talukaMaster.setTalukaCode(talukaCode);

                            // marathi
                            talukaMaster.setTalukaCodeMr(translationServiceUtility.translationText(talukaCode));
                        }

                        talukaMaster = talukaMasterRepository.save(talukaMaster);
                    }

                    talukaUploadList.add(talukaMaster);
                }
            }

            if (!talukaUploadList.isEmpty()) {
                if (talukaUploadList.get(0) != null) {
                    try {
                        notificationDataUtility.notificationData(
                            "taluka master file uploaded",
                            "taluka master file : " + files.getOriginalFilename() + " uploaded",
                            false,
                            talukaUploadList.get(0).getCreatedDate(),
                            "talukaMasterFileUploaded"
                        );
                    } catch (Exception e) {}
                }

                return ResponseEntity.ok().body(talukaUploadList);
            } else {
                throw new BadRequestAlertException("File is already parsed", ENTITY_NAME, "FileExist");
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
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
