package com.cbs.middleware.service;

import com.cbs.middleware.domain.KmMaganiCrop;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KmMaganiCrop}.
 */
public interface KmMaganiCropService {
    /**
     * Save a kmMaganiCrop.
     *
     * @param kmMaganiCrop the entity to save.
     * @return the persisted entity.
     */
    KmMaganiCrop save(KmMaganiCrop kmMaganiCrop);

    /**
     * Updates a kmMaganiCrop.
     *
     * @param kmMaganiCrop the entity to update.
     * @return the persisted entity.
     */
    KmMaganiCrop update(KmMaganiCrop kmMaganiCrop);

    /**
     * Partially updates a kmMaganiCrop.
     *
     * @param kmMaganiCrop the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KmMaganiCrop> partialUpdate(KmMaganiCrop kmMaganiCrop);

    /**
     * Get all the kmMaganiCrops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KmMaganiCrop> findAll(Pageable pageable);

    /**
     * Get the "id" kmMaganiCrop.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KmMaganiCrop> findOne(Long id);

    /**
     * Delete the "id" kmMaganiCrop.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
