package com.cbs.middleware.repository;

import com.cbs.middleware.domain.StateMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StateMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateMasterRepository extends JpaRepository<StateMaster, Long> {}
