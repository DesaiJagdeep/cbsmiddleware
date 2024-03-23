package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CenterReportMarch;
import com.cbs.middleware.domain.CourtCaseDetails;
import com.cbs.middleware.domain.IsCalculateTemp;
import com.cbs.middleware.domain.SummaryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CenterReportMarchRepository extends JpaRepository<CenterReportMarch, Long>, JpaSpecificationExecutor<CenterReportMarch> {

    @Query(value = "select * from center_report_march where pacs_number=:pacsNumber and financial_year=:financialYear ", nativeQuery = true)
    List<CenterReportMarch> FindFromCenterReportMarch(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number),sum(disburse_amount),sum(recovery_amount),upto_50000 from center_report_march where pacs_number=:pacsNumber and financial_year=:financialYear group by upto_50000", nativeQuery = true)
    List<Object[]> FindTotalLoansAndAccounts(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,upto_50000 from center_report_march crm where pacs_number=:pacsNumber and financial_year=:financialYear  and recovery_amount > 0 group by upto_50000", nativeQuery = true)
    List<Object[]> FindRecoveryAccounts(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number),sum(interest_first_15),sum(interest_first_25),upto_50000,social_category  from center_report_march crm where pacs_number=:pacsNumber and financial_year=:financialYear group by upto_50000,social_category", nativeQuery = true)
    List<Object[]> FindCastWiseInterestAmtAndAccs(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(interest_first_15),sum(interest_first_25),upto_50000,gender  from center_report_march crm where pacs_number=:pacsNumber and financial_year=:financialYear and gender='FEMALE' group by upto_50000", nativeQuery = true)
    List<Object[]> FindWomensInterestAmtAndAccs(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(interest_first_15),sum(interest_first_25),upto_50000  from center_report_march crm where pacs_number=:pacsNumber and financial_year=:financialYear and farmer_type !='Other' group by upto_50000", nativeQuery = true)
    List<Object[]> FindFarmerTypeWiseInterestAmtAndAccs(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);


//IsRecover=1
    @Query(value = "select count(distinct aadhar_number),sum(interest_state_first_3),upto_50000,social_category  from center_report_march crm where pacs_number=:pacsNumber and financial_year=:financialYear and is_recover=1 group by upto_50000,social_category", nativeQuery = true)
    List<Object[]> FindCastWiseInterestAmtAndAccsOnlyRecover(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(interest_state_first_3),upto_50000,gender  from center_report_march crm where pacs_number=:pacsNumber and financial_year=:financialYear and is_recover=1 and gender='FEMALE' group by upto_50000", nativeQuery = true)
    List<Object[]> FindWomensInterestAmtAndAccsOnlyRecover(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(interest_state_first_3),upto_50000  from center_report_march crm where pacs_number=:pacsNumber and financial_year=:financialYear and is_recover=1 and farmer_type !='Other' group by upto_50000", nativeQuery = true)
    List<Object[]> FindFarmerTypeWiseInterestAmtAndAccsOnlyRecover(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);



}

