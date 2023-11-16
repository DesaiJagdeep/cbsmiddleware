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
}
