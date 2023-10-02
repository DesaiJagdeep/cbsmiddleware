package com.cbs.middleware.service;

import com.cbs.middleware.domain.KarkhanaVasuli;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KarkhanaVasuli}.
 */
public interface KarkhanaVasuliService {
    /**
     * Save a karkhanaVasuli.
     *
     * @param karkhanaVasuli the entity to save.
     * @return the persisted entity.
     */
    KarkhanaVasuli save(KarkhanaVasuli karkhanaVasuli);

    /**
     * Updates a karkhanaVasuli.
     *
     * @param karkhanaVasuli the entity to update.
     * @return the persisted entity.
     */
    KarkhanaVasuli update(KarkhanaVasuli karkhanaVasuli);

    /**
     * Partially updates a karkhanaVasuli.
     *
     * @param karkhanaVasuli the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KarkhanaVasuli> partialUpdate(KarkhanaVasuli karkhanaVasuli);

    /**
     * Get all the karkhanaVasulis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KarkhanaVasuli> findAll(Pageable pageable);

    /**
     * Get the "id" karkhanaVasuli.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KarkhanaVasuli> findOne(Long id);

    /**
     * Delete the "id" karkhanaVasuli.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
