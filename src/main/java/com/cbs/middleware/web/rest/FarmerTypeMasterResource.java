package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.repository.FarmerTypeMasterRepository;
import com.cbs.middleware.service.FarmerTypeMasterService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.FarmerTypeMaster}.
 */
@RestController
@RequestMapping("/api")
public class FarmerTypeMasterResource {

    private final Logger log = LoggerFactory.getLogger(FarmerTypeMasterResource.class);

    private static final String ENTITY_NAME = "farmerTypeMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FarmerTypeMasterService farmerTypeMasterService;

    private final FarmerTypeMasterRepository farmerTypeMasterRepository;

    public FarmerTypeMasterResource(
        FarmerTypeMasterService farmerTypeMasterService,
        FarmerTypeMasterRepository farmerTypeMasterRepository
    ) {
        this.farmerTypeMasterService = farmerTypeMasterService;
        this.farmerTypeMasterRepository = farmerTypeMasterRepository;
    }

    /**
     * {@code POST  /farmer-type-masters} : Create a new farmerTypeMaster.
     *
     * @param farmerTypeMaster the farmerTypeMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new farmerTypeMaster, or with status {@code 400 (Bad Request)} if the farmerTypeMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/farmer-type-masters")
    public ResponseEntity<FarmerTypeMaster> createFarmerTypeMaster(@RequestBody FarmerTypeMaster farmerTypeMaster)
        throws URISyntaxException {
        log.debug("REST request to save FarmerTypeMaster : {}", farmerTypeMaster);
        if (farmerTypeMaster.getId() != null) {
            throw new BadRequestAlertException("A new farmerTypeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FarmerTypeMaster result = farmerTypeMasterService.save(farmerTypeMaster);
        return ResponseEntity
            .created(new URI("/api/farmer-type-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /farmer-type-masters/:id} : Updates an existing farmerTypeMaster.
     *
     * @param id the id of the farmerTypeMaster to save.
     * @param farmerTypeMaster the farmerTypeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated farmerTypeMaster,
     * or with status {@code 400 (Bad Request)} if the farmerTypeMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the farmerTypeMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/farmer-type-masters/{id}")
    public ResponseEntity<FarmerTypeMaster> updateFarmerTypeMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FarmerTypeMaster farmerTypeMaster
    ) throws URISyntaxException {
        log.debug("REST request to update FarmerTypeMaster : {}, {}", id, farmerTypeMaster);
        if (farmerTypeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, farmerTypeMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!farmerTypeMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FarmerTypeMaster result = farmerTypeMasterService.update(farmerTypeMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farmerTypeMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /farmer-type-masters/:id} : Partial updates given fields of an existing farmerTypeMaster, field will ignore if it is null
     *
     * @param id the id of the farmerTypeMaster to save.
     * @param farmerTypeMaster the farmerTypeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated farmerTypeMaster,
     * or with status {@code 400 (Bad Request)} if the farmerTypeMaster is not valid,
     * or with status {@code 404 (Not Found)} if the farmerTypeMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the farmerTypeMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/farmer-type-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FarmerTypeMaster> partialUpdateFarmerTypeMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FarmerTypeMaster farmerTypeMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update FarmerTypeMaster partially : {}, {}", id, farmerTypeMaster);
        if (farmerTypeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, farmerTypeMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!farmerTypeMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FarmerTypeMaster> result = farmerTypeMasterService.partialUpdate(farmerTypeMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farmerTypeMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /farmer-type-masters} : get all the farmerTypeMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of farmerTypeMasters in body.
     */
    @GetMapping("/farmer-type-masters")
    public ResponseEntity<List<FarmerTypeMaster>> getAllFarmerTypeMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FarmerTypeMasters");
        Page<FarmerTypeMaster> page = farmerTypeMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /farmer-type-masters/:id} : get the "id" farmerTypeMaster.
     *
     * @param id the id of the farmerTypeMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the farmerTypeMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/farmer-type-masters/{id}")
    public ResponseEntity<FarmerTypeMaster> getFarmerTypeMaster(@PathVariable Long id) {
        log.debug("REST request to get FarmerTypeMaster : {}", id);
        Optional<FarmerTypeMaster> farmerTypeMaster = farmerTypeMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(farmerTypeMaster);
    }

    /**
     * {@code DELETE  /farmer-type-masters/:id} : delete the "id" farmerTypeMaster.
     *
     * @param id the id of the farmerTypeMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/farmer-type-masters/{id}")
    public ResponseEntity<Void> deleteFarmerTypeMaster(@PathVariable Long id) {
        log.debug("REST request to delete FarmerTypeMaster : {}", id);
        farmerTypeMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
