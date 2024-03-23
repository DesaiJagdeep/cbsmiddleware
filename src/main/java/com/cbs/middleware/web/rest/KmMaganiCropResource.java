package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KmMaganiCrop;
import com.cbs.middleware.repository.KmMaganiCropRepository;
import com.cbs.middleware.service.KmMaganiCropQueryService;
import com.cbs.middleware.service.KmMaganiCropService;
import com.cbs.middleware.service.criteria.KmMaganiCropCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KmMaganiCrop}.
 */
@RestController
@RequestMapping("/api")
public class KmMaganiCropResource {

    private final Logger log = LoggerFactory.getLogger(KmMaganiCropResource.class);

    private static final String ENTITY_NAME = "kmMaganiCrop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KmMaganiCropService kmMaganiCropService;

    private final KmMaganiCropRepository kmMaganiCropRepository;

    private final KmMaganiCropQueryService kmMaganiCropQueryService;

    public KmMaganiCropResource(
        KmMaganiCropService kmMaganiCropService,
        KmMaganiCropRepository kmMaganiCropRepository,
        KmMaganiCropQueryService kmMaganiCropQueryService
    ) {
        this.kmMaganiCropService = kmMaganiCropService;
        this.kmMaganiCropRepository = kmMaganiCropRepository;
        this.kmMaganiCropQueryService = kmMaganiCropQueryService;
    }

    /**
     * {@code POST  /km-magani-crops} : Create a new kmMaganiCrop.
     *
     * @param kmMaganiCrop the kmMaganiCrop to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kmMaganiCrop, or with status {@code 400 (Bad Request)} if the kmMaganiCrop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/km-magani-crops")
    public ResponseEntity<KmMaganiCrop> createKmMaganiCrop(@RequestBody KmMaganiCrop kmMaganiCrop) throws URISyntaxException {
        log.debug("REST request to save KmMaganiCrop : {}", kmMaganiCrop);
        if (kmMaganiCrop.getId() != null) {
            throw new BadRequestAlertException("A new kmMaganiCrop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KmMaganiCrop result = kmMaganiCropService.save(kmMaganiCrop);
        return ResponseEntity
            .created(new URI("/api/km-magani-crops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /km-magani-crops/:id} : Updates an existing kmMaganiCrop.
     *
     * @param id the id of the kmMaganiCrop to save.
     * @param kmMaganiCrop the kmMaganiCrop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmMaganiCrop,
     * or with status {@code 400 (Bad Request)} if the kmMaganiCrop is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kmMaganiCrop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/km-magani-crops/{id}")
    public ResponseEntity<KmMaganiCrop> updateKmMaganiCrop(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmMaganiCrop kmMaganiCrop
    ) throws URISyntaxException {
        log.debug("REST request to update KmMaganiCrop : {}, {}", id, kmMaganiCrop);
        if (kmMaganiCrop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmMaganiCrop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmMaganiCropRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KmMaganiCrop result = kmMaganiCropService.update(kmMaganiCrop);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmMaganiCrop.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /km-magani-crops/:id} : Partial updates given fields of an existing kmMaganiCrop, field will ignore if it is null
     *
     * @param id the id of the kmMaganiCrop to save.
     * @param kmMaganiCrop the kmMaganiCrop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmMaganiCrop,
     * or with status {@code 400 (Bad Request)} if the kmMaganiCrop is not valid,
     * or with status {@code 404 (Not Found)} if the kmMaganiCrop is not found,
     * or with status {@code 500 (Internal Server Error)} if the kmMaganiCrop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/km-magani-crops/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KmMaganiCrop> partialUpdateKmMaganiCrop(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmMaganiCrop kmMaganiCrop
    ) throws URISyntaxException {
        log.debug("REST request to partial update KmMaganiCrop partially : {}, {}", id, kmMaganiCrop);
        if (kmMaganiCrop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmMaganiCrop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmMaganiCropRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KmMaganiCrop> result = kmMaganiCropService.partialUpdate(kmMaganiCrop);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmMaganiCrop.getId().toString())
        );
    }

    /**
     * {@code GET  /km-magani-crops} : get all the kmMaganiCrops.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kmMaganiCrops in body.
     */
    @GetMapping("/km-magani-crops")
    public ResponseEntity<List<KmMaganiCrop>> getAllKmMaganiCrops(
        KmMaganiCropCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KmMaganiCrops by criteria: {}", criteria);
        Page<KmMaganiCrop> page = kmMaganiCropQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /km-magani-crops/count} : count all the kmMaganiCrops.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/km-magani-crops/count")
    public ResponseEntity<Long> countKmMaganiCrops(KmMaganiCropCriteria criteria) {
        log.debug("REST request to count KmMaganiCrops by criteria: {}", criteria);
        return ResponseEntity.ok().body(kmMaganiCropQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /km-magani-crops/:id} : get the "id" kmMaganiCrop.
     *
     * @param id the id of the kmMaganiCrop to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kmMaganiCrop, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/km-magani-crops/{id}")
    public ResponseEntity<KmMaganiCrop> getKmMaganiCrop(@PathVariable Long id) {
        log.debug("REST request to get KmMaganiCrop : {}", id);
        Optional<KmMaganiCrop> kmMaganiCrop = kmMaganiCropService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kmMaganiCrop);
    }

    /**
     * {@code DELETE  /km-magani-crops/:id} : delete the "id" kmMaganiCrop.
     *
     * @param id the id of the kmMaganiCrop to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/km-magani-crops/{id}")
    public ResponseEntity<Void> deleteKmMaganiCrop(@PathVariable Long id) {
        log.debug("REST request to delete KmMaganiCrop : {}", id);
        kmMaganiCropService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
