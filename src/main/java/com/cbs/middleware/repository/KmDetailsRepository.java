package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the KmDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmDetailsRepository extends JpaRepository<KmDetails, Long>, JpaSpecificationExecutor<KmDetails> {
    List<KmDetails> findByKmDateEquals(Instant kmDate);

    //This query should have pacs_number too ,as we haven't added pacs_number in kmDetails, for time being
    @Query(value = "select * from km_details where km_date=:kmDate and financial_year=:financialYear", nativeQuery = true)
    List<KmDetails> findByKmDateAndFinancialYear(@Param("kmDate") Instant kmDate, @Param("financialYear") String financialYear);

}
