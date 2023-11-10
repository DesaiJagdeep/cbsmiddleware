package com.cbs.middleware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cbs.middleware.domain.CourtCaseSettingFile;

/**
 * Spring Data JPA repository for the CourtCaseSetting entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface CourtCaseSettingFileRepository extends JpaRepository<CourtCaseSettingFile, Long>, JpaSpecificationExecutor<CourtCaseSettingFile> {
    boolean existsByFileName(String originalFilename);

}
