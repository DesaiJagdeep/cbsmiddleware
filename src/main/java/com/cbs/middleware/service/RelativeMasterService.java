package com.cbs.middleware.service;

import com.cbs.middleware.domain.RelativeMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RelativeMaster}.
 */
public interface RelativeMasterService {
    /**
     * Save a relativeMaster.
     *
     * @param relativeMaster the entity to save.
     * @return the persisted entity.
     */
    RelativeMaster save(RelativeMaster relativeMaster);

    /**
     * Updates a relativeMaster.
     *
     * @param relativeMaster the entity to update.
     * @return the persisted entity.
     */
    RelativeMaster update(RelativeMaster relativeMaster);

    /**
     * Partially updates a relativeMaster.
     *
     * @param relativeMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RelativeMaster> partialUpdate(RelativeMaster relativeMaster);

    /**
     * Get all the relativeMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RelativeMaster> findAll(Pageable pageable);

    /**
     * Get the "id" relativeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RelativeMaster> findOne(Long id);

    /**
     * Delete the "id" relativeMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
