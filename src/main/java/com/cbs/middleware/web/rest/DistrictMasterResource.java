package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.DistrictMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.repository.DistrictMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.DistrictMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.DistrictMaster}.
 */
@RestController
@RequestMapping("/api")
public class DistrictMasterResource {

    private final Logger log = LoggerFactory.getLogger(DistrictMasterResource.class);

    private static final String ENTITY_NAME = "districtMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictMasterService districtMasterService;

    private final DistrictMasterRepository districtMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public DistrictMasterResource(DistrictMasterService districtMasterService, DistrictMasterRepository districtMasterRepository) {
        this.districtMasterService = districtMasterService;
        this.districtMasterRepository = districtMasterRepository;
    }

    /**
     * {@code POST  /district-masters} : Create a new districtMaster.
     *
     * @param districtMaster the districtMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtMaster, or with status {@code 400 (Bad Request)} if the districtMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/district-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<DistrictMaster> createDistrictMaster(@RequestBody DistrictMaster districtMaster) throws URISyntaxException {
        log.debug("REST request to save DistrictMaster : {}", districtMaster);
        if (districtMaster.getId() != null) {
            throw new BadRequestAlertException("A new districtMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistrictMaster result = districtMasterService.save(districtMaster);

        if (result != null) {
            Notification notification = new Notification(
                "District Master Created",
                "District Master: " + result.getDistrictName() + " Created",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "DistrictMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .created(new URI("/api/district-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /district-masters/:id} : Updates an existing districtMaster.
     *
     * @param id the id of the districtMaster to save.
     * @param districtMaster the districtMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtMaster,
     * or with status {@code 400 (Bad Request)} if the districtMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/district-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<DistrictMaster> updateDistrictMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistrictMaster districtMaster
    ) throws URISyntaxException {
        log.debug("REST request to update DistrictMaster : {}, {}", id, districtMaster);
        if (districtMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, districtMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DistrictMaster result = districtMasterService.update(districtMaster);
        if (result != null) {
            Notification notification = new Notification(
                "District Master Updated",
                "District Master: " + result.getDistrictName() + " Updated",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "DistrictMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districtMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /district-masters/:id} : Partial updates given fields of an existing districtMaster, field will ignore if it is null
     *
     * @param id the id of the districtMaster to save.
     * @param districtMaster the districtMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtMaster,
     * or with status {@code 400 (Bad Request)} if the districtMaster is not valid,
     * or with status {@code 404 (Not Found)} if the districtMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the districtMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/district-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<DistrictMaster> partialUpdateDistrictMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistrictMaster districtMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update DistrictMaster partially : {}, {}", id, districtMaster);
        if (districtMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, districtMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DistrictMaster> result = districtMasterService.partialUpdate(districtMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districtMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /district-masters} : get all the districtMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districtMasters in body.
     */
    @GetMapping("/district-masters")
    public ResponseEntity<List<DistrictMaster>> getAllDistrictMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DistrictMasters");
        Page<DistrictMaster> page = districtMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /district-masters/:id} : get the "id" districtMaster.
     *
     * @param id the id of the districtMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districtMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/district-masters/{id}")
    public ResponseEntity<DistrictMaster> getDistrictMaster(@PathVariable Long id) {
        log.debug("REST request to get DistrictMaster : {}", id);
        Optional<DistrictMaster> districtMaster = districtMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(districtMaster);
    }

    /**
     * {@code DELETE  /district-masters/:id} : delete the "id" districtMaster.
     *
     * @param id the id of the districtMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/district-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteDistrictMaster(@PathVariable Long id) {
        log.debug("REST request to delete DistrictMaster : {}", id);
        Optional<DistrictMaster> districtMaster = districtMasterService.findOne(id);
        if (!districtMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        DistrictMaster result = districtMaster.get();
        districtMasterService.delete(id);

        Notification notification = new Notification(
            "District Master Deleted",
            "District Master: " + result.getDistrictName() + " Deleted",
            false,
            result.getCreatedDate(),
            "", //recipient
            result.getCreatedBy(), //sender
            "DistrictMasterDeleted" //type
        );
        notificationRepository.save(notification);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
