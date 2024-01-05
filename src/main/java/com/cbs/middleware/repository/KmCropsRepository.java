package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmCrops;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KmCrops entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmCropsRepository extends JpaRepository<KmCrops, Long>, JpaSpecificationExecutor<KmCrops> {}
