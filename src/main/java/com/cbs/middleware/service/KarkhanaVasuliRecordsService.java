package com.cbs.middleware.service;

import com.cbs.middleware.domain.KarkhanaVasuliRecords;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KarkhanaVasuliRecords}.
 */
public interface KarkhanaVasuliRecordsService {
    /**
     * Save a karkhanaVasuliRecords.
     *
     * @param karkhanaVasuliRecords the entity to save.
     * @return the persisted entity.
     */
    KarkhanaVasuliRecords save(KarkhanaVasuliRecords karkhanaVasuliRecords);

    /**
     * Updates a karkhanaVasuliRecords.
     *
     * @param karkhanaVasuliRecords the entity to update.
     * @return the persisted entity.
     */
    KarkhanaVasuliRecords update(KarkhanaVasuliRecords karkhanaVasuliRecords);

    /**
     * Partially updates a karkhanaVasuliRecords.
     *
     * @param karkhanaVasuliRecords the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KarkhanaVasuliRecords> partialUpdate(KarkhanaVasuliRecords karkhanaVasuliRecords);

    /**
     * Get all the karkhanaVasuliRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KarkhanaVasuliRecords> findAll(Pageable pageable);

    /**
     * Get the "id" karkhanaVasuliRecords.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KarkhanaVasuliRecords> findOne(Long id);

    /**
     * Delete the "id" karkhanaVasuliRecords.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
