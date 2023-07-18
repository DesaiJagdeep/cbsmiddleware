package com.cbs.middleware.repository;

import com.cbs.middleware.domain.AccountHolderMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountHolderMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountHolderMasterRepository extends JpaRepository<AccountHolderMaster, Long> {
    List<AccountHolderMaster> findByAccountHolderIsContaining(String accountHolder);
}
