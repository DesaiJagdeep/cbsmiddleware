package com.cbs.middleware.service;

import com.cbs.middleware.domain.CourtCase;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CourtCase}.
 */
public interface CourtCaseService {
    /**
     * Save a courtCase.
     *
     * @param courtCase the entity to save.
     * @return the persisted entity.
     */
    CourtCase save(CourtCase courtCase);

    /**
     * Updates a courtCase.
     *
     * @param courtCase the entity to update.
     * @return the persisted entity.
     */
    CourtCase update(CourtCase courtCase);

    /**
     * Partially updates a courtCase.
     *
     * @param courtCase the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourtCase> partialUpdate(CourtCase courtCase);

    /**
     * Get all the courtCases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourtCase> findAll(Pageable pageable);

    /**
     * Get the "id" courtCase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourtCase> findOne(Long id);

    /**
     * Delete the "id" courtCase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
