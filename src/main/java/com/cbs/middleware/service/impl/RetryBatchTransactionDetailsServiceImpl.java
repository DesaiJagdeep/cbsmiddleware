package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.RetryBatchTransaction;
import com.cbs.middleware.domain.RetryBatchTransactionDetails;
import com.cbs.middleware.repository.RetryBatchTransactionDetailsRepository;
import com.cbs.middleware.repository.RetryBatchTransactionRepository;
import com.cbs.middleware.service.RetryBatchTransactionDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryBatchTransactionDetailsServiceImpl implements RetryBatchTransactionDetailsService {
    private final Logger log = LoggerFactory.getLogger(BatchTransactionServiceImpl.class);

    private final RetryBatchTransactionDetailsRepository retryBatchTransactionDetailsRepository;

    public RetryBatchTransactionDetailsServiceImpl(RetryBatchTransactionDetailsRepository retryBatchTransactionDetailsRepository) {
        this.retryBatchTransactionDetailsRepository = retryBatchTransactionDetailsRepository;
    }

    @Override
    public RetryBatchTransactionDetails save(RetryBatchTransactionDetails batchTransaction) {
        log.debug("Request to save RetryBatchTransactionDetails : {}", batchTransaction);
        return retryBatchTransactionDetailsRepository.save(batchTransaction);
    }
}
