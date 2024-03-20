package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CenterReportJune;
import com.cbs.middleware.domain.StateReportPanjabro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StateReportPanjabraoRepository extends JpaRepository<StateReportPanjabro, Long>, JpaSpecificationExecutor<StateReportPanjabro> {

    @Query(value = "select * from state_report_panjabrao where pacs_number=:pacsNumber and financial_year=:financialYear ", nativeQuery = true)
    List<StateReportPanjabro> SelectFromStateReportPanjabro(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);
}

