package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.BatchTransaction;
import com.cbs.middleware.repository.BatchTransactionRepository;
import com.cbs.middleware.service.BatchTransactionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BatchTransaction}.
 */
@Service
@Transactional
public class BatchTransactionServiceImpl implements BatchTransactionService {

    private final Logger log = LoggerFactory.getLogger(BatchTransactionServiceImpl.class);

    private final BatchTransactionRepository batchTransactionRepository;

    public BatchTransactionServiceImpl(BatchTransactionRepository batchTransactionRepository) {
        this.batchTransactionRepository = batchTransactionRepository;
    }

    @Override
    public BatchTransaction save(BatchTransaction batchTransaction) {
        log.debug("Request to save BatchTransaction : {}", batchTransaction);
        return batchTransactionRepository.save(batchTransaction);
    }

    @Override
    public BatchTransaction update(BatchTransaction batchTransaction) {
        log.debug("Request to update BatchTransaction : {}", batchTransaction);
        return batchTransactionRepository.save(batchTransaction);
    }

    @Override
    public Optional<BatchTransaction> partialUpdate(BatchTransaction batchTransaction) {
        log.debug("Request to partially update BatchTransaction : {}", batchTransaction);

        return batchTransactionRepository
            .findById(batchTransaction.getId())
            .map(existingBatchTransaction -> {
                if (batchTransaction.getStatus() != null) {
                    existingBatchTransaction.setStatus(batchTransaction.getStatus());
                }
                if (batchTransaction.getBatchDetails() != null) {
                    existingBatchTransaction.setBatchDetails(batchTransaction.getBatchDetails());
                }
                if (batchTransaction.getApplicationCount() != null) {
                    existingBatchTransaction.setApplicationCount(batchTransaction.getApplicationCount());
                }
                if (batchTransaction.getNotes() != null) {
                    existingBatchTransaction.setNotes(batchTransaction.getNotes());
                }
                if (batchTransaction.getBatchId() != null) {
                    existingBatchTransaction.setBatchId(batchTransaction.getBatchId());
                }
                if (batchTransaction.getBatchAckId() != null) {
                    existingBatchTransaction.setBatchAckId(batchTransaction.getBatchAckId());
                }

                return existingBatchTransaction;
            })
            .map(batchTransactionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BatchTransaction> findAll(Pageable pageable) {
        log.debug("Request to get all BatchTransactions");
        return batchTransactionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BatchTransaction> findOne(Long id) {
        log.debug("Request to get BatchTransaction : {}", id);
        return batchTransactionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BatchTransaction : {}", id);
        batchTransactionRepository.deleteById(id);
    }
}
