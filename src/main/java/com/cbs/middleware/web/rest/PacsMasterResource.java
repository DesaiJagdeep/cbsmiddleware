package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.domain.PacsMaster;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.service.PacsMasterService;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.cbs.middleware.domain.PacsMaster}.
 */
@RestController
@RequestMapping("/api")
public class PacsMasterResource {

    private final Logger log = LoggerFactory.getLogger(PacsMasterResource.class);

    private static final String ENTITY_NAME = "pacsMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PacsMasterService pacsMasterService;

    private final PacsMasterRepository pacsMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    public PacsMasterResource(PacsMasterService pacsMasterService, PacsMasterRepository pacsMasterRepository) {
        this.pacsMasterService = pacsMasterService;
        this.pacsMasterRepository = pacsMasterRepository;
    }

    /**
     * {@code POST  /pacs-masters} : Create a new pacsMaster.
     *
     * @param pacsMaster the pacsMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pacsMaster, or with status {@code 400 (Bad Request)} if the pacsMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pacs-masters")
    public ResponseEntity<PacsMaster> createPacsMaster(@RequestBody PacsMaster pacsMaster) throws URISyntaxException {
        log.debug("REST request to save PacsMaster : {}", pacsMaster);
        if (pacsMaster.getId() != null) {
            throw new BadRequestAlertException("A new pacsMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacsMaster result = pacsMasterService.save(pacsMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Pacs Master Created",
                    "Pacs Master: " + result.getPacsName() + " Created",
                    false,
                    result.getCreatedDate(),
                    "PacsMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .created(new URI("/api/pacs-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pacs-masters/:id} : Updates an existing pacsMaster.
     *
     * @param id the id of the pacsMaster to save.
     * @param pacsMaster the pacsMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pacsMaster,
     * or with status {@code 400 (Bad Request)} if the pacsMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pacsMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pacs-masters/{id}")
    public ResponseEntity<PacsMaster> updatePacsMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PacsMaster pacsMaster
    ) throws URISyntaxException {
        log.debug("REST request to update PacsMaster : {}, {}", id, pacsMaster);
        if (pacsMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pacsMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacsMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PacsMaster result = pacsMasterService.update(pacsMaster);
        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Pacs Master Created",
                    "Pacs Master: " + result.getPacsName() + " Updated",
                    false,
                    result.getCreatedDate(),
                    "PacsMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pacsMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pacs-masters/:id} : Partial updates given fields of an existing pacsMaster, field will ignore if it is null
     *
     * @param id the id of the pacsMaster to save.
     * @param pacsMaster the pacsMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pacsMaster,
     * or with status {@code 400 (Bad Request)} if the pacsMaster is not valid,
     * or with status {@code 404 (Not Found)} if the pacsMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the pacsMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pacs-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PacsMaster> partialUpdatePacsMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PacsMaster pacsMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update PacsMaster partially : {}, {}", id, pacsMaster);
        if (pacsMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pacsMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacsMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PacsMaster> result = pacsMasterService.partialUpdate(pacsMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pacsMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /pacs-masters} : get all the pacsMasters.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pacsMasters in body.
     */
    @GetMapping("/pacs-masters")
    public ResponseEntity<List<PacsMaster>> getAllPacsMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of PacsMasters");
        Page<PacsMaster> page;
        if (eagerload) {
            page = pacsMasterService.findAllWithEagerRelationships(pageable);
        } else {
            page = pacsMasterService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pacs-masters/:id} : get the "id" pacsMaster.
     *
     * @param id the id of the pacsMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pacsMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pacs-masters/{id}")
    public ResponseEntity<PacsMaster> getPacsMaster(@PathVariable Long id) {
        log.debug("REST request to get PacsMaster : {}", id);
        Optional<PacsMaster> pacsMaster = pacsMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pacsMaster);
    }

    /**
     * {@code DELETE  /pacs-masters/:id} : delete the "id" pacsMaster.
     *
     * @param id the id of the pacsMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pacs-masters/{id}")
    public ResponseEntity<Void> deletePacsMaster(@PathVariable Long id) {
        log.debug("REST request to delete PacsMaster : {}", id);

        Optional<PacsMaster> pacsMaster = pacsMasterService.findOne(id);
        if (!pacsMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        PacsMaster result = pacsMaster.get();

        pacsMasterService.delete(id);

        try {
            notificationDataUtility.notificationData(
                "Pacs Master Deleted",
                "Pacs Master: " + result.getPacsName() + " Deleted",
                false,
                result.getCreatedDate(),
                "PacsMasterDeleted" //type
            );
        } catch (Exception e) {}

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
