package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.VillageMaster;
import com.cbs.middleware.repository.VillageMasterRepository;
import com.cbs.middleware.service.VillageMasterQueryService;
import com.cbs.middleware.service.VillageMasterService;
import com.cbs.middleware.service.criteria.VillageMasterCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.cbs.middleware.domain.VillageMaster}.
 */
@RestController
@RequestMapping("/api/village-masters")
public class VillageMasterResource {

    private final Logger log = LoggerFactory.getLogger(VillageMasterResource.class);

    private static final String ENTITY_NAME = "villageMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VillageMasterService villageMasterService;

    private final VillageMasterRepository villageMasterRepository;

    private final VillageMasterQueryService villageMasterQueryService;

    public VillageMasterResource(
        VillageMasterService villageMasterService,
        VillageMasterRepository villageMasterRepository,
        VillageMasterQueryService villageMasterQueryService
    ) {
        this.villageMasterService = villageMasterService;
        this.villageMasterRepository = villageMasterRepository;
        this.villageMasterQueryService = villageMasterQueryService;
    }

    /**
     * {@code POST  /village-masters} : Create a new villageMaster.
     *
     * @param villageMaster the villageMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new villageMaster, or with status {@code 400 (Bad Request)} if the villageMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VillageMaster> createVillageMaster(@Valid @RequestBody VillageMaster villageMaster) throws URISyntaxException {
        log.debug("REST request to save VillageMaster : {}", villageMaster);
        if (villageMaster.getId() != null) {
            throw new BadRequestAlertException("A new villageMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VillageMaster result = villageMasterService.save(villageMaster);
        return ResponseEntity
            .created(new URI("/api/village-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /village-masters/:id} : Updates an existing villageMaster.
     *
     * @param id the id of the villageMaster to save.
     * @param villageMaster the villageMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated villageMaster,
     * or with status {@code 400 (Bad Request)} if the villageMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the villageMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VillageMaster> updateVillageMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VillageMaster villageMaster
    ) throws URISyntaxException {
        log.debug("REST request to update VillageMaster : {}, {}", id, villageMaster);
        if (villageMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, villageMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!villageMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VillageMaster result = villageMasterService.update(villageMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, villageMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /village-masters/:id} : Partial updates given fields of an existing villageMaster, field will ignore if it is null
     *
     * @param id the id of the villageMaster to save.
     * @param villageMaster the villageMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated villageMaster,
     * or with status {@code 400 (Bad Request)} if the villageMaster is not valid,
     * or with status {@code 404 (Not Found)} if the villageMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the villageMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VillageMaster> partialUpdateVillageMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VillageMaster villageMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update VillageMaster partially : {}, {}", id, villageMaster);
        if (villageMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, villageMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!villageMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VillageMaster> result = villageMasterService.partialUpdate(villageMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, villageMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /village-masters} : get all the villageMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of villageMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<VillageMaster>> getAllVillageMasters(
        VillageMasterCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get VillageMasters by criteria: {}", criteria);

        Page<VillageMaster> page = villageMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /village-masters/count} : count all the villageMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countVillageMasters(VillageMasterCriteria criteria) {
        log.debug("REST request to count VillageMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(villageMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /village-masters/:id} : get the "id" villageMaster.
     *
     * @param id the id of the villageMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the villageMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VillageMaster> getVillageMaster(@PathVariable Long id) {
        log.debug("REST request to get VillageMaster : {}", id);
        Optional<VillageMaster> villageMaster = villageMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(villageMaster);
    }

    /**
     * {@code DELETE  /village-masters/:id} : delete the "id" villageMaster.
     *
     * @param id the id of the villageMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVillageMaster(@PathVariable Long id) {
        log.debug("REST request to delete VillageMaster : {}", id);
        villageMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
