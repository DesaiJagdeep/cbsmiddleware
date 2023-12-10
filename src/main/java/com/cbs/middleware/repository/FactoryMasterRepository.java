package com.cbs.middleware.repository;

import com.cbs.middleware.domain.FactoryMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FactoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactoryMasterRepository extends JpaRepository<FactoryMaster, Long>, JpaSpecificationExecutor<FactoryMaster> {}
