package com.cbs.middleware.repository;

import com.cbs.middleware.domain.FarmerTypeMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FarmerTypeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmerTypeMasterRepository extends JpaRepository<FarmerTypeMaster, Long> {}
