package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CenterReportJune;
import com.cbs.middleware.domain.CenterReportMarch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CenterReportJuneRepository extends JpaRepository<CenterReportJune, Long>, JpaSpecificationExecutor<CenterReportJune> {

    @Query(value = "select * from center_report_june where pacs_number=:pacsNumber and financial_year=:financialYear ", nativeQuery = true)
    List<CenterReportJune> SelectFromCenterReportJune(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);
}
