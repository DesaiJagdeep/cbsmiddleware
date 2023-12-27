package com.cbs.middleware.repository;

import com.cbs.middleware.domain.RetryBatchTransaction;
import com.cbs.middleware.domain.RetryBatchTransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RetryBatchTransactionDetailsRepository extends JpaRepository<RetryBatchTransactionDetails, Long>, JpaSpecificationExecutor<RetryBatchTransactionDetails> {
    RetryBatchTransactionDetails findOneByUniqueId(String uniqueId);

    @Query(value = "select count(*) from retry_batch_transaction_details where retry_batch_transaction_id In(select id from retry_batch_transaction where batch_ack_id=:batchAckId)", nativeQuery = true)
    Integer countByStatus(@Param("batchAckId") String batchAckId);
}
