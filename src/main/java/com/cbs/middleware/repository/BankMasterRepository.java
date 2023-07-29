package com.cbs.middleware.repository;

import com.cbs.middleware.domain.BankMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankMasterRepository extends JpaRepository<BankMaster, Long> {
    @Query("select bankMaster.bankCode from BankMaster bankMaster where bankMaster.bankName =:bankName")
    String findBankCodeByBankName(@Param("bankName") String bankName);
}
