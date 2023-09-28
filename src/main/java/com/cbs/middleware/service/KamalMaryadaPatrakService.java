package com.cbs.middleware.service;

import com.cbs.middleware.domain.KamalMaryadaPatrak;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KamalMaryadaPatrak}.
 */
public interface KamalMaryadaPatrakService {
    /**
     * Save a kamalMaryadaPatrak.
     *
     * @param kamalMaryadaPatrak the entity to save.
     * @return the persisted entity.
     */
    KamalMaryadaPatrak save(KamalMaryadaPatrak kamalMaryadaPatrak);

    /**
     * Updates a kamalMaryadaPatrak.
     *
     * @param kamalMaryadaPatrak the entity to update.
     * @return the persisted entity.
     */
    KamalMaryadaPatrak update(KamalMaryadaPatrak kamalMaryadaPatrak);

    /**
     * Partially updates a kamalMaryadaPatrak.
     *
     * @param kamalMaryadaPatrak the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KamalMaryadaPatrak> partialUpdate(KamalMaryadaPatrak kamalMaryadaPatrak);

    /**
     * Get all the kamalMaryadaPatraks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KamalMaryadaPatrak> findAll(Pageable pageable);

    /**
     * Get the "id" kamalMaryadaPatrak.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KamalMaryadaPatrak> findOne(Long id);

    /**
     * Delete the "id" kamalMaryadaPatrak.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
