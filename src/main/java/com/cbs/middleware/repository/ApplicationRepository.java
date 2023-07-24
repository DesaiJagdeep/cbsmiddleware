package com.cbs.middleware.repository;

import com.cbs.middleware.domain.Application;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
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

    // @Query("select application from Application application  where application.batchId is null and  application.applicationStatus=0 and application.issFilePortalId =:issFilePortalId")
    //List<Application> findAllByBatchIdAndApplicationStatusAndIssFilePortalId(@Param("issFilePortalId") Long issFilePortalId);

    List<Application> findAllByBatchIdAndApplicationStatusAndIssFilePortalId(Object nullValue, Integer errorStatus, Long issFilePortalId);

    Application findOneByUniqueId(String uniqueId);

    @Query("select application.issFilePortalId from Application application where application.batchId =:batchId")
    List<Long> findIssFilePortalIdByBatchId(@Param("batchId") String batchId);
}
