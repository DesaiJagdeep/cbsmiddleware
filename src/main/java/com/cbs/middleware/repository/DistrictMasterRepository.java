package com.cbs.middleware.repository;

import com.cbs.middleware.domain.DistrictMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DistrictMaster entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface DistrictMasterRepository extends JpaRepository<DistrictMaster, Long> {}
