package com.cbs.middleware.repository;

import com.cbs.middleware.domain.VillageMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VillageMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VillageMasterRepository extends JpaRepository<VillageMaster, Long>, JpaSpecificationExecutor<VillageMaster> {}
