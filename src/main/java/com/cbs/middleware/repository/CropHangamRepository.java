package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CropHangam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CropHangam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CropHangamRepository extends JpaRepository<CropHangam, Long>, JpaSpecificationExecutor<CropHangam> {}
