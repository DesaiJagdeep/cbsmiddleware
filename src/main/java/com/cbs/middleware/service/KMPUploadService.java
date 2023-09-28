package com.cbs.middleware.service;

import com.cbs.middleware.domain.KMPUpload;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KMPUpload}.
 */
public interface KMPUploadService {
    /**
     * Save a kMPUpload.
     *
     * @param kMPUpload the entity to save.
     * @return the persisted entity.
     */
    KMPUpload save(KMPUpload kMPUpload);

    /**
     * Updates a kMPUpload.
     *
     * @param kMPUpload the entity to update.
     * @return the persisted entity.
     */
    KMPUpload update(KMPUpload kMPUpload);

    /**
     * Partially updates a kMPUpload.
     *
     * @param kMPUpload the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KMPUpload> partialUpdate(KMPUpload kMPUpload);

    /**
     * Get all the kMPUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KMPUpload> findAll(Pageable pageable);

    /**
     * Get the "id" kMPUpload.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KMPUpload> findOne(Long id);

    /**
     * Delete the "id" kMPUpload.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
