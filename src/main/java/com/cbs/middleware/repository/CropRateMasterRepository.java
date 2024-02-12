package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CropRateMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the CropRateMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CropRateMasterRepository extends JpaRepository<CropRateMaster, Long>, JpaSpecificationExecutor<CropRateMaster> {
    @Query(value = " select * from crop_rate_master where crop_master_id=:cropMasterId and financial_year=:financialYear ",nativeQuery=true)
    Optional<CropRateMaster> findCropRateByCropAndFinancialYear(@Param("cropMasterId") Long cropMasterId, @Param("financialYear") String financialYear);
}
