package com.cbs.middleware.service;

import com.cbs.middleware.domain.BatchTransaction;
import com.cbs.middleware.domain.RetryBatchTransaction;

public interface RetryBatchTransactionService {
    /**
     * Save a retryBatchTransaction.
     *
     * @param retryBatchTransaction the entity to save.
     * @return the persisted entity.
     */
    RetryBatchTransaction save(RetryBatchTransaction retryBatchTransaction);
}
