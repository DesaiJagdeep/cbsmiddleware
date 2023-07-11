package com.cbs.middleware.service;

import com.cbs.middleware.domain.BankMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BankMaster}.
 */
public interface BankMasterService {
    /**
     * Save a bankMaster.
     *
     * @param bankMaster the entity to save.
     * @return the persisted entity.
     */
    BankMaster save(BankMaster bankMaster);

    /**
     * Updates a bankMaster.
     *
     * @param bankMaster the entity to update.
     * @return the persisted entity.
     */
    BankMaster update(BankMaster bankMaster);

    /**
     * Partially updates a bankMaster.
     *
     * @param bankMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankMaster> partialUpdate(BankMaster bankMaster);

    /**
     * Get all the bankMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankMaster> findAll(Pageable pageable);

    /**
     * Get the "id" bankMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankMaster> findOne(Long id);

    /**
     * Delete the "id" bankMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
