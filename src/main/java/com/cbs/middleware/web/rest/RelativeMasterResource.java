package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.RelativeMaster;
import com.cbs.middleware.repository.RelativeMasterRepository;
import com.cbs.middleware.service.RelativeMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.RelativeMaster}.
 */
@RestController
@RequestMapping("/api")
public class RelativeMasterResource {

    private final Logger log = LoggerFactory.getLogger(RelativeMasterResource.class);

    private static final String ENTITY_NAME = "relativeMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelativeMasterService relativeMasterService;

    private final RelativeMasterRepository relativeMasterRepository;

    public RelativeMasterResource(RelativeMasterService relativeMasterService, RelativeMasterRepository relativeMasterRepository) {
        this.relativeMasterService = relativeMasterService;
        this.relativeMasterRepository = relativeMasterRepository;
    }

    /**
     * {@code POST  /relative-masters} : Create a new relativeMaster.
     *
     * @param relativeMaster the relativeMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relativeMaster, or with status {@code 400 (Bad Request)} if the relativeMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relative-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<RelativeMaster> createRelativeMaster(@RequestBody RelativeMaster relativeMaster) throws URISyntaxException {
        log.debug("REST request to save RelativeMaster : {}", relativeMaster);
        if (relativeMaster.getId() != null) {
            throw new BadRequestAlertException("A new relativeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RelativeMaster result = relativeMasterService.save(relativeMaster);
        return ResponseEntity
            .created(new URI("/api/relative-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relative-masters/:id} : Updates an existing relativeMaster.
     *
     * @param id the id of the relativeMaster to save.
     * @param relativeMaster the relativeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relativeMaster,
     * or with status {@code 400 (Bad Request)} if the relativeMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relativeMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relative-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<RelativeMaster> updateRelativeMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelativeMaster relativeMaster
    ) throws URISyntaxException {
        log.debug("REST request to update RelativeMaster : {}, {}", id, relativeMaster);
        if (relativeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relativeMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relativeMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RelativeMaster result = relativeMasterService.update(relativeMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relativeMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /relative-masters/:id} : Partial updates given fields of an existing relativeMaster, field will ignore if it is null
     *
     * @param id the id of the relativeMaster to save.
     * @param relativeMaster the relativeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relativeMaster,
     * or with status {@code 400 (Bad Request)} if the relativeMaster is not valid,
     * or with status {@code 404 (Not Found)} if the relativeMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the relativeMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/relative-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<RelativeMaster> partialUpdateRelativeMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RelativeMaster relativeMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelativeMaster partially : {}, {}", id, relativeMaster);
        if (relativeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relativeMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relativeMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RelativeMaster> result = relativeMasterService.partialUpdate(relativeMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relativeMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /relative-masters} : get all the relativeMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relativeMasters in body.
     */
    @GetMapping("/relative-masters")
    public ResponseEntity<List<RelativeMaster>> getAllRelativeMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RelativeMasters");
        Page<RelativeMaster> page = relativeMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /relative-masters/:id} : get the "id" relativeMaster.
     *
     * @param id the id of the relativeMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relativeMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relative-masters/{id}")
    public ResponseEntity<RelativeMaster> getRelativeMaster(@PathVariable Long id) {
        log.debug("REST request to get RelativeMaster : {}", id);
        Optional<RelativeMaster> relativeMaster = relativeMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relativeMaster);
    }

    /**
     * {@code DELETE  /relative-masters/:id} : delete the "id" relativeMaster.
     *
     * @param id the id of the relativeMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relative-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteRelativeMaster(@PathVariable Long id) {
        log.debug("REST request to delete RelativeMaster : {}", id);
        relativeMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
