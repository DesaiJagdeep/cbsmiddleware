package com.cbs.middleware.service;

import com.cbs.middleware.domain.VillageMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cbs.middleware.domain.VillageMaster}.
 */
public interface VillageMasterService {
    /**
     * Save a villageMaster.
     *
     * @param villageMaster the entity to save.
     * @return the persisted entity.
     */
    VillageMaster save(VillageMaster villageMaster);

    /**
     * Updates a villageMaster.
     *
     * @param villageMaster the entity to update.
     * @return the persisted entity.
     */
    VillageMaster update(VillageMaster villageMaster);

    /**
     * Partially updates a villageMaster.
     *
     * @param villageMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VillageMaster> partialUpdate(VillageMaster villageMaster);

    /**
     * Get all the villageMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VillageMaster> findAll(Pageable pageable);

    /**
     * Get the "id" villageMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VillageMaster> findOne(Long id);

    /**
     * Delete the "id" villageMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
