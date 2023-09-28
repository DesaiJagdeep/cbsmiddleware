package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.LoanDemandKMPatrak;
import com.cbs.middleware.repository.LoanDemandKMPatrakRepository;
import com.cbs.middleware.service.LoanDemandKMPatrakQueryService;
import com.cbs.middleware.service.LoanDemandKMPatrakService;
import com.cbs.middleware.service.criteria.LoanDemandKMPatrakCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.LoanDemandKMPatrak}.
 */
@RestController
@RequestMapping("/api")
public class LoanDemandKMPatrakResource {

    private final Logger log = LoggerFactory.getLogger(LoanDemandKMPatrakResource.class);

    private static final String ENTITY_NAME = "loanDemandKMPatrak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanDemandKMPatrakService loanDemandKMPatrakService;

    private final LoanDemandKMPatrakRepository loanDemandKMPatrakRepository;

    private final LoanDemandKMPatrakQueryService loanDemandKMPatrakQueryService;

    public LoanDemandKMPatrakResource(
        LoanDemandKMPatrakService loanDemandKMPatrakService,
        LoanDemandKMPatrakRepository loanDemandKMPatrakRepository,
        LoanDemandKMPatrakQueryService loanDemandKMPatrakQueryService
    ) {
        this.loanDemandKMPatrakService = loanDemandKMPatrakService;
        this.loanDemandKMPatrakRepository = loanDemandKMPatrakRepository;
        this.loanDemandKMPatrakQueryService = loanDemandKMPatrakQueryService;
    }

    /**
     * {@code POST  /loan-demand-km-patraks} : Create a new loanDemandKMPatrak.
     *
     * @param loanDemandKMPatrak the loanDemandKMPatrak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanDemandKMPatrak, or with status {@code 400 (Bad Request)} if the loanDemandKMPatrak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-demand-km-patraks")
    public ResponseEntity<LoanDemandKMPatrak> createLoanDemandKMPatrak(@RequestBody LoanDemandKMPatrak loanDemandKMPatrak)
        throws URISyntaxException {
        log.debug("REST request to save LoanDemandKMPatrak : {}", loanDemandKMPatrak);
        if (loanDemandKMPatrak.getId() != null) {
            throw new BadRequestAlertException("A new loanDemandKMPatrak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanDemandKMPatrak result = loanDemandKMPatrakService.save(loanDemandKMPatrak);
        return ResponseEntity
            .created(new URI("/api/loan-demand-km-patraks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-demand-km-patraks/:id} : Updates an existing loanDemandKMPatrak.
     *
     * @param id the id of the loanDemandKMPatrak to save.
     * @param loanDemandKMPatrak the loanDemandKMPatrak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDemandKMPatrak,
     * or with status {@code 400 (Bad Request)} if the loanDemandKMPatrak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanDemandKMPatrak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-demand-km-patraks/{id}")
    public ResponseEntity<LoanDemandKMPatrak> updateLoanDemandKMPatrak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDemandKMPatrak loanDemandKMPatrak
    ) throws URISyntaxException {
        log.debug("REST request to update LoanDemandKMPatrak : {}, {}", id, loanDemandKMPatrak);
        if (loanDemandKMPatrak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDemandKMPatrak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDemandKMPatrakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanDemandKMPatrak result = loanDemandKMPatrakService.update(loanDemandKMPatrak);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDemandKMPatrak.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-demand-km-patraks/:id} : Partial updates given fields of an existing loanDemandKMPatrak, field will ignore if it is null
     *
     * @param id the id of the loanDemandKMPatrak to save.
     * @param loanDemandKMPatrak the loanDemandKMPatrak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDemandKMPatrak,
     * or with status {@code 400 (Bad Request)} if the loanDemandKMPatrak is not valid,
     * or with status {@code 404 (Not Found)} if the loanDemandKMPatrak is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanDemandKMPatrak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-demand-km-patraks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanDemandKMPatrak> partialUpdateLoanDemandKMPatrak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDemandKMPatrak loanDemandKMPatrak
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanDemandKMPatrak partially : {}, {}", id, loanDemandKMPatrak);
        if (loanDemandKMPatrak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDemandKMPatrak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDemandKMPatrakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanDemandKMPatrak> result = loanDemandKMPatrakService.partialUpdate(loanDemandKMPatrak);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDemandKMPatrak.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-demand-km-patraks} : get all the loanDemandKMPatraks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanDemandKMPatraks in body.
     */
    @GetMapping("/loan-demand-km-patraks")
    public ResponseEntity<List<LoanDemandKMPatrak>> getAllLoanDemandKMPatraks(
        LoanDemandKMPatrakCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanDemandKMPatraks by criteria: {}", criteria);
        Page<LoanDemandKMPatrak> page = loanDemandKMPatrakQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-demand-km-patraks/count} : count all the loanDemandKMPatraks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/loan-demand-km-patraks/count")
    public ResponseEntity<Long> countLoanDemandKMPatraks(LoanDemandKMPatrakCriteria criteria) {
        log.debug("REST request to count LoanDemandKMPatraks by criteria: {}", criteria);
        return ResponseEntity.ok().body(loanDemandKMPatrakQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /loan-demand-km-patraks/:id} : get the "id" loanDemandKMPatrak.
     *
     * @param id the id of the loanDemandKMPatrak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanDemandKMPatrak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-demand-km-patraks/{id}")
    public ResponseEntity<LoanDemandKMPatrak> getLoanDemandKMPatrak(@PathVariable Long id) {
        log.debug("REST request to get LoanDemandKMPatrak : {}", id);
        Optional<LoanDemandKMPatrak> loanDemandKMPatrak = loanDemandKMPatrakService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanDemandKMPatrak);
    }

    /**
     * {@code DELETE  /loan-demand-km-patraks/:id} : delete the "id" loanDemandKMPatrak.
     *
     * @param id the id of the loanDemandKMPatrak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-demand-km-patraks/{id}")
    public ResponseEntity<Void> deleteLoanDemandKMPatrak(@PathVariable Long id) {
        log.debug("REST request to delete LoanDemandKMPatrak : {}", id);
        loanDemandKMPatrakService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
