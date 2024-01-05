package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KmLoans;
import com.cbs.middleware.repository.KmLoansRepository;
import com.cbs.middleware.service.KmLoansQueryService;
import com.cbs.middleware.service.KmLoansService;
import com.cbs.middleware.service.criteria.KmLoansCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KmLoans}.
 */
@RestController
@RequestMapping("/api/km-loans")
public class KmLoansResource {

    private final Logger log = LoggerFactory.getLogger(KmLoansResource.class);

    private static final String ENTITY_NAME = "kmLoans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KmLoansService kmLoansService;

    private final KmLoansRepository kmLoansRepository;

    private final KmLoansQueryService kmLoansQueryService;

    public KmLoansResource(KmLoansService kmLoansService, KmLoansRepository kmLoansRepository, KmLoansQueryService kmLoansQueryService) {
        this.kmLoansService = kmLoansService;
        this.kmLoansRepository = kmLoansRepository;
        this.kmLoansQueryService = kmLoansQueryService;
    }

    /**
     * {@code POST  /km-loans} : Create a new kmLoans.
     *
     * @param kmLoans the kmLoans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kmLoans, or with status {@code 400 (Bad Request)} if the kmLoans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<KmLoans> createKmLoans(@RequestBody KmLoans kmLoans) throws URISyntaxException {
        log.debug("REST request to save KmLoans : {}", kmLoans);
        if (kmLoans.getId() != null) {
            throw new BadRequestAlertException("A new kmLoans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KmLoans result = kmLoansService.save(kmLoans);
        return ResponseEntity
            .created(new URI("/api/km-loans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /km-loans/:id} : Updates an existing kmLoans.
     *
     * @param id the id of the kmLoans to save.
     * @param kmLoans the kmLoans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmLoans,
     * or with status {@code 400 (Bad Request)} if the kmLoans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kmLoans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KmLoans> updateKmLoans(@PathVariable(value = "id", required = false) final Long id, @RequestBody KmLoans kmLoans)
        throws URISyntaxException {
        log.debug("REST request to update KmLoans : {}, {}", id, kmLoans);
        if (kmLoans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmLoans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmLoansRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KmLoans result = kmLoansService.update(kmLoans);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmLoans.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /km-loans/:id} : Partial updates given fields of an existing kmLoans, field will ignore if it is null
     *
     * @param id the id of the kmLoans to save.
     * @param kmLoans the kmLoans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmLoans,
     * or with status {@code 400 (Bad Request)} if the kmLoans is not valid,
     * or with status {@code 404 (Not Found)} if the kmLoans is not found,
     * or with status {@code 500 (Internal Server Error)} if the kmLoans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KmLoans> partialUpdateKmLoans(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmLoans kmLoans
    ) throws URISyntaxException {
        log.debug("REST request to partial update KmLoans partially : {}, {}", id, kmLoans);
        if (kmLoans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmLoans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmLoansRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KmLoans> result = kmLoansService.partialUpdate(kmLoans);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmLoans.getId().toString())
        );
    }

    /**
     * {@code GET  /km-loans} : get all the kmLoans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kmLoans in body.
     */
    @GetMapping("")
    public ResponseEntity<List<KmLoans>> getAllKmLoans(
        KmLoansCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KmLoans by criteria: {}", criteria);

        Page<KmLoans> page = kmLoansQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /km-loans/count} : count all the kmLoans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countKmLoans(KmLoansCriteria criteria) {
        log.debug("REST request to count KmLoans by criteria: {}", criteria);
        return ResponseEntity.ok().body(kmLoansQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /km-loans/:id} : get the "id" kmLoans.
     *
     * @param id the id of the kmLoans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kmLoans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KmLoans> getKmLoans(@PathVariable Long id) {
        log.debug("REST request to get KmLoans : {}", id);
        Optional<KmLoans> kmLoans = kmLoansService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kmLoans);
    }

    /**
     * {@code DELETE  /km-loans/:id} : delete the "id" kmLoans.
     *
     * @param id the id of the kmLoans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKmLoans(@PathVariable Long id) {
        log.debug("REST request to delete KmLoans : {}", id);
        kmLoansService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
