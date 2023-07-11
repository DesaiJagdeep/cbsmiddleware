package com.cbs.middleware.service;

import com.cbs.middleware.domain.StateMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link StateMaster}.
 */
public interface StateMasterService {
    /**
     * Save a stateMaster.
     *
     * @param stateMaster the entity to save.
     * @return the persisted entity.
     */
    StateMaster save(StateMaster stateMaster);

    /**
     * Updates a stateMaster.
     *
     * @param stateMaster the entity to update.
     * @return the persisted entity.
     */
    StateMaster update(StateMaster stateMaster);

    /**
     * Partially updates a stateMaster.
     *
     * @param stateMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StateMaster> partialUpdate(StateMaster stateMaster);

    /**
     * Get all the stateMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StateMaster> findAll(Pageable pageable);

    /**
     * Get the "id" stateMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StateMaster> findOne(Long id);

    /**
     * Delete the "id" stateMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
