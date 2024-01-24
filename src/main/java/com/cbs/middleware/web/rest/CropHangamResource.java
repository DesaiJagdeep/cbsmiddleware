package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.CropHangam;
import com.cbs.middleware.repository.CropHangamRepository;
import com.cbs.middleware.service.CropHangamQueryService;
import com.cbs.middleware.service.CropHangamService;
import com.cbs.middleware.service.criteria.CropHangamCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.CropHangam}.
 */
@RestController
@RequestMapping("/api")
public class CropHangamResource {

    private final Logger log = LoggerFactory.getLogger(CropHangamResource.class);

    private static final String ENTITY_NAME = "cropHangam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CropHangamService cropHangamService;

    private final CropHangamRepository cropHangamRepository;

    private final CropHangamQueryService cropHangamQueryService;

    public CropHangamResource(
        CropHangamService cropHangamService,
        CropHangamRepository cropHangamRepository,
        CropHangamQueryService cropHangamQueryService
    ) {
        this.cropHangamService = cropHangamService;
        this.cropHangamRepository = cropHangamRepository;
        this.cropHangamQueryService = cropHangamQueryService;
    }

    /**
     * {@code POST  /crop-hangams} : Create a new cropHangam.
     *
     * @param cropHangam the cropHangam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cropHangam, or with status {@code 400 (Bad Request)} if the cropHangam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crop-hangams")
    public ResponseEntity<CropHangam> createCropHangam(@RequestBody CropHangam cropHangam) throws URISyntaxException {
        log.debug("REST request to save CropHangam : {}", cropHangam);
        if (cropHangam.getId() != null) {
            throw new BadRequestAlertException("A new cropHangam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CropHangam result = cropHangamService.save(cropHangam);
        return ResponseEntity
            .created(new URI("/api/crop-hangams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crop-hangams/:id} : Updates an existing cropHangam.
     *
     * @param id the id of the cropHangam to save.
     * @param cropHangam the cropHangam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropHangam,
     * or with status {@code 400 (Bad Request)} if the cropHangam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cropHangam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crop-hangams/{id}")
    public ResponseEntity<CropHangam> updateCropHangam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CropHangam cropHangam
    ) throws URISyntaxException {
        log.debug("REST request to update CropHangam : {}, {}", id, cropHangam);
        if (cropHangam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropHangam.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropHangamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CropHangam result = cropHangamService.update(cropHangam);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropHangam.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /crop-hangams/:id} : Partial updates given fields of an existing cropHangam, field will ignore if it is null
     *
     * @param id the id of the cropHangam to save.
     * @param cropHangam the cropHangam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropHangam,
     * or with status {@code 400 (Bad Request)} if the cropHangam is not valid,
     * or with status {@code 404 (Not Found)} if the cropHangam is not found,
     * or with status {@code 500 (Internal Server Error)} if the cropHangam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crop-hangams/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CropHangam> partialUpdateCropHangam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CropHangam cropHangam
    ) throws URISyntaxException {
        log.debug("REST request to partial update CropHangam partially : {}, {}", id, cropHangam);
        if (cropHangam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropHangam.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropHangamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CropHangam> result = cropHangamService.partialUpdate(cropHangam);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropHangam.getId().toString())
        );
    }

    /**
     * {@code GET  /crop-hangams} : get all the cropHangams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cropHangams in body.
     */
    @GetMapping("/crop-hangams")
    public ResponseEntity<List<CropHangam>> getAllCropHangams(
        CropHangamCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CropHangams by criteria: {}", criteria);
        Page<CropHangam> page = cropHangamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crop-hangams/count} : count all the cropHangams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/crop-hangams/count")
    public ResponseEntity<Long> countCropHangams(CropHangamCriteria criteria) {
        log.debug("REST request to count CropHangams by criteria: {}", criteria);
        return ResponseEntity.ok().body(cropHangamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /crop-hangams/:id} : get the "id" cropHangam.
     *
     * @param id the id of the cropHangam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cropHangam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crop-hangams/{id}")
    public ResponseEntity<CropHangam> getCropHangam(@PathVariable Long id) {
        log.debug("REST request to get CropHangam : {}", id);
        Optional<CropHangam> cropHangam = cropHangamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cropHangam);
    }

    /**
     * {@code DELETE  /crop-hangams/:id} : delete the "id" cropHangam.
     *
     * @param id the id of the cropHangam to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crop-hangams/{id}")
    public ResponseEntity<Void> deleteCropHangam(@PathVariable Long id) {
        log.debug("REST request to delete CropHangam : {}", id);
        cropHangamService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
