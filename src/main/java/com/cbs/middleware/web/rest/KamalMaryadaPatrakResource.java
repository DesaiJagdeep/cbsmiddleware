package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KamalMaryadaPatrak;
import com.cbs.middleware.repository.KamalMaryadaPatrakRepository;
import com.cbs.middleware.service.KamalMaryadaPatrakQueryService;
import com.cbs.middleware.service.KamalMaryadaPatrakService;
import com.cbs.middleware.service.criteria.KamalMaryadaPatrakCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KamalMaryadaPatrak}.
 */
@RestController
@RequestMapping("/api")
public class KamalMaryadaPatrakResource {

    private final Logger log = LoggerFactory.getLogger(KamalMaryadaPatrakResource.class);

    private static final String ENTITY_NAME = "kamalMaryadaPatrak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KamalMaryadaPatrakService kamalMaryadaPatrakService;

    private final KamalMaryadaPatrakRepository kamalMaryadaPatrakRepository;

    private final KamalMaryadaPatrakQueryService kamalMaryadaPatrakQueryService;

    public KamalMaryadaPatrakResource(
        KamalMaryadaPatrakService kamalMaryadaPatrakService,
        KamalMaryadaPatrakRepository kamalMaryadaPatrakRepository,
        KamalMaryadaPatrakQueryService kamalMaryadaPatrakQueryService
    ) {
        this.kamalMaryadaPatrakService = kamalMaryadaPatrakService;
        this.kamalMaryadaPatrakRepository = kamalMaryadaPatrakRepository;
        this.kamalMaryadaPatrakQueryService = kamalMaryadaPatrakQueryService;
    }

    /**
     * {@code POST  /kamal-maryada-patraks} : Create a new kamalMaryadaPatrak.
     *
     * @param kamalMaryadaPatrak the kamalMaryadaPatrak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kamalMaryadaPatrak, or with status {@code 400 (Bad Request)} if the kamalMaryadaPatrak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kamal-maryada-patraks")
    public ResponseEntity<KamalMaryadaPatrak> createKamalMaryadaPatrak(@RequestBody KamalMaryadaPatrak kamalMaryadaPatrak)
        throws URISyntaxException {
        log.debug("REST request to save KamalMaryadaPatrak : {}", kamalMaryadaPatrak);
        if (kamalMaryadaPatrak.getId() != null) {
            throw new BadRequestAlertException("A new kamalMaryadaPatrak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KamalMaryadaPatrak result = kamalMaryadaPatrakService.save(kamalMaryadaPatrak);
        return ResponseEntity
            .created(new URI("/api/kamal-maryada-patraks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kamal-maryada-patraks/:id} : Updates an existing kamalMaryadaPatrak.
     *
     * @param id the id of the kamalMaryadaPatrak to save.
     * @param kamalMaryadaPatrak the kamalMaryadaPatrak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kamalMaryadaPatrak,
     * or with status {@code 400 (Bad Request)} if the kamalMaryadaPatrak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kamalMaryadaPatrak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kamal-maryada-patraks/{id}")
    public ResponseEntity<KamalMaryadaPatrak> updateKamalMaryadaPatrak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KamalMaryadaPatrak kamalMaryadaPatrak
    ) throws URISyntaxException {
        log.debug("REST request to update KamalMaryadaPatrak : {}, {}", id, kamalMaryadaPatrak);
        if (kamalMaryadaPatrak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kamalMaryadaPatrak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kamalMaryadaPatrakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KamalMaryadaPatrak result = kamalMaryadaPatrakService.update(kamalMaryadaPatrak);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kamalMaryadaPatrak.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kamal-maryada-patraks/:id} : Partial updates given fields of an existing kamalMaryadaPatrak, field will ignore if it is null
     *
     * @param id the id of the kamalMaryadaPatrak to save.
     * @param kamalMaryadaPatrak the kamalMaryadaPatrak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kamalMaryadaPatrak,
     * or with status {@code 400 (Bad Request)} if the kamalMaryadaPatrak is not valid,
     * or with status {@code 404 (Not Found)} if the kamalMaryadaPatrak is not found,
     * or with status {@code 500 (Internal Server Error)} if the kamalMaryadaPatrak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kamal-maryada-patraks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KamalMaryadaPatrak> partialUpdateKamalMaryadaPatrak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KamalMaryadaPatrak kamalMaryadaPatrak
    ) throws URISyntaxException {
        log.debug("REST request to partial update KamalMaryadaPatrak partially : {}, {}", id, kamalMaryadaPatrak);
        if (kamalMaryadaPatrak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kamalMaryadaPatrak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kamalMaryadaPatrakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KamalMaryadaPatrak> result = kamalMaryadaPatrakService.partialUpdate(kamalMaryadaPatrak);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kamalMaryadaPatrak.getId().toString())
        );
    }

    /**
     * {@code GET  /kamal-maryada-patraks} : get all the kamalMaryadaPatraks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kamalMaryadaPatraks in body.
     */
    @GetMapping("/kamal-maryada-patraks")
    public ResponseEntity<List<KamalMaryadaPatrak>> getAllKamalMaryadaPatraks(
        KamalMaryadaPatrakCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KamalMaryadaPatraks by criteria: {}", criteria);
        Page<KamalMaryadaPatrak> page = kamalMaryadaPatrakQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kamal-maryada-patraks/count} : count all the kamalMaryadaPatraks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/kamal-maryada-patraks/count")
    public ResponseEntity<Long> countKamalMaryadaPatraks(KamalMaryadaPatrakCriteria criteria) {
        log.debug("REST request to count KamalMaryadaPatraks by criteria: {}", criteria);
        return ResponseEntity.ok().body(kamalMaryadaPatrakQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /kamal-maryada-patraks/:id} : get the "id" kamalMaryadaPatrak.
     *
     * @param id the id of the kamalMaryadaPatrak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kamalMaryadaPatrak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kamal-maryada-patraks/{id}")
    public ResponseEntity<KamalMaryadaPatrak> getKamalMaryadaPatrak(@PathVariable Long id) {
        log.debug("REST request to get KamalMaryadaPatrak : {}", id);
        Optional<KamalMaryadaPatrak> kamalMaryadaPatrak = kamalMaryadaPatrakService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kamalMaryadaPatrak);
    }

    /**
     * {@code DELETE  /kamal-maryada-patraks/:id} : delete the "id" kamalMaryadaPatrak.
     *
     * @param id the id of the kamalMaryadaPatrak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kamal-maryada-patraks/{id}")
    public ResponseEntity<Void> deleteKamalMaryadaPatrak(@PathVariable Long id) {
        log.debug("REST request to delete KamalMaryadaPatrak : {}", id);
        kamalMaryadaPatrakService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
