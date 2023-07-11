package com.cbs.middleware.service;

import com.cbs.middleware.domain.PacsMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PacsMaster}.
 */
public interface PacsMasterService {
    /**
     * Save a pacsMaster.
     *
     * @param pacsMaster the entity to save.
     * @return the persisted entity.
     */
    PacsMaster save(PacsMaster pacsMaster);

    /**
     * Updates a pacsMaster.
     *
     * @param pacsMaster the entity to update.
     * @return the persisted entity.
     */
    PacsMaster update(PacsMaster pacsMaster);

    /**
     * Partially updates a pacsMaster.
     *
     * @param pacsMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PacsMaster> partialUpdate(PacsMaster pacsMaster);

    /**
     * Get all the pacsMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PacsMaster> findAll(Pageable pageable);

    /**
     * Get the "id" pacsMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PacsMaster> findOne(Long id);

    /**
     * Delete the "id" pacsMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
