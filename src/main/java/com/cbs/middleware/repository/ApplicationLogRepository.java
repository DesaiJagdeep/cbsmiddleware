package com.cbs.middleware.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Query(value = "SELECT * FROM application_log WHERE error_type = 'KCC ERROR' AND status= 'Error' AND error_message LIKE 'This accountNumber is already being processed by batch%' AND iss_file_parser_id IN (SELECT iss_file_parser_id FROM application_transaction  WHERE kcc_status= 0 ) ", nativeQuery = true)
    List<ApplicationLog> findAllByKCCStatus();
}
