package com.cbs.middleware.repository;

import com.cbs.middleware.domain.CropMaster;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CropMaster entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface CropMasterRepository extends JpaRepository<CropMaster, Long> {
    boolean existsByCropName(String cropName);

    List<CropMaster> findByCropNameIsContaining(String cropName);

    String findCropCodeByCropNameIsContaining(String cropName);

    boolean existsByCropCode(String kccIssCropCode);
}
