package com.cbs.middleware.repository;

import com.cbs.middleware.domain.ActivityType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ActivityType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long>, JpaSpecificationExecutor<ActivityType> {}
