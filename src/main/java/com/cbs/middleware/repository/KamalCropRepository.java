package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KamalCrop;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the KamalCrop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KamalCropRepository extends JpaRepository<KamalCrop, Long>, JpaSpecificationExecutor<KamalCrop> {
   @Query(value = " select * from kamal_crop where farmer_type_master_id=3 and season_master_id=5 and financial_year=:financialYear and pacs_number=:pacsNumber and km_date=:kmDate ",nativeQuery = true)
    List<KamalCrop> findBySugarcaneMarginal(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber,@Param("kmDate") Instant kmDate);
    @Query(value = " select * from kamal_crop where farmer_type_master_id=1 and season_master_id=5 and financial_year=:financialYear and pacs_number=:pacsNumber and km_date=:kmDate ",nativeQuery = true)
    List<KamalCrop> findBySugarcaneSmall(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber,@Param("kmDate") Instant kmDate);
    @Query(value = " select * from kamal_crop where farmer_type_master_id=2 and season_master_id=5 and financial_year=:financialYear and pacs_number=:pacsNumber and km_date=:kmDate ",nativeQuery = true)
    List<KamalCrop> findBySugarcaneOther(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber,@Param("kmDate") Instant kmDate);
    @Query(value = " select * from kamal_crop where pacs_number=:pacsNumber and financial_year=:financialYear and km_date=:kmDate and farmer_type_master_id=3 and (season_master_id=2 or season_master_id=6 ) ",nativeQuery = true)
    List<KamalCrop> findByKharipMarginal(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber,@Param("kmDate") Instant kmDate);
    @Query(value = " select * from kamal_crop where farmer_type_master_id=1  and financial_year=:financialYear and pacs_number=:pacsNumber and km_date=:kmDate and (season_master_id=2 or season_master_id=6 ) ",nativeQuery = true)
    List<KamalCrop> findByKharipSmall(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber,@Param("kmDate") Instant kmDate);
    @Query(value = " select * from kamal_crop where farmer_type_master_id=2  and financial_year=:financialYear and pacs_number=:pacsNumber and km_date=:kmDate and (season_master_id=2 or season_master_id=6 ) ",nativeQuery = true)
    List<KamalCrop> findByKharipOther(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber,@Param("kmDate") Instant kmDate);
    @Query(value = " select * from kamal_crop where farmer_type_master_id=3  and financial_year=:financialYear and pacs_number=:pacsNumber and km_date=:kmDate and (season_master_id=2 or season_master_id=6 )",nativeQuery = true)
    List<KamalCrop> findByRabbiMarginal(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber,@Param("kmDate") Instant kmDate);
    @Query(value = " select * from kamal_crop where farmer_type_master_id=1  and financial_year=:financialYear and pacs_number=:pacsNumber and km_date=:kmDate and (season_master_id=2 or season_master_id=6 )",nativeQuery = true)
    List<KamalCrop> findByRabbiSmall(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber,@Param("kmDate") Instant kmDate);
    @Query(value = " select * from kamal_crop where farmer_type_master_id=2  and financial_year=:financialYear and pacs_number=:pacsNumber and km_date=:kmDate and (season_master_id=2 or season_master_id=6 )",nativeQuery = true)
    List<KamalCrop> findByRabbiOther(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber,@Param("kmDate") Instant kmDate);
}
