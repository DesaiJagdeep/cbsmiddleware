package com.cbs.middleware.service;

import com.cbs.middleware.domain.CropRateMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CropRateMaster}.
 */
public interface CropRateMasterService {
    /**
     * Save a cropRateMaster.
     *
     * @param cropRateMaster the entity to save.
     * @return the persisted entity.
     */
    CropRateMaster save(CropRateMaster cropRateMaster);

    /**
     * Updates a cropRateMaster.
     *
     * @param cropRateMaster the entity to update.
     * @return the persisted entity.
     */
    CropRateMaster update(CropRateMaster cropRateMaster);

    /**
     * Partially updates a cropRateMaster.
     *
     * @param cropRateMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CropRateMaster> partialUpdate(CropRateMaster cropRateMaster);

    /**
     * Get all the cropRateMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CropRateMaster> findAll(Pageable pageable);

    /**
     * Get the "id" cropRateMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CropRateMaster> findOne(Long id);

    /**
     * Delete the "id" cropRateMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
