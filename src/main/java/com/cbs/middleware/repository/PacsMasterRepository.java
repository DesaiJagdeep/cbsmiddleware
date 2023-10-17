package com.cbs.middleware.repository;

import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.PacsMaster;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PacsMaster entity.
 */
@Repository
public interface PacsMasterRepository extends JpaRepository<PacsMaster, Long> {
    default Optional<PacsMaster> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PacsMaster> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PacsMaster> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct pacsMaster from PacsMaster pacsMaster left join fetch pacsMaster.bankBranchMaster",
        countQuery = "select count(distinct pacsMaster) from PacsMaster pacsMaster"
    )
    Page<PacsMaster> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct pacsMaster from PacsMaster pacsMaster left join fetch pacsMaster.bankBranchMaster")
    List<PacsMaster> findAllWithToOneRelationships();

    @Query("select pacsMaster from PacsMaster pacsMaster left join fetch pacsMaster.bankBranchMaster where pacsMaster.id =:id")
    Optional<PacsMaster> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select pacsMaster.pacsNumber from PacsMaster pacsMaster where pacsMaster.pacsName =:pacsName")
    String findPacsNumberByPacsName(@Param("pacsName") String pacsName);

    List<PacsMaster> findAllByBankBranchMaster(BankBranchMaster bankBranchMaster);

    @Query("select pacsMaster from PacsMaster pacsMaster where pacsMaster.bankBranchMaster.branchName =:branchName")
    List<PacsMaster> findAllByBranchName(@Param("branchName") String branchName);

    boolean existsByPacsName(String pacsName);

    boolean existsByPacsNumber(String pacsName);
}
