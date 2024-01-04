package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KmDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmDetailsRepository extends JpaRepository<KmDetails, Long>, JpaSpecificationExecutor<KmDetails> {}
