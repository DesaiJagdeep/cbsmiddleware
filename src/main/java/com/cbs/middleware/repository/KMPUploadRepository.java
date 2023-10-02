package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KMPUpload;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KMPUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KMPUploadRepository extends JpaRepository<KMPUpload, Long> {
    boolean existsByFileName(String originalFilename);
}
