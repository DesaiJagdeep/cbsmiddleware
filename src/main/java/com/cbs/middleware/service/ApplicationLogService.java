package com.cbs.middleware.service;

import com.cbs.middleware.domain.ApplicationLog;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ApplicationLog}.
 */
public interface ApplicationLogService {
    /**
     * Save a applicationLog.
     *
     * @param applicationLog the entity to save.
     * @return the persisted entity.
     */
    ApplicationLog save(ApplicationLog applicationLog);

    /**
     * Updates a applicationLog.
     *
     * @param applicationLog the entity to update.
     * @return the persisted entity.
     */
    ApplicationLog update(ApplicationLog applicationLog);

    /**
     * Partially updates a applicationLog.
     *
     * @param applicationLog the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationLog> partialUpdate(ApplicationLog applicationLog);

    /**
     * Get all the applicationLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationLog> findAll(Pageable pageable);

    /**
     * Get all the applicationLogs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationLog> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" applicationLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationLog> findOne(Long id);

    /**
     * Delete the "id" applicationLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ApplicationLog> findRejectedApplications();

}
