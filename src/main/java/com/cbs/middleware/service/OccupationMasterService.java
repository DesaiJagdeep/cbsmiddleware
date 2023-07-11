package com.cbs.middleware.service;

import com.cbs.middleware.domain.OccupationMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OccupationMaster}.
 */
public interface OccupationMasterService {
    /**
     * Save a occupationMaster.
     *
     * @param occupationMaster the entity to save.
     * @return the persisted entity.
     */
    OccupationMaster save(OccupationMaster occupationMaster);

    /**
     * Updates a occupationMaster.
     *
     * @param occupationMaster the entity to update.
     * @return the persisted entity.
     */
    OccupationMaster update(OccupationMaster occupationMaster);

    /**
     * Partially updates a occupationMaster.
     *
     * @param occupationMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OccupationMaster> partialUpdate(OccupationMaster occupationMaster);

    /**
     * Get all the occupationMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OccupationMaster> findAll(Pageable pageable);

    /**
     * Get the "id" occupationMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OccupationMaster> findOne(Long id);

    /**
     * Delete the "id" occupationMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
