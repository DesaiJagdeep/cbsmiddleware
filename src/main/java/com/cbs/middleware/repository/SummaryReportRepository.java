package com.cbs.middleware.repository;

import com.cbs.middleware.domain.StateReportPanjabro;
import com.cbs.middleware.domain.SummaryReport;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STPositiveUniversalMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SummaryReportRepository extends JpaRepository<SummaryReport, Long>, JpaSpecificationExecutor<SummaryReport> {

    @Query(value = "select * from summary_report where pacs_number=:pacsNumber and financial_year=:financialYear and report_type=:reportType and report_condition=:reportCondition", nativeQuery = true)
    List<SummaryReport> SelectFromSummaryReport(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear,@Param("reportType") Integer reportType,@Param("reportCondition") Integer reportCondition);





}
