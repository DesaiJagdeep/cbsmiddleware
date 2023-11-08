package com.cbs.middleware.repository;

import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.BankMaster;
import com.cbs.middleware.domain.TalukaMaster;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankBranchMaster entity.
 */
@Repository
public interface BankBranchMasterRepository extends JpaRepository<BankBranchMaster, Long> {
    default Optional<BankBranchMaster> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BankBranchMaster> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BankBranchMaster> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct bankBranchMaster from BankBranchMaster bankBranchMaster left join fetch bankBranchMaster.bankMaster",
        countQuery = "select count(distinct bankBranchMaster) from BankBranchMaster bankBranchMaster"
    )
    Page<BankBranchMaster> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct bankBranchMaster from BankBranchMaster bankBranchMaster left join fetch bankBranchMaster.bankMaster")
    List<BankBranchMaster> findAllWithToOneRelationships();

    @Query(
        "select bankBranchMaster from BankBranchMaster bankBranchMaster left join fetch bankBranchMaster.bankMaster where bankBranchMaster.id =:id"
    )
    Optional<BankBranchMaster> findOneWithToOneRelationships(@Param("id") Long id);

    List<BankBranchMaster> findAllByBankMaster(BankMaster bankMaster);

    @Query(
        "select bankBranchMaster.schemeWiseBranchCode from BankBranchMaster bankBranchMaster where bankBranchMaster.branchName =:branchName"
    )
    String findSchemeWiseBranchCodeByBranchName(@Param("branchName") String branchName);

    boolean existsByBranchName(String talukaName);

    BankBranchMaster findOneByBranchName(String talukaName);
    
    Optional<BankBranchMaster> findOneBySchemeWiseBranchCode(String schemeWiseBranchCode);

	List<BankBranchMaster> findAllByTalukaMaster(TalukaMaster talukaMaster);
}
