package com.cbs.middleware.repository;

import com.cbs.middleware.domain.BatchTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BatchTransaction entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface BatchTransactionRepository extends JpaRepository<BatchTransaction, Long>, JpaSpecificationExecutor<BatchTransaction> {
    List<BatchTransaction> findAllByStatus(String string);

}
