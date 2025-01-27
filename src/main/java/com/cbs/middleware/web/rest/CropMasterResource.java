package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.repository.CropMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.CropMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.CropMaster}.
 */
@RestController
@RequestMapping("/api")
public class CropMasterResource {

    private final Logger log = LoggerFactory.getLogger(CropMasterResource.class);

    private static final String ENTITY_NAME = "cropMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CropMasterService cropMasterService;

    private final CropMasterRepository cropMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    public CropMasterResource(CropMasterService cropMasterService, CropMasterRepository cropMasterRepository) {
        this.cropMasterService = cropMasterService;
        this.cropMasterRepository = cropMasterRepository;
    }

    /**
     * {@code POST  /crop-masters} : Create a new cropMaster.
     *
     * @param cropMaster the cropMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cropMaster, or with status {@code 400 (Bad Request)} if the cropMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crop-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CropMaster> createCropMaster(@RequestBody CropMaster cropMaster) throws URISyntaxException {
        log.debug("REST request to save CropMaster : {}", cropMaster);
        if (cropMaster.getId() != null) {
            throw new BadRequestAlertException("A new cropMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CropMaster result = cropMasterService.save(cropMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Crop Master Created",
                    "Crop Master: " + result.getCropName() + " Created",
                    false,
                    result.getCreatedDate(),
                    "CourtCaseRecordFileUploaded" // type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .created(new URI("/api/crop-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/crop-master-list")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<List<CropMaster>> createCropMasters(@RequestBody List<CropMaster> cropMaster) throws URISyntaxException {
        log.debug("REST request to save CropMaster : {}", cropMaster);

        if (cropMaster.isEmpty()) {
            throw new BadRequestAlertException("List is empty", ENTITY_NAME, "emptyList");
        }
        List<CropMaster> result = cropMasterRepository.saveAll(cropMaster);
        if (result.isEmpty()) {
            throw new BadRequestAlertException("Error in save crop master", ENTITY_NAME, "errorInSave");
        }
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /crop-masters/:id} : Updates an existing cropMaster.
     *
     * @param id the id of the cropMaster to save.
     * @param cropMaster the cropMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropMaster,
     * or with status {@code 400 (Bad Request)} if the cropMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cropMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crop-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CropMaster> updateCropMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CropMaster cropMaster
    ) throws URISyntaxException {
        log.debug("REST request to update CropMaster : {}, {}", id, cropMaster);
        if (cropMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CropMaster result = cropMasterService.update(cropMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Crop Master Updated",
                    "Crop Master: " + result.getCropName() + " Updated",
                    false,
                    result.getCreatedDate(),
                    "CropMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /crop-masters/:id} : Partial updates given fields of an existing cropMaster, field will ignore if it is null
     *
     * @param id the id of the cropMaster to save.
     * @param cropMaster the cropMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropMaster,
     * or with status {@code 400 (Bad Request)} if the cropMaster is not valid,
     * or with status {@code 404 (Not Found)} if the cropMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the cropMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crop-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CropMaster> partialUpdateCropMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CropMaster cropMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update CropMaster partially : {}, {}", id, cropMaster);
        if (cropMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CropMaster> result = cropMasterService.partialUpdate(cropMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /crop-masters} : get all the cropMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cropMasters in body.
     */
    @GetMapping("/crop-masters")
    public ResponseEntity<List<CropMaster>> getAllCropMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CropMasters");
        Page<CropMaster> page = cropMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crop-masters/:id} : get the "id" cropMaster.
     *
     * @param id the id of the cropMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cropMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crop-masters/{id}")
    public ResponseEntity<CropMaster> getCropMaster(@PathVariable Long id) {
        log.debug("REST request to get CropMaster : {}", id);
        Optional<CropMaster> cropMaster = cropMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cropMaster);
    }

    /**
     * {@code DELETE  /crop-masters/:id} : delete the "id" cropMaster.
     *
     * @param id the id of the cropMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crop-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteCropMaster(@PathVariable Long id) {
        log.debug("REST request to delete CropMaster : {}", id);
        Optional<CropMaster> cropMaster = cropMasterService.findOne(id);
        if (!cropMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CropMaster result = cropMaster.get();
        cropMasterService.delete(id);

        try {
            notificationDataUtility.notificationData(
                "Crop Master Deleted",
                "Crop Master: " + result.getCropName() + " Deleted",
                false,
                result.getCreatedDate(),
                "CropMasterDeleted" //type
            );
        } catch (Exception e) {}

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
