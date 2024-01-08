package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.domain.KmDetails;
import com.cbs.middleware.domain.KmLoans;
import com.cbs.middleware.repository.KmLoansRepository;
import com.cbs.middleware.service.criteria.KmLoansCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KmLoansResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KmLoansResourceIT {

    private static final Double DEFAULT_HECTOR = 1D;
    private static final Double UPDATED_HECTOR = 2D;
    private static final Double SMALLER_HECTOR = 1D - 1D;

    private static final String DEFAULT_HECTOR_MR = "AAAAAAAAAA";
    private static final String UPDATED_HECTOR_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_ARE = 1D;
    private static final Double UPDATED_ARE = 2D;
    private static final Double SMALLER_ARE = 1D - 1D;

    private static final String DEFAULT_AREMR = "AAAAAAAAAA";
    private static final String UPDATED_AREMR = "BBBBBBBBBB";

    private static final Double DEFAULT_NO_OF_TREE = 1D;
    private static final Double UPDATED_NO_OF_TREE = 2D;
    private static final Double SMALLER_NO_OF_TREE = 1D - 1D;

    private static final String DEFAULT_NO_OF_TREE_MR = "AAAAAAAAAA";
    private static final String UPDATED_NO_OF_TREE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_SANCTION_AMT = 1D;
    private static final Double UPDATED_SANCTION_AMT = 2D;
    private static final Double SMALLER_SANCTION_AMT = 1D - 1D;

    private static final String DEFAULT_SANCTION_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_SANCTION_AMT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_LOAN_AMT = 1D;
    private static final Double UPDATED_LOAN_AMT = 2D;
    private static final Double SMALLER_LOAN_AMT = 1D - 1D;

    private static final String DEFAULT_LOAN_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_AMT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_RECEIVABLE_AMT = 1D;
    private static final Double UPDATED_RECEIVABLE_AMT = 2D;
    private static final Double SMALLER_RECEIVABLE_AMT = 1D - 1D;

    private static final String DEFAULT_RECEIVABLE_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVABLE_AMT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DUE_AMT = 1D;
    private static final Double UPDATED_DUE_AMT = 2D;
    private static final Double SMALLER_DUE_AMT = 1D - 1D;

    private static final String DEFAULT_DUE_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_DUE_AMT_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DUE_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_DUE_DATE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_SPARE = "AAAAAAAAAA";
    private static final String UPDATED_SPARE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/km-loans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KmLoansRepository kmLoansRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKmLoansMockMvc;

    private KmLoans kmLoans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmLoans createEntity(EntityManager em) {
        KmLoans kmLoans = new KmLoans()
            .hector(DEFAULT_HECTOR)
            .hectorMr(DEFAULT_HECTOR_MR)
            .are(DEFAULT_ARE)
            .aremr(DEFAULT_AREMR)
            .noOfTree(DEFAULT_NO_OF_TREE)
            .noOfTreeMr(DEFAULT_NO_OF_TREE_MR)
            .sanctionAmt(DEFAULT_SANCTION_AMT)
            .sanctionAmtMr(DEFAULT_SANCTION_AMT_MR)
            .loanAmt(DEFAULT_LOAN_AMT)
            .loanAmtMr(DEFAULT_LOAN_AMT_MR)
            .receivableAmt(DEFAULT_RECEIVABLE_AMT)
            .receivableAmtMr(DEFAULT_RECEIVABLE_AMT_MR)
            .dueAmt(DEFAULT_DUE_AMT)
            .dueAmtMr(DEFAULT_DUE_AMT_MR)
            .dueDate(DEFAULT_DUE_DATE)
            .dueDateMr(DEFAULT_DUE_DATE_MR)
            .spare(DEFAULT_SPARE);
        return kmLoans;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmLoans createUpdatedEntity(EntityManager em) {
        KmLoans kmLoans = new KmLoans()
            .hector(UPDATED_HECTOR)
            .hectorMr(UPDATED_HECTOR_MR)
            .are(UPDATED_ARE)
            .aremr(UPDATED_AREMR)
            .noOfTree(UPDATED_NO_OF_TREE)
            .noOfTreeMr(UPDATED_NO_OF_TREE_MR)
            .sanctionAmt(UPDATED_SANCTION_AMT)
            .sanctionAmtMr(UPDATED_SANCTION_AMT_MR)
            .loanAmt(UPDATED_LOAN_AMT)
            .loanAmtMr(UPDATED_LOAN_AMT_MR)
            .receivableAmt(UPDATED_RECEIVABLE_AMT)
            .receivableAmtMr(UPDATED_RECEIVABLE_AMT_MR)
            .dueAmt(UPDATED_DUE_AMT)
            .dueAmtMr(UPDATED_DUE_AMT_MR)
            .dueDate(UPDATED_DUE_DATE)
            .dueDateMr(UPDATED_DUE_DATE_MR)
            .spare(UPDATED_SPARE);
        return kmLoans;
    }

    @BeforeEach
    public void initTest() {
        kmLoans = createEntity(em);
    }

    @Test
    @Transactional
    void createKmLoans() throws Exception {
        int databaseSizeBeforeCreate = kmLoansRepository.findAll().size();
        // Create the KmLoans
        restKmLoansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmLoans)))
            .andExpect(status().isCreated());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeCreate + 1);
        KmLoans testKmLoans = kmLoansList.get(kmLoansList.size() - 1);
        assertThat(testKmLoans.getHector()).isEqualTo(DEFAULT_HECTOR);
        assertThat(testKmLoans.getHectorMr()).isEqualTo(DEFAULT_HECTOR_MR);
        assertThat(testKmLoans.getAre()).isEqualTo(DEFAULT_ARE);
        assertThat(testKmLoans.getAremr()).isEqualTo(DEFAULT_AREMR);
        assertThat(testKmLoans.getNoOfTree()).isEqualTo(DEFAULT_NO_OF_TREE);
        assertThat(testKmLoans.getNoOfTreeMr()).isEqualTo(DEFAULT_NO_OF_TREE_MR);
        assertThat(testKmLoans.getSanctionAmt()).isEqualTo(DEFAULT_SANCTION_AMT);
        assertThat(testKmLoans.getSanctionAmtMr()).isEqualTo(DEFAULT_SANCTION_AMT_MR);
        assertThat(testKmLoans.getLoanAmt()).isEqualTo(DEFAULT_LOAN_AMT);
        assertThat(testKmLoans.getLoanAmtMr()).isEqualTo(DEFAULT_LOAN_AMT_MR);
        assertThat(testKmLoans.getReceivableAmt()).isEqualTo(DEFAULT_RECEIVABLE_AMT);
        assertThat(testKmLoans.getReceivableAmtMr()).isEqualTo(DEFAULT_RECEIVABLE_AMT_MR);
        assertThat(testKmLoans.getDueAmt()).isEqualTo(DEFAULT_DUE_AMT);
        assertThat(testKmLoans.getDueAmtMr()).isEqualTo(DEFAULT_DUE_AMT_MR);
        assertThat(testKmLoans.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testKmLoans.getDueDateMr()).isEqualTo(DEFAULT_DUE_DATE_MR);
        assertThat(testKmLoans.getSpare()).isEqualTo(DEFAULT_SPARE);
    }

    @Test
    @Transactional
    void createKmLoansWithExistingId() throws Exception {
        // Create the KmLoans with an existing ID
        kmLoans.setId(1L);

        int databaseSizeBeforeCreate = kmLoansRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmLoansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmLoans)))
            .andExpect(status().isBadRequest());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKmLoans() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList
        restKmLoansMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmLoans.getId().intValue())))
            .andExpect(jsonPath("$.[*].hector").value(hasItem(DEFAULT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].hectorMr").value(hasItem(DEFAULT_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].aremr").value(hasItem(DEFAULT_AREMR)))
            .andExpect(jsonPath("$.[*].noOfTree").value(hasItem(DEFAULT_NO_OF_TREE.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfTreeMr").value(hasItem(DEFAULT_NO_OF_TREE_MR)))
            .andExpect(jsonPath("$.[*].sanctionAmt").value(hasItem(DEFAULT_SANCTION_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].sanctionAmtMr").value(hasItem(DEFAULT_SANCTION_AMT_MR)))
            .andExpect(jsonPath("$.[*].loanAmt").value(hasItem(DEFAULT_LOAN_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanAmtMr").value(hasItem(DEFAULT_LOAN_AMT_MR)))
            .andExpect(jsonPath("$.[*].receivableAmt").value(hasItem(DEFAULT_RECEIVABLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivableAmtMr").value(hasItem(DEFAULT_RECEIVABLE_AMT_MR)))
            .andExpect(jsonPath("$.[*].dueAmt").value(hasItem(DEFAULT_DUE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmtMr").value(hasItem(DEFAULT_DUE_AMT_MR)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDateMr").value(hasItem(DEFAULT_DUE_DATE_MR)))
            .andExpect(jsonPath("$.[*].spare").value(hasItem(DEFAULT_SPARE)));
    }

    @Test
    @Transactional
    void getKmLoans() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get the kmLoans
        restKmLoansMockMvc
            .perform(get(ENTITY_API_URL_ID, kmLoans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kmLoans.getId().intValue()))
            .andExpect(jsonPath("$.hector").value(DEFAULT_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.hectorMr").value(DEFAULT_HECTOR_MR))
            .andExpect(jsonPath("$.are").value(DEFAULT_ARE.doubleValue()))
            .andExpect(jsonPath("$.aremr").value(DEFAULT_AREMR))
            .andExpect(jsonPath("$.noOfTree").value(DEFAULT_NO_OF_TREE.doubleValue()))
            .andExpect(jsonPath("$.noOfTreeMr").value(DEFAULT_NO_OF_TREE_MR))
            .andExpect(jsonPath("$.sanctionAmt").value(DEFAULT_SANCTION_AMT.doubleValue()))
            .andExpect(jsonPath("$.sanctionAmtMr").value(DEFAULT_SANCTION_AMT_MR))
            .andExpect(jsonPath("$.loanAmt").value(DEFAULT_LOAN_AMT.doubleValue()))
            .andExpect(jsonPath("$.loanAmtMr").value(DEFAULT_LOAN_AMT_MR))
            .andExpect(jsonPath("$.receivableAmt").value(DEFAULT_RECEIVABLE_AMT.doubleValue()))
            .andExpect(jsonPath("$.receivableAmtMr").value(DEFAULT_RECEIVABLE_AMT_MR))
            .andExpect(jsonPath("$.dueAmt").value(DEFAULT_DUE_AMT.doubleValue()))
            .andExpect(jsonPath("$.dueAmtMr").value(DEFAULT_DUE_AMT_MR))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.dueDateMr").value(DEFAULT_DUE_DATE_MR))
            .andExpect(jsonPath("$.spare").value(DEFAULT_SPARE));
    }

    @Test
    @Transactional
    void getKmLoansByIdFiltering() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        Long id = kmLoans.getId();

        defaultKmLoansShouldBeFound("id.equals=" + id);
        defaultKmLoansShouldNotBeFound("id.notEquals=" + id);

        defaultKmLoansShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKmLoansShouldNotBeFound("id.greaterThan=" + id);

        defaultKmLoansShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKmLoansShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hector equals to DEFAULT_HECTOR
        defaultKmLoansShouldBeFound("hector.equals=" + DEFAULT_HECTOR);

        // Get all the kmLoansList where hector equals to UPDATED_HECTOR
        defaultKmLoansShouldNotBeFound("hector.equals=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hector in DEFAULT_HECTOR or UPDATED_HECTOR
        defaultKmLoansShouldBeFound("hector.in=" + DEFAULT_HECTOR + "," + UPDATED_HECTOR);

        // Get all the kmLoansList where hector equals to UPDATED_HECTOR
        defaultKmLoansShouldNotBeFound("hector.in=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hector is not null
        defaultKmLoansShouldBeFound("hector.specified=true");

        // Get all the kmLoansList where hector is null
        defaultKmLoansShouldNotBeFound("hector.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hector is greater than or equal to DEFAULT_HECTOR
        defaultKmLoansShouldBeFound("hector.greaterThanOrEqual=" + DEFAULT_HECTOR);

        // Get all the kmLoansList where hector is greater than or equal to UPDATED_HECTOR
        defaultKmLoansShouldNotBeFound("hector.greaterThanOrEqual=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hector is less than or equal to DEFAULT_HECTOR
        defaultKmLoansShouldBeFound("hector.lessThanOrEqual=" + DEFAULT_HECTOR);

        // Get all the kmLoansList where hector is less than or equal to SMALLER_HECTOR
        defaultKmLoansShouldNotBeFound("hector.lessThanOrEqual=" + SMALLER_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hector is less than DEFAULT_HECTOR
        defaultKmLoansShouldNotBeFound("hector.lessThan=" + DEFAULT_HECTOR);

        // Get all the kmLoansList where hector is less than UPDATED_HECTOR
        defaultKmLoansShouldBeFound("hector.lessThan=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hector is greater than DEFAULT_HECTOR
        defaultKmLoansShouldNotBeFound("hector.greaterThan=" + DEFAULT_HECTOR);

        // Get all the kmLoansList where hector is greater than SMALLER_HECTOR
        defaultKmLoansShouldBeFound("hector.greaterThan=" + SMALLER_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hectorMr equals to DEFAULT_HECTOR_MR
        defaultKmLoansShouldBeFound("hectorMr.equals=" + DEFAULT_HECTOR_MR);

        // Get all the kmLoansList where hectorMr equals to UPDATED_HECTOR_MR
        defaultKmLoansShouldNotBeFound("hectorMr.equals=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hectorMr in DEFAULT_HECTOR_MR or UPDATED_HECTOR_MR
        defaultKmLoansShouldBeFound("hectorMr.in=" + DEFAULT_HECTOR_MR + "," + UPDATED_HECTOR_MR);

        // Get all the kmLoansList where hectorMr equals to UPDATED_HECTOR_MR
        defaultKmLoansShouldNotBeFound("hectorMr.in=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hectorMr is not null
        defaultKmLoansShouldBeFound("hectorMr.specified=true");

        // Get all the kmLoansList where hectorMr is null
        defaultKmLoansShouldNotBeFound("hectorMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hectorMr contains DEFAULT_HECTOR_MR
        defaultKmLoansShouldBeFound("hectorMr.contains=" + DEFAULT_HECTOR_MR);

        // Get all the kmLoansList where hectorMr contains UPDATED_HECTOR_MR
        defaultKmLoansShouldNotBeFound("hectorMr.contains=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByHectorMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where hectorMr does not contain DEFAULT_HECTOR_MR
        defaultKmLoansShouldNotBeFound("hectorMr.doesNotContain=" + DEFAULT_HECTOR_MR);

        // Get all the kmLoansList where hectorMr does not contain UPDATED_HECTOR_MR
        defaultKmLoansShouldBeFound("hectorMr.doesNotContain=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByAreIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are equals to DEFAULT_ARE
        defaultKmLoansShouldBeFound("are.equals=" + DEFAULT_ARE);

        // Get all the kmLoansList where are equals to UPDATED_ARE
        defaultKmLoansShouldNotBeFound("are.equals=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    void getAllKmLoansByAreIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are in DEFAULT_ARE or UPDATED_ARE
        defaultKmLoansShouldBeFound("are.in=" + DEFAULT_ARE + "," + UPDATED_ARE);

        // Get all the kmLoansList where are equals to UPDATED_ARE
        defaultKmLoansShouldNotBeFound("are.in=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    void getAllKmLoansByAreIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are is not null
        defaultKmLoansShouldBeFound("are.specified=true");

        // Get all the kmLoansList where are is null
        defaultKmLoansShouldNotBeFound("are.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByAreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are is greater than or equal to DEFAULT_ARE
        defaultKmLoansShouldBeFound("are.greaterThanOrEqual=" + DEFAULT_ARE);

        // Get all the kmLoansList where are is greater than or equal to UPDATED_ARE
        defaultKmLoansShouldNotBeFound("are.greaterThanOrEqual=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    void getAllKmLoansByAreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are is less than or equal to DEFAULT_ARE
        defaultKmLoansShouldBeFound("are.lessThanOrEqual=" + DEFAULT_ARE);

        // Get all the kmLoansList where are is less than or equal to SMALLER_ARE
        defaultKmLoansShouldNotBeFound("are.lessThanOrEqual=" + SMALLER_ARE);
    }

    @Test
    @Transactional
    void getAllKmLoansByAreIsLessThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are is less than DEFAULT_ARE
        defaultKmLoansShouldNotBeFound("are.lessThan=" + DEFAULT_ARE);

        // Get all the kmLoansList where are is less than UPDATED_ARE
        defaultKmLoansShouldBeFound("are.lessThan=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    void getAllKmLoansByAreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are is greater than DEFAULT_ARE
        defaultKmLoansShouldNotBeFound("are.greaterThan=" + DEFAULT_ARE);

        // Get all the kmLoansList where are is greater than SMALLER_ARE
        defaultKmLoansShouldBeFound("are.greaterThan=" + SMALLER_ARE);
    }

    @Test
    @Transactional
    void getAllKmLoansByAremrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where aremr equals to DEFAULT_AREMR
        defaultKmLoansShouldBeFound("aremr.equals=" + DEFAULT_AREMR);

        // Get all the kmLoansList where aremr equals to UPDATED_AREMR
        defaultKmLoansShouldNotBeFound("aremr.equals=" + UPDATED_AREMR);
    }

    @Test
    @Transactional
    void getAllKmLoansByAremrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where aremr in DEFAULT_AREMR or UPDATED_AREMR
        defaultKmLoansShouldBeFound("aremr.in=" + DEFAULT_AREMR + "," + UPDATED_AREMR);

        // Get all the kmLoansList where aremr equals to UPDATED_AREMR
        defaultKmLoansShouldNotBeFound("aremr.in=" + UPDATED_AREMR);
    }

    @Test
    @Transactional
    void getAllKmLoansByAremrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where aremr is not null
        defaultKmLoansShouldBeFound("aremr.specified=true");

        // Get all the kmLoansList where aremr is null
        defaultKmLoansShouldNotBeFound("aremr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByAremrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where aremr contains DEFAULT_AREMR
        defaultKmLoansShouldBeFound("aremr.contains=" + DEFAULT_AREMR);

        // Get all the kmLoansList where aremr contains UPDATED_AREMR
        defaultKmLoansShouldNotBeFound("aremr.contains=" + UPDATED_AREMR);
    }

    @Test
    @Transactional
    void getAllKmLoansByAremrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where aremr does not contain DEFAULT_AREMR
        defaultKmLoansShouldNotBeFound("aremr.doesNotContain=" + DEFAULT_AREMR);

        // Get all the kmLoansList where aremr does not contain UPDATED_AREMR
        defaultKmLoansShouldBeFound("aremr.doesNotContain=" + UPDATED_AREMR);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTree equals to DEFAULT_NO_OF_TREE
        defaultKmLoansShouldBeFound("noOfTree.equals=" + DEFAULT_NO_OF_TREE);

        // Get all the kmLoansList where noOfTree equals to UPDATED_NO_OF_TREE
        defaultKmLoansShouldNotBeFound("noOfTree.equals=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTree in DEFAULT_NO_OF_TREE or UPDATED_NO_OF_TREE
        defaultKmLoansShouldBeFound("noOfTree.in=" + DEFAULT_NO_OF_TREE + "," + UPDATED_NO_OF_TREE);

        // Get all the kmLoansList where noOfTree equals to UPDATED_NO_OF_TREE
        defaultKmLoansShouldNotBeFound("noOfTree.in=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTree is not null
        defaultKmLoansShouldBeFound("noOfTree.specified=true");

        // Get all the kmLoansList where noOfTree is null
        defaultKmLoansShouldNotBeFound("noOfTree.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTree is greater than or equal to DEFAULT_NO_OF_TREE
        defaultKmLoansShouldBeFound("noOfTree.greaterThanOrEqual=" + DEFAULT_NO_OF_TREE);

        // Get all the kmLoansList where noOfTree is greater than or equal to UPDATED_NO_OF_TREE
        defaultKmLoansShouldNotBeFound("noOfTree.greaterThanOrEqual=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTree is less than or equal to DEFAULT_NO_OF_TREE
        defaultKmLoansShouldBeFound("noOfTree.lessThanOrEqual=" + DEFAULT_NO_OF_TREE);

        // Get all the kmLoansList where noOfTree is less than or equal to SMALLER_NO_OF_TREE
        defaultKmLoansShouldNotBeFound("noOfTree.lessThanOrEqual=" + SMALLER_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeIsLessThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTree is less than DEFAULT_NO_OF_TREE
        defaultKmLoansShouldNotBeFound("noOfTree.lessThan=" + DEFAULT_NO_OF_TREE);

        // Get all the kmLoansList where noOfTree is less than UPDATED_NO_OF_TREE
        defaultKmLoansShouldBeFound("noOfTree.lessThan=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTree is greater than DEFAULT_NO_OF_TREE
        defaultKmLoansShouldNotBeFound("noOfTree.greaterThan=" + DEFAULT_NO_OF_TREE);

        // Get all the kmLoansList where noOfTree is greater than SMALLER_NO_OF_TREE
        defaultKmLoansShouldBeFound("noOfTree.greaterThan=" + SMALLER_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTreeMr equals to DEFAULT_NO_OF_TREE_MR
        defaultKmLoansShouldBeFound("noOfTreeMr.equals=" + DEFAULT_NO_OF_TREE_MR);

        // Get all the kmLoansList where noOfTreeMr equals to UPDATED_NO_OF_TREE_MR
        defaultKmLoansShouldNotBeFound("noOfTreeMr.equals=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTreeMr in DEFAULT_NO_OF_TREE_MR or UPDATED_NO_OF_TREE_MR
        defaultKmLoansShouldBeFound("noOfTreeMr.in=" + DEFAULT_NO_OF_TREE_MR + "," + UPDATED_NO_OF_TREE_MR);

        // Get all the kmLoansList where noOfTreeMr equals to UPDATED_NO_OF_TREE_MR
        defaultKmLoansShouldNotBeFound("noOfTreeMr.in=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTreeMr is not null
        defaultKmLoansShouldBeFound("noOfTreeMr.specified=true");

        // Get all the kmLoansList where noOfTreeMr is null
        defaultKmLoansShouldNotBeFound("noOfTreeMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTreeMr contains DEFAULT_NO_OF_TREE_MR
        defaultKmLoansShouldBeFound("noOfTreeMr.contains=" + DEFAULT_NO_OF_TREE_MR);

        // Get all the kmLoansList where noOfTreeMr contains UPDATED_NO_OF_TREE_MR
        defaultKmLoansShouldNotBeFound("noOfTreeMr.contains=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByNoOfTreeMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where noOfTreeMr does not contain DEFAULT_NO_OF_TREE_MR
        defaultKmLoansShouldNotBeFound("noOfTreeMr.doesNotContain=" + DEFAULT_NO_OF_TREE_MR);

        // Get all the kmLoansList where noOfTreeMr does not contain UPDATED_NO_OF_TREE_MR
        defaultKmLoansShouldBeFound("noOfTreeMr.doesNotContain=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmt equals to DEFAULT_SANCTION_AMT
        defaultKmLoansShouldBeFound("sanctionAmt.equals=" + DEFAULT_SANCTION_AMT);

        // Get all the kmLoansList where sanctionAmt equals to UPDATED_SANCTION_AMT
        defaultKmLoansShouldNotBeFound("sanctionAmt.equals=" + UPDATED_SANCTION_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmt in DEFAULT_SANCTION_AMT or UPDATED_SANCTION_AMT
        defaultKmLoansShouldBeFound("sanctionAmt.in=" + DEFAULT_SANCTION_AMT + "," + UPDATED_SANCTION_AMT);

        // Get all the kmLoansList where sanctionAmt equals to UPDATED_SANCTION_AMT
        defaultKmLoansShouldNotBeFound("sanctionAmt.in=" + UPDATED_SANCTION_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmt is not null
        defaultKmLoansShouldBeFound("sanctionAmt.specified=true");

        // Get all the kmLoansList where sanctionAmt is null
        defaultKmLoansShouldNotBeFound("sanctionAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmt is greater than or equal to DEFAULT_SANCTION_AMT
        defaultKmLoansShouldBeFound("sanctionAmt.greaterThanOrEqual=" + DEFAULT_SANCTION_AMT);

        // Get all the kmLoansList where sanctionAmt is greater than or equal to UPDATED_SANCTION_AMT
        defaultKmLoansShouldNotBeFound("sanctionAmt.greaterThanOrEqual=" + UPDATED_SANCTION_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmt is less than or equal to DEFAULT_SANCTION_AMT
        defaultKmLoansShouldBeFound("sanctionAmt.lessThanOrEqual=" + DEFAULT_SANCTION_AMT);

        // Get all the kmLoansList where sanctionAmt is less than or equal to SMALLER_SANCTION_AMT
        defaultKmLoansShouldNotBeFound("sanctionAmt.lessThanOrEqual=" + SMALLER_SANCTION_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmt is less than DEFAULT_SANCTION_AMT
        defaultKmLoansShouldNotBeFound("sanctionAmt.lessThan=" + DEFAULT_SANCTION_AMT);

        // Get all the kmLoansList where sanctionAmt is less than UPDATED_SANCTION_AMT
        defaultKmLoansShouldBeFound("sanctionAmt.lessThan=" + UPDATED_SANCTION_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmt is greater than DEFAULT_SANCTION_AMT
        defaultKmLoansShouldNotBeFound("sanctionAmt.greaterThan=" + DEFAULT_SANCTION_AMT);

        // Get all the kmLoansList where sanctionAmt is greater than SMALLER_SANCTION_AMT
        defaultKmLoansShouldBeFound("sanctionAmt.greaterThan=" + SMALLER_SANCTION_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmtMr equals to DEFAULT_SANCTION_AMT_MR
        defaultKmLoansShouldBeFound("sanctionAmtMr.equals=" + DEFAULT_SANCTION_AMT_MR);

        // Get all the kmLoansList where sanctionAmtMr equals to UPDATED_SANCTION_AMT_MR
        defaultKmLoansShouldNotBeFound("sanctionAmtMr.equals=" + UPDATED_SANCTION_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmtMr in DEFAULT_SANCTION_AMT_MR or UPDATED_SANCTION_AMT_MR
        defaultKmLoansShouldBeFound("sanctionAmtMr.in=" + DEFAULT_SANCTION_AMT_MR + "," + UPDATED_SANCTION_AMT_MR);

        // Get all the kmLoansList where sanctionAmtMr equals to UPDATED_SANCTION_AMT_MR
        defaultKmLoansShouldNotBeFound("sanctionAmtMr.in=" + UPDATED_SANCTION_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmtMr is not null
        defaultKmLoansShouldBeFound("sanctionAmtMr.specified=true");

        // Get all the kmLoansList where sanctionAmtMr is null
        defaultKmLoansShouldNotBeFound("sanctionAmtMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmtMr contains DEFAULT_SANCTION_AMT_MR
        defaultKmLoansShouldBeFound("sanctionAmtMr.contains=" + DEFAULT_SANCTION_AMT_MR);

        // Get all the kmLoansList where sanctionAmtMr contains UPDATED_SANCTION_AMT_MR
        defaultKmLoansShouldNotBeFound("sanctionAmtMr.contains=" + UPDATED_SANCTION_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansBySanctionAmtMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where sanctionAmtMr does not contain DEFAULT_SANCTION_AMT_MR
        defaultKmLoansShouldNotBeFound("sanctionAmtMr.doesNotContain=" + DEFAULT_SANCTION_AMT_MR);

        // Get all the kmLoansList where sanctionAmtMr does not contain UPDATED_SANCTION_AMT_MR
        defaultKmLoansShouldBeFound("sanctionAmtMr.doesNotContain=" + UPDATED_SANCTION_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmt equals to DEFAULT_LOAN_AMT
        defaultKmLoansShouldBeFound("loanAmt.equals=" + DEFAULT_LOAN_AMT);

        // Get all the kmLoansList where loanAmt equals to UPDATED_LOAN_AMT
        defaultKmLoansShouldNotBeFound("loanAmt.equals=" + UPDATED_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmt in DEFAULT_LOAN_AMT or UPDATED_LOAN_AMT
        defaultKmLoansShouldBeFound("loanAmt.in=" + DEFAULT_LOAN_AMT + "," + UPDATED_LOAN_AMT);

        // Get all the kmLoansList where loanAmt equals to UPDATED_LOAN_AMT
        defaultKmLoansShouldNotBeFound("loanAmt.in=" + UPDATED_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmt is not null
        defaultKmLoansShouldBeFound("loanAmt.specified=true");

        // Get all the kmLoansList where loanAmt is null
        defaultKmLoansShouldNotBeFound("loanAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmt is greater than or equal to DEFAULT_LOAN_AMT
        defaultKmLoansShouldBeFound("loanAmt.greaterThanOrEqual=" + DEFAULT_LOAN_AMT);

        // Get all the kmLoansList where loanAmt is greater than or equal to UPDATED_LOAN_AMT
        defaultKmLoansShouldNotBeFound("loanAmt.greaterThanOrEqual=" + UPDATED_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmt is less than or equal to DEFAULT_LOAN_AMT
        defaultKmLoansShouldBeFound("loanAmt.lessThanOrEqual=" + DEFAULT_LOAN_AMT);

        // Get all the kmLoansList where loanAmt is less than or equal to SMALLER_LOAN_AMT
        defaultKmLoansShouldNotBeFound("loanAmt.lessThanOrEqual=" + SMALLER_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmt is less than DEFAULT_LOAN_AMT
        defaultKmLoansShouldNotBeFound("loanAmt.lessThan=" + DEFAULT_LOAN_AMT);

        // Get all the kmLoansList where loanAmt is less than UPDATED_LOAN_AMT
        defaultKmLoansShouldBeFound("loanAmt.lessThan=" + UPDATED_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmt is greater than DEFAULT_LOAN_AMT
        defaultKmLoansShouldNotBeFound("loanAmt.greaterThan=" + DEFAULT_LOAN_AMT);

        // Get all the kmLoansList where loanAmt is greater than SMALLER_LOAN_AMT
        defaultKmLoansShouldBeFound("loanAmt.greaterThan=" + SMALLER_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmtMr equals to DEFAULT_LOAN_AMT_MR
        defaultKmLoansShouldBeFound("loanAmtMr.equals=" + DEFAULT_LOAN_AMT_MR);

        // Get all the kmLoansList where loanAmtMr equals to UPDATED_LOAN_AMT_MR
        defaultKmLoansShouldNotBeFound("loanAmtMr.equals=" + UPDATED_LOAN_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmtMr in DEFAULT_LOAN_AMT_MR or UPDATED_LOAN_AMT_MR
        defaultKmLoansShouldBeFound("loanAmtMr.in=" + DEFAULT_LOAN_AMT_MR + "," + UPDATED_LOAN_AMT_MR);

        // Get all the kmLoansList where loanAmtMr equals to UPDATED_LOAN_AMT_MR
        defaultKmLoansShouldNotBeFound("loanAmtMr.in=" + UPDATED_LOAN_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmtMr is not null
        defaultKmLoansShouldBeFound("loanAmtMr.specified=true");

        // Get all the kmLoansList where loanAmtMr is null
        defaultKmLoansShouldNotBeFound("loanAmtMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmtMr contains DEFAULT_LOAN_AMT_MR
        defaultKmLoansShouldBeFound("loanAmtMr.contains=" + DEFAULT_LOAN_AMT_MR);

        // Get all the kmLoansList where loanAmtMr contains UPDATED_LOAN_AMT_MR
        defaultKmLoansShouldNotBeFound("loanAmtMr.contains=" + UPDATED_LOAN_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmtMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmtMr does not contain DEFAULT_LOAN_AMT_MR
        defaultKmLoansShouldNotBeFound("loanAmtMr.doesNotContain=" + DEFAULT_LOAN_AMT_MR);

        // Get all the kmLoansList where loanAmtMr does not contain UPDATED_LOAN_AMT_MR
        defaultKmLoansShouldBeFound("loanAmtMr.doesNotContain=" + UPDATED_LOAN_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt equals to DEFAULT_RECEIVABLE_AMT
        defaultKmLoansShouldBeFound("receivableAmt.equals=" + DEFAULT_RECEIVABLE_AMT);

        // Get all the kmLoansList where receivableAmt equals to UPDATED_RECEIVABLE_AMT
        defaultKmLoansShouldNotBeFound("receivableAmt.equals=" + UPDATED_RECEIVABLE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt in DEFAULT_RECEIVABLE_AMT or UPDATED_RECEIVABLE_AMT
        defaultKmLoansShouldBeFound("receivableAmt.in=" + DEFAULT_RECEIVABLE_AMT + "," + UPDATED_RECEIVABLE_AMT);

        // Get all the kmLoansList where receivableAmt equals to UPDATED_RECEIVABLE_AMT
        defaultKmLoansShouldNotBeFound("receivableAmt.in=" + UPDATED_RECEIVABLE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt is not null
        defaultKmLoansShouldBeFound("receivableAmt.specified=true");

        // Get all the kmLoansList where receivableAmt is null
        defaultKmLoansShouldNotBeFound("receivableAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt is greater than or equal to DEFAULT_RECEIVABLE_AMT
        defaultKmLoansShouldBeFound("receivableAmt.greaterThanOrEqual=" + DEFAULT_RECEIVABLE_AMT);

        // Get all the kmLoansList where receivableAmt is greater than or equal to UPDATED_RECEIVABLE_AMT
        defaultKmLoansShouldNotBeFound("receivableAmt.greaterThanOrEqual=" + UPDATED_RECEIVABLE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt is less than or equal to DEFAULT_RECEIVABLE_AMT
        defaultKmLoansShouldBeFound("receivableAmt.lessThanOrEqual=" + DEFAULT_RECEIVABLE_AMT);

        // Get all the kmLoansList where receivableAmt is less than or equal to SMALLER_RECEIVABLE_AMT
        defaultKmLoansShouldNotBeFound("receivableAmt.lessThanOrEqual=" + SMALLER_RECEIVABLE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt is less than DEFAULT_RECEIVABLE_AMT
        defaultKmLoansShouldNotBeFound("receivableAmt.lessThan=" + DEFAULT_RECEIVABLE_AMT);

        // Get all the kmLoansList where receivableAmt is less than UPDATED_RECEIVABLE_AMT
        defaultKmLoansShouldBeFound("receivableAmt.lessThan=" + UPDATED_RECEIVABLE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt is greater than DEFAULT_RECEIVABLE_AMT
        defaultKmLoansShouldNotBeFound("receivableAmt.greaterThan=" + DEFAULT_RECEIVABLE_AMT);

        // Get all the kmLoansList where receivableAmt is greater than SMALLER_RECEIVABLE_AMT
        defaultKmLoansShouldBeFound("receivableAmt.greaterThan=" + SMALLER_RECEIVABLE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmtMr equals to DEFAULT_RECEIVABLE_AMT_MR
        defaultKmLoansShouldBeFound("receivableAmtMr.equals=" + DEFAULT_RECEIVABLE_AMT_MR);

        // Get all the kmLoansList where receivableAmtMr equals to UPDATED_RECEIVABLE_AMT_MR
        defaultKmLoansShouldNotBeFound("receivableAmtMr.equals=" + UPDATED_RECEIVABLE_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmtMr in DEFAULT_RECEIVABLE_AMT_MR or UPDATED_RECEIVABLE_AMT_MR
        defaultKmLoansShouldBeFound("receivableAmtMr.in=" + DEFAULT_RECEIVABLE_AMT_MR + "," + UPDATED_RECEIVABLE_AMT_MR);

        // Get all the kmLoansList where receivableAmtMr equals to UPDATED_RECEIVABLE_AMT_MR
        defaultKmLoansShouldNotBeFound("receivableAmtMr.in=" + UPDATED_RECEIVABLE_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmtMr is not null
        defaultKmLoansShouldBeFound("receivableAmtMr.specified=true");

        // Get all the kmLoansList where receivableAmtMr is null
        defaultKmLoansShouldNotBeFound("receivableAmtMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmtMr contains DEFAULT_RECEIVABLE_AMT_MR
        defaultKmLoansShouldBeFound("receivableAmtMr.contains=" + DEFAULT_RECEIVABLE_AMT_MR);

        // Get all the kmLoansList where receivableAmtMr contains UPDATED_RECEIVABLE_AMT_MR
        defaultKmLoansShouldNotBeFound("receivableAmtMr.contains=" + UPDATED_RECEIVABLE_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByReceivableAmtMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmtMr does not contain DEFAULT_RECEIVABLE_AMT_MR
        defaultKmLoansShouldNotBeFound("receivableAmtMr.doesNotContain=" + DEFAULT_RECEIVABLE_AMT_MR);

        // Get all the kmLoansList where receivableAmtMr does not contain UPDATED_RECEIVABLE_AMT_MR
        defaultKmLoansShouldBeFound("receivableAmtMr.doesNotContain=" + UPDATED_RECEIVABLE_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt equals to DEFAULT_DUE_AMT
        defaultKmLoansShouldBeFound("dueAmt.equals=" + DEFAULT_DUE_AMT);

        // Get all the kmLoansList where dueAmt equals to UPDATED_DUE_AMT
        defaultKmLoansShouldNotBeFound("dueAmt.equals=" + UPDATED_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt in DEFAULT_DUE_AMT or UPDATED_DUE_AMT
        defaultKmLoansShouldBeFound("dueAmt.in=" + DEFAULT_DUE_AMT + "," + UPDATED_DUE_AMT);

        // Get all the kmLoansList where dueAmt equals to UPDATED_DUE_AMT
        defaultKmLoansShouldNotBeFound("dueAmt.in=" + UPDATED_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt is not null
        defaultKmLoansShouldBeFound("dueAmt.specified=true");

        // Get all the kmLoansList where dueAmt is null
        defaultKmLoansShouldNotBeFound("dueAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt is greater than or equal to DEFAULT_DUE_AMT
        defaultKmLoansShouldBeFound("dueAmt.greaterThanOrEqual=" + DEFAULT_DUE_AMT);

        // Get all the kmLoansList where dueAmt is greater than or equal to UPDATED_DUE_AMT
        defaultKmLoansShouldNotBeFound("dueAmt.greaterThanOrEqual=" + UPDATED_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt is less than or equal to DEFAULT_DUE_AMT
        defaultKmLoansShouldBeFound("dueAmt.lessThanOrEqual=" + DEFAULT_DUE_AMT);

        // Get all the kmLoansList where dueAmt is less than or equal to SMALLER_DUE_AMT
        defaultKmLoansShouldNotBeFound("dueAmt.lessThanOrEqual=" + SMALLER_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt is less than DEFAULT_DUE_AMT
        defaultKmLoansShouldNotBeFound("dueAmt.lessThan=" + DEFAULT_DUE_AMT);

        // Get all the kmLoansList where dueAmt is less than UPDATED_DUE_AMT
        defaultKmLoansShouldBeFound("dueAmt.lessThan=" + UPDATED_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt is greater than DEFAULT_DUE_AMT
        defaultKmLoansShouldNotBeFound("dueAmt.greaterThan=" + DEFAULT_DUE_AMT);

        // Get all the kmLoansList where dueAmt is greater than SMALLER_DUE_AMT
        defaultKmLoansShouldBeFound("dueAmt.greaterThan=" + SMALLER_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmtMr equals to DEFAULT_DUE_AMT_MR
        defaultKmLoansShouldBeFound("dueAmtMr.equals=" + DEFAULT_DUE_AMT_MR);

        // Get all the kmLoansList where dueAmtMr equals to UPDATED_DUE_AMT_MR
        defaultKmLoansShouldNotBeFound("dueAmtMr.equals=" + UPDATED_DUE_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmtMr in DEFAULT_DUE_AMT_MR or UPDATED_DUE_AMT_MR
        defaultKmLoansShouldBeFound("dueAmtMr.in=" + DEFAULT_DUE_AMT_MR + "," + UPDATED_DUE_AMT_MR);

        // Get all the kmLoansList where dueAmtMr equals to UPDATED_DUE_AMT_MR
        defaultKmLoansShouldNotBeFound("dueAmtMr.in=" + UPDATED_DUE_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmtMr is not null
        defaultKmLoansShouldBeFound("dueAmtMr.specified=true");

        // Get all the kmLoansList where dueAmtMr is null
        defaultKmLoansShouldNotBeFound("dueAmtMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmtMr contains DEFAULT_DUE_AMT_MR
        defaultKmLoansShouldBeFound("dueAmtMr.contains=" + DEFAULT_DUE_AMT_MR);

        // Get all the kmLoansList where dueAmtMr contains UPDATED_DUE_AMT_MR
        defaultKmLoansShouldNotBeFound("dueAmtMr.contains=" + UPDATED_DUE_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueAmtMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmtMr does not contain DEFAULT_DUE_AMT_MR
        defaultKmLoansShouldNotBeFound("dueAmtMr.doesNotContain=" + DEFAULT_DUE_AMT_MR);

        // Get all the kmLoansList where dueAmtMr does not contain UPDATED_DUE_AMT_MR
        defaultKmLoansShouldBeFound("dueAmtMr.doesNotContain=" + UPDATED_DUE_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDate equals to DEFAULT_DUE_DATE
        defaultKmLoansShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the kmLoansList where dueDate equals to UPDATED_DUE_DATE
        defaultKmLoansShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultKmLoansShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the kmLoansList where dueDate equals to UPDATED_DUE_DATE
        defaultKmLoansShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDate is not null
        defaultKmLoansShouldBeFound("dueDate.specified=true");

        // Get all the kmLoansList where dueDate is null
        defaultKmLoansShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByDueDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDateMr equals to DEFAULT_DUE_DATE_MR
        defaultKmLoansShouldBeFound("dueDateMr.equals=" + DEFAULT_DUE_DATE_MR);

        // Get all the kmLoansList where dueDateMr equals to UPDATED_DUE_DATE_MR
        defaultKmLoansShouldNotBeFound("dueDateMr.equals=" + UPDATED_DUE_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDateMr in DEFAULT_DUE_DATE_MR or UPDATED_DUE_DATE_MR
        defaultKmLoansShouldBeFound("dueDateMr.in=" + DEFAULT_DUE_DATE_MR + "," + UPDATED_DUE_DATE_MR);

        // Get all the kmLoansList where dueDateMr equals to UPDATED_DUE_DATE_MR
        defaultKmLoansShouldNotBeFound("dueDateMr.in=" + UPDATED_DUE_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDateMr is not null
        defaultKmLoansShouldBeFound("dueDateMr.specified=true");

        // Get all the kmLoansList where dueDateMr is null
        defaultKmLoansShouldNotBeFound("dueDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByDueDateMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDateMr contains DEFAULT_DUE_DATE_MR
        defaultKmLoansShouldBeFound("dueDateMr.contains=" + DEFAULT_DUE_DATE_MR);

        // Get all the kmLoansList where dueDateMr contains UPDATED_DUE_DATE_MR
        defaultKmLoansShouldNotBeFound("dueDateMr.contains=" + UPDATED_DUE_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByDueDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDateMr does not contain DEFAULT_DUE_DATE_MR
        defaultKmLoansShouldNotBeFound("dueDateMr.doesNotContain=" + DEFAULT_DUE_DATE_MR);

        // Get all the kmLoansList where dueDateMr does not contain UPDATED_DUE_DATE_MR
        defaultKmLoansShouldBeFound("dueDateMr.doesNotContain=" + UPDATED_DUE_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansBySpareIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where spare equals to DEFAULT_SPARE
        defaultKmLoansShouldBeFound("spare.equals=" + DEFAULT_SPARE);

        // Get all the kmLoansList where spare equals to UPDATED_SPARE
        defaultKmLoansShouldNotBeFound("spare.equals=" + UPDATED_SPARE);
    }

    @Test
    @Transactional
    void getAllKmLoansBySpareIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where spare in DEFAULT_SPARE or UPDATED_SPARE
        defaultKmLoansShouldBeFound("spare.in=" + DEFAULT_SPARE + "," + UPDATED_SPARE);

        // Get all the kmLoansList where spare equals to UPDATED_SPARE
        defaultKmLoansShouldNotBeFound("spare.in=" + UPDATED_SPARE);
    }

    @Test
    @Transactional
    void getAllKmLoansBySpareIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where spare is not null
        defaultKmLoansShouldBeFound("spare.specified=true");

        // Get all the kmLoansList where spare is null
        defaultKmLoansShouldNotBeFound("spare.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansBySpareContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where spare contains DEFAULT_SPARE
        defaultKmLoansShouldBeFound("spare.contains=" + DEFAULT_SPARE);

        // Get all the kmLoansList where spare contains UPDATED_SPARE
        defaultKmLoansShouldNotBeFound("spare.contains=" + UPDATED_SPARE);
    }

    @Test
    @Transactional
    void getAllKmLoansBySpareNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where spare does not contain DEFAULT_SPARE
        defaultKmLoansShouldNotBeFound("spare.doesNotContain=" + DEFAULT_SPARE);

        // Get all the kmLoansList where spare does not contain UPDATED_SPARE
        defaultKmLoansShouldBeFound("spare.doesNotContain=" + UPDATED_SPARE);
    }

    @Test
    @Transactional
    void getAllKmLoansByCropMasterIsEqualToSomething() throws Exception {
        CropMaster cropMaster;
        if (TestUtil.findAll(em, CropMaster.class).isEmpty()) {
            kmLoansRepository.saveAndFlush(kmLoans);
            cropMaster = CropMasterResourceIT.createEntity(em);
        } else {
            cropMaster = TestUtil.findAll(em, CropMaster.class).get(0);
        }
        em.persist(cropMaster);
        em.flush();
        kmLoans.setCropMaster(cropMaster);
        kmLoansRepository.saveAndFlush(kmLoans);
        Long cropMasterId = cropMaster.getId();

        // Get all the kmLoansList where cropMaster equals to cropMasterId
        defaultKmLoansShouldBeFound("cropMasterId.equals=" + cropMasterId);

        // Get all the kmLoansList where cropMaster equals to (cropMasterId + 1)
        defaultKmLoansShouldNotBeFound("cropMasterId.equals=" + (cropMasterId + 1));
    }

    @Test
    @Transactional
    void getAllKmLoansByKmDetailsIsEqualToSomething() throws Exception {
        KmDetails kmDetails;
        if (TestUtil.findAll(em, KmDetails.class).isEmpty()) {
            kmLoansRepository.saveAndFlush(kmLoans);
            kmDetails = KmDetailsResourceIT.createEntity(em);
        } else {
            kmDetails = TestUtil.findAll(em, KmDetails.class).get(0);
        }
        em.persist(kmDetails);
        em.flush();
        kmLoans.setKmDetails(kmDetails);
        kmLoansRepository.saveAndFlush(kmLoans);
        Long kmDetailsId = kmDetails.getId();

        // Get all the kmLoansList where kmDetails equals to kmDetailsId
        defaultKmLoansShouldBeFound("kmDetailsId.equals=" + kmDetailsId);

        // Get all the kmLoansList where kmDetails equals to (kmDetailsId + 1)
        defaultKmLoansShouldNotBeFound("kmDetailsId.equals=" + (kmDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKmLoansShouldBeFound(String filter) throws Exception {
        restKmLoansMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmLoans.getId().intValue())))
            .andExpect(jsonPath("$.[*].hector").value(hasItem(DEFAULT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].hectorMr").value(hasItem(DEFAULT_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].aremr").value(hasItem(DEFAULT_AREMR)))
            .andExpect(jsonPath("$.[*].noOfTree").value(hasItem(DEFAULT_NO_OF_TREE.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfTreeMr").value(hasItem(DEFAULT_NO_OF_TREE_MR)))
            .andExpect(jsonPath("$.[*].sanctionAmt").value(hasItem(DEFAULT_SANCTION_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].sanctionAmtMr").value(hasItem(DEFAULT_SANCTION_AMT_MR)))
            .andExpect(jsonPath("$.[*].loanAmt").value(hasItem(DEFAULT_LOAN_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanAmtMr").value(hasItem(DEFAULT_LOAN_AMT_MR)))
            .andExpect(jsonPath("$.[*].receivableAmt").value(hasItem(DEFAULT_RECEIVABLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivableAmtMr").value(hasItem(DEFAULT_RECEIVABLE_AMT_MR)))
            .andExpect(jsonPath("$.[*].dueAmt").value(hasItem(DEFAULT_DUE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmtMr").value(hasItem(DEFAULT_DUE_AMT_MR)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDateMr").value(hasItem(DEFAULT_DUE_DATE_MR)))
            .andExpect(jsonPath("$.[*].spare").value(hasItem(DEFAULT_SPARE)));

        // Check, that the count call also returns 1
        restKmLoansMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKmLoansShouldNotBeFound(String filter) throws Exception {
        restKmLoansMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKmLoansMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKmLoans() throws Exception {
        // Get the kmLoans
        restKmLoansMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKmLoans() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();

        // Update the kmLoans
        KmLoans updatedKmLoans = kmLoansRepository.findById(kmLoans.getId()).get();
        // Disconnect from session so that the updates on updatedKmLoans are not directly saved in db
        em.detach(updatedKmLoans);
        updatedKmLoans
            .hector(UPDATED_HECTOR)
            .hectorMr(UPDATED_HECTOR_MR)
            .are(UPDATED_ARE)
            .aremr(UPDATED_AREMR)
            .noOfTree(UPDATED_NO_OF_TREE)
            .noOfTreeMr(UPDATED_NO_OF_TREE_MR)
            .sanctionAmt(UPDATED_SANCTION_AMT)
            .sanctionAmtMr(UPDATED_SANCTION_AMT_MR)
            .loanAmt(UPDATED_LOAN_AMT)
            .loanAmtMr(UPDATED_LOAN_AMT_MR)
            .receivableAmt(UPDATED_RECEIVABLE_AMT)
            .receivableAmtMr(UPDATED_RECEIVABLE_AMT_MR)
            .dueAmt(UPDATED_DUE_AMT)
            .dueAmtMr(UPDATED_DUE_AMT_MR)
            .dueDate(UPDATED_DUE_DATE)
            .dueDateMr(UPDATED_DUE_DATE_MR)
            .spare(UPDATED_SPARE);

        restKmLoansMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKmLoans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKmLoans))
            )
            .andExpect(status().isOk());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate);
        KmLoans testKmLoans = kmLoansList.get(kmLoansList.size() - 1);
        assertThat(testKmLoans.getHector()).isEqualTo(UPDATED_HECTOR);
        assertThat(testKmLoans.getHectorMr()).isEqualTo(UPDATED_HECTOR_MR);
        assertThat(testKmLoans.getAre()).isEqualTo(UPDATED_ARE);
        assertThat(testKmLoans.getAremr()).isEqualTo(UPDATED_AREMR);
        assertThat(testKmLoans.getNoOfTree()).isEqualTo(UPDATED_NO_OF_TREE);
        assertThat(testKmLoans.getNoOfTreeMr()).isEqualTo(UPDATED_NO_OF_TREE_MR);
        assertThat(testKmLoans.getSanctionAmt()).isEqualTo(UPDATED_SANCTION_AMT);
        assertThat(testKmLoans.getSanctionAmtMr()).isEqualTo(UPDATED_SANCTION_AMT_MR);
        assertThat(testKmLoans.getLoanAmt()).isEqualTo(UPDATED_LOAN_AMT);
        assertThat(testKmLoans.getLoanAmtMr()).isEqualTo(UPDATED_LOAN_AMT_MR);
        assertThat(testKmLoans.getReceivableAmt()).isEqualTo(UPDATED_RECEIVABLE_AMT);
        assertThat(testKmLoans.getReceivableAmtMr()).isEqualTo(UPDATED_RECEIVABLE_AMT_MR);
        assertThat(testKmLoans.getDueAmt()).isEqualTo(UPDATED_DUE_AMT);
        assertThat(testKmLoans.getDueAmtMr()).isEqualTo(UPDATED_DUE_AMT_MR);
        assertThat(testKmLoans.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testKmLoans.getDueDateMr()).isEqualTo(UPDATED_DUE_DATE_MR);
        assertThat(testKmLoans.getSpare()).isEqualTo(UPDATED_SPARE);
    }

    @Test
    @Transactional
    void putNonExistingKmLoans() throws Exception {
        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();
        kmLoans.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmLoansMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kmLoans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmLoans))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKmLoans() throws Exception {
        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();
        kmLoans.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmLoansMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmLoans))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKmLoans() throws Exception {
        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();
        kmLoans.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmLoansMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmLoans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKmLoansWithPatch() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();

        // Update the kmLoans using partial update
        KmLoans partialUpdatedKmLoans = new KmLoans();
        partialUpdatedKmLoans.setId(kmLoans.getId());

        partialUpdatedKmLoans
            .hector(UPDATED_HECTOR)
            .hectorMr(UPDATED_HECTOR_MR)
            .noOfTree(UPDATED_NO_OF_TREE)
            .noOfTreeMr(UPDATED_NO_OF_TREE_MR)
            .sanctionAmt(UPDATED_SANCTION_AMT)
            .sanctionAmtMr(UPDATED_SANCTION_AMT_MR)
            .loanAmt(UPDATED_LOAN_AMT)
            .loanAmtMr(UPDATED_LOAN_AMT_MR)
            .dueAmtMr(UPDATED_DUE_AMT_MR)
            .dueDate(UPDATED_DUE_DATE)
            .spare(UPDATED_SPARE);

        restKmLoansMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmLoans.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmLoans))
            )
            .andExpect(status().isOk());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate);
        KmLoans testKmLoans = kmLoansList.get(kmLoansList.size() - 1);
        assertThat(testKmLoans.getHector()).isEqualTo(UPDATED_HECTOR);
        assertThat(testKmLoans.getHectorMr()).isEqualTo(UPDATED_HECTOR_MR);
        assertThat(testKmLoans.getAre()).isEqualTo(DEFAULT_ARE);
        assertThat(testKmLoans.getAremr()).isEqualTo(DEFAULT_AREMR);
        assertThat(testKmLoans.getNoOfTree()).isEqualTo(UPDATED_NO_OF_TREE);
        assertThat(testKmLoans.getNoOfTreeMr()).isEqualTo(UPDATED_NO_OF_TREE_MR);
        assertThat(testKmLoans.getSanctionAmt()).isEqualTo(UPDATED_SANCTION_AMT);
        assertThat(testKmLoans.getSanctionAmtMr()).isEqualTo(UPDATED_SANCTION_AMT_MR);
        assertThat(testKmLoans.getLoanAmt()).isEqualTo(UPDATED_LOAN_AMT);
        assertThat(testKmLoans.getLoanAmtMr()).isEqualTo(UPDATED_LOAN_AMT_MR);
        assertThat(testKmLoans.getReceivableAmt()).isEqualTo(DEFAULT_RECEIVABLE_AMT);
        assertThat(testKmLoans.getReceivableAmtMr()).isEqualTo(DEFAULT_RECEIVABLE_AMT_MR);
        assertThat(testKmLoans.getDueAmt()).isEqualTo(DEFAULT_DUE_AMT);
        assertThat(testKmLoans.getDueAmtMr()).isEqualTo(UPDATED_DUE_AMT_MR);
        assertThat(testKmLoans.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testKmLoans.getDueDateMr()).isEqualTo(DEFAULT_DUE_DATE_MR);
        assertThat(testKmLoans.getSpare()).isEqualTo(UPDATED_SPARE);
    }

    @Test
    @Transactional
    void fullUpdateKmLoansWithPatch() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();

        // Update the kmLoans using partial update
        KmLoans partialUpdatedKmLoans = new KmLoans();
        partialUpdatedKmLoans.setId(kmLoans.getId());

        partialUpdatedKmLoans
            .hector(UPDATED_HECTOR)
            .hectorMr(UPDATED_HECTOR_MR)
            .are(UPDATED_ARE)
            .aremr(UPDATED_AREMR)
            .noOfTree(UPDATED_NO_OF_TREE)
            .noOfTreeMr(UPDATED_NO_OF_TREE_MR)
            .sanctionAmt(UPDATED_SANCTION_AMT)
            .sanctionAmtMr(UPDATED_SANCTION_AMT_MR)
            .loanAmt(UPDATED_LOAN_AMT)
            .loanAmtMr(UPDATED_LOAN_AMT_MR)
            .receivableAmt(UPDATED_RECEIVABLE_AMT)
            .receivableAmtMr(UPDATED_RECEIVABLE_AMT_MR)
            .dueAmt(UPDATED_DUE_AMT)
            .dueAmtMr(UPDATED_DUE_AMT_MR)
            .dueDate(UPDATED_DUE_DATE)
            .dueDateMr(UPDATED_DUE_DATE_MR)
            .spare(UPDATED_SPARE);

        restKmLoansMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmLoans.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmLoans))
            )
            .andExpect(status().isOk());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate);
        KmLoans testKmLoans = kmLoansList.get(kmLoansList.size() - 1);
        assertThat(testKmLoans.getHector()).isEqualTo(UPDATED_HECTOR);
        assertThat(testKmLoans.getHectorMr()).isEqualTo(UPDATED_HECTOR_MR);
        assertThat(testKmLoans.getAre()).isEqualTo(UPDATED_ARE);
        assertThat(testKmLoans.getAremr()).isEqualTo(UPDATED_AREMR);
        assertThat(testKmLoans.getNoOfTree()).isEqualTo(UPDATED_NO_OF_TREE);
        assertThat(testKmLoans.getNoOfTreeMr()).isEqualTo(UPDATED_NO_OF_TREE_MR);
        assertThat(testKmLoans.getSanctionAmt()).isEqualTo(UPDATED_SANCTION_AMT);
        assertThat(testKmLoans.getSanctionAmtMr()).isEqualTo(UPDATED_SANCTION_AMT_MR);
        assertThat(testKmLoans.getLoanAmt()).isEqualTo(UPDATED_LOAN_AMT);
        assertThat(testKmLoans.getLoanAmtMr()).isEqualTo(UPDATED_LOAN_AMT_MR);
        assertThat(testKmLoans.getReceivableAmt()).isEqualTo(UPDATED_RECEIVABLE_AMT);
        assertThat(testKmLoans.getReceivableAmtMr()).isEqualTo(UPDATED_RECEIVABLE_AMT_MR);
        assertThat(testKmLoans.getDueAmt()).isEqualTo(UPDATED_DUE_AMT);
        assertThat(testKmLoans.getDueAmtMr()).isEqualTo(UPDATED_DUE_AMT_MR);
        assertThat(testKmLoans.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testKmLoans.getDueDateMr()).isEqualTo(UPDATED_DUE_DATE_MR);
        assertThat(testKmLoans.getSpare()).isEqualTo(UPDATED_SPARE);
    }

    @Test
    @Transactional
    void patchNonExistingKmLoans() throws Exception {
        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();
        kmLoans.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmLoansMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kmLoans.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmLoans))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKmLoans() throws Exception {
        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();
        kmLoans.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmLoansMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmLoans))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKmLoans() throws Exception {
        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();
        kmLoans.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmLoansMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kmLoans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKmLoans() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        int databaseSizeBeforeDelete = kmLoansRepository.findAll().size();

        // Delete the kmLoans
        restKmLoansMockMvc
            .perform(delete(ENTITY_API_URL_ID, kmLoans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
