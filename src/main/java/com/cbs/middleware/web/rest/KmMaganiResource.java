package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KmMagani;
import com.cbs.middleware.repository.KmMaganiRepository;
import com.cbs.middleware.service.KmMaganiQueryService;
import com.cbs.middleware.service.KmMaganiService;
import com.cbs.middleware.service.criteria.KmMaganiCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KmMagani}.
 */
@RestController
@RequestMapping("/api")
public class KmMaganiResource {

    private final Logger log = LoggerFactory.getLogger(KmMaganiResource.class);

    private static final String ENTITY_NAME = "kmMagani";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KmMaganiService kmMaganiService;

    private final KmMaganiRepository kmMaganiRepository;

    private final KmMaganiQueryService kmMaganiQueryService;

    public KmMaganiResource(
        KmMaganiService kmMaganiService,
        KmMaganiRepository kmMaganiRepository,
        KmMaganiQueryService kmMaganiQueryService
    ) {
        this.kmMaganiService = kmMaganiService;
        this.kmMaganiRepository = kmMaganiRepository;
        this.kmMaganiQueryService = kmMaganiQueryService;
    }

    /**
     * {@code POST  /km-maganis} : Create a new kmMagani.
     *
     * @param kmMagani the kmMagani to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kmMagani, or with status {@code 400 (Bad Request)} if the kmMagani has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/km-maganis")
    public ResponseEntity<KmMagani> createKmMagani(@RequestBody KmMagani kmMagani) throws URISyntaxException {
        log.debug("REST request to save KmMagani : {}", kmMagani);
        if (kmMagani.getId() != null) {
            throw new BadRequestAlertException("A new kmMagani cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KmMagani result = kmMaganiService.save(kmMagani);
        return ResponseEntity
            .created(new URI("/api/km-maganis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /km-maganis/:id} : Updates an existing kmMagani.
     *
     * @param id the id of the kmMagani to save.
     * @param kmMagani the kmMagani to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmMagani,
     * or with status {@code 400 (Bad Request)} if the kmMagani is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kmMagani couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/km-maganis/{id}")
    public ResponseEntity<KmMagani> updateKmMagani(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmMagani kmMagani
    ) throws URISyntaxException {
        log.debug("REST request to update KmMagani : {}, {}", id, kmMagani);
        if (kmMagani.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmMagani.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmMaganiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KmMagani result = kmMaganiService.update(kmMagani);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmMagani.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /km-maganis/:id} : Partial updates given fields of an existing kmMagani, field will ignore if it is null
     *
     * @param id the id of the kmMagani to save.
     * @param kmMagani the kmMagani to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kmMagani,
     * or with status {@code 400 (Bad Request)} if the kmMagani is not valid,
     * or with status {@code 404 (Not Found)} if the kmMagani is not found,
     * or with status {@code 500 (Internal Server Error)} if the kmMagani couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/km-maganis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KmMagani> partialUpdateKmMagani(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KmMagani kmMagani
    ) throws URISyntaxException {
        log.debug("REST request to partial update KmMagani partially : {}, {}", id, kmMagani);
        if (kmMagani.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kmMagani.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kmMaganiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KmMagani> result = kmMaganiService.partialUpdate(kmMagani);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kmMagani.getId().toString())
        );
    }

    /**
     * {@code GET  /km-maganis} : get all the kmMaganis.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kmMaganis in body.
     */
    @GetMapping("/km-maganis")
    public ResponseEntity<List<KmMagani>> getAllKmMaganis(
        KmMaganiCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KmMaganis by criteria: {}", criteria);
        Page<KmMagani> page = kmMaganiQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /km-maganis/count} : count all the kmMaganis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/km-maganis/count")
    public ResponseEntity<Long> countKmMaganis(KmMaganiCriteria criteria) {
        log.debug("REST request to count KmMaganis by criteria: {}", criteria);
        return ResponseEntity.ok().body(kmMaganiQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /km-maganis/:id} : get the "id" kmMagani.
     *
     * @param id the id of the kmMagani to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kmMagani, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/km-maganis/{id}")
    public ResponseEntity<KmMagani> getKmMagani(@PathVariable Long id) {
        log.debug("REST request to get KmMagani : {}", id);
        Optional<KmMagani> kmMagani = kmMaganiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kmMagani);
    }

    /**
     * {@code DELETE  /km-maganis/:id} : delete the "id" kmMagani.
     *
     * @param id the id of the kmMagani to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/km-maganis/{id}")
    public ResponseEntity<Void> deleteKmMagani(@PathVariable Long id) {
        log.debug("REST request to delete KmMagani : {}", id);
        kmMaganiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
