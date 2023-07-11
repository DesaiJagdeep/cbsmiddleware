package com.cbs.middleware.repository;

import com.cbs.middleware.domain.SeasonMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SeasonMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonMasterRepository extends JpaRepository<SeasonMaster, Long> {}
