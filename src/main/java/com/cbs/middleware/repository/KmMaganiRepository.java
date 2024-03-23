package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmMagani;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KmMagani entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmMaganiRepository extends JpaRepository<KmMagani, Long>, JpaSpecificationExecutor<KmMagani> {}
