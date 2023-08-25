package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.BankMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.repository.BankMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.BankMasterService;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.cbs.middleware.domain.BankMaster}.
 */
@RestController
@RequestMapping("/api")
public class BankMasterResource {

    private final Logger log = LoggerFactory.getLogger(BankMasterResource.class);

    private static final String ENTITY_NAME = "bankMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankMasterService bankMasterService;

    private final BankMasterRepository bankMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public BankMasterResource(BankMasterService bankMasterService, BankMasterRepository bankMasterRepository) {
        this.bankMasterService = bankMasterService;
        this.bankMasterRepository = bankMasterRepository;
    }

    /**
     * {@code POST  /bank-masters} : Create a new bankMaster.
     *
     * @param bankMaster the bankMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankMaster, or with status {@code 400 (Bad Request)} if the bankMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<BankMaster> createBankMaster(@RequestBody BankMaster bankMaster) throws URISyntaxException {
        log.debug("REST request to save BankMaster : {}", bankMaster);
        if (bankMaster.getId() != null) {
            throw new BadRequestAlertException("A new bankMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankMaster result = bankMasterService.save(bankMaster);

        if (result != null) {
            Notification notification = new Notification(
                "Bank Master Created",
                "Bank Master: " + result.getBankName() + " Created",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "BankMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }

        return ResponseEntity
            .created(new URI("/api/bank-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/bank-master-list")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<List<BankMaster>> createBankMasterList(@RequestBody List<BankMaster> bankMasterList) throws URISyntaxException {
        log.debug("REST request to save BankMaster : {}", bankMasterList);
        if (bankMasterList.isEmpty()) {
            throw new BadRequestAlertException("List is empty", ENTITY_NAME, "emptyList");
        }
        List<BankMaster> result = bankMasterRepository.saveAll(bankMasterList);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /bank-masters/:id} : Updates an existing bankMaster.
     *
     * @param id the id of the bankMaster to save.
     * @param bankMaster the bankMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankMaster,
     * or with status {@code 400 (Bad Request)} if the bankMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<BankMaster> updateBankMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankMaster bankMaster
    ) throws URISyntaxException {
        log.debug("REST request to update BankMaster : {}, {}", id, bankMaster);
        if (bankMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankMaster result = bankMasterService.update(bankMaster);

        if (result != null) {
            Notification notification = new Notification(
                "Bank Master Updated",
                "Bank Master: " + result.getBankName() + " Updated",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "BankMasterUpdated" //type
            );
            notificationRepository.save(notification);
        }

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-masters/:id} : Partial updates given fields of an existing bankMaster, field will ignore if it is null
     *
     * @param id the id of the bankMaster to save.
     * @param bankMaster the bankMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankMaster,
     * or with status {@code 400 (Bad Request)} if the bankMaster is not valid,
     * or with status {@code 404 (Not Found)} if the bankMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<BankMaster> partialUpdateBankMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankMaster bankMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankMaster partially : {}, {}", id, bankMaster);
        if (bankMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankMaster> result = bankMasterService.partialUpdate(bankMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-masters} : get all the bankMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankMasters in body.
     */
    @GetMapping("/bank-masters")
    public ResponseEntity<List<BankMaster>> getAllBankMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BankMasters");
        Page<BankMaster> page = bankMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-masters/:id} : get the "id" bankMaster.
     *
     * @param id the id of the bankMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-masters/{id}")
    public ResponseEntity<BankMaster> getBankMaster(@PathVariable Long id) {
        log.debug("REST request to get BankMaster : {}", id);
        Optional<BankMaster> bankMaster = bankMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankMaster);
    }

    /**
     * {@code DELETE  /bank-masters/:id} : delete the "id" bankMaster.
     *
     * @param id the id of the bankMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteBankMaster(@PathVariable Long id) {
        log.debug("REST request to delete BankMaster : {}", id);
        Optional<BankMaster> bankMaster = bankMasterService.findOne(id);
        if (!bankMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BankMaster result = bankMaster.get();

        bankMasterService.delete(id);

        if (result != null) {
            Notification notification = new Notification(
                "Bank Master Created",
                "Bank Master: " + result.getBankName() + " deleted",
                false,
                result.getCreatedDate(),
                "", //recipient
                result.getCreatedBy(), //sender
                "BankMasterDeleted" //type
            );
            notificationRepository.save(notification);
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
