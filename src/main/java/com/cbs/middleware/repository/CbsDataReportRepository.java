package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CbsDataReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CbsDataReport entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface CbsDataReportRepository extends JpaRepository<CbsDataReport, Long>, JpaSpecificationExecutor<CbsDataReport> {}
