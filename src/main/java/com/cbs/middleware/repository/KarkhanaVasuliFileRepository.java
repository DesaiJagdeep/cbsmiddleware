package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KarkhanaVasuliFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KarkhanaVasuliFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KarkhanaVasuliFileRepository
    extends JpaRepository<KarkhanaVasuliFile, Long>, JpaSpecificationExecutor<KarkhanaVasuliFile> {}
