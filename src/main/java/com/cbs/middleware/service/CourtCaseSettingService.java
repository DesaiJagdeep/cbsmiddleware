package com.cbs.middleware.service;

import com.cbs.middleware.domain.CourtCaseSetting;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CourtCaseSetting}.
 */
public interface CourtCaseSettingService {
    /**
     * Save a courtCaseSetting.
     *
     * @param courtCaseSetting the entity to save.
     * @return the persisted entity.
     */
    CourtCaseSetting save(CourtCaseSetting courtCaseSetting);

    /**
     * Updates a courtCaseSetting.
     *
     * @param courtCaseSetting the entity to update.
     * @return the persisted entity.
     */
    CourtCaseSetting update(CourtCaseSetting courtCaseSetting);

    /**
     * Partially updates a courtCaseSetting.
     *
     * @param courtCaseSetting the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourtCaseSetting> partialUpdate(CourtCaseSetting courtCaseSetting);

    /**
     * Get all the courtCaseSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourtCaseSetting> findAll(Pageable pageable);

    /**
     * Get the "id" courtCaseSetting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourtCaseSetting> findOne(Long id);

    /**
     * Delete the "id" courtCaseSetting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
