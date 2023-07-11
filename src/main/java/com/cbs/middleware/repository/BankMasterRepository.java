package com.cbs.middleware.repository;

import com.cbs.middleware.domain.BankMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankMasterRepository extends JpaRepository<BankMaster, Long> {}
