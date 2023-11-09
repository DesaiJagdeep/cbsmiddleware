package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.domain.CourtCaseSetting;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CourtCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourtCaseRepository extends JpaRepository<CourtCase, Long>, JpaSpecificationExecutor<CourtCase> {
    boolean existsByFileName(String originalFilename);

    Optional<CourtCase> findOneByNameOfDefaulter(String nameOFdef);

	List<CourtCase> findAllByCourtCaseSetting(CourtCaseSetting courtCaseSetting);
}
