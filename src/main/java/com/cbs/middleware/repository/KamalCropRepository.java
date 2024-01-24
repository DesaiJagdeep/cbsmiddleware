package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KamalCrop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KamalCrop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KamalCropRepository extends JpaRepository<KamalCrop, Long>, JpaSpecificationExecutor<KamalCrop> {}
