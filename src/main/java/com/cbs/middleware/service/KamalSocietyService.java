package com.cbs.middleware.service;

import com.cbs.middleware.domain.KamalSociety;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KamalSociety}.
 */
public interface KamalSocietyService {
    /**
     * Save a kamalSociety.
     *
     * @param kamalSociety the entity to save.
     * @return the persisted entity.
     */
    KamalSociety save(KamalSociety kamalSociety);

    /**
     * Updates a kamalSociety.
     *
     * @param kamalSociety the entity to update.
     * @return the persisted entity.
     */
    KamalSociety update(KamalSociety kamalSociety);

    /**
     * Partially updates a kamalSociety.
     *
     * @param kamalSociety the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KamalSociety> partialUpdate(KamalSociety kamalSociety);

    /**
     * Get all the kamalSocieties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KamalSociety> findAll(Pageable pageable);

    /**
     * Get the "id" kamalSociety.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KamalSociety> findOne(Long id);

    /**
     * Delete the "id" kamalSociety.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
