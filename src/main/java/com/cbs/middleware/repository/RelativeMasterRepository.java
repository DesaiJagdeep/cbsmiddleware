package com.cbs.middleware.repository;

import com.cbs.middleware.domain.RelativeMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RelativeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelativeMasterRepository extends JpaRepository<RelativeMaster, Long> {
    List<RelativeMaster> findByRelativeNameIsContaining(String relativeName);
}
