package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.IsCalculateTemp;
import com.cbs.middleware.repository.IsCalculateTempRepository;
import com.cbs.middleware.service.IsCalculateTempQueryService;
import com.cbs.middleware.service.IsCalculateTempService;
import com.cbs.middleware.service.criteria.IsCalculateTempCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.IsCalculateTemp}.
 */
@RestController
@RequestMapping("/api")
public class IsCalculateTempResource {

    private final Logger log = LoggerFactory.getLogger(IsCalculateTempResource.class);

    private static final String ENTITY_NAME = "isCalculateTemp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IsCalculateTempService isCalculateTempService;

    private final IsCalculateTempRepository isCalculateTempRepository;

    private final IsCalculateTempQueryService isCalculateTempQueryService;

    public IsCalculateTempResource(
        IsCalculateTempService isCalculateTempService,
        IsCalculateTempRepository isCalculateTempRepository,
        IsCalculateTempQueryService isCalculateTempQueryService
    ) {
        this.isCalculateTempService = isCalculateTempService;
        this.isCalculateTempRepository = isCalculateTempRepository;
        this.isCalculateTempQueryService = isCalculateTempQueryService;
    }

    /**
     * {@code POST  /is-calculate-temps} : Create a new isCalculateTemp.
     *
     * @param isCalculateTemp the isCalculateTemp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new isCalculateTemp, or with status {@code 400 (Bad Request)} if the isCalculateTemp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/is-calculate-temps")
    public ResponseEntity<IsCalculateTemp> createIsCalculateTemp(@RequestBody IsCalculateTemp isCalculateTemp) throws URISyntaxException {
        log.debug("REST request to save IsCalculateTemp : {}", isCalculateTemp);
        if (isCalculateTemp.getId() != null) {
            throw new BadRequestAlertException("A new isCalculateTemp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IsCalculateTemp result = isCalculateTempService.save(isCalculateTemp);
        return ResponseEntity
            .created(new URI("/api/is-calculate-temps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /is-calculate-temps/:id} : Updates an existing isCalculateTemp.
     *
     * @param id the id of the isCalculateTemp to save.
     * @param isCalculateTemp the isCalculateTemp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated isCalculateTemp,
     * or with status {@code 400 (Bad Request)} if the isCalculateTemp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the isCalculateTemp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/is-calculate-temps/{id}")
    public ResponseEntity<IsCalculateTemp> updateIsCalculateTemp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IsCalculateTemp isCalculateTemp
    ) throws URISyntaxException {
        log.debug("REST request to update IsCalculateTemp : {}, {}", id, isCalculateTemp);
        if (isCalculateTemp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, isCalculateTemp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!isCalculateTempRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IsCalculateTemp result = isCalculateTempService.update(isCalculateTemp);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, isCalculateTemp.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /is-calculate-temps/:id} : Partial updates given fields of an existing isCalculateTemp, field will ignore if it is null
     *
     * @param id the id of the isCalculateTemp to save.
     * @param isCalculateTemp the isCalculateTemp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated isCalculateTemp,
     * or with status {@code 400 (Bad Request)} if the isCalculateTemp is not valid,
     * or with status {@code 404 (Not Found)} if the isCalculateTemp is not found,
     * or with status {@code 500 (Internal Server Error)} if the isCalculateTemp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/is-calculate-temps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IsCalculateTemp> partialUpdateIsCalculateTemp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IsCalculateTemp isCalculateTemp
    ) throws URISyntaxException {
        log.debug("REST request to partial update IsCalculateTemp partially : {}, {}", id, isCalculateTemp);
        if (isCalculateTemp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, isCalculateTemp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!isCalculateTempRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IsCalculateTemp> result = isCalculateTempService.partialUpdate(isCalculateTemp);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, isCalculateTemp.getId().toString())
        );
    }

    /**
     * {@code GET  /is-calculate-temps} : get all the isCalculateTemps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of isCalculateTemps in body.
     */
    @GetMapping("/is-calculate-temps")
    public ResponseEntity<List<IsCalculateTemp>> getAllIsCalculateTemps(
        IsCalculateTempCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get IsCalculateTemps by criteria: {}", criteria);
        Page<IsCalculateTemp> page = isCalculateTempQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /is-calculate-temps/count} : count all the isCalculateTemps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/is-calculate-temps/count")
    public ResponseEntity<Long> countIsCalculateTemps(IsCalculateTempCriteria criteria) {
        log.debug("REST request to count IsCalculateTemps by criteria: {}", criteria);
        return ResponseEntity.ok().body(isCalculateTempQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /is-calculate-temps/:id} : get the "id" isCalculateTemp.
     *
     * @param id the id of the isCalculateTemp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the isCalculateTemp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/is-calculate-temps/{id}")
    public ResponseEntity<IsCalculateTemp> getIsCalculateTemp(@PathVariable Long id) {
        log.debug("REST request to get IsCalculateTemp : {}", id);
        Optional<IsCalculateTemp> isCalculateTemp = isCalculateTempService.findOne(id);
        return ResponseUtil.wrapOrNotFound(isCalculateTemp);
    }

    /**
     * {@code DELETE  /is-calculate-temps/:id} : delete the "id" isCalculateTemp.
     *
     * @param id the id of the isCalculateTemp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/is-calculate-temps/{id}")
    public ResponseEntity<Void> deleteIsCalculateTemp(@PathVariable Long id) {
        log.debug("REST request to delete IsCalculateTemp : {}", id);
        isCalculateTempService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
