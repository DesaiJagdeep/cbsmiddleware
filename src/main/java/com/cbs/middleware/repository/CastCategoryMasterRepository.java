package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CastCategoryMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CastCategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CastCategoryMasterRepository extends JpaRepository<CastCategoryMaster, Long> {}
