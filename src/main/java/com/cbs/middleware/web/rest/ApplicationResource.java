package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.domainUtil.Report;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.service.ApplicationQueryService;
import com.cbs.middleware.service.ApplicationService;
import com.cbs.middleware.service.criteria.ApplicationCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.BankBranchPacksCodeGet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.Application}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

    private static final String ENTITY_NAME = "application";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationService applicationService;

    private final ApplicationRepository applicationRepository;

    private final ApplicationQueryService applicationQueryService;

    @Autowired
    BankBranchPacksCodeGet bankBranchPacksCodeGet;

    public ApplicationResource(
        ApplicationService applicationService,
        ApplicationRepository applicationRepository,
        ApplicationQueryService applicationQueryService
    ) {
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
        this.applicationQueryService = applicationQueryService;
    }

    /**
     * {@code POST  /applications} : Create a new application.
     *
     * @param application the application to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new application, or with status {@code 400 (Bad Request)} if
     *         the application has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applications")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) throws URISyntaxException {
        log.debug("REST request to save Application : {}", application);
        if (application.getId() != null) {
            throw new BadRequestAlertException("A new application cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Application result = applicationService.save(application);
        return ResponseEntity
            .created(new URI("/api/applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applications/:id} : Updates an existing application.
     *
     * @param id          the id of the application to save.
     * @param application the application to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated application, or with status {@code 400 (Bad Request)} if
     *         the application is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the application couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applications/{id}")
    public ResponseEntity<Application> updateApplication(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Application application
    ) throws URISyntaxException {
        log.debug("REST request to update Application : {}, {}", id, application);
        if (application.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, application.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Application result = applicationService.update(application);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, application.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applications/:id} : Partial updates given fields of an
     * existing application, field will ignore if it is null
     *
     * @param id          the id of the application to save.
     * @param application the application to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated application, or with status {@code 400 (Bad Request)} if
     *         the application is not valid, or with status {@code 404 (Not Found)}
     *         if the application is not found, or with status
     *         {@code 500 (Internal Server Error)} if the application couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Application> partialUpdateApplication(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Application application
    ) throws URISyntaxException {
        log.debug("REST request to partial update Application partially : {}, {}", id, application);
        if (application.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, application.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Application> result = applicationService.partialUpdate(application);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, application.getId().toString())
        );
    }

    /**
     * {@code GET  /applications} : get all the applications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of applications in body.
     */
    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplications(
        ApplicationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        Page<Application> page = null;
        log.debug("REST request to get Applications by criteria: {}", criteria);

        Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            page =
                applicationQueryService.findByCriteriaByPacsCode(
                    Long.parseLong(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY)),
                    pageable
                );
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BRANCH_CODE_KEY))) {
            page =
                applicationQueryService.findByCriteriaByBranchCode(
                    Long.parseLong(branchOrPacksNumber.get(Constants.BRANCH_CODE_KEY)),
                    criteria,
                    pageable
                );
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            page = applicationQueryService.findByCriteria(criteria, pageable);
        } else {
            throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applications/count} : count all the applications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/applications/count")
    public ResponseEntity<Long> countApplications(ApplicationCriteria criteria) {
        log.debug("REST request to count Applications by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationQueryService.countByCriteria(criteria));
    }

    /**
     *
     * @param year
     * @param bankName
     * @param branchName
     * @param packsName
     * @param criteria
     * @param pageable
     * @return
     */

    @GetMapping("/cbs-data-report")
    public ResponseEntity<Report> getAllCbsDataReport(
        ApplicationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CbsDataReports by criteria: {}", criteria);

        Report report = new Report();

        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.setFinancialYear(criteria.getFinancialYear());

        if (criteria.getBankCode() != null) {
            applicationCriteria.setBankCode(criteria.getBankCode());
        }

        if (criteria.getBranchCode() != null) {
            applicationCriteria.setBranchCode(criteria.getBranchCode());
        }

        if (criteria.getPacksCode() != null) {
            applicationCriteria.setPacksCode(criteria.getPacksCode());
        }

        report.setRecordsInYear(applicationQueryService.countByCriteria(applicationCriteria));

        StringFilter stringFilter = new StringFilter();
        stringFilter.setNotEquals(null);
        applicationCriteria.setBatchId(stringFilter);
        report.setRecordSubmittedToKCC(applicationQueryService.countByCriteria(applicationCriteria));

        IntegerFilter filter = new IntegerFilter();
        filter.setEquals(1);
        applicationCriteria.setApplicationStatus(filter);
        report.setAcceptedRecordByKCC(applicationQueryService.countByCriteria(applicationCriteria));

        filter = new IntegerFilter();
        filter.setEquals(0);
        applicationCriteria.setApplicationStatus(filter);
        report.setRejectedRecordByKCC(applicationQueryService.countByCriteria(applicationCriteria));

        Page<Application> page = applicationQueryService.findByCriteria(criteria, pageable);
        report.setApplicationList(page.getContent());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(report);
    }

    /**
     * {@code GET  /applications/:id} : get the "id" application.
     *
     * @param id the id of the application to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the application, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applications/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable Long id) {
        log.debug("REST request to get Application : {}", id);
        Optional<Application> application = applicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(application);
    }

    /**
     * {@code DELETE  /applications/:id} : delete the "id" application.
     *
     * @param id the id of the application to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete Application : {}", id);
        applicationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
