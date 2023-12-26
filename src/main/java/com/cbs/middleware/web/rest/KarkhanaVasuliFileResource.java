package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KarkhanaVasuliFile;
import com.cbs.middleware.repository.KarkhanaVasuliFileRepository;
import com.cbs.middleware.service.KarkhanaVasuliFileQueryService;
import com.cbs.middleware.service.KarkhanaVasuliFileService;
import com.cbs.middleware.service.criteria.KarkhanaVasuliFileCriteria;
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
 * REST controller for managing {@link com.cbs.middleware.domain.KarkhanaVasuliFile}.
 */
@RestController
@RequestMapping("/api/karkhana-vasuli-files")
public class KarkhanaVasuliFileResource {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliFileResource.class);

    private static final String ENTITY_NAME = "karkhanaVasuliFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KarkhanaVasuliFileService karkhanaVasuliFileService;

    private final KarkhanaVasuliFileRepository karkhanaVasuliFileRepository;

    private final KarkhanaVasuliFileQueryService karkhanaVasuliFileQueryService;

    public KarkhanaVasuliFileResource(
        KarkhanaVasuliFileService karkhanaVasuliFileService,
        KarkhanaVasuliFileRepository karkhanaVasuliFileRepository,
        KarkhanaVasuliFileQueryService karkhanaVasuliFileQueryService
    ) {
        this.karkhanaVasuliFileService = karkhanaVasuliFileService;
        this.karkhanaVasuliFileRepository = karkhanaVasuliFileRepository;
        this.karkhanaVasuliFileQueryService = karkhanaVasuliFileQueryService;
    }

    /**
     * {@code POST  /karkhana-vasuli-files} : Create a new karkhanaVasuliFile.
     *
     * @param karkhanaVasuliFile the karkhanaVasuliFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new karkhanaVasuliFile, or with status {@code 400 (Bad Request)} if the karkhanaVasuliFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<KarkhanaVasuliFile> createKarkhanaVasuliFile(@RequestBody KarkhanaVasuliFile karkhanaVasuliFile)
        throws URISyntaxException {
        log.debug("REST request to save KarkhanaVasuliFile : {}", karkhanaVasuliFile);
        if (karkhanaVasuliFile.getId() != null) {
            throw new BadRequestAlertException("A new karkhanaVasuliFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KarkhanaVasuliFile result = karkhanaVasuliFileService.save(karkhanaVasuliFile);
        return ResponseEntity
            .created(new URI("/api/karkhana-vasuli-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /karkhana-vasuli-files/:id} : Updates an existing karkhanaVasuliFile.
     *
     * @param id the id of the karkhanaVasuliFile to save.
     * @param karkhanaVasuliFile the karkhanaVasuliFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated karkhanaVasuliFile,
     * or with status {@code 400 (Bad Request)} if the karkhanaVasuliFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the karkhanaVasuliFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KarkhanaVasuliFile> updateKarkhanaVasuliFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KarkhanaVasuliFile karkhanaVasuliFile
    ) throws URISyntaxException {
        log.debug("REST request to update KarkhanaVasuliFile : {}, {}", id, karkhanaVasuliFile);
        if (karkhanaVasuliFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, karkhanaVasuliFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!karkhanaVasuliFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KarkhanaVasuliFile result = karkhanaVasuliFileService.update(karkhanaVasuliFile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, karkhanaVasuliFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /karkhana-vasuli-files/:id} : Partial updates given fields of an existing karkhanaVasuliFile, field will ignore if it is null
     *
     * @param id the id of the karkhanaVasuliFile to save.
     * @param karkhanaVasuliFile the karkhanaVasuliFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated karkhanaVasuliFile,
     * or with status {@code 400 (Bad Request)} if the karkhanaVasuliFile is not valid,
     * or with status {@code 404 (Not Found)} if the karkhanaVasuliFile is not found,
     * or with status {@code 500 (Internal Server Error)} if the karkhanaVasuliFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KarkhanaVasuliFile> partialUpdateKarkhanaVasuliFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KarkhanaVasuliFile karkhanaVasuliFile
    ) throws URISyntaxException {
        log.debug("REST request to partial update KarkhanaVasuliFile partially : {}, {}", id, karkhanaVasuliFile);
        if (karkhanaVasuliFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, karkhanaVasuliFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!karkhanaVasuliFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KarkhanaVasuliFile> result = karkhanaVasuliFileService.partialUpdate(karkhanaVasuliFile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, karkhanaVasuliFile.getId().toString())
        );
    }

    /**
     * {@code GET  /karkhana-vasuli-files} : get all the karkhanaVasuliFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of karkhanaVasuliFiles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<KarkhanaVasuliFile>> getAllKarkhanaVasuliFiles(
        KarkhanaVasuliFileCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KarkhanaVasuliFiles by criteria: {}", criteria);

        Page<KarkhanaVasuliFile> page = karkhanaVasuliFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /karkhana-vasuli-files/count} : count all the karkhanaVasuliFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countKarkhanaVasuliFiles(KarkhanaVasuliFileCriteria criteria) {
        log.debug("REST request to count KarkhanaVasuliFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(karkhanaVasuliFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /karkhana-vasuli-files/:id} : get the "id" karkhanaVasuliFile.
     *
     * @param id the id of the karkhanaVasuliFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the karkhanaVasuliFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KarkhanaVasuliFile> getKarkhanaVasuliFile(@PathVariable Long id) {
        log.debug("REST request to get KarkhanaVasuliFile : {}", id);
        Optional<KarkhanaVasuliFile> karkhanaVasuliFile = karkhanaVasuliFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(karkhanaVasuliFile);
    }

    /**
     * {@code DELETE  /karkhana-vasuli-files/:id} : delete the "id" karkhanaVasuliFile.
     *
     * @param id the id of the karkhanaVasuliFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKarkhanaVasuliFile(@PathVariable Long id) {
        log.debug("REST request to delete KarkhanaVasuliFile : {}", id);
        karkhanaVasuliFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}