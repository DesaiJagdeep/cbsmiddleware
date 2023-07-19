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

    List<Application> findAllByBatchIdAndApplicationStatus(Object object, long l);

    @Query("SELECT DISTINCT a.financialYear FROM Application a")
    Set<String> findUniqueFinancialYear();

    List<Application> findAllByBatchIdAndApplicationStatusAndFinancialYear(Object object, long l, String finantialYear);
}
