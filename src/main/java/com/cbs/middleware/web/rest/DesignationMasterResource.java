package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.DesignationMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.repository.DesignationMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.DesignationMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.DesignationMaster}.
 */
@RestController
@RequestMapping("/api")
public class DesignationMasterResource {

    private final Logger log = LoggerFactory.getLogger(DesignationMasterResource.class);

    private static final String ENTITY_NAME = "designationMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DesignationMasterService designationMasterService;

    private final DesignationMasterRepository designationMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    public DesignationMasterResource(
        DesignationMasterService designationMasterService,
        DesignationMasterRepository designationMasterRepository
    ) {
        this.designationMasterService = designationMasterService;
        this.designationMasterRepository = designationMasterRepository;
    }

    /**
     * {@code POST  /designation-masters} : Create a new designationMaster.
     *
     * @param designationMaster the designationMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new designationMaster, or with status {@code 400 (Bad Request)} if the designationMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/designation-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<DesignationMaster> createDesignationMaster(@RequestBody DesignationMaster designationMaster)
        throws URISyntaxException {
        log.debug("REST request to save DesignationMaster : {}", designationMaster);
        if (designationMaster.getId() != null) {
            throw new BadRequestAlertException("A new designationMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignationMaster result = designationMasterService.save(designationMaster);
        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Designation Master Created",
                    "Designation Master: " + result.getDesignationName() + " Created",
                    false,
                    result.getCreatedDate(),
                    "DesignationMasterUpdated" //type
                );
            } catch (Exception e) {}
        }

        return ResponseEntity
            .created(new URI("/api/designation-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /designation-masters/:id} : Updates an existing designationMaster.
     *
     * @param id the id of the designationMaster to save.
     * @param designationMaster the designationMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated designationMaster,
     * or with status {@code 400 (Bad Request)} if the designationMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the designationMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/designation-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<DesignationMaster> updateDesignationMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DesignationMaster designationMaster
    ) throws URISyntaxException {
        log.debug("REST request to update DesignationMaster : {}, {}", id, designationMaster);
        if (designationMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, designationMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!designationMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DesignationMaster result = designationMasterService.update(designationMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Designation Master Updated",
                    "Designation Master: " + result.getDesignationName() + " Updated",
                    false,
                    result.getCreatedDate(),
                    "DesignationMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, designationMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /designation-masters/:id} : Partial updates given fields of an existing designationMaster, field will ignore if it is null
     *
     * @param id the id of the designationMaster to save.
     * @param designationMaster the designationMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated designationMaster,
     * or with status {@code 400 (Bad Request)} if the designationMaster is not valid,
     * or with status {@code 404 (Not Found)} if the designationMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the designationMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/designation-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<DesignationMaster> partialUpdateDesignationMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DesignationMaster designationMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update DesignationMaster partially : {}, {}", id, designationMaster);
        if (designationMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, designationMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!designationMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DesignationMaster> result = designationMasterService.partialUpdate(designationMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, designationMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /designation-masters} : get all the designationMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of designationMasters in body.
     */
    @GetMapping("/designation-masters")
    public ResponseEntity<List<DesignationMaster>> getAllDesignationMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of DesignationMasters");
        Page<DesignationMaster> page = designationMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /designation-masters/:id} : get the "id" designationMaster.
     *
     * @param id the id of the designationMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the designationMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/designation-masters/{id}")
    public ResponseEntity<DesignationMaster> getDesignationMaster(@PathVariable Long id) {
        log.debug("REST request to get DesignationMaster : {}", id);
        Optional<DesignationMaster> designationMaster = designationMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(designationMaster);
    }

    /**
     * {@code DELETE  /designation-masters/:id} : delete the "id" designationMaster.
     *
     * @param id the id of the designationMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/designation-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteDesignationMaster(@PathVariable Long id) {
        log.debug("REST request to delete DesignationMaster : {}", id);

        Optional<DesignationMaster> designationMaster = designationMasterService.findOne(id);
        if (!designationMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        DesignationMaster result = designationMaster.get();

        designationMasterService.delete(id);

        try {
            notificationDataUtility.notificationData(
                "Designation Master Deleted",
                "Designation Master: " + result.getDesignationName() + " Deleted",
                false,
                result.getCreatedDate(),
                "DesignationMasterDeleted" //type
            );
        } catch (Exception e) {}

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
