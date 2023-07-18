package com.cbs.middleware.service;

import com.cbs.middleware.domain.ActivityType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ActivityType}.
 */
public interface ActivityTypeService {
    /**
     * Save a activityType.
     *
     * @param activityType the entity to save.
     * @return the persisted entity.
     */
    ActivityType save(ActivityType activityType);

    /**
     * Updates a activityType.
     *
     * @param activityType the entity to update.
     * @return the persisted entity.
     */
    ActivityType update(ActivityType activityType);

    /**
     * Partially updates a activityType.
     *
     * @param activityType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ActivityType> partialUpdate(ActivityType activityType);

    /**
     * Get all the activityTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityType> findAll(Pageable pageable);

    /**
     * Get the "id" activityType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivityType> findOne(Long id);

    /**
     * Delete the "id" activityType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
