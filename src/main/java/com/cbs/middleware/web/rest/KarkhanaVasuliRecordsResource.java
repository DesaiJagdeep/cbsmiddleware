package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KarkhanaVasuliRecords;
import com.cbs.middleware.repository.KarkhanaVasuliRecordsRepository;
import com.cbs.middleware.service.KarkhanaVasuliRecordsQueryService;
import com.cbs.middleware.service.KarkhanaVasuliRecordsService;
import com.cbs.middleware.service.criteria.KarkhanaVasuliRecordsCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KarkhanaVasuliRecords}.
 */
@RestController
@RequestMapping("/api/karkhana-vasuli-records")
public class KarkhanaVasuliRecordsResource {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliRecordsResource.class);

    private static final String ENTITY_NAME = "karkhanaVasuliRecords";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KarkhanaVasuliRecordsService karkhanaVasuliRecordsService;

    private final KarkhanaVasuliRecordsRepository karkhanaVasuliRecordsRepository;

    private final KarkhanaVasuliRecordsQueryService karkhanaVasuliRecordsQueryService;

    public KarkhanaVasuliRecordsResource(
        KarkhanaVasuliRecordsService karkhanaVasuliRecordsService,
        KarkhanaVasuliRecordsRepository karkhanaVasuliRecordsRepository,
        KarkhanaVasuliRecordsQueryService karkhanaVasuliRecordsQueryService
    ) {
        this.karkhanaVasuliRecordsService = karkhanaVasuliRecordsService;
        this.karkhanaVasuliRecordsRepository = karkhanaVasuliRecordsRepository;
        this.karkhanaVasuliRecordsQueryService = karkhanaVasuliRecordsQueryService;
    }

    /**
     * {@code POST  /karkhana-vasuli-records} : Create a new karkhanaVasuliRecords.
     *
     * @param karkhanaVasuliRecords the karkhanaVasuliRecords to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new karkhanaVasuliRecords, or with status {@code 400 (Bad Request)} if the karkhanaVasuliRecords has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<KarkhanaVasuliRecords> createKarkhanaVasuliRecords(@RequestBody KarkhanaVasuliRecords karkhanaVasuliRecords)
        throws URISyntaxException {
        log.debug("REST request to save KarkhanaVasuliRecords : {}", karkhanaVasuliRecords);
        if (karkhanaVasuliRecords.getId() != null) {
            throw new BadRequestAlertException("A new karkhanaVasuliRecords cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KarkhanaVasuliRecords result = karkhanaVasuliRecordsService.save(karkhanaVasuliRecords);
        return ResponseEntity
            .created(new URI("/api/karkhana-vasuli-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /karkhana-vasuli-records/:id} : Updates an existing karkhanaVasuliRecords.
     *
     * @param id the id of the karkhanaVasuliRecords to save.
     * @param karkhanaVasuliRecords the karkhanaVasuliRecords to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated karkhanaVasuliRecords,
     * or with status {@code 400 (Bad Request)} if the karkhanaVasuliRecords is not valid,
     * or with status {@code 500 (Internal Server Error)} if the karkhanaVasuliRecords couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KarkhanaVasuliRecords> updateKarkhanaVasuliRecords(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KarkhanaVasuliRecords karkhanaVasuliRecords
    ) throws URISyntaxException {
        log.debug("REST request to update KarkhanaVasuliRecords : {}, {}", id, karkhanaVasuliRecords);
        if (karkhanaVasuliRecords.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, karkhanaVasuliRecords.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!karkhanaVasuliRecordsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KarkhanaVasuliRecords result = karkhanaVasuliRecordsService.update(karkhanaVasuliRecords);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, karkhanaVasuliRecords.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /karkhana-vasuli-records/:id} : Partial updates given fields of an existing karkhanaVasuliRecords, field will ignore if it is null
     *
     * @param id the id of the karkhanaVasuliRecords to save.
     * @param karkhanaVasuliRecords the karkhanaVasuliRecords to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated karkhanaVasuliRecords,
     * or with status {@code 400 (Bad Request)} if the karkhanaVasuliRecords is not valid,
     * or with status {@code 404 (Not Found)} if the karkhanaVasuliRecords is not found,
     * or with status {@code 500 (Internal Server Error)} if the karkhanaVasuliRecords couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KarkhanaVasuliRecords> partialUpdateKarkhanaVasuliRecords(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KarkhanaVasuliRecords karkhanaVasuliRecords
    ) throws URISyntaxException {
        log.debug("REST request to partial update KarkhanaVasuliRecords partially : {}, {}", id, karkhanaVasuliRecords);
        if (karkhanaVasuliRecords.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, karkhanaVasuliRecords.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!karkhanaVasuliRecordsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KarkhanaVasuliRecords> result = karkhanaVasuliRecordsService.partialUpdate(karkhanaVasuliRecords);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, karkhanaVasuliRecords.getId().toString())
        );
    }

    /**
     * {@code GET  /karkhana-vasuli-records} : get all the karkhanaVasuliRecords.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of karkhanaVasuliRecords in body.
     */
    @GetMapping("")
    public ResponseEntity<List<KarkhanaVasuliRecords>> getAllKarkhanaVasuliRecords(
        KarkhanaVasuliRecordsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KarkhanaVasuliRecords by criteria: {}", criteria);

        Page<KarkhanaVasuliRecords> page = karkhanaVasuliRecordsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /karkhana-vasuli-records/count} : count all the karkhanaVasuliRecords.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countKarkhanaVasuliRecords(KarkhanaVasuliRecordsCriteria criteria) {
        log.debug("REST request to count KarkhanaVasuliRecords by criteria: {}", criteria);
        return ResponseEntity.ok().body(karkhanaVasuliRecordsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /karkhana-vasuli-records/:id} : get the "id" karkhanaVasuliRecords.
     *
     * @param id the id of the karkhanaVasuliRecords to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the karkhanaVasuliRecords, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KarkhanaVasuliRecords> getKarkhanaVasuliRecords(@PathVariable Long id) {
        log.debug("REST request to get KarkhanaVasuliRecords : {}", id);
        Optional<KarkhanaVasuliRecords> karkhanaVasuliRecords = karkhanaVasuliRecordsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(karkhanaVasuliRecords);
    }

    /**
     * {@code DELETE  /karkhana-vasuli-records/:id} : delete the "id" karkhanaVasuliRecords.
     *
     * @param id the id of the karkhanaVasuliRecords to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKarkhanaVasuliRecords(@PathVariable Long id) {
        log.debug("REST request to delete KarkhanaVasuliRecords : {}", id);
        karkhanaVasuliRecordsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
