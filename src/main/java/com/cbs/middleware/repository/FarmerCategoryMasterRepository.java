package com.cbs.middleware.repository;

import com.cbs.middleware.domain.FarmerCategoryMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FarmerCategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmerCategoryMasterRepository extends JpaRepository<FarmerCategoryMaster, Long> {
    List<FarmerCategoryMaster> findByFarmerCategoryIsContaining(String farmerCategory);
}
