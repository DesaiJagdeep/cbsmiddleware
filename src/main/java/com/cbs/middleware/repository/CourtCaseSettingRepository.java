package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CourtCaseSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CourtCaseSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourtCaseSettingRepository extends JpaRepository<CourtCaseSetting, Long>, JpaSpecificationExecutor<CourtCaseSetting> {}
