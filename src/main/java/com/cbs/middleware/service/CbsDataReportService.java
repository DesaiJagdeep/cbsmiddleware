package com.cbs.middleware.service;

import com.cbs.middleware.domain.CbsDataReport;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CbsDataReport}.
 */
public interface CbsDataReportService {
    /**
     * Save a cbsDataReport.
     *
     * @param cbsDataReport the entity to save.
     * @return the persisted entity.
     */
    CbsDataReport save(CbsDataReport cbsDataReport);

    /**
     * Updates a cbsDataReport.
     *
     * @param cbsDataReport the entity to update.
     * @return the persisted entity.
     */
    CbsDataReport update(CbsDataReport cbsDataReport);

    /**
     * Partially updates a cbsDataReport.
     *
     * @param cbsDataReport the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbsDataReport> partialUpdate(CbsDataReport cbsDataReport);

    /**
     * Get all the cbsDataReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbsDataReport> findAll(Pageable pageable);

    /**
     * Get the "id" cbsDataReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbsDataReport> findOne(Long id);

    /**
     * Delete the "id" cbsDataReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
