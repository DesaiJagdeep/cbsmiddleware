package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.FinancialYear;
import com.cbs.middleware.repository.FinancialYearRepository;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.FinancialYear}.
 */
@RestController
@RequestMapping("/api")
public class FinancialYearResource {

    private final Logger log = LoggerFactory.getLogger(FinancialYearResource.class);

    private static final String ENTITY_NAME = "financialYear";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinancialYearRepository financialYearRepository;

    public FinancialYearResource(FinancialYearRepository financialYearRepository) {
        this.financialYearRepository = financialYearRepository;
    }

    /**
     * {@code POST  /financial-year} : Create a new financialYear.
     *
     * @param financialYear the financialYear to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new financialYear, or with status {@code 400 (Bad Request)} if the financialYear has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/financial-year")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<FinancialYear> createApplication(@RequestBody FinancialYear financialYear) throws URISyntaxException {
        log.debug("REST request to save FinancialYear : {}", financialYear);
        if (financialYear.getId() != null) {
            throw new BadRequestAlertException("A new financialYear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinancialYear result = financialYearRepository.save(financialYear);
        return ResponseEntity
            .created(new URI("/api/financial-year/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /financial-year/:id} : Updates an existing financialYear.
     *
     * @param id the id of the financialYear to save.
     * @param financialYear the financialYear to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financialYear,
     * or with status {@code 400 (Bad Request)} if the financialYear is not valid,
     * or with status {@code 500 (Internal Server Error)} if the financialYear couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/financial-year/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<FinancialYear> updateApplication(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FinancialYear financialYear
    ) throws URISyntaxException {
        log.debug("REST request to update FinancialYear : {}, {}", id, financialYear);
        if (financialYear.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, financialYear.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!financialYearRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FinancialYear result = financialYearRepository.save(financialYear);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, financialYear.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /financial-year} : get all the financial-year.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of financial-year in body.
     */
    @GetMapping("/financial-year")
    public ResponseEntity<List<FinancialYear>> getAllApplications() {
        log.debug("REST request to get Applications by criteria: {}");

        List<FinancialYear> findAll = financialYearRepository.findAll();
        return ResponseEntity.ok().body(findAll);
    }

    /**
     * {@code GET  /financial-year/:id} : get the "id" financialYear.
     *
     * @param id the id of the financialYear to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the financialYear, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/financial-year/{id}")
    public ResponseEntity<FinancialYear> getApplication(@PathVariable Long id) {
        log.debug("REST request to get FinancialYear : {}", id);
        Optional<FinancialYear> financialYear = financialYearRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(financialYear);
    }

    /**
     * {@code DELETE  /financial-year/:id} : delete the "id" financialYear.
     *
     * @param id the id of the financialYear to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/financial-year/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete FinancialYear : {}", id);
        financialYearRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
