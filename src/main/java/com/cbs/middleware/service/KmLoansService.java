package com.cbs.middleware.service;

import com.cbs.middleware.domain.KmLoans;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KmLoans}.
 */
public interface KmLoansService {
    /**
     * Save a kmLoans.
     *
     * @param kmLoans the entity to save.
     * @return the persisted entity.
     */
    KmLoans save(KmLoans kmLoans);

    /**
     * Updates a kmLoans.
     *
     * @param kmLoans the entity to update.
     * @return the persisted entity.
     */
    KmLoans update(KmLoans kmLoans);

    /**
     * Partially updates a kmLoans.
     *
     * @param kmLoans the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KmLoans> partialUpdate(KmLoans kmLoans);

    /**
     * Get all the kmLoans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KmLoans> findAll(Pageable pageable);

    /**
     * Get the "id" kmLoans.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KmLoans> findOne(Long id);

    /**
     * Delete the "id" kmLoans.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
