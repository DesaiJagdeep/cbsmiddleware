package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CropRateMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CropRateMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CropRateMasterRepository extends JpaRepository<CropRateMaster, Long>, JpaSpecificationExecutor<CropRateMaster> {}
