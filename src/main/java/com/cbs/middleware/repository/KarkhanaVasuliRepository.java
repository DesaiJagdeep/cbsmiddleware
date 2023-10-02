package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KarkhanaVasuli;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KarkhanaVasuli entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KarkhanaVasuliRepository extends JpaRepository<KarkhanaVasuli, Long>, JpaSpecificationExecutor<KarkhanaVasuli> {}
