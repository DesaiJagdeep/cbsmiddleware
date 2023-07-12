package com.cbs.middleware.service;

import com.cbs.middleware.domain.BatchTransaction;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BatchTransaction}.
 */
public interface BatchTransactionService {
    /**
     * Save a batchTransaction.
     *
     * @param batchTransaction the entity to save.
     * @return the persisted entity.
     */
    BatchTransaction save(BatchTransaction batchTransaction);

    /**
     * Updates a batchTransaction.
     *
     * @param batchTransaction the entity to update.
     * @return the persisted entity.
     */
    BatchTransaction update(BatchTransaction batchTransaction);

    /**
     * Partially updates a batchTransaction.
     *
     * @param batchTransaction the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BatchTransaction> partialUpdate(BatchTransaction batchTransaction);

    /**
     * Get all the batchTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BatchTransaction> findAll(Pageable pageable);

    /**
     * Get the "id" batchTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BatchTransaction> findOne(Long id);

    /**
     * Delete the "id" batchTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
