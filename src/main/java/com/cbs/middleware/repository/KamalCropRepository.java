package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KamalCrop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the KamalCrop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KamalCropRepository extends JpaRepository<KamalCrop, Long>, JpaSpecificationExecutor<KamalCrop> {
   @Query(value = " select * from kamal_crop where farmer_type_master_id=3 and season_master_id=5 ",nativeQuery = true)
    List<KamalCrop> findBySugarcaneMarginal();
    @Query(value = " select * from kamal_crop where farmer_type_master_id=1 and season_master_id=5 ",nativeQuery = true)
    List<KamalCrop> findBySugarcaneSmall();
    @Query(value = " select * from kamal_crop where farmer_type_master_id=2 and season_master_id=5 ",nativeQuery = true)
    List<KamalCrop> findBySugarcaneOther();
    @Query(value = " select * from kamal_crop where farmer_type_master_id=3 and season_master_id=1 or season_master_id=7 ",nativeQuery = true)
    List<KamalCrop> findByKharipMarginal();
    @Query(value = " select * from kamal_crop where farmer_type_master_id=1 and season_master_id=1 or season_master_id=7 ",nativeQuery = true)
    List<KamalCrop> findByKharipSmall();
    @Query(value = " select * from kamal_crop where farmer_type_master_id=2 and season_master_id=1 or season_master_id=7 ",nativeQuery = true)
    List<KamalCrop> findByKharipOther();
    @Query(value = " select * from kamal_crop where farmer_type_master_id=3 and season_master_id=2 or season_master_id=6 ",nativeQuery = true)
    List<KamalCrop> findByRabbiMarginal();
    @Query(value = " select * from kamal_crop where farmer_type_master_id=1 and season_master_id=2 or season_master_id=6 ",nativeQuery = true)
    List<KamalCrop> findByRabbiSmall();
    @Query(value = " select * from kamal_crop where farmer_type_master_id=2 and season_master_id=2 or season_master_id=6 ",nativeQuery = true)
    List<KamalCrop> findByRabbiOther();
}
