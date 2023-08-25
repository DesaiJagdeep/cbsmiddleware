package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.ActivityType;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.repository.ActivityTypeRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.ActivityTypeQueryService;
import com.cbs.middleware.service.ActivityTypeService;
import com.cbs.middleware.service.criteria.ActivityTypeCriteria;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.ActivityType}.
 */
@RestController
@RequestMapping("/api")
public class ActivityTypeResource {

    private final Logger log = LoggerFactory.getLogger(ActivityTypeResource.class);

    private static final String ENTITY_NAME = "activityType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityTypeService activityTypeService;

    private final ActivityTypeRepository activityTypeRepository;

    private final ActivityTypeQueryService activityTypeQueryService;

    @Autowired
    NotificationRepository notificationRepository;

    public ActivityTypeResource(
        ActivityTypeService activityTypeService,
        ActivityTypeRepository activityTypeRepository,
        ActivityTypeQueryService activityTypeQueryService
    ) {
        this.activityTypeService = activityTypeService;
        this.activityTypeRepository = activityTypeRepository;
        this.activityTypeQueryService = activityTypeQueryService;
    }

    /**
     * {@code POST  /activity-types} : Create a new activityType.
     *
     * @param activityType the activityType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityType, or with status {@code 400 (Bad Request)} if the activityType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-types")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<ActivityType> createActivityType(@RequestBody ActivityType activityType) throws URISyntaxException {
        log.debug("REST request to save ActivityType : {}", activityType);
        if (activityType.getId() != null) {
            throw new BadRequestAlertException("A new activityType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityType result = activityTypeService.save(activityType);
        if (result != null) {
            Notification notification = new Notification(
                "Activity Type Master Created",
                "Activity Type Master: " + result.getActivityType() + " Created",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "ActivityTypeMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .created(new URI("/api/activity-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-types/:id} : Updates an existing activityType.
     *
     * @param id the id of the activityType to save.
     * @param activityType the activityType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityType,
     * or with status {@code 400 (Bad Request)} if the activityType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-types/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<ActivityType> updateActivityType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ActivityType activityType
    ) throws URISyntaxException {
        log.debug("REST request to update ActivityType : {}, {}", id, activityType);
        if (activityType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActivityType result = activityTypeService.update(activityType);
        if (result != null) {
            Notification notification = new Notification(
                "Activity Type Master Created",
                "Activity Type Master: " + result.getActivityType() + " updated",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "ActivityTypeMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /activity-types/:id} : Partial updates given fields of an existing activityType, field will ignore if it is null
     *
     * @param id the id of the activityType to save.
     * @param activityType the activityType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityType,
     * or with status {@code 400 (Bad Request)} if the activityType is not valid,
     * or with status {@code 404 (Not Found)} if the activityType is not found,
     * or with status {@code 500 (Internal Server Error)} if the activityType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/activity-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<ActivityType> partialUpdateActivityType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ActivityType activityType
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActivityType partially : {}, {}", id, activityType);
        if (activityType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActivityType> result = activityTypeService.partialUpdate(activityType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityType.getId().toString())
        );
    }

    /**
     * {@code GET  /activity-types} : get all the activityTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityTypes in body.
     */
    @GetMapping("/activity-types")
    public ResponseEntity<List<ActivityType>> getAllActivityTypes(
        ActivityTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ActivityTypes by criteria: {}", criteria);
        Page<ActivityType> page = activityTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activity-types/count} : count all the activityTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/activity-types/count")
    public ResponseEntity<Long> countActivityTypes(ActivityTypeCriteria criteria) {
        log.debug("REST request to count ActivityTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(activityTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /activity-types/:id} : get the "id" activityType.
     *
     * @param id the id of the activityType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-types/{id}")
    public ResponseEntity<ActivityType> getActivityType(@PathVariable Long id) {
        log.debug("REST request to get ActivityType : {}", id);
        Optional<ActivityType> activityType = activityTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityType);
    }

    /**
     * {@code DELETE  /activity-types/:id} : delete the "id" activityType.
     *
     * @param id the id of the activityType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-types/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteActivityType(@PathVariable Long id) {
        log.debug("REST request to delete ActivityType : {}", id);
        Optional<ActivityType> activityType = activityTypeService.findOne(id);
        if (!activityType.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ActivityType result = activityType.get();
        activityTypeService.delete(id);

        if (result != null) {
            Notification notification = new Notification(
                "Activity Type Master Created",
                "Activity Type Master: " + result.getActivityType() + " deleted",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "ActivityTypeMasterDeleted" //type
            );
            notificationRepository.save(notification);
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
