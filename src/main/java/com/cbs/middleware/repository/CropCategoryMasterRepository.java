package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CropCategoryMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CropCategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CropCategoryMasterRepository extends JpaRepository<CropCategoryMaster, Long> {}
