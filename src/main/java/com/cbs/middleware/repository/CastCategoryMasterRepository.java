package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CastCategoryMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CastCategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CastCategoryMasterRepository extends JpaRepository<CastCategoryMaster, Long> {
    List<CastCategoryMaster> findByCastCategoryNameIsContaining(String castCategoryName);
}
