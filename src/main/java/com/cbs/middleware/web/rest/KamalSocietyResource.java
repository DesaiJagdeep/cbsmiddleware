package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.*;
import com.cbs.middleware.repository.*;
import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.service.KamalSocietyQueryService;
import com.cbs.middleware.service.KamalSocietyService;
import com.cbs.middleware.service.criteria.KamalSocietyCriteria;
import com.cbs.middleware.service.dto.NewKmReportPayload;
import com.cbs.middleware.service.dto.ReportDD;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.KamalSociety}.
 */
@RestController
@RequestMapping("/api")
public class KamalSocietyResource {
    private static final String ENTITY_NAME = "kamalSociety";
    private final Logger log = LoggerFactory.getLogger(KamalSocietyResource.class);
    private final KamalSocietyService kamalSocietyService;
    private final KamalSocietyRepository kamalSocietyRepository;
    private final KamalSocietyQueryService kamalSocietyQueryService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    KamalCropRepository kamalCropRepository;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    private PacsMasterRepository pacsMasterRepository;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private CropRateMasterRepository cropRateMasterRepository;
    @Autowired
    private BankBranchMasterRepository bankBranchMasterRepository;

    public KamalSocietyResource(
        KamalSocietyService kamalSocietyService,
        KamalSocietyRepository kamalSocietyRepository,
        KamalSocietyQueryService kamalSocietyQueryService
    ) {
        this.kamalSocietyService = kamalSocietyService;
        this.kamalSocietyRepository = kamalSocietyRepository;
        this.kamalSocietyQueryService = kamalSocietyQueryService;
    }

    /**
     * {@code POST  /kamal-societies} : Create a new kamalSociety.
     *
     * @param kamalSociety the kamalSociety to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kamalSociety, or with status {@code 400 (Bad Request)} if the kamalSociety has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kamal-societies")
    public ResponseEntity<KamalSociety> createKamalSociety(@Valid @RequestBody KamalSociety kamalSociety) throws URISyntaxException {
        log.debug("REST request to save KamalSociety : {}", kamalSociety);
        if (kamalSociety.getId() != null) {
            throw new BadRequestAlertException("A new kamalSociety cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        String pacsNumber = optUser.get().getPacsNumber();
        kamalSociety.setPacsNumber(pacsNumber);
        kamalSociety.setPacsName(pacsMasterRepository.findOneByPacsNumber(pacsNumber).get().getPacsNameMr());
        kamalSociety.setBranchId(pacsMasterRepository.findOneByPacsNumber(pacsNumber).get().getBankBranchMaster().getId());
        kamalSociety.setBranchName(pacsMasterRepository.findOneByPacsNumber(pacsNumber).get().getBankBranchMaster().getBranchNameMr());

        //set pacs number and FinancialYear to every kamalaCrop Object
        Set<KamalCrop> kamalCrops = kamalSociety.getKamalCrops();
        kamalCrops.forEach(kamalCrop -> {
                kamalCrop.setPacsNumber(pacsNumber);
                kamalCrop.setFinancialYear(kamalSociety.getFinancialYear());
                kamalCrop.setKmDate(kamalSociety.getKmDate());
            }
        );

        kamalSociety.setKamalCrops(kamalCrops);

        KamalSociety result = kamalSocietyService.save(kamalSociety);

        return ResponseEntity
            .created(new URI("/api/kamal-societies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * {@code PUT  /kamal-societies/:id} : Updates an existing kamalSociety.
     *
     * @param id           the id of the kamalSociety to save.
     * @param kamalSociety the kamalSociety to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kamalSociety,
     * or with status {@code 400 (Bad Request)} if the kamalSociety is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kamalSociety couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kamal-societies/{id}")
    public ResponseEntity<KamalSociety> updateKamalSociety(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KamalSociety kamalSociety
    ) throws URISyntaxException {
        log.debug("REST request to update KamalSociety : {}, {}", id, kamalSociety);
        if (kamalSociety.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kamalSociety.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kamalSocietyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KamalSociety result = kamalSocietyService.update(kamalSociety);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kamalSociety.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kamal-societies/:id} : Partial updates given fields of an existing kamalSociety, field will ignore if it is null
     *
     * @param id           the id of the kamalSociety to save.
     * @param kamalSociety the kamalSociety to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kamalSociety,
     * or with status {@code 400 (Bad Request)} if the kamalSociety is not valid,
     * or with status {@code 404 (Not Found)} if the kamalSociety is not found,
     * or with status {@code 500 (Internal Server Error)} if the kamalSociety couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kamal-societies/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<KamalSociety> partialUpdateKamalSociety(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KamalSociety kamalSociety
    ) throws URISyntaxException {
        log.debug("REST request to partial update KamalSociety partially : {}, {}", id, kamalSociety);
        if (kamalSociety.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kamalSociety.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kamalSocietyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KamalSociety> result = kamalSocietyService.partialUpdate(kamalSociety);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kamalSociety.getId().toString())
        );
    }

    /**
     * {@code GET  /kamal-societies} : get all the kamalSocieties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kamalSocieties in body.
     */
    @GetMapping("/kamal-societies")
    public ResponseEntity<List<KamalSociety>> getAllKamalSocieties(
        KamalSocietyCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KamalSocieties by criteria: {}", criteria);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();

        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());

        if (authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
            Long branchId = bankBranchMasterRepository.findOneBySchemeWiseBranchCode(optUser.get().getSchemeWiseBranchCode()).get().getId();
            LongFilter branchIdFilter = new LongFilter();
            branchIdFilter.setEquals(branchId);

            BooleanFilter pacsVerifiedFilter = new BooleanFilter();
            pacsVerifiedFilter.setEquals(true);

            BooleanFilter branchVerifiedFlag = new BooleanFilter();
            branchVerifiedFlag.setEquals(true);

            criteria.setBranchId(branchIdFilter);
            criteria.setPacsVerifiedFlag(pacsVerifiedFilter);
            criteria.setBranchVerifiedFlag(branchVerifiedFlag);

        } else if (authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_USER)) {
            Long branchId = bankBranchMasterRepository.findOneBySchemeWiseBranchCode(optUser.get().getSchemeWiseBranchCode()).get().getId();
            LongFilter branchIdFilter = new LongFilter();
            branchIdFilter.setEquals(branchId);

            BooleanFilter pacsVerifiedFilter = new BooleanFilter();
            pacsVerifiedFilter.setEquals(true);
            criteria.setPacsVerifiedFlag(pacsVerifiedFilter);

        } else if (authority.toString().equals(AuthoritiesConstants.ROLE_PACS_USER)) {
            String pacsNumber = optUser.get().getPacsNumber();
            StringFilter pacsNumberFilter = new StringFilter();
            pacsNumberFilter.setEquals(pacsNumber);
            criteria.setPacsNumber(pacsNumberFilter);

        } else if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
            BooleanFilter pacsVerifiedFilter = new BooleanFilter();
            pacsVerifiedFilter.setEquals(true);
            BooleanFilter branchVerifiedFilter = new BooleanFilter();
            branchVerifiedFilter.setEquals(true);
            BooleanFilter headOfficeVerifiedFilter = new BooleanFilter();
            headOfficeVerifiedFilter.setEquals(true);
            criteria.setPacsVerifiedFlag(pacsVerifiedFilter);
            criteria.setBranchVerifiedFlag(branchVerifiedFilter);
            criteria.setHeadOfficeVerifiedFlag(headOfficeVerifiedFilter);
        }


        Page<KamalSociety> page = kamalSocietyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kamal-societies/count} : count all the kamalSocieties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/kamal-societies/count")
    public ResponseEntity<Long> countKamalSocieties(KamalSocietyCriteria criteria) {
        log.debug("REST request to count KamalSocieties by criteria: {}", criteria);
        return ResponseEntity.ok().body(kamalSocietyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /kamal-societies/:id} : get the "id" kamalSociety.
     *
     * @param id the id of the kamalSociety to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kamalSociety, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kamal-societies/{id}")
    public ResponseEntity<KamalSociety> getKamalSociety(@PathVariable Long id) {
        log.debug("REST request to get KamalSociety : {}", id);
        Optional<KamalSociety> kamalSociety = kamalSocietyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kamalSociety);
    }

    /**
     * {@code DELETE  /kamal-societies/:id} : delete the "id" kamalSociety.
     *
     * @param id the id of the kamalSociety to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kamal-societies/{id}")
    public ResponseEntity<Void> deleteKamalSociety(@PathVariable Long id) {
        log.debug("REST request to delete KamalSociety : {}", id);
        kamalSocietyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


    @GetMapping("/DD1Report")
    public ResponseEntity<byte[]> generatePDFFromHTML() throws Exception {

        List<String> htmlList = new ArrayList<>();

        String htmlStringForPdf = null;

        // Parse input string to Instant
        Instant inputInstant = Instant.parse("2024-03-30T09:39:52Z");
        // Truncate time to midnight (00:00:00)
        Instant resultInstant = inputInstant.truncatedTo(ChronoUnit.DAYS);

        Optional<KamalSociety> kamalSociety = kamalSocietyRepository.findById(1L);
        //List<KamalSociety> all = kamalSocietyRepository.findAll();
        int sumOfMemberFarmer = kamalSocietyRepository.sumOfMemberFarmer(kamalSociety.get().getPacsNumber(), kamalSociety.get().getFinancialYear());

        String sumOfMemberFarmerMr = TranslationServiceUtility.numberTOMarathiNumber(String.valueOf(sumOfMemberFarmer));
        List<ReportDD> reportData = new ArrayList<>();

        String fy = "2023-2024";
        String kmDate = "2023-01-01T00:00:00.000Z";
        String kmFromDate = "2024-04-01T00:00:00.000Z";
        String KmToDate = "2024-03-31T00:00:00.000Z";

        Instant kmDateInstant = Instant.parse(kmDate);
        Instant kmFromDateInstant = Instant.parse(kmFromDate);
        Instant KmToDateInstant = Instant.parse(KmToDate);

        // List<KamalSociety> all=kamalSocietyRepository.findByFyAndKmDateAndKmFromToKmToDate(fy,kmDateInstant,kmFromDateInstant,KmToDateInstant);

        List<KamalSociety> all = kamalSocietyRepository.findAll();
        // Grouping KamalSociety by branchName
        Map<String, List<KamalSociety>> groupedByBranchName = all.stream()
            .collect(Collectors.groupingBy(KamalSociety::getBranchName));

        // Iterating through the groupedByBranchName map
        for (Map.Entry<String, List<KamalSociety>> entry : groupedByBranchName.entrySet()) {
            String branchName = entry.getKey();
            List<KamalSociety> kamalSocieties = entry.getValue();

            // Creating ReportDD and adding it to the reportData list
            ReportDD reportDD = new ReportDD(branchName, kamalSocieties);
            reportData.add(reportDD);
        }

        List<KamalSociety> toPrint = new ArrayList<KamalSociety>();

        for (int i = 0; i < reportData.size(); i++) {
            ReportDD data = reportData.get(i);
            KamalSociety KamalSocietyForBranchName = new KamalSociety();
            KamalSocietyForBranchName.setPacsName(data.getBranchName());
            toPrint.add(KamalSocietyForBranchName);
            int sumTotalLand = 0;
            int sumBagayat = 0;
            int sumJirayat = 0;
            int sumTotalFarmer = 0;
            int sumMemberFarmer = 0;
            int sumNonMemberFarmer = 0;

            for (int j = 0; j < data.getKamalSocieties().size(); j++) {
                KamalSociety temp = data.getKamalSocieties().get(j);
                temp.setId(0L);
                sumTotalLand = sumTotalLand + Integer.parseInt(temp.getTotalLand());
                sumBagayat = sumBagayat + Integer.parseInt(temp.getBagayat());
                sumJirayat = sumJirayat + Integer.parseInt(temp.getJirayat());
                sumTotalFarmer = sumTotalFarmer + Integer.parseInt(temp.getTotalFarmer());
                sumMemberFarmer = sumMemberFarmer + Integer.parseInt(temp.getMemberFarmer());
                sumNonMemberFarmer = sumNonMemberFarmer + Integer.parseInt(temp.getNonMemberFarmer());

                toPrint.add(temp);
            }

            KamalSociety total = new KamalSociety();
            total.setPacsName("एकुण");
            total.setId(Long.valueOf(data.getKamalSocieties().size()));
            total.setTotalLand("" + sumTotalLand);
            total.setBagayat("" + sumBagayat);
            total.setJirayat("" + sumJirayat);
            total.setTotalFarmer("" + sumTotalFarmer);
            total.setMemberFarmer("" + sumMemberFarmer);
            total.setNonMemberFarmer("" + sumNonMemberFarmer);
            toPrint.add(total);

        }

        htmlStringForPdf = DD1Template("newKm/DD1.html", toPrint, sumOfMemberFarmerMr, TranslationServiceUtility.getInstance());

        htmlList.add(htmlStringForPdf);

        ResponseEntity<byte[]> response = null;
        if (htmlList.size() == 1) {
            //code for the generating pdf from html string

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A3.rotate());

            // Create ConverterProperties and set the font provider
            ConverterProperties converterProperties = new ConverterProperties();

            FontProvider fontProvider = new FontProvider();

            File file = new File(Constants.fontFilePath);
            File file1 = new File(Constants.fontFilePath1);

            // Resource resource = resourceLoader.getResource("classpath:" + "fonts/NotoSans-Regular.ttf");
            //String filepath=resource.getFile().getAbsolutePath();

            String filepath = file.getAbsolutePath();
            String filepath1 = file1.getAbsolutePath();

            fontProvider.addFont(filepath, PdfEncodings.IDENTITY_H);
            fontProvider.addFont(filepath1, PdfEncodings.IDENTITY_H);


            converterProperties.setFontProvider(fontProvider);
            converterProperties.setCharset("UTF-8");

            //converting html to pdf
            HtmlConverter.convertToPdf(htmlList.get(0), pdfDocument, converterProperties);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("content-disposition", "attachment; filename=" + getUniqueNumberString() + "certificate.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            response = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
        } else if (htmlList.size() > 1) {

            // Create ConverterProperties and set the font provider
            ConverterProperties converterProperties = new ConverterProperties();

            FontProvider fontProvider = new FontProvider();
            Resource resource = resourceLoader.getResource("classpath:" + "fonts/NotoSans-Regular.ttf");
            String filepath = resource.getFile().getAbsolutePath();
            fontProvider.addFont(filepath, PdfEncodings.IDENTITY_H);

            converterProperties.setFontProvider(fontProvider);
            converterProperties.setCharset("UTF-8");

            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

                for (String htmlString : htmlList) {

                    //code for the generating pdf from html string

                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream1);
                    PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                    pdfDocument.setDefaultPageSize(PageSize.A3.rotate());

                    //converting html to pdf
                    HtmlConverter.convertToPdf(htmlString, pdfDocument, converterProperties);

                    //adding files in zip
                    ZipEntry zipEntry = new ZipEntry("certificate" + getUniqueNumberString() + ".pdf");
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.write(byteArrayOutputStream1.toByteArray());
                    zipOutputStream.closeEntry();
                }

                zipOutputStream.close();
                byteArrayOutputStream.close();

                byte[] zipBytes = byteArrayOutputStream.toByteArray();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "file" + getUniqueNumberString() + ".zip");

                return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
            } catch (Exception e) {
                throw new BadRequestAlertException("Error in file downloading", ENTITY_NAME, "errorInFileDownload");
            }

        } else {
            throw new BadRequestAlertException("Error in file downloading", ENTITY_NAME, "errorInFileDownload");
        }

        return response;
    }

    private String DD1Template(String template, List<KamalSociety> reportDD, String sumOfMemberFarmerMr, TranslationServiceUtility translationServiceUtility) {
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("reportDD", reportDD);
        context.setVariable("sumOfMemberFarmerMr", sumOfMemberFarmerMr);
        context.setVariable("translationServiceUtility", translationServiceUtility);

        String content = templateEngine.process(template, context);
        return content;
    }

    String getUniqueNumberString() {
        Calendar cal = new GregorianCalendar();
        return
            "" +
                cal.get(Calendar.MILLISECOND);
    }


    //Manjuri Crop Detail

    @PostMapping("/kmReports")
    public ResponseEntity<byte[]> generateManjuriPDFFromHTML(@RequestBody NewKmReportPayload newKmReportPayload) throws Exception {
        String financialYear = newKmReportPayload.getFinancialYear();
        String pacsNumber = newKmReportPayload.getPacsNumber();
        //Instant kmDate = newKmReportPayload.getKmDate();

        String kmDateString = "2023-01-01T00:00:00.000Z";
        Instant kmDate = Instant.parse(kmDateString);

        List<String> htmlList = new ArrayList<>();

        String htmlStringForPdf = null;


        List<KamalCrop> sugarcaneMarginalListDB = kamalCropRepository.findBySugarcaneMarginal(financialYear, pacsNumber, kmDate);
        List<KamalCrop> sugarcaneMarginalListToPrint = new ArrayList<>();
        if (!sugarcaneMarginalListDB.isEmpty()) {
            List<KamalCrop> sugarcaneMarginalList = getCropListWithAmountAsPerCropRate(sugarcaneMarginalListDB, financialYear);
            sugarcaneMarginalListToPrint.addAll(sugarcaneMarginalList);
        }

        List<KamalCrop> sugarcaneSmallListDB = kamalCropRepository.findBySugarcaneSmall(financialYear, pacsNumber, kmDate);
        List<KamalCrop> sugarcaneSmallListToPrint = new ArrayList<>();
        if (!sugarcaneSmallListDB.isEmpty()) {
            List<KamalCrop> sugarcaneSmallList = getCropListWithAmountAsPerCropRate(sugarcaneSmallListDB, financialYear);
            sugarcaneSmallListToPrint.addAll(sugarcaneSmallList);
        }

        //to get the sugarcane एकूण (अ)
        KamalCrop totalSugarcaneA = getObjectOfTotal(sugarcaneMarginalListToPrint, sugarcaneSmallListToPrint);


        List<KamalCrop> sugarcaneOtherListDB = kamalCropRepository.findBySugarcaneOther(financialYear, pacsNumber, kmDate);
        List<KamalCrop> sugarcaneOtherListToPrint = new ArrayList<>();
        if (!sugarcaneOtherListDB.isEmpty()) {
            List<KamalCrop> sugarcaneOtherList = getCropListWithAmountAsPerCropRate(sugarcaneOtherListDB, financialYear);
            sugarcaneOtherListToPrint.addAll(sugarcaneOtherList);
        }
        //to get the sugarcane एकूण (ब)
        KamalCrop totalSugarcaneB = getObjectOfTotal(sugarcaneOtherListToPrint);

        //to get the sugarcane एकूण (अ+ब)
        KamalCrop totalSugarcaneAplusB = getObjectOfTotal(Collections.singletonList(totalSugarcaneA), Collections.singletonList(totalSugarcaneB));

        List<KamalCrop> kharipMarginalListDB = kamalCropRepository.findByKharipMarginal(financialYear, pacsNumber, kmDate);
        List<KamalCrop> kharipMarginalListToPrint = new ArrayList<>();
        if (!kharipMarginalListDB.isEmpty()) {
            List<KamalCrop> kharipMarginalList = getCropListWithAmountAsPerCropRate(kharipMarginalListDB, financialYear);
            kharipMarginalListToPrint.addAll(kharipMarginalList);
        }

        List<KamalCrop> kharipSmallListDB = kamalCropRepository.findByKharipSmall(financialYear, pacsNumber, kmDate);
        List<KamalCrop> kharipSmallListToPrint = new ArrayList<>();
        if (!kharipSmallListDB.isEmpty()) {
            List<KamalCrop> kharipSmallList = getCropListWithAmountAsPerCropRate(kharipSmallListDB, financialYear);
            kharipSmallListToPrint.addAll(kharipSmallList);
        }
        //to get the kharip एकूण (अ)
        KamalCrop totalKharipA = getObjectOfTotal(kharipMarginalListToPrint, kharipSmallListToPrint);

        List<KamalCrop> kharipOtherListDB = kamalCropRepository.findByKharipOther(financialYear, pacsNumber, kmDate);
        List<KamalCrop> kharipOtherListToPrint = new ArrayList<>();
        if (!kharipOtherListDB.isEmpty()) {
            List<KamalCrop> kharipOtherList = getCropListWithAmountAsPerCropRate(kharipOtherListDB, financialYear);
            kharipOtherListToPrint.addAll(kharipOtherList);
        }
        //to get the kharip एकूण (ब)
        KamalCrop totalKharipB = getObjectOfTotal(kharipOtherListToPrint);

        //to get the kharip एकूण (अ+ब)
        KamalCrop totalKharipAplusB = getObjectOfTotal(Collections.singletonList(totalKharipA), Collections.singletonList(totalKharipB));


        List<KamalCrop> rabbiMarginalListDB = kamalCropRepository.findByRabbiMarginal(financialYear, pacsNumber, kmDate);
        List<KamalCrop> rabbiMarginalListToPrint = new ArrayList<>();
        if (!rabbiMarginalListDB.isEmpty()) {
            List<KamalCrop> rabbiMarginalList = getCropListWithAmountAsPerCropRate(rabbiMarginalListDB, financialYear);
            rabbiMarginalListToPrint.addAll(rabbiMarginalList);
        }

        List<KamalCrop> rabbiSmallListDB = kamalCropRepository.findByRabbiSmall(financialYear, pacsNumber, kmDate);
        List<KamalCrop> rabbiSmallListToPrint = new ArrayList<>();
        if (!rabbiSmallListDB.isEmpty()) {
            List<KamalCrop> rabbiSmallList = getCropListWithAmountAsPerCropRate(rabbiSmallListDB, financialYear);
            rabbiSmallListToPrint.addAll(rabbiSmallList);
        }
        //to get the rabbi एकूण (अ)
        KamalCrop totalRabbiA = getObjectOfTotal(rabbiMarginalListToPrint, rabbiSmallListToPrint);

        List<KamalCrop> rabbiOtherListDB = kamalCropRepository.findByRabbiOther(financialYear, pacsNumber, kmDate);
        List<KamalCrop> rabbiOtherListToPrint = new ArrayList<>();
        if (!rabbiOtherListDB.isEmpty()) {
            List<KamalCrop> rabbiOtherList = getCropListWithAmountAsPerCropRate(rabbiOtherListDB, financialYear);
            rabbiOtherListToPrint.addAll(rabbiOtherList);
        }
        //to get the Rabbi एकूण (ब)
        KamalCrop totalRabbiB = getObjectOfTotal(kharipOtherListToPrint);

        //to get the Rabbi एकूण (अ+ब)
        KamalCrop totalRabbiAplusB = getObjectOfTotal(Collections.singletonList(totalRabbiA), Collections.singletonList(totalRabbiB));

        //to get total एकूण (१+२+३)
        List<KamalCrop> grandTotalList = new ArrayList<>();
        grandTotalList.add(totalSugarcaneAplusB);
        grandTotalList.add(totalKharipAplusB);
        grandTotalList.add(totalRabbiAplusB);
        KamalCrop grandTotal = getObjectOfTotal(grandTotalList);


        //for 1) karyalayeenNivedan Report
        Optional<KamalSociety> kamalSocietyOptional = kamalSocietyRepository.findByFyPacsNumberKmDate(financialYear, pacsNumber, kmDate);

        KamalSociety kamalSociety = kamalSocietyOptional.get();
        List<KamalCrop> marginalSummary = new ArrayList<>();
        marginalSummary.addAll(sugarcaneMarginalListToPrint);
        marginalSummary.addAll(kharipMarginalListToPrint);
        marginalSummary.addAll(rabbiMarginalListToPrint);

        List<KamalCrop> smallSummary = new ArrayList<>();
        smallSummary.addAll(sugarcaneSmallListToPrint);
        smallSummary.addAll(kharipSmallListToPrint);
        smallSummary.addAll(rabbiSmallListToPrint);

        List<KamalCrop> otherSummary = new ArrayList<>();
        otherSummary.addAll(sugarcaneOtherListToPrint);
        otherSummary.addAll(kharipOtherListToPrint);
        otherSummary.addAll(rabbiOtherListToPrint);

        List<KamalCrop> marginalSummaryToPrint = new ArrayList<>();
        List<KamalCrop> smallSummaryToPrint = new ArrayList<>();
        List<KamalCrop> otherSummaryToPrint = new ArrayList<>();
        //addition of memberCount,area,pacsAmount,branchAmount,headOfficeAmount in 1st element of printList

        marginalSummaryToPrint = getListWithSumForKarayalayeenNivedanReport(marginalSummary);
        smallSummaryToPrint = getListWithSumForKarayalayeenNivedanReport(smallSummary);
        otherSummaryToPrint = getListWithSumForKarayalayeenNivedanReport(otherSummary);


        switch (newKmReportPayload.getTemplateName()) {

            //page 1
            case "karyalayeenNivedan":
                htmlStringForPdf = karyalayeenNivedanTemplate("newKm/karyalayeenNivedan.html",
                    kamalSociety,
                    marginalSummaryToPrint,
                    smallSummaryToPrint,
                    otherSummaryToPrint,
                    TranslationServiceUtility.getInstance()
                );
                htmlList.add(htmlStringForPdf);
                break;

            //page 2
            //  case "mukhyaKacheriShifaras":

            //page3
            case "karjManjuriShifaras":
                htmlStringForPdf = manjuriTemplate("newKm/karjManjuriShifaras.html",
                    sugarcaneMarginalListToPrint,
                    sugarcaneSmallListToPrint,
                    totalSugarcaneA,
                    sugarcaneOtherListToPrint,
                    totalSugarcaneB,
                    totalSugarcaneAplusB,
                    kharipMarginalListToPrint,
                    kharipSmallListToPrint,
                    totalKharipA,
                    kharipOtherListToPrint,
                    totalKharipB,
                    totalKharipAplusB,
                    rabbiMarginalListToPrint,
                    rabbiSmallListToPrint,
                    totalRabbiA,
                    rabbiOtherListToPrint,
                    totalRabbiB,
                    totalRabbiAplusB,
                    grandTotal,
                    TranslationServiceUtility.getInstance());

                htmlList.add(htmlStringForPdf);
                break;

            //page 4
            //  case "pikKarjMagni":

            //page 5
            //  case "sanchalakShifaras":

            //page 6
            //  case "bankGahanKhat":

            //page 7
            //  case "karjVatapSlip":

            //page 8
            //  case "vachanPatrika":


        }


        ResponseEntity<byte[]> response = null;
        if (htmlList.size() == 1) {
            //code for the generating pdf from html string

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);

            // Create ConverterProperties and set the font provider
            ConverterProperties converterProperties = new ConverterProperties();

            FontProvider fontProvider = new FontProvider();

            File file = new File(Constants.fontFilePath);
            File file1 = new File(Constants.fontFilePath1);

            // Resource resource = resourceLoader.getResource("classpath:" + "fonts/NotoSans-Regular.ttf");
            //String filepath=resource.getFile().getAbsolutePath();

            String filepath = file.getAbsolutePath();
            String filepath1 = file1.getAbsolutePath();

            fontProvider.addFont(filepath, PdfEncodings.IDENTITY_H);
            fontProvider.addFont(filepath1, PdfEncodings.IDENTITY_H);


            converterProperties.setFontProvider(fontProvider);
            converterProperties.setCharset("UTF-8");

            //converting html to pdf
            HtmlConverter.convertToPdf(htmlList.get(0), pdfDocument, converterProperties);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("content-disposition", "attachment; filename=" + getUniqueNumberString() + "certificate.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            response = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
        } else if (htmlList.size() > 1) {

            // Create ConverterProperties and set the font provider
            ConverterProperties converterProperties = new ConverterProperties();

            FontProvider fontProvider = new FontProvider();
            Resource resource = resourceLoader.getResource("classpath:" + "fonts/NotoSans-Regular.ttf");
            String filepath = resource.getFile().getAbsolutePath();
            fontProvider.addFont(filepath, PdfEncodings.IDENTITY_H);

            converterProperties.setFontProvider(fontProvider);
            converterProperties.setCharset("UTF-8");

            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

                for (String htmlString : htmlList) {

                    //code for the generating pdf from html string

                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream1);
                    PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                    pdfDocument.setDefaultPageSize(PageSize.A3.rotate());

                    //converting html to pdf
                    HtmlConverter.convertToPdf(htmlString, pdfDocument, converterProperties);

                    //adding files in zip
                    ZipEntry zipEntry = new ZipEntry("certificate" + getUniqueNumberString() + ".pdf");
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.write(byteArrayOutputStream1.toByteArray());
                    zipOutputStream.closeEntry();
                }

                zipOutputStream.close();
                byteArrayOutputStream.close();

                byte[] zipBytes = byteArrayOutputStream.toByteArray();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "file" + getUniqueNumberString() + ".zip");

                return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
            } catch (Exception e) {
                throw new BadRequestAlertException("Error in file downloading", ENTITY_NAME, "errorInFileDownload");
            }

        } else {
            throw new BadRequestAlertException("Error in file downloading", ENTITY_NAME, "errorInFileDownload");
        }

        return response;
    }

    private List<KamalCrop> getListWithSumForKarayalayeenNivedanReport(List<KamalCrop> summaryList) {
        //addition of memberCount,area,pacsAmount,branchAmount,headOfficeAmount in 1st element of printList
        int totalMemberCount = 0;
        double totalArea = 0.0;
        double pacsAmount = 0.0;
        double branchAmount = 0.0;
        double headOfficeAmount = 0.0;

        for (KamalCrop kamalCrop : summaryList) {
            if (kamalCrop.getMemberCount() != null) {
                totalMemberCount += Integer.parseInt(kamalCrop.getMemberCount());
            }
            if (kamalCrop.getArea() != null) {
                totalArea += Double.parseDouble(kamalCrop.getArea());
            }
            if (kamalCrop.getPacsAmount() != null) {
                pacsAmount += Double.parseDouble(kamalCrop.getPacsAmount());
            }
            if (kamalCrop.getBranchAmount() != null) {
                branchAmount += Double.parseDouble(kamalCrop.getBranchAmount());
            }
            if (kamalCrop.getHeadOfficeAmount() != null) {
                headOfficeAmount += Double.parseDouble(kamalCrop.getHeadOfficeAmount());
            }
        }
        summaryList.get(0).setMemberCount(String.valueOf(totalMemberCount));
        summaryList.get(0).setArea(String.valueOf(totalArea));
        summaryList.get(0).setPacsAmount(String.valueOf(pacsAmount));
        summaryList.get(0).setBranchAmount(String.valueOf(branchAmount));
        summaryList.get(0).setHeadOfficeAmount(String.valueOf(headOfficeAmount));

        //if  same crop has different farmerTypes is available then to avoid duplicate cropName printing in report
        if(summaryList.size()>1){
            List<KamalCrop> kamalCropListWithDistinctCrop=new ArrayList<>();
            for (int i=0; i<=summaryList.size()-2;i++) {
                for(int j=i+1;j<=summaryList.size()-1;j++){
                    if(summaryList.get(i).getCropMaster().getId().equals(summaryList.get(j).getCropMaster().getId())){
                        KamalCrop kamalDistinctCrop=new KamalCrop();
                        kamalDistinctCrop.setCropMaster(summaryList.get(i).getCropMaster());
                        kamalCropListWithDistinctCrop.add(kamalDistinctCrop);
                    }
                }
            }
            kamalCropListWithDistinctCrop.get(0).setMemberCount(String.valueOf(totalMemberCount));
            kamalCropListWithDistinctCrop.get(0).setArea(String.valueOf(totalArea));
            kamalCropListWithDistinctCrop.get(0).setPacsAmount(String.valueOf(pacsAmount));
            kamalCropListWithDistinctCrop.get(0).setBranchAmount(String.valueOf(branchAmount));
            kamalCropListWithDistinctCrop.get(0).setHeadOfficeAmount(String.valueOf(headOfficeAmount));

            return kamalCropListWithDistinctCrop;
            }
        return summaryList;
    }


    private KamalCrop getObjectOfTotal(List<KamalCrop> kamalCropList) {
        double totalCropEligibilityAmount = 0.0;
        int totalMemberCount = 0;
        double totalArea = 0.0;
        double totalAmount = 0.0;

        for (KamalCrop kamalCrop : kamalCropList) {
            // totalCropEligibilityAmount += Double.parseDouble(kamalCrop.getCropEligibilityAmount());
            if (kamalCrop.getMemberCount() != null) {
                totalMemberCount += Integer.parseInt(kamalCrop.getMemberCount());
            }
            if (kamalCrop.getArea() != null) {
                totalArea += Double.parseDouble(kamalCrop.getArea());
            }
            if (kamalCrop.getDivisionalOfficeAmount() != null) {
                totalAmount += Double.parseDouble(kamalCrop.getDivisionalOfficeAmount());
            }
        }
        KamalCrop kamalCropTotal = new KamalCrop();
        kamalCropTotal.setCropEligibilityAmount(String.valueOf(totalCropEligibilityAmount));
        kamalCropTotal.setMemberCount(String.valueOf(totalMemberCount));
        kamalCropTotal.setArea(String.valueOf(totalArea));
        kamalCropTotal.setDivisionalOfficeAmount(String.valueOf(totalAmount));

        return kamalCropTotal;
    }

    private KamalCrop getObjectOfTotal(List<KamalCrop> kamalCropList1, List<KamalCrop> kamalCropList2) {
        double totalCropEligibilityAmount = 0.0;
        int totalMemberCount = 0;
        double totalArea = 0.0;
        double totalAmount = 0.0;

        for (KamalCrop kamalCrop : kamalCropList1) {
            //totalCropEligibilityAmount += Double.parseDouble(kamalCrop.getCropEligibilityAmount());
            if (kamalCrop.getMemberCount() != null) {
                totalMemberCount += Integer.parseInt(kamalCrop.getMemberCount());
            }
            if (kamalCrop.getArea() != null) {
                totalArea += Double.parseDouble(kamalCrop.getArea());
            }
            if (kamalCrop.getDivisionalOfficeAmount() != null) {
                totalAmount += Double.parseDouble(kamalCrop.getDivisionalOfficeAmount());
            }
        }

        for (KamalCrop kamalCrop : kamalCropList2) {
            //totalCropEligibilityAmount += Double.parseDouble(kamalCrop.getCropEligibilityAmount());
            if (kamalCrop.getMemberCount() != null) {
                totalMemberCount += Integer.parseInt(kamalCrop.getMemberCount());
            }
            if (kamalCrop.getArea() != null) {
                totalArea += Double.parseDouble(kamalCrop.getArea());
            }
            if (kamalCrop.getDivisionalOfficeAmount() != null) {
                totalAmount += Double.parseDouble(kamalCrop.getDivisionalOfficeAmount());
            }
        }


        KamalCrop kamalCropTotal = new KamalCrop();
        kamalCropTotal.setCropEligibilityAmount(String.valueOf(totalCropEligibilityAmount));
        kamalCropTotal.setMemberCount(String.valueOf(totalMemberCount));
        kamalCropTotal.setArea(String.valueOf(totalArea));
        kamalCropTotal.setDivisionalOfficeAmount(String.valueOf(totalAmount));

        return kamalCropTotal;

    }


    private List<KamalCrop> getCropListWithAmountAsPerCropRate(List<KamalCrop> kamalCropListDB, String financialYear) {
        List<KamalCrop> kamalCropsWithAmount = new ArrayList<>();

        for (KamalCrop kamalCrop : kamalCropListDB) {
            Optional<CropRateMaster> cropRateMasterDB = cropRateMasterRepository.findCropRateByCropAndFinancialYear(kamalCrop.getCropMaster().getId(), financialYear);
            if (cropRateMasterDB.isPresent()) {
                Double area = Double.parseDouble(kamalCrop.getArea());
                Double perHectorCropAmount = cropRateMasterDB.get().getCurrentAmount();

                double pacsAmount = area * perHectorCropAmount;
                kamalCrop.setPacsAmount((pacsAmount) + "0");
                kamalCrop.setCropEligibilityAmount(String.valueOf(perHectorCropAmount));
                KamalCrop savedInDbWithAmount = kamalCropRepository.save(kamalCrop);
                kamalCropsWithAmount.add(savedInDbWithAmount);
            } else {
                kamalCropsWithAmount.add(kamalCrop);
            }
        }
        return kamalCropsWithAmount;
    }

    private String manjuriTemplate(String template,
                                   List<KamalCrop> sugarcaneMarginal,
                                   List<KamalCrop> sugarcaneSmall,
                                   KamalCrop totalSugarcaneA,
                                   List<KamalCrop> sugarcaneOther,
                                   KamalCrop totalSugarcaneB,
                                   KamalCrop totalSugarcaneAplusB,
                                   List<KamalCrop> kharipMarginal,
                                   List<KamalCrop> kharipSmall,
                                   KamalCrop totalKharipA,
                                   List<KamalCrop> kharipOther,
                                   KamalCrop totalKharipB,
                                   KamalCrop totalKharipAplusB,
                                   List<KamalCrop> rabbiMarginal,
                                   List<KamalCrop> rabbiSmall,
                                   KamalCrop totalRabbiA,
                                   List<KamalCrop> rabbiOther,
                                   KamalCrop totalRabbiB,
                                   KamalCrop totalRabbiAplusB,
                                   KamalCrop grandTotal,
                                   TranslationServiceUtility translationServiceUtility) {
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("sugarcaneMarginal", sugarcaneMarginal);
        context.setVariable("sugarcaneSmall", sugarcaneSmall);
        context.setVariable("totalSugarcaneA", totalSugarcaneA);
        context.setVariable("sugarcaneOther", sugarcaneOther);
        context.setVariable("totalSugarcaneB", totalSugarcaneB);
        context.setVariable("totalSugarcaneAplusB", totalSugarcaneAplusB);
        context.setVariable("kharipMarginal", kharipMarginal);
        context.setVariable("kharipSmall", kharipSmall);
        context.setVariable("totalKharipA", totalKharipA);
        context.setVariable("kharipOther", kharipOther);
        context.setVariable("totalKharipB", totalKharipB);
        context.setVariable("totalKharipAplusB", totalKharipAplusB);
        context.setVariable("rabbiMarginal", rabbiMarginal);
        context.setVariable("rabbiSmall", rabbiSmall);
        context.setVariable("totalRabbiA", totalRabbiA);
        context.setVariable("rabbiOther", rabbiOther);
        context.setVariable("totalRabbiB", totalRabbiB);
        context.setVariable("totalRabbiAplusB", totalRabbiAplusB);
        context.setVariable("grandTotal", grandTotal);
        context.setVariable("translationServiceUtility", translationServiceUtility);
        return templateEngine.process(template, context);

    }

    private String karyalayeenNivedanTemplate(String template,
                                              KamalSociety kamalSociety,
                                              List<KamalCrop> marginalSummary,
                                              List<KamalCrop> smallSummary,
                                              List<KamalCrop> otherSummary,
                                              TranslationServiceUtility translationServiceUtility) {
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("kamalSociety", kamalSociety);
        context.setVariable("marginalSummary", marginalSummary);
        context.setVariable("smallSummary", smallSummary);
        context.setVariable("otherSummary", otherSummary);
        context.setVariable("translationServiceUtility", translationServiceUtility);
        return templateEngine.process(template, context);
    }

}
