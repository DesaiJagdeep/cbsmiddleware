package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the KmDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmDetailsRepository extends JpaRepository<KmDetails, Long>, JpaSpecificationExecutor<KmDetails> {
    List<KmDetails> findByKmDateEquals(Instant kmDate);

}
