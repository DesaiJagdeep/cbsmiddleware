package com.cbs.middleware.repository;

import com.cbs.middleware.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RetryBatchTransactionRepository extends JpaRepository<RetryBatchTransaction, Long>, JpaSpecificationExecutor<RetryBatchTransaction> {
    List<RetryBatchTransaction> findAllByStatus(String string);


}
