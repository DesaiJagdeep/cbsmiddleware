package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KmCrops;
import com.cbs.middleware.repository.KmCropsRepository;
import com.cbs.middleware.service.KmCropsQueryService;
import com.cbs.middleware.service.KmCropsService;
import com.cbs.middleware.service.criteria.KmCropsCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KmCrops}.
 */
@RestController
@RequestMapping("/api/km-crops")
public class KmCropsResource {

    private final Logger log = LoggerFactory.getLogger(KmCropsResource.class);

    private static final String ENTITY_NAME = "kmCrops";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KmCropsService kmCropsService;

    private final KmCropsRepository kmCropsRepository;

    private final KmCropsQueryService kmCropsQueryService;

    public KmCropsResource(KmCropsService kmCropsService, KmCropsRepository kmCropsRepository, KmCropsQueryService kmCropsQueryService) {
        this.kmCropsService = kmCropsService;
        this.kmCropsRepository = kmCropsRepository;
        this.kmCropsQueryService = kmCropsQueryService;
    }

    /**
     * {@code POST  /km-crops} : Create a new kmCrops.
     *
     * @param kmCrops the kmCrops to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kmCrops, or with status {@code 400 (Bad Request)} if the kmCrops has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<KmCrops> createKmCrops(@RequestBody KmCrops kmCrops) throws URISyntaxException {
        log.debug("REST request to save KmCrops : {}", kmCrops);
        if (kmCrops.getId() != null) {
            throw new BadRequestAlertException("A new kmCrops cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KmCrops result = kmCropsService.save(kmCrops);
        return ResponseEntity
            .created(new URI("/api/km-crops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /km-crops/:id} : Updates an existing kmCrops.
     *
     * @param id the id of the kmCrops to save.
     * @param kmCrops the kmCrops to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmCrops,
     * or with status {@code 400 (Bad Request)} if the kmCrops is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kmCrops couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KmCrops> updateKmCrops(@PathVariable(value = "id", required = false) final Long id, @RequestBody KmCrops kmCrops)
        throws URISyntaxException {
        log.debug("REST request to update KmCrops : {}, {}", id, kmCrops);
        if (kmCrops.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmCrops.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmCropsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KmCrops result = kmCropsService.update(kmCrops);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmCrops.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /km-crops/:id} : Partial updates given fields of an existing kmCrops, field will ignore if it is null
     *
     * @param id the id of the kmCrops to save.
     * @param kmCrops the kmCrops to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmCrops,
     * or with status {@code 400 (Bad Request)} if the kmCrops is not valid,
     * or with status {@code 404 (Not Found)} if the kmCrops is not found,
     * or with status {@code 500 (Internal Server Error)} if the kmCrops couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KmCrops> partialUpdateKmCrops(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmCrops kmCrops
    ) throws URISyntaxException {
        log.debug("REST request to partial update KmCrops partially : {}, {}", id, kmCrops);
        if (kmCrops.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmCrops.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmCropsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KmCrops> result = kmCropsService.partialUpdate(kmCrops);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmCrops.getId().toString())
        );
    }

    /**
     * {@code GET  /km-crops} : get all the kmCrops.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kmCrops in body.
     */
    @GetMapping("")
    public ResponseEntity<List<KmCrops>> getAllKmCrops(
        KmCropsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KmCrops by criteria: {}", criteria);

        Page<KmCrops> page = kmCropsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /km-crops/count} : count all the kmCrops.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countKmCrops(KmCropsCriteria criteria) {
        log.debug("REST request to count KmCrops by criteria: {}", criteria);
        return ResponseEntity.ok().body(kmCropsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /km-crops/:id} : get the "id" kmCrops.
     *
     * @param id the id of the kmCrops to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kmCrops, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KmCrops> getKmCrops(@PathVariable Long id) {
        log.debug("REST request to get KmCrops : {}", id);
        Optional<KmCrops> kmCrops = kmCropsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kmCrops);
    }

    /**
     * {@code DELETE  /km-crops/:id} : delete the "id" kmCrops.
     *
     * @param id the id of the kmCrops to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKmCrops(@PathVariable Long id) {
        log.debug("REST request to delete KmCrops : {}", id);
        kmCropsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
