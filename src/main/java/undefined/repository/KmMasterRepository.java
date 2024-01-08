package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KmMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmMasterRepository extends JpaRepository<KmMaster, Long>, JpaSpecificationExecutor<KmMaster> {

}
