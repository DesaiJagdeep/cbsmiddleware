package com.cbs.middleware.repository;

import com.cbs.middleware.domain.RelativeMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RelativeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelativeMasterRepository extends JpaRepository<RelativeMaster, Long> {}
