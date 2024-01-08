package com.cbs.middleware.web.rest;

import com.cbs.middleware.CbsMiddlewareApp;

import com.cbs.middleware.domain.KmLoans;
import com.cbs.middleware.repository.KmLoansRepository;
import com.cbs.middleware.service.KmLoansService;
import com.cbs.middleware.web.rest.errors.ExceptionTranslator;
import com.cbs.middleware.service.dto.KmLoansCriteria;
import com.cbs.middleware.service.KmLoansQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KmLoansResource REST controller.
 *
 * @see KmLoansResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CbsMiddlewareApp.class)
public class KmLoansResourceIntTest {

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_LOAN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_LOAN_AMOUNT = 1D;
    private static final Double UPDATED_LOAN_AMOUNT = 2D;

    private static final String DEFAULT_LOAN_AMOUNT_MR = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_AMOUNT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_ARE = 1D;
    private static final Double UPDATED_ARE = 2D;

    private static final String DEFAULT_ARE_MR = "AAAAAAAAAA";
    private static final String UPDATED_ARE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_RECEIVABLE_AMT = 1D;
    private static final Double UPDATED_RECEIVABLE_AMT = 2D;

    private static final String DEFAULT_RECEIVABLE_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVABLE_AMT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DUE_AMT = 1D;
    private static final Double UPDATED_DUE_AMT = 2D;

    private static final String DEFAULT_DUE_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_DUE_AMT_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private KmLoansRepository kmLoansRepository;

    @Autowired
    private KmLoansService kmLoansService;

    @Autowired
    private KmLoansQueryService kmLoansQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKmLoansMockMvc;

    private KmLoans kmLoans;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KmLoansResource kmLoansResource = new KmLoansResource(kmLoansService, kmLoansQueryService);
        this.restKmLoansMockMvc = MockMvcBuilders.standaloneSetup(kmLoansResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmLoans createEntity(EntityManager em) {
        KmLoans kmLoans = new KmLoans();
        kmLoans.setCropName(DEFAULT_CROP_NAME);
        kmLoans.setCropNameMr(DEFAULT_CROP_NAME_MR);
        kmLoans.setLoanDate(DEFAULT_LOAN_DATE);
        kmLoans.setLoanAmount(DEFAULT_LOAN_AMOUNT);
        kmLoans.setLoanAmountMr(DEFAULT_LOAN_AMOUNT_MR);
        kmLoans.setAre(DEFAULT_ARE);
        kmLoans.setAreMr(DEFAULT_ARE_MR);
        kmLoans.setReceivableAmt(DEFAULT_RECEIVABLE_AMT);
        kmLoans.setReceivableAmtMr(DEFAULT_RECEIVABLE_AMT_MR);
        kmLoans.setDueAmt(DEFAULT_DUE_AMT);
        kmLoans.setDueAmtMr(DEFAULT_DUE_AMT_MR);
        kmLoans.setDueDate(DEFAULT_DUE_DATE);
        return kmLoans;
    }

    @Before
    public void initTest() {
        kmLoans = createEntity(em);
    }

    @Test
    @Transactional
    public void createKmLoans() throws Exception {
        int databaseSizeBeforeCreate = kmLoansRepository.findAll().size();

        // Create the KmLoans
        restKmLoansMockMvc.perform(post("/api/km-loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmLoans)))
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
    public void createKmLoansWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kmLoansRepository.findAll().size();

        // Create the KmLoans with an existing ID
        kmLoans.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmLoansMockMvc.perform(post("/api/km-loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmLoans)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKmLoans() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList
        restKmLoansMockMvc.perform(get("/api/km-loans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmLoans.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME.toString())))
            .andExpect(jsonPath("$.[*].cropNameMr").value(hasItem(DEFAULT_CROP_NAME_MR.toString())))
            .andExpect(jsonPath("$.[*].loanDate").value(hasItem(DEFAULT_LOAN_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanAmountMr").value(hasItem(DEFAULT_LOAN_AMOUNT_MR.toString())))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].areMr").value(hasItem(DEFAULT_ARE_MR.toString())))
            .andExpect(jsonPath("$.[*].receivableAmt").value(hasItem(DEFAULT_RECEIVABLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivableAmtMr").value(hasItem(DEFAULT_RECEIVABLE_AMT_MR.toString())))
            .andExpect(jsonPath("$.[*].dueAmt").value(hasItem(DEFAULT_DUE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmtMr").value(hasItem(DEFAULT_DUE_AMT_MR.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getKmLoans() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get the kmLoans
        restKmLoansMockMvc.perform(get("/api/km-loans/{id}", kmLoans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kmLoans.getId().intValue()))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME.toString()))
            .andExpect(jsonPath("$.cropNameMr").value(DEFAULT_CROP_NAME_MR.toString()))
            .andExpect(jsonPath("$.loanDate").value(DEFAULT_LOAN_DATE.toString()))
            .andExpect(jsonPath("$.loanAmount").value(DEFAULT_LOAN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.loanAmountMr").value(DEFAULT_LOAN_AMOUNT_MR.toString()))
            .andExpect(jsonPath("$.are").value(DEFAULT_ARE.doubleValue()))
            .andExpect(jsonPath("$.areMr").value(DEFAULT_ARE_MR.toString()))
            .andExpect(jsonPath("$.receivableAmt").value(DEFAULT_RECEIVABLE_AMT.doubleValue()))
            .andExpect(jsonPath("$.receivableAmtMr").value(DEFAULT_RECEIVABLE_AMT_MR.toString()))
            .andExpect(jsonPath("$.dueAmt").value(DEFAULT_DUE_AMT.doubleValue()))
            .andExpect(jsonPath("$.dueAmtMr").value(DEFAULT_DUE_AMT_MR.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllKmLoansByCropNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropName equals to DEFAULT_CROP_NAME
        defaultKmLoansShouldBeFound("cropName.equals=" + DEFAULT_CROP_NAME);

        // Get all the kmLoansList where cropName equals to UPDATED_CROP_NAME
        defaultKmLoansShouldNotBeFound("cropName.equals=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    public void getAllKmLoansByCropNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropName in DEFAULT_CROP_NAME or UPDATED_CROP_NAME
        defaultKmLoansShouldBeFound("cropName.in=" + DEFAULT_CROP_NAME + "," + UPDATED_CROP_NAME);

        // Get all the kmLoansList where cropName equals to UPDATED_CROP_NAME
        defaultKmLoansShouldNotBeFound("cropName.in=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    public void getAllKmLoansByCropNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropName is not null
        defaultKmLoansShouldBeFound("cropName.specified=true");

        // Get all the kmLoansList where cropName is null
        defaultKmLoansShouldNotBeFound("cropName.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByCropNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropNameMr equals to DEFAULT_CROP_NAME_MR
        defaultKmLoansShouldBeFound("cropNameMr.equals=" + DEFAULT_CROP_NAME_MR);

        // Get all the kmLoansList where cropNameMr equals to UPDATED_CROP_NAME_MR
        defaultKmLoansShouldNotBeFound("cropNameMr.equals=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByCropNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropNameMr in DEFAULT_CROP_NAME_MR or UPDATED_CROP_NAME_MR
        defaultKmLoansShouldBeFound("cropNameMr.in=" + DEFAULT_CROP_NAME_MR + "," + UPDATED_CROP_NAME_MR);

        // Get all the kmLoansList where cropNameMr equals to UPDATED_CROP_NAME_MR
        defaultKmLoansShouldNotBeFound("cropNameMr.in=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByCropNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where cropNameMr is not null
        defaultKmLoansShouldBeFound("cropNameMr.specified=true");

        // Get all the kmLoansList where cropNameMr is null
        defaultKmLoansShouldNotBeFound("cropNameMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByLoanDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanDate equals to DEFAULT_LOAN_DATE
        defaultKmLoansShouldBeFound("loanDate.equals=" + DEFAULT_LOAN_DATE);

        // Get all the kmLoansList where loanDate equals to UPDATED_LOAN_DATE
        defaultKmLoansShouldNotBeFound("loanDate.equals=" + UPDATED_LOAN_DATE);
    }

    @Test
    @Transactional
    public void getAllKmLoansByLoanDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanDate in DEFAULT_LOAN_DATE or UPDATED_LOAN_DATE
        defaultKmLoansShouldBeFound("loanDate.in=" + DEFAULT_LOAN_DATE + "," + UPDATED_LOAN_DATE);

        // Get all the kmLoansList where loanDate equals to UPDATED_LOAN_DATE
        defaultKmLoansShouldNotBeFound("loanDate.in=" + UPDATED_LOAN_DATE);
    }

    @Test
    @Transactional
    public void getAllKmLoansByLoanDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanDate is not null
        defaultKmLoansShouldBeFound("loanDate.specified=true");

        // Get all the kmLoansList where loanDate is null
        defaultKmLoansShouldNotBeFound("loanDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByLoanAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount equals to DEFAULT_LOAN_AMOUNT
        defaultKmLoansShouldBeFound("loanAmount.equals=" + DEFAULT_LOAN_AMOUNT);

        // Get all the kmLoansList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultKmLoansShouldNotBeFound("loanAmount.equals=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllKmLoansByLoanAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount in DEFAULT_LOAN_AMOUNT or UPDATED_LOAN_AMOUNT
        defaultKmLoansShouldBeFound("loanAmount.in=" + DEFAULT_LOAN_AMOUNT + "," + UPDATED_LOAN_AMOUNT);

        // Get all the kmLoansList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultKmLoansShouldNotBeFound("loanAmount.in=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllKmLoansByLoanAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmount is not null
        defaultKmLoansShouldBeFound("loanAmount.specified=true");

        // Get all the kmLoansList where loanAmount is null
        defaultKmLoansShouldNotBeFound("loanAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByLoanAmountMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmountMr equals to DEFAULT_LOAN_AMOUNT_MR
        defaultKmLoansShouldBeFound("loanAmountMr.equals=" + DEFAULT_LOAN_AMOUNT_MR);

        // Get all the kmLoansList where loanAmountMr equals to UPDATED_LOAN_AMOUNT_MR
        defaultKmLoansShouldNotBeFound("loanAmountMr.equals=" + UPDATED_LOAN_AMOUNT_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByLoanAmountMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmountMr in DEFAULT_LOAN_AMOUNT_MR or UPDATED_LOAN_AMOUNT_MR
        defaultKmLoansShouldBeFound("loanAmountMr.in=" + DEFAULT_LOAN_AMOUNT_MR + "," + UPDATED_LOAN_AMOUNT_MR);

        // Get all the kmLoansList where loanAmountMr equals to UPDATED_LOAN_AMOUNT_MR
        defaultKmLoansShouldNotBeFound("loanAmountMr.in=" + UPDATED_LOAN_AMOUNT_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByLoanAmountMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where loanAmountMr is not null
        defaultKmLoansShouldBeFound("loanAmountMr.specified=true");

        // Get all the kmLoansList where loanAmountMr is null
        defaultKmLoansShouldNotBeFound("loanAmountMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByAreIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are equals to DEFAULT_ARE
        defaultKmLoansShouldBeFound("are.equals=" + DEFAULT_ARE);

        // Get all the kmLoansList where are equals to UPDATED_ARE
        defaultKmLoansShouldNotBeFound("are.equals=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    public void getAllKmLoansByAreIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are in DEFAULT_ARE or UPDATED_ARE
        defaultKmLoansShouldBeFound("are.in=" + DEFAULT_ARE + "," + UPDATED_ARE);

        // Get all the kmLoansList where are equals to UPDATED_ARE
        defaultKmLoansShouldNotBeFound("are.in=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    public void getAllKmLoansByAreIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where are is not null
        defaultKmLoansShouldBeFound("are.specified=true");

        // Get all the kmLoansList where are is null
        defaultKmLoansShouldNotBeFound("are.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByAreMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where areMr equals to DEFAULT_ARE_MR
        defaultKmLoansShouldBeFound("areMr.equals=" + DEFAULT_ARE_MR);

        // Get all the kmLoansList where areMr equals to UPDATED_ARE_MR
        defaultKmLoansShouldNotBeFound("areMr.equals=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByAreMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where areMr in DEFAULT_ARE_MR or UPDATED_ARE_MR
        defaultKmLoansShouldBeFound("areMr.in=" + DEFAULT_ARE_MR + "," + UPDATED_ARE_MR);

        // Get all the kmLoansList where areMr equals to UPDATED_ARE_MR
        defaultKmLoansShouldNotBeFound("areMr.in=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByAreMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where areMr is not null
        defaultKmLoansShouldBeFound("areMr.specified=true");

        // Get all the kmLoansList where areMr is null
        defaultKmLoansShouldNotBeFound("areMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByReceivableAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt equals to DEFAULT_RECEIVABLE_AMT
        defaultKmLoansShouldBeFound("receivableAmt.equals=" + DEFAULT_RECEIVABLE_AMT);

        // Get all the kmLoansList where receivableAmt equals to UPDATED_RECEIVABLE_AMT
        defaultKmLoansShouldNotBeFound("receivableAmt.equals=" + UPDATED_RECEIVABLE_AMT);
    }

    @Test
    @Transactional
    public void getAllKmLoansByReceivableAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt in DEFAULT_RECEIVABLE_AMT or UPDATED_RECEIVABLE_AMT
        defaultKmLoansShouldBeFound("receivableAmt.in=" + DEFAULT_RECEIVABLE_AMT + "," + UPDATED_RECEIVABLE_AMT);

        // Get all the kmLoansList where receivableAmt equals to UPDATED_RECEIVABLE_AMT
        defaultKmLoansShouldNotBeFound("receivableAmt.in=" + UPDATED_RECEIVABLE_AMT);
    }

    @Test
    @Transactional
    public void getAllKmLoansByReceivableAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmt is not null
        defaultKmLoansShouldBeFound("receivableAmt.specified=true");

        // Get all the kmLoansList where receivableAmt is null
        defaultKmLoansShouldNotBeFound("receivableAmt.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByReceivableAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmtMr equals to DEFAULT_RECEIVABLE_AMT_MR
        defaultKmLoansShouldBeFound("receivableAmtMr.equals=" + DEFAULT_RECEIVABLE_AMT_MR);

        // Get all the kmLoansList where receivableAmtMr equals to UPDATED_RECEIVABLE_AMT_MR
        defaultKmLoansShouldNotBeFound("receivableAmtMr.equals=" + UPDATED_RECEIVABLE_AMT_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByReceivableAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmtMr in DEFAULT_RECEIVABLE_AMT_MR or UPDATED_RECEIVABLE_AMT_MR
        defaultKmLoansShouldBeFound("receivableAmtMr.in=" + DEFAULT_RECEIVABLE_AMT_MR + "," + UPDATED_RECEIVABLE_AMT_MR);

        // Get all the kmLoansList where receivableAmtMr equals to UPDATED_RECEIVABLE_AMT_MR
        defaultKmLoansShouldNotBeFound("receivableAmtMr.in=" + UPDATED_RECEIVABLE_AMT_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByReceivableAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where receivableAmtMr is not null
        defaultKmLoansShouldBeFound("receivableAmtMr.specified=true");

        // Get all the kmLoansList where receivableAmtMr is null
        defaultKmLoansShouldNotBeFound("receivableAmtMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByDueAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt equals to DEFAULT_DUE_AMT
        defaultKmLoansShouldBeFound("dueAmt.equals=" + DEFAULT_DUE_AMT);

        // Get all the kmLoansList where dueAmt equals to UPDATED_DUE_AMT
        defaultKmLoansShouldNotBeFound("dueAmt.equals=" + UPDATED_DUE_AMT);
    }

    @Test
    @Transactional
    public void getAllKmLoansByDueAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt in DEFAULT_DUE_AMT or UPDATED_DUE_AMT
        defaultKmLoansShouldBeFound("dueAmt.in=" + DEFAULT_DUE_AMT + "," + UPDATED_DUE_AMT);

        // Get all the kmLoansList where dueAmt equals to UPDATED_DUE_AMT
        defaultKmLoansShouldNotBeFound("dueAmt.in=" + UPDATED_DUE_AMT);
    }

    @Test
    @Transactional
    public void getAllKmLoansByDueAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmt is not null
        defaultKmLoansShouldBeFound("dueAmt.specified=true");

        // Get all the kmLoansList where dueAmt is null
        defaultKmLoansShouldNotBeFound("dueAmt.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByDueAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmtMr equals to DEFAULT_DUE_AMT_MR
        defaultKmLoansShouldBeFound("dueAmtMr.equals=" + DEFAULT_DUE_AMT_MR);

        // Get all the kmLoansList where dueAmtMr equals to UPDATED_DUE_AMT_MR
        defaultKmLoansShouldNotBeFound("dueAmtMr.equals=" + UPDATED_DUE_AMT_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByDueAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmtMr in DEFAULT_DUE_AMT_MR or UPDATED_DUE_AMT_MR
        defaultKmLoansShouldBeFound("dueAmtMr.in=" + DEFAULT_DUE_AMT_MR + "," + UPDATED_DUE_AMT_MR);

        // Get all the kmLoansList where dueAmtMr equals to UPDATED_DUE_AMT_MR
        defaultKmLoansShouldNotBeFound("dueAmtMr.in=" + UPDATED_DUE_AMT_MR);
    }

    @Test
    @Transactional
    public void getAllKmLoansByDueAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueAmtMr is not null
        defaultKmLoansShouldBeFound("dueAmtMr.specified=true");

        // Get all the kmLoansList where dueAmtMr is null
        defaultKmLoansShouldNotBeFound("dueAmtMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmLoansByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDate equals to DEFAULT_DUE_DATE
        defaultKmLoansShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the kmLoansList where dueDate equals to UPDATED_DUE_DATE
        defaultKmLoansShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllKmLoansByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultKmLoansShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the kmLoansList where dueDate equals to UPDATED_DUE_DATE
        defaultKmLoansShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllKmLoansByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmLoansRepository.saveAndFlush(kmLoans);

        // Get all the kmLoansList where dueDate is not null
        defaultKmLoansShouldBeFound("dueDate.specified=true");

        // Get all the kmLoansList where dueDate is null
        defaultKmLoansShouldNotBeFound("dueDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultKmLoansShouldBeFound(String filter) throws Exception {
        restKmLoansMockMvc.perform(get("/api/km-loans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmLoans.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME.toString())))
            .andExpect(jsonPath("$.[*].cropNameMr").value(hasItem(DEFAULT_CROP_NAME_MR.toString())))
            .andExpect(jsonPath("$.[*].loanDate").value(hasItem(DEFAULT_LOAN_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanAmountMr").value(hasItem(DEFAULT_LOAN_AMOUNT_MR.toString())))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].areMr").value(hasItem(DEFAULT_ARE_MR.toString())))
            .andExpect(jsonPath("$.[*].receivableAmt").value(hasItem(DEFAULT_RECEIVABLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivableAmtMr").value(hasItem(DEFAULT_RECEIVABLE_AMT_MR.toString())))
            .andExpect(jsonPath("$.[*].dueAmt").value(hasItem(DEFAULT_DUE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmtMr").value(hasItem(DEFAULT_DUE_AMT_MR.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultKmLoansShouldNotBeFound(String filter) throws Exception {
        restKmLoansMockMvc.perform(get("/api/km-loans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingKmLoans() throws Exception {
        // Get the kmLoans
        restKmLoansMockMvc.perform(get("/api/km-loans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKmLoans() throws Exception {
        // Initialize the database
        kmLoansService.save(kmLoans);

        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();

        // Update the kmLoans
        KmLoans updatedKmLoans = kmLoansRepository.findOne(kmLoans.getId());
        updatedKmLoans.setCropName(UPDATED_CROP_NAME);
        updatedKmLoans.setCropNameMr(UPDATED_CROP_NAME_MR);
        updatedKmLoans.setLoanDate(UPDATED_LOAN_DATE);
        updatedKmLoans.setLoanAmount(UPDATED_LOAN_AMOUNT);
        updatedKmLoans.setLoanAmountMr(UPDATED_LOAN_AMOUNT_MR);
        updatedKmLoans.setAre(UPDATED_ARE);
        updatedKmLoans.setAreMr(UPDATED_ARE_MR);
        updatedKmLoans.setReceivableAmt(UPDATED_RECEIVABLE_AMT);
        updatedKmLoans.setReceivableAmtMr(UPDATED_RECEIVABLE_AMT_MR);
        updatedKmLoans.setDueAmt(UPDATED_DUE_AMT);
        updatedKmLoans.setDueAmtMr(UPDATED_DUE_AMT_MR);
        updatedKmLoans.setDueDate(UPDATED_DUE_DATE);

        restKmLoansMockMvc.perform(put("/api/km-loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKmLoans)))
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
    public void updateNonExistingKmLoans() throws Exception {
        int databaseSizeBeforeUpdate = kmLoansRepository.findAll().size();

        // Create the KmLoans

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKmLoansMockMvc.perform(put("/api/km-loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmLoans)))
            .andExpect(status().isCreated());

        // Validate the KmLoans in the database
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKmLoans() throws Exception {
        // Initialize the database
        kmLoansService.save(kmLoans);

        int databaseSizeBeforeDelete = kmLoansRepository.findAll().size();

        // Get the kmLoans
        restKmLoansMockMvc.perform(delete("/api/km-loans/{id}", kmLoans.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KmLoans> kmLoansList = kmLoansRepository.findAll();
        assertThat(kmLoansList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmLoans.class);
        KmLoans kmLoans1 = new KmLoans();
        kmLoans1.setId(1L);
        KmLoans kmLoans2 = new KmLoans();
        kmLoans2.setId(kmLoans1.getId());
        assertThat(kmLoans1).isEqualTo(kmLoans2);
        kmLoans2.setId(2L);
        assertThat(kmLoans1).isNotEqualTo(kmLoans2);
        kmLoans1.setId(null);
        assertThat(kmLoans1).isNotEqualTo(kmLoans2);
    }
}
