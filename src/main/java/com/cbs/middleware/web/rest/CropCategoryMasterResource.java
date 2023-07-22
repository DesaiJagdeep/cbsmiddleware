package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.CropCategoryMaster;
import com.cbs.middleware.repository.CropCategoryMasterRepository;
import com.cbs.middleware.service.CropCategoryMasterService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.CropCategoryMaster}.
 */
@RestController
@RequestMapping("/api")
public class CropCategoryMasterResource {

    private final Logger log = LoggerFactory.getLogger(CropCategoryMasterResource.class);

    private static final String ENTITY_NAME = "cropCategoryMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CropCategoryMasterService cropCategoryMasterService;

    private final CropCategoryMasterRepository cropCategoryMasterRepository;

    public CropCategoryMasterResource(
        CropCategoryMasterService cropCategoryMasterService,
        CropCategoryMasterRepository cropCategoryMasterRepository
    ) {
        this.cropCategoryMasterService = cropCategoryMasterService;
        this.cropCategoryMasterRepository = cropCategoryMasterRepository;
    }

    /**
     * {@code POST  /crop-category-masters} : Create a new cropCategoryMaster.
     *
     * @param cropCategoryMaster the cropCategoryMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cropCategoryMaster, or with status {@code 400 (Bad Request)} if the cropCategoryMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crop-category-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CropCategoryMaster> createCropCategoryMaster(@RequestBody CropCategoryMaster cropCategoryMaster)
        throws URISyntaxException {
        log.debug("REST request to save CropCategoryMaster : {}", cropCategoryMaster);
        if (cropCategoryMaster.getId() != null) {
            throw new BadRequestAlertException("A new cropCategoryMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CropCategoryMaster result = cropCategoryMasterService.save(cropCategoryMaster);
        return ResponseEntity
            .created(new URI("/api/crop-category-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crop-category-masters/:id} : Updates an existing cropCategoryMaster.
     *
     * @param id the id of the cropCategoryMaster to save.
     * @param cropCategoryMaster the cropCategoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropCategoryMaster,
     * or with status {@code 400 (Bad Request)} if the cropCategoryMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cropCategoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crop-category-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CropCategoryMaster> updateCropCategoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CropCategoryMaster cropCategoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to update CropCategoryMaster : {}, {}", id, cropCategoryMaster);
        if (cropCategoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropCategoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropCategoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CropCategoryMaster result = cropCategoryMasterService.update(cropCategoryMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropCategoryMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /crop-category-masters/:id} : Partial updates given fields of an existing cropCategoryMaster, field will ignore if it is null
     *
     * @param id the id of the cropCategoryMaster to save.
     * @param cropCategoryMaster the cropCategoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropCategoryMaster,
     * or with status {@code 400 (Bad Request)} if the cropCategoryMaster is not valid,
     * or with status {@code 404 (Not Found)} if the cropCategoryMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the cropCategoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crop-category-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CropCategoryMaster> partialUpdateCropCategoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CropCategoryMaster cropCategoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update CropCategoryMaster partially : {}, {}", id, cropCategoryMaster);
        if (cropCategoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropCategoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropCategoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CropCategoryMaster> result = cropCategoryMasterService.partialUpdate(cropCategoryMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropCategoryMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /crop-category-masters} : get all the cropCategoryMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cropCategoryMasters in body.
     */
    @GetMapping("/crop-category-masters")
    public ResponseEntity<List<CropCategoryMaster>> getAllCropCategoryMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CropCategoryMasters");
        Page<CropCategoryMaster> page = cropCategoryMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crop-category-masters/:id} : get the "id" cropCategoryMaster.
     *
     * @param id the id of the cropCategoryMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cropCategoryMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crop-category-masters/{id}")
    public ResponseEntity<CropCategoryMaster> getCropCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to get CropCategoryMaster : {}", id);
        Optional<CropCategoryMaster> cropCategoryMaster = cropCategoryMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cropCategoryMaster);
    }

    /**
     * {@code DELETE  /crop-category-masters/:id} : delete the "id" cropCategoryMaster.
     *
     * @param id the id of the cropCategoryMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crop-category-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteCropCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to delete CropCategoryMaster : {}", id);
        cropCategoryMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
