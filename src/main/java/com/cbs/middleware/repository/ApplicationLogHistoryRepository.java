package com.cbs.middleware.repository;

import com.cbs.middleware.domain.ApplicationLogHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApplicationLogHistory entity.
 */
@Repository
public interface ApplicationLogHistoryRepository
    extends JpaRepository<ApplicationLogHistory, Long>, JpaSpecificationExecutor<ApplicationLogHistory> {
    default Optional<ApplicationLogHistory> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ApplicationLogHistory> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ApplicationLogHistory> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct applicationLogHistory from ApplicationLogHistory applicationLogHistory left join fetch applicationLogHistory.issFileParser",
        countQuery = "select count(distinct applicationLogHistory) from ApplicationLogHistory applicationLogHistory"
    )
    Page<ApplicationLogHistory> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct applicationLogHistory from ApplicationLogHistory applicationLogHistory left join fetch applicationLogHistory.issFileParser"
    )
    List<ApplicationLogHistory> findAllWithToOneRelationships();

    @Query(
        "select applicationLogHistory from ApplicationLogHistory applicationLogHistory left join fetch applicationLogHistory.issFileParser where applicationLogHistory.id =:id"
    )
    Optional<ApplicationLogHistory> findOneWithToOneRelationships(@Param("id") Long id);
}
