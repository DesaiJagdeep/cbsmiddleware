package com.cbs.middleware.repository;

import com.cbs.middleware.domain.LoanDemandKMPatrak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanDemandKMPatrak entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface LoanDemandKMPatrakRepository
    extends JpaRepository<LoanDemandKMPatrak, Long>, JpaSpecificationExecutor<LoanDemandKMPatrak> {}
