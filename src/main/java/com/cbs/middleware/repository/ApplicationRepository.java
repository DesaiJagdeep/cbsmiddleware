package com.cbs.middleware.repository;

import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.IssFileParser;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.sun.mail.imap.protocol.ID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Application entity.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
    default Optional<Application> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Application> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Application> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct application from Application application left join fetch application.issFileParser",
        countQuery = "select count(distinct application) from Application application"
    )
    Page<Application> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct application from Application application left join fetch application.issFileParser")
    List<Application> findAllWithToOneRelationships();

    @Query("select application from Application application left join fetch application.issFileParser where application.id =:id")
    Optional<Application> findOneWithToOneRelationships(@Param("id") Long id);

    List<Application> findAllByIssFilePortalId(Long id);

    // @Query("select application from Application application where
    // application.batchId is null and application.applicationStatus=2 and
    // application.issFilePortalId =:issFilePortalId")
    // List<Application>
    // findAllByBatchIdAndApplicationStatusAndIssFilePortalId(@Param("issFilePortalId")
    // Long issFilePortalId);

    List<Application> findAllByBatchIdAndApplicationStatusAndIssFilePortalId(Object nullValue, Integer errorStatus, Long issFilePortalId);

    Application findOneByUniqueId(String uniqueId);

    @Query(value = "SELECT * FROM application_transaction WHERE iss_file_parser_id=:iss_file_parser_id and application_status=1", nativeQuery = true)
Application findApplicatonsByParserId(@Param("iss_file_parser_id") Long iss_file_parser_id) ;

    @Query("select application.issFilePortalId from Application application where application.batchId =:batchId")
    List<Long> findIssFilePortalIdByBatchId(@Param("batchId") String batchId);


    @Query(
        "select count(*) from Application application where application.batchId is null and application.issFilePortalId =:issFilePortalId"
    )
    Long countByIssFilePortalIdAndBatchIdNull(@Param("issFilePortalId") Long issFilePortalId);

    @Query(
        "select count(*) from Application application where application.batchId is not null and application.issFilePortalId =:issFilePortalId"
    )
    Long countByIssFilePortalIdAndBatchIdNotNull(@Param("issFilePortalId") Long issFilePortalId);

    @Query("select count(*) from Application application where application.issFilePortalId =:issFilePortalId")
    Integer countByIssFilePortalId(@Param("issFilePortalId") Long issFilePortalId);

    Optional<Application> findOneByIssFileParser(IssFileParser issFileParser);

    @Query(
        "select count(*) from Application application where application.batchId is not null and application.financialYear =:financialYear"
    )
    Long countByFinancialYearAndBatchIdNotNull(@Param("financialYear") String financialYear);

    @Query(
        "select count(*) from Application application where application.financialYear =:financialYear and application.applicationStatus =:applicationStatus"
    )
    Long countByFinancialYearAndApplicationStatus(
        @Param("financialYear") String financialYear,
        @Param("applicationStatus") Integer applicationStatus
    );

    @Query(
        "select count(*) from Application application where application.issFilePortalId =:issFilePortalId and application.applicationStatus =:applicationStatus"
    )
    Long countByIssFilePortalIdAndApplicationStatus(
        @Param("issFilePortalId") Long issFilePortalId,
        @Param("applicationStatus") Integer applicationStatus
    );


    Page<Application> findAllByPacksCode(Long pacsCode, Pageable pageable);

    Page<Application> findAllBySchemeWiseBranchCode(Long schemeWiseBranchCode, Pageable pageable);

    @Query(value = "SELECT * FROM application_transaction WHERE iss_file_parser_id=:IssFileParserId", nativeQuery = true)
    List<Application> findRejectedApplicatonsByParserId(@Param("IssFileParserId") Long IssFileParserId) ;

    @Transactional

    void deleteByIssFileParser(IssFileParser issFileParser);

    List<Application> findAllByUniqueId(String uniqueId);

    @Query(value = "delete from application_transaction where iss_file_parser_id=:id ", nativeQuery = true)
    void deleteByIssFileParserId(@Param("id") Long id);
    @Query(value = "select * from application_transaction where financial_year is null; ", nativeQuery = true)
    List<Application> findByFianacialYearNull();

//This account number is already being processed by...
    @Query(value = "SELECT DISTINCT iss_file_portal_id FROM application_transaction WHERE kcc_status= 0 AND iss_file_parser_id not IN (select iss_file_parser_id from retry_batch_transaction_details rbtd where api_type = 1) AND application_errors LIKE 'This accountNumber is already being processed by batch%'", nativeQuery = true)
    List<Long> findDistinctByPortalId();


    //Invalid preUniqueId
    @Query(value = "SELECT DISTINCT iss_file_portal_id FROM application_transaction WHERE kcc_status= 0 and application_errors LIKE '%preUniqueid;%' and preunique_id is null", nativeQuery = true)
    List<Long> findDistinctPortalIdToUpdatePreuniqueId();

    @Query(value = "SELECT * FROM application_transaction WHERE kcc_status = 0 and application_errors LIKE '%preUniqueid;%' and iss_file_portal_id=:iss_portal_id and preunique_id is null", nativeQuery = true)
    List<Application> findApplicationsToUpdatePreuniqueId(@Param("iss_portal_id") Long iss_portal_id);


    @Query(value = "SELECT DISTINCT iss_file_portal_id FROM application_transaction WHERE kcc_status= 0 and application_errors LIKE '%preUniqueid;%' and preunique_id is not null AND iss_file_parser_id not IN (select iss_file_parser_id from retry_batch_transaction_details rbtd where api_type = 2)", nativeQuery = true)
    List<Long> findDistinctPortalIdToSubmitBatch();

    @Query(value = "SELECT * FROM application_transaction WHERE kcc_status = 0 and application_errors LIKE '%preUniqueid;%' and iss_file_portal_id=:iss_portal_id and preunique_id is not null AND iss_file_parser_id not IN (select iss_file_parser_id from retry_batch_transaction_details rbtd where api_type = 2)", nativeQuery = true)
    List<Application> findApplicationsToSubmitBatchOfInvalidPreuniqueIdErroApp(@Param("iss_portal_id") Long iss_portal_id);


    //Duplicate Farmer and account details
    @Query(value = "SELECT DISTINCT iss_file_portal_id FROM application_transaction WHERE kcc_status= 0  AND application_errors LIKE 'Duplicate farmer and account details. %' AND iss_file_parser_id not IN (select iss_file_parser_id from retry_batch_transaction_details rbtd where api_type = 3)", nativeQuery = true)
    List<Long> findDistinctPortalIdDuplicateFarmer();




}
