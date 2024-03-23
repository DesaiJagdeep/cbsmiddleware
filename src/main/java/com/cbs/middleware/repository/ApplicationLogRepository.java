package com.cbs.middleware.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.cbs.middleware.domain.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.IssFileParser;

/**
 * Spring Data JPA repository for the ApplicationLog entity.
 */
@Repository
public interface ApplicationLogRepository extends JpaRepository<ApplicationLog, Long>, JpaSpecificationExecutor<ApplicationLog> {
    default Optional<ApplicationLog> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ApplicationLog> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ApplicationLog> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct applicationLog from ApplicationLog applicationLog left join fetch applicationLog.issFileParser",
        countQuery = "select count(distinct applicationLog) from ApplicationLog applicationLog"
    )
    Page<ApplicationLog> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct applicationLog from ApplicationLog applicationLog left join fetch applicationLog.issFileParser")
    List<ApplicationLog> findAllWithToOneRelationships();

    @Query(
        "select applicationLog from ApplicationLog applicationLog left join fetch applicationLog.issFileParser where applicationLog.id =:id"
    )
    Optional<ApplicationLog> findOneWithToOneRelationships(@Param("id") Long id);

    Set<ApplicationLog> findAllByIssFileParser(IssFileParser issPortalFile);

    Optional<ApplicationLog> findOneByIssFileParser(IssFileParser issFileParser);

    List<ApplicationLog> findAllByIssPortalIdAndStatus(Long iPId, String status);

    List<ApplicationLog> findAllByIssPortalIdAndErrorTypeAndStatus(Long issPortalFileId, String validationerror, String error);

    List<ApplicationLog> findAllByIssPortalId(Long id);

    @Query(value = "select count(*) from application_log where iss_portal_id=:id and error_type='KCC Error' and status='ERROR' ",nativeQuery = true)
    Long countByKccError(@Param("id") Long id);
    @Query(value = "delete from application_log where iss_file_parser_id=:id ",nativeQuery = true)
    void deleteByIssFileParser(@Param("id") Long id);

    @Query(value = "SELECT * FROM application_log WHERE error_type = 'KCC Error' AND status= 'Error' AND error_message LIKE 'This accountNumber is already being processed by batch%' AND iss_portal_id =:iss_portal_id", nativeQuery = true)
    List<ApplicationLog> findAllByStatusError(@Param("iss_portal_id") Long iss_portal_id);


    @Query(value = "SELECT * FROM application_log WHERE error_type = 'KCC Error' AND status= 'Error' AND error_message LIKE 'Duplicate farmer and account details. %%' AND iss_portal_id =:iss_portal_id", nativeQuery = true)
    List<ApplicationLog> findAllByErrorDuplicateFarmerDetails(@Param("iss_portal_id") Long iss_portal_id);

    @Query(value = "SELECT count(id) FROM application_log WHERE  iss_portal_id=:id AND error_type = 'Validation Error' AND status= 'ERROR' ", nativeQuery = true)
    Long countByValidationError(@Param("id") Long id);
    @Query(value = "select * from iss_file_parser where id=:id ", nativeQuery = true)
    List<IssFileParser> findByIssFileParserId(@Param("id") Long id);


    @Query(value = "select * from application_log where (error_message like 'message%' or error_message like 'batchId number%') and error_type='KCC Error' and status='ERROR' ", nativeQuery = true)
    List<ApplicationLog> findByErrorMessageAndStatus(@Param("finalQuery")String finalQuery);

    Optional<ApplicationLog> findLogByIssFileParserId(@Param("id")Long id);

}
