package com.cbs.middleware.web.rest;

import com.cbs.middleware.CbsMiddlewareApp;

import com.cbs.middleware.domain.KmDetails;
import com.cbs.middleware.repository.KmDetailsRepository;
import com.cbs.middleware.service.KmDetailsService;
import com.cbs.middleware.web.rest.errors.ExceptionTranslator;
import com.cbs.middleware.service.dto.KmDetailsCriteria;
import com.cbs.middleware.service.KmDetailsQueryService;

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
 * Test class for the KmDetailsResource REST controller.
 *
 * @see KmDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CbsMiddlewareApp.class)
public class KmDetailsResourceIntTest {

    private static final Double DEFAULT_SHARES = 1D;
    private static final Double UPDATED_SHARES = 2D;

    private static final String DEFAULT_SHARES_MR = "AAAAAAAAAA";
    private static final String UPDATED_SHARES_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_SUGAR_SHARES = 1D;
    private static final Double UPDATED_SUGAR_SHARES = 2D;

    private static final String DEFAULT_SUGAR_SHARES_MR = "AAAAAAAAAA";
    private static final String UPDATED_SUGAR_SHARES_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DEPOSIT = 1D;
    private static final Double UPDATED_DEPOSIT = 2D;

    private static final String DEFAULT_DEPOSIT_MR = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSIT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DUE_LOAN = 1D;
    private static final Double UPDATED_DUE_LOAN = 2D;

    private static final String DEFAULT_DUE_LOAN_MR = "AAAAAAAAAA";
    private static final String UPDATED_DUE_LOAN_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DUE_AMOUNT = 1D;
    private static final Double UPDATED_DUE_AMOUNT = 2D;

    private static final String DEFAULT_DUE_AMOUNT_MR = "AAAAAAAAAA";
    private static final String UPDATED_DUE_AMOUNT_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_KM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_KM_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_KM_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_BAGAYAT_HECTOR = 1D;
    private static final Double UPDATED_BAGAYAT_HECTOR = 2D;

    private static final String DEFAULT_BAGAYAT_HECTOR_MR = "AAAAAAAAAA";
    private static final String UPDATED_BAGAYAT_HECTOR_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BAGAYAT_ARE = 1D;
    private static final Double UPDATED_BAGAYAT_ARE = 2D;

    private static final String DEFAULT_BAGAYAT_ARE_MR = "AAAAAAAAAA";
    private static final String UPDATED_BAGAYAT_ARE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_JIRAYAT_HECTOR = 1D;
    private static final Double UPDATED_JIRAYAT_HECTOR = 2D;

    private static final String DEFAULT_JIRAYAT_HECTOR_MR = "AAAAAAAAAA";
    private static final String UPDATED_JIRAYAT_HECTOR_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_JIRAYAT_ARE = 1D;
    private static final Double UPDATED_JIRAYAT_ARE = 2D;

    private static final String DEFAULT_JIRAYAT_ARE_MR = "AAAAAAAAAA";
    private static final String UPDATED_JIRAYAT_ARE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_LAND_VALUE = 1D;
    private static final Double UPDATED_LAND_VALUE = 2D;

    private static final String DEFAULT_LAND_VALUE_MR = "AAAAAAAAAA";
    private static final String UPDATED_LAND_VALUE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_E_AGGREMENT_AMT = 1D;
    private static final Double UPDATED_E_AGGREMENT_AMT = 2D;

    private static final String DEFAULT_E_AGREEMENT_AMT = "AAAAAAAAAA";
    private static final String UPDATED_E_AGREEMENT_AMT = "BBBBBBBBBB";

    private static final Instant DEFAULT_E_AGREEMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_E_AGREEMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_BOJA_AMOUNT = 1D;
    private static final Double UPDATED_BOJA_AMOUNT = 2D;

    private static final String DEFAULT_BOJA_AMOUNT_MR = "AAAAAAAAAA";
    private static final String UPDATED_BOJA_AMOUNT_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_BOJA_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOJA_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private KmDetailsRepository kmDetailsRepository;

    @Autowired
    private KmDetailsService kmDetailsService;

    @Autowired
    private KmDetailsQueryService kmDetailsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKmDetailsMockMvc;

    private KmDetails kmDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KmDetailsResource kmDetailsResource = new KmDetailsResource(kmDetailsService, kmDetailsQueryService);
        this.restKmDetailsMockMvc = MockMvcBuilders.standaloneSetup(kmDetailsResource)
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
    public static KmDetails createEntity(EntityManager em) {
        KmDetails kmDetails = new KmDetails();
        kmDetails.setShares(DEFAULT_SHARES);
        kmDetails.setSharesMr(DEFAULT_SHARES_MR);
        kmDetails.setSugarShares(DEFAULT_SUGAR_SHARES);
        kmDetails.setSugarSharesMr(DEFAULT_SUGAR_SHARES_MR);
        kmDetails.setDeposit(DEFAULT_DEPOSIT);
        kmDetails.setDepositMr(DEFAULT_DEPOSIT_MR);
        kmDetails.setDueLoan(DEFAULT_DUE_LOAN);
        kmDetails.setDueLoanMr(DEFAULT_DUE_LOAN_MR);
        kmDetails.setDueAmount(DEFAULT_DUE_AMOUNT);
        kmDetails.setDueAmountMr(DEFAULT_DUE_AMOUNT_MR);
        kmDetails.setDueDate(DEFAULT_DUE_DATE);
        kmDetails.setKmDate(DEFAULT_KM_DATE);
        kmDetails.setKmFromDate(DEFAULT_KM_FROM_DATE);
        kmDetails.setKmToDate(DEFAULT_KM_TO_DATE);
        kmDetails.setBagayatHector(DEFAULT_BAGAYAT_HECTOR);
        kmDetails.setBagayatHectorMr(DEFAULT_BAGAYAT_HECTOR_MR);
        kmDetails.setBagayatAre(DEFAULT_BAGAYAT_ARE);
        kmDetails.setBagayatAreMr(DEFAULT_BAGAYAT_ARE_MR);
        kmDetails.setJirayatHector(DEFAULT_JIRAYAT_HECTOR);
        kmDetails.setJirayatHectorMr(DEFAULT_JIRAYAT_HECTOR_MR);
        kmDetails.setJirayatAre(DEFAULT_JIRAYAT_ARE);
        kmDetails.setJirayatAreMr(DEFAULT_JIRAYAT_ARE_MR);
        kmDetails.setLandValue(DEFAULT_LAND_VALUE);
        kmDetails.setLandValueMr(DEFAULT_LAND_VALUE_MR);
        kmDetails.seteAggrementAmt(DEFAULT_E_AGGREMENT_AMT);
        kmDetails.seteAgreementAmt(DEFAULT_E_AGREEMENT_AMT);
        kmDetails.seteAgreementDate(DEFAULT_E_AGREEMENT_DATE);
        kmDetails.setBojaAmount(DEFAULT_BOJA_AMOUNT);
        kmDetails.setBojaAmountMr(DEFAULT_BOJA_AMOUNT_MR);
        kmDetails.setBojaDate(DEFAULT_BOJA_DATE);
        return kmDetails;
    }

    @Before
    public void initTest() {
        kmDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createKmDetails() throws Exception {
        int databaseSizeBeforeCreate = kmDetailsRepository.findAll().size();

        // Create the KmDetails
        restKmDetailsMockMvc.perform(post("/api/km-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmDetails)))
            .andExpect(status().isCreated());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        KmDetails testKmDetails = kmDetailsList.get(kmDetailsList.size() - 1);
        assertThat(testKmDetails.getShares()).isEqualTo(DEFAULT_SHARES);
        assertThat(testKmDetails.getSharesMr()).isEqualTo(DEFAULT_SHARES_MR);
        assertThat(testKmDetails.getSugarShares()).isEqualTo(DEFAULT_SUGAR_SHARES);
        assertThat(testKmDetails.getSugarSharesMr()).isEqualTo(DEFAULT_SUGAR_SHARES_MR);
        assertThat(testKmDetails.getDeposit()).isEqualTo(DEFAULT_DEPOSIT);
        assertThat(testKmDetails.getDepositMr()).isEqualTo(DEFAULT_DEPOSIT_MR);
        assertThat(testKmDetails.getDueLoan()).isEqualTo(DEFAULT_DUE_LOAN);
        assertThat(testKmDetails.getDueLoanMr()).isEqualTo(DEFAULT_DUE_LOAN_MR);
        assertThat(testKmDetails.getDueAmount()).isEqualTo(DEFAULT_DUE_AMOUNT);
        assertThat(testKmDetails.getDueAmountMr()).isEqualTo(DEFAULT_DUE_AMOUNT_MR);
        assertThat(testKmDetails.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testKmDetails.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
        assertThat(testKmDetails.getKmFromDate()).isEqualTo(DEFAULT_KM_FROM_DATE);
        assertThat(testKmDetails.getKmToDate()).isEqualTo(DEFAULT_KM_TO_DATE);
        assertThat(testKmDetails.getBagayatHector()).isEqualTo(DEFAULT_BAGAYAT_HECTOR);
        assertThat(testKmDetails.getBagayatHectorMr()).isEqualTo(DEFAULT_BAGAYAT_HECTOR_MR);
        assertThat(testKmDetails.getBagayatAre()).isEqualTo(DEFAULT_BAGAYAT_ARE);
        assertThat(testKmDetails.getBagayatAreMr()).isEqualTo(DEFAULT_BAGAYAT_ARE_MR);
        assertThat(testKmDetails.getJirayatHector()).isEqualTo(DEFAULT_JIRAYAT_HECTOR);
        assertThat(testKmDetails.getJirayatHectorMr()).isEqualTo(DEFAULT_JIRAYAT_HECTOR_MR);
        assertThat(testKmDetails.getJirayatAre()).isEqualTo(DEFAULT_JIRAYAT_ARE);
        assertThat(testKmDetails.getJirayatAreMr()).isEqualTo(DEFAULT_JIRAYAT_ARE_MR);
        assertThat(testKmDetails.getLandValue()).isEqualTo(DEFAULT_LAND_VALUE);
        assertThat(testKmDetails.getLandValueMr()).isEqualTo(DEFAULT_LAND_VALUE_MR);
        assertThat(testKmDetails.geteAggrementAmt()).isEqualTo(DEFAULT_E_AGGREMENT_AMT);
        assertThat(testKmDetails.geteAgreementAmt()).isEqualTo(DEFAULT_E_AGREEMENT_AMT);
        assertThat(testKmDetails.geteAgreementDate()).isEqualTo(DEFAULT_E_AGREEMENT_DATE);
        assertThat(testKmDetails.getBojaAmount()).isEqualTo(DEFAULT_BOJA_AMOUNT);
        assertThat(testKmDetails.getBojaAmountMr()).isEqualTo(DEFAULT_BOJA_AMOUNT_MR);
        assertThat(testKmDetails.getBojaDate()).isEqualTo(DEFAULT_BOJA_DATE);
    }

    @Test
    @Transactional
    public void createKmDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kmDetailsRepository.findAll().size();

        // Create the KmDetails with an existing ID
        kmDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmDetailsMockMvc.perform(post("/api/km-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKmDetails() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList
        restKmDetailsMockMvc.perform(get("/api/km-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].shares").value(hasItem(DEFAULT_SHARES.doubleValue())))
            .andExpect(jsonPath("$.[*].sharesMr").value(hasItem(DEFAULT_SHARES_MR.toString())))
            .andExpect(jsonPath("$.[*].sugarShares").value(hasItem(DEFAULT_SUGAR_SHARES.doubleValue())))
            .andExpect(jsonPath("$.[*].sugarSharesMr").value(hasItem(DEFAULT_SUGAR_SHARES_MR.toString())))
            .andExpect(jsonPath("$.[*].deposit").value(hasItem(DEFAULT_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].depositMr").value(hasItem(DEFAULT_DEPOSIT_MR.toString())))
            .andExpect(jsonPath("$.[*].dueLoan").value(hasItem(DEFAULT_DUE_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].dueLoanMr").value(hasItem(DEFAULT_DUE_LOAN_MR.toString())))
            .andExpect(jsonPath("$.[*].dueAmount").value(hasItem(DEFAULT_DUE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmountMr").value(hasItem(DEFAULT_DUE_AMOUNT_MR.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmFromDate").value(hasItem(DEFAULT_KM_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmToDate").value(hasItem(DEFAULT_KM_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].bagayatHector").value(hasItem(DEFAULT_BAGAYAT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].bagayatHectorMr").value(hasItem(DEFAULT_BAGAYAT_HECTOR_MR.toString())))
            .andExpect(jsonPath("$.[*].bagayatAre").value(hasItem(DEFAULT_BAGAYAT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].bagayatAreMr").value(hasItem(DEFAULT_BAGAYAT_ARE_MR.toString())))
            .andExpect(jsonPath("$.[*].jirayatHector").value(hasItem(DEFAULT_JIRAYAT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].jirayatHectorMr").value(hasItem(DEFAULT_JIRAYAT_HECTOR_MR.toString())))
            .andExpect(jsonPath("$.[*].jirayatAre").value(hasItem(DEFAULT_JIRAYAT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].jirayatAreMr").value(hasItem(DEFAULT_JIRAYAT_ARE_MR.toString())))
            .andExpect(jsonPath("$.[*].landValue").value(hasItem(DEFAULT_LAND_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].landValueMr").value(hasItem(DEFAULT_LAND_VALUE_MR.toString())))
            .andExpect(jsonPath("$.[*].eAggrementAmt").value(hasItem(DEFAULT_E_AGGREMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].eAgreementAmt").value(hasItem(DEFAULT_E_AGREEMENT_AMT.toString())))
            .andExpect(jsonPath("$.[*].eAgreementDate").value(hasItem(DEFAULT_E_AGREEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].bojaAmount").value(hasItem(DEFAULT_BOJA_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].bojaAmountMr").value(hasItem(DEFAULT_BOJA_AMOUNT_MR.toString())))
            .andExpect(jsonPath("$.[*].bojaDate").value(hasItem(DEFAULT_BOJA_DATE.toString())));
    }

    @Test
    @Transactional
    public void getKmDetails() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get the kmDetails
        restKmDetailsMockMvc.perform(get("/api/km-details/{id}", kmDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kmDetails.getId().intValue()))
            .andExpect(jsonPath("$.shares").value(DEFAULT_SHARES.doubleValue()))
            .andExpect(jsonPath("$.sharesMr").value(DEFAULT_SHARES_MR.toString()))
            .andExpect(jsonPath("$.sugarShares").value(DEFAULT_SUGAR_SHARES.doubleValue()))
            .andExpect(jsonPath("$.sugarSharesMr").value(DEFAULT_SUGAR_SHARES_MR.toString()))
            .andExpect(jsonPath("$.deposit").value(DEFAULT_DEPOSIT.doubleValue()))
            .andExpect(jsonPath("$.depositMr").value(DEFAULT_DEPOSIT_MR.toString()))
            .andExpect(jsonPath("$.dueLoan").value(DEFAULT_DUE_LOAN.doubleValue()))
            .andExpect(jsonPath("$.dueLoanMr").value(DEFAULT_DUE_LOAN_MR.toString()))
            .andExpect(jsonPath("$.dueAmount").value(DEFAULT_DUE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dueAmountMr").value(DEFAULT_DUE_AMOUNT_MR.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.kmDate").value(DEFAULT_KM_DATE.toString()))
            .andExpect(jsonPath("$.kmFromDate").value(DEFAULT_KM_FROM_DATE.toString()))
            .andExpect(jsonPath("$.kmToDate").value(DEFAULT_KM_TO_DATE.toString()))
            .andExpect(jsonPath("$.bagayatHector").value(DEFAULT_BAGAYAT_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.bagayatHectorMr").value(DEFAULT_BAGAYAT_HECTOR_MR.toString()))
            .andExpect(jsonPath("$.bagayatAre").value(DEFAULT_BAGAYAT_ARE.doubleValue()))
            .andExpect(jsonPath("$.bagayatAreMr").value(DEFAULT_BAGAYAT_ARE_MR.toString()))
            .andExpect(jsonPath("$.jirayatHector").value(DEFAULT_JIRAYAT_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.jirayatHectorMr").value(DEFAULT_JIRAYAT_HECTOR_MR.toString()))
            .andExpect(jsonPath("$.jirayatAre").value(DEFAULT_JIRAYAT_ARE.doubleValue()))
            .andExpect(jsonPath("$.jirayatAreMr").value(DEFAULT_JIRAYAT_ARE_MR.toString()))
            .andExpect(jsonPath("$.landValue").value(DEFAULT_LAND_VALUE.doubleValue()))
            .andExpect(jsonPath("$.landValueMr").value(DEFAULT_LAND_VALUE_MR.toString()))
            .andExpect(jsonPath("$.eAggrementAmt").value(DEFAULT_E_AGGREMENT_AMT.doubleValue()))
            .andExpect(jsonPath("$.eAgreementAmt").value(DEFAULT_E_AGREEMENT_AMT.toString()))
            .andExpect(jsonPath("$.eAgreementDate").value(DEFAULT_E_AGREEMENT_DATE.toString()))
            .andExpect(jsonPath("$.bojaAmount").value(DEFAULT_BOJA_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.bojaAmountMr").value(DEFAULT_BOJA_AMOUNT_MR.toString()))
            .andExpect(jsonPath("$.bojaDate").value(DEFAULT_BOJA_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySharesIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares equals to DEFAULT_SHARES
        defaultKmDetailsShouldBeFound("shares.equals=" + DEFAULT_SHARES);

        // Get all the kmDetailsList where shares equals to UPDATED_SHARES
        defaultKmDetailsShouldNotBeFound("shares.equals=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySharesIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares in DEFAULT_SHARES or UPDATED_SHARES
        defaultKmDetailsShouldBeFound("shares.in=" + DEFAULT_SHARES + "," + UPDATED_SHARES);

        // Get all the kmDetailsList where shares equals to UPDATED_SHARES
        defaultKmDetailsShouldNotBeFound("shares.in=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySharesIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares is not null
        defaultKmDetailsShouldBeFound("shares.specified=true");

        // Get all the kmDetailsList where shares is null
        defaultKmDetailsShouldNotBeFound("shares.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySharesMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sharesMr equals to DEFAULT_SHARES_MR
        defaultKmDetailsShouldBeFound("sharesMr.equals=" + DEFAULT_SHARES_MR);

        // Get all the kmDetailsList where sharesMr equals to UPDATED_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sharesMr.equals=" + UPDATED_SHARES_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySharesMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sharesMr in DEFAULT_SHARES_MR or UPDATED_SHARES_MR
        defaultKmDetailsShouldBeFound("sharesMr.in=" + DEFAULT_SHARES_MR + "," + UPDATED_SHARES_MR);

        // Get all the kmDetailsList where sharesMr equals to UPDATED_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sharesMr.in=" + UPDATED_SHARES_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySharesMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sharesMr is not null
        defaultKmDetailsShouldBeFound("sharesMr.specified=true");

        // Get all the kmDetailsList where sharesMr is null
        defaultKmDetailsShouldNotBeFound("sharesMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySugarSharesIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares equals to DEFAULT_SUGAR_SHARES
        defaultKmDetailsShouldBeFound("sugarShares.equals=" + DEFAULT_SUGAR_SHARES);

        // Get all the kmDetailsList where sugarShares equals to UPDATED_SUGAR_SHARES
        defaultKmDetailsShouldNotBeFound("sugarShares.equals=" + UPDATED_SUGAR_SHARES);
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySugarSharesIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares in DEFAULT_SUGAR_SHARES or UPDATED_SUGAR_SHARES
        defaultKmDetailsShouldBeFound("sugarShares.in=" + DEFAULT_SUGAR_SHARES + "," + UPDATED_SUGAR_SHARES);

        // Get all the kmDetailsList where sugarShares equals to UPDATED_SUGAR_SHARES
        defaultKmDetailsShouldNotBeFound("sugarShares.in=" + UPDATED_SUGAR_SHARES);
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySugarSharesIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares is not null
        defaultKmDetailsShouldBeFound("sugarShares.specified=true");

        // Get all the kmDetailsList where sugarShares is null
        defaultKmDetailsShouldNotBeFound("sugarShares.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySugarSharesMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarSharesMr equals to DEFAULT_SUGAR_SHARES_MR
        defaultKmDetailsShouldBeFound("sugarSharesMr.equals=" + DEFAULT_SUGAR_SHARES_MR);

        // Get all the kmDetailsList where sugarSharesMr equals to UPDATED_SUGAR_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sugarSharesMr.equals=" + UPDATED_SUGAR_SHARES_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySugarSharesMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarSharesMr in DEFAULT_SUGAR_SHARES_MR or UPDATED_SUGAR_SHARES_MR
        defaultKmDetailsShouldBeFound("sugarSharesMr.in=" + DEFAULT_SUGAR_SHARES_MR + "," + UPDATED_SUGAR_SHARES_MR);

        // Get all the kmDetailsList where sugarSharesMr equals to UPDATED_SUGAR_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sugarSharesMr.in=" + UPDATED_SUGAR_SHARES_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsBySugarSharesMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarSharesMr is not null
        defaultKmDetailsShouldBeFound("sugarSharesMr.specified=true");

        // Get all the kmDetailsList where sugarSharesMr is null
        defaultKmDetailsShouldNotBeFound("sugarSharesMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDepositIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposit equals to DEFAULT_DEPOSIT
        defaultKmDetailsShouldBeFound("deposit.equals=" + DEFAULT_DEPOSIT);

        // Get all the kmDetailsList where deposit equals to UPDATED_DEPOSIT
        defaultKmDetailsShouldNotBeFound("deposit.equals=" + UPDATED_DEPOSIT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDepositIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposit in DEFAULT_DEPOSIT or UPDATED_DEPOSIT
        defaultKmDetailsShouldBeFound("deposit.in=" + DEFAULT_DEPOSIT + "," + UPDATED_DEPOSIT);

        // Get all the kmDetailsList where deposit equals to UPDATED_DEPOSIT
        defaultKmDetailsShouldNotBeFound("deposit.in=" + UPDATED_DEPOSIT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDepositIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposit is not null
        defaultKmDetailsShouldBeFound("deposit.specified=true");

        // Get all the kmDetailsList where deposit is null
        defaultKmDetailsShouldNotBeFound("deposit.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDepositMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where depositMr equals to DEFAULT_DEPOSIT_MR
        defaultKmDetailsShouldBeFound("depositMr.equals=" + DEFAULT_DEPOSIT_MR);

        // Get all the kmDetailsList where depositMr equals to UPDATED_DEPOSIT_MR
        defaultKmDetailsShouldNotBeFound("depositMr.equals=" + UPDATED_DEPOSIT_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDepositMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where depositMr in DEFAULT_DEPOSIT_MR or UPDATED_DEPOSIT_MR
        defaultKmDetailsShouldBeFound("depositMr.in=" + DEFAULT_DEPOSIT_MR + "," + UPDATED_DEPOSIT_MR);

        // Get all the kmDetailsList where depositMr equals to UPDATED_DEPOSIT_MR
        defaultKmDetailsShouldNotBeFound("depositMr.in=" + UPDATED_DEPOSIT_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDepositMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where depositMr is not null
        defaultKmDetailsShouldBeFound("depositMr.specified=true");

        // Get all the kmDetailsList where depositMr is null
        defaultKmDetailsShouldNotBeFound("depositMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan equals to DEFAULT_DUE_LOAN
        defaultKmDetailsShouldBeFound("dueLoan.equals=" + DEFAULT_DUE_LOAN);

        // Get all the kmDetailsList where dueLoan equals to UPDATED_DUE_LOAN
        defaultKmDetailsShouldNotBeFound("dueLoan.equals=" + UPDATED_DUE_LOAN);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueLoanIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan in DEFAULT_DUE_LOAN or UPDATED_DUE_LOAN
        defaultKmDetailsShouldBeFound("dueLoan.in=" + DEFAULT_DUE_LOAN + "," + UPDATED_DUE_LOAN);

        // Get all the kmDetailsList where dueLoan equals to UPDATED_DUE_LOAN
        defaultKmDetailsShouldNotBeFound("dueLoan.in=" + UPDATED_DUE_LOAN);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan is not null
        defaultKmDetailsShouldBeFound("dueLoan.specified=true");

        // Get all the kmDetailsList where dueLoan is null
        defaultKmDetailsShouldNotBeFound("dueLoan.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueLoanMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoanMr equals to DEFAULT_DUE_LOAN_MR
        defaultKmDetailsShouldBeFound("dueLoanMr.equals=" + DEFAULT_DUE_LOAN_MR);

        // Get all the kmDetailsList where dueLoanMr equals to UPDATED_DUE_LOAN_MR
        defaultKmDetailsShouldNotBeFound("dueLoanMr.equals=" + UPDATED_DUE_LOAN_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueLoanMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoanMr in DEFAULT_DUE_LOAN_MR or UPDATED_DUE_LOAN_MR
        defaultKmDetailsShouldBeFound("dueLoanMr.in=" + DEFAULT_DUE_LOAN_MR + "," + UPDATED_DUE_LOAN_MR);

        // Get all the kmDetailsList where dueLoanMr equals to UPDATED_DUE_LOAN_MR
        defaultKmDetailsShouldNotBeFound("dueLoanMr.in=" + UPDATED_DUE_LOAN_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueLoanMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoanMr is not null
        defaultKmDetailsShouldBeFound("dueLoanMr.specified=true");

        // Get all the kmDetailsList where dueLoanMr is null
        defaultKmDetailsShouldNotBeFound("dueLoanMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount equals to DEFAULT_DUE_AMOUNT
        defaultKmDetailsShouldBeFound("dueAmount.equals=" + DEFAULT_DUE_AMOUNT);

        // Get all the kmDetailsList where dueAmount equals to UPDATED_DUE_AMOUNT
        defaultKmDetailsShouldNotBeFound("dueAmount.equals=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount in DEFAULT_DUE_AMOUNT or UPDATED_DUE_AMOUNT
        defaultKmDetailsShouldBeFound("dueAmount.in=" + DEFAULT_DUE_AMOUNT + "," + UPDATED_DUE_AMOUNT);

        // Get all the kmDetailsList where dueAmount equals to UPDATED_DUE_AMOUNT
        defaultKmDetailsShouldNotBeFound("dueAmount.in=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount is not null
        defaultKmDetailsShouldBeFound("dueAmount.specified=true");

        // Get all the kmDetailsList where dueAmount is null
        defaultKmDetailsShouldNotBeFound("dueAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueAmountMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmountMr equals to DEFAULT_DUE_AMOUNT_MR
        defaultKmDetailsShouldBeFound("dueAmountMr.equals=" + DEFAULT_DUE_AMOUNT_MR);

        // Get all the kmDetailsList where dueAmountMr equals to UPDATED_DUE_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("dueAmountMr.equals=" + UPDATED_DUE_AMOUNT_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueAmountMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmountMr in DEFAULT_DUE_AMOUNT_MR or UPDATED_DUE_AMOUNT_MR
        defaultKmDetailsShouldBeFound("dueAmountMr.in=" + DEFAULT_DUE_AMOUNT_MR + "," + UPDATED_DUE_AMOUNT_MR);

        // Get all the kmDetailsList where dueAmountMr equals to UPDATED_DUE_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("dueAmountMr.in=" + UPDATED_DUE_AMOUNT_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueAmountMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmountMr is not null
        defaultKmDetailsShouldBeFound("dueAmountMr.specified=true");

        // Get all the kmDetailsList where dueAmountMr is null
        defaultKmDetailsShouldNotBeFound("dueAmountMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDate equals to DEFAULT_DUE_DATE
        defaultKmDetailsShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the kmDetailsList where dueDate equals to UPDATED_DUE_DATE
        defaultKmDetailsShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultKmDetailsShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the kmDetailsList where dueDate equals to UPDATED_DUE_DATE
        defaultKmDetailsShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDate is not null
        defaultKmDetailsShouldBeFound("dueDate.specified=true");

        // Get all the kmDetailsList where dueDate is null
        defaultKmDetailsShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByKmDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDate equals to DEFAULT_KM_DATE
        defaultKmDetailsShouldBeFound("kmDate.equals=" + DEFAULT_KM_DATE);

        // Get all the kmDetailsList where kmDate equals to UPDATED_KM_DATE
        defaultKmDetailsShouldNotBeFound("kmDate.equals=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByKmDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDate in DEFAULT_KM_DATE or UPDATED_KM_DATE
        defaultKmDetailsShouldBeFound("kmDate.in=" + DEFAULT_KM_DATE + "," + UPDATED_KM_DATE);

        // Get all the kmDetailsList where kmDate equals to UPDATED_KM_DATE
        defaultKmDetailsShouldNotBeFound("kmDate.in=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByKmDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDate is not null
        defaultKmDetailsShouldBeFound("kmDate.specified=true");

        // Get all the kmDetailsList where kmDate is null
        defaultKmDetailsShouldNotBeFound("kmDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByKmFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDate equals to DEFAULT_KM_FROM_DATE
        defaultKmDetailsShouldBeFound("kmFromDate.equals=" + DEFAULT_KM_FROM_DATE);

        // Get all the kmDetailsList where kmFromDate equals to UPDATED_KM_FROM_DATE
        defaultKmDetailsShouldNotBeFound("kmFromDate.equals=" + UPDATED_KM_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByKmFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDate in DEFAULT_KM_FROM_DATE or UPDATED_KM_FROM_DATE
        defaultKmDetailsShouldBeFound("kmFromDate.in=" + DEFAULT_KM_FROM_DATE + "," + UPDATED_KM_FROM_DATE);

        // Get all the kmDetailsList where kmFromDate equals to UPDATED_KM_FROM_DATE
        defaultKmDetailsShouldNotBeFound("kmFromDate.in=" + UPDATED_KM_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByKmFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDate is not null
        defaultKmDetailsShouldBeFound("kmFromDate.specified=true");

        // Get all the kmDetailsList where kmFromDate is null
        defaultKmDetailsShouldNotBeFound("kmFromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByKmToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDate equals to DEFAULT_KM_TO_DATE
        defaultKmDetailsShouldBeFound("kmToDate.equals=" + DEFAULT_KM_TO_DATE);

        // Get all the kmDetailsList where kmToDate equals to UPDATED_KM_TO_DATE
        defaultKmDetailsShouldNotBeFound("kmToDate.equals=" + UPDATED_KM_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByKmToDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDate in DEFAULT_KM_TO_DATE or UPDATED_KM_TO_DATE
        defaultKmDetailsShouldBeFound("kmToDate.in=" + DEFAULT_KM_TO_DATE + "," + UPDATED_KM_TO_DATE);

        // Get all the kmDetailsList where kmToDate equals to UPDATED_KM_TO_DATE
        defaultKmDetailsShouldNotBeFound("kmToDate.in=" + UPDATED_KM_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByKmToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDate is not null
        defaultKmDetailsShouldBeFound("kmToDate.specified=true");

        // Get all the kmDetailsList where kmToDate is null
        defaultKmDetailsShouldNotBeFound("kmToDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector equals to DEFAULT_BAGAYAT_HECTOR
        defaultKmDetailsShouldBeFound("bagayatHector.equals=" + DEFAULT_BAGAYAT_HECTOR);

        // Get all the kmDetailsList where bagayatHector equals to UPDATED_BAGAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("bagayatHector.equals=" + UPDATED_BAGAYAT_HECTOR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector in DEFAULT_BAGAYAT_HECTOR or UPDATED_BAGAYAT_HECTOR
        defaultKmDetailsShouldBeFound("bagayatHector.in=" + DEFAULT_BAGAYAT_HECTOR + "," + UPDATED_BAGAYAT_HECTOR);

        // Get all the kmDetailsList where bagayatHector equals to UPDATED_BAGAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("bagayatHector.in=" + UPDATED_BAGAYAT_HECTOR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector is not null
        defaultKmDetailsShouldBeFound("bagayatHector.specified=true");

        // Get all the kmDetailsList where bagayatHector is null
        defaultKmDetailsShouldNotBeFound("bagayatHector.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatHectorMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHectorMr equals to DEFAULT_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("bagayatHectorMr.equals=" + DEFAULT_BAGAYAT_HECTOR_MR);

        // Get all the kmDetailsList where bagayatHectorMr equals to UPDATED_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("bagayatHectorMr.equals=" + UPDATED_BAGAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatHectorMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHectorMr in DEFAULT_BAGAYAT_HECTOR_MR or UPDATED_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("bagayatHectorMr.in=" + DEFAULT_BAGAYAT_HECTOR_MR + "," + UPDATED_BAGAYAT_HECTOR_MR);

        // Get all the kmDetailsList where bagayatHectorMr equals to UPDATED_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("bagayatHectorMr.in=" + UPDATED_BAGAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatHectorMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHectorMr is not null
        defaultKmDetailsShouldBeFound("bagayatHectorMr.specified=true");

        // Get all the kmDetailsList where bagayatHectorMr is null
        defaultKmDetailsShouldNotBeFound("bagayatHectorMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatAreIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre equals to DEFAULT_BAGAYAT_ARE
        defaultKmDetailsShouldBeFound("bagayatAre.equals=" + DEFAULT_BAGAYAT_ARE);

        // Get all the kmDetailsList where bagayatAre equals to UPDATED_BAGAYAT_ARE
        defaultKmDetailsShouldNotBeFound("bagayatAre.equals=" + UPDATED_BAGAYAT_ARE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatAreIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre in DEFAULT_BAGAYAT_ARE or UPDATED_BAGAYAT_ARE
        defaultKmDetailsShouldBeFound("bagayatAre.in=" + DEFAULT_BAGAYAT_ARE + "," + UPDATED_BAGAYAT_ARE);

        // Get all the kmDetailsList where bagayatAre equals to UPDATED_BAGAYAT_ARE
        defaultKmDetailsShouldNotBeFound("bagayatAre.in=" + UPDATED_BAGAYAT_ARE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatAreIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre is not null
        defaultKmDetailsShouldBeFound("bagayatAre.specified=true");

        // Get all the kmDetailsList where bagayatAre is null
        defaultKmDetailsShouldNotBeFound("bagayatAre.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatAreMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAreMr equals to DEFAULT_BAGAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("bagayatAreMr.equals=" + DEFAULT_BAGAYAT_ARE_MR);

        // Get all the kmDetailsList where bagayatAreMr equals to UPDATED_BAGAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("bagayatAreMr.equals=" + UPDATED_BAGAYAT_ARE_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatAreMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAreMr in DEFAULT_BAGAYAT_ARE_MR or UPDATED_BAGAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("bagayatAreMr.in=" + DEFAULT_BAGAYAT_ARE_MR + "," + UPDATED_BAGAYAT_ARE_MR);

        // Get all the kmDetailsList where bagayatAreMr equals to UPDATED_BAGAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("bagayatAreMr.in=" + UPDATED_BAGAYAT_ARE_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBagayatAreMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAreMr is not null
        defaultKmDetailsShouldBeFound("bagayatAreMr.specified=true");

        // Get all the kmDetailsList where bagayatAreMr is null
        defaultKmDetailsShouldNotBeFound("bagayatAreMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector equals to DEFAULT_JIRAYAT_HECTOR
        defaultKmDetailsShouldBeFound("jirayatHector.equals=" + DEFAULT_JIRAYAT_HECTOR);

        // Get all the kmDetailsList where jirayatHector equals to UPDATED_JIRAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("jirayatHector.equals=" + UPDATED_JIRAYAT_HECTOR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector in DEFAULT_JIRAYAT_HECTOR or UPDATED_JIRAYAT_HECTOR
        defaultKmDetailsShouldBeFound("jirayatHector.in=" + DEFAULT_JIRAYAT_HECTOR + "," + UPDATED_JIRAYAT_HECTOR);

        // Get all the kmDetailsList where jirayatHector equals to UPDATED_JIRAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("jirayatHector.in=" + UPDATED_JIRAYAT_HECTOR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector is not null
        defaultKmDetailsShouldBeFound("jirayatHector.specified=true");

        // Get all the kmDetailsList where jirayatHector is null
        defaultKmDetailsShouldNotBeFound("jirayatHector.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatHectorMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHectorMr equals to DEFAULT_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("jirayatHectorMr.equals=" + DEFAULT_JIRAYAT_HECTOR_MR);

        // Get all the kmDetailsList where jirayatHectorMr equals to UPDATED_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("jirayatHectorMr.equals=" + UPDATED_JIRAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatHectorMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHectorMr in DEFAULT_JIRAYAT_HECTOR_MR or UPDATED_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("jirayatHectorMr.in=" + DEFAULT_JIRAYAT_HECTOR_MR + "," + UPDATED_JIRAYAT_HECTOR_MR);

        // Get all the kmDetailsList where jirayatHectorMr equals to UPDATED_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("jirayatHectorMr.in=" + UPDATED_JIRAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatHectorMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHectorMr is not null
        defaultKmDetailsShouldBeFound("jirayatHectorMr.specified=true");

        // Get all the kmDetailsList where jirayatHectorMr is null
        defaultKmDetailsShouldNotBeFound("jirayatHectorMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatAreIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre equals to DEFAULT_JIRAYAT_ARE
        defaultKmDetailsShouldBeFound("jirayatAre.equals=" + DEFAULT_JIRAYAT_ARE);

        // Get all the kmDetailsList where jirayatAre equals to UPDATED_JIRAYAT_ARE
        defaultKmDetailsShouldNotBeFound("jirayatAre.equals=" + UPDATED_JIRAYAT_ARE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatAreIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre in DEFAULT_JIRAYAT_ARE or UPDATED_JIRAYAT_ARE
        defaultKmDetailsShouldBeFound("jirayatAre.in=" + DEFAULT_JIRAYAT_ARE + "," + UPDATED_JIRAYAT_ARE);

        // Get all the kmDetailsList where jirayatAre equals to UPDATED_JIRAYAT_ARE
        defaultKmDetailsShouldNotBeFound("jirayatAre.in=" + UPDATED_JIRAYAT_ARE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatAreIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre is not null
        defaultKmDetailsShouldBeFound("jirayatAre.specified=true");

        // Get all the kmDetailsList where jirayatAre is null
        defaultKmDetailsShouldNotBeFound("jirayatAre.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatAreMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAreMr equals to DEFAULT_JIRAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("jirayatAreMr.equals=" + DEFAULT_JIRAYAT_ARE_MR);

        // Get all the kmDetailsList where jirayatAreMr equals to UPDATED_JIRAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("jirayatAreMr.equals=" + UPDATED_JIRAYAT_ARE_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatAreMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAreMr in DEFAULT_JIRAYAT_ARE_MR or UPDATED_JIRAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("jirayatAreMr.in=" + DEFAULT_JIRAYAT_ARE_MR + "," + UPDATED_JIRAYAT_ARE_MR);

        // Get all the kmDetailsList where jirayatAreMr equals to UPDATED_JIRAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("jirayatAreMr.in=" + UPDATED_JIRAYAT_ARE_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByJirayatAreMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAreMr is not null
        defaultKmDetailsShouldBeFound("jirayatAreMr.specified=true");

        // Get all the kmDetailsList where jirayatAreMr is null
        defaultKmDetailsShouldNotBeFound("jirayatAreMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByLandValueIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue equals to DEFAULT_LAND_VALUE
        defaultKmDetailsShouldBeFound("landValue.equals=" + DEFAULT_LAND_VALUE);

        // Get all the kmDetailsList where landValue equals to UPDATED_LAND_VALUE
        defaultKmDetailsShouldNotBeFound("landValue.equals=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByLandValueIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue in DEFAULT_LAND_VALUE or UPDATED_LAND_VALUE
        defaultKmDetailsShouldBeFound("landValue.in=" + DEFAULT_LAND_VALUE + "," + UPDATED_LAND_VALUE);

        // Get all the kmDetailsList where landValue equals to UPDATED_LAND_VALUE
        defaultKmDetailsShouldNotBeFound("landValue.in=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByLandValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue is not null
        defaultKmDetailsShouldBeFound("landValue.specified=true");

        // Get all the kmDetailsList where landValue is null
        defaultKmDetailsShouldNotBeFound("landValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByLandValueMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValueMr equals to DEFAULT_LAND_VALUE_MR
        defaultKmDetailsShouldBeFound("landValueMr.equals=" + DEFAULT_LAND_VALUE_MR);

        // Get all the kmDetailsList where landValueMr equals to UPDATED_LAND_VALUE_MR
        defaultKmDetailsShouldNotBeFound("landValueMr.equals=" + UPDATED_LAND_VALUE_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByLandValueMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValueMr in DEFAULT_LAND_VALUE_MR or UPDATED_LAND_VALUE_MR
        defaultKmDetailsShouldBeFound("landValueMr.in=" + DEFAULT_LAND_VALUE_MR + "," + UPDATED_LAND_VALUE_MR);

        // Get all the kmDetailsList where landValueMr equals to UPDATED_LAND_VALUE_MR
        defaultKmDetailsShouldNotBeFound("landValueMr.in=" + UPDATED_LAND_VALUE_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByLandValueMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValueMr is not null
        defaultKmDetailsShouldBeFound("landValueMr.specified=true");

        // Get all the kmDetailsList where landValueMr is null
        defaultKmDetailsShouldNotBeFound("landValueMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByeAggrementAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAggrementAmt equals to DEFAULT_E_AGGREMENT_AMT
        defaultKmDetailsShouldBeFound("eAggrementAmt.equals=" + DEFAULT_E_AGGREMENT_AMT);

        // Get all the kmDetailsList where eAggrementAmt equals to UPDATED_E_AGGREMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAggrementAmt.equals=" + UPDATED_E_AGGREMENT_AMT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByeAggrementAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAggrementAmt in DEFAULT_E_AGGREMENT_AMT or UPDATED_E_AGGREMENT_AMT
        defaultKmDetailsShouldBeFound("eAggrementAmt.in=" + DEFAULT_E_AGGREMENT_AMT + "," + UPDATED_E_AGGREMENT_AMT);

        // Get all the kmDetailsList where eAggrementAmt equals to UPDATED_E_AGGREMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAggrementAmt.in=" + UPDATED_E_AGGREMENT_AMT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByeAggrementAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAggrementAmt is not null
        defaultKmDetailsShouldBeFound("eAggrementAmt.specified=true");

        // Get all the kmDetailsList where eAggrementAmt is null
        defaultKmDetailsShouldNotBeFound("eAggrementAmt.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByeAgreementAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt equals to DEFAULT_E_AGREEMENT_AMT
        defaultKmDetailsShouldBeFound("eAgreementAmt.equals=" + DEFAULT_E_AGREEMENT_AMT);

        // Get all the kmDetailsList where eAgreementAmt equals to UPDATED_E_AGREEMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.equals=" + UPDATED_E_AGREEMENT_AMT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByeAgreementAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt in DEFAULT_E_AGREEMENT_AMT or UPDATED_E_AGREEMENT_AMT
        defaultKmDetailsShouldBeFound("eAgreementAmt.in=" + DEFAULT_E_AGREEMENT_AMT + "," + UPDATED_E_AGREEMENT_AMT);

        // Get all the kmDetailsList where eAgreementAmt equals to UPDATED_E_AGREEMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.in=" + UPDATED_E_AGREEMENT_AMT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByeAgreementAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt is not null
        defaultKmDetailsShouldBeFound("eAgreementAmt.specified=true");

        // Get all the kmDetailsList where eAgreementAmt is null
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByeAgreementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDate equals to DEFAULT_E_AGREEMENT_DATE
        defaultKmDetailsShouldBeFound("eAgreementDate.equals=" + DEFAULT_E_AGREEMENT_DATE);

        // Get all the kmDetailsList where eAgreementDate equals to UPDATED_E_AGREEMENT_DATE
        defaultKmDetailsShouldNotBeFound("eAgreementDate.equals=" + UPDATED_E_AGREEMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByeAgreementDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDate in DEFAULT_E_AGREEMENT_DATE or UPDATED_E_AGREEMENT_DATE
        defaultKmDetailsShouldBeFound("eAgreementDate.in=" + DEFAULT_E_AGREEMENT_DATE + "," + UPDATED_E_AGREEMENT_DATE);

        // Get all the kmDetailsList where eAgreementDate equals to UPDATED_E_AGREEMENT_DATE
        defaultKmDetailsShouldNotBeFound("eAgreementDate.in=" + UPDATED_E_AGREEMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByeAgreementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDate is not null
        defaultKmDetailsShouldBeFound("eAgreementDate.specified=true");

        // Get all the kmDetailsList where eAgreementDate is null
        defaultKmDetailsShouldNotBeFound("eAgreementDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBojaAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount equals to DEFAULT_BOJA_AMOUNT
        defaultKmDetailsShouldBeFound("bojaAmount.equals=" + DEFAULT_BOJA_AMOUNT);

        // Get all the kmDetailsList where bojaAmount equals to UPDATED_BOJA_AMOUNT
        defaultKmDetailsShouldNotBeFound("bojaAmount.equals=" + UPDATED_BOJA_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBojaAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount in DEFAULT_BOJA_AMOUNT or UPDATED_BOJA_AMOUNT
        defaultKmDetailsShouldBeFound("bojaAmount.in=" + DEFAULT_BOJA_AMOUNT + "," + UPDATED_BOJA_AMOUNT);

        // Get all the kmDetailsList where bojaAmount equals to UPDATED_BOJA_AMOUNT
        defaultKmDetailsShouldNotBeFound("bojaAmount.in=" + UPDATED_BOJA_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBojaAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount is not null
        defaultKmDetailsShouldBeFound("bojaAmount.specified=true");

        // Get all the kmDetailsList where bojaAmount is null
        defaultKmDetailsShouldNotBeFound("bojaAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBojaAmountMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmountMr equals to DEFAULT_BOJA_AMOUNT_MR
        defaultKmDetailsShouldBeFound("bojaAmountMr.equals=" + DEFAULT_BOJA_AMOUNT_MR);

        // Get all the kmDetailsList where bojaAmountMr equals to UPDATED_BOJA_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("bojaAmountMr.equals=" + UPDATED_BOJA_AMOUNT_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBojaAmountMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmountMr in DEFAULT_BOJA_AMOUNT_MR or UPDATED_BOJA_AMOUNT_MR
        defaultKmDetailsShouldBeFound("bojaAmountMr.in=" + DEFAULT_BOJA_AMOUNT_MR + "," + UPDATED_BOJA_AMOUNT_MR);

        // Get all the kmDetailsList where bojaAmountMr equals to UPDATED_BOJA_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("bojaAmountMr.in=" + UPDATED_BOJA_AMOUNT_MR);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBojaAmountMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmountMr is not null
        defaultKmDetailsShouldBeFound("bojaAmountMr.specified=true");

        // Get all the kmDetailsList where bojaAmountMr is null
        defaultKmDetailsShouldNotBeFound("bojaAmountMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBojaDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDate equals to DEFAULT_BOJA_DATE
        defaultKmDetailsShouldBeFound("bojaDate.equals=" + DEFAULT_BOJA_DATE);

        // Get all the kmDetailsList where bojaDate equals to UPDATED_BOJA_DATE
        defaultKmDetailsShouldNotBeFound("bojaDate.equals=" + UPDATED_BOJA_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBojaDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDate in DEFAULT_BOJA_DATE or UPDATED_BOJA_DATE
        defaultKmDetailsShouldBeFound("bojaDate.in=" + DEFAULT_BOJA_DATE + "," + UPDATED_BOJA_DATE);

        // Get all the kmDetailsList where bojaDate equals to UPDATED_BOJA_DATE
        defaultKmDetailsShouldNotBeFound("bojaDate.in=" + UPDATED_BOJA_DATE);
    }

    @Test
    @Transactional
    public void getAllKmDetailsByBojaDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDate is not null
        defaultKmDetailsShouldBeFound("bojaDate.specified=true");

        // Get all the kmDetailsList where bojaDate is null
        defaultKmDetailsShouldNotBeFound("bojaDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultKmDetailsShouldBeFound(String filter) throws Exception {
        restKmDetailsMockMvc.perform(get("/api/km-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].shares").value(hasItem(DEFAULT_SHARES.doubleValue())))
            .andExpect(jsonPath("$.[*].sharesMr").value(hasItem(DEFAULT_SHARES_MR.toString())))
            .andExpect(jsonPath("$.[*].sugarShares").value(hasItem(DEFAULT_SUGAR_SHARES.doubleValue())))
            .andExpect(jsonPath("$.[*].sugarSharesMr").value(hasItem(DEFAULT_SUGAR_SHARES_MR.toString())))
            .andExpect(jsonPath("$.[*].deposit").value(hasItem(DEFAULT_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].depositMr").value(hasItem(DEFAULT_DEPOSIT_MR.toString())))
            .andExpect(jsonPath("$.[*].dueLoan").value(hasItem(DEFAULT_DUE_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].dueLoanMr").value(hasItem(DEFAULT_DUE_LOAN_MR.toString())))
            .andExpect(jsonPath("$.[*].dueAmount").value(hasItem(DEFAULT_DUE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmountMr").value(hasItem(DEFAULT_DUE_AMOUNT_MR.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmFromDate").value(hasItem(DEFAULT_KM_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmToDate").value(hasItem(DEFAULT_KM_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].bagayatHector").value(hasItem(DEFAULT_BAGAYAT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].bagayatHectorMr").value(hasItem(DEFAULT_BAGAYAT_HECTOR_MR.toString())))
            .andExpect(jsonPath("$.[*].bagayatAre").value(hasItem(DEFAULT_BAGAYAT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].bagayatAreMr").value(hasItem(DEFAULT_BAGAYAT_ARE_MR.toString())))
            .andExpect(jsonPath("$.[*].jirayatHector").value(hasItem(DEFAULT_JIRAYAT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].jirayatHectorMr").value(hasItem(DEFAULT_JIRAYAT_HECTOR_MR.toString())))
            .andExpect(jsonPath("$.[*].jirayatAre").value(hasItem(DEFAULT_JIRAYAT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].jirayatAreMr").value(hasItem(DEFAULT_JIRAYAT_ARE_MR.toString())))
            .andExpect(jsonPath("$.[*].landValue").value(hasItem(DEFAULT_LAND_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].landValueMr").value(hasItem(DEFAULT_LAND_VALUE_MR.toString())))
            .andExpect(jsonPath("$.[*].eAggrementAmt").value(hasItem(DEFAULT_E_AGGREMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].eAgreementAmt").value(hasItem(DEFAULT_E_AGREEMENT_AMT.toString())))
            .andExpect(jsonPath("$.[*].eAgreementDate").value(hasItem(DEFAULT_E_AGREEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].bojaAmount").value(hasItem(DEFAULT_BOJA_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].bojaAmountMr").value(hasItem(DEFAULT_BOJA_AMOUNT_MR.toString())))
            .andExpect(jsonPath("$.[*].bojaDate").value(hasItem(DEFAULT_BOJA_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultKmDetailsShouldNotBeFound(String filter) throws Exception {
        restKmDetailsMockMvc.perform(get("/api/km-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingKmDetails() throws Exception {
        // Get the kmDetails
        restKmDetailsMockMvc.perform(get("/api/km-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKmDetails() throws Exception {
        // Initialize the database
        kmDetailsService.save(kmDetails);

        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();

        // Update the kmDetails
        KmDetails updatedKmDetails = kmDetailsRepository.findOne(kmDetails.getId());
        updatedKmDetails.setShares(UPDATED_SHARES);
        updatedKmDetails.setSharesMr(UPDATED_SHARES_MR);
        updatedKmDetails.setSugarShares(UPDATED_SUGAR_SHARES);
        updatedKmDetails.setSugarSharesMr(UPDATED_SUGAR_SHARES_MR);
        updatedKmDetails.setDeposit(UPDATED_DEPOSIT);
        updatedKmDetails.setDepositMr(UPDATED_DEPOSIT_MR);
        updatedKmDetails.setDueLoan(UPDATED_DUE_LOAN);
        updatedKmDetails.setDueLoanMr(UPDATED_DUE_LOAN_MR);
        updatedKmDetails.setDueAmount(UPDATED_DUE_AMOUNT);
        updatedKmDetails.setDueAmountMr(UPDATED_DUE_AMOUNT_MR);
        updatedKmDetails.setDueDate(UPDATED_DUE_DATE);
        updatedKmDetails.setKmDate(UPDATED_KM_DATE);
        updatedKmDetails.setKmFromDate(UPDATED_KM_FROM_DATE);
        updatedKmDetails.setKmToDate(UPDATED_KM_TO_DATE);
        updatedKmDetails.setBagayatHector(UPDATED_BAGAYAT_HECTOR);
        updatedKmDetails.setBagayatHectorMr(UPDATED_BAGAYAT_HECTOR_MR);
        updatedKmDetails.setBagayatAre(UPDATED_BAGAYAT_ARE);
        updatedKmDetails.setBagayatAreMr(UPDATED_BAGAYAT_ARE_MR);
        updatedKmDetails.setJirayatHector(UPDATED_JIRAYAT_HECTOR);
        updatedKmDetails.setJirayatHectorMr(UPDATED_JIRAYAT_HECTOR_MR);
        updatedKmDetails.setJirayatAre(UPDATED_JIRAYAT_ARE);
        updatedKmDetails.setJirayatAreMr(UPDATED_JIRAYAT_ARE_MR);
        updatedKmDetails.setLandValue(UPDATED_LAND_VALUE);
        updatedKmDetails.setLandValueMr(UPDATED_LAND_VALUE_MR);
        updatedKmDetails.seteAggrementAmt(UPDATED_E_AGGREMENT_AMT);
        updatedKmDetails.seteAgreementAmt(UPDATED_E_AGREEMENT_AMT);
        updatedKmDetails.seteAgreementDate(UPDATED_E_AGREEMENT_DATE);
        updatedKmDetails.setBojaAmount(UPDATED_BOJA_AMOUNT);
        updatedKmDetails.setBojaAmountMr(UPDATED_BOJA_AMOUNT_MR);
        updatedKmDetails.setBojaDate(UPDATED_BOJA_DATE);

        restKmDetailsMockMvc.perform(put("/api/km-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKmDetails)))
            .andExpect(status().isOk());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
        KmDetails testKmDetails = kmDetailsList.get(kmDetailsList.size() - 1);
        assertThat(testKmDetails.getShares()).isEqualTo(UPDATED_SHARES);
        assertThat(testKmDetails.getSharesMr()).isEqualTo(UPDATED_SHARES_MR);
        assertThat(testKmDetails.getSugarShares()).isEqualTo(UPDATED_SUGAR_SHARES);
        assertThat(testKmDetails.getSugarSharesMr()).isEqualTo(UPDATED_SUGAR_SHARES_MR);
        assertThat(testKmDetails.getDeposit()).isEqualTo(UPDATED_DEPOSIT);
        assertThat(testKmDetails.getDepositMr()).isEqualTo(UPDATED_DEPOSIT_MR);
        assertThat(testKmDetails.getDueLoan()).isEqualTo(UPDATED_DUE_LOAN);
        assertThat(testKmDetails.getDueLoanMr()).isEqualTo(UPDATED_DUE_LOAN_MR);
        assertThat(testKmDetails.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
        assertThat(testKmDetails.getDueAmountMr()).isEqualTo(UPDATED_DUE_AMOUNT_MR);
        assertThat(testKmDetails.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testKmDetails.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKmDetails.getKmFromDate()).isEqualTo(UPDATED_KM_FROM_DATE);
        assertThat(testKmDetails.getKmToDate()).isEqualTo(UPDATED_KM_TO_DATE);
        assertThat(testKmDetails.getBagayatHector()).isEqualTo(UPDATED_BAGAYAT_HECTOR);
        assertThat(testKmDetails.getBagayatHectorMr()).isEqualTo(UPDATED_BAGAYAT_HECTOR_MR);
        assertThat(testKmDetails.getBagayatAre()).isEqualTo(UPDATED_BAGAYAT_ARE);
        assertThat(testKmDetails.getBagayatAreMr()).isEqualTo(UPDATED_BAGAYAT_ARE_MR);
        assertThat(testKmDetails.getJirayatHector()).isEqualTo(UPDATED_JIRAYAT_HECTOR);
        assertThat(testKmDetails.getJirayatHectorMr()).isEqualTo(UPDATED_JIRAYAT_HECTOR_MR);
        assertThat(testKmDetails.getJirayatAre()).isEqualTo(UPDATED_JIRAYAT_ARE);
        assertThat(testKmDetails.getJirayatAreMr()).isEqualTo(UPDATED_JIRAYAT_ARE_MR);
        assertThat(testKmDetails.getLandValue()).isEqualTo(UPDATED_LAND_VALUE);
        assertThat(testKmDetails.getLandValueMr()).isEqualTo(UPDATED_LAND_VALUE_MR);
        assertThat(testKmDetails.geteAggrementAmt()).isEqualTo(UPDATED_E_AGGREMENT_AMT);
        assertThat(testKmDetails.geteAgreementAmt()).isEqualTo(UPDATED_E_AGREEMENT_AMT);
        assertThat(testKmDetails.geteAgreementDate()).isEqualTo(UPDATED_E_AGREEMENT_DATE);
        assertThat(testKmDetails.getBojaAmount()).isEqualTo(UPDATED_BOJA_AMOUNT);
        assertThat(testKmDetails.getBojaAmountMr()).isEqualTo(UPDATED_BOJA_AMOUNT_MR);
        assertThat(testKmDetails.getBojaDate()).isEqualTo(UPDATED_BOJA_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingKmDetails() throws Exception {
        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();

        // Create the KmDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKmDetailsMockMvc.perform(put("/api/km-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmDetails)))
            .andExpect(status().isCreated());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKmDetails() throws Exception {
        // Initialize the database
        kmDetailsService.save(kmDetails);

        int databaseSizeBeforeDelete = kmDetailsRepository.findAll().size();

        // Get the kmDetails
        restKmDetailsMockMvc.perform(delete("/api/km-details/{id}", kmDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmDetails.class);
        KmDetails kmDetails1 = new KmDetails();
        kmDetails1.setId(1L);
        KmDetails kmDetails2 = new KmDetails();
        kmDetails2.setId(kmDetails1.getId());
        assertThat(kmDetails1).isEqualTo(kmDetails2);
        kmDetails2.setId(2L);
        assertThat(kmDetails1).isNotEqualTo(kmDetails2);
        kmDetails1.setId(null);
        assertThat(kmDetails1).isNotEqualTo(kmDetails2);
    }
}
