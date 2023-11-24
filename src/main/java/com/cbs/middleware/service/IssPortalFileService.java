package com.cbs.middleware.service;

import com.cbs.middleware.domain.IssPortalFile;

import java.util.Optional;

import com.cbs.middleware.service.dto.IssPortalFileCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link IssPortalFile}.
 */
public interface IssPortalFileService {
    /**
     * Save a issPortalFile.
     *
     * @param issPortalFile the entity to save.
     * @return the persisted entity.
     */
    IssPortalFile save(IssPortalFile issPortalFile);

    /**
     * Updates a issPortalFile.
     *
     * @param issPortalFile the entity to update.
     * @return the persisted entity.
     */
    IssPortalFile update(IssPortalFile issPortalFile);

    /**
     * Partially updates a issPortalFile.
     *
     * @param issPortalFile the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IssPortalFile> partialUpdate(IssPortalFile issPortalFile);

    /**
     * Get all the issPortalFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IssPortalFile> findAll(Pageable pageable);

    /**
     * Get the "id" issPortalFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IssPortalFile> findOne(Long id);

    /**
     * Delete the "id" issPortalFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    IssPortalFileCountDTO findAllRecords();

}
