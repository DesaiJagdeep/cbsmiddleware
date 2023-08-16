package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.CbsDataReport;
import com.cbs.middleware.repository.CbsDataReportRepository;
import com.cbs.middleware.service.CbsDataReportQueryService;
import com.cbs.middleware.service.CbsDataReportService;
import com.cbs.middleware.service.criteria.CbsDataReportCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.CbsDataReport}.
 */
@RestController
@RequestMapping("/api")
public class CbsDataReportResource {

    private final Logger log = LoggerFactory.getLogger(CbsDataReportResource.class);

    private static final String ENTITY_NAME = "cbsDataReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbsDataReportService cbsDataReportService;

    private final CbsDataReportRepository cbsDataReportRepository;

    private final CbsDataReportQueryService cbsDataReportQueryService;

    public CbsDataReportResource(
        CbsDataReportService cbsDataReportService,
        CbsDataReportRepository cbsDataReportRepository,
        CbsDataReportQueryService cbsDataReportQueryService
    ) {
        this.cbsDataReportService = cbsDataReportService;
        this.cbsDataReportRepository = cbsDataReportRepository;
        this.cbsDataReportQueryService = cbsDataReportQueryService;
    }

    /**
     * {@code POST  /cbs-data-reports} : Create a new cbsDataReport.
     *
     * @param cbsDataReport the cbsDataReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbsDataReport, or with status {@code 400 (Bad Request)} if the cbsDataReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cbs-data-reports")
    public ResponseEntity<CbsDataReport> createCbsDataReport(@RequestBody CbsDataReport cbsDataReport) throws URISyntaxException {
        log.debug("REST request to save CbsDataReport : {}", cbsDataReport);
        if (cbsDataReport.getId() != null) {
            throw new BadRequestAlertException("A new cbsDataReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CbsDataReport result = cbsDataReportService.save(cbsDataReport);
        return ResponseEntity
            .created(new URI("/api/cbs-data-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cbs-data-reports/:id} : Updates an existing cbsDataReport.
     *
     * @param id the id of the cbsDataReport to save.
     * @param cbsDataReport the cbsDataReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbsDataReport,
     * or with status {@code 400 (Bad Request)} if the cbsDataReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbsDataReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cbs-data-reports/{id}")
    public ResponseEntity<CbsDataReport> updateCbsDataReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbsDataReport cbsDataReport
    ) throws URISyntaxException {
        log.debug("REST request to update CbsDataReport : {}, {}", id, cbsDataReport);
        if (cbsDataReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbsDataReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbsDataReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CbsDataReport result = cbsDataReportService.update(cbsDataReport);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbsDataReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cbs-data-reports/:id} : Partial updates given fields of an existing cbsDataReport, field will ignore if it is null
     *
     * @param id the id of the cbsDataReport to save.
     * @param cbsDataReport the cbsDataReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbsDataReport,
     * or with status {@code 400 (Bad Request)} if the cbsDataReport is not valid,
     * or with status {@code 404 (Not Found)} if the cbsDataReport is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbsDataReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cbs-data-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbsDataReport> partialUpdateCbsDataReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbsDataReport cbsDataReport
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbsDataReport partially : {}, {}", id, cbsDataReport);
        if (cbsDataReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbsDataReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbsDataReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbsDataReport> result = cbsDataReportService.partialUpdate(cbsDataReport);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbsDataReport.getId().toString())
        );
    }

    /**
     * {@code GET  /cbs-data-reports} : get all the cbsDataReports.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbsDataReports in body.
     */
    @GetMapping("/cbs-data-reports")
    public ResponseEntity<List<CbsDataReport>> getAllCbsDataReports(
        CbsDataReportCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CbsDataReports by criteria: {}", criteria);
        Page<CbsDataReport> page = cbsDataReportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cbs-data-reports/count} : count all the cbsDataReports.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cbs-data-reports/count")
    public ResponseEntity<Long> countCbsDataReports(CbsDataReportCriteria criteria) {
        log.debug("REST request to count CbsDataReports by criteria: {}", criteria);
        return ResponseEntity.ok().body(cbsDataReportQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cbs-data-reports/:id} : get the "id" cbsDataReport.
     *
     * @param id the id of the cbsDataReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbsDataReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cbs-data-reports/{id}")
    public ResponseEntity<CbsDataReport> getCbsDataReport(@PathVariable Long id) {
        log.debug("REST request to get CbsDataReport : {}", id);
        Optional<CbsDataReport> cbsDataReport = cbsDataReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbsDataReport);
    }

    /**
     * {@code DELETE  /cbs-data-reports/:id} : delete the "id" cbsDataReport.
     *
     * @param id the id of the cbsDataReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cbs-data-reports/{id}")
    public ResponseEntity<Void> deleteCbsDataReport(@PathVariable Long id) {
        log.debug("REST request to delete CbsDataReport : {}", id);
        cbsDataReportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
