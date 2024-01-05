package com.cbs.middleware.service;

import com.cbs.middleware.domain.KmCrops;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cbs.middleware.domain.KmCrops}.
 */
public interface KmCropsService {
    /**
     * Save a kmCrops.
     *
     * @param kmCrops the entity to save.
     * @return the persisted entity.
     */
    KmCrops save(KmCrops kmCrops);

    /**
     * Updates a kmCrops.
     *
     * @param kmCrops the entity to update.
     * @return the persisted entity.
     */
    KmCrops update(KmCrops kmCrops);

    /**
     * Partially updates a kmCrops.
     *
     * @param kmCrops the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KmCrops> partialUpdate(KmCrops kmCrops);

    /**
     * Get all the kmCrops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KmCrops> findAll(Pageable pageable);

    /**
     * Get the "id" kmCrops.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KmCrops> findOne(Long id);

    /**
     * Delete the "id" kmCrops.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
