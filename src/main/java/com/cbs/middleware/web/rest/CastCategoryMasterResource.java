package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.CastCategoryMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.repository.CastCategoryMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.CastCategoryMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.CastCategoryMaster}.
 */
@RestController
@RequestMapping("/api")
public class CastCategoryMasterResource {

    private final Logger log = LoggerFactory.getLogger(CastCategoryMasterResource.class);

    private static final String ENTITY_NAME = "castCategoryMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CastCategoryMasterService castCategoryMasterService;

    private final CastCategoryMasterRepository castCategoryMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    public CastCategoryMasterResource(
        CastCategoryMasterService castCategoryMasterService,
        CastCategoryMasterRepository castCategoryMasterRepository
    ) {
        this.castCategoryMasterService = castCategoryMasterService;
        this.castCategoryMasterRepository = castCategoryMasterRepository;
    }

    /**
     * {@code POST  /cast-category-masters} : Create a new castCategoryMaster.
     *
     * @param castCategoryMaster the castCategoryMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new castCategoryMaster, or with status {@code 400 (Bad Request)} if the castCategoryMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cast-category-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CastCategoryMaster> createCastCategoryMaster(@RequestBody CastCategoryMaster castCategoryMaster)
        throws URISyntaxException {
        log.debug("REST request to save CastCategoryMaster : {}", castCategoryMaster);
        if (castCategoryMaster.getId() != null) {
            throw new BadRequestAlertException("A new castCategoryMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CastCategoryMaster result = castCategoryMasterService.save(castCategoryMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Cast Category Master Created",
                    "Cast Category Master: " + result.getCastCategoryName() + " Created",
                    false,
                    result.getCreatedDate(),
                    "CastCategoryMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .created(new URI("/api/cast-category-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cast-category-masters/:id} : Updates an existing castCategoryMaster.
     *
     * @param id the id of the castCategoryMaster to save.
     * @param castCategoryMaster the castCategoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated castCategoryMaster,
     * or with status {@code 400 (Bad Request)} if the castCategoryMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the castCategoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cast-category-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CastCategoryMaster> updateCastCategoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CastCategoryMaster castCategoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to update CastCategoryMaster : {}, {}", id, castCategoryMaster);
        if (castCategoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, castCategoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!castCategoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CastCategoryMaster result = castCategoryMasterService.update(castCategoryMaster);
        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Cast Category Master Updated",
                    "Cast Category Master: " + result.getCastCategoryName() + " Updated",
                    false,
                    result.getCreatedDate(),
                    "CastCategoryMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, castCategoryMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cast-category-masters/:id} : Partial updates given fields of an existing castCategoryMaster, field will ignore if it is null
     *
     * @param id the id of the castCategoryMaster to save.
     * @param castCategoryMaster the castCategoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated castCategoryMaster,
     * or with status {@code 400 (Bad Request)} if the castCategoryMaster is not valid,
     * or with status {@code 404 (Not Found)} if the castCategoryMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the castCategoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cast-category-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CastCategoryMaster> partialUpdateCastCategoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CastCategoryMaster castCategoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update CastCategoryMaster partially : {}, {}", id, castCategoryMaster);
        if (castCategoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, castCategoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!castCategoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CastCategoryMaster> result = castCategoryMasterService.partialUpdate(castCategoryMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, castCategoryMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /cast-category-masters} : get all the castCategoryMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of castCategoryMasters in body.
     */
    @GetMapping("/cast-category-masters")
    public ResponseEntity<List<CastCategoryMaster>> getAllCastCategoryMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CastCategoryMasters");
        Page<CastCategoryMaster> page = castCategoryMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cast-category-masters/:id} : get the "id" castCategoryMaster.
     *
     * @param id the id of the castCategoryMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the castCategoryMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cast-category-masters/{id}")
    public ResponseEntity<CastCategoryMaster> getCastCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to get CastCategoryMaster : {}", id);
        Optional<CastCategoryMaster> castCategoryMaster = castCategoryMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(castCategoryMaster);
    }

    /**
     * {@code DELETE  /cast-category-masters/:id} : delete the "id" castCategoryMaster.
     *
     * @param id the id of the castCategoryMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cast-category-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteCastCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to delete CastCategoryMaster : {}", id);
        Optional<CastCategoryMaster> castCategoryMaster = castCategoryMasterService.findOne(id);
        if (!castCategoryMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CastCategoryMaster result = castCategoryMaster.get();

        castCategoryMasterService.delete(id);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Cast Category Master Deleted",
                    "Cast Category Master : " + result.getCastCategoryName() + "Deleted",
                    false,
                    result.getCreatedDate(),
                    "CastCategoryMasterDeleted" //type
                );
            } catch (Exception e) {}
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
