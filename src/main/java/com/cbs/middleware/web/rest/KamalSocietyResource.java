package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KamalSociety;
import com.cbs.middleware.repository.KamalSocietyRepository;
import com.cbs.middleware.service.KamalSocietyQueryService;
import com.cbs.middleware.service.KamalSocietyService;
import com.cbs.middleware.service.criteria.KamalSocietyCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KamalSociety}.
 */
@RestController
@RequestMapping("/api")
public class KamalSocietyResource {

    private final Logger log = LoggerFactory.getLogger(KamalSocietyResource.class);

    private static final String ENTITY_NAME = "kamalSociety";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KamalSocietyService kamalSocietyService;

    private final KamalSocietyRepository kamalSocietyRepository;

    private final KamalSocietyQueryService kamalSocietyQueryService;

    public KamalSocietyResource(
        KamalSocietyService kamalSocietyService,
        KamalSocietyRepository kamalSocietyRepository,
        KamalSocietyQueryService kamalSocietyQueryService
    ) {
        this.kamalSocietyService = kamalSocietyService;
        this.kamalSocietyRepository = kamalSocietyRepository;
        this.kamalSocietyQueryService = kamalSocietyQueryService;
    }

    /**
     * {@code POST  /kamal-societies} : Create a new kamalSociety.
     *
     * @param kamalSociety the kamalSociety to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kamalSociety, or with status {@code 400 (Bad Request)} if the kamalSociety has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kamal-societies")
    public ResponseEntity<KamalSociety> createKamalSociety(@RequestBody KamalSociety kamalSociety) throws URISyntaxException {
        log.debug("REST request to save KamalSociety : {}", kamalSociety);
        if (kamalSociety.getId() != null) {
            throw new BadRequestAlertException("A new kamalSociety cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KamalSociety result = kamalSocietyService.save(kamalSociety);
        return ResponseEntity
            .created(new URI("/api/kamal-societies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kamal-societies/:id} : Updates an existing kamalSociety.
     *
     * @param id the id of the kamalSociety to save.
     * @param kamalSociety the kamalSociety to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kamalSociety,
     * or with status {@code 400 (Bad Request)} if the kamalSociety is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kamalSociety couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kamal-societies/{id}")
    public ResponseEntity<KamalSociety> updateKamalSociety(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KamalSociety kamalSociety
    ) throws URISyntaxException {
        log.debug("REST request to update KamalSociety : {}, {}", id, kamalSociety);
        if (kamalSociety.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kamalSociety.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kamalSocietyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KamalSociety result = kamalSocietyService.update(kamalSociety);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kamalSociety.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kamal-societies/:id} : Partial updates given fields of an existing kamalSociety, field will ignore if it is null
     *
     * @param id the id of the kamalSociety to save.
     * @param kamalSociety the kamalSociety to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kamalSociety,
     * or with status {@code 400 (Bad Request)} if the kamalSociety is not valid,
     * or with status {@code 404 (Not Found)} if the kamalSociety is not found,
     * or with status {@code 500 (Internal Server Error)} if the kamalSociety couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kamal-societies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KamalSociety> partialUpdateKamalSociety(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KamalSociety kamalSociety
    ) throws URISyntaxException {
        log.debug("REST request to partial update KamalSociety partially : {}, {}", id, kamalSociety);
        if (kamalSociety.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kamalSociety.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kamalSocietyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KamalSociety> result = kamalSocietyService.partialUpdate(kamalSociety);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kamalSociety.getId().toString())
        );
    }

    /**
     * {@code GET  /kamal-societies} : get all the kamalSocieties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kamalSocieties in body.
     */
    @GetMapping("/kamal-societies")
    public ResponseEntity<List<KamalSociety>> getAllKamalSocieties(
        KamalSocietyCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KamalSocieties by criteria: {}", criteria);
        Page<KamalSociety> page = kamalSocietyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kamal-societies/count} : count all the kamalSocieties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/kamal-societies/count")
    public ResponseEntity<Long> countKamalSocieties(KamalSocietyCriteria criteria) {
        log.debug("REST request to count KamalSocieties by criteria: {}", criteria);
        return ResponseEntity.ok().body(kamalSocietyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /kamal-societies/:id} : get the "id" kamalSociety.
     *
     * @param id the id of the kamalSociety to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kamalSociety, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kamal-societies/{id}")
    public ResponseEntity<KamalSociety> getKamalSociety(@PathVariable Long id) {
        log.debug("REST request to get KamalSociety : {}", id);
        Optional<KamalSociety> kamalSociety = kamalSocietyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kamalSociety);
    }

    /**
     * {@code DELETE  /kamal-societies/:id} : delete the "id" kamalSociety.
     *
     * @param id the id of the kamalSociety to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kamal-societies/{id}")
    public ResponseEntity<Void> deleteKamalSociety(@PathVariable Long id) {
        log.debug("REST request to delete KamalSociety : {}", id);
        kamalSocietyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
