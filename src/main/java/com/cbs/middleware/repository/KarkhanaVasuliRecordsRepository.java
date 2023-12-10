package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KarkhanaVasuliRecords;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KarkhanaVasuliRecords entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KarkhanaVasuliRecordsRepository
    extends JpaRepository<KarkhanaVasuliRecords, Long>, JpaSpecificationExecutor<KarkhanaVasuliRecords> {}
