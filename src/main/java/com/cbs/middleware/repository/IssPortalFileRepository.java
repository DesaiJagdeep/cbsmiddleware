package com.cbs.middleware.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cbs.middleware.domain.IssPortalFile;

import javax.persistence.Tuple;

/**
 * Spring Data JPA repository for the IssPortalFile entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface IssPortalFileRepository extends JpaRepository<IssPortalFile, Long>, JpaSpecificationExecutor<IssPortalFile> {
    Optional<IssPortalFile> findByUniqueName(String uniqueId);

    boolean existsByFileName(String originalFilename);

    boolean existsByFileNameAndFinancialYear(String originalFilename, String financialYear);

    Page<IssPortalFile> findAllBySchemeWiseBranchCode(Long branchCode, Pageable pageable);

    @Query("select i from IssPortalFile i where i.schemeWiseBranchCode = ?1")
    List<IssPortalFile> findBySchemeWiseBranchCodeEquals(Long schemeWiseBranchCode, Pageable pageable);

    Page<IssPortalFile> findAllByPacsCode(Long pacsCode, Pageable pageable);

    Optional<List<IssPortalFile>> findAllBySchemeWiseBranchCodeAndFinancialYear(Long branchCode, String financialYear);


    @Query("select count(*) from IssPortalFile issPortalFile where issPortalFile.schemeWiseBranchCode =:schemeWiseBranchCode AND issPortalFile.financialYear =:financialYear AND issPortalFile.appSubmitedToKccCount>0")
    Integer findCompletedCountByBankBranch(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode, @Param("financialYear") String financialYear);


    @Query("select count(*) from IssPortalFile issPortalFile where issPortalFile.schemeWiseBranchCode =:schemeWiseBranchCode AND issPortalFile.financialYear =:financialYear AND issPortalFile.errorRecordCount>0 AND issPortalFile.appSubmitedToKccCount=0")
    Integer findInProgressCountByBankBranch(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode, @Param("financialYear") String financialYear);


    @Query("select count(*) from IssPortalFile issPortalFile where issPortalFile.schemeWiseBranchCode =:schemeWiseBranchCode AND issPortalFile.financialYear =:financialYear AND issPortalFile.errorRecordCount=0 AND issPortalFile.appPendingToSubmitCount>0 AND issPortalFile.appSubmitedToKccCount=0")
    Integer findPendingForApprovalCountByBankBranch(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode, @Param("financialYear") String financialYear);

    //------------------------------------------------------
    @Query(value = "select count(id) from iss_file_parser where financial_year=:financialYear ", nativeQuery = true)
    Integer findTotalApplicationCountByFinancialYear(@Param("financialYear") String financialYear);

    @Query(value = "select count(id) from application_log where status = 'ERROR' and error_type= 'Validation Error' and iss_file_parser_id IN (select id from iss_file_parser where financial_year=:financialYear)", nativeQuery = true)
    Integer findValidationErrorCountByFinancialYear(@Param("financialYear") String financialYear);

    @Query(value = " select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where financial_year=:financialYear) and  application_status = 1", nativeQuery = true)
    Integer findKccAcceptedCountByFinancialYear(@Param("financialYear") String financialYear);

    @Query(value = " select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where financial_year=:financialYear) and  application_status = 0", nativeQuery = true)
    Integer findKccRejectedCountByFinancialYear(@Param("financialYear") String financialYear);

    @Query(value = "select count(at.id) from application_transaction at , application_log al where  at.iss_file_parser_id in (select id from iss_file_parser where financial_year=:financialYear ) and at.application_status = 0 and  at.iss_file_parser_id = al.iss_file_parser_id and (al.error_message like 'This accountNumber is already being processed by batch%' OR al.error_message like 'Duplicate farmer and account details%') and error_type ='KCC Error' ", nativeQuery = true)
    Integer findKccDuplicateOrAccountNumberProcessedCount(@Param("financialYear") String financialYear);

    @Query(value = " select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where financial_year=:financialYear) and  application_status = 2", nativeQuery = true)
    Integer findKccPendingCountByFinancialYear(@Param("financialYear") String financialYear);

    @Query(value = "select count(distinct(aadhar_number)) from iss_file_parser where financial_year=:financialYear ", nativeQuery = true)
    Integer findDistinctFarmersCountByFinancialYear(@Param("financialYear") String financialYear);

    //-------------------
    //Branch by taluka id and financial year
    @Query(value = "select distinct(scheme_wise_branch_code),branch_name from iss_portal_file where financial_year=:financialYear and scheme_wise_branch_code in (select scheme_wise_branch_code from bank_branch_master where taluka_master_id =:talukaId) order by branch_name asc", nativeQuery = true)
    List<Object[]> findTalukaWiseBranches(@Param("talukaId") Long talukaId, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and scheme_wise_branch_code =:schemeWiseBranchCode)", nativeQuery = true)
    Long findTotalAppByBranchCodeAndFinancialYear(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from application_log where status = 'ERROR' and error_type= 'Validation Error' and iss_file_parser_id IN (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and scheme_wise_branch_code =:schemeWiseBranchCode))", nativeQuery = true)
    Long findValidationErrorByBranchCodeAndFinancialYear(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and scheme_wise_branch_code =:schemeWiseBranchCode)) and  application_status = 1", nativeQuery = true)
    Long findKccAcceptedByBranchCodeAndFinancialYear(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and scheme_wise_branch_code =:schemeWiseBranchCode)) and  application_status = 0", nativeQuery = true)
    Long findKccRejectedByBranchCodeAndFinancialYear(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and scheme_wise_branch_code =:schemeWiseBranchCode)) and  application_status = 2", nativeQuery = true)
    Long findKccPendingByBranchCodeAndFinancialYear(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode, @Param("financialYear") String financialYear);

    //------------------------------------------------
    //pacs by schemeWiseDBanchCode and FinancialYear
    @Query(value = "select pacs_code,pacs_name from iss_portal_file where scheme_wise_branch_code=:schemeBranchCode and financial_year=:financialYear order by pacs_name asc ", nativeQuery = true)
    List<Object[]> findBranchWisePacs(@Param("schemeBranchCode") Long schemeBranchCode, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and pacs_code =:pacsCode)", nativeQuery = true)
    Long findTotalAppByPacsCodeAndFinancialYear(@Param("pacsCode") Long pacsCode, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from application_log where status = 'ERROR' and error_type= 'Validation Error' and iss_file_parser_id IN (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and pacs_code =:pacsCode))", nativeQuery = true)
    Long findValidationErrorByPacsCodeAndFinancialYear(@Param("pacsCode") Long pacsCode, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and pacs_code =:pacsCode)) and  application_status = 1", nativeQuery = true)
    Long findKccAcceptedByPacsCodeAndFinancialYear(@Param("pacsCode") Long pacsCode, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and pacs_code =:pacsCode)) and  application_status = 0", nativeQuery = true)
    Long findKccRejectedByPacsCodeAndFinancialYear(@Param("pacsCode") Long pacsCode, @Param("financialYear") String financialYear);

    @Query(value = "select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:financialYear and pacs_code =:pacsCode)) and  application_status = 2", nativeQuery = true)
    Long findKccPendingByPacsCodeAndFinancialYear(@Param("pacsCode") Long pacsCode, @Param("financialYear") String financialYear);

   // @Query(value = "select count(ipf.id)from iss_portal_file ipf where ipf.financial_year=:financialYear and ipf.scheme_wise_branch_code in (select scheme_wise_branch_code from bank_branch_master where taluka_master_id =:talukaId ) and verified_file=0", nativeQuery = true)
   @Query(value = "select count(ipf.id)from iss_portal_file ipf where ipf.financial_year=:financialYear and ipf.scheme_wise_branch_code in (select scheme_wise_branch_code from bank_branch_master where taluka_master_id =:talukaId ) and download_file = 0", nativeQuery = true)
    Integer findPendingForApprovalCountByBanchUser(@Param("talukaId") Long talukaId, @Param("financialYear") String financialYear);
    @Query(value = "select count(ipf.id)from iss_portal_file ipf where ipf.financial_year=:financialYear and ipf.scheme_wise_branch_code in (select scheme_wise_branch_code from bank_branch_master where taluka_master_id =:talukaId ) and download_file = 1 and verified_file=0 ", nativeQuery = true)
   Integer findPendingForApprovalCountByBanchAdmin(@Param("talukaId") Long talukaId, @Param("financialYear") String financialYear);

    @Query(value = "select count(ipf.id) from iss_portal_file ipf where ipf.financial_year=:financialYear and ipf.scheme_wise_branch_code in (select scheme_wise_branch_code from bank_branch_master where taluka_master_id =:talukaId)", nativeQuery = true)
    Integer findTotalIssPortalFileByTalukaId(@Param("talukaId") Long talukaId, @Param("financialYear") String financialYear);

    @Query(value = "select count(ipf.id) from iss_portal_file ipf where ipf.financial_year=:financialYear and ipf.scheme_wise_branch_code in (select scheme_wise_branch_code from bank_branch_master where taluka_master_id =:talukaId) and exists (select 1 from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id = ipf.id) and batch_id is not null )", nativeQuery = true)
    Integer findNotNullIssPortalFile(@Param("talukaId") Long talukaId, @Param("financialYear") String financialYear);

    @Query(value = "select count(ipf.id)from iss_portal_file ipf where ipf.financial_year=:financialYear and ipf.scheme_wise_branch_code in (select scheme_wise_branch_code from bank_branch_master where taluka_master_id =:talukaId) and ipf.app_submited_to_kcc_count>0",nativeQuery = true)
    Integer findCompletedCountByTalukaId(@Param("talukaId") Long talukaId, @Param("financialYear") String financialYear);

    @Query(value = "select count(ipf.id)from iss_portal_file ipf where ipf.financial_year=:financialYear and ipf.scheme_wise_branch_code in (select scheme_wise_branch_code from bank_branch_master where taluka_master_id =:talukaId) and ipf.error_record_count>0 AND ipf.app_submited_to_kcc_count=0",nativeQuery = true)
    Integer findInProgressCountByTalukaId(@Param("talukaId") Long talukaId, @Param("financialYear") String financialYear);

}
