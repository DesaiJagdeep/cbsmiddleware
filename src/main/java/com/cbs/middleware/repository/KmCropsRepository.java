package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmCrops;
import com.cbs.middleware.domain.KmDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the KmCrops entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmCropsRepository extends JpaRepository<KmCrops, Long>, JpaSpecificationExecutor<KmCrops> {
    List<KmCrops> findByKmDetails_IdEquals(Long id);

    List<KmCrops> findByKmDetailsEquals(KmDetails kmDetails);

    List<KmCrops> findByHectorEquals(Double hector);






}
