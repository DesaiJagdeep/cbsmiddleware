package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.domain.CropRateMaster;
import com.cbs.middleware.repository.CropRateMasterRepository;
import com.cbs.middleware.service.criteria.CropRateMasterCriteria;
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
 * Integration tests for the {@link CropRateMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CropRateMasterResourceIT {

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final Double DEFAULT_CURRENT_AMOUNT = 1D;
    private static final Double UPDATED_CURRENT_AMOUNT = 2D;
    private static final Double SMALLER_CURRENT_AMOUNT = 1D - 1D;

    private static final String DEFAULT_CURRENT_AMOUNT_MR = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_AMOUNT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENTAGE = 1D;
    private static final Double UPDATED_PERCENTAGE = 2D;
    private static final Double SMALLER_PERCENTAGE = 1D - 1D;

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

    private static final String ENTITY_API_URL = "/api/crop-rate-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CropRateMasterRepository cropRateMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCropRateMasterMockMvc;

    private CropRateMaster cropRateMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CropRateMaster createEntity(EntityManager em) {
        CropRateMaster cropRateMaster = new CropRateMaster()
            .financialYear(DEFAULT_FINANCIAL_YEAR)
            .currentAmount(DEFAULT_CURRENT_AMOUNT)
            .currentAmountMr(DEFAULT_CURRENT_AMOUNT_MR)
            .percentage(DEFAULT_PERCENTAGE)
            .activeFlag(DEFAULT_ACTIVE_FLAG);
        return cropRateMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CropRateMaster createUpdatedEntity(EntityManager em) {
        CropRateMaster cropRateMaster = new CropRateMaster()
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .currentAmount(UPDATED_CURRENT_AMOUNT)
            .currentAmountMr(UPDATED_CURRENT_AMOUNT_MR)
            .percentage(UPDATED_PERCENTAGE)
            .activeFlag(UPDATED_ACTIVE_FLAG);
        return cropRateMaster;
    }

    @BeforeEach
    public void initTest() {
        cropRateMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createCropRateMaster() throws Exception {
        int databaseSizeBeforeCreate = cropRateMasterRepository.findAll().size();
        // Create the CropRateMaster
        restCropRateMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropRateMaster))
            )
            .andExpect(status().isCreated());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CropRateMaster testCropRateMaster = cropRateMasterList.get(cropRateMasterList.size() - 1);
        assertThat(testCropRateMaster.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testCropRateMaster.getCurrentAmount()).isEqualTo(DEFAULT_CURRENT_AMOUNT);
        assertThat(testCropRateMaster.getCurrentAmountMr()).isEqualTo(DEFAULT_CURRENT_AMOUNT_MR);
        assertThat(testCropRateMaster.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testCropRateMaster.getActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    void createCropRateMasterWithExistingId() throws Exception {
        // Create the CropRateMaster with an existing ID
        cropRateMaster.setId(1L);

        int databaseSizeBeforeCreate = cropRateMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCropRateMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropRateMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFinancialYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = cropRateMasterRepository.findAll().size();
        // set the field null
        cropRateMaster.setFinancialYear(null);

        // Create the CropRateMaster, which fails.

        restCropRateMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropRateMaster))
            )
            .andExpect(status().isBadRequest());

        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = cropRateMasterRepository.findAll().size();
        // set the field null
        cropRateMaster.setCurrentAmount(null);

        // Create the CropRateMaster, which fails.

        restCropRateMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropRateMaster))
            )
            .andExpect(status().isBadRequest());

        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCropRateMasters() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList
        restCropRateMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cropRateMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].currentAmount").value(hasItem(DEFAULT_CURRENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currentAmountMr").value(hasItem(DEFAULT_CURRENT_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())));
    }

    @Test
    @Transactional
    void getCropRateMaster() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get the cropRateMaster
        restCropRateMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, cropRateMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cropRateMaster.getId().intValue()))
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.currentAmount").value(DEFAULT_CURRENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currentAmountMr").value(DEFAULT_CURRENT_AMOUNT_MR))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()));
    }

    @Test
    @Transactional
    void getCropRateMastersByIdFiltering() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        Long id = cropRateMaster.getId();

        defaultCropRateMasterShouldBeFound("id.equals=" + id);
        defaultCropRateMasterShouldNotBeFound("id.notEquals=" + id);

        defaultCropRateMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCropRateMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultCropRateMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCropRateMasterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultCropRateMasterShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the cropRateMasterList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultCropRateMasterShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultCropRateMasterShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the cropRateMasterList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultCropRateMasterShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where financialYear is not null
        defaultCropRateMasterShouldBeFound("financialYear.specified=true");

        // Get all the cropRateMasterList where financialYear is null
        defaultCropRateMasterShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllCropRateMastersByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultCropRateMasterShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the cropRateMasterList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultCropRateMasterShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultCropRateMasterShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the cropRateMasterList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultCropRateMasterShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmount equals to DEFAULT_CURRENT_AMOUNT
        defaultCropRateMasterShouldBeFound("currentAmount.equals=" + DEFAULT_CURRENT_AMOUNT);

        // Get all the cropRateMasterList where currentAmount equals to UPDATED_CURRENT_AMOUNT
        defaultCropRateMasterShouldNotBeFound("currentAmount.equals=" + UPDATED_CURRENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmount in DEFAULT_CURRENT_AMOUNT or UPDATED_CURRENT_AMOUNT
        defaultCropRateMasterShouldBeFound("currentAmount.in=" + DEFAULT_CURRENT_AMOUNT + "," + UPDATED_CURRENT_AMOUNT);

        // Get all the cropRateMasterList where currentAmount equals to UPDATED_CURRENT_AMOUNT
        defaultCropRateMasterShouldNotBeFound("currentAmount.in=" + UPDATED_CURRENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmount is not null
        defaultCropRateMasterShouldBeFound("currentAmount.specified=true");

        // Get all the cropRateMasterList where currentAmount is null
        defaultCropRateMasterShouldNotBeFound("currentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmount is greater than or equal to DEFAULT_CURRENT_AMOUNT
        defaultCropRateMasterShouldBeFound("currentAmount.greaterThanOrEqual=" + DEFAULT_CURRENT_AMOUNT);

        // Get all the cropRateMasterList where currentAmount is greater than or equal to UPDATED_CURRENT_AMOUNT
        defaultCropRateMasterShouldNotBeFound("currentAmount.greaterThanOrEqual=" + UPDATED_CURRENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmount is less than or equal to DEFAULT_CURRENT_AMOUNT
        defaultCropRateMasterShouldBeFound("currentAmount.lessThanOrEqual=" + DEFAULT_CURRENT_AMOUNT);

        // Get all the cropRateMasterList where currentAmount is less than or equal to SMALLER_CURRENT_AMOUNT
        defaultCropRateMasterShouldNotBeFound("currentAmount.lessThanOrEqual=" + SMALLER_CURRENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmount is less than DEFAULT_CURRENT_AMOUNT
        defaultCropRateMasterShouldNotBeFound("currentAmount.lessThan=" + DEFAULT_CURRENT_AMOUNT);

        // Get all the cropRateMasterList where currentAmount is less than UPDATED_CURRENT_AMOUNT
        defaultCropRateMasterShouldBeFound("currentAmount.lessThan=" + UPDATED_CURRENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmount is greater than DEFAULT_CURRENT_AMOUNT
        defaultCropRateMasterShouldNotBeFound("currentAmount.greaterThan=" + DEFAULT_CURRENT_AMOUNT);

        // Get all the cropRateMasterList where currentAmount is greater than SMALLER_CURRENT_AMOUNT
        defaultCropRateMasterShouldBeFound("currentAmount.greaterThan=" + SMALLER_CURRENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountMrIsEqualToSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmountMr equals to DEFAULT_CURRENT_AMOUNT_MR
        defaultCropRateMasterShouldBeFound("currentAmountMr.equals=" + DEFAULT_CURRENT_AMOUNT_MR);

        // Get all the cropRateMasterList where currentAmountMr equals to UPDATED_CURRENT_AMOUNT_MR
        defaultCropRateMasterShouldNotBeFound("currentAmountMr.equals=" + UPDATED_CURRENT_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountMrIsInShouldWork() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmountMr in DEFAULT_CURRENT_AMOUNT_MR or UPDATED_CURRENT_AMOUNT_MR
        defaultCropRateMasterShouldBeFound("currentAmountMr.in=" + DEFAULT_CURRENT_AMOUNT_MR + "," + UPDATED_CURRENT_AMOUNT_MR);

        // Get all the cropRateMasterList where currentAmountMr equals to UPDATED_CURRENT_AMOUNT_MR
        defaultCropRateMasterShouldNotBeFound("currentAmountMr.in=" + UPDATED_CURRENT_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmountMr is not null
        defaultCropRateMasterShouldBeFound("currentAmountMr.specified=true");

        // Get all the cropRateMasterList where currentAmountMr is null
        defaultCropRateMasterShouldNotBeFound("currentAmountMr.specified=false");
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountMrContainsSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmountMr contains DEFAULT_CURRENT_AMOUNT_MR
        defaultCropRateMasterShouldBeFound("currentAmountMr.contains=" + DEFAULT_CURRENT_AMOUNT_MR);

        // Get all the cropRateMasterList where currentAmountMr contains UPDATED_CURRENT_AMOUNT_MR
        defaultCropRateMasterShouldNotBeFound("currentAmountMr.contains=" + UPDATED_CURRENT_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCurrentAmountMrNotContainsSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where currentAmountMr does not contain DEFAULT_CURRENT_AMOUNT_MR
        defaultCropRateMasterShouldNotBeFound("currentAmountMr.doesNotContain=" + DEFAULT_CURRENT_AMOUNT_MR);

        // Get all the cropRateMasterList where currentAmountMr does not contain UPDATED_CURRENT_AMOUNT_MR
        defaultCropRateMasterShouldBeFound("currentAmountMr.doesNotContain=" + UPDATED_CURRENT_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where percentage equals to DEFAULT_PERCENTAGE
        defaultCropRateMasterShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the cropRateMasterList where percentage equals to UPDATED_PERCENTAGE
        defaultCropRateMasterShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultCropRateMasterShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the cropRateMasterList where percentage equals to UPDATED_PERCENTAGE
        defaultCropRateMasterShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where percentage is not null
        defaultCropRateMasterShouldBeFound("percentage.specified=true");

        // Get all the cropRateMasterList where percentage is null
        defaultCropRateMasterShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    void getAllCropRateMastersByPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where percentage is greater than or equal to DEFAULT_PERCENTAGE
        defaultCropRateMasterShouldBeFound("percentage.greaterThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the cropRateMasterList where percentage is greater than or equal to UPDATED_PERCENTAGE
        defaultCropRateMasterShouldNotBeFound("percentage.greaterThanOrEqual=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where percentage is less than or equal to DEFAULT_PERCENTAGE
        defaultCropRateMasterShouldBeFound("percentage.lessThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the cropRateMasterList where percentage is less than or equal to SMALLER_PERCENTAGE
        defaultCropRateMasterShouldNotBeFound("percentage.lessThanOrEqual=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where percentage is less than DEFAULT_PERCENTAGE
        defaultCropRateMasterShouldNotBeFound("percentage.lessThan=" + DEFAULT_PERCENTAGE);

        // Get all the cropRateMasterList where percentage is less than UPDATED_PERCENTAGE
        defaultCropRateMasterShouldBeFound("percentage.lessThan=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where percentage is greater than DEFAULT_PERCENTAGE
        defaultCropRateMasterShouldNotBeFound("percentage.greaterThan=" + DEFAULT_PERCENTAGE);

        // Get all the cropRateMasterList where percentage is greater than SMALLER_PERCENTAGE
        defaultCropRateMasterShouldBeFound("percentage.greaterThan=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByActiveFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where activeFlag equals to DEFAULT_ACTIVE_FLAG
        defaultCropRateMasterShouldBeFound("activeFlag.equals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the cropRateMasterList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultCropRateMasterShouldNotBeFound("activeFlag.equals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByActiveFlagIsInShouldWork() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where activeFlag in DEFAULT_ACTIVE_FLAG or UPDATED_ACTIVE_FLAG
        defaultCropRateMasterShouldBeFound("activeFlag.in=" + DEFAULT_ACTIVE_FLAG + "," + UPDATED_ACTIVE_FLAG);

        // Get all the cropRateMasterList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultCropRateMasterShouldNotBeFound("activeFlag.in=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    void getAllCropRateMastersByActiveFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        // Get all the cropRateMasterList where activeFlag is not null
        defaultCropRateMasterShouldBeFound("activeFlag.specified=true");

        // Get all the cropRateMasterList where activeFlag is null
        defaultCropRateMasterShouldNotBeFound("activeFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllCropRateMastersByCropMasterIsEqualToSomething() throws Exception {
        CropMaster cropMaster;
        if (TestUtil.findAll(em, CropMaster.class).isEmpty()) {
            cropRateMasterRepository.saveAndFlush(cropRateMaster);
            cropMaster = CropMasterResourceIT.createEntity(em);
        } else {
            cropMaster = TestUtil.findAll(em, CropMaster.class).get(0);
        }
        em.persist(cropMaster);
        em.flush();
        cropRateMaster.setCropMaster(cropMaster);
        cropRateMasterRepository.saveAndFlush(cropRateMaster);
        Long cropMasterId = cropMaster.getId();

        // Get all the cropRateMasterList where cropMaster equals to cropMasterId
        defaultCropRateMasterShouldBeFound("cropMasterId.equals=" + cropMasterId);

        // Get all the cropRateMasterList where cropMaster equals to (cropMasterId + 1)
        defaultCropRateMasterShouldNotBeFound("cropMasterId.equals=" + (cropMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCropRateMasterShouldBeFound(String filter) throws Exception {
        restCropRateMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cropRateMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].currentAmount").value(hasItem(DEFAULT_CURRENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currentAmountMr").value(hasItem(DEFAULT_CURRENT_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())));

        // Check, that the count call also returns 1
        restCropRateMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCropRateMasterShouldNotBeFound(String filter) throws Exception {
        restCropRateMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCropRateMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCropRateMaster() throws Exception {
        // Get the cropRateMaster
        restCropRateMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCropRateMaster() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        int databaseSizeBeforeUpdate = cropRateMasterRepository.findAll().size();

        // Update the cropRateMaster
        CropRateMaster updatedCropRateMaster = cropRateMasterRepository.findById(cropRateMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCropRateMaster are not directly saved in db
        em.detach(updatedCropRateMaster);
        updatedCropRateMaster
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .currentAmount(UPDATED_CURRENT_AMOUNT)
            .currentAmountMr(UPDATED_CURRENT_AMOUNT_MR)
            .percentage(UPDATED_PERCENTAGE)
            .activeFlag(UPDATED_ACTIVE_FLAG);

        restCropRateMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCropRateMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCropRateMaster))
            )
            .andExpect(status().isOk());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeUpdate);
        CropRateMaster testCropRateMaster = cropRateMasterList.get(cropRateMasterList.size() - 1);
        assertThat(testCropRateMaster.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testCropRateMaster.getCurrentAmount()).isEqualTo(UPDATED_CURRENT_AMOUNT);
        assertThat(testCropRateMaster.getCurrentAmountMr()).isEqualTo(UPDATED_CURRENT_AMOUNT_MR);
        assertThat(testCropRateMaster.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testCropRateMaster.getActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    void putNonExistingCropRateMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropRateMasterRepository.findAll().size();
        cropRateMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropRateMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cropRateMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cropRateMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCropRateMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropRateMasterRepository.findAll().size();
        cropRateMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropRateMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cropRateMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCropRateMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropRateMasterRepository.findAll().size();
        cropRateMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropRateMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropRateMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCropRateMasterWithPatch() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        int databaseSizeBeforeUpdate = cropRateMasterRepository.findAll().size();

        // Update the cropRateMaster using partial update
        CropRateMaster partialUpdatedCropRateMaster = new CropRateMaster();
        partialUpdatedCropRateMaster.setId(cropRateMaster.getId());

        partialUpdatedCropRateMaster.currentAmount(UPDATED_CURRENT_AMOUNT).activeFlag(UPDATED_ACTIVE_FLAG);

        restCropRateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCropRateMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCropRateMaster))
            )
            .andExpect(status().isOk());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeUpdate);
        CropRateMaster testCropRateMaster = cropRateMasterList.get(cropRateMasterList.size() - 1);
        assertThat(testCropRateMaster.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testCropRateMaster.getCurrentAmount()).isEqualTo(UPDATED_CURRENT_AMOUNT);
        assertThat(testCropRateMaster.getCurrentAmountMr()).isEqualTo(DEFAULT_CURRENT_AMOUNT_MR);
        assertThat(testCropRateMaster.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testCropRateMaster.getActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    void fullUpdateCropRateMasterWithPatch() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        int databaseSizeBeforeUpdate = cropRateMasterRepository.findAll().size();

        // Update the cropRateMaster using partial update
        CropRateMaster partialUpdatedCropRateMaster = new CropRateMaster();
        partialUpdatedCropRateMaster.setId(cropRateMaster.getId());

        partialUpdatedCropRateMaster
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .currentAmount(UPDATED_CURRENT_AMOUNT)
            .currentAmountMr(UPDATED_CURRENT_AMOUNT_MR)
            .percentage(UPDATED_PERCENTAGE)
            .activeFlag(UPDATED_ACTIVE_FLAG);

        restCropRateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCropRateMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCropRateMaster))
            )
            .andExpect(status().isOk());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeUpdate);
        CropRateMaster testCropRateMaster = cropRateMasterList.get(cropRateMasterList.size() - 1);
        assertThat(testCropRateMaster.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testCropRateMaster.getCurrentAmount()).isEqualTo(UPDATED_CURRENT_AMOUNT);
        assertThat(testCropRateMaster.getCurrentAmountMr()).isEqualTo(UPDATED_CURRENT_AMOUNT_MR);
        assertThat(testCropRateMaster.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testCropRateMaster.getActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    void patchNonExistingCropRateMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropRateMasterRepository.findAll().size();
        cropRateMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropRateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cropRateMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cropRateMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCropRateMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropRateMasterRepository.findAll().size();
        cropRateMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropRateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cropRateMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCropRateMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropRateMasterRepository.findAll().size();
        cropRateMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropRateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cropRateMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CropRateMaster in the database
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCropRateMaster() throws Exception {
        // Initialize the database
        cropRateMasterRepository.saveAndFlush(cropRateMaster);

        int databaseSizeBeforeDelete = cropRateMasterRepository.findAll().size();

        // Delete the cropRateMaster
        restCropRateMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, cropRateMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CropRateMaster> cropRateMasterList = cropRateMasterRepository.findAll();
        assertThat(cropRateMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
