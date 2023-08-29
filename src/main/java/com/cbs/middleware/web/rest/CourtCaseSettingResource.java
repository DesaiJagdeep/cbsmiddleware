package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.CourtCaseSetting;
import com.cbs.middleware.repository.CourtCaseSettingRepository;
import com.cbs.middleware.service.CourtCaseSettingQueryService;
import com.cbs.middleware.service.CourtCaseSettingService;
import com.cbs.middleware.service.criteria.CourtCaseSettingCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.CourtCaseSetting}.
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
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courtCaseSetting, or with status {@code 400 (Bad Request)} if the courtCaseSetting has already an ID.
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
     * @param id the id of the courtCaseSetting to save.
     * @param courtCaseSetting the courtCaseSetting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courtCaseSetting,
     * or with status {@code 400 (Bad Request)} if the courtCaseSetting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courtCaseSetting couldn't be updated.
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

    /**
     * {@code PATCH  /court-case-settings/:id} : Partial updates given fields of an existing courtCaseSetting, field will ignore if it is null
     *
     * @param id the id of the courtCaseSetting to save.
     * @param courtCaseSetting the courtCaseSetting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courtCaseSetting,
     * or with status {@code 400 (Bad Request)} if the courtCaseSetting is not valid,
     * or with status {@code 404 (Not Found)} if the courtCaseSetting is not found,
     * or with status {@code 500 (Internal Server Error)} if the courtCaseSetting couldn't be updated.
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courtCaseSettings in body.
     */
    @GetMapping("/court-case-settings")
    public ResponseEntity<List<CourtCaseSetting>> getAllCourtCaseSettings(
        CourtCaseSettingCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourtCaseSettings by criteria: {}", criteria);
        Page<CourtCaseSetting> page = courtCaseSettingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /court-case-settings/count} : count all the courtCaseSettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courtCaseSetting, or with status {@code 404 (Not Found)}.
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
}
