package com.cbs.middleware.service;

import com.cbs.middleware.domain.CategoryMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CategoryMaster}.
 */
public interface CategoryMasterService {
    /**
     * Save a categoryMaster.
     *
     * @param categoryMaster the entity to save.
     * @return the persisted entity.
     */
    CategoryMaster save(CategoryMaster categoryMaster);

    /**
     * Updates a categoryMaster.
     *
     * @param categoryMaster the entity to update.
     * @return the persisted entity.
     */
    CategoryMaster update(CategoryMaster categoryMaster);

    /**
     * Partially updates a categoryMaster.
     *
     * @param categoryMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoryMaster> partialUpdate(CategoryMaster categoryMaster);

    /**
     * Get all the categoryMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryMaster> findAll(Pageable pageable);

    /**
     * Get the "id" categoryMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryMaster> findOne(Long id);

    /**
     * Delete the "id" categoryMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
