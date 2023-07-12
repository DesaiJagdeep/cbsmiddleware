package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.ApplicationLogHistory;
import com.cbs.middleware.repository.ApplicationLogHistoryRepository;
import com.cbs.middleware.service.ApplicationLogHistoryQueryService;
import com.cbs.middleware.service.ApplicationLogHistoryService;
import com.cbs.middleware.service.criteria.ApplicationLogHistoryCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.ApplicationLogHistory}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationLogHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationLogHistoryResource.class);

    private static final String ENTITY_NAME = "applicationLogHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationLogHistoryService applicationLogHistoryService;

    private final ApplicationLogHistoryRepository applicationLogHistoryRepository;

    private final ApplicationLogHistoryQueryService applicationLogHistoryQueryService;

    public ApplicationLogHistoryResource(
        ApplicationLogHistoryService applicationLogHistoryService,
        ApplicationLogHistoryRepository applicationLogHistoryRepository,
        ApplicationLogHistoryQueryService applicationLogHistoryQueryService
    ) {
        this.applicationLogHistoryService = applicationLogHistoryService;
        this.applicationLogHistoryRepository = applicationLogHistoryRepository;
        this.applicationLogHistoryQueryService = applicationLogHistoryQueryService;
    }

    /**
     * {@code POST  /application-log-histories} : Create a new applicationLogHistory.
     *
     * @param applicationLogHistory the applicationLogHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationLogHistory, or with status {@code 400 (Bad Request)} if the applicationLogHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-log-histories")
    public ResponseEntity<ApplicationLogHistory> createApplicationLogHistory(@RequestBody ApplicationLogHistory applicationLogHistory)
        throws URISyntaxException {
        log.debug("REST request to save ApplicationLogHistory : {}", applicationLogHistory);
        if (applicationLogHistory.getId() != null) {
            throw new BadRequestAlertException("A new applicationLogHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationLogHistory result = applicationLogHistoryService.save(applicationLogHistory);
        return ResponseEntity
            .created(new URI("/api/application-log-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-log-histories/:id} : Updates an existing applicationLogHistory.
     *
     * @param id the id of the applicationLogHistory to save.
     * @param applicationLogHistory the applicationLogHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationLogHistory,
     * or with status {@code 400 (Bad Request)} if the applicationLogHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationLogHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-log-histories/{id}")
    public ResponseEntity<ApplicationLogHistory> updateApplicationLogHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationLogHistory applicationLogHistory
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationLogHistory : {}, {}", id, applicationLogHistory);
        if (applicationLogHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationLogHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationLogHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApplicationLogHistory result = applicationLogHistoryService.update(applicationLogHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationLogHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /application-log-histories/:id} : Partial updates given fields of an existing applicationLogHistory, field will ignore if it is null
     *
     * @param id the id of the applicationLogHistory to save.
     * @param applicationLogHistory the applicationLogHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationLogHistory,
     * or with status {@code 400 (Bad Request)} if the applicationLogHistory is not valid,
     * or with status {@code 404 (Not Found)} if the applicationLogHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationLogHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/application-log-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationLogHistory> partialUpdateApplicationLogHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApplicationLogHistory applicationLogHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApplicationLogHistory partially : {}, {}", id, applicationLogHistory);
        if (applicationLogHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationLogHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationLogHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationLogHistory> result = applicationLogHistoryService.partialUpdate(applicationLogHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationLogHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /application-log-histories} : get all the applicationLogHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationLogHistories in body.
     */
    @GetMapping("/application-log-histories")
    public ResponseEntity<List<ApplicationLogHistory>> getAllApplicationLogHistories(
        ApplicationLogHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApplicationLogHistories by criteria: {}", criteria);
        Page<ApplicationLogHistory> page = applicationLogHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-log-histories/count} : count all the applicationLogHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/application-log-histories/count")
    public ResponseEntity<Long> countApplicationLogHistories(ApplicationLogHistoryCriteria criteria) {
        log.debug("REST request to count ApplicationLogHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationLogHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-log-histories/:id} : get the "id" applicationLogHistory.
     *
     * @param id the id of the applicationLogHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationLogHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-log-histories/{id}")
    public ResponseEntity<ApplicationLogHistory> getApplicationLogHistory(@PathVariable Long id) {
        log.debug("REST request to get ApplicationLogHistory : {}", id);
        Optional<ApplicationLogHistory> applicationLogHistory = applicationLogHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationLogHistory);
    }

    /**
     * {@code DELETE  /application-log-histories/:id} : delete the "id" applicationLogHistory.
     *
     * @param id the id of the applicationLogHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-log-histories/{id}")
    public ResponseEntity<Void> deleteApplicationLogHistory(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationLogHistory : {}", id);
        applicationLogHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
