package com.cbs.middleware.service;

import com.cbs.middleware.domain.CropCategoryMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CropCategoryMaster}.
 */
public interface CropCategoryMasterService {
    /**
     * Save a cropCategoryMaster.
     *
     * @param cropCategoryMaster the entity to save.
     * @return the persisted entity.
     */
    CropCategoryMaster save(CropCategoryMaster cropCategoryMaster);

    /**
     * Updates a cropCategoryMaster.
     *
     * @param cropCategoryMaster the entity to update.
     * @return the persisted entity.
     */
    CropCategoryMaster update(CropCategoryMaster cropCategoryMaster);

    /**
     * Partially updates a cropCategoryMaster.
     *
     * @param cropCategoryMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CropCategoryMaster> partialUpdate(CropCategoryMaster cropCategoryMaster);

    /**
     * Get all the cropCategoryMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CropCategoryMaster> findAll(Pageable pageable);

    /**
     * Get the "id" cropCategoryMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CropCategoryMaster> findOne(Long id);

    /**
     * Delete the "id" cropCategoryMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
