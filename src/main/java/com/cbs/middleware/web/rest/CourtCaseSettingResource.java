package com.cbs.middleware.web.rest;

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
import java.util.Map;
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
//import com.knf.dev.demo.helper.WordHelper;
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

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.domain.CourtCaseSetting;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.repository.CourtCaseSettingRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.CourtCaseSettingQueryService;
import com.cbs.middleware.service.CourtCaseSettingService;
import com.cbs.middleware.service.criteria.CourtCaseSettingCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.BankBranchPacksCodeGet;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.cbs.middleware.domain.CourtCaseSetting}.
 */
@RestController
@RequestMapping("/api")
public class CourtCaseSettingResource {

    private final Logger log = LoggerFactory.getLogger(CourtCaseSettingResource.class);

    private static final String ENTITY_NAME = "courtCaseSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourtCaseSettingService courtCaseSettingService;

    private final CourtCaseSettingRepository courtCaseSettingRepository;

    private final CourtCaseSettingQueryService courtCaseSettingQueryService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    @Autowired
    TranslationServiceUtility translationServiceUtility;
    
    @Autowired
    BankBranchPacksCodeGet bankBranchPacksCodeGet;
    
    @Autowired
    CourtCaseRepository courtCaseRepository;

    public CourtCaseSettingResource(
        CourtCaseSettingService courtCaseSettingService,
        CourtCaseSettingRepository courtCaseSettingRepository,
        CourtCaseSettingQueryService courtCaseSettingQueryService
    ) {
        this.courtCaseSettingService = courtCaseSettingService;
        this.courtCaseSettingRepository = courtCaseSettingRepository;
        this.courtCaseSettingQueryService = courtCaseSettingQueryService;
    }

    /**
     * {@code POST  /court-case-settings} : Create a new courtCaseSetting.
     *
     * @param courtCaseSetting the courtCaseSetting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new courtCaseSetting, or with status
     *         {@code 400 (Bad Request)} if the courtCaseSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

   

    @PostMapping("/court-case-setting-file")
    public ResponseEntity<List<CourtCaseSetting>> createCourtCaseFile(
        @RequestParam("file") MultipartFile files,
        RedirectAttributes redirectAttributes
    ) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());

        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        if (courtCaseSettingRepository.existsByFileName(files.getOriginalFilename())) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(2); // Get the current row
            boolean flagForLabel = false;
            String srNo = getCellValue(row.getCell(0));
            if (StringUtils.isNotBlank(srNo)) {
                srNo = srNo.trim().replace("\n", " ").toLowerCase();
                if (!srNo.contains("vasuli") || !srNo.contains("adhikari") || !srNo.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String secondNoticeDate = getCellValue(row.getCell(13));
            if (StringUtils.isNoneBlank(secondNoticeDate)) {
                secondNoticeDate = secondNoticeDate.trim().replace("\n", " ").toLowerCase();
                if (!secondNoticeDate.contains("meeting") || !secondNoticeDate.contains("time")) {
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
        List<CourtCaseSetting> courtCaseSettingList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                CourtCaseSetting courtCaseSetting = new CourtCaseSetting();
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
                        courtCaseSetting.setFileName(files.getOriginalFilename());
                        courtCaseSetting.setUniqueFileName(uniqueName + "." + fileExtension);
                        falgForFileName = true;
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(0)))) {
                        //English
                        courtCaseSetting.setVasuliAdhikariNameEn(getCellValue(row.getCell(0)));

                        //Marathi
                        courtCaseSetting.setVasuliAdhikariName(translationServiceUtility.translationText(getCellValue(row.getCell(0))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(1)))) {
                        //English
                        courtCaseSetting.setArOfficeNameEn(getCellValue(row.getCell(1)));

                        //Marathi
                        courtCaseSetting.setArOfficeName(translationServiceUtility.translationText(getCellValue(row.getCell(1))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(2)))) {
                        //English
                        courtCaseSetting.setChairmanNameEn(getCellValue(row.getCell(2)));

                        //Marathi
                        courtCaseSetting.setChairmanName(translationServiceUtility.translationText(getCellValue(row.getCell(2))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(3)))) {
                        //English
                        courtCaseSetting.setSachivNameEn(getCellValue(row.getCell(3)));

                        //Marathi
                        courtCaseSetting.setSachivName(translationServiceUtility.translationText(getCellValue(row.getCell(3))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(4)))) {
                        //English
                        courtCaseSetting.setSuchakNameEn(getCellValue(row.getCell(4)));

                        //Marathi
                        courtCaseSetting.setSuchakName(translationServiceUtility.translationText(getCellValue(row.getCell(4))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(5)))) {
                        //English
                        courtCaseSetting.setAnumodakNameEn(getCellValue(row.getCell(5)));

                        //Marathi
                        courtCaseSetting.setAnumodakName(translationServiceUtility.translationText(getCellValue(row.getCell(5))));
                    }
                    if (StringUtils.isNotBlank(getCellValue(row.getCell(6)))) {
                        //English
                        courtCaseSetting.setVasuliExpenseEn(getCellAmountValue(row.getCell(6)));

                        //Marathi

                        courtCaseSetting.setVasuliExpense(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(6)))
                        );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(7)))) {
                        //English
                        courtCaseSetting.setOtherExpenseEn(getCellAmountValue(row.getCell(7)));

                        //Marathi
                        courtCaseSetting.setOtherExpense(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(7)))
                        );
                    }
                    if (StringUtils.isNotBlank(getCellValue(row.getCell(8)))) {
                        //English
                        courtCaseSetting.setNoticeExpenseEn(getCellAmountValue(row.getCell(8)));

                        //Marathi
                        courtCaseSetting.setNoticeExpense(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(8)))
                        );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(9))) && getCellValue(row.getCell(9)).matches("[0-9]+")) {
                        //English
                        courtCaseSetting.setMeetingNoEn(Long.parseLong(getCellValue(row.getCell(9))));

                        //Marathi

                        courtCaseSetting.setMeetingNo(translationServiceUtility.translationText(getCellValue(row.getCell(9))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(10)))) {
                        courtCaseSetting.setMeetingDate(getDateCellValue(row.getCell(10)));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(11))) && getCellValue(row.getCell(11)).matches("[0-9]+")) {
                        //English
                        courtCaseSetting.setSubjectNoEn(Long.parseLong(getCellValue(row.getCell(11))));

                        //Marathi

                        courtCaseSetting.setSubjectNo(translationServiceUtility.translationText(getCellValue(row.getCell(11))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(12)))) {
                        //English
                        courtCaseSetting.setMeetingDayEn(getCellValue(row.getCell(12)));

                        //Marathi
                        courtCaseSetting.setMeetingDay(translationServiceUtility.translationText(getCellValue(row.getCell(12))));
                    }

                    String meetingTime = getCellValue(row.getCell(13));
                    if (StringUtils.isNotBlank(meetingTime)) {
                        //English
                        courtCaseSetting.setMeetingTimeEn(meetingTime);

                        //Marathi
                        courtCaseSetting.setMeetingTime(translationServiceUtility.translationText(meetingTime));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(14)))) {
                        // english
                        courtCaseSetting.setBankNameEn(getCellValue(row.getCell(14)));
                        // marathi
                        courtCaseSetting.setBankName(translationServiceUtility.translationText(getCellValue(row.getCell(14))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(15)))) {
                        // english
                        courtCaseSetting.setTalukaNameEn(getCellValue(row.getCell(15)));
                        // marathi
                        courtCaseSetting.setTalukaName(translationServiceUtility.translationText(getCellValue(row.getCell(15))));
                    }

                    courtCaseSettingList.add(courtCaseSetting);
                }
            }

            if (!courtCaseSettingList.isEmpty()) {
                courtCaseSettingRepository.saveAll(courtCaseSettingList);

                if (courtCaseSettingList.get(0) != null) {
                    try {
                        notificationDataUtility.notificationData(
                            "Court Case record file uploaded",
                            "Court Case record file : " + files.getOriginalFilename() + " uploaded",
                            false,
                            courtCaseSettingList.get(0).getCreatedDate(),
                            "CourtCaseRecordFileUploaded" // type
                        );
                    } catch (Exception e) {}
                }

                return ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, files.getOriginalFilename()))
                    .body(courtCaseSettingList);
            } else {
                throw new BadRequestAlertException("File is already parsed", ENTITY_NAME, "FileExist");
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
    }


    /**
     * {@code POST  /court-case-settings} : Create a new courtCaseSetting.
     *
     * @param courtCaseSetting the courtCaseSetting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new courtCaseSetting, or with status
     *         {@code 400 (Bad Request)} if the courtCaseSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/court-case-settings")
    public ResponseEntity<CourtCaseSetting> createCourtCaseSetting(@RequestBody CourtCaseSetting courtCaseSetting)
        throws URISyntaxException {
        log.debug("REST request to save CourtCaseSetting : {}", courtCaseSetting);
        if (courtCaseSetting.getId() != null) {
            throw new BadRequestAlertException("A new courtCaseSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourtCaseSetting result = courtCaseSettingService.save(courtCaseSetting);
        return ResponseEntity
            .created(new URI("/api/court-case-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /court-case-settings/:id} : Updates an existing courtCaseSetting.
     *
     * @param id               the id of the courtCaseSetting to save.
     * @param courtCaseSetting the courtCaseSetting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated courtCaseSetting, or with status
     *         {@code 400 (Bad Request)} if the courtCaseSetting is not valid, or
     *         with status {@code 500 (Internal Server Error)} if the
     *         courtCaseSetting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/court-case-settings/{id}")
    public ResponseEntity<CourtCaseSetting> updateCourtCaseSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCaseSetting courtCaseSetting
    ) throws URISyntaxException {
        log.debug("REST request to update CourtCaseSetting : {}, {}", id, courtCaseSetting);
        if (courtCaseSetting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCaseSetting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourtCaseSetting result = courtCaseSettingService.update(courtCaseSetting);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCaseSetting.getId().toString()))
            .body(result);
    }
    
    
    
    
    
    
    
    
    @PutMapping("/update-court-case-setting-ref/{id}")
    public ResponseEntity<CourtCaseSetting> updateCourtCaseSettingRef(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCaseSetting courtCaseSetting
    ) throws URISyntaxException {
        log.debug("REST request to update CourtCaseSetting : {}, {}", id, courtCaseSetting);
        if (courtCaseSetting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCaseSetting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        List<CourtCase> courtCaseList=courtCaseRepository.findAllByCourtCaseSetting(courtCaseSettingRepository.findById(id).get());
        
        //removing object if print
        courtCaseList.removeIf(cc -> !cc.getNoticeOfRepayLoanCount().equals(0));
        courtCaseList.removeIf(cc -> !cc.getPriorDemandNoticeCount().equals(0));
        courtCaseList.removeIf(cc -> !cc.getShetiKarjCount().equals(0));
        courtCaseList.removeIf(cc -> !cc.getBigarShetiKarjCount().equals(0));
        courtCaseList.removeIf(cc -> !cc.getOneZeroOnePrakaranCount().equals(0));
        courtCaseList.removeIf(cc -> !cc.getAppendixThreeCount().equals(0));
        courtCaseList.removeIf(cc -> !cc.getAppendixFourCount().equals(0));
        
        
        courtCaseSetting.setId(null);
        CourtCaseSetting result = courtCaseSettingService.save(courtCaseSetting);
        
        
      //calculating postage value from setting file
		 String totalPostageValue = getTotalPostageValue(result.getVasuliExpenseEn(),
				 result.getOtherExpenseEn(),
				 result.getNoticeExpenseEn());
		 
        courtCaseList.forEach(cc -> {
			cc.setSettingCode(""+result.getId());
			cc.setSettingCodeEn(result.getId());
			cc.setTotalPostage(totalPostageValue);
			cc.setCourtCaseSetting(result);
			});
        
        
        courtCaseRepository.saveAll(courtCaseList);
        
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCaseSetting.getId().toString()))
            .body(result);
    }
    
    
    private String getTotalPostageValue(Double getLoanAmountEn, Double getDueInterestEn, Double getDuePenalInterestEn) {
        try {
            String format = String.format("%.2f", Double.sum(getLoanAmountEn, Double.sum(getDueInterestEn, getDuePenalInterestEn)));

            return translationServiceUtility.translationText(format);
        } catch (Exception e) {
            return "Error in translation";
        }
    }

    /**
     * {@code PATCH  /court-case-settings/:id} : Partial updates given fields of an
     * existing courtCaseSetting, field will ignore if it is null
     *
     * @param id               the id of the courtCaseSetting to save.
     * @param courtCaseSetting the courtCaseSetting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated courtCaseSetting, or with status
     *         {@code 400 (Bad Request)} if the courtCaseSetting is not valid, or
     *         with status {@code 404 (Not Found)} if the courtCaseSetting is not
     *         found, or with status {@code 500 (Internal Server Error)} if the
     *         courtCaseSetting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/court-case-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourtCaseSetting> partialUpdateCourtCaseSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCaseSetting courtCaseSetting
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourtCaseSetting partially : {}, {}", id, courtCaseSetting);
        if (courtCaseSetting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCaseSetting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourtCaseSetting> result = courtCaseSettingService.partialUpdate(courtCaseSetting);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCaseSetting.getId().toString())
        );
    }

    /**
     * {@code GET  /court-case-settings} : get all the courtCaseSettings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of courtCaseSettings in body.
     */
    @GetMapping("/court-case-settings")
    public ResponseEntity<List<CourtCaseSetting>> getAllCourtCaseSettings(
        CourtCaseSettingCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourtCaseSettings by criteria: {}", criteria);
       // Page<CourtCaseSetting> page = courtCaseSettingQueryService.findByCriteria(criteria, pageable);
        
        
        Page<CourtCaseSetting> page = null;
        Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            page =
            		courtCaseSettingQueryService.findByCriteriaPackNumber(criteria, pageable, branchOrPacksNumber.get(Constants.PACKS_CODE_KEY));
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY))) {
            page =
            		courtCaseSettingQueryService.findByCriteriaSchemeWiseBranchCode(
                    criteria,
                    pageable,
                    branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY)
                );
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            page = courtCaseSettingQueryService.findByCriteria(criteria, pageable);
        } else {
            throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }
        
        
        
        
        
        
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /court-case-settings/count} : count all the courtCaseSettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/court-case-settings/count")
    public ResponseEntity<Long> countCourtCaseSettings(CourtCaseSettingCriteria criteria) {
        log.debug("REST request to count CourtCaseSettings by criteria: {}", criteria);
        return ResponseEntity.ok().body(courtCaseSettingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /court-case-settings/:id} : get the "id" courtCaseSetting.
     *
     * @param id the id of the courtCaseSetting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the courtCaseSetting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/court-case-settings/{id}")
    public ResponseEntity<CourtCaseSetting> getCourtCaseSetting(@PathVariable Long id) {
        log.debug("REST request to get CourtCaseSetting : {}", id);
        Optional<CourtCaseSetting> courtCaseSetting = courtCaseSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courtCaseSetting);
    }

    /**
     * {@code DELETE  /court-case-settings/:id} : delete the "id" courtCaseSetting.
     *
     * @param id the id of the courtCaseSetting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/court-case-settings/{id}")
    public ResponseEntity<Void> deleteCourtCaseSetting(@PathVariable Long id) {
        log.debug("REST request to delete CourtCaseSetting : {}", id);
        courtCaseSettingService.delete(id);
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
            //            if (cellValue.contains(".0")) {
            //                cellValue = cellValue.substring(0, cellValue.indexOf("."));
            //            }
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
