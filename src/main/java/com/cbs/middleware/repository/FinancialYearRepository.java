package com.cbs.middleware.repository;

import com.cbs.middleware.domain.FinancialYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FinancialYear entity.
 */
@Repository
public interface FinancialYearRepository extends JpaRepository<FinancialYear, Long>, JpaSpecificationExecutor<FinancialYear> {}
