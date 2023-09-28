package com.cbs.middleware.service;

import com.cbs.middleware.domain.LoanDemandKMPatrak;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link LoanDemandKMPatrak}.
 */
public interface LoanDemandKMPatrakService {
    /**
     * Save a loanDemandKMPatrak.
     *
     * @param loanDemandKMPatrak the entity to save.
     * @return the persisted entity.
     */
    LoanDemandKMPatrak save(LoanDemandKMPatrak loanDemandKMPatrak);

    /**
     * Updates a loanDemandKMPatrak.
     *
     * @param loanDemandKMPatrak the entity to update.
     * @return the persisted entity.
     */
    LoanDemandKMPatrak update(LoanDemandKMPatrak loanDemandKMPatrak);

    /**
     * Partially updates a loanDemandKMPatrak.
     *
     * @param loanDemandKMPatrak the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LoanDemandKMPatrak> partialUpdate(LoanDemandKMPatrak loanDemandKMPatrak);

    /**
     * Get all the loanDemandKMPatraks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoanDemandKMPatrak> findAll(Pageable pageable);

    /**
     * Get the "id" loanDemandKMPatrak.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoanDemandKMPatrak> findOne(Long id);

    /**
     * Delete the "id" loanDemandKMPatrak.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
