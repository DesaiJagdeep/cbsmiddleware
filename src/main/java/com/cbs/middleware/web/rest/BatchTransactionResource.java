package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.BatchTransaction;
import com.cbs.middleware.repository.BatchTransactionRepository;
import com.cbs.middleware.service.BatchTransactionQueryService;
import com.cbs.middleware.service.BatchTransactionService;
import com.cbs.middleware.service.criteria.BatchTransactionCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.BatchTransaction}.
 */
@RestController
@RequestMapping("/api")
public class BatchTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BatchTransactionResource.class);

    private static final String ENTITY_NAME = "batchTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatchTransactionService batchTransactionService;

    private final BatchTransactionRepository batchTransactionRepository;

    private final BatchTransactionQueryService batchTransactionQueryService;

    public BatchTransactionResource(
        BatchTransactionService batchTransactionService,
        BatchTransactionRepository batchTransactionRepository,
        BatchTransactionQueryService batchTransactionQueryService
    ) {
        this.batchTransactionService = batchTransactionService;
        this.batchTransactionRepository = batchTransactionRepository;
        this.batchTransactionQueryService = batchTransactionQueryService;
    }

    /**
     * {@code POST  /batch-transactions} : Create a new batchTransaction.
     *
     * @param batchTransaction the batchTransaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batchTransaction, or with status {@code 400 (Bad Request)} if the batchTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/batch-transactions")
    public ResponseEntity<BatchTransaction> createBatchTransaction(@RequestBody BatchTransaction batchTransaction)
        throws URISyntaxException {
        log.debug("REST request to save BatchTransaction : {}", batchTransaction);
        if (batchTransaction.getId() != null) {
            throw new BadRequestAlertException("A new batchTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BatchTransaction result = batchTransactionService.save(batchTransaction);
        return ResponseEntity
            .created(new URI("/api/batch-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /batch-transactions/:id} : Updates an existing batchTransaction.
     *
     * @param id the id of the batchTransaction to save.
     * @param batchTransaction the batchTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchTransaction,
     * or with status {@code 400 (Bad Request)} if the batchTransaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batchTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/batch-transactions/{id}")
    public ResponseEntity<BatchTransaction> updateBatchTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BatchTransaction batchTransaction
    ) throws URISyntaxException {
        log.debug("REST request to update BatchTransaction : {}, {}", id, batchTransaction);
        if (batchTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BatchTransaction result = batchTransactionService.update(batchTransaction);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batchTransaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /batch-transactions/:id} : Partial updates given fields of an existing batchTransaction, field will ignore if it is null
     *
     * @param id the id of the batchTransaction to save.
     * @param batchTransaction the batchTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchTransaction,
     * or with status {@code 400 (Bad Request)} if the batchTransaction is not valid,
     * or with status {@code 404 (Not Found)} if the batchTransaction is not found,
     * or with status {@code 500 (Internal Server Error)} if the batchTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/batch-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BatchTransaction> partialUpdateBatchTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BatchTransaction batchTransaction
    ) throws URISyntaxException {
        log.debug("REST request to partial update BatchTransaction partially : {}, {}", id, batchTransaction);
        if (batchTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BatchTransaction> result = batchTransactionService.partialUpdate(batchTransaction);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batchTransaction.getId().toString())
        );
    }

    /**
     * {@code GET  /batch-transactions} : get all the batchTransactions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batchTransactions in body.
     */
    @GetMapping("/batch-transactions")
    public ResponseEntity<List<BatchTransaction>> getAllBatchTransactions(
        BatchTransactionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BatchTransactions by criteria: {}", criteria);
        Page<BatchTransaction> page = batchTransactionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /batch-transactions/count} : count all the batchTransactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/batch-transactions/count")
    public ResponseEntity<Long> countBatchTransactions(BatchTransactionCriteria criteria) {
        log.debug("REST request to count BatchTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(batchTransactionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /batch-transactions/:id} : get the "id" batchTransaction.
     *
     * @param id the id of the batchTransaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batchTransaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/batch-transactions/{id}")
    public ResponseEntity<BatchTransaction> getBatchTransaction(@PathVariable Long id) {
        log.debug("REST request to get BatchTransaction : {}", id);
        Optional<BatchTransaction> batchTransaction = batchTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(batchTransaction);
    }

    /**
     * {@code DELETE  /batch-transactions/:id} : delete the "id" batchTransaction.
     *
     * @param id the id of the batchTransaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/batch-transactions/{id}")
    public ResponseEntity<Void> deleteBatchTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BatchTransaction : {}", id);
        batchTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
