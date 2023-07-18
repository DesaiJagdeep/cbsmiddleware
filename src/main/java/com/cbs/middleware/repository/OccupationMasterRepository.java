package com.cbs.middleware.repository;

import com.cbs.middleware.domain.OccupationMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OccupationMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OccupationMasterRepository extends JpaRepository<OccupationMaster, Long> {
    List<OccupationMaster> findByOccupationNameIsContaining(String occupationName);
}
