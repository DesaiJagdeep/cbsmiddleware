package com.cbs.middleware.service;

import com.cbs.middleware.domain.CropHangam;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CropHangam}.
 */
public interface CropHangamService {
    /**
     * Save a cropHangam.
     *
     * @param cropHangam the entity to save.
     * @return the persisted entity.
     */
    CropHangam save(CropHangam cropHangam);

    /**
     * Updates a cropHangam.
     *
     * @param cropHangam the entity to update.
     * @return the persisted entity.
     */
    CropHangam update(CropHangam cropHangam);

    /**
     * Partially updates a cropHangam.
     *
     * @param cropHangam the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CropHangam> partialUpdate(CropHangam cropHangam);

    /**
     * Get all the cropHangams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CropHangam> findAll(Pageable pageable);

    /**
     * Get the "id" cropHangam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CropHangam> findOne(Long id);

    /**
     * Delete the "id" cropHangam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
