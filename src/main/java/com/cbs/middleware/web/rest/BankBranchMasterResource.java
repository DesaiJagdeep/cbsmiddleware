package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.BankBranchMasterMapper;
import com.cbs.middleware.domain.BankMaster;
import com.cbs.middleware.domain.BranchDetailsMapper;
import com.cbs.middleware.domain.CBSResponce;
import com.cbs.middleware.repository.BankBranchMasterRepository;
import com.cbs.middleware.repository.BankMasterRepository;
import com.cbs.middleware.service.BankBranchMasterService;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.BankBranchMaster}.
 */
@RestController
@RequestMapping("/api")
public class BankBranchMasterResource {

    private final Logger log = LoggerFactory.getLogger(BankBranchMasterResource.class);

    private static final String ENTITY_NAME = "bankBranchMaster";

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
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankBranchMaster, or with status {@code 400 (Bad Request)} if the bankBranchMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-branch-masters")
    public ResponseEntity<BankBranchMaster> createBankBranchMaster(@RequestBody BankBranchMaster bankBranchMaster)
        throws URISyntaxException {
        log.debug("REST request to save BankBranchMaster : {}", bankBranchMaster);
        if (bankBranchMaster.getId() != null) {
            throw new BadRequestAlertException("A new bankBranchMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankBranchMaster result = bankBranchMasterService.save(bankBranchMaster);
        return ResponseEntity
            .created(new URI("/api/bank-branch-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-branch-masters/:id} : Updates an existing bankBranchMaster.
     *
     * @param id the id of the bankBranchMaster to save.
     * @param bankBranchMaster the bankBranchMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankBranchMaster,
     * or with status {@code 400 (Bad Request)} if the bankBranchMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankBranchMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-branch-masters/{id}")
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
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankBranchMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-branch-masters/:id} : Partial updates given fields of an existing bankBranchMaster, field will ignore if it is null
     *
     * @param id the id of the bankBranchMaster to save.
     * @param bankBranchMaster the bankBranchMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankBranchMaster,
     * or with status {@code 400 (Bad Request)} if the bankBranchMaster is not valid,
     * or with status {@code 404 (Not Found)} if the bankBranchMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankBranchMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-branch-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
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
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankBranchMasters in body.
     */
    @GetMapping("/bank-branch-masters")
    public ResponseEntity<List<BankBranchMaster>> getAllBankBranchMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BankBranchMasters");
        Page<BankBranchMaster> page = bankBranchMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Autowired
    BankMasterRepository bankMasterRepository;

    @Autowired
    IssFileParserResource fileParserResource;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/save-bank-branch-masters")
    public ResponseEntity<List<BankBranchMasterMapper>> saveAllBankBranchMasters() {
        String cbsResponceString = "";
        List<BankMaster> findAll = bankMasterRepository.findAll();
        List<BankBranchMasterMapper> jsonArrayList = new ArrayList<>();
        for (BankMaster bankMaster : findAll) {
            StringBuilder st = new StringBuilder();
            st.append("{\"bankCode\":\"");
            st.append(bankMaster.getBankCode());
            st.append("\"}");

            String encryption = fileParserResource.encryptionStrings("" + st);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + encryption);
            //Making input payload
            CBSMiddleareInputPayload cbsMiddleareInputPayload = new CBSMiddleareInputPayload();
            cbsMiddleareInputPayload.setAuthCode(Constants.AUTH_CODE);
            cbsMiddleareInputPayload.setData(encryption);

            //call fasalrin submit api
            try {
                // Set the request URL
                String url = applicationProperties.getCBSMiddlewareBaseURL() + "/branches";
                // Set the request headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                // Create the HttpEntity object with headers and body
                HttpEntity<Object> requestEntity = new HttpEntity<>(cbsMiddleareInputPayload, headers);
                // Make the HTTP POST request
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

                cbsResponceString = responseEntity.getBody();

                BankBranchMasterMapper submitApiRespDecryption = null;
                CBSResponce convertValue = null;
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                convertValue = objectMapper.readValue(cbsResponceString, CBSResponce.class);

                if (convertValue.isStatus()) {
                    String decryption = fileParserResource.decryption("" + convertValue.getData());

                    objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    submitApiRespDecryption = objectMapper.readValue(decryption, BankBranchMasterMapper.class);

                    jsonArrayList.add(submitApiRespDecryption);

                    for (BankBranchMasterMapper bankBranchMasterMapper : jsonArrayList) {
                        for (BranchDetailsMapper branchDetailsMapper : bankBranchMasterMapper.getBranches()) {
                            BankBranchMaster bankBranchMaster = new BankBranchMaster();
                            bankBranchMaster.setBankCode(bankBranchMasterMapper.getBankCode());
                            bankBranchMaster.setBranchCode(branchDetailsMapper.getBranchCode());
                            bankBranchMaster.setBranchName(branchDetailsMapper.getBranchName());
                            bankBranchMaster.setIfscCode(branchDetailsMapper.getIfscCode());

                            bankBranchMasterRepository.save(bankBranchMaster);
                        }
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        return ResponseEntity.ok().body(jsonArrayList);
    }

    /**
     * {@code GET  /bank-branch-masters/:id} : get the "id" bankBranchMaster.
     *
     * @param id the id of the bankBranchMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankBranchMaster, or with status {@code 404 (Not Found)}.
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
    public ResponseEntity<Void> deleteBankBranchMaster(@PathVariable Long id) {
        log.debug("REST request to delete BankBranchMaster : {}", id);
        bankBranchMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
