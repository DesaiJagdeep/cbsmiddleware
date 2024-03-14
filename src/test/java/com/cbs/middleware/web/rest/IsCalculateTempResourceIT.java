package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.IsCalculateTemp;
import com.cbs.middleware.repository.IsCalculateTempRepository;
import com.cbs.middleware.service.criteria.IsCalculateTempCriteria;
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
 * Integration tests for the {@link IsCalculateTempResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IsCalculateTempResourceIT {

    private static final Integer DEFAULT_SERIAL_NO = 1;
    private static final Integer UPDATED_SERIAL_NO = 2;
    private static final Integer SMALLER_SERIAL_NO = 1 - 1;

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final Long DEFAULT_ISS_FILE_PARSER_ID = 1L;
    private static final Long UPDATED_ISS_FILE_PARSER_ID = 2L;
    private static final Long SMALLER_ISS_FILE_PARSER_ID = 1L - 1L;

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_ACCOUNT_NUMBER_KCC = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_ACCOUNT_NUMBER_KCC = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIAL_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_SANCTION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_SANCTION_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_SANCTION_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_SANCTION_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DISBURSEMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DISBURSEMENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DISBURSE_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISBURSE_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_MATURITY_LOAN_DATE = "AAAAAAAAAA";
    private static final String UPDATED_MATURITY_LOAN_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RECOVERY_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_RECOVERY_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_RECOVERY_INTEREST = "AAAAAAAAAA";
    private static final String UPDATED_RECOVERY_INTEREST = "BBBBBBBBBB";

    private static final String DEFAULT_RECOVERY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_RECOVERY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_BALANCE_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_BALANCE_AMOUNT = "BBBBBBBBBB";

    private static final Long DEFAULT_PREV_DAYS = 1L;
    private static final Long UPDATED_PREV_DAYS = 2L;
    private static final Long SMALLER_PREV_DAYS = 1L - 1L;

    private static final Long DEFAULT_PRES_DAYS = 1L;
    private static final Long UPDATED_PRES_DAYS = 2L;
    private static final Long SMALLER_PRES_DAYS = 1L - 1L;

    private static final Long DEFAULT_ACTUAL_DAYS = 1L;
    private static final Long UPDATED_ACTUAL_DAYS = 2L;
    private static final Long SMALLER_ACTUAL_DAYS = 1L - 1L;

    private static final Integer DEFAULT_N_PROD = 1;
    private static final Integer UPDATED_N_PROD = 2;
    private static final Integer SMALLER_N_PROD = 1 - 1;

    private static final String DEFAULT_PRODUCT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_BANK = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ABH_3_LAKH = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ABH_3_LAKH = "BBBBBBBBBB";

    private static final Double DEFAULT_INTEREST_FIRST_15 = 1D;
    private static final Double UPDATED_INTEREST_FIRST_15 = 2D;
    private static final Double SMALLER_INTEREST_FIRST_15 = 1D - 1D;

    private static final Double DEFAULT_INTEREST_FIRST_25 = 1D;
    private static final Double UPDATED_INTEREST_FIRST_25 = 2D;
    private static final Double SMALLER_INTEREST_FIRST_25 = 1D - 1D;

    private static final Double DEFAULT_INTEREST_SECOND_15 = 1D;
    private static final Double UPDATED_INTEREST_SECOND_15 = 2D;
    private static final Double SMALLER_INTEREST_SECOND_15 = 1D - 1D;

    private static final Double DEFAULT_INTEREST_SECOND_25 = 1D;
    private static final Double UPDATED_INTEREST_SECOND_25 = 2D;
    private static final Double SMALLER_INTEREST_SECOND_25 = 1D - 1D;

    private static final Double DEFAULT_INTEREST_STATE_FIRST_3 = 1D;
    private static final Double UPDATED_INTEREST_STATE_FIRST_3 = 2D;
    private static final Double SMALLER_INTEREST_STATE_FIRST_3 = 1D - 1D;

    private static final Double DEFAULT_INTEREST_STATE_SECOND_3 = 1D;
    private static final Double UPDATED_INTEREST_STATE_SECOND_3 = 2D;
    private static final Double SMALLER_INTEREST_STATE_SECOND_3 = 1D - 1D;

    private static final Double DEFAULT_INTEREST_FIRST_ABH_3 = 1D;
    private static final Double UPDATED_INTEREST_FIRST_ABH_3 = 2D;
    private static final Double SMALLER_INTEREST_FIRST_ABH_3 = 1D - 1D;

    private static final Double DEFAULT_INTEREST_SECOND_ABH_3 = 1D;
    private static final Double UPDATED_INTEREST_SECOND_ABH_3 = 2D;
    private static final Double SMALLER_INTEREST_SECOND_ABH_3 = 1D - 1D;

    private static final Double DEFAULT_INTEREST_ABOVE_3_LAKH = 1D;
    private static final Double UPDATED_INTEREST_ABOVE_3_LAKH = 2D;
    private static final Double SMALLER_INTEREST_ABOVE_3_LAKH = 1D - 1D;

    private static final Double DEFAULT_PANJABRAO_INT_3 = 1D;
    private static final Double UPDATED_PANJABRAO_INT_3 = 2D;
    private static final Double SMALLER_PANJABRAO_INT_3 = 1D - 1D;

    private static final Integer DEFAULT_IS_RECOVER = 1;
    private static final Integer UPDATED_IS_RECOVER = 2;
    private static final Integer SMALLER_IS_RECOVER = 1 - 1;

    private static final Long DEFAULT_ABH_3_LAKH_AMT = 1L;
    private static final Long UPDATED_ABH_3_LAKH_AMT = 2L;
    private static final Long SMALLER_ABH_3_LAKH_AMT = 1L - 1L;

    private static final Integer DEFAULT_UPTO_50000 = 1;
    private static final Integer UPDATED_UPTO_50000 = 2;
    private static final Integer SMALLER_UPTO_50000 = 1 - 1;

    private static final String ENTITY_API_URL = "/api/is-calculate-temps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IsCalculateTempRepository isCalculateTempRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIsCalculateTempMockMvc;

    private IsCalculateTemp isCalculateTemp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsCalculateTemp createEntity(EntityManager em) {
        IsCalculateTemp isCalculateTemp = new IsCalculateTemp()
            .serialNo(DEFAULT_SERIAL_NO)
            .financialYear(DEFAULT_FINANCIAL_YEAR)
            .issFileParserId(DEFAULT_ISS_FILE_PARSER_ID)
            .branchCode(DEFAULT_BRANCH_CODE)
            .loanAccountNumberKcc(DEFAULT_LOAN_ACCOUNT_NUMBER_KCC)
            .farmerName(DEFAULT_FARMER_NAME)
            .gender(DEFAULT_GENDER)
            .aadharNumber(DEFAULT_AADHAR_NUMBER)
            .mobileNo(DEFAULT_MOBILE_NO)
            .farmerType(DEFAULT_FARMER_TYPE)
            .socialCategory(DEFAULT_SOCIAL_CATEGORY)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .loanSanctionDate(DEFAULT_LOAN_SANCTION_DATE)
            .loanSanctionAmount(DEFAULT_LOAN_SANCTION_AMOUNT)
            .disbursementDate(DEFAULT_DISBURSEMENT_DATE)
            .disburseAmount(DEFAULT_DISBURSE_AMOUNT)
            .maturityLoanDate(DEFAULT_MATURITY_LOAN_DATE)
            .bankDate(DEFAULT_BANK_DATE)
            .cropName(DEFAULT_CROP_NAME)
            .recoveryAmount(DEFAULT_RECOVERY_AMOUNT)
            .recoveryInterest(DEFAULT_RECOVERY_INTEREST)
            .recoveryDate(DEFAULT_RECOVERY_DATE)
            .balanceAmount(DEFAULT_BALANCE_AMOUNT)
            .prevDays(DEFAULT_PREV_DAYS)
            .presDays(DEFAULT_PRES_DAYS)
            .actualDays(DEFAULT_ACTUAL_DAYS)
            .nProd(DEFAULT_N_PROD)
            .productAmount(DEFAULT_PRODUCT_AMOUNT)
            .productBank(DEFAULT_PRODUCT_BANK)
            .productAbh3Lakh(DEFAULT_PRODUCT_ABH_3_LAKH)
            .interestFirst15(DEFAULT_INTEREST_FIRST_15)
            .interestFirst25(DEFAULT_INTEREST_FIRST_25)
            .interestSecond15(DEFAULT_INTEREST_SECOND_15)
            .interestSecond25(DEFAULT_INTEREST_SECOND_25)
            .interestStateFirst3(DEFAULT_INTEREST_STATE_FIRST_3)
            .interestStateSecond3(DEFAULT_INTEREST_STATE_SECOND_3)
            .interestFirstAbh3(DEFAULT_INTEREST_FIRST_ABH_3)
            .interestSecondAbh3(DEFAULT_INTEREST_SECOND_ABH_3)
            .interestAbove3Lakh(DEFAULT_INTEREST_ABOVE_3_LAKH)
            .panjabraoInt3(DEFAULT_PANJABRAO_INT_3)
            .isRecover(DEFAULT_IS_RECOVER)
            .abh3LakhAmt(DEFAULT_ABH_3_LAKH_AMT)
            .upto50000(DEFAULT_UPTO_50000);
        return isCalculateTemp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsCalculateTemp createUpdatedEntity(EntityManager em) {
        IsCalculateTemp isCalculateTemp = new IsCalculateTemp()
            .serialNo(UPDATED_SERIAL_NO)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .issFileParserId(UPDATED_ISS_FILE_PARSER_ID)
            .branchCode(UPDATED_BRANCH_CODE)
            .loanAccountNumberKcc(UPDATED_LOAN_ACCOUNT_NUMBER_KCC)
            .farmerName(UPDATED_FARMER_NAME)
            .gender(UPDATED_GENDER)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .mobileNo(UPDATED_MOBILE_NO)
            .farmerType(UPDATED_FARMER_TYPE)
            .socialCategory(UPDATED_SOCIAL_CATEGORY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .loanSanctionDate(UPDATED_LOAN_SANCTION_DATE)
            .loanSanctionAmount(UPDATED_LOAN_SANCTION_AMOUNT)
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .disburseAmount(UPDATED_DISBURSE_AMOUNT)
            .maturityLoanDate(UPDATED_MATURITY_LOAN_DATE)
            .bankDate(UPDATED_BANK_DATE)
            .cropName(UPDATED_CROP_NAME)
            .recoveryAmount(UPDATED_RECOVERY_AMOUNT)
            .recoveryInterest(UPDATED_RECOVERY_INTEREST)
            .recoveryDate(UPDATED_RECOVERY_DATE)
            .balanceAmount(UPDATED_BALANCE_AMOUNT)
            .prevDays(UPDATED_PREV_DAYS)
            .presDays(UPDATED_PRES_DAYS)
            .actualDays(UPDATED_ACTUAL_DAYS)
            .nProd(UPDATED_N_PROD)
            .productAmount(UPDATED_PRODUCT_AMOUNT)
            .productBank(UPDATED_PRODUCT_BANK)
            .productAbh3Lakh(UPDATED_PRODUCT_ABH_3_LAKH)
            .interestFirst15(UPDATED_INTEREST_FIRST_15)
            .interestFirst25(UPDATED_INTEREST_FIRST_25)
            .interestSecond15(UPDATED_INTEREST_SECOND_15)
            .interestSecond25(UPDATED_INTEREST_SECOND_25)
            .interestStateFirst3(UPDATED_INTEREST_STATE_FIRST_3)
            .interestStateSecond3(UPDATED_INTEREST_STATE_SECOND_3)
            .interestFirstAbh3(UPDATED_INTEREST_FIRST_ABH_3)
            .interestSecondAbh3(UPDATED_INTEREST_SECOND_ABH_3)
            .interestAbove3Lakh(UPDATED_INTEREST_ABOVE_3_LAKH)
            .panjabraoInt3(UPDATED_PANJABRAO_INT_3)
            .isRecover(UPDATED_IS_RECOVER)
            .abh3LakhAmt(UPDATED_ABH_3_LAKH_AMT)
            .upto50000(UPDATED_UPTO_50000);
        return isCalculateTemp;
    }

    @BeforeEach
    public void initTest() {
        isCalculateTemp = createEntity(em);
    }

    @Test
    @Transactional
    void createIsCalculateTemp() throws Exception {
        int databaseSizeBeforeCreate = isCalculateTempRepository.findAll().size();
        // Create the IsCalculateTemp
        restIsCalculateTempMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isCreated());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeCreate + 1);
        IsCalculateTemp testIsCalculateTemp = isCalculateTempList.get(isCalculateTempList.size() - 1);
        assertThat(testIsCalculateTemp.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testIsCalculateTemp.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testIsCalculateTemp.getIssFileParserId()).isEqualTo(DEFAULT_ISS_FILE_PARSER_ID);
        assertThat(testIsCalculateTemp.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testIsCalculateTemp.getLoanAccountNumberKcc()).isEqualTo(DEFAULT_LOAN_ACCOUNT_NUMBER_KCC);
        assertThat(testIsCalculateTemp.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testIsCalculateTemp.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIsCalculateTemp.getAadharNumber()).isEqualTo(DEFAULT_AADHAR_NUMBER);
        assertThat(testIsCalculateTemp.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testIsCalculateTemp.getFarmerType()).isEqualTo(DEFAULT_FARMER_TYPE);
        assertThat(testIsCalculateTemp.getSocialCategory()).isEqualTo(DEFAULT_SOCIAL_CATEGORY);
        assertThat(testIsCalculateTemp.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testIsCalculateTemp.getLoanSanctionDate()).isEqualTo(DEFAULT_LOAN_SANCTION_DATE);
        assertThat(testIsCalculateTemp.getLoanSanctionAmount()).isEqualTo(DEFAULT_LOAN_SANCTION_AMOUNT);
        assertThat(testIsCalculateTemp.getDisbursementDate()).isEqualTo(DEFAULT_DISBURSEMENT_DATE);
        assertThat(testIsCalculateTemp.getDisburseAmount()).isEqualTo(DEFAULT_DISBURSE_AMOUNT);
        assertThat(testIsCalculateTemp.getMaturityLoanDate()).isEqualTo(DEFAULT_MATURITY_LOAN_DATE);
        assertThat(testIsCalculateTemp.getBankDate()).isEqualTo(DEFAULT_BANK_DATE);
        assertThat(testIsCalculateTemp.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testIsCalculateTemp.getRecoveryAmount()).isEqualTo(DEFAULT_RECOVERY_AMOUNT);
        assertThat(testIsCalculateTemp.getRecoveryInterest()).isEqualTo(DEFAULT_RECOVERY_INTEREST);
        assertThat(testIsCalculateTemp.getRecoveryDate()).isEqualTo(DEFAULT_RECOVERY_DATE);
        assertThat(testIsCalculateTemp.getBalanceAmount()).isEqualTo(DEFAULT_BALANCE_AMOUNT);
        assertThat(testIsCalculateTemp.getPrevDays()).isEqualTo(DEFAULT_PREV_DAYS);
        assertThat(testIsCalculateTemp.getPresDays()).isEqualTo(DEFAULT_PRES_DAYS);
        assertThat(testIsCalculateTemp.getActualDays()).isEqualTo(DEFAULT_ACTUAL_DAYS);
        assertThat(testIsCalculateTemp.getnProd()).isEqualTo(DEFAULT_N_PROD);
        assertThat(testIsCalculateTemp.getProductAmount()).isEqualTo(DEFAULT_PRODUCT_AMOUNT);
        assertThat(testIsCalculateTemp.getProductBank()).isEqualTo(DEFAULT_PRODUCT_BANK);
        assertThat(testIsCalculateTemp.getProductAbh3Lakh()).isEqualTo(DEFAULT_PRODUCT_ABH_3_LAKH);
        assertThat(testIsCalculateTemp.getInterestFirst15()).isEqualTo(DEFAULT_INTEREST_FIRST_15);
        assertThat(testIsCalculateTemp.getInterestFirst25()).isEqualTo(DEFAULT_INTEREST_FIRST_25);
        assertThat(testIsCalculateTemp.getInterestSecond15()).isEqualTo(DEFAULT_INTEREST_SECOND_15);
        assertThat(testIsCalculateTemp.getInterestSecond25()).isEqualTo(DEFAULT_INTEREST_SECOND_25);
        assertThat(testIsCalculateTemp.getInterestStateFirst3()).isEqualTo(DEFAULT_INTEREST_STATE_FIRST_3);
        assertThat(testIsCalculateTemp.getInterestStateSecond3()).isEqualTo(DEFAULT_INTEREST_STATE_SECOND_3);
        assertThat(testIsCalculateTemp.getInterestFirstAbh3()).isEqualTo(DEFAULT_INTEREST_FIRST_ABH_3);
        assertThat(testIsCalculateTemp.getInterestSecondAbh3()).isEqualTo(DEFAULT_INTEREST_SECOND_ABH_3);
        assertThat(testIsCalculateTemp.getInterestAbove3Lakh()).isEqualTo(DEFAULT_INTEREST_ABOVE_3_LAKH);
        assertThat(testIsCalculateTemp.getPanjabraoInt3()).isEqualTo(DEFAULT_PANJABRAO_INT_3);
        assertThat(testIsCalculateTemp.getIsRecover()).isEqualTo(DEFAULT_IS_RECOVER);
        assertThat(testIsCalculateTemp.getAbh3LakhAmt()).isEqualTo(DEFAULT_ABH_3_LAKH_AMT);
        assertThat(testIsCalculateTemp.getUpto50000()).isEqualTo(DEFAULT_UPTO_50000);
    }

    @Test
    @Transactional
    void createIsCalculateTempWithExistingId() throws Exception {
        // Create the IsCalculateTemp with an existing ID
        isCalculateTemp.setId(1L);

        int databaseSizeBeforeCreate = isCalculateTempRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsCalculateTempMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIsCalculateTemps() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isCalculateTemp.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].issFileParserId").value(hasItem(DEFAULT_ISS_FILE_PARSER_ID.intValue())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].loanAccountNumberKcc").value(hasItem(DEFAULT_LOAN_ACCOUNT_NUMBER_KCC)))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].aadharNumber").value(hasItem(DEFAULT_AADHAR_NUMBER)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].farmerType").value(hasItem(DEFAULT_FARMER_TYPE)))
            .andExpect(jsonPath("$.[*].socialCategory").value(hasItem(DEFAULT_SOCIAL_CATEGORY)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].loanSanctionDate").value(hasItem(DEFAULT_LOAN_SANCTION_DATE)))
            .andExpect(jsonPath("$.[*].loanSanctionAmount").value(hasItem(DEFAULT_LOAN_SANCTION_AMOUNT)))
            .andExpect(jsonPath("$.[*].disbursementDate").value(hasItem(DEFAULT_DISBURSEMENT_DATE)))
            .andExpect(jsonPath("$.[*].disburseAmount").value(hasItem(DEFAULT_DISBURSE_AMOUNT)))
            .andExpect(jsonPath("$.[*].maturityLoanDate").value(hasItem(DEFAULT_MATURITY_LOAN_DATE)))
            .andExpect(jsonPath("$.[*].bankDate").value(hasItem(DEFAULT_BANK_DATE)))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].recoveryAmount").value(hasItem(DEFAULT_RECOVERY_AMOUNT)))
            .andExpect(jsonPath("$.[*].recoveryInterest").value(hasItem(DEFAULT_RECOVERY_INTEREST)))
            .andExpect(jsonPath("$.[*].recoveryDate").value(hasItem(DEFAULT_RECOVERY_DATE)))
            .andExpect(jsonPath("$.[*].balanceAmount").value(hasItem(DEFAULT_BALANCE_AMOUNT)))
            .andExpect(jsonPath("$.[*].prevDays").value(hasItem(DEFAULT_PREV_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].presDays").value(hasItem(DEFAULT_PRES_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].actualDays").value(hasItem(DEFAULT_ACTUAL_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].nProd").value(hasItem(DEFAULT_N_PROD)))
            .andExpect(jsonPath("$.[*].productAmount").value(hasItem(DEFAULT_PRODUCT_AMOUNT)))
            .andExpect(jsonPath("$.[*].productBank").value(hasItem(DEFAULT_PRODUCT_BANK)))
            .andExpect(jsonPath("$.[*].productAbh3Lakh").value(hasItem(DEFAULT_PRODUCT_ABH_3_LAKH)))
            .andExpect(jsonPath("$.[*].interestFirst15").value(hasItem(DEFAULT_INTEREST_FIRST_15.doubleValue())))
            .andExpect(jsonPath("$.[*].interestFirst25").value(hasItem(DEFAULT_INTEREST_FIRST_25.doubleValue())))
            .andExpect(jsonPath("$.[*].interestSecond15").value(hasItem(DEFAULT_INTEREST_SECOND_15.doubleValue())))
            .andExpect(jsonPath("$.[*].interestSecond25").value(hasItem(DEFAULT_INTEREST_SECOND_25.doubleValue())))
            .andExpect(jsonPath("$.[*].interestStateFirst3").value(hasItem(DEFAULT_INTEREST_STATE_FIRST_3.doubleValue())))
            .andExpect(jsonPath("$.[*].interestStateSecond3").value(hasItem(DEFAULT_INTEREST_STATE_SECOND_3.doubleValue())))
            .andExpect(jsonPath("$.[*].interestFirstAbh3").value(hasItem(DEFAULT_INTEREST_FIRST_ABH_3.doubleValue())))
            .andExpect(jsonPath("$.[*].interestSecondAbh3").value(hasItem(DEFAULT_INTEREST_SECOND_ABH_3.doubleValue())))
            .andExpect(jsonPath("$.[*].interestAbove3Lakh").value(hasItem(DEFAULT_INTEREST_ABOVE_3_LAKH.doubleValue())))
            .andExpect(jsonPath("$.[*].panjabraoInt3").value(hasItem(DEFAULT_PANJABRAO_INT_3.doubleValue())))
            .andExpect(jsonPath("$.[*].isRecover").value(hasItem(DEFAULT_IS_RECOVER)))
            .andExpect(jsonPath("$.[*].abh3LakhAmt").value(hasItem(DEFAULT_ABH_3_LAKH_AMT.intValue())))
            .andExpect(jsonPath("$.[*].upto50000").value(hasItem(DEFAULT_UPTO_50000)));
    }

    @Test
    @Transactional
    void getIsCalculateTemp() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get the isCalculateTemp
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL_ID, isCalculateTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(isCalculateTemp.getId().intValue()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO))
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.issFileParserId").value(DEFAULT_ISS_FILE_PARSER_ID.intValue()))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE))
            .andExpect(jsonPath("$.loanAccountNumberKcc").value(DEFAULT_LOAN_ACCOUNT_NUMBER_KCC))
            .andExpect(jsonPath("$.farmerName").value(DEFAULT_FARMER_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.aadharNumber").value(DEFAULT_AADHAR_NUMBER))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.farmerType").value(DEFAULT_FARMER_TYPE))
            .andExpect(jsonPath("$.socialCategory").value(DEFAULT_SOCIAL_CATEGORY))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.loanSanctionDate").value(DEFAULT_LOAN_SANCTION_DATE))
            .andExpect(jsonPath("$.loanSanctionAmount").value(DEFAULT_LOAN_SANCTION_AMOUNT))
            .andExpect(jsonPath("$.disbursementDate").value(DEFAULT_DISBURSEMENT_DATE))
            .andExpect(jsonPath("$.disburseAmount").value(DEFAULT_DISBURSE_AMOUNT))
            .andExpect(jsonPath("$.maturityLoanDate").value(DEFAULT_MATURITY_LOAN_DATE))
            .andExpect(jsonPath("$.bankDate").value(DEFAULT_BANK_DATE))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME))
            .andExpect(jsonPath("$.recoveryAmount").value(DEFAULT_RECOVERY_AMOUNT))
            .andExpect(jsonPath("$.recoveryInterest").value(DEFAULT_RECOVERY_INTEREST))
            .andExpect(jsonPath("$.recoveryDate").value(DEFAULT_RECOVERY_DATE))
            .andExpect(jsonPath("$.balanceAmount").value(DEFAULT_BALANCE_AMOUNT))
            .andExpect(jsonPath("$.prevDays").value(DEFAULT_PREV_DAYS.intValue()))
            .andExpect(jsonPath("$.presDays").value(DEFAULT_PRES_DAYS.intValue()))
            .andExpect(jsonPath("$.actualDays").value(DEFAULT_ACTUAL_DAYS.intValue()))
            .andExpect(jsonPath("$.nProd").value(DEFAULT_N_PROD))
            .andExpect(jsonPath("$.productAmount").value(DEFAULT_PRODUCT_AMOUNT))
            .andExpect(jsonPath("$.productBank").value(DEFAULT_PRODUCT_BANK))
            .andExpect(jsonPath("$.productAbh3Lakh").value(DEFAULT_PRODUCT_ABH_3_LAKH))
            .andExpect(jsonPath("$.interestFirst15").value(DEFAULT_INTEREST_FIRST_15.doubleValue()))
            .andExpect(jsonPath("$.interestFirst25").value(DEFAULT_INTEREST_FIRST_25.doubleValue()))
            .andExpect(jsonPath("$.interestSecond15").value(DEFAULT_INTEREST_SECOND_15.doubleValue()))
            .andExpect(jsonPath("$.interestSecond25").value(DEFAULT_INTEREST_SECOND_25.doubleValue()))
            .andExpect(jsonPath("$.interestStateFirst3").value(DEFAULT_INTEREST_STATE_FIRST_3.doubleValue()))
            .andExpect(jsonPath("$.interestStateSecond3").value(DEFAULT_INTEREST_STATE_SECOND_3.doubleValue()))
            .andExpect(jsonPath("$.interestFirstAbh3").value(DEFAULT_INTEREST_FIRST_ABH_3.doubleValue()))
            .andExpect(jsonPath("$.interestSecondAbh3").value(DEFAULT_INTEREST_SECOND_ABH_3.doubleValue()))
            .andExpect(jsonPath("$.interestAbove3Lakh").value(DEFAULT_INTEREST_ABOVE_3_LAKH.doubleValue()))
            .andExpect(jsonPath("$.panjabraoInt3").value(DEFAULT_PANJABRAO_INT_3.doubleValue()))
            .andExpect(jsonPath("$.isRecover").value(DEFAULT_IS_RECOVER))
            .andExpect(jsonPath("$.abh3LakhAmt").value(DEFAULT_ABH_3_LAKH_AMT.intValue()))
            .andExpect(jsonPath("$.upto50000").value(DEFAULT_UPTO_50000));
    }

    @Test
    @Transactional
    void getIsCalculateTempsByIdFiltering() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        Long id = isCalculateTemp.getId();

        defaultIsCalculateTempShouldBeFound("id.equals=" + id);
        defaultIsCalculateTempShouldNotBeFound("id.notEquals=" + id);

        defaultIsCalculateTempShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIsCalculateTempShouldNotBeFound("id.greaterThan=" + id);

        defaultIsCalculateTempShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIsCalculateTempShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo equals to DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.equals=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo equals to UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.equals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo in DEFAULT_SERIAL_NO or UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.in=" + DEFAULT_SERIAL_NO + "," + UPDATED_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo equals to UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.in=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is not null
        defaultIsCalculateTempShouldBeFound("serialNo.specified=true");

        // Get all the isCalculateTempList where serialNo is null
        defaultIsCalculateTempShouldNotBeFound("serialNo.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is greater than or equal to DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.greaterThanOrEqual=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo is greater than or equal to UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.greaterThanOrEqual=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is less than or equal to DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.lessThanOrEqual=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo is less than or equal to SMALLER_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.lessThanOrEqual=" + SMALLER_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is less than DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.lessThan=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo is less than UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.lessThan=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is greater than DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.greaterThan=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo is greater than SMALLER_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.greaterThan=" + SMALLER_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultIsCalculateTempShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the isCalculateTempList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the isCalculateTempList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear is not null
        defaultIsCalculateTempShouldBeFound("financialYear.specified=true");

        // Get all the isCalculateTempList where financialYear is null
        defaultIsCalculateTempShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultIsCalculateTempShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the isCalculateTempList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultIsCalculateTempShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the isCalculateTempList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIssFileParserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where issFileParserId equals to DEFAULT_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldBeFound("issFileParserId.equals=" + DEFAULT_ISS_FILE_PARSER_ID);

        // Get all the isCalculateTempList where issFileParserId equals to UPDATED_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldNotBeFound("issFileParserId.equals=" + UPDATED_ISS_FILE_PARSER_ID);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIssFileParserIdIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where issFileParserId in DEFAULT_ISS_FILE_PARSER_ID or UPDATED_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldBeFound("issFileParserId.in=" + DEFAULT_ISS_FILE_PARSER_ID + "," + UPDATED_ISS_FILE_PARSER_ID);

        // Get all the isCalculateTempList where issFileParserId equals to UPDATED_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldNotBeFound("issFileParserId.in=" + UPDATED_ISS_FILE_PARSER_ID);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIssFileParserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where issFileParserId is not null
        defaultIsCalculateTempShouldBeFound("issFileParserId.specified=true");

        // Get all the isCalculateTempList where issFileParserId is null
        defaultIsCalculateTempShouldNotBeFound("issFileParserId.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIssFileParserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where issFileParserId is greater than or equal to DEFAULT_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldBeFound("issFileParserId.greaterThanOrEqual=" + DEFAULT_ISS_FILE_PARSER_ID);

        // Get all the isCalculateTempList where issFileParserId is greater than or equal to UPDATED_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldNotBeFound("issFileParserId.greaterThanOrEqual=" + UPDATED_ISS_FILE_PARSER_ID);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIssFileParserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where issFileParserId is less than or equal to DEFAULT_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldBeFound("issFileParserId.lessThanOrEqual=" + DEFAULT_ISS_FILE_PARSER_ID);

        // Get all the isCalculateTempList where issFileParserId is less than or equal to SMALLER_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldNotBeFound("issFileParserId.lessThanOrEqual=" + SMALLER_ISS_FILE_PARSER_ID);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIssFileParserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where issFileParserId is less than DEFAULT_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldNotBeFound("issFileParserId.lessThan=" + DEFAULT_ISS_FILE_PARSER_ID);

        // Get all the isCalculateTempList where issFileParserId is less than UPDATED_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldBeFound("issFileParserId.lessThan=" + UPDATED_ISS_FILE_PARSER_ID);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIssFileParserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where issFileParserId is greater than DEFAULT_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldNotBeFound("issFileParserId.greaterThan=" + DEFAULT_ISS_FILE_PARSER_ID);

        // Get all the isCalculateTempList where issFileParserId is greater than SMALLER_ISS_FILE_PARSER_ID
        defaultIsCalculateTempShouldBeFound("issFileParserId.greaterThan=" + SMALLER_ISS_FILE_PARSER_ID);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where branchCode equals to DEFAULT_BRANCH_CODE
        defaultIsCalculateTempShouldBeFound("branchCode.equals=" + DEFAULT_BRANCH_CODE);

        // Get all the isCalculateTempList where branchCode equals to UPDATED_BRANCH_CODE
        defaultIsCalculateTempShouldNotBeFound("branchCode.equals=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where branchCode in DEFAULT_BRANCH_CODE or UPDATED_BRANCH_CODE
        defaultIsCalculateTempShouldBeFound("branchCode.in=" + DEFAULT_BRANCH_CODE + "," + UPDATED_BRANCH_CODE);

        // Get all the isCalculateTempList where branchCode equals to UPDATED_BRANCH_CODE
        defaultIsCalculateTempShouldNotBeFound("branchCode.in=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where branchCode is not null
        defaultIsCalculateTempShouldBeFound("branchCode.specified=true");

        // Get all the isCalculateTempList where branchCode is null
        defaultIsCalculateTempShouldNotBeFound("branchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBranchCodeContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where branchCode contains DEFAULT_BRANCH_CODE
        defaultIsCalculateTempShouldBeFound("branchCode.contains=" + DEFAULT_BRANCH_CODE);

        // Get all the isCalculateTempList where branchCode contains UPDATED_BRANCH_CODE
        defaultIsCalculateTempShouldNotBeFound("branchCode.contains=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBranchCodeNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where branchCode does not contain DEFAULT_BRANCH_CODE
        defaultIsCalculateTempShouldNotBeFound("branchCode.doesNotContain=" + DEFAULT_BRANCH_CODE);

        // Get all the isCalculateTempList where branchCode does not contain UPDATED_BRANCH_CODE
        defaultIsCalculateTempShouldBeFound("branchCode.doesNotContain=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanAccountNumberKccIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanAccountNumberKcc equals to DEFAULT_LOAN_ACCOUNT_NUMBER_KCC
        defaultIsCalculateTempShouldBeFound("loanAccountNumberKcc.equals=" + DEFAULT_LOAN_ACCOUNT_NUMBER_KCC);

        // Get all the isCalculateTempList where loanAccountNumberKcc equals to UPDATED_LOAN_ACCOUNT_NUMBER_KCC
        defaultIsCalculateTempShouldNotBeFound("loanAccountNumberKcc.equals=" + UPDATED_LOAN_ACCOUNT_NUMBER_KCC);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanAccountNumberKccIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanAccountNumberKcc in DEFAULT_LOAN_ACCOUNT_NUMBER_KCC or UPDATED_LOAN_ACCOUNT_NUMBER_KCC
        defaultIsCalculateTempShouldBeFound(
            "loanAccountNumberKcc.in=" + DEFAULT_LOAN_ACCOUNT_NUMBER_KCC + "," + UPDATED_LOAN_ACCOUNT_NUMBER_KCC
        );

        // Get all the isCalculateTempList where loanAccountNumberKcc equals to UPDATED_LOAN_ACCOUNT_NUMBER_KCC
        defaultIsCalculateTempShouldNotBeFound("loanAccountNumberKcc.in=" + UPDATED_LOAN_ACCOUNT_NUMBER_KCC);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanAccountNumberKccIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanAccountNumberKcc is not null
        defaultIsCalculateTempShouldBeFound("loanAccountNumberKcc.specified=true");

        // Get all the isCalculateTempList where loanAccountNumberKcc is null
        defaultIsCalculateTempShouldNotBeFound("loanAccountNumberKcc.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanAccountNumberKccContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanAccountNumberKcc contains DEFAULT_LOAN_ACCOUNT_NUMBER_KCC
        defaultIsCalculateTempShouldBeFound("loanAccountNumberKcc.contains=" + DEFAULT_LOAN_ACCOUNT_NUMBER_KCC);

        // Get all the isCalculateTempList where loanAccountNumberKcc contains UPDATED_LOAN_ACCOUNT_NUMBER_KCC
        defaultIsCalculateTempShouldNotBeFound("loanAccountNumberKcc.contains=" + UPDATED_LOAN_ACCOUNT_NUMBER_KCC);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanAccountNumberKccNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanAccountNumberKcc does not contain DEFAULT_LOAN_ACCOUNT_NUMBER_KCC
        defaultIsCalculateTempShouldNotBeFound("loanAccountNumberKcc.doesNotContain=" + DEFAULT_LOAN_ACCOUNT_NUMBER_KCC);

        // Get all the isCalculateTempList where loanAccountNumberKcc does not contain UPDATED_LOAN_ACCOUNT_NUMBER_KCC
        defaultIsCalculateTempShouldBeFound("loanAccountNumberKcc.doesNotContain=" + UPDATED_LOAN_ACCOUNT_NUMBER_KCC);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerName equals to DEFAULT_FARMER_NAME
        defaultIsCalculateTempShouldBeFound("farmerName.equals=" + DEFAULT_FARMER_NAME);

        // Get all the isCalculateTempList where farmerName equals to UPDATED_FARMER_NAME
        defaultIsCalculateTempShouldNotBeFound("farmerName.equals=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerNameIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerName in DEFAULT_FARMER_NAME or UPDATED_FARMER_NAME
        defaultIsCalculateTempShouldBeFound("farmerName.in=" + DEFAULT_FARMER_NAME + "," + UPDATED_FARMER_NAME);

        // Get all the isCalculateTempList where farmerName equals to UPDATED_FARMER_NAME
        defaultIsCalculateTempShouldNotBeFound("farmerName.in=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerName is not null
        defaultIsCalculateTempShouldBeFound("farmerName.specified=true");

        // Get all the isCalculateTempList where farmerName is null
        defaultIsCalculateTempShouldNotBeFound("farmerName.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerNameContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerName contains DEFAULT_FARMER_NAME
        defaultIsCalculateTempShouldBeFound("farmerName.contains=" + DEFAULT_FARMER_NAME);

        // Get all the isCalculateTempList where farmerName contains UPDATED_FARMER_NAME
        defaultIsCalculateTempShouldNotBeFound("farmerName.contains=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerNameNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerName does not contain DEFAULT_FARMER_NAME
        defaultIsCalculateTempShouldNotBeFound("farmerName.doesNotContain=" + DEFAULT_FARMER_NAME);

        // Get all the isCalculateTempList where farmerName does not contain UPDATED_FARMER_NAME
        defaultIsCalculateTempShouldBeFound("farmerName.doesNotContain=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where gender equals to DEFAULT_GENDER
        defaultIsCalculateTempShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the isCalculateTempList where gender equals to UPDATED_GENDER
        defaultIsCalculateTempShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultIsCalculateTempShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the isCalculateTempList where gender equals to UPDATED_GENDER
        defaultIsCalculateTempShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where gender is not null
        defaultIsCalculateTempShouldBeFound("gender.specified=true");

        // Get all the isCalculateTempList where gender is null
        defaultIsCalculateTempShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByGenderContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where gender contains DEFAULT_GENDER
        defaultIsCalculateTempShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the isCalculateTempList where gender contains UPDATED_GENDER
        defaultIsCalculateTempShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where gender does not contain DEFAULT_GENDER
        defaultIsCalculateTempShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the isCalculateTempList where gender does not contain UPDATED_GENDER
        defaultIsCalculateTempShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAadharNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where aadharNumber equals to DEFAULT_AADHAR_NUMBER
        defaultIsCalculateTempShouldBeFound("aadharNumber.equals=" + DEFAULT_AADHAR_NUMBER);

        // Get all the isCalculateTempList where aadharNumber equals to UPDATED_AADHAR_NUMBER
        defaultIsCalculateTempShouldNotBeFound("aadharNumber.equals=" + UPDATED_AADHAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAadharNumberIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where aadharNumber in DEFAULT_AADHAR_NUMBER or UPDATED_AADHAR_NUMBER
        defaultIsCalculateTempShouldBeFound("aadharNumber.in=" + DEFAULT_AADHAR_NUMBER + "," + UPDATED_AADHAR_NUMBER);

        // Get all the isCalculateTempList where aadharNumber equals to UPDATED_AADHAR_NUMBER
        defaultIsCalculateTempShouldNotBeFound("aadharNumber.in=" + UPDATED_AADHAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAadharNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where aadharNumber is not null
        defaultIsCalculateTempShouldBeFound("aadharNumber.specified=true");

        // Get all the isCalculateTempList where aadharNumber is null
        defaultIsCalculateTempShouldNotBeFound("aadharNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAadharNumberContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where aadharNumber contains DEFAULT_AADHAR_NUMBER
        defaultIsCalculateTempShouldBeFound("aadharNumber.contains=" + DEFAULT_AADHAR_NUMBER);

        // Get all the isCalculateTempList where aadharNumber contains UPDATED_AADHAR_NUMBER
        defaultIsCalculateTempShouldNotBeFound("aadharNumber.contains=" + UPDATED_AADHAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAadharNumberNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where aadharNumber does not contain DEFAULT_AADHAR_NUMBER
        defaultIsCalculateTempShouldNotBeFound("aadharNumber.doesNotContain=" + DEFAULT_AADHAR_NUMBER);

        // Get all the isCalculateTempList where aadharNumber does not contain UPDATED_AADHAR_NUMBER
        defaultIsCalculateTempShouldBeFound("aadharNumber.doesNotContain=" + UPDATED_AADHAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultIsCalculateTempShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the isCalculateTempList where mobileNo equals to UPDATED_MOBILE_NO
        defaultIsCalculateTempShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultIsCalculateTempShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the isCalculateTempList where mobileNo equals to UPDATED_MOBILE_NO
        defaultIsCalculateTempShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where mobileNo is not null
        defaultIsCalculateTempShouldBeFound("mobileNo.specified=true");

        // Get all the isCalculateTempList where mobileNo is null
        defaultIsCalculateTempShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where mobileNo contains DEFAULT_MOBILE_NO
        defaultIsCalculateTempShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the isCalculateTempList where mobileNo contains UPDATED_MOBILE_NO
        defaultIsCalculateTempShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultIsCalculateTempShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the isCalculateTempList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultIsCalculateTempShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerType equals to DEFAULT_FARMER_TYPE
        defaultIsCalculateTempShouldBeFound("farmerType.equals=" + DEFAULT_FARMER_TYPE);

        // Get all the isCalculateTempList where farmerType equals to UPDATED_FARMER_TYPE
        defaultIsCalculateTempShouldNotBeFound("farmerType.equals=" + UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerTypeIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerType in DEFAULT_FARMER_TYPE or UPDATED_FARMER_TYPE
        defaultIsCalculateTempShouldBeFound("farmerType.in=" + DEFAULT_FARMER_TYPE + "," + UPDATED_FARMER_TYPE);

        // Get all the isCalculateTempList where farmerType equals to UPDATED_FARMER_TYPE
        defaultIsCalculateTempShouldNotBeFound("farmerType.in=" + UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerType is not null
        defaultIsCalculateTempShouldBeFound("farmerType.specified=true");

        // Get all the isCalculateTempList where farmerType is null
        defaultIsCalculateTempShouldNotBeFound("farmerType.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerTypeContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerType contains DEFAULT_FARMER_TYPE
        defaultIsCalculateTempShouldBeFound("farmerType.contains=" + DEFAULT_FARMER_TYPE);

        // Get all the isCalculateTempList where farmerType contains UPDATED_FARMER_TYPE
        defaultIsCalculateTempShouldNotBeFound("farmerType.contains=" + UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFarmerTypeNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where farmerType does not contain DEFAULT_FARMER_TYPE
        defaultIsCalculateTempShouldNotBeFound("farmerType.doesNotContain=" + DEFAULT_FARMER_TYPE);

        // Get all the isCalculateTempList where farmerType does not contain UPDATED_FARMER_TYPE
        defaultIsCalculateTempShouldBeFound("farmerType.doesNotContain=" + UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySocialCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where socialCategory equals to DEFAULT_SOCIAL_CATEGORY
        defaultIsCalculateTempShouldBeFound("socialCategory.equals=" + DEFAULT_SOCIAL_CATEGORY);

        // Get all the isCalculateTempList where socialCategory equals to UPDATED_SOCIAL_CATEGORY
        defaultIsCalculateTempShouldNotBeFound("socialCategory.equals=" + UPDATED_SOCIAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySocialCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where socialCategory in DEFAULT_SOCIAL_CATEGORY or UPDATED_SOCIAL_CATEGORY
        defaultIsCalculateTempShouldBeFound("socialCategory.in=" + DEFAULT_SOCIAL_CATEGORY + "," + UPDATED_SOCIAL_CATEGORY);

        // Get all the isCalculateTempList where socialCategory equals to UPDATED_SOCIAL_CATEGORY
        defaultIsCalculateTempShouldNotBeFound("socialCategory.in=" + UPDATED_SOCIAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySocialCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where socialCategory is not null
        defaultIsCalculateTempShouldBeFound("socialCategory.specified=true");

        // Get all the isCalculateTempList where socialCategory is null
        defaultIsCalculateTempShouldNotBeFound("socialCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySocialCategoryContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where socialCategory contains DEFAULT_SOCIAL_CATEGORY
        defaultIsCalculateTempShouldBeFound("socialCategory.contains=" + DEFAULT_SOCIAL_CATEGORY);

        // Get all the isCalculateTempList where socialCategory contains UPDATED_SOCIAL_CATEGORY
        defaultIsCalculateTempShouldNotBeFound("socialCategory.contains=" + UPDATED_SOCIAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySocialCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where socialCategory does not contain DEFAULT_SOCIAL_CATEGORY
        defaultIsCalculateTempShouldNotBeFound("socialCategory.doesNotContain=" + DEFAULT_SOCIAL_CATEGORY);

        // Get all the isCalculateTempList where socialCategory does not contain UPDATED_SOCIAL_CATEGORY
        defaultIsCalculateTempShouldBeFound("socialCategory.doesNotContain=" + UPDATED_SOCIAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultIsCalculateTempShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the isCalculateTempList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultIsCalculateTempShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultIsCalculateTempShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the isCalculateTempList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultIsCalculateTempShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where accountNumber is not null
        defaultIsCalculateTempShouldBeFound("accountNumber.specified=true");

        // Get all the isCalculateTempList where accountNumber is null
        defaultIsCalculateTempShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultIsCalculateTempShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the isCalculateTempList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultIsCalculateTempShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultIsCalculateTempShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the isCalculateTempList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultIsCalculateTempShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionDate equals to DEFAULT_LOAN_SANCTION_DATE
        defaultIsCalculateTempShouldBeFound("loanSanctionDate.equals=" + DEFAULT_LOAN_SANCTION_DATE);

        // Get all the isCalculateTempList where loanSanctionDate equals to UPDATED_LOAN_SANCTION_DATE
        defaultIsCalculateTempShouldNotBeFound("loanSanctionDate.equals=" + UPDATED_LOAN_SANCTION_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionDateIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionDate in DEFAULT_LOAN_SANCTION_DATE or UPDATED_LOAN_SANCTION_DATE
        defaultIsCalculateTempShouldBeFound("loanSanctionDate.in=" + DEFAULT_LOAN_SANCTION_DATE + "," + UPDATED_LOAN_SANCTION_DATE);

        // Get all the isCalculateTempList where loanSanctionDate equals to UPDATED_LOAN_SANCTION_DATE
        defaultIsCalculateTempShouldNotBeFound("loanSanctionDate.in=" + UPDATED_LOAN_SANCTION_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionDate is not null
        defaultIsCalculateTempShouldBeFound("loanSanctionDate.specified=true");

        // Get all the isCalculateTempList where loanSanctionDate is null
        defaultIsCalculateTempShouldNotBeFound("loanSanctionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionDateContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionDate contains DEFAULT_LOAN_SANCTION_DATE
        defaultIsCalculateTempShouldBeFound("loanSanctionDate.contains=" + DEFAULT_LOAN_SANCTION_DATE);

        // Get all the isCalculateTempList where loanSanctionDate contains UPDATED_LOAN_SANCTION_DATE
        defaultIsCalculateTempShouldNotBeFound("loanSanctionDate.contains=" + UPDATED_LOAN_SANCTION_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionDateNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionDate does not contain DEFAULT_LOAN_SANCTION_DATE
        defaultIsCalculateTempShouldNotBeFound("loanSanctionDate.doesNotContain=" + DEFAULT_LOAN_SANCTION_DATE);

        // Get all the isCalculateTempList where loanSanctionDate does not contain UPDATED_LOAN_SANCTION_DATE
        defaultIsCalculateTempShouldBeFound("loanSanctionDate.doesNotContain=" + UPDATED_LOAN_SANCTION_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionAmount equals to DEFAULT_LOAN_SANCTION_AMOUNT
        defaultIsCalculateTempShouldBeFound("loanSanctionAmount.equals=" + DEFAULT_LOAN_SANCTION_AMOUNT);

        // Get all the isCalculateTempList where loanSanctionAmount equals to UPDATED_LOAN_SANCTION_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("loanSanctionAmount.equals=" + UPDATED_LOAN_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionAmount in DEFAULT_LOAN_SANCTION_AMOUNT or UPDATED_LOAN_SANCTION_AMOUNT
        defaultIsCalculateTempShouldBeFound("loanSanctionAmount.in=" + DEFAULT_LOAN_SANCTION_AMOUNT + "," + UPDATED_LOAN_SANCTION_AMOUNT);

        // Get all the isCalculateTempList where loanSanctionAmount equals to UPDATED_LOAN_SANCTION_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("loanSanctionAmount.in=" + UPDATED_LOAN_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionAmount is not null
        defaultIsCalculateTempShouldBeFound("loanSanctionAmount.specified=true");

        // Get all the isCalculateTempList where loanSanctionAmount is null
        defaultIsCalculateTempShouldNotBeFound("loanSanctionAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionAmountContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionAmount contains DEFAULT_LOAN_SANCTION_AMOUNT
        defaultIsCalculateTempShouldBeFound("loanSanctionAmount.contains=" + DEFAULT_LOAN_SANCTION_AMOUNT);

        // Get all the isCalculateTempList where loanSanctionAmount contains UPDATED_LOAN_SANCTION_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("loanSanctionAmount.contains=" + UPDATED_LOAN_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByLoanSanctionAmountNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where loanSanctionAmount does not contain DEFAULT_LOAN_SANCTION_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("loanSanctionAmount.doesNotContain=" + DEFAULT_LOAN_SANCTION_AMOUNT);

        // Get all the isCalculateTempList where loanSanctionAmount does not contain UPDATED_LOAN_SANCTION_AMOUNT
        defaultIsCalculateTempShouldBeFound("loanSanctionAmount.doesNotContain=" + UPDATED_LOAN_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisbursementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disbursementDate equals to DEFAULT_DISBURSEMENT_DATE
        defaultIsCalculateTempShouldBeFound("disbursementDate.equals=" + DEFAULT_DISBURSEMENT_DATE);

        // Get all the isCalculateTempList where disbursementDate equals to UPDATED_DISBURSEMENT_DATE
        defaultIsCalculateTempShouldNotBeFound("disbursementDate.equals=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisbursementDateIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disbursementDate in DEFAULT_DISBURSEMENT_DATE or UPDATED_DISBURSEMENT_DATE
        defaultIsCalculateTempShouldBeFound("disbursementDate.in=" + DEFAULT_DISBURSEMENT_DATE + "," + UPDATED_DISBURSEMENT_DATE);

        // Get all the isCalculateTempList where disbursementDate equals to UPDATED_DISBURSEMENT_DATE
        defaultIsCalculateTempShouldNotBeFound("disbursementDate.in=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisbursementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disbursementDate is not null
        defaultIsCalculateTempShouldBeFound("disbursementDate.specified=true");

        // Get all the isCalculateTempList where disbursementDate is null
        defaultIsCalculateTempShouldNotBeFound("disbursementDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisbursementDateContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disbursementDate contains DEFAULT_DISBURSEMENT_DATE
        defaultIsCalculateTempShouldBeFound("disbursementDate.contains=" + DEFAULT_DISBURSEMENT_DATE);

        // Get all the isCalculateTempList where disbursementDate contains UPDATED_DISBURSEMENT_DATE
        defaultIsCalculateTempShouldNotBeFound("disbursementDate.contains=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisbursementDateNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disbursementDate does not contain DEFAULT_DISBURSEMENT_DATE
        defaultIsCalculateTempShouldNotBeFound("disbursementDate.doesNotContain=" + DEFAULT_DISBURSEMENT_DATE);

        // Get all the isCalculateTempList where disbursementDate does not contain UPDATED_DISBURSEMENT_DATE
        defaultIsCalculateTempShouldBeFound("disbursementDate.doesNotContain=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisburseAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disburseAmount equals to DEFAULT_DISBURSE_AMOUNT
        defaultIsCalculateTempShouldBeFound("disburseAmount.equals=" + DEFAULT_DISBURSE_AMOUNT);

        // Get all the isCalculateTempList where disburseAmount equals to UPDATED_DISBURSE_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("disburseAmount.equals=" + UPDATED_DISBURSE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisburseAmountIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disburseAmount in DEFAULT_DISBURSE_AMOUNT or UPDATED_DISBURSE_AMOUNT
        defaultIsCalculateTempShouldBeFound("disburseAmount.in=" + DEFAULT_DISBURSE_AMOUNT + "," + UPDATED_DISBURSE_AMOUNT);

        // Get all the isCalculateTempList where disburseAmount equals to UPDATED_DISBURSE_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("disburseAmount.in=" + UPDATED_DISBURSE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisburseAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disburseAmount is not null
        defaultIsCalculateTempShouldBeFound("disburseAmount.specified=true");

        // Get all the isCalculateTempList where disburseAmount is null
        defaultIsCalculateTempShouldNotBeFound("disburseAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisburseAmountContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disburseAmount contains DEFAULT_DISBURSE_AMOUNT
        defaultIsCalculateTempShouldBeFound("disburseAmount.contains=" + DEFAULT_DISBURSE_AMOUNT);

        // Get all the isCalculateTempList where disburseAmount contains UPDATED_DISBURSE_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("disburseAmount.contains=" + UPDATED_DISBURSE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByDisburseAmountNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where disburseAmount does not contain DEFAULT_DISBURSE_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("disburseAmount.doesNotContain=" + DEFAULT_DISBURSE_AMOUNT);

        // Get all the isCalculateTempList where disburseAmount does not contain UPDATED_DISBURSE_AMOUNT
        defaultIsCalculateTempShouldBeFound("disburseAmount.doesNotContain=" + UPDATED_DISBURSE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMaturityLoanDateIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where maturityLoanDate equals to DEFAULT_MATURITY_LOAN_DATE
        defaultIsCalculateTempShouldBeFound("maturityLoanDate.equals=" + DEFAULT_MATURITY_LOAN_DATE);

        // Get all the isCalculateTempList where maturityLoanDate equals to UPDATED_MATURITY_LOAN_DATE
        defaultIsCalculateTempShouldNotBeFound("maturityLoanDate.equals=" + UPDATED_MATURITY_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMaturityLoanDateIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where maturityLoanDate in DEFAULT_MATURITY_LOAN_DATE or UPDATED_MATURITY_LOAN_DATE
        defaultIsCalculateTempShouldBeFound("maturityLoanDate.in=" + DEFAULT_MATURITY_LOAN_DATE + "," + UPDATED_MATURITY_LOAN_DATE);

        // Get all the isCalculateTempList where maturityLoanDate equals to UPDATED_MATURITY_LOAN_DATE
        defaultIsCalculateTempShouldNotBeFound("maturityLoanDate.in=" + UPDATED_MATURITY_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMaturityLoanDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where maturityLoanDate is not null
        defaultIsCalculateTempShouldBeFound("maturityLoanDate.specified=true");

        // Get all the isCalculateTempList where maturityLoanDate is null
        defaultIsCalculateTempShouldNotBeFound("maturityLoanDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMaturityLoanDateContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where maturityLoanDate contains DEFAULT_MATURITY_LOAN_DATE
        defaultIsCalculateTempShouldBeFound("maturityLoanDate.contains=" + DEFAULT_MATURITY_LOAN_DATE);

        // Get all the isCalculateTempList where maturityLoanDate contains UPDATED_MATURITY_LOAN_DATE
        defaultIsCalculateTempShouldNotBeFound("maturityLoanDate.contains=" + UPDATED_MATURITY_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByMaturityLoanDateNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where maturityLoanDate does not contain DEFAULT_MATURITY_LOAN_DATE
        defaultIsCalculateTempShouldNotBeFound("maturityLoanDate.doesNotContain=" + DEFAULT_MATURITY_LOAN_DATE);

        // Get all the isCalculateTempList where maturityLoanDate does not contain UPDATED_MATURITY_LOAN_DATE
        defaultIsCalculateTempShouldBeFound("maturityLoanDate.doesNotContain=" + UPDATED_MATURITY_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBankDateIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where bankDate equals to DEFAULT_BANK_DATE
        defaultIsCalculateTempShouldBeFound("bankDate.equals=" + DEFAULT_BANK_DATE);

        // Get all the isCalculateTempList where bankDate equals to UPDATED_BANK_DATE
        defaultIsCalculateTempShouldNotBeFound("bankDate.equals=" + UPDATED_BANK_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBankDateIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where bankDate in DEFAULT_BANK_DATE or UPDATED_BANK_DATE
        defaultIsCalculateTempShouldBeFound("bankDate.in=" + DEFAULT_BANK_DATE + "," + UPDATED_BANK_DATE);

        // Get all the isCalculateTempList where bankDate equals to UPDATED_BANK_DATE
        defaultIsCalculateTempShouldNotBeFound("bankDate.in=" + UPDATED_BANK_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBankDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where bankDate is not null
        defaultIsCalculateTempShouldBeFound("bankDate.specified=true");

        // Get all the isCalculateTempList where bankDate is null
        defaultIsCalculateTempShouldNotBeFound("bankDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBankDateContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where bankDate contains DEFAULT_BANK_DATE
        defaultIsCalculateTempShouldBeFound("bankDate.contains=" + DEFAULT_BANK_DATE);

        // Get all the isCalculateTempList where bankDate contains UPDATED_BANK_DATE
        defaultIsCalculateTempShouldNotBeFound("bankDate.contains=" + UPDATED_BANK_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBankDateNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where bankDate does not contain DEFAULT_BANK_DATE
        defaultIsCalculateTempShouldNotBeFound("bankDate.doesNotContain=" + DEFAULT_BANK_DATE);

        // Get all the isCalculateTempList where bankDate does not contain UPDATED_BANK_DATE
        defaultIsCalculateTempShouldBeFound("bankDate.doesNotContain=" + UPDATED_BANK_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByCropNameIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where cropName equals to DEFAULT_CROP_NAME
        defaultIsCalculateTempShouldBeFound("cropName.equals=" + DEFAULT_CROP_NAME);

        // Get all the isCalculateTempList where cropName equals to UPDATED_CROP_NAME
        defaultIsCalculateTempShouldNotBeFound("cropName.equals=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByCropNameIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where cropName in DEFAULT_CROP_NAME or UPDATED_CROP_NAME
        defaultIsCalculateTempShouldBeFound("cropName.in=" + DEFAULT_CROP_NAME + "," + UPDATED_CROP_NAME);

        // Get all the isCalculateTempList where cropName equals to UPDATED_CROP_NAME
        defaultIsCalculateTempShouldNotBeFound("cropName.in=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByCropNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where cropName is not null
        defaultIsCalculateTempShouldBeFound("cropName.specified=true");

        // Get all the isCalculateTempList where cropName is null
        defaultIsCalculateTempShouldNotBeFound("cropName.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByCropNameContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where cropName contains DEFAULT_CROP_NAME
        defaultIsCalculateTempShouldBeFound("cropName.contains=" + DEFAULT_CROP_NAME);

        // Get all the isCalculateTempList where cropName contains UPDATED_CROP_NAME
        defaultIsCalculateTempShouldNotBeFound("cropName.contains=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByCropNameNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where cropName does not contain DEFAULT_CROP_NAME
        defaultIsCalculateTempShouldNotBeFound("cropName.doesNotContain=" + DEFAULT_CROP_NAME);

        // Get all the isCalculateTempList where cropName does not contain UPDATED_CROP_NAME
        defaultIsCalculateTempShouldBeFound("cropName.doesNotContain=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryAmount equals to DEFAULT_RECOVERY_AMOUNT
        defaultIsCalculateTempShouldBeFound("recoveryAmount.equals=" + DEFAULT_RECOVERY_AMOUNT);

        // Get all the isCalculateTempList where recoveryAmount equals to UPDATED_RECOVERY_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("recoveryAmount.equals=" + UPDATED_RECOVERY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryAmountIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryAmount in DEFAULT_RECOVERY_AMOUNT or UPDATED_RECOVERY_AMOUNT
        defaultIsCalculateTempShouldBeFound("recoveryAmount.in=" + DEFAULT_RECOVERY_AMOUNT + "," + UPDATED_RECOVERY_AMOUNT);

        // Get all the isCalculateTempList where recoveryAmount equals to UPDATED_RECOVERY_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("recoveryAmount.in=" + UPDATED_RECOVERY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryAmount is not null
        defaultIsCalculateTempShouldBeFound("recoveryAmount.specified=true");

        // Get all the isCalculateTempList where recoveryAmount is null
        defaultIsCalculateTempShouldNotBeFound("recoveryAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryAmountContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryAmount contains DEFAULT_RECOVERY_AMOUNT
        defaultIsCalculateTempShouldBeFound("recoveryAmount.contains=" + DEFAULT_RECOVERY_AMOUNT);

        // Get all the isCalculateTempList where recoveryAmount contains UPDATED_RECOVERY_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("recoveryAmount.contains=" + UPDATED_RECOVERY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryAmountNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryAmount does not contain DEFAULT_RECOVERY_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("recoveryAmount.doesNotContain=" + DEFAULT_RECOVERY_AMOUNT);

        // Get all the isCalculateTempList where recoveryAmount does not contain UPDATED_RECOVERY_AMOUNT
        defaultIsCalculateTempShouldBeFound("recoveryAmount.doesNotContain=" + UPDATED_RECOVERY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryInterest equals to DEFAULT_RECOVERY_INTEREST
        defaultIsCalculateTempShouldBeFound("recoveryInterest.equals=" + DEFAULT_RECOVERY_INTEREST);

        // Get all the isCalculateTempList where recoveryInterest equals to UPDATED_RECOVERY_INTEREST
        defaultIsCalculateTempShouldNotBeFound("recoveryInterest.equals=" + UPDATED_RECOVERY_INTEREST);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryInterestIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryInterest in DEFAULT_RECOVERY_INTEREST or UPDATED_RECOVERY_INTEREST
        defaultIsCalculateTempShouldBeFound("recoveryInterest.in=" + DEFAULT_RECOVERY_INTEREST + "," + UPDATED_RECOVERY_INTEREST);

        // Get all the isCalculateTempList where recoveryInterest equals to UPDATED_RECOVERY_INTEREST
        defaultIsCalculateTempShouldNotBeFound("recoveryInterest.in=" + UPDATED_RECOVERY_INTEREST);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryInterest is not null
        defaultIsCalculateTempShouldBeFound("recoveryInterest.specified=true");

        // Get all the isCalculateTempList where recoveryInterest is null
        defaultIsCalculateTempShouldNotBeFound("recoveryInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryInterestContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryInterest contains DEFAULT_RECOVERY_INTEREST
        defaultIsCalculateTempShouldBeFound("recoveryInterest.contains=" + DEFAULT_RECOVERY_INTEREST);

        // Get all the isCalculateTempList where recoveryInterest contains UPDATED_RECOVERY_INTEREST
        defaultIsCalculateTempShouldNotBeFound("recoveryInterest.contains=" + UPDATED_RECOVERY_INTEREST);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryInterestNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryInterest does not contain DEFAULT_RECOVERY_INTEREST
        defaultIsCalculateTempShouldNotBeFound("recoveryInterest.doesNotContain=" + DEFAULT_RECOVERY_INTEREST);

        // Get all the isCalculateTempList where recoveryInterest does not contain UPDATED_RECOVERY_INTEREST
        defaultIsCalculateTempShouldBeFound("recoveryInterest.doesNotContain=" + UPDATED_RECOVERY_INTEREST);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryDate equals to DEFAULT_RECOVERY_DATE
        defaultIsCalculateTempShouldBeFound("recoveryDate.equals=" + DEFAULT_RECOVERY_DATE);

        // Get all the isCalculateTempList where recoveryDate equals to UPDATED_RECOVERY_DATE
        defaultIsCalculateTempShouldNotBeFound("recoveryDate.equals=" + UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryDate in DEFAULT_RECOVERY_DATE or UPDATED_RECOVERY_DATE
        defaultIsCalculateTempShouldBeFound("recoveryDate.in=" + DEFAULT_RECOVERY_DATE + "," + UPDATED_RECOVERY_DATE);

        // Get all the isCalculateTempList where recoveryDate equals to UPDATED_RECOVERY_DATE
        defaultIsCalculateTempShouldNotBeFound("recoveryDate.in=" + UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryDate is not null
        defaultIsCalculateTempShouldBeFound("recoveryDate.specified=true");

        // Get all the isCalculateTempList where recoveryDate is null
        defaultIsCalculateTempShouldNotBeFound("recoveryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryDateContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryDate contains DEFAULT_RECOVERY_DATE
        defaultIsCalculateTempShouldBeFound("recoveryDate.contains=" + DEFAULT_RECOVERY_DATE);

        // Get all the isCalculateTempList where recoveryDate contains UPDATED_RECOVERY_DATE
        defaultIsCalculateTempShouldNotBeFound("recoveryDate.contains=" + UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByRecoveryDateNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where recoveryDate does not contain DEFAULT_RECOVERY_DATE
        defaultIsCalculateTempShouldNotBeFound("recoveryDate.doesNotContain=" + DEFAULT_RECOVERY_DATE);

        // Get all the isCalculateTempList where recoveryDate does not contain UPDATED_RECOVERY_DATE
        defaultIsCalculateTempShouldBeFound("recoveryDate.doesNotContain=" + UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBalanceAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where balanceAmount equals to DEFAULT_BALANCE_AMOUNT
        defaultIsCalculateTempShouldBeFound("balanceAmount.equals=" + DEFAULT_BALANCE_AMOUNT);

        // Get all the isCalculateTempList where balanceAmount equals to UPDATED_BALANCE_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("balanceAmount.equals=" + UPDATED_BALANCE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBalanceAmountIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where balanceAmount in DEFAULT_BALANCE_AMOUNT or UPDATED_BALANCE_AMOUNT
        defaultIsCalculateTempShouldBeFound("balanceAmount.in=" + DEFAULT_BALANCE_AMOUNT + "," + UPDATED_BALANCE_AMOUNT);

        // Get all the isCalculateTempList where balanceAmount equals to UPDATED_BALANCE_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("balanceAmount.in=" + UPDATED_BALANCE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBalanceAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where balanceAmount is not null
        defaultIsCalculateTempShouldBeFound("balanceAmount.specified=true");

        // Get all the isCalculateTempList where balanceAmount is null
        defaultIsCalculateTempShouldNotBeFound("balanceAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBalanceAmountContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where balanceAmount contains DEFAULT_BALANCE_AMOUNT
        defaultIsCalculateTempShouldBeFound("balanceAmount.contains=" + DEFAULT_BALANCE_AMOUNT);

        // Get all the isCalculateTempList where balanceAmount contains UPDATED_BALANCE_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("balanceAmount.contains=" + UPDATED_BALANCE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByBalanceAmountNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where balanceAmount does not contain DEFAULT_BALANCE_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("balanceAmount.doesNotContain=" + DEFAULT_BALANCE_AMOUNT);

        // Get all the isCalculateTempList where balanceAmount does not contain UPDATED_BALANCE_AMOUNT
        defaultIsCalculateTempShouldBeFound("balanceAmount.doesNotContain=" + UPDATED_BALANCE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPrevDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where prevDays equals to DEFAULT_PREV_DAYS
        defaultIsCalculateTempShouldBeFound("prevDays.equals=" + DEFAULT_PREV_DAYS);

        // Get all the isCalculateTempList where prevDays equals to UPDATED_PREV_DAYS
        defaultIsCalculateTempShouldNotBeFound("prevDays.equals=" + UPDATED_PREV_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPrevDaysIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where prevDays in DEFAULT_PREV_DAYS or UPDATED_PREV_DAYS
        defaultIsCalculateTempShouldBeFound("prevDays.in=" + DEFAULT_PREV_DAYS + "," + UPDATED_PREV_DAYS);

        // Get all the isCalculateTempList where prevDays equals to UPDATED_PREV_DAYS
        defaultIsCalculateTempShouldNotBeFound("prevDays.in=" + UPDATED_PREV_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPrevDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where prevDays is not null
        defaultIsCalculateTempShouldBeFound("prevDays.specified=true");

        // Get all the isCalculateTempList where prevDays is null
        defaultIsCalculateTempShouldNotBeFound("prevDays.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPrevDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where prevDays is greater than or equal to DEFAULT_PREV_DAYS
        defaultIsCalculateTempShouldBeFound("prevDays.greaterThanOrEqual=" + DEFAULT_PREV_DAYS);

        // Get all the isCalculateTempList where prevDays is greater than or equal to UPDATED_PREV_DAYS
        defaultIsCalculateTempShouldNotBeFound("prevDays.greaterThanOrEqual=" + UPDATED_PREV_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPrevDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where prevDays is less than or equal to DEFAULT_PREV_DAYS
        defaultIsCalculateTempShouldBeFound("prevDays.lessThanOrEqual=" + DEFAULT_PREV_DAYS);

        // Get all the isCalculateTempList where prevDays is less than or equal to SMALLER_PREV_DAYS
        defaultIsCalculateTempShouldNotBeFound("prevDays.lessThanOrEqual=" + SMALLER_PREV_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPrevDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where prevDays is less than DEFAULT_PREV_DAYS
        defaultIsCalculateTempShouldNotBeFound("prevDays.lessThan=" + DEFAULT_PREV_DAYS);

        // Get all the isCalculateTempList where prevDays is less than UPDATED_PREV_DAYS
        defaultIsCalculateTempShouldBeFound("prevDays.lessThan=" + UPDATED_PREV_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPrevDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where prevDays is greater than DEFAULT_PREV_DAYS
        defaultIsCalculateTempShouldNotBeFound("prevDays.greaterThan=" + DEFAULT_PREV_DAYS);

        // Get all the isCalculateTempList where prevDays is greater than SMALLER_PREV_DAYS
        defaultIsCalculateTempShouldBeFound("prevDays.greaterThan=" + SMALLER_PREV_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPresDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where presDays equals to DEFAULT_PRES_DAYS
        defaultIsCalculateTempShouldBeFound("presDays.equals=" + DEFAULT_PRES_DAYS);

        // Get all the isCalculateTempList where presDays equals to UPDATED_PRES_DAYS
        defaultIsCalculateTempShouldNotBeFound("presDays.equals=" + UPDATED_PRES_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPresDaysIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where presDays in DEFAULT_PRES_DAYS or UPDATED_PRES_DAYS
        defaultIsCalculateTempShouldBeFound("presDays.in=" + DEFAULT_PRES_DAYS + "," + UPDATED_PRES_DAYS);

        // Get all the isCalculateTempList where presDays equals to UPDATED_PRES_DAYS
        defaultIsCalculateTempShouldNotBeFound("presDays.in=" + UPDATED_PRES_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPresDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where presDays is not null
        defaultIsCalculateTempShouldBeFound("presDays.specified=true");

        // Get all the isCalculateTempList where presDays is null
        defaultIsCalculateTempShouldNotBeFound("presDays.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPresDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where presDays is greater than or equal to DEFAULT_PRES_DAYS
        defaultIsCalculateTempShouldBeFound("presDays.greaterThanOrEqual=" + DEFAULT_PRES_DAYS);

        // Get all the isCalculateTempList where presDays is greater than or equal to UPDATED_PRES_DAYS
        defaultIsCalculateTempShouldNotBeFound("presDays.greaterThanOrEqual=" + UPDATED_PRES_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPresDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where presDays is less than or equal to DEFAULT_PRES_DAYS
        defaultIsCalculateTempShouldBeFound("presDays.lessThanOrEqual=" + DEFAULT_PRES_DAYS);

        // Get all the isCalculateTempList where presDays is less than or equal to SMALLER_PRES_DAYS
        defaultIsCalculateTempShouldNotBeFound("presDays.lessThanOrEqual=" + SMALLER_PRES_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPresDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where presDays is less than DEFAULT_PRES_DAYS
        defaultIsCalculateTempShouldNotBeFound("presDays.lessThan=" + DEFAULT_PRES_DAYS);

        // Get all the isCalculateTempList where presDays is less than UPDATED_PRES_DAYS
        defaultIsCalculateTempShouldBeFound("presDays.lessThan=" + UPDATED_PRES_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPresDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where presDays is greater than DEFAULT_PRES_DAYS
        defaultIsCalculateTempShouldNotBeFound("presDays.greaterThan=" + DEFAULT_PRES_DAYS);

        // Get all the isCalculateTempList where presDays is greater than SMALLER_PRES_DAYS
        defaultIsCalculateTempShouldBeFound("presDays.greaterThan=" + SMALLER_PRES_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByActualDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where actualDays equals to DEFAULT_ACTUAL_DAYS
        defaultIsCalculateTempShouldBeFound("actualDays.equals=" + DEFAULT_ACTUAL_DAYS);

        // Get all the isCalculateTempList where actualDays equals to UPDATED_ACTUAL_DAYS
        defaultIsCalculateTempShouldNotBeFound("actualDays.equals=" + UPDATED_ACTUAL_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByActualDaysIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where actualDays in DEFAULT_ACTUAL_DAYS or UPDATED_ACTUAL_DAYS
        defaultIsCalculateTempShouldBeFound("actualDays.in=" + DEFAULT_ACTUAL_DAYS + "," + UPDATED_ACTUAL_DAYS);

        // Get all the isCalculateTempList where actualDays equals to UPDATED_ACTUAL_DAYS
        defaultIsCalculateTempShouldNotBeFound("actualDays.in=" + UPDATED_ACTUAL_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByActualDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where actualDays is not null
        defaultIsCalculateTempShouldBeFound("actualDays.specified=true");

        // Get all the isCalculateTempList where actualDays is null
        defaultIsCalculateTempShouldNotBeFound("actualDays.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByActualDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where actualDays is greater than or equal to DEFAULT_ACTUAL_DAYS
        defaultIsCalculateTempShouldBeFound("actualDays.greaterThanOrEqual=" + DEFAULT_ACTUAL_DAYS);

        // Get all the isCalculateTempList where actualDays is greater than or equal to UPDATED_ACTUAL_DAYS
        defaultIsCalculateTempShouldNotBeFound("actualDays.greaterThanOrEqual=" + UPDATED_ACTUAL_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByActualDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where actualDays is less than or equal to DEFAULT_ACTUAL_DAYS
        defaultIsCalculateTempShouldBeFound("actualDays.lessThanOrEqual=" + DEFAULT_ACTUAL_DAYS);

        // Get all the isCalculateTempList where actualDays is less than or equal to SMALLER_ACTUAL_DAYS
        defaultIsCalculateTempShouldNotBeFound("actualDays.lessThanOrEqual=" + SMALLER_ACTUAL_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByActualDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where actualDays is less than DEFAULT_ACTUAL_DAYS
        defaultIsCalculateTempShouldNotBeFound("actualDays.lessThan=" + DEFAULT_ACTUAL_DAYS);

        // Get all the isCalculateTempList where actualDays is less than UPDATED_ACTUAL_DAYS
        defaultIsCalculateTempShouldBeFound("actualDays.lessThan=" + UPDATED_ACTUAL_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByActualDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where actualDays is greater than DEFAULT_ACTUAL_DAYS
        defaultIsCalculateTempShouldNotBeFound("actualDays.greaterThan=" + DEFAULT_ACTUAL_DAYS);

        // Get all the isCalculateTempList where actualDays is greater than SMALLER_ACTUAL_DAYS
        defaultIsCalculateTempShouldBeFound("actualDays.greaterThan=" + SMALLER_ACTUAL_DAYS);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBynProdIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where nProd equals to DEFAULT_N_PROD
        defaultIsCalculateTempShouldBeFound("nProd.equals=" + DEFAULT_N_PROD);

        // Get all the isCalculateTempList where nProd equals to UPDATED_N_PROD
        defaultIsCalculateTempShouldNotBeFound("nProd.equals=" + UPDATED_N_PROD);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBynProdIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where nProd in DEFAULT_N_PROD or UPDATED_N_PROD
        defaultIsCalculateTempShouldBeFound("nProd.in=" + DEFAULT_N_PROD + "," + UPDATED_N_PROD);

        // Get all the isCalculateTempList where nProd equals to UPDATED_N_PROD
        defaultIsCalculateTempShouldNotBeFound("nProd.in=" + UPDATED_N_PROD);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBynProdIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where nProd is not null
        defaultIsCalculateTempShouldBeFound("nProd.specified=true");

        // Get all the isCalculateTempList where nProd is null
        defaultIsCalculateTempShouldNotBeFound("nProd.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBynProdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where nProd is greater than or equal to DEFAULT_N_PROD
        defaultIsCalculateTempShouldBeFound("nProd.greaterThanOrEqual=" + DEFAULT_N_PROD);

        // Get all the isCalculateTempList where nProd is greater than or equal to UPDATED_N_PROD
        defaultIsCalculateTempShouldNotBeFound("nProd.greaterThanOrEqual=" + UPDATED_N_PROD);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBynProdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where nProd is less than or equal to DEFAULT_N_PROD
        defaultIsCalculateTempShouldBeFound("nProd.lessThanOrEqual=" + DEFAULT_N_PROD);

        // Get all the isCalculateTempList where nProd is less than or equal to SMALLER_N_PROD
        defaultIsCalculateTempShouldNotBeFound("nProd.lessThanOrEqual=" + SMALLER_N_PROD);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBynProdIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where nProd is less than DEFAULT_N_PROD
        defaultIsCalculateTempShouldNotBeFound("nProd.lessThan=" + DEFAULT_N_PROD);

        // Get all the isCalculateTempList where nProd is less than UPDATED_N_PROD
        defaultIsCalculateTempShouldBeFound("nProd.lessThan=" + UPDATED_N_PROD);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBynProdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where nProd is greater than DEFAULT_N_PROD
        defaultIsCalculateTempShouldNotBeFound("nProd.greaterThan=" + DEFAULT_N_PROD);

        // Get all the isCalculateTempList where nProd is greater than SMALLER_N_PROD
        defaultIsCalculateTempShouldBeFound("nProd.greaterThan=" + SMALLER_N_PROD);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAmount equals to DEFAULT_PRODUCT_AMOUNT
        defaultIsCalculateTempShouldBeFound("productAmount.equals=" + DEFAULT_PRODUCT_AMOUNT);

        // Get all the isCalculateTempList where productAmount equals to UPDATED_PRODUCT_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("productAmount.equals=" + UPDATED_PRODUCT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAmountIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAmount in DEFAULT_PRODUCT_AMOUNT or UPDATED_PRODUCT_AMOUNT
        defaultIsCalculateTempShouldBeFound("productAmount.in=" + DEFAULT_PRODUCT_AMOUNT + "," + UPDATED_PRODUCT_AMOUNT);

        // Get all the isCalculateTempList where productAmount equals to UPDATED_PRODUCT_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("productAmount.in=" + UPDATED_PRODUCT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAmount is not null
        defaultIsCalculateTempShouldBeFound("productAmount.specified=true");

        // Get all the isCalculateTempList where productAmount is null
        defaultIsCalculateTempShouldNotBeFound("productAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAmountContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAmount contains DEFAULT_PRODUCT_AMOUNT
        defaultIsCalculateTempShouldBeFound("productAmount.contains=" + DEFAULT_PRODUCT_AMOUNT);

        // Get all the isCalculateTempList where productAmount contains UPDATED_PRODUCT_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("productAmount.contains=" + UPDATED_PRODUCT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAmountNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAmount does not contain DEFAULT_PRODUCT_AMOUNT
        defaultIsCalculateTempShouldNotBeFound("productAmount.doesNotContain=" + DEFAULT_PRODUCT_AMOUNT);

        // Get all the isCalculateTempList where productAmount does not contain UPDATED_PRODUCT_AMOUNT
        defaultIsCalculateTempShouldBeFound("productAmount.doesNotContain=" + UPDATED_PRODUCT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductBankIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productBank equals to DEFAULT_PRODUCT_BANK
        defaultIsCalculateTempShouldBeFound("productBank.equals=" + DEFAULT_PRODUCT_BANK);

        // Get all the isCalculateTempList where productBank equals to UPDATED_PRODUCT_BANK
        defaultIsCalculateTempShouldNotBeFound("productBank.equals=" + UPDATED_PRODUCT_BANK);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductBankIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productBank in DEFAULT_PRODUCT_BANK or UPDATED_PRODUCT_BANK
        defaultIsCalculateTempShouldBeFound("productBank.in=" + DEFAULT_PRODUCT_BANK + "," + UPDATED_PRODUCT_BANK);

        // Get all the isCalculateTempList where productBank equals to UPDATED_PRODUCT_BANK
        defaultIsCalculateTempShouldNotBeFound("productBank.in=" + UPDATED_PRODUCT_BANK);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductBankIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productBank is not null
        defaultIsCalculateTempShouldBeFound("productBank.specified=true");

        // Get all the isCalculateTempList where productBank is null
        defaultIsCalculateTempShouldNotBeFound("productBank.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductBankContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productBank contains DEFAULT_PRODUCT_BANK
        defaultIsCalculateTempShouldBeFound("productBank.contains=" + DEFAULT_PRODUCT_BANK);

        // Get all the isCalculateTempList where productBank contains UPDATED_PRODUCT_BANK
        defaultIsCalculateTempShouldNotBeFound("productBank.contains=" + UPDATED_PRODUCT_BANK);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductBankNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productBank does not contain DEFAULT_PRODUCT_BANK
        defaultIsCalculateTempShouldNotBeFound("productBank.doesNotContain=" + DEFAULT_PRODUCT_BANK);

        // Get all the isCalculateTempList where productBank does not contain UPDATED_PRODUCT_BANK
        defaultIsCalculateTempShouldBeFound("productBank.doesNotContain=" + UPDATED_PRODUCT_BANK);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAbh3LakhIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAbh3Lakh equals to DEFAULT_PRODUCT_ABH_3_LAKH
        defaultIsCalculateTempShouldBeFound("productAbh3Lakh.equals=" + DEFAULT_PRODUCT_ABH_3_LAKH);

        // Get all the isCalculateTempList where productAbh3Lakh equals to UPDATED_PRODUCT_ABH_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("productAbh3Lakh.equals=" + UPDATED_PRODUCT_ABH_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAbh3LakhIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAbh3Lakh in DEFAULT_PRODUCT_ABH_3_LAKH or UPDATED_PRODUCT_ABH_3_LAKH
        defaultIsCalculateTempShouldBeFound("productAbh3Lakh.in=" + DEFAULT_PRODUCT_ABH_3_LAKH + "," + UPDATED_PRODUCT_ABH_3_LAKH);

        // Get all the isCalculateTempList where productAbh3Lakh equals to UPDATED_PRODUCT_ABH_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("productAbh3Lakh.in=" + UPDATED_PRODUCT_ABH_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAbh3LakhIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAbh3Lakh is not null
        defaultIsCalculateTempShouldBeFound("productAbh3Lakh.specified=true");

        // Get all the isCalculateTempList where productAbh3Lakh is null
        defaultIsCalculateTempShouldNotBeFound("productAbh3Lakh.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAbh3LakhContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAbh3Lakh contains DEFAULT_PRODUCT_ABH_3_LAKH
        defaultIsCalculateTempShouldBeFound("productAbh3Lakh.contains=" + DEFAULT_PRODUCT_ABH_3_LAKH);

        // Get all the isCalculateTempList where productAbh3Lakh contains UPDATED_PRODUCT_ABH_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("productAbh3Lakh.contains=" + UPDATED_PRODUCT_ABH_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByProductAbh3LakhNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where productAbh3Lakh does not contain DEFAULT_PRODUCT_ABH_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("productAbh3Lakh.doesNotContain=" + DEFAULT_PRODUCT_ABH_3_LAKH);

        // Get all the isCalculateTempList where productAbh3Lakh does not contain UPDATED_PRODUCT_ABH_3_LAKH
        defaultIsCalculateTempShouldBeFound("productAbh3Lakh.doesNotContain=" + UPDATED_PRODUCT_ABH_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst15IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst15 equals to DEFAULT_INTEREST_FIRST_15
        defaultIsCalculateTempShouldBeFound("interestFirst15.equals=" + DEFAULT_INTEREST_FIRST_15);

        // Get all the isCalculateTempList where interestFirst15 equals to UPDATED_INTEREST_FIRST_15
        defaultIsCalculateTempShouldNotBeFound("interestFirst15.equals=" + UPDATED_INTEREST_FIRST_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst15IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst15 in DEFAULT_INTEREST_FIRST_15 or UPDATED_INTEREST_FIRST_15
        defaultIsCalculateTempShouldBeFound("interestFirst15.in=" + DEFAULT_INTEREST_FIRST_15 + "," + UPDATED_INTEREST_FIRST_15);

        // Get all the isCalculateTempList where interestFirst15 equals to UPDATED_INTEREST_FIRST_15
        defaultIsCalculateTempShouldNotBeFound("interestFirst15.in=" + UPDATED_INTEREST_FIRST_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst15IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst15 is not null
        defaultIsCalculateTempShouldBeFound("interestFirst15.specified=true");

        // Get all the isCalculateTempList where interestFirst15 is null
        defaultIsCalculateTempShouldNotBeFound("interestFirst15.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst15IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst15 is greater than or equal to DEFAULT_INTEREST_FIRST_15
        defaultIsCalculateTempShouldBeFound("interestFirst15.greaterThanOrEqual=" + DEFAULT_INTEREST_FIRST_15);

        // Get all the isCalculateTempList where interestFirst15 is greater than or equal to UPDATED_INTEREST_FIRST_15
        defaultIsCalculateTempShouldNotBeFound("interestFirst15.greaterThanOrEqual=" + UPDATED_INTEREST_FIRST_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst15IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst15 is less than or equal to DEFAULT_INTEREST_FIRST_15
        defaultIsCalculateTempShouldBeFound("interestFirst15.lessThanOrEqual=" + DEFAULT_INTEREST_FIRST_15);

        // Get all the isCalculateTempList where interestFirst15 is less than or equal to SMALLER_INTEREST_FIRST_15
        defaultIsCalculateTempShouldNotBeFound("interestFirst15.lessThanOrEqual=" + SMALLER_INTEREST_FIRST_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst15IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst15 is less than DEFAULT_INTEREST_FIRST_15
        defaultIsCalculateTempShouldNotBeFound("interestFirst15.lessThan=" + DEFAULT_INTEREST_FIRST_15);

        // Get all the isCalculateTempList where interestFirst15 is less than UPDATED_INTEREST_FIRST_15
        defaultIsCalculateTempShouldBeFound("interestFirst15.lessThan=" + UPDATED_INTEREST_FIRST_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst15IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst15 is greater than DEFAULT_INTEREST_FIRST_15
        defaultIsCalculateTempShouldNotBeFound("interestFirst15.greaterThan=" + DEFAULT_INTEREST_FIRST_15);

        // Get all the isCalculateTempList where interestFirst15 is greater than SMALLER_INTEREST_FIRST_15
        defaultIsCalculateTempShouldBeFound("interestFirst15.greaterThan=" + SMALLER_INTEREST_FIRST_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst25IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst25 equals to DEFAULT_INTEREST_FIRST_25
        defaultIsCalculateTempShouldBeFound("interestFirst25.equals=" + DEFAULT_INTEREST_FIRST_25);

        // Get all the isCalculateTempList where interestFirst25 equals to UPDATED_INTEREST_FIRST_25
        defaultIsCalculateTempShouldNotBeFound("interestFirst25.equals=" + UPDATED_INTEREST_FIRST_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst25IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst25 in DEFAULT_INTEREST_FIRST_25 or UPDATED_INTEREST_FIRST_25
        defaultIsCalculateTempShouldBeFound("interestFirst25.in=" + DEFAULT_INTEREST_FIRST_25 + "," + UPDATED_INTEREST_FIRST_25);

        // Get all the isCalculateTempList where interestFirst25 equals to UPDATED_INTEREST_FIRST_25
        defaultIsCalculateTempShouldNotBeFound("interestFirst25.in=" + UPDATED_INTEREST_FIRST_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst25IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst25 is not null
        defaultIsCalculateTempShouldBeFound("interestFirst25.specified=true");

        // Get all the isCalculateTempList where interestFirst25 is null
        defaultIsCalculateTempShouldNotBeFound("interestFirst25.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst25IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst25 is greater than or equal to DEFAULT_INTEREST_FIRST_25
        defaultIsCalculateTempShouldBeFound("interestFirst25.greaterThanOrEqual=" + DEFAULT_INTEREST_FIRST_25);

        // Get all the isCalculateTempList where interestFirst25 is greater than or equal to UPDATED_INTEREST_FIRST_25
        defaultIsCalculateTempShouldNotBeFound("interestFirst25.greaterThanOrEqual=" + UPDATED_INTEREST_FIRST_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst25IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst25 is less than or equal to DEFAULT_INTEREST_FIRST_25
        defaultIsCalculateTempShouldBeFound("interestFirst25.lessThanOrEqual=" + DEFAULT_INTEREST_FIRST_25);

        // Get all the isCalculateTempList where interestFirst25 is less than or equal to SMALLER_INTEREST_FIRST_25
        defaultIsCalculateTempShouldNotBeFound("interestFirst25.lessThanOrEqual=" + SMALLER_INTEREST_FIRST_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst25IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst25 is less than DEFAULT_INTEREST_FIRST_25
        defaultIsCalculateTempShouldNotBeFound("interestFirst25.lessThan=" + DEFAULT_INTEREST_FIRST_25);

        // Get all the isCalculateTempList where interestFirst25 is less than UPDATED_INTEREST_FIRST_25
        defaultIsCalculateTempShouldBeFound("interestFirst25.lessThan=" + UPDATED_INTEREST_FIRST_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirst25IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirst25 is greater than DEFAULT_INTEREST_FIRST_25
        defaultIsCalculateTempShouldNotBeFound("interestFirst25.greaterThan=" + DEFAULT_INTEREST_FIRST_25);

        // Get all the isCalculateTempList where interestFirst25 is greater than SMALLER_INTEREST_FIRST_25
        defaultIsCalculateTempShouldBeFound("interestFirst25.greaterThan=" + SMALLER_INTEREST_FIRST_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond15IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond15 equals to DEFAULT_INTEREST_SECOND_15
        defaultIsCalculateTempShouldBeFound("interestSecond15.equals=" + DEFAULT_INTEREST_SECOND_15);

        // Get all the isCalculateTempList where interestSecond15 equals to UPDATED_INTEREST_SECOND_15
        defaultIsCalculateTempShouldNotBeFound("interestSecond15.equals=" + UPDATED_INTEREST_SECOND_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond15IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond15 in DEFAULT_INTEREST_SECOND_15 or UPDATED_INTEREST_SECOND_15
        defaultIsCalculateTempShouldBeFound("interestSecond15.in=" + DEFAULT_INTEREST_SECOND_15 + "," + UPDATED_INTEREST_SECOND_15);

        // Get all the isCalculateTempList where interestSecond15 equals to UPDATED_INTEREST_SECOND_15
        defaultIsCalculateTempShouldNotBeFound("interestSecond15.in=" + UPDATED_INTEREST_SECOND_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond15IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond15 is not null
        defaultIsCalculateTempShouldBeFound("interestSecond15.specified=true");

        // Get all the isCalculateTempList where interestSecond15 is null
        defaultIsCalculateTempShouldNotBeFound("interestSecond15.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond15IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond15 is greater than or equal to DEFAULT_INTEREST_SECOND_15
        defaultIsCalculateTempShouldBeFound("interestSecond15.greaterThanOrEqual=" + DEFAULT_INTEREST_SECOND_15);

        // Get all the isCalculateTempList where interestSecond15 is greater than or equal to UPDATED_INTEREST_SECOND_15
        defaultIsCalculateTempShouldNotBeFound("interestSecond15.greaterThanOrEqual=" + UPDATED_INTEREST_SECOND_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond15IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond15 is less than or equal to DEFAULT_INTEREST_SECOND_15
        defaultIsCalculateTempShouldBeFound("interestSecond15.lessThanOrEqual=" + DEFAULT_INTEREST_SECOND_15);

        // Get all the isCalculateTempList where interestSecond15 is less than or equal to SMALLER_INTEREST_SECOND_15
        defaultIsCalculateTempShouldNotBeFound("interestSecond15.lessThanOrEqual=" + SMALLER_INTEREST_SECOND_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond15IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond15 is less than DEFAULT_INTEREST_SECOND_15
        defaultIsCalculateTempShouldNotBeFound("interestSecond15.lessThan=" + DEFAULT_INTEREST_SECOND_15);

        // Get all the isCalculateTempList where interestSecond15 is less than UPDATED_INTEREST_SECOND_15
        defaultIsCalculateTempShouldBeFound("interestSecond15.lessThan=" + UPDATED_INTEREST_SECOND_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond15IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond15 is greater than DEFAULT_INTEREST_SECOND_15
        defaultIsCalculateTempShouldNotBeFound("interestSecond15.greaterThan=" + DEFAULT_INTEREST_SECOND_15);

        // Get all the isCalculateTempList where interestSecond15 is greater than SMALLER_INTEREST_SECOND_15
        defaultIsCalculateTempShouldBeFound("interestSecond15.greaterThan=" + SMALLER_INTEREST_SECOND_15);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond25IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond25 equals to DEFAULT_INTEREST_SECOND_25
        defaultIsCalculateTempShouldBeFound("interestSecond25.equals=" + DEFAULT_INTEREST_SECOND_25);

        // Get all the isCalculateTempList where interestSecond25 equals to UPDATED_INTEREST_SECOND_25
        defaultIsCalculateTempShouldNotBeFound("interestSecond25.equals=" + UPDATED_INTEREST_SECOND_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond25IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond25 in DEFAULT_INTEREST_SECOND_25 or UPDATED_INTEREST_SECOND_25
        defaultIsCalculateTempShouldBeFound("interestSecond25.in=" + DEFAULT_INTEREST_SECOND_25 + "," + UPDATED_INTEREST_SECOND_25);

        // Get all the isCalculateTempList where interestSecond25 equals to UPDATED_INTEREST_SECOND_25
        defaultIsCalculateTempShouldNotBeFound("interestSecond25.in=" + UPDATED_INTEREST_SECOND_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond25IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond25 is not null
        defaultIsCalculateTempShouldBeFound("interestSecond25.specified=true");

        // Get all the isCalculateTempList where interestSecond25 is null
        defaultIsCalculateTempShouldNotBeFound("interestSecond25.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond25IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond25 is greater than or equal to DEFAULT_INTEREST_SECOND_25
        defaultIsCalculateTempShouldBeFound("interestSecond25.greaterThanOrEqual=" + DEFAULT_INTEREST_SECOND_25);

        // Get all the isCalculateTempList where interestSecond25 is greater than or equal to UPDATED_INTEREST_SECOND_25
        defaultIsCalculateTempShouldNotBeFound("interestSecond25.greaterThanOrEqual=" + UPDATED_INTEREST_SECOND_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond25IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond25 is less than or equal to DEFAULT_INTEREST_SECOND_25
        defaultIsCalculateTempShouldBeFound("interestSecond25.lessThanOrEqual=" + DEFAULT_INTEREST_SECOND_25);

        // Get all the isCalculateTempList where interestSecond25 is less than or equal to SMALLER_INTEREST_SECOND_25
        defaultIsCalculateTempShouldNotBeFound("interestSecond25.lessThanOrEqual=" + SMALLER_INTEREST_SECOND_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond25IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond25 is less than DEFAULT_INTEREST_SECOND_25
        defaultIsCalculateTempShouldNotBeFound("interestSecond25.lessThan=" + DEFAULT_INTEREST_SECOND_25);

        // Get all the isCalculateTempList where interestSecond25 is less than UPDATED_INTEREST_SECOND_25
        defaultIsCalculateTempShouldBeFound("interestSecond25.lessThan=" + UPDATED_INTEREST_SECOND_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecond25IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecond25 is greater than DEFAULT_INTEREST_SECOND_25
        defaultIsCalculateTempShouldNotBeFound("interestSecond25.greaterThan=" + DEFAULT_INTEREST_SECOND_25);

        // Get all the isCalculateTempList where interestSecond25 is greater than SMALLER_INTEREST_SECOND_25
        defaultIsCalculateTempShouldBeFound("interestSecond25.greaterThan=" + SMALLER_INTEREST_SECOND_25);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateFirst3IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateFirst3 equals to DEFAULT_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldBeFound("interestStateFirst3.equals=" + DEFAULT_INTEREST_STATE_FIRST_3);

        // Get all the isCalculateTempList where interestStateFirst3 equals to UPDATED_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldNotBeFound("interestStateFirst3.equals=" + UPDATED_INTEREST_STATE_FIRST_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateFirst3IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateFirst3 in DEFAULT_INTEREST_STATE_FIRST_3 or UPDATED_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldBeFound(
            "interestStateFirst3.in=" + DEFAULT_INTEREST_STATE_FIRST_3 + "," + UPDATED_INTEREST_STATE_FIRST_3
        );

        // Get all the isCalculateTempList where interestStateFirst3 equals to UPDATED_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldNotBeFound("interestStateFirst3.in=" + UPDATED_INTEREST_STATE_FIRST_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateFirst3IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateFirst3 is not null
        defaultIsCalculateTempShouldBeFound("interestStateFirst3.specified=true");

        // Get all the isCalculateTempList where interestStateFirst3 is null
        defaultIsCalculateTempShouldNotBeFound("interestStateFirst3.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateFirst3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateFirst3 is greater than or equal to DEFAULT_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldBeFound("interestStateFirst3.greaterThanOrEqual=" + DEFAULT_INTEREST_STATE_FIRST_3);

        // Get all the isCalculateTempList where interestStateFirst3 is greater than or equal to UPDATED_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldNotBeFound("interestStateFirst3.greaterThanOrEqual=" + UPDATED_INTEREST_STATE_FIRST_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateFirst3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateFirst3 is less than or equal to DEFAULT_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldBeFound("interestStateFirst3.lessThanOrEqual=" + DEFAULT_INTEREST_STATE_FIRST_3);

        // Get all the isCalculateTempList where interestStateFirst3 is less than or equal to SMALLER_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldNotBeFound("interestStateFirst3.lessThanOrEqual=" + SMALLER_INTEREST_STATE_FIRST_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateFirst3IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateFirst3 is less than DEFAULT_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldNotBeFound("interestStateFirst3.lessThan=" + DEFAULT_INTEREST_STATE_FIRST_3);

        // Get all the isCalculateTempList where interestStateFirst3 is less than UPDATED_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldBeFound("interestStateFirst3.lessThan=" + UPDATED_INTEREST_STATE_FIRST_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateFirst3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateFirst3 is greater than DEFAULT_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldNotBeFound("interestStateFirst3.greaterThan=" + DEFAULT_INTEREST_STATE_FIRST_3);

        // Get all the isCalculateTempList where interestStateFirst3 is greater than SMALLER_INTEREST_STATE_FIRST_3
        defaultIsCalculateTempShouldBeFound("interestStateFirst3.greaterThan=" + SMALLER_INTEREST_STATE_FIRST_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateSecond3IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateSecond3 equals to DEFAULT_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldBeFound("interestStateSecond3.equals=" + DEFAULT_INTEREST_STATE_SECOND_3);

        // Get all the isCalculateTempList where interestStateSecond3 equals to UPDATED_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldNotBeFound("interestStateSecond3.equals=" + UPDATED_INTEREST_STATE_SECOND_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateSecond3IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateSecond3 in DEFAULT_INTEREST_STATE_SECOND_3 or UPDATED_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldBeFound(
            "interestStateSecond3.in=" + DEFAULT_INTEREST_STATE_SECOND_3 + "," + UPDATED_INTEREST_STATE_SECOND_3
        );

        // Get all the isCalculateTempList where interestStateSecond3 equals to UPDATED_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldNotBeFound("interestStateSecond3.in=" + UPDATED_INTEREST_STATE_SECOND_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateSecond3IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateSecond3 is not null
        defaultIsCalculateTempShouldBeFound("interestStateSecond3.specified=true");

        // Get all the isCalculateTempList where interestStateSecond3 is null
        defaultIsCalculateTempShouldNotBeFound("interestStateSecond3.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateSecond3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateSecond3 is greater than or equal to DEFAULT_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldBeFound("interestStateSecond3.greaterThanOrEqual=" + DEFAULT_INTEREST_STATE_SECOND_3);

        // Get all the isCalculateTempList where interestStateSecond3 is greater than or equal to UPDATED_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldNotBeFound("interestStateSecond3.greaterThanOrEqual=" + UPDATED_INTEREST_STATE_SECOND_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateSecond3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateSecond3 is less than or equal to DEFAULT_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldBeFound("interestStateSecond3.lessThanOrEqual=" + DEFAULT_INTEREST_STATE_SECOND_3);

        // Get all the isCalculateTempList where interestStateSecond3 is less than or equal to SMALLER_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldNotBeFound("interestStateSecond3.lessThanOrEqual=" + SMALLER_INTEREST_STATE_SECOND_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateSecond3IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateSecond3 is less than DEFAULT_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldNotBeFound("interestStateSecond3.lessThan=" + DEFAULT_INTEREST_STATE_SECOND_3);

        // Get all the isCalculateTempList where interestStateSecond3 is less than UPDATED_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldBeFound("interestStateSecond3.lessThan=" + UPDATED_INTEREST_STATE_SECOND_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestStateSecond3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestStateSecond3 is greater than DEFAULT_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldNotBeFound("interestStateSecond3.greaterThan=" + DEFAULT_INTEREST_STATE_SECOND_3);

        // Get all the isCalculateTempList where interestStateSecond3 is greater than SMALLER_INTEREST_STATE_SECOND_3
        defaultIsCalculateTempShouldBeFound("interestStateSecond3.greaterThan=" + SMALLER_INTEREST_STATE_SECOND_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirstAbh3IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirstAbh3 equals to DEFAULT_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldBeFound("interestFirstAbh3.equals=" + DEFAULT_INTEREST_FIRST_ABH_3);

        // Get all the isCalculateTempList where interestFirstAbh3 equals to UPDATED_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestFirstAbh3.equals=" + UPDATED_INTEREST_FIRST_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirstAbh3IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirstAbh3 in DEFAULT_INTEREST_FIRST_ABH_3 or UPDATED_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldBeFound("interestFirstAbh3.in=" + DEFAULT_INTEREST_FIRST_ABH_3 + "," + UPDATED_INTEREST_FIRST_ABH_3);

        // Get all the isCalculateTempList where interestFirstAbh3 equals to UPDATED_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestFirstAbh3.in=" + UPDATED_INTEREST_FIRST_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirstAbh3IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirstAbh3 is not null
        defaultIsCalculateTempShouldBeFound("interestFirstAbh3.specified=true");

        // Get all the isCalculateTempList where interestFirstAbh3 is null
        defaultIsCalculateTempShouldNotBeFound("interestFirstAbh3.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirstAbh3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirstAbh3 is greater than or equal to DEFAULT_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldBeFound("interestFirstAbh3.greaterThanOrEqual=" + DEFAULT_INTEREST_FIRST_ABH_3);

        // Get all the isCalculateTempList where interestFirstAbh3 is greater than or equal to UPDATED_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestFirstAbh3.greaterThanOrEqual=" + UPDATED_INTEREST_FIRST_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirstAbh3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirstAbh3 is less than or equal to DEFAULT_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldBeFound("interestFirstAbh3.lessThanOrEqual=" + DEFAULT_INTEREST_FIRST_ABH_3);

        // Get all the isCalculateTempList where interestFirstAbh3 is less than or equal to SMALLER_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestFirstAbh3.lessThanOrEqual=" + SMALLER_INTEREST_FIRST_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirstAbh3IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirstAbh3 is less than DEFAULT_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestFirstAbh3.lessThan=" + DEFAULT_INTEREST_FIRST_ABH_3);

        // Get all the isCalculateTempList where interestFirstAbh3 is less than UPDATED_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldBeFound("interestFirstAbh3.lessThan=" + UPDATED_INTEREST_FIRST_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestFirstAbh3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestFirstAbh3 is greater than DEFAULT_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestFirstAbh3.greaterThan=" + DEFAULT_INTEREST_FIRST_ABH_3);

        // Get all the isCalculateTempList where interestFirstAbh3 is greater than SMALLER_INTEREST_FIRST_ABH_3
        defaultIsCalculateTempShouldBeFound("interestFirstAbh3.greaterThan=" + SMALLER_INTEREST_FIRST_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecondAbh3IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecondAbh3 equals to DEFAULT_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldBeFound("interestSecondAbh3.equals=" + DEFAULT_INTEREST_SECOND_ABH_3);

        // Get all the isCalculateTempList where interestSecondAbh3 equals to UPDATED_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestSecondAbh3.equals=" + UPDATED_INTEREST_SECOND_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecondAbh3IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecondAbh3 in DEFAULT_INTEREST_SECOND_ABH_3 or UPDATED_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldBeFound("interestSecondAbh3.in=" + DEFAULT_INTEREST_SECOND_ABH_3 + "," + UPDATED_INTEREST_SECOND_ABH_3);

        // Get all the isCalculateTempList where interestSecondAbh3 equals to UPDATED_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestSecondAbh3.in=" + UPDATED_INTEREST_SECOND_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecondAbh3IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecondAbh3 is not null
        defaultIsCalculateTempShouldBeFound("interestSecondAbh3.specified=true");

        // Get all the isCalculateTempList where interestSecondAbh3 is null
        defaultIsCalculateTempShouldNotBeFound("interestSecondAbh3.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecondAbh3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecondAbh3 is greater than or equal to DEFAULT_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldBeFound("interestSecondAbh3.greaterThanOrEqual=" + DEFAULT_INTEREST_SECOND_ABH_3);

        // Get all the isCalculateTempList where interestSecondAbh3 is greater than or equal to UPDATED_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestSecondAbh3.greaterThanOrEqual=" + UPDATED_INTEREST_SECOND_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecondAbh3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecondAbh3 is less than or equal to DEFAULT_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldBeFound("interestSecondAbh3.lessThanOrEqual=" + DEFAULT_INTEREST_SECOND_ABH_3);

        // Get all the isCalculateTempList where interestSecondAbh3 is less than or equal to SMALLER_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestSecondAbh3.lessThanOrEqual=" + SMALLER_INTEREST_SECOND_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecondAbh3IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecondAbh3 is less than DEFAULT_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestSecondAbh3.lessThan=" + DEFAULT_INTEREST_SECOND_ABH_3);

        // Get all the isCalculateTempList where interestSecondAbh3 is less than UPDATED_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldBeFound("interestSecondAbh3.lessThan=" + UPDATED_INTEREST_SECOND_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestSecondAbh3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestSecondAbh3 is greater than DEFAULT_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldNotBeFound("interestSecondAbh3.greaterThan=" + DEFAULT_INTEREST_SECOND_ABH_3);

        // Get all the isCalculateTempList where interestSecondAbh3 is greater than SMALLER_INTEREST_SECOND_ABH_3
        defaultIsCalculateTempShouldBeFound("interestSecondAbh3.greaterThan=" + SMALLER_INTEREST_SECOND_ABH_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestAbove3LakhIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestAbove3Lakh equals to DEFAULT_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldBeFound("interestAbove3Lakh.equals=" + DEFAULT_INTEREST_ABOVE_3_LAKH);

        // Get all the isCalculateTempList where interestAbove3Lakh equals to UPDATED_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("interestAbove3Lakh.equals=" + UPDATED_INTEREST_ABOVE_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestAbove3LakhIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestAbove3Lakh in DEFAULT_INTEREST_ABOVE_3_LAKH or UPDATED_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldBeFound("interestAbove3Lakh.in=" + DEFAULT_INTEREST_ABOVE_3_LAKH + "," + UPDATED_INTEREST_ABOVE_3_LAKH);

        // Get all the isCalculateTempList where interestAbove3Lakh equals to UPDATED_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("interestAbove3Lakh.in=" + UPDATED_INTEREST_ABOVE_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestAbove3LakhIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestAbove3Lakh is not null
        defaultIsCalculateTempShouldBeFound("interestAbove3Lakh.specified=true");

        // Get all the isCalculateTempList where interestAbove3Lakh is null
        defaultIsCalculateTempShouldNotBeFound("interestAbove3Lakh.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestAbove3LakhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestAbove3Lakh is greater than or equal to DEFAULT_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldBeFound("interestAbove3Lakh.greaterThanOrEqual=" + DEFAULT_INTEREST_ABOVE_3_LAKH);

        // Get all the isCalculateTempList where interestAbove3Lakh is greater than or equal to UPDATED_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("interestAbove3Lakh.greaterThanOrEqual=" + UPDATED_INTEREST_ABOVE_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestAbove3LakhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestAbove3Lakh is less than or equal to DEFAULT_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldBeFound("interestAbove3Lakh.lessThanOrEqual=" + DEFAULT_INTEREST_ABOVE_3_LAKH);

        // Get all the isCalculateTempList where interestAbove3Lakh is less than or equal to SMALLER_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("interestAbove3Lakh.lessThanOrEqual=" + SMALLER_INTEREST_ABOVE_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestAbove3LakhIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestAbove3Lakh is less than DEFAULT_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("interestAbove3Lakh.lessThan=" + DEFAULT_INTEREST_ABOVE_3_LAKH);

        // Get all the isCalculateTempList where interestAbove3Lakh is less than UPDATED_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldBeFound("interestAbove3Lakh.lessThan=" + UPDATED_INTEREST_ABOVE_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByInterestAbove3LakhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where interestAbove3Lakh is greater than DEFAULT_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldNotBeFound("interestAbove3Lakh.greaterThan=" + DEFAULT_INTEREST_ABOVE_3_LAKH);

        // Get all the isCalculateTempList where interestAbove3Lakh is greater than SMALLER_INTEREST_ABOVE_3_LAKH
        defaultIsCalculateTempShouldBeFound("interestAbove3Lakh.greaterThan=" + SMALLER_INTEREST_ABOVE_3_LAKH);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPanjabraoInt3IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where panjabraoInt3 equals to DEFAULT_PANJABRAO_INT_3
        defaultIsCalculateTempShouldBeFound("panjabraoInt3.equals=" + DEFAULT_PANJABRAO_INT_3);

        // Get all the isCalculateTempList where panjabraoInt3 equals to UPDATED_PANJABRAO_INT_3
        defaultIsCalculateTempShouldNotBeFound("panjabraoInt3.equals=" + UPDATED_PANJABRAO_INT_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPanjabraoInt3IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where panjabraoInt3 in DEFAULT_PANJABRAO_INT_3 or UPDATED_PANJABRAO_INT_3
        defaultIsCalculateTempShouldBeFound("panjabraoInt3.in=" + DEFAULT_PANJABRAO_INT_3 + "," + UPDATED_PANJABRAO_INT_3);

        // Get all the isCalculateTempList where panjabraoInt3 equals to UPDATED_PANJABRAO_INT_3
        defaultIsCalculateTempShouldNotBeFound("panjabraoInt3.in=" + UPDATED_PANJABRAO_INT_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPanjabraoInt3IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where panjabraoInt3 is not null
        defaultIsCalculateTempShouldBeFound("panjabraoInt3.specified=true");

        // Get all the isCalculateTempList where panjabraoInt3 is null
        defaultIsCalculateTempShouldNotBeFound("panjabraoInt3.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPanjabraoInt3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where panjabraoInt3 is greater than or equal to DEFAULT_PANJABRAO_INT_3
        defaultIsCalculateTempShouldBeFound("panjabraoInt3.greaterThanOrEqual=" + DEFAULT_PANJABRAO_INT_3);

        // Get all the isCalculateTempList where panjabraoInt3 is greater than or equal to UPDATED_PANJABRAO_INT_3
        defaultIsCalculateTempShouldNotBeFound("panjabraoInt3.greaterThanOrEqual=" + UPDATED_PANJABRAO_INT_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPanjabraoInt3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where panjabraoInt3 is less than or equal to DEFAULT_PANJABRAO_INT_3
        defaultIsCalculateTempShouldBeFound("panjabraoInt3.lessThanOrEqual=" + DEFAULT_PANJABRAO_INT_3);

        // Get all the isCalculateTempList where panjabraoInt3 is less than or equal to SMALLER_PANJABRAO_INT_3
        defaultIsCalculateTempShouldNotBeFound("panjabraoInt3.lessThanOrEqual=" + SMALLER_PANJABRAO_INT_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPanjabraoInt3IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where panjabraoInt3 is less than DEFAULT_PANJABRAO_INT_3
        defaultIsCalculateTempShouldNotBeFound("panjabraoInt3.lessThan=" + DEFAULT_PANJABRAO_INT_3);

        // Get all the isCalculateTempList where panjabraoInt3 is less than UPDATED_PANJABRAO_INT_3
        defaultIsCalculateTempShouldBeFound("panjabraoInt3.lessThan=" + UPDATED_PANJABRAO_INT_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByPanjabraoInt3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where panjabraoInt3 is greater than DEFAULT_PANJABRAO_INT_3
        defaultIsCalculateTempShouldNotBeFound("panjabraoInt3.greaterThan=" + DEFAULT_PANJABRAO_INT_3);

        // Get all the isCalculateTempList where panjabraoInt3 is greater than SMALLER_PANJABRAO_INT_3
        defaultIsCalculateTempShouldBeFound("panjabraoInt3.greaterThan=" + SMALLER_PANJABRAO_INT_3);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIsRecoverIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where isRecover equals to DEFAULT_IS_RECOVER
        defaultIsCalculateTempShouldBeFound("isRecover.equals=" + DEFAULT_IS_RECOVER);

        // Get all the isCalculateTempList where isRecover equals to UPDATED_IS_RECOVER
        defaultIsCalculateTempShouldNotBeFound("isRecover.equals=" + UPDATED_IS_RECOVER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIsRecoverIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where isRecover in DEFAULT_IS_RECOVER or UPDATED_IS_RECOVER
        defaultIsCalculateTempShouldBeFound("isRecover.in=" + DEFAULT_IS_RECOVER + "," + UPDATED_IS_RECOVER);

        // Get all the isCalculateTempList where isRecover equals to UPDATED_IS_RECOVER
        defaultIsCalculateTempShouldNotBeFound("isRecover.in=" + UPDATED_IS_RECOVER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIsRecoverIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where isRecover is not null
        defaultIsCalculateTempShouldBeFound("isRecover.specified=true");

        // Get all the isCalculateTempList where isRecover is null
        defaultIsCalculateTempShouldNotBeFound("isRecover.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIsRecoverIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where isRecover is greater than or equal to DEFAULT_IS_RECOVER
        defaultIsCalculateTempShouldBeFound("isRecover.greaterThanOrEqual=" + DEFAULT_IS_RECOVER);

        // Get all the isCalculateTempList where isRecover is greater than or equal to UPDATED_IS_RECOVER
        defaultIsCalculateTempShouldNotBeFound("isRecover.greaterThanOrEqual=" + UPDATED_IS_RECOVER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIsRecoverIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where isRecover is less than or equal to DEFAULT_IS_RECOVER
        defaultIsCalculateTempShouldBeFound("isRecover.lessThanOrEqual=" + DEFAULT_IS_RECOVER);

        // Get all the isCalculateTempList where isRecover is less than or equal to SMALLER_IS_RECOVER
        defaultIsCalculateTempShouldNotBeFound("isRecover.lessThanOrEqual=" + SMALLER_IS_RECOVER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIsRecoverIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where isRecover is less than DEFAULT_IS_RECOVER
        defaultIsCalculateTempShouldNotBeFound("isRecover.lessThan=" + DEFAULT_IS_RECOVER);

        // Get all the isCalculateTempList where isRecover is less than UPDATED_IS_RECOVER
        defaultIsCalculateTempShouldBeFound("isRecover.lessThan=" + UPDATED_IS_RECOVER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByIsRecoverIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where isRecover is greater than DEFAULT_IS_RECOVER
        defaultIsCalculateTempShouldNotBeFound("isRecover.greaterThan=" + DEFAULT_IS_RECOVER);

        // Get all the isCalculateTempList where isRecover is greater than SMALLER_IS_RECOVER
        defaultIsCalculateTempShouldBeFound("isRecover.greaterThan=" + SMALLER_IS_RECOVER);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAbh3LakhAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where abh3LakhAmt equals to DEFAULT_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldBeFound("abh3LakhAmt.equals=" + DEFAULT_ABH_3_LAKH_AMT);

        // Get all the isCalculateTempList where abh3LakhAmt equals to UPDATED_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldNotBeFound("abh3LakhAmt.equals=" + UPDATED_ABH_3_LAKH_AMT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAbh3LakhAmtIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where abh3LakhAmt in DEFAULT_ABH_3_LAKH_AMT or UPDATED_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldBeFound("abh3LakhAmt.in=" + DEFAULT_ABH_3_LAKH_AMT + "," + UPDATED_ABH_3_LAKH_AMT);

        // Get all the isCalculateTempList where abh3LakhAmt equals to UPDATED_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldNotBeFound("abh3LakhAmt.in=" + UPDATED_ABH_3_LAKH_AMT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAbh3LakhAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where abh3LakhAmt is not null
        defaultIsCalculateTempShouldBeFound("abh3LakhAmt.specified=true");

        // Get all the isCalculateTempList where abh3LakhAmt is null
        defaultIsCalculateTempShouldNotBeFound("abh3LakhAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAbh3LakhAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where abh3LakhAmt is greater than or equal to DEFAULT_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldBeFound("abh3LakhAmt.greaterThanOrEqual=" + DEFAULT_ABH_3_LAKH_AMT);

        // Get all the isCalculateTempList where abh3LakhAmt is greater than or equal to UPDATED_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldNotBeFound("abh3LakhAmt.greaterThanOrEqual=" + UPDATED_ABH_3_LAKH_AMT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAbh3LakhAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where abh3LakhAmt is less than or equal to DEFAULT_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldBeFound("abh3LakhAmt.lessThanOrEqual=" + DEFAULT_ABH_3_LAKH_AMT);

        // Get all the isCalculateTempList where abh3LakhAmt is less than or equal to SMALLER_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldNotBeFound("abh3LakhAmt.lessThanOrEqual=" + SMALLER_ABH_3_LAKH_AMT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAbh3LakhAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where abh3LakhAmt is less than DEFAULT_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldNotBeFound("abh3LakhAmt.lessThan=" + DEFAULT_ABH_3_LAKH_AMT);

        // Get all the isCalculateTempList where abh3LakhAmt is less than UPDATED_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldBeFound("abh3LakhAmt.lessThan=" + UPDATED_ABH_3_LAKH_AMT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByAbh3LakhAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where abh3LakhAmt is greater than DEFAULT_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldNotBeFound("abh3LakhAmt.greaterThan=" + DEFAULT_ABH_3_LAKH_AMT);

        // Get all the isCalculateTempList where abh3LakhAmt is greater than SMALLER_ABH_3_LAKH_AMT
        defaultIsCalculateTempShouldBeFound("abh3LakhAmt.greaterThan=" + SMALLER_ABH_3_LAKH_AMT);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByUpto50000IsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where upto50000 equals to DEFAULT_UPTO_50000
        defaultIsCalculateTempShouldBeFound("upto50000.equals=" + DEFAULT_UPTO_50000);

        // Get all the isCalculateTempList where upto50000 equals to UPDATED_UPTO_50000
        defaultIsCalculateTempShouldNotBeFound("upto50000.equals=" + UPDATED_UPTO_50000);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByUpto50000IsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where upto50000 in DEFAULT_UPTO_50000 or UPDATED_UPTO_50000
        defaultIsCalculateTempShouldBeFound("upto50000.in=" + DEFAULT_UPTO_50000 + "," + UPDATED_UPTO_50000);

        // Get all the isCalculateTempList where upto50000 equals to UPDATED_UPTO_50000
        defaultIsCalculateTempShouldNotBeFound("upto50000.in=" + UPDATED_UPTO_50000);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByUpto50000IsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where upto50000 is not null
        defaultIsCalculateTempShouldBeFound("upto50000.specified=true");

        // Get all the isCalculateTempList where upto50000 is null
        defaultIsCalculateTempShouldNotBeFound("upto50000.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByUpto50000IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where upto50000 is greater than or equal to DEFAULT_UPTO_50000
        defaultIsCalculateTempShouldBeFound("upto50000.greaterThanOrEqual=" + DEFAULT_UPTO_50000);

        // Get all the isCalculateTempList where upto50000 is greater than or equal to UPDATED_UPTO_50000
        defaultIsCalculateTempShouldNotBeFound("upto50000.greaterThanOrEqual=" + UPDATED_UPTO_50000);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByUpto50000IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where upto50000 is less than or equal to DEFAULT_UPTO_50000
        defaultIsCalculateTempShouldBeFound("upto50000.lessThanOrEqual=" + DEFAULT_UPTO_50000);

        // Get all the isCalculateTempList where upto50000 is less than or equal to SMALLER_UPTO_50000
        defaultIsCalculateTempShouldNotBeFound("upto50000.lessThanOrEqual=" + SMALLER_UPTO_50000);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByUpto50000IsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where upto50000 is less than DEFAULT_UPTO_50000
        defaultIsCalculateTempShouldNotBeFound("upto50000.lessThan=" + DEFAULT_UPTO_50000);

        // Get all the isCalculateTempList where upto50000 is less than UPDATED_UPTO_50000
        defaultIsCalculateTempShouldBeFound("upto50000.lessThan=" + UPDATED_UPTO_50000);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByUpto50000IsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where upto50000 is greater than DEFAULT_UPTO_50000
        defaultIsCalculateTempShouldNotBeFound("upto50000.greaterThan=" + DEFAULT_UPTO_50000);

        // Get all the isCalculateTempList where upto50000 is greater than SMALLER_UPTO_50000
        defaultIsCalculateTempShouldBeFound("upto50000.greaterThan=" + SMALLER_UPTO_50000);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIsCalculateTempShouldBeFound(String filter) throws Exception {
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isCalculateTemp.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].issFileParserId").value(hasItem(DEFAULT_ISS_FILE_PARSER_ID.intValue())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].loanAccountNumberKcc").value(hasItem(DEFAULT_LOAN_ACCOUNT_NUMBER_KCC)))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].aadharNumber").value(hasItem(DEFAULT_AADHAR_NUMBER)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].farmerType").value(hasItem(DEFAULT_FARMER_TYPE)))
            .andExpect(jsonPath("$.[*].socialCategory").value(hasItem(DEFAULT_SOCIAL_CATEGORY)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].loanSanctionDate").value(hasItem(DEFAULT_LOAN_SANCTION_DATE)))
            .andExpect(jsonPath("$.[*].loanSanctionAmount").value(hasItem(DEFAULT_LOAN_SANCTION_AMOUNT)))
            .andExpect(jsonPath("$.[*].disbursementDate").value(hasItem(DEFAULT_DISBURSEMENT_DATE)))
            .andExpect(jsonPath("$.[*].disburseAmount").value(hasItem(DEFAULT_DISBURSE_AMOUNT)))
            .andExpect(jsonPath("$.[*].maturityLoanDate").value(hasItem(DEFAULT_MATURITY_LOAN_DATE)))
            .andExpect(jsonPath("$.[*].bankDate").value(hasItem(DEFAULT_BANK_DATE)))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].recoveryAmount").value(hasItem(DEFAULT_RECOVERY_AMOUNT)))
            .andExpect(jsonPath("$.[*].recoveryInterest").value(hasItem(DEFAULT_RECOVERY_INTEREST)))
            .andExpect(jsonPath("$.[*].recoveryDate").value(hasItem(DEFAULT_RECOVERY_DATE)))
            .andExpect(jsonPath("$.[*].balanceAmount").value(hasItem(DEFAULT_BALANCE_AMOUNT)))
            .andExpect(jsonPath("$.[*].prevDays").value(hasItem(DEFAULT_PREV_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].presDays").value(hasItem(DEFAULT_PRES_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].actualDays").value(hasItem(DEFAULT_ACTUAL_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].nProd").value(hasItem(DEFAULT_N_PROD)))
            .andExpect(jsonPath("$.[*].productAmount").value(hasItem(DEFAULT_PRODUCT_AMOUNT)))
            .andExpect(jsonPath("$.[*].productBank").value(hasItem(DEFAULT_PRODUCT_BANK)))
            .andExpect(jsonPath("$.[*].productAbh3Lakh").value(hasItem(DEFAULT_PRODUCT_ABH_3_LAKH)))
            .andExpect(jsonPath("$.[*].interestFirst15").value(hasItem(DEFAULT_INTEREST_FIRST_15.doubleValue())))
            .andExpect(jsonPath("$.[*].interestFirst25").value(hasItem(DEFAULT_INTEREST_FIRST_25.doubleValue())))
            .andExpect(jsonPath("$.[*].interestSecond15").value(hasItem(DEFAULT_INTEREST_SECOND_15.doubleValue())))
            .andExpect(jsonPath("$.[*].interestSecond25").value(hasItem(DEFAULT_INTEREST_SECOND_25.doubleValue())))
            .andExpect(jsonPath("$.[*].interestStateFirst3").value(hasItem(DEFAULT_INTEREST_STATE_FIRST_3.doubleValue())))
            .andExpect(jsonPath("$.[*].interestStateSecond3").value(hasItem(DEFAULT_INTEREST_STATE_SECOND_3.doubleValue())))
            .andExpect(jsonPath("$.[*].interestFirstAbh3").value(hasItem(DEFAULT_INTEREST_FIRST_ABH_3.doubleValue())))
            .andExpect(jsonPath("$.[*].interestSecondAbh3").value(hasItem(DEFAULT_INTEREST_SECOND_ABH_3.doubleValue())))
            .andExpect(jsonPath("$.[*].interestAbove3Lakh").value(hasItem(DEFAULT_INTEREST_ABOVE_3_LAKH.doubleValue())))
            .andExpect(jsonPath("$.[*].panjabraoInt3").value(hasItem(DEFAULT_PANJABRAO_INT_3.doubleValue())))
            .andExpect(jsonPath("$.[*].isRecover").value(hasItem(DEFAULT_IS_RECOVER)))
            .andExpect(jsonPath("$.[*].abh3LakhAmt").value(hasItem(DEFAULT_ABH_3_LAKH_AMT.intValue())))
            .andExpect(jsonPath("$.[*].upto50000").value(hasItem(DEFAULT_UPTO_50000)));

        // Check, that the count call also returns 1
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIsCalculateTempShouldNotBeFound(String filter) throws Exception {
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIsCalculateTemp() throws Exception {
        // Get the isCalculateTemp
        restIsCalculateTempMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIsCalculateTemp() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();

        // Update the isCalculateTemp
        IsCalculateTemp updatedIsCalculateTemp = isCalculateTempRepository.findById(isCalculateTemp.getId()).get();
        // Disconnect from session so that the updates on updatedIsCalculateTemp are not directly saved in db
        em.detach(updatedIsCalculateTemp);
        updatedIsCalculateTemp
            .serialNo(UPDATED_SERIAL_NO)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .issFileParserId(UPDATED_ISS_FILE_PARSER_ID)
            .branchCode(UPDATED_BRANCH_CODE)
            .loanAccountNumberKcc(UPDATED_LOAN_ACCOUNT_NUMBER_KCC)
            .farmerName(UPDATED_FARMER_NAME)
            .gender(UPDATED_GENDER)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .mobileNo(UPDATED_MOBILE_NO)
            .farmerType(UPDATED_FARMER_TYPE)
            .socialCategory(UPDATED_SOCIAL_CATEGORY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .loanSanctionDate(UPDATED_LOAN_SANCTION_DATE)
            .loanSanctionAmount(UPDATED_LOAN_SANCTION_AMOUNT)
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .disburseAmount(UPDATED_DISBURSE_AMOUNT)
            .maturityLoanDate(UPDATED_MATURITY_LOAN_DATE)
            .bankDate(UPDATED_BANK_DATE)
            .cropName(UPDATED_CROP_NAME)
            .recoveryAmount(UPDATED_RECOVERY_AMOUNT)
            .recoveryInterest(UPDATED_RECOVERY_INTEREST)
            .recoveryDate(UPDATED_RECOVERY_DATE)
            .balanceAmount(UPDATED_BALANCE_AMOUNT)
            .prevDays(UPDATED_PREV_DAYS)
            .presDays(UPDATED_PRES_DAYS)
            .actualDays(UPDATED_ACTUAL_DAYS)
            .nProd(UPDATED_N_PROD)
            .productAmount(UPDATED_PRODUCT_AMOUNT)
            .productBank(UPDATED_PRODUCT_BANK)
            .productAbh3Lakh(UPDATED_PRODUCT_ABH_3_LAKH)
            .interestFirst15(UPDATED_INTEREST_FIRST_15)
            .interestFirst25(UPDATED_INTEREST_FIRST_25)
            .interestSecond15(UPDATED_INTEREST_SECOND_15)
            .interestSecond25(UPDATED_INTEREST_SECOND_25)
            .interestStateFirst3(UPDATED_INTEREST_STATE_FIRST_3)
            .interestStateSecond3(UPDATED_INTEREST_STATE_SECOND_3)
            .interestFirstAbh3(UPDATED_INTEREST_FIRST_ABH_3)
            .interestSecondAbh3(UPDATED_INTEREST_SECOND_ABH_3)
            .interestAbove3Lakh(UPDATED_INTEREST_ABOVE_3_LAKH)
            .panjabraoInt3(UPDATED_PANJABRAO_INT_3)
            .isRecover(UPDATED_IS_RECOVER)
            .abh3LakhAmt(UPDATED_ABH_3_LAKH_AMT)
            .upto50000(UPDATED_UPTO_50000);

        restIsCalculateTempMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIsCalculateTemp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIsCalculateTemp))
            )
            .andExpect(status().isOk());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
        IsCalculateTemp testIsCalculateTemp = isCalculateTempList.get(isCalculateTempList.size() - 1);
        assertThat(testIsCalculateTemp.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testIsCalculateTemp.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testIsCalculateTemp.getIssFileParserId()).isEqualTo(UPDATED_ISS_FILE_PARSER_ID);
        assertThat(testIsCalculateTemp.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testIsCalculateTemp.getLoanAccountNumberKcc()).isEqualTo(UPDATED_LOAN_ACCOUNT_NUMBER_KCC);
        assertThat(testIsCalculateTemp.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testIsCalculateTemp.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIsCalculateTemp.getAadharNumber()).isEqualTo(UPDATED_AADHAR_NUMBER);
        assertThat(testIsCalculateTemp.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testIsCalculateTemp.getFarmerType()).isEqualTo(UPDATED_FARMER_TYPE);
        assertThat(testIsCalculateTemp.getSocialCategory()).isEqualTo(UPDATED_SOCIAL_CATEGORY);
        assertThat(testIsCalculateTemp.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testIsCalculateTemp.getLoanSanctionDate()).isEqualTo(UPDATED_LOAN_SANCTION_DATE);
        assertThat(testIsCalculateTemp.getLoanSanctionAmount()).isEqualTo(UPDATED_LOAN_SANCTION_AMOUNT);
        assertThat(testIsCalculateTemp.getDisbursementDate()).isEqualTo(UPDATED_DISBURSEMENT_DATE);
        assertThat(testIsCalculateTemp.getDisburseAmount()).isEqualTo(UPDATED_DISBURSE_AMOUNT);
        assertThat(testIsCalculateTemp.getMaturityLoanDate()).isEqualTo(UPDATED_MATURITY_LOAN_DATE);
        assertThat(testIsCalculateTemp.getBankDate()).isEqualTo(UPDATED_BANK_DATE);
        assertThat(testIsCalculateTemp.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testIsCalculateTemp.getRecoveryAmount()).isEqualTo(UPDATED_RECOVERY_AMOUNT);
        assertThat(testIsCalculateTemp.getRecoveryInterest()).isEqualTo(UPDATED_RECOVERY_INTEREST);
        assertThat(testIsCalculateTemp.getRecoveryDate()).isEqualTo(UPDATED_RECOVERY_DATE);
        assertThat(testIsCalculateTemp.getBalanceAmount()).isEqualTo(UPDATED_BALANCE_AMOUNT);
        assertThat(testIsCalculateTemp.getPrevDays()).isEqualTo(UPDATED_PREV_DAYS);
        assertThat(testIsCalculateTemp.getPresDays()).isEqualTo(UPDATED_PRES_DAYS);
        assertThat(testIsCalculateTemp.getActualDays()).isEqualTo(UPDATED_ACTUAL_DAYS);
        assertThat(testIsCalculateTemp.getnProd()).isEqualTo(UPDATED_N_PROD);
        assertThat(testIsCalculateTemp.getProductAmount()).isEqualTo(UPDATED_PRODUCT_AMOUNT);
        assertThat(testIsCalculateTemp.getProductBank()).isEqualTo(UPDATED_PRODUCT_BANK);
        assertThat(testIsCalculateTemp.getProductAbh3Lakh()).isEqualTo(UPDATED_PRODUCT_ABH_3_LAKH);
        assertThat(testIsCalculateTemp.getInterestFirst15()).isEqualTo(UPDATED_INTEREST_FIRST_15);
        assertThat(testIsCalculateTemp.getInterestFirst25()).isEqualTo(UPDATED_INTEREST_FIRST_25);
        assertThat(testIsCalculateTemp.getInterestSecond15()).isEqualTo(UPDATED_INTEREST_SECOND_15);
        assertThat(testIsCalculateTemp.getInterestSecond25()).isEqualTo(UPDATED_INTEREST_SECOND_25);
        assertThat(testIsCalculateTemp.getInterestStateFirst3()).isEqualTo(UPDATED_INTEREST_STATE_FIRST_3);
        assertThat(testIsCalculateTemp.getInterestStateSecond3()).isEqualTo(UPDATED_INTEREST_STATE_SECOND_3);
        assertThat(testIsCalculateTemp.getInterestFirstAbh3()).isEqualTo(UPDATED_INTEREST_FIRST_ABH_3);
        assertThat(testIsCalculateTemp.getInterestSecondAbh3()).isEqualTo(UPDATED_INTEREST_SECOND_ABH_3);
        assertThat(testIsCalculateTemp.getInterestAbove3Lakh()).isEqualTo(UPDATED_INTEREST_ABOVE_3_LAKH);
        assertThat(testIsCalculateTemp.getPanjabraoInt3()).isEqualTo(UPDATED_PANJABRAO_INT_3);
        assertThat(testIsCalculateTemp.getIsRecover()).isEqualTo(UPDATED_IS_RECOVER);
        assertThat(testIsCalculateTemp.getAbh3LakhAmt()).isEqualTo(UPDATED_ABH_3_LAKH_AMT);
        assertThat(testIsCalculateTemp.getUpto50000()).isEqualTo(UPDATED_UPTO_50000);
    }

    @Test
    @Transactional
    void putNonExistingIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                put(ENTITY_API_URL_ID, isCalculateTemp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIsCalculateTempWithPatch() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();

        // Update the isCalculateTemp using partial update
        IsCalculateTemp partialUpdatedIsCalculateTemp = new IsCalculateTemp();
        partialUpdatedIsCalculateTemp.setId(isCalculateTemp.getId());

        partialUpdatedIsCalculateTemp
            .serialNo(UPDATED_SERIAL_NO)
            .farmerName(UPDATED_FARMER_NAME)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .mobileNo(UPDATED_MOBILE_NO)
            .farmerType(UPDATED_FARMER_TYPE)
            .socialCategory(UPDATED_SOCIAL_CATEGORY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankDate(UPDATED_BANK_DATE)
            .cropName(UPDATED_CROP_NAME)
            .recoveryInterest(UPDATED_RECOVERY_INTEREST)
            .recoveryDate(UPDATED_RECOVERY_DATE)
            .balanceAmount(UPDATED_BALANCE_AMOUNT)
            .prevDays(UPDATED_PREV_DAYS)
            .actualDays(UPDATED_ACTUAL_DAYS)
            .nProd(UPDATED_N_PROD)
            .productAmount(UPDATED_PRODUCT_AMOUNT)
            .productBank(UPDATED_PRODUCT_BANK)
            .interestFirst25(UPDATED_INTEREST_FIRST_25)
            .interestFirstAbh3(UPDATED_INTEREST_FIRST_ABH_3)
            .panjabraoInt3(UPDATED_PANJABRAO_INT_3)
            .abh3LakhAmt(UPDATED_ABH_3_LAKH_AMT);

        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsCalculateTemp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsCalculateTemp))
            )
            .andExpect(status().isOk());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
        IsCalculateTemp testIsCalculateTemp = isCalculateTempList.get(isCalculateTempList.size() - 1);
        assertThat(testIsCalculateTemp.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testIsCalculateTemp.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testIsCalculateTemp.getIssFileParserId()).isEqualTo(DEFAULT_ISS_FILE_PARSER_ID);
        assertThat(testIsCalculateTemp.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testIsCalculateTemp.getLoanAccountNumberKcc()).isEqualTo(DEFAULT_LOAN_ACCOUNT_NUMBER_KCC);
        assertThat(testIsCalculateTemp.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testIsCalculateTemp.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIsCalculateTemp.getAadharNumber()).isEqualTo(UPDATED_AADHAR_NUMBER);
        assertThat(testIsCalculateTemp.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testIsCalculateTemp.getFarmerType()).isEqualTo(UPDATED_FARMER_TYPE);
        assertThat(testIsCalculateTemp.getSocialCategory()).isEqualTo(UPDATED_SOCIAL_CATEGORY);
        assertThat(testIsCalculateTemp.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testIsCalculateTemp.getLoanSanctionDate()).isEqualTo(DEFAULT_LOAN_SANCTION_DATE);
        assertThat(testIsCalculateTemp.getLoanSanctionAmount()).isEqualTo(DEFAULT_LOAN_SANCTION_AMOUNT);
        assertThat(testIsCalculateTemp.getDisbursementDate()).isEqualTo(DEFAULT_DISBURSEMENT_DATE);
        assertThat(testIsCalculateTemp.getDisburseAmount()).isEqualTo(DEFAULT_DISBURSE_AMOUNT);
        assertThat(testIsCalculateTemp.getMaturityLoanDate()).isEqualTo(DEFAULT_MATURITY_LOAN_DATE);
        assertThat(testIsCalculateTemp.getBankDate()).isEqualTo(UPDATED_BANK_DATE);
        assertThat(testIsCalculateTemp.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testIsCalculateTemp.getRecoveryAmount()).isEqualTo(DEFAULT_RECOVERY_AMOUNT);
        assertThat(testIsCalculateTemp.getRecoveryInterest()).isEqualTo(UPDATED_RECOVERY_INTEREST);
        assertThat(testIsCalculateTemp.getRecoveryDate()).isEqualTo(UPDATED_RECOVERY_DATE);
        assertThat(testIsCalculateTemp.getBalanceAmount()).isEqualTo(UPDATED_BALANCE_AMOUNT);
        assertThat(testIsCalculateTemp.getPrevDays()).isEqualTo(UPDATED_PREV_DAYS);
        assertThat(testIsCalculateTemp.getPresDays()).isEqualTo(DEFAULT_PRES_DAYS);
        assertThat(testIsCalculateTemp.getActualDays()).isEqualTo(UPDATED_ACTUAL_DAYS);
        assertThat(testIsCalculateTemp.getnProd()).isEqualTo(UPDATED_N_PROD);
        assertThat(testIsCalculateTemp.getProductAmount()).isEqualTo(UPDATED_PRODUCT_AMOUNT);
        assertThat(testIsCalculateTemp.getProductBank()).isEqualTo(UPDATED_PRODUCT_BANK);
        assertThat(testIsCalculateTemp.getProductAbh3Lakh()).isEqualTo(DEFAULT_PRODUCT_ABH_3_LAKH);
        assertThat(testIsCalculateTemp.getInterestFirst15()).isEqualTo(DEFAULT_INTEREST_FIRST_15);
        assertThat(testIsCalculateTemp.getInterestFirst25()).isEqualTo(UPDATED_INTEREST_FIRST_25);
        assertThat(testIsCalculateTemp.getInterestSecond15()).isEqualTo(DEFAULT_INTEREST_SECOND_15);
        assertThat(testIsCalculateTemp.getInterestSecond25()).isEqualTo(DEFAULT_INTEREST_SECOND_25);
        assertThat(testIsCalculateTemp.getInterestStateFirst3()).isEqualTo(DEFAULT_INTEREST_STATE_FIRST_3);
        assertThat(testIsCalculateTemp.getInterestStateSecond3()).isEqualTo(DEFAULT_INTEREST_STATE_SECOND_3);
        assertThat(testIsCalculateTemp.getInterestFirstAbh3()).isEqualTo(UPDATED_INTEREST_FIRST_ABH_3);
        assertThat(testIsCalculateTemp.getInterestSecondAbh3()).isEqualTo(DEFAULT_INTEREST_SECOND_ABH_3);
        assertThat(testIsCalculateTemp.getInterestAbove3Lakh()).isEqualTo(DEFAULT_INTEREST_ABOVE_3_LAKH);
        assertThat(testIsCalculateTemp.getPanjabraoInt3()).isEqualTo(UPDATED_PANJABRAO_INT_3);
        assertThat(testIsCalculateTemp.getIsRecover()).isEqualTo(DEFAULT_IS_RECOVER);
        assertThat(testIsCalculateTemp.getAbh3LakhAmt()).isEqualTo(UPDATED_ABH_3_LAKH_AMT);
        assertThat(testIsCalculateTemp.getUpto50000()).isEqualTo(DEFAULT_UPTO_50000);
    }

    @Test
    @Transactional
    void fullUpdateIsCalculateTempWithPatch() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();

        // Update the isCalculateTemp using partial update
        IsCalculateTemp partialUpdatedIsCalculateTemp = new IsCalculateTemp();
        partialUpdatedIsCalculateTemp.setId(isCalculateTemp.getId());

        partialUpdatedIsCalculateTemp
            .serialNo(UPDATED_SERIAL_NO)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .issFileParserId(UPDATED_ISS_FILE_PARSER_ID)
            .branchCode(UPDATED_BRANCH_CODE)
            .loanAccountNumberKcc(UPDATED_LOAN_ACCOUNT_NUMBER_KCC)
            .farmerName(UPDATED_FARMER_NAME)
            .gender(UPDATED_GENDER)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .mobileNo(UPDATED_MOBILE_NO)
            .farmerType(UPDATED_FARMER_TYPE)
            .socialCategory(UPDATED_SOCIAL_CATEGORY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .loanSanctionDate(UPDATED_LOAN_SANCTION_DATE)
            .loanSanctionAmount(UPDATED_LOAN_SANCTION_AMOUNT)
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .disburseAmount(UPDATED_DISBURSE_AMOUNT)
            .maturityLoanDate(UPDATED_MATURITY_LOAN_DATE)
            .bankDate(UPDATED_BANK_DATE)
            .cropName(UPDATED_CROP_NAME)
            .recoveryAmount(UPDATED_RECOVERY_AMOUNT)
            .recoveryInterest(UPDATED_RECOVERY_INTEREST)
            .recoveryDate(UPDATED_RECOVERY_DATE)
            .balanceAmount(UPDATED_BALANCE_AMOUNT)
            .prevDays(UPDATED_PREV_DAYS)
            .presDays(UPDATED_PRES_DAYS)
            .actualDays(UPDATED_ACTUAL_DAYS)
            .nProd(UPDATED_N_PROD)
            .productAmount(UPDATED_PRODUCT_AMOUNT)
            .productBank(UPDATED_PRODUCT_BANK)
            .productAbh3Lakh(UPDATED_PRODUCT_ABH_3_LAKH)
            .interestFirst15(UPDATED_INTEREST_FIRST_15)
            .interestFirst25(UPDATED_INTEREST_FIRST_25)
            .interestSecond15(UPDATED_INTEREST_SECOND_15)
            .interestSecond25(UPDATED_INTEREST_SECOND_25)
            .interestStateFirst3(UPDATED_INTEREST_STATE_FIRST_3)
            .interestStateSecond3(UPDATED_INTEREST_STATE_SECOND_3)
            .interestFirstAbh3(UPDATED_INTEREST_FIRST_ABH_3)
            .interestSecondAbh3(UPDATED_INTEREST_SECOND_ABH_3)
            .interestAbove3Lakh(UPDATED_INTEREST_ABOVE_3_LAKH)
            .panjabraoInt3(UPDATED_PANJABRAO_INT_3)
            .isRecover(UPDATED_IS_RECOVER)
            .abh3LakhAmt(UPDATED_ABH_3_LAKH_AMT)
            .upto50000(UPDATED_UPTO_50000);

        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsCalculateTemp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsCalculateTemp))
            )
            .andExpect(status().isOk());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
        IsCalculateTemp testIsCalculateTemp = isCalculateTempList.get(isCalculateTempList.size() - 1);
        assertThat(testIsCalculateTemp.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testIsCalculateTemp.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testIsCalculateTemp.getIssFileParserId()).isEqualTo(UPDATED_ISS_FILE_PARSER_ID);
        assertThat(testIsCalculateTemp.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testIsCalculateTemp.getLoanAccountNumberKcc()).isEqualTo(UPDATED_LOAN_ACCOUNT_NUMBER_KCC);
        assertThat(testIsCalculateTemp.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testIsCalculateTemp.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIsCalculateTemp.getAadharNumber()).isEqualTo(UPDATED_AADHAR_NUMBER);
        assertThat(testIsCalculateTemp.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testIsCalculateTemp.getFarmerType()).isEqualTo(UPDATED_FARMER_TYPE);
        assertThat(testIsCalculateTemp.getSocialCategory()).isEqualTo(UPDATED_SOCIAL_CATEGORY);
        assertThat(testIsCalculateTemp.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testIsCalculateTemp.getLoanSanctionDate()).isEqualTo(UPDATED_LOAN_SANCTION_DATE);
        assertThat(testIsCalculateTemp.getLoanSanctionAmount()).isEqualTo(UPDATED_LOAN_SANCTION_AMOUNT);
        assertThat(testIsCalculateTemp.getDisbursementDate()).isEqualTo(UPDATED_DISBURSEMENT_DATE);
        assertThat(testIsCalculateTemp.getDisburseAmount()).isEqualTo(UPDATED_DISBURSE_AMOUNT);
        assertThat(testIsCalculateTemp.getMaturityLoanDate()).isEqualTo(UPDATED_MATURITY_LOAN_DATE);
        assertThat(testIsCalculateTemp.getBankDate()).isEqualTo(UPDATED_BANK_DATE);
        assertThat(testIsCalculateTemp.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testIsCalculateTemp.getRecoveryAmount()).isEqualTo(UPDATED_RECOVERY_AMOUNT);
        assertThat(testIsCalculateTemp.getRecoveryInterest()).isEqualTo(UPDATED_RECOVERY_INTEREST);
        assertThat(testIsCalculateTemp.getRecoveryDate()).isEqualTo(UPDATED_RECOVERY_DATE);
        assertThat(testIsCalculateTemp.getBalanceAmount()).isEqualTo(UPDATED_BALANCE_AMOUNT);
        assertThat(testIsCalculateTemp.getPrevDays()).isEqualTo(UPDATED_PREV_DAYS);
        assertThat(testIsCalculateTemp.getPresDays()).isEqualTo(UPDATED_PRES_DAYS);
        assertThat(testIsCalculateTemp.getActualDays()).isEqualTo(UPDATED_ACTUAL_DAYS);
        assertThat(testIsCalculateTemp.getnProd()).isEqualTo(UPDATED_N_PROD);
        assertThat(testIsCalculateTemp.getProductAmount()).isEqualTo(UPDATED_PRODUCT_AMOUNT);
        assertThat(testIsCalculateTemp.getProductBank()).isEqualTo(UPDATED_PRODUCT_BANK);
        assertThat(testIsCalculateTemp.getProductAbh3Lakh()).isEqualTo(UPDATED_PRODUCT_ABH_3_LAKH);
        assertThat(testIsCalculateTemp.getInterestFirst15()).isEqualTo(UPDATED_INTEREST_FIRST_15);
        assertThat(testIsCalculateTemp.getInterestFirst25()).isEqualTo(UPDATED_INTEREST_FIRST_25);
        assertThat(testIsCalculateTemp.getInterestSecond15()).isEqualTo(UPDATED_INTEREST_SECOND_15);
        assertThat(testIsCalculateTemp.getInterestSecond25()).isEqualTo(UPDATED_INTEREST_SECOND_25);
        assertThat(testIsCalculateTemp.getInterestStateFirst3()).isEqualTo(UPDATED_INTEREST_STATE_FIRST_3);
        assertThat(testIsCalculateTemp.getInterestStateSecond3()).isEqualTo(UPDATED_INTEREST_STATE_SECOND_3);
        assertThat(testIsCalculateTemp.getInterestFirstAbh3()).isEqualTo(UPDATED_INTEREST_FIRST_ABH_3);
        assertThat(testIsCalculateTemp.getInterestSecondAbh3()).isEqualTo(UPDATED_INTEREST_SECOND_ABH_3);
        assertThat(testIsCalculateTemp.getInterestAbove3Lakh()).isEqualTo(UPDATED_INTEREST_ABOVE_3_LAKH);
        assertThat(testIsCalculateTemp.getPanjabraoInt3()).isEqualTo(UPDATED_PANJABRAO_INT_3);
        assertThat(testIsCalculateTemp.getIsRecover()).isEqualTo(UPDATED_IS_RECOVER);
        assertThat(testIsCalculateTemp.getAbh3LakhAmt()).isEqualTo(UPDATED_ABH_3_LAKH_AMT);
        assertThat(testIsCalculateTemp.getUpto50000()).isEqualTo(UPDATED_UPTO_50000);
    }

    @Test
    @Transactional
    void patchNonExistingIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, isCalculateTemp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIsCalculateTemp() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        int databaseSizeBeforeDelete = isCalculateTempRepository.findAll().size();

        // Delete the isCalculateTemp
        restIsCalculateTempMockMvc
            .perform(delete(ENTITY_API_URL_ID, isCalculateTemp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
