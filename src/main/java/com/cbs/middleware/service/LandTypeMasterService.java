package com.cbs.middleware.service;

import com.cbs.middleware.domain.LandTypeMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link LandTypeMaster}.
 */
public interface LandTypeMasterService {
    /**
     * Save a landTypeMaster.
     *
     * @param landTypeMaster the entity to save.
     * @return the persisted entity.
     */
    LandTypeMaster save(LandTypeMaster landTypeMaster);

    /**
     * Updates a landTypeMaster.
     *
     * @param landTypeMaster the entity to update.
     * @return the persisted entity.
     */
    LandTypeMaster update(LandTypeMaster landTypeMaster);

    /**
     * Partially updates a landTypeMaster.
     *
     * @param landTypeMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LandTypeMaster> partialUpdate(LandTypeMaster landTypeMaster);

    /**
     * Get all the landTypeMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LandTypeMaster> findAll(Pageable pageable);

    /**
     * Get the "id" landTypeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LandTypeMaster> findOne(Long id);

    /**
     * Delete the "id" landTypeMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
