package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.BatchTransaction;
import com.cbs.middleware.domain.RetryBatchTransaction;
import com.cbs.middleware.repository.BatchTransactionRepository;
import com.cbs.middleware.repository.RetryBatchTransactionRepository;
import com.cbs.middleware.service.BatchTransactionService;
import com.cbs.middleware.service.RetryBatchTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class RetryBatchTransactionServiceImpl implements RetryBatchTransactionService {

    private final Logger log = LoggerFactory.getLogger(BatchTransactionServiceImpl.class);

    private final RetryBatchTransactionRepository retryBatchTransactionRepository;

    public RetryBatchTransactionServiceImpl(RetryBatchTransactionRepository retryBatchTransactionRepository) {
        this.retryBatchTransactionRepository = retryBatchTransactionRepository;
    }

    @Override
    public RetryBatchTransaction save(RetryBatchTransaction batchTransaction) {
        log.debug("Request to save RetryBatchTransaction : {}", batchTransaction);
        return retryBatchTransactionRepository.save(batchTransaction);
    }
}




