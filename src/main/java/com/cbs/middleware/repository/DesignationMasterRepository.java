package com.cbs.middleware.repository;

import com.cbs.middleware.domain.DesignationMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DesignationMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationMasterRepository extends JpaRepository<DesignationMaster, Long> {}
