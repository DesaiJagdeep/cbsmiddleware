package com.cbs.middleware.service;

import com.cbs.middleware.domain.KmMagani;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KmMagani}.
 */
public interface KmMaganiService {
    /**
     * Save a kmMagani.
     *
     * @param kmMagani the entity to save.
     * @return the persisted entity.
     */
    KmMagani save(KmMagani kmMagani);

    /**
     * Updates a kmMagani.
     *
     * @param kmMagani the entity to update.
     * @return the persisted entity.
     */
    KmMagani update(KmMagani kmMagani);

    /**
     * Partially updates a kmMagani.
     *
     * @param kmMagani the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KmMagani> partialUpdate(KmMagani kmMagani);

    /**
     * Get all the kmMaganis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KmMagani> findAll(Pageable pageable);

    /**
     * Get the "id" kmMagani.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KmMagani> findOne(Long id);

    /**
     * Delete the "id" kmMagani.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
