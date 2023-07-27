package com.cbs.middleware.repository;

import com.cbs.middleware.domain.IssChildPortalFile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IssPortalFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssChildPortalFileRepository
    extends JpaRepository<IssChildPortalFile, Long>, JpaSpecificationExecutor<IssChildPortalFile> {
    Optional<IssChildPortalFile> findByUniqueName(String uniqueId);

    boolean existsByFileName(String originalFilename);

    boolean existsByFileNameAndFinancialYear(String originalFilename, String financialYear);
}
