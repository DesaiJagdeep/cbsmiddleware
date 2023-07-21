package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.service.IssPortalFileQueryService;
import com.cbs.middleware.service.IssPortalFileService;
import com.cbs.middleware.service.criteria.IssPortalFileCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssPortalFile}.
 */
@RestController
@RequestMapping("/api")
public class IssPortalFileResource {

    private final Logger log = LoggerFactory.getLogger(IssPortalFileResource.class);

    private static final String ENTITY_NAME = "issPortalFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssPortalFileService issPortalFileService;

    private final IssPortalFileRepository issPortalFileRepository;

    private final IssPortalFileQueryService issPortalFileQueryService;

    public IssPortalFileResource(
        IssPortalFileService issPortalFileService,
        IssPortalFileRepository issPortalFileRepository,
        IssPortalFileQueryService issPortalFileQueryService
    ) {
        this.issPortalFileService = issPortalFileService;
        this.issPortalFileRepository = issPortalFileRepository;
        this.issPortalFileQueryService = issPortalFileQueryService;
    }

    @GetMapping("/download-file/{idIFP}")
    @PreAuthorize("@authentication.hasPermision('',#idIFP,'','FILE_DOWNLOAD','DOWNLOAD')")
    public Object excelDownload(@PathVariable Long idIFP) {
        Optional<IssPortalFile> findByUniqueName = issPortalFileRepository.findById(idIFP);
        if (findByUniqueName.isPresent()) {
            Path file = Paths.get(Constants.ORIGINAL_FILE_PATH + findByUniqueName.get().getUniqueName());

            if (!Files.exists(file)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            byte[] fileBytes;
            try {
                fileBytes = Files.readAllBytes(file);
                ByteArrayResource resource = new ByteArrayResource(fileBytes);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", findByUniqueName.get().getUniqueName());

                return ResponseEntity.ok().headers(headers).contentLength(fileBytes.length).body(resource);
            } catch (IOException e) {
                throw new BadRequestAlertException("Error in file download", ENTITY_NAME, "fileNotFound");
            }
        } else {
            throw new BadRequestAlertException("Error in file download", ENTITY_NAME, "fileNotFound");
        }
    }

    /**
     * {@code POST  /iss-portal-files} : Create a new issPortalFile.
     *
     * @param issPortalFile the issPortalFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issPortalFile, or with status {@code 400 (Bad Request)} if the issPortalFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/iss-portal-files")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<IssPortalFile> createIssPortalFile(@RequestBody IssPortalFile issPortalFile) throws URISyntaxException {
        log.debug("REST request to save IssPortalFile : {}", issPortalFile);
        if (issPortalFile.getId() != null) {
            throw new BadRequestAlertException("A new issPortalFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssPortalFile result = issPortalFileService.save(issPortalFile);
        return ResponseEntity
            .created(new URI("/api/iss-portal-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /iss-portal-files/:id} : Updates an existing issPortalFile.
     *
     * @param id the id of the issPortalFile to save.
     * @param issPortalFile the issPortalFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issPortalFile,
     * or with status {@code 400 (Bad Request)} if the issPortalFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issPortalFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/iss-portal-files/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<IssPortalFile> updateIssPortalFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssPortalFile issPortalFile
    ) throws URISyntaxException {
        log.debug("REST request to update IssPortalFile : {}, {}", id, issPortalFile);
        if (issPortalFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issPortalFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issPortalFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IssPortalFile result = issPortalFileService.update(issPortalFile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issPortalFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /iss-portal-files/:id} : Partial updates given fields of an existing issPortalFile, field will ignore if it is null
     *
     * @param id the id of the issPortalFile to save.
     * @param issPortalFile the issPortalFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issPortalFile,
     * or with status {@code 400 (Bad Request)} if the issPortalFile is not valid,
     * or with status {@code 404 (Not Found)} if the issPortalFile is not found,
     * or with status {@code 500 (Internal Server Error)} if the issPortalFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/iss-portal-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<IssPortalFile> partialUpdateIssPortalFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssPortalFile issPortalFile
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssPortalFile partially : {}, {}", id, issPortalFile);
        if (issPortalFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issPortalFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issPortalFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IssPortalFile> result = issPortalFileService.partialUpdate(issPortalFile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issPortalFile.getId().toString())
        );
    }

    /**
     * {@code GET  /iss-portal-files} : get all the issPortalFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issPortalFiles in body.
     */
    @GetMapping("/iss-portal-files")
    public ResponseEntity<List<IssPortalFile>> getAllIssPortalFiles(
        IssPortalFileCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get IssPortalFiles by criteria: {}", criteria);
        Page<IssPortalFile> page = issPortalFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /iss-portal-files/count} : count all the issPortalFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/iss-portal-files/count")
    public ResponseEntity<Long> countIssPortalFiles(IssPortalFileCriteria criteria) {
        log.debug("REST request to count IssPortalFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(issPortalFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /iss-portal-files/:id} : get the "id" issPortalFile.
     *
     * @param id the id of the issPortalFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issPortalFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/iss-portal-files/{id}")
    public ResponseEntity<IssPortalFile> getIssPortalFile(@PathVariable Long id) {
        log.debug("REST request to get IssPortalFile : {}", id);
        Optional<IssPortalFile> issPortalFile = issPortalFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issPortalFile);
    }

    /**
     * {@code DELETE  /iss-portal-files/:id} : delete the "id" issPortalFile.
     *
     * @param id the id of the issPortalFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/iss-portal-files/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteIssPortalFile(@PathVariable Long id) {
        log.debug("REST request to delete IssPortalFile : {}", id);
        issPortalFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
