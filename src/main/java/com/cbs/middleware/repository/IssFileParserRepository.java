package com.cbs.middleware.repository;

import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IssFileParser entity.
 */
@Repository
public interface IssFileParserRepository extends JpaRepository<IssFileParser, Long>, JpaSpecificationExecutor<IssFileParser> {
    default Optional<IssFileParser> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<IssFileParser> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<IssFileParser> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct issFileParser from IssFileParser issFileParser left join fetch issFileParser.issPortalFile",
        countQuery = "select count(distinct issFileParser) from IssFileParser issFileParser"
    )
    Page<IssFileParser> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct issFileParser from IssFileParser issFileParser left join fetch issFileParser.issPortalFile")
    List<IssFileParser> findAllWithToOneRelationships();

    @Query("select issFileParser from IssFileParser issFileParser left join fetch issFileParser.issPortalFile where issFileParser.id =:id")
    Optional<IssFileParser> findOneWithToOneRelationships(@Param("id") Long id);

    List<IssFileParser> findAllByFinancialYear(String string);

    List<IssFileParser> findAllByIssPortalFile(IssPortalFile issPortalFile);
}
