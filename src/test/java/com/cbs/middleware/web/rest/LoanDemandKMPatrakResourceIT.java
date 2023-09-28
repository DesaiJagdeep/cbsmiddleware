package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.LoanDemandKMPatrak;
import com.cbs.middleware.repository.LoanDemandKMPatrakRepository;
import com.cbs.middleware.service.criteria.LoanDemandKMPatrakCriteria;
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
 * Integration tests for the {@link LoanDemandKMPatrakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanDemandKMPatrakResourceIT {

    private static final String DEFAULT_DEMAND_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_KM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_KM_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_KM_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_SHARES = "AAAAAAAAAA";
    private static final String UPDATED_SHARES = "BBBBBBBBBB";

    private static final String DEFAULT_PID = "AAAAAAAAAA";
    private static final String UPDATED_PID = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DEMAND_AREA = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CROP_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK = "AAAAAAAAAA";
    private static final String UPDATED_CHECK = "BBBBBBBBBB";

    private static final String DEFAULT_GOODS = "AAAAAAAAAA";
    private static final String UPDATED_GOODS = "BBBBBBBBBB";

    private static final String DEFAULT_SHARESN = "AAAAAAAAAA";
    private static final String UPDATED_SHARESN = "BBBBBBBBBB";

    private static final String DEFAULT_HN = "AAAAAAAAAA";
    private static final String UPDATED_HN = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_H_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_H_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KHATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_KHATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REMAINING = "AAAAAAAAAA";
    private static final String UPDATED_REMAINING = "BBBBBBBBBB";

    private static final String DEFAULT_ARREARS = "AAAAAAAAAA";
    private static final String UPDATED_ARREARS = "BBBBBBBBBB";

    private static final String DEFAULT_KM_ACCEPTANCE = "AAAAAAAAAA";
    private static final String UPDATED_KM_ACCEPTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_PAID_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PAID_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_KM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_KM_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PENDING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PENDING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PENDING_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DEPOSITE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEPOSITE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DEPOSITE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ACCOUNT_NUMBER_B = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER_B = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_DUE = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_DUE = "BBBBBBBBBB";

    private static final String DEFAULT_ARREARS_B = "AAAAAAAAAA";
    private static final String UPDATED_ARREARS_B = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DUE_DATE_B = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE_B = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE_B = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CROP_B = "AAAAAAAAAA";
    private static final String UPDATED_CROP_B = "BBBBBBBBBB";

    private static final String DEFAULT_KM_ACCEPTANCE_B = "AAAAAAAAAA";
    private static final String UPDATED_KM_ACCEPTANCE_B = "BBBBBBBBBB";

    private static final String DEFAULT_KM_CODE_B = "AAAAAAAAAA";
    private static final String UPDATED_KM_CODE_B = "BBBBBBBBBB";

    private static final String DEFAULT_H_AGREEMENT_NUMBER_B = "AAAAAAAAAA";
    private static final String UPDATED_H_AGREEMENT_NUMBER_B = "BBBBBBBBBB";

    private static final String DEFAULT_H_AGREEMENT_AREA_B = "AAAAAAAAAA";
    private static final String UPDATED_H_AGREEMENT_AREA_B = "BBBBBBBBBB";

    private static final String DEFAULT_H_AGREEMENT_BURDEN_B = "AAAAAAAAAA";
    private static final String UPDATED_H_AGREEMENT_BURDEN_B = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_PAID_B = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_PAID_B = "BBBBBBBBBB";

    private static final String DEFAULT_DEMAND_AREA_B = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND_AREA_B = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B = "BBBBBBBBBB";

    private static final String DEFAULT_SHARES_B = "AAAAAAAAAA";
    private static final String UPDATED_SHARES_B = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VASUL_PATRA_REPAYMENT_DATE_B = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VASUL_PATRA_REPAYMENT_DATE_B = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/loan-demand-km-patraks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanDemandKMPatrakRepository loanDemandKMPatrakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanDemandKMPatrakMockMvc;

    private LoanDemandKMPatrak loanDemandKMPatrak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDemandKMPatrak createEntity(EntityManager em) {
        LoanDemandKMPatrak loanDemandKMPatrak = new LoanDemandKMPatrak()
            .demandCode(DEFAULT_DEMAND_CODE)
            .date(DEFAULT_DATE)
            .kmDate(DEFAULT_KM_DATE)
            .shares(DEFAULT_SHARES)
            .pid(DEFAULT_PID)
            .code(DEFAULT_CODE)
            .demandArea(DEFAULT_DEMAND_AREA)
            .cropType(DEFAULT_CROP_TYPE)
            .total(DEFAULT_TOTAL)
            .check(DEFAULT_CHECK)
            .goods(DEFAULT_GOODS)
            .sharesn(DEFAULT_SHARESN)
            .hn(DEFAULT_HN)
            .area(DEFAULT_AREA)
            .hAmount(DEFAULT_H_AMOUNT)
            .name(DEFAULT_NAME)
            .khateCode(DEFAULT_KHATE_CODE)
            .remaining(DEFAULT_REMAINING)
            .arrears(DEFAULT_ARREARS)
            .kmAcceptance(DEFAULT_KM_ACCEPTANCE)
            .paidDate(DEFAULT_PAID_DATE)
            .kmCode(DEFAULT_KM_CODE)
            .pendingDate(DEFAULT_PENDING_DATE)
            .depositeDate(DEFAULT_DEPOSITE_DATE)
            .accountNumberB(DEFAULT_ACCOUNT_NUMBER_B)
            .loanDue(DEFAULT_LOAN_DUE)
            .arrearsB(DEFAULT_ARREARS_B)
            .dueDateB(DEFAULT_DUE_DATE_B)
            .cropB(DEFAULT_CROP_B)
            .kmAcceptanceB(DEFAULT_KM_ACCEPTANCE_B)
            .kmCodeB(DEFAULT_KM_CODE_B)
            .hAgreementNumberB(DEFAULT_H_AGREEMENT_NUMBER_B)
            .hAgreementAreaB(DEFAULT_H_AGREEMENT_AREA_B)
            .hAgreementBurdenB(DEFAULT_H_AGREEMENT_BURDEN_B)
            .totalPaidB(DEFAULT_TOTAL_PAID_B)
            .demandAreaB(DEFAULT_DEMAND_AREA_B)
            .checkInTheFormOfPaymentB(DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B)
            .sharesB(DEFAULT_SHARES_B)
            .vasulPatraRepaymentDateB(DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B);
        return loanDemandKMPatrak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDemandKMPatrak createUpdatedEntity(EntityManager em) {
        LoanDemandKMPatrak loanDemandKMPatrak = new LoanDemandKMPatrak()
            .demandCode(UPDATED_DEMAND_CODE)
            .date(UPDATED_DATE)
            .kmDate(UPDATED_KM_DATE)
            .shares(UPDATED_SHARES)
            .pid(UPDATED_PID)
            .code(UPDATED_CODE)
            .demandArea(UPDATED_DEMAND_AREA)
            .cropType(UPDATED_CROP_TYPE)
            .total(UPDATED_TOTAL)
            .check(UPDATED_CHECK)
            .goods(UPDATED_GOODS)
            .sharesn(UPDATED_SHARESN)
            .hn(UPDATED_HN)
            .area(UPDATED_AREA)
            .hAmount(UPDATED_H_AMOUNT)
            .name(UPDATED_NAME)
            .khateCode(UPDATED_KHATE_CODE)
            .remaining(UPDATED_REMAINING)
            .arrears(UPDATED_ARREARS)
            .kmAcceptance(UPDATED_KM_ACCEPTANCE)
            .paidDate(UPDATED_PAID_DATE)
            .kmCode(UPDATED_KM_CODE)
            .pendingDate(UPDATED_PENDING_DATE)
            .depositeDate(UPDATED_DEPOSITE_DATE)
            .accountNumberB(UPDATED_ACCOUNT_NUMBER_B)
            .loanDue(UPDATED_LOAN_DUE)
            .arrearsB(UPDATED_ARREARS_B)
            .dueDateB(UPDATED_DUE_DATE_B)
            .cropB(UPDATED_CROP_B)
            .kmAcceptanceB(UPDATED_KM_ACCEPTANCE_B)
            .kmCodeB(UPDATED_KM_CODE_B)
            .hAgreementNumberB(UPDATED_H_AGREEMENT_NUMBER_B)
            .hAgreementAreaB(UPDATED_H_AGREEMENT_AREA_B)
            .hAgreementBurdenB(UPDATED_H_AGREEMENT_BURDEN_B)
            .totalPaidB(UPDATED_TOTAL_PAID_B)
            .demandAreaB(UPDATED_DEMAND_AREA_B)
            .checkInTheFormOfPaymentB(UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B)
            .sharesB(UPDATED_SHARES_B)
            .vasulPatraRepaymentDateB(UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);
        return loanDemandKMPatrak;
    }

    @BeforeEach
    public void initTest() {
        loanDemandKMPatrak = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanDemandKMPatrak() throws Exception {
        int databaseSizeBeforeCreate = loanDemandKMPatrakRepository.findAll().size();
        // Create the LoanDemandKMPatrak
        restLoanDemandKMPatrakMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDemandKMPatrak))
            )
            .andExpect(status().isCreated());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeCreate + 1);
        LoanDemandKMPatrak testLoanDemandKMPatrak = loanDemandKMPatrakList.get(loanDemandKMPatrakList.size() - 1);
        assertThat(testLoanDemandKMPatrak.getDemandCode()).isEqualTo(DEFAULT_DEMAND_CODE);
        assertThat(testLoanDemandKMPatrak.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testLoanDemandKMPatrak.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
        assertThat(testLoanDemandKMPatrak.getShares()).isEqualTo(DEFAULT_SHARES);
        assertThat(testLoanDemandKMPatrak.getPid()).isEqualTo(DEFAULT_PID);
        assertThat(testLoanDemandKMPatrak.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLoanDemandKMPatrak.getDemandArea()).isEqualTo(DEFAULT_DEMAND_AREA);
        assertThat(testLoanDemandKMPatrak.getCropType()).isEqualTo(DEFAULT_CROP_TYPE);
        assertThat(testLoanDemandKMPatrak.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testLoanDemandKMPatrak.getCheck()).isEqualTo(DEFAULT_CHECK);
        assertThat(testLoanDemandKMPatrak.getGoods()).isEqualTo(DEFAULT_GOODS);
        assertThat(testLoanDemandKMPatrak.getSharesn()).isEqualTo(DEFAULT_SHARESN);
        assertThat(testLoanDemandKMPatrak.getHn()).isEqualTo(DEFAULT_HN);
        assertThat(testLoanDemandKMPatrak.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testLoanDemandKMPatrak.gethAmount()).isEqualTo(DEFAULT_H_AMOUNT);
        assertThat(testLoanDemandKMPatrak.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLoanDemandKMPatrak.getKhateCode()).isEqualTo(DEFAULT_KHATE_CODE);
        assertThat(testLoanDemandKMPatrak.getRemaining()).isEqualTo(DEFAULT_REMAINING);
        assertThat(testLoanDemandKMPatrak.getArrears()).isEqualTo(DEFAULT_ARREARS);
        assertThat(testLoanDemandKMPatrak.getKmAcceptance()).isEqualTo(DEFAULT_KM_ACCEPTANCE);
        assertThat(testLoanDemandKMPatrak.getPaidDate()).isEqualTo(DEFAULT_PAID_DATE);
        assertThat(testLoanDemandKMPatrak.getKmCode()).isEqualTo(DEFAULT_KM_CODE);
        assertThat(testLoanDemandKMPatrak.getPendingDate()).isEqualTo(DEFAULT_PENDING_DATE);
        assertThat(testLoanDemandKMPatrak.getDepositeDate()).isEqualTo(DEFAULT_DEPOSITE_DATE);
        assertThat(testLoanDemandKMPatrak.getAccountNumberB()).isEqualTo(DEFAULT_ACCOUNT_NUMBER_B);
        assertThat(testLoanDemandKMPatrak.getLoanDue()).isEqualTo(DEFAULT_LOAN_DUE);
        assertThat(testLoanDemandKMPatrak.getArrearsB()).isEqualTo(DEFAULT_ARREARS_B);
        assertThat(testLoanDemandKMPatrak.getDueDateB()).isEqualTo(DEFAULT_DUE_DATE_B);
        assertThat(testLoanDemandKMPatrak.getCropB()).isEqualTo(DEFAULT_CROP_B);
        assertThat(testLoanDemandKMPatrak.getKmAcceptanceB()).isEqualTo(DEFAULT_KM_ACCEPTANCE_B);
        assertThat(testLoanDemandKMPatrak.getKmCodeB()).isEqualTo(DEFAULT_KM_CODE_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementNumberB()).isEqualTo(DEFAULT_H_AGREEMENT_NUMBER_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementAreaB()).isEqualTo(DEFAULT_H_AGREEMENT_AREA_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementBurdenB()).isEqualTo(DEFAULT_H_AGREEMENT_BURDEN_B);
        assertThat(testLoanDemandKMPatrak.getTotalPaidB()).isEqualTo(DEFAULT_TOTAL_PAID_B);
        assertThat(testLoanDemandKMPatrak.getDemandAreaB()).isEqualTo(DEFAULT_DEMAND_AREA_B);
        assertThat(testLoanDemandKMPatrak.getCheckInTheFormOfPaymentB()).isEqualTo(DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B);
        assertThat(testLoanDemandKMPatrak.getSharesB()).isEqualTo(DEFAULT_SHARES_B);
        assertThat(testLoanDemandKMPatrak.getVasulPatraRepaymentDateB()).isEqualTo(DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    @Test
    @Transactional
    void createLoanDemandKMPatrakWithExistingId() throws Exception {
        // Create the LoanDemandKMPatrak with an existing ID
        loanDemandKMPatrak.setId(1L);

        int databaseSizeBeforeCreate = loanDemandKMPatrakRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanDemandKMPatrakMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDemandKMPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraks() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList
        restLoanDemandKMPatrakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDemandKMPatrak.getId().intValue())))
            .andExpect(jsonPath("$.[*].demandCode").value(hasItem(DEFAULT_DEMAND_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].shares").value(hasItem(DEFAULT_SHARES)))
            .andExpect(jsonPath("$.[*].pid").value(hasItem(DEFAULT_PID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].demandArea").value(hasItem(DEFAULT_DEMAND_AREA)))
            .andExpect(jsonPath("$.[*].cropType").value(hasItem(DEFAULT_CROP_TYPE)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].check").value(hasItem(DEFAULT_CHECK)))
            .andExpect(jsonPath("$.[*].goods").value(hasItem(DEFAULT_GOODS)))
            .andExpect(jsonPath("$.[*].sharesn").value(hasItem(DEFAULT_SHARESN)))
            .andExpect(jsonPath("$.[*].hn").value(hasItem(DEFAULT_HN)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].hAmount").value(hasItem(DEFAULT_H_AMOUNT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].khateCode").value(hasItem(DEFAULT_KHATE_CODE)))
            .andExpect(jsonPath("$.[*].remaining").value(hasItem(DEFAULT_REMAINING)))
            .andExpect(jsonPath("$.[*].arrears").value(hasItem(DEFAULT_ARREARS)))
            .andExpect(jsonPath("$.[*].kmAcceptance").value(hasItem(DEFAULT_KM_ACCEPTANCE)))
            .andExpect(jsonPath("$.[*].paidDate").value(hasItem(DEFAULT_PAID_DATE)))
            .andExpect(jsonPath("$.[*].kmCode").value(hasItem(DEFAULT_KM_CODE)))
            .andExpect(jsonPath("$.[*].pendingDate").value(hasItem(DEFAULT_PENDING_DATE.toString())))
            .andExpect(jsonPath("$.[*].depositeDate").value(hasItem(DEFAULT_DEPOSITE_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountNumberB").value(hasItem(DEFAULT_ACCOUNT_NUMBER_B)))
            .andExpect(jsonPath("$.[*].loanDue").value(hasItem(DEFAULT_LOAN_DUE)))
            .andExpect(jsonPath("$.[*].arrearsB").value(hasItem(DEFAULT_ARREARS_B)))
            .andExpect(jsonPath("$.[*].dueDateB").value(hasItem(DEFAULT_DUE_DATE_B.toString())))
            .andExpect(jsonPath("$.[*].cropB").value(hasItem(DEFAULT_CROP_B)))
            .andExpect(jsonPath("$.[*].kmAcceptanceB").value(hasItem(DEFAULT_KM_ACCEPTANCE_B)))
            .andExpect(jsonPath("$.[*].kmCodeB").value(hasItem(DEFAULT_KM_CODE_B)))
            .andExpect(jsonPath("$.[*].hAgreementNumberB").value(hasItem(DEFAULT_H_AGREEMENT_NUMBER_B)))
            .andExpect(jsonPath("$.[*].hAgreementAreaB").value(hasItem(DEFAULT_H_AGREEMENT_AREA_B)))
            .andExpect(jsonPath("$.[*].hAgreementBurdenB").value(hasItem(DEFAULT_H_AGREEMENT_BURDEN_B)))
            .andExpect(jsonPath("$.[*].totalPaidB").value(hasItem(DEFAULT_TOTAL_PAID_B)))
            .andExpect(jsonPath("$.[*].demandAreaB").value(hasItem(DEFAULT_DEMAND_AREA_B)))
            .andExpect(jsonPath("$.[*].checkInTheFormOfPaymentB").value(hasItem(DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B)))
            .andExpect(jsonPath("$.[*].sharesB").value(hasItem(DEFAULT_SHARES_B)))
            .andExpect(jsonPath("$.[*].vasulPatraRepaymentDateB").value(hasItem(DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B.toString())));
    }

    @Test
    @Transactional
    void getLoanDemandKMPatrak() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get the loanDemandKMPatrak
        restLoanDemandKMPatrakMockMvc
            .perform(get(ENTITY_API_URL_ID, loanDemandKMPatrak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanDemandKMPatrak.getId().intValue()))
            .andExpect(jsonPath("$.demandCode").value(DEFAULT_DEMAND_CODE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.kmDate").value(DEFAULT_KM_DATE.toString()))
            .andExpect(jsonPath("$.shares").value(DEFAULT_SHARES))
            .andExpect(jsonPath("$.pid").value(DEFAULT_PID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.demandArea").value(DEFAULT_DEMAND_AREA))
            .andExpect(jsonPath("$.cropType").value(DEFAULT_CROP_TYPE))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.check").value(DEFAULT_CHECK))
            .andExpect(jsonPath("$.goods").value(DEFAULT_GOODS))
            .andExpect(jsonPath("$.sharesn").value(DEFAULT_SHARESN))
            .andExpect(jsonPath("$.hn").value(DEFAULT_HN))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.hAmount").value(DEFAULT_H_AMOUNT))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.khateCode").value(DEFAULT_KHATE_CODE))
            .andExpect(jsonPath("$.remaining").value(DEFAULT_REMAINING))
            .andExpect(jsonPath("$.arrears").value(DEFAULT_ARREARS))
            .andExpect(jsonPath("$.kmAcceptance").value(DEFAULT_KM_ACCEPTANCE))
            .andExpect(jsonPath("$.paidDate").value(DEFAULT_PAID_DATE))
            .andExpect(jsonPath("$.kmCode").value(DEFAULT_KM_CODE))
            .andExpect(jsonPath("$.pendingDate").value(DEFAULT_PENDING_DATE.toString()))
            .andExpect(jsonPath("$.depositeDate").value(DEFAULT_DEPOSITE_DATE.toString()))
            .andExpect(jsonPath("$.accountNumberB").value(DEFAULT_ACCOUNT_NUMBER_B))
            .andExpect(jsonPath("$.loanDue").value(DEFAULT_LOAN_DUE))
            .andExpect(jsonPath("$.arrearsB").value(DEFAULT_ARREARS_B))
            .andExpect(jsonPath("$.dueDateB").value(DEFAULT_DUE_DATE_B.toString()))
            .andExpect(jsonPath("$.cropB").value(DEFAULT_CROP_B))
            .andExpect(jsonPath("$.kmAcceptanceB").value(DEFAULT_KM_ACCEPTANCE_B))
            .andExpect(jsonPath("$.kmCodeB").value(DEFAULT_KM_CODE_B))
            .andExpect(jsonPath("$.hAgreementNumberB").value(DEFAULT_H_AGREEMENT_NUMBER_B))
            .andExpect(jsonPath("$.hAgreementAreaB").value(DEFAULT_H_AGREEMENT_AREA_B))
            .andExpect(jsonPath("$.hAgreementBurdenB").value(DEFAULT_H_AGREEMENT_BURDEN_B))
            .andExpect(jsonPath("$.totalPaidB").value(DEFAULT_TOTAL_PAID_B))
            .andExpect(jsonPath("$.demandAreaB").value(DEFAULT_DEMAND_AREA_B))
            .andExpect(jsonPath("$.checkInTheFormOfPaymentB").value(DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B))
            .andExpect(jsonPath("$.sharesB").value(DEFAULT_SHARES_B))
            .andExpect(jsonPath("$.vasulPatraRepaymentDateB").value(DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B.toString()));
    }

    @Test
    @Transactional
    void getLoanDemandKMPatraksByIdFiltering() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        Long id = loanDemandKMPatrak.getId();

        defaultLoanDemandKMPatrakShouldBeFound("id.equals=" + id);
        defaultLoanDemandKMPatrakShouldNotBeFound("id.notEquals=" + id);

        defaultLoanDemandKMPatrakShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanDemandKMPatrakShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanDemandKMPatrakShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanDemandKMPatrakShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandCode equals to DEFAULT_DEMAND_CODE
        defaultLoanDemandKMPatrakShouldBeFound("demandCode.equals=" + DEFAULT_DEMAND_CODE);

        // Get all the loanDemandKMPatrakList where demandCode equals to UPDATED_DEMAND_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("demandCode.equals=" + UPDATED_DEMAND_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandCode in DEFAULT_DEMAND_CODE or UPDATED_DEMAND_CODE
        defaultLoanDemandKMPatrakShouldBeFound("demandCode.in=" + DEFAULT_DEMAND_CODE + "," + UPDATED_DEMAND_CODE);

        // Get all the loanDemandKMPatrakList where demandCode equals to UPDATED_DEMAND_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("demandCode.in=" + UPDATED_DEMAND_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandCode is not null
        defaultLoanDemandKMPatrakShouldBeFound("demandCode.specified=true");

        // Get all the loanDemandKMPatrakList where demandCode is null
        defaultLoanDemandKMPatrakShouldNotBeFound("demandCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandCodeContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandCode contains DEFAULT_DEMAND_CODE
        defaultLoanDemandKMPatrakShouldBeFound("demandCode.contains=" + DEFAULT_DEMAND_CODE);

        // Get all the loanDemandKMPatrakList where demandCode contains UPDATED_DEMAND_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("demandCode.contains=" + UPDATED_DEMAND_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandCode does not contain DEFAULT_DEMAND_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("demandCode.doesNotContain=" + DEFAULT_DEMAND_CODE);

        // Get all the loanDemandKMPatrakList where demandCode does not contain UPDATED_DEMAND_CODE
        defaultLoanDemandKMPatrakShouldBeFound("demandCode.doesNotContain=" + UPDATED_DEMAND_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where date equals to DEFAULT_DATE
        defaultLoanDemandKMPatrakShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the loanDemandKMPatrakList where date equals to UPDATED_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where date in DEFAULT_DATE or UPDATED_DATE
        defaultLoanDemandKMPatrakShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the loanDemandKMPatrakList where date equals to UPDATED_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where date is not null
        defaultLoanDemandKMPatrakShouldBeFound("date.specified=true");

        // Get all the loanDemandKMPatrakList where date is null
        defaultLoanDemandKMPatrakShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where date is greater than or equal to DEFAULT_DATE
        defaultLoanDemandKMPatrakShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the loanDemandKMPatrakList where date is greater than or equal to UPDATED_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where date is less than or equal to DEFAULT_DATE
        defaultLoanDemandKMPatrakShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the loanDemandKMPatrakList where date is less than or equal to SMALLER_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where date is less than DEFAULT_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the loanDemandKMPatrakList where date is less than UPDATED_DATE
        defaultLoanDemandKMPatrakShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where date is greater than DEFAULT_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the loanDemandKMPatrakList where date is greater than SMALLER_DATE
        defaultLoanDemandKMPatrakShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmDate equals to DEFAULT_KM_DATE
        defaultLoanDemandKMPatrakShouldBeFound("kmDate.equals=" + DEFAULT_KM_DATE);

        // Get all the loanDemandKMPatrakList where kmDate equals to UPDATED_KM_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmDate.equals=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmDate in DEFAULT_KM_DATE or UPDATED_KM_DATE
        defaultLoanDemandKMPatrakShouldBeFound("kmDate.in=" + DEFAULT_KM_DATE + "," + UPDATED_KM_DATE);

        // Get all the loanDemandKMPatrakList where kmDate equals to UPDATED_KM_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmDate.in=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmDate is not null
        defaultLoanDemandKMPatrakShouldBeFound("kmDate.specified=true");

        // Get all the loanDemandKMPatrakList where kmDate is null
        defaultLoanDemandKMPatrakShouldNotBeFound("kmDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmDate is greater than or equal to DEFAULT_KM_DATE
        defaultLoanDemandKMPatrakShouldBeFound("kmDate.greaterThanOrEqual=" + DEFAULT_KM_DATE);

        // Get all the loanDemandKMPatrakList where kmDate is greater than or equal to UPDATED_KM_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmDate.greaterThanOrEqual=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmDate is less than or equal to DEFAULT_KM_DATE
        defaultLoanDemandKMPatrakShouldBeFound("kmDate.lessThanOrEqual=" + DEFAULT_KM_DATE);

        // Get all the loanDemandKMPatrakList where kmDate is less than or equal to SMALLER_KM_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmDate.lessThanOrEqual=" + SMALLER_KM_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmDateIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmDate is less than DEFAULT_KM_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmDate.lessThan=" + DEFAULT_KM_DATE);

        // Get all the loanDemandKMPatrakList where kmDate is less than UPDATED_KM_DATE
        defaultLoanDemandKMPatrakShouldBeFound("kmDate.lessThan=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmDate is greater than DEFAULT_KM_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmDate.greaterThan=" + DEFAULT_KM_DATE);

        // Get all the loanDemandKMPatrakList where kmDate is greater than SMALLER_KM_DATE
        defaultLoanDemandKMPatrakShouldBeFound("kmDate.greaterThan=" + SMALLER_KM_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where shares equals to DEFAULT_SHARES
        defaultLoanDemandKMPatrakShouldBeFound("shares.equals=" + DEFAULT_SHARES);

        // Get all the loanDemandKMPatrakList where shares equals to UPDATED_SHARES
        defaultLoanDemandKMPatrakShouldNotBeFound("shares.equals=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where shares in DEFAULT_SHARES or UPDATED_SHARES
        defaultLoanDemandKMPatrakShouldBeFound("shares.in=" + DEFAULT_SHARES + "," + UPDATED_SHARES);

        // Get all the loanDemandKMPatrakList where shares equals to UPDATED_SHARES
        defaultLoanDemandKMPatrakShouldNotBeFound("shares.in=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where shares is not null
        defaultLoanDemandKMPatrakShouldBeFound("shares.specified=true");

        // Get all the loanDemandKMPatrakList where shares is null
        defaultLoanDemandKMPatrakShouldNotBeFound("shares.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where shares contains DEFAULT_SHARES
        defaultLoanDemandKMPatrakShouldBeFound("shares.contains=" + DEFAULT_SHARES);

        // Get all the loanDemandKMPatrakList where shares contains UPDATED_SHARES
        defaultLoanDemandKMPatrakShouldNotBeFound("shares.contains=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where shares does not contain DEFAULT_SHARES
        defaultLoanDemandKMPatrakShouldNotBeFound("shares.doesNotContain=" + DEFAULT_SHARES);

        // Get all the loanDemandKMPatrakList where shares does not contain UPDATED_SHARES
        defaultLoanDemandKMPatrakShouldBeFound("shares.doesNotContain=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPidIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pid equals to DEFAULT_PID
        defaultLoanDemandKMPatrakShouldBeFound("pid.equals=" + DEFAULT_PID);

        // Get all the loanDemandKMPatrakList where pid equals to UPDATED_PID
        defaultLoanDemandKMPatrakShouldNotBeFound("pid.equals=" + UPDATED_PID);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPidIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pid in DEFAULT_PID or UPDATED_PID
        defaultLoanDemandKMPatrakShouldBeFound("pid.in=" + DEFAULT_PID + "," + UPDATED_PID);

        // Get all the loanDemandKMPatrakList where pid equals to UPDATED_PID
        defaultLoanDemandKMPatrakShouldNotBeFound("pid.in=" + UPDATED_PID);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPidIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pid is not null
        defaultLoanDemandKMPatrakShouldBeFound("pid.specified=true");

        // Get all the loanDemandKMPatrakList where pid is null
        defaultLoanDemandKMPatrakShouldNotBeFound("pid.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPidContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pid contains DEFAULT_PID
        defaultLoanDemandKMPatrakShouldBeFound("pid.contains=" + DEFAULT_PID);

        // Get all the loanDemandKMPatrakList where pid contains UPDATED_PID
        defaultLoanDemandKMPatrakShouldNotBeFound("pid.contains=" + UPDATED_PID);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPidNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pid does not contain DEFAULT_PID
        defaultLoanDemandKMPatrakShouldNotBeFound("pid.doesNotContain=" + DEFAULT_PID);

        // Get all the loanDemandKMPatrakList where pid does not contain UPDATED_PID
        defaultLoanDemandKMPatrakShouldBeFound("pid.doesNotContain=" + UPDATED_PID);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where code equals to DEFAULT_CODE
        defaultLoanDemandKMPatrakShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the loanDemandKMPatrakList where code equals to UPDATED_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where code in DEFAULT_CODE or UPDATED_CODE
        defaultLoanDemandKMPatrakShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the loanDemandKMPatrakList where code equals to UPDATED_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where code is not null
        defaultLoanDemandKMPatrakShouldBeFound("code.specified=true");

        // Get all the loanDemandKMPatrakList where code is null
        defaultLoanDemandKMPatrakShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCodeContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where code contains DEFAULT_CODE
        defaultLoanDemandKMPatrakShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the loanDemandKMPatrakList where code contains UPDATED_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where code does not contain DEFAULT_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the loanDemandKMPatrakList where code does not contain UPDATED_CODE
        defaultLoanDemandKMPatrakShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandArea equals to DEFAULT_DEMAND_AREA
        defaultLoanDemandKMPatrakShouldBeFound("demandArea.equals=" + DEFAULT_DEMAND_AREA);

        // Get all the loanDemandKMPatrakList where demandArea equals to UPDATED_DEMAND_AREA
        defaultLoanDemandKMPatrakShouldNotBeFound("demandArea.equals=" + UPDATED_DEMAND_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandArea in DEFAULT_DEMAND_AREA or UPDATED_DEMAND_AREA
        defaultLoanDemandKMPatrakShouldBeFound("demandArea.in=" + DEFAULT_DEMAND_AREA + "," + UPDATED_DEMAND_AREA);

        // Get all the loanDemandKMPatrakList where demandArea equals to UPDATED_DEMAND_AREA
        defaultLoanDemandKMPatrakShouldNotBeFound("demandArea.in=" + UPDATED_DEMAND_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandArea is not null
        defaultLoanDemandKMPatrakShouldBeFound("demandArea.specified=true");

        // Get all the loanDemandKMPatrakList where demandArea is null
        defaultLoanDemandKMPatrakShouldNotBeFound("demandArea.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandArea contains DEFAULT_DEMAND_AREA
        defaultLoanDemandKMPatrakShouldBeFound("demandArea.contains=" + DEFAULT_DEMAND_AREA);

        // Get all the loanDemandKMPatrakList where demandArea contains UPDATED_DEMAND_AREA
        defaultLoanDemandKMPatrakShouldNotBeFound("demandArea.contains=" + UPDATED_DEMAND_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandArea does not contain DEFAULT_DEMAND_AREA
        defaultLoanDemandKMPatrakShouldNotBeFound("demandArea.doesNotContain=" + DEFAULT_DEMAND_AREA);

        // Get all the loanDemandKMPatrakList where demandArea does not contain UPDATED_DEMAND_AREA
        defaultLoanDemandKMPatrakShouldBeFound("demandArea.doesNotContain=" + UPDATED_DEMAND_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropType equals to DEFAULT_CROP_TYPE
        defaultLoanDemandKMPatrakShouldBeFound("cropType.equals=" + DEFAULT_CROP_TYPE);

        // Get all the loanDemandKMPatrakList where cropType equals to UPDATED_CROP_TYPE
        defaultLoanDemandKMPatrakShouldNotBeFound("cropType.equals=" + UPDATED_CROP_TYPE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropTypeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropType in DEFAULT_CROP_TYPE or UPDATED_CROP_TYPE
        defaultLoanDemandKMPatrakShouldBeFound("cropType.in=" + DEFAULT_CROP_TYPE + "," + UPDATED_CROP_TYPE);

        // Get all the loanDemandKMPatrakList where cropType equals to UPDATED_CROP_TYPE
        defaultLoanDemandKMPatrakShouldNotBeFound("cropType.in=" + UPDATED_CROP_TYPE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropType is not null
        defaultLoanDemandKMPatrakShouldBeFound("cropType.specified=true");

        // Get all the loanDemandKMPatrakList where cropType is null
        defaultLoanDemandKMPatrakShouldNotBeFound("cropType.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropTypeContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropType contains DEFAULT_CROP_TYPE
        defaultLoanDemandKMPatrakShouldBeFound("cropType.contains=" + DEFAULT_CROP_TYPE);

        // Get all the loanDemandKMPatrakList where cropType contains UPDATED_CROP_TYPE
        defaultLoanDemandKMPatrakShouldNotBeFound("cropType.contains=" + UPDATED_CROP_TYPE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropTypeNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropType does not contain DEFAULT_CROP_TYPE
        defaultLoanDemandKMPatrakShouldNotBeFound("cropType.doesNotContain=" + DEFAULT_CROP_TYPE);

        // Get all the loanDemandKMPatrakList where cropType does not contain UPDATED_CROP_TYPE
        defaultLoanDemandKMPatrakShouldBeFound("cropType.doesNotContain=" + UPDATED_CROP_TYPE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where total equals to DEFAULT_TOTAL
        defaultLoanDemandKMPatrakShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the loanDemandKMPatrakList where total equals to UPDATED_TOTAL
        defaultLoanDemandKMPatrakShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultLoanDemandKMPatrakShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the loanDemandKMPatrakList where total equals to UPDATED_TOTAL
        defaultLoanDemandKMPatrakShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where total is not null
        defaultLoanDemandKMPatrakShouldBeFound("total.specified=true");

        // Get all the loanDemandKMPatrakList where total is null
        defaultLoanDemandKMPatrakShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where total contains DEFAULT_TOTAL
        defaultLoanDemandKMPatrakShouldBeFound("total.contains=" + DEFAULT_TOTAL);

        // Get all the loanDemandKMPatrakList where total contains UPDATED_TOTAL
        defaultLoanDemandKMPatrakShouldNotBeFound("total.contains=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where total does not contain DEFAULT_TOTAL
        defaultLoanDemandKMPatrakShouldNotBeFound("total.doesNotContain=" + DEFAULT_TOTAL);

        // Get all the loanDemandKMPatrakList where total does not contain UPDATED_TOTAL
        defaultLoanDemandKMPatrakShouldBeFound("total.doesNotContain=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where check equals to DEFAULT_CHECK
        defaultLoanDemandKMPatrakShouldBeFound("check.equals=" + DEFAULT_CHECK);

        // Get all the loanDemandKMPatrakList where check equals to UPDATED_CHECK
        defaultLoanDemandKMPatrakShouldNotBeFound("check.equals=" + UPDATED_CHECK);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where check in DEFAULT_CHECK or UPDATED_CHECK
        defaultLoanDemandKMPatrakShouldBeFound("check.in=" + DEFAULT_CHECK + "," + UPDATED_CHECK);

        // Get all the loanDemandKMPatrakList where check equals to UPDATED_CHECK
        defaultLoanDemandKMPatrakShouldNotBeFound("check.in=" + UPDATED_CHECK);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where check is not null
        defaultLoanDemandKMPatrakShouldBeFound("check.specified=true");

        // Get all the loanDemandKMPatrakList where check is null
        defaultLoanDemandKMPatrakShouldNotBeFound("check.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where check contains DEFAULT_CHECK
        defaultLoanDemandKMPatrakShouldBeFound("check.contains=" + DEFAULT_CHECK);

        // Get all the loanDemandKMPatrakList where check contains UPDATED_CHECK
        defaultLoanDemandKMPatrakShouldNotBeFound("check.contains=" + UPDATED_CHECK);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where check does not contain DEFAULT_CHECK
        defaultLoanDemandKMPatrakShouldNotBeFound("check.doesNotContain=" + DEFAULT_CHECK);

        // Get all the loanDemandKMPatrakList where check does not contain UPDATED_CHECK
        defaultLoanDemandKMPatrakShouldBeFound("check.doesNotContain=" + UPDATED_CHECK);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByGoodsIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where goods equals to DEFAULT_GOODS
        defaultLoanDemandKMPatrakShouldBeFound("goods.equals=" + DEFAULT_GOODS);

        // Get all the loanDemandKMPatrakList where goods equals to UPDATED_GOODS
        defaultLoanDemandKMPatrakShouldNotBeFound("goods.equals=" + UPDATED_GOODS);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByGoodsIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where goods in DEFAULT_GOODS or UPDATED_GOODS
        defaultLoanDemandKMPatrakShouldBeFound("goods.in=" + DEFAULT_GOODS + "," + UPDATED_GOODS);

        // Get all the loanDemandKMPatrakList where goods equals to UPDATED_GOODS
        defaultLoanDemandKMPatrakShouldNotBeFound("goods.in=" + UPDATED_GOODS);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByGoodsIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where goods is not null
        defaultLoanDemandKMPatrakShouldBeFound("goods.specified=true");

        // Get all the loanDemandKMPatrakList where goods is null
        defaultLoanDemandKMPatrakShouldNotBeFound("goods.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByGoodsContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where goods contains DEFAULT_GOODS
        defaultLoanDemandKMPatrakShouldBeFound("goods.contains=" + DEFAULT_GOODS);

        // Get all the loanDemandKMPatrakList where goods contains UPDATED_GOODS
        defaultLoanDemandKMPatrakShouldNotBeFound("goods.contains=" + UPDATED_GOODS);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByGoodsNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where goods does not contain DEFAULT_GOODS
        defaultLoanDemandKMPatrakShouldNotBeFound("goods.doesNotContain=" + DEFAULT_GOODS);

        // Get all the loanDemandKMPatrakList where goods does not contain UPDATED_GOODS
        defaultLoanDemandKMPatrakShouldBeFound("goods.doesNotContain=" + UPDATED_GOODS);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesnIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesn equals to DEFAULT_SHARESN
        defaultLoanDemandKMPatrakShouldBeFound("sharesn.equals=" + DEFAULT_SHARESN);

        // Get all the loanDemandKMPatrakList where sharesn equals to UPDATED_SHARESN
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesn.equals=" + UPDATED_SHARESN);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesnIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesn in DEFAULT_SHARESN or UPDATED_SHARESN
        defaultLoanDemandKMPatrakShouldBeFound("sharesn.in=" + DEFAULT_SHARESN + "," + UPDATED_SHARESN);

        // Get all the loanDemandKMPatrakList where sharesn equals to UPDATED_SHARESN
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesn.in=" + UPDATED_SHARESN);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesnIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesn is not null
        defaultLoanDemandKMPatrakShouldBeFound("sharesn.specified=true");

        // Get all the loanDemandKMPatrakList where sharesn is null
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesn.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesnContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesn contains DEFAULT_SHARESN
        defaultLoanDemandKMPatrakShouldBeFound("sharesn.contains=" + DEFAULT_SHARESN);

        // Get all the loanDemandKMPatrakList where sharesn contains UPDATED_SHARESN
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesn.contains=" + UPDATED_SHARESN);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesnNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesn does not contain DEFAULT_SHARESN
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesn.doesNotContain=" + DEFAULT_SHARESN);

        // Get all the loanDemandKMPatrakList where sharesn does not contain UPDATED_SHARESN
        defaultLoanDemandKMPatrakShouldBeFound("sharesn.doesNotContain=" + UPDATED_SHARESN);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByHnIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hn equals to DEFAULT_HN
        defaultLoanDemandKMPatrakShouldBeFound("hn.equals=" + DEFAULT_HN);

        // Get all the loanDemandKMPatrakList where hn equals to UPDATED_HN
        defaultLoanDemandKMPatrakShouldNotBeFound("hn.equals=" + UPDATED_HN);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByHnIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hn in DEFAULT_HN or UPDATED_HN
        defaultLoanDemandKMPatrakShouldBeFound("hn.in=" + DEFAULT_HN + "," + UPDATED_HN);

        // Get all the loanDemandKMPatrakList where hn equals to UPDATED_HN
        defaultLoanDemandKMPatrakShouldNotBeFound("hn.in=" + UPDATED_HN);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByHnIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hn is not null
        defaultLoanDemandKMPatrakShouldBeFound("hn.specified=true");

        // Get all the loanDemandKMPatrakList where hn is null
        defaultLoanDemandKMPatrakShouldNotBeFound("hn.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByHnContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hn contains DEFAULT_HN
        defaultLoanDemandKMPatrakShouldBeFound("hn.contains=" + DEFAULT_HN);

        // Get all the loanDemandKMPatrakList where hn contains UPDATED_HN
        defaultLoanDemandKMPatrakShouldNotBeFound("hn.contains=" + UPDATED_HN);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByHnNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hn does not contain DEFAULT_HN
        defaultLoanDemandKMPatrakShouldNotBeFound("hn.doesNotContain=" + DEFAULT_HN);

        // Get all the loanDemandKMPatrakList where hn does not contain UPDATED_HN
        defaultLoanDemandKMPatrakShouldBeFound("hn.doesNotContain=" + UPDATED_HN);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where area equals to DEFAULT_AREA
        defaultLoanDemandKMPatrakShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the loanDemandKMPatrakList where area equals to UPDATED_AREA
        defaultLoanDemandKMPatrakShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where area in DEFAULT_AREA or UPDATED_AREA
        defaultLoanDemandKMPatrakShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the loanDemandKMPatrakList where area equals to UPDATED_AREA
        defaultLoanDemandKMPatrakShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where area is not null
        defaultLoanDemandKMPatrakShouldBeFound("area.specified=true");

        // Get all the loanDemandKMPatrakList where area is null
        defaultLoanDemandKMPatrakShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAreaContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where area contains DEFAULT_AREA
        defaultLoanDemandKMPatrakShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the loanDemandKMPatrakList where area contains UPDATED_AREA
        defaultLoanDemandKMPatrakShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where area does not contain DEFAULT_AREA
        defaultLoanDemandKMPatrakShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the loanDemandKMPatrakList where area does not contain UPDATED_AREA
        defaultLoanDemandKMPatrakShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAmount equals to DEFAULT_H_AMOUNT
        defaultLoanDemandKMPatrakShouldBeFound("hAmount.equals=" + DEFAULT_H_AMOUNT);

        // Get all the loanDemandKMPatrakList where hAmount equals to UPDATED_H_AMOUNT
        defaultLoanDemandKMPatrakShouldNotBeFound("hAmount.equals=" + UPDATED_H_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAmount in DEFAULT_H_AMOUNT or UPDATED_H_AMOUNT
        defaultLoanDemandKMPatrakShouldBeFound("hAmount.in=" + DEFAULT_H_AMOUNT + "," + UPDATED_H_AMOUNT);

        // Get all the loanDemandKMPatrakList where hAmount equals to UPDATED_H_AMOUNT
        defaultLoanDemandKMPatrakShouldNotBeFound("hAmount.in=" + UPDATED_H_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAmount is not null
        defaultLoanDemandKMPatrakShouldBeFound("hAmount.specified=true");

        // Get all the loanDemandKMPatrakList where hAmount is null
        defaultLoanDemandKMPatrakShouldNotBeFound("hAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAmountContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAmount contains DEFAULT_H_AMOUNT
        defaultLoanDemandKMPatrakShouldBeFound("hAmount.contains=" + DEFAULT_H_AMOUNT);

        // Get all the loanDemandKMPatrakList where hAmount contains UPDATED_H_AMOUNT
        defaultLoanDemandKMPatrakShouldNotBeFound("hAmount.contains=" + UPDATED_H_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAmountNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAmount does not contain DEFAULT_H_AMOUNT
        defaultLoanDemandKMPatrakShouldNotBeFound("hAmount.doesNotContain=" + DEFAULT_H_AMOUNT);

        // Get all the loanDemandKMPatrakList where hAmount does not contain UPDATED_H_AMOUNT
        defaultLoanDemandKMPatrakShouldBeFound("hAmount.doesNotContain=" + UPDATED_H_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where name equals to DEFAULT_NAME
        defaultLoanDemandKMPatrakShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the loanDemandKMPatrakList where name equals to UPDATED_NAME
        defaultLoanDemandKMPatrakShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLoanDemandKMPatrakShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the loanDemandKMPatrakList where name equals to UPDATED_NAME
        defaultLoanDemandKMPatrakShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where name is not null
        defaultLoanDemandKMPatrakShouldBeFound("name.specified=true");

        // Get all the loanDemandKMPatrakList where name is null
        defaultLoanDemandKMPatrakShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByNameContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where name contains DEFAULT_NAME
        defaultLoanDemandKMPatrakShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the loanDemandKMPatrakList where name contains UPDATED_NAME
        defaultLoanDemandKMPatrakShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where name does not contain DEFAULT_NAME
        defaultLoanDemandKMPatrakShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the loanDemandKMPatrakList where name does not contain UPDATED_NAME
        defaultLoanDemandKMPatrakShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKhateCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where khateCode equals to DEFAULT_KHATE_CODE
        defaultLoanDemandKMPatrakShouldBeFound("khateCode.equals=" + DEFAULT_KHATE_CODE);

        // Get all the loanDemandKMPatrakList where khateCode equals to UPDATED_KHATE_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("khateCode.equals=" + UPDATED_KHATE_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKhateCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where khateCode in DEFAULT_KHATE_CODE or UPDATED_KHATE_CODE
        defaultLoanDemandKMPatrakShouldBeFound("khateCode.in=" + DEFAULT_KHATE_CODE + "," + UPDATED_KHATE_CODE);

        // Get all the loanDemandKMPatrakList where khateCode equals to UPDATED_KHATE_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("khateCode.in=" + UPDATED_KHATE_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKhateCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where khateCode is not null
        defaultLoanDemandKMPatrakShouldBeFound("khateCode.specified=true");

        // Get all the loanDemandKMPatrakList where khateCode is null
        defaultLoanDemandKMPatrakShouldNotBeFound("khateCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKhateCodeContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where khateCode contains DEFAULT_KHATE_CODE
        defaultLoanDemandKMPatrakShouldBeFound("khateCode.contains=" + DEFAULT_KHATE_CODE);

        // Get all the loanDemandKMPatrakList where khateCode contains UPDATED_KHATE_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("khateCode.contains=" + UPDATED_KHATE_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKhateCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where khateCode does not contain DEFAULT_KHATE_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("khateCode.doesNotContain=" + DEFAULT_KHATE_CODE);

        // Get all the loanDemandKMPatrakList where khateCode does not contain UPDATED_KHATE_CODE
        defaultLoanDemandKMPatrakShouldBeFound("khateCode.doesNotContain=" + UPDATED_KHATE_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByRemainingIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where remaining equals to DEFAULT_REMAINING
        defaultLoanDemandKMPatrakShouldBeFound("remaining.equals=" + DEFAULT_REMAINING);

        // Get all the loanDemandKMPatrakList where remaining equals to UPDATED_REMAINING
        defaultLoanDemandKMPatrakShouldNotBeFound("remaining.equals=" + UPDATED_REMAINING);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByRemainingIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where remaining in DEFAULT_REMAINING or UPDATED_REMAINING
        defaultLoanDemandKMPatrakShouldBeFound("remaining.in=" + DEFAULT_REMAINING + "," + UPDATED_REMAINING);

        // Get all the loanDemandKMPatrakList where remaining equals to UPDATED_REMAINING
        defaultLoanDemandKMPatrakShouldNotBeFound("remaining.in=" + UPDATED_REMAINING);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByRemainingIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where remaining is not null
        defaultLoanDemandKMPatrakShouldBeFound("remaining.specified=true");

        // Get all the loanDemandKMPatrakList where remaining is null
        defaultLoanDemandKMPatrakShouldNotBeFound("remaining.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByRemainingContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where remaining contains DEFAULT_REMAINING
        defaultLoanDemandKMPatrakShouldBeFound("remaining.contains=" + DEFAULT_REMAINING);

        // Get all the loanDemandKMPatrakList where remaining contains UPDATED_REMAINING
        defaultLoanDemandKMPatrakShouldNotBeFound("remaining.contains=" + UPDATED_REMAINING);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByRemainingNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where remaining does not contain DEFAULT_REMAINING
        defaultLoanDemandKMPatrakShouldNotBeFound("remaining.doesNotContain=" + DEFAULT_REMAINING);

        // Get all the loanDemandKMPatrakList where remaining does not contain UPDATED_REMAINING
        defaultLoanDemandKMPatrakShouldBeFound("remaining.doesNotContain=" + UPDATED_REMAINING);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrears equals to DEFAULT_ARREARS
        defaultLoanDemandKMPatrakShouldBeFound("arrears.equals=" + DEFAULT_ARREARS);

        // Get all the loanDemandKMPatrakList where arrears equals to UPDATED_ARREARS
        defaultLoanDemandKMPatrakShouldNotBeFound("arrears.equals=" + UPDATED_ARREARS);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrears in DEFAULT_ARREARS or UPDATED_ARREARS
        defaultLoanDemandKMPatrakShouldBeFound("arrears.in=" + DEFAULT_ARREARS + "," + UPDATED_ARREARS);

        // Get all the loanDemandKMPatrakList where arrears equals to UPDATED_ARREARS
        defaultLoanDemandKMPatrakShouldNotBeFound("arrears.in=" + UPDATED_ARREARS);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrears is not null
        defaultLoanDemandKMPatrakShouldBeFound("arrears.specified=true");

        // Get all the loanDemandKMPatrakList where arrears is null
        defaultLoanDemandKMPatrakShouldNotBeFound("arrears.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrears contains DEFAULT_ARREARS
        defaultLoanDemandKMPatrakShouldBeFound("arrears.contains=" + DEFAULT_ARREARS);

        // Get all the loanDemandKMPatrakList where arrears contains UPDATED_ARREARS
        defaultLoanDemandKMPatrakShouldNotBeFound("arrears.contains=" + UPDATED_ARREARS);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrears does not contain DEFAULT_ARREARS
        defaultLoanDemandKMPatrakShouldNotBeFound("arrears.doesNotContain=" + DEFAULT_ARREARS);

        // Get all the loanDemandKMPatrakList where arrears does not contain UPDATED_ARREARS
        defaultLoanDemandKMPatrakShouldBeFound("arrears.doesNotContain=" + UPDATED_ARREARS);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptance equals to DEFAULT_KM_ACCEPTANCE
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptance.equals=" + DEFAULT_KM_ACCEPTANCE);

        // Get all the loanDemandKMPatrakList where kmAcceptance equals to UPDATED_KM_ACCEPTANCE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptance.equals=" + UPDATED_KM_ACCEPTANCE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptance in DEFAULT_KM_ACCEPTANCE or UPDATED_KM_ACCEPTANCE
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptance.in=" + DEFAULT_KM_ACCEPTANCE + "," + UPDATED_KM_ACCEPTANCE);

        // Get all the loanDemandKMPatrakList where kmAcceptance equals to UPDATED_KM_ACCEPTANCE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptance.in=" + UPDATED_KM_ACCEPTANCE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptance is not null
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptance.specified=true");

        // Get all the loanDemandKMPatrakList where kmAcceptance is null
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptance.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptance contains DEFAULT_KM_ACCEPTANCE
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptance.contains=" + DEFAULT_KM_ACCEPTANCE);

        // Get all the loanDemandKMPatrakList where kmAcceptance contains UPDATED_KM_ACCEPTANCE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptance.contains=" + UPDATED_KM_ACCEPTANCE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptance does not contain DEFAULT_KM_ACCEPTANCE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptance.doesNotContain=" + DEFAULT_KM_ACCEPTANCE);

        // Get all the loanDemandKMPatrakList where kmAcceptance does not contain UPDATED_KM_ACCEPTANCE
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptance.doesNotContain=" + UPDATED_KM_ACCEPTANCE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPaidDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where paidDate equals to DEFAULT_PAID_DATE
        defaultLoanDemandKMPatrakShouldBeFound("paidDate.equals=" + DEFAULT_PAID_DATE);

        // Get all the loanDemandKMPatrakList where paidDate equals to UPDATED_PAID_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("paidDate.equals=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPaidDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where paidDate in DEFAULT_PAID_DATE or UPDATED_PAID_DATE
        defaultLoanDemandKMPatrakShouldBeFound("paidDate.in=" + DEFAULT_PAID_DATE + "," + UPDATED_PAID_DATE);

        // Get all the loanDemandKMPatrakList where paidDate equals to UPDATED_PAID_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("paidDate.in=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPaidDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where paidDate is not null
        defaultLoanDemandKMPatrakShouldBeFound("paidDate.specified=true");

        // Get all the loanDemandKMPatrakList where paidDate is null
        defaultLoanDemandKMPatrakShouldNotBeFound("paidDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPaidDateContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where paidDate contains DEFAULT_PAID_DATE
        defaultLoanDemandKMPatrakShouldBeFound("paidDate.contains=" + DEFAULT_PAID_DATE);

        // Get all the loanDemandKMPatrakList where paidDate contains UPDATED_PAID_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("paidDate.contains=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPaidDateNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where paidDate does not contain DEFAULT_PAID_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("paidDate.doesNotContain=" + DEFAULT_PAID_DATE);

        // Get all the loanDemandKMPatrakList where paidDate does not contain UPDATED_PAID_DATE
        defaultLoanDemandKMPatrakShouldBeFound("paidDate.doesNotContain=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCode equals to DEFAULT_KM_CODE
        defaultLoanDemandKMPatrakShouldBeFound("kmCode.equals=" + DEFAULT_KM_CODE);

        // Get all the loanDemandKMPatrakList where kmCode equals to UPDATED_KM_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCode.equals=" + UPDATED_KM_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCode in DEFAULT_KM_CODE or UPDATED_KM_CODE
        defaultLoanDemandKMPatrakShouldBeFound("kmCode.in=" + DEFAULT_KM_CODE + "," + UPDATED_KM_CODE);

        // Get all the loanDemandKMPatrakList where kmCode equals to UPDATED_KM_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCode.in=" + UPDATED_KM_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCode is not null
        defaultLoanDemandKMPatrakShouldBeFound("kmCode.specified=true");

        // Get all the loanDemandKMPatrakList where kmCode is null
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCode contains DEFAULT_KM_CODE
        defaultLoanDemandKMPatrakShouldBeFound("kmCode.contains=" + DEFAULT_KM_CODE);

        // Get all the loanDemandKMPatrakList where kmCode contains UPDATED_KM_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCode.contains=" + UPDATED_KM_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCode does not contain DEFAULT_KM_CODE
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCode.doesNotContain=" + DEFAULT_KM_CODE);

        // Get all the loanDemandKMPatrakList where kmCode does not contain UPDATED_KM_CODE
        defaultLoanDemandKMPatrakShouldBeFound("kmCode.doesNotContain=" + UPDATED_KM_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPendingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pendingDate equals to DEFAULT_PENDING_DATE
        defaultLoanDemandKMPatrakShouldBeFound("pendingDate.equals=" + DEFAULT_PENDING_DATE);

        // Get all the loanDemandKMPatrakList where pendingDate equals to UPDATED_PENDING_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("pendingDate.equals=" + UPDATED_PENDING_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPendingDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pendingDate in DEFAULT_PENDING_DATE or UPDATED_PENDING_DATE
        defaultLoanDemandKMPatrakShouldBeFound("pendingDate.in=" + DEFAULT_PENDING_DATE + "," + UPDATED_PENDING_DATE);

        // Get all the loanDemandKMPatrakList where pendingDate equals to UPDATED_PENDING_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("pendingDate.in=" + UPDATED_PENDING_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPendingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pendingDate is not null
        defaultLoanDemandKMPatrakShouldBeFound("pendingDate.specified=true");

        // Get all the loanDemandKMPatrakList where pendingDate is null
        defaultLoanDemandKMPatrakShouldNotBeFound("pendingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPendingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pendingDate is greater than or equal to DEFAULT_PENDING_DATE
        defaultLoanDemandKMPatrakShouldBeFound("pendingDate.greaterThanOrEqual=" + DEFAULT_PENDING_DATE);

        // Get all the loanDemandKMPatrakList where pendingDate is greater than or equal to UPDATED_PENDING_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("pendingDate.greaterThanOrEqual=" + UPDATED_PENDING_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPendingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pendingDate is less than or equal to DEFAULT_PENDING_DATE
        defaultLoanDemandKMPatrakShouldBeFound("pendingDate.lessThanOrEqual=" + DEFAULT_PENDING_DATE);

        // Get all the loanDemandKMPatrakList where pendingDate is less than or equal to SMALLER_PENDING_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("pendingDate.lessThanOrEqual=" + SMALLER_PENDING_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPendingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pendingDate is less than DEFAULT_PENDING_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("pendingDate.lessThan=" + DEFAULT_PENDING_DATE);

        // Get all the loanDemandKMPatrakList where pendingDate is less than UPDATED_PENDING_DATE
        defaultLoanDemandKMPatrakShouldBeFound("pendingDate.lessThan=" + UPDATED_PENDING_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByPendingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where pendingDate is greater than DEFAULT_PENDING_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("pendingDate.greaterThan=" + DEFAULT_PENDING_DATE);

        // Get all the loanDemandKMPatrakList where pendingDate is greater than SMALLER_PENDING_DATE
        defaultLoanDemandKMPatrakShouldBeFound("pendingDate.greaterThan=" + SMALLER_PENDING_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDepositeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where depositeDate equals to DEFAULT_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldBeFound("depositeDate.equals=" + DEFAULT_DEPOSITE_DATE);

        // Get all the loanDemandKMPatrakList where depositeDate equals to UPDATED_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("depositeDate.equals=" + UPDATED_DEPOSITE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDepositeDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where depositeDate in DEFAULT_DEPOSITE_DATE or UPDATED_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldBeFound("depositeDate.in=" + DEFAULT_DEPOSITE_DATE + "," + UPDATED_DEPOSITE_DATE);

        // Get all the loanDemandKMPatrakList where depositeDate equals to UPDATED_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("depositeDate.in=" + UPDATED_DEPOSITE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDepositeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where depositeDate is not null
        defaultLoanDemandKMPatrakShouldBeFound("depositeDate.specified=true");

        // Get all the loanDemandKMPatrakList where depositeDate is null
        defaultLoanDemandKMPatrakShouldNotBeFound("depositeDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDepositeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where depositeDate is greater than or equal to DEFAULT_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldBeFound("depositeDate.greaterThanOrEqual=" + DEFAULT_DEPOSITE_DATE);

        // Get all the loanDemandKMPatrakList where depositeDate is greater than or equal to UPDATED_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("depositeDate.greaterThanOrEqual=" + UPDATED_DEPOSITE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDepositeDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where depositeDate is less than or equal to DEFAULT_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldBeFound("depositeDate.lessThanOrEqual=" + DEFAULT_DEPOSITE_DATE);

        // Get all the loanDemandKMPatrakList where depositeDate is less than or equal to SMALLER_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("depositeDate.lessThanOrEqual=" + SMALLER_DEPOSITE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDepositeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where depositeDate is less than DEFAULT_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("depositeDate.lessThan=" + DEFAULT_DEPOSITE_DATE);

        // Get all the loanDemandKMPatrakList where depositeDate is less than UPDATED_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldBeFound("depositeDate.lessThan=" + UPDATED_DEPOSITE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDepositeDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where depositeDate is greater than DEFAULT_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldNotBeFound("depositeDate.greaterThan=" + DEFAULT_DEPOSITE_DATE);

        // Get all the loanDemandKMPatrakList where depositeDate is greater than SMALLER_DEPOSITE_DATE
        defaultLoanDemandKMPatrakShouldBeFound("depositeDate.greaterThan=" + SMALLER_DEPOSITE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAccountNumberBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where accountNumberB equals to DEFAULT_ACCOUNT_NUMBER_B
        defaultLoanDemandKMPatrakShouldBeFound("accountNumberB.equals=" + DEFAULT_ACCOUNT_NUMBER_B);

        // Get all the loanDemandKMPatrakList where accountNumberB equals to UPDATED_ACCOUNT_NUMBER_B
        defaultLoanDemandKMPatrakShouldNotBeFound("accountNumberB.equals=" + UPDATED_ACCOUNT_NUMBER_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAccountNumberBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where accountNumberB in DEFAULT_ACCOUNT_NUMBER_B or UPDATED_ACCOUNT_NUMBER_B
        defaultLoanDemandKMPatrakShouldBeFound("accountNumberB.in=" + DEFAULT_ACCOUNT_NUMBER_B + "," + UPDATED_ACCOUNT_NUMBER_B);

        // Get all the loanDemandKMPatrakList where accountNumberB equals to UPDATED_ACCOUNT_NUMBER_B
        defaultLoanDemandKMPatrakShouldNotBeFound("accountNumberB.in=" + UPDATED_ACCOUNT_NUMBER_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAccountNumberBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where accountNumberB is not null
        defaultLoanDemandKMPatrakShouldBeFound("accountNumberB.specified=true");

        // Get all the loanDemandKMPatrakList where accountNumberB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("accountNumberB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAccountNumberBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where accountNumberB contains DEFAULT_ACCOUNT_NUMBER_B
        defaultLoanDemandKMPatrakShouldBeFound("accountNumberB.contains=" + DEFAULT_ACCOUNT_NUMBER_B);

        // Get all the loanDemandKMPatrakList where accountNumberB contains UPDATED_ACCOUNT_NUMBER_B
        defaultLoanDemandKMPatrakShouldNotBeFound("accountNumberB.contains=" + UPDATED_ACCOUNT_NUMBER_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByAccountNumberBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where accountNumberB does not contain DEFAULT_ACCOUNT_NUMBER_B
        defaultLoanDemandKMPatrakShouldNotBeFound("accountNumberB.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER_B);

        // Get all the loanDemandKMPatrakList where accountNumberB does not contain UPDATED_ACCOUNT_NUMBER_B
        defaultLoanDemandKMPatrakShouldBeFound("accountNumberB.doesNotContain=" + UPDATED_ACCOUNT_NUMBER_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByLoanDueIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where loanDue equals to DEFAULT_LOAN_DUE
        defaultLoanDemandKMPatrakShouldBeFound("loanDue.equals=" + DEFAULT_LOAN_DUE);

        // Get all the loanDemandKMPatrakList where loanDue equals to UPDATED_LOAN_DUE
        defaultLoanDemandKMPatrakShouldNotBeFound("loanDue.equals=" + UPDATED_LOAN_DUE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByLoanDueIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where loanDue in DEFAULT_LOAN_DUE or UPDATED_LOAN_DUE
        defaultLoanDemandKMPatrakShouldBeFound("loanDue.in=" + DEFAULT_LOAN_DUE + "," + UPDATED_LOAN_DUE);

        // Get all the loanDemandKMPatrakList where loanDue equals to UPDATED_LOAN_DUE
        defaultLoanDemandKMPatrakShouldNotBeFound("loanDue.in=" + UPDATED_LOAN_DUE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByLoanDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where loanDue is not null
        defaultLoanDemandKMPatrakShouldBeFound("loanDue.specified=true");

        // Get all the loanDemandKMPatrakList where loanDue is null
        defaultLoanDemandKMPatrakShouldNotBeFound("loanDue.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByLoanDueContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where loanDue contains DEFAULT_LOAN_DUE
        defaultLoanDemandKMPatrakShouldBeFound("loanDue.contains=" + DEFAULT_LOAN_DUE);

        // Get all the loanDemandKMPatrakList where loanDue contains UPDATED_LOAN_DUE
        defaultLoanDemandKMPatrakShouldNotBeFound("loanDue.contains=" + UPDATED_LOAN_DUE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByLoanDueNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where loanDue does not contain DEFAULT_LOAN_DUE
        defaultLoanDemandKMPatrakShouldNotBeFound("loanDue.doesNotContain=" + DEFAULT_LOAN_DUE);

        // Get all the loanDemandKMPatrakList where loanDue does not contain UPDATED_LOAN_DUE
        defaultLoanDemandKMPatrakShouldBeFound("loanDue.doesNotContain=" + UPDATED_LOAN_DUE);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrearsB equals to DEFAULT_ARREARS_B
        defaultLoanDemandKMPatrakShouldBeFound("arrearsB.equals=" + DEFAULT_ARREARS_B);

        // Get all the loanDemandKMPatrakList where arrearsB equals to UPDATED_ARREARS_B
        defaultLoanDemandKMPatrakShouldNotBeFound("arrearsB.equals=" + UPDATED_ARREARS_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrearsB in DEFAULT_ARREARS_B or UPDATED_ARREARS_B
        defaultLoanDemandKMPatrakShouldBeFound("arrearsB.in=" + DEFAULT_ARREARS_B + "," + UPDATED_ARREARS_B);

        // Get all the loanDemandKMPatrakList where arrearsB equals to UPDATED_ARREARS_B
        defaultLoanDemandKMPatrakShouldNotBeFound("arrearsB.in=" + UPDATED_ARREARS_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrearsB is not null
        defaultLoanDemandKMPatrakShouldBeFound("arrearsB.specified=true");

        // Get all the loanDemandKMPatrakList where arrearsB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("arrearsB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrearsB contains DEFAULT_ARREARS_B
        defaultLoanDemandKMPatrakShouldBeFound("arrearsB.contains=" + DEFAULT_ARREARS_B);

        // Get all the loanDemandKMPatrakList where arrearsB contains UPDATED_ARREARS_B
        defaultLoanDemandKMPatrakShouldNotBeFound("arrearsB.contains=" + UPDATED_ARREARS_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByArrearsBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where arrearsB does not contain DEFAULT_ARREARS_B
        defaultLoanDemandKMPatrakShouldNotBeFound("arrearsB.doesNotContain=" + DEFAULT_ARREARS_B);

        // Get all the loanDemandKMPatrakList where arrearsB does not contain UPDATED_ARREARS_B
        defaultLoanDemandKMPatrakShouldBeFound("arrearsB.doesNotContain=" + UPDATED_ARREARS_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDueDateBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where dueDateB equals to DEFAULT_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("dueDateB.equals=" + DEFAULT_DUE_DATE_B);

        // Get all the loanDemandKMPatrakList where dueDateB equals to UPDATED_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("dueDateB.equals=" + UPDATED_DUE_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDueDateBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where dueDateB in DEFAULT_DUE_DATE_B or UPDATED_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("dueDateB.in=" + DEFAULT_DUE_DATE_B + "," + UPDATED_DUE_DATE_B);

        // Get all the loanDemandKMPatrakList where dueDateB equals to UPDATED_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("dueDateB.in=" + UPDATED_DUE_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDueDateBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where dueDateB is not null
        defaultLoanDemandKMPatrakShouldBeFound("dueDateB.specified=true");

        // Get all the loanDemandKMPatrakList where dueDateB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("dueDateB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDueDateBIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where dueDateB is greater than or equal to DEFAULT_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("dueDateB.greaterThanOrEqual=" + DEFAULT_DUE_DATE_B);

        // Get all the loanDemandKMPatrakList where dueDateB is greater than or equal to UPDATED_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("dueDateB.greaterThanOrEqual=" + UPDATED_DUE_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDueDateBIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where dueDateB is less than or equal to DEFAULT_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("dueDateB.lessThanOrEqual=" + DEFAULT_DUE_DATE_B);

        // Get all the loanDemandKMPatrakList where dueDateB is less than or equal to SMALLER_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("dueDateB.lessThanOrEqual=" + SMALLER_DUE_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDueDateBIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where dueDateB is less than DEFAULT_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("dueDateB.lessThan=" + DEFAULT_DUE_DATE_B);

        // Get all the loanDemandKMPatrakList where dueDateB is less than UPDATED_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("dueDateB.lessThan=" + UPDATED_DUE_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDueDateBIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where dueDateB is greater than DEFAULT_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("dueDateB.greaterThan=" + DEFAULT_DUE_DATE_B);

        // Get all the loanDemandKMPatrakList where dueDateB is greater than SMALLER_DUE_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("dueDateB.greaterThan=" + SMALLER_DUE_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropB equals to DEFAULT_CROP_B
        defaultLoanDemandKMPatrakShouldBeFound("cropB.equals=" + DEFAULT_CROP_B);

        // Get all the loanDemandKMPatrakList where cropB equals to UPDATED_CROP_B
        defaultLoanDemandKMPatrakShouldNotBeFound("cropB.equals=" + UPDATED_CROP_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropB in DEFAULT_CROP_B or UPDATED_CROP_B
        defaultLoanDemandKMPatrakShouldBeFound("cropB.in=" + DEFAULT_CROP_B + "," + UPDATED_CROP_B);

        // Get all the loanDemandKMPatrakList where cropB equals to UPDATED_CROP_B
        defaultLoanDemandKMPatrakShouldNotBeFound("cropB.in=" + UPDATED_CROP_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropB is not null
        defaultLoanDemandKMPatrakShouldBeFound("cropB.specified=true");

        // Get all the loanDemandKMPatrakList where cropB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("cropB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropB contains DEFAULT_CROP_B
        defaultLoanDemandKMPatrakShouldBeFound("cropB.contains=" + DEFAULT_CROP_B);

        // Get all the loanDemandKMPatrakList where cropB contains UPDATED_CROP_B
        defaultLoanDemandKMPatrakShouldNotBeFound("cropB.contains=" + UPDATED_CROP_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCropBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where cropB does not contain DEFAULT_CROP_B
        defaultLoanDemandKMPatrakShouldNotBeFound("cropB.doesNotContain=" + DEFAULT_CROP_B);

        // Get all the loanDemandKMPatrakList where cropB does not contain UPDATED_CROP_B
        defaultLoanDemandKMPatrakShouldBeFound("cropB.doesNotContain=" + UPDATED_CROP_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptanceB equals to DEFAULT_KM_ACCEPTANCE_B
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptanceB.equals=" + DEFAULT_KM_ACCEPTANCE_B);

        // Get all the loanDemandKMPatrakList where kmAcceptanceB equals to UPDATED_KM_ACCEPTANCE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptanceB.equals=" + UPDATED_KM_ACCEPTANCE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptanceB in DEFAULT_KM_ACCEPTANCE_B or UPDATED_KM_ACCEPTANCE_B
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptanceB.in=" + DEFAULT_KM_ACCEPTANCE_B + "," + UPDATED_KM_ACCEPTANCE_B);

        // Get all the loanDemandKMPatrakList where kmAcceptanceB equals to UPDATED_KM_ACCEPTANCE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptanceB.in=" + UPDATED_KM_ACCEPTANCE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptanceB is not null
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptanceB.specified=true");

        // Get all the loanDemandKMPatrakList where kmAcceptanceB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptanceB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptanceB contains DEFAULT_KM_ACCEPTANCE_B
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptanceB.contains=" + DEFAULT_KM_ACCEPTANCE_B);

        // Get all the loanDemandKMPatrakList where kmAcceptanceB contains UPDATED_KM_ACCEPTANCE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptanceB.contains=" + UPDATED_KM_ACCEPTANCE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmAcceptanceBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmAcceptanceB does not contain DEFAULT_KM_ACCEPTANCE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("kmAcceptanceB.doesNotContain=" + DEFAULT_KM_ACCEPTANCE_B);

        // Get all the loanDemandKMPatrakList where kmAcceptanceB does not contain UPDATED_KM_ACCEPTANCE_B
        defaultLoanDemandKMPatrakShouldBeFound("kmAcceptanceB.doesNotContain=" + UPDATED_KM_ACCEPTANCE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCodeB equals to DEFAULT_KM_CODE_B
        defaultLoanDemandKMPatrakShouldBeFound("kmCodeB.equals=" + DEFAULT_KM_CODE_B);

        // Get all the loanDemandKMPatrakList where kmCodeB equals to UPDATED_KM_CODE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCodeB.equals=" + UPDATED_KM_CODE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCodeB in DEFAULT_KM_CODE_B or UPDATED_KM_CODE_B
        defaultLoanDemandKMPatrakShouldBeFound("kmCodeB.in=" + DEFAULT_KM_CODE_B + "," + UPDATED_KM_CODE_B);

        // Get all the loanDemandKMPatrakList where kmCodeB equals to UPDATED_KM_CODE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCodeB.in=" + UPDATED_KM_CODE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCodeB is not null
        defaultLoanDemandKMPatrakShouldBeFound("kmCodeB.specified=true");

        // Get all the loanDemandKMPatrakList where kmCodeB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCodeB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCodeB contains DEFAULT_KM_CODE_B
        defaultLoanDemandKMPatrakShouldBeFound("kmCodeB.contains=" + DEFAULT_KM_CODE_B);

        // Get all the loanDemandKMPatrakList where kmCodeB contains UPDATED_KM_CODE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCodeB.contains=" + UPDATED_KM_CODE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByKmCodeBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where kmCodeB does not contain DEFAULT_KM_CODE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("kmCodeB.doesNotContain=" + DEFAULT_KM_CODE_B);

        // Get all the loanDemandKMPatrakList where kmCodeB does not contain UPDATED_KM_CODE_B
        defaultLoanDemandKMPatrakShouldBeFound("kmCodeB.doesNotContain=" + UPDATED_KM_CODE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementNumberBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementNumberB equals to DEFAULT_H_AGREEMENT_NUMBER_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementNumberB.equals=" + DEFAULT_H_AGREEMENT_NUMBER_B);

        // Get all the loanDemandKMPatrakList where hAgreementNumberB equals to UPDATED_H_AGREEMENT_NUMBER_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementNumberB.equals=" + UPDATED_H_AGREEMENT_NUMBER_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementNumberBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementNumberB in DEFAULT_H_AGREEMENT_NUMBER_B or UPDATED_H_AGREEMENT_NUMBER_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementNumberB.in=" + DEFAULT_H_AGREEMENT_NUMBER_B + "," + UPDATED_H_AGREEMENT_NUMBER_B);

        // Get all the loanDemandKMPatrakList where hAgreementNumberB equals to UPDATED_H_AGREEMENT_NUMBER_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementNumberB.in=" + UPDATED_H_AGREEMENT_NUMBER_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementNumberBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementNumberB is not null
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementNumberB.specified=true");

        // Get all the loanDemandKMPatrakList where hAgreementNumberB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementNumberB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementNumberBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementNumberB contains DEFAULT_H_AGREEMENT_NUMBER_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementNumberB.contains=" + DEFAULT_H_AGREEMENT_NUMBER_B);

        // Get all the loanDemandKMPatrakList where hAgreementNumberB contains UPDATED_H_AGREEMENT_NUMBER_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementNumberB.contains=" + UPDATED_H_AGREEMENT_NUMBER_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementNumberBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementNumberB does not contain DEFAULT_H_AGREEMENT_NUMBER_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementNumberB.doesNotContain=" + DEFAULT_H_AGREEMENT_NUMBER_B);

        // Get all the loanDemandKMPatrakList where hAgreementNumberB does not contain UPDATED_H_AGREEMENT_NUMBER_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementNumberB.doesNotContain=" + UPDATED_H_AGREEMENT_NUMBER_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementAreaBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementAreaB equals to DEFAULT_H_AGREEMENT_AREA_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementAreaB.equals=" + DEFAULT_H_AGREEMENT_AREA_B);

        // Get all the loanDemandKMPatrakList where hAgreementAreaB equals to UPDATED_H_AGREEMENT_AREA_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementAreaB.equals=" + UPDATED_H_AGREEMENT_AREA_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementAreaBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementAreaB in DEFAULT_H_AGREEMENT_AREA_B or UPDATED_H_AGREEMENT_AREA_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementAreaB.in=" + DEFAULT_H_AGREEMENT_AREA_B + "," + UPDATED_H_AGREEMENT_AREA_B);

        // Get all the loanDemandKMPatrakList where hAgreementAreaB equals to UPDATED_H_AGREEMENT_AREA_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementAreaB.in=" + UPDATED_H_AGREEMENT_AREA_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementAreaBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementAreaB is not null
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementAreaB.specified=true");

        // Get all the loanDemandKMPatrakList where hAgreementAreaB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementAreaB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementAreaBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementAreaB contains DEFAULT_H_AGREEMENT_AREA_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementAreaB.contains=" + DEFAULT_H_AGREEMENT_AREA_B);

        // Get all the loanDemandKMPatrakList where hAgreementAreaB contains UPDATED_H_AGREEMENT_AREA_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementAreaB.contains=" + UPDATED_H_AGREEMENT_AREA_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementAreaBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementAreaB does not contain DEFAULT_H_AGREEMENT_AREA_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementAreaB.doesNotContain=" + DEFAULT_H_AGREEMENT_AREA_B);

        // Get all the loanDemandKMPatrakList where hAgreementAreaB does not contain UPDATED_H_AGREEMENT_AREA_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementAreaB.doesNotContain=" + UPDATED_H_AGREEMENT_AREA_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementBurdenBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB equals to DEFAULT_H_AGREEMENT_BURDEN_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementBurdenB.equals=" + DEFAULT_H_AGREEMENT_BURDEN_B);

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB equals to UPDATED_H_AGREEMENT_BURDEN_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementBurdenB.equals=" + UPDATED_H_AGREEMENT_BURDEN_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementBurdenBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB in DEFAULT_H_AGREEMENT_BURDEN_B or UPDATED_H_AGREEMENT_BURDEN_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementBurdenB.in=" + DEFAULT_H_AGREEMENT_BURDEN_B + "," + UPDATED_H_AGREEMENT_BURDEN_B);

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB equals to UPDATED_H_AGREEMENT_BURDEN_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementBurdenB.in=" + UPDATED_H_AGREEMENT_BURDEN_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementBurdenBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB is not null
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementBurdenB.specified=true");

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementBurdenB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementBurdenBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB contains DEFAULT_H_AGREEMENT_BURDEN_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementBurdenB.contains=" + DEFAULT_H_AGREEMENT_BURDEN_B);

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB contains UPDATED_H_AGREEMENT_BURDEN_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementBurdenB.contains=" + UPDATED_H_AGREEMENT_BURDEN_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByhAgreementBurdenBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB does not contain DEFAULT_H_AGREEMENT_BURDEN_B
        defaultLoanDemandKMPatrakShouldNotBeFound("hAgreementBurdenB.doesNotContain=" + DEFAULT_H_AGREEMENT_BURDEN_B);

        // Get all the loanDemandKMPatrakList where hAgreementBurdenB does not contain UPDATED_H_AGREEMENT_BURDEN_B
        defaultLoanDemandKMPatrakShouldBeFound("hAgreementBurdenB.doesNotContain=" + UPDATED_H_AGREEMENT_BURDEN_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalPaidBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where totalPaidB equals to DEFAULT_TOTAL_PAID_B
        defaultLoanDemandKMPatrakShouldBeFound("totalPaidB.equals=" + DEFAULT_TOTAL_PAID_B);

        // Get all the loanDemandKMPatrakList where totalPaidB equals to UPDATED_TOTAL_PAID_B
        defaultLoanDemandKMPatrakShouldNotBeFound("totalPaidB.equals=" + UPDATED_TOTAL_PAID_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalPaidBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where totalPaidB in DEFAULT_TOTAL_PAID_B or UPDATED_TOTAL_PAID_B
        defaultLoanDemandKMPatrakShouldBeFound("totalPaidB.in=" + DEFAULT_TOTAL_PAID_B + "," + UPDATED_TOTAL_PAID_B);

        // Get all the loanDemandKMPatrakList where totalPaidB equals to UPDATED_TOTAL_PAID_B
        defaultLoanDemandKMPatrakShouldNotBeFound("totalPaidB.in=" + UPDATED_TOTAL_PAID_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalPaidBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where totalPaidB is not null
        defaultLoanDemandKMPatrakShouldBeFound("totalPaidB.specified=true");

        // Get all the loanDemandKMPatrakList where totalPaidB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("totalPaidB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalPaidBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where totalPaidB contains DEFAULT_TOTAL_PAID_B
        defaultLoanDemandKMPatrakShouldBeFound("totalPaidB.contains=" + DEFAULT_TOTAL_PAID_B);

        // Get all the loanDemandKMPatrakList where totalPaidB contains UPDATED_TOTAL_PAID_B
        defaultLoanDemandKMPatrakShouldNotBeFound("totalPaidB.contains=" + UPDATED_TOTAL_PAID_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByTotalPaidBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where totalPaidB does not contain DEFAULT_TOTAL_PAID_B
        defaultLoanDemandKMPatrakShouldNotBeFound("totalPaidB.doesNotContain=" + DEFAULT_TOTAL_PAID_B);

        // Get all the loanDemandKMPatrakList where totalPaidB does not contain UPDATED_TOTAL_PAID_B
        defaultLoanDemandKMPatrakShouldBeFound("totalPaidB.doesNotContain=" + UPDATED_TOTAL_PAID_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandAreaB equals to DEFAULT_DEMAND_AREA_B
        defaultLoanDemandKMPatrakShouldBeFound("demandAreaB.equals=" + DEFAULT_DEMAND_AREA_B);

        // Get all the loanDemandKMPatrakList where demandAreaB equals to UPDATED_DEMAND_AREA_B
        defaultLoanDemandKMPatrakShouldNotBeFound("demandAreaB.equals=" + UPDATED_DEMAND_AREA_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandAreaB in DEFAULT_DEMAND_AREA_B or UPDATED_DEMAND_AREA_B
        defaultLoanDemandKMPatrakShouldBeFound("demandAreaB.in=" + DEFAULT_DEMAND_AREA_B + "," + UPDATED_DEMAND_AREA_B);

        // Get all the loanDemandKMPatrakList where demandAreaB equals to UPDATED_DEMAND_AREA_B
        defaultLoanDemandKMPatrakShouldNotBeFound("demandAreaB.in=" + UPDATED_DEMAND_AREA_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandAreaB is not null
        defaultLoanDemandKMPatrakShouldBeFound("demandAreaB.specified=true");

        // Get all the loanDemandKMPatrakList where demandAreaB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("demandAreaB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandAreaB contains DEFAULT_DEMAND_AREA_B
        defaultLoanDemandKMPatrakShouldBeFound("demandAreaB.contains=" + DEFAULT_DEMAND_AREA_B);

        // Get all the loanDemandKMPatrakList where demandAreaB contains UPDATED_DEMAND_AREA_B
        defaultLoanDemandKMPatrakShouldNotBeFound("demandAreaB.contains=" + UPDATED_DEMAND_AREA_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByDemandAreaBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where demandAreaB does not contain DEFAULT_DEMAND_AREA_B
        defaultLoanDemandKMPatrakShouldNotBeFound("demandAreaB.doesNotContain=" + DEFAULT_DEMAND_AREA_B);

        // Get all the loanDemandKMPatrakList where demandAreaB does not contain UPDATED_DEMAND_AREA_B
        defaultLoanDemandKMPatrakShouldBeFound("demandAreaB.doesNotContain=" + UPDATED_DEMAND_AREA_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckInTheFormOfPaymentBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB equals to DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B
        defaultLoanDemandKMPatrakShouldBeFound("checkInTheFormOfPaymentB.equals=" + DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B);

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB equals to UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B
        defaultLoanDemandKMPatrakShouldNotBeFound("checkInTheFormOfPaymentB.equals=" + UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckInTheFormOfPaymentBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB in DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B or UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B
        defaultLoanDemandKMPatrakShouldBeFound(
            "checkInTheFormOfPaymentB.in=" + DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B + "," + UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B
        );

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB equals to UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B
        defaultLoanDemandKMPatrakShouldNotBeFound("checkInTheFormOfPaymentB.in=" + UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckInTheFormOfPaymentBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB is not null
        defaultLoanDemandKMPatrakShouldBeFound("checkInTheFormOfPaymentB.specified=true");

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("checkInTheFormOfPaymentB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckInTheFormOfPaymentBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB contains DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B
        defaultLoanDemandKMPatrakShouldBeFound("checkInTheFormOfPaymentB.contains=" + DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B);

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB contains UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B
        defaultLoanDemandKMPatrakShouldNotBeFound("checkInTheFormOfPaymentB.contains=" + UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByCheckInTheFormOfPaymentBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB does not contain DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B
        defaultLoanDemandKMPatrakShouldNotBeFound("checkInTheFormOfPaymentB.doesNotContain=" + DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B);

        // Get all the loanDemandKMPatrakList where checkInTheFormOfPaymentB does not contain UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B
        defaultLoanDemandKMPatrakShouldBeFound("checkInTheFormOfPaymentB.doesNotContain=" + UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesB equals to DEFAULT_SHARES_B
        defaultLoanDemandKMPatrakShouldBeFound("sharesB.equals=" + DEFAULT_SHARES_B);

        // Get all the loanDemandKMPatrakList where sharesB equals to UPDATED_SHARES_B
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesB.equals=" + UPDATED_SHARES_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesB in DEFAULT_SHARES_B or UPDATED_SHARES_B
        defaultLoanDemandKMPatrakShouldBeFound("sharesB.in=" + DEFAULT_SHARES_B + "," + UPDATED_SHARES_B);

        // Get all the loanDemandKMPatrakList where sharesB equals to UPDATED_SHARES_B
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesB.in=" + UPDATED_SHARES_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesB is not null
        defaultLoanDemandKMPatrakShouldBeFound("sharesB.specified=true");

        // Get all the loanDemandKMPatrakList where sharesB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesBContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesB contains DEFAULT_SHARES_B
        defaultLoanDemandKMPatrakShouldBeFound("sharesB.contains=" + DEFAULT_SHARES_B);

        // Get all the loanDemandKMPatrakList where sharesB contains UPDATED_SHARES_B
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesB.contains=" + UPDATED_SHARES_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksBySharesBNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where sharesB does not contain DEFAULT_SHARES_B
        defaultLoanDemandKMPatrakShouldNotBeFound("sharesB.doesNotContain=" + DEFAULT_SHARES_B);

        // Get all the loanDemandKMPatrakList where sharesB does not contain UPDATED_SHARES_B
        defaultLoanDemandKMPatrakShouldBeFound("sharesB.doesNotContain=" + UPDATED_SHARES_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByVasulPatraRepaymentDateBIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB equals to DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("vasulPatraRepaymentDateB.equals=" + DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB equals to UPDATED_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("vasulPatraRepaymentDateB.equals=" + UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByVasulPatraRepaymentDateBIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB in DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B or UPDATED_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound(
            "vasulPatraRepaymentDateB.in=" + DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B + "," + UPDATED_VASUL_PATRA_REPAYMENT_DATE_B
        );

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB equals to UPDATED_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("vasulPatraRepaymentDateB.in=" + UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByVasulPatraRepaymentDateBIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is not null
        defaultLoanDemandKMPatrakShouldBeFound("vasulPatraRepaymentDateB.specified=true");

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is null
        defaultLoanDemandKMPatrakShouldNotBeFound("vasulPatraRepaymentDateB.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByVasulPatraRepaymentDateBIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is greater than or equal to DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("vasulPatraRepaymentDateB.greaterThanOrEqual=" + DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is greater than or equal to UPDATED_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("vasulPatraRepaymentDateB.greaterThanOrEqual=" + UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByVasulPatraRepaymentDateBIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is less than or equal to DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("vasulPatraRepaymentDateB.lessThanOrEqual=" + DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is less than or equal to SMALLER_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("vasulPatraRepaymentDateB.lessThanOrEqual=" + SMALLER_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByVasulPatraRepaymentDateBIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is less than DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("vasulPatraRepaymentDateB.lessThan=" + DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is less than UPDATED_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("vasulPatraRepaymentDateB.lessThan=" + UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    @Test
    @Transactional
    void getAllLoanDemandKMPatraksByVasulPatraRepaymentDateBIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is greater than DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldNotBeFound("vasulPatraRepaymentDateB.greaterThan=" + DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B);

        // Get all the loanDemandKMPatrakList where vasulPatraRepaymentDateB is greater than SMALLER_VASUL_PATRA_REPAYMENT_DATE_B
        defaultLoanDemandKMPatrakShouldBeFound("vasulPatraRepaymentDateB.greaterThan=" + SMALLER_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanDemandKMPatrakShouldBeFound(String filter) throws Exception {
        restLoanDemandKMPatrakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDemandKMPatrak.getId().intValue())))
            .andExpect(jsonPath("$.[*].demandCode").value(hasItem(DEFAULT_DEMAND_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].shares").value(hasItem(DEFAULT_SHARES)))
            .andExpect(jsonPath("$.[*].pid").value(hasItem(DEFAULT_PID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].demandArea").value(hasItem(DEFAULT_DEMAND_AREA)))
            .andExpect(jsonPath("$.[*].cropType").value(hasItem(DEFAULT_CROP_TYPE)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].check").value(hasItem(DEFAULT_CHECK)))
            .andExpect(jsonPath("$.[*].goods").value(hasItem(DEFAULT_GOODS)))
            .andExpect(jsonPath("$.[*].sharesn").value(hasItem(DEFAULT_SHARESN)))
            .andExpect(jsonPath("$.[*].hn").value(hasItem(DEFAULT_HN)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].hAmount").value(hasItem(DEFAULT_H_AMOUNT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].khateCode").value(hasItem(DEFAULT_KHATE_CODE)))
            .andExpect(jsonPath("$.[*].remaining").value(hasItem(DEFAULT_REMAINING)))
            .andExpect(jsonPath("$.[*].arrears").value(hasItem(DEFAULT_ARREARS)))
            .andExpect(jsonPath("$.[*].kmAcceptance").value(hasItem(DEFAULT_KM_ACCEPTANCE)))
            .andExpect(jsonPath("$.[*].paidDate").value(hasItem(DEFAULT_PAID_DATE)))
            .andExpect(jsonPath("$.[*].kmCode").value(hasItem(DEFAULT_KM_CODE)))
            .andExpect(jsonPath("$.[*].pendingDate").value(hasItem(DEFAULT_PENDING_DATE.toString())))
            .andExpect(jsonPath("$.[*].depositeDate").value(hasItem(DEFAULT_DEPOSITE_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountNumberB").value(hasItem(DEFAULT_ACCOUNT_NUMBER_B)))
            .andExpect(jsonPath("$.[*].loanDue").value(hasItem(DEFAULT_LOAN_DUE)))
            .andExpect(jsonPath("$.[*].arrearsB").value(hasItem(DEFAULT_ARREARS_B)))
            .andExpect(jsonPath("$.[*].dueDateB").value(hasItem(DEFAULT_DUE_DATE_B.toString())))
            .andExpect(jsonPath("$.[*].cropB").value(hasItem(DEFAULT_CROP_B)))
            .andExpect(jsonPath("$.[*].kmAcceptanceB").value(hasItem(DEFAULT_KM_ACCEPTANCE_B)))
            .andExpect(jsonPath("$.[*].kmCodeB").value(hasItem(DEFAULT_KM_CODE_B)))
            .andExpect(jsonPath("$.[*].hAgreementNumberB").value(hasItem(DEFAULT_H_AGREEMENT_NUMBER_B)))
            .andExpect(jsonPath("$.[*].hAgreementAreaB").value(hasItem(DEFAULT_H_AGREEMENT_AREA_B)))
            .andExpect(jsonPath("$.[*].hAgreementBurdenB").value(hasItem(DEFAULT_H_AGREEMENT_BURDEN_B)))
            .andExpect(jsonPath("$.[*].totalPaidB").value(hasItem(DEFAULT_TOTAL_PAID_B)))
            .andExpect(jsonPath("$.[*].demandAreaB").value(hasItem(DEFAULT_DEMAND_AREA_B)))
            .andExpect(jsonPath("$.[*].checkInTheFormOfPaymentB").value(hasItem(DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B)))
            .andExpect(jsonPath("$.[*].sharesB").value(hasItem(DEFAULT_SHARES_B)))
            .andExpect(jsonPath("$.[*].vasulPatraRepaymentDateB").value(hasItem(DEFAULT_VASUL_PATRA_REPAYMENT_DATE_B.toString())));

        // Check, that the count call also returns 1
        restLoanDemandKMPatrakMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanDemandKMPatrakShouldNotBeFound(String filter) throws Exception {
        restLoanDemandKMPatrakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanDemandKMPatrakMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanDemandKMPatrak() throws Exception {
        // Get the loanDemandKMPatrak
        restLoanDemandKMPatrakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoanDemandKMPatrak() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        int databaseSizeBeforeUpdate = loanDemandKMPatrakRepository.findAll().size();

        // Update the loanDemandKMPatrak
        LoanDemandKMPatrak updatedLoanDemandKMPatrak = loanDemandKMPatrakRepository.findById(loanDemandKMPatrak.getId()).get();
        // Disconnect from session so that the updates on updatedLoanDemandKMPatrak are not directly saved in db
        em.detach(updatedLoanDemandKMPatrak);
        updatedLoanDemandKMPatrak
            .demandCode(UPDATED_DEMAND_CODE)
            .date(UPDATED_DATE)
            .kmDate(UPDATED_KM_DATE)
            .shares(UPDATED_SHARES)
            .pid(UPDATED_PID)
            .code(UPDATED_CODE)
            .demandArea(UPDATED_DEMAND_AREA)
            .cropType(UPDATED_CROP_TYPE)
            .total(UPDATED_TOTAL)
            .check(UPDATED_CHECK)
            .goods(UPDATED_GOODS)
            .sharesn(UPDATED_SHARESN)
            .hn(UPDATED_HN)
            .area(UPDATED_AREA)
            .hAmount(UPDATED_H_AMOUNT)
            .name(UPDATED_NAME)
            .khateCode(UPDATED_KHATE_CODE)
            .remaining(UPDATED_REMAINING)
            .arrears(UPDATED_ARREARS)
            .kmAcceptance(UPDATED_KM_ACCEPTANCE)
            .paidDate(UPDATED_PAID_DATE)
            .kmCode(UPDATED_KM_CODE)
            .pendingDate(UPDATED_PENDING_DATE)
            .depositeDate(UPDATED_DEPOSITE_DATE)
            .accountNumberB(UPDATED_ACCOUNT_NUMBER_B)
            .loanDue(UPDATED_LOAN_DUE)
            .arrearsB(UPDATED_ARREARS_B)
            .dueDateB(UPDATED_DUE_DATE_B)
            .cropB(UPDATED_CROP_B)
            .kmAcceptanceB(UPDATED_KM_ACCEPTANCE_B)
            .kmCodeB(UPDATED_KM_CODE_B)
            .hAgreementNumberB(UPDATED_H_AGREEMENT_NUMBER_B)
            .hAgreementAreaB(UPDATED_H_AGREEMENT_AREA_B)
            .hAgreementBurdenB(UPDATED_H_AGREEMENT_BURDEN_B)
            .totalPaidB(UPDATED_TOTAL_PAID_B)
            .demandAreaB(UPDATED_DEMAND_AREA_B)
            .checkInTheFormOfPaymentB(UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B)
            .sharesB(UPDATED_SHARES_B)
            .vasulPatraRepaymentDateB(UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);

        restLoanDemandKMPatrakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoanDemandKMPatrak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLoanDemandKMPatrak))
            )
            .andExpect(status().isOk());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeUpdate);
        LoanDemandKMPatrak testLoanDemandKMPatrak = loanDemandKMPatrakList.get(loanDemandKMPatrakList.size() - 1);
        assertThat(testLoanDemandKMPatrak.getDemandCode()).isEqualTo(UPDATED_DEMAND_CODE);
        assertThat(testLoanDemandKMPatrak.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLoanDemandKMPatrak.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testLoanDemandKMPatrak.getShares()).isEqualTo(UPDATED_SHARES);
        assertThat(testLoanDemandKMPatrak.getPid()).isEqualTo(UPDATED_PID);
        assertThat(testLoanDemandKMPatrak.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLoanDemandKMPatrak.getDemandArea()).isEqualTo(UPDATED_DEMAND_AREA);
        assertThat(testLoanDemandKMPatrak.getCropType()).isEqualTo(UPDATED_CROP_TYPE);
        assertThat(testLoanDemandKMPatrak.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testLoanDemandKMPatrak.getCheck()).isEqualTo(UPDATED_CHECK);
        assertThat(testLoanDemandKMPatrak.getGoods()).isEqualTo(UPDATED_GOODS);
        assertThat(testLoanDemandKMPatrak.getSharesn()).isEqualTo(UPDATED_SHARESN);
        assertThat(testLoanDemandKMPatrak.getHn()).isEqualTo(UPDATED_HN);
        assertThat(testLoanDemandKMPatrak.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLoanDemandKMPatrak.gethAmount()).isEqualTo(UPDATED_H_AMOUNT);
        assertThat(testLoanDemandKMPatrak.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLoanDemandKMPatrak.getKhateCode()).isEqualTo(UPDATED_KHATE_CODE);
        assertThat(testLoanDemandKMPatrak.getRemaining()).isEqualTo(UPDATED_REMAINING);
        assertThat(testLoanDemandKMPatrak.getArrears()).isEqualTo(UPDATED_ARREARS);
        assertThat(testLoanDemandKMPatrak.getKmAcceptance()).isEqualTo(UPDATED_KM_ACCEPTANCE);
        assertThat(testLoanDemandKMPatrak.getPaidDate()).isEqualTo(UPDATED_PAID_DATE);
        assertThat(testLoanDemandKMPatrak.getKmCode()).isEqualTo(UPDATED_KM_CODE);
        assertThat(testLoanDemandKMPatrak.getPendingDate()).isEqualTo(UPDATED_PENDING_DATE);
        assertThat(testLoanDemandKMPatrak.getDepositeDate()).isEqualTo(UPDATED_DEPOSITE_DATE);
        assertThat(testLoanDemandKMPatrak.getAccountNumberB()).isEqualTo(UPDATED_ACCOUNT_NUMBER_B);
        assertThat(testLoanDemandKMPatrak.getLoanDue()).isEqualTo(UPDATED_LOAN_DUE);
        assertThat(testLoanDemandKMPatrak.getArrearsB()).isEqualTo(UPDATED_ARREARS_B);
        assertThat(testLoanDemandKMPatrak.getDueDateB()).isEqualTo(UPDATED_DUE_DATE_B);
        assertThat(testLoanDemandKMPatrak.getCropB()).isEqualTo(UPDATED_CROP_B);
        assertThat(testLoanDemandKMPatrak.getKmAcceptanceB()).isEqualTo(UPDATED_KM_ACCEPTANCE_B);
        assertThat(testLoanDemandKMPatrak.getKmCodeB()).isEqualTo(UPDATED_KM_CODE_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementNumberB()).isEqualTo(UPDATED_H_AGREEMENT_NUMBER_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementAreaB()).isEqualTo(UPDATED_H_AGREEMENT_AREA_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementBurdenB()).isEqualTo(UPDATED_H_AGREEMENT_BURDEN_B);
        assertThat(testLoanDemandKMPatrak.getTotalPaidB()).isEqualTo(UPDATED_TOTAL_PAID_B);
        assertThat(testLoanDemandKMPatrak.getDemandAreaB()).isEqualTo(UPDATED_DEMAND_AREA_B);
        assertThat(testLoanDemandKMPatrak.getCheckInTheFormOfPaymentB()).isEqualTo(UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B);
        assertThat(testLoanDemandKMPatrak.getSharesB()).isEqualTo(UPDATED_SHARES_B);
        assertThat(testLoanDemandKMPatrak.getVasulPatraRepaymentDateB()).isEqualTo(UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    @Test
    @Transactional
    void putNonExistingLoanDemandKMPatrak() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandKMPatrakRepository.findAll().size();
        loanDemandKMPatrak.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDemandKMPatrakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanDemandKMPatrak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandKMPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanDemandKMPatrak() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandKMPatrakRepository.findAll().size();
        loanDemandKMPatrak.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDemandKMPatrakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandKMPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanDemandKMPatrak() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandKMPatrakRepository.findAll().size();
        loanDemandKMPatrak.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDemandKMPatrakMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDemandKMPatrak))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanDemandKMPatrakWithPatch() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        int databaseSizeBeforeUpdate = loanDemandKMPatrakRepository.findAll().size();

        // Update the loanDemandKMPatrak using partial update
        LoanDemandKMPatrak partialUpdatedLoanDemandKMPatrak = new LoanDemandKMPatrak();
        partialUpdatedLoanDemandKMPatrak.setId(loanDemandKMPatrak.getId());

        partialUpdatedLoanDemandKMPatrak
            .demandCode(UPDATED_DEMAND_CODE)
            .shares(UPDATED_SHARES)
            .pid(UPDATED_PID)
            .hn(UPDATED_HN)
            .khateCode(UPDATED_KHATE_CODE)
            .remaining(UPDATED_REMAINING)
            .kmCode(UPDATED_KM_CODE)
            .pendingDate(UPDATED_PENDING_DATE)
            .accountNumberB(UPDATED_ACCOUNT_NUMBER_B)
            .arrearsB(UPDATED_ARREARS_B)
            .kmAcceptanceB(UPDATED_KM_ACCEPTANCE_B)
            .kmCodeB(UPDATED_KM_CODE_B)
            .vasulPatraRepaymentDateB(UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);

        restLoanDemandKMPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDemandKMPatrak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDemandKMPatrak))
            )
            .andExpect(status().isOk());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeUpdate);
        LoanDemandKMPatrak testLoanDemandKMPatrak = loanDemandKMPatrakList.get(loanDemandKMPatrakList.size() - 1);
        assertThat(testLoanDemandKMPatrak.getDemandCode()).isEqualTo(UPDATED_DEMAND_CODE);
        assertThat(testLoanDemandKMPatrak.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testLoanDemandKMPatrak.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
        assertThat(testLoanDemandKMPatrak.getShares()).isEqualTo(UPDATED_SHARES);
        assertThat(testLoanDemandKMPatrak.getPid()).isEqualTo(UPDATED_PID);
        assertThat(testLoanDemandKMPatrak.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLoanDemandKMPatrak.getDemandArea()).isEqualTo(DEFAULT_DEMAND_AREA);
        assertThat(testLoanDemandKMPatrak.getCropType()).isEqualTo(DEFAULT_CROP_TYPE);
        assertThat(testLoanDemandKMPatrak.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testLoanDemandKMPatrak.getCheck()).isEqualTo(DEFAULT_CHECK);
        assertThat(testLoanDemandKMPatrak.getGoods()).isEqualTo(DEFAULT_GOODS);
        assertThat(testLoanDemandKMPatrak.getSharesn()).isEqualTo(DEFAULT_SHARESN);
        assertThat(testLoanDemandKMPatrak.getHn()).isEqualTo(UPDATED_HN);
        assertThat(testLoanDemandKMPatrak.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testLoanDemandKMPatrak.gethAmount()).isEqualTo(DEFAULT_H_AMOUNT);
        assertThat(testLoanDemandKMPatrak.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLoanDemandKMPatrak.getKhateCode()).isEqualTo(UPDATED_KHATE_CODE);
        assertThat(testLoanDemandKMPatrak.getRemaining()).isEqualTo(UPDATED_REMAINING);
        assertThat(testLoanDemandKMPatrak.getArrears()).isEqualTo(DEFAULT_ARREARS);
        assertThat(testLoanDemandKMPatrak.getKmAcceptance()).isEqualTo(DEFAULT_KM_ACCEPTANCE);
        assertThat(testLoanDemandKMPatrak.getPaidDate()).isEqualTo(DEFAULT_PAID_DATE);
        assertThat(testLoanDemandKMPatrak.getKmCode()).isEqualTo(UPDATED_KM_CODE);
        assertThat(testLoanDemandKMPatrak.getPendingDate()).isEqualTo(UPDATED_PENDING_DATE);
        assertThat(testLoanDemandKMPatrak.getDepositeDate()).isEqualTo(DEFAULT_DEPOSITE_DATE);
        assertThat(testLoanDemandKMPatrak.getAccountNumberB()).isEqualTo(UPDATED_ACCOUNT_NUMBER_B);
        assertThat(testLoanDemandKMPatrak.getLoanDue()).isEqualTo(DEFAULT_LOAN_DUE);
        assertThat(testLoanDemandKMPatrak.getArrearsB()).isEqualTo(UPDATED_ARREARS_B);
        assertThat(testLoanDemandKMPatrak.getDueDateB()).isEqualTo(DEFAULT_DUE_DATE_B);
        assertThat(testLoanDemandKMPatrak.getCropB()).isEqualTo(DEFAULT_CROP_B);
        assertThat(testLoanDemandKMPatrak.getKmAcceptanceB()).isEqualTo(UPDATED_KM_ACCEPTANCE_B);
        assertThat(testLoanDemandKMPatrak.getKmCodeB()).isEqualTo(UPDATED_KM_CODE_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementNumberB()).isEqualTo(DEFAULT_H_AGREEMENT_NUMBER_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementAreaB()).isEqualTo(DEFAULT_H_AGREEMENT_AREA_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementBurdenB()).isEqualTo(DEFAULT_H_AGREEMENT_BURDEN_B);
        assertThat(testLoanDemandKMPatrak.getTotalPaidB()).isEqualTo(DEFAULT_TOTAL_PAID_B);
        assertThat(testLoanDemandKMPatrak.getDemandAreaB()).isEqualTo(DEFAULT_DEMAND_AREA_B);
        assertThat(testLoanDemandKMPatrak.getCheckInTheFormOfPaymentB()).isEqualTo(DEFAULT_CHECK_IN_THE_FORM_OF_PAYMENT_B);
        assertThat(testLoanDemandKMPatrak.getSharesB()).isEqualTo(DEFAULT_SHARES_B);
        assertThat(testLoanDemandKMPatrak.getVasulPatraRepaymentDateB()).isEqualTo(UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    @Test
    @Transactional
    void fullUpdateLoanDemandKMPatrakWithPatch() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        int databaseSizeBeforeUpdate = loanDemandKMPatrakRepository.findAll().size();

        // Update the loanDemandKMPatrak using partial update
        LoanDemandKMPatrak partialUpdatedLoanDemandKMPatrak = new LoanDemandKMPatrak();
        partialUpdatedLoanDemandKMPatrak.setId(loanDemandKMPatrak.getId());

        partialUpdatedLoanDemandKMPatrak
            .demandCode(UPDATED_DEMAND_CODE)
            .date(UPDATED_DATE)
            .kmDate(UPDATED_KM_DATE)
            .shares(UPDATED_SHARES)
            .pid(UPDATED_PID)
            .code(UPDATED_CODE)
            .demandArea(UPDATED_DEMAND_AREA)
            .cropType(UPDATED_CROP_TYPE)
            .total(UPDATED_TOTAL)
            .check(UPDATED_CHECK)
            .goods(UPDATED_GOODS)
            .sharesn(UPDATED_SHARESN)
            .hn(UPDATED_HN)
            .area(UPDATED_AREA)
            .hAmount(UPDATED_H_AMOUNT)
            .name(UPDATED_NAME)
            .khateCode(UPDATED_KHATE_CODE)
            .remaining(UPDATED_REMAINING)
            .arrears(UPDATED_ARREARS)
            .kmAcceptance(UPDATED_KM_ACCEPTANCE)
            .paidDate(UPDATED_PAID_DATE)
            .kmCode(UPDATED_KM_CODE)
            .pendingDate(UPDATED_PENDING_DATE)
            .depositeDate(UPDATED_DEPOSITE_DATE)
            .accountNumberB(UPDATED_ACCOUNT_NUMBER_B)
            .loanDue(UPDATED_LOAN_DUE)
            .arrearsB(UPDATED_ARREARS_B)
            .dueDateB(UPDATED_DUE_DATE_B)
            .cropB(UPDATED_CROP_B)
            .kmAcceptanceB(UPDATED_KM_ACCEPTANCE_B)
            .kmCodeB(UPDATED_KM_CODE_B)
            .hAgreementNumberB(UPDATED_H_AGREEMENT_NUMBER_B)
            .hAgreementAreaB(UPDATED_H_AGREEMENT_AREA_B)
            .hAgreementBurdenB(UPDATED_H_AGREEMENT_BURDEN_B)
            .totalPaidB(UPDATED_TOTAL_PAID_B)
            .demandAreaB(UPDATED_DEMAND_AREA_B)
            .checkInTheFormOfPaymentB(UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B)
            .sharesB(UPDATED_SHARES_B)
            .vasulPatraRepaymentDateB(UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);

        restLoanDemandKMPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDemandKMPatrak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDemandKMPatrak))
            )
            .andExpect(status().isOk());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeUpdate);
        LoanDemandKMPatrak testLoanDemandKMPatrak = loanDemandKMPatrakList.get(loanDemandKMPatrakList.size() - 1);
        assertThat(testLoanDemandKMPatrak.getDemandCode()).isEqualTo(UPDATED_DEMAND_CODE);
        assertThat(testLoanDemandKMPatrak.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLoanDemandKMPatrak.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testLoanDemandKMPatrak.getShares()).isEqualTo(UPDATED_SHARES);
        assertThat(testLoanDemandKMPatrak.getPid()).isEqualTo(UPDATED_PID);
        assertThat(testLoanDemandKMPatrak.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLoanDemandKMPatrak.getDemandArea()).isEqualTo(UPDATED_DEMAND_AREA);
        assertThat(testLoanDemandKMPatrak.getCropType()).isEqualTo(UPDATED_CROP_TYPE);
        assertThat(testLoanDemandKMPatrak.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testLoanDemandKMPatrak.getCheck()).isEqualTo(UPDATED_CHECK);
        assertThat(testLoanDemandKMPatrak.getGoods()).isEqualTo(UPDATED_GOODS);
        assertThat(testLoanDemandKMPatrak.getSharesn()).isEqualTo(UPDATED_SHARESN);
        assertThat(testLoanDemandKMPatrak.getHn()).isEqualTo(UPDATED_HN);
        assertThat(testLoanDemandKMPatrak.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLoanDemandKMPatrak.gethAmount()).isEqualTo(UPDATED_H_AMOUNT);
        assertThat(testLoanDemandKMPatrak.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLoanDemandKMPatrak.getKhateCode()).isEqualTo(UPDATED_KHATE_CODE);
        assertThat(testLoanDemandKMPatrak.getRemaining()).isEqualTo(UPDATED_REMAINING);
        assertThat(testLoanDemandKMPatrak.getArrears()).isEqualTo(UPDATED_ARREARS);
        assertThat(testLoanDemandKMPatrak.getKmAcceptance()).isEqualTo(UPDATED_KM_ACCEPTANCE);
        assertThat(testLoanDemandKMPatrak.getPaidDate()).isEqualTo(UPDATED_PAID_DATE);
        assertThat(testLoanDemandKMPatrak.getKmCode()).isEqualTo(UPDATED_KM_CODE);
        assertThat(testLoanDemandKMPatrak.getPendingDate()).isEqualTo(UPDATED_PENDING_DATE);
        assertThat(testLoanDemandKMPatrak.getDepositeDate()).isEqualTo(UPDATED_DEPOSITE_DATE);
        assertThat(testLoanDemandKMPatrak.getAccountNumberB()).isEqualTo(UPDATED_ACCOUNT_NUMBER_B);
        assertThat(testLoanDemandKMPatrak.getLoanDue()).isEqualTo(UPDATED_LOAN_DUE);
        assertThat(testLoanDemandKMPatrak.getArrearsB()).isEqualTo(UPDATED_ARREARS_B);
        assertThat(testLoanDemandKMPatrak.getDueDateB()).isEqualTo(UPDATED_DUE_DATE_B);
        assertThat(testLoanDemandKMPatrak.getCropB()).isEqualTo(UPDATED_CROP_B);
        assertThat(testLoanDemandKMPatrak.getKmAcceptanceB()).isEqualTo(UPDATED_KM_ACCEPTANCE_B);
        assertThat(testLoanDemandKMPatrak.getKmCodeB()).isEqualTo(UPDATED_KM_CODE_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementNumberB()).isEqualTo(UPDATED_H_AGREEMENT_NUMBER_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementAreaB()).isEqualTo(UPDATED_H_AGREEMENT_AREA_B);
        assertThat(testLoanDemandKMPatrak.gethAgreementBurdenB()).isEqualTo(UPDATED_H_AGREEMENT_BURDEN_B);
        assertThat(testLoanDemandKMPatrak.getTotalPaidB()).isEqualTo(UPDATED_TOTAL_PAID_B);
        assertThat(testLoanDemandKMPatrak.getDemandAreaB()).isEqualTo(UPDATED_DEMAND_AREA_B);
        assertThat(testLoanDemandKMPatrak.getCheckInTheFormOfPaymentB()).isEqualTo(UPDATED_CHECK_IN_THE_FORM_OF_PAYMENT_B);
        assertThat(testLoanDemandKMPatrak.getSharesB()).isEqualTo(UPDATED_SHARES_B);
        assertThat(testLoanDemandKMPatrak.getVasulPatraRepaymentDateB()).isEqualTo(UPDATED_VASUL_PATRA_REPAYMENT_DATE_B);
    }

    @Test
    @Transactional
    void patchNonExistingLoanDemandKMPatrak() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandKMPatrakRepository.findAll().size();
        loanDemandKMPatrak.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDemandKMPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanDemandKMPatrak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandKMPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanDemandKMPatrak() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandKMPatrakRepository.findAll().size();
        loanDemandKMPatrak.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDemandKMPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandKMPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanDemandKMPatrak() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandKMPatrakRepository.findAll().size();
        loanDemandKMPatrak.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDemandKMPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandKMPatrak))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDemandKMPatrak in the database
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanDemandKMPatrak() throws Exception {
        // Initialize the database
        loanDemandKMPatrakRepository.saveAndFlush(loanDemandKMPatrak);

        int databaseSizeBeforeDelete = loanDemandKMPatrakRepository.findAll().size();

        // Delete the loanDemandKMPatrak
        restLoanDemandKMPatrakMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanDemandKMPatrak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanDemandKMPatrak> loanDemandKMPatrakList = loanDemandKMPatrakRepository.findAll();
        assertThat(loanDemandKMPatrakList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
