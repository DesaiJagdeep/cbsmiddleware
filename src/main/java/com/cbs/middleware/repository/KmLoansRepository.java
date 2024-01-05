package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmLoans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KmLoans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmLoansRepository extends JpaRepository<KmLoans, Long>, JpaSpecificationExecutor<KmLoans> {}
