package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KmDetails;
import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.KmDetailsRepository;
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
 * Integration tests for the {@link KmDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KmDetailsResourceIT {

    private static final Double DEFAULT_SHARES = 1D;
    private static final Double UPDATED_SHARES = 2D;
    private static final Double SMALLER_SHARES = 1D - 1D;

    private static final String DEFAULT_SHARES_MR = "AAAAAAAAAA";
    private static final String UPDATED_SHARES_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_SUGAR_SHARES = 1D;
    private static final Double UPDATED_SUGAR_SHARES = 2D;
    private static final Double SMALLER_SUGAR_SHARES = 1D - 1D;

    private static final String DEFAULT_SUGAR_SHARES_MR = "AAAAAAAAAA";
    private static final String UPDATED_SUGAR_SHARES_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DEPOSITE = 1D;
    private static final Double UPDATED_DEPOSITE = 2D;
    private static final Double SMALLER_DEPOSITE = 1D - 1D;

    private static final String DEFAULT_DEPOSITE_MR = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSITE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DUE_LOAN = 1D;
    private static final Double UPDATED_DUE_LOAN = 2D;
    private static final Double SMALLER_DUE_LOAN = 1D - 1D;

    private static final String DEFAULT_DUE_LOAN_MR = "AAAAAAAAAA";
    private static final String UPDATED_DUE_LOAN_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DUE_AMOUNT = 1D;
    private static final Double UPDATED_DUE_AMOUNT = 2D;
    private static final Double SMALLER_DUE_AMOUNT = 1D - 1D;

    private static final String DEFAULT_DUE_AMOUNT_MR = "AAAAAAAAAA";
    private static final String UPDATED_DUE_AMOUNT_MR = "BBBBBBBBBB";

    private static final String DEFAULT_DUE_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_DUE_DATE_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_KM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_KM_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_KM_DATE_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_KM_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_KM_FROM_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_KM_FROM_DATE_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_KM_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_KM_TO_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_KM_TO_DATE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BAGAYAT_HECTOR = 1D;
    private static final Double UPDATED_BAGAYAT_HECTOR = 2D;
    private static final Double SMALLER_BAGAYAT_HECTOR = 1D - 1D;

    private static final String DEFAULT_BAGAYAT_HECTOR_MR = "AAAAAAAAAA";
    private static final String UPDATED_BAGAYAT_HECTOR_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BAGAYAT_ARE = 1D;
    private static final Double UPDATED_BAGAYAT_ARE = 2D;
    private static final Double SMALLER_BAGAYAT_ARE = 1D - 1D;

    private static final String DEFAULT_BAGAYAT_ARE_MR = "AAAAAAAAAA";
    private static final String UPDATED_BAGAYAT_ARE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_JIRAYAT_HECTOR = 1D;
    private static final Double UPDATED_JIRAYAT_HECTOR = 2D;
    private static final Double SMALLER_JIRAYAT_HECTOR = 1D - 1D;

    private static final String DEFAULT_JIRAYAT_HECTOR_MR = "AAAAAAAAAA";
    private static final String UPDATED_JIRAYAT_HECTOR_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_JIRAYAT_ARE = 1D;
    private static final Double UPDATED_JIRAYAT_ARE = 2D;
    private static final Double SMALLER_JIRAYAT_ARE = 1D - 1D;

    private static final String DEFAULT_JIRAYAT_ARE_MR = "AAAAAAAAAA";
    private static final String UPDATED_JIRAYAT_ARE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_ZINDAGI_AMT = 1D;
    private static final Double UPDATED_ZINDAGI_AMT = 2D;
    private static final Double SMALLER_ZINDAGI_AMT = 1D - 1D;

    private static final Long DEFAULT_ZINDAGI_NO = 1L;
    private static final Long UPDATED_ZINDAGI_NO = 2L;
    private static final Long SMALLER_ZINDAGI_NO = 1L - 1L;

    private static final Long DEFAULT_SURVEY_NO = 1L;
    private static final Long UPDATED_SURVEY_NO = 2L;
    private static final Long SMALLER_SURVEY_NO = 1L - 1L;

    private static final Double DEFAULT_LAND_VALUE = 1D;
    private static final Double UPDATED_LAND_VALUE = 2D;
    private static final Double SMALLER_LAND_VALUE = 1D - 1D;

    private static final String DEFAULT_LAND_VALUE_MR = "AAAAAAAAAA";
    private static final String UPDATED_LAND_VALUE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_E_AGREEMENT_AMT = 1D;
    private static final Double UPDATED_E_AGREEMENT_AMT = 2D;
    private static final Double SMALLER_E_AGREEMENT_AMT = 1D - 1D;

    private static final String DEFAULT_E_AGREEMENT_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_E_AGREEMENT_AMT_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_E_AGREEMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_E_AGREEMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_E_AGREEMENT_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_E_AGREEMENT_DATE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BOJA_AMOUNT = 1D;
    private static final Double UPDATED_BOJA_AMOUNT = 2D;
    private static final Double SMALLER_BOJA_AMOUNT = 1D - 1D;

    private static final String DEFAULT_BOJA_AMOUNT_MR = "AAAAAAAAAA";
    private static final String UPDATED_BOJA_AMOUNT_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_BOJA_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOJA_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BOJA_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_BOJA_DATE_MR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/km-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KmDetailsRepository kmDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKmDetailsMockMvc;

    private KmDetails kmDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmDetails createEntity(EntityManager em) {
        KmDetails kmDetails = new KmDetails()
            .shares(DEFAULT_SHARES)
            .sharesMr(DEFAULT_SHARES_MR)
            .sugarShares(DEFAULT_SUGAR_SHARES)
            .sugarSharesMr(DEFAULT_SUGAR_SHARES_MR)
            .deposite(DEFAULT_DEPOSITE)
            .depositeMr(DEFAULT_DEPOSITE_MR)
            .dueLoan(DEFAULT_DUE_LOAN)
            .dueLoanMr(DEFAULT_DUE_LOAN_MR)
            .dueAmount(DEFAULT_DUE_AMOUNT)
            .dueAmountMr(DEFAULT_DUE_AMOUNT_MR)
            .dueDateMr(DEFAULT_DUE_DATE_MR)
            .dueDate(DEFAULT_DUE_DATE)
            .kmDate(DEFAULT_KM_DATE)
            .kmDateMr(DEFAULT_KM_DATE_MR)
            .kmFromDate(DEFAULT_KM_FROM_DATE)
            .kmFromDateMr(DEFAULT_KM_FROM_DATE_MR)
            .kmToDate(DEFAULT_KM_TO_DATE)
            .kmToDateMr(DEFAULT_KM_TO_DATE_MR)
            .bagayatHector(DEFAULT_BAGAYAT_HECTOR)
            .bagayatHectorMr(DEFAULT_BAGAYAT_HECTOR_MR)
            .bagayatAre(DEFAULT_BAGAYAT_ARE)
            .bagayatAreMr(DEFAULT_BAGAYAT_ARE_MR)
            .jirayatHector(DEFAULT_JIRAYAT_HECTOR)
            .jirayatHectorMr(DEFAULT_JIRAYAT_HECTOR_MR)
            .jirayatAre(DEFAULT_JIRAYAT_ARE)
            .jirayatAreMr(DEFAULT_JIRAYAT_ARE_MR)
            .zindagiAmt(DEFAULT_ZINDAGI_AMT)
            .zindagiNo(DEFAULT_ZINDAGI_NO)
            .surveyNo(DEFAULT_SURVEY_NO)
            .landValue(DEFAULT_LAND_VALUE)
            .landValueMr(DEFAULT_LAND_VALUE_MR)
            .eAgreementAmt(DEFAULT_E_AGREEMENT_AMT)
            .eAgreementAmtMr(DEFAULT_E_AGREEMENT_AMT_MR)
            .eAgreementDate(DEFAULT_E_AGREEMENT_DATE)
            .eAgreementDateMr(DEFAULT_E_AGREEMENT_DATE_MR)
            .bojaAmount(DEFAULT_BOJA_AMOUNT)
            .bojaAmountMr(DEFAULT_BOJA_AMOUNT_MR)
            .bojaDate(DEFAULT_BOJA_DATE)
            .bojaDateMr(DEFAULT_BOJA_DATE_MR);
        return kmDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmDetails createUpdatedEntity(EntityManager em) {
        KmDetails kmDetails = new KmDetails()
            .shares(UPDATED_SHARES)
            .sharesMr(UPDATED_SHARES_MR)
            .sugarShares(UPDATED_SUGAR_SHARES)
            .sugarSharesMr(UPDATED_SUGAR_SHARES_MR)
            .deposite(UPDATED_DEPOSITE)
            .depositeMr(UPDATED_DEPOSITE_MR)
            .dueLoan(UPDATED_DUE_LOAN)
            .dueLoanMr(UPDATED_DUE_LOAN_MR)
            .dueAmount(UPDATED_DUE_AMOUNT)
            .dueAmountMr(UPDATED_DUE_AMOUNT_MR)
            .dueDateMr(UPDATED_DUE_DATE_MR)
            .dueDate(UPDATED_DUE_DATE)
            .kmDate(UPDATED_KM_DATE)
            .kmDateMr(UPDATED_KM_DATE_MR)
            .kmFromDate(UPDATED_KM_FROM_DATE)
            .kmFromDateMr(UPDATED_KM_FROM_DATE_MR)
            .kmToDate(UPDATED_KM_TO_DATE)
            .kmToDateMr(UPDATED_KM_TO_DATE_MR)
            .bagayatHector(UPDATED_BAGAYAT_HECTOR)
            .bagayatHectorMr(UPDATED_BAGAYAT_HECTOR_MR)
            .bagayatAre(UPDATED_BAGAYAT_ARE)
            .bagayatAreMr(UPDATED_BAGAYAT_ARE_MR)
            .jirayatHector(UPDATED_JIRAYAT_HECTOR)
            .jirayatHectorMr(UPDATED_JIRAYAT_HECTOR_MR)
            .jirayatAre(UPDATED_JIRAYAT_ARE)
            .jirayatAreMr(UPDATED_JIRAYAT_ARE_MR)
            .zindagiAmt(UPDATED_ZINDAGI_AMT)
            .zindagiNo(UPDATED_ZINDAGI_NO)
            .surveyNo(UPDATED_SURVEY_NO)
            .landValue(UPDATED_LAND_VALUE)
            .landValueMr(UPDATED_LAND_VALUE_MR)
            .eAgreementAmt(UPDATED_E_AGREEMENT_AMT)
            .eAgreementAmtMr(UPDATED_E_AGREEMENT_AMT_MR)
            .eAgreementDate(UPDATED_E_AGREEMENT_DATE)
            .eAgreementDateMr(UPDATED_E_AGREEMENT_DATE_MR)
            .bojaAmount(UPDATED_BOJA_AMOUNT)
            .bojaAmountMr(UPDATED_BOJA_AMOUNT_MR)
            .bojaDate(UPDATED_BOJA_DATE)
            .bojaDateMr(UPDATED_BOJA_DATE_MR);
        return kmDetails;
    }

    @BeforeEach
    public void initTest() {
        kmDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createKmDetails() throws Exception {
        int databaseSizeBeforeCreate = kmDetailsRepository.findAll().size();
        // Create the KmDetails
        restKmDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmDetails)))
            .andExpect(status().isCreated());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        KmDetails testKmDetails = kmDetailsList.get(kmDetailsList.size() - 1);
        assertThat(testKmDetails.getShares()).isEqualTo(DEFAULT_SHARES);
        assertThat(testKmDetails.getSharesMr()).isEqualTo(DEFAULT_SHARES_MR);
        assertThat(testKmDetails.getSugarShares()).isEqualTo(DEFAULT_SUGAR_SHARES);
        assertThat(testKmDetails.getSugarSharesMr()).isEqualTo(DEFAULT_SUGAR_SHARES_MR);
        assertThat(testKmDetails.getDeposite()).isEqualTo(DEFAULT_DEPOSITE);
        assertThat(testKmDetails.getDepositeMr()).isEqualTo(DEFAULT_DEPOSITE_MR);
        assertThat(testKmDetails.getDueLoan()).isEqualTo(DEFAULT_DUE_LOAN);
        assertThat(testKmDetails.getDueLoanMr()).isEqualTo(DEFAULT_DUE_LOAN_MR);
        assertThat(testKmDetails.getDueAmount()).isEqualTo(DEFAULT_DUE_AMOUNT);
        assertThat(testKmDetails.getDueAmountMr()).isEqualTo(DEFAULT_DUE_AMOUNT_MR);
        assertThat(testKmDetails.getDueDateMr()).isEqualTo(DEFAULT_DUE_DATE_MR);
        assertThat(testKmDetails.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testKmDetails.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
        assertThat(testKmDetails.getKmDateMr()).isEqualTo(DEFAULT_KM_DATE_MR);
        assertThat(testKmDetails.getKmFromDate()).isEqualTo(DEFAULT_KM_FROM_DATE);
        assertThat(testKmDetails.getKmFromDateMr()).isEqualTo(DEFAULT_KM_FROM_DATE_MR);
        assertThat(testKmDetails.getKmToDate()).isEqualTo(DEFAULT_KM_TO_DATE);
        assertThat(testKmDetails.getKmToDateMr()).isEqualTo(DEFAULT_KM_TO_DATE_MR);
        assertThat(testKmDetails.getBagayatHector()).isEqualTo(DEFAULT_BAGAYAT_HECTOR);
        assertThat(testKmDetails.getBagayatHectorMr()).isEqualTo(DEFAULT_BAGAYAT_HECTOR_MR);
        assertThat(testKmDetails.getBagayatAre()).isEqualTo(DEFAULT_BAGAYAT_ARE);
        assertThat(testKmDetails.getBagayatAreMr()).isEqualTo(DEFAULT_BAGAYAT_ARE_MR);
        assertThat(testKmDetails.getJirayatHector()).isEqualTo(DEFAULT_JIRAYAT_HECTOR);
        assertThat(testKmDetails.getJirayatHectorMr()).isEqualTo(DEFAULT_JIRAYAT_HECTOR_MR);
        assertThat(testKmDetails.getJirayatAre()).isEqualTo(DEFAULT_JIRAYAT_ARE);
        assertThat(testKmDetails.getJirayatAreMr()).isEqualTo(DEFAULT_JIRAYAT_ARE_MR);
        assertThat(testKmDetails.getZindagiAmt()).isEqualTo(DEFAULT_ZINDAGI_AMT);
        assertThat(testKmDetails.getZindagiNo()).isEqualTo(DEFAULT_ZINDAGI_NO);
        assertThat(testKmDetails.getSurveyNo()).isEqualTo(DEFAULT_SURVEY_NO);
        assertThat(testKmDetails.getLandValue()).isEqualTo(DEFAULT_LAND_VALUE);
        assertThat(testKmDetails.getLandValueMr()).isEqualTo(DEFAULT_LAND_VALUE_MR);
        assertThat(testKmDetails.geteAgreementAmt()).isEqualTo(DEFAULT_E_AGREEMENT_AMT);
        assertThat(testKmDetails.geteAgreementAmtMr()).isEqualTo(DEFAULT_E_AGREEMENT_AMT_MR);
        assertThat(testKmDetails.geteAgreementDate()).isEqualTo(DEFAULT_E_AGREEMENT_DATE);
        assertThat(testKmDetails.geteAgreementDateMr()).isEqualTo(DEFAULT_E_AGREEMENT_DATE_MR);
        assertThat(testKmDetails.getBojaAmount()).isEqualTo(DEFAULT_BOJA_AMOUNT);
        assertThat(testKmDetails.getBojaAmountMr()).isEqualTo(DEFAULT_BOJA_AMOUNT_MR);
        assertThat(testKmDetails.getBojaDate()).isEqualTo(DEFAULT_BOJA_DATE);
        assertThat(testKmDetails.getBojaDateMr()).isEqualTo(DEFAULT_BOJA_DATE_MR);
    }

    @Test
    @Transactional
    void createKmDetailsWithExistingId() throws Exception {
        // Create the KmDetails with an existing ID
        kmDetails.setId(1L);

        int databaseSizeBeforeCreate = kmDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmDetails)))
            .andExpect(status().isBadRequest());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKmDetails() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList
        restKmDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].shares").value(hasItem(DEFAULT_SHARES.doubleValue())))
            .andExpect(jsonPath("$.[*].sharesMr").value(hasItem(DEFAULT_SHARES_MR)))
            .andExpect(jsonPath("$.[*].sugarShares").value(hasItem(DEFAULT_SUGAR_SHARES.doubleValue())))
            .andExpect(jsonPath("$.[*].sugarSharesMr").value(hasItem(DEFAULT_SUGAR_SHARES_MR)))
            .andExpect(jsonPath("$.[*].deposite").value(hasItem(DEFAULT_DEPOSITE.doubleValue())))
            .andExpect(jsonPath("$.[*].depositeMr").value(hasItem(DEFAULT_DEPOSITE_MR)))
            .andExpect(jsonPath("$.[*].dueLoan").value(hasItem(DEFAULT_DUE_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].dueLoanMr").value(hasItem(DEFAULT_DUE_LOAN_MR)))
            .andExpect(jsonPath("$.[*].dueAmount").value(hasItem(DEFAULT_DUE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmountMr").value(hasItem(DEFAULT_DUE_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].dueDateMr").value(hasItem(DEFAULT_DUE_DATE_MR)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDateMr").value(hasItem(DEFAULT_KM_DATE_MR)))
            .andExpect(jsonPath("$.[*].kmFromDate").value(hasItem(DEFAULT_KM_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmFromDateMr").value(hasItem(DEFAULT_KM_FROM_DATE_MR)))
            .andExpect(jsonPath("$.[*].kmToDate").value(hasItem(DEFAULT_KM_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmToDateMr").value(hasItem(DEFAULT_KM_TO_DATE_MR)))
            .andExpect(jsonPath("$.[*].bagayatHector").value(hasItem(DEFAULT_BAGAYAT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].bagayatHectorMr").value(hasItem(DEFAULT_BAGAYAT_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].bagayatAre").value(hasItem(DEFAULT_BAGAYAT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].bagayatAreMr").value(hasItem(DEFAULT_BAGAYAT_ARE_MR)))
            .andExpect(jsonPath("$.[*].jirayatHector").value(hasItem(DEFAULT_JIRAYAT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].jirayatHectorMr").value(hasItem(DEFAULT_JIRAYAT_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].jirayatAre").value(hasItem(DEFAULT_JIRAYAT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].jirayatAreMr").value(hasItem(DEFAULT_JIRAYAT_ARE_MR)))
            .andExpect(jsonPath("$.[*].zindagiAmt").value(hasItem(DEFAULT_ZINDAGI_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].zindagiNo").value(hasItem(DEFAULT_ZINDAGI_NO.intValue())))
            .andExpect(jsonPath("$.[*].surveyNo").value(hasItem(DEFAULT_SURVEY_NO.intValue())))
            .andExpect(jsonPath("$.[*].landValue").value(hasItem(DEFAULT_LAND_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].landValueMr").value(hasItem(DEFAULT_LAND_VALUE_MR)))
            .andExpect(jsonPath("$.[*].eAgreementAmt").value(hasItem(DEFAULT_E_AGREEMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].eAgreementAmtMr").value(hasItem(DEFAULT_E_AGREEMENT_AMT_MR)))
            .andExpect(jsonPath("$.[*].eAgreementDate").value(hasItem(DEFAULT_E_AGREEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].eAgreementDateMr").value(hasItem(DEFAULT_E_AGREEMENT_DATE_MR)))
            .andExpect(jsonPath("$.[*].bojaAmount").value(hasItem(DEFAULT_BOJA_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].bojaAmountMr").value(hasItem(DEFAULT_BOJA_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].bojaDate").value(hasItem(DEFAULT_BOJA_DATE.toString())))
            .andExpect(jsonPath("$.[*].bojaDateMr").value(hasItem(DEFAULT_BOJA_DATE_MR)));
    }

    @Test
    @Transactional
    void getKmDetails() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get the kmDetails
        restKmDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, kmDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kmDetails.getId().intValue()))
            .andExpect(jsonPath("$.shares").value(DEFAULT_SHARES.doubleValue()))
            .andExpect(jsonPath("$.sharesMr").value(DEFAULT_SHARES_MR))
            .andExpect(jsonPath("$.sugarShares").value(DEFAULT_SUGAR_SHARES.doubleValue()))
            .andExpect(jsonPath("$.sugarSharesMr").value(DEFAULT_SUGAR_SHARES_MR))
            .andExpect(jsonPath("$.deposite").value(DEFAULT_DEPOSITE.doubleValue()))
            .andExpect(jsonPath("$.depositeMr").value(DEFAULT_DEPOSITE_MR))
            .andExpect(jsonPath("$.dueLoan").value(DEFAULT_DUE_LOAN.doubleValue()))
            .andExpect(jsonPath("$.dueLoanMr").value(DEFAULT_DUE_LOAN_MR))
            .andExpect(jsonPath("$.dueAmount").value(DEFAULT_DUE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dueAmountMr").value(DEFAULT_DUE_AMOUNT_MR))
            .andExpect(jsonPath("$.dueDateMr").value(DEFAULT_DUE_DATE_MR))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.kmDate").value(DEFAULT_KM_DATE.toString()))
            .andExpect(jsonPath("$.kmDateMr").value(DEFAULT_KM_DATE_MR))
            .andExpect(jsonPath("$.kmFromDate").value(DEFAULT_KM_FROM_DATE.toString()))
            .andExpect(jsonPath("$.kmFromDateMr").value(DEFAULT_KM_FROM_DATE_MR))
            .andExpect(jsonPath("$.kmToDate").value(DEFAULT_KM_TO_DATE.toString()))
            .andExpect(jsonPath("$.kmToDateMr").value(DEFAULT_KM_TO_DATE_MR))
            .andExpect(jsonPath("$.bagayatHector").value(DEFAULT_BAGAYAT_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.bagayatHectorMr").value(DEFAULT_BAGAYAT_HECTOR_MR))
            .andExpect(jsonPath("$.bagayatAre").value(DEFAULT_BAGAYAT_ARE.doubleValue()))
            .andExpect(jsonPath("$.bagayatAreMr").value(DEFAULT_BAGAYAT_ARE_MR))
            .andExpect(jsonPath("$.jirayatHector").value(DEFAULT_JIRAYAT_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.jirayatHectorMr").value(DEFAULT_JIRAYAT_HECTOR_MR))
            .andExpect(jsonPath("$.jirayatAre").value(DEFAULT_JIRAYAT_ARE.doubleValue()))
            .andExpect(jsonPath("$.jirayatAreMr").value(DEFAULT_JIRAYAT_ARE_MR))
            .andExpect(jsonPath("$.zindagiAmt").value(DEFAULT_ZINDAGI_AMT.doubleValue()))
            .andExpect(jsonPath("$.zindagiNo").value(DEFAULT_ZINDAGI_NO.intValue()))
            .andExpect(jsonPath("$.surveyNo").value(DEFAULT_SURVEY_NO.intValue()))
            .andExpect(jsonPath("$.landValue").value(DEFAULT_LAND_VALUE.doubleValue()))
            .andExpect(jsonPath("$.landValueMr").value(DEFAULT_LAND_VALUE_MR))
            .andExpect(jsonPath("$.eAgreementAmt").value(DEFAULT_E_AGREEMENT_AMT.doubleValue()))
            .andExpect(jsonPath("$.eAgreementAmtMr").value(DEFAULT_E_AGREEMENT_AMT_MR))
            .andExpect(jsonPath("$.eAgreementDate").value(DEFAULT_E_AGREEMENT_DATE.toString()))
            .andExpect(jsonPath("$.eAgreementDateMr").value(DEFAULT_E_AGREEMENT_DATE_MR))
            .andExpect(jsonPath("$.bojaAmount").value(DEFAULT_BOJA_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.bojaAmountMr").value(DEFAULT_BOJA_AMOUNT_MR))
            .andExpect(jsonPath("$.bojaDate").value(DEFAULT_BOJA_DATE.toString()))
            .andExpect(jsonPath("$.bojaDateMr").value(DEFAULT_BOJA_DATE_MR));
    }

    @Test
    @Transactional
    void getKmDetailsByIdFiltering() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        Long id = kmDetails.getId();

        defaultKmDetailsShouldBeFound("id.equals=" + id);
        defaultKmDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultKmDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKmDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultKmDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKmDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares equals to DEFAULT_SHARES
        defaultKmDetailsShouldBeFound("shares.equals=" + DEFAULT_SHARES);

        // Get all the kmDetailsList where shares equals to UPDATED_SHARES
        defaultKmDetailsShouldNotBeFound("shares.equals=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares in DEFAULT_SHARES or UPDATED_SHARES
        defaultKmDetailsShouldBeFound("shares.in=" + DEFAULT_SHARES + "," + UPDATED_SHARES);

        // Get all the kmDetailsList where shares equals to UPDATED_SHARES
        defaultKmDetailsShouldNotBeFound("shares.in=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares is not null
        defaultKmDetailsShouldBeFound("shares.specified=true");

        // Get all the kmDetailsList where shares is null
        defaultKmDetailsShouldNotBeFound("shares.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares is greater than or equal to DEFAULT_SHARES
        defaultKmDetailsShouldBeFound("shares.greaterThanOrEqual=" + DEFAULT_SHARES);

        // Get all the kmDetailsList where shares is greater than or equal to UPDATED_SHARES
        defaultKmDetailsShouldNotBeFound("shares.greaterThanOrEqual=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares is less than or equal to DEFAULT_SHARES
        defaultKmDetailsShouldBeFound("shares.lessThanOrEqual=" + DEFAULT_SHARES);

        // Get all the kmDetailsList where shares is less than or equal to SMALLER_SHARES
        defaultKmDetailsShouldNotBeFound("shares.lessThanOrEqual=" + SMALLER_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares is less than DEFAULT_SHARES
        defaultKmDetailsShouldNotBeFound("shares.lessThan=" + DEFAULT_SHARES);

        // Get all the kmDetailsList where shares is less than UPDATED_SHARES
        defaultKmDetailsShouldBeFound("shares.lessThan=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where shares is greater than DEFAULT_SHARES
        defaultKmDetailsShouldNotBeFound("shares.greaterThan=" + DEFAULT_SHARES);

        // Get all the kmDetailsList where shares is greater than SMALLER_SHARES
        defaultKmDetailsShouldBeFound("shares.greaterThan=" + SMALLER_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sharesMr equals to DEFAULT_SHARES_MR
        defaultKmDetailsShouldBeFound("sharesMr.equals=" + DEFAULT_SHARES_MR);

        // Get all the kmDetailsList where sharesMr equals to UPDATED_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sharesMr.equals=" + UPDATED_SHARES_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sharesMr in DEFAULT_SHARES_MR or UPDATED_SHARES_MR
        defaultKmDetailsShouldBeFound("sharesMr.in=" + DEFAULT_SHARES_MR + "," + UPDATED_SHARES_MR);

        // Get all the kmDetailsList where sharesMr equals to UPDATED_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sharesMr.in=" + UPDATED_SHARES_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sharesMr is not null
        defaultKmDetailsShouldBeFound("sharesMr.specified=true");

        // Get all the kmDetailsList where sharesMr is null
        defaultKmDetailsShouldNotBeFound("sharesMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sharesMr contains DEFAULT_SHARES_MR
        defaultKmDetailsShouldBeFound("sharesMr.contains=" + DEFAULT_SHARES_MR);

        // Get all the kmDetailsList where sharesMr contains UPDATED_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sharesMr.contains=" + UPDATED_SHARES_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySharesMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sharesMr does not contain DEFAULT_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sharesMr.doesNotContain=" + DEFAULT_SHARES_MR);

        // Get all the kmDetailsList where sharesMr does not contain UPDATED_SHARES_MR
        defaultKmDetailsShouldBeFound("sharesMr.doesNotContain=" + UPDATED_SHARES_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares equals to DEFAULT_SUGAR_SHARES
        defaultKmDetailsShouldBeFound("sugarShares.equals=" + DEFAULT_SUGAR_SHARES);

        // Get all the kmDetailsList where sugarShares equals to UPDATED_SUGAR_SHARES
        defaultKmDetailsShouldNotBeFound("sugarShares.equals=" + UPDATED_SUGAR_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares in DEFAULT_SUGAR_SHARES or UPDATED_SUGAR_SHARES
        defaultKmDetailsShouldBeFound("sugarShares.in=" + DEFAULT_SUGAR_SHARES + "," + UPDATED_SUGAR_SHARES);

        // Get all the kmDetailsList where sugarShares equals to UPDATED_SUGAR_SHARES
        defaultKmDetailsShouldNotBeFound("sugarShares.in=" + UPDATED_SUGAR_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares is not null
        defaultKmDetailsShouldBeFound("sugarShares.specified=true");

        // Get all the kmDetailsList where sugarShares is null
        defaultKmDetailsShouldNotBeFound("sugarShares.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares is greater than or equal to DEFAULT_SUGAR_SHARES
        defaultKmDetailsShouldBeFound("sugarShares.greaterThanOrEqual=" + DEFAULT_SUGAR_SHARES);

        // Get all the kmDetailsList where sugarShares is greater than or equal to UPDATED_SUGAR_SHARES
        defaultKmDetailsShouldNotBeFound("sugarShares.greaterThanOrEqual=" + UPDATED_SUGAR_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares is less than or equal to DEFAULT_SUGAR_SHARES
        defaultKmDetailsShouldBeFound("sugarShares.lessThanOrEqual=" + DEFAULT_SUGAR_SHARES);

        // Get all the kmDetailsList where sugarShares is less than or equal to SMALLER_SUGAR_SHARES
        defaultKmDetailsShouldNotBeFound("sugarShares.lessThanOrEqual=" + SMALLER_SUGAR_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares is less than DEFAULT_SUGAR_SHARES
        defaultKmDetailsShouldNotBeFound("sugarShares.lessThan=" + DEFAULT_SUGAR_SHARES);

        // Get all the kmDetailsList where sugarShares is less than UPDATED_SUGAR_SHARES
        defaultKmDetailsShouldBeFound("sugarShares.lessThan=" + UPDATED_SUGAR_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarShares is greater than DEFAULT_SUGAR_SHARES
        defaultKmDetailsShouldNotBeFound("sugarShares.greaterThan=" + DEFAULT_SUGAR_SHARES);

        // Get all the kmDetailsList where sugarShares is greater than SMALLER_SUGAR_SHARES
        defaultKmDetailsShouldBeFound("sugarShares.greaterThan=" + SMALLER_SUGAR_SHARES);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarSharesMr equals to DEFAULT_SUGAR_SHARES_MR
        defaultKmDetailsShouldBeFound("sugarSharesMr.equals=" + DEFAULT_SUGAR_SHARES_MR);

        // Get all the kmDetailsList where sugarSharesMr equals to UPDATED_SUGAR_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sugarSharesMr.equals=" + UPDATED_SUGAR_SHARES_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarSharesMr in DEFAULT_SUGAR_SHARES_MR or UPDATED_SUGAR_SHARES_MR
        defaultKmDetailsShouldBeFound("sugarSharesMr.in=" + DEFAULT_SUGAR_SHARES_MR + "," + UPDATED_SUGAR_SHARES_MR);

        // Get all the kmDetailsList where sugarSharesMr equals to UPDATED_SUGAR_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sugarSharesMr.in=" + UPDATED_SUGAR_SHARES_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarSharesMr is not null
        defaultKmDetailsShouldBeFound("sugarSharesMr.specified=true");

        // Get all the kmDetailsList where sugarSharesMr is null
        defaultKmDetailsShouldNotBeFound("sugarSharesMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarSharesMr contains DEFAULT_SUGAR_SHARES_MR
        defaultKmDetailsShouldBeFound("sugarSharesMr.contains=" + DEFAULT_SUGAR_SHARES_MR);

        // Get all the kmDetailsList where sugarSharesMr contains UPDATED_SUGAR_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sugarSharesMr.contains=" + UPDATED_SUGAR_SHARES_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySugarSharesMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where sugarSharesMr does not contain DEFAULT_SUGAR_SHARES_MR
        defaultKmDetailsShouldNotBeFound("sugarSharesMr.doesNotContain=" + DEFAULT_SUGAR_SHARES_MR);

        // Get all the kmDetailsList where sugarSharesMr does not contain UPDATED_SUGAR_SHARES_MR
        defaultKmDetailsShouldBeFound("sugarSharesMr.doesNotContain=" + UPDATED_SUGAR_SHARES_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposite equals to DEFAULT_DEPOSITE
        defaultKmDetailsShouldBeFound("deposite.equals=" + DEFAULT_DEPOSITE);

        // Get all the kmDetailsList where deposite equals to UPDATED_DEPOSITE
        defaultKmDetailsShouldNotBeFound("deposite.equals=" + UPDATED_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposite in DEFAULT_DEPOSITE or UPDATED_DEPOSITE
        defaultKmDetailsShouldBeFound("deposite.in=" + DEFAULT_DEPOSITE + "," + UPDATED_DEPOSITE);

        // Get all the kmDetailsList where deposite equals to UPDATED_DEPOSITE
        defaultKmDetailsShouldNotBeFound("deposite.in=" + UPDATED_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposite is not null
        defaultKmDetailsShouldBeFound("deposite.specified=true");

        // Get all the kmDetailsList where deposite is null
        defaultKmDetailsShouldNotBeFound("deposite.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposite is greater than or equal to DEFAULT_DEPOSITE
        defaultKmDetailsShouldBeFound("deposite.greaterThanOrEqual=" + DEFAULT_DEPOSITE);

        // Get all the kmDetailsList where deposite is greater than or equal to UPDATED_DEPOSITE
        defaultKmDetailsShouldNotBeFound("deposite.greaterThanOrEqual=" + UPDATED_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposite is less than or equal to DEFAULT_DEPOSITE
        defaultKmDetailsShouldBeFound("deposite.lessThanOrEqual=" + DEFAULT_DEPOSITE);

        // Get all the kmDetailsList where deposite is less than or equal to SMALLER_DEPOSITE
        defaultKmDetailsShouldNotBeFound("deposite.lessThanOrEqual=" + SMALLER_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposite is less than DEFAULT_DEPOSITE
        defaultKmDetailsShouldNotBeFound("deposite.lessThan=" + DEFAULT_DEPOSITE);

        // Get all the kmDetailsList where deposite is less than UPDATED_DEPOSITE
        defaultKmDetailsShouldBeFound("deposite.lessThan=" + UPDATED_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where deposite is greater than DEFAULT_DEPOSITE
        defaultKmDetailsShouldNotBeFound("deposite.greaterThan=" + DEFAULT_DEPOSITE);

        // Get all the kmDetailsList where deposite is greater than SMALLER_DEPOSITE
        defaultKmDetailsShouldBeFound("deposite.greaterThan=" + SMALLER_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where depositeMr equals to DEFAULT_DEPOSITE_MR
        defaultKmDetailsShouldBeFound("depositeMr.equals=" + DEFAULT_DEPOSITE_MR);

        // Get all the kmDetailsList where depositeMr equals to UPDATED_DEPOSITE_MR
        defaultKmDetailsShouldNotBeFound("depositeMr.equals=" + UPDATED_DEPOSITE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where depositeMr in DEFAULT_DEPOSITE_MR or UPDATED_DEPOSITE_MR
        defaultKmDetailsShouldBeFound("depositeMr.in=" + DEFAULT_DEPOSITE_MR + "," + UPDATED_DEPOSITE_MR);

        // Get all the kmDetailsList where depositeMr equals to UPDATED_DEPOSITE_MR
        defaultKmDetailsShouldNotBeFound("depositeMr.in=" + UPDATED_DEPOSITE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where depositeMr is not null
        defaultKmDetailsShouldBeFound("depositeMr.specified=true");

        // Get all the kmDetailsList where depositeMr is null
        defaultKmDetailsShouldNotBeFound("depositeMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where depositeMr contains DEFAULT_DEPOSITE_MR
        defaultKmDetailsShouldBeFound("depositeMr.contains=" + DEFAULT_DEPOSITE_MR);

        // Get all the kmDetailsList where depositeMr contains UPDATED_DEPOSITE_MR
        defaultKmDetailsShouldNotBeFound("depositeMr.contains=" + UPDATED_DEPOSITE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDepositeMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where depositeMr does not contain DEFAULT_DEPOSITE_MR
        defaultKmDetailsShouldNotBeFound("depositeMr.doesNotContain=" + DEFAULT_DEPOSITE_MR);

        // Get all the kmDetailsList where depositeMr does not contain UPDATED_DEPOSITE_MR
        defaultKmDetailsShouldBeFound("depositeMr.doesNotContain=" + UPDATED_DEPOSITE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan equals to DEFAULT_DUE_LOAN
        defaultKmDetailsShouldBeFound("dueLoan.equals=" + DEFAULT_DUE_LOAN);

        // Get all the kmDetailsList where dueLoan equals to UPDATED_DUE_LOAN
        defaultKmDetailsShouldNotBeFound("dueLoan.equals=" + UPDATED_DUE_LOAN);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan in DEFAULT_DUE_LOAN or UPDATED_DUE_LOAN
        defaultKmDetailsShouldBeFound("dueLoan.in=" + DEFAULT_DUE_LOAN + "," + UPDATED_DUE_LOAN);

        // Get all the kmDetailsList where dueLoan equals to UPDATED_DUE_LOAN
        defaultKmDetailsShouldNotBeFound("dueLoan.in=" + UPDATED_DUE_LOAN);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan is not null
        defaultKmDetailsShouldBeFound("dueLoan.specified=true");

        // Get all the kmDetailsList where dueLoan is null
        defaultKmDetailsShouldNotBeFound("dueLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan is greater than or equal to DEFAULT_DUE_LOAN
        defaultKmDetailsShouldBeFound("dueLoan.greaterThanOrEqual=" + DEFAULT_DUE_LOAN);

        // Get all the kmDetailsList where dueLoan is greater than or equal to UPDATED_DUE_LOAN
        defaultKmDetailsShouldNotBeFound("dueLoan.greaterThanOrEqual=" + UPDATED_DUE_LOAN);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan is less than or equal to DEFAULT_DUE_LOAN
        defaultKmDetailsShouldBeFound("dueLoan.lessThanOrEqual=" + DEFAULT_DUE_LOAN);

        // Get all the kmDetailsList where dueLoan is less than or equal to SMALLER_DUE_LOAN
        defaultKmDetailsShouldNotBeFound("dueLoan.lessThanOrEqual=" + SMALLER_DUE_LOAN);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan is less than DEFAULT_DUE_LOAN
        defaultKmDetailsShouldNotBeFound("dueLoan.lessThan=" + DEFAULT_DUE_LOAN);

        // Get all the kmDetailsList where dueLoan is less than UPDATED_DUE_LOAN
        defaultKmDetailsShouldBeFound("dueLoan.lessThan=" + UPDATED_DUE_LOAN);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoan is greater than DEFAULT_DUE_LOAN
        defaultKmDetailsShouldNotBeFound("dueLoan.greaterThan=" + DEFAULT_DUE_LOAN);

        // Get all the kmDetailsList where dueLoan is greater than SMALLER_DUE_LOAN
        defaultKmDetailsShouldBeFound("dueLoan.greaterThan=" + SMALLER_DUE_LOAN);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoanMr equals to DEFAULT_DUE_LOAN_MR
        defaultKmDetailsShouldBeFound("dueLoanMr.equals=" + DEFAULT_DUE_LOAN_MR);

        // Get all the kmDetailsList where dueLoanMr equals to UPDATED_DUE_LOAN_MR
        defaultKmDetailsShouldNotBeFound("dueLoanMr.equals=" + UPDATED_DUE_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoanMr in DEFAULT_DUE_LOAN_MR or UPDATED_DUE_LOAN_MR
        defaultKmDetailsShouldBeFound("dueLoanMr.in=" + DEFAULT_DUE_LOAN_MR + "," + UPDATED_DUE_LOAN_MR);

        // Get all the kmDetailsList where dueLoanMr equals to UPDATED_DUE_LOAN_MR
        defaultKmDetailsShouldNotBeFound("dueLoanMr.in=" + UPDATED_DUE_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoanMr is not null
        defaultKmDetailsShouldBeFound("dueLoanMr.specified=true");

        // Get all the kmDetailsList where dueLoanMr is null
        defaultKmDetailsShouldNotBeFound("dueLoanMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoanMr contains DEFAULT_DUE_LOAN_MR
        defaultKmDetailsShouldBeFound("dueLoanMr.contains=" + DEFAULT_DUE_LOAN_MR);

        // Get all the kmDetailsList where dueLoanMr contains UPDATED_DUE_LOAN_MR
        defaultKmDetailsShouldNotBeFound("dueLoanMr.contains=" + UPDATED_DUE_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueLoanMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueLoanMr does not contain DEFAULT_DUE_LOAN_MR
        defaultKmDetailsShouldNotBeFound("dueLoanMr.doesNotContain=" + DEFAULT_DUE_LOAN_MR);

        // Get all the kmDetailsList where dueLoanMr does not contain UPDATED_DUE_LOAN_MR
        defaultKmDetailsShouldBeFound("dueLoanMr.doesNotContain=" + UPDATED_DUE_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount equals to DEFAULT_DUE_AMOUNT
        defaultKmDetailsShouldBeFound("dueAmount.equals=" + DEFAULT_DUE_AMOUNT);

        // Get all the kmDetailsList where dueAmount equals to UPDATED_DUE_AMOUNT
        defaultKmDetailsShouldNotBeFound("dueAmount.equals=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount in DEFAULT_DUE_AMOUNT or UPDATED_DUE_AMOUNT
        defaultKmDetailsShouldBeFound("dueAmount.in=" + DEFAULT_DUE_AMOUNT + "," + UPDATED_DUE_AMOUNT);

        // Get all the kmDetailsList where dueAmount equals to UPDATED_DUE_AMOUNT
        defaultKmDetailsShouldNotBeFound("dueAmount.in=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount is not null
        defaultKmDetailsShouldBeFound("dueAmount.specified=true");

        // Get all the kmDetailsList where dueAmount is null
        defaultKmDetailsShouldNotBeFound("dueAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount is greater than or equal to DEFAULT_DUE_AMOUNT
        defaultKmDetailsShouldBeFound("dueAmount.greaterThanOrEqual=" + DEFAULT_DUE_AMOUNT);

        // Get all the kmDetailsList where dueAmount is greater than or equal to UPDATED_DUE_AMOUNT
        defaultKmDetailsShouldNotBeFound("dueAmount.greaterThanOrEqual=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount is less than or equal to DEFAULT_DUE_AMOUNT
        defaultKmDetailsShouldBeFound("dueAmount.lessThanOrEqual=" + DEFAULT_DUE_AMOUNT);

        // Get all the kmDetailsList where dueAmount is less than or equal to SMALLER_DUE_AMOUNT
        defaultKmDetailsShouldNotBeFound("dueAmount.lessThanOrEqual=" + SMALLER_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount is less than DEFAULT_DUE_AMOUNT
        defaultKmDetailsShouldNotBeFound("dueAmount.lessThan=" + DEFAULT_DUE_AMOUNT);

        // Get all the kmDetailsList where dueAmount is less than UPDATED_DUE_AMOUNT
        defaultKmDetailsShouldBeFound("dueAmount.lessThan=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmount is greater than DEFAULT_DUE_AMOUNT
        defaultKmDetailsShouldNotBeFound("dueAmount.greaterThan=" + DEFAULT_DUE_AMOUNT);

        // Get all the kmDetailsList where dueAmount is greater than SMALLER_DUE_AMOUNT
        defaultKmDetailsShouldBeFound("dueAmount.greaterThan=" + SMALLER_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmountMr equals to DEFAULT_DUE_AMOUNT_MR
        defaultKmDetailsShouldBeFound("dueAmountMr.equals=" + DEFAULT_DUE_AMOUNT_MR);

        // Get all the kmDetailsList where dueAmountMr equals to UPDATED_DUE_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("dueAmountMr.equals=" + UPDATED_DUE_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmountMr in DEFAULT_DUE_AMOUNT_MR or UPDATED_DUE_AMOUNT_MR
        defaultKmDetailsShouldBeFound("dueAmountMr.in=" + DEFAULT_DUE_AMOUNT_MR + "," + UPDATED_DUE_AMOUNT_MR);

        // Get all the kmDetailsList where dueAmountMr equals to UPDATED_DUE_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("dueAmountMr.in=" + UPDATED_DUE_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmountMr is not null
        defaultKmDetailsShouldBeFound("dueAmountMr.specified=true");

        // Get all the kmDetailsList where dueAmountMr is null
        defaultKmDetailsShouldNotBeFound("dueAmountMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmountMr contains DEFAULT_DUE_AMOUNT_MR
        defaultKmDetailsShouldBeFound("dueAmountMr.contains=" + DEFAULT_DUE_AMOUNT_MR);

        // Get all the kmDetailsList where dueAmountMr contains UPDATED_DUE_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("dueAmountMr.contains=" + UPDATED_DUE_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueAmountMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueAmountMr does not contain DEFAULT_DUE_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("dueAmountMr.doesNotContain=" + DEFAULT_DUE_AMOUNT_MR);

        // Get all the kmDetailsList where dueAmountMr does not contain UPDATED_DUE_AMOUNT_MR
        defaultKmDetailsShouldBeFound("dueAmountMr.doesNotContain=" + UPDATED_DUE_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDateMr equals to DEFAULT_DUE_DATE_MR
        defaultKmDetailsShouldBeFound("dueDateMr.equals=" + DEFAULT_DUE_DATE_MR);

        // Get all the kmDetailsList where dueDateMr equals to UPDATED_DUE_DATE_MR
        defaultKmDetailsShouldNotBeFound("dueDateMr.equals=" + UPDATED_DUE_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDateMr in DEFAULT_DUE_DATE_MR or UPDATED_DUE_DATE_MR
        defaultKmDetailsShouldBeFound("dueDateMr.in=" + DEFAULT_DUE_DATE_MR + "," + UPDATED_DUE_DATE_MR);

        // Get all the kmDetailsList where dueDateMr equals to UPDATED_DUE_DATE_MR
        defaultKmDetailsShouldNotBeFound("dueDateMr.in=" + UPDATED_DUE_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDateMr is not null
        defaultKmDetailsShouldBeFound("dueDateMr.specified=true");

        // Get all the kmDetailsList where dueDateMr is null
        defaultKmDetailsShouldNotBeFound("dueDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueDateMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDateMr contains DEFAULT_DUE_DATE_MR
        defaultKmDetailsShouldBeFound("dueDateMr.contains=" + DEFAULT_DUE_DATE_MR);

        // Get all the kmDetailsList where dueDateMr contains UPDATED_DUE_DATE_MR
        defaultKmDetailsShouldNotBeFound("dueDateMr.contains=" + UPDATED_DUE_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDateMr does not contain DEFAULT_DUE_DATE_MR
        defaultKmDetailsShouldNotBeFound("dueDateMr.doesNotContain=" + DEFAULT_DUE_DATE_MR);

        // Get all the kmDetailsList where dueDateMr does not contain UPDATED_DUE_DATE_MR
        defaultKmDetailsShouldBeFound("dueDateMr.doesNotContain=" + UPDATED_DUE_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDate equals to DEFAULT_DUE_DATE
        defaultKmDetailsShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the kmDetailsList where dueDate equals to UPDATED_DUE_DATE
        defaultKmDetailsShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultKmDetailsShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the kmDetailsList where dueDate equals to UPDATED_DUE_DATE
        defaultKmDetailsShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where dueDate is not null
        defaultKmDetailsShouldBeFound("dueDate.specified=true");

        // Get all the kmDetailsList where dueDate is null
        defaultKmDetailsShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDate equals to DEFAULT_KM_DATE
        defaultKmDetailsShouldBeFound("kmDate.equals=" + DEFAULT_KM_DATE);

        // Get all the kmDetailsList where kmDate equals to UPDATED_KM_DATE
        defaultKmDetailsShouldNotBeFound("kmDate.equals=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDate in DEFAULT_KM_DATE or UPDATED_KM_DATE
        defaultKmDetailsShouldBeFound("kmDate.in=" + DEFAULT_KM_DATE + "," + UPDATED_KM_DATE);

        // Get all the kmDetailsList where kmDate equals to UPDATED_KM_DATE
        defaultKmDetailsShouldNotBeFound("kmDate.in=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDate is not null
        defaultKmDetailsShouldBeFound("kmDate.specified=true");

        // Get all the kmDetailsList where kmDate is null
        defaultKmDetailsShouldNotBeFound("kmDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDateMr equals to DEFAULT_KM_DATE_MR
        defaultKmDetailsShouldBeFound("kmDateMr.equals=" + DEFAULT_KM_DATE_MR);

        // Get all the kmDetailsList where kmDateMr equals to UPDATED_KM_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmDateMr.equals=" + UPDATED_KM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDateMr in DEFAULT_KM_DATE_MR or UPDATED_KM_DATE_MR
        defaultKmDetailsShouldBeFound("kmDateMr.in=" + DEFAULT_KM_DATE_MR + "," + UPDATED_KM_DATE_MR);

        // Get all the kmDetailsList where kmDateMr equals to UPDATED_KM_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmDateMr.in=" + UPDATED_KM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDateMr is not null
        defaultKmDetailsShouldBeFound("kmDateMr.specified=true");

        // Get all the kmDetailsList where kmDateMr is null
        defaultKmDetailsShouldNotBeFound("kmDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmDateMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDateMr contains DEFAULT_KM_DATE_MR
        defaultKmDetailsShouldBeFound("kmDateMr.contains=" + DEFAULT_KM_DATE_MR);

        // Get all the kmDetailsList where kmDateMr contains UPDATED_KM_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmDateMr.contains=" + UPDATED_KM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmDateMr does not contain DEFAULT_KM_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmDateMr.doesNotContain=" + DEFAULT_KM_DATE_MR);

        // Get all the kmDetailsList where kmDateMr does not contain UPDATED_KM_DATE_MR
        defaultKmDetailsShouldBeFound("kmDateMr.doesNotContain=" + UPDATED_KM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDate equals to DEFAULT_KM_FROM_DATE
        defaultKmDetailsShouldBeFound("kmFromDate.equals=" + DEFAULT_KM_FROM_DATE);

        // Get all the kmDetailsList where kmFromDate equals to UPDATED_KM_FROM_DATE
        defaultKmDetailsShouldNotBeFound("kmFromDate.equals=" + UPDATED_KM_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDate in DEFAULT_KM_FROM_DATE or UPDATED_KM_FROM_DATE
        defaultKmDetailsShouldBeFound("kmFromDate.in=" + DEFAULT_KM_FROM_DATE + "," + UPDATED_KM_FROM_DATE);

        // Get all the kmDetailsList where kmFromDate equals to UPDATED_KM_FROM_DATE
        defaultKmDetailsShouldNotBeFound("kmFromDate.in=" + UPDATED_KM_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDate is not null
        defaultKmDetailsShouldBeFound("kmFromDate.specified=true");

        // Get all the kmDetailsList where kmFromDate is null
        defaultKmDetailsShouldNotBeFound("kmFromDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmFromDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDateMr equals to DEFAULT_KM_FROM_DATE_MR
        defaultKmDetailsShouldBeFound("kmFromDateMr.equals=" + DEFAULT_KM_FROM_DATE_MR);

        // Get all the kmDetailsList where kmFromDateMr equals to UPDATED_KM_FROM_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmFromDateMr.equals=" + UPDATED_KM_FROM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmFromDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDateMr in DEFAULT_KM_FROM_DATE_MR or UPDATED_KM_FROM_DATE_MR
        defaultKmDetailsShouldBeFound("kmFromDateMr.in=" + DEFAULT_KM_FROM_DATE_MR + "," + UPDATED_KM_FROM_DATE_MR);

        // Get all the kmDetailsList where kmFromDateMr equals to UPDATED_KM_FROM_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmFromDateMr.in=" + UPDATED_KM_FROM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmFromDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDateMr is not null
        defaultKmDetailsShouldBeFound("kmFromDateMr.specified=true");

        // Get all the kmDetailsList where kmFromDateMr is null
        defaultKmDetailsShouldNotBeFound("kmFromDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmFromDateMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDateMr contains DEFAULT_KM_FROM_DATE_MR
        defaultKmDetailsShouldBeFound("kmFromDateMr.contains=" + DEFAULT_KM_FROM_DATE_MR);

        // Get all the kmDetailsList where kmFromDateMr contains UPDATED_KM_FROM_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmFromDateMr.contains=" + UPDATED_KM_FROM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmFromDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmFromDateMr does not contain DEFAULT_KM_FROM_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmFromDateMr.doesNotContain=" + DEFAULT_KM_FROM_DATE_MR);

        // Get all the kmDetailsList where kmFromDateMr does not contain UPDATED_KM_FROM_DATE_MR
        defaultKmDetailsShouldBeFound("kmFromDateMr.doesNotContain=" + UPDATED_KM_FROM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDate equals to DEFAULT_KM_TO_DATE
        defaultKmDetailsShouldBeFound("kmToDate.equals=" + DEFAULT_KM_TO_DATE);

        // Get all the kmDetailsList where kmToDate equals to UPDATED_KM_TO_DATE
        defaultKmDetailsShouldNotBeFound("kmToDate.equals=" + UPDATED_KM_TO_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmToDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDate in DEFAULT_KM_TO_DATE or UPDATED_KM_TO_DATE
        defaultKmDetailsShouldBeFound("kmToDate.in=" + DEFAULT_KM_TO_DATE + "," + UPDATED_KM_TO_DATE);

        // Get all the kmDetailsList where kmToDate equals to UPDATED_KM_TO_DATE
        defaultKmDetailsShouldNotBeFound("kmToDate.in=" + UPDATED_KM_TO_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDate is not null
        defaultKmDetailsShouldBeFound("kmToDate.specified=true");

        // Get all the kmDetailsList where kmToDate is null
        defaultKmDetailsShouldNotBeFound("kmToDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmToDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDateMr equals to DEFAULT_KM_TO_DATE_MR
        defaultKmDetailsShouldBeFound("kmToDateMr.equals=" + DEFAULT_KM_TO_DATE_MR);

        // Get all the kmDetailsList where kmToDateMr equals to UPDATED_KM_TO_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmToDateMr.equals=" + UPDATED_KM_TO_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmToDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDateMr in DEFAULT_KM_TO_DATE_MR or UPDATED_KM_TO_DATE_MR
        defaultKmDetailsShouldBeFound("kmToDateMr.in=" + DEFAULT_KM_TO_DATE_MR + "," + UPDATED_KM_TO_DATE_MR);

        // Get all the kmDetailsList where kmToDateMr equals to UPDATED_KM_TO_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmToDateMr.in=" + UPDATED_KM_TO_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmToDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDateMr is not null
        defaultKmDetailsShouldBeFound("kmToDateMr.specified=true");

        // Get all the kmDetailsList where kmToDateMr is null
        defaultKmDetailsShouldNotBeFound("kmToDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmToDateMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDateMr contains DEFAULT_KM_TO_DATE_MR
        defaultKmDetailsShouldBeFound("kmToDateMr.contains=" + DEFAULT_KM_TO_DATE_MR);

        // Get all the kmDetailsList where kmToDateMr contains UPDATED_KM_TO_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmToDateMr.contains=" + UPDATED_KM_TO_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmToDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where kmToDateMr does not contain DEFAULT_KM_TO_DATE_MR
        defaultKmDetailsShouldNotBeFound("kmToDateMr.doesNotContain=" + DEFAULT_KM_TO_DATE_MR);

        // Get all the kmDetailsList where kmToDateMr does not contain UPDATED_KM_TO_DATE_MR
        defaultKmDetailsShouldBeFound("kmToDateMr.doesNotContain=" + UPDATED_KM_TO_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector equals to DEFAULT_BAGAYAT_HECTOR
        defaultKmDetailsShouldBeFound("bagayatHector.equals=" + DEFAULT_BAGAYAT_HECTOR);

        // Get all the kmDetailsList where bagayatHector equals to UPDATED_BAGAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("bagayatHector.equals=" + UPDATED_BAGAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector in DEFAULT_BAGAYAT_HECTOR or UPDATED_BAGAYAT_HECTOR
        defaultKmDetailsShouldBeFound("bagayatHector.in=" + DEFAULT_BAGAYAT_HECTOR + "," + UPDATED_BAGAYAT_HECTOR);

        // Get all the kmDetailsList where bagayatHector equals to UPDATED_BAGAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("bagayatHector.in=" + UPDATED_BAGAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector is not null
        defaultKmDetailsShouldBeFound("bagayatHector.specified=true");

        // Get all the kmDetailsList where bagayatHector is null
        defaultKmDetailsShouldNotBeFound("bagayatHector.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector is greater than or equal to DEFAULT_BAGAYAT_HECTOR
        defaultKmDetailsShouldBeFound("bagayatHector.greaterThanOrEqual=" + DEFAULT_BAGAYAT_HECTOR);

        // Get all the kmDetailsList where bagayatHector is greater than or equal to UPDATED_BAGAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("bagayatHector.greaterThanOrEqual=" + UPDATED_BAGAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector is less than or equal to DEFAULT_BAGAYAT_HECTOR
        defaultKmDetailsShouldBeFound("bagayatHector.lessThanOrEqual=" + DEFAULT_BAGAYAT_HECTOR);

        // Get all the kmDetailsList where bagayatHector is less than or equal to SMALLER_BAGAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("bagayatHector.lessThanOrEqual=" + SMALLER_BAGAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector is less than DEFAULT_BAGAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("bagayatHector.lessThan=" + DEFAULT_BAGAYAT_HECTOR);

        // Get all the kmDetailsList where bagayatHector is less than UPDATED_BAGAYAT_HECTOR
        defaultKmDetailsShouldBeFound("bagayatHector.lessThan=" + UPDATED_BAGAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHector is greater than DEFAULT_BAGAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("bagayatHector.greaterThan=" + DEFAULT_BAGAYAT_HECTOR);

        // Get all the kmDetailsList where bagayatHector is greater than SMALLER_BAGAYAT_HECTOR
        defaultKmDetailsShouldBeFound("bagayatHector.greaterThan=" + SMALLER_BAGAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHectorMr equals to DEFAULT_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("bagayatHectorMr.equals=" + DEFAULT_BAGAYAT_HECTOR_MR);

        // Get all the kmDetailsList where bagayatHectorMr equals to UPDATED_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("bagayatHectorMr.equals=" + UPDATED_BAGAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHectorMr in DEFAULT_BAGAYAT_HECTOR_MR or UPDATED_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("bagayatHectorMr.in=" + DEFAULT_BAGAYAT_HECTOR_MR + "," + UPDATED_BAGAYAT_HECTOR_MR);

        // Get all the kmDetailsList where bagayatHectorMr equals to UPDATED_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("bagayatHectorMr.in=" + UPDATED_BAGAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHectorMr is not null
        defaultKmDetailsShouldBeFound("bagayatHectorMr.specified=true");

        // Get all the kmDetailsList where bagayatHectorMr is null
        defaultKmDetailsShouldNotBeFound("bagayatHectorMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHectorMr contains DEFAULT_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("bagayatHectorMr.contains=" + DEFAULT_BAGAYAT_HECTOR_MR);

        // Get all the kmDetailsList where bagayatHectorMr contains UPDATED_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("bagayatHectorMr.contains=" + UPDATED_BAGAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatHectorMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatHectorMr does not contain DEFAULT_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("bagayatHectorMr.doesNotContain=" + DEFAULT_BAGAYAT_HECTOR_MR);

        // Get all the kmDetailsList where bagayatHectorMr does not contain UPDATED_BAGAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("bagayatHectorMr.doesNotContain=" + UPDATED_BAGAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre equals to DEFAULT_BAGAYAT_ARE
        defaultKmDetailsShouldBeFound("bagayatAre.equals=" + DEFAULT_BAGAYAT_ARE);

        // Get all the kmDetailsList where bagayatAre equals to UPDATED_BAGAYAT_ARE
        defaultKmDetailsShouldNotBeFound("bagayatAre.equals=" + UPDATED_BAGAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre in DEFAULT_BAGAYAT_ARE or UPDATED_BAGAYAT_ARE
        defaultKmDetailsShouldBeFound("bagayatAre.in=" + DEFAULT_BAGAYAT_ARE + "," + UPDATED_BAGAYAT_ARE);

        // Get all the kmDetailsList where bagayatAre equals to UPDATED_BAGAYAT_ARE
        defaultKmDetailsShouldNotBeFound("bagayatAre.in=" + UPDATED_BAGAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre is not null
        defaultKmDetailsShouldBeFound("bagayatAre.specified=true");

        // Get all the kmDetailsList where bagayatAre is null
        defaultKmDetailsShouldNotBeFound("bagayatAre.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre is greater than or equal to DEFAULT_BAGAYAT_ARE
        defaultKmDetailsShouldBeFound("bagayatAre.greaterThanOrEqual=" + DEFAULT_BAGAYAT_ARE);

        // Get all the kmDetailsList where bagayatAre is greater than or equal to UPDATED_BAGAYAT_ARE
        defaultKmDetailsShouldNotBeFound("bagayatAre.greaterThanOrEqual=" + UPDATED_BAGAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre is less than or equal to DEFAULT_BAGAYAT_ARE
        defaultKmDetailsShouldBeFound("bagayatAre.lessThanOrEqual=" + DEFAULT_BAGAYAT_ARE);

        // Get all the kmDetailsList where bagayatAre is less than or equal to SMALLER_BAGAYAT_ARE
        defaultKmDetailsShouldNotBeFound("bagayatAre.lessThanOrEqual=" + SMALLER_BAGAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre is less than DEFAULT_BAGAYAT_ARE
        defaultKmDetailsShouldNotBeFound("bagayatAre.lessThan=" + DEFAULT_BAGAYAT_ARE);

        // Get all the kmDetailsList where bagayatAre is less than UPDATED_BAGAYAT_ARE
        defaultKmDetailsShouldBeFound("bagayatAre.lessThan=" + UPDATED_BAGAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAre is greater than DEFAULT_BAGAYAT_ARE
        defaultKmDetailsShouldNotBeFound("bagayatAre.greaterThan=" + DEFAULT_BAGAYAT_ARE);

        // Get all the kmDetailsList where bagayatAre is greater than SMALLER_BAGAYAT_ARE
        defaultKmDetailsShouldBeFound("bagayatAre.greaterThan=" + SMALLER_BAGAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAreMr equals to DEFAULT_BAGAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("bagayatAreMr.equals=" + DEFAULT_BAGAYAT_ARE_MR);

        // Get all the kmDetailsList where bagayatAreMr equals to UPDATED_BAGAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("bagayatAreMr.equals=" + UPDATED_BAGAYAT_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAreMr in DEFAULT_BAGAYAT_ARE_MR or UPDATED_BAGAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("bagayatAreMr.in=" + DEFAULT_BAGAYAT_ARE_MR + "," + UPDATED_BAGAYAT_ARE_MR);

        // Get all the kmDetailsList where bagayatAreMr equals to UPDATED_BAGAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("bagayatAreMr.in=" + UPDATED_BAGAYAT_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAreMr is not null
        defaultKmDetailsShouldBeFound("bagayatAreMr.specified=true");

        // Get all the kmDetailsList where bagayatAreMr is null
        defaultKmDetailsShouldNotBeFound("bagayatAreMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAreMr contains DEFAULT_BAGAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("bagayatAreMr.contains=" + DEFAULT_BAGAYAT_ARE_MR);

        // Get all the kmDetailsList where bagayatAreMr contains UPDATED_BAGAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("bagayatAreMr.contains=" + UPDATED_BAGAYAT_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBagayatAreMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bagayatAreMr does not contain DEFAULT_BAGAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("bagayatAreMr.doesNotContain=" + DEFAULT_BAGAYAT_ARE_MR);

        // Get all the kmDetailsList where bagayatAreMr does not contain UPDATED_BAGAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("bagayatAreMr.doesNotContain=" + UPDATED_BAGAYAT_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector equals to DEFAULT_JIRAYAT_HECTOR
        defaultKmDetailsShouldBeFound("jirayatHector.equals=" + DEFAULT_JIRAYAT_HECTOR);

        // Get all the kmDetailsList where jirayatHector equals to UPDATED_JIRAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("jirayatHector.equals=" + UPDATED_JIRAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector in DEFAULT_JIRAYAT_HECTOR or UPDATED_JIRAYAT_HECTOR
        defaultKmDetailsShouldBeFound("jirayatHector.in=" + DEFAULT_JIRAYAT_HECTOR + "," + UPDATED_JIRAYAT_HECTOR);

        // Get all the kmDetailsList where jirayatHector equals to UPDATED_JIRAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("jirayatHector.in=" + UPDATED_JIRAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector is not null
        defaultKmDetailsShouldBeFound("jirayatHector.specified=true");

        // Get all the kmDetailsList where jirayatHector is null
        defaultKmDetailsShouldNotBeFound("jirayatHector.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector is greater than or equal to DEFAULT_JIRAYAT_HECTOR
        defaultKmDetailsShouldBeFound("jirayatHector.greaterThanOrEqual=" + DEFAULT_JIRAYAT_HECTOR);

        // Get all the kmDetailsList where jirayatHector is greater than or equal to UPDATED_JIRAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("jirayatHector.greaterThanOrEqual=" + UPDATED_JIRAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector is less than or equal to DEFAULT_JIRAYAT_HECTOR
        defaultKmDetailsShouldBeFound("jirayatHector.lessThanOrEqual=" + DEFAULT_JIRAYAT_HECTOR);

        // Get all the kmDetailsList where jirayatHector is less than or equal to SMALLER_JIRAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("jirayatHector.lessThanOrEqual=" + SMALLER_JIRAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector is less than DEFAULT_JIRAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("jirayatHector.lessThan=" + DEFAULT_JIRAYAT_HECTOR);

        // Get all the kmDetailsList where jirayatHector is less than UPDATED_JIRAYAT_HECTOR
        defaultKmDetailsShouldBeFound("jirayatHector.lessThan=" + UPDATED_JIRAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHector is greater than DEFAULT_JIRAYAT_HECTOR
        defaultKmDetailsShouldNotBeFound("jirayatHector.greaterThan=" + DEFAULT_JIRAYAT_HECTOR);

        // Get all the kmDetailsList where jirayatHector is greater than SMALLER_JIRAYAT_HECTOR
        defaultKmDetailsShouldBeFound("jirayatHector.greaterThan=" + SMALLER_JIRAYAT_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHectorMr equals to DEFAULT_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("jirayatHectorMr.equals=" + DEFAULT_JIRAYAT_HECTOR_MR);

        // Get all the kmDetailsList where jirayatHectorMr equals to UPDATED_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("jirayatHectorMr.equals=" + UPDATED_JIRAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHectorMr in DEFAULT_JIRAYAT_HECTOR_MR or UPDATED_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("jirayatHectorMr.in=" + DEFAULT_JIRAYAT_HECTOR_MR + "," + UPDATED_JIRAYAT_HECTOR_MR);

        // Get all the kmDetailsList where jirayatHectorMr equals to UPDATED_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("jirayatHectorMr.in=" + UPDATED_JIRAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHectorMr is not null
        defaultKmDetailsShouldBeFound("jirayatHectorMr.specified=true");

        // Get all the kmDetailsList where jirayatHectorMr is null
        defaultKmDetailsShouldNotBeFound("jirayatHectorMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHectorMr contains DEFAULT_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("jirayatHectorMr.contains=" + DEFAULT_JIRAYAT_HECTOR_MR);

        // Get all the kmDetailsList where jirayatHectorMr contains UPDATED_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("jirayatHectorMr.contains=" + UPDATED_JIRAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatHectorMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatHectorMr does not contain DEFAULT_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldNotBeFound("jirayatHectorMr.doesNotContain=" + DEFAULT_JIRAYAT_HECTOR_MR);

        // Get all the kmDetailsList where jirayatHectorMr does not contain UPDATED_JIRAYAT_HECTOR_MR
        defaultKmDetailsShouldBeFound("jirayatHectorMr.doesNotContain=" + UPDATED_JIRAYAT_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre equals to DEFAULT_JIRAYAT_ARE
        defaultKmDetailsShouldBeFound("jirayatAre.equals=" + DEFAULT_JIRAYAT_ARE);

        // Get all the kmDetailsList where jirayatAre equals to UPDATED_JIRAYAT_ARE
        defaultKmDetailsShouldNotBeFound("jirayatAre.equals=" + UPDATED_JIRAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre in DEFAULT_JIRAYAT_ARE or UPDATED_JIRAYAT_ARE
        defaultKmDetailsShouldBeFound("jirayatAre.in=" + DEFAULT_JIRAYAT_ARE + "," + UPDATED_JIRAYAT_ARE);

        // Get all the kmDetailsList where jirayatAre equals to UPDATED_JIRAYAT_ARE
        defaultKmDetailsShouldNotBeFound("jirayatAre.in=" + UPDATED_JIRAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre is not null
        defaultKmDetailsShouldBeFound("jirayatAre.specified=true");

        // Get all the kmDetailsList where jirayatAre is null
        defaultKmDetailsShouldNotBeFound("jirayatAre.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre is greater than or equal to DEFAULT_JIRAYAT_ARE
        defaultKmDetailsShouldBeFound("jirayatAre.greaterThanOrEqual=" + DEFAULT_JIRAYAT_ARE);

        // Get all the kmDetailsList where jirayatAre is greater than or equal to UPDATED_JIRAYAT_ARE
        defaultKmDetailsShouldNotBeFound("jirayatAre.greaterThanOrEqual=" + UPDATED_JIRAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre is less than or equal to DEFAULT_JIRAYAT_ARE
        defaultKmDetailsShouldBeFound("jirayatAre.lessThanOrEqual=" + DEFAULT_JIRAYAT_ARE);

        // Get all the kmDetailsList where jirayatAre is less than or equal to SMALLER_JIRAYAT_ARE
        defaultKmDetailsShouldNotBeFound("jirayatAre.lessThanOrEqual=" + SMALLER_JIRAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre is less than DEFAULT_JIRAYAT_ARE
        defaultKmDetailsShouldNotBeFound("jirayatAre.lessThan=" + DEFAULT_JIRAYAT_ARE);

        // Get all the kmDetailsList where jirayatAre is less than UPDATED_JIRAYAT_ARE
        defaultKmDetailsShouldBeFound("jirayatAre.lessThan=" + UPDATED_JIRAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAre is greater than DEFAULT_JIRAYAT_ARE
        defaultKmDetailsShouldNotBeFound("jirayatAre.greaterThan=" + DEFAULT_JIRAYAT_ARE);

        // Get all the kmDetailsList where jirayatAre is greater than SMALLER_JIRAYAT_ARE
        defaultKmDetailsShouldBeFound("jirayatAre.greaterThan=" + SMALLER_JIRAYAT_ARE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAreMr equals to DEFAULT_JIRAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("jirayatAreMr.equals=" + DEFAULT_JIRAYAT_ARE_MR);

        // Get all the kmDetailsList where jirayatAreMr equals to UPDATED_JIRAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("jirayatAreMr.equals=" + UPDATED_JIRAYAT_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAreMr in DEFAULT_JIRAYAT_ARE_MR or UPDATED_JIRAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("jirayatAreMr.in=" + DEFAULT_JIRAYAT_ARE_MR + "," + UPDATED_JIRAYAT_ARE_MR);

        // Get all the kmDetailsList where jirayatAreMr equals to UPDATED_JIRAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("jirayatAreMr.in=" + UPDATED_JIRAYAT_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAreMr is not null
        defaultKmDetailsShouldBeFound("jirayatAreMr.specified=true");

        // Get all the kmDetailsList where jirayatAreMr is null
        defaultKmDetailsShouldNotBeFound("jirayatAreMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAreMr contains DEFAULT_JIRAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("jirayatAreMr.contains=" + DEFAULT_JIRAYAT_ARE_MR);

        // Get all the kmDetailsList where jirayatAreMr contains UPDATED_JIRAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("jirayatAreMr.contains=" + UPDATED_JIRAYAT_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByJirayatAreMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where jirayatAreMr does not contain DEFAULT_JIRAYAT_ARE_MR
        defaultKmDetailsShouldNotBeFound("jirayatAreMr.doesNotContain=" + DEFAULT_JIRAYAT_ARE_MR);

        // Get all the kmDetailsList where jirayatAreMr does not contain UPDATED_JIRAYAT_ARE_MR
        defaultKmDetailsShouldBeFound("jirayatAreMr.doesNotContain=" + UPDATED_JIRAYAT_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiAmt equals to DEFAULT_ZINDAGI_AMT
        defaultKmDetailsShouldBeFound("zindagiAmt.equals=" + DEFAULT_ZINDAGI_AMT);

        // Get all the kmDetailsList where zindagiAmt equals to UPDATED_ZINDAGI_AMT
        defaultKmDetailsShouldNotBeFound("zindagiAmt.equals=" + UPDATED_ZINDAGI_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiAmt in DEFAULT_ZINDAGI_AMT or UPDATED_ZINDAGI_AMT
        defaultKmDetailsShouldBeFound("zindagiAmt.in=" + DEFAULT_ZINDAGI_AMT + "," + UPDATED_ZINDAGI_AMT);

        // Get all the kmDetailsList where zindagiAmt equals to UPDATED_ZINDAGI_AMT
        defaultKmDetailsShouldNotBeFound("zindagiAmt.in=" + UPDATED_ZINDAGI_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiAmt is not null
        defaultKmDetailsShouldBeFound("zindagiAmt.specified=true");

        // Get all the kmDetailsList where zindagiAmt is null
        defaultKmDetailsShouldNotBeFound("zindagiAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiAmt is greater than or equal to DEFAULT_ZINDAGI_AMT
        defaultKmDetailsShouldBeFound("zindagiAmt.greaterThanOrEqual=" + DEFAULT_ZINDAGI_AMT);

        // Get all the kmDetailsList where zindagiAmt is greater than or equal to UPDATED_ZINDAGI_AMT
        defaultKmDetailsShouldNotBeFound("zindagiAmt.greaterThanOrEqual=" + UPDATED_ZINDAGI_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiAmt is less than or equal to DEFAULT_ZINDAGI_AMT
        defaultKmDetailsShouldBeFound("zindagiAmt.lessThanOrEqual=" + DEFAULT_ZINDAGI_AMT);

        // Get all the kmDetailsList where zindagiAmt is less than or equal to SMALLER_ZINDAGI_AMT
        defaultKmDetailsShouldNotBeFound("zindagiAmt.lessThanOrEqual=" + SMALLER_ZINDAGI_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiAmt is less than DEFAULT_ZINDAGI_AMT
        defaultKmDetailsShouldNotBeFound("zindagiAmt.lessThan=" + DEFAULT_ZINDAGI_AMT);

        // Get all the kmDetailsList where zindagiAmt is less than UPDATED_ZINDAGI_AMT
        defaultKmDetailsShouldBeFound("zindagiAmt.lessThan=" + UPDATED_ZINDAGI_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiAmt is greater than DEFAULT_ZINDAGI_AMT
        defaultKmDetailsShouldNotBeFound("zindagiAmt.greaterThan=" + DEFAULT_ZINDAGI_AMT);

        // Get all the kmDetailsList where zindagiAmt is greater than SMALLER_ZINDAGI_AMT
        defaultKmDetailsShouldBeFound("zindagiAmt.greaterThan=" + SMALLER_ZINDAGI_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiNo equals to DEFAULT_ZINDAGI_NO
        defaultKmDetailsShouldBeFound("zindagiNo.equals=" + DEFAULT_ZINDAGI_NO);

        // Get all the kmDetailsList where zindagiNo equals to UPDATED_ZINDAGI_NO
        defaultKmDetailsShouldNotBeFound("zindagiNo.equals=" + UPDATED_ZINDAGI_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiNo in DEFAULT_ZINDAGI_NO or UPDATED_ZINDAGI_NO
        defaultKmDetailsShouldBeFound("zindagiNo.in=" + DEFAULT_ZINDAGI_NO + "," + UPDATED_ZINDAGI_NO);

        // Get all the kmDetailsList where zindagiNo equals to UPDATED_ZINDAGI_NO
        defaultKmDetailsShouldNotBeFound("zindagiNo.in=" + UPDATED_ZINDAGI_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiNo is not null
        defaultKmDetailsShouldBeFound("zindagiNo.specified=true");

        // Get all the kmDetailsList where zindagiNo is null
        defaultKmDetailsShouldNotBeFound("zindagiNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiNo is greater than or equal to DEFAULT_ZINDAGI_NO
        defaultKmDetailsShouldBeFound("zindagiNo.greaterThanOrEqual=" + DEFAULT_ZINDAGI_NO);

        // Get all the kmDetailsList where zindagiNo is greater than or equal to UPDATED_ZINDAGI_NO
        defaultKmDetailsShouldNotBeFound("zindagiNo.greaterThanOrEqual=" + UPDATED_ZINDAGI_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiNo is less than or equal to DEFAULT_ZINDAGI_NO
        defaultKmDetailsShouldBeFound("zindagiNo.lessThanOrEqual=" + DEFAULT_ZINDAGI_NO);

        // Get all the kmDetailsList where zindagiNo is less than or equal to SMALLER_ZINDAGI_NO
        defaultKmDetailsShouldNotBeFound("zindagiNo.lessThanOrEqual=" + SMALLER_ZINDAGI_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiNoIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiNo is less than DEFAULT_ZINDAGI_NO
        defaultKmDetailsShouldNotBeFound("zindagiNo.lessThan=" + DEFAULT_ZINDAGI_NO);

        // Get all the kmDetailsList where zindagiNo is less than UPDATED_ZINDAGI_NO
        defaultKmDetailsShouldBeFound("zindagiNo.lessThan=" + UPDATED_ZINDAGI_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsByZindagiNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where zindagiNo is greater than DEFAULT_ZINDAGI_NO
        defaultKmDetailsShouldNotBeFound("zindagiNo.greaterThan=" + DEFAULT_ZINDAGI_NO);

        // Get all the kmDetailsList where zindagiNo is greater than SMALLER_ZINDAGI_NO
        defaultKmDetailsShouldBeFound("zindagiNo.greaterThan=" + SMALLER_ZINDAGI_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySurveyNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where surveyNo equals to DEFAULT_SURVEY_NO
        defaultKmDetailsShouldBeFound("surveyNo.equals=" + DEFAULT_SURVEY_NO);

        // Get all the kmDetailsList where surveyNo equals to UPDATED_SURVEY_NO
        defaultKmDetailsShouldNotBeFound("surveyNo.equals=" + UPDATED_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySurveyNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where surveyNo in DEFAULT_SURVEY_NO or UPDATED_SURVEY_NO
        defaultKmDetailsShouldBeFound("surveyNo.in=" + DEFAULT_SURVEY_NO + "," + UPDATED_SURVEY_NO);

        // Get all the kmDetailsList where surveyNo equals to UPDATED_SURVEY_NO
        defaultKmDetailsShouldNotBeFound("surveyNo.in=" + UPDATED_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySurveyNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where surveyNo is not null
        defaultKmDetailsShouldBeFound("surveyNo.specified=true");

        // Get all the kmDetailsList where surveyNo is null
        defaultKmDetailsShouldNotBeFound("surveyNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsBySurveyNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where surveyNo is greater than or equal to DEFAULT_SURVEY_NO
        defaultKmDetailsShouldBeFound("surveyNo.greaterThanOrEqual=" + DEFAULT_SURVEY_NO);

        // Get all the kmDetailsList where surveyNo is greater than or equal to UPDATED_SURVEY_NO
        defaultKmDetailsShouldNotBeFound("surveyNo.greaterThanOrEqual=" + UPDATED_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySurveyNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where surveyNo is less than or equal to DEFAULT_SURVEY_NO
        defaultKmDetailsShouldBeFound("surveyNo.lessThanOrEqual=" + DEFAULT_SURVEY_NO);

        // Get all the kmDetailsList where surveyNo is less than or equal to SMALLER_SURVEY_NO
        defaultKmDetailsShouldNotBeFound("surveyNo.lessThanOrEqual=" + SMALLER_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySurveyNoIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where surveyNo is less than DEFAULT_SURVEY_NO
        defaultKmDetailsShouldNotBeFound("surveyNo.lessThan=" + DEFAULT_SURVEY_NO);

        // Get all the kmDetailsList where surveyNo is less than UPDATED_SURVEY_NO
        defaultKmDetailsShouldBeFound("surveyNo.lessThan=" + UPDATED_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsBySurveyNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where surveyNo is greater than DEFAULT_SURVEY_NO
        defaultKmDetailsShouldNotBeFound("surveyNo.greaterThan=" + DEFAULT_SURVEY_NO);

        // Get all the kmDetailsList where surveyNo is greater than SMALLER_SURVEY_NO
        defaultKmDetailsShouldBeFound("surveyNo.greaterThan=" + SMALLER_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue equals to DEFAULT_LAND_VALUE
        defaultKmDetailsShouldBeFound("landValue.equals=" + DEFAULT_LAND_VALUE);

        // Get all the kmDetailsList where landValue equals to UPDATED_LAND_VALUE
        defaultKmDetailsShouldNotBeFound("landValue.equals=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue in DEFAULT_LAND_VALUE or UPDATED_LAND_VALUE
        defaultKmDetailsShouldBeFound("landValue.in=" + DEFAULT_LAND_VALUE + "," + UPDATED_LAND_VALUE);

        // Get all the kmDetailsList where landValue equals to UPDATED_LAND_VALUE
        defaultKmDetailsShouldNotBeFound("landValue.in=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue is not null
        defaultKmDetailsShouldBeFound("landValue.specified=true");

        // Get all the kmDetailsList where landValue is null
        defaultKmDetailsShouldNotBeFound("landValue.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue is greater than or equal to DEFAULT_LAND_VALUE
        defaultKmDetailsShouldBeFound("landValue.greaterThanOrEqual=" + DEFAULT_LAND_VALUE);

        // Get all the kmDetailsList where landValue is greater than or equal to UPDATED_LAND_VALUE
        defaultKmDetailsShouldNotBeFound("landValue.greaterThanOrEqual=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue is less than or equal to DEFAULT_LAND_VALUE
        defaultKmDetailsShouldBeFound("landValue.lessThanOrEqual=" + DEFAULT_LAND_VALUE);

        // Get all the kmDetailsList where landValue is less than or equal to SMALLER_LAND_VALUE
        defaultKmDetailsShouldNotBeFound("landValue.lessThanOrEqual=" + SMALLER_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue is less than DEFAULT_LAND_VALUE
        defaultKmDetailsShouldNotBeFound("landValue.lessThan=" + DEFAULT_LAND_VALUE);

        // Get all the kmDetailsList where landValue is less than UPDATED_LAND_VALUE
        defaultKmDetailsShouldBeFound("landValue.lessThan=" + UPDATED_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValue is greater than DEFAULT_LAND_VALUE
        defaultKmDetailsShouldNotBeFound("landValue.greaterThan=" + DEFAULT_LAND_VALUE);

        // Get all the kmDetailsList where landValue is greater than SMALLER_LAND_VALUE
        defaultKmDetailsShouldBeFound("landValue.greaterThan=" + SMALLER_LAND_VALUE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValueMr equals to DEFAULT_LAND_VALUE_MR
        defaultKmDetailsShouldBeFound("landValueMr.equals=" + DEFAULT_LAND_VALUE_MR);

        // Get all the kmDetailsList where landValueMr equals to UPDATED_LAND_VALUE_MR
        defaultKmDetailsShouldNotBeFound("landValueMr.equals=" + UPDATED_LAND_VALUE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValueMr in DEFAULT_LAND_VALUE_MR or UPDATED_LAND_VALUE_MR
        defaultKmDetailsShouldBeFound("landValueMr.in=" + DEFAULT_LAND_VALUE_MR + "," + UPDATED_LAND_VALUE_MR);

        // Get all the kmDetailsList where landValueMr equals to UPDATED_LAND_VALUE_MR
        defaultKmDetailsShouldNotBeFound("landValueMr.in=" + UPDATED_LAND_VALUE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValueMr is not null
        defaultKmDetailsShouldBeFound("landValueMr.specified=true");

        // Get all the kmDetailsList where landValueMr is null
        defaultKmDetailsShouldNotBeFound("landValueMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValueMr contains DEFAULT_LAND_VALUE_MR
        defaultKmDetailsShouldBeFound("landValueMr.contains=" + DEFAULT_LAND_VALUE_MR);

        // Get all the kmDetailsList where landValueMr contains UPDATED_LAND_VALUE_MR
        defaultKmDetailsShouldNotBeFound("landValueMr.contains=" + UPDATED_LAND_VALUE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByLandValueMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where landValueMr does not contain DEFAULT_LAND_VALUE_MR
        defaultKmDetailsShouldNotBeFound("landValueMr.doesNotContain=" + DEFAULT_LAND_VALUE_MR);

        // Get all the kmDetailsList where landValueMr does not contain UPDATED_LAND_VALUE_MR
        defaultKmDetailsShouldBeFound("landValueMr.doesNotContain=" + UPDATED_LAND_VALUE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt equals to DEFAULT_E_AGREEMENT_AMT
        defaultKmDetailsShouldBeFound("eAgreementAmt.equals=" + DEFAULT_E_AGREEMENT_AMT);

        // Get all the kmDetailsList where eAgreementAmt equals to UPDATED_E_AGREEMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.equals=" + UPDATED_E_AGREEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt in DEFAULT_E_AGREEMENT_AMT or UPDATED_E_AGREEMENT_AMT
        defaultKmDetailsShouldBeFound("eAgreementAmt.in=" + DEFAULT_E_AGREEMENT_AMT + "," + UPDATED_E_AGREEMENT_AMT);

        // Get all the kmDetailsList where eAgreementAmt equals to UPDATED_E_AGREEMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.in=" + UPDATED_E_AGREEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt is not null
        defaultKmDetailsShouldBeFound("eAgreementAmt.specified=true");

        // Get all the kmDetailsList where eAgreementAmt is null
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt is greater than or equal to DEFAULT_E_AGREEMENT_AMT
        defaultKmDetailsShouldBeFound("eAgreementAmt.greaterThanOrEqual=" + DEFAULT_E_AGREEMENT_AMT);

        // Get all the kmDetailsList where eAgreementAmt is greater than or equal to UPDATED_E_AGREEMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.greaterThanOrEqual=" + UPDATED_E_AGREEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt is less than or equal to DEFAULT_E_AGREEMENT_AMT
        defaultKmDetailsShouldBeFound("eAgreementAmt.lessThanOrEqual=" + DEFAULT_E_AGREEMENT_AMT);

        // Get all the kmDetailsList where eAgreementAmt is less than or equal to SMALLER_E_AGREEMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.lessThanOrEqual=" + SMALLER_E_AGREEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt is less than DEFAULT_E_AGREEMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.lessThan=" + DEFAULT_E_AGREEMENT_AMT);

        // Get all the kmDetailsList where eAgreementAmt is less than UPDATED_E_AGREEMENT_AMT
        defaultKmDetailsShouldBeFound("eAgreementAmt.lessThan=" + UPDATED_E_AGREEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmt is greater than DEFAULT_E_AGREEMENT_AMT
        defaultKmDetailsShouldNotBeFound("eAgreementAmt.greaterThan=" + DEFAULT_E_AGREEMENT_AMT);

        // Get all the kmDetailsList where eAgreementAmt is greater than SMALLER_E_AGREEMENT_AMT
        defaultKmDetailsShouldBeFound("eAgreementAmt.greaterThan=" + SMALLER_E_AGREEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmtMr equals to DEFAULT_E_AGREEMENT_AMT_MR
        defaultKmDetailsShouldBeFound("eAgreementAmtMr.equals=" + DEFAULT_E_AGREEMENT_AMT_MR);

        // Get all the kmDetailsList where eAgreementAmtMr equals to UPDATED_E_AGREEMENT_AMT_MR
        defaultKmDetailsShouldNotBeFound("eAgreementAmtMr.equals=" + UPDATED_E_AGREEMENT_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmtMr in DEFAULT_E_AGREEMENT_AMT_MR or UPDATED_E_AGREEMENT_AMT_MR
        defaultKmDetailsShouldBeFound("eAgreementAmtMr.in=" + DEFAULT_E_AGREEMENT_AMT_MR + "," + UPDATED_E_AGREEMENT_AMT_MR);

        // Get all the kmDetailsList where eAgreementAmtMr equals to UPDATED_E_AGREEMENT_AMT_MR
        defaultKmDetailsShouldNotBeFound("eAgreementAmtMr.in=" + UPDATED_E_AGREEMENT_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmtMr is not null
        defaultKmDetailsShouldBeFound("eAgreementAmtMr.specified=true");

        // Get all the kmDetailsList where eAgreementAmtMr is null
        defaultKmDetailsShouldNotBeFound("eAgreementAmtMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmtMr contains DEFAULT_E_AGREEMENT_AMT_MR
        defaultKmDetailsShouldBeFound("eAgreementAmtMr.contains=" + DEFAULT_E_AGREEMENT_AMT_MR);

        // Get all the kmDetailsList where eAgreementAmtMr contains UPDATED_E_AGREEMENT_AMT_MR
        defaultKmDetailsShouldNotBeFound("eAgreementAmtMr.contains=" + UPDATED_E_AGREEMENT_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementAmtMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementAmtMr does not contain DEFAULT_E_AGREEMENT_AMT_MR
        defaultKmDetailsShouldNotBeFound("eAgreementAmtMr.doesNotContain=" + DEFAULT_E_AGREEMENT_AMT_MR);

        // Get all the kmDetailsList where eAgreementAmtMr does not contain UPDATED_E_AGREEMENT_AMT_MR
        defaultKmDetailsShouldBeFound("eAgreementAmtMr.doesNotContain=" + UPDATED_E_AGREEMENT_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDate equals to DEFAULT_E_AGREEMENT_DATE
        defaultKmDetailsShouldBeFound("eAgreementDate.equals=" + DEFAULT_E_AGREEMENT_DATE);

        // Get all the kmDetailsList where eAgreementDate equals to UPDATED_E_AGREEMENT_DATE
        defaultKmDetailsShouldNotBeFound("eAgreementDate.equals=" + UPDATED_E_AGREEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDate in DEFAULT_E_AGREEMENT_DATE or UPDATED_E_AGREEMENT_DATE
        defaultKmDetailsShouldBeFound("eAgreementDate.in=" + DEFAULT_E_AGREEMENT_DATE + "," + UPDATED_E_AGREEMENT_DATE);

        // Get all the kmDetailsList where eAgreementDate equals to UPDATED_E_AGREEMENT_DATE
        defaultKmDetailsShouldNotBeFound("eAgreementDate.in=" + UPDATED_E_AGREEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDate is not null
        defaultKmDetailsShouldBeFound("eAgreementDate.specified=true");

        // Get all the kmDetailsList where eAgreementDate is null
        defaultKmDetailsShouldNotBeFound("eAgreementDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDateMr equals to DEFAULT_E_AGREEMENT_DATE_MR
        defaultKmDetailsShouldBeFound("eAgreementDateMr.equals=" + DEFAULT_E_AGREEMENT_DATE_MR);

        // Get all the kmDetailsList where eAgreementDateMr equals to UPDATED_E_AGREEMENT_DATE_MR
        defaultKmDetailsShouldNotBeFound("eAgreementDateMr.equals=" + UPDATED_E_AGREEMENT_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDateMr in DEFAULT_E_AGREEMENT_DATE_MR or UPDATED_E_AGREEMENT_DATE_MR
        defaultKmDetailsShouldBeFound("eAgreementDateMr.in=" + DEFAULT_E_AGREEMENT_DATE_MR + "," + UPDATED_E_AGREEMENT_DATE_MR);

        // Get all the kmDetailsList where eAgreementDateMr equals to UPDATED_E_AGREEMENT_DATE_MR
        defaultKmDetailsShouldNotBeFound("eAgreementDateMr.in=" + UPDATED_E_AGREEMENT_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDateMr is not null
        defaultKmDetailsShouldBeFound("eAgreementDateMr.specified=true");

        // Get all the kmDetailsList where eAgreementDateMr is null
        defaultKmDetailsShouldNotBeFound("eAgreementDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementDateMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDateMr contains DEFAULT_E_AGREEMENT_DATE_MR
        defaultKmDetailsShouldBeFound("eAgreementDateMr.contains=" + DEFAULT_E_AGREEMENT_DATE_MR);

        // Get all the kmDetailsList where eAgreementDateMr contains UPDATED_E_AGREEMENT_DATE_MR
        defaultKmDetailsShouldNotBeFound("eAgreementDateMr.contains=" + UPDATED_E_AGREEMENT_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByeAgreementDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where eAgreementDateMr does not contain DEFAULT_E_AGREEMENT_DATE_MR
        defaultKmDetailsShouldNotBeFound("eAgreementDateMr.doesNotContain=" + DEFAULT_E_AGREEMENT_DATE_MR);

        // Get all the kmDetailsList where eAgreementDateMr does not contain UPDATED_E_AGREEMENT_DATE_MR
        defaultKmDetailsShouldBeFound("eAgreementDateMr.doesNotContain=" + UPDATED_E_AGREEMENT_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount equals to DEFAULT_BOJA_AMOUNT
        defaultKmDetailsShouldBeFound("bojaAmount.equals=" + DEFAULT_BOJA_AMOUNT);

        // Get all the kmDetailsList where bojaAmount equals to UPDATED_BOJA_AMOUNT
        defaultKmDetailsShouldNotBeFound("bojaAmount.equals=" + UPDATED_BOJA_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount in DEFAULT_BOJA_AMOUNT or UPDATED_BOJA_AMOUNT
        defaultKmDetailsShouldBeFound("bojaAmount.in=" + DEFAULT_BOJA_AMOUNT + "," + UPDATED_BOJA_AMOUNT);

        // Get all the kmDetailsList where bojaAmount equals to UPDATED_BOJA_AMOUNT
        defaultKmDetailsShouldNotBeFound("bojaAmount.in=" + UPDATED_BOJA_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount is not null
        defaultKmDetailsShouldBeFound("bojaAmount.specified=true");

        // Get all the kmDetailsList where bojaAmount is null
        defaultKmDetailsShouldNotBeFound("bojaAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount is greater than or equal to DEFAULT_BOJA_AMOUNT
        defaultKmDetailsShouldBeFound("bojaAmount.greaterThanOrEqual=" + DEFAULT_BOJA_AMOUNT);

        // Get all the kmDetailsList where bojaAmount is greater than or equal to UPDATED_BOJA_AMOUNT
        defaultKmDetailsShouldNotBeFound("bojaAmount.greaterThanOrEqual=" + UPDATED_BOJA_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount is less than or equal to DEFAULT_BOJA_AMOUNT
        defaultKmDetailsShouldBeFound("bojaAmount.lessThanOrEqual=" + DEFAULT_BOJA_AMOUNT);

        // Get all the kmDetailsList where bojaAmount is less than or equal to SMALLER_BOJA_AMOUNT
        defaultKmDetailsShouldNotBeFound("bojaAmount.lessThanOrEqual=" + SMALLER_BOJA_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount is less than DEFAULT_BOJA_AMOUNT
        defaultKmDetailsShouldNotBeFound("bojaAmount.lessThan=" + DEFAULT_BOJA_AMOUNT);

        // Get all the kmDetailsList where bojaAmount is less than UPDATED_BOJA_AMOUNT
        defaultKmDetailsShouldBeFound("bojaAmount.lessThan=" + UPDATED_BOJA_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmount is greater than DEFAULT_BOJA_AMOUNT
        defaultKmDetailsShouldNotBeFound("bojaAmount.greaterThan=" + DEFAULT_BOJA_AMOUNT);

        // Get all the kmDetailsList where bojaAmount is greater than SMALLER_BOJA_AMOUNT
        defaultKmDetailsShouldBeFound("bojaAmount.greaterThan=" + SMALLER_BOJA_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmountMr equals to DEFAULT_BOJA_AMOUNT_MR
        defaultKmDetailsShouldBeFound("bojaAmountMr.equals=" + DEFAULT_BOJA_AMOUNT_MR);

        // Get all the kmDetailsList where bojaAmountMr equals to UPDATED_BOJA_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("bojaAmountMr.equals=" + UPDATED_BOJA_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmountMr in DEFAULT_BOJA_AMOUNT_MR or UPDATED_BOJA_AMOUNT_MR
        defaultKmDetailsShouldBeFound("bojaAmountMr.in=" + DEFAULT_BOJA_AMOUNT_MR + "," + UPDATED_BOJA_AMOUNT_MR);

        // Get all the kmDetailsList where bojaAmountMr equals to UPDATED_BOJA_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("bojaAmountMr.in=" + UPDATED_BOJA_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmountMr is not null
        defaultKmDetailsShouldBeFound("bojaAmountMr.specified=true");

        // Get all the kmDetailsList where bojaAmountMr is null
        defaultKmDetailsShouldNotBeFound("bojaAmountMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmountMr contains DEFAULT_BOJA_AMOUNT_MR
        defaultKmDetailsShouldBeFound("bojaAmountMr.contains=" + DEFAULT_BOJA_AMOUNT_MR);

        // Get all the kmDetailsList where bojaAmountMr contains UPDATED_BOJA_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("bojaAmountMr.contains=" + UPDATED_BOJA_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaAmountMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaAmountMr does not contain DEFAULT_BOJA_AMOUNT_MR
        defaultKmDetailsShouldNotBeFound("bojaAmountMr.doesNotContain=" + DEFAULT_BOJA_AMOUNT_MR);

        // Get all the kmDetailsList where bojaAmountMr does not contain UPDATED_BOJA_AMOUNT_MR
        defaultKmDetailsShouldBeFound("bojaAmountMr.doesNotContain=" + UPDATED_BOJA_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDate equals to DEFAULT_BOJA_DATE
        defaultKmDetailsShouldBeFound("bojaDate.equals=" + DEFAULT_BOJA_DATE);

        // Get all the kmDetailsList where bojaDate equals to UPDATED_BOJA_DATE
        defaultKmDetailsShouldNotBeFound("bojaDate.equals=" + UPDATED_BOJA_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDate in DEFAULT_BOJA_DATE or UPDATED_BOJA_DATE
        defaultKmDetailsShouldBeFound("bojaDate.in=" + DEFAULT_BOJA_DATE + "," + UPDATED_BOJA_DATE);

        // Get all the kmDetailsList where bojaDate equals to UPDATED_BOJA_DATE
        defaultKmDetailsShouldNotBeFound("bojaDate.in=" + UPDATED_BOJA_DATE);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDate is not null
        defaultKmDetailsShouldBeFound("bojaDate.specified=true");

        // Get all the kmDetailsList where bojaDate is null
        defaultKmDetailsShouldNotBeFound("bojaDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDateMr equals to DEFAULT_BOJA_DATE_MR
        defaultKmDetailsShouldBeFound("bojaDateMr.equals=" + DEFAULT_BOJA_DATE_MR);

        // Get all the kmDetailsList where bojaDateMr equals to UPDATED_BOJA_DATE_MR
        defaultKmDetailsShouldNotBeFound("bojaDateMr.equals=" + UPDATED_BOJA_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDateMr in DEFAULT_BOJA_DATE_MR or UPDATED_BOJA_DATE_MR
        defaultKmDetailsShouldBeFound("bojaDateMr.in=" + DEFAULT_BOJA_DATE_MR + "," + UPDATED_BOJA_DATE_MR);

        // Get all the kmDetailsList where bojaDateMr equals to UPDATED_BOJA_DATE_MR
        defaultKmDetailsShouldNotBeFound("bojaDateMr.in=" + UPDATED_BOJA_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDateMr is not null
        defaultKmDetailsShouldBeFound("bojaDateMr.specified=true");

        // Get all the kmDetailsList where bojaDateMr is null
        defaultKmDetailsShouldNotBeFound("bojaDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaDateMrContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDateMr contains DEFAULT_BOJA_DATE_MR
        defaultKmDetailsShouldBeFound("bojaDateMr.contains=" + DEFAULT_BOJA_DATE_MR);

        // Get all the kmDetailsList where bojaDateMr contains UPDATED_BOJA_DATE_MR
        defaultKmDetailsShouldNotBeFound("bojaDateMr.contains=" + UPDATED_BOJA_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByBojaDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        // Get all the kmDetailsList where bojaDateMr does not contain DEFAULT_BOJA_DATE_MR
        defaultKmDetailsShouldNotBeFound("bojaDateMr.doesNotContain=" + DEFAULT_BOJA_DATE_MR);

        // Get all the kmDetailsList where bojaDateMr does not contain UPDATED_BOJA_DATE_MR
        defaultKmDetailsShouldBeFound("bojaDateMr.doesNotContain=" + UPDATED_BOJA_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKmDetailsByKmMasterIsEqualToSomething() throws Exception {
        KmMaster kmMaster;
        if (TestUtil.findAll(em, KmMaster.class).isEmpty()) {
            kmDetailsRepository.saveAndFlush(kmDetails);
            kmMaster = KmMasterResourceIT.createEntity(em);
        } else {
            kmMaster = TestUtil.findAll(em, KmMaster.class).get(0);
        }
        em.persist(kmMaster);
        em.flush();
        kmDetails.setKmMaster(kmMaster);
        kmDetailsRepository.saveAndFlush(kmDetails);
        Long kmMasterId = kmMaster.getId();
        // Get all the kmDetailsList where kmMaster equals to kmMasterId
        defaultKmDetailsShouldBeFound("kmMasterId.equals=" + kmMasterId);

        // Get all the kmDetailsList where kmMaster equals to (kmMasterId + 1)
        defaultKmDetailsShouldNotBeFound("kmMasterId.equals=" + (kmMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKmDetailsShouldBeFound(String filter) throws Exception {
        restKmDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].shares").value(hasItem(DEFAULT_SHARES.doubleValue())))
            .andExpect(jsonPath("$.[*].sharesMr").value(hasItem(DEFAULT_SHARES_MR)))
            .andExpect(jsonPath("$.[*].sugarShares").value(hasItem(DEFAULT_SUGAR_SHARES.doubleValue())))
            .andExpect(jsonPath("$.[*].sugarSharesMr").value(hasItem(DEFAULT_SUGAR_SHARES_MR)))
            .andExpect(jsonPath("$.[*].deposite").value(hasItem(DEFAULT_DEPOSITE.doubleValue())))
            .andExpect(jsonPath("$.[*].depositeMr").value(hasItem(DEFAULT_DEPOSITE_MR)))
            .andExpect(jsonPath("$.[*].dueLoan").value(hasItem(DEFAULT_DUE_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].dueLoanMr").value(hasItem(DEFAULT_DUE_LOAN_MR)))
            .andExpect(jsonPath("$.[*].dueAmount").value(hasItem(DEFAULT_DUE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmountMr").value(hasItem(DEFAULT_DUE_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].dueDateMr").value(hasItem(DEFAULT_DUE_DATE_MR)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDateMr").value(hasItem(DEFAULT_KM_DATE_MR)))
            .andExpect(jsonPath("$.[*].kmFromDate").value(hasItem(DEFAULT_KM_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmFromDateMr").value(hasItem(DEFAULT_KM_FROM_DATE_MR)))
            .andExpect(jsonPath("$.[*].kmToDate").value(hasItem(DEFAULT_KM_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmToDateMr").value(hasItem(DEFAULT_KM_TO_DATE_MR)))
            .andExpect(jsonPath("$.[*].bagayatHector").value(hasItem(DEFAULT_BAGAYAT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].bagayatHectorMr").value(hasItem(DEFAULT_BAGAYAT_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].bagayatAre").value(hasItem(DEFAULT_BAGAYAT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].bagayatAreMr").value(hasItem(DEFAULT_BAGAYAT_ARE_MR)))
            .andExpect(jsonPath("$.[*].jirayatHector").value(hasItem(DEFAULT_JIRAYAT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].jirayatHectorMr").value(hasItem(DEFAULT_JIRAYAT_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].jirayatAre").value(hasItem(DEFAULT_JIRAYAT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].jirayatAreMr").value(hasItem(DEFAULT_JIRAYAT_ARE_MR)))
            .andExpect(jsonPath("$.[*].zindagiAmt").value(hasItem(DEFAULT_ZINDAGI_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].zindagiNo").value(hasItem(DEFAULT_ZINDAGI_NO.intValue())))
            .andExpect(jsonPath("$.[*].surveyNo").value(hasItem(DEFAULT_SURVEY_NO.intValue())))
            .andExpect(jsonPath("$.[*].landValue").value(hasItem(DEFAULT_LAND_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].landValueMr").value(hasItem(DEFAULT_LAND_VALUE_MR)))
            .andExpect(jsonPath("$.[*].eAgreementAmt").value(hasItem(DEFAULT_E_AGREEMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].eAgreementAmtMr").value(hasItem(DEFAULT_E_AGREEMENT_AMT_MR)))
            .andExpect(jsonPath("$.[*].eAgreementDate").value(hasItem(DEFAULT_E_AGREEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].eAgreementDateMr").value(hasItem(DEFAULT_E_AGREEMENT_DATE_MR)))
            .andExpect(jsonPath("$.[*].bojaAmount").value(hasItem(DEFAULT_BOJA_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].bojaAmountMr").value(hasItem(DEFAULT_BOJA_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].bojaDate").value(hasItem(DEFAULT_BOJA_DATE.toString())))
            .andExpect(jsonPath("$.[*].bojaDateMr").value(hasItem(DEFAULT_BOJA_DATE_MR)));

        // Check, that the count call also returns 1
        restKmDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKmDetailsShouldNotBeFound(String filter) throws Exception {
        restKmDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKmDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKmDetails() throws Exception {
        // Get the kmDetails
        restKmDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKmDetails() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();

        // Update the kmDetails
        KmDetails updatedKmDetails = kmDetailsRepository.findById(kmDetails.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKmDetails are not directly saved in db
        em.detach(updatedKmDetails);
        updatedKmDetails
            .shares(UPDATED_SHARES)
            .sharesMr(UPDATED_SHARES_MR)
            .sugarShares(UPDATED_SUGAR_SHARES)
            .sugarSharesMr(UPDATED_SUGAR_SHARES_MR)
            .deposite(UPDATED_DEPOSITE)
            .depositeMr(UPDATED_DEPOSITE_MR)
            .dueLoan(UPDATED_DUE_LOAN)
            .dueLoanMr(UPDATED_DUE_LOAN_MR)
            .dueAmount(UPDATED_DUE_AMOUNT)
            .dueAmountMr(UPDATED_DUE_AMOUNT_MR)
            .dueDateMr(UPDATED_DUE_DATE_MR)
            .dueDate(UPDATED_DUE_DATE)
            .kmDate(UPDATED_KM_DATE)
            .kmDateMr(UPDATED_KM_DATE_MR)
            .kmFromDate(UPDATED_KM_FROM_DATE)
            .kmFromDateMr(UPDATED_KM_FROM_DATE_MR)
            .kmToDate(UPDATED_KM_TO_DATE)
            .kmToDateMr(UPDATED_KM_TO_DATE_MR)
            .bagayatHector(UPDATED_BAGAYAT_HECTOR)
            .bagayatHectorMr(UPDATED_BAGAYAT_HECTOR_MR)
            .bagayatAre(UPDATED_BAGAYAT_ARE)
            .bagayatAreMr(UPDATED_BAGAYAT_ARE_MR)
            .jirayatHector(UPDATED_JIRAYAT_HECTOR)
            .jirayatHectorMr(UPDATED_JIRAYAT_HECTOR_MR)
            .jirayatAre(UPDATED_JIRAYAT_ARE)
            .jirayatAreMr(UPDATED_JIRAYAT_ARE_MR)
            .zindagiAmt(UPDATED_ZINDAGI_AMT)
            .zindagiNo(UPDATED_ZINDAGI_NO)
            .surveyNo(UPDATED_SURVEY_NO)
            .landValue(UPDATED_LAND_VALUE)
            .landValueMr(UPDATED_LAND_VALUE_MR)
            .eAgreementAmt(UPDATED_E_AGREEMENT_AMT)
            .eAgreementAmtMr(UPDATED_E_AGREEMENT_AMT_MR)
            .eAgreementDate(UPDATED_E_AGREEMENT_DATE)
            .eAgreementDateMr(UPDATED_E_AGREEMENT_DATE_MR)
            .bojaAmount(UPDATED_BOJA_AMOUNT)
            .bojaAmountMr(UPDATED_BOJA_AMOUNT_MR)
            .bojaDate(UPDATED_BOJA_DATE)
            .bojaDateMr(UPDATED_BOJA_DATE_MR);

        restKmDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKmDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKmDetails))
            )
            .andExpect(status().isOk());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
        KmDetails testKmDetails = kmDetailsList.get(kmDetailsList.size() - 1);
        assertThat(testKmDetails.getShares()).isEqualTo(UPDATED_SHARES);
        assertThat(testKmDetails.getSharesMr()).isEqualTo(UPDATED_SHARES_MR);
        assertThat(testKmDetails.getSugarShares()).isEqualTo(UPDATED_SUGAR_SHARES);
        assertThat(testKmDetails.getSugarSharesMr()).isEqualTo(UPDATED_SUGAR_SHARES_MR);
        assertThat(testKmDetails.getDeposite()).isEqualTo(UPDATED_DEPOSITE);
        assertThat(testKmDetails.getDepositeMr()).isEqualTo(UPDATED_DEPOSITE_MR);
        assertThat(testKmDetails.getDueLoan()).isEqualTo(UPDATED_DUE_LOAN);
        assertThat(testKmDetails.getDueLoanMr()).isEqualTo(UPDATED_DUE_LOAN_MR);
        assertThat(testKmDetails.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
        assertThat(testKmDetails.getDueAmountMr()).isEqualTo(UPDATED_DUE_AMOUNT_MR);
        assertThat(testKmDetails.getDueDateMr()).isEqualTo(UPDATED_DUE_DATE_MR);
        assertThat(testKmDetails.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testKmDetails.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKmDetails.getKmDateMr()).isEqualTo(UPDATED_KM_DATE_MR);
        assertThat(testKmDetails.getKmFromDate()).isEqualTo(UPDATED_KM_FROM_DATE);
        assertThat(testKmDetails.getKmFromDateMr()).isEqualTo(UPDATED_KM_FROM_DATE_MR);
        assertThat(testKmDetails.getKmToDate()).isEqualTo(UPDATED_KM_TO_DATE);
        assertThat(testKmDetails.getKmToDateMr()).isEqualTo(UPDATED_KM_TO_DATE_MR);
        assertThat(testKmDetails.getBagayatHector()).isEqualTo(UPDATED_BAGAYAT_HECTOR);
        assertThat(testKmDetails.getBagayatHectorMr()).isEqualTo(UPDATED_BAGAYAT_HECTOR_MR);
        assertThat(testKmDetails.getBagayatAre()).isEqualTo(UPDATED_BAGAYAT_ARE);
        assertThat(testKmDetails.getBagayatAreMr()).isEqualTo(UPDATED_BAGAYAT_ARE_MR);
        assertThat(testKmDetails.getJirayatHector()).isEqualTo(UPDATED_JIRAYAT_HECTOR);
        assertThat(testKmDetails.getJirayatHectorMr()).isEqualTo(UPDATED_JIRAYAT_HECTOR_MR);
        assertThat(testKmDetails.getJirayatAre()).isEqualTo(UPDATED_JIRAYAT_ARE);
        assertThat(testKmDetails.getJirayatAreMr()).isEqualTo(UPDATED_JIRAYAT_ARE_MR);
        assertThat(testKmDetails.getZindagiAmt()).isEqualTo(UPDATED_ZINDAGI_AMT);
        assertThat(testKmDetails.getZindagiNo()).isEqualTo(UPDATED_ZINDAGI_NO);
        assertThat(testKmDetails.getSurveyNo()).isEqualTo(UPDATED_SURVEY_NO);
        assertThat(testKmDetails.getLandValue()).isEqualTo(UPDATED_LAND_VALUE);
        assertThat(testKmDetails.getLandValueMr()).isEqualTo(UPDATED_LAND_VALUE_MR);
        assertThat(testKmDetails.geteAgreementAmt()).isEqualTo(UPDATED_E_AGREEMENT_AMT);
        assertThat(testKmDetails.geteAgreementAmtMr()).isEqualTo(UPDATED_E_AGREEMENT_AMT_MR);
        assertThat(testKmDetails.geteAgreementDate()).isEqualTo(UPDATED_E_AGREEMENT_DATE);
        assertThat(testKmDetails.geteAgreementDateMr()).isEqualTo(UPDATED_E_AGREEMENT_DATE_MR);
        assertThat(testKmDetails.getBojaAmount()).isEqualTo(UPDATED_BOJA_AMOUNT);
        assertThat(testKmDetails.getBojaAmountMr()).isEqualTo(UPDATED_BOJA_AMOUNT_MR);
        assertThat(testKmDetails.getBojaDate()).isEqualTo(UPDATED_BOJA_DATE);
        assertThat(testKmDetails.getBojaDateMr()).isEqualTo(UPDATED_BOJA_DATE_MR);
    }

    @Test
    @Transactional
    void putNonExistingKmDetails() throws Exception {
        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();
        kmDetails.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kmDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKmDetails() throws Exception {
        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();
        kmDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKmDetails() throws Exception {
        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();
        kmDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKmDetailsWithPatch() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();

        // Update the kmDetails using partial update
        KmDetails partialUpdatedKmDetails = new KmDetails();
        partialUpdatedKmDetails.setId(kmDetails.getId());

        partialUpdatedKmDetails
            .sugarShares(UPDATED_SUGAR_SHARES)
            .sugarSharesMr(UPDATED_SUGAR_SHARES_MR)
            .depositeMr(UPDATED_DEPOSITE_MR)
            .dueDate(UPDATED_DUE_DATE)
            .kmToDateMr(UPDATED_KM_TO_DATE_MR)
            .bagayatAre(UPDATED_BAGAYAT_ARE)
            .jirayatAreMr(UPDATED_JIRAYAT_ARE_MR)
            .landValue(UPDATED_LAND_VALUE)
            .landValueMr(UPDATED_LAND_VALUE_MR)
            .eAgreementAmt(UPDATED_E_AGREEMENT_AMT)
            .eAgreementAmtMr(UPDATED_E_AGREEMENT_AMT_MR)
            .eAgreementDateMr(UPDATED_E_AGREEMENT_DATE_MR);

        restKmDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmDetails))
            )
            .andExpect(status().isOk());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
        KmDetails testKmDetails = kmDetailsList.get(kmDetailsList.size() - 1);
        assertThat(testKmDetails.getShares()).isEqualTo(DEFAULT_SHARES);
        assertThat(testKmDetails.getSharesMr()).isEqualTo(DEFAULT_SHARES_MR);
        assertThat(testKmDetails.getSugarShares()).isEqualTo(UPDATED_SUGAR_SHARES);
        assertThat(testKmDetails.getSugarSharesMr()).isEqualTo(UPDATED_SUGAR_SHARES_MR);
        assertThat(testKmDetails.getDeposite()).isEqualTo(DEFAULT_DEPOSITE);
        assertThat(testKmDetails.getDepositeMr()).isEqualTo(UPDATED_DEPOSITE_MR);
        assertThat(testKmDetails.getDueLoan()).isEqualTo(DEFAULT_DUE_LOAN);
        assertThat(testKmDetails.getDueLoanMr()).isEqualTo(DEFAULT_DUE_LOAN_MR);
        assertThat(testKmDetails.getDueAmount()).isEqualTo(DEFAULT_DUE_AMOUNT);
        assertThat(testKmDetails.getDueAmountMr()).isEqualTo(DEFAULT_DUE_AMOUNT_MR);
        assertThat(testKmDetails.getDueDateMr()).isEqualTo(DEFAULT_DUE_DATE_MR);
        assertThat(testKmDetails.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testKmDetails.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
        assertThat(testKmDetails.getKmDateMr()).isEqualTo(DEFAULT_KM_DATE_MR);
        assertThat(testKmDetails.getKmFromDate()).isEqualTo(DEFAULT_KM_FROM_DATE);
        assertThat(testKmDetails.getKmFromDateMr()).isEqualTo(DEFAULT_KM_FROM_DATE_MR);
        assertThat(testKmDetails.getKmToDate()).isEqualTo(DEFAULT_KM_TO_DATE);
        assertThat(testKmDetails.getKmToDateMr()).isEqualTo(UPDATED_KM_TO_DATE_MR);
        assertThat(testKmDetails.getBagayatHector()).isEqualTo(DEFAULT_BAGAYAT_HECTOR);
        assertThat(testKmDetails.getBagayatHectorMr()).isEqualTo(DEFAULT_BAGAYAT_HECTOR_MR);
        assertThat(testKmDetails.getBagayatAre()).isEqualTo(UPDATED_BAGAYAT_ARE);
        assertThat(testKmDetails.getBagayatAreMr()).isEqualTo(DEFAULT_BAGAYAT_ARE_MR);
        assertThat(testKmDetails.getJirayatHector()).isEqualTo(DEFAULT_JIRAYAT_HECTOR);
        assertThat(testKmDetails.getJirayatHectorMr()).isEqualTo(DEFAULT_JIRAYAT_HECTOR_MR);
        assertThat(testKmDetails.getJirayatAre()).isEqualTo(DEFAULT_JIRAYAT_ARE);
        assertThat(testKmDetails.getJirayatAreMr()).isEqualTo(UPDATED_JIRAYAT_ARE_MR);
        assertThat(testKmDetails.getZindagiAmt()).isEqualTo(DEFAULT_ZINDAGI_AMT);
        assertThat(testKmDetails.getZindagiNo()).isEqualTo(DEFAULT_ZINDAGI_NO);
        assertThat(testKmDetails.getSurveyNo()).isEqualTo(DEFAULT_SURVEY_NO);
        assertThat(testKmDetails.getLandValue()).isEqualTo(UPDATED_LAND_VALUE);
        assertThat(testKmDetails.getLandValueMr()).isEqualTo(UPDATED_LAND_VALUE_MR);
        assertThat(testKmDetails.geteAgreementAmt()).isEqualTo(UPDATED_E_AGREEMENT_AMT);
        assertThat(testKmDetails.geteAgreementAmtMr()).isEqualTo(UPDATED_E_AGREEMENT_AMT_MR);
        assertThat(testKmDetails.geteAgreementDate()).isEqualTo(DEFAULT_E_AGREEMENT_DATE);
        assertThat(testKmDetails.geteAgreementDateMr()).isEqualTo(UPDATED_E_AGREEMENT_DATE_MR);
        assertThat(testKmDetails.getBojaAmount()).isEqualTo(DEFAULT_BOJA_AMOUNT);
        assertThat(testKmDetails.getBojaAmountMr()).isEqualTo(DEFAULT_BOJA_AMOUNT_MR);
        assertThat(testKmDetails.getBojaDate()).isEqualTo(DEFAULT_BOJA_DATE);
        assertThat(testKmDetails.getBojaDateMr()).isEqualTo(DEFAULT_BOJA_DATE_MR);
    }

    @Test
    @Transactional
    void fullUpdateKmDetailsWithPatch() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();

        // Update the kmDetails using partial update
        KmDetails partialUpdatedKmDetails = new KmDetails();
        partialUpdatedKmDetails.setId(kmDetails.getId());

        partialUpdatedKmDetails
            .shares(UPDATED_SHARES)
            .sharesMr(UPDATED_SHARES_MR)
            .sugarShares(UPDATED_SUGAR_SHARES)
            .sugarSharesMr(UPDATED_SUGAR_SHARES_MR)
            .deposite(UPDATED_DEPOSITE)
            .depositeMr(UPDATED_DEPOSITE_MR)
            .dueLoan(UPDATED_DUE_LOAN)
            .dueLoanMr(UPDATED_DUE_LOAN_MR)
            .dueAmount(UPDATED_DUE_AMOUNT)
            .dueAmountMr(UPDATED_DUE_AMOUNT_MR)
            .dueDateMr(UPDATED_DUE_DATE_MR)
            .dueDate(UPDATED_DUE_DATE)
            .kmDate(UPDATED_KM_DATE)
            .kmDateMr(UPDATED_KM_DATE_MR)
            .kmFromDate(UPDATED_KM_FROM_DATE)
            .kmFromDateMr(UPDATED_KM_FROM_DATE_MR)
            .kmToDate(UPDATED_KM_TO_DATE)
            .kmToDateMr(UPDATED_KM_TO_DATE_MR)
            .bagayatHector(UPDATED_BAGAYAT_HECTOR)
            .bagayatHectorMr(UPDATED_BAGAYAT_HECTOR_MR)
            .bagayatAre(UPDATED_BAGAYAT_ARE)
            .bagayatAreMr(UPDATED_BAGAYAT_ARE_MR)
            .jirayatHector(UPDATED_JIRAYAT_HECTOR)
            .jirayatHectorMr(UPDATED_JIRAYAT_HECTOR_MR)
            .jirayatAre(UPDATED_JIRAYAT_ARE)
            .jirayatAreMr(UPDATED_JIRAYAT_ARE_MR)
            .zindagiAmt(UPDATED_ZINDAGI_AMT)
            .zindagiNo(UPDATED_ZINDAGI_NO)
            .surveyNo(UPDATED_SURVEY_NO)
            .landValue(UPDATED_LAND_VALUE)
            .landValueMr(UPDATED_LAND_VALUE_MR)
            .eAgreementAmt(UPDATED_E_AGREEMENT_AMT)
            .eAgreementAmtMr(UPDATED_E_AGREEMENT_AMT_MR)
            .eAgreementDate(UPDATED_E_AGREEMENT_DATE)
            .eAgreementDateMr(UPDATED_E_AGREEMENT_DATE_MR)
            .bojaAmount(UPDATED_BOJA_AMOUNT)
            .bojaAmountMr(UPDATED_BOJA_AMOUNT_MR)
            .bojaDate(UPDATED_BOJA_DATE)
            .bojaDateMr(UPDATED_BOJA_DATE_MR);

        restKmDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmDetails))
            )
            .andExpect(status().isOk());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
        KmDetails testKmDetails = kmDetailsList.get(kmDetailsList.size() - 1);
        assertThat(testKmDetails.getShares()).isEqualTo(UPDATED_SHARES);
        assertThat(testKmDetails.getSharesMr()).isEqualTo(UPDATED_SHARES_MR);
        assertThat(testKmDetails.getSugarShares()).isEqualTo(UPDATED_SUGAR_SHARES);
        assertThat(testKmDetails.getSugarSharesMr()).isEqualTo(UPDATED_SUGAR_SHARES_MR);
        assertThat(testKmDetails.getDeposite()).isEqualTo(UPDATED_DEPOSITE);
        assertThat(testKmDetails.getDepositeMr()).isEqualTo(UPDATED_DEPOSITE_MR);
        assertThat(testKmDetails.getDueLoan()).isEqualTo(UPDATED_DUE_LOAN);
        assertThat(testKmDetails.getDueLoanMr()).isEqualTo(UPDATED_DUE_LOAN_MR);
        assertThat(testKmDetails.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
        assertThat(testKmDetails.getDueAmountMr()).isEqualTo(UPDATED_DUE_AMOUNT_MR);
        assertThat(testKmDetails.getDueDateMr()).isEqualTo(UPDATED_DUE_DATE_MR);
        assertThat(testKmDetails.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testKmDetails.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKmDetails.getKmDateMr()).isEqualTo(UPDATED_KM_DATE_MR);
        assertThat(testKmDetails.getKmFromDate()).isEqualTo(UPDATED_KM_FROM_DATE);
        assertThat(testKmDetails.getKmFromDateMr()).isEqualTo(UPDATED_KM_FROM_DATE_MR);
        assertThat(testKmDetails.getKmToDate()).isEqualTo(UPDATED_KM_TO_DATE);
        assertThat(testKmDetails.getKmToDateMr()).isEqualTo(UPDATED_KM_TO_DATE_MR);
        assertThat(testKmDetails.getBagayatHector()).isEqualTo(UPDATED_BAGAYAT_HECTOR);
        assertThat(testKmDetails.getBagayatHectorMr()).isEqualTo(UPDATED_BAGAYAT_HECTOR_MR);
        assertThat(testKmDetails.getBagayatAre()).isEqualTo(UPDATED_BAGAYAT_ARE);
        assertThat(testKmDetails.getBagayatAreMr()).isEqualTo(UPDATED_BAGAYAT_ARE_MR);
        assertThat(testKmDetails.getJirayatHector()).isEqualTo(UPDATED_JIRAYAT_HECTOR);
        assertThat(testKmDetails.getJirayatHectorMr()).isEqualTo(UPDATED_JIRAYAT_HECTOR_MR);
        assertThat(testKmDetails.getJirayatAre()).isEqualTo(UPDATED_JIRAYAT_ARE);
        assertThat(testKmDetails.getJirayatAreMr()).isEqualTo(UPDATED_JIRAYAT_ARE_MR);
        assertThat(testKmDetails.getZindagiAmt()).isEqualTo(UPDATED_ZINDAGI_AMT);
        assertThat(testKmDetails.getZindagiNo()).isEqualTo(UPDATED_ZINDAGI_NO);
        assertThat(testKmDetails.getSurveyNo()).isEqualTo(UPDATED_SURVEY_NO);
        assertThat(testKmDetails.getLandValue()).isEqualTo(UPDATED_LAND_VALUE);
        assertThat(testKmDetails.getLandValueMr()).isEqualTo(UPDATED_LAND_VALUE_MR);
        assertThat(testKmDetails.geteAgreementAmt()).isEqualTo(UPDATED_E_AGREEMENT_AMT);
        assertThat(testKmDetails.geteAgreementAmtMr()).isEqualTo(UPDATED_E_AGREEMENT_AMT_MR);
        assertThat(testKmDetails.geteAgreementDate()).isEqualTo(UPDATED_E_AGREEMENT_DATE);
        assertThat(testKmDetails.geteAgreementDateMr()).isEqualTo(UPDATED_E_AGREEMENT_DATE_MR);
        assertThat(testKmDetails.getBojaAmount()).isEqualTo(UPDATED_BOJA_AMOUNT);
        assertThat(testKmDetails.getBojaAmountMr()).isEqualTo(UPDATED_BOJA_AMOUNT_MR);
        assertThat(testKmDetails.getBojaDate()).isEqualTo(UPDATED_BOJA_DATE);
        assertThat(testKmDetails.getBojaDateMr()).isEqualTo(UPDATED_BOJA_DATE_MR);
    }

    @Test
    @Transactional
    void patchNonExistingKmDetails() throws Exception {
        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();
        kmDetails.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kmDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKmDetails() throws Exception {
        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();
        kmDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKmDetails() throws Exception {
        int databaseSizeBeforeUpdate = kmDetailsRepository.findAll().size();
        kmDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kmDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmDetails in the database
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKmDetails() throws Exception {
        // Initialize the database
        kmDetailsRepository.saveAndFlush(kmDetails);

        int databaseSizeBeforeDelete = kmDetailsRepository.findAll().size();

        // Delete the kmDetails
        restKmDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, kmDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KmDetails> kmDetailsList = kmDetailsRepository.findAll();
        assertThat(kmDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
