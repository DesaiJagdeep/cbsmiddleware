package com.cbs.middleware.repository;

import com.cbs.middleware.domain.BankBranchMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankBranchMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankBranchMasterRepository extends JpaRepository<BankBranchMaster, Long> {}
