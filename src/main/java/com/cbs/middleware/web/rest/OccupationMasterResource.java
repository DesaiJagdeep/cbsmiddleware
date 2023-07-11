package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.OccupationMaster;
import com.cbs.middleware.repository.OccupationMasterRepository;
import com.cbs.middleware.service.OccupationMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.OccupationMaster}.
 */
@RestController
@RequestMapping("/api")
public class OccupationMasterResource {

    private final Logger log = LoggerFactory.getLogger(OccupationMasterResource.class);

    private static final String ENTITY_NAME = "occupationMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OccupationMasterService occupationMasterService;

    private final OccupationMasterRepository occupationMasterRepository;

    public OccupationMasterResource(
        OccupationMasterService occupationMasterService,
        OccupationMasterRepository occupationMasterRepository
    ) {
        this.occupationMasterService = occupationMasterService;
        this.occupationMasterRepository = occupationMasterRepository;
    }

    /**
     * {@code POST  /occupation-masters} : Create a new occupationMaster.
     *
     * @param occupationMaster the occupationMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new occupationMaster, or with status {@code 400 (Bad Request)} if the occupationMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/occupation-masters")
    public ResponseEntity<OccupationMaster> createOccupationMaster(@RequestBody OccupationMaster occupationMaster)
        throws URISyntaxException {
        log.debug("REST request to save OccupationMaster : {}", occupationMaster);
        if (occupationMaster.getId() != null) {
            throw new BadRequestAlertException("A new occupationMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OccupationMaster result = occupationMasterService.save(occupationMaster);
        return ResponseEntity
            .created(new URI("/api/occupation-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /occupation-masters/:id} : Updates an existing occupationMaster.
     *
     * @param id the id of the occupationMaster to save.
     * @param occupationMaster the occupationMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated occupationMaster,
     * or with status {@code 400 (Bad Request)} if the occupationMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the occupationMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/occupation-masters/{id}")
    public ResponseEntity<OccupationMaster> updateOccupationMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OccupationMaster occupationMaster
    ) throws URISyntaxException {
        log.debug("REST request to update OccupationMaster : {}, {}", id, occupationMaster);
        if (occupationMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, occupationMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!occupationMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OccupationMaster result = occupationMasterService.update(occupationMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, occupationMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /occupation-masters/:id} : Partial updates given fields of an existing occupationMaster, field will ignore if it is null
     *
     * @param id the id of the occupationMaster to save.
     * @param occupationMaster the occupationMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated occupationMaster,
     * or with status {@code 400 (Bad Request)} if the occupationMaster is not valid,
     * or with status {@code 404 (Not Found)} if the occupationMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the occupationMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/occupation-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OccupationMaster> partialUpdateOccupationMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OccupationMaster occupationMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update OccupationMaster partially : {}, {}", id, occupationMaster);
        if (occupationMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, occupationMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!occupationMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OccupationMaster> result = occupationMasterService.partialUpdate(occupationMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, occupationMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /occupation-masters} : get all the occupationMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of occupationMasters in body.
     */
    @GetMapping("/occupation-masters")
    public ResponseEntity<List<OccupationMaster>> getAllOccupationMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of OccupationMasters");
        Page<OccupationMaster> page = occupationMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /occupation-masters/:id} : get the "id" occupationMaster.
     *
     * @param id the id of the occupationMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the occupationMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/occupation-masters/{id}")
    public ResponseEntity<OccupationMaster> getOccupationMaster(@PathVariable Long id) {
        log.debug("REST request to get OccupationMaster : {}", id);
        Optional<OccupationMaster> occupationMaster = occupationMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(occupationMaster);
    }

    /**
     * {@code DELETE  /occupation-masters/:id} : delete the "id" occupationMaster.
     *
     * @param id the id of the occupationMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/occupation-masters/{id}")
    public ResponseEntity<Void> deleteOccupationMaster(@PathVariable Long id) {
        log.debug("REST request to delete OccupationMaster : {}", id);
        occupationMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
