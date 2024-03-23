package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CenterReportJune;
import com.cbs.middleware.domain.StateReportPanjabro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StateReportPanjabraoRepository extends JpaRepository<StateReportPanjabro, Long>, JpaSpecificationExecutor<StateReportPanjabro> {

    @Query(value = "select * from state_report_panjabrao where pacs_number=:pacsNumber and financial_year=:financialYear", nativeQuery = true)
    List<StateReportPanjabro> SelectFromStateReportPanjabro(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number),sum(disburse_amount),sum(recovery_amount),upto_50000 from state_report_panjabrao where pacs_number=:pacsNumber and financial_year=:financialYear  group by upto_50000", nativeQuery = true)
    List<Object[]> FindTotalLoansAndAccounts(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,upto_50000 from state_report_panjabrao crm where pacs_number=:pacsNumber and financial_year=:financialYear  and recovery_amount > 0 group by upto_50000", nativeQuery = true)
    List<Object[]> FindRecoveryAccounts(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number),sum(panjabrao_int_3),upto_50000,social_category  from state_report_panjabrao crm where pacs_number=:pacsNumber and financial_year=:financialYear and is_recover =1 group by upto_50000,social_category", nativeQuery = true)
    List<Object[]> FindCastWiseInterestAmtAndAccs(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(panjabrao_int_3),upto_50000,gender  from state_report_panjabrao crm where pacs_number=:pacsNumber and financial_year=:financialYear and is_recover =1 and gender='FEMALE' group by upto_50000", nativeQuery = true)
    List<Object[]> FindWomensInterestAmtAndAccs(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(panjabrao_int_3),upto_50000  from state_report_panjabrao crm where pacs_number=:pacsNumber and financial_year=:financialYear and is_recover =1 and farmer_type !='Other' group by upto_50000", nativeQuery = true)
    List<Object[]> FindFarmerTypeWiseInterestAmtAndAccs(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);



}

