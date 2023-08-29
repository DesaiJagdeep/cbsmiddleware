package com.cbs.middleware.service;

import com.cbs.middleware.domain.CourtCaseDetails;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CourtCaseDetails}.
 */
public interface CourtCaseDetailsService {
    /**
     * Save a courtCaseDetails.
     *
     * @param courtCaseDetails the entity to save.
     * @return the persisted entity.
     */
    CourtCaseDetails save(CourtCaseDetails courtCaseDetails);

    /**
     * Updates a courtCaseDetails.
     *
     * @param courtCaseDetails the entity to update.
     * @return the persisted entity.
     */
    CourtCaseDetails update(CourtCaseDetails courtCaseDetails);

    /**
     * Partially updates a courtCaseDetails.
     *
     * @param courtCaseDetails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourtCaseDetails> partialUpdate(CourtCaseDetails courtCaseDetails);

    /**
     * Get all the courtCaseDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourtCaseDetails> findAll(Pageable pageable);

    /**
     * Get the "id" courtCaseDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourtCaseDetails> findOne(Long id);

    /**
     * Delete the "id" courtCaseDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
