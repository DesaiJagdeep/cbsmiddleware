package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.FarmerCategoryMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.repository.FarmerCategoryMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.FarmerCategoryMasterService;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing
 * {@link com.cbs.middleware.domain.FarmerCategoryMaster}.
 */
@RestController
@RequestMapping("/api")
public class FarmerCategoryMasterResource {

    private final Logger log = LoggerFactory.getLogger(FarmerCategoryMasterResource.class);

    private static final String ENTITY_NAME = "farmerCategoryMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FarmerCategoryMasterService farmerCategoryMasterService;

    private final FarmerCategoryMasterRepository farmerCategoryMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public FarmerCategoryMasterResource(
        FarmerCategoryMasterService farmerCategoryMasterService,
        FarmerCategoryMasterRepository farmerCategoryMasterRepository
    ) {
        this.farmerCategoryMasterService = farmerCategoryMasterService;
        this.farmerCategoryMasterRepository = farmerCategoryMasterRepository;
    }

    /**
     * {@code POST  /farmer-category-masters} : Create a new farmerCategoryMaster.
     *
     * @param farmerCategoryMaster the farmerCategoryMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new farmerCategoryMaster, or with status
     *         {@code 400 (Bad Request)} if the farmerCategoryMaster has already an
     *         ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/farmer-category-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<FarmerCategoryMaster> createFarmerCategoryMaster(@RequestBody FarmerCategoryMaster farmerCategoryMaster)
        throws URISyntaxException {
        log.debug("REST request to save FarmerCategoryMaster : {}", farmerCategoryMaster);
        if (farmerCategoryMaster.getId() != null) {
            throw new BadRequestAlertException("A new farmerCategoryMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FarmerCategoryMaster result = farmerCategoryMasterService.save(farmerCategoryMaster);

        if (result != null) {
            Notification notification = new Notification(
                "Farmer Category Master Created",
                "Farmer Category Master: " + result.getFarmerCategory() + " Created",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "FarmerCategoryMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .created(new URI("/api/farmer-category-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /farmer-category-masters/:id} : Updates an existing
     * farmerCategoryMaster.
     *
     * @param id                   the id of the farmerCategoryMaster to save.
     * @param farmerCategoryMaster the farmerCategoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated farmerCategoryMaster, or with status
     *         {@code 400 (Bad Request)} if the farmerCategoryMaster is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         farmerCategoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/farmer-category-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<FarmerCategoryMaster> updateFarmerCategoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FarmerCategoryMaster farmerCategoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to update FarmerCategoryMaster : {}, {}", id, farmerCategoryMaster);
        if (farmerCategoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, farmerCategoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!farmerCategoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FarmerCategoryMaster result = farmerCategoryMasterService.update(farmerCategoryMaster);
        if (result != null) {
            Notification notification = new Notification(
                "Farmer Category Master Updated",
                "Farmer Category Master: " + result.getFarmerCategory() + " Updated",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "FarmerCategoryMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farmerCategoryMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /farmer-category-masters/:id} : Partial updates given fields of
     * an existing farmerCategoryMaster, field will ignore if it is null
     *
     * @param id                   the id of the farmerCategoryMaster to save.
     * @param farmerCategoryMaster the farmerCategoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated farmerCategoryMaster, or with status
     *         {@code 400 (Bad Request)} if the farmerCategoryMaster is not valid,
     *         or with status {@code 404 (Not Found)} if the farmerCategoryMaster is
     *         not found, or with status {@code 500 (Internal Server Error)} if the
     *         farmerCategoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/farmer-category-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<FarmerCategoryMaster> partialUpdateFarmerCategoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FarmerCategoryMaster farmerCategoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update FarmerCategoryMaster partially : {}, {}", id, farmerCategoryMaster);
        if (farmerCategoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, farmerCategoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!farmerCategoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FarmerCategoryMaster> result = farmerCategoryMasterService.partialUpdate(farmerCategoryMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farmerCategoryMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /farmer-category-masters} : get all the farmerCategoryMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of farmerCategoryMasters in body.
     */
    @GetMapping("/farmer-category-masters")
    public ResponseEntity<List<FarmerCategoryMaster>> getAllFarmerCategoryMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FarmerCategoryMasters");
        Page<FarmerCategoryMaster> page = farmerCategoryMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /farmer-category-masters/:id} : get the "id"
     * farmerCategoryMaster.
     *
     * @param id the id of the farmerCategoryMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the farmerCategoryMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/farmer-category-masters/{id}")
    public ResponseEntity<FarmerCategoryMaster> getFarmerCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to get FarmerCategoryMaster : {}", id);
        Optional<FarmerCategoryMaster> farmerCategoryMaster = farmerCategoryMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(farmerCategoryMaster);
    }

    /**
     * {@code DELETE  /farmer-category-masters/:id} : delete the "id"
     * farmerCategoryMaster.
     *
     * @param id the id of the farmerCategoryMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/farmer-category-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteFarmerCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to delete FarmerCategoryMaster : {}", id);
        Optional<FarmerCategoryMaster> farmerCategoryMaster = farmerCategoryMasterService.findOne(id);
        if (!farmerCategoryMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        FarmerCategoryMaster result = farmerCategoryMaster.get();

        farmerCategoryMasterService.delete(id);

        Notification notification = new Notification(
            "Farmer Category Master Deleted",
            "Farmer Category Master: " + result.getFarmerCategory() + " Deleted",
            false,
            result.getCreatedDate(),
            "", //recipient
            result.getCreatedBy(), //sender
            "FarmerCategoryMasterDeleted" //type
        );
        notificationRepository.save(notification);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
