package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.domain.CourtCaseSetting;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CourtCase entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface CourtCaseRepository extends JpaRepository<CourtCase, Long>, JpaSpecificationExecutor<CourtCase> {
    boolean existsByFileName(String originalFilename);

    Optional<CourtCase> findOneByNameOfDefaulter(String nameOFdef);

    @Query("select courtCase from CourtCase courtCase where courtCase.courtCaseSetting =:courtCaseSetting  AND courtCase.noticeOfRepayLoanCount=0 AND courtCase.priorDemandNoticeCount=0")
	List<CourtCase> findAllByCourtCaseSettingAndNoticeNotPrint(@Param("courtCaseSetting") CourtCaseSetting courtCaseSetting);
}
