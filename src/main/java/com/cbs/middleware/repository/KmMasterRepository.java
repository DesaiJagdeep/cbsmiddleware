package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KmMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmMasterRepository extends JpaRepository<KmMaster, Long>, JpaSpecificationExecutor<KmMaster> {}
