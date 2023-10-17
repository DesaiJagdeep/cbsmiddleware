package com.cbs.middleware.repository;

import com.cbs.middleware.domain.TalukaMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TalukaMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TalukaMasterRepository extends JpaRepository<TalukaMaster, Long> {
    boolean existsByTalukaName(String talukaName);

    TalukaMaster findOneByTalukaName(String talukaName);
}
