package com.cbs.middleware.service;

import com.cbs.middleware.domain.DistrictMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DistrictMaster}.
 */
public interface DistrictMasterService {
    /**
     * Save a districtMaster.
     *
     * @param districtMaster the entity to save.
     * @return the persisted entity.
     */
    DistrictMaster save(DistrictMaster districtMaster);

    /**
     * Updates a districtMaster.
     *
     * @param districtMaster the entity to update.
     * @return the persisted entity.
     */
    DistrictMaster update(DistrictMaster districtMaster);

    /**
     * Partially updates a districtMaster.
     *
     * @param districtMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DistrictMaster> partialUpdate(DistrictMaster districtMaster);

    /**
     * Get all the districtMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DistrictMaster> findAll(Pageable pageable);

    /**
     * Get the "id" districtMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistrictMaster> findOne(Long id);

    /**
     * Delete the "id" districtMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
