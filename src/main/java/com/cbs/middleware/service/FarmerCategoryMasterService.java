package com.cbs.middleware.service;

import com.cbs.middleware.domain.FarmerCategoryMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FarmerCategoryMaster}.
 */
public interface FarmerCategoryMasterService {
    /**
     * Save a farmerCategoryMaster.
     *
     * @param farmerCategoryMaster the entity to save.
     * @return the persisted entity.
     */
    FarmerCategoryMaster save(FarmerCategoryMaster farmerCategoryMaster);

    /**
     * Updates a farmerCategoryMaster.
     *
     * @param farmerCategoryMaster the entity to update.
     * @return the persisted entity.
     */
    FarmerCategoryMaster update(FarmerCategoryMaster farmerCategoryMaster);

    /**
     * Partially updates a farmerCategoryMaster.
     *
     * @param farmerCategoryMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FarmerCategoryMaster> partialUpdate(FarmerCategoryMaster farmerCategoryMaster);

    /**
     * Get all the farmerCategoryMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FarmerCategoryMaster> findAll(Pageable pageable);

    /**
     * Get the "id" farmerCategoryMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FarmerCategoryMaster> findOne(Long id);

    /**
     * Delete the "id" farmerCategoryMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
