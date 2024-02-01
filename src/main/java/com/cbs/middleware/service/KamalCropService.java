package com.cbs.middleware.service;

import com.cbs.middleware.domain.KamalCrop;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KamalCrop}.
 */
public interface KamalCropService {
    /**
     * Save a kamalCrop.
     *
     * @param kamalCrop the entity to save.
     * @return the persisted entity.
     */
    KamalCrop save(KamalCrop kamalCrop);

    /**
     * Updates a kamalCrop.
     *
     * @param kamalCrop the entity to update.
     * @return the persisted entity.
     */
    KamalCrop update(KamalCrop kamalCrop);

    /**
     * Partially updates a kamalCrop.
     *
     * @param kamalCrop the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KamalCrop> partialUpdate(KamalCrop kamalCrop);

    /**
     * Get all the kamalCrops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KamalCrop> findAll(Pageable pageable);

    /**
     * Get the "id" kamalCrop.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KamalCrop> findOne(Long id);

    /**
     * Delete the "id" kamalCrop.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
