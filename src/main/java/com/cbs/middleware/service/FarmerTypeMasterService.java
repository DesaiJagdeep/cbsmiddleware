package com.cbs.middleware.service;

import com.cbs.middleware.domain.FarmerTypeMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FarmerTypeMaster}.
 */
public interface FarmerTypeMasterService {
    /**
     * Save a farmerTypeMaster.
     *
     * @param farmerTypeMaster the entity to save.
     * @return the persisted entity.
     */
    FarmerTypeMaster save(FarmerTypeMaster farmerTypeMaster);

    /**
     * Updates a farmerTypeMaster.
     *
     * @param farmerTypeMaster the entity to update.
     * @return the persisted entity.
     */
    FarmerTypeMaster update(FarmerTypeMaster farmerTypeMaster);

    /**
     * Partially updates a farmerTypeMaster.
     *
     * @param farmerTypeMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FarmerTypeMaster> partialUpdate(FarmerTypeMaster farmerTypeMaster);

    /**
     * Get all the farmerTypeMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FarmerTypeMaster> findAll(Pageable pageable);

    /**
     * Get the "id" farmerTypeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FarmerTypeMaster> findOne(Long id);

    /**
     * Delete the "id" farmerTypeMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
