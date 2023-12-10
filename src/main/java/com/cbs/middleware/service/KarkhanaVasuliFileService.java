package com.cbs.middleware.service;

import com.cbs.middleware.domain.KarkhanaVasuliFile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cbs.middleware.domain.KarkhanaVasuliFile}.
 */
public interface KarkhanaVasuliFileService {
    /**
     * Save a karkhanaVasuliFile.
     *
     * @param karkhanaVasuliFile the entity to save.
     * @return the persisted entity.
     */
    KarkhanaVasuliFile save(KarkhanaVasuliFile karkhanaVasuliFile);

    /**
     * Updates a karkhanaVasuliFile.
     *
     * @param karkhanaVasuliFile the entity to update.
     * @return the persisted entity.
     */
    KarkhanaVasuliFile update(KarkhanaVasuliFile karkhanaVasuliFile);

    /**
     * Partially updates a karkhanaVasuliFile.
     *
     * @param karkhanaVasuliFile the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KarkhanaVasuliFile> partialUpdate(KarkhanaVasuliFile karkhanaVasuliFile);

    /**
     * Get all the karkhanaVasuliFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KarkhanaVasuliFile> findAll(Pageable pageable);

    /**
     * Get the "id" karkhanaVasuliFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KarkhanaVasuliFile> findOne(Long id);

    /**
     * Delete the "id" karkhanaVasuliFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
