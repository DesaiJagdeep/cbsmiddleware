package com.cbs.middleware.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.domain.CourtCaseFile;

/**
 * Spring Data JPA repository for the CourtCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourtCaseFileRepository extends JpaRepository<CourtCaseFile, Long>, JpaSpecificationExecutor<CourtCaseFile> {
    boolean existsByFileName(String originalFilename);
}
