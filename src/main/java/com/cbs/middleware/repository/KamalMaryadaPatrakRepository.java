package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KamalMaryadaPatrak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KamalMaryadaPatrak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KamalMaryadaPatrakRepository
    extends JpaRepository<KamalMaryadaPatrak, Long>, JpaSpecificationExecutor<KamalMaryadaPatrak> {}
