package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.*;
import com.cbs.middleware.repository.KmDetailsRepository;
import com.cbs.middleware.repository.KmMasterRepository;
import com.cbs.middleware.service.KmMasterQueryService;
import com.cbs.middleware.service.KmMasterService;
import com.cbs.middleware.service.criteria.CourtCaseCriteria;
import com.cbs.middleware.service.criteria.KmMasterCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.KmMaster}.
 */
@RestController
@RequestMapping("/api")
public class KmMasterResource {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    private SpringTemplateEngine templateEngine;
    private final Logger log = LoggerFactory.getLogger(KmMasterResource.class);

    private static final String ENTITY_NAME = "kmMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KmMasterService kmMasterService;

    private final KmMasterRepository kmMasterRepository;

    private final KmMasterQueryService kmMasterQueryService;
    @Autowired
    private KmDetailsRepository kmDetailsRepository;

    public KmMasterResource(
        KmMasterService kmMasterService,
        KmMasterRepository kmMasterRepository,
        KmMasterQueryService kmMasterQueryService
    ) {
        this.kmMasterService = kmMasterService;
        this.kmMasterRepository = kmMasterRepository;
        this.kmMasterQueryService = kmMasterQueryService;
    }

    /**
     * {@code POST  /km-masters} : Create a new kmMaster.
     *
     * @param kmMaster the kmMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kmMaster, or with status {@code 400 (Bad Request)} if the kmMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/km-masters")
    public ResponseEntity<KmMaster> createKmMaster(@Valid @RequestBody KmMaster kmMaster) throws URISyntaxException {
        log.debug("REST request to save KmMaster : {}", kmMaster);
        if (kmMaster.getId() != null) {
            throw new BadRequestAlertException("A new kmMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KmMaster result = kmMasterService.save(kmMaster);
        return ResponseEntity
            .created(new URI("/api/km-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /km-masters/:id} : Updates an existing kmMaster.
     *
     * @param id the id of the kmMaster to save.
     * @param kmMaster the kmMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmMaster,
     * or with status {@code 400 (Bad Request)} if the kmMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kmMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/km-masters/{id}")
    public ResponseEntity<KmMaster> updateKmMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KmMaster kmMaster
    ) throws URISyntaxException {
        log.debug("REST request to update KmMaster : {}, {}", id, kmMaster);
        if (kmMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KmMaster result = kmMasterService.update(kmMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /km-masters/:id} : Partial updates given fields of an existing kmMaster, field will ignore if it is null
     *
     * @param id the id of the kmMaster to save.
     * @param kmMaster the kmMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmMaster,
     * or with status {@code 400 (Bad Request)} if the kmMaster is not valid,
     * or with status {@code 404 (Not Found)} if the kmMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the kmMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/km-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KmMaster> partialUpdateKmMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KmMaster kmMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update KmMaster partially : {}, {}", id, kmMaster);
        if (kmMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KmMaster> result = kmMasterService.partialUpdate(kmMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /km-masters} : get all the kmMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kmMasters in body.
     */
    @GetMapping("/km-masters")
    public ResponseEntity<List<KmMaster>> getAllKmMasters(
        KmMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KmMasters by criteria: {}", criteria);
        Page<KmMaster> page = kmMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /km-masters/count} : count all the kmMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/km-masters/count")
    public ResponseEntity<Long> countKmMasters(KmMasterCriteria criteria) {
        log.debug("REST request to count KmMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(kmMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /km-masters/:id} : get the "id" kmMaster.
     *
     * @param id the id of the kmMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kmMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/km-masters/{id}")
    public ResponseEntity<KmMaster> getKmMaster(@PathVariable Long id) {
        log.debug("REST request to get KmMaster : {}", id);
        Optional<KmMaster> kmMaster = kmMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kmMaster);
    }

    /**
     * {@code DELETE  /km-masters/:id} : delete the "id" kmMaster.
     *
     * @param id the id of the kmMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/km-masters/{id}")
    public ResponseEntity<Void> deleteKmMaster(@PathVariable Long id) {
        log.debug("REST request to delete KmMaster : {}", id);
        kmMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


    @GetMapping("/kmReport")
    public ResponseEntity<byte[]> generatePDFFromHTML() throws Exception {

      /*  Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();
        String pacsOrbranchCode = "";

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            pacsOrbranchCode = branchOrPacksNumber.get(Constants.PACKS_CODE_KEY);

        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY))) {

            pacsOrbranchCode = branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY);
        } else {
            throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }*/


      List<String> htmlList = new ArrayList<>();

         /* CourtCaseCriteria criteria = new CourtCaseCriteria();

        //creating filter
        StringFilter pacsOrbranchCodeFilter = new StringFilter();
        pacsOrbranchCodeFilter.setEquals(pacsOrbranchCode);

        StringFilter bankFilter = new StringFilter();
        bankFilter.setEquals(one01ReportParam.getBankName());

        StringFilter financialYear = new StringFilter();
        financialYear.setEquals(one01ReportParam.getFinancialYear());

        //adding initial values
        criteria.setBranchOrPacsCode(pacsOrbranchCodeFilter);
        criteria.setBankName(bankFilter);
        criteria.setFinancialYear(financialYear);

        CourtCase courtCase = null;
        String noticeDate = "";*/
        String htmlStringForPdf = null;

        // Parse input string to Instant
        Instant inputInstant = Instant.parse("2024-03-30T09:39:52Z");
        // Truncate time to midnight (00:00:00)
        Instant resultInstant = inputInstant.truncatedTo(ChronoUnit.DAYS);

        List<KmDetails> KmDetailsByKmDateEquals = kmDetailsRepository.findByKmDateEquals(resultInstant);

      /*  List<Long> kmMasterIds = KmDetailsByKmDateEquals.stream()
            .map(kmDetail -> kmDetail.getKmMaster().getId())
            .collect(Collectors.toList());
        List<KmMaster> kmMasterList = kmMasterRepository.findAllById(kmMasterIds);*/

        List<KmMaster> kmMasterList =new ArrayList<>();

        for (KmDetails kmDetails:KmDetailsByKmDateEquals) {
            Long kmMasterId = kmDetails.getKmMaster().getId();
            Optional<KmMaster> kmMaster = kmMasterRepository.findById(kmMasterId);
            kmMasterList.add(kmMaster.get());
        }

        for (KmMaster kmMaster:kmMasterList) {
            // generating html from template
            htmlStringForPdf =  kmTemplate("kmNotice/km.html",kmMaster);
            htmlList.add(htmlStringForPdf);
        }

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

    private String kmTemplate(String template, KmMaster kmMaster) {
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("kmMaster", kmMaster);
        String content = templateEngine.process(template, context);
        return content;
    }

    String getUniqueNumberString() {
        Calendar cal = new GregorianCalendar();
        return
            "" +
                cal.get(Calendar.MILLISECOND);
    }

}
