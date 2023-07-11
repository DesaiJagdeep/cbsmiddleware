package com.cbs.middleware.repository;

import com.cbs.middleware.domain.IssPortalFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IssPortalFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssPortalFileRepository extends JpaRepository<IssPortalFile, Long>, JpaSpecificationExecutor<IssPortalFile> {}
