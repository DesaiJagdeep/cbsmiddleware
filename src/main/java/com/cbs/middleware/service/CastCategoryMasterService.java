package com.cbs.middleware.service;

import com.cbs.middleware.domain.CastCategoryMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CastCategoryMaster}.
 */
public interface CastCategoryMasterService {
    /**
     * Save a castCategoryMaster.
     *
     * @param castCategoryMaster the entity to save.
     * @return the persisted entity.
     */
    CastCategoryMaster save(CastCategoryMaster castCategoryMaster);

    /**
     * Updates a castCategoryMaster.
     *
     * @param castCategoryMaster the entity to update.
     * @return the persisted entity.
     */
    CastCategoryMaster update(CastCategoryMaster castCategoryMaster);

    /**
     * Partially updates a castCategoryMaster.
     *
     * @param castCategoryMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CastCategoryMaster> partialUpdate(CastCategoryMaster castCategoryMaster);

    /**
     * Get all the castCategoryMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CastCategoryMaster> findAll(Pageable pageable);

    /**
     * Get the "id" castCategoryMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CastCategoryMaster> findOne(Long id);

    /**
     * Delete the "id" castCategoryMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
