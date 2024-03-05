package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.*;
import com.cbs.middleware.repository.KmDetailsRepository;
import com.cbs.middleware.repository.KmLoansRepository;
import com.cbs.middleware.repository.KmMasterRepository;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.service.KmMasterQueryService;
import com.cbs.middleware.service.KmMasterService;
import com.cbs.middleware.service.criteria.KmMasterCriteria;
import com.cbs.middleware.service.dto.MemberWiseKmPayload;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private static final String ENTITY_NAME = "kmMaster";
    private final Logger log = LoggerFactory.getLogger(KmMasterResource.class);
    private final KmMasterService kmMasterService;
    private final KmMasterRepository kmMasterRepository;
    private final KmMasterQueryService kmMasterQueryService;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    private KmDetailsRepository kmDetailsRepository;
    @Autowired
    private KmLoansRepository kmLoansRepository;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        String pacsNumber = optUser.get().getPacsNumber();
        kmMaster.setPacsNumber(pacsNumber);

        KmMaster result = kmMasterService.save(kmMaster);
        return ResponseEntity
            .created(new URI("/api/km-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /km-masters/:id} : Updates an existing kmMaster.
     *
     * @param id       the id of the kmMaster to save.
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
     * @param id       the id of the kmMaster to save.
     * @param kmMaster the kmMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmMaster,
     * or with status {@code 400 (Bad Request)} if the kmMaster is not valid,
     * or with status {@code 404 (Not Found)} if the kmMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the kmMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/km-masters/{id}", consumes = {"application/json", "application/merge-patch+json"})
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        String pacsNumber = optUser.get().getPacsNumber();

        StringFilter pacsFilter = new StringFilter();
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


    @PostMapping("/member-wise-km-report")
    public ResponseEntity<byte[]> generatePDFFromHTML(@RequestBody MemberWiseKmPayload memberWiseKmPayload) throws Exception {
        String financialYear = memberWiseKmPayload.getFinancialYear();
        String templateName = memberWiseKmPayload.getTemplateName();
        Instant kmDate = memberWiseKmPayload.getKmDate();


        List<String> htmlList = new ArrayList<>();
        String htmlStringForPdf = null;

        //This query should have pacs_number too ,as we haven't added pacs_number in kmDetails, for time being
        List<KmDetails> kmDetailsList = kmDetailsRepository.findByKmDateAndFinancialYear(kmDate, financialYear);

        List<Long> kmMasterIds = new ArrayList<>();
        for (KmDetails kmDetails : kmDetailsList) {
            Long kmMasterId = kmDetails.getKmMaster().getId();
            kmMasterIds.add(kmMasterId);

        }
        List<KmMaster> kmMasterList = kmMasterRepository.findAllById(kmMasterIds);



    /*    List<KmDetails> KmDetailsByKmDateEquals = kmDetailsRepository.findByKmDateEquals(kmDate);

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
        }*/

        String html = "&lt;p&gt;hello&lt;/p&gt;";


        switch (memberWiseKmPayload.getTemplateName()) {

            //page 1
            case "kamalPatrak":
                String content = getContent(kmMasterList);
   /*             htmlStringForPdf = kamalPatrakTemplate("memberWiseKm/kamalPatrak.html",
                    kmMasterList,
                    content,
                    TranslationServiceUtility.getInstance()
                );*/
                htmlList.add(content);
                break;

            case "kamalSummary":
                Object directPdf = downloadDirectPdf();
                return (ResponseEntity<byte[]>) directPdf;

        }

        String htmlStringForPdf1 = htmlStringForPdf;
        System.out.println(htmlStringForPdf1);
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

    private Object downloadDirectPdf() {
        Path file = Paths.get(Constants.ORIGINAL_FILE_PATH + "kamalSummary.pdf");

        byte[] fileBytes;
          try {

              fileBytes = Files.readAllBytes(file);
              ByteArrayResource resource = new ByteArrayResource(fileBytes);

              HttpHeaders headers = new HttpHeaders();
              headers.setContentType(MediaType.APPLICATION_PDF);
             // headers.setContentDispositionFormData("filename", issPortalFile.getFileName());

              List<String> contentDispositionList = new ArrayList<>();
              contentDispositionList.add("Content-Disposition");

              headers.setAccessControlExposeHeaders(contentDispositionList);
              headers.set("X-Cbsmiddlewareapp-Alert", "cbsMiddlewareApp.issPortalFile.download");
              //headers.set("X-Cbsmiddlewareapp-Params", issPortalFile.getFileName());

              return ResponseEntity.ok().headers(headers).contentLength(fileBytes.length).body(resource);
          } catch (IOException e) {
              throw new BadRequestAlertException("Error in file download", ENTITY_NAME, "fileNotFound");
          }
      }


    private String getContent(List<KmMaster> kmMasterList) {
        StringBuilder toReturn = new StringBuilder();

        double totalShares=0.0;
        double totalDeposite=0.0;
        double totalBagayat=0.0;
        double totalDemand=0.0;
        double totalBhagKapat=0.0;

        toReturn.append("<!DOCTYPE html>\n" +
            "<html lang=\"\">\n" +
            "<head>\n" +
            "  <meta charset=\"UTF-8\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "  <title>Kamal Patrak</title>\n" +
            "</head>\n" +
            "<body style=\"box-sizing: border-box;\">\n" +
            "<table width=\"100%\">\n" +
            "  <tr style=\"font-size: 14px;\">\n" +
            "    <th width=\"50%\" style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
            "      <p style=\"text-align: center; margin: 10px 0; font-size: 20px;\">सिंधुदुर्ग जिल्हा मध्यवर्ती सहकारी बँक लि. सिंधुदुर्ग </p>\n" +
            "      <p style=\"text-align: center; margin: 0;\">\n" +
            "        <small style=\"font-weight: 400;\">किसान पतपात्र योजनेखाली अल्पमुदत शेती कर्जाचे कमाल मर्यादा पत्रक </small>\n" +
            "      </p>\n" +
            "      <div style=\"width: 60%; float: right;\">\n" +
            "        <p style=\"margin: 5px; \">\n" +
            "          <small>\n" +
            "            सन <span style=\"font-weight: 400;\">01/04/2024</span>\n" +
            "            ते <span style=\"font-weight: 400;\">31/03/2029 </span>\n" +
            "          </small>\n" +
            "        </p>\n" +
            "      </div>\n" +
            "    </th>\n" +
            "    <th width=\"50%\" style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
            "      <p style=\"text-align: center; margin: 10px 0; font-size: 20px;\">भारत माता महिला नागरी पत  संस्था लि.</p>\n" +
            "      <p style=\"text-align: center; margin: 0;\">\n" +
            "        <small style=\"font-weight: 400;\">केडगाव</small>\n" +
            "      </p>\n" +
            "    </th>\n" +
            "  </tr>\n" +
            "</table>\n" +
            "<div style=\"padding: 0px 10px 10px 0px; min-height: 60vh;\">");


        toReturn.append("<table width=\"100%\" style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
            "    <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">अ. न.</th>\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">पि. आय. डी</th>\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">सभासद </th>\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">शेअर्स  </th>\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">ठेव </th>\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">येणे बाकी  </th>\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">थक बाकी  </th>\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">एकुण क्षेत्र  </th>\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">इ  क्षेत्र </th>\n" +
            "      <th colspan=\"6\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">सभासद पिकवार मागणी, रक्कम  </th>\n" +
            "      <th colspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">संस्था शिफारस </th>\n" +
            "      <th colspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">बँक शिफारस </th>\n" +
            "      <th rowspan=\"2\" style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">भाग </th>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">हंगाम </th>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">पीक </th>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">क्षेत्र</th>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\"> ला. क्षेत्र </th>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">कलम संख्या </th>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">मागणी </th>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">कलम संख्या </th>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">रक्कम </th>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">कलम संख्या </th>\n" +
            "      <th style=\"border: 1px solid black; border-collapse: collapse; font-size: 12px;\">रक्कम </th>\n" +
            "    </tr>");

        int srNo = 1;
        for (KmMaster kmMaster : kmMasterList) {
            KmDetails kmDetails = kmMaster.getKmDetails();
            Set<KmLoans> kmLoans = kmDetails.getKmLoans();
            Set<KmCrops> kmCrops = kmDetails.getKmCrops();

            String kmDetail = "" +
                "<tr style=\"border: 1px solid black; border-collapse: collapse;\"  " +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; text-align: right;\">" + srNo++ + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\">" + kmMaster.getPacsMemberCode() + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\">" + kmMaster.getFarmerName() + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;text-align: right; padding-right: 2px;\">" + getIntValue(kmDetails.getShares()) + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right;\">" + getIntValue(kmDetails.getDeposite()) + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right;\">" + getIntValue(kmDetails.getDueLoan()) + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right;\">" + getIntValue(kmDetails.getDueAmount()) + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;text-align: right; padding-right: 2px;\">" + getIntValue(kmDetails.getBagayatHector()) + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;text-align: right; padding-right: 2px;\">" + getIntValue(kmDetails.getBagayatHector()) + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"></td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"></td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"></td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"></td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"></td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;font-weight:600 ;text-align: right; padding-right: 2px;\">" + getIntValue(getTotal(kmCrops, "demand")) + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"></td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;text-align: right; padding-right: 2px;\">" + getIntValue(getTotal(kmCrops, "demand")) + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"></td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;text-align: right; padding-right: 2px;\">" + getIntValue(getTotal(kmCrops, "demand")) + "</td>\n" +
                "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;text-align: right; padding-right: 2px;\">" + getIntValue(getTotal(kmCrops, "demand") * 0.05) + "</td>\n" +
                "    </tr>";
            toReturn.append(kmDetail);

            totalShares+=kmDetails.getShares();
            totalDeposite+=kmDetails.getDeposite();
            totalBagayat+=kmDetails.getBagayatHector();
            totalDemand+=getTotal(kmCrops, "demand");
            totalBhagKapat+=(getTotal(kmCrops, "demand")* 0.05);

            boolean first = true;
            for (KmCrops kmCrop : kmCrops) {

                    String crop = "" +
                        "<tr style=\"border: 1px solid black; border-collapse: collapse;\"  ";
                    if (first) {
                        crop = crop + "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\" rowspan=\"" + kmCrops.size() + "\"></td>\n" +
                            "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\" rowspan=\"" + kmCrops.size() + "\"></td>\n" +
                            "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\" rowspan=\"" + kmCrops.size() + "\"></td>\n" +
                            "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\" rowspan=\"" + kmCrops.size() + "\"></td>\n" +
                            "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\" rowspan=\"" + kmCrops.size() + "\"></td>\n";
                    }
                    crop = crop + "      " +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right; padding-right: 2px;\">" +getAmountFromKmLoan(kmCrop,"LoanAmount")+"</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right;padding-right: 2px;\">" +getAmountFromKmLoan(kmCrop,"DueAmount")+"</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"></td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"></td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\">" + kmCrop.getCropMaster().getSeasonMaster().getSeasonName() + " </td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\">" + kmCrop.getCropMaster().getCropName() + "</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right; padding-right: 2px;\">" + getIntValue(kmCrop.getAre()) + "</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right; padding-right: 2px;\">" + getIntValue(kmCrop.getAre()) + "</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right; padding-right: 2px;\">" + getIntValue(kmCrop.getNoOfTree()) + "</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right; padding-right: 2px;\">" + getIntValue(kmCrop.getDemand()) + "</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right; padding-right: 2px;\">" + getIntValue(kmCrop.getNoOfTree()) + "</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right; padding-right: 2px;\">" + getIntValue(kmCrop.getDemand()) + "</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right; padding-right: 2px;\">" + getIntValue(kmCrop.getNoOfTree()) + "</td>\n" +
                        "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;text-align: right; padding-right: 2px;\">" + getIntValue(kmCrop.getDemand()) + "</td>\n";
                    if (first) {
                        crop = crop + "      <td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\" rowspan=\"" + kmCrops.size() + "\"></td>\n" +
                            "    </tr>\n";

                    }
                    first = false;
                    toReturn.append(crop);

            }

        }
        toReturn.append(
"<tr style=\"border: 1px solid black; border-collapse: collapse;>"+
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;font-weight:600 px;\"> एकूण </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;\"> "+ getIntValue(totalShares)+"</td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;\">"+ getIntValue(totalDeposite)+"</td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;\"> "+ getIntValue(totalBagayat)+"</td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;\"> "+ getIntValue(totalBagayat)+"</td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;\"> "+ getIntValue(totalDemand)+"</td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;\"> "+ getIntValue(totalDemand)+"</td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end;\"> </td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;\"> "+ getIntValue(totalDemand)+"</td>\n" +
            "<td style=\"border: 1px solid black; border-collapse: collapse; text-align: end; font-weight:600 ;\"> "+ getIntValue(totalBhagKapat)+"</td>\n"+
    "</tr>"
        );


        toReturn.append(
            "</table>\n" +
                "</div>\n" +
                "<div>\n" +
                "  <div style=\"width: 30%; text-align: center; font-size: 14px; display: inline-block;\">\n" +
                "    <p>\n" +
                "      वरील माहितीत सूचनाबर हुकूम <br>\n" +
                "      सर्व पात्र सभासदांचा समावेश केला असून बिनचूक केली आहे\n" +
                "    </p>\n" +
                "    <h5 style=\"padding: 8px;\">सचिव</h5>\n" +
                "    <p>भारत माता महिला नागरी पत  संस्था लि.</p>\n" +
                "  </div>\n" +
                "  <div style=\"width: 30%; text-align: center; font-size: 14px; display: inline-block;\">\n" +
                "    <p>\n" +
                "      वरील माहितीत सूचनाबर हुकूम <br>\n" +
                "      सर्व पात्र सभासदांचा समावेश केला असून बिनचूक केली आहे\n" +
                "    </p>\n" +
                "    <h5 style=\"padding: 8px;\">विकास अधिकारी</h5>\n" +
                "    <p>शाखा</p>\n" +
                "  </div>\n" +
                "  <div style=\"width: 30%; text-align: center; font-size: 14px; display: inline-block;\">\n" +
                "    <p>\n" +
                "      वरील माहितीत सूचनाबर हुकूम <br>\n" +
                "      सर्व पात्र सभासदांचा समावेश केला असून बिनचूक केली आहे\n" +
                "    </p>\n" +
                "    <h5 style=\"padding: 8px;\">विकास अधिकारी</h5>\n" +
                "    <p>शाखा</p>\n" +
                "  </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html></html>");
        return toReturn.toString();
    }

    private String getAmountFromKmLoan(KmCrops kmCrop, String amount) {
        Double  returnAmount=null;

        Optional<KmDetails> kmDetails = kmDetailsRepository.findById(kmCrop.getKmDetails().getId());
        List<KmLoans> kmLoanList = kmLoansRepository.findByKmDetails_IdEquals(kmDetails.get().getId());

        for (KmLoans kmLoan:kmLoanList) {
            if(amount.equalsIgnoreCase("LoanAmount") && kmCrop.getCropMaster().equals(kmLoan.getCropMaster())){
                returnAmount= kmLoan.getLoanAmt();
            } else if (amount.equalsIgnoreCase("DueAmount") && kmCrop.getCropMaster().equals(kmLoan.getCropMaster())) {
                returnAmount = kmLoan.getDueAmt();
            }
        }

        if (returnAmount == null) {
            return "";
        }
        int intValue = returnAmount.intValue();

        if (intValue == 0) {
            return "";
        } else {
            return String.valueOf(intValue);
        }

    }

    private String getIntValue(Double shares) {
        if (shares == null) {
            return "";
        }
        int intValue = shares.intValue();
        if (intValue == 0) {
            return "";
        } else {
            return String.valueOf(intValue);
        }

    }

    private double getTotal(Set<KmCrops> kmCrops, String type) {
        double toReturn = 0;
        if ("are".equals(type)) {
            for (KmCrops kmCrop : kmCrops) {
                toReturn = toReturn + kmCrop.getAre();
            }
        } else if ("demand".equals(type)) {
            for (KmCrops kmCrop : kmCrops) {
                toReturn = toReturn + kmCrop.getDemand();
            }
        }
        return toReturn;
    }


    private String kamalPatrakTemplate(String template, List<KmMaster> kmMasterList, String html, TranslationServiceUtility translationServiceUtility) {
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("kmMasterList", kmMasterList);
        context.setVariable("html", html);
        context.setVariable("translationServiceUtility", translationServiceUtility);
        String content = templateEngine.process(template, context);
        return content;
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

    //Direct pdf Download
//  @GetMapping("/download-file/{idIFP}")
//  @PreAuthorize("@authentication.hasPermision('',#idIFP,'','FILE_DOWNLOAD','DOWNLOAD')")
//  public Object pdfDownload(@PathVariable Long idIFP) {
//      Optional<IssPortalFile> findByIssPortalFileId = issPortalFileRepository.findById(idIFP);
//      if (findByIssPortalFileId.isPresent()) {
//          Path file = Paths.get(Constants.ORIGINAL_FILE_PATH + findByIssPortalFileId.get().getUniqueName());
//
//          if (!Files.exists(file)) {
//              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//          }
//          IssPortalFile issPortalFile = findByIssPortalFileId.get();
//          byte[] fileBytes;
//          try {
//              fileBytes = Files.readAllBytes(file);
//              ByteArrayResource resource = new ByteArrayResource(fileBytes);
//
//              HttpHeaders headers = new HttpHeaders();
//              headers.setContentType(MediaType.APPLICATION_PDF);
//              headers.setContentDispositionFormData("filename", issPortalFile.getFileName());
//
//              List<String> contentDispositionList = new ArrayList<>();
//              contentDispositionList.add("Content-Disposition");
//
//              headers.setAccessControlExposeHeaders(contentDispositionList);
//              headers.set("X-Cbsmiddlewareapp-Alert", "cbsMiddlewareApp.issPortalFile.download");
//              headers.set("X-Cbsmiddlewareapp-Params", issPortalFile.getFileName());
//
//              if (resource != null) {
//                  try {
//                      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                      Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
//                      String name = "";
//                      if (optUser.isPresent()) {
//                          name = optUser.get().getFirstName() + " " + optUser.get().getLastName();
//                      }
//
//                      notificationDataUtility.notificationData(
//                          "Application records file downloaded by user ",
//                          "Application records file: " + issPortalFile.getFileName() + " is downloaded by user " + name,
//                          false,
//                          Instant.now(),
//                          "ApplicationRecordFileDownload" // type
//                      );
//
//                  } catch (Exception e) {
//                  }
//              }
//
//              return ResponseEntity.ok().headers(headers).contentLength(fileBytes.length).body(resource);
//          } catch (IOException e) {
//              throw new BadRequestAlertException("Error in file download", ENTITY_NAME, "fileNotFound");
//          }
//      } else {
//          throw new BadRequestAlertException("Error in file download", ENTITY_NAME, "fileNotFound");
//      }
//  }


}
