package com.cbs.middleware.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.service.KmMasterService;
import com.cbs.middleware.web.rest.util.HeaderUtil;
import com.cbs.middleware.web.rest.util.PaginationUtil;
import com.cbs.middleware.service.dto.KmMasterCriteria;
import com.cbs.middleware.service.KmMasterQueryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing KmMaster.
 */
@RestController
@RequestMapping("/api")
public class KmMasterResource {

    private final Logger log = LoggerFactory.getLogger(KmMasterResource.class);

    private static final String ENTITY_NAME = "kmMaster";

    private final KmMasterService kmMasterService;
    private final KmMasterQueryService kmMasterQueryService;

    public KmMasterResource(KmMasterService kmMasterService, KmMasterQueryService kmMasterQueryService) {
        this.kmMasterService = kmMasterService;
        this.kmMasterQueryService = kmMasterQueryService;
    }

    /**
     * POST  /km-masters : Create a new kmMaster.
     *
     * @param kmMaster the kmMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kmMaster, or with status 400 (Bad Request) if the kmMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/km-masters")
    @Timed
    public ResponseEntity<KmMaster> createKmMaster(@RequestBody KmMaster kmMaster) throws URISyntaxException {
        log.debug("REST request to save KmMaster : {}", kmMaster);
        if (kmMaster.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new kmMaster cannot already have an ID")).body(null);
        }
        KmMaster result = kmMasterService.save(kmMaster);
        return ResponseEntity.created(new URI("/api/km-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /km-masters : Updates an existing kmMaster.
     *
     * @param kmMaster the kmMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kmMaster,
     * or with status 400 (Bad Request) if the kmMaster is not valid,
     * or with status 500 (Internal Server Error) if the kmMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/km-masters")
    @Timed
    public ResponseEntity<KmMaster> updateKmMaster(@RequestBody KmMaster kmMaster) throws URISyntaxException {
        log.debug("REST request to update KmMaster : {}", kmMaster);
        if (kmMaster.getId() == null) {
            return createKmMaster(kmMaster);
        }
        KmMaster result = kmMasterService.save(kmMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kmMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /km-masters : get all the kmMasters.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of kmMasters in body
     */
    @GetMapping("/km-masters")
    @Timed
    public ResponseEntity<List<KmMaster>> getAllKmMasters(KmMasterCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get KmMasters by criteria: {}", criteria);
        Page<KmMaster> page = kmMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/km-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /km-masters/:id : get the "id" kmMaster.
     *
     * @param id the id of the kmMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kmMaster, or with status 404 (Not Found)
     */
    @GetMapping("/km-masters/{id}")
    @Timed
    public ResponseEntity<KmMaster> getKmMaster(@PathVariable Long id) {
        log.debug("REST request to get KmMaster : {}", id);
        KmMaster kmMaster = kmMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kmMaster));
    }

    /**
     * DELETE  /km-masters/:id : delete the "id" kmMaster.
     *
     * @param id the id of the kmMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/km-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteKmMaster(@PathVariable Long id) {
        log.debug("REST request to delete KmMaster : {}", id);
        kmMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
