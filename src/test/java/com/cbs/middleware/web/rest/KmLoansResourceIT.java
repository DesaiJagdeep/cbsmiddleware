package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KmDetails;
import com.cbs.middleware.domain.KmLoans;
import com.cbs.middleware.repository.KmLoansRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_LOAN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_LOAN_AMOUNT = 1D;
    private static final Double UPDATED_LOAN_AMOUNT = 2D;
    private static final Double SMALLER_LOAN_AMOUNT = 1D - 1D;

    private static final String DEFAULT_LOAN_AMOUNT_MR = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_AMOUNT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_ARE = 1D;
    private static final Double UPDATED_ARE = 2D;
    private static final Double SMALLER_ARE = 1D - 1D;

    private static final String DEFAULT_ARE_MR = "AAAAAAAAAA";
    private static final String UPDATED_ARE_MR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/km-loans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

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
            .cropName(DEFAULT_CROP_NAME)
            .cropNameMr(DEFAULT_CROP_NAME_MR)
            .loanDate(DEFAULT_LOAN_DATE)
            .loanAmount(DEFAULT_LOAN_AMOUNT)
            .loanAmountMr(DEFAULT_LOAN_AMOUNT_MR)
            .are(DEFAULT_ARE)
            .areMr(DEFAULT_ARE_MR)
            .receivableAmt(DEFAULT_RECEIVABLE_AMT)
            .receivableAmtMr(DEFAULT_RECEIVABLE_AMT_MR)
            .dueAmt(DEFAULT_DUE_AMT)
            .dueAmtMr(DEFAULT_DUE_AMT_MR)
            .dueDate(DEFAULT_DUE_DATE);
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
            .cropName(UPDATED_CROP_NAME)
            .cropNameMr(UPDATED_CROP_NAME_MR)
            .loanDate(UPDATED_LOAN_DATE)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanAmountMr(UPDATED_LOAN_AMOUNT_MR)
            .are(UPDATED_ARE)
            .areMr(UPDATED_ARE_MR)
            .receivableAmt(UPDATED_RECEIVABLE_AMT)
            .receivableAmtMr(UPDATED_RECEIVABLE_AMT_MR)
            .dueAmt(UPDATED_DUE_AMT)
            .dueAmtMr(UPDATED_DUE_AMT_MR)
            .dueDate(UPDATED_DUE_DATE);
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
        assertThat(testKmLoans.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testKmLoans.getCropNameMr()).isEqualTo(DEFAULT_CROP_NAME_MR);
        assertThat(testKmLoans.getLoanDate()).isEqualTo(DEFAULT_LOAN_DATE);
        assertThat(testKmLoans.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testKmLoans.getLoanAmountMr()).isEqualTo(DEFAULT_LOAN_AMOUNT_MR);
        assertThat(testKmLoans.getAre()).isEqualTo(DEFAULT_ARE);
        assertThat(testKmLoans.getAreMr()).isEqualTo(DEFAULT_ARE_MR);
        assertThat(testKmLoans.getReceivableAmt()).isEqualTo(DEFAULT_RECEIVABLE_AMT);
        assertThat(testKmLoans.getReceivableAmtMr()).isEqualTo(DEFAULT_RECEIVABLE_AMT_MR);
        assertThat(testKmLoans.getDueAmt()).isEqualTo(DEFAULT_DUE_AMT);
        assertThat(testKmLoans.getDueAmtMr()).isEqualTo(DEFAULT_DUE_AMT_MR);
        assertThat(testKmLoans.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
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
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].cropNameMr").value(hasItem(DEFAULT_CROP_NAME_MR)))
            .andExpect(jsonPath("$.[*].loanDate").value(hasItem(DEFAULT_LOAN_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanAmountMr").value(hasItem(DEFAULT_LOAN_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].areMr").value(hasItem(DEFAULT_ARE_MR)))
            .andExpect(jsonPath("$.[*].receivableAmt").value(hasItem(DEFAULT_RECEIVABLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivableAmtMr").value(hasItem(DEFAULT_RECEIVABLE_AMT_MR)))
            .andExpect(jsonPath("$.[*].dueAmt").value(hasItem(DEFAULT_DUE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmtMr").value(hasItem(DEFAULT_DUE_AMT_MR)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())));
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
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME))
            .andExpect(jsonPath("$.cropNameMr").value(DEFAULT_CROP_NAME_MR))
            .andExpect(jsonPath("$.loanDate").value(DEFAULT_LOAN_DATE.toString()))
            .andExpect(jsonPath("$.loanAmount").value(DEFAULT_LOAN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.loanAmountMr").value(DEFAULT_LOAN_AMOUNT_MR))
            .andExpect(jsonPath("$.are").value(DEFAULT_ARE.doubleValue()))
            .andExpect(jsonPath("$.areMr").value(DEFAULT_ARE_MR))
            .andExpect(jsonPath("$.receivableAmt").value(DEFAULT_RECEIVABLE_AMT.doubleValue()))
            .andExpect(jsonPath("$.receivableAmtMr").value(DEFAULT_RECEIVABLE_AMT_MR))
            .andExpect(jsonPath("$.dueAmt").value(DEFAULT_DUE_AMT.doubleValue()))
            .andExpect(jsonPath("$.dueAmtMr").value(DEFAULT_DUE_AMT_MR))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()));
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
    void getAllKmLoansByCropNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropName equals to DEFAULT_CROP_NAME
        defaultKmLoansShouldBeFound("cropName.equals=" + DEFAULT_CROP_NAME);

        // Get all the kmLoansList where cropName equals to UPDATED_CROP_NAME
        defaultKmLoansShouldNotBeFound("cropName.equals=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmLoansByCropNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropName in DEFAULT_CROP_NAME or UPDATED_CROP_NAME
        defaultKmLoansShouldBeFound("cropName.in=" + DEFAULT_CROP_NAME + "," + UPDATED_CROP_NAME);

        // Get all the kmLoansList where cropName equals to UPDATED_CROP_NAME
        defaultKmLoansShouldNotBeFound("cropName.in=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmLoansByCropNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropName is not null
        defaultKmLoansShouldBeFound("cropName.specified=true");

        // Get all the kmLoansList where cropName is null
        defaultKmLoansShouldNotBeFound("cropName.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByCropNameContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropName contains DEFAULT_CROP_NAME
        defaultKmLoansShouldBeFound("cropName.contains=" + DEFAULT_CROP_NAME);

        // Get all the kmLoansList where cropName contains UPDATED_CROP_NAME
        defaultKmLoansShouldNotBeFound("cropName.contains=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmLoansByCropNameNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropName does not contain DEFAULT_CROP_NAME
        defaultKmLoansShouldNotBeFound("cropName.doesNotContain=" + DEFAULT_CROP_NAME);

        // Get all the kmLoansList where cropName does not contain UPDATED_CROP_NAME
        defaultKmLoansShouldBeFound("cropName.doesNotContain=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmLoansByCropNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropNameMr equals to DEFAULT_CROP_NAME_MR
        defaultKmLoansShouldBeFound("cropNameMr.equals=" + DEFAULT_CROP_NAME_MR);

        // Get all the kmLoansList where cropNameMr equals to UPDATED_CROP_NAME_MR
        defaultKmLoansShouldNotBeFound("cropNameMr.equals=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByCropNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropNameMr in DEFAULT_CROP_NAME_MR or UPDATED_CROP_NAME_MR
        defaultKmLoansShouldBeFound("cropNameMr.in=" + DEFAULT_CROP_NAME_MR + "," + UPDATED_CROP_NAME_MR);

        // Get all the kmLoansList where cropNameMr equals to UPDATED_CROP_NAME_MR
        defaultKmLoansShouldNotBeFound("cropNameMr.in=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByCropNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropNameMr is not null
        defaultKmLoansShouldBeFound("cropNameMr.specified=true");

        // Get all the kmLoansList where cropNameMr is null
        defaultKmLoansShouldNotBeFound("cropNameMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByCropNameMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropNameMr contains DEFAULT_CROP_NAME_MR
        defaultKmLoansShouldBeFound("cropNameMr.contains=" + DEFAULT_CROP_NAME_MR);

        // Get all the kmLoansList where cropNameMr contains UPDATED_CROP_NAME_MR
        defaultKmLoansShouldNotBeFound("cropNameMr.contains=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByCropNameMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropNameMr does not contain DEFAULT_CROP_NAME_MR
        defaultKmLoansShouldNotBeFound("cropNameMr.doesNotContain=" + DEFAULT_CROP_NAME_MR);

        // Get all the kmLoansList where cropNameMr does not contain UPDATED_CROP_NAME_MR
        defaultKmLoansShouldBeFound("cropNameMr.doesNotContain=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanDate equals to DEFAULT_LOAN_DATE
        defaultKmLoansShouldBeFound("loanDate.equals=" + DEFAULT_LOAN_DATE);

        // Get all the kmLoansList where loanDate equals to UPDATED_LOAN_DATE
        defaultKmLoansShouldNotBeFound("loanDate.equals=" + UPDATED_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanDate in DEFAULT_LOAN_DATE or UPDATED_LOAN_DATE
        defaultKmLoansShouldBeFound("loanDate.in=" + DEFAULT_LOAN_DATE + "," + UPDATED_LOAN_DATE);

        // Get all the kmLoansList where loanDate equals to UPDATED_LOAN_DATE
        defaultKmLoansShouldNotBeFound("loanDate.in=" + UPDATED_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanDate is not null
        defaultKmLoansShouldBeFound("loanDate.specified=true");

        // Get all the kmLoansList where loanDate is null
        defaultKmLoansShouldNotBeFound("loanDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount equals to DEFAULT_LOAN_AMOUNT
        defaultKmLoansShouldBeFound("loanAmount.equals=" + DEFAULT_LOAN_AMOUNT);

        // Get all the kmLoansList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultKmLoansShouldNotBeFound("loanAmount.equals=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount in DEFAULT_LOAN_AMOUNT or UPDATED_LOAN_AMOUNT
        defaultKmLoansShouldBeFound("loanAmount.in=" + DEFAULT_LOAN_AMOUNT + "," + UPDATED_LOAN_AMOUNT);

        // Get all the kmLoansList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultKmLoansShouldNotBeFound("loanAmount.in=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount is not null
        defaultKmLoansShouldBeFound("loanAmount.specified=true");

        // Get all the kmLoansList where loanAmount is null
        defaultKmLoansShouldNotBeFound("loanAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount is greater than or equal to DEFAULT_LOAN_AMOUNT
        defaultKmLoansShouldBeFound("loanAmount.greaterThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the kmLoansList where loanAmount is greater than or equal to UPDATED_LOAN_AMOUNT
        defaultKmLoansShouldNotBeFound("loanAmount.greaterThanOrEqual=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount is less than or equal to DEFAULT_LOAN_AMOUNT
        defaultKmLoansShouldBeFound("loanAmount.lessThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the kmLoansList where loanAmount is less than or equal to SMALLER_LOAN_AMOUNT
        defaultKmLoansShouldNotBeFound("loanAmount.lessThanOrEqual=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount is less than DEFAULT_LOAN_AMOUNT
        defaultKmLoansShouldNotBeFound("loanAmount.lessThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the kmLoansList where loanAmount is less than UPDATED_LOAN_AMOUNT
        defaultKmLoansShouldBeFound("loanAmount.lessThan=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount is greater than DEFAULT_LOAN_AMOUNT
        defaultKmLoansShouldNotBeFound("loanAmount.greaterThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the kmLoansList where loanAmount is greater than SMALLER_LOAN_AMOUNT
        defaultKmLoansShouldBeFound("loanAmount.greaterThan=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmountMr equals to DEFAULT_LOAN_AMOUNT_MR
        defaultKmLoansShouldBeFound("loanAmountMr.equals=" + DEFAULT_LOAN_AMOUNT_MR);

        // Get all the kmLoansList where loanAmountMr equals to UPDATED_LOAN_AMOUNT_MR
        defaultKmLoansShouldNotBeFound("loanAmountMr.equals=" + UPDATED_LOAN_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmountMr in DEFAULT_LOAN_AMOUNT_MR or UPDATED_LOAN_AMOUNT_MR
        defaultKmLoansShouldBeFound("loanAmountMr.in=" + DEFAULT_LOAN_AMOUNT_MR + "," + UPDATED_LOAN_AMOUNT_MR);

        // Get all the kmLoansList where loanAmountMr equals to UPDATED_LOAN_AMOUNT_MR
        defaultKmLoansShouldNotBeFound("loanAmountMr.in=" + UPDATED_LOAN_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmountMr is not null
        defaultKmLoansShouldBeFound("loanAmountMr.specified=true");

        // Get all the kmLoansList where loanAmountMr is null
        defaultKmLoansShouldNotBeFound("loanAmountMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmountMr contains DEFAULT_LOAN_AMOUNT_MR
        defaultKmLoansShouldBeFound("loanAmountMr.contains=" + DEFAULT_LOAN_AMOUNT_MR);

        // Get all the kmLoansList where loanAmountMr contains UPDATED_LOAN_AMOUNT_MR
        defaultKmLoansShouldNotBeFound("loanAmountMr.contains=" + UPDATED_LOAN_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByLoanAmountMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmountMr does not contain DEFAULT_LOAN_AMOUNT_MR
        defaultKmLoansShouldNotBeFound("loanAmountMr.doesNotContain=" + DEFAULT_LOAN_AMOUNT_MR);

        // Get all the kmLoansList where loanAmountMr does not contain UPDATED_LOAN_AMOUNT_MR
        defaultKmLoansShouldBeFound("loanAmountMr.doesNotContain=" + UPDATED_LOAN_AMOUNT_MR);
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
    void getAllKmLoansByAreMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where areMr equals to DEFAULT_ARE_MR
        defaultKmLoansShouldBeFound("areMr.equals=" + DEFAULT_ARE_MR);

        // Get all the kmLoansList where areMr equals to UPDATED_ARE_MR
        defaultKmLoansShouldNotBeFound("areMr.equals=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByAreMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where areMr in DEFAULT_ARE_MR or UPDATED_ARE_MR
        defaultKmLoansShouldBeFound("areMr.in=" + DEFAULT_ARE_MR + "," + UPDATED_ARE_MR);

        // Get all the kmLoansList where areMr equals to UPDATED_ARE_MR
        defaultKmLoansShouldNotBeFound("areMr.in=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByAreMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where areMr is not null
        defaultKmLoansShouldBeFound("areMr.specified=true");

        // Get all the kmLoansList where areMr is null
        defaultKmLoansShouldNotBeFound("areMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmLoansByAreMrContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where areMr contains DEFAULT_ARE_MR
        defaultKmLoansShouldBeFound("areMr.contains=" + DEFAULT_ARE_MR);

        // Get all the kmLoansList where areMr contains UPDATED_ARE_MR
        defaultKmLoansShouldNotBeFound("areMr.contains=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmLoansByAreMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where areMr does not contain DEFAULT_ARE_MR
        defaultKmLoansShouldNotBeFound("areMr.doesNotContain=" + DEFAULT_ARE_MR);

        // Get all the kmLoansList where areMr does not contain UPDATED_ARE_MR
        defaultKmLoansShouldBeFound("areMr.doesNotContain=" + UPDATED_ARE_MR);
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
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].cropNameMr").value(hasItem(DEFAULT_CROP_NAME_MR)))
            .andExpect(jsonPath("$.[*].loanDate").value(hasItem(DEFAULT_LOAN_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanAmountMr").value(hasItem(DEFAULT_LOAN_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].areMr").value(hasItem(DEFAULT_ARE_MR)))
            .andExpect(jsonPath("$.[*].receivableAmt").value(hasItem(DEFAULT_RECEIVABLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivableAmtMr").value(hasItem(DEFAULT_RECEIVABLE_AMT_MR)))
            .andExpect(jsonPath("$.[*].dueAmt").value(hasItem(DEFAULT_DUE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmtMr").value(hasItem(DEFAULT_DUE_AMT_MR)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())));

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
        KmLoans updatedKmLoans = kmLoansRepository.findById(kmLoans.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKmLoans are not directly saved in db
        em.detach(updatedKmLoans);
        updatedKmLoans
            .cropName(UPDATED_CROP_NAME)
            .cropNameMr(UPDATED_CROP_NAME_MR)
            .loanDate(UPDATED_LOAN_DATE)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanAmountMr(UPDATED_LOAN_AMOUNT_MR)
            .are(UPDATED_ARE)
            .areMr(UPDATED_ARE_MR)
            .receivableAmt(UPDATED_RECEIVABLE_AMT)
            .receivableAmtMr(UPDATED_RECEIVABLE_AMT_MR)
            .dueAmt(UPDATED_DUE_AMT)
            .dueAmtMr(UPDATED_DUE_AMT_MR)
            .dueDate(UPDATED_DUE_DATE);

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
        assertThat(testKmLoans.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testKmLoans.getCropNameMr()).isEqualTo(UPDATED_CROP_NAME_MR);
        assertThat(testKmLoans.getLoanDate()).isEqualTo(UPDATED_LOAN_DATE);
        assertThat(testKmLoans.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testKmLoans.getLoanAmountMr()).isEqualTo(UPDATED_LOAN_AMOUNT_MR);
        assertThat(testKmLoans.getAre()).isEqualTo(UPDATED_ARE);
        assertThat(testKmLoans.getAreMr()).isEqualTo(UPDATED_ARE_MR);
        assertThat(testKmLoans.getReceivableAmt()).isEqualTo(UPDATED_RECEIVABLE_AMT);
        assertThat(testKmLoans.getReceivableAmtMr()).isEqualTo(UPDATED_RECEIVABLE_AMT_MR);
        assertThat(testKmLoans.getDueAmt()).isEqualTo(UPDATED_DUE_AMT);
        assertThat(testKmLoans.getDueAmtMr()).isEqualTo(UPDATED_DUE_AMT_MR);
        assertThat(testKmLoans.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingKmLoans() throws Exception {
        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();
        kmLoans.setId(longCount.incrementAndGet());

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
        kmLoans.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmLoansMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
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
        kmLoans.setId(longCount.incrementAndGet());

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

        partialUpdatedKmLoans.loanDate(UPDATED_LOAN_DATE).receivableAmt(UPDATED_RECEIVABLE_AMT).receivableAmtMr(UPDATED_RECEIVABLE_AMT_MR);

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
        assertThat(testKmLoans.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testKmLoans.getCropNameMr()).isEqualTo(DEFAULT_CROP_NAME_MR);
        assertThat(testKmLoans.getLoanDate()).isEqualTo(UPDATED_LOAN_DATE);
        assertThat(testKmLoans.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testKmLoans.getLoanAmountMr()).isEqualTo(DEFAULT_LOAN_AMOUNT_MR);
        assertThat(testKmLoans.getAre()).isEqualTo(DEFAULT_ARE);
        assertThat(testKmLoans.getAreMr()).isEqualTo(DEFAULT_ARE_MR);
        assertThat(testKmLoans.getReceivableAmt()).isEqualTo(UPDATED_RECEIVABLE_AMT);
        assertThat(testKmLoans.getReceivableAmtMr()).isEqualTo(UPDATED_RECEIVABLE_AMT_MR);
        assertThat(testKmLoans.getDueAmt()).isEqualTo(DEFAULT_DUE_AMT);
        assertThat(testKmLoans.getDueAmtMr()).isEqualTo(DEFAULT_DUE_AMT_MR);
        assertThat(testKmLoans.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
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
            .cropName(UPDATED_CROP_NAME)
            .cropNameMr(UPDATED_CROP_NAME_MR)
            .loanDate(UPDATED_LOAN_DATE)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanAmountMr(UPDATED_LOAN_AMOUNT_MR)
            .are(UPDATED_ARE)
            .areMr(UPDATED_ARE_MR)
            .receivableAmt(UPDATED_RECEIVABLE_AMT)
            .receivableAmtMr(UPDATED_RECEIVABLE_AMT_MR)
            .dueAmt(UPDATED_DUE_AMT)
            .dueAmtMr(UPDATED_DUE_AMT_MR)
            .dueDate(UPDATED_DUE_DATE);

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
        assertThat(testKmLoans.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testKmLoans.getCropNameMr()).isEqualTo(UPDATED_CROP_NAME_MR);
        assertThat(testKmLoans.getLoanDate()).isEqualTo(UPDATED_LOAN_DATE);
        assertThat(testKmLoans.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testKmLoans.getLoanAmountMr()).isEqualTo(UPDATED_LOAN_AMOUNT_MR);
        assertThat(testKmLoans.getAre()).isEqualTo(UPDATED_ARE);
        assertThat(testKmLoans.getAreMr()).isEqualTo(UPDATED_ARE_MR);
        assertThat(testKmLoans.getReceivableAmt()).isEqualTo(UPDATED_RECEIVABLE_AMT);
        assertThat(testKmLoans.getReceivableAmtMr()).isEqualTo(UPDATED_RECEIVABLE_AMT_MR);
        assertThat(testKmLoans.getDueAmt()).isEqualTo(UPDATED_DUE_AMT);
        assertThat(testKmLoans.getDueAmtMr()).isEqualTo(UPDATED_DUE_AMT_MR);
        assertThat(testKmLoans.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingKmLoans() throws Exception {
        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();
        kmLoans.setId(longCount.incrementAndGet());

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
        kmLoans.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmLoansMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
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
        kmLoans.setId(longCount.incrementAndGet());

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
