package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KamalSociety;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the KamalSociety entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KamalSocietyRepository extends JpaRepository<KamalSociety, Long>, JpaSpecificationExecutor<KamalSociety> {
    @Query(value = "select sum(member_farmer) from kamal_society where pacs_number=:pacsNumber and financial_year=:financialYear ",nativeQuery = true)
    int sumOfMemberFarmer(@Param("pacsNumber") String pacsNumber,@Param("financialYear") String financialYear);
    @Query(value = "select * from kamal_society where financial_year=:fy and km_date=:kmDateInstant and  km_from_date=:kmFromDateInstant and km_to_date=:kmToDateInstant  order by branch_id",nativeQuery = true)
    List<KamalSociety> findByFyAndKmDateAndKmFromToKmToDate(@Param("fy") String fy, @Param("kmDateInstant") Instant kmDateInstant, @Param("kmFromDateInstant")Instant kmFromDateInstant, @Param("kmToDateInstant")Instant kmToDateInstant);

    @Query(value = "select * from kamal_society where financial_year=:financialYear and pacs_number=:pacsNumber and km_date=:kmDate ",nativeQuery = true)
    Optional<KamalSociety> findByFyPacsNumberKmDate(@Param("financialYear") String financialYear,@Param("pacsNumber") String pacsNumber, @Param("kmDate")Instant kmDate);



}
