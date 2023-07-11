package com.cbs.middleware.repository;

import com.cbs.middleware.domain.OccupationMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OccupationMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OccupationMasterRepository extends JpaRepository<OccupationMaster, Long> {}
