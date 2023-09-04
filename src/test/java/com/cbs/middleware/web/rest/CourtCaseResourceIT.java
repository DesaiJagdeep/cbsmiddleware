package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.service.criteria.CourtCaseCriteria;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CourtCaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourtCaseResourceIT {

    private static final String DEFAULT_SR_NO = "AAAAAAAAAA";
    private static final String UPDATED_SR_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_OF_DEFAULTER = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_DEFAULTER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_LOAN_AMOUNT = 1D;
    private static final Double UPDATED_LOAN_AMOUNT = 2D;
    private static final Double SMALLER_LOAN_AMOUNT = 1D - 1D;

    private static final LocalDate DEFAULT_LOAN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LOAN_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LOAN_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TERM_OF_LOAN = "AAAAAAAAAA";
    private static final String UPDATED_TERM_OF_LOAN = "BBBBBBBBBB";

    private static final Double DEFAULT_INTEREST_RATE = 1D;
    private static final Double UPDATED_INTEREST_RATE = 2D;
    private static final Double SMALLER_INTEREST_RATE = 1D - 1D;

    private static final Double DEFAULT_INSTALLMENT_AMOUNT = 1D;
    private static final Double UPDATED_INSTALLMENT_AMOUNT = 2D;
    private static final Double SMALLER_INSTALLMENT_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_TOTAL_CREDIT = 1D;
    private static final Double UPDATED_TOTAL_CREDIT = 2D;
    private static final Double SMALLER_TOTAL_CREDIT = 1D - 1D;

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;
    private static final Double SMALLER_BALANCE = 1D - 1D;

    private static final Double DEFAULT_INTEREST_PAID = 1D;
    private static final Double UPDATED_INTEREST_PAID = 2D;
    private static final Double SMALLER_INTEREST_PAID = 1D - 1D;

    private static final Double DEFAULT_PENAL_INTEREST_PAID = 1D;
    private static final Double UPDATED_PENAL_INTEREST_PAID = 2D;
    private static final Double SMALLER_PENAL_INTEREST_PAID = 1D - 1D;

    private static final Double DEFAULT_DUE_AMOUNT = 1D;
    private static final Double UPDATED_DUE_AMOUNT = 2D;
    private static final Double SMALLER_DUE_AMOUNT = 1D - 1D;

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_DUE_INTEREST = 1D;
    private static final Double UPDATED_DUE_INTEREST = 2D;
    private static final Double SMALLER_DUE_INTEREST = 1D - 1D;

    private static final Double DEFAULT_DUE_PENAL_INTEREST = 1D;
    private static final Double UPDATED_DUE_PENAL_INTEREST = 2D;
    private static final Double SMALLER_DUE_PENAL_INTEREST = 1D - 1D;

    private static final Double DEFAULT_DUE_MORE_INTEREST = 1D;
    private static final Double UPDATED_DUE_MORE_INTEREST = 2D;
    private static final Double SMALLER_DUE_MORE_INTEREST = 1D - 1D;

    private static final Double DEFAULT_INTEREST_RECIVABLE = 1D;
    private static final Double UPDATED_INTEREST_RECIVABLE = 2D;
    private static final Double SMALLER_INTEREST_RECIVABLE = 1D - 1D;

    private static final String DEFAULT_GAURENTOR_ONE = "AAAAAAAAAA";
    private static final String UPDATED_GAURENTOR_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_GAURENTOR_ONE_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_GAURENTOR_ONE_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_GAURENTOR_TWO = "AAAAAAAAAA";
    private static final String UPDATED_GAURENTOR_TWO = "BBBBBBBBBB";

    private static final String DEFAULT_GAURENTOR_TWO_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_GAURENTOR_TWO_ADDRESS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FIRST_NOTICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_NOTICE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FIRST_NOTICE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_SECOND_NOTICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SECOND_NOTICE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SECOND_NOTICE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/court-cases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourtCaseRepository courtCaseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourtCaseMockMvc;

    private CourtCase courtCase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCase createEntity(EntityManager em) {
        CourtCase courtCase = new CourtCase()
            .srNo(DEFAULT_SR_NO)
            .accountNo(DEFAULT_ACCOUNT_NO)
            .nameOfDefaulter(DEFAULT_NAME_OF_DEFAULTER)
            .address(DEFAULT_ADDRESS)
            .loanType(DEFAULT_LOAN_TYPE)
            .loanAmount(DEFAULT_LOAN_AMOUNT)
            .loanDate(DEFAULT_LOAN_DATE)
            .termOfLoan(DEFAULT_TERM_OF_LOAN)
            .interestRate(DEFAULT_INTEREST_RATE)
            .installmentAmount(DEFAULT_INSTALLMENT_AMOUNT)
            .totalCredit(DEFAULT_TOTAL_CREDIT)
            .balance(DEFAULT_BALANCE)
            .interestPaid(DEFAULT_INTEREST_PAID)
            .penalInterestPaid(DEFAULT_PENAL_INTEREST_PAID)
            .dueAmount(DEFAULT_DUE_AMOUNT)
            .dueDate(DEFAULT_DUE_DATE)
            .dueInterest(DEFAULT_DUE_INTEREST)
            .duePenalInterest(DEFAULT_DUE_PENAL_INTEREST)
            .dueMoreInterest(DEFAULT_DUE_MORE_INTEREST)
            .interestRecivable(DEFAULT_INTEREST_RECIVABLE)
            .gaurentorOne(DEFAULT_GAURENTOR_ONE)
            .gaurentorOneAddress(DEFAULT_GAURENTOR_ONE_ADDRESS)
            .gaurentorTwo(DEFAULT_GAURENTOR_TWO)
            .gaurentorTwoAddress(DEFAULT_GAURENTOR_TWO_ADDRESS)
            .firstNoticeDate(DEFAULT_FIRST_NOTICE_DATE)
            .secondNoticeDate(DEFAULT_SECOND_NOTICE_DATE);
        return courtCase;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCase createUpdatedEntity(EntityManager em) {
        CourtCase courtCase = new CourtCase()
            .srNo(UPDATED_SR_NO)
            .accountNo(UPDATED_ACCOUNT_NO)
            .nameOfDefaulter(UPDATED_NAME_OF_DEFAULTER)
            .address(UPDATED_ADDRESS)
            .loanType(UPDATED_LOAN_TYPE)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanDate(UPDATED_LOAN_DATE)
            .termOfLoan(UPDATED_TERM_OF_LOAN)
            .interestRate(UPDATED_INTEREST_RATE)
            .installmentAmount(UPDATED_INSTALLMENT_AMOUNT)
            .totalCredit(UPDATED_TOTAL_CREDIT)
            .balance(UPDATED_BALANCE)
            .interestPaid(UPDATED_INTEREST_PAID)
            .penalInterestPaid(UPDATED_PENAL_INTEREST_PAID)
            .dueAmount(UPDATED_DUE_AMOUNT)
            .dueDate(UPDATED_DUE_DATE)
            .dueInterest(UPDATED_DUE_INTEREST)
            .duePenalInterest(UPDATED_DUE_PENAL_INTEREST)
            .dueMoreInterest(UPDATED_DUE_MORE_INTEREST)
            .interestRecivable(UPDATED_INTEREST_RECIVABLE)
            .gaurentorOne(UPDATED_GAURENTOR_ONE)
            .gaurentorOneAddress(UPDATED_GAURENTOR_ONE_ADDRESS)
            .gaurentorTwo(UPDATED_GAURENTOR_TWO)
            .gaurentorTwoAddress(UPDATED_GAURENTOR_TWO_ADDRESS)
            .firstNoticeDate(UPDATED_FIRST_NOTICE_DATE)
            .secondNoticeDate(UPDATED_SECOND_NOTICE_DATE);
        return courtCase;
    }

    @BeforeEach
    public void initTest() {
        courtCase = createEntity(em);
    }

    @Test
    @Transactional
    void createCourtCase() throws Exception {
        int databaseSizeBeforeCreate = courtCaseRepository.findAll().size();
        // Create the CourtCase
        restCourtCaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCase)))
            .andExpect(status().isCreated());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeCreate + 1);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getSrNo()).isEqualTo(DEFAULT_SR_NO);
        assertThat(testCourtCase.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testCourtCase.getNameOfDefaulter()).isEqualTo(DEFAULT_NAME_OF_DEFAULTER);
        assertThat(testCourtCase.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCourtCase.getLoanType()).isEqualTo(DEFAULT_LOAN_TYPE);
        assertThat(testCourtCase.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testCourtCase.getLoanDate()).isEqualTo(DEFAULT_LOAN_DATE);
        assertThat(testCourtCase.getTermOfLoan()).isEqualTo(DEFAULT_TERM_OF_LOAN);
        assertThat(testCourtCase.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testCourtCase.getInstallmentAmount()).isEqualTo(DEFAULT_INSTALLMENT_AMOUNT);
        assertThat(testCourtCase.getTotalCredit()).isEqualTo(DEFAULT_TOTAL_CREDIT);
        assertThat(testCourtCase.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testCourtCase.getInterestPaid()).isEqualTo(DEFAULT_INTEREST_PAID);
        assertThat(testCourtCase.getPenalInterestPaid()).isEqualTo(DEFAULT_PENAL_INTEREST_PAID);
        assertThat(testCourtCase.getDueAmount()).isEqualTo(DEFAULT_DUE_AMOUNT);
        assertThat(testCourtCase.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCourtCase.getDueInterest()).isEqualTo(DEFAULT_DUE_INTEREST);
        assertThat(testCourtCase.getDuePenalInterest()).isEqualTo(DEFAULT_DUE_PENAL_INTEREST);
        assertThat(testCourtCase.getDueMoreInterest()).isEqualTo(DEFAULT_DUE_MORE_INTEREST);
        assertThat(testCourtCase.getInterestRecivable()).isEqualTo(DEFAULT_INTEREST_RECIVABLE);
        assertThat(testCourtCase.getGaurentorOne()).isEqualTo(DEFAULT_GAURENTOR_ONE);
        assertThat(testCourtCase.getGaurentorOneAddress()).isEqualTo(DEFAULT_GAURENTOR_ONE_ADDRESS);
        assertThat(testCourtCase.getGaurentorTwo()).isEqualTo(DEFAULT_GAURENTOR_TWO);
        assertThat(testCourtCase.getGaurentorTwoAddress()).isEqualTo(DEFAULT_GAURENTOR_TWO_ADDRESS);
        assertThat(testCourtCase.getFirstNoticeDate()).isEqualTo(DEFAULT_FIRST_NOTICE_DATE);
        assertThat(testCourtCase.getSecondNoticeDate()).isEqualTo(DEFAULT_SECOND_NOTICE_DATE);
    }

    @Test
    @Transactional
    void createCourtCaseWithExistingId() throws Exception {
        // Create the CourtCase with an existing ID
        courtCase.setId(1L);

        int databaseSizeBeforeCreate = courtCaseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourtCaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCase)))
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourtCases() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].nameOfDefaulter").value(hasItem(DEFAULT_NAME_OF_DEFAULTER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].loanType").value(hasItem(DEFAULT_LOAN_TYPE)))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanDate").value(hasItem(DEFAULT_LOAN_DATE.toString())))
            .andExpect(jsonPath("$.[*].termOfLoan").value(hasItem(DEFAULT_TERM_OF_LOAN)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].installmentAmount").value(hasItem(DEFAULT_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCredit").value(hasItem(DEFAULT_TOTAL_CREDIT.doubleValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestPaid").value(hasItem(DEFAULT_INTEREST_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].penalInterestPaid").value(hasItem(DEFAULT_PENAL_INTEREST_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmount").value(hasItem(DEFAULT_DUE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueInterest").value(hasItem(DEFAULT_DUE_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].duePenalInterest").value(hasItem(DEFAULT_DUE_PENAL_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].dueMoreInterest").value(hasItem(DEFAULT_DUE_MORE_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRecivable").value(hasItem(DEFAULT_INTEREST_RECIVABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].gaurentorOne").value(hasItem(DEFAULT_GAURENTOR_ONE)))
            .andExpect(jsonPath("$.[*].gaurentorOneAddress").value(hasItem(DEFAULT_GAURENTOR_ONE_ADDRESS)))
            .andExpect(jsonPath("$.[*].gaurentorTwo").value(hasItem(DEFAULT_GAURENTOR_TWO)))
            .andExpect(jsonPath("$.[*].gaurentorTwoAddress").value(hasItem(DEFAULT_GAURENTOR_TWO_ADDRESS)))
            .andExpect(jsonPath("$.[*].firstNoticeDate").value(hasItem(DEFAULT_FIRST_NOTICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].secondNoticeDate").value(hasItem(DEFAULT_SECOND_NOTICE_DATE.toString())));
    }

    @Test
    @Transactional
    void getCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get the courtCase
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL_ID, courtCase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courtCase.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO))
            .andExpect(jsonPath("$.nameOfDefaulter").value(DEFAULT_NAME_OF_DEFAULTER))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.loanType").value(DEFAULT_LOAN_TYPE))
            .andExpect(jsonPath("$.loanAmount").value(DEFAULT_LOAN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.loanDate").value(DEFAULT_LOAN_DATE.toString()))
            .andExpect(jsonPath("$.termOfLoan").value(DEFAULT_TERM_OF_LOAN))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.installmentAmount").value(DEFAULT_INSTALLMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalCredit").value(DEFAULT_TOTAL_CREDIT.doubleValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.interestPaid").value(DEFAULT_INTEREST_PAID.doubleValue()))
            .andExpect(jsonPath("$.penalInterestPaid").value(DEFAULT_PENAL_INTEREST_PAID.doubleValue()))
            .andExpect(jsonPath("$.dueAmount").value(DEFAULT_DUE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.dueInterest").value(DEFAULT_DUE_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.duePenalInterest").value(DEFAULT_DUE_PENAL_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.dueMoreInterest").value(DEFAULT_DUE_MORE_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.interestRecivable").value(DEFAULT_INTEREST_RECIVABLE.doubleValue()))
            .andExpect(jsonPath("$.gaurentorOne").value(DEFAULT_GAURENTOR_ONE))
            .andExpect(jsonPath("$.gaurentorOneAddress").value(DEFAULT_GAURENTOR_ONE_ADDRESS))
            .andExpect(jsonPath("$.gaurentorTwo").value(DEFAULT_GAURENTOR_TWO))
            .andExpect(jsonPath("$.gaurentorTwoAddress").value(DEFAULT_GAURENTOR_TWO_ADDRESS))
            .andExpect(jsonPath("$.firstNoticeDate").value(DEFAULT_FIRST_NOTICE_DATE.toString()))
            .andExpect(jsonPath("$.secondNoticeDate").value(DEFAULT_SECOND_NOTICE_DATE.toString()));
    }

    @Test
    @Transactional
    void getCourtCasesByIdFiltering() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        Long id = courtCase.getId();

        defaultCourtCaseShouldBeFound("id.equals=" + id);
        defaultCourtCaseShouldNotBeFound("id.notEquals=" + id);

        defaultCourtCaseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourtCaseShouldNotBeFound("id.greaterThan=" + id);

        defaultCourtCaseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourtCaseShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where srNo equals to DEFAULT_SR_NO
        defaultCourtCaseShouldBeFound("srNo.equals=" + DEFAULT_SR_NO);

        // Get all the courtCaseList where srNo equals to UPDATED_SR_NO
        defaultCourtCaseShouldNotBeFound("srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where srNo in DEFAULT_SR_NO or UPDATED_SR_NO
        defaultCourtCaseShouldBeFound("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO);

        // Get all the courtCaseList where srNo equals to UPDATED_SR_NO
        defaultCourtCaseShouldNotBeFound("srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where srNo is not null
        defaultCourtCaseShouldBeFound("srNo.specified=true");

        // Get all the courtCaseList where srNo is null
        defaultCourtCaseShouldNotBeFound("srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySrNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where srNo contains DEFAULT_SR_NO
        defaultCourtCaseShouldBeFound("srNo.contains=" + DEFAULT_SR_NO);

        // Get all the courtCaseList where srNo contains UPDATED_SR_NO
        defaultCourtCaseShouldNotBeFound("srNo.contains=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySrNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where srNo does not contain DEFAULT_SR_NO
        defaultCourtCaseShouldNotBeFound("srNo.doesNotContain=" + DEFAULT_SR_NO);

        // Get all the courtCaseList where srNo does not contain UPDATED_SR_NO
        defaultCourtCaseShouldBeFound("srNo.doesNotContain=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultCourtCaseShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the courtCaseList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultCourtCaseShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultCourtCaseShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the courtCaseList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultCourtCaseShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accountNo is not null
        defaultCourtCaseShouldBeFound("accountNo.specified=true");

        // Get all the courtCaseList where accountNo is null
        defaultCourtCaseShouldNotBeFound("accountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccountNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accountNo contains DEFAULT_ACCOUNT_NO
        defaultCourtCaseShouldBeFound("accountNo.contains=" + DEFAULT_ACCOUNT_NO);

        // Get all the courtCaseList where accountNo contains UPDATED_ACCOUNT_NO
        defaultCourtCaseShouldNotBeFound("accountNo.contains=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccountNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accountNo does not contain DEFAULT_ACCOUNT_NO
        defaultCourtCaseShouldNotBeFound("accountNo.doesNotContain=" + DEFAULT_ACCOUNT_NO);

        // Get all the courtCaseList where accountNo does not contain UPDATED_ACCOUNT_NO
        defaultCourtCaseShouldBeFound("accountNo.doesNotContain=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNameOfDefaulterIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nameOfDefaulter equals to DEFAULT_NAME_OF_DEFAULTER
        defaultCourtCaseShouldBeFound("nameOfDefaulter.equals=" + DEFAULT_NAME_OF_DEFAULTER);

        // Get all the courtCaseList where nameOfDefaulter equals to UPDATED_NAME_OF_DEFAULTER
        defaultCourtCaseShouldNotBeFound("nameOfDefaulter.equals=" + UPDATED_NAME_OF_DEFAULTER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNameOfDefaulterIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nameOfDefaulter in DEFAULT_NAME_OF_DEFAULTER or UPDATED_NAME_OF_DEFAULTER
        defaultCourtCaseShouldBeFound("nameOfDefaulter.in=" + DEFAULT_NAME_OF_DEFAULTER + "," + UPDATED_NAME_OF_DEFAULTER);

        // Get all the courtCaseList where nameOfDefaulter equals to UPDATED_NAME_OF_DEFAULTER
        defaultCourtCaseShouldNotBeFound("nameOfDefaulter.in=" + UPDATED_NAME_OF_DEFAULTER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNameOfDefaulterIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nameOfDefaulter is not null
        defaultCourtCaseShouldBeFound("nameOfDefaulter.specified=true");

        // Get all the courtCaseList where nameOfDefaulter is null
        defaultCourtCaseShouldNotBeFound("nameOfDefaulter.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByNameOfDefaulterContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nameOfDefaulter contains DEFAULT_NAME_OF_DEFAULTER
        defaultCourtCaseShouldBeFound("nameOfDefaulter.contains=" + DEFAULT_NAME_OF_DEFAULTER);

        // Get all the courtCaseList where nameOfDefaulter contains UPDATED_NAME_OF_DEFAULTER
        defaultCourtCaseShouldNotBeFound("nameOfDefaulter.contains=" + UPDATED_NAME_OF_DEFAULTER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNameOfDefaulterNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nameOfDefaulter does not contain DEFAULT_NAME_OF_DEFAULTER
        defaultCourtCaseShouldNotBeFound("nameOfDefaulter.doesNotContain=" + DEFAULT_NAME_OF_DEFAULTER);

        // Get all the courtCaseList where nameOfDefaulter does not contain UPDATED_NAME_OF_DEFAULTER
        defaultCourtCaseShouldBeFound("nameOfDefaulter.doesNotContain=" + UPDATED_NAME_OF_DEFAULTER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address equals to DEFAULT_ADDRESS
        defaultCourtCaseShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the courtCaseList where address equals to UPDATED_ADDRESS
        defaultCourtCaseShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCourtCaseShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the courtCaseList where address equals to UPDATED_ADDRESS
        defaultCourtCaseShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address is not null
        defaultCourtCaseShouldBeFound("address.specified=true");

        // Get all the courtCaseList where address is null
        defaultCourtCaseShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address contains DEFAULT_ADDRESS
        defaultCourtCaseShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the courtCaseList where address contains UPDATED_ADDRESS
        defaultCourtCaseShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address does not contain DEFAULT_ADDRESS
        defaultCourtCaseShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the courtCaseList where address does not contain UPDATED_ADDRESS
        defaultCourtCaseShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanType equals to DEFAULT_LOAN_TYPE
        defaultCourtCaseShouldBeFound("loanType.equals=" + DEFAULT_LOAN_TYPE);

        // Get all the courtCaseList where loanType equals to UPDATED_LOAN_TYPE
        defaultCourtCaseShouldNotBeFound("loanType.equals=" + UPDATED_LOAN_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanTypeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanType in DEFAULT_LOAN_TYPE or UPDATED_LOAN_TYPE
        defaultCourtCaseShouldBeFound("loanType.in=" + DEFAULT_LOAN_TYPE + "," + UPDATED_LOAN_TYPE);

        // Get all the courtCaseList where loanType equals to UPDATED_LOAN_TYPE
        defaultCourtCaseShouldNotBeFound("loanType.in=" + UPDATED_LOAN_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanType is not null
        defaultCourtCaseShouldBeFound("loanType.specified=true");

        // Get all the courtCaseList where loanType is null
        defaultCourtCaseShouldNotBeFound("loanType.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanTypeContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanType contains DEFAULT_LOAN_TYPE
        defaultCourtCaseShouldBeFound("loanType.contains=" + DEFAULT_LOAN_TYPE);

        // Get all the courtCaseList where loanType contains UPDATED_LOAN_TYPE
        defaultCourtCaseShouldNotBeFound("loanType.contains=" + UPDATED_LOAN_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanTypeNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanType does not contain DEFAULT_LOAN_TYPE
        defaultCourtCaseShouldNotBeFound("loanType.doesNotContain=" + DEFAULT_LOAN_TYPE);

        // Get all the courtCaseList where loanType does not contain UPDATED_LOAN_TYPE
        defaultCourtCaseShouldBeFound("loanType.doesNotContain=" + UPDATED_LOAN_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanAmount equals to DEFAULT_LOAN_AMOUNT
        defaultCourtCaseShouldBeFound("loanAmount.equals=" + DEFAULT_LOAN_AMOUNT);

        // Get all the courtCaseList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultCourtCaseShouldNotBeFound("loanAmount.equals=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanAmountIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanAmount in DEFAULT_LOAN_AMOUNT or UPDATED_LOAN_AMOUNT
        defaultCourtCaseShouldBeFound("loanAmount.in=" + DEFAULT_LOAN_AMOUNT + "," + UPDATED_LOAN_AMOUNT);

        // Get all the courtCaseList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultCourtCaseShouldNotBeFound("loanAmount.in=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanAmount is not null
        defaultCourtCaseShouldBeFound("loanAmount.specified=true");

        // Get all the courtCaseList where loanAmount is null
        defaultCourtCaseShouldNotBeFound("loanAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanAmount is greater than or equal to DEFAULT_LOAN_AMOUNT
        defaultCourtCaseShouldBeFound("loanAmount.greaterThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the courtCaseList where loanAmount is greater than or equal to UPDATED_LOAN_AMOUNT
        defaultCourtCaseShouldNotBeFound("loanAmount.greaterThanOrEqual=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanAmount is less than or equal to DEFAULT_LOAN_AMOUNT
        defaultCourtCaseShouldBeFound("loanAmount.lessThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the courtCaseList where loanAmount is less than or equal to SMALLER_LOAN_AMOUNT
        defaultCourtCaseShouldNotBeFound("loanAmount.lessThanOrEqual=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanAmount is less than DEFAULT_LOAN_AMOUNT
        defaultCourtCaseShouldNotBeFound("loanAmount.lessThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the courtCaseList where loanAmount is less than UPDATED_LOAN_AMOUNT
        defaultCourtCaseShouldBeFound("loanAmount.lessThan=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanAmount is greater than DEFAULT_LOAN_AMOUNT
        defaultCourtCaseShouldNotBeFound("loanAmount.greaterThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the courtCaseList where loanAmount is greater than SMALLER_LOAN_AMOUNT
        defaultCourtCaseShouldBeFound("loanAmount.greaterThan=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanDate equals to DEFAULT_LOAN_DATE
        defaultCourtCaseShouldBeFound("loanDate.equals=" + DEFAULT_LOAN_DATE);

        // Get all the courtCaseList where loanDate equals to UPDATED_LOAN_DATE
        defaultCourtCaseShouldNotBeFound("loanDate.equals=" + UPDATED_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanDate in DEFAULT_LOAN_DATE or UPDATED_LOAN_DATE
        defaultCourtCaseShouldBeFound("loanDate.in=" + DEFAULT_LOAN_DATE + "," + UPDATED_LOAN_DATE);

        // Get all the courtCaseList where loanDate equals to UPDATED_LOAN_DATE
        defaultCourtCaseShouldNotBeFound("loanDate.in=" + UPDATED_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanDate is not null
        defaultCourtCaseShouldBeFound("loanDate.specified=true");

        // Get all the courtCaseList where loanDate is null
        defaultCourtCaseShouldNotBeFound("loanDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanDate is greater than or equal to DEFAULT_LOAN_DATE
        defaultCourtCaseShouldBeFound("loanDate.greaterThanOrEqual=" + DEFAULT_LOAN_DATE);

        // Get all the courtCaseList where loanDate is greater than or equal to UPDATED_LOAN_DATE
        defaultCourtCaseShouldNotBeFound("loanDate.greaterThanOrEqual=" + UPDATED_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanDate is less than or equal to DEFAULT_LOAN_DATE
        defaultCourtCaseShouldBeFound("loanDate.lessThanOrEqual=" + DEFAULT_LOAN_DATE);

        // Get all the courtCaseList where loanDate is less than or equal to SMALLER_LOAN_DATE
        defaultCourtCaseShouldNotBeFound("loanDate.lessThanOrEqual=" + SMALLER_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanDate is less than DEFAULT_LOAN_DATE
        defaultCourtCaseShouldNotBeFound("loanDate.lessThan=" + DEFAULT_LOAN_DATE);

        // Get all the courtCaseList where loanDate is less than UPDATED_LOAN_DATE
        defaultCourtCaseShouldBeFound("loanDate.lessThan=" + UPDATED_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLoanDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where loanDate is greater than DEFAULT_LOAN_DATE
        defaultCourtCaseShouldNotBeFound("loanDate.greaterThan=" + DEFAULT_LOAN_DATE);

        // Get all the courtCaseList where loanDate is greater than SMALLER_LOAN_DATE
        defaultCourtCaseShouldBeFound("loanDate.greaterThan=" + SMALLER_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTermOfLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where termOfLoan equals to DEFAULT_TERM_OF_LOAN
        defaultCourtCaseShouldBeFound("termOfLoan.equals=" + DEFAULT_TERM_OF_LOAN);

        // Get all the courtCaseList where termOfLoan equals to UPDATED_TERM_OF_LOAN
        defaultCourtCaseShouldNotBeFound("termOfLoan.equals=" + UPDATED_TERM_OF_LOAN);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTermOfLoanIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where termOfLoan in DEFAULT_TERM_OF_LOAN or UPDATED_TERM_OF_LOAN
        defaultCourtCaseShouldBeFound("termOfLoan.in=" + DEFAULT_TERM_OF_LOAN + "," + UPDATED_TERM_OF_LOAN);

        // Get all the courtCaseList where termOfLoan equals to UPDATED_TERM_OF_LOAN
        defaultCourtCaseShouldNotBeFound("termOfLoan.in=" + UPDATED_TERM_OF_LOAN);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTermOfLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where termOfLoan is not null
        defaultCourtCaseShouldBeFound("termOfLoan.specified=true");

        // Get all the courtCaseList where termOfLoan is null
        defaultCourtCaseShouldNotBeFound("termOfLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByTermOfLoanContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where termOfLoan contains DEFAULT_TERM_OF_LOAN
        defaultCourtCaseShouldBeFound("termOfLoan.contains=" + DEFAULT_TERM_OF_LOAN);

        // Get all the courtCaseList where termOfLoan contains UPDATED_TERM_OF_LOAN
        defaultCourtCaseShouldNotBeFound("termOfLoan.contains=" + UPDATED_TERM_OF_LOAN);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTermOfLoanNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where termOfLoan does not contain DEFAULT_TERM_OF_LOAN
        defaultCourtCaseShouldNotBeFound("termOfLoan.doesNotContain=" + DEFAULT_TERM_OF_LOAN);

        // Get all the courtCaseList where termOfLoan does not contain UPDATED_TERM_OF_LOAN
        defaultCourtCaseShouldBeFound("termOfLoan.doesNotContain=" + UPDATED_TERM_OF_LOAN);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRate equals to DEFAULT_INTEREST_RATE
        defaultCourtCaseShouldBeFound("interestRate.equals=" + DEFAULT_INTEREST_RATE);

        // Get all the courtCaseList where interestRate equals to UPDATED_INTEREST_RATE
        defaultCourtCaseShouldNotBeFound("interestRate.equals=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRate in DEFAULT_INTEREST_RATE or UPDATED_INTEREST_RATE
        defaultCourtCaseShouldBeFound("interestRate.in=" + DEFAULT_INTEREST_RATE + "," + UPDATED_INTEREST_RATE);

        // Get all the courtCaseList where interestRate equals to UPDATED_INTEREST_RATE
        defaultCourtCaseShouldNotBeFound("interestRate.in=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRate is not null
        defaultCourtCaseShouldBeFound("interestRate.specified=true");

        // Get all the courtCaseList where interestRate is null
        defaultCourtCaseShouldNotBeFound("interestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRate is greater than or equal to DEFAULT_INTEREST_RATE
        defaultCourtCaseShouldBeFound("interestRate.greaterThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the courtCaseList where interestRate is greater than or equal to UPDATED_INTEREST_RATE
        defaultCourtCaseShouldNotBeFound("interestRate.greaterThanOrEqual=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRate is less than or equal to DEFAULT_INTEREST_RATE
        defaultCourtCaseShouldBeFound("interestRate.lessThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the courtCaseList where interestRate is less than or equal to SMALLER_INTEREST_RATE
        defaultCourtCaseShouldNotBeFound("interestRate.lessThanOrEqual=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRate is less than DEFAULT_INTEREST_RATE
        defaultCourtCaseShouldNotBeFound("interestRate.lessThan=" + DEFAULT_INTEREST_RATE);

        // Get all the courtCaseList where interestRate is less than UPDATED_INTEREST_RATE
        defaultCourtCaseShouldBeFound("interestRate.lessThan=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRate is greater than DEFAULT_INTEREST_RATE
        defaultCourtCaseShouldNotBeFound("interestRate.greaterThan=" + DEFAULT_INTEREST_RATE);

        // Get all the courtCaseList where interestRate is greater than SMALLER_INTEREST_RATE
        defaultCourtCaseShouldBeFound("interestRate.greaterThan=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInstallmentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where installmentAmount equals to DEFAULT_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldBeFound("installmentAmount.equals=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the courtCaseList where installmentAmount equals to UPDATED_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldNotBeFound("installmentAmount.equals=" + UPDATED_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInstallmentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where installmentAmount in DEFAULT_INSTALLMENT_AMOUNT or UPDATED_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldBeFound("installmentAmount.in=" + DEFAULT_INSTALLMENT_AMOUNT + "," + UPDATED_INSTALLMENT_AMOUNT);

        // Get all the courtCaseList where installmentAmount equals to UPDATED_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldNotBeFound("installmentAmount.in=" + UPDATED_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInstallmentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where installmentAmount is not null
        defaultCourtCaseShouldBeFound("installmentAmount.specified=true");

        // Get all the courtCaseList where installmentAmount is null
        defaultCourtCaseShouldNotBeFound("installmentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByInstallmentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where installmentAmount is greater than or equal to DEFAULT_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldBeFound("installmentAmount.greaterThanOrEqual=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the courtCaseList where installmentAmount is greater than or equal to UPDATED_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldNotBeFound("installmentAmount.greaterThanOrEqual=" + UPDATED_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInstallmentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where installmentAmount is less than or equal to DEFAULT_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldBeFound("installmentAmount.lessThanOrEqual=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the courtCaseList where installmentAmount is less than or equal to SMALLER_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldNotBeFound("installmentAmount.lessThanOrEqual=" + SMALLER_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInstallmentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where installmentAmount is less than DEFAULT_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldNotBeFound("installmentAmount.lessThan=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the courtCaseList where installmentAmount is less than UPDATED_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldBeFound("installmentAmount.lessThan=" + UPDATED_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInstallmentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where installmentAmount is greater than DEFAULT_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldNotBeFound("installmentAmount.greaterThan=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the courtCaseList where installmentAmount is greater than SMALLER_INSTALLMENT_AMOUNT
        defaultCourtCaseShouldBeFound("installmentAmount.greaterThan=" + SMALLER_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalCreditIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalCredit equals to DEFAULT_TOTAL_CREDIT
        defaultCourtCaseShouldBeFound("totalCredit.equals=" + DEFAULT_TOTAL_CREDIT);

        // Get all the courtCaseList where totalCredit equals to UPDATED_TOTAL_CREDIT
        defaultCourtCaseShouldNotBeFound("totalCredit.equals=" + UPDATED_TOTAL_CREDIT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalCreditIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalCredit in DEFAULT_TOTAL_CREDIT or UPDATED_TOTAL_CREDIT
        defaultCourtCaseShouldBeFound("totalCredit.in=" + DEFAULT_TOTAL_CREDIT + "," + UPDATED_TOTAL_CREDIT);

        // Get all the courtCaseList where totalCredit equals to UPDATED_TOTAL_CREDIT
        defaultCourtCaseShouldNotBeFound("totalCredit.in=" + UPDATED_TOTAL_CREDIT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalCreditIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalCredit is not null
        defaultCourtCaseShouldBeFound("totalCredit.specified=true");

        // Get all the courtCaseList where totalCredit is null
        defaultCourtCaseShouldNotBeFound("totalCredit.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalCreditIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalCredit is greater than or equal to DEFAULT_TOTAL_CREDIT
        defaultCourtCaseShouldBeFound("totalCredit.greaterThanOrEqual=" + DEFAULT_TOTAL_CREDIT);

        // Get all the courtCaseList where totalCredit is greater than or equal to UPDATED_TOTAL_CREDIT
        defaultCourtCaseShouldNotBeFound("totalCredit.greaterThanOrEqual=" + UPDATED_TOTAL_CREDIT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalCreditIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalCredit is less than or equal to DEFAULT_TOTAL_CREDIT
        defaultCourtCaseShouldBeFound("totalCredit.lessThanOrEqual=" + DEFAULT_TOTAL_CREDIT);

        // Get all the courtCaseList where totalCredit is less than or equal to SMALLER_TOTAL_CREDIT
        defaultCourtCaseShouldNotBeFound("totalCredit.lessThanOrEqual=" + SMALLER_TOTAL_CREDIT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalCreditIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalCredit is less than DEFAULT_TOTAL_CREDIT
        defaultCourtCaseShouldNotBeFound("totalCredit.lessThan=" + DEFAULT_TOTAL_CREDIT);

        // Get all the courtCaseList where totalCredit is less than UPDATED_TOTAL_CREDIT
        defaultCourtCaseShouldBeFound("totalCredit.lessThan=" + UPDATED_TOTAL_CREDIT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalCreditIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalCredit is greater than DEFAULT_TOTAL_CREDIT
        defaultCourtCaseShouldNotBeFound("totalCredit.greaterThan=" + DEFAULT_TOTAL_CREDIT);

        // Get all the courtCaseList where totalCredit is greater than SMALLER_TOTAL_CREDIT
        defaultCourtCaseShouldBeFound("totalCredit.greaterThan=" + SMALLER_TOTAL_CREDIT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where balance equals to DEFAULT_BALANCE
        defaultCourtCaseShouldBeFound("balance.equals=" + DEFAULT_BALANCE);

        // Get all the courtCaseList where balance equals to UPDATED_BALANCE
        defaultCourtCaseShouldNotBeFound("balance.equals=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where balance in DEFAULT_BALANCE or UPDATED_BALANCE
        defaultCourtCaseShouldBeFound("balance.in=" + DEFAULT_BALANCE + "," + UPDATED_BALANCE);

        // Get all the courtCaseList where balance equals to UPDATED_BALANCE
        defaultCourtCaseShouldNotBeFound("balance.in=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where balance is not null
        defaultCourtCaseShouldBeFound("balance.specified=true");

        // Get all the courtCaseList where balance is null
        defaultCourtCaseShouldNotBeFound("balance.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where balance is greater than or equal to DEFAULT_BALANCE
        defaultCourtCaseShouldBeFound("balance.greaterThanOrEqual=" + DEFAULT_BALANCE);

        // Get all the courtCaseList where balance is greater than or equal to UPDATED_BALANCE
        defaultCourtCaseShouldNotBeFound("balance.greaterThanOrEqual=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where balance is less than or equal to DEFAULT_BALANCE
        defaultCourtCaseShouldBeFound("balance.lessThanOrEqual=" + DEFAULT_BALANCE);

        // Get all the courtCaseList where balance is less than or equal to SMALLER_BALANCE
        defaultCourtCaseShouldNotBeFound("balance.lessThanOrEqual=" + SMALLER_BALANCE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where balance is less than DEFAULT_BALANCE
        defaultCourtCaseShouldNotBeFound("balance.lessThan=" + DEFAULT_BALANCE);

        // Get all the courtCaseList where balance is less than UPDATED_BALANCE
        defaultCourtCaseShouldBeFound("balance.lessThan=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where balance is greater than DEFAULT_BALANCE
        defaultCourtCaseShouldNotBeFound("balance.greaterThan=" + DEFAULT_BALANCE);

        // Get all the courtCaseList where balance is greater than SMALLER_BALANCE
        defaultCourtCaseShouldBeFound("balance.greaterThan=" + SMALLER_BALANCE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestPaidIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestPaid equals to DEFAULT_INTEREST_PAID
        defaultCourtCaseShouldBeFound("interestPaid.equals=" + DEFAULT_INTEREST_PAID);

        // Get all the courtCaseList where interestPaid equals to UPDATED_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("interestPaid.equals=" + UPDATED_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestPaidIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestPaid in DEFAULT_INTEREST_PAID or UPDATED_INTEREST_PAID
        defaultCourtCaseShouldBeFound("interestPaid.in=" + DEFAULT_INTEREST_PAID + "," + UPDATED_INTEREST_PAID);

        // Get all the courtCaseList where interestPaid equals to UPDATED_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("interestPaid.in=" + UPDATED_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestPaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestPaid is not null
        defaultCourtCaseShouldBeFound("interestPaid.specified=true");

        // Get all the courtCaseList where interestPaid is null
        defaultCourtCaseShouldNotBeFound("interestPaid.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestPaidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestPaid is greater than or equal to DEFAULT_INTEREST_PAID
        defaultCourtCaseShouldBeFound("interestPaid.greaterThanOrEqual=" + DEFAULT_INTEREST_PAID);

        // Get all the courtCaseList where interestPaid is greater than or equal to UPDATED_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("interestPaid.greaterThanOrEqual=" + UPDATED_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestPaidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestPaid is less than or equal to DEFAULT_INTEREST_PAID
        defaultCourtCaseShouldBeFound("interestPaid.lessThanOrEqual=" + DEFAULT_INTEREST_PAID);

        // Get all the courtCaseList where interestPaid is less than or equal to SMALLER_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("interestPaid.lessThanOrEqual=" + SMALLER_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestPaidIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestPaid is less than DEFAULT_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("interestPaid.lessThan=" + DEFAULT_INTEREST_PAID);

        // Get all the courtCaseList where interestPaid is less than UPDATED_INTEREST_PAID
        defaultCourtCaseShouldBeFound("interestPaid.lessThan=" + UPDATED_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestPaidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestPaid is greater than DEFAULT_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("interestPaid.greaterThan=" + DEFAULT_INTEREST_PAID);

        // Get all the courtCaseList where interestPaid is greater than SMALLER_INTEREST_PAID
        defaultCourtCaseShouldBeFound("interestPaid.greaterThan=" + SMALLER_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByPenalInterestPaidIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where penalInterestPaid equals to DEFAULT_PENAL_INTEREST_PAID
        defaultCourtCaseShouldBeFound("penalInterestPaid.equals=" + DEFAULT_PENAL_INTEREST_PAID);

        // Get all the courtCaseList where penalInterestPaid equals to UPDATED_PENAL_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("penalInterestPaid.equals=" + UPDATED_PENAL_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByPenalInterestPaidIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where penalInterestPaid in DEFAULT_PENAL_INTEREST_PAID or UPDATED_PENAL_INTEREST_PAID
        defaultCourtCaseShouldBeFound("penalInterestPaid.in=" + DEFAULT_PENAL_INTEREST_PAID + "," + UPDATED_PENAL_INTEREST_PAID);

        // Get all the courtCaseList where penalInterestPaid equals to UPDATED_PENAL_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("penalInterestPaid.in=" + UPDATED_PENAL_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByPenalInterestPaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where penalInterestPaid is not null
        defaultCourtCaseShouldBeFound("penalInterestPaid.specified=true");

        // Get all the courtCaseList where penalInterestPaid is null
        defaultCourtCaseShouldNotBeFound("penalInterestPaid.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByPenalInterestPaidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where penalInterestPaid is greater than or equal to DEFAULT_PENAL_INTEREST_PAID
        defaultCourtCaseShouldBeFound("penalInterestPaid.greaterThanOrEqual=" + DEFAULT_PENAL_INTEREST_PAID);

        // Get all the courtCaseList where penalInterestPaid is greater than or equal to UPDATED_PENAL_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("penalInterestPaid.greaterThanOrEqual=" + UPDATED_PENAL_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByPenalInterestPaidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where penalInterestPaid is less than or equal to DEFAULT_PENAL_INTEREST_PAID
        defaultCourtCaseShouldBeFound("penalInterestPaid.lessThanOrEqual=" + DEFAULT_PENAL_INTEREST_PAID);

        // Get all the courtCaseList where penalInterestPaid is less than or equal to SMALLER_PENAL_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("penalInterestPaid.lessThanOrEqual=" + SMALLER_PENAL_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByPenalInterestPaidIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where penalInterestPaid is less than DEFAULT_PENAL_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("penalInterestPaid.lessThan=" + DEFAULT_PENAL_INTEREST_PAID);

        // Get all the courtCaseList where penalInterestPaid is less than UPDATED_PENAL_INTEREST_PAID
        defaultCourtCaseShouldBeFound("penalInterestPaid.lessThan=" + UPDATED_PENAL_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByPenalInterestPaidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where penalInterestPaid is greater than DEFAULT_PENAL_INTEREST_PAID
        defaultCourtCaseShouldNotBeFound("penalInterestPaid.greaterThan=" + DEFAULT_PENAL_INTEREST_PAID);

        // Get all the courtCaseList where penalInterestPaid is greater than SMALLER_PENAL_INTEREST_PAID
        defaultCourtCaseShouldBeFound("penalInterestPaid.greaterThan=" + SMALLER_PENAL_INTEREST_PAID);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueAmount equals to DEFAULT_DUE_AMOUNT
        defaultCourtCaseShouldBeFound("dueAmount.equals=" + DEFAULT_DUE_AMOUNT);

        // Get all the courtCaseList where dueAmount equals to UPDATED_DUE_AMOUNT
        defaultCourtCaseShouldNotBeFound("dueAmount.equals=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueAmountIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueAmount in DEFAULT_DUE_AMOUNT or UPDATED_DUE_AMOUNT
        defaultCourtCaseShouldBeFound("dueAmount.in=" + DEFAULT_DUE_AMOUNT + "," + UPDATED_DUE_AMOUNT);

        // Get all the courtCaseList where dueAmount equals to UPDATED_DUE_AMOUNT
        defaultCourtCaseShouldNotBeFound("dueAmount.in=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueAmount is not null
        defaultCourtCaseShouldBeFound("dueAmount.specified=true");

        // Get all the courtCaseList where dueAmount is null
        defaultCourtCaseShouldNotBeFound("dueAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueAmount is greater than or equal to DEFAULT_DUE_AMOUNT
        defaultCourtCaseShouldBeFound("dueAmount.greaterThanOrEqual=" + DEFAULT_DUE_AMOUNT);

        // Get all the courtCaseList where dueAmount is greater than or equal to UPDATED_DUE_AMOUNT
        defaultCourtCaseShouldNotBeFound("dueAmount.greaterThanOrEqual=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueAmount is less than or equal to DEFAULT_DUE_AMOUNT
        defaultCourtCaseShouldBeFound("dueAmount.lessThanOrEqual=" + DEFAULT_DUE_AMOUNT);

        // Get all the courtCaseList where dueAmount is less than or equal to SMALLER_DUE_AMOUNT
        defaultCourtCaseShouldNotBeFound("dueAmount.lessThanOrEqual=" + SMALLER_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueAmount is less than DEFAULT_DUE_AMOUNT
        defaultCourtCaseShouldNotBeFound("dueAmount.lessThan=" + DEFAULT_DUE_AMOUNT);

        // Get all the courtCaseList where dueAmount is less than UPDATED_DUE_AMOUNT
        defaultCourtCaseShouldBeFound("dueAmount.lessThan=" + UPDATED_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueAmount is greater than DEFAULT_DUE_AMOUNT
        defaultCourtCaseShouldNotBeFound("dueAmount.greaterThan=" + DEFAULT_DUE_AMOUNT);

        // Get all the courtCaseList where dueAmount is greater than SMALLER_DUE_AMOUNT
        defaultCourtCaseShouldBeFound("dueAmount.greaterThan=" + SMALLER_DUE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueDate equals to DEFAULT_DUE_DATE
        defaultCourtCaseShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the courtCaseList where dueDate equals to UPDATED_DUE_DATE
        defaultCourtCaseShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultCourtCaseShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the courtCaseList where dueDate equals to UPDATED_DUE_DATE
        defaultCourtCaseShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueDate is not null
        defaultCourtCaseShouldBeFound("dueDate.specified=true");

        // Get all the courtCaseList where dueDate is null
        defaultCourtCaseShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultCourtCaseShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the courtCaseList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultCourtCaseShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultCourtCaseShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the courtCaseList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultCourtCaseShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueDate is less than DEFAULT_DUE_DATE
        defaultCourtCaseShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the courtCaseList where dueDate is less than UPDATED_DUE_DATE
        defaultCourtCaseShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueDate is greater than DEFAULT_DUE_DATE
        defaultCourtCaseShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the courtCaseList where dueDate is greater than SMALLER_DUE_DATE
        defaultCourtCaseShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueInterest equals to DEFAULT_DUE_INTEREST
        defaultCourtCaseShouldBeFound("dueInterest.equals=" + DEFAULT_DUE_INTEREST);

        // Get all the courtCaseList where dueInterest equals to UPDATED_DUE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueInterest.equals=" + UPDATED_DUE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueInterestIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueInterest in DEFAULT_DUE_INTEREST or UPDATED_DUE_INTEREST
        defaultCourtCaseShouldBeFound("dueInterest.in=" + DEFAULT_DUE_INTEREST + "," + UPDATED_DUE_INTEREST);

        // Get all the courtCaseList where dueInterest equals to UPDATED_DUE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueInterest.in=" + UPDATED_DUE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueInterest is not null
        defaultCourtCaseShouldBeFound("dueInterest.specified=true");

        // Get all the courtCaseList where dueInterest is null
        defaultCourtCaseShouldNotBeFound("dueInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueInterest is greater than or equal to DEFAULT_DUE_INTEREST
        defaultCourtCaseShouldBeFound("dueInterest.greaterThanOrEqual=" + DEFAULT_DUE_INTEREST);

        // Get all the courtCaseList where dueInterest is greater than or equal to UPDATED_DUE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueInterest.greaterThanOrEqual=" + UPDATED_DUE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueInterest is less than or equal to DEFAULT_DUE_INTEREST
        defaultCourtCaseShouldBeFound("dueInterest.lessThanOrEqual=" + DEFAULT_DUE_INTEREST);

        // Get all the courtCaseList where dueInterest is less than or equal to SMALLER_DUE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueInterest.lessThanOrEqual=" + SMALLER_DUE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueInterest is less than DEFAULT_DUE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueInterest.lessThan=" + DEFAULT_DUE_INTEREST);

        // Get all the courtCaseList where dueInterest is less than UPDATED_DUE_INTEREST
        defaultCourtCaseShouldBeFound("dueInterest.lessThan=" + UPDATED_DUE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueInterest is greater than DEFAULT_DUE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueInterest.greaterThan=" + DEFAULT_DUE_INTEREST);

        // Get all the courtCaseList where dueInterest is greater than SMALLER_DUE_INTEREST
        defaultCourtCaseShouldBeFound("dueInterest.greaterThan=" + SMALLER_DUE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDuePenalInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where duePenalInterest equals to DEFAULT_DUE_PENAL_INTEREST
        defaultCourtCaseShouldBeFound("duePenalInterest.equals=" + DEFAULT_DUE_PENAL_INTEREST);

        // Get all the courtCaseList where duePenalInterest equals to UPDATED_DUE_PENAL_INTEREST
        defaultCourtCaseShouldNotBeFound("duePenalInterest.equals=" + UPDATED_DUE_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDuePenalInterestIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where duePenalInterest in DEFAULT_DUE_PENAL_INTEREST or UPDATED_DUE_PENAL_INTEREST
        defaultCourtCaseShouldBeFound("duePenalInterest.in=" + DEFAULT_DUE_PENAL_INTEREST + "," + UPDATED_DUE_PENAL_INTEREST);

        // Get all the courtCaseList where duePenalInterest equals to UPDATED_DUE_PENAL_INTEREST
        defaultCourtCaseShouldNotBeFound("duePenalInterest.in=" + UPDATED_DUE_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDuePenalInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where duePenalInterest is not null
        defaultCourtCaseShouldBeFound("duePenalInterest.specified=true");

        // Get all the courtCaseList where duePenalInterest is null
        defaultCourtCaseShouldNotBeFound("duePenalInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDuePenalInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where duePenalInterest is greater than or equal to DEFAULT_DUE_PENAL_INTEREST
        defaultCourtCaseShouldBeFound("duePenalInterest.greaterThanOrEqual=" + DEFAULT_DUE_PENAL_INTEREST);

        // Get all the courtCaseList where duePenalInterest is greater than or equal to UPDATED_DUE_PENAL_INTEREST
        defaultCourtCaseShouldNotBeFound("duePenalInterest.greaterThanOrEqual=" + UPDATED_DUE_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDuePenalInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where duePenalInterest is less than or equal to DEFAULT_DUE_PENAL_INTEREST
        defaultCourtCaseShouldBeFound("duePenalInterest.lessThanOrEqual=" + DEFAULT_DUE_PENAL_INTEREST);

        // Get all the courtCaseList where duePenalInterest is less than or equal to SMALLER_DUE_PENAL_INTEREST
        defaultCourtCaseShouldNotBeFound("duePenalInterest.lessThanOrEqual=" + SMALLER_DUE_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDuePenalInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where duePenalInterest is less than DEFAULT_DUE_PENAL_INTEREST
        defaultCourtCaseShouldNotBeFound("duePenalInterest.lessThan=" + DEFAULT_DUE_PENAL_INTEREST);

        // Get all the courtCaseList where duePenalInterest is less than UPDATED_DUE_PENAL_INTEREST
        defaultCourtCaseShouldBeFound("duePenalInterest.lessThan=" + UPDATED_DUE_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDuePenalInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where duePenalInterest is greater than DEFAULT_DUE_PENAL_INTEREST
        defaultCourtCaseShouldNotBeFound("duePenalInterest.greaterThan=" + DEFAULT_DUE_PENAL_INTEREST);

        // Get all the courtCaseList where duePenalInterest is greater than SMALLER_DUE_PENAL_INTEREST
        defaultCourtCaseShouldBeFound("duePenalInterest.greaterThan=" + SMALLER_DUE_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueMoreInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueMoreInterest equals to DEFAULT_DUE_MORE_INTEREST
        defaultCourtCaseShouldBeFound("dueMoreInterest.equals=" + DEFAULT_DUE_MORE_INTEREST);

        // Get all the courtCaseList where dueMoreInterest equals to UPDATED_DUE_MORE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueMoreInterest.equals=" + UPDATED_DUE_MORE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueMoreInterestIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueMoreInterest in DEFAULT_DUE_MORE_INTEREST or UPDATED_DUE_MORE_INTEREST
        defaultCourtCaseShouldBeFound("dueMoreInterest.in=" + DEFAULT_DUE_MORE_INTEREST + "," + UPDATED_DUE_MORE_INTEREST);

        // Get all the courtCaseList where dueMoreInterest equals to UPDATED_DUE_MORE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueMoreInterest.in=" + UPDATED_DUE_MORE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueMoreInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueMoreInterest is not null
        defaultCourtCaseShouldBeFound("dueMoreInterest.specified=true");

        // Get all the courtCaseList where dueMoreInterest is null
        defaultCourtCaseShouldNotBeFound("dueMoreInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueMoreInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueMoreInterest is greater than or equal to DEFAULT_DUE_MORE_INTEREST
        defaultCourtCaseShouldBeFound("dueMoreInterest.greaterThanOrEqual=" + DEFAULT_DUE_MORE_INTEREST);

        // Get all the courtCaseList where dueMoreInterest is greater than or equal to UPDATED_DUE_MORE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueMoreInterest.greaterThanOrEqual=" + UPDATED_DUE_MORE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueMoreInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueMoreInterest is less than or equal to DEFAULT_DUE_MORE_INTEREST
        defaultCourtCaseShouldBeFound("dueMoreInterest.lessThanOrEqual=" + DEFAULT_DUE_MORE_INTEREST);

        // Get all the courtCaseList where dueMoreInterest is less than or equal to SMALLER_DUE_MORE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueMoreInterest.lessThanOrEqual=" + SMALLER_DUE_MORE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueMoreInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueMoreInterest is less than DEFAULT_DUE_MORE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueMoreInterest.lessThan=" + DEFAULT_DUE_MORE_INTEREST);

        // Get all the courtCaseList where dueMoreInterest is less than UPDATED_DUE_MORE_INTEREST
        defaultCourtCaseShouldBeFound("dueMoreInterest.lessThan=" + UPDATED_DUE_MORE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDueMoreInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dueMoreInterest is greater than DEFAULT_DUE_MORE_INTEREST
        defaultCourtCaseShouldNotBeFound("dueMoreInterest.greaterThan=" + DEFAULT_DUE_MORE_INTEREST);

        // Get all the courtCaseList where dueMoreInterest is greater than SMALLER_DUE_MORE_INTEREST
        defaultCourtCaseShouldBeFound("dueMoreInterest.greaterThan=" + SMALLER_DUE_MORE_INTEREST);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRecivableIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRecivable equals to DEFAULT_INTEREST_RECIVABLE
        defaultCourtCaseShouldBeFound("interestRecivable.equals=" + DEFAULT_INTEREST_RECIVABLE);

        // Get all the courtCaseList where interestRecivable equals to UPDATED_INTEREST_RECIVABLE
        defaultCourtCaseShouldNotBeFound("interestRecivable.equals=" + UPDATED_INTEREST_RECIVABLE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRecivableIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRecivable in DEFAULT_INTEREST_RECIVABLE or UPDATED_INTEREST_RECIVABLE
        defaultCourtCaseShouldBeFound("interestRecivable.in=" + DEFAULT_INTEREST_RECIVABLE + "," + UPDATED_INTEREST_RECIVABLE);

        // Get all the courtCaseList where interestRecivable equals to UPDATED_INTEREST_RECIVABLE
        defaultCourtCaseShouldNotBeFound("interestRecivable.in=" + UPDATED_INTEREST_RECIVABLE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRecivableIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRecivable is not null
        defaultCourtCaseShouldBeFound("interestRecivable.specified=true");

        // Get all the courtCaseList where interestRecivable is null
        defaultCourtCaseShouldNotBeFound("interestRecivable.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRecivableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRecivable is greater than or equal to DEFAULT_INTEREST_RECIVABLE
        defaultCourtCaseShouldBeFound("interestRecivable.greaterThanOrEqual=" + DEFAULT_INTEREST_RECIVABLE);

        // Get all the courtCaseList where interestRecivable is greater than or equal to UPDATED_INTEREST_RECIVABLE
        defaultCourtCaseShouldNotBeFound("interestRecivable.greaterThanOrEqual=" + UPDATED_INTEREST_RECIVABLE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRecivableIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRecivable is less than or equal to DEFAULT_INTEREST_RECIVABLE
        defaultCourtCaseShouldBeFound("interestRecivable.lessThanOrEqual=" + DEFAULT_INTEREST_RECIVABLE);

        // Get all the courtCaseList where interestRecivable is less than or equal to SMALLER_INTEREST_RECIVABLE
        defaultCourtCaseShouldNotBeFound("interestRecivable.lessThanOrEqual=" + SMALLER_INTEREST_RECIVABLE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRecivableIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRecivable is less than DEFAULT_INTEREST_RECIVABLE
        defaultCourtCaseShouldNotBeFound("interestRecivable.lessThan=" + DEFAULT_INTEREST_RECIVABLE);

        // Get all the courtCaseList where interestRecivable is less than UPDATED_INTEREST_RECIVABLE
        defaultCourtCaseShouldBeFound("interestRecivable.lessThan=" + UPDATED_INTEREST_RECIVABLE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByInterestRecivableIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where interestRecivable is greater than DEFAULT_INTEREST_RECIVABLE
        defaultCourtCaseShouldNotBeFound("interestRecivable.greaterThan=" + DEFAULT_INTEREST_RECIVABLE);

        // Get all the courtCaseList where interestRecivable is greater than SMALLER_INTEREST_RECIVABLE
        defaultCourtCaseShouldBeFound("interestRecivable.greaterThan=" + SMALLER_INTEREST_RECIVABLE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOne equals to DEFAULT_GAURENTOR_ONE
        defaultCourtCaseShouldBeFound("gaurentorOne.equals=" + DEFAULT_GAURENTOR_ONE);

        // Get all the courtCaseList where gaurentorOne equals to UPDATED_GAURENTOR_ONE
        defaultCourtCaseShouldNotBeFound("gaurentorOne.equals=" + UPDATED_GAURENTOR_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOne in DEFAULT_GAURENTOR_ONE or UPDATED_GAURENTOR_ONE
        defaultCourtCaseShouldBeFound("gaurentorOne.in=" + DEFAULT_GAURENTOR_ONE + "," + UPDATED_GAURENTOR_ONE);

        // Get all the courtCaseList where gaurentorOne equals to UPDATED_GAURENTOR_ONE
        defaultCourtCaseShouldNotBeFound("gaurentorOne.in=" + UPDATED_GAURENTOR_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOne is not null
        defaultCourtCaseShouldBeFound("gaurentorOne.specified=true");

        // Get all the courtCaseList where gaurentorOne is null
        defaultCourtCaseShouldNotBeFound("gaurentorOne.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOne contains DEFAULT_GAURENTOR_ONE
        defaultCourtCaseShouldBeFound("gaurentorOne.contains=" + DEFAULT_GAURENTOR_ONE);

        // Get all the courtCaseList where gaurentorOne contains UPDATED_GAURENTOR_ONE
        defaultCourtCaseShouldNotBeFound("gaurentorOne.contains=" + UPDATED_GAURENTOR_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOne does not contain DEFAULT_GAURENTOR_ONE
        defaultCourtCaseShouldNotBeFound("gaurentorOne.doesNotContain=" + DEFAULT_GAURENTOR_ONE);

        // Get all the courtCaseList where gaurentorOne does not contain UPDATED_GAURENTOR_ONE
        defaultCourtCaseShouldBeFound("gaurentorOne.doesNotContain=" + UPDATED_GAURENTOR_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOneAddress equals to DEFAULT_GAURENTOR_ONE_ADDRESS
        defaultCourtCaseShouldBeFound("gaurentorOneAddress.equals=" + DEFAULT_GAURENTOR_ONE_ADDRESS);

        // Get all the courtCaseList where gaurentorOneAddress equals to UPDATED_GAURENTOR_ONE_ADDRESS
        defaultCourtCaseShouldNotBeFound("gaurentorOneAddress.equals=" + UPDATED_GAURENTOR_ONE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneAddressIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOneAddress in DEFAULT_GAURENTOR_ONE_ADDRESS or UPDATED_GAURENTOR_ONE_ADDRESS
        defaultCourtCaseShouldBeFound("gaurentorOneAddress.in=" + DEFAULT_GAURENTOR_ONE_ADDRESS + "," + UPDATED_GAURENTOR_ONE_ADDRESS);

        // Get all the courtCaseList where gaurentorOneAddress equals to UPDATED_GAURENTOR_ONE_ADDRESS
        defaultCourtCaseShouldNotBeFound("gaurentorOneAddress.in=" + UPDATED_GAURENTOR_ONE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOneAddress is not null
        defaultCourtCaseShouldBeFound("gaurentorOneAddress.specified=true");

        // Get all the courtCaseList where gaurentorOneAddress is null
        defaultCourtCaseShouldNotBeFound("gaurentorOneAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneAddressContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOneAddress contains DEFAULT_GAURENTOR_ONE_ADDRESS
        defaultCourtCaseShouldBeFound("gaurentorOneAddress.contains=" + DEFAULT_GAURENTOR_ONE_ADDRESS);

        // Get all the courtCaseList where gaurentorOneAddress contains UPDATED_GAURENTOR_ONE_ADDRESS
        defaultCourtCaseShouldNotBeFound("gaurentorOneAddress.contains=" + UPDATED_GAURENTOR_ONE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorOneAddressNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorOneAddress does not contain DEFAULT_GAURENTOR_ONE_ADDRESS
        defaultCourtCaseShouldNotBeFound("gaurentorOneAddress.doesNotContain=" + DEFAULT_GAURENTOR_ONE_ADDRESS);

        // Get all the courtCaseList where gaurentorOneAddress does not contain UPDATED_GAURENTOR_ONE_ADDRESS
        defaultCourtCaseShouldBeFound("gaurentorOneAddress.doesNotContain=" + UPDATED_GAURENTOR_ONE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwo equals to DEFAULT_GAURENTOR_TWO
        defaultCourtCaseShouldBeFound("gaurentorTwo.equals=" + DEFAULT_GAURENTOR_TWO);

        // Get all the courtCaseList where gaurentorTwo equals to UPDATED_GAURENTOR_TWO
        defaultCourtCaseShouldNotBeFound("gaurentorTwo.equals=" + UPDATED_GAURENTOR_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwo in DEFAULT_GAURENTOR_TWO or UPDATED_GAURENTOR_TWO
        defaultCourtCaseShouldBeFound("gaurentorTwo.in=" + DEFAULT_GAURENTOR_TWO + "," + UPDATED_GAURENTOR_TWO);

        // Get all the courtCaseList where gaurentorTwo equals to UPDATED_GAURENTOR_TWO
        defaultCourtCaseShouldNotBeFound("gaurentorTwo.in=" + UPDATED_GAURENTOR_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwo is not null
        defaultCourtCaseShouldBeFound("gaurentorTwo.specified=true");

        // Get all the courtCaseList where gaurentorTwo is null
        defaultCourtCaseShouldNotBeFound("gaurentorTwo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwo contains DEFAULT_GAURENTOR_TWO
        defaultCourtCaseShouldBeFound("gaurentorTwo.contains=" + DEFAULT_GAURENTOR_TWO);

        // Get all the courtCaseList where gaurentorTwo contains UPDATED_GAURENTOR_TWO
        defaultCourtCaseShouldNotBeFound("gaurentorTwo.contains=" + UPDATED_GAURENTOR_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwo does not contain DEFAULT_GAURENTOR_TWO
        defaultCourtCaseShouldNotBeFound("gaurentorTwo.doesNotContain=" + DEFAULT_GAURENTOR_TWO);

        // Get all the courtCaseList where gaurentorTwo does not contain UPDATED_GAURENTOR_TWO
        defaultCourtCaseShouldBeFound("gaurentorTwo.doesNotContain=" + UPDATED_GAURENTOR_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwoAddress equals to DEFAULT_GAURENTOR_TWO_ADDRESS
        defaultCourtCaseShouldBeFound("gaurentorTwoAddress.equals=" + DEFAULT_GAURENTOR_TWO_ADDRESS);

        // Get all the courtCaseList where gaurentorTwoAddress equals to UPDATED_GAURENTOR_TWO_ADDRESS
        defaultCourtCaseShouldNotBeFound("gaurentorTwoAddress.equals=" + UPDATED_GAURENTOR_TWO_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoAddressIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwoAddress in DEFAULT_GAURENTOR_TWO_ADDRESS or UPDATED_GAURENTOR_TWO_ADDRESS
        defaultCourtCaseShouldBeFound("gaurentorTwoAddress.in=" + DEFAULT_GAURENTOR_TWO_ADDRESS + "," + UPDATED_GAURENTOR_TWO_ADDRESS);

        // Get all the courtCaseList where gaurentorTwoAddress equals to UPDATED_GAURENTOR_TWO_ADDRESS
        defaultCourtCaseShouldNotBeFound("gaurentorTwoAddress.in=" + UPDATED_GAURENTOR_TWO_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwoAddress is not null
        defaultCourtCaseShouldBeFound("gaurentorTwoAddress.specified=true");

        // Get all the courtCaseList where gaurentorTwoAddress is null
        defaultCourtCaseShouldNotBeFound("gaurentorTwoAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoAddressContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwoAddress contains DEFAULT_GAURENTOR_TWO_ADDRESS
        defaultCourtCaseShouldBeFound("gaurentorTwoAddress.contains=" + DEFAULT_GAURENTOR_TWO_ADDRESS);

        // Get all the courtCaseList where gaurentorTwoAddress contains UPDATED_GAURENTOR_TWO_ADDRESS
        defaultCourtCaseShouldNotBeFound("gaurentorTwoAddress.contains=" + UPDATED_GAURENTOR_TWO_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGaurentorTwoAddressNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gaurentorTwoAddress does not contain DEFAULT_GAURENTOR_TWO_ADDRESS
        defaultCourtCaseShouldNotBeFound("gaurentorTwoAddress.doesNotContain=" + DEFAULT_GAURENTOR_TWO_ADDRESS);

        // Get all the courtCaseList where gaurentorTwoAddress does not contain UPDATED_GAURENTOR_TWO_ADDRESS
        defaultCourtCaseShouldBeFound("gaurentorTwoAddress.doesNotContain=" + UPDATED_GAURENTOR_TWO_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNoticeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstNoticeDate equals to DEFAULT_FIRST_NOTICE_DATE
        defaultCourtCaseShouldBeFound("firstNoticeDate.equals=" + DEFAULT_FIRST_NOTICE_DATE);

        // Get all the courtCaseList where firstNoticeDate equals to UPDATED_FIRST_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("firstNoticeDate.equals=" + UPDATED_FIRST_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNoticeDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstNoticeDate in DEFAULT_FIRST_NOTICE_DATE or UPDATED_FIRST_NOTICE_DATE
        defaultCourtCaseShouldBeFound("firstNoticeDate.in=" + DEFAULT_FIRST_NOTICE_DATE + "," + UPDATED_FIRST_NOTICE_DATE);

        // Get all the courtCaseList where firstNoticeDate equals to UPDATED_FIRST_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("firstNoticeDate.in=" + UPDATED_FIRST_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNoticeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstNoticeDate is not null
        defaultCourtCaseShouldBeFound("firstNoticeDate.specified=true");

        // Get all the courtCaseList where firstNoticeDate is null
        defaultCourtCaseShouldNotBeFound("firstNoticeDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNoticeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstNoticeDate is greater than or equal to DEFAULT_FIRST_NOTICE_DATE
        defaultCourtCaseShouldBeFound("firstNoticeDate.greaterThanOrEqual=" + DEFAULT_FIRST_NOTICE_DATE);

        // Get all the courtCaseList where firstNoticeDate is greater than or equal to UPDATED_FIRST_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("firstNoticeDate.greaterThanOrEqual=" + UPDATED_FIRST_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNoticeDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstNoticeDate is less than or equal to DEFAULT_FIRST_NOTICE_DATE
        defaultCourtCaseShouldBeFound("firstNoticeDate.lessThanOrEqual=" + DEFAULT_FIRST_NOTICE_DATE);

        // Get all the courtCaseList where firstNoticeDate is less than or equal to SMALLER_FIRST_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("firstNoticeDate.lessThanOrEqual=" + SMALLER_FIRST_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNoticeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstNoticeDate is less than DEFAULT_FIRST_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("firstNoticeDate.lessThan=" + DEFAULT_FIRST_NOTICE_DATE);

        // Get all the courtCaseList where firstNoticeDate is less than UPDATED_FIRST_NOTICE_DATE
        defaultCourtCaseShouldBeFound("firstNoticeDate.lessThan=" + UPDATED_FIRST_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNoticeDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstNoticeDate is greater than DEFAULT_FIRST_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("firstNoticeDate.greaterThan=" + DEFAULT_FIRST_NOTICE_DATE);

        // Get all the courtCaseList where firstNoticeDate is greater than SMALLER_FIRST_NOTICE_DATE
        defaultCourtCaseShouldBeFound("firstNoticeDate.greaterThan=" + SMALLER_FIRST_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySecondNoticeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where secondNoticeDate equals to DEFAULT_SECOND_NOTICE_DATE
        defaultCourtCaseShouldBeFound("secondNoticeDate.equals=" + DEFAULT_SECOND_NOTICE_DATE);

        // Get all the courtCaseList where secondNoticeDate equals to UPDATED_SECOND_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("secondNoticeDate.equals=" + UPDATED_SECOND_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySecondNoticeDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where secondNoticeDate in DEFAULT_SECOND_NOTICE_DATE or UPDATED_SECOND_NOTICE_DATE
        defaultCourtCaseShouldBeFound("secondNoticeDate.in=" + DEFAULT_SECOND_NOTICE_DATE + "," + UPDATED_SECOND_NOTICE_DATE);

        // Get all the courtCaseList where secondNoticeDate equals to UPDATED_SECOND_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("secondNoticeDate.in=" + UPDATED_SECOND_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySecondNoticeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where secondNoticeDate is not null
        defaultCourtCaseShouldBeFound("secondNoticeDate.specified=true");

        // Get all the courtCaseList where secondNoticeDate is null
        defaultCourtCaseShouldNotBeFound("secondNoticeDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySecondNoticeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where secondNoticeDate is greater than or equal to DEFAULT_SECOND_NOTICE_DATE
        defaultCourtCaseShouldBeFound("secondNoticeDate.greaterThanOrEqual=" + DEFAULT_SECOND_NOTICE_DATE);

        // Get all the courtCaseList where secondNoticeDate is greater than or equal to UPDATED_SECOND_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("secondNoticeDate.greaterThanOrEqual=" + UPDATED_SECOND_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySecondNoticeDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where secondNoticeDate is less than or equal to DEFAULT_SECOND_NOTICE_DATE
        defaultCourtCaseShouldBeFound("secondNoticeDate.lessThanOrEqual=" + DEFAULT_SECOND_NOTICE_DATE);

        // Get all the courtCaseList where secondNoticeDate is less than or equal to SMALLER_SECOND_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("secondNoticeDate.lessThanOrEqual=" + SMALLER_SECOND_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySecondNoticeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where secondNoticeDate is less than DEFAULT_SECOND_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("secondNoticeDate.lessThan=" + DEFAULT_SECOND_NOTICE_DATE);

        // Get all the courtCaseList where secondNoticeDate is less than UPDATED_SECOND_NOTICE_DATE
        defaultCourtCaseShouldBeFound("secondNoticeDate.lessThan=" + UPDATED_SECOND_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySecondNoticeDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where secondNoticeDate is greater than DEFAULT_SECOND_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("secondNoticeDate.greaterThan=" + DEFAULT_SECOND_NOTICE_DATE);

        // Get all the courtCaseList where secondNoticeDate is greater than SMALLER_SECOND_NOTICE_DATE
        defaultCourtCaseShouldBeFound("secondNoticeDate.greaterThan=" + SMALLER_SECOND_NOTICE_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourtCaseShouldBeFound(String filter) throws Exception {
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].nameOfDefaulter").value(hasItem(DEFAULT_NAME_OF_DEFAULTER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].loanType").value(hasItem(DEFAULT_LOAN_TYPE)))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanDate").value(hasItem(DEFAULT_LOAN_DATE.toString())))
            .andExpect(jsonPath("$.[*].termOfLoan").value(hasItem(DEFAULT_TERM_OF_LOAN)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].installmentAmount").value(hasItem(DEFAULT_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCredit").value(hasItem(DEFAULT_TOTAL_CREDIT.doubleValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestPaid").value(hasItem(DEFAULT_INTEREST_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].penalInterestPaid").value(hasItem(DEFAULT_PENAL_INTEREST_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].dueAmount").value(hasItem(DEFAULT_DUE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueInterest").value(hasItem(DEFAULT_DUE_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].duePenalInterest").value(hasItem(DEFAULT_DUE_PENAL_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].dueMoreInterest").value(hasItem(DEFAULT_DUE_MORE_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRecivable").value(hasItem(DEFAULT_INTEREST_RECIVABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].gaurentorOne").value(hasItem(DEFAULT_GAURENTOR_ONE)))
            .andExpect(jsonPath("$.[*].gaurentorOneAddress").value(hasItem(DEFAULT_GAURENTOR_ONE_ADDRESS)))
            .andExpect(jsonPath("$.[*].gaurentorTwo").value(hasItem(DEFAULT_GAURENTOR_TWO)))
            .andExpect(jsonPath("$.[*].gaurentorTwoAddress").value(hasItem(DEFAULT_GAURENTOR_TWO_ADDRESS)))
            .andExpect(jsonPath("$.[*].firstNoticeDate").value(hasItem(DEFAULT_FIRST_NOTICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].secondNoticeDate").value(hasItem(DEFAULT_SECOND_NOTICE_DATE.toString())));

        // Check, that the count call also returns 1
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourtCaseShouldNotBeFound(String filter) throws Exception {
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourtCase() throws Exception {
        // Get the courtCase
        restCourtCaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase
        CourtCase updatedCourtCase = courtCaseRepository.findById(courtCase.getId()).get();
        // Disconnect from session so that the updates on updatedCourtCase are not directly saved in db
        em.detach(updatedCourtCase);
        updatedCourtCase
            .srNo(UPDATED_SR_NO)
            .accountNo(UPDATED_ACCOUNT_NO)
            .nameOfDefaulter(UPDATED_NAME_OF_DEFAULTER)
            .address(UPDATED_ADDRESS)
            .loanType(UPDATED_LOAN_TYPE)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanDate(UPDATED_LOAN_DATE)
            .termOfLoan(UPDATED_TERM_OF_LOAN)
            .interestRate(UPDATED_INTEREST_RATE)
            .installmentAmount(UPDATED_INSTALLMENT_AMOUNT)
            .totalCredit(UPDATED_TOTAL_CREDIT)
            .balance(UPDATED_BALANCE)
            .interestPaid(UPDATED_INTEREST_PAID)
            .penalInterestPaid(UPDATED_PENAL_INTEREST_PAID)
            .dueAmount(UPDATED_DUE_AMOUNT)
            .dueDate(UPDATED_DUE_DATE)
            .dueInterest(UPDATED_DUE_INTEREST)
            .duePenalInterest(UPDATED_DUE_PENAL_INTEREST)
            .dueMoreInterest(UPDATED_DUE_MORE_INTEREST)
            .interestRecivable(UPDATED_INTEREST_RECIVABLE)
            .gaurentorOne(UPDATED_GAURENTOR_ONE)
            .gaurentorOneAddress(UPDATED_GAURENTOR_ONE_ADDRESS)
            .gaurentorTwo(UPDATED_GAURENTOR_TWO)
            .gaurentorTwoAddress(UPDATED_GAURENTOR_TWO_ADDRESS)
            .firstNoticeDate(UPDATED_FIRST_NOTICE_DATE)
            .secondNoticeDate(UPDATED_SECOND_NOTICE_DATE);

        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCourtCase.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getSrNo()).isEqualTo(UPDATED_SR_NO);
        assertThat(testCourtCase.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testCourtCase.getNameOfDefaulter()).isEqualTo(UPDATED_NAME_OF_DEFAULTER);
        assertThat(testCourtCase.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCourtCase.getLoanType()).isEqualTo(UPDATED_LOAN_TYPE);
        assertThat(testCourtCase.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testCourtCase.getLoanDate()).isEqualTo(UPDATED_LOAN_DATE);
        assertThat(testCourtCase.getTermOfLoan()).isEqualTo(UPDATED_TERM_OF_LOAN);
        assertThat(testCourtCase.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testCourtCase.getInstallmentAmount()).isEqualTo(UPDATED_INSTALLMENT_AMOUNT);
        assertThat(testCourtCase.getTotalCredit()).isEqualTo(UPDATED_TOTAL_CREDIT);
        assertThat(testCourtCase.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testCourtCase.getInterestPaid()).isEqualTo(UPDATED_INTEREST_PAID);
        assertThat(testCourtCase.getPenalInterestPaid()).isEqualTo(UPDATED_PENAL_INTEREST_PAID);
        assertThat(testCourtCase.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
        assertThat(testCourtCase.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCourtCase.getDueInterest()).isEqualTo(UPDATED_DUE_INTEREST);
        assertThat(testCourtCase.getDuePenalInterest()).isEqualTo(UPDATED_DUE_PENAL_INTEREST);
        assertThat(testCourtCase.getDueMoreInterest()).isEqualTo(UPDATED_DUE_MORE_INTEREST);
        assertThat(testCourtCase.getInterestRecivable()).isEqualTo(UPDATED_INTEREST_RECIVABLE);
        assertThat(testCourtCase.getGaurentorOne()).isEqualTo(UPDATED_GAURENTOR_ONE);
        assertThat(testCourtCase.getGaurentorOneAddress()).isEqualTo(UPDATED_GAURENTOR_ONE_ADDRESS);
        assertThat(testCourtCase.getGaurentorTwo()).isEqualTo(UPDATED_GAURENTOR_TWO);
        assertThat(testCourtCase.getGaurentorTwoAddress()).isEqualTo(UPDATED_GAURENTOR_TWO_ADDRESS);
        assertThat(testCourtCase.getFirstNoticeDate()).isEqualTo(UPDATED_FIRST_NOTICE_DATE);
        assertThat(testCourtCase.getSecondNoticeDate()).isEqualTo(UPDATED_SECOND_NOTICE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courtCase.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCase)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourtCaseWithPatch() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase using partial update
        CourtCase partialUpdatedCourtCase = new CourtCase();
        partialUpdatedCourtCase.setId(courtCase.getId());

        partialUpdatedCourtCase
            .address(UPDATED_ADDRESS)
            .interestRate(UPDATED_INTEREST_RATE)
            .installmentAmount(UPDATED_INSTALLMENT_AMOUNT)
            .totalCredit(UPDATED_TOTAL_CREDIT)
            .balance(UPDATED_BALANCE)
            .dueAmount(UPDATED_DUE_AMOUNT)
            .dueMoreInterest(UPDATED_DUE_MORE_INTEREST)
            .interestRecivable(UPDATED_INTEREST_RECIVABLE)
            .gaurentorOne(UPDATED_GAURENTOR_ONE)
            .gaurentorTwoAddress(UPDATED_GAURENTOR_TWO_ADDRESS)
            .firstNoticeDate(UPDATED_FIRST_NOTICE_DATE);

        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getSrNo()).isEqualTo(DEFAULT_SR_NO);
        assertThat(testCourtCase.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testCourtCase.getNameOfDefaulter()).isEqualTo(DEFAULT_NAME_OF_DEFAULTER);
        assertThat(testCourtCase.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCourtCase.getLoanType()).isEqualTo(DEFAULT_LOAN_TYPE);
        assertThat(testCourtCase.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testCourtCase.getLoanDate()).isEqualTo(DEFAULT_LOAN_DATE);
        assertThat(testCourtCase.getTermOfLoan()).isEqualTo(DEFAULT_TERM_OF_LOAN);
        assertThat(testCourtCase.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testCourtCase.getInstallmentAmount()).isEqualTo(UPDATED_INSTALLMENT_AMOUNT);
        assertThat(testCourtCase.getTotalCredit()).isEqualTo(UPDATED_TOTAL_CREDIT);
        assertThat(testCourtCase.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testCourtCase.getInterestPaid()).isEqualTo(DEFAULT_INTEREST_PAID);
        assertThat(testCourtCase.getPenalInterestPaid()).isEqualTo(DEFAULT_PENAL_INTEREST_PAID);
        assertThat(testCourtCase.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
        assertThat(testCourtCase.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCourtCase.getDueInterest()).isEqualTo(DEFAULT_DUE_INTEREST);
        assertThat(testCourtCase.getDuePenalInterest()).isEqualTo(DEFAULT_DUE_PENAL_INTEREST);
        assertThat(testCourtCase.getDueMoreInterest()).isEqualTo(UPDATED_DUE_MORE_INTEREST);
        assertThat(testCourtCase.getInterestRecivable()).isEqualTo(UPDATED_INTEREST_RECIVABLE);
        assertThat(testCourtCase.getGaurentorOne()).isEqualTo(UPDATED_GAURENTOR_ONE);
        assertThat(testCourtCase.getGaurentorOneAddress()).isEqualTo(DEFAULT_GAURENTOR_ONE_ADDRESS);
        assertThat(testCourtCase.getGaurentorTwo()).isEqualTo(DEFAULT_GAURENTOR_TWO);
        assertThat(testCourtCase.getGaurentorTwoAddress()).isEqualTo(UPDATED_GAURENTOR_TWO_ADDRESS);
        assertThat(testCourtCase.getFirstNoticeDate()).isEqualTo(UPDATED_FIRST_NOTICE_DATE);
        assertThat(testCourtCase.getSecondNoticeDate()).isEqualTo(DEFAULT_SECOND_NOTICE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCourtCaseWithPatch() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase using partial update
        CourtCase partialUpdatedCourtCase = new CourtCase();
        partialUpdatedCourtCase.setId(courtCase.getId());

        partialUpdatedCourtCase
            .srNo(UPDATED_SR_NO)
            .accountNo(UPDATED_ACCOUNT_NO)
            .nameOfDefaulter(UPDATED_NAME_OF_DEFAULTER)
            .address(UPDATED_ADDRESS)
            .loanType(UPDATED_LOAN_TYPE)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanDate(UPDATED_LOAN_DATE)
            .termOfLoan(UPDATED_TERM_OF_LOAN)
            .interestRate(UPDATED_INTEREST_RATE)
            .installmentAmount(UPDATED_INSTALLMENT_AMOUNT)
            .totalCredit(UPDATED_TOTAL_CREDIT)
            .balance(UPDATED_BALANCE)
            .interestPaid(UPDATED_INTEREST_PAID)
            .penalInterestPaid(UPDATED_PENAL_INTEREST_PAID)
            .dueAmount(UPDATED_DUE_AMOUNT)
            .dueDate(UPDATED_DUE_DATE)
            .dueInterest(UPDATED_DUE_INTEREST)
            .duePenalInterest(UPDATED_DUE_PENAL_INTEREST)
            .dueMoreInterest(UPDATED_DUE_MORE_INTEREST)
            .interestRecivable(UPDATED_INTEREST_RECIVABLE)
            .gaurentorOne(UPDATED_GAURENTOR_ONE)
            .gaurentorOneAddress(UPDATED_GAURENTOR_ONE_ADDRESS)
            .gaurentorTwo(UPDATED_GAURENTOR_TWO)
            .gaurentorTwoAddress(UPDATED_GAURENTOR_TWO_ADDRESS)
            .firstNoticeDate(UPDATED_FIRST_NOTICE_DATE)
            .secondNoticeDate(UPDATED_SECOND_NOTICE_DATE);

        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getSrNo()).isEqualTo(UPDATED_SR_NO);
        assertThat(testCourtCase.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testCourtCase.getNameOfDefaulter()).isEqualTo(UPDATED_NAME_OF_DEFAULTER);
        assertThat(testCourtCase.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCourtCase.getLoanType()).isEqualTo(UPDATED_LOAN_TYPE);
        assertThat(testCourtCase.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testCourtCase.getLoanDate()).isEqualTo(UPDATED_LOAN_DATE);
        assertThat(testCourtCase.getTermOfLoan()).isEqualTo(UPDATED_TERM_OF_LOAN);
        assertThat(testCourtCase.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testCourtCase.getInstallmentAmount()).isEqualTo(UPDATED_INSTALLMENT_AMOUNT);
        assertThat(testCourtCase.getTotalCredit()).isEqualTo(UPDATED_TOTAL_CREDIT);
        assertThat(testCourtCase.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testCourtCase.getInterestPaid()).isEqualTo(UPDATED_INTEREST_PAID);
        assertThat(testCourtCase.getPenalInterestPaid()).isEqualTo(UPDATED_PENAL_INTEREST_PAID);
        assertThat(testCourtCase.getDueAmount()).isEqualTo(UPDATED_DUE_AMOUNT);
        assertThat(testCourtCase.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCourtCase.getDueInterest()).isEqualTo(UPDATED_DUE_INTEREST);
        assertThat(testCourtCase.getDuePenalInterest()).isEqualTo(UPDATED_DUE_PENAL_INTEREST);
        assertThat(testCourtCase.getDueMoreInterest()).isEqualTo(UPDATED_DUE_MORE_INTEREST);
        assertThat(testCourtCase.getInterestRecivable()).isEqualTo(UPDATED_INTEREST_RECIVABLE);
        assertThat(testCourtCase.getGaurentorOne()).isEqualTo(UPDATED_GAURENTOR_ONE);
        assertThat(testCourtCase.getGaurentorOneAddress()).isEqualTo(UPDATED_GAURENTOR_ONE_ADDRESS);
        assertThat(testCourtCase.getGaurentorTwo()).isEqualTo(UPDATED_GAURENTOR_TWO);
        assertThat(testCourtCase.getGaurentorTwoAddress()).isEqualTo(UPDATED_GAURENTOR_TWO_ADDRESS);
        assertThat(testCourtCase.getFirstNoticeDate()).isEqualTo(UPDATED_FIRST_NOTICE_DATE);
        assertThat(testCourtCase.getSecondNoticeDate()).isEqualTo(UPDATED_SECOND_NOTICE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeDelete = courtCaseRepository.findAll().size();

        // Delete the courtCase
        restCourtCaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, courtCase.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
