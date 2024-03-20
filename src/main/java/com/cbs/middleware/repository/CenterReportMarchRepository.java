package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CenterReportMarch;
import com.cbs.middleware.domain.CourtCaseDetails;
import com.cbs.middleware.domain.IsCalculateTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CenterReportMarchRepository extends JpaRepository<CenterReportMarch, Long>, JpaSpecificationExecutor<CenterReportMarch> {

    @Query(value = "select * from center_report_march where pacs_number=:pacsNumber and financial_year=:financialYear ", nativeQuery = true)
    List<CenterReportMarch> SelectFromCenterReportMarch(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);



}

