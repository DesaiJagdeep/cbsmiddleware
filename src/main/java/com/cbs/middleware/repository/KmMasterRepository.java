package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the KmMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmMasterRepository extends JpaRepository<KmMaster, Long>, JpaSpecificationExecutor<KmMaster> {
    KmMaster findByIdEquals(Long id);

    @Query(value = "select * from km_master where id=:kmMasterId ",nativeQuery = true)
    KmMaster findOneById(@Param("kmMasterId") Long kmMasterId);
/*    @Query(value = "select * from km_details where km_date ",nativeQuery = true)
    List<KmMaster> findByKmDateInKmDetails(String kmDate);*/



}
