package com.cbs.middleware.service;

import com.cbs.middleware.domain.IsCalculateTemp;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link IsCalculateTemp}.
 */
public interface IsCalculateTempService {
    /**
     * Save a isCalculateTemp.
     *
     * @param isCalculateTemp the entity to save.
     * @return the persisted entity.
     */
    IsCalculateTemp save(IsCalculateTemp isCalculateTemp);

    /**
     * Updates a isCalculateTemp.
     *
     * @param isCalculateTemp the entity to update.
     * @return the persisted entity.
     */
    IsCalculateTemp update(IsCalculateTemp isCalculateTemp);

    /**
     * Partially updates a isCalculateTemp.
     *
     * @param isCalculateTemp the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IsCalculateTemp> partialUpdate(IsCalculateTemp isCalculateTemp);

    /**
     * Get all the isCalculateTemps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IsCalculateTemp> findAll(Pageable pageable);

    /**
     * Get the "id" isCalculateTemp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IsCalculateTemp> findOne(Long id);

    /**
     * Delete the "id" isCalculateTemp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
