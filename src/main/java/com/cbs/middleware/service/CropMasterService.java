package com.cbs.middleware.service;

import com.cbs.middleware.domain.CropMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CropMaster}.
 */
public interface CropMasterService {
    /**
     * Save a cropMaster.
     *
     * @param cropMaster the entity to save.
     * @return the persisted entity.
     */
    CropMaster save(CropMaster cropMaster);

    /**
     * Updates a cropMaster.
     *
     * @param cropMaster the entity to update.
     * @return the persisted entity.
     */
    CropMaster update(CropMaster cropMaster);

    /**
     * Partially updates a cropMaster.
     *
     * @param cropMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CropMaster> partialUpdate(CropMaster cropMaster);

    /**
     * Get all the cropMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CropMaster> findAll(Pageable pageable);

    /**
     * Get the "id" cropMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CropMaster> findOne(Long id);

    /**
     * Delete the "id" cropMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
