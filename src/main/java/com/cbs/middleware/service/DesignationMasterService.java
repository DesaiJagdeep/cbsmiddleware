package com.cbs.middleware.service;

import com.cbs.middleware.domain.DesignationMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DesignationMaster}.
 */
public interface DesignationMasterService {
    /**
     * Save a designationMaster.
     *
     * @param designationMaster the entity to save.
     * @return the persisted entity.
     */
    DesignationMaster save(DesignationMaster designationMaster);

    /**
     * Updates a designationMaster.
     *
     * @param designationMaster the entity to update.
     * @return the persisted entity.
     */
    DesignationMaster update(DesignationMaster designationMaster);

    /**
     * Partially updates a designationMaster.
     *
     * @param designationMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DesignationMaster> partialUpdate(DesignationMaster designationMaster);

    /**
     * Get all the designationMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DesignationMaster> findAll(Pageable pageable);

    /**
     * Get the "id" designationMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DesignationMaster> findOne(Long id);

    /**
     * Delete the "id" designationMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
