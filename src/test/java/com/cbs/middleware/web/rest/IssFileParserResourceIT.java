package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.service.IssFileParserService;
import com.cbs.middleware.service.criteria.IssFileParserCriteria;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IssFileParserResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class IssFileParserResourceIT {

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEME_WISE_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SCHEME_WISE_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC = "AAAAAAAAAA";
    private static final String UPDATED_IFSC = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_ACCOUNT_NUMBERKCC = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_ACCOUNT_NUMBERKCC = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DATEOF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATEOF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_AGE_AT_TIME_OF_SANCTION = "AAAAAAAAAA";
    private static final String UPDATED_AGE_AT_TIME_OF_SANCTION = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_FARMERS_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_FARMERS_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIAL_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIVE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RELATIVE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIVE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RELATIVE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCK_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_HOLDER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_SACTION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_SACTION_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_SANCTION_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_SANCTION_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_TENURE_OF_LOAN = "AAAAAAAAAA";
    private static final String UPDATED_TENURE_OF_LOAN = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_OVER_DUE_PAYMENT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_OVER_DUE_PAYMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURVEY_NO = "AAAAAAAAAA";
    private static final String UPDATED_SURVEY_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SAT_BARA_SUBSURVEY_NO = "AAAAAAAAAA";
    private static final String UPDATED_SAT_BARA_SUBSURVEY_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SEASON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEASON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_HECT = "AAAAAAAAAA";
    private static final String UPDATED_AREA_HECT = "BBBBBBBBBB";

    private static final String DEFAULT_LAND_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LAND_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DISBURSEMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DISBURSEMENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DISBURSE_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISBURSE_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_MATURITY_LOAN_DATE = "AAAAAAAAAA";
    private static final String UPDATED_MATURITY_LOAN_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_RECOVERY_AMOUNT_PRINCIPLE = "AAAAAAAAAA";
    private static final String UPDATED_RECOVERY_AMOUNT_PRINCIPLE = "BBBBBBBBBB";

    private static final String DEFAULT_RECOVERY_AMOUNT_INTEREST = "AAAAAAAAAA";
    private static final String UPDATED_RECOVERY_AMOUNT_INTEREST = "BBBBBBBBBB";

    private static final String DEFAULT_RECOVERY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_RECOVERY_DATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/iss-file-parsers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssFileParserRepository issFileParserRepository;

    @Mock
    private IssFileParserRepository issFileParserRepositoryMock;

    @Mock
    private IssFileParserService issFileParserServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssFileParserMockMvc;

    private IssFileParser issFileParser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssFileParser createEntity(EntityManager em) {
        IssFileParser issFileParser = new IssFileParser()
            .financialYear(DEFAULT_FINANCIAL_YEAR)
            .bankName(DEFAULT_BANK_NAME)
            .bankCode(DEFAULT_BANK_CODE)
            .branchName(DEFAULT_BRANCH_NAME)
            .branchCode(DEFAULT_BRANCH_CODE)
            .schemeWiseBranchCode(DEFAULT_SCHEME_WISE_BRANCH_CODE)
            .ifsc(DEFAULT_IFSC)
            .loanAccountNumberkcc(DEFAULT_LOAN_ACCOUNT_NUMBERKCC)
            .farmerName(DEFAULT_FARMER_NAME)
            .gender(DEFAULT_GENDER)
            .aadharNumber(DEFAULT_AADHAR_NUMBER)
            .dateofBirth(DEFAULT_DATEOF_BIRTH)
            .ageAtTimeOfSanction(DEFAULT_AGE_AT_TIME_OF_SANCTION)
            .mobileNo(DEFAULT_MOBILE_NO)
            .farmersCategory(DEFAULT_FARMERS_CATEGORY)
            .farmerType(DEFAULT_FARMER_TYPE)
            .socialCategory(DEFAULT_SOCIAL_CATEGORY)
            .relativeType(DEFAULT_RELATIVE_TYPE)
            .relativeName(DEFAULT_RELATIVE_NAME)
            .stateName(DEFAULT_STATE_NAME)
            .stateCode(DEFAULT_STATE_CODE)
            .districtName(DEFAULT_DISTRICT_NAME)
            .districtCode(DEFAULT_DISTRICT_CODE)
            .blockCode(DEFAULT_BLOCK_CODE)
            .blockName(DEFAULT_BLOCK_NAME)
            .villageCode(DEFAULT_VILLAGE_CODE)
            .villageName(DEFAULT_VILLAGE_NAME)
            .address(DEFAULT_ADDRESS)
            .pinCode(DEFAULT_PIN_CODE)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .pacsName(DEFAULT_PACS_NAME)
            .pacsNumber(DEFAULT_PACS_NUMBER)
            .accountHolderType(DEFAULT_ACCOUNT_HOLDER_TYPE)
            .primaryOccupation(DEFAULT_PRIMARY_OCCUPATION)
            .loanSactionDate(DEFAULT_LOAN_SACTION_DATE)
            .loanSanctionAmount(DEFAULT_LOAN_SANCTION_AMOUNT)
            .tenureOFLoan(DEFAULT_TENURE_OF_LOAN)
            .dateOfOverDuePayment(DEFAULT_DATE_OF_OVER_DUE_PAYMENT)
            .cropName(DEFAULT_CROP_NAME)
            .surveyNo(DEFAULT_SURVEY_NO)
            .satBaraSubsurveyNo(DEFAULT_SAT_BARA_SUBSURVEY_NO)
            .seasonName(DEFAULT_SEASON_NAME)
            .areaHect(DEFAULT_AREA_HECT)
            .landType(DEFAULT_LAND_TYPE)
            .disbursementDate(DEFAULT_DISBURSEMENT_DATE)
            .disburseAmount(DEFAULT_DISBURSE_AMOUNT)
            .maturityLoanDate(DEFAULT_MATURITY_LOAN_DATE)
            .recoveryAmountPrinciple(DEFAULT_RECOVERY_AMOUNT_PRINCIPLE)
            .recoveryAmountInterest(DEFAULT_RECOVERY_AMOUNT_INTEREST)
            .recoveryDate(DEFAULT_RECOVERY_DATE);
        return issFileParser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssFileParser createUpdatedEntity(EntityManager em) {
        IssFileParser issFileParser = new IssFileParser()
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .bankName(UPDATED_BANK_NAME)
            .bankCode(UPDATED_BANK_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchCode(UPDATED_BRANCH_CODE)
            .schemeWiseBranchCode(UPDATED_SCHEME_WISE_BRANCH_CODE)
            .ifsc(UPDATED_IFSC)
            .loanAccountNumberkcc(UPDATED_LOAN_ACCOUNT_NUMBERKCC)
            .farmerName(UPDATED_FARMER_NAME)
            .gender(UPDATED_GENDER)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .dateofBirth(UPDATED_DATEOF_BIRTH)
            .ageAtTimeOfSanction(UPDATED_AGE_AT_TIME_OF_SANCTION)
            .mobileNo(UPDATED_MOBILE_NO)
            .farmersCategory(UPDATED_FARMERS_CATEGORY)
            .farmerType(UPDATED_FARMER_TYPE)
            .socialCategory(UPDATED_SOCIAL_CATEGORY)
            .relativeType(UPDATED_RELATIVE_TYPE)
            .relativeName(UPDATED_RELATIVE_NAME)
            .stateName(UPDATED_STATE_NAME)
            .stateCode(UPDATED_STATE_CODE)
            .districtName(UPDATED_DISTRICT_NAME)
            .districtCode(UPDATED_DISTRICT_CODE)
            .blockCode(UPDATED_BLOCK_CODE)
            .blockName(UPDATED_BLOCK_NAME)
            .villageCode(UPDATED_VILLAGE_CODE)
            .villageName(UPDATED_VILLAGE_NAME)
            .address(UPDATED_ADDRESS)
            .pinCode(UPDATED_PIN_CODE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .pacsName(UPDATED_PACS_NAME)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .accountHolderType(UPDATED_ACCOUNT_HOLDER_TYPE)
            .primaryOccupation(UPDATED_PRIMARY_OCCUPATION)
            .loanSactionDate(UPDATED_LOAN_SACTION_DATE)
            .loanSanctionAmount(UPDATED_LOAN_SANCTION_AMOUNT)
            .tenureOFLoan(UPDATED_TENURE_OF_LOAN)
            .dateOfOverDuePayment(UPDATED_DATE_OF_OVER_DUE_PAYMENT)
            .cropName(UPDATED_CROP_NAME)
            .surveyNo(UPDATED_SURVEY_NO)
            .satBaraSubsurveyNo(UPDATED_SAT_BARA_SUBSURVEY_NO)
            .seasonName(UPDATED_SEASON_NAME)
            .areaHect(UPDATED_AREA_HECT)
            .landType(UPDATED_LAND_TYPE)
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .disburseAmount(UPDATED_DISBURSE_AMOUNT)
            .maturityLoanDate(UPDATED_MATURITY_LOAN_DATE)
            .recoveryAmountPrinciple(UPDATED_RECOVERY_AMOUNT_PRINCIPLE)
            .recoveryAmountInterest(UPDATED_RECOVERY_AMOUNT_INTEREST)
            .recoveryDate(UPDATED_RECOVERY_DATE);
        return issFileParser;
    }

    @BeforeEach
    public void initTest() {
        issFileParser = createEntity(em);
    }

    @Test
    @Transactional
    void createIssFileParser() throws Exception {
        int databaseSizeBeforeCreate = issFileParserRepository.findAll().size();
        // Create the IssFileParser
        restIssFileParserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issFileParser)))
            .andExpect(status().isCreated());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeCreate + 1);
        IssFileParser testIssFileParser = issFileParserList.get(issFileParserList.size() - 1);
        assertThat(testIssFileParser.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testIssFileParser.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testIssFileParser.getBankCode()).isEqualTo(DEFAULT_BANK_CODE);
        assertThat(testIssFileParser.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testIssFileParser.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testIssFileParser.getSchemeWiseBranchCode()).isEqualTo(DEFAULT_SCHEME_WISE_BRANCH_CODE);
        assertThat(testIssFileParser.getIfsc()).isEqualTo(DEFAULT_IFSC);
        assertThat(testIssFileParser.getLoanAccountNumberkcc()).isEqualTo(DEFAULT_LOAN_ACCOUNT_NUMBERKCC);
        assertThat(testIssFileParser.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testIssFileParser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIssFileParser.getAadharNumber()).isEqualTo(DEFAULT_AADHAR_NUMBER);
        assertThat(testIssFileParser.getDateofBirth()).isEqualTo(DEFAULT_DATEOF_BIRTH);
        assertThat(testIssFileParser.getAgeAtTimeOfSanction()).isEqualTo(DEFAULT_AGE_AT_TIME_OF_SANCTION);
        assertThat(testIssFileParser.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testIssFileParser.getFarmersCategory()).isEqualTo(DEFAULT_FARMERS_CATEGORY);
        assertThat(testIssFileParser.getFarmerType()).isEqualTo(DEFAULT_FARMER_TYPE);
        assertThat(testIssFileParser.getSocialCategory()).isEqualTo(DEFAULT_SOCIAL_CATEGORY);
        assertThat(testIssFileParser.getRelativeType()).isEqualTo(DEFAULT_RELATIVE_TYPE);
        assertThat(testIssFileParser.getRelativeName()).isEqualTo(DEFAULT_RELATIVE_NAME);
        assertThat(testIssFileParser.getStateName()).isEqualTo(DEFAULT_STATE_NAME);
        assertThat(testIssFileParser.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testIssFileParser.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);
        assertThat(testIssFileParser.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
        assertThat(testIssFileParser.getBlockCode()).isEqualTo(DEFAULT_BLOCK_CODE);
        assertThat(testIssFileParser.getBlockName()).isEqualTo(DEFAULT_BLOCK_NAME);
        assertThat(testIssFileParser.getVillageCode()).isEqualTo(DEFAULT_VILLAGE_CODE);
        assertThat(testIssFileParser.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
        assertThat(testIssFileParser.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testIssFileParser.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testIssFileParser.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testIssFileParser.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testIssFileParser.getPacsName()).isEqualTo(DEFAULT_PACS_NAME);
        assertThat(testIssFileParser.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testIssFileParser.getAccountHolderType()).isEqualTo(DEFAULT_ACCOUNT_HOLDER_TYPE);
        assertThat(testIssFileParser.getPrimaryOccupation()).isEqualTo(DEFAULT_PRIMARY_OCCUPATION);
        assertThat(testIssFileParser.getLoanSactionDate()).isEqualTo(DEFAULT_LOAN_SACTION_DATE);
        assertThat(testIssFileParser.getLoanSanctionAmount()).isEqualTo(DEFAULT_LOAN_SANCTION_AMOUNT);
        assertThat(testIssFileParser.getTenureOFLoan()).isEqualTo(DEFAULT_TENURE_OF_LOAN);
        assertThat(testIssFileParser.getDateOfOverDuePayment()).isEqualTo(DEFAULT_DATE_OF_OVER_DUE_PAYMENT);
        assertThat(testIssFileParser.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testIssFileParser.getSurveyNo()).isEqualTo(DEFAULT_SURVEY_NO);
        assertThat(testIssFileParser.getSatBaraSubsurveyNo()).isEqualTo(DEFAULT_SAT_BARA_SUBSURVEY_NO);
        assertThat(testIssFileParser.getSeasonName()).isEqualTo(DEFAULT_SEASON_NAME);
        assertThat(testIssFileParser.getAreaHect()).isEqualTo(DEFAULT_AREA_HECT);
        assertThat(testIssFileParser.getLandType()).isEqualTo(DEFAULT_LAND_TYPE);
        assertThat(testIssFileParser.getDisbursementDate()).isEqualTo(DEFAULT_DISBURSEMENT_DATE);
        assertThat(testIssFileParser.getDisburseAmount()).isEqualTo(DEFAULT_DISBURSE_AMOUNT);
        assertThat(testIssFileParser.getMaturityLoanDate()).isEqualTo(DEFAULT_MATURITY_LOAN_DATE);
        assertThat(testIssFileParser.getRecoveryAmountPrinciple()).isEqualTo(DEFAULT_RECOVERY_AMOUNT_PRINCIPLE);
        assertThat(testIssFileParser.getRecoveryAmountInterest()).isEqualTo(DEFAULT_RECOVERY_AMOUNT_INTEREST);
        assertThat(testIssFileParser.getRecoveryDate()).isEqualTo(DEFAULT_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void createIssFileParserWithExistingId() throws Exception {
        // Create the IssFileParser with an existing ID
        issFileParser.setId(1L);

        int databaseSizeBeforeCreate = issFileParserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssFileParserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issFileParser)))
            .andExpect(status().isBadRequest());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIssFileParsers() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList
        restIssFileParserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issFileParser.getId().intValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].bankCode").value(hasItem(DEFAULT_BANK_CODE)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].schemeWiseBranchCode").value(hasItem(DEFAULT_SCHEME_WISE_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].ifsc").value(hasItem(DEFAULT_IFSC)))
            .andExpect(jsonPath("$.[*].loanAccountNumberkcc").value(hasItem(DEFAULT_LOAN_ACCOUNT_NUMBERKCC)))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].aadharNumber").value(hasItem(DEFAULT_AADHAR_NUMBER)))
            .andExpect(jsonPath("$.[*].dateofBirth").value(hasItem(DEFAULT_DATEOF_BIRTH)))
            .andExpect(jsonPath("$.[*].ageAtTimeOfSanction").value(hasItem(DEFAULT_AGE_AT_TIME_OF_SANCTION)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].farmersCategory").value(hasItem(DEFAULT_FARMERS_CATEGORY)))
            .andExpect(jsonPath("$.[*].farmerType").value(hasItem(DEFAULT_FARMER_TYPE)))
            .andExpect(jsonPath("$.[*].socialCategory").value(hasItem(DEFAULT_SOCIAL_CATEGORY)))
            .andExpect(jsonPath("$.[*].relativeType").value(hasItem(DEFAULT_RELATIVE_TYPE)))
            .andExpect(jsonPath("$.[*].relativeName").value(hasItem(DEFAULT_RELATIVE_NAME)))
            .andExpect(jsonPath("$.[*].stateName").value(hasItem(DEFAULT_STATE_NAME)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)))
            .andExpect(jsonPath("$.[*].districtCode").value(hasItem(DEFAULT_DISTRICT_CODE)))
            .andExpect(jsonPath("$.[*].blockCode").value(hasItem(DEFAULT_BLOCK_CODE)))
            .andExpect(jsonPath("$.[*].blockName").value(hasItem(DEFAULT_BLOCK_NAME)))
            .andExpect(jsonPath("$.[*].villageCode").value(hasItem(DEFAULT_VILLAGE_CODE)))
            .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].pacsName").value(hasItem(DEFAULT_PACS_NAME)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].accountHolderType").value(hasItem(DEFAULT_ACCOUNT_HOLDER_TYPE)))
            .andExpect(jsonPath("$.[*].primaryOccupation").value(hasItem(DEFAULT_PRIMARY_OCCUPATION)))
            .andExpect(jsonPath("$.[*].loanSactionDate").value(hasItem(DEFAULT_LOAN_SACTION_DATE)))
            .andExpect(jsonPath("$.[*].loanSanctionAmount").value(hasItem(DEFAULT_LOAN_SANCTION_AMOUNT)))
            .andExpect(jsonPath("$.[*].tenureOFLoan").value(hasItem(DEFAULT_TENURE_OF_LOAN)))
            .andExpect(jsonPath("$.[*].dateOfOverDuePayment").value(hasItem(DEFAULT_DATE_OF_OVER_DUE_PAYMENT)))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].surveyNo").value(hasItem(DEFAULT_SURVEY_NO)))
            .andExpect(jsonPath("$.[*].satBaraSubsurveyNo").value(hasItem(DEFAULT_SAT_BARA_SUBSURVEY_NO)))
            .andExpect(jsonPath("$.[*].seasonName").value(hasItem(DEFAULT_SEASON_NAME)))
            .andExpect(jsonPath("$.[*].areaHect").value(hasItem(DEFAULT_AREA_HECT)))
            .andExpect(jsonPath("$.[*].landType").value(hasItem(DEFAULT_LAND_TYPE)))
            .andExpect(jsonPath("$.[*].disbursementDate").value(hasItem(DEFAULT_DISBURSEMENT_DATE)))
            .andExpect(jsonPath("$.[*].disburseAmount").value(hasItem(DEFAULT_DISBURSE_AMOUNT)))
            .andExpect(jsonPath("$.[*].maturityLoanDate").value(hasItem(DEFAULT_MATURITY_LOAN_DATE)))
            .andExpect(jsonPath("$.[*].recoveryAmountPrinciple").value(hasItem(DEFAULT_RECOVERY_AMOUNT_PRINCIPLE)))
            .andExpect(jsonPath("$.[*].recoveryAmountInterest").value(hasItem(DEFAULT_RECOVERY_AMOUNT_INTEREST)))
            .andExpect(jsonPath("$.[*].recoveryDate").value(hasItem(DEFAULT_RECOVERY_DATE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIssFileParsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(issFileParserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIssFileParserMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(issFileParserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIssFileParsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(issFileParserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIssFileParserMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(issFileParserRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getIssFileParser() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get the issFileParser
        restIssFileParserMockMvc
            .perform(get(ENTITY_API_URL_ID, issFileParser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issFileParser.getId().intValue()))
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.bankCode").value(DEFAULT_BANK_CODE))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE))
            .andExpect(jsonPath("$.schemeWiseBranchCode").value(DEFAULT_SCHEME_WISE_BRANCH_CODE))
            .andExpect(jsonPath("$.ifsc").value(DEFAULT_IFSC))
            .andExpect(jsonPath("$.loanAccountNumberkcc").value(DEFAULT_LOAN_ACCOUNT_NUMBERKCC))
            .andExpect(jsonPath("$.farmerName").value(DEFAULT_FARMER_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.aadharNumber").value(DEFAULT_AADHAR_NUMBER))
            .andExpect(jsonPath("$.dateofBirth").value(DEFAULT_DATEOF_BIRTH))
            .andExpect(jsonPath("$.ageAtTimeOfSanction").value(DEFAULT_AGE_AT_TIME_OF_SANCTION))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.farmersCategory").value(DEFAULT_FARMERS_CATEGORY))
            .andExpect(jsonPath("$.farmerType").value(DEFAULT_FARMER_TYPE))
            .andExpect(jsonPath("$.socialCategory").value(DEFAULT_SOCIAL_CATEGORY))
            .andExpect(jsonPath("$.relativeType").value(DEFAULT_RELATIVE_TYPE))
            .andExpect(jsonPath("$.relativeName").value(DEFAULT_RELATIVE_NAME))
            .andExpect(jsonPath("$.stateName").value(DEFAULT_STATE_NAME))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE))
            .andExpect(jsonPath("$.districtName").value(DEFAULT_DISTRICT_NAME))
            .andExpect(jsonPath("$.districtCode").value(DEFAULT_DISTRICT_CODE))
            .andExpect(jsonPath("$.blockCode").value(DEFAULT_BLOCK_CODE))
            .andExpect(jsonPath("$.blockName").value(DEFAULT_BLOCK_NAME))
            .andExpect(jsonPath("$.villageCode").value(DEFAULT_VILLAGE_CODE))
            .andExpect(jsonPath("$.villageName").value(DEFAULT_VILLAGE_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.pacsName").value(DEFAULT_PACS_NAME))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER))
            .andExpect(jsonPath("$.accountHolderType").value(DEFAULT_ACCOUNT_HOLDER_TYPE))
            .andExpect(jsonPath("$.primaryOccupation").value(DEFAULT_PRIMARY_OCCUPATION))
            .andExpect(jsonPath("$.loanSactionDate").value(DEFAULT_LOAN_SACTION_DATE))
            .andExpect(jsonPath("$.loanSanctionAmount").value(DEFAULT_LOAN_SANCTION_AMOUNT))
            .andExpect(jsonPath("$.tenureOFLoan").value(DEFAULT_TENURE_OF_LOAN))
            .andExpect(jsonPath("$.dateOfOverDuePayment").value(DEFAULT_DATE_OF_OVER_DUE_PAYMENT))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME))
            .andExpect(jsonPath("$.surveyNo").value(DEFAULT_SURVEY_NO))
            .andExpect(jsonPath("$.satBaraSubsurveyNo").value(DEFAULT_SAT_BARA_SUBSURVEY_NO))
            .andExpect(jsonPath("$.seasonName").value(DEFAULT_SEASON_NAME))
            .andExpect(jsonPath("$.areaHect").value(DEFAULT_AREA_HECT))
            .andExpect(jsonPath("$.landType").value(DEFAULT_LAND_TYPE))
            .andExpect(jsonPath("$.disbursementDate").value(DEFAULT_DISBURSEMENT_DATE))
            .andExpect(jsonPath("$.disburseAmount").value(DEFAULT_DISBURSE_AMOUNT))
            .andExpect(jsonPath("$.maturityLoanDate").value(DEFAULT_MATURITY_LOAN_DATE))
            .andExpect(jsonPath("$.recoveryAmountPrinciple").value(DEFAULT_RECOVERY_AMOUNT_PRINCIPLE))
            .andExpect(jsonPath("$.recoveryAmountInterest").value(DEFAULT_RECOVERY_AMOUNT_INTEREST))
            .andExpect(jsonPath("$.recoveryDate").value(DEFAULT_RECOVERY_DATE));
    }

    @Test
    @Transactional
    void getIssFileParsersByIdFiltering() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        Long id = issFileParser.getId();

        defaultIssFileParserShouldBeFound("id.equals=" + id);
        defaultIssFileParserShouldNotBeFound("id.notEquals=" + id);

        defaultIssFileParserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIssFileParserShouldNotBeFound("id.greaterThan=" + id);

        defaultIssFileParserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIssFileParserShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultIssFileParserShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the issFileParserList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultIssFileParserShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultIssFileParserShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the issFileParserList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultIssFileParserShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where financialYear is not null
        defaultIssFileParserShouldBeFound("financialYear.specified=true");

        // Get all the issFileParserList where financialYear is null
        defaultIssFileParserShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultIssFileParserShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the issFileParserList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultIssFileParserShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultIssFileParserShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the issFileParserList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultIssFileParserShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankName equals to DEFAULT_BANK_NAME
        defaultIssFileParserShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the issFileParserList where bankName equals to UPDATED_BANK_NAME
        defaultIssFileParserShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultIssFileParserShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the issFileParserList where bankName equals to UPDATED_BANK_NAME
        defaultIssFileParserShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankName is not null
        defaultIssFileParserShouldBeFound("bankName.specified=true");

        // Get all the issFileParserList where bankName is null
        defaultIssFileParserShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankName contains DEFAULT_BANK_NAME
        defaultIssFileParserShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the issFileParserList where bankName contains UPDATED_BANK_NAME
        defaultIssFileParserShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankName does not contain DEFAULT_BANK_NAME
        defaultIssFileParserShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the issFileParserList where bankName does not contain UPDATED_BANK_NAME
        defaultIssFileParserShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankCode equals to DEFAULT_BANK_CODE
        defaultIssFileParserShouldBeFound("bankCode.equals=" + DEFAULT_BANK_CODE);

        // Get all the issFileParserList where bankCode equals to UPDATED_BANK_CODE
        defaultIssFileParserShouldNotBeFound("bankCode.equals=" + UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankCode in DEFAULT_BANK_CODE or UPDATED_BANK_CODE
        defaultIssFileParserShouldBeFound("bankCode.in=" + DEFAULT_BANK_CODE + "," + UPDATED_BANK_CODE);

        // Get all the issFileParserList where bankCode equals to UPDATED_BANK_CODE
        defaultIssFileParserShouldNotBeFound("bankCode.in=" + UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankCode is not null
        defaultIssFileParserShouldBeFound("bankCode.specified=true");

        // Get all the issFileParserList where bankCode is null
        defaultIssFileParserShouldNotBeFound("bankCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankCodeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankCode contains DEFAULT_BANK_CODE
        defaultIssFileParserShouldBeFound("bankCode.contains=" + DEFAULT_BANK_CODE);

        // Get all the issFileParserList where bankCode contains UPDATED_BANK_CODE
        defaultIssFileParserShouldNotBeFound("bankCode.contains=" + UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBankCodeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where bankCode does not contain DEFAULT_BANK_CODE
        defaultIssFileParserShouldNotBeFound("bankCode.doesNotContain=" + DEFAULT_BANK_CODE);

        // Get all the issFileParserList where bankCode does not contain UPDATED_BANK_CODE
        defaultIssFileParserShouldBeFound("bankCode.doesNotContain=" + UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchName equals to DEFAULT_BRANCH_NAME
        defaultIssFileParserShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the issFileParserList where branchName equals to UPDATED_BRANCH_NAME
        defaultIssFileParserShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultIssFileParserShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the issFileParserList where branchName equals to UPDATED_BRANCH_NAME
        defaultIssFileParserShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchName is not null
        defaultIssFileParserShouldBeFound("branchName.specified=true");

        // Get all the issFileParserList where branchName is null
        defaultIssFileParserShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchName contains DEFAULT_BRANCH_NAME
        defaultIssFileParserShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the issFileParserList where branchName contains UPDATED_BRANCH_NAME
        defaultIssFileParserShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultIssFileParserShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the issFileParserList where branchName does not contain UPDATED_BRANCH_NAME
        defaultIssFileParserShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchCode equals to DEFAULT_BRANCH_CODE
        defaultIssFileParserShouldBeFound("branchCode.equals=" + DEFAULT_BRANCH_CODE);

        // Get all the issFileParserList where branchCode equals to UPDATED_BRANCH_CODE
        defaultIssFileParserShouldNotBeFound("branchCode.equals=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchCode in DEFAULT_BRANCH_CODE or UPDATED_BRANCH_CODE
        defaultIssFileParserShouldBeFound("branchCode.in=" + DEFAULT_BRANCH_CODE + "," + UPDATED_BRANCH_CODE);

        // Get all the issFileParserList where branchCode equals to UPDATED_BRANCH_CODE
        defaultIssFileParserShouldNotBeFound("branchCode.in=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchCode is not null
        defaultIssFileParserShouldBeFound("branchCode.specified=true");

        // Get all the issFileParserList where branchCode is null
        defaultIssFileParserShouldNotBeFound("branchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchCodeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchCode contains DEFAULT_BRANCH_CODE
        defaultIssFileParserShouldBeFound("branchCode.contains=" + DEFAULT_BRANCH_CODE);

        // Get all the issFileParserList where branchCode contains UPDATED_BRANCH_CODE
        defaultIssFileParserShouldNotBeFound("branchCode.contains=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBranchCodeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where branchCode does not contain DEFAULT_BRANCH_CODE
        defaultIssFileParserShouldNotBeFound("branchCode.doesNotContain=" + DEFAULT_BRANCH_CODE);

        // Get all the issFileParserList where branchCode does not contain UPDATED_BRANCH_CODE
        defaultIssFileParserShouldBeFound("branchCode.doesNotContain=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySchemeWiseBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where schemeWiseBranchCode equals to DEFAULT_SCHEME_WISE_BRANCH_CODE
        defaultIssFileParserShouldBeFound("schemeWiseBranchCode.equals=" + DEFAULT_SCHEME_WISE_BRANCH_CODE);

        // Get all the issFileParserList where schemeWiseBranchCode equals to UPDATED_SCHEME_WISE_BRANCH_CODE
        defaultIssFileParserShouldNotBeFound("schemeWiseBranchCode.equals=" + UPDATED_SCHEME_WISE_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySchemeWiseBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where schemeWiseBranchCode in DEFAULT_SCHEME_WISE_BRANCH_CODE or UPDATED_SCHEME_WISE_BRANCH_CODE
        defaultIssFileParserShouldBeFound(
            "schemeWiseBranchCode.in=" + DEFAULT_SCHEME_WISE_BRANCH_CODE + "," + UPDATED_SCHEME_WISE_BRANCH_CODE
        );

        // Get all the issFileParserList where schemeWiseBranchCode equals to UPDATED_SCHEME_WISE_BRANCH_CODE
        defaultIssFileParserShouldNotBeFound("schemeWiseBranchCode.in=" + UPDATED_SCHEME_WISE_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySchemeWiseBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where schemeWiseBranchCode is not null
        defaultIssFileParserShouldBeFound("schemeWiseBranchCode.specified=true");

        // Get all the issFileParserList where schemeWiseBranchCode is null
        defaultIssFileParserShouldNotBeFound("schemeWiseBranchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySchemeWiseBranchCodeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where schemeWiseBranchCode contains DEFAULT_SCHEME_WISE_BRANCH_CODE
        defaultIssFileParserShouldBeFound("schemeWiseBranchCode.contains=" + DEFAULT_SCHEME_WISE_BRANCH_CODE);

        // Get all the issFileParserList where schemeWiseBranchCode contains UPDATED_SCHEME_WISE_BRANCH_CODE
        defaultIssFileParserShouldNotBeFound("schemeWiseBranchCode.contains=" + UPDATED_SCHEME_WISE_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySchemeWiseBranchCodeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where schemeWiseBranchCode does not contain DEFAULT_SCHEME_WISE_BRANCH_CODE
        defaultIssFileParserShouldNotBeFound("schemeWiseBranchCode.doesNotContain=" + DEFAULT_SCHEME_WISE_BRANCH_CODE);

        // Get all the issFileParserList where schemeWiseBranchCode does not contain UPDATED_SCHEME_WISE_BRANCH_CODE
        defaultIssFileParserShouldBeFound("schemeWiseBranchCode.doesNotContain=" + UPDATED_SCHEME_WISE_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByIfscIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ifsc equals to DEFAULT_IFSC
        defaultIssFileParserShouldBeFound("ifsc.equals=" + DEFAULT_IFSC);

        // Get all the issFileParserList where ifsc equals to UPDATED_IFSC
        defaultIssFileParserShouldNotBeFound("ifsc.equals=" + UPDATED_IFSC);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByIfscIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ifsc in DEFAULT_IFSC or UPDATED_IFSC
        defaultIssFileParserShouldBeFound("ifsc.in=" + DEFAULT_IFSC + "," + UPDATED_IFSC);

        // Get all the issFileParserList where ifsc equals to UPDATED_IFSC
        defaultIssFileParserShouldNotBeFound("ifsc.in=" + UPDATED_IFSC);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByIfscIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ifsc is not null
        defaultIssFileParserShouldBeFound("ifsc.specified=true");

        // Get all the issFileParserList where ifsc is null
        defaultIssFileParserShouldNotBeFound("ifsc.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByIfscContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ifsc contains DEFAULT_IFSC
        defaultIssFileParserShouldBeFound("ifsc.contains=" + DEFAULT_IFSC);

        // Get all the issFileParserList where ifsc contains UPDATED_IFSC
        defaultIssFileParserShouldNotBeFound("ifsc.contains=" + UPDATED_IFSC);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByIfscNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ifsc does not contain DEFAULT_IFSC
        defaultIssFileParserShouldNotBeFound("ifsc.doesNotContain=" + DEFAULT_IFSC);

        // Get all the issFileParserList where ifsc does not contain UPDATED_IFSC
        defaultIssFileParserShouldBeFound("ifsc.doesNotContain=" + UPDATED_IFSC);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanAccountNumberkccIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanAccountNumberkcc equals to DEFAULT_LOAN_ACCOUNT_NUMBERKCC
        defaultIssFileParserShouldBeFound("loanAccountNumberkcc.equals=" + DEFAULT_LOAN_ACCOUNT_NUMBERKCC);

        // Get all the issFileParserList where loanAccountNumberkcc equals to UPDATED_LOAN_ACCOUNT_NUMBERKCC
        defaultIssFileParserShouldNotBeFound("loanAccountNumberkcc.equals=" + UPDATED_LOAN_ACCOUNT_NUMBERKCC);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanAccountNumberkccIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanAccountNumberkcc in DEFAULT_LOAN_ACCOUNT_NUMBERKCC or UPDATED_LOAN_ACCOUNT_NUMBERKCC
        defaultIssFileParserShouldBeFound(
            "loanAccountNumberkcc.in=" + DEFAULT_LOAN_ACCOUNT_NUMBERKCC + "," + UPDATED_LOAN_ACCOUNT_NUMBERKCC
        );

        // Get all the issFileParserList where loanAccountNumberkcc equals to UPDATED_LOAN_ACCOUNT_NUMBERKCC
        defaultIssFileParserShouldNotBeFound("loanAccountNumberkcc.in=" + UPDATED_LOAN_ACCOUNT_NUMBERKCC);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanAccountNumberkccIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanAccountNumberkcc is not null
        defaultIssFileParserShouldBeFound("loanAccountNumberkcc.specified=true");

        // Get all the issFileParserList where loanAccountNumberkcc is null
        defaultIssFileParserShouldNotBeFound("loanAccountNumberkcc.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanAccountNumberkccContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanAccountNumberkcc contains DEFAULT_LOAN_ACCOUNT_NUMBERKCC
        defaultIssFileParserShouldBeFound("loanAccountNumberkcc.contains=" + DEFAULT_LOAN_ACCOUNT_NUMBERKCC);

        // Get all the issFileParserList where loanAccountNumberkcc contains UPDATED_LOAN_ACCOUNT_NUMBERKCC
        defaultIssFileParserShouldNotBeFound("loanAccountNumberkcc.contains=" + UPDATED_LOAN_ACCOUNT_NUMBERKCC);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanAccountNumberkccNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanAccountNumberkcc does not contain DEFAULT_LOAN_ACCOUNT_NUMBERKCC
        defaultIssFileParserShouldNotBeFound("loanAccountNumberkcc.doesNotContain=" + DEFAULT_LOAN_ACCOUNT_NUMBERKCC);

        // Get all the issFileParserList where loanAccountNumberkcc does not contain UPDATED_LOAN_ACCOUNT_NUMBERKCC
        defaultIssFileParserShouldBeFound("loanAccountNumberkcc.doesNotContain=" + UPDATED_LOAN_ACCOUNT_NUMBERKCC);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerName equals to DEFAULT_FARMER_NAME
        defaultIssFileParserShouldBeFound("farmerName.equals=" + DEFAULT_FARMER_NAME);

        // Get all the issFileParserList where farmerName equals to UPDATED_FARMER_NAME
        defaultIssFileParserShouldNotBeFound("farmerName.equals=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerName in DEFAULT_FARMER_NAME or UPDATED_FARMER_NAME
        defaultIssFileParserShouldBeFound("farmerName.in=" + DEFAULT_FARMER_NAME + "," + UPDATED_FARMER_NAME);

        // Get all the issFileParserList where farmerName equals to UPDATED_FARMER_NAME
        defaultIssFileParserShouldNotBeFound("farmerName.in=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerName is not null
        defaultIssFileParserShouldBeFound("farmerName.specified=true");

        // Get all the issFileParserList where farmerName is null
        defaultIssFileParserShouldNotBeFound("farmerName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerName contains DEFAULT_FARMER_NAME
        defaultIssFileParserShouldBeFound("farmerName.contains=" + DEFAULT_FARMER_NAME);

        // Get all the issFileParserList where farmerName contains UPDATED_FARMER_NAME
        defaultIssFileParserShouldNotBeFound("farmerName.contains=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerName does not contain DEFAULT_FARMER_NAME
        defaultIssFileParserShouldNotBeFound("farmerName.doesNotContain=" + DEFAULT_FARMER_NAME);

        // Get all the issFileParserList where farmerName does not contain UPDATED_FARMER_NAME
        defaultIssFileParserShouldBeFound("farmerName.doesNotContain=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where gender equals to DEFAULT_GENDER
        defaultIssFileParserShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the issFileParserList where gender equals to UPDATED_GENDER
        defaultIssFileParserShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultIssFileParserShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the issFileParserList where gender equals to UPDATED_GENDER
        defaultIssFileParserShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where gender is not null
        defaultIssFileParserShouldBeFound("gender.specified=true");

        // Get all the issFileParserList where gender is null
        defaultIssFileParserShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByGenderContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where gender contains DEFAULT_GENDER
        defaultIssFileParserShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the issFileParserList where gender contains UPDATED_GENDER
        defaultIssFileParserShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where gender does not contain DEFAULT_GENDER
        defaultIssFileParserShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the issFileParserList where gender does not contain UPDATED_GENDER
        defaultIssFileParserShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAadharNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where aadharNumber equals to DEFAULT_AADHAR_NUMBER
        defaultIssFileParserShouldBeFound("aadharNumber.equals=" + DEFAULT_AADHAR_NUMBER);

        // Get all the issFileParserList where aadharNumber equals to UPDATED_AADHAR_NUMBER
        defaultIssFileParserShouldNotBeFound("aadharNumber.equals=" + UPDATED_AADHAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAadharNumberIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where aadharNumber in DEFAULT_AADHAR_NUMBER or UPDATED_AADHAR_NUMBER
        defaultIssFileParserShouldBeFound("aadharNumber.in=" + DEFAULT_AADHAR_NUMBER + "," + UPDATED_AADHAR_NUMBER);

        // Get all the issFileParserList where aadharNumber equals to UPDATED_AADHAR_NUMBER
        defaultIssFileParserShouldNotBeFound("aadharNumber.in=" + UPDATED_AADHAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAadharNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where aadharNumber is not null
        defaultIssFileParserShouldBeFound("aadharNumber.specified=true");

        // Get all the issFileParserList where aadharNumber is null
        defaultIssFileParserShouldNotBeFound("aadharNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAadharNumberContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where aadharNumber contains DEFAULT_AADHAR_NUMBER
        defaultIssFileParserShouldBeFound("aadharNumber.contains=" + DEFAULT_AADHAR_NUMBER);

        // Get all the issFileParserList where aadharNumber contains UPDATED_AADHAR_NUMBER
        defaultIssFileParserShouldNotBeFound("aadharNumber.contains=" + UPDATED_AADHAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAadharNumberNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where aadharNumber does not contain DEFAULT_AADHAR_NUMBER
        defaultIssFileParserShouldNotBeFound("aadharNumber.doesNotContain=" + DEFAULT_AADHAR_NUMBER);

        // Get all the issFileParserList where aadharNumber does not contain UPDATED_AADHAR_NUMBER
        defaultIssFileParserShouldBeFound("aadharNumber.doesNotContain=" + UPDATED_AADHAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateofBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateofBirth equals to DEFAULT_DATEOF_BIRTH
        defaultIssFileParserShouldBeFound("dateofBirth.equals=" + DEFAULT_DATEOF_BIRTH);

        // Get all the issFileParserList where dateofBirth equals to UPDATED_DATEOF_BIRTH
        defaultIssFileParserShouldNotBeFound("dateofBirth.equals=" + UPDATED_DATEOF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateofBirthIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateofBirth in DEFAULT_DATEOF_BIRTH or UPDATED_DATEOF_BIRTH
        defaultIssFileParserShouldBeFound("dateofBirth.in=" + DEFAULT_DATEOF_BIRTH + "," + UPDATED_DATEOF_BIRTH);

        // Get all the issFileParserList where dateofBirth equals to UPDATED_DATEOF_BIRTH
        defaultIssFileParserShouldNotBeFound("dateofBirth.in=" + UPDATED_DATEOF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateofBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateofBirth is not null
        defaultIssFileParserShouldBeFound("dateofBirth.specified=true");

        // Get all the issFileParserList where dateofBirth is null
        defaultIssFileParserShouldNotBeFound("dateofBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateofBirthContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateofBirth contains DEFAULT_DATEOF_BIRTH
        defaultIssFileParserShouldBeFound("dateofBirth.contains=" + DEFAULT_DATEOF_BIRTH);

        // Get all the issFileParserList where dateofBirth contains UPDATED_DATEOF_BIRTH
        defaultIssFileParserShouldNotBeFound("dateofBirth.contains=" + UPDATED_DATEOF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateofBirthNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateofBirth does not contain DEFAULT_DATEOF_BIRTH
        defaultIssFileParserShouldNotBeFound("dateofBirth.doesNotContain=" + DEFAULT_DATEOF_BIRTH);

        // Get all the issFileParserList where dateofBirth does not contain UPDATED_DATEOF_BIRTH
        defaultIssFileParserShouldBeFound("dateofBirth.doesNotContain=" + UPDATED_DATEOF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAgeAtTimeOfSanctionIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ageAtTimeOfSanction equals to DEFAULT_AGE_AT_TIME_OF_SANCTION
        defaultIssFileParserShouldBeFound("ageAtTimeOfSanction.equals=" + DEFAULT_AGE_AT_TIME_OF_SANCTION);

        // Get all the issFileParserList where ageAtTimeOfSanction equals to UPDATED_AGE_AT_TIME_OF_SANCTION
        defaultIssFileParserShouldNotBeFound("ageAtTimeOfSanction.equals=" + UPDATED_AGE_AT_TIME_OF_SANCTION);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAgeAtTimeOfSanctionIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ageAtTimeOfSanction in DEFAULT_AGE_AT_TIME_OF_SANCTION or UPDATED_AGE_AT_TIME_OF_SANCTION
        defaultIssFileParserShouldBeFound(
            "ageAtTimeOfSanction.in=" + DEFAULT_AGE_AT_TIME_OF_SANCTION + "," + UPDATED_AGE_AT_TIME_OF_SANCTION
        );

        // Get all the issFileParserList where ageAtTimeOfSanction equals to UPDATED_AGE_AT_TIME_OF_SANCTION
        defaultIssFileParserShouldNotBeFound("ageAtTimeOfSanction.in=" + UPDATED_AGE_AT_TIME_OF_SANCTION);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAgeAtTimeOfSanctionIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ageAtTimeOfSanction is not null
        defaultIssFileParserShouldBeFound("ageAtTimeOfSanction.specified=true");

        // Get all the issFileParserList where ageAtTimeOfSanction is null
        defaultIssFileParserShouldNotBeFound("ageAtTimeOfSanction.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAgeAtTimeOfSanctionContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ageAtTimeOfSanction contains DEFAULT_AGE_AT_TIME_OF_SANCTION
        defaultIssFileParserShouldBeFound("ageAtTimeOfSanction.contains=" + DEFAULT_AGE_AT_TIME_OF_SANCTION);

        // Get all the issFileParserList where ageAtTimeOfSanction contains UPDATED_AGE_AT_TIME_OF_SANCTION
        defaultIssFileParserShouldNotBeFound("ageAtTimeOfSanction.contains=" + UPDATED_AGE_AT_TIME_OF_SANCTION);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAgeAtTimeOfSanctionNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where ageAtTimeOfSanction does not contain DEFAULT_AGE_AT_TIME_OF_SANCTION
        defaultIssFileParserShouldNotBeFound("ageAtTimeOfSanction.doesNotContain=" + DEFAULT_AGE_AT_TIME_OF_SANCTION);

        // Get all the issFileParserList where ageAtTimeOfSanction does not contain UPDATED_AGE_AT_TIME_OF_SANCTION
        defaultIssFileParserShouldBeFound("ageAtTimeOfSanction.doesNotContain=" + UPDATED_AGE_AT_TIME_OF_SANCTION);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultIssFileParserShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the issFileParserList where mobileNo equals to UPDATED_MOBILE_NO
        defaultIssFileParserShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultIssFileParserShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the issFileParserList where mobileNo equals to UPDATED_MOBILE_NO
        defaultIssFileParserShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where mobileNo is not null
        defaultIssFileParserShouldBeFound("mobileNo.specified=true");

        // Get all the issFileParserList where mobileNo is null
        defaultIssFileParserShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where mobileNo contains DEFAULT_MOBILE_NO
        defaultIssFileParserShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the issFileParserList where mobileNo contains UPDATED_MOBILE_NO
        defaultIssFileParserShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultIssFileParserShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the issFileParserList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultIssFileParserShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmersCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmersCategory equals to DEFAULT_FARMERS_CATEGORY
        defaultIssFileParserShouldBeFound("farmersCategory.equals=" + DEFAULT_FARMERS_CATEGORY);

        // Get all the issFileParserList where farmersCategory equals to UPDATED_FARMERS_CATEGORY
        defaultIssFileParserShouldNotBeFound("farmersCategory.equals=" + UPDATED_FARMERS_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmersCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmersCategory in DEFAULT_FARMERS_CATEGORY or UPDATED_FARMERS_CATEGORY
        defaultIssFileParserShouldBeFound("farmersCategory.in=" + DEFAULT_FARMERS_CATEGORY + "," + UPDATED_FARMERS_CATEGORY);

        // Get all the issFileParserList where farmersCategory equals to UPDATED_FARMERS_CATEGORY
        defaultIssFileParserShouldNotBeFound("farmersCategory.in=" + UPDATED_FARMERS_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmersCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmersCategory is not null
        defaultIssFileParserShouldBeFound("farmersCategory.specified=true");

        // Get all the issFileParserList where farmersCategory is null
        defaultIssFileParserShouldNotBeFound("farmersCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmersCategoryContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmersCategory contains DEFAULT_FARMERS_CATEGORY
        defaultIssFileParserShouldBeFound("farmersCategory.contains=" + DEFAULT_FARMERS_CATEGORY);

        // Get all the issFileParserList where farmersCategory contains UPDATED_FARMERS_CATEGORY
        defaultIssFileParserShouldNotBeFound("farmersCategory.contains=" + UPDATED_FARMERS_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmersCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmersCategory does not contain DEFAULT_FARMERS_CATEGORY
        defaultIssFileParserShouldNotBeFound("farmersCategory.doesNotContain=" + DEFAULT_FARMERS_CATEGORY);

        // Get all the issFileParserList where farmersCategory does not contain UPDATED_FARMERS_CATEGORY
        defaultIssFileParserShouldBeFound("farmersCategory.doesNotContain=" + UPDATED_FARMERS_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerType equals to DEFAULT_FARMER_TYPE
        defaultIssFileParserShouldBeFound("farmerType.equals=" + DEFAULT_FARMER_TYPE);

        // Get all the issFileParserList where farmerType equals to UPDATED_FARMER_TYPE
        defaultIssFileParserShouldNotBeFound("farmerType.equals=" + UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerTypeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerType in DEFAULT_FARMER_TYPE or UPDATED_FARMER_TYPE
        defaultIssFileParserShouldBeFound("farmerType.in=" + DEFAULT_FARMER_TYPE + "," + UPDATED_FARMER_TYPE);

        // Get all the issFileParserList where farmerType equals to UPDATED_FARMER_TYPE
        defaultIssFileParserShouldNotBeFound("farmerType.in=" + UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerType is not null
        defaultIssFileParserShouldBeFound("farmerType.specified=true");

        // Get all the issFileParserList where farmerType is null
        defaultIssFileParserShouldNotBeFound("farmerType.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerTypeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerType contains DEFAULT_FARMER_TYPE
        defaultIssFileParserShouldBeFound("farmerType.contains=" + DEFAULT_FARMER_TYPE);

        // Get all the issFileParserList where farmerType contains UPDATED_FARMER_TYPE
        defaultIssFileParserShouldNotBeFound("farmerType.contains=" + UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByFarmerTypeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where farmerType does not contain DEFAULT_FARMER_TYPE
        defaultIssFileParserShouldNotBeFound("farmerType.doesNotContain=" + DEFAULT_FARMER_TYPE);

        // Get all the issFileParserList where farmerType does not contain UPDATED_FARMER_TYPE
        defaultIssFileParserShouldBeFound("farmerType.doesNotContain=" + UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySocialCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where socialCategory equals to DEFAULT_SOCIAL_CATEGORY
        defaultIssFileParserShouldBeFound("socialCategory.equals=" + DEFAULT_SOCIAL_CATEGORY);

        // Get all the issFileParserList where socialCategory equals to UPDATED_SOCIAL_CATEGORY
        defaultIssFileParserShouldNotBeFound("socialCategory.equals=" + UPDATED_SOCIAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySocialCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where socialCategory in DEFAULT_SOCIAL_CATEGORY or UPDATED_SOCIAL_CATEGORY
        defaultIssFileParserShouldBeFound("socialCategory.in=" + DEFAULT_SOCIAL_CATEGORY + "," + UPDATED_SOCIAL_CATEGORY);

        // Get all the issFileParserList where socialCategory equals to UPDATED_SOCIAL_CATEGORY
        defaultIssFileParserShouldNotBeFound("socialCategory.in=" + UPDATED_SOCIAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySocialCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where socialCategory is not null
        defaultIssFileParserShouldBeFound("socialCategory.specified=true");

        // Get all the issFileParserList where socialCategory is null
        defaultIssFileParserShouldNotBeFound("socialCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySocialCategoryContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where socialCategory contains DEFAULT_SOCIAL_CATEGORY
        defaultIssFileParserShouldBeFound("socialCategory.contains=" + DEFAULT_SOCIAL_CATEGORY);

        // Get all the issFileParserList where socialCategory contains UPDATED_SOCIAL_CATEGORY
        defaultIssFileParserShouldNotBeFound("socialCategory.contains=" + UPDATED_SOCIAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySocialCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where socialCategory does not contain DEFAULT_SOCIAL_CATEGORY
        defaultIssFileParserShouldNotBeFound("socialCategory.doesNotContain=" + DEFAULT_SOCIAL_CATEGORY);

        // Get all the issFileParserList where socialCategory does not contain UPDATED_SOCIAL_CATEGORY
        defaultIssFileParserShouldBeFound("socialCategory.doesNotContain=" + UPDATED_SOCIAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeType equals to DEFAULT_RELATIVE_TYPE
        defaultIssFileParserShouldBeFound("relativeType.equals=" + DEFAULT_RELATIVE_TYPE);

        // Get all the issFileParserList where relativeType equals to UPDATED_RELATIVE_TYPE
        defaultIssFileParserShouldNotBeFound("relativeType.equals=" + UPDATED_RELATIVE_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeType in DEFAULT_RELATIVE_TYPE or UPDATED_RELATIVE_TYPE
        defaultIssFileParserShouldBeFound("relativeType.in=" + DEFAULT_RELATIVE_TYPE + "," + UPDATED_RELATIVE_TYPE);

        // Get all the issFileParserList where relativeType equals to UPDATED_RELATIVE_TYPE
        defaultIssFileParserShouldNotBeFound("relativeType.in=" + UPDATED_RELATIVE_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeType is not null
        defaultIssFileParserShouldBeFound("relativeType.specified=true");

        // Get all the issFileParserList where relativeType is null
        defaultIssFileParserShouldNotBeFound("relativeType.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeTypeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeType contains DEFAULT_RELATIVE_TYPE
        defaultIssFileParserShouldBeFound("relativeType.contains=" + DEFAULT_RELATIVE_TYPE);

        // Get all the issFileParserList where relativeType contains UPDATED_RELATIVE_TYPE
        defaultIssFileParserShouldNotBeFound("relativeType.contains=" + UPDATED_RELATIVE_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeTypeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeType does not contain DEFAULT_RELATIVE_TYPE
        defaultIssFileParserShouldNotBeFound("relativeType.doesNotContain=" + DEFAULT_RELATIVE_TYPE);

        // Get all the issFileParserList where relativeType does not contain UPDATED_RELATIVE_TYPE
        defaultIssFileParserShouldBeFound("relativeType.doesNotContain=" + UPDATED_RELATIVE_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeName equals to DEFAULT_RELATIVE_NAME
        defaultIssFileParserShouldBeFound("relativeName.equals=" + DEFAULT_RELATIVE_NAME);

        // Get all the issFileParserList where relativeName equals to UPDATED_RELATIVE_NAME
        defaultIssFileParserShouldNotBeFound("relativeName.equals=" + UPDATED_RELATIVE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeName in DEFAULT_RELATIVE_NAME or UPDATED_RELATIVE_NAME
        defaultIssFileParserShouldBeFound("relativeName.in=" + DEFAULT_RELATIVE_NAME + "," + UPDATED_RELATIVE_NAME);

        // Get all the issFileParserList where relativeName equals to UPDATED_RELATIVE_NAME
        defaultIssFileParserShouldNotBeFound("relativeName.in=" + UPDATED_RELATIVE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeName is not null
        defaultIssFileParserShouldBeFound("relativeName.specified=true");

        // Get all the issFileParserList where relativeName is null
        defaultIssFileParserShouldNotBeFound("relativeName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeName contains DEFAULT_RELATIVE_NAME
        defaultIssFileParserShouldBeFound("relativeName.contains=" + DEFAULT_RELATIVE_NAME);

        // Get all the issFileParserList where relativeName contains UPDATED_RELATIVE_NAME
        defaultIssFileParserShouldNotBeFound("relativeName.contains=" + UPDATED_RELATIVE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRelativeNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where relativeName does not contain DEFAULT_RELATIVE_NAME
        defaultIssFileParserShouldNotBeFound("relativeName.doesNotContain=" + DEFAULT_RELATIVE_NAME);

        // Get all the issFileParserList where relativeName does not contain UPDATED_RELATIVE_NAME
        defaultIssFileParserShouldBeFound("relativeName.doesNotContain=" + UPDATED_RELATIVE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateName equals to DEFAULT_STATE_NAME
        defaultIssFileParserShouldBeFound("stateName.equals=" + DEFAULT_STATE_NAME);

        // Get all the issFileParserList where stateName equals to UPDATED_STATE_NAME
        defaultIssFileParserShouldNotBeFound("stateName.equals=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateName in DEFAULT_STATE_NAME or UPDATED_STATE_NAME
        defaultIssFileParserShouldBeFound("stateName.in=" + DEFAULT_STATE_NAME + "," + UPDATED_STATE_NAME);

        // Get all the issFileParserList where stateName equals to UPDATED_STATE_NAME
        defaultIssFileParserShouldNotBeFound("stateName.in=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateName is not null
        defaultIssFileParserShouldBeFound("stateName.specified=true");

        // Get all the issFileParserList where stateName is null
        defaultIssFileParserShouldNotBeFound("stateName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateName contains DEFAULT_STATE_NAME
        defaultIssFileParserShouldBeFound("stateName.contains=" + DEFAULT_STATE_NAME);

        // Get all the issFileParserList where stateName contains UPDATED_STATE_NAME
        defaultIssFileParserShouldNotBeFound("stateName.contains=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateName does not contain DEFAULT_STATE_NAME
        defaultIssFileParserShouldNotBeFound("stateName.doesNotContain=" + DEFAULT_STATE_NAME);

        // Get all the issFileParserList where stateName does not contain UPDATED_STATE_NAME
        defaultIssFileParserShouldBeFound("stateName.doesNotContain=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateCode equals to DEFAULT_STATE_CODE
        defaultIssFileParserShouldBeFound("stateCode.equals=" + DEFAULT_STATE_CODE);

        // Get all the issFileParserList where stateCode equals to UPDATED_STATE_CODE
        defaultIssFileParserShouldNotBeFound("stateCode.equals=" + UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateCode in DEFAULT_STATE_CODE or UPDATED_STATE_CODE
        defaultIssFileParserShouldBeFound("stateCode.in=" + DEFAULT_STATE_CODE + "," + UPDATED_STATE_CODE);

        // Get all the issFileParserList where stateCode equals to UPDATED_STATE_CODE
        defaultIssFileParserShouldNotBeFound("stateCode.in=" + UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateCode is not null
        defaultIssFileParserShouldBeFound("stateCode.specified=true");

        // Get all the issFileParserList where stateCode is null
        defaultIssFileParserShouldNotBeFound("stateCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateCodeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateCode contains DEFAULT_STATE_CODE
        defaultIssFileParserShouldBeFound("stateCode.contains=" + DEFAULT_STATE_CODE);

        // Get all the issFileParserList where stateCode contains UPDATED_STATE_CODE
        defaultIssFileParserShouldNotBeFound("stateCode.contains=" + UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByStateCodeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where stateCode does not contain DEFAULT_STATE_CODE
        defaultIssFileParserShouldNotBeFound("stateCode.doesNotContain=" + DEFAULT_STATE_CODE);

        // Get all the issFileParserList where stateCode does not contain UPDATED_STATE_CODE
        defaultIssFileParserShouldBeFound("stateCode.doesNotContain=" + UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtName equals to DEFAULT_DISTRICT_NAME
        defaultIssFileParserShouldBeFound("districtName.equals=" + DEFAULT_DISTRICT_NAME);

        // Get all the issFileParserList where districtName equals to UPDATED_DISTRICT_NAME
        defaultIssFileParserShouldNotBeFound("districtName.equals=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtName in DEFAULT_DISTRICT_NAME or UPDATED_DISTRICT_NAME
        defaultIssFileParserShouldBeFound("districtName.in=" + DEFAULT_DISTRICT_NAME + "," + UPDATED_DISTRICT_NAME);

        // Get all the issFileParserList where districtName equals to UPDATED_DISTRICT_NAME
        defaultIssFileParserShouldNotBeFound("districtName.in=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtName is not null
        defaultIssFileParserShouldBeFound("districtName.specified=true");

        // Get all the issFileParserList where districtName is null
        defaultIssFileParserShouldNotBeFound("districtName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtName contains DEFAULT_DISTRICT_NAME
        defaultIssFileParserShouldBeFound("districtName.contains=" + DEFAULT_DISTRICT_NAME);

        // Get all the issFileParserList where districtName contains UPDATED_DISTRICT_NAME
        defaultIssFileParserShouldNotBeFound("districtName.contains=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtName does not contain DEFAULT_DISTRICT_NAME
        defaultIssFileParserShouldNotBeFound("districtName.doesNotContain=" + DEFAULT_DISTRICT_NAME);

        // Get all the issFileParserList where districtName does not contain UPDATED_DISTRICT_NAME
        defaultIssFileParserShouldBeFound("districtName.doesNotContain=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtCode equals to DEFAULT_DISTRICT_CODE
        defaultIssFileParserShouldBeFound("districtCode.equals=" + DEFAULT_DISTRICT_CODE);

        // Get all the issFileParserList where districtCode equals to UPDATED_DISTRICT_CODE
        defaultIssFileParserShouldNotBeFound("districtCode.equals=" + UPDATED_DISTRICT_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtCode in DEFAULT_DISTRICT_CODE or UPDATED_DISTRICT_CODE
        defaultIssFileParserShouldBeFound("districtCode.in=" + DEFAULT_DISTRICT_CODE + "," + UPDATED_DISTRICT_CODE);

        // Get all the issFileParserList where districtCode equals to UPDATED_DISTRICT_CODE
        defaultIssFileParserShouldNotBeFound("districtCode.in=" + UPDATED_DISTRICT_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtCode is not null
        defaultIssFileParserShouldBeFound("districtCode.specified=true");

        // Get all the issFileParserList where districtCode is null
        defaultIssFileParserShouldNotBeFound("districtCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictCodeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtCode contains DEFAULT_DISTRICT_CODE
        defaultIssFileParserShouldBeFound("districtCode.contains=" + DEFAULT_DISTRICT_CODE);

        // Get all the issFileParserList where districtCode contains UPDATED_DISTRICT_CODE
        defaultIssFileParserShouldNotBeFound("districtCode.contains=" + UPDATED_DISTRICT_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDistrictCodeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where districtCode does not contain DEFAULT_DISTRICT_CODE
        defaultIssFileParserShouldNotBeFound("districtCode.doesNotContain=" + DEFAULT_DISTRICT_CODE);

        // Get all the issFileParserList where districtCode does not contain UPDATED_DISTRICT_CODE
        defaultIssFileParserShouldBeFound("districtCode.doesNotContain=" + UPDATED_DISTRICT_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockCode equals to DEFAULT_BLOCK_CODE
        defaultIssFileParserShouldBeFound("blockCode.equals=" + DEFAULT_BLOCK_CODE);

        // Get all the issFileParserList where blockCode equals to UPDATED_BLOCK_CODE
        defaultIssFileParserShouldNotBeFound("blockCode.equals=" + UPDATED_BLOCK_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockCode in DEFAULT_BLOCK_CODE or UPDATED_BLOCK_CODE
        defaultIssFileParserShouldBeFound("blockCode.in=" + DEFAULT_BLOCK_CODE + "," + UPDATED_BLOCK_CODE);

        // Get all the issFileParserList where blockCode equals to UPDATED_BLOCK_CODE
        defaultIssFileParserShouldNotBeFound("blockCode.in=" + UPDATED_BLOCK_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockCode is not null
        defaultIssFileParserShouldBeFound("blockCode.specified=true");

        // Get all the issFileParserList where blockCode is null
        defaultIssFileParserShouldNotBeFound("blockCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockCodeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockCode contains DEFAULT_BLOCK_CODE
        defaultIssFileParserShouldBeFound("blockCode.contains=" + DEFAULT_BLOCK_CODE);

        // Get all the issFileParserList where blockCode contains UPDATED_BLOCK_CODE
        defaultIssFileParserShouldNotBeFound("blockCode.contains=" + UPDATED_BLOCK_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockCodeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockCode does not contain DEFAULT_BLOCK_CODE
        defaultIssFileParserShouldNotBeFound("blockCode.doesNotContain=" + DEFAULT_BLOCK_CODE);

        // Get all the issFileParserList where blockCode does not contain UPDATED_BLOCK_CODE
        defaultIssFileParserShouldBeFound("blockCode.doesNotContain=" + UPDATED_BLOCK_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockName equals to DEFAULT_BLOCK_NAME
        defaultIssFileParserShouldBeFound("blockName.equals=" + DEFAULT_BLOCK_NAME);

        // Get all the issFileParserList where blockName equals to UPDATED_BLOCK_NAME
        defaultIssFileParserShouldNotBeFound("blockName.equals=" + UPDATED_BLOCK_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockName in DEFAULT_BLOCK_NAME or UPDATED_BLOCK_NAME
        defaultIssFileParserShouldBeFound("blockName.in=" + DEFAULT_BLOCK_NAME + "," + UPDATED_BLOCK_NAME);

        // Get all the issFileParserList where blockName equals to UPDATED_BLOCK_NAME
        defaultIssFileParserShouldNotBeFound("blockName.in=" + UPDATED_BLOCK_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockName is not null
        defaultIssFileParserShouldBeFound("blockName.specified=true");

        // Get all the issFileParserList where blockName is null
        defaultIssFileParserShouldNotBeFound("blockName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockName contains DEFAULT_BLOCK_NAME
        defaultIssFileParserShouldBeFound("blockName.contains=" + DEFAULT_BLOCK_NAME);

        // Get all the issFileParserList where blockName contains UPDATED_BLOCK_NAME
        defaultIssFileParserShouldNotBeFound("blockName.contains=" + UPDATED_BLOCK_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByBlockNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where blockName does not contain DEFAULT_BLOCK_NAME
        defaultIssFileParserShouldNotBeFound("blockName.doesNotContain=" + DEFAULT_BLOCK_NAME);

        // Get all the issFileParserList where blockName does not contain UPDATED_BLOCK_NAME
        defaultIssFileParserShouldBeFound("blockName.doesNotContain=" + UPDATED_BLOCK_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageCode equals to DEFAULT_VILLAGE_CODE
        defaultIssFileParserShouldBeFound("villageCode.equals=" + DEFAULT_VILLAGE_CODE);

        // Get all the issFileParserList where villageCode equals to UPDATED_VILLAGE_CODE
        defaultIssFileParserShouldNotBeFound("villageCode.equals=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageCode in DEFAULT_VILLAGE_CODE or UPDATED_VILLAGE_CODE
        defaultIssFileParserShouldBeFound("villageCode.in=" + DEFAULT_VILLAGE_CODE + "," + UPDATED_VILLAGE_CODE);

        // Get all the issFileParserList where villageCode equals to UPDATED_VILLAGE_CODE
        defaultIssFileParserShouldNotBeFound("villageCode.in=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageCode is not null
        defaultIssFileParserShouldBeFound("villageCode.specified=true");

        // Get all the issFileParserList where villageCode is null
        defaultIssFileParserShouldNotBeFound("villageCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageCodeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageCode contains DEFAULT_VILLAGE_CODE
        defaultIssFileParserShouldBeFound("villageCode.contains=" + DEFAULT_VILLAGE_CODE);

        // Get all the issFileParserList where villageCode contains UPDATED_VILLAGE_CODE
        defaultIssFileParserShouldNotBeFound("villageCode.contains=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageCodeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageCode does not contain DEFAULT_VILLAGE_CODE
        defaultIssFileParserShouldNotBeFound("villageCode.doesNotContain=" + DEFAULT_VILLAGE_CODE);

        // Get all the issFileParserList where villageCode does not contain UPDATED_VILLAGE_CODE
        defaultIssFileParserShouldBeFound("villageCode.doesNotContain=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageName equals to DEFAULT_VILLAGE_NAME
        defaultIssFileParserShouldBeFound("villageName.equals=" + DEFAULT_VILLAGE_NAME);

        // Get all the issFileParserList where villageName equals to UPDATED_VILLAGE_NAME
        defaultIssFileParserShouldNotBeFound("villageName.equals=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageName in DEFAULT_VILLAGE_NAME or UPDATED_VILLAGE_NAME
        defaultIssFileParserShouldBeFound("villageName.in=" + DEFAULT_VILLAGE_NAME + "," + UPDATED_VILLAGE_NAME);

        // Get all the issFileParserList where villageName equals to UPDATED_VILLAGE_NAME
        defaultIssFileParserShouldNotBeFound("villageName.in=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageName is not null
        defaultIssFileParserShouldBeFound("villageName.specified=true");

        // Get all the issFileParserList where villageName is null
        defaultIssFileParserShouldNotBeFound("villageName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageName contains DEFAULT_VILLAGE_NAME
        defaultIssFileParserShouldBeFound("villageName.contains=" + DEFAULT_VILLAGE_NAME);

        // Get all the issFileParserList where villageName contains UPDATED_VILLAGE_NAME
        defaultIssFileParserShouldNotBeFound("villageName.contains=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByVillageNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where villageName does not contain DEFAULT_VILLAGE_NAME
        defaultIssFileParserShouldNotBeFound("villageName.doesNotContain=" + DEFAULT_VILLAGE_NAME);

        // Get all the issFileParserList where villageName does not contain UPDATED_VILLAGE_NAME
        defaultIssFileParserShouldBeFound("villageName.doesNotContain=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where address equals to DEFAULT_ADDRESS
        defaultIssFileParserShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the issFileParserList where address equals to UPDATED_ADDRESS
        defaultIssFileParserShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultIssFileParserShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the issFileParserList where address equals to UPDATED_ADDRESS
        defaultIssFileParserShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where address is not null
        defaultIssFileParserShouldBeFound("address.specified=true");

        // Get all the issFileParserList where address is null
        defaultIssFileParserShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAddressContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where address contains DEFAULT_ADDRESS
        defaultIssFileParserShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the issFileParserList where address contains UPDATED_ADDRESS
        defaultIssFileParserShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where address does not contain DEFAULT_ADDRESS
        defaultIssFileParserShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the issFileParserList where address does not contain UPDATED_ADDRESS
        defaultIssFileParserShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pinCode equals to DEFAULT_PIN_CODE
        defaultIssFileParserShouldBeFound("pinCode.equals=" + DEFAULT_PIN_CODE);

        // Get all the issFileParserList where pinCode equals to UPDATED_PIN_CODE
        defaultIssFileParserShouldNotBeFound("pinCode.equals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pinCode in DEFAULT_PIN_CODE or UPDATED_PIN_CODE
        defaultIssFileParserShouldBeFound("pinCode.in=" + DEFAULT_PIN_CODE + "," + UPDATED_PIN_CODE);

        // Get all the issFileParserList where pinCode equals to UPDATED_PIN_CODE
        defaultIssFileParserShouldNotBeFound("pinCode.in=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pinCode is not null
        defaultIssFileParserShouldBeFound("pinCode.specified=true");

        // Get all the issFileParserList where pinCode is null
        defaultIssFileParserShouldNotBeFound("pinCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPinCodeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pinCode contains DEFAULT_PIN_CODE
        defaultIssFileParserShouldBeFound("pinCode.contains=" + DEFAULT_PIN_CODE);

        // Get all the issFileParserList where pinCode contains UPDATED_PIN_CODE
        defaultIssFileParserShouldNotBeFound("pinCode.contains=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPinCodeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pinCode does not contain DEFAULT_PIN_CODE
        defaultIssFileParserShouldNotBeFound("pinCode.doesNotContain=" + DEFAULT_PIN_CODE);

        // Get all the issFileParserList where pinCode does not contain UPDATED_PIN_CODE
        defaultIssFileParserShouldBeFound("pinCode.doesNotContain=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultIssFileParserShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the issFileParserList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultIssFileParserShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultIssFileParserShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the issFileParserList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultIssFileParserShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountType is not null
        defaultIssFileParserShouldBeFound("accountType.specified=true");

        // Get all the issFileParserList where accountType is null
        defaultIssFileParserShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountTypeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountType contains DEFAULT_ACCOUNT_TYPE
        defaultIssFileParserShouldBeFound("accountType.contains=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the issFileParserList where accountType contains UPDATED_ACCOUNT_TYPE
        defaultIssFileParserShouldNotBeFound("accountType.contains=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountTypeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountType does not contain DEFAULT_ACCOUNT_TYPE
        defaultIssFileParserShouldNotBeFound("accountType.doesNotContain=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the issFileParserList where accountType does not contain UPDATED_ACCOUNT_TYPE
        defaultIssFileParserShouldBeFound("accountType.doesNotContain=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultIssFileParserShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the issFileParserList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultIssFileParserShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultIssFileParserShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the issFileParserList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultIssFileParserShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountNumber is not null
        defaultIssFileParserShouldBeFound("accountNumber.specified=true");

        // Get all the issFileParserList where accountNumber is null
        defaultIssFileParserShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultIssFileParserShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the issFileParserList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultIssFileParserShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultIssFileParserShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the issFileParserList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultIssFileParserShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsName equals to DEFAULT_PACS_NAME
        defaultIssFileParserShouldBeFound("pacsName.equals=" + DEFAULT_PACS_NAME);

        // Get all the issFileParserList where pacsName equals to UPDATED_PACS_NAME
        defaultIssFileParserShouldNotBeFound("pacsName.equals=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsName in DEFAULT_PACS_NAME or UPDATED_PACS_NAME
        defaultIssFileParserShouldBeFound("pacsName.in=" + DEFAULT_PACS_NAME + "," + UPDATED_PACS_NAME);

        // Get all the issFileParserList where pacsName equals to UPDATED_PACS_NAME
        defaultIssFileParserShouldNotBeFound("pacsName.in=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsName is not null
        defaultIssFileParserShouldBeFound("pacsName.specified=true");

        // Get all the issFileParserList where pacsName is null
        defaultIssFileParserShouldNotBeFound("pacsName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsName contains DEFAULT_PACS_NAME
        defaultIssFileParserShouldBeFound("pacsName.contains=" + DEFAULT_PACS_NAME);

        // Get all the issFileParserList where pacsName contains UPDATED_PACS_NAME
        defaultIssFileParserShouldNotBeFound("pacsName.contains=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsName does not contain DEFAULT_PACS_NAME
        defaultIssFileParserShouldNotBeFound("pacsName.doesNotContain=" + DEFAULT_PACS_NAME);

        // Get all the issFileParserList where pacsName does not contain UPDATED_PACS_NAME
        defaultIssFileParserShouldBeFound("pacsName.doesNotContain=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsNumber equals to DEFAULT_PACS_NUMBER
        defaultIssFileParserShouldBeFound("pacsNumber.equals=" + DEFAULT_PACS_NUMBER);

        // Get all the issFileParserList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultIssFileParserShouldNotBeFound("pacsNumber.equals=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsNumber in DEFAULT_PACS_NUMBER or UPDATED_PACS_NUMBER
        defaultIssFileParserShouldBeFound("pacsNumber.in=" + DEFAULT_PACS_NUMBER + "," + UPDATED_PACS_NUMBER);

        // Get all the issFileParserList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultIssFileParserShouldNotBeFound("pacsNumber.in=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsNumber is not null
        defaultIssFileParserShouldBeFound("pacsNumber.specified=true");

        // Get all the issFileParserList where pacsNumber is null
        defaultIssFileParserShouldNotBeFound("pacsNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNumberContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsNumber contains DEFAULT_PACS_NUMBER
        defaultIssFileParserShouldBeFound("pacsNumber.contains=" + DEFAULT_PACS_NUMBER);

        // Get all the issFileParserList where pacsNumber contains UPDATED_PACS_NUMBER
        defaultIssFileParserShouldNotBeFound("pacsNumber.contains=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPacsNumberNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where pacsNumber does not contain DEFAULT_PACS_NUMBER
        defaultIssFileParserShouldNotBeFound("pacsNumber.doesNotContain=" + DEFAULT_PACS_NUMBER);

        // Get all the issFileParserList where pacsNumber does not contain UPDATED_PACS_NUMBER
        defaultIssFileParserShouldBeFound("pacsNumber.doesNotContain=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountHolderTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountHolderType equals to DEFAULT_ACCOUNT_HOLDER_TYPE
        defaultIssFileParserShouldBeFound("accountHolderType.equals=" + DEFAULT_ACCOUNT_HOLDER_TYPE);

        // Get all the issFileParserList where accountHolderType equals to UPDATED_ACCOUNT_HOLDER_TYPE
        defaultIssFileParserShouldNotBeFound("accountHolderType.equals=" + UPDATED_ACCOUNT_HOLDER_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountHolderTypeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountHolderType in DEFAULT_ACCOUNT_HOLDER_TYPE or UPDATED_ACCOUNT_HOLDER_TYPE
        defaultIssFileParserShouldBeFound("accountHolderType.in=" + DEFAULT_ACCOUNT_HOLDER_TYPE + "," + UPDATED_ACCOUNT_HOLDER_TYPE);

        // Get all the issFileParserList where accountHolderType equals to UPDATED_ACCOUNT_HOLDER_TYPE
        defaultIssFileParserShouldNotBeFound("accountHolderType.in=" + UPDATED_ACCOUNT_HOLDER_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountHolderTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountHolderType is not null
        defaultIssFileParserShouldBeFound("accountHolderType.specified=true");

        // Get all the issFileParserList where accountHolderType is null
        defaultIssFileParserShouldNotBeFound("accountHolderType.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountHolderTypeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountHolderType contains DEFAULT_ACCOUNT_HOLDER_TYPE
        defaultIssFileParserShouldBeFound("accountHolderType.contains=" + DEFAULT_ACCOUNT_HOLDER_TYPE);

        // Get all the issFileParserList where accountHolderType contains UPDATED_ACCOUNT_HOLDER_TYPE
        defaultIssFileParserShouldNotBeFound("accountHolderType.contains=" + UPDATED_ACCOUNT_HOLDER_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAccountHolderTypeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where accountHolderType does not contain DEFAULT_ACCOUNT_HOLDER_TYPE
        defaultIssFileParserShouldNotBeFound("accountHolderType.doesNotContain=" + DEFAULT_ACCOUNT_HOLDER_TYPE);

        // Get all the issFileParserList where accountHolderType does not contain UPDATED_ACCOUNT_HOLDER_TYPE
        defaultIssFileParserShouldBeFound("accountHolderType.doesNotContain=" + UPDATED_ACCOUNT_HOLDER_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPrimaryOccupationIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where primaryOccupation equals to DEFAULT_PRIMARY_OCCUPATION
        defaultIssFileParserShouldBeFound("primaryOccupation.equals=" + DEFAULT_PRIMARY_OCCUPATION);

        // Get all the issFileParserList where primaryOccupation equals to UPDATED_PRIMARY_OCCUPATION
        defaultIssFileParserShouldNotBeFound("primaryOccupation.equals=" + UPDATED_PRIMARY_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPrimaryOccupationIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where primaryOccupation in DEFAULT_PRIMARY_OCCUPATION or UPDATED_PRIMARY_OCCUPATION
        defaultIssFileParserShouldBeFound("primaryOccupation.in=" + DEFAULT_PRIMARY_OCCUPATION + "," + UPDATED_PRIMARY_OCCUPATION);

        // Get all the issFileParserList where primaryOccupation equals to UPDATED_PRIMARY_OCCUPATION
        defaultIssFileParserShouldNotBeFound("primaryOccupation.in=" + UPDATED_PRIMARY_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPrimaryOccupationIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where primaryOccupation is not null
        defaultIssFileParserShouldBeFound("primaryOccupation.specified=true");

        // Get all the issFileParserList where primaryOccupation is null
        defaultIssFileParserShouldNotBeFound("primaryOccupation.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPrimaryOccupationContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where primaryOccupation contains DEFAULT_PRIMARY_OCCUPATION
        defaultIssFileParserShouldBeFound("primaryOccupation.contains=" + DEFAULT_PRIMARY_OCCUPATION);

        // Get all the issFileParserList where primaryOccupation contains UPDATED_PRIMARY_OCCUPATION
        defaultIssFileParserShouldNotBeFound("primaryOccupation.contains=" + UPDATED_PRIMARY_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByPrimaryOccupationNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where primaryOccupation does not contain DEFAULT_PRIMARY_OCCUPATION
        defaultIssFileParserShouldNotBeFound("primaryOccupation.doesNotContain=" + DEFAULT_PRIMARY_OCCUPATION);

        // Get all the issFileParserList where primaryOccupation does not contain UPDATED_PRIMARY_OCCUPATION
        defaultIssFileParserShouldBeFound("primaryOccupation.doesNotContain=" + UPDATED_PRIMARY_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSactionDate equals to DEFAULT_LOAN_SACTION_DATE
        defaultIssFileParserShouldBeFound("loanSactionDate.equals=" + DEFAULT_LOAN_SACTION_DATE);

        // Get all the issFileParserList where loanSactionDate equals to UPDATED_LOAN_SACTION_DATE
        defaultIssFileParserShouldNotBeFound("loanSactionDate.equals=" + UPDATED_LOAN_SACTION_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSactionDate in DEFAULT_LOAN_SACTION_DATE or UPDATED_LOAN_SACTION_DATE
        defaultIssFileParserShouldBeFound("loanSactionDate.in=" + DEFAULT_LOAN_SACTION_DATE + "," + UPDATED_LOAN_SACTION_DATE);

        // Get all the issFileParserList where loanSactionDate equals to UPDATED_LOAN_SACTION_DATE
        defaultIssFileParserShouldNotBeFound("loanSactionDate.in=" + UPDATED_LOAN_SACTION_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSactionDate is not null
        defaultIssFileParserShouldBeFound("loanSactionDate.specified=true");

        // Get all the issFileParserList where loanSactionDate is null
        defaultIssFileParserShouldNotBeFound("loanSactionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSactionDateContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSactionDate contains DEFAULT_LOAN_SACTION_DATE
        defaultIssFileParserShouldBeFound("loanSactionDate.contains=" + DEFAULT_LOAN_SACTION_DATE);

        // Get all the issFileParserList where loanSactionDate contains UPDATED_LOAN_SACTION_DATE
        defaultIssFileParserShouldNotBeFound("loanSactionDate.contains=" + UPDATED_LOAN_SACTION_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSactionDateNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSactionDate does not contain DEFAULT_LOAN_SACTION_DATE
        defaultIssFileParserShouldNotBeFound("loanSactionDate.doesNotContain=" + DEFAULT_LOAN_SACTION_DATE);

        // Get all the issFileParserList where loanSactionDate does not contain UPDATED_LOAN_SACTION_DATE
        defaultIssFileParserShouldBeFound("loanSactionDate.doesNotContain=" + UPDATED_LOAN_SACTION_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSanctionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSanctionAmount equals to DEFAULT_LOAN_SANCTION_AMOUNT
        defaultIssFileParserShouldBeFound("loanSanctionAmount.equals=" + DEFAULT_LOAN_SANCTION_AMOUNT);

        // Get all the issFileParserList where loanSanctionAmount equals to UPDATED_LOAN_SANCTION_AMOUNT
        defaultIssFileParserShouldNotBeFound("loanSanctionAmount.equals=" + UPDATED_LOAN_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSanctionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSanctionAmount in DEFAULT_LOAN_SANCTION_AMOUNT or UPDATED_LOAN_SANCTION_AMOUNT
        defaultIssFileParserShouldBeFound("loanSanctionAmount.in=" + DEFAULT_LOAN_SANCTION_AMOUNT + "," + UPDATED_LOAN_SANCTION_AMOUNT);

        // Get all the issFileParserList where loanSanctionAmount equals to UPDATED_LOAN_SANCTION_AMOUNT
        defaultIssFileParserShouldNotBeFound("loanSanctionAmount.in=" + UPDATED_LOAN_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSanctionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSanctionAmount is not null
        defaultIssFileParserShouldBeFound("loanSanctionAmount.specified=true");

        // Get all the issFileParserList where loanSanctionAmount is null
        defaultIssFileParserShouldNotBeFound("loanSanctionAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSanctionAmountContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSanctionAmount contains DEFAULT_LOAN_SANCTION_AMOUNT
        defaultIssFileParserShouldBeFound("loanSanctionAmount.contains=" + DEFAULT_LOAN_SANCTION_AMOUNT);

        // Get all the issFileParserList where loanSanctionAmount contains UPDATED_LOAN_SANCTION_AMOUNT
        defaultIssFileParserShouldNotBeFound("loanSanctionAmount.contains=" + UPDATED_LOAN_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLoanSanctionAmountNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where loanSanctionAmount does not contain DEFAULT_LOAN_SANCTION_AMOUNT
        defaultIssFileParserShouldNotBeFound("loanSanctionAmount.doesNotContain=" + DEFAULT_LOAN_SANCTION_AMOUNT);

        // Get all the issFileParserList where loanSanctionAmount does not contain UPDATED_LOAN_SANCTION_AMOUNT
        defaultIssFileParserShouldBeFound("loanSanctionAmount.doesNotContain=" + UPDATED_LOAN_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByTenureOFLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where tenureOFLoan equals to DEFAULT_TENURE_OF_LOAN
        defaultIssFileParserShouldBeFound("tenureOFLoan.equals=" + DEFAULT_TENURE_OF_LOAN);

        // Get all the issFileParserList where tenureOFLoan equals to UPDATED_TENURE_OF_LOAN
        defaultIssFileParserShouldNotBeFound("tenureOFLoan.equals=" + UPDATED_TENURE_OF_LOAN);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByTenureOFLoanIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where tenureOFLoan in DEFAULT_TENURE_OF_LOAN or UPDATED_TENURE_OF_LOAN
        defaultIssFileParserShouldBeFound("tenureOFLoan.in=" + DEFAULT_TENURE_OF_LOAN + "," + UPDATED_TENURE_OF_LOAN);

        // Get all the issFileParserList where tenureOFLoan equals to UPDATED_TENURE_OF_LOAN
        defaultIssFileParserShouldNotBeFound("tenureOFLoan.in=" + UPDATED_TENURE_OF_LOAN);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByTenureOFLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where tenureOFLoan is not null
        defaultIssFileParserShouldBeFound("tenureOFLoan.specified=true");

        // Get all the issFileParserList where tenureOFLoan is null
        defaultIssFileParserShouldNotBeFound("tenureOFLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByTenureOFLoanContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where tenureOFLoan contains DEFAULT_TENURE_OF_LOAN
        defaultIssFileParserShouldBeFound("tenureOFLoan.contains=" + DEFAULT_TENURE_OF_LOAN);

        // Get all the issFileParserList where tenureOFLoan contains UPDATED_TENURE_OF_LOAN
        defaultIssFileParserShouldNotBeFound("tenureOFLoan.contains=" + UPDATED_TENURE_OF_LOAN);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByTenureOFLoanNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where tenureOFLoan does not contain DEFAULT_TENURE_OF_LOAN
        defaultIssFileParserShouldNotBeFound("tenureOFLoan.doesNotContain=" + DEFAULT_TENURE_OF_LOAN);

        // Get all the issFileParserList where tenureOFLoan does not contain UPDATED_TENURE_OF_LOAN
        defaultIssFileParserShouldBeFound("tenureOFLoan.doesNotContain=" + UPDATED_TENURE_OF_LOAN);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateOfOverDuePaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateOfOverDuePayment equals to DEFAULT_DATE_OF_OVER_DUE_PAYMENT
        defaultIssFileParserShouldBeFound("dateOfOverDuePayment.equals=" + DEFAULT_DATE_OF_OVER_DUE_PAYMENT);

        // Get all the issFileParserList where dateOfOverDuePayment equals to UPDATED_DATE_OF_OVER_DUE_PAYMENT
        defaultIssFileParserShouldNotBeFound("dateOfOverDuePayment.equals=" + UPDATED_DATE_OF_OVER_DUE_PAYMENT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateOfOverDuePaymentIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateOfOverDuePayment in DEFAULT_DATE_OF_OVER_DUE_PAYMENT or UPDATED_DATE_OF_OVER_DUE_PAYMENT
        defaultIssFileParserShouldBeFound(
            "dateOfOverDuePayment.in=" + DEFAULT_DATE_OF_OVER_DUE_PAYMENT + "," + UPDATED_DATE_OF_OVER_DUE_PAYMENT
        );

        // Get all the issFileParserList where dateOfOverDuePayment equals to UPDATED_DATE_OF_OVER_DUE_PAYMENT
        defaultIssFileParserShouldNotBeFound("dateOfOverDuePayment.in=" + UPDATED_DATE_OF_OVER_DUE_PAYMENT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateOfOverDuePaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateOfOverDuePayment is not null
        defaultIssFileParserShouldBeFound("dateOfOverDuePayment.specified=true");

        // Get all the issFileParserList where dateOfOverDuePayment is null
        defaultIssFileParserShouldNotBeFound("dateOfOverDuePayment.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateOfOverDuePaymentContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateOfOverDuePayment contains DEFAULT_DATE_OF_OVER_DUE_PAYMENT
        defaultIssFileParserShouldBeFound("dateOfOverDuePayment.contains=" + DEFAULT_DATE_OF_OVER_DUE_PAYMENT);

        // Get all the issFileParserList where dateOfOverDuePayment contains UPDATED_DATE_OF_OVER_DUE_PAYMENT
        defaultIssFileParserShouldNotBeFound("dateOfOverDuePayment.contains=" + UPDATED_DATE_OF_OVER_DUE_PAYMENT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDateOfOverDuePaymentNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where dateOfOverDuePayment does not contain DEFAULT_DATE_OF_OVER_DUE_PAYMENT
        defaultIssFileParserShouldNotBeFound("dateOfOverDuePayment.doesNotContain=" + DEFAULT_DATE_OF_OVER_DUE_PAYMENT);

        // Get all the issFileParserList where dateOfOverDuePayment does not contain UPDATED_DATE_OF_OVER_DUE_PAYMENT
        defaultIssFileParserShouldBeFound("dateOfOverDuePayment.doesNotContain=" + UPDATED_DATE_OF_OVER_DUE_PAYMENT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByCropNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where cropName equals to DEFAULT_CROP_NAME
        defaultIssFileParserShouldBeFound("cropName.equals=" + DEFAULT_CROP_NAME);

        // Get all the issFileParserList where cropName equals to UPDATED_CROP_NAME
        defaultIssFileParserShouldNotBeFound("cropName.equals=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByCropNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where cropName in DEFAULT_CROP_NAME or UPDATED_CROP_NAME
        defaultIssFileParserShouldBeFound("cropName.in=" + DEFAULT_CROP_NAME + "," + UPDATED_CROP_NAME);

        // Get all the issFileParserList where cropName equals to UPDATED_CROP_NAME
        defaultIssFileParserShouldNotBeFound("cropName.in=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByCropNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where cropName is not null
        defaultIssFileParserShouldBeFound("cropName.specified=true");

        // Get all the issFileParserList where cropName is null
        defaultIssFileParserShouldNotBeFound("cropName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByCropNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where cropName contains DEFAULT_CROP_NAME
        defaultIssFileParserShouldBeFound("cropName.contains=" + DEFAULT_CROP_NAME);

        // Get all the issFileParserList where cropName contains UPDATED_CROP_NAME
        defaultIssFileParserShouldNotBeFound("cropName.contains=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByCropNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where cropName does not contain DEFAULT_CROP_NAME
        defaultIssFileParserShouldNotBeFound("cropName.doesNotContain=" + DEFAULT_CROP_NAME);

        // Get all the issFileParserList where cropName does not contain UPDATED_CROP_NAME
        defaultIssFileParserShouldBeFound("cropName.doesNotContain=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySurveyNoIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where surveyNo equals to DEFAULT_SURVEY_NO
        defaultIssFileParserShouldBeFound("surveyNo.equals=" + DEFAULT_SURVEY_NO);

        // Get all the issFileParserList where surveyNo equals to UPDATED_SURVEY_NO
        defaultIssFileParserShouldNotBeFound("surveyNo.equals=" + UPDATED_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySurveyNoIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where surveyNo in DEFAULT_SURVEY_NO or UPDATED_SURVEY_NO
        defaultIssFileParserShouldBeFound("surveyNo.in=" + DEFAULT_SURVEY_NO + "," + UPDATED_SURVEY_NO);

        // Get all the issFileParserList where surveyNo equals to UPDATED_SURVEY_NO
        defaultIssFileParserShouldNotBeFound("surveyNo.in=" + UPDATED_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySurveyNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where surveyNo is not null
        defaultIssFileParserShouldBeFound("surveyNo.specified=true");

        // Get all the issFileParserList where surveyNo is null
        defaultIssFileParserShouldNotBeFound("surveyNo.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySurveyNoContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where surveyNo contains DEFAULT_SURVEY_NO
        defaultIssFileParserShouldBeFound("surveyNo.contains=" + DEFAULT_SURVEY_NO);

        // Get all the issFileParserList where surveyNo contains UPDATED_SURVEY_NO
        defaultIssFileParserShouldNotBeFound("surveyNo.contains=" + UPDATED_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySurveyNoNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where surveyNo does not contain DEFAULT_SURVEY_NO
        defaultIssFileParserShouldNotBeFound("surveyNo.doesNotContain=" + DEFAULT_SURVEY_NO);

        // Get all the issFileParserList where surveyNo does not contain UPDATED_SURVEY_NO
        defaultIssFileParserShouldBeFound("surveyNo.doesNotContain=" + UPDATED_SURVEY_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySatBaraSubsurveyNoIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where satBaraSubsurveyNo equals to DEFAULT_SAT_BARA_SUBSURVEY_NO
        defaultIssFileParserShouldBeFound("satBaraSubsurveyNo.equals=" + DEFAULT_SAT_BARA_SUBSURVEY_NO);

        // Get all the issFileParserList where satBaraSubsurveyNo equals to UPDATED_SAT_BARA_SUBSURVEY_NO
        defaultIssFileParserShouldNotBeFound("satBaraSubsurveyNo.equals=" + UPDATED_SAT_BARA_SUBSURVEY_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySatBaraSubsurveyNoIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where satBaraSubsurveyNo in DEFAULT_SAT_BARA_SUBSURVEY_NO or UPDATED_SAT_BARA_SUBSURVEY_NO
        defaultIssFileParserShouldBeFound("satBaraSubsurveyNo.in=" + DEFAULT_SAT_BARA_SUBSURVEY_NO + "," + UPDATED_SAT_BARA_SUBSURVEY_NO);

        // Get all the issFileParserList where satBaraSubsurveyNo equals to UPDATED_SAT_BARA_SUBSURVEY_NO
        defaultIssFileParserShouldNotBeFound("satBaraSubsurveyNo.in=" + UPDATED_SAT_BARA_SUBSURVEY_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySatBaraSubsurveyNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where satBaraSubsurveyNo is not null
        defaultIssFileParserShouldBeFound("satBaraSubsurveyNo.specified=true");

        // Get all the issFileParserList where satBaraSubsurveyNo is null
        defaultIssFileParserShouldNotBeFound("satBaraSubsurveyNo.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySatBaraSubsurveyNoContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where satBaraSubsurveyNo contains DEFAULT_SAT_BARA_SUBSURVEY_NO
        defaultIssFileParserShouldBeFound("satBaraSubsurveyNo.contains=" + DEFAULT_SAT_BARA_SUBSURVEY_NO);

        // Get all the issFileParserList where satBaraSubsurveyNo contains UPDATED_SAT_BARA_SUBSURVEY_NO
        defaultIssFileParserShouldNotBeFound("satBaraSubsurveyNo.contains=" + UPDATED_SAT_BARA_SUBSURVEY_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySatBaraSubsurveyNoNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where satBaraSubsurveyNo does not contain DEFAULT_SAT_BARA_SUBSURVEY_NO
        defaultIssFileParserShouldNotBeFound("satBaraSubsurveyNo.doesNotContain=" + DEFAULT_SAT_BARA_SUBSURVEY_NO);

        // Get all the issFileParserList where satBaraSubsurveyNo does not contain UPDATED_SAT_BARA_SUBSURVEY_NO
        defaultIssFileParserShouldBeFound("satBaraSubsurveyNo.doesNotContain=" + UPDATED_SAT_BARA_SUBSURVEY_NO);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySeasonNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where seasonName equals to DEFAULT_SEASON_NAME
        defaultIssFileParserShouldBeFound("seasonName.equals=" + DEFAULT_SEASON_NAME);

        // Get all the issFileParserList where seasonName equals to UPDATED_SEASON_NAME
        defaultIssFileParserShouldNotBeFound("seasonName.equals=" + UPDATED_SEASON_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySeasonNameIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where seasonName in DEFAULT_SEASON_NAME or UPDATED_SEASON_NAME
        defaultIssFileParserShouldBeFound("seasonName.in=" + DEFAULT_SEASON_NAME + "," + UPDATED_SEASON_NAME);

        // Get all the issFileParserList where seasonName equals to UPDATED_SEASON_NAME
        defaultIssFileParserShouldNotBeFound("seasonName.in=" + UPDATED_SEASON_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySeasonNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where seasonName is not null
        defaultIssFileParserShouldBeFound("seasonName.specified=true");

        // Get all the issFileParserList where seasonName is null
        defaultIssFileParserShouldNotBeFound("seasonName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySeasonNameContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where seasonName contains DEFAULT_SEASON_NAME
        defaultIssFileParserShouldBeFound("seasonName.contains=" + DEFAULT_SEASON_NAME);

        // Get all the issFileParserList where seasonName contains UPDATED_SEASON_NAME
        defaultIssFileParserShouldNotBeFound("seasonName.contains=" + UPDATED_SEASON_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersBySeasonNameNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where seasonName does not contain DEFAULT_SEASON_NAME
        defaultIssFileParserShouldNotBeFound("seasonName.doesNotContain=" + DEFAULT_SEASON_NAME);

        // Get all the issFileParserList where seasonName does not contain UPDATED_SEASON_NAME
        defaultIssFileParserShouldBeFound("seasonName.doesNotContain=" + UPDATED_SEASON_NAME);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAreaHectIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where areaHect equals to DEFAULT_AREA_HECT
        defaultIssFileParserShouldBeFound("areaHect.equals=" + DEFAULT_AREA_HECT);

        // Get all the issFileParserList where areaHect equals to UPDATED_AREA_HECT
        defaultIssFileParserShouldNotBeFound("areaHect.equals=" + UPDATED_AREA_HECT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAreaHectIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where areaHect in DEFAULT_AREA_HECT or UPDATED_AREA_HECT
        defaultIssFileParserShouldBeFound("areaHect.in=" + DEFAULT_AREA_HECT + "," + UPDATED_AREA_HECT);

        // Get all the issFileParserList where areaHect equals to UPDATED_AREA_HECT
        defaultIssFileParserShouldNotBeFound("areaHect.in=" + UPDATED_AREA_HECT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAreaHectIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where areaHect is not null
        defaultIssFileParserShouldBeFound("areaHect.specified=true");

        // Get all the issFileParserList where areaHect is null
        defaultIssFileParserShouldNotBeFound("areaHect.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAreaHectContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where areaHect contains DEFAULT_AREA_HECT
        defaultIssFileParserShouldBeFound("areaHect.contains=" + DEFAULT_AREA_HECT);

        // Get all the issFileParserList where areaHect contains UPDATED_AREA_HECT
        defaultIssFileParserShouldNotBeFound("areaHect.contains=" + UPDATED_AREA_HECT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByAreaHectNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where areaHect does not contain DEFAULT_AREA_HECT
        defaultIssFileParserShouldNotBeFound("areaHect.doesNotContain=" + DEFAULT_AREA_HECT);

        // Get all the issFileParserList where areaHect does not contain UPDATED_AREA_HECT
        defaultIssFileParserShouldBeFound("areaHect.doesNotContain=" + UPDATED_AREA_HECT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLandTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where landType equals to DEFAULT_LAND_TYPE
        defaultIssFileParserShouldBeFound("landType.equals=" + DEFAULT_LAND_TYPE);

        // Get all the issFileParserList where landType equals to UPDATED_LAND_TYPE
        defaultIssFileParserShouldNotBeFound("landType.equals=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLandTypeIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where landType in DEFAULT_LAND_TYPE or UPDATED_LAND_TYPE
        defaultIssFileParserShouldBeFound("landType.in=" + DEFAULT_LAND_TYPE + "," + UPDATED_LAND_TYPE);

        // Get all the issFileParserList where landType equals to UPDATED_LAND_TYPE
        defaultIssFileParserShouldNotBeFound("landType.in=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLandTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where landType is not null
        defaultIssFileParserShouldBeFound("landType.specified=true");

        // Get all the issFileParserList where landType is null
        defaultIssFileParserShouldNotBeFound("landType.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLandTypeContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where landType contains DEFAULT_LAND_TYPE
        defaultIssFileParserShouldBeFound("landType.contains=" + DEFAULT_LAND_TYPE);

        // Get all the issFileParserList where landType contains UPDATED_LAND_TYPE
        defaultIssFileParserShouldNotBeFound("landType.contains=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByLandTypeNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where landType does not contain DEFAULT_LAND_TYPE
        defaultIssFileParserShouldNotBeFound("landType.doesNotContain=" + DEFAULT_LAND_TYPE);

        // Get all the issFileParserList where landType does not contain UPDATED_LAND_TYPE
        defaultIssFileParserShouldBeFound("landType.doesNotContain=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisbursementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disbursementDate equals to DEFAULT_DISBURSEMENT_DATE
        defaultIssFileParserShouldBeFound("disbursementDate.equals=" + DEFAULT_DISBURSEMENT_DATE);

        // Get all the issFileParserList where disbursementDate equals to UPDATED_DISBURSEMENT_DATE
        defaultIssFileParserShouldNotBeFound("disbursementDate.equals=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisbursementDateIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disbursementDate in DEFAULT_DISBURSEMENT_DATE or UPDATED_DISBURSEMENT_DATE
        defaultIssFileParserShouldBeFound("disbursementDate.in=" + DEFAULT_DISBURSEMENT_DATE + "," + UPDATED_DISBURSEMENT_DATE);

        // Get all the issFileParserList where disbursementDate equals to UPDATED_DISBURSEMENT_DATE
        defaultIssFileParserShouldNotBeFound("disbursementDate.in=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisbursementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disbursementDate is not null
        defaultIssFileParserShouldBeFound("disbursementDate.specified=true");

        // Get all the issFileParserList where disbursementDate is null
        defaultIssFileParserShouldNotBeFound("disbursementDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisbursementDateContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disbursementDate contains DEFAULT_DISBURSEMENT_DATE
        defaultIssFileParserShouldBeFound("disbursementDate.contains=" + DEFAULT_DISBURSEMENT_DATE);

        // Get all the issFileParserList where disbursementDate contains UPDATED_DISBURSEMENT_DATE
        defaultIssFileParserShouldNotBeFound("disbursementDate.contains=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisbursementDateNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disbursementDate does not contain DEFAULT_DISBURSEMENT_DATE
        defaultIssFileParserShouldNotBeFound("disbursementDate.doesNotContain=" + DEFAULT_DISBURSEMENT_DATE);

        // Get all the issFileParserList where disbursementDate does not contain UPDATED_DISBURSEMENT_DATE
        defaultIssFileParserShouldBeFound("disbursementDate.doesNotContain=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisburseAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disburseAmount equals to DEFAULT_DISBURSE_AMOUNT
        defaultIssFileParserShouldBeFound("disburseAmount.equals=" + DEFAULT_DISBURSE_AMOUNT);

        // Get all the issFileParserList where disburseAmount equals to UPDATED_DISBURSE_AMOUNT
        defaultIssFileParserShouldNotBeFound("disburseAmount.equals=" + UPDATED_DISBURSE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisburseAmountIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disburseAmount in DEFAULT_DISBURSE_AMOUNT or UPDATED_DISBURSE_AMOUNT
        defaultIssFileParserShouldBeFound("disburseAmount.in=" + DEFAULT_DISBURSE_AMOUNT + "," + UPDATED_DISBURSE_AMOUNT);

        // Get all the issFileParserList where disburseAmount equals to UPDATED_DISBURSE_AMOUNT
        defaultIssFileParserShouldNotBeFound("disburseAmount.in=" + UPDATED_DISBURSE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisburseAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disburseAmount is not null
        defaultIssFileParserShouldBeFound("disburseAmount.specified=true");

        // Get all the issFileParserList where disburseAmount is null
        defaultIssFileParserShouldNotBeFound("disburseAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisburseAmountContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disburseAmount contains DEFAULT_DISBURSE_AMOUNT
        defaultIssFileParserShouldBeFound("disburseAmount.contains=" + DEFAULT_DISBURSE_AMOUNT);

        // Get all the issFileParserList where disburseAmount contains UPDATED_DISBURSE_AMOUNT
        defaultIssFileParserShouldNotBeFound("disburseAmount.contains=" + UPDATED_DISBURSE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByDisburseAmountNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where disburseAmount does not contain DEFAULT_DISBURSE_AMOUNT
        defaultIssFileParserShouldNotBeFound("disburseAmount.doesNotContain=" + DEFAULT_DISBURSE_AMOUNT);

        // Get all the issFileParserList where disburseAmount does not contain UPDATED_DISBURSE_AMOUNT
        defaultIssFileParserShouldBeFound("disburseAmount.doesNotContain=" + UPDATED_DISBURSE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMaturityLoanDateIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where maturityLoanDate equals to DEFAULT_MATURITY_LOAN_DATE
        defaultIssFileParserShouldBeFound("maturityLoanDate.equals=" + DEFAULT_MATURITY_LOAN_DATE);

        // Get all the issFileParserList where maturityLoanDate equals to UPDATED_MATURITY_LOAN_DATE
        defaultIssFileParserShouldNotBeFound("maturityLoanDate.equals=" + UPDATED_MATURITY_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMaturityLoanDateIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where maturityLoanDate in DEFAULT_MATURITY_LOAN_DATE or UPDATED_MATURITY_LOAN_DATE
        defaultIssFileParserShouldBeFound("maturityLoanDate.in=" + DEFAULT_MATURITY_LOAN_DATE + "," + UPDATED_MATURITY_LOAN_DATE);

        // Get all the issFileParserList where maturityLoanDate equals to UPDATED_MATURITY_LOAN_DATE
        defaultIssFileParserShouldNotBeFound("maturityLoanDate.in=" + UPDATED_MATURITY_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMaturityLoanDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where maturityLoanDate is not null
        defaultIssFileParserShouldBeFound("maturityLoanDate.specified=true");

        // Get all the issFileParserList where maturityLoanDate is null
        defaultIssFileParserShouldNotBeFound("maturityLoanDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMaturityLoanDateContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where maturityLoanDate contains DEFAULT_MATURITY_LOAN_DATE
        defaultIssFileParserShouldBeFound("maturityLoanDate.contains=" + DEFAULT_MATURITY_LOAN_DATE);

        // Get all the issFileParserList where maturityLoanDate contains UPDATED_MATURITY_LOAN_DATE
        defaultIssFileParserShouldNotBeFound("maturityLoanDate.contains=" + UPDATED_MATURITY_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByMaturityLoanDateNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where maturityLoanDate does not contain DEFAULT_MATURITY_LOAN_DATE
        defaultIssFileParserShouldNotBeFound("maturityLoanDate.doesNotContain=" + DEFAULT_MATURITY_LOAN_DATE);

        // Get all the issFileParserList where maturityLoanDate does not contain UPDATED_MATURITY_LOAN_DATE
        defaultIssFileParserShouldBeFound("maturityLoanDate.doesNotContain=" + UPDATED_MATURITY_LOAN_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountPrincipleIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountPrinciple equals to DEFAULT_RECOVERY_AMOUNT_PRINCIPLE
        defaultIssFileParserShouldBeFound("recoveryAmountPrinciple.equals=" + DEFAULT_RECOVERY_AMOUNT_PRINCIPLE);

        // Get all the issFileParserList where recoveryAmountPrinciple equals to UPDATED_RECOVERY_AMOUNT_PRINCIPLE
        defaultIssFileParserShouldNotBeFound("recoveryAmountPrinciple.equals=" + UPDATED_RECOVERY_AMOUNT_PRINCIPLE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountPrincipleIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountPrinciple in DEFAULT_RECOVERY_AMOUNT_PRINCIPLE or UPDATED_RECOVERY_AMOUNT_PRINCIPLE
        defaultIssFileParserShouldBeFound(
            "recoveryAmountPrinciple.in=" + DEFAULT_RECOVERY_AMOUNT_PRINCIPLE + "," + UPDATED_RECOVERY_AMOUNT_PRINCIPLE
        );

        // Get all the issFileParserList where recoveryAmountPrinciple equals to UPDATED_RECOVERY_AMOUNT_PRINCIPLE
        defaultIssFileParserShouldNotBeFound("recoveryAmountPrinciple.in=" + UPDATED_RECOVERY_AMOUNT_PRINCIPLE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountPrincipleIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountPrinciple is not null
        defaultIssFileParserShouldBeFound("recoveryAmountPrinciple.specified=true");

        // Get all the issFileParserList where recoveryAmountPrinciple is null
        defaultIssFileParserShouldNotBeFound("recoveryAmountPrinciple.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountPrincipleContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountPrinciple contains DEFAULT_RECOVERY_AMOUNT_PRINCIPLE
        defaultIssFileParserShouldBeFound("recoveryAmountPrinciple.contains=" + DEFAULT_RECOVERY_AMOUNT_PRINCIPLE);

        // Get all the issFileParserList where recoveryAmountPrinciple contains UPDATED_RECOVERY_AMOUNT_PRINCIPLE
        defaultIssFileParserShouldNotBeFound("recoveryAmountPrinciple.contains=" + UPDATED_RECOVERY_AMOUNT_PRINCIPLE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountPrincipleNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountPrinciple does not contain DEFAULT_RECOVERY_AMOUNT_PRINCIPLE
        defaultIssFileParserShouldNotBeFound("recoveryAmountPrinciple.doesNotContain=" + DEFAULT_RECOVERY_AMOUNT_PRINCIPLE);

        // Get all the issFileParserList where recoveryAmountPrinciple does not contain UPDATED_RECOVERY_AMOUNT_PRINCIPLE
        defaultIssFileParserShouldBeFound("recoveryAmountPrinciple.doesNotContain=" + UPDATED_RECOVERY_AMOUNT_PRINCIPLE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountInterest equals to DEFAULT_RECOVERY_AMOUNT_INTEREST
        defaultIssFileParserShouldBeFound("recoveryAmountInterest.equals=" + DEFAULT_RECOVERY_AMOUNT_INTEREST);

        // Get all the issFileParserList where recoveryAmountInterest equals to UPDATED_RECOVERY_AMOUNT_INTEREST
        defaultIssFileParserShouldNotBeFound("recoveryAmountInterest.equals=" + UPDATED_RECOVERY_AMOUNT_INTEREST);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountInterestIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountInterest in DEFAULT_RECOVERY_AMOUNT_INTEREST or UPDATED_RECOVERY_AMOUNT_INTEREST
        defaultIssFileParserShouldBeFound(
            "recoveryAmountInterest.in=" + DEFAULT_RECOVERY_AMOUNT_INTEREST + "," + UPDATED_RECOVERY_AMOUNT_INTEREST
        );

        // Get all the issFileParserList where recoveryAmountInterest equals to UPDATED_RECOVERY_AMOUNT_INTEREST
        defaultIssFileParserShouldNotBeFound("recoveryAmountInterest.in=" + UPDATED_RECOVERY_AMOUNT_INTEREST);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountInterest is not null
        defaultIssFileParserShouldBeFound("recoveryAmountInterest.specified=true");

        // Get all the issFileParserList where recoveryAmountInterest is null
        defaultIssFileParserShouldNotBeFound("recoveryAmountInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountInterestContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountInterest contains DEFAULT_RECOVERY_AMOUNT_INTEREST
        defaultIssFileParserShouldBeFound("recoveryAmountInterest.contains=" + DEFAULT_RECOVERY_AMOUNT_INTEREST);

        // Get all the issFileParserList where recoveryAmountInterest contains UPDATED_RECOVERY_AMOUNT_INTEREST
        defaultIssFileParserShouldNotBeFound("recoveryAmountInterest.contains=" + UPDATED_RECOVERY_AMOUNT_INTEREST);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryAmountInterestNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryAmountInterest does not contain DEFAULT_RECOVERY_AMOUNT_INTEREST
        defaultIssFileParserShouldNotBeFound("recoveryAmountInterest.doesNotContain=" + DEFAULT_RECOVERY_AMOUNT_INTEREST);

        // Get all the issFileParserList where recoveryAmountInterest does not contain UPDATED_RECOVERY_AMOUNT_INTEREST
        defaultIssFileParserShouldBeFound("recoveryAmountInterest.doesNotContain=" + UPDATED_RECOVERY_AMOUNT_INTEREST);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryDate equals to DEFAULT_RECOVERY_DATE
        defaultIssFileParserShouldBeFound("recoveryDate.equals=" + DEFAULT_RECOVERY_DATE);

        // Get all the issFileParserList where recoveryDate equals to UPDATED_RECOVERY_DATE
        defaultIssFileParserShouldNotBeFound("recoveryDate.equals=" + UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryDate in DEFAULT_RECOVERY_DATE or UPDATED_RECOVERY_DATE
        defaultIssFileParserShouldBeFound("recoveryDate.in=" + DEFAULT_RECOVERY_DATE + "," + UPDATED_RECOVERY_DATE);

        // Get all the issFileParserList where recoveryDate equals to UPDATED_RECOVERY_DATE
        defaultIssFileParserShouldNotBeFound("recoveryDate.in=" + UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryDate is not null
        defaultIssFileParserShouldBeFound("recoveryDate.specified=true");

        // Get all the issFileParserList where recoveryDate is null
        defaultIssFileParserShouldNotBeFound("recoveryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryDateContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryDate contains DEFAULT_RECOVERY_DATE
        defaultIssFileParserShouldBeFound("recoveryDate.contains=" + DEFAULT_RECOVERY_DATE);

        // Get all the issFileParserList where recoveryDate contains UPDATED_RECOVERY_DATE
        defaultIssFileParserShouldNotBeFound("recoveryDate.contains=" + UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByRecoveryDateNotContainsSomething() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        // Get all the issFileParserList where recoveryDate does not contain DEFAULT_RECOVERY_DATE
        defaultIssFileParserShouldNotBeFound("recoveryDate.doesNotContain=" + DEFAULT_RECOVERY_DATE);

        // Get all the issFileParserList where recoveryDate does not contain UPDATED_RECOVERY_DATE
        defaultIssFileParserShouldBeFound("recoveryDate.doesNotContain=" + UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void getAllIssFileParsersByIssPortalFileIsEqualToSomething() throws Exception {
        IssPortalFile issPortalFile;
        if (TestUtil.findAll(em, IssPortalFile.class).isEmpty()) {
            issFileParserRepository.saveAndFlush(issFileParser);
            issPortalFile = IssPortalFileResourceIT.createEntity(em);
        } else {
            issPortalFile = TestUtil.findAll(em, IssPortalFile.class).get(0);
        }
        em.persist(issPortalFile);
        em.flush();
        issFileParser.setIssPortalFile(issPortalFile);
        issFileParserRepository.saveAndFlush(issFileParser);
        Long issPortalFileId = issPortalFile.getId();

        // Get all the issFileParserList where issPortalFile equals to issPortalFileId
        defaultIssFileParserShouldBeFound("issPortalFileId.equals=" + issPortalFileId);

        // Get all the issFileParserList where issPortalFile equals to (issPortalFileId + 1)
        defaultIssFileParserShouldNotBeFound("issPortalFileId.equals=" + (issPortalFileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIssFileParserShouldBeFound(String filter) throws Exception {
        restIssFileParserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issFileParser.getId().intValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].bankCode").value(hasItem(DEFAULT_BANK_CODE)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].schemeWiseBranchCode").value(hasItem(DEFAULT_SCHEME_WISE_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].ifsc").value(hasItem(DEFAULT_IFSC)))
            .andExpect(jsonPath("$.[*].loanAccountNumberkcc").value(hasItem(DEFAULT_LOAN_ACCOUNT_NUMBERKCC)))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].aadharNumber").value(hasItem(DEFAULT_AADHAR_NUMBER)))
            .andExpect(jsonPath("$.[*].dateofBirth").value(hasItem(DEFAULT_DATEOF_BIRTH)))
            .andExpect(jsonPath("$.[*].ageAtTimeOfSanction").value(hasItem(DEFAULT_AGE_AT_TIME_OF_SANCTION)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].farmersCategory").value(hasItem(DEFAULT_FARMERS_CATEGORY)))
            .andExpect(jsonPath("$.[*].farmerType").value(hasItem(DEFAULT_FARMER_TYPE)))
            .andExpect(jsonPath("$.[*].socialCategory").value(hasItem(DEFAULT_SOCIAL_CATEGORY)))
            .andExpect(jsonPath("$.[*].relativeType").value(hasItem(DEFAULT_RELATIVE_TYPE)))
            .andExpect(jsonPath("$.[*].relativeName").value(hasItem(DEFAULT_RELATIVE_NAME)))
            .andExpect(jsonPath("$.[*].stateName").value(hasItem(DEFAULT_STATE_NAME)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)))
            .andExpect(jsonPath("$.[*].districtCode").value(hasItem(DEFAULT_DISTRICT_CODE)))
            .andExpect(jsonPath("$.[*].blockCode").value(hasItem(DEFAULT_BLOCK_CODE)))
            .andExpect(jsonPath("$.[*].blockName").value(hasItem(DEFAULT_BLOCK_NAME)))
            .andExpect(jsonPath("$.[*].villageCode").value(hasItem(DEFAULT_VILLAGE_CODE)))
            .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].pacsName").value(hasItem(DEFAULT_PACS_NAME)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].accountHolderType").value(hasItem(DEFAULT_ACCOUNT_HOLDER_TYPE)))
            .andExpect(jsonPath("$.[*].primaryOccupation").value(hasItem(DEFAULT_PRIMARY_OCCUPATION)))
            .andExpect(jsonPath("$.[*].loanSactionDate").value(hasItem(DEFAULT_LOAN_SACTION_DATE)))
            .andExpect(jsonPath("$.[*].loanSanctionAmount").value(hasItem(DEFAULT_LOAN_SANCTION_AMOUNT)))
            .andExpect(jsonPath("$.[*].tenureOFLoan").value(hasItem(DEFAULT_TENURE_OF_LOAN)))
            .andExpect(jsonPath("$.[*].dateOfOverDuePayment").value(hasItem(DEFAULT_DATE_OF_OVER_DUE_PAYMENT)))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].surveyNo").value(hasItem(DEFAULT_SURVEY_NO)))
            .andExpect(jsonPath("$.[*].satBaraSubsurveyNo").value(hasItem(DEFAULT_SAT_BARA_SUBSURVEY_NO)))
            .andExpect(jsonPath("$.[*].seasonName").value(hasItem(DEFAULT_SEASON_NAME)))
            .andExpect(jsonPath("$.[*].areaHect").value(hasItem(DEFAULT_AREA_HECT)))
            .andExpect(jsonPath("$.[*].landType").value(hasItem(DEFAULT_LAND_TYPE)))
            .andExpect(jsonPath("$.[*].disbursementDate").value(hasItem(DEFAULT_DISBURSEMENT_DATE)))
            .andExpect(jsonPath("$.[*].disburseAmount").value(hasItem(DEFAULT_DISBURSE_AMOUNT)))
            .andExpect(jsonPath("$.[*].maturityLoanDate").value(hasItem(DEFAULT_MATURITY_LOAN_DATE)))
            .andExpect(jsonPath("$.[*].recoveryAmountPrinciple").value(hasItem(DEFAULT_RECOVERY_AMOUNT_PRINCIPLE)))
            .andExpect(jsonPath("$.[*].recoveryAmountInterest").value(hasItem(DEFAULT_RECOVERY_AMOUNT_INTEREST)))
            .andExpect(jsonPath("$.[*].recoveryDate").value(hasItem(DEFAULT_RECOVERY_DATE)));

        // Check, that the count call also returns 1
        restIssFileParserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIssFileParserShouldNotBeFound(String filter) throws Exception {
        restIssFileParserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIssFileParserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIssFileParser() throws Exception {
        // Get the issFileParser
        restIssFileParserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIssFileParser() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        int databaseSizeBeforeUpdate = issFileParserRepository.findAll().size();

        // Update the issFileParser
        IssFileParser updatedIssFileParser = issFileParserRepository.findById(issFileParser.getId()).get();
        // Disconnect from session so that the updates on updatedIssFileParser are not directly saved in db
        em.detach(updatedIssFileParser);
        updatedIssFileParser
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .bankName(UPDATED_BANK_NAME)
            .bankCode(UPDATED_BANK_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchCode(UPDATED_BRANCH_CODE)
            .schemeWiseBranchCode(UPDATED_SCHEME_WISE_BRANCH_CODE)
            .ifsc(UPDATED_IFSC)
            .loanAccountNumberkcc(UPDATED_LOAN_ACCOUNT_NUMBERKCC)
            .farmerName(UPDATED_FARMER_NAME)
            .gender(UPDATED_GENDER)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .dateofBirth(UPDATED_DATEOF_BIRTH)
            .ageAtTimeOfSanction(UPDATED_AGE_AT_TIME_OF_SANCTION)
            .mobileNo(UPDATED_MOBILE_NO)
            .farmersCategory(UPDATED_FARMERS_CATEGORY)
            .farmerType(UPDATED_FARMER_TYPE)
            .socialCategory(UPDATED_SOCIAL_CATEGORY)
            .relativeType(UPDATED_RELATIVE_TYPE)
            .relativeName(UPDATED_RELATIVE_NAME)
            .stateName(UPDATED_STATE_NAME)
            .stateCode(UPDATED_STATE_CODE)
            .districtName(UPDATED_DISTRICT_NAME)
            .districtCode(UPDATED_DISTRICT_CODE)
            .blockCode(UPDATED_BLOCK_CODE)
            .blockName(UPDATED_BLOCK_NAME)
            .villageCode(UPDATED_VILLAGE_CODE)
            .villageName(UPDATED_VILLAGE_NAME)
            .address(UPDATED_ADDRESS)
            .pinCode(UPDATED_PIN_CODE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .pacsName(UPDATED_PACS_NAME)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .accountHolderType(UPDATED_ACCOUNT_HOLDER_TYPE)
            .primaryOccupation(UPDATED_PRIMARY_OCCUPATION)
            .loanSactionDate(UPDATED_LOAN_SACTION_DATE)
            .loanSanctionAmount(UPDATED_LOAN_SANCTION_AMOUNT)
            .tenureOFLoan(UPDATED_TENURE_OF_LOAN)
            .dateOfOverDuePayment(UPDATED_DATE_OF_OVER_DUE_PAYMENT)
            .cropName(UPDATED_CROP_NAME)
            .surveyNo(UPDATED_SURVEY_NO)
            .satBaraSubsurveyNo(UPDATED_SAT_BARA_SUBSURVEY_NO)
            .seasonName(UPDATED_SEASON_NAME)
            .areaHect(UPDATED_AREA_HECT)
            .landType(UPDATED_LAND_TYPE)
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .disburseAmount(UPDATED_DISBURSE_AMOUNT)
            .maturityLoanDate(UPDATED_MATURITY_LOAN_DATE)
            .recoveryAmountPrinciple(UPDATED_RECOVERY_AMOUNT_PRINCIPLE)
            .recoveryAmountInterest(UPDATED_RECOVERY_AMOUNT_INTEREST)
            .recoveryDate(UPDATED_RECOVERY_DATE);

        restIssFileParserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIssFileParser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIssFileParser))
            )
            .andExpect(status().isOk());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeUpdate);
        IssFileParser testIssFileParser = issFileParserList.get(issFileParserList.size() - 1);
        assertThat(testIssFileParser.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testIssFileParser.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testIssFileParser.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
        assertThat(testIssFileParser.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testIssFileParser.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testIssFileParser.getSchemeWiseBranchCode()).isEqualTo(UPDATED_SCHEME_WISE_BRANCH_CODE);
        assertThat(testIssFileParser.getIfsc()).isEqualTo(UPDATED_IFSC);
        assertThat(testIssFileParser.getLoanAccountNumberkcc()).isEqualTo(UPDATED_LOAN_ACCOUNT_NUMBERKCC);
        assertThat(testIssFileParser.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testIssFileParser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIssFileParser.getAadharNumber()).isEqualTo(UPDATED_AADHAR_NUMBER);
        assertThat(testIssFileParser.getDateofBirth()).isEqualTo(UPDATED_DATEOF_BIRTH);
        assertThat(testIssFileParser.getAgeAtTimeOfSanction()).isEqualTo(UPDATED_AGE_AT_TIME_OF_SANCTION);
        assertThat(testIssFileParser.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testIssFileParser.getFarmersCategory()).isEqualTo(UPDATED_FARMERS_CATEGORY);
        assertThat(testIssFileParser.getFarmerType()).isEqualTo(UPDATED_FARMER_TYPE);
        assertThat(testIssFileParser.getSocialCategory()).isEqualTo(UPDATED_SOCIAL_CATEGORY);
        assertThat(testIssFileParser.getRelativeType()).isEqualTo(UPDATED_RELATIVE_TYPE);
        assertThat(testIssFileParser.getRelativeName()).isEqualTo(UPDATED_RELATIVE_NAME);
        assertThat(testIssFileParser.getStateName()).isEqualTo(UPDATED_STATE_NAME);
        assertThat(testIssFileParser.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testIssFileParser.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testIssFileParser.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testIssFileParser.getBlockCode()).isEqualTo(UPDATED_BLOCK_CODE);
        assertThat(testIssFileParser.getBlockName()).isEqualTo(UPDATED_BLOCK_NAME);
        assertThat(testIssFileParser.getVillageCode()).isEqualTo(UPDATED_VILLAGE_CODE);
        assertThat(testIssFileParser.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testIssFileParser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testIssFileParser.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testIssFileParser.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testIssFileParser.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testIssFileParser.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
        assertThat(testIssFileParser.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testIssFileParser.getAccountHolderType()).isEqualTo(UPDATED_ACCOUNT_HOLDER_TYPE);
        assertThat(testIssFileParser.getPrimaryOccupation()).isEqualTo(UPDATED_PRIMARY_OCCUPATION);
        assertThat(testIssFileParser.getLoanSactionDate()).isEqualTo(UPDATED_LOAN_SACTION_DATE);
        assertThat(testIssFileParser.getLoanSanctionAmount()).isEqualTo(UPDATED_LOAN_SANCTION_AMOUNT);
        assertThat(testIssFileParser.getTenureOFLoan()).isEqualTo(UPDATED_TENURE_OF_LOAN);
        assertThat(testIssFileParser.getDateOfOverDuePayment()).isEqualTo(UPDATED_DATE_OF_OVER_DUE_PAYMENT);
        assertThat(testIssFileParser.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testIssFileParser.getSurveyNo()).isEqualTo(UPDATED_SURVEY_NO);
        assertThat(testIssFileParser.getSatBaraSubsurveyNo()).isEqualTo(UPDATED_SAT_BARA_SUBSURVEY_NO);
        assertThat(testIssFileParser.getSeasonName()).isEqualTo(UPDATED_SEASON_NAME);
        assertThat(testIssFileParser.getAreaHect()).isEqualTo(UPDATED_AREA_HECT);
        assertThat(testIssFileParser.getLandType()).isEqualTo(UPDATED_LAND_TYPE);
        assertThat(testIssFileParser.getDisbursementDate()).isEqualTo(UPDATED_DISBURSEMENT_DATE);
        assertThat(testIssFileParser.getDisburseAmount()).isEqualTo(UPDATED_DISBURSE_AMOUNT);
        assertThat(testIssFileParser.getMaturityLoanDate()).isEqualTo(UPDATED_MATURITY_LOAN_DATE);
        assertThat(testIssFileParser.getRecoveryAmountPrinciple()).isEqualTo(UPDATED_RECOVERY_AMOUNT_PRINCIPLE);
        assertThat(testIssFileParser.getRecoveryAmountInterest()).isEqualTo(UPDATED_RECOVERY_AMOUNT_INTEREST);
        assertThat(testIssFileParser.getRecoveryDate()).isEqualTo(UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void putNonExistingIssFileParser() throws Exception {
        int databaseSizeBeforeUpdate = issFileParserRepository.findAll().size();
        issFileParser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssFileParserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, issFileParser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issFileParser))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIssFileParser() throws Exception {
        int databaseSizeBeforeUpdate = issFileParserRepository.findAll().size();
        issFileParser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssFileParserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issFileParser))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIssFileParser() throws Exception {
        int databaseSizeBeforeUpdate = issFileParserRepository.findAll().size();
        issFileParser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssFileParserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issFileParser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIssFileParserWithPatch() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        int databaseSizeBeforeUpdate = issFileParserRepository.findAll().size();

        // Update the issFileParser using partial update
        IssFileParser partialUpdatedIssFileParser = new IssFileParser();
        partialUpdatedIssFileParser.setId(issFileParser.getId());

        partialUpdatedIssFileParser
            .bankName(UPDATED_BANK_NAME)
            .bankCode(UPDATED_BANK_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchCode(UPDATED_BRANCH_CODE)
            .schemeWiseBranchCode(UPDATED_SCHEME_WISE_BRANCH_CODE)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .dateofBirth(UPDATED_DATEOF_BIRTH)
            .ageAtTimeOfSanction(UPDATED_AGE_AT_TIME_OF_SANCTION)
            .farmersCategory(UPDATED_FARMERS_CATEGORY)
            .farmerType(UPDATED_FARMER_TYPE)
            .socialCategory(UPDATED_SOCIAL_CATEGORY)
            .stateCode(UPDATED_STATE_CODE)
            .villageCode(UPDATED_VILLAGE_CODE)
            .address(UPDATED_ADDRESS)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .pacsName(UPDATED_PACS_NAME)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .accountHolderType(UPDATED_ACCOUNT_HOLDER_TYPE)
            .loanSanctionAmount(UPDATED_LOAN_SANCTION_AMOUNT)
            .dateOfOverDuePayment(UPDATED_DATE_OF_OVER_DUE_PAYMENT)
            .cropName(UPDATED_CROP_NAME)
            .surveyNo(UPDATED_SURVEY_NO)
            .satBaraSubsurveyNo(UPDATED_SAT_BARA_SUBSURVEY_NO)
            .seasonName(UPDATED_SEASON_NAME)
            .areaHect(UPDATED_AREA_HECT)
            .recoveryAmountInterest(UPDATED_RECOVERY_AMOUNT_INTEREST);

        restIssFileParserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssFileParser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssFileParser))
            )
            .andExpect(status().isOk());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeUpdate);
        IssFileParser testIssFileParser = issFileParserList.get(issFileParserList.size() - 1);
        assertThat(testIssFileParser.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testIssFileParser.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testIssFileParser.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
        assertThat(testIssFileParser.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testIssFileParser.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testIssFileParser.getSchemeWiseBranchCode()).isEqualTo(UPDATED_SCHEME_WISE_BRANCH_CODE);
        assertThat(testIssFileParser.getIfsc()).isEqualTo(DEFAULT_IFSC);
        assertThat(testIssFileParser.getLoanAccountNumberkcc()).isEqualTo(DEFAULT_LOAN_ACCOUNT_NUMBERKCC);
        assertThat(testIssFileParser.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testIssFileParser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIssFileParser.getAadharNumber()).isEqualTo(UPDATED_AADHAR_NUMBER);
        assertThat(testIssFileParser.getDateofBirth()).isEqualTo(UPDATED_DATEOF_BIRTH);
        assertThat(testIssFileParser.getAgeAtTimeOfSanction()).isEqualTo(UPDATED_AGE_AT_TIME_OF_SANCTION);
        assertThat(testIssFileParser.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testIssFileParser.getFarmersCategory()).isEqualTo(UPDATED_FARMERS_CATEGORY);
        assertThat(testIssFileParser.getFarmerType()).isEqualTo(UPDATED_FARMER_TYPE);
        assertThat(testIssFileParser.getSocialCategory()).isEqualTo(UPDATED_SOCIAL_CATEGORY);
        assertThat(testIssFileParser.getRelativeType()).isEqualTo(DEFAULT_RELATIVE_TYPE);
        assertThat(testIssFileParser.getRelativeName()).isEqualTo(DEFAULT_RELATIVE_NAME);
        assertThat(testIssFileParser.getStateName()).isEqualTo(DEFAULT_STATE_NAME);
        assertThat(testIssFileParser.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testIssFileParser.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);
        assertThat(testIssFileParser.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
        assertThat(testIssFileParser.getBlockCode()).isEqualTo(DEFAULT_BLOCK_CODE);
        assertThat(testIssFileParser.getBlockName()).isEqualTo(DEFAULT_BLOCK_NAME);
        assertThat(testIssFileParser.getVillageCode()).isEqualTo(UPDATED_VILLAGE_CODE);
        assertThat(testIssFileParser.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
        assertThat(testIssFileParser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testIssFileParser.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testIssFileParser.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testIssFileParser.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testIssFileParser.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
        assertThat(testIssFileParser.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testIssFileParser.getAccountHolderType()).isEqualTo(UPDATED_ACCOUNT_HOLDER_TYPE);
        assertThat(testIssFileParser.getPrimaryOccupation()).isEqualTo(DEFAULT_PRIMARY_OCCUPATION);
        assertThat(testIssFileParser.getLoanSactionDate()).isEqualTo(DEFAULT_LOAN_SACTION_DATE);
        assertThat(testIssFileParser.getLoanSanctionAmount()).isEqualTo(UPDATED_LOAN_SANCTION_AMOUNT);
        assertThat(testIssFileParser.getTenureOFLoan()).isEqualTo(DEFAULT_TENURE_OF_LOAN);
        assertThat(testIssFileParser.getDateOfOverDuePayment()).isEqualTo(UPDATED_DATE_OF_OVER_DUE_PAYMENT);
        assertThat(testIssFileParser.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testIssFileParser.getSurveyNo()).isEqualTo(UPDATED_SURVEY_NO);
        assertThat(testIssFileParser.getSatBaraSubsurveyNo()).isEqualTo(UPDATED_SAT_BARA_SUBSURVEY_NO);
        assertThat(testIssFileParser.getSeasonName()).isEqualTo(UPDATED_SEASON_NAME);
        assertThat(testIssFileParser.getAreaHect()).isEqualTo(UPDATED_AREA_HECT);
        assertThat(testIssFileParser.getLandType()).isEqualTo(DEFAULT_LAND_TYPE);
        assertThat(testIssFileParser.getDisbursementDate()).isEqualTo(DEFAULT_DISBURSEMENT_DATE);
        assertThat(testIssFileParser.getDisburseAmount()).isEqualTo(DEFAULT_DISBURSE_AMOUNT);
        assertThat(testIssFileParser.getMaturityLoanDate()).isEqualTo(DEFAULT_MATURITY_LOAN_DATE);
        assertThat(testIssFileParser.getRecoveryAmountPrinciple()).isEqualTo(DEFAULT_RECOVERY_AMOUNT_PRINCIPLE);
        assertThat(testIssFileParser.getRecoveryAmountInterest()).isEqualTo(UPDATED_RECOVERY_AMOUNT_INTEREST);
        assertThat(testIssFileParser.getRecoveryDate()).isEqualTo(DEFAULT_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void fullUpdateIssFileParserWithPatch() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        int databaseSizeBeforeUpdate = issFileParserRepository.findAll().size();

        // Update the issFileParser using partial update
        IssFileParser partialUpdatedIssFileParser = new IssFileParser();
        partialUpdatedIssFileParser.setId(issFileParser.getId());

        partialUpdatedIssFileParser
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .bankName(UPDATED_BANK_NAME)
            .bankCode(UPDATED_BANK_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchCode(UPDATED_BRANCH_CODE)
            .schemeWiseBranchCode(UPDATED_SCHEME_WISE_BRANCH_CODE)
            .ifsc(UPDATED_IFSC)
            .loanAccountNumberkcc(UPDATED_LOAN_ACCOUNT_NUMBERKCC)
            .farmerName(UPDATED_FARMER_NAME)
            .gender(UPDATED_GENDER)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .dateofBirth(UPDATED_DATEOF_BIRTH)
            .ageAtTimeOfSanction(UPDATED_AGE_AT_TIME_OF_SANCTION)
            .mobileNo(UPDATED_MOBILE_NO)
            .farmersCategory(UPDATED_FARMERS_CATEGORY)
            .farmerType(UPDATED_FARMER_TYPE)
            .socialCategory(UPDATED_SOCIAL_CATEGORY)
            .relativeType(UPDATED_RELATIVE_TYPE)
            .relativeName(UPDATED_RELATIVE_NAME)
            .stateName(UPDATED_STATE_NAME)
            .stateCode(UPDATED_STATE_CODE)
            .districtName(UPDATED_DISTRICT_NAME)
            .districtCode(UPDATED_DISTRICT_CODE)
            .blockCode(UPDATED_BLOCK_CODE)
            .blockName(UPDATED_BLOCK_NAME)
            .villageCode(UPDATED_VILLAGE_CODE)
            .villageName(UPDATED_VILLAGE_NAME)
            .address(UPDATED_ADDRESS)
            .pinCode(UPDATED_PIN_CODE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .pacsName(UPDATED_PACS_NAME)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .accountHolderType(UPDATED_ACCOUNT_HOLDER_TYPE)
            .primaryOccupation(UPDATED_PRIMARY_OCCUPATION)
            .loanSactionDate(UPDATED_LOAN_SACTION_DATE)
            .loanSanctionAmount(UPDATED_LOAN_SANCTION_AMOUNT)
            .tenureOFLoan(UPDATED_TENURE_OF_LOAN)
            .dateOfOverDuePayment(UPDATED_DATE_OF_OVER_DUE_PAYMENT)
            .cropName(UPDATED_CROP_NAME)
            .surveyNo(UPDATED_SURVEY_NO)
            .satBaraSubsurveyNo(UPDATED_SAT_BARA_SUBSURVEY_NO)
            .seasonName(UPDATED_SEASON_NAME)
            .areaHect(UPDATED_AREA_HECT)
            .landType(UPDATED_LAND_TYPE)
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .disburseAmount(UPDATED_DISBURSE_AMOUNT)
            .maturityLoanDate(UPDATED_MATURITY_LOAN_DATE)
            .recoveryAmountPrinciple(UPDATED_RECOVERY_AMOUNT_PRINCIPLE)
            .recoveryAmountInterest(UPDATED_RECOVERY_AMOUNT_INTEREST)
            .recoveryDate(UPDATED_RECOVERY_DATE);

        restIssFileParserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssFileParser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssFileParser))
            )
            .andExpect(status().isOk());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeUpdate);
        IssFileParser testIssFileParser = issFileParserList.get(issFileParserList.size() - 1);
        assertThat(testIssFileParser.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testIssFileParser.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testIssFileParser.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
        assertThat(testIssFileParser.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testIssFileParser.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testIssFileParser.getSchemeWiseBranchCode()).isEqualTo(UPDATED_SCHEME_WISE_BRANCH_CODE);
        assertThat(testIssFileParser.getIfsc()).isEqualTo(UPDATED_IFSC);
        assertThat(testIssFileParser.getLoanAccountNumberkcc()).isEqualTo(UPDATED_LOAN_ACCOUNT_NUMBERKCC);
        assertThat(testIssFileParser.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testIssFileParser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIssFileParser.getAadharNumber()).isEqualTo(UPDATED_AADHAR_NUMBER);
        assertThat(testIssFileParser.getDateofBirth()).isEqualTo(UPDATED_DATEOF_BIRTH);
        assertThat(testIssFileParser.getAgeAtTimeOfSanction()).isEqualTo(UPDATED_AGE_AT_TIME_OF_SANCTION);
        assertThat(testIssFileParser.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testIssFileParser.getFarmersCategory()).isEqualTo(UPDATED_FARMERS_CATEGORY);
        assertThat(testIssFileParser.getFarmerType()).isEqualTo(UPDATED_FARMER_TYPE);
        assertThat(testIssFileParser.getSocialCategory()).isEqualTo(UPDATED_SOCIAL_CATEGORY);
        assertThat(testIssFileParser.getRelativeType()).isEqualTo(UPDATED_RELATIVE_TYPE);
        assertThat(testIssFileParser.getRelativeName()).isEqualTo(UPDATED_RELATIVE_NAME);
        assertThat(testIssFileParser.getStateName()).isEqualTo(UPDATED_STATE_NAME);
        assertThat(testIssFileParser.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testIssFileParser.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testIssFileParser.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testIssFileParser.getBlockCode()).isEqualTo(UPDATED_BLOCK_CODE);
        assertThat(testIssFileParser.getBlockName()).isEqualTo(UPDATED_BLOCK_NAME);
        assertThat(testIssFileParser.getVillageCode()).isEqualTo(UPDATED_VILLAGE_CODE);
        assertThat(testIssFileParser.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testIssFileParser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testIssFileParser.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testIssFileParser.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testIssFileParser.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testIssFileParser.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
        assertThat(testIssFileParser.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testIssFileParser.getAccountHolderType()).isEqualTo(UPDATED_ACCOUNT_HOLDER_TYPE);
        assertThat(testIssFileParser.getPrimaryOccupation()).isEqualTo(UPDATED_PRIMARY_OCCUPATION);
        assertThat(testIssFileParser.getLoanSactionDate()).isEqualTo(UPDATED_LOAN_SACTION_DATE);
        assertThat(testIssFileParser.getLoanSanctionAmount()).isEqualTo(UPDATED_LOAN_SANCTION_AMOUNT);
        assertThat(testIssFileParser.getTenureOFLoan()).isEqualTo(UPDATED_TENURE_OF_LOAN);
        assertThat(testIssFileParser.getDateOfOverDuePayment()).isEqualTo(UPDATED_DATE_OF_OVER_DUE_PAYMENT);
        assertThat(testIssFileParser.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testIssFileParser.getSurveyNo()).isEqualTo(UPDATED_SURVEY_NO);
        assertThat(testIssFileParser.getSatBaraSubsurveyNo()).isEqualTo(UPDATED_SAT_BARA_SUBSURVEY_NO);
        assertThat(testIssFileParser.getSeasonName()).isEqualTo(UPDATED_SEASON_NAME);
        assertThat(testIssFileParser.getAreaHect()).isEqualTo(UPDATED_AREA_HECT);
        assertThat(testIssFileParser.getLandType()).isEqualTo(UPDATED_LAND_TYPE);
        assertThat(testIssFileParser.getDisbursementDate()).isEqualTo(UPDATED_DISBURSEMENT_DATE);
        assertThat(testIssFileParser.getDisburseAmount()).isEqualTo(UPDATED_DISBURSE_AMOUNT);
        assertThat(testIssFileParser.getMaturityLoanDate()).isEqualTo(UPDATED_MATURITY_LOAN_DATE);
        assertThat(testIssFileParser.getRecoveryAmountPrinciple()).isEqualTo(UPDATED_RECOVERY_AMOUNT_PRINCIPLE);
        assertThat(testIssFileParser.getRecoveryAmountInterest()).isEqualTo(UPDATED_RECOVERY_AMOUNT_INTEREST);
        assertThat(testIssFileParser.getRecoveryDate()).isEqualTo(UPDATED_RECOVERY_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingIssFileParser() throws Exception {
        int databaseSizeBeforeUpdate = issFileParserRepository.findAll().size();
        issFileParser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssFileParserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, issFileParser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issFileParser))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIssFileParser() throws Exception {
        int databaseSizeBeforeUpdate = issFileParserRepository.findAll().size();
        issFileParser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssFileParserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issFileParser))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIssFileParser() throws Exception {
        int databaseSizeBeforeUpdate = issFileParserRepository.findAll().size();
        issFileParser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssFileParserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(issFileParser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IssFileParser in the database
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIssFileParser() throws Exception {
        // Initialize the database
        issFileParserRepository.saveAndFlush(issFileParser);

        int databaseSizeBeforeDelete = issFileParserRepository.findAll().size();

        // Delete the issFileParser
        restIssFileParserMockMvc
            .perform(delete(ENTITY_API_URL_ID, issFileParser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssFileParser> issFileParserList = issFileParserRepository.findAll();
        assertThat(issFileParserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
