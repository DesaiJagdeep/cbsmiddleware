package com.cbs.middleware.service;

import com.cbs.middleware.domain.TalukaMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TalukaMaster}.
 */
public interface TalukaMasterService {
    /**
     * Save a talukaMaster.
     *
     * @param talukaMaster the entity to save.
     * @return the persisted entity.
     */
    TalukaMaster save(TalukaMaster talukaMaster);

    /**
     * Updates a talukaMaster.
     *
     * @param talukaMaster the entity to update.
     * @return the persisted entity.
     */
    TalukaMaster update(TalukaMaster talukaMaster);

    /**
     * Partially updates a talukaMaster.
     *
     * @param talukaMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TalukaMaster> partialUpdate(TalukaMaster talukaMaster);

    /**
     * Get all the talukaMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TalukaMaster> findAll(Pageable pageable);

    /**
     * Get the "id" talukaMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TalukaMaster> findOne(Long id);

    /**
     * Delete the "id" talukaMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
