package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.KMPUpload;
import com.cbs.middleware.repository.KMPUploadRepository;
import com.cbs.middleware.service.KMPUploadService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.KMPUpload}.
 */
@RestController
@RequestMapping("/api")
public class KMPUploadResource {

    private final Logger log = LoggerFactory.getLogger(KMPUploadResource.class);

    private static final String ENTITY_NAME = "kMPUpload";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KMPUploadService kMPUploadService;

    private final KMPUploadRepository kMPUploadRepository;

    public KMPUploadResource(KMPUploadService kMPUploadService, KMPUploadRepository kMPUploadRepository) {
        this.kMPUploadService = kMPUploadService;
        this.kMPUploadRepository = kMPUploadRepository;
    }

    /**
     * {@code POST  /kmp-uploads} : Create a new kMPUpload.
     *
     * @param kMPUpload the kMPUpload to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kMPUpload, or with status {@code 400 (Bad Request)} if the kMPUpload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kmp-uploads")
    public ResponseEntity<KMPUpload> createKMPUpload(@RequestBody KMPUpload kMPUpload) throws URISyntaxException {
        log.debug("REST request to save KMPUpload : {}", kMPUpload);
        if (kMPUpload.getId() != null) {
            throw new BadRequestAlertException("A new kMPUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KMPUpload result = kMPUploadService.save(kMPUpload);
        return ResponseEntity
            .created(new URI("/api/kmp-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kmp-uploads/:id} : Updates an existing kMPUpload.
     *
     * @param id the id of the kMPUpload to save.
     * @param kMPUpload the kMPUpload to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kMPUpload,
     * or with status {@code 400 (Bad Request)} if the kMPUpload is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kMPUpload couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kmp-uploads/{id}")
    public ResponseEntity<KMPUpload> updateKMPUpload(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KMPUpload kMPUpload
    ) throws URISyntaxException {
        log.debug("REST request to update KMPUpload : {}, {}", id, kMPUpload);
        if (kMPUpload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kMPUpload.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kMPUploadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KMPUpload result = kMPUploadService.update(kMPUpload);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kMPUpload.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kmp-uploads/:id} : Partial updates given fields of an existing kMPUpload, field will ignore if it is null
     *
     * @param id the id of the kMPUpload to save.
     * @param kMPUpload the kMPUpload to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kMPUpload,
     * or with status {@code 400 (Bad Request)} if the kMPUpload is not valid,
     * or with status {@code 404 (Not Found)} if the kMPUpload is not found,
     * or with status {@code 500 (Internal Server Error)} if the kMPUpload couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kmp-uploads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KMPUpload> partialUpdateKMPUpload(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KMPUpload kMPUpload
    ) throws URISyntaxException {
        log.debug("REST request to partial update KMPUpload partially : {}, {}", id, kMPUpload);
        if (kMPUpload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kMPUpload.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kMPUploadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KMPUpload> result = kMPUploadService.partialUpdate(kMPUpload);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kMPUpload.getId().toString())
        );
    }

    /**
     * {@code GET  /kmp-uploads} : get all the kMPUploads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kMPUploads in body.
     */
    @GetMapping("/kmp-uploads")
    public ResponseEntity<List<KMPUpload>> getAllKMPUploads(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of KMPUploads");
        Page<KMPUpload> page = kMPUploadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kmp-uploads/:id} : get the "id" kMPUpload.
     *
     * @param id the id of the kMPUpload to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kMPUpload, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kmp-uploads/{id}")
    public ResponseEntity<KMPUpload> getKMPUpload(@PathVariable Long id) {
        log.debug("REST request to get KMPUpload : {}", id);
        Optional<KMPUpload> kMPUpload = kMPUploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kMPUpload);
    }

    /**
     * {@code DELETE  /kmp-uploads/:id} : delete the "id" kMPUpload.
     *
     * @param id the id of the kMPUpload to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kmp-uploads/{id}")
    public ResponseEntity<Void> deleteKMPUpload(@PathVariable Long id) {
        log.debug("REST request to delete KMPUpload : {}", id);
        kMPUploadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
