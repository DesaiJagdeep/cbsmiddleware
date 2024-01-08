package com.cbs.middleware.service;

import com.cbs.middleware.domain.FactoryMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FactoryMaster}.
 */
public interface FactoryMasterService {
    /**
     * Save a factoryMaster.
     *
     * @param factoryMaster the entity to save.
     * @return the persisted entity.
     */
    FactoryMaster save(FactoryMaster factoryMaster);

    /**
     * Updates a factoryMaster.
     *
     * @param factoryMaster the entity to update.
     * @return the persisted entity.
     */
    FactoryMaster update(FactoryMaster factoryMaster);

    /**
     * Partially updates a factoryMaster.
     *
     * @param factoryMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FactoryMaster> partialUpdate(FactoryMaster factoryMaster);

    /**
     * Get all the factoryMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FactoryMaster> findAll(Pageable pageable);

    /**
     * Get the "id" factoryMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FactoryMaster> findOne(Long id);

    /**
     * Delete the "id" factoryMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
