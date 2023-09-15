package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.BankMaster;
import com.cbs.middleware.domain.Notification;
import com.cbs.middleware.domain.PacsMaster;
import com.cbs.middleware.domain.domainUtil.BranchForPacksList;
import com.cbs.middleware.repository.BankBranchMasterRepository;
import com.cbs.middleware.repository.BankMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.service.BankBranchMasterService;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.cbs.middleware.domain.BankBranchMaster}.
 */
@RestController
@RequestMapping("/api")
public class BankBranchMasterResource {

    private final Logger log = LoggerFactory.getLogger(BankBranchMasterResource.class);

    private static final String ENTITY_NAME = "bankBranchMaster";

    @Autowired
    BankMasterRepository bankMasterRepository;

    @Autowired
    PacsMasterRepository pacsMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankBranchMasterService bankBranchMasterService;

    private final BankBranchMasterRepository bankBranchMasterRepository;

    public BankBranchMasterResource(
        BankBranchMasterService bankBranchMasterService,
        BankBranchMasterRepository bankBranchMasterRepository
    ) {
        this.bankBranchMasterService = bankBranchMasterService;
        this.bankBranchMasterRepository = bankBranchMasterRepository;
    }

    /**
     * {@code POST  /bank-branch-masters} : Create a new bankBranchMaster.
     *
     * @param bankBranchMaster the bankBranchMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new bankBranchMaster, or with status
     *         {@code 400 (Bad Request)} if the bankBranchMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-branch-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<BankBranchMaster> createBankBranchMaster(@RequestBody BankBranchMaster bankBranchMaster)
        throws URISyntaxException {
        log.debug("REST request to save BankBranchMaster : {}", bankBranchMaster);
        if (bankBranchMaster.getId() != null) {
            throw new BadRequestAlertException("A new bankBranchMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankBranchMaster result = bankBranchMasterService.save(bankBranchMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Bank Branch Master Created",
                    "Bank Branch Master: " + result.getBranchName() + " Created",
                    false,
                    result.getCreatedDate(),
                    "AccountHolderMasterUpdated" //type
                );
            } catch (Exception e) {}
        }

        return ResponseEntity
            .created(new URI("/api/bank-branch-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-branch-masters/:id} : Updates an existing bankBranchMaster.
     *
     * @param id               the id of the bankBranchMaster to save.
     * @param bankBranchMaster the bankBranchMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated bankBranchMaster, or with status
     *         {@code 400 (Bad Request)} if the bankBranchMaster is not valid, or
     *         with status {@code 500 (Internal Server Error)} if the
     *         bankBranchMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-branch-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<BankBranchMaster> updateBankBranchMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankBranchMaster bankBranchMaster
    ) throws URISyntaxException {
        log.debug("REST request to update BankBranchMaster : {}, {}", id, bankBranchMaster);
        if (bankBranchMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankBranchMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankBranchMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankBranchMaster result = bankBranchMasterService.update(bankBranchMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Bank Branch Master Updated",
                    "Bank Branch Master: " + result.getBranchName() + " Updated",
                    false,
                    result.getCreatedDate(),
                    "AccountHolderMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankBranchMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-branch-masters/:id} : Partial updates given fields of an
     * existing bankBranchMaster, field will ignore if it is null
     *
     * @param id               the id of the bankBranchMaster to save.
     * @param bankBranchMaster the bankBranchMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated bankBranchMaster, or with status
     *         {@code 400 (Bad Request)} if the bankBranchMaster is not valid, or
     *         with status {@code 404 (Not Found)} if the bankBranchMaster is not
     *         found, or with status {@code 500 (Internal Server Error)} if the
     *         bankBranchMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-branch-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<BankBranchMaster> partialUpdateBankBranchMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankBranchMaster bankBranchMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankBranchMaster partially : {}, {}", id, bankBranchMaster);
        if (bankBranchMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankBranchMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankBranchMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankBranchMaster> result = bankBranchMasterService.partialUpdate(bankBranchMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankBranchMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-branch-masters} : get all the bankBranchMasters.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of bankBranchMasters in body.
     */
    @GetMapping("/bank-branch-masters")
    public ResponseEntity<List<BankBranchMaster>> getAllBankBranchMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of BankBranchMasters");
        Page<BankBranchMaster> page;
        if (eagerload) {
            page = bankBranchMasterService.findAllWithEagerRelationships(pageable);
        } else {
            page = bankBranchMasterService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/bank-branch-masters-wt-pacs")
    public ResponseEntity<List<BranchForPacksList>> getAllBankBranchMastersWithPacks(@RequestParam(value = "bankName") String bankName) {
        log.debug("REST request to get a page of BankBranchMasters");

        Optional<BankMaster> findOneByBankName = bankMasterRepository.findOneByBankName(bankName);

        if (!findOneByBankName.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        List<BranchForPacksList> branchForPacksListList = new ArrayList<>();
        List<BankBranchMaster> branchForPacksList = bankBranchMasterRepository.findAllByBankMaster(findOneByBankName.get());
        if (!branchForPacksList.isEmpty()) {
            for (BankBranchMaster bankBranchMaster : branchForPacksList) {
                BranchForPacksList branchForPacksListObj = new BranchForPacksList();
                branchForPacksListObj.setBranchName(bankBranchMaster.getBranchName());
                List<PacsMaster> pacsMasterMaster = pacsMasterRepository.findAllByBankBranchMaster(bankBranchMaster);
                branchForPacksListObj.setPacksNameList(pacsMasterMaster.stream().map(PacsMaster::getPacsName).collect(Collectors.toList()));
                branchForPacksListList.add(branchForPacksListObj);
            }

            return ResponseEntity.ok().body(branchForPacksListList);
        } else {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
    }

    /**
     * {@code GET  /bank-branch-masters/:id} : get the "id" bankBranchMaster.
     *
     * @param id the id of the bankBranchMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the bankBranchMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-branch-masters/{id}")
    public ResponseEntity<BankBranchMaster> getBankBranchMaster(@PathVariable Long id) {
        log.debug("REST request to get BankBranchMaster : {}", id);
        Optional<BankBranchMaster> bankBranchMaster = bankBranchMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankBranchMaster);
    }

    /**
     * {@code DELETE  /bank-branch-masters/:id} : delete the "id" bankBranchMaster.
     *
     * @param id the id of the bankBranchMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-branch-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteBankBranchMaster(@PathVariable Long id) {
        log.debug("REST request to delete BankBranchMaster : {}", id);

        Optional<BankBranchMaster> bankBranchMaster = bankBranchMasterService.findOne(id);
        if (!bankBranchMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BankBranchMaster result = bankBranchMaster.get();
        bankBranchMasterService.delete(id);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Bank Branch Master Created",
                    "Bank Branch Master: " + result.getBranchName() + "deleted",
                    false,
                    result.getCreatedDate(),
                    "AccountHolderMasterDeleted" //type
                );
            } catch (Exception e) {}
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
