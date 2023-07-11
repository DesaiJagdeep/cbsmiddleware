package com.cbs.middleware.service;

import com.cbs.middleware.domain.SeasonMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SeasonMaster}.
 */
public interface SeasonMasterService {
    /**
     * Save a seasonMaster.
     *
     * @param seasonMaster the entity to save.
     * @return the persisted entity.
     */
    SeasonMaster save(SeasonMaster seasonMaster);

    /**
     * Updates a seasonMaster.
     *
     * @param seasonMaster the entity to update.
     * @return the persisted entity.
     */
    SeasonMaster update(SeasonMaster seasonMaster);

    /**
     * Partially updates a seasonMaster.
     *
     * @param seasonMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SeasonMaster> partialUpdate(SeasonMaster seasonMaster);

    /**
     * Get all the seasonMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SeasonMaster> findAll(Pageable pageable);

    /**
     * Get the "id" seasonMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeasonMaster> findOne(Long id);

    /**
     * Delete the "id" seasonMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
