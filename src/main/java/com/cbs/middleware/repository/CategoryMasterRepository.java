package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CategoryMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryMasterRepository extends JpaRepository<CategoryMaster, Long> {}
