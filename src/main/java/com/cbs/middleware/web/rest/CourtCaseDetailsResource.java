package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.CourtCaseDetails;
import com.cbs.middleware.repository.CourtCaseDetailsRepository;
import com.cbs.middleware.service.CourtCaseDetailsQueryService;
import com.cbs.middleware.service.CourtCaseDetailsService;
import com.cbs.middleware.service.criteria.CourtCaseDetailsCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.CourtCaseDetails}.
 */
@RestController
@RequestMapping("/api")
public class CourtCaseDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CourtCaseDetailsResource.class);

    private static final String ENTITY_NAME = "courtCaseDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourtCaseDetailsService courtCaseDetailsService;

    private final CourtCaseDetailsRepository courtCaseDetailsRepository;

    private final CourtCaseDetailsQueryService courtCaseDetailsQueryService;

    public CourtCaseDetailsResource(
        CourtCaseDetailsService courtCaseDetailsService,
        CourtCaseDetailsRepository courtCaseDetailsRepository,
        CourtCaseDetailsQueryService courtCaseDetailsQueryService
    ) {
        this.courtCaseDetailsService = courtCaseDetailsService;
        this.courtCaseDetailsRepository = courtCaseDetailsRepository;
        this.courtCaseDetailsQueryService = courtCaseDetailsQueryService;
    }

    /**
     * {@code POST  /court-case-details} : Create a new courtCaseDetails.
     *
     * @param courtCaseDetails the courtCaseDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courtCaseDetails, or with status {@code 400 (Bad Request)} if the courtCaseDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/court-case-details")
    public ResponseEntity<CourtCaseDetails> createCourtCaseDetails(@RequestBody CourtCaseDetails courtCaseDetails)
        throws URISyntaxException {
        log.debug("REST request to save CourtCaseDetails : {}", courtCaseDetails);
        if (courtCaseDetails.getId() != null) {
            throw new BadRequestAlertException("A new courtCaseDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourtCaseDetails result = courtCaseDetailsService.save(courtCaseDetails);
        return ResponseEntity
            .created(new URI("/api/court-case-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /court-case-details/:id} : Updates an existing courtCaseDetails.
     *
     * @param id the id of the courtCaseDetails to save.
     * @param courtCaseDetails the courtCaseDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courtCaseDetails,
     * or with status {@code 400 (Bad Request)} if the courtCaseDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courtCaseDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/court-case-details/{id}")
    public ResponseEntity<CourtCaseDetails> updateCourtCaseDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCaseDetails courtCaseDetails
    ) throws URISyntaxException {
        log.debug("REST request to update CourtCaseDetails : {}, {}", id, courtCaseDetails);
        if (courtCaseDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCaseDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourtCaseDetails result = courtCaseDetailsService.update(courtCaseDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCaseDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /court-case-details/:id} : Partial updates given fields of an existing courtCaseDetails, field will ignore if it is null
     *
     * @param id the id of the courtCaseDetails to save.
     * @param courtCaseDetails the courtCaseDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courtCaseDetails,
     * or with status {@code 400 (Bad Request)} if the courtCaseDetails is not valid,
     * or with status {@code 404 (Not Found)} if the courtCaseDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the courtCaseDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/court-case-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourtCaseDetails> partialUpdateCourtCaseDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCaseDetails courtCaseDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourtCaseDetails partially : {}, {}", id, courtCaseDetails);
        if (courtCaseDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCaseDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourtCaseDetails> result = courtCaseDetailsService.partialUpdate(courtCaseDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCaseDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /court-case-details} : get all the courtCaseDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courtCaseDetails in body.
     */
    @GetMapping("/court-case-details")
    public ResponseEntity<List<CourtCaseDetails>> getAllCourtCaseDetails(
        CourtCaseDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourtCaseDetails by criteria: {}", criteria);
        Page<CourtCaseDetails> page = courtCaseDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /court-case-details/count} : count all the courtCaseDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/court-case-details/count")
    public ResponseEntity<Long> countCourtCaseDetails(CourtCaseDetailsCriteria criteria) {
        log.debug("REST request to count CourtCaseDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(courtCaseDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /court-case-details/:id} : get the "id" courtCaseDetails.
     *
     * @param id the id of the courtCaseDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courtCaseDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/court-case-details/{id}")
    public ResponseEntity<CourtCaseDetails> getCourtCaseDetails(@PathVariable Long id) {
        log.debug("REST request to get CourtCaseDetails : {}", id);
        Optional<CourtCaseDetails> courtCaseDetails = courtCaseDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courtCaseDetails);
    }

    /**
     * {@code DELETE  /court-case-details/:id} : delete the "id" courtCaseDetails.
     *
     * @param id the id of the courtCaseDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/court-case-details/{id}")
    public ResponseEntity<Void> deleteCourtCaseDetails(@PathVariable Long id) {
        log.debug("REST request to delete CourtCaseDetails : {}", id);
        courtCaseDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
