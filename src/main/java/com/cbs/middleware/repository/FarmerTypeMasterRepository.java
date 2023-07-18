package com.cbs.middleware.repository;

import com.cbs.middleware.domain.FarmerTypeMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FarmerTypeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmerTypeMasterRepository extends JpaRepository<FarmerTypeMaster, Long> {
    List<FarmerTypeMaster> findByFarmerTypeIsContaining(String farmerType);
}
