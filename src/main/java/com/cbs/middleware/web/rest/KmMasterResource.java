package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.KmMasterRepository;
import com.cbs.middleware.service.KmMasterQueryService;
import com.cbs.middleware.service.KmMasterService;
import com.cbs.middleware.service.criteria.KmMasterCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KmMaster}.
 */
@RestController
@RequestMapping("/api/km-masters")
public class KmMasterResource {

    private final Logger log = LoggerFactory.getLogger(KmMasterResource.class);

    private static final String ENTITY_NAME = "kmMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KmMasterService kmMasterService;

    private final KmMasterRepository kmMasterRepository;

    private final KmMasterQueryService kmMasterQueryService;

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
    @PostMapping("")
    public ResponseEntity<KmMaster> createKmMaster(@RequestBody KmMaster kmMaster) throws URISyntaxException {
        log.debug("REST request to save KmMaster : {}", kmMaster);
        if (kmMaster.getId() != null) {
            throw new BadRequestAlertException("A new kmMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KmMaster result = kmMasterService.save(kmMaster);
        return ResponseEntity
            .created(new URI("/api/km-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /km-masters/:id} : Updates an existing kmMaster.
     *
     * @param id the id of the kmMaster to save.
     * @param kmMaster the kmMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmMaster,
     * or with status {@code 400 (Bad Request)} if the kmMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kmMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KmMaster> updateKmMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmMaster kmMaster
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
     * @param id the id of the kmMaster to save.
     * @param kmMaster the kmMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmMaster,
     * or with status {@code 400 (Bad Request)} if the kmMaster is not valid,
     * or with status {@code 404 (Not Found)} if the kmMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the kmMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KmMaster> partialUpdateKmMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmMaster kmMaster
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
    @GetMapping("")
    public ResponseEntity<List<KmMaster>> getAllKmMasters(
        KmMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KmMasters by criteria: {}", criteria);

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
    @GetMapping("/count")
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
    @GetMapping("/{id}")
    public ResponseEntity<KmMaster> getKmMaster(@PathVariable("id") Long id) {
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKmMaster(@PathVariable("id") Long id) {
        log.debug("REST request to delete KmMaster : {}", id);
        kmMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
