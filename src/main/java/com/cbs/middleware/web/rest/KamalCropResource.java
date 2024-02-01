package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KamalCrop;
import com.cbs.middleware.repository.KamalCropRepository;
import com.cbs.middleware.service.KamalCropQueryService;
import com.cbs.middleware.service.KamalCropService;
import com.cbs.middleware.service.criteria.KamalCropCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KamalCrop}.
 */
@RestController
@RequestMapping("/api")
public class KamalCropResource {

    private final Logger log = LoggerFactory.getLogger(KamalCropResource.class);

    private static final String ENTITY_NAME = "kamalCrop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KamalCropService kamalCropService;

    private final KamalCropRepository kamalCropRepository;

    private final KamalCropQueryService kamalCropQueryService;

    public KamalCropResource(
        KamalCropService kamalCropService,
        KamalCropRepository kamalCropRepository,
        KamalCropQueryService kamalCropQueryService
    ) {
        this.kamalCropService = kamalCropService;
        this.kamalCropRepository = kamalCropRepository;
        this.kamalCropQueryService = kamalCropQueryService;
    }

    /**
     * {@code POST  /kamal-crops} : Create a new kamalCrop.
     *
     * @param kamalCrop the kamalCrop to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kamalCrop, or with status {@code 400 (Bad Request)} if the kamalCrop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kamal-crops")
    public ResponseEntity<KamalCrop> createKamalCrop(@RequestBody KamalCrop kamalCrop) throws URISyntaxException {
        log.debug("REST request to save KamalCrop : {}", kamalCrop);
        if (kamalCrop.getId() != null) {
            throw new BadRequestAlertException("A new kamalCrop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KamalCrop result = kamalCropService.save(kamalCrop);
        return ResponseEntity
            .created(new URI("/api/kamal-crops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kamal-crops/:id} : Updates an existing kamalCrop.
     *
     * @param id the id of the kamalCrop to save.
     * @param kamalCrop the kamalCrop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kamalCrop,
     * or with status {@code 400 (Bad Request)} if the kamalCrop is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kamalCrop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kamal-crops/{id}")
    public ResponseEntity<KamalCrop> updateKamalCrop(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KamalCrop kamalCrop
    ) throws URISyntaxException {
        log.debug("REST request to update KamalCrop : {}, {}", id, kamalCrop);
        if (kamalCrop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kamalCrop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kamalCropRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KamalCrop result = kamalCropService.update(kamalCrop);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kamalCrop.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kamal-crops/:id} : Partial updates given fields of an existing kamalCrop, field will ignore if it is null
     *
     * @param id the id of the kamalCrop to save.
     * @param kamalCrop the kamalCrop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kamalCrop,
     * or with status {@code 400 (Bad Request)} if the kamalCrop is not valid,
     * or with status {@code 404 (Not Found)} if the kamalCrop is not found,
     * or with status {@code 500 (Internal Server Error)} if the kamalCrop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kamal-crops/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KamalCrop> partialUpdateKamalCrop(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KamalCrop kamalCrop
    ) throws URISyntaxException {
        log.debug("REST request to partial update KamalCrop partially : {}, {}", id, kamalCrop);
        if (kamalCrop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kamalCrop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kamalCropRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KamalCrop> result = kamalCropService.partialUpdate(kamalCrop);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kamalCrop.getId().toString())
        );
    }

    /**
     * {@code GET  /kamal-crops} : get all the kamalCrops.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kamalCrops in body.
     */
    @GetMapping("/kamal-crops")
    public ResponseEntity<List<KamalCrop>> getAllKamalCrops(
        KamalCropCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KamalCrops by criteria: {}", criteria);
        Page<KamalCrop> page = kamalCropQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kamal-crops/count} : count all the kamalCrops.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/kamal-crops/count")
    public ResponseEntity<Long> countKamalCrops(KamalCropCriteria criteria) {
        log.debug("REST request to count KamalCrops by criteria: {}", criteria);
        return ResponseEntity.ok().body(kamalCropQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /kamal-crops/:id} : get the "id" kamalCrop.
     *
     * @param id the id of the kamalCrop to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kamalCrop, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kamal-crops/{id}")
    public ResponseEntity<KamalCrop> getKamalCrop(@PathVariable Long id) {
        log.debug("REST request to get KamalCrop : {}", id);
        Optional<KamalCrop> kamalCrop = kamalCropService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kamalCrop);
    }

    /**
     * {@code DELETE  /kamal-crops/:id} : delete the "id" kamalCrop.
     *
     * @param id the id of the kamalCrop to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kamal-crops/{id}")
    public ResponseEntity<Void> deleteKamalCrop(@PathVariable Long id) {
        log.debug("REST request to delete KamalCrop : {}", id);
        kamalCropService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
