package com.cbs.middleware.repository;

import com.cbs.middleware.domain.AccountHolderMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountHolderMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountHolderMasterRepository extends JpaRepository<AccountHolderMaster, Long> {}
