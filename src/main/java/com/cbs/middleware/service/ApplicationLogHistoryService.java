package com.cbs.middleware.service;

import com.cbs.middleware.domain.ApplicationLogHistory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ApplicationLogHistory}.
 */
public interface ApplicationLogHistoryService {
    /**
     * Save a applicationLogHistory.
     *
     * @param applicationLogHistory the entity to save.
     * @return the persisted entity.
     */
    ApplicationLogHistory save(ApplicationLogHistory applicationLogHistory);

    /**
     * Updates a applicationLogHistory.
     *
     * @param applicationLogHistory the entity to update.
     * @return the persisted entity.
     */
    ApplicationLogHistory update(ApplicationLogHistory applicationLogHistory);

    /**
     * Partially updates a applicationLogHistory.
     *
     * @param applicationLogHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationLogHistory> partialUpdate(ApplicationLogHistory applicationLogHistory);

    /**
     * Get all the applicationLogHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationLogHistory> findAll(Pageable pageable);

    /**
     * Get all the applicationLogHistories with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationLogHistory> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" applicationLogHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationLogHistory> findOne(Long id);

    /**
     * Delete the "id" applicationLogHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
