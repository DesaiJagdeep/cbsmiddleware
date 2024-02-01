package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.domain.KamalCrop;
import com.cbs.middleware.domain.KamalSociety;
import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.repository.KamalCropRepository;
import com.cbs.middleware.service.criteria.KamalCropCriteria;
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
 * Integration tests for the {@link KamalCropResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KamalCropResourceIT {

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_COUNT = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_COUNT = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_PACS_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_HEAD_OFFICE_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_OFFICE_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_ELIGIBILITY_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_CROP_ELIGIBILITY_AMOUNT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kamal-crops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KamalCropRepository kamalCropRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKamalCropMockMvc;

    private KamalCrop kamalCrop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalCrop createEntity(EntityManager em) {
        KamalCrop kamalCrop = new KamalCrop()
            .pacsNumber(DEFAULT_PACS_NUMBER)
            .financialYear(DEFAULT_FINANCIAL_YEAR)
            .memberCount(DEFAULT_MEMBER_COUNT)
            .area(DEFAULT_AREA)
            .pacsAmount(DEFAULT_PACS_AMOUNT)
            .branchAmount(DEFAULT_BRANCH_AMOUNT)
            .headOfficeAmount(DEFAULT_HEAD_OFFICE_AMOUNT)
            .cropEligibilityAmount(DEFAULT_CROP_ELIGIBILITY_AMOUNT);
        return kamalCrop;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalCrop createUpdatedEntity(EntityManager em) {
        KamalCrop kamalCrop = new KamalCrop()
            .pacsNumber(UPDATED_PACS_NUMBER)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .memberCount(UPDATED_MEMBER_COUNT)
            .area(UPDATED_AREA)
            .pacsAmount(UPDATED_PACS_AMOUNT)
            .branchAmount(UPDATED_BRANCH_AMOUNT)
            .headOfficeAmount(UPDATED_HEAD_OFFICE_AMOUNT)
            .cropEligibilityAmount(UPDATED_CROP_ELIGIBILITY_AMOUNT);
        return kamalCrop;
    }

    @BeforeEach
    public void initTest() {
        kamalCrop = createEntity(em);
    }

    @Test
    @Transactional
    void createKamalCrop() throws Exception {
        int databaseSizeBeforeCreate = kamalCropRepository.findAll().size();
        // Create the KamalCrop
        restKamalCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalCrop)))
            .andExpect(status().isCreated());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeCreate + 1);
        KamalCrop testKamalCrop = kamalCropList.get(kamalCropList.size() - 1);
        assertThat(testKamalCrop.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKamalCrop.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testKamalCrop.getMemberCount()).isEqualTo(DEFAULT_MEMBER_COUNT);
        assertThat(testKamalCrop.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testKamalCrop.getPacsAmount()).isEqualTo(DEFAULT_PACS_AMOUNT);
        assertThat(testKamalCrop.getBranchAmount()).isEqualTo(DEFAULT_BRANCH_AMOUNT);
        assertThat(testKamalCrop.getHeadOfficeAmount()).isEqualTo(DEFAULT_HEAD_OFFICE_AMOUNT);
        assertThat(testKamalCrop.getCropEligibilityAmount()).isEqualTo(DEFAULT_CROP_ELIGIBILITY_AMOUNT);
    }

    @Test
    @Transactional
    void createKamalCropWithExistingId() throws Exception {
        // Create the KamalCrop with an existing ID
        kamalCrop.setId(1L);

        int databaseSizeBeforeCreate = kamalCropRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKamalCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalCrop)))
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKamalCrops() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalCrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].memberCount").value(hasItem(DEFAULT_MEMBER_COUNT)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].pacsAmount").value(hasItem(DEFAULT_PACS_AMOUNT)))
            .andExpect(jsonPath("$.[*].branchAmount").value(hasItem(DEFAULT_BRANCH_AMOUNT)))
            .andExpect(jsonPath("$.[*].headOfficeAmount").value(hasItem(DEFAULT_HEAD_OFFICE_AMOUNT)))
            .andExpect(jsonPath("$.[*].cropEligibilityAmount").value(hasItem(DEFAULT_CROP_ELIGIBILITY_AMOUNT)));
    }

    @Test
    @Transactional
    void getKamalCrop() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get the kamalCrop
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL_ID, kamalCrop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kamalCrop.getId().intValue()))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER))
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.memberCount").value(DEFAULT_MEMBER_COUNT))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.pacsAmount").value(DEFAULT_PACS_AMOUNT))
            .andExpect(jsonPath("$.branchAmount").value(DEFAULT_BRANCH_AMOUNT))
            .andExpect(jsonPath("$.headOfficeAmount").value(DEFAULT_HEAD_OFFICE_AMOUNT))
            .andExpect(jsonPath("$.cropEligibilityAmount").value(DEFAULT_CROP_ELIGIBILITY_AMOUNT));
    }

    @Test
    @Transactional
    void getKamalCropsByIdFiltering() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        Long id = kamalCrop.getId();

        defaultKamalCropShouldBeFound("id.equals=" + id);
        defaultKamalCropShouldNotBeFound("id.notEquals=" + id);

        defaultKamalCropShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKamalCropShouldNotBeFound("id.greaterThan=" + id);

        defaultKamalCropShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKamalCropShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber equals to DEFAULT_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.equals=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.equals=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber in DEFAULT_PACS_NUMBER or UPDATED_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.in=" + DEFAULT_PACS_NUMBER + "," + UPDATED_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.in=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber is not null
        defaultKamalCropShouldBeFound("pacsNumber.specified=true");

        // Get all the kamalCropList where pacsNumber is null
        defaultKamalCropShouldNotBeFound("pacsNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber contains DEFAULT_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.contains=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber contains UPDATED_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.contains=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber does not contain DEFAULT_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.doesNotContain=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber does not contain UPDATED_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.doesNotContain=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultKamalCropShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalCropList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the kamalCropList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear is not null
        defaultKamalCropShouldBeFound("financialYear.specified=true");

        // Get all the kamalCropList where financialYear is null
        defaultKamalCropShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultKamalCropShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalCropList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultKamalCropShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalCropList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemberCountIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memberCount equals to DEFAULT_MEMBER_COUNT
        defaultKamalCropShouldBeFound("memberCount.equals=" + DEFAULT_MEMBER_COUNT);

        // Get all the kamalCropList where memberCount equals to UPDATED_MEMBER_COUNT
        defaultKamalCropShouldNotBeFound("memberCount.equals=" + UPDATED_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemberCountIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memberCount in DEFAULT_MEMBER_COUNT or UPDATED_MEMBER_COUNT
        defaultKamalCropShouldBeFound("memberCount.in=" + DEFAULT_MEMBER_COUNT + "," + UPDATED_MEMBER_COUNT);

        // Get all the kamalCropList where memberCount equals to UPDATED_MEMBER_COUNT
        defaultKamalCropShouldNotBeFound("memberCount.in=" + UPDATED_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemberCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memberCount is not null
        defaultKamalCropShouldBeFound("memberCount.specified=true");

        // Get all the kamalCropList where memberCount is null
        defaultKamalCropShouldNotBeFound("memberCount.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemberCountContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memberCount contains DEFAULT_MEMBER_COUNT
        defaultKamalCropShouldBeFound("memberCount.contains=" + DEFAULT_MEMBER_COUNT);

        // Get all the kamalCropList where memberCount contains UPDATED_MEMBER_COUNT
        defaultKamalCropShouldNotBeFound("memberCount.contains=" + UPDATED_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemberCountNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memberCount does not contain DEFAULT_MEMBER_COUNT
        defaultKamalCropShouldNotBeFound("memberCount.doesNotContain=" + DEFAULT_MEMBER_COUNT);

        // Get all the kamalCropList where memberCount does not contain UPDATED_MEMBER_COUNT
        defaultKamalCropShouldBeFound("memberCount.doesNotContain=" + UPDATED_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where area equals to DEFAULT_AREA
        defaultKamalCropShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the kamalCropList where area equals to UPDATED_AREA
        defaultKamalCropShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllKamalCropsByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where area in DEFAULT_AREA or UPDATED_AREA
        defaultKamalCropShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the kamalCropList where area equals to UPDATED_AREA
        defaultKamalCropShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllKamalCropsByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where area is not null
        defaultKamalCropShouldBeFound("area.specified=true");

        // Get all the kamalCropList where area is null
        defaultKamalCropShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByAreaContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where area contains DEFAULT_AREA
        defaultKamalCropShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the kamalCropList where area contains UPDATED_AREA
        defaultKamalCropShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllKamalCropsByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where area does not contain DEFAULT_AREA
        defaultKamalCropShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the kamalCropList where area does not contain UPDATED_AREA
        defaultKamalCropShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsAmount equals to DEFAULT_PACS_AMOUNT
        defaultKamalCropShouldBeFound("pacsAmount.equals=" + DEFAULT_PACS_AMOUNT);

        // Get all the kamalCropList where pacsAmount equals to UPDATED_PACS_AMOUNT
        defaultKamalCropShouldNotBeFound("pacsAmount.equals=" + UPDATED_PACS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsAmount in DEFAULT_PACS_AMOUNT or UPDATED_PACS_AMOUNT
        defaultKamalCropShouldBeFound("pacsAmount.in=" + DEFAULT_PACS_AMOUNT + "," + UPDATED_PACS_AMOUNT);

        // Get all the kamalCropList where pacsAmount equals to UPDATED_PACS_AMOUNT
        defaultKamalCropShouldNotBeFound("pacsAmount.in=" + UPDATED_PACS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsAmount is not null
        defaultKamalCropShouldBeFound("pacsAmount.specified=true");

        // Get all the kamalCropList where pacsAmount is null
        defaultKamalCropShouldNotBeFound("pacsAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsAmountContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsAmount contains DEFAULT_PACS_AMOUNT
        defaultKamalCropShouldBeFound("pacsAmount.contains=" + DEFAULT_PACS_AMOUNT);

        // Get all the kamalCropList where pacsAmount contains UPDATED_PACS_AMOUNT
        defaultKamalCropShouldNotBeFound("pacsAmount.contains=" + UPDATED_PACS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsAmountNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsAmount does not contain DEFAULT_PACS_AMOUNT
        defaultKamalCropShouldNotBeFound("pacsAmount.doesNotContain=" + DEFAULT_PACS_AMOUNT);

        // Get all the kamalCropList where pacsAmount does not contain UPDATED_PACS_AMOUNT
        defaultKamalCropShouldBeFound("pacsAmount.doesNotContain=" + UPDATED_PACS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByBranchAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where branchAmount equals to DEFAULT_BRANCH_AMOUNT
        defaultKamalCropShouldBeFound("branchAmount.equals=" + DEFAULT_BRANCH_AMOUNT);

        // Get all the kamalCropList where branchAmount equals to UPDATED_BRANCH_AMOUNT
        defaultKamalCropShouldNotBeFound("branchAmount.equals=" + UPDATED_BRANCH_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByBranchAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where branchAmount in DEFAULT_BRANCH_AMOUNT or UPDATED_BRANCH_AMOUNT
        defaultKamalCropShouldBeFound("branchAmount.in=" + DEFAULT_BRANCH_AMOUNT + "," + UPDATED_BRANCH_AMOUNT);

        // Get all the kamalCropList where branchAmount equals to UPDATED_BRANCH_AMOUNT
        defaultKamalCropShouldNotBeFound("branchAmount.in=" + UPDATED_BRANCH_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByBranchAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where branchAmount is not null
        defaultKamalCropShouldBeFound("branchAmount.specified=true");

        // Get all the kamalCropList where branchAmount is null
        defaultKamalCropShouldNotBeFound("branchAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByBranchAmountContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where branchAmount contains DEFAULT_BRANCH_AMOUNT
        defaultKamalCropShouldBeFound("branchAmount.contains=" + DEFAULT_BRANCH_AMOUNT);

        // Get all the kamalCropList where branchAmount contains UPDATED_BRANCH_AMOUNT
        defaultKamalCropShouldNotBeFound("branchAmount.contains=" + UPDATED_BRANCH_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByBranchAmountNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where branchAmount does not contain DEFAULT_BRANCH_AMOUNT
        defaultKamalCropShouldNotBeFound("branchAmount.doesNotContain=" + DEFAULT_BRANCH_AMOUNT);

        // Get all the kamalCropList where branchAmount does not contain UPDATED_BRANCH_AMOUNT
        defaultKamalCropShouldBeFound("branchAmount.doesNotContain=" + UPDATED_BRANCH_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByHeadOfficeAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where headOfficeAmount equals to DEFAULT_HEAD_OFFICE_AMOUNT
        defaultKamalCropShouldBeFound("headOfficeAmount.equals=" + DEFAULT_HEAD_OFFICE_AMOUNT);

        // Get all the kamalCropList where headOfficeAmount equals to UPDATED_HEAD_OFFICE_AMOUNT
        defaultKamalCropShouldNotBeFound("headOfficeAmount.equals=" + UPDATED_HEAD_OFFICE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByHeadOfficeAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where headOfficeAmount in DEFAULT_HEAD_OFFICE_AMOUNT or UPDATED_HEAD_OFFICE_AMOUNT
        defaultKamalCropShouldBeFound("headOfficeAmount.in=" + DEFAULT_HEAD_OFFICE_AMOUNT + "," + UPDATED_HEAD_OFFICE_AMOUNT);

        // Get all the kamalCropList where headOfficeAmount equals to UPDATED_HEAD_OFFICE_AMOUNT
        defaultKamalCropShouldNotBeFound("headOfficeAmount.in=" + UPDATED_HEAD_OFFICE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByHeadOfficeAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where headOfficeAmount is not null
        defaultKamalCropShouldBeFound("headOfficeAmount.specified=true");

        // Get all the kamalCropList where headOfficeAmount is null
        defaultKamalCropShouldNotBeFound("headOfficeAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByHeadOfficeAmountContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where headOfficeAmount contains DEFAULT_HEAD_OFFICE_AMOUNT
        defaultKamalCropShouldBeFound("headOfficeAmount.contains=" + DEFAULT_HEAD_OFFICE_AMOUNT);

        // Get all the kamalCropList where headOfficeAmount contains UPDATED_HEAD_OFFICE_AMOUNT
        defaultKamalCropShouldNotBeFound("headOfficeAmount.contains=" + UPDATED_HEAD_OFFICE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByHeadOfficeAmountNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where headOfficeAmount does not contain DEFAULT_HEAD_OFFICE_AMOUNT
        defaultKamalCropShouldNotBeFound("headOfficeAmount.doesNotContain=" + DEFAULT_HEAD_OFFICE_AMOUNT);

        // Get all the kamalCropList where headOfficeAmount does not contain UPDATED_HEAD_OFFICE_AMOUNT
        defaultKamalCropShouldBeFound("headOfficeAmount.doesNotContain=" + UPDATED_HEAD_OFFICE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByCropEligibilityAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where cropEligibilityAmount equals to DEFAULT_CROP_ELIGIBILITY_AMOUNT
        defaultKamalCropShouldBeFound("cropEligibilityAmount.equals=" + DEFAULT_CROP_ELIGIBILITY_AMOUNT);

        // Get all the kamalCropList where cropEligibilityAmount equals to UPDATED_CROP_ELIGIBILITY_AMOUNT
        defaultKamalCropShouldNotBeFound("cropEligibilityAmount.equals=" + UPDATED_CROP_ELIGIBILITY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByCropEligibilityAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where cropEligibilityAmount in DEFAULT_CROP_ELIGIBILITY_AMOUNT or UPDATED_CROP_ELIGIBILITY_AMOUNT
        defaultKamalCropShouldBeFound(
            "cropEligibilityAmount.in=" + DEFAULT_CROP_ELIGIBILITY_AMOUNT + "," + UPDATED_CROP_ELIGIBILITY_AMOUNT
        );

        // Get all the kamalCropList where cropEligibilityAmount equals to UPDATED_CROP_ELIGIBILITY_AMOUNT
        defaultKamalCropShouldNotBeFound("cropEligibilityAmount.in=" + UPDATED_CROP_ELIGIBILITY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByCropEligibilityAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where cropEligibilityAmount is not null
        defaultKamalCropShouldBeFound("cropEligibilityAmount.specified=true");

        // Get all the kamalCropList where cropEligibilityAmount is null
        defaultKamalCropShouldNotBeFound("cropEligibilityAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByCropEligibilityAmountContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where cropEligibilityAmount contains DEFAULT_CROP_ELIGIBILITY_AMOUNT
        defaultKamalCropShouldBeFound("cropEligibilityAmount.contains=" + DEFAULT_CROP_ELIGIBILITY_AMOUNT);

        // Get all the kamalCropList where cropEligibilityAmount contains UPDATED_CROP_ELIGIBILITY_AMOUNT
        defaultKamalCropShouldNotBeFound("cropEligibilityAmount.contains=" + UPDATED_CROP_ELIGIBILITY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByCropEligibilityAmountNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where cropEligibilityAmount does not contain DEFAULT_CROP_ELIGIBILITY_AMOUNT
        defaultKamalCropShouldNotBeFound("cropEligibilityAmount.doesNotContain=" + DEFAULT_CROP_ELIGIBILITY_AMOUNT);

        // Get all the kamalCropList where cropEligibilityAmount does not contain UPDATED_CROP_ELIGIBILITY_AMOUNT
        defaultKamalCropShouldBeFound("cropEligibilityAmount.doesNotContain=" + UPDATED_CROP_ELIGIBILITY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKamalCropsByKamalSocietyIsEqualToSomething() throws Exception {
        KamalSociety kamalSociety;
        if (TestUtil.findAll(em, KamalSociety.class).isEmpty()) {
            kamalCropRepository.saveAndFlush(kamalCrop);
            kamalSociety = KamalSocietyResourceIT.createEntity(em);
        } else {
            kamalSociety = TestUtil.findAll(em, KamalSociety.class).get(0);
        }
        em.persist(kamalSociety);
        em.flush();
        kamalCrop.setKamalSociety(kamalSociety);
        kamalCropRepository.saveAndFlush(kamalCrop);
        Long kamalSocietyId = kamalSociety.getId();

        // Get all the kamalCropList where kamalSociety equals to kamalSocietyId
        defaultKamalCropShouldBeFound("kamalSocietyId.equals=" + kamalSocietyId);

        // Get all the kamalCropList where kamalSociety equals to (kamalSocietyId + 1)
        defaultKamalCropShouldNotBeFound("kamalSocietyId.equals=" + (kamalSocietyId + 1));
    }

    @Test
    @Transactional
    void getAllKamalCropsByFarmerTypeMasterIsEqualToSomething() throws Exception {
        FarmerTypeMaster farmerTypeMaster;
        if (TestUtil.findAll(em, FarmerTypeMaster.class).isEmpty()) {
            kamalCropRepository.saveAndFlush(kamalCrop);
            farmerTypeMaster = FarmerTypeMasterResourceIT.createEntity(em);
        } else {
            farmerTypeMaster = TestUtil.findAll(em, FarmerTypeMaster.class).get(0);
        }
        em.persist(farmerTypeMaster);
        em.flush();
        kamalCrop.setFarmerTypeMaster(farmerTypeMaster);
        kamalCropRepository.saveAndFlush(kamalCrop);
        Long farmerTypeMasterId = farmerTypeMaster.getId();

        // Get all the kamalCropList where farmerTypeMaster equals to farmerTypeMasterId
        defaultKamalCropShouldBeFound("farmerTypeMasterId.equals=" + farmerTypeMasterId);

        // Get all the kamalCropList where farmerTypeMaster equals to (farmerTypeMasterId + 1)
        defaultKamalCropShouldNotBeFound("farmerTypeMasterId.equals=" + (farmerTypeMasterId + 1));
    }

    @Test
    @Transactional
    void getAllKamalCropsBySeasonMasterIsEqualToSomething() throws Exception {
        SeasonMaster seasonMaster;
        if (TestUtil.findAll(em, SeasonMaster.class).isEmpty()) {
            kamalCropRepository.saveAndFlush(kamalCrop);
            seasonMaster = SeasonMasterResourceIT.createEntity(em);
        } else {
            seasonMaster = TestUtil.findAll(em, SeasonMaster.class).get(0);
        }
        em.persist(seasonMaster);
        em.flush();
        kamalCrop.setSeasonMaster(seasonMaster);
        kamalCropRepository.saveAndFlush(kamalCrop);
        Long seasonMasterId = seasonMaster.getId();

        // Get all the kamalCropList where seasonMaster equals to seasonMasterId
        defaultKamalCropShouldBeFound("seasonMasterId.equals=" + seasonMasterId);

        // Get all the kamalCropList where seasonMaster equals to (seasonMasterId + 1)
        defaultKamalCropShouldNotBeFound("seasonMasterId.equals=" + (seasonMasterId + 1));
    }

    @Test
    @Transactional
    void getAllKamalCropsByCropMasterIsEqualToSomething() throws Exception {
        CropMaster cropMaster;
        if (TestUtil.findAll(em, CropMaster.class).isEmpty()) {
            kamalCropRepository.saveAndFlush(kamalCrop);
            cropMaster = CropMasterResourceIT.createEntity(em);
        } else {
            cropMaster = TestUtil.findAll(em, CropMaster.class).get(0);
        }
        em.persist(cropMaster);
        em.flush();
        kamalCrop.setCropMaster(cropMaster);
        kamalCropRepository.saveAndFlush(kamalCrop);
        Long cropMasterId = cropMaster.getId();

        // Get all the kamalCropList where cropMaster equals to cropMasterId
        defaultKamalCropShouldBeFound("cropMasterId.equals=" + cropMasterId);

        // Get all the kamalCropList where cropMaster equals to (cropMasterId + 1)
        defaultKamalCropShouldNotBeFound("cropMasterId.equals=" + (cropMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKamalCropShouldBeFound(String filter) throws Exception {
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalCrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].memberCount").value(hasItem(DEFAULT_MEMBER_COUNT)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].pacsAmount").value(hasItem(DEFAULT_PACS_AMOUNT)))
            .andExpect(jsonPath("$.[*].branchAmount").value(hasItem(DEFAULT_BRANCH_AMOUNT)))
            .andExpect(jsonPath("$.[*].headOfficeAmount").value(hasItem(DEFAULT_HEAD_OFFICE_AMOUNT)))
            .andExpect(jsonPath("$.[*].cropEligibilityAmount").value(hasItem(DEFAULT_CROP_ELIGIBILITY_AMOUNT)));

        // Check, that the count call also returns 1
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKamalCropShouldNotBeFound(String filter) throws Exception {
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKamalCrop() throws Exception {
        // Get the kamalCrop
        restKamalCropMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKamalCrop() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();

        // Update the kamalCrop
        KamalCrop updatedKamalCrop = kamalCropRepository.findById(kamalCrop.getId()).get();
        // Disconnect from session so that the updates on updatedKamalCrop are not directly saved in db
        em.detach(updatedKamalCrop);
        updatedKamalCrop
            .pacsNumber(UPDATED_PACS_NUMBER)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .memberCount(UPDATED_MEMBER_COUNT)
            .area(UPDATED_AREA)
            .pacsAmount(UPDATED_PACS_AMOUNT)
            .branchAmount(UPDATED_BRANCH_AMOUNT)
            .headOfficeAmount(UPDATED_HEAD_OFFICE_AMOUNT)
            .cropEligibilityAmount(UPDATED_CROP_ELIGIBILITY_AMOUNT);

        restKamalCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKamalCrop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKamalCrop))
            )
            .andExpect(status().isOk());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
        KamalCrop testKamalCrop = kamalCropList.get(kamalCropList.size() - 1);
        assertThat(testKamalCrop.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalCrop.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testKamalCrop.getMemberCount()).isEqualTo(UPDATED_MEMBER_COUNT);
        assertThat(testKamalCrop.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testKamalCrop.getPacsAmount()).isEqualTo(UPDATED_PACS_AMOUNT);
        assertThat(testKamalCrop.getBranchAmount()).isEqualTo(UPDATED_BRANCH_AMOUNT);
        assertThat(testKamalCrop.getHeadOfficeAmount()).isEqualTo(UPDATED_HEAD_OFFICE_AMOUNT);
        assertThat(testKamalCrop.getCropEligibilityAmount()).isEqualTo(UPDATED_CROP_ELIGIBILITY_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kamalCrop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalCrop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKamalCropWithPatch() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();

        // Update the kamalCrop using partial update
        KamalCrop partialUpdatedKamalCrop = new KamalCrop();
        partialUpdatedKamalCrop.setId(kamalCrop.getId());

        partialUpdatedKamalCrop
            .pacsNumber(UPDATED_PACS_NUMBER)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .area(UPDATED_AREA)
            .branchAmount(UPDATED_BRANCH_AMOUNT)
            .headOfficeAmount(UPDATED_HEAD_OFFICE_AMOUNT);

        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalCrop))
            )
            .andExpect(status().isOk());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
        KamalCrop testKamalCrop = kamalCropList.get(kamalCropList.size() - 1);
        assertThat(testKamalCrop.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalCrop.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testKamalCrop.getMemberCount()).isEqualTo(DEFAULT_MEMBER_COUNT);
        assertThat(testKamalCrop.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testKamalCrop.getPacsAmount()).isEqualTo(DEFAULT_PACS_AMOUNT);
        assertThat(testKamalCrop.getBranchAmount()).isEqualTo(UPDATED_BRANCH_AMOUNT);
        assertThat(testKamalCrop.getHeadOfficeAmount()).isEqualTo(UPDATED_HEAD_OFFICE_AMOUNT);
        assertThat(testKamalCrop.getCropEligibilityAmount()).isEqualTo(DEFAULT_CROP_ELIGIBILITY_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateKamalCropWithPatch() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();

        // Update the kamalCrop using partial update
        KamalCrop partialUpdatedKamalCrop = new KamalCrop();
        partialUpdatedKamalCrop.setId(kamalCrop.getId());

        partialUpdatedKamalCrop
            .pacsNumber(UPDATED_PACS_NUMBER)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .memberCount(UPDATED_MEMBER_COUNT)
            .area(UPDATED_AREA)
            .pacsAmount(UPDATED_PACS_AMOUNT)
            .branchAmount(UPDATED_BRANCH_AMOUNT)
            .headOfficeAmount(UPDATED_HEAD_OFFICE_AMOUNT)
            .cropEligibilityAmount(UPDATED_CROP_ELIGIBILITY_AMOUNT);

        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalCrop))
            )
            .andExpect(status().isOk());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
        KamalCrop testKamalCrop = kamalCropList.get(kamalCropList.size() - 1);
        assertThat(testKamalCrop.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalCrop.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testKamalCrop.getMemberCount()).isEqualTo(UPDATED_MEMBER_COUNT);
        assertThat(testKamalCrop.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testKamalCrop.getPacsAmount()).isEqualTo(UPDATED_PACS_AMOUNT);
        assertThat(testKamalCrop.getBranchAmount()).isEqualTo(UPDATED_BRANCH_AMOUNT);
        assertThat(testKamalCrop.getHeadOfficeAmount()).isEqualTo(UPDATED_HEAD_OFFICE_AMOUNT);
        assertThat(testKamalCrop.getCropEligibilityAmount()).isEqualTo(UPDATED_CROP_ELIGIBILITY_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kamalCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKamalCrop() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        int databaseSizeBeforeDelete = kamalCropRepository.findAll().size();

        // Delete the kamalCrop
        restKamalCropMockMvc
            .perform(delete(ENTITY_API_URL_ID, kamalCrop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
