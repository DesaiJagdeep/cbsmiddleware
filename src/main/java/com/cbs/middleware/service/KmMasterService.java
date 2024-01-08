package com.cbs.middleware.service;

import com.cbs.middleware.domain.KmMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KmMaster}.
 */
public interface KmMasterService {
    /**
     * Save a kmMaster.
     *
     * @param kmMaster the entity to save.
     * @return the persisted entity.
     */
    KmMaster save(KmMaster kmMaster);

    /**
     * Updates a kmMaster.
     *
     * @param kmMaster the entity to update.
     * @return the persisted entity.
     */
    KmMaster update(KmMaster kmMaster);

    /**
     * Partially updates a kmMaster.
     *
     * @param kmMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KmMaster> partialUpdate(KmMaster kmMaster);

    /**
     * Get all the kmMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KmMaster> findAll(Pageable pageable);

    /**
     * Get the "id" kmMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KmMaster> findOne(Long id);

    /**
     * Delete the "id" kmMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
