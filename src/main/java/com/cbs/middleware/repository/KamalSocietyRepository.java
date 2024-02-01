package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KamalSociety;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KamalSociety entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KamalSocietyRepository extends JpaRepository<KamalSociety, Long>, JpaSpecificationExecutor<KamalSociety> {}
