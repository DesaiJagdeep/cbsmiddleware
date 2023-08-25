package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.domain.StateMaster;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.StateMasterRepository;
import com.cbs.middleware.service.StateMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.StateMaster}.
 */
@RestController
@RequestMapping("/api")
public class StateMasterResource {

    private final Logger log = LoggerFactory.getLogger(StateMasterResource.class);

    private static final String ENTITY_NAME = "stateMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateMasterService stateMasterService;

    private final StateMasterRepository stateMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public StateMasterResource(StateMasterService stateMasterService, StateMasterRepository stateMasterRepository) {
        this.stateMasterService = stateMasterService;
        this.stateMasterRepository = stateMasterRepository;
    }

    /**
     * {@code POST  /state-masters} : Create a new stateMaster.
     *
     * @param stateMaster the stateMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateMaster, or with status {@code 400 (Bad Request)} if the stateMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/state-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<StateMaster> createStateMaster(@RequestBody StateMaster stateMaster) throws URISyntaxException {
        log.debug("REST request to save StateMaster : {}", stateMaster);
        if (stateMaster.getId() != null) {
            throw new BadRequestAlertException("A new stateMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateMaster result = stateMasterService.save(stateMaster);

        if (result != null) {
            Notification notification = new Notification(
                "State Master Created",
                "State Master: " + result.getStateName() + " Created",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "StateMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .created(new URI("/api/state-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-masters/:id} : Updates an existing stateMaster.
     *
     * @param id the id of the stateMaster to save.
     * @param stateMaster the stateMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateMaster,
     * or with status {@code 400 (Bad Request)} if the stateMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/state-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<StateMaster> updateStateMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StateMaster stateMaster
    ) throws URISyntaxException {
        log.debug("REST request to update StateMaster : {}, {}", id, stateMaster);
        if (stateMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stateMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stateMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StateMaster result = stateMasterService.update(stateMaster);

        if (result != null) {
            Notification notification = new Notification(
                "State Master Updated",
                "State Master: " + result.getStateName() + " Updated",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "StateMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /state-masters/:id} : Partial updates given fields of an existing stateMaster, field will ignore if it is null
     *
     * @param id the id of the stateMaster to save.
     * @param stateMaster the stateMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateMaster,
     * or with status {@code 400 (Bad Request)} if the stateMaster is not valid,
     * or with status {@code 404 (Not Found)} if the stateMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the stateMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/state-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<StateMaster> partialUpdateStateMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StateMaster stateMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update StateMaster partially : {}, {}", id, stateMaster);
        if (stateMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stateMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stateMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StateMaster> result = stateMasterService.partialUpdate(stateMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /state-masters} : get all the stateMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stateMasters in body.
     */
    @GetMapping("/state-masters")
    public ResponseEntity<List<StateMaster>> getAllStateMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of StateMasters");
        Page<StateMaster> page = stateMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /state-masters/:id} : get the "id" stateMaster.
     *
     * @param id the id of the stateMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state-masters/{id}")
    public ResponseEntity<StateMaster> getStateMaster(@PathVariable Long id) {
        log.debug("REST request to get StateMaster : {}", id);
        Optional<StateMaster> stateMaster = stateMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateMaster);
    }

    /**
     * {@code DELETE  /state-masters/:id} : delete the "id" stateMaster.
     *
     * @param id the id of the stateMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/state-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteStateMaster(@PathVariable Long id) {
        log.debug("REST request to delete StateMaster : {}", id);

        Optional<StateMaster> stateMaster = stateMasterService.findOne(id);
        if (!stateMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        StateMaster result = stateMaster.get();

        stateMasterService.delete(id);

        Notification notification = new Notification(
            "State Master Deleted",
            "State Master: " + result.getStateName() + " Deleted",
            false,
            result.getCreatedDate(),
            "", //recipient
            result.getCreatedBy(), //sender
            "StateMasterDeleted" //type
        );
        notificationRepository.save(notification);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
