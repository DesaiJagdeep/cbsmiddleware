package com.cbs.middleware.service;

import com.cbs.middleware.domain.KmDetails;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KmDetails}.
 */
public interface KmDetailsService {
    /**
     * Save a kmDetails.
     *
     * @param kmDetails the entity to save.
     * @return the persisted entity.
     */
    KmDetails save(KmDetails kmDetails);

    /**
     * Updates a kmDetails.
     *
     * @param kmDetails the entity to update.
     * @return the persisted entity.
     */
    KmDetails update(KmDetails kmDetails);

    /**
     * Partially updates a kmDetails.
     *
     * @param kmDetails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KmDetails> partialUpdate(KmDetails kmDetails);

    /**
     * Get all the kmDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KmDetails> findAll(Pageable pageable);

    /**
     * Get the "id" kmDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KmDetails> findOne(Long id);

    /**
     * Delete the "id" kmDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
