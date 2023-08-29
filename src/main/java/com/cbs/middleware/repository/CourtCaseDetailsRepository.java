package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CourtCaseDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CourtCaseDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourtCaseDetailsRepository extends JpaRepository<CourtCaseDetails, Long>, JpaSpecificationExecutor<CourtCaseDetails> {}
