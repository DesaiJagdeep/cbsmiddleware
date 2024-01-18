package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.FactoryMaster;
import com.cbs.middleware.repository.FactoryMasterRepository;
import com.cbs.middleware.service.FactoryMasterQueryService;
import com.cbs.middleware.service.FactoryMasterService;
import com.cbs.middleware.service.criteria.FactoryMasterCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.FactoryMaster}.
 */
@RestController
@RequestMapping("/api")
public class FactoryMasterResource {

    private final Logger log = LoggerFactory.getLogger(FactoryMasterResource.class);

    private static final String ENTITY_NAME = "factoryMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactoryMasterService factoryMasterService;

    private final FactoryMasterRepository factoryMasterRepository;

    private final FactoryMasterQueryService factoryMasterQueryService;

    public FactoryMasterResource(
        FactoryMasterService factoryMasterService,
        FactoryMasterRepository factoryMasterRepository,
        FactoryMasterQueryService factoryMasterQueryService
    ) {
        this.factoryMasterService = factoryMasterService;
        this.factoryMasterRepository = factoryMasterRepository;
        this.factoryMasterQueryService = factoryMasterQueryService;
    }

    /**
     * {@code POST  /factory-masters} : Create a new factoryMaster.
     *
     * @param factoryMaster the factoryMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factoryMaster, or with status {@code 400 (Bad Request)} if the factoryMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/factory-masters")
    public ResponseEntity<FactoryMaster> createFactoryMaster(@RequestBody FactoryMaster factoryMaster) throws URISyntaxException {
        log.debug("REST request to save FactoryMaster : {}", factoryMaster);
        if (factoryMaster.getId() != null) {
            throw new BadRequestAlertException("A new factoryMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactoryMaster result = factoryMasterService.save(factoryMaster);
        return ResponseEntity
            .created(new URI("/api/factory-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /factory-masters/:id} : Updates an existing factoryMaster.
     *
     * @param id the id of the factoryMaster to save.
     * @param factoryMaster the factoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factoryMaster,
     * or with status {@code 400 (Bad Request)} if the factoryMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/factory-masters/{id}")
    public ResponseEntity<FactoryMaster> updateFactoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FactoryMaster factoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to update FactoryMaster : {}, {}", id, factoryMaster);
        if (factoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FactoryMaster result = factoryMasterService.update(factoryMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, factoryMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /factory-masters/:id} : Partial updates given fields of an existing factoryMaster, field will ignore if it is null
     *
     * @param id the id of the factoryMaster to save.
     * @param factoryMaster the factoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factoryMaster,
     * or with status {@code 400 (Bad Request)} if the factoryMaster is not valid,
     * or with status {@code 404 (Not Found)} if the factoryMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the factoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/factory-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FactoryMaster> partialUpdateFactoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FactoryMaster factoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update FactoryMaster partially : {}, {}", id, factoryMaster);
        if (factoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FactoryMaster> result = factoryMasterService.partialUpdate(factoryMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, factoryMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /factory-masters} : get all the factoryMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factoryMasters in body.
     */
    @GetMapping("/factory-masters")
    public ResponseEntity<List<FactoryMaster>> getAllFactoryMasters(
        FactoryMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FactoryMasters by criteria: {}", criteria);
        Page<FactoryMaster> page = factoryMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /factory-masters/count} : count all the factoryMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/factory-masters/count")
    public ResponseEntity<Long> countFactoryMasters(FactoryMasterCriteria criteria) {
        log.debug("REST request to count FactoryMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(factoryMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /factory-masters/:id} : get the "id" factoryMaster.
     *
     * @param id the id of the factoryMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factoryMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/factory-masters/{id}")
    public ResponseEntity<FactoryMaster> getFactoryMaster(@PathVariable Long id) {
        log.debug("REST request to get FactoryMaster : {}", id);
        Optional<FactoryMaster> factoryMaster = factoryMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factoryMaster);
    }

    /**
     * {@code DELETE  /factory-masters/:id} : delete the "id" factoryMaster.
     *
     * @param id the id of the factoryMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/factory-masters/{id}")
    public ResponseEntity<Void> deleteFactoryMaster(@PathVariable Long id) {
        log.debug("REST request to delete FactoryMaster : {}", id);
        factoryMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
