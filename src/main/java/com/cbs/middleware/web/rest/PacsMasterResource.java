package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.PacsMaster;
import com.cbs.middleware.repository.BankBranchMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.service.PacsMasterService;
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
import javax.websocket.server.PathParam;
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
 * REST controller for managing {@link com.cbs.middleware.domain.PacsMaster}.
 */
@RestController
@RequestMapping("/api")
public class PacsMasterResource {

    private final Logger log = LoggerFactory.getLogger(PacsMasterResource.class);

    private static final String ENTITY_NAME = "pacsMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PacsMasterService pacsMasterService;

    private final PacsMasterRepository pacsMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    @Autowired
    BankBranchMasterRepository bankBranchMasterRepository;

    @Autowired
    TranslationServiceUtility translationServiceUtility;

    public PacsMasterResource(PacsMasterService pacsMasterService, PacsMasterRepository pacsMasterRepository) {
        this.pacsMasterService = pacsMasterService;
        this.pacsMasterRepository = pacsMasterRepository;
    }

    /**
     * {@code POST  /pacs-masters} : Create a new pacsMaster.
     *
     * @param pacsMaster the pacsMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pacsMaster, or with status {@code 400 (Bad Request)} if the pacsMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pacs-masters")
    public ResponseEntity<PacsMaster> createPacsMaster(@RequestBody PacsMaster pacsMaster) throws URISyntaxException {
        log.debug("REST request to save PacsMaster : {}", pacsMaster);
        if (pacsMaster.getId() != null) {
            throw new BadRequestAlertException("A new pacsMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacsMaster result = pacsMasterService.save(pacsMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Pacs Master Created",
                    "Pacs Master: " + result.getPacsName() + " Created",
                    false,
                    result.getCreatedDate(),
                    "PacsMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .created(new URI("/api/pacs-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pacs-masters/:id} : Updates an existing pacsMaster.
     *
     * @param id the id of the pacsMaster to save.
     * @param pacsMaster the pacsMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pacsMaster,
     * or with status {@code 400 (Bad Request)} if the pacsMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pacsMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pacs-masters/{id}")
    public ResponseEntity<PacsMaster> updatePacsMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PacsMaster pacsMaster
    ) throws URISyntaxException {
        log.debug("REST request to update PacsMaster : {}, {}", id, pacsMaster);
        if (pacsMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pacsMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacsMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PacsMaster result = pacsMasterService.update(pacsMaster);
        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Pacs Master Created",
                    "Pacs Master: " + result.getPacsName() + " Updated",
                    false,
                    result.getCreatedDate(),
                    "PacsMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pacsMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pacs-masters/:id} : Partial updates given fields of an existing pacsMaster, field will ignore if it is null
     *
     * @param id the id of the pacsMaster to save.
     * @param pacsMaster the pacsMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pacsMaster,
     * or with status {@code 400 (Bad Request)} if the pacsMaster is not valid,
     * or with status {@code 404 (Not Found)} if the pacsMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the pacsMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pacs-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PacsMaster> partialUpdatePacsMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PacsMaster pacsMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update PacsMaster partially : {}, {}", id, pacsMaster);
        if (pacsMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pacsMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacsMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PacsMaster> result = pacsMasterService.partialUpdate(pacsMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pacsMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /pacs-masters} : get all the pacsMasters.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pacsMasters in body.
     */
    @GetMapping("/pacs-masters")
    public ResponseEntity<List<PacsMaster>> getAllPacsMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of PacsMasters");
        Page<PacsMaster> page;
        if (eagerload) {
            page = pacsMasterService.findAllWithEagerRelationships(pageable);
        } else {
            page = pacsMasterService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/pacs-master")
    public ResponseEntity<List<PacsMaster>> getAllPacsMasterByBranchName(@PathParam(value = "branchName") String branchName) {
        log.debug("REST request to get a page of PacsMasters");
        List<PacsMaster> page;
        page = pacsMasterRepository.findAllByBranchName(branchName);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /pacs-masters/:id} : get the "id" pacsMaster.
     *
     * @param id the id of the pacsMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pacsMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pacs-masters/{id}")
    public ResponseEntity<PacsMaster> getPacsMaster(@PathVariable Long id) {
        log.debug("REST request to get PacsMaster : {}", id);
        Optional<PacsMaster> pacsMaster = pacsMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pacsMaster);
    }

    /**
     * {@code DELETE  /pacs-masters/:id} : delete the "id" pacsMaster.
     *
     * @param id the id of the pacsMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pacs-masters/{id}")
    public ResponseEntity<Void> deletePacsMaster(@PathVariable Long id) {
        log.debug("REST request to delete PacsMaster : {}", id);

        Optional<PacsMaster> pacsMaster = pacsMasterService.findOne(id);
        if (!pacsMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        PacsMaster result = pacsMaster.get();

        pacsMasterService.delete(id);

        try {
            notificationDataUtility.notificationData(
                "Pacs Master Deleted",
                "Pacs Master: " + result.getPacsName() + " Deleted",
                false,
                result.getCreatedDate(),
                "PacsMasterDeleted" //type
            );
        } catch (Exception e) {}

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/pacs-file-upload")
    public ResponseEntity<List<PacsMaster>> createPacsMasterFile(
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

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + talukacode);

            if (StringUtils.isNoneBlank(talukacode)) {
                talukacode = talukacode.trim().replace("\n", " ").toLowerCase();
                if (!talukacode.contains("taluka_code")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            if (flagForLabel) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }
        } catch (BadRequestAlertException e) {
            e.printStackTrace();
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
        List<PacsMaster> pacsMasterMasterUploadList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                PacsMaster pacsMaster = new PacsMaster();
                if (row != null) {
                    if (StringUtils.isBlank(getCellValue(row.getCell(0))) && StringUtils.isBlank(getCellValue(row.getCell(1)))) {
                        break;
                    }

                    String pacsNumber = getCellValue(row.getCell(4));

                    if (StringUtils.isNotBlank(pacsNumber) && !pacsMasterRepository.existsByPacsNumber(pacsNumber)) {
                        String bankBranch = getCellValue(row.getCell(3));
                        BankBranchMaster bankBranchMaster = bankBranchMasterRepository.findOneByBranchName(bankBranch);

                        pacsMaster.setBankBranchMaster(bankBranchMaster);

                        String pacsName = getCellValue(row.getCell(5));
                        // english
                        pacsMaster.setPacsName(pacsName);

                        // marathi
                        pacsMaster.setPacsNameMr(translationServiceUtility.translationText(pacsName));

                        pacsMaster.setPacsNumber(pacsNumber);

                        pacsMaster = pacsMasterRepository.save(pacsMaster);
                    }

                    pacsMasterMasterUploadList.add(pacsMaster);
                }
            }

            if (!pacsMasterMasterUploadList.isEmpty()) {
                if (pacsMasterMasterUploadList.get(0) != null) {
                    try {
                        notificationDataUtility.notificationData(
                            "taluka master file uploaded",
                            "taluka master file : " + files.getOriginalFilename() + " uploaded",
                            false,
                            pacsMasterMasterUploadList.get(0).getCreatedDate(),
                            "talukaMasterFileUploaded"
                        );
                    } catch (Exception e) {}
                }

                return ResponseEntity.ok().body(pacsMasterMasterUploadList);
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
