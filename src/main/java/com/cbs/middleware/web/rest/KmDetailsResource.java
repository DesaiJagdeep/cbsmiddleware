package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KmDetails;
import com.cbs.middleware.repository.KmDetailsRepository;
import com.cbs.middleware.service.KmDetailsQueryService;
import com.cbs.middleware.service.KmDetailsService;
import com.cbs.middleware.service.criteria.KmDetailsCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KmDetails}.
 */
@RestController
@RequestMapping("/api/km-details")
public class KmDetailsResource {

    private final Logger log = LoggerFactory.getLogger(KmDetailsResource.class);

    private static final String ENTITY_NAME = "kmDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KmDetailsService kmDetailsService;

    private final KmDetailsRepository kmDetailsRepository;

    private final KmDetailsQueryService kmDetailsQueryService;

    public KmDetailsResource(
        KmDetailsService kmDetailsService,
        KmDetailsRepository kmDetailsRepository,
        KmDetailsQueryService kmDetailsQueryService
    ) {
        this.kmDetailsService = kmDetailsService;
        this.kmDetailsRepository = kmDetailsRepository;
        this.kmDetailsQueryService = kmDetailsQueryService;
    }

    /**
     * {@code POST  /km-details} : Create a new kmDetails.
     *
     * @param kmDetails the kmDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kmDetails, or with status {@code 400 (Bad Request)} if the kmDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<KmDetails> createKmDetails(@RequestBody KmDetails kmDetails) throws URISyntaxException {
        log.debug("REST request to save KmDetails : {}", kmDetails);
        if (kmDetails.getId() != null) {
            throw new BadRequestAlertException("A new kmDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KmDetails result = kmDetailsService.save(kmDetails);
        return ResponseEntity
            .created(new URI("/api/km-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /km-details/:id} : Updates an existing kmDetails.
     *
     * @param id the id of the kmDetails to save.
     * @param kmDetails the kmDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmDetails,
     * or with status {@code 400 (Bad Request)} if the kmDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kmDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KmDetails> updateKmDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmDetails kmDetails
    ) throws URISyntaxException {
        log.debug("REST request to update KmDetails : {}, {}", id, kmDetails);
        if (kmDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KmDetails result = kmDetailsService.update(kmDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /km-details/:id} : Partial updates given fields of an existing kmDetails, field will ignore if it is null
     *
     * @param id the id of the kmDetails to save.
     * @param kmDetails the kmDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmDetails,
     * or with status {@code 400 (Bad Request)} if the kmDetails is not valid,
     * or with status {@code 404 (Not Found)} if the kmDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the kmDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KmDetails> partialUpdateKmDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmDetails kmDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update KmDetails partially : {}, {}", id, kmDetails);
        if (kmDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KmDetails> result = kmDetailsService.partialUpdate(kmDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /km-details} : get all the kmDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kmDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<KmDetails>> getAllKmDetails(
        KmDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KmDetails by criteria: {}", criteria);

        Page<KmDetails> page = kmDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /km-details/count} : count all the kmDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countKmDetails(KmDetailsCriteria criteria) {
        log.debug("REST request to count KmDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(kmDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /km-details/:id} : get the "id" kmDetails.
     *
     * @param id the id of the kmDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kmDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KmDetails> getKmDetails(@PathVariable Long id) {
        log.debug("REST request to get KmDetails : {}", id);
        Optional<KmDetails> kmDetails = kmDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kmDetails);
    }

    /**
     * {@code DELETE  /km-details/:id} : delete the "id" kmDetails.
     *
     * @param id the id of the kmDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKmDetails(@PathVariable Long id) {
        log.debug("REST request to delete KmDetails : {}", id);
        kmDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
