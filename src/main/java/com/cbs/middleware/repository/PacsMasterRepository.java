package com.cbs.middleware.repository;

import com.cbs.middleware.domain.PacsMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PacsMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacsMasterRepository extends JpaRepository<PacsMaster, Long> {}
