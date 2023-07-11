package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.LandTypeMaster;
import com.cbs.middleware.repository.LandTypeMasterRepository;
import com.cbs.middleware.service.LandTypeMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.LandTypeMaster}.
 */
@RestController
@RequestMapping("/api")
public class LandTypeMasterResource {

    private final Logger log = LoggerFactory.getLogger(LandTypeMasterResource.class);

    private static final String ENTITY_NAME = "landTypeMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LandTypeMasterService landTypeMasterService;

    private final LandTypeMasterRepository landTypeMasterRepository;

    public LandTypeMasterResource(LandTypeMasterService landTypeMasterService, LandTypeMasterRepository landTypeMasterRepository) {
        this.landTypeMasterService = landTypeMasterService;
        this.landTypeMasterRepository = landTypeMasterRepository;
    }

    /**
     * {@code POST  /land-type-masters} : Create a new landTypeMaster.
     *
     * @param landTypeMaster the landTypeMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new landTypeMaster, or with status {@code 400 (Bad Request)} if the landTypeMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/land-type-masters")
    public ResponseEntity<LandTypeMaster> createLandTypeMaster(@RequestBody LandTypeMaster landTypeMaster) throws URISyntaxException {
        log.debug("REST request to save LandTypeMaster : {}", landTypeMaster);
        if (landTypeMaster.getId() != null) {
            throw new BadRequestAlertException("A new landTypeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LandTypeMaster result = landTypeMasterService.save(landTypeMaster);
        return ResponseEntity
            .created(new URI("/api/land-type-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /land-type-masters/:id} : Updates an existing landTypeMaster.
     *
     * @param id the id of the landTypeMaster to save.
     * @param landTypeMaster the landTypeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landTypeMaster,
     * or with status {@code 400 (Bad Request)} if the landTypeMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the landTypeMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/land-type-masters/{id}")
    public ResponseEntity<LandTypeMaster> updateLandTypeMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LandTypeMaster landTypeMaster
    ) throws URISyntaxException {
        log.debug("REST request to update LandTypeMaster : {}, {}", id, landTypeMaster);
        if (landTypeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landTypeMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landTypeMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LandTypeMaster result = landTypeMasterService.update(landTypeMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landTypeMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /land-type-masters/:id} : Partial updates given fields of an existing landTypeMaster, field will ignore if it is null
     *
     * @param id the id of the landTypeMaster to save.
     * @param landTypeMaster the landTypeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landTypeMaster,
     * or with status {@code 400 (Bad Request)} if the landTypeMaster is not valid,
     * or with status {@code 404 (Not Found)} if the landTypeMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the landTypeMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/land-type-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LandTypeMaster> partialUpdateLandTypeMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LandTypeMaster landTypeMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update LandTypeMaster partially : {}, {}", id, landTypeMaster);
        if (landTypeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landTypeMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landTypeMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LandTypeMaster> result = landTypeMasterService.partialUpdate(landTypeMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landTypeMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /land-type-masters} : get all the landTypeMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of landTypeMasters in body.
     */
    @GetMapping("/land-type-masters")
    public ResponseEntity<List<LandTypeMaster>> getAllLandTypeMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LandTypeMasters");
        Page<LandTypeMaster> page = landTypeMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /land-type-masters/:id} : get the "id" landTypeMaster.
     *
     * @param id the id of the landTypeMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the landTypeMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/land-type-masters/{id}")
    public ResponseEntity<LandTypeMaster> getLandTypeMaster(@PathVariable Long id) {
        log.debug("REST request to get LandTypeMaster : {}", id);
        Optional<LandTypeMaster> landTypeMaster = landTypeMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(landTypeMaster);
    }

    /**
     * {@code DELETE  /land-type-masters/:id} : delete the "id" landTypeMaster.
     *
     * @param id the id of the landTypeMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/land-type-masters/{id}")
    public ResponseEntity<Void> deleteLandTypeMaster(@PathVariable Long id) {
        log.debug("REST request to delete LandTypeMaster : {}", id);
        landTypeMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
