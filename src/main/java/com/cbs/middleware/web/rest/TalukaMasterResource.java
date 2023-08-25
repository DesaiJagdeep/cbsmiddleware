package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.domain.TalukaMaster;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.TalukaMasterRepository;
import com.cbs.middleware.service.TalukaMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.TalukaMaster}.
 */
@RestController
@RequestMapping("/api")
public class TalukaMasterResource {

    private final Logger log = LoggerFactory.getLogger(TalukaMasterResource.class);

    private static final String ENTITY_NAME = "talukaMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TalukaMasterService talukaMasterService;

    private final TalukaMasterRepository talukaMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public TalukaMasterResource(TalukaMasterService talukaMasterService, TalukaMasterRepository talukaMasterRepository) {
        this.talukaMasterService = talukaMasterService;
        this.talukaMasterRepository = talukaMasterRepository;
    }

    /**
     * {@code POST  /taluka-masters} : Create a new talukaMaster.
     *
     * @param talukaMaster the talukaMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new talukaMaster, or with status {@code 400 (Bad Request)} if the talukaMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taluka-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<TalukaMaster> createTalukaMaster(@RequestBody TalukaMaster talukaMaster) throws URISyntaxException {
        log.debug("REST request to save TalukaMaster : {}", talukaMaster);
        if (talukaMaster.getId() != null) {
            throw new BadRequestAlertException("A new talukaMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TalukaMaster result = talukaMasterService.save(talukaMaster);

        if (result != null) {
            Notification notification = new Notification(
                "Taluka Master Created",
                "Taluka Master: " + result.getTalukaName() + " Created",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "TalukaMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .created(new URI("/api/taluka-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taluka-masters/:id} : Updates an existing talukaMaster.
     *
     * @param id the id of the talukaMaster to save.
     * @param talukaMaster the talukaMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated talukaMaster,
     * or with status {@code 400 (Bad Request)} if the talukaMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the talukaMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taluka-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<TalukaMaster> updateTalukaMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TalukaMaster talukaMaster
    ) throws URISyntaxException {
        log.debug("REST request to update TalukaMaster : {}, {}", id, talukaMaster);
        if (talukaMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, talukaMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!talukaMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TalukaMaster result = talukaMasterService.update(talukaMaster);

        if (result != null) {
            Notification notification = new Notification(
                "Taluka Master Updated",
                "Taluka Master: " + result.getTalukaName() + " Updated",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "TalukaMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, talukaMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taluka-masters/:id} : Partial updates given fields of an existing talukaMaster, field will ignore if it is null
     *
     * @param id the id of the talukaMaster to save.
     * @param talukaMaster the talukaMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated talukaMaster,
     * or with status {@code 400 (Bad Request)} if the talukaMaster is not valid,
     * or with status {@code 404 (Not Found)} if the talukaMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the talukaMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taluka-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<TalukaMaster> partialUpdateTalukaMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TalukaMaster talukaMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update TalukaMaster partially : {}, {}", id, talukaMaster);
        if (talukaMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, talukaMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!talukaMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TalukaMaster> result = talukaMasterService.partialUpdate(talukaMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, talukaMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /taluka-masters} : get all the talukaMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of talukaMasters in body.
     */
    @GetMapping("/taluka-masters")
    public ResponseEntity<List<TalukaMaster>> getAllTalukaMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TalukaMasters");
        Page<TalukaMaster> page = talukaMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /taluka-masters/:id} : get the "id" talukaMaster.
     *
     * @param id the id of the talukaMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the talukaMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taluka-masters/{id}")
    public ResponseEntity<TalukaMaster> getTalukaMaster(@PathVariable Long id) {
        log.debug("REST request to get TalukaMaster : {}", id);
        Optional<TalukaMaster> talukaMaster = talukaMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(talukaMaster);
    }

    /**
     * {@code DELETE  /taluka-masters/:id} : delete the "id" talukaMaster.
     *
     * @param id the id of the talukaMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taluka-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteTalukaMaster(@PathVariable Long id) {
        log.debug("REST request to delete TalukaMaster : {}", id);

        Optional<TalukaMaster> talukaMaster = talukaMasterService.findOne(id);
        if (!talukaMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TalukaMaster result = talukaMaster.get();
        talukaMasterService.delete(id);

        Notification notification = new Notification(
            "Taluka Master Deleted",
            "Taluka Master: " + result.getTalukaName() + " Deleted",
            false,
            result.getCreatedDate(),
            "", //recipient
            result.getCreatedBy(), //sender
            "TalukaMasterDeleted" //type
        );
        notificationRepository.save(notification);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
