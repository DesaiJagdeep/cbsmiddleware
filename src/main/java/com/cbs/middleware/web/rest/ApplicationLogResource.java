package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.service.ApplicationLogQueryService;
import com.cbs.middleware.service.ApplicationLogService;
import com.cbs.middleware.service.criteria.ApplicationLogCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.BankBranchPacksCodeGet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.cbs.middleware.domain.ApplicationLog}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationLogResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationLogResource.class);

    private static final String ENTITY_NAME = "applicationLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationLogService applicationLogService;

    private final ApplicationLogRepository applicationLogRepository;

    private final ApplicationLogQueryService applicationLogQueryService;

    @Autowired
    IssFileParserRepository issFileParserRepository;

    @Autowired
    BankBranchPacksCodeGet bankBranchPacksCodeGet;

    public ApplicationLogResource(
        ApplicationLogService applicationLogService,
        ApplicationLogRepository applicationLogRepository,
        ApplicationLogQueryService applicationLogQueryService
    ) {
        this.applicationLogService = applicationLogService;
        this.applicationLogRepository = applicationLogRepository;
        this.applicationLogQueryService = applicationLogQueryService;
    }

    /**
     * {@code POST  /application-logs} : Create a new applicationLog.
     *
     * @param applicationLog the applicationLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new applicationLog, or with status {@code 400 (Bad Request)}
     *         if the applicationLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-logs")
    public ResponseEntity<ApplicationLog> createApplicationLog(@RequestBody ApplicationLog applicationLog) throws URISyntaxException {
        log.debug("REST request to save ApplicationLog : {}", applicationLog);
        if (applicationLog.getId() != null) {
            throw new BadRequestAlertException("A new applicationLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationLog result = applicationLogService.save(applicationLog);
        return ResponseEntity
            .created(new URI("/api/application-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-logs/:id} : Updates an existing applicationLog.
     *
     * @param id             the id of the applicationLog to save.
     * @param applicationLog the applicationLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated applicationLog, or with status {@code 400 (Bad Request)}
     *         if the applicationLog is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the applicationLog couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-logs/{id}")
    public ResponseEntity<ApplicationLog> updateApplicationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationLog applicationLog
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationLog : {}, {}", id, applicationLog);
        if (applicationLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationLog result = applicationLogService.update(applicationLog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /application-logs/:id} : Partial updates given fields of an
     * existing applicationLog, field will ignore if it is null
     *
     * @param id             the id of the applicationLog to save.
     * @param applicationLog the applicationLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated applicationLog, or with status {@code 400 (Bad Request)}
     *         if the applicationLog is not valid, or with status
     *         {@code 404 (Not Found)} if the applicationLog is not found, or with
     *         status {@code 500 (Internal Server Error)} if the applicationLog
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/application-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationLog> partialUpdateApplicationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationLog applicationLog
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicationLog partially : {}, {}", id, applicationLog);
        if (applicationLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationLog> result = applicationLogService.partialUpdate(applicationLog);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationLog.getId().toString())
        );
    }

    /**
     * {@code GET  /application-logs} : get all the applicationLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of applicationLogs in body.
     */
    @GetMapping("/application-logs")
    public ResponseEntity<List<ApplicationLog>> getAllApplicationLogs(
        ApplicationLogCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApplicationLogs by criteria: {}", criteria);
        Page<ApplicationLog> page = null;
        Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            page = applicationLogQueryService.findByCriteria(criteria, pageable);
            //			page = applicationLogQueryService.findByCriteriaCountByPacsCode(
            //					Long.parseLong(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY)), pageable);
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BRANCH_CODE_KEY))) {
            page = applicationLogQueryService.findByCriteria(criteria, pageable);
            //			page = applicationLogQueryService.findByCriteriaCountByBranchCode(
            //					Long.parseLong(branchOrPacksNumber.get(Constants.BRANCH_CODE_KEY)), criteria, pageable);
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            page = applicationLogQueryService.findByCriteria(criteria, pageable);
        } else {
            throw new UnAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/application-error-records/{IPId}")
    public ResponseEntity<List<IssFileParser>> getAllApplicationLogs(@PathVariable Long IPId) {
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAllByIssPortalIdAndStatus(IPId, "ERROR");

        List<IssFileParser> issFileParserList = new ArrayList<>();
        for (ApplicationLog applicationLog : applicationLogList) {
            issFileParserList.add(applicationLog.getIssFileParser());
        }

        return ResponseEntity.ok().body(issFileParserList);
    }

    /**
     * {@code GET  /application-logs/count} : count all the applicationLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/application-logs/count")
    public ResponseEntity<Long> countApplicationLogs(ApplicationLogCriteria criteria) {
        log.debug("REST request to count ApplicationLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-logs/:id} : get the "id" applicationLog.
     *
     * @param id the id of the applicationLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the applicationLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-logs/{id}")
    public ResponseEntity<ApplicationLog> getApplicationLog(@PathVariable Long id) {
        log.debug("REST request to get ApplicationLog : {}", id);
        Optional<ApplicationLog> applicationLog = applicationLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationLog);
    }

    /**
     * {@code DELETE  /application-logs/:id} : delete the "id" applicationLog.
     *
     * @param id the id of the applicationLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-logs/{id}")
    public ResponseEntity<Void> deleteApplicationLog(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationLog : {}", id);
        applicationLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
