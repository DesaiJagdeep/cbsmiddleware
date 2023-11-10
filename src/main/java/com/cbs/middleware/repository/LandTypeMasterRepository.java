package com.cbs.middleware.repository;

import com.cbs.middleware.domain.LandTypeMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LandTypeMaster entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface LandTypeMasterRepository extends JpaRepository<LandTypeMaster, Long> {
    List<LandTypeMaster> findByLandTypeIsContaining(String landType);
}
