package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
import com.cbs.middleware.service.AccountHolderMasterService;
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
 * REST controller for managing {@link com.cbs.middleware.domain.AccountHolderMaster}.
 */
@RestController
@RequestMapping("/api")
public class AccountHolderMasterResource {

    private final Logger log = LoggerFactory.getLogger(AccountHolderMasterResource.class);

    private static final String ENTITY_NAME = "accountHolderMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountHolderMasterService accountHolderMasterService;

    private final AccountHolderMasterRepository accountHolderMasterRepository;

    public AccountHolderMasterResource(
        AccountHolderMasterService accountHolderMasterService,
        AccountHolderMasterRepository accountHolderMasterRepository
    ) {
        this.accountHolderMasterService = accountHolderMasterService;
        this.accountHolderMasterRepository = accountHolderMasterRepository;
    }

    /**
     * {@code POST  /account-holder-masters} : Create a new accountHolderMaster.
     *
     * @param accountHolderMaster the accountHolderMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountHolderMaster, or with status {@code 400 (Bad Request)} if the accountHolderMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-holder-masters")
    public ResponseEntity<AccountHolderMaster> createAccountHolderMaster(@RequestBody AccountHolderMaster accountHolderMaster)
        throws URISyntaxException {
        log.debug("REST request to save AccountHolderMaster : {}", accountHolderMaster);
        if (accountHolderMaster.getId() != null) {
            throw new BadRequestAlertException("A new accountHolderMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountHolderMaster result = accountHolderMasterService.save(accountHolderMaster);
        return ResponseEntity
            .created(new URI("/api/account-holder-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-holder-masters/:id} : Updates an existing accountHolderMaster.
     *
     * @param id the id of the accountHolderMaster to save.
     * @param accountHolderMaster the accountHolderMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountHolderMaster,
     * or with status {@code 400 (Bad Request)} if the accountHolderMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountHolderMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-holder-masters/{id}")
    public ResponseEntity<AccountHolderMaster> updateAccountHolderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountHolderMaster accountHolderMaster
    ) throws URISyntaxException {
        log.debug("REST request to update AccountHolderMaster : {}, {}", id, accountHolderMaster);
        if (accountHolderMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountHolderMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountHolderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountHolderMaster result = accountHolderMasterService.update(accountHolderMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountHolderMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /account-holder-masters/:id} : Partial updates given fields of an existing accountHolderMaster, field will ignore if it is null
     *
     * @param id the id of the accountHolderMaster to save.
     * @param accountHolderMaster the accountHolderMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountHolderMaster,
     * or with status {@code 400 (Bad Request)} if the accountHolderMaster is not valid,
     * or with status {@code 404 (Not Found)} if the accountHolderMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountHolderMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/account-holder-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountHolderMaster> partialUpdateAccountHolderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountHolderMaster accountHolderMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountHolderMaster partially : {}, {}", id, accountHolderMaster);
        if (accountHolderMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountHolderMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountHolderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountHolderMaster> result = accountHolderMasterService.partialUpdate(accountHolderMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountHolderMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /account-holder-masters} : get all the accountHolderMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountHolderMasters in body.
     */
    @GetMapping("/account-holder-masters")
    public ResponseEntity<List<AccountHolderMaster>> getAllAccountHolderMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AccountHolderMasters");
        Page<AccountHolderMaster> page = accountHolderMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-holder-masters/:id} : get the "id" accountHolderMaster.
     *
     * @param id the id of the accountHolderMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountHolderMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-holder-masters/{id}")
    public ResponseEntity<AccountHolderMaster> getAccountHolderMaster(@PathVariable Long id) {
        log.debug("REST request to get AccountHolderMaster : {}", id);
        Optional<AccountHolderMaster> accountHolderMaster = accountHolderMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountHolderMaster);
    }

    /**
     * {@code DELETE  /account-holder-masters/:id} : delete the "id" accountHolderMaster.
     *
     * @param id the id of the accountHolderMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-holder-masters/{id}")
    public ResponseEntity<Void> deleteAccountHolderMaster(@PathVariable Long id) {
        log.debug("REST request to delete AccountHolderMaster : {}", id);
        accountHolderMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
