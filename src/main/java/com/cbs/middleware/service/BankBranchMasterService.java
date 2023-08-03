package com.cbs.middleware.service;

import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.BankMaster;
import com.cbs.middleware.domain.domainUtil.BranchForPacksList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BankBranchMaster}.
 */
public interface BankBranchMasterService {
    /**
     * Save a bankBranchMaster.
     *
     * @param bankBranchMaster the entity to save.
     * @return the persisted entity.
     */
    BankBranchMaster save(BankBranchMaster bankBranchMaster);

    /**
     * Updates a bankBranchMaster.
     *
     * @param bankBranchMaster the entity to update.
     * @return the persisted entity.
     */
    BankBranchMaster update(BankBranchMaster bankBranchMaster);

    /**
     * Partially updates a bankBranchMaster.
     *
     * @param bankBranchMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankBranchMaster> partialUpdate(BankBranchMaster bankBranchMaster);

    /**
     * Get all the bankBranchMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankBranchMaster> findAll(Pageable pageable);

    /**
     * Get all the bankBranchMasters with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankBranchMaster> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" bankBranchMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankBranchMaster> findOne(Long id);

    /**
     * Delete the "id" bankBranchMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
