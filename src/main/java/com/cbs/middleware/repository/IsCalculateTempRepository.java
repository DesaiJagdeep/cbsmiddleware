package com.cbs.middleware.repository;

import com.cbs.middleware.domain.IsCalculateTemp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IsCalculateTemp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IsCalculateTempRepository extends JpaRepository<IsCalculateTemp, Long>, JpaSpecificationExecutor<IsCalculateTemp> {}
