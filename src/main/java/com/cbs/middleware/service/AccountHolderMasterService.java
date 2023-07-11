package com.cbs.middleware.service;

import com.cbs.middleware.domain.AccountHolderMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AccountHolderMaster}.
 */
public interface AccountHolderMasterService {
    /**
     * Save a accountHolderMaster.
     *
     * @param accountHolderMaster the entity to save.
     * @return the persisted entity.
     */
    AccountHolderMaster save(AccountHolderMaster accountHolderMaster);

    /**
     * Updates a accountHolderMaster.
     *
     * @param accountHolderMaster the entity to update.
     * @return the persisted entity.
     */
    AccountHolderMaster update(AccountHolderMaster accountHolderMaster);

    /**
     * Partially updates a accountHolderMaster.
     *
     * @param accountHolderMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AccountHolderMaster> partialUpdate(AccountHolderMaster accountHolderMaster);

    /**
     * Get all the accountHolderMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountHolderMaster> findAll(Pageable pageable);

    /**
     * Get the "id" accountHolderMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountHolderMaster> findOne(Long id);

    /**
     * Delete the "id" accountHolderMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
