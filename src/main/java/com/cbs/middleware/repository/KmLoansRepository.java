package com.cbs.middleware.repository;

import com.cbs.middleware.domain.KmLoans;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the KmLoans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KmLoansRepository extends JpaRepository<KmLoans, Long>, JpaSpecificationExecutor<KmLoans> {
    List<KmLoans> findByKmDetails_IdEquals(Long id);
    @Query(value = "select * from km_loans where km_details_id=:id ", nativeQuery = true)
    List <KmLoans>findKmLoansByKmDetailsId(@Param("id")Long id);

}
