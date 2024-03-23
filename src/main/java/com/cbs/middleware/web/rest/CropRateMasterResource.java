package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.CropRateMaster;
import com.cbs.middleware.repository.CropRateMasterRepository;
import com.cbs.middleware.service.CropRateMasterQueryService;
import com.cbs.middleware.service.CropRateMasterService;
import com.cbs.middleware.service.criteria.CropRateMasterCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.cbs.middleware.domain.CropRateMaster}.
 */
@RestController
@RequestMapping("/api")
public class CropRateMasterResource {

    private final Logger log = LoggerFactory.getLogger(CropRateMasterResource.class);

    private static final String ENTITY_NAME = "cropRateMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CropRateMasterService cropRateMasterService;

    private final CropRateMasterRepository cropRateMasterRepository;

    private final CropRateMasterQueryService cropRateMasterQueryService;

    public CropRateMasterResource(
        CropRateMasterService cropRateMasterService,
        CropRateMasterRepository cropRateMasterRepository,
        CropRateMasterQueryService cropRateMasterQueryService
    ) {
        this.cropRateMasterService = cropRateMasterService;
        this.cropRateMasterRepository = cropRateMasterRepository;
        this.cropRateMasterQueryService = cropRateMasterQueryService;
    }

    /**
     * {@code POST  /crop-rate-masters} : Create a new cropRateMaster.
     *
     * @param cropRateMaster the cropRateMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cropRateMaster, or with status {@code 400 (Bad Request)} if the cropRateMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crop-rate-masters")
    public ResponseEntity<CropRateMaster> createCropRateMaster(@Valid @RequestBody CropRateMaster cropRateMaster)
        throws URISyntaxException {
        log.debug("REST request to save CropRateMaster : {}", cropRateMaster);
        if (cropRateMaster.getId() != null) {
            throw new BadRequestAlertException("A new cropRateMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CropRateMaster result = cropRateMasterService.save(cropRateMaster);
        return ResponseEntity
            .created(new URI("/api/crop-rate-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crop-rate-masters/:id} : Updates an existing cropRateMaster.
     *
     * @param id the id of the cropRateMaster to save.
     * @param cropRateMaster the cropRateMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropRateMaster,
     * or with status {@code 400 (Bad Request)} if the cropRateMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cropRateMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crop-rate-masters/{id}")
    public ResponseEntity<CropRateMaster> updateCropRateMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CropRateMaster cropRateMaster
    ) throws URISyntaxException {
        log.debug("REST request to update CropRateMaster : {}, {}", id, cropRateMaster);
        if (cropRateMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropRateMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropRateMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CropRateMaster result = cropRateMasterService.update(cropRateMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropRateMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /crop-rate-masters/:id} : Partial updates given fields of an existing cropRateMaster, field will ignore if it is null
     *
     * @param id the id of the cropRateMaster to save.
     * @param cropRateMaster the cropRateMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropRateMaster,
     * or with status {@code 400 (Bad Request)} if the cropRateMaster is not valid,
     * or with status {@code 404 (Not Found)} if the cropRateMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the cropRateMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crop-rate-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CropRateMaster> partialUpdateCropRateMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CropRateMaster cropRateMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update CropRateMaster partially : {}, {}", id, cropRateMaster);
        if (cropRateMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropRateMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropRateMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CropRateMaster> result = cropRateMasterService.partialUpdate(cropRateMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropRateMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /crop-rate-masters} : get all the cropRateMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cropRateMasters in body.
     */
    @GetMapping("/crop-rate-masters")
    public ResponseEntity<List<CropRateMaster>> getAllCropRateMasters(
        CropRateMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CropRateMasters by criteria: {}", criteria);
        Page<CropRateMaster> page = cropRateMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crop-rate-masters/count} : count all the cropRateMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/crop-rate-masters/count")
    public ResponseEntity<Long> countCropRateMasters(CropRateMasterCriteria criteria) {
        log.debug("REST request to count CropRateMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(cropRateMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /crop-rate-masters/:id} : get the "id" cropRateMaster.
     *
     * @param id the id of the cropRateMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cropRateMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crop-rate-masters/{id}")
    public ResponseEntity<CropRateMaster> getCropRateMaster(@PathVariable Long id) {
        log.debug("REST request to get CropRateMaster : {}", id);
        Optional<CropRateMaster> cropRateMaster = cropRateMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cropRateMaster);
    }

    /**
     * {@code DELETE  /crop-rate-masters/:id} : delete the "id" cropRateMaster.
     *
     * @param id the id of the cropRateMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crop-rate-masters/{id}")
    public ResponseEntity<Void> deleteCropRateMaster(@PathVariable Long id) {
        log.debug("REST request to delete CropRateMaster : {}", id);
        cropRateMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
