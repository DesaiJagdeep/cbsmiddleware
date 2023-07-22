package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.CategoryMaster;
import com.cbs.middleware.repository.CategoryMasterRepository;
import com.cbs.middleware.service.CategoryMasterService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.CategoryMaster}.
 */
@RestController
@RequestMapping("/api")
public class CategoryMasterResource {

    private final Logger log = LoggerFactory.getLogger(CategoryMasterResource.class);

    private static final String ENTITY_NAME = "categoryMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryMasterService categoryMasterService;

    private final CategoryMasterRepository categoryMasterRepository;

    public CategoryMasterResource(CategoryMasterService categoryMasterService, CategoryMasterRepository categoryMasterRepository) {
        this.categoryMasterService = categoryMasterService;
        this.categoryMasterRepository = categoryMasterRepository;
    }

    /**
     * {@code POST  /category-masters} : Create a new categoryMaster.
     *
     * @param categoryMaster the categoryMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryMaster, or with status {@code 400 (Bad Request)} if the categoryMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CategoryMaster> createCategoryMaster(@RequestBody CategoryMaster categoryMaster) throws URISyntaxException {
        log.debug("REST request to save CategoryMaster : {}", categoryMaster);
        if (categoryMaster.getId() != null) {
            throw new BadRequestAlertException("A new categoryMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryMaster result = categoryMasterService.save(categoryMaster);
        return ResponseEntity
            .created(new URI("/api/category-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-masters/:id} : Updates an existing categoryMaster.
     *
     * @param id the id of the categoryMaster to save.
     * @param categoryMaster the categoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryMaster,
     * or with status {@code 400 (Bad Request)} if the categoryMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CategoryMaster> updateCategoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryMaster categoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to update CategoryMaster : {}, {}", id, categoryMaster);
        if (categoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoryMaster result = categoryMasterService.update(categoryMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoryMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /category-masters/:id} : Partial updates given fields of an existing categoryMaster, field will ignore if it is null
     *
     * @param id the id of the categoryMaster to save.
     * @param categoryMaster the categoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryMaster,
     * or with status {@code 400 (Bad Request)} if the categoryMaster is not valid,
     * or with status {@code 404 (Not Found)} if the categoryMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/category-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<CategoryMaster> partialUpdateCategoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryMaster categoryMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoryMaster partially : {}, {}", id, categoryMaster);
        if (categoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoryMaster> result = categoryMasterService.partialUpdate(categoryMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoryMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /category-masters} : get all the categoryMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryMasters in body.
     */
    @GetMapping("/category-masters")
    public ResponseEntity<List<CategoryMaster>> getAllCategoryMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CategoryMasters");
        Page<CategoryMaster> page = categoryMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-masters/:id} : get the "id" categoryMaster.
     *
     * @param id the id of the categoryMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-masters/{id}")
    public ResponseEntity<CategoryMaster> getCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to get CategoryMaster : {}", id);
        Optional<CategoryMaster> categoryMaster = categoryMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryMaster);
    }

    /**
     * {@code DELETE  /category-masters/:id} : delete the "id" categoryMaster.
     *
     * @param id the id of the categoryMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to delete CategoryMaster : {}", id);
        categoryMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
