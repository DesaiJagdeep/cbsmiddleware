package com.cbs.middleware.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<IssPortalFile> findAllByPacsCode(Long pacsCode, Pageable pageable);

    Optional<List<IssPortalFile>> findAllBySchemeWiseBranchCodeAndFinancialYear(Long branchCode, String financialYear);


    @Query("select count(*) from IssPortalFile issPortalFile where issPortalFile.schemeWiseBranchCode =:schemeWiseBranchCode AND issPortalFile.financialYear =:financialYear AND issPortalFile.appSubmitedToKccCount>0")
	Integer findCompletedCountByBankBranch(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode,@Param("financialYear") String financialYear);

    @Query("select count(*) from IssPortalFile issPortalFile where issPortalFile.schemeWiseBranchCode =:schemeWiseBranchCode AND issPortalFile.financialYear =:financialYear AND issPortalFile.errorRecordCount>0 AND issPortalFile.appSubmitedToKccCount=0")
	Integer findInProgressCountByBankBranch(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode,@Param("financialYear") String financialYear);

    @Query("select count(*) from IssPortalFile issPortalFile where issPortalFile.schemeWiseBranchCode =:schemeWiseBranchCode AND issPortalFile.financialYear =:financialYear AND issPortalFile.errorRecordCount=0 AND issPortalFile.appPendingToSubmitCount>0 AND issPortalFile.appSubmitedToKccCount=0")
	Integer findPendingForApprovalCountByBankBranch(@Param("schemeWiseBranchCode") Long schemeWiseBranchCode,@Param("financialYear") String financialYear);

    @Query("select sum(applicationCount) from IssPortalFile where financial_year=:financialYear")
    Integer findSumOfapplicationCount(@Param("financialYear") String financialYear);
    @Query("select sum(errorRecordCount) from IssPortalFile where financial_year=:financialYear")
    Integer findSumOfErrorRecordCount(@Param("financialYear") String financialYear);
    @Query("select sum(appSubmitedToKccCount) from IssPortalFile where financial_year=:financialYear")
    Integer findSumOfAppSubmitedTokccCount(@Param("financialYear") String financialYear);
    @Query("select sum(appAcceptedByKccCount) from IssPortalFile where financial_year=:financialYear")
    Integer findSumOfappAcceptedByKccCount(@Param("financialYear") String financialYear);
    @Query("select sum(kccErrorRecordCount) from IssPortalFile where financial_year=:financialYear")
    Integer findSumOfkccErrorRecordCount(@Param("financialYear") String financialYear);
    @Query("select sum(isf.applicationCount) as application_count,sum(isf.errorRecordCount) as error_record_count,sum(isf.appSubmitedToKccCount) as kcc_submitted,sum(isf.appAcceptedByKccCount) as kcc_accepted, sum(isf.kccErrorRecordCount) as kcc_error_count ,bbm.branchName as branch_name, isf.pacsCode as pacs_code,isf.pacsName as pacs_name from IssPortalFile isf, BankBranchMaster bbm WHERE isf.financialYear =:finacialYear AND  isf.schemeWiseBranchCode = bbm.schemeWiseBranchCode AND bbm.schemeWiseBranchCode IN (select schemeWiseBranchCode from  BankBranchMaster WHERE bbm.talukaMaster.id =:talukaId)  group by bbm.branchName,isf.pacsCode,isf.pacsName")
    List<Tuple> findIssPortalFilesByTalukaIdAndFinacialYear(@Param("talukaId") Long talukaId  , @Param("finacialYear") String finacialYear );
    @Query("select sum(isf.applicationCount) as application_count,sum(isf.errorRecordCount) as error_record_count,sum(isf.appSubmitedToKccCount) as kcc_submitted,sum(isf.appAcceptedByKccCount) as kcc_accepted, sum(isf.kccErrorRecordCount) as kcc_error_count ,bbm.branchName as branch_name, isf.pacsCode as pacs_code,isf.pacsName as pacs_name from IssPortalFile isf, BankBranchMaster bbm WHERE isf.financialYear =:finacialYear AND  isf.schemeWiseBranchCode = bbm.schemeWiseBranchCode AND bbm.schemeWiseBranchCode=:sBranchCode group by bbm.branchName,isf.pacsCode,isf.pacsName")
    List<Tuple> findIssPortalFilesBySchemeWiseBranchCodeAndFinacialYear(@Param("sBranchCode")String sBranchCode,  @Param("finacialYear") String finacialYear);


    //-------------------------------------
    @Query(value = "select count(id) from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:finacialYear);",nativeQuery = true)
    Integer findTotalApplicationCountByFinancialYear(@Param("finacialYear") String finacialYear);

    @Query(value = "select count(id) from application_log where status = 'ERROR' and error_type= 'Validation Error' and iss_file_parser_id IN (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:finacialYear));",nativeQuery = true)
    Integer findValidationErrorCountByFinancialYear(@Param("finacialYear") String finacialYear);

    @Query(value = " select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:finacialYear))and  application_status = 0;",nativeQuery = true)
    Integer findKccAcceptedCountByFinancialYear(@Param("finacialYear") String finacialYear);

    @Query(value = " select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:finacialYear))and  application_status = 1;",nativeQuery = true)
    Integer findKccRejectedCountByFinancialYear(@Param("finacialYear") String finacialYear);

    @Query(value = " select count(id) from application_transaction where iss_file_parser_id in (select id from iss_file_parser where iss_portal_file_id IN (select id from iss_portal_file where financial_year=:finacialYear))and  application_status = 2;",nativeQuery = true)
    Integer findKccPendingCountByFinancialYear(@Param("finacialYear") String finacialYear);
}
