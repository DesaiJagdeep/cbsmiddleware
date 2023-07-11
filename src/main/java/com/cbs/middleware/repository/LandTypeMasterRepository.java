package com.cbs.middleware.repository;

import com.cbs.middleware.domain.LandTypeMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LandTypeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LandTypeMasterRepository extends JpaRepository<LandTypeMaster, Long> {}
