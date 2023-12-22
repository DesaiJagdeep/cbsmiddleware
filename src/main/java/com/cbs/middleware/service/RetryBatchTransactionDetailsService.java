package com.cbs.middleware.service;

import com.cbs.middleware.domain.RetryBatchTransaction;
import com.cbs.middleware.domain.RetryBatchTransactionDetails;

public interface RetryBatchTransactionDetailsService {
    /**
     * Save a retryBatchTransactionDetails.
     *
     * @param retryBatchTransactionDetails the entity to save.
     * @return the persisted entity.
     */
    RetryBatchTransactionDetails save(RetryBatchTransactionDetails retryBatchTransactionDetails);
}
