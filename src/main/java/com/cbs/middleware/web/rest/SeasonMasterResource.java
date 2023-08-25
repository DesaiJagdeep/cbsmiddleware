package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.SeasonMasterRepository;
import com.cbs.middleware.service.SeasonMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.SeasonMaster}.
 */
@RestController
@RequestMapping("/api")
public class SeasonMasterResource {

    private final Logger log = LoggerFactory.getLogger(SeasonMasterResource.class);

    private static final String ENTITY_NAME = "seasonMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeasonMasterService seasonMasterService;

    private final SeasonMasterRepository seasonMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public SeasonMasterResource(SeasonMasterService seasonMasterService, SeasonMasterRepository seasonMasterRepository) {
        this.seasonMasterService = seasonMasterService;
        this.seasonMasterRepository = seasonMasterRepository;
    }

    /**
     * {@code POST  /season-masters} : Create a new seasonMaster.
     *
     * @param seasonMaster the seasonMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seasonMaster, or with status {@code 400 (Bad Request)} if the seasonMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/season-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<SeasonMaster> createSeasonMaster(@RequestBody SeasonMaster seasonMaster) throws URISyntaxException {
        log.debug("REST request to save SeasonMaster : {}", seasonMaster);
        if (seasonMaster.getId() != null) {
            throw new BadRequestAlertException("A new seasonMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeasonMaster result = seasonMasterService.save(seasonMaster);

        if (result != null) {
            Notification notification = new Notification(
                "Season Master Created",
                "Season Master: " + result.getSeasonName() + " Created",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "SeasonMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .created(new URI("/api/season-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /season-masters/:id} : Updates an existing seasonMaster.
     *
     * @param id the id of the seasonMaster to save.
     * @param seasonMaster the seasonMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seasonMaster,
     * or with status {@code 400 (Bad Request)} if the seasonMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seasonMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/season-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<SeasonMaster> updateSeasonMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SeasonMaster seasonMaster
    ) throws URISyntaxException {
        log.debug("REST request to update SeasonMaster : {}, {}", id, seasonMaster);
        if (seasonMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seasonMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seasonMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SeasonMaster result = seasonMasterService.update(seasonMaster);

        if (result != null) {
            Notification notification = new Notification(
                "Season Master Updated",
                "Season Master: " + result.getSeasonName() + " Updated",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "SeasonMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seasonMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /season-masters/:id} : Partial updates given fields of an existing seasonMaster, field will ignore if it is null
     *
     * @param id the id of the seasonMaster to save.
     * @param seasonMaster the seasonMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seasonMaster,
     * or with status {@code 400 (Bad Request)} if the seasonMaster is not valid,
     * or with status {@code 404 (Not Found)} if the seasonMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the seasonMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/season-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<SeasonMaster> partialUpdateSeasonMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SeasonMaster seasonMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update SeasonMaster partially : {}, {}", id, seasonMaster);
        if (seasonMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seasonMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seasonMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SeasonMaster> result = seasonMasterService.partialUpdate(seasonMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seasonMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /season-masters} : get all the seasonMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seasonMasters in body.
     */
    @GetMapping("/season-masters")
    public ResponseEntity<List<SeasonMaster>> getAllSeasonMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SeasonMasters");
        Page<SeasonMaster> page = seasonMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/seasonMasters/{seasonName}")
    public ResponseEntity<List<SeasonMaster>> getAllSeasonMastersByseason(@PathVariable String seasonName) {
        List<SeasonMaster> findBySeasonNameIsContaining = seasonMasterRepository.findBySeasonNameIsContaining(seasonName);
        return ResponseEntity.ok().body(findBySeasonNameIsContaining);
    }

    /**
     * {@code GET  /season-masters/:id} : get the "id" seasonMaster.
     *
     * @param id the id of the seasonMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seasonMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/season-masters/{id}")
    public ResponseEntity<SeasonMaster> getSeasonMaster(@PathVariable Long id) {
        log.debug("REST request to get SeasonMaster : {}", id);
        Optional<SeasonMaster> seasonMaster = seasonMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seasonMaster);
    }

    /**
     * {@code DELETE  /season-masters/:id} : delete the "id" seasonMaster.
     *
     * @param id the id of the seasonMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/season-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteSeasonMaster(@PathVariable Long id) {
        log.debug("REST request to delete SeasonMaster : {}", id);

        Optional<SeasonMaster> seasonMaster = seasonMasterService.findOne(id);
        if (!seasonMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        SeasonMaster result = seasonMaster.get();

        seasonMasterService.delete(id);

        Notification notification = new Notification(
            "Season Master Deleted",
            "Season Master: " + result.getSeasonName() + " Deleted",
            false,
            result.getCreatedDate(),
            "", //recipient
            result.getCreatedBy(), //sender
            "SeasonMasterDeleted" //type
        );
        notificationRepository.save(notification);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
