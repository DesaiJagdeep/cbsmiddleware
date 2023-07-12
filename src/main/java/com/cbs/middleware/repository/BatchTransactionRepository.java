package com.cbs.middleware.repository;

import com.cbs.middleware.domain.BatchTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BatchTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatchTransactionRepository extends JpaRepository<BatchTransaction, Long>, JpaSpecificationExecutor<BatchTransaction> {}
