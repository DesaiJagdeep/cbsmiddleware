package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CourtCase;
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
}
