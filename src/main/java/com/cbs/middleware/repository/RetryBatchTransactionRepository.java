package com.cbs.middleware.repository;

import com.cbs.middleware.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RetryBatchTransactionRepository extends JpaRepository<RetryBatchTransaction, Long>, JpaSpecificationExecutor<RetryBatchTransaction> {


    @Query(value = "SELECT * FROM retry_batch_transaction WHERE batch_id=:batchId And status='Submitted'", nativeQuery = true)
    RetryBatchTransaction findRetryBatchTransactionByBatchId(@Param("batchId") String batchId);


    List<RetryBatchTransaction> findAllByStatus(String status);

}
