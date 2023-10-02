package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KarkhanaVasuli;
import com.cbs.middleware.repository.KarkhanaVasuliRepository;
import com.cbs.middleware.service.KarkhanaVasuliQueryService;
import com.cbs.middleware.service.KarkhanaVasuliService;
import com.cbs.middleware.service.criteria.KarkhanaVasuliCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KarkhanaVasuli}.
 */
@RestController
@RequestMapping("/api")
public class KarkhanaVasuliResource {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliResource.class);

    private static final String ENTITY_NAME = "karkhanaVasuli";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KarkhanaVasuliService karkhanaVasuliService;

    private final KarkhanaVasuliRepository karkhanaVasuliRepository;

    private final KarkhanaVasuliQueryService karkhanaVasuliQueryService;

    public KarkhanaVasuliResource(
        KarkhanaVasuliService karkhanaVasuliService,
        KarkhanaVasuliRepository karkhanaVasuliRepository,
        KarkhanaVasuliQueryService karkhanaVasuliQueryService
    ) {
        this.karkhanaVasuliService = karkhanaVasuliService;
        this.karkhanaVasuliRepository = karkhanaVasuliRepository;
        this.karkhanaVasuliQueryService = karkhanaVasuliQueryService;
    }

    /**
     * {@code POST  /karkhana-vasulis} : Create a new karkhanaVasuli.
     *
     * @param karkhanaVasuli the karkhanaVasuli to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new karkhanaVasuli, or with status {@code 400 (Bad Request)} if the karkhanaVasuli has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/karkhana-vasulis")
    public ResponseEntity<KarkhanaVasuli> createKarkhanaVasuli(@RequestBody KarkhanaVasuli karkhanaVasuli) throws URISyntaxException {
        log.debug("REST request to save KarkhanaVasuli : {}", karkhanaVasuli);
        if (karkhanaVasuli.getId() != null) {
            throw new BadRequestAlertException("A new karkhanaVasuli cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KarkhanaVasuli result = karkhanaVasuliService.save(karkhanaVasuli);
        return ResponseEntity
            .created(new URI("/api/karkhana-vasulis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /karkhana-vasulis/:id} : Updates an existing karkhanaVasuli.
     *
     * @param id the id of the karkhanaVasuli to save.
     * @param karkhanaVasuli the karkhanaVasuli to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated karkhanaVasuli,
     * or with status {@code 400 (Bad Request)} if the karkhanaVasuli is not valid,
     * or with status {@code 500 (Internal Server Error)} if the karkhanaVasuli couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/karkhana-vasulis/{id}")
    public ResponseEntity<KarkhanaVasuli> updateKarkhanaVasuli(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KarkhanaVasuli karkhanaVasuli
    ) throws URISyntaxException {
        log.debug("REST request to update KarkhanaVasuli : {}, {}", id, karkhanaVasuli);
        if (karkhanaVasuli.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, karkhanaVasuli.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!karkhanaVasuliRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KarkhanaVasuli result = karkhanaVasuliService.update(karkhanaVasuli);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, karkhanaVasuli.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /karkhana-vasulis/:id} : Partial updates given fields of an existing karkhanaVasuli, field will ignore if it is null
     *
     * @param id the id of the karkhanaVasuli to save.
     * @param karkhanaVasuli the karkhanaVasuli to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated karkhanaVasuli,
     * or with status {@code 400 (Bad Request)} if the karkhanaVasuli is not valid,
     * or with status {@code 404 (Not Found)} if the karkhanaVasuli is not found,
     * or with status {@code 500 (Internal Server Error)} if the karkhanaVasuli couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/karkhana-vasulis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KarkhanaVasuli> partialUpdateKarkhanaVasuli(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KarkhanaVasuli karkhanaVasuli
    ) throws URISyntaxException {
        log.debug("REST request to partial update KarkhanaVasuli partially : {}, {}", id, karkhanaVasuli);
        if (karkhanaVasuli.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, karkhanaVasuli.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!karkhanaVasuliRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KarkhanaVasuli> result = karkhanaVasuliService.partialUpdate(karkhanaVasuli);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, karkhanaVasuli.getId().toString())
        );
    }

    /**
     * {@code GET  /karkhana-vasulis} : get all the karkhanaVasulis.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of karkhanaVasulis in body.
     */
    @GetMapping("/karkhana-vasulis")
    public ResponseEntity<List<KarkhanaVasuli>> getAllKarkhanaVasulis(
        KarkhanaVasuliCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KarkhanaVasulis by criteria: {}", criteria);
        Page<KarkhanaVasuli> page = karkhanaVasuliQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /karkhana-vasulis/count} : count all the karkhanaVasulis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/karkhana-vasulis/count")
    public ResponseEntity<Long> countKarkhanaVasulis(KarkhanaVasuliCriteria criteria) {
        log.debug("REST request to count KarkhanaVasulis by criteria: {}", criteria);
        return ResponseEntity.ok().body(karkhanaVasuliQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /karkhana-vasulis/:id} : get the "id" karkhanaVasuli.
     *
     * @param id the id of the karkhanaVasuli to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the karkhanaVasuli, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/karkhana-vasulis/{id}")
    public ResponseEntity<KarkhanaVasuli> getKarkhanaVasuli(@PathVariable Long id) {
        log.debug("REST request to get KarkhanaVasuli : {}", id);
        Optional<KarkhanaVasuli> karkhanaVasuli = karkhanaVasuliService.findOne(id);
        return ResponseUtil.wrapOrNotFound(karkhanaVasuli);
    }

    /**
     * {@code DELETE  /karkhana-vasulis/:id} : delete the "id" karkhanaVasuli.
     *
     * @param id the id of the karkhanaVasuli to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/karkhana-vasulis/{id}")
    public ResponseEntity<Void> deleteKarkhanaVasuli(@PathVariable Long id) {
        log.debug("REST request to delete KarkhanaVasuli : {}", id);
        karkhanaVasuliService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
