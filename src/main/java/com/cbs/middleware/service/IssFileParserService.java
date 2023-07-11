package com.cbs.middleware.service;

import com.cbs.middleware.domain.IssFileParser;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link IssFileParser}.
 */
public interface IssFileParserService {
    /**
     * Save a issFileParser.
     *
     * @param issFileParser the entity to save.
     * @return the persisted entity.
     */
    IssFileParser save(IssFileParser issFileParser);

    /**
     * Updates a issFileParser.
     *
     * @param issFileParser the entity to update.
     * @return the persisted entity.
     */
    IssFileParser update(IssFileParser issFileParser);

    /**
     * Partially updates a issFileParser.
     *
     * @param issFileParser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IssFileParser> partialUpdate(IssFileParser issFileParser);

    /**
     * Get all the issFileParsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IssFileParser> findAll(Pageable pageable);

    /**
     * Get all the issFileParsers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IssFileParser> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" issFileParser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IssFileParser> findOne(Long id);

    /**
     * Delete the "id" issFileParser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
