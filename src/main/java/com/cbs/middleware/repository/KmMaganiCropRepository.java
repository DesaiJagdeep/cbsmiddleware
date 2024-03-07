package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmMaganiCrop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KmMaganiCrop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmMaganiCropRepository extends JpaRepository<KmMaganiCrop, Long>, JpaSpecificationExecutor<KmMaganiCrop> {}
