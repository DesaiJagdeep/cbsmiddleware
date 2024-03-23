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

    @Query(value = "select distinct aadhar_number from center_report_june where pacs_number=:pacsNumber and financial_year=:financialYear ", nativeQuery = true)
    List<String> DistinctAadharCenterReportJune(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select distinct iss_file_parser_id from center_report_june where aadhar_number=:aadharNumber", nativeQuery = true)
    List<String> DistinctIssFileParserIdByAadhar(@Param("aadharNumber") String aadharNumber);

    @Query(value = "select * from center_report_june where iss_file_parser_id=:issFileParserId", nativeQuery = true)
    List<CenterReportJune> SelectFromCenterReportJuneByParserId(@Param("issFileParserId") String issFileParserId);






    @Query(value = "select count(distinct aadhar_number),sum(disburse_amount),sum(recovery_amount),upto_50000 from center_report_june where pacs_number=:pacsNumber and financial_year=:financialYear group by upto_50000", nativeQuery = true)
    List<Object[]> FindTotalLoansAndAccounts(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,upto_50000 from center_report_june crm where pacs_number=:pacsNumber and financial_year=:financialYear and recovery_amount > 0 group by upto_50000", nativeQuery = true)
    List<Object[]> FindRecoveryAccounts(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number),sum(interest_second_15),sum(inerest_second_25),upto_50000,social_category  from center_report_june where pacs_number=:pacsNumber and financial_year=:financialYear group by upto_50000,social_category", nativeQuery = true)
    List<Object[]> FindCastWiseInterestAmtAndAccs(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(interest_second_15),sum(interest_second_25),upto_50000,gender from center_report_june where pacs_number=:pacsNumber and financial_year=:financialYear and gender='FEMALE' group by upto_50000", nativeQuery = true)
    List<Object[]> FindWomensInterestAmtAndAccs(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(interest_second_15),sum(interest_second_25),upto_50000  from center_report_june where pacs_number=:pacsNumber and financial_year=:financialYear and farmer_type !='Other' group by upto_50000", nativeQuery = true)
    List<Object[]> FindFarmerTypeWiseInterestAmtAndAccs(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    //IsRecover=1
    @Query(value = "select count(distinct aadhar_number),sum(interest_state_second_3),upto_50000,social_category  from center_report_june where pacs_number=:pacsNumber and financial_year=:financialYear and is_recover=1 group by upto_50000,social_category", nativeQuery = true)
    List<Object[]> FindCastWiseInterestAmtAndAccsOnlyRecover(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(interest_state_second_3),upto_50000,gender  from center_report_june where pacs_number=:pacsNumber and financial_year=:financialYear and is_recover=1 and gender='FEMALE' group by upto_50000", nativeQuery = true)
    List<Object[]> FindWomensInterestAmtAndAccsOnlyRecover(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);

    @Query(value = "select count(distinct aadhar_number) ,sum(interest_state_second_3),upto_50000  from center_report_june where pacs_number=:pacsNumber and financial_year=:financialYear and is_recover=1 and farmer_type !='Other' group by upto_50000", nativeQuery = true)
    List<Object[]> FindFarmerTypeWiseInterestAmtAndAccsOnlyRecover(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);




}
