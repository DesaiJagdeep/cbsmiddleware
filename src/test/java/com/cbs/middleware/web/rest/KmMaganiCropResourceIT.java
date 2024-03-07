package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KmMagani;
import com.cbs.middleware.domain.KmMaganiCrop;
import com.cbs.middleware.repository.KmMaganiCropRepository;
import com.cbs.middleware.service.criteria.KmMaganiCropCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link KmMaganiCropResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KmMaganiCropResourceIT {

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_CROP_BALANCE = 1D;
    private static final Double UPDATED_CROP_BALANCE = 2D;
    private static final Double SMALLER_CROP_BALANCE = 1D - 1D;

    private static final Double DEFAULT_CROP_DUE = 1D;
    private static final Double UPDATED_CROP_DUE = 2D;
    private static final Double SMALLER_CROP_DUE = 1D - 1D;

    private static final Instant DEFAULT_CROP_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CROP_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CROP_VASULI_PATRA_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CROP_VASULI_PATRA_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_KM_MANJURI = 1D;
    private static final Double UPDATED_KM_MANJURI = 2D;
    private static final Double SMALLER_KM_MANJURI = 1D - 1D;

    private static final Double DEFAULT_KM_AREA = 1D;
    private static final Double UPDATED_KM_AREA = 2D;
    private static final Double SMALLER_KM_AREA = 1D - 1D;

    private static final String DEFAULT_E_KARAR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_E_KARAR_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_E_KARAR_AMOUNT = 1D;
    private static final Double UPDATED_E_KARAR_AMOUNT = 2D;
    private static final Double SMALLER_E_KARAR_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_E_KARAR_AREA = 1D;
    private static final Double UPDATED_E_KARAR_AREA = 2D;
    private static final Double SMALLER_E_KARAR_AREA = 1D - 1D;

    private static final Double DEFAULT_MAGANI_AREA = 1D;
    private static final Double UPDATED_MAGANI_AREA = 2D;
    private static final Double SMALLER_MAGANI_AREA = 1D - 1D;

    private static final Double DEFAULT_MAGANI_AMOUNT = 1D;
    private static final Double UPDATED_MAGANI_AMOUNT = 2D;
    private static final Double SMALLER_MAGANI_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_MAGANI_SHARE = 1D;
    private static final Double UPDATED_MAGANI_SHARE = 2D;
    private static final Double SMALLER_MAGANI_SHARE = 1D - 1D;

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/km-magani-crops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KmMaganiCropRepository kmMaganiCropRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKmMaganiCropMockMvc;

    private KmMaganiCrop kmMaganiCrop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmMaganiCrop createEntity(EntityManager em) {
        KmMaganiCrop kmMaganiCrop = new KmMaganiCrop()
            .cropName(DEFAULT_CROP_NAME)
            .cropBalance(DEFAULT_CROP_BALANCE)
            .cropDue(DEFAULT_CROP_DUE)
            .cropDueDate(DEFAULT_CROP_DUE_DATE)
            .cropVasuliPatraDate(DEFAULT_CROP_VASULI_PATRA_DATE)
            .kmManjuri(DEFAULT_KM_MANJURI)
            .kmArea(DEFAULT_KM_AREA)
            .eKararNumber(DEFAULT_E_KARAR_NUMBER)
            .eKararAmount(DEFAULT_E_KARAR_AMOUNT)
            .eKararArea(DEFAULT_E_KARAR_AREA)
            .maganiArea(DEFAULT_MAGANI_AREA)
            .maganiAmount(DEFAULT_MAGANI_AMOUNT)
            .maganiShare(DEFAULT_MAGANI_SHARE)
            .pacsNumber(DEFAULT_PACS_NUMBER);
        return kmMaganiCrop;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmMaganiCrop createUpdatedEntity(EntityManager em) {
        KmMaganiCrop kmMaganiCrop = new KmMaganiCrop()
            .cropName(UPDATED_CROP_NAME)
            .cropBalance(UPDATED_CROP_BALANCE)
            .cropDue(UPDATED_CROP_DUE)
            .cropDueDate(UPDATED_CROP_DUE_DATE)
            .cropVasuliPatraDate(UPDATED_CROP_VASULI_PATRA_DATE)
            .kmManjuri(UPDATED_KM_MANJURI)
            .kmArea(UPDATED_KM_AREA)
            .eKararNumber(UPDATED_E_KARAR_NUMBER)
            .eKararAmount(UPDATED_E_KARAR_AMOUNT)
            .eKararArea(UPDATED_E_KARAR_AREA)
            .maganiArea(UPDATED_MAGANI_AREA)
            .maganiAmount(UPDATED_MAGANI_AMOUNT)
            .maganiShare(UPDATED_MAGANI_SHARE)
            .pacsNumber(UPDATED_PACS_NUMBER);
        return kmMaganiCrop;
    }

    @BeforeEach
    public void initTest() {
        kmMaganiCrop = createEntity(em);
    }

    @Test
    @Transactional
    void createKmMaganiCrop() throws Exception {
        int databaseSizeBeforeCreate = kmMaganiCropRepository.findAll().size();
        // Create the KmMaganiCrop
        restKmMaganiCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop)))
            .andExpect(status().isCreated());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeCreate + 1);
        KmMaganiCrop testKmMaganiCrop = kmMaganiCropList.get(kmMaganiCropList.size() - 1);
        assertThat(testKmMaganiCrop.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testKmMaganiCrop.getCropBalance()).isEqualTo(DEFAULT_CROP_BALANCE);
        assertThat(testKmMaganiCrop.getCropDue()).isEqualTo(DEFAULT_CROP_DUE);
        assertThat(testKmMaganiCrop.getCropDueDate()).isEqualTo(DEFAULT_CROP_DUE_DATE);
        assertThat(testKmMaganiCrop.getCropVasuliPatraDate()).isEqualTo(DEFAULT_CROP_VASULI_PATRA_DATE);
        assertThat(testKmMaganiCrop.getKmManjuri()).isEqualTo(DEFAULT_KM_MANJURI);
        assertThat(testKmMaganiCrop.getKmArea()).isEqualTo(DEFAULT_KM_AREA);
        assertThat(testKmMaganiCrop.geteKararNumber()).isEqualTo(DEFAULT_E_KARAR_NUMBER);
        assertThat(testKmMaganiCrop.geteKararAmount()).isEqualTo(DEFAULT_E_KARAR_AMOUNT);
        assertThat(testKmMaganiCrop.geteKararArea()).isEqualTo(DEFAULT_E_KARAR_AREA);
        assertThat(testKmMaganiCrop.getMaganiArea()).isEqualTo(DEFAULT_MAGANI_AREA);
        assertThat(testKmMaganiCrop.getMaganiAmount()).isEqualTo(DEFAULT_MAGANI_AMOUNT);
        assertThat(testKmMaganiCrop.getMaganiShare()).isEqualTo(DEFAULT_MAGANI_SHARE);
        assertThat(testKmMaganiCrop.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
    }

    @Test
    @Transactional
    void createKmMaganiCropWithExistingId() throws Exception {
        // Create the KmMaganiCrop with an existing ID
        kmMaganiCrop.setId(1L);

        int databaseSizeBeforeCreate = kmMaganiCropRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmMaganiCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop)))
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKmMaganiCrops() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMaganiCrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].cropBalance").value(hasItem(DEFAULT_CROP_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].cropDue").value(hasItem(DEFAULT_CROP_DUE.doubleValue())))
            .andExpect(jsonPath("$.[*].cropDueDate").value(hasItem(DEFAULT_CROP_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].cropVasuliPatraDate").value(hasItem(DEFAULT_CROP_VASULI_PATRA_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmManjuri").value(hasItem(DEFAULT_KM_MANJURI.doubleValue())))
            .andExpect(jsonPath("$.[*].kmArea").value(hasItem(DEFAULT_KM_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].eKararNumber").value(hasItem(DEFAULT_E_KARAR_NUMBER)))
            .andExpect(jsonPath("$.[*].eKararAmount").value(hasItem(DEFAULT_E_KARAR_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].eKararArea").value(hasItem(DEFAULT_E_KARAR_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].maganiArea").value(hasItem(DEFAULT_MAGANI_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].maganiAmount").value(hasItem(DEFAULT_MAGANI_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maganiShare").value(hasItem(DEFAULT_MAGANI_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)));
    }

    @Test
    @Transactional
    void getKmMaganiCrop() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get the kmMaganiCrop
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL_ID, kmMaganiCrop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kmMaganiCrop.getId().intValue()))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME))
            .andExpect(jsonPath("$.cropBalance").value(DEFAULT_CROP_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.cropDue").value(DEFAULT_CROP_DUE.doubleValue()))
            .andExpect(jsonPath("$.cropDueDate").value(DEFAULT_CROP_DUE_DATE.toString()))
            .andExpect(jsonPath("$.cropVasuliPatraDate").value(DEFAULT_CROP_VASULI_PATRA_DATE.toString()))
            .andExpect(jsonPath("$.kmManjuri").value(DEFAULT_KM_MANJURI.doubleValue()))
            .andExpect(jsonPath("$.kmArea").value(DEFAULT_KM_AREA.doubleValue()))
            .andExpect(jsonPath("$.eKararNumber").value(DEFAULT_E_KARAR_NUMBER))
            .andExpect(jsonPath("$.eKararAmount").value(DEFAULT_E_KARAR_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.eKararArea").value(DEFAULT_E_KARAR_AREA.doubleValue()))
            .andExpect(jsonPath("$.maganiArea").value(DEFAULT_MAGANI_AREA.doubleValue()))
            .andExpect(jsonPath("$.maganiAmount").value(DEFAULT_MAGANI_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.maganiShare").value(DEFAULT_MAGANI_SHARE.doubleValue()))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER));
    }

    @Test
    @Transactional
    void getKmMaganiCropsByIdFiltering() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        Long id = kmMaganiCrop.getId();

        defaultKmMaganiCropShouldBeFound("id.equals=" + id);
        defaultKmMaganiCropShouldNotBeFound("id.notEquals=" + id);

        defaultKmMaganiCropShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKmMaganiCropShouldNotBeFound("id.greaterThan=" + id);

        defaultKmMaganiCropShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKmMaganiCropShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName equals to DEFAULT_CROP_NAME
        defaultKmMaganiCropShouldBeFound("cropName.equals=" + DEFAULT_CROP_NAME);

        // Get all the kmMaganiCropList where cropName equals to UPDATED_CROP_NAME
        defaultKmMaganiCropShouldNotBeFound("cropName.equals=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName in DEFAULT_CROP_NAME or UPDATED_CROP_NAME
        defaultKmMaganiCropShouldBeFound("cropName.in=" + DEFAULT_CROP_NAME + "," + UPDATED_CROP_NAME);

        // Get all the kmMaganiCropList where cropName equals to UPDATED_CROP_NAME
        defaultKmMaganiCropShouldNotBeFound("cropName.in=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName is not null
        defaultKmMaganiCropShouldBeFound("cropName.specified=true");

        // Get all the kmMaganiCropList where cropName is null
        defaultKmMaganiCropShouldNotBeFound("cropName.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName contains DEFAULT_CROP_NAME
        defaultKmMaganiCropShouldBeFound("cropName.contains=" + DEFAULT_CROP_NAME);

        // Get all the kmMaganiCropList where cropName contains UPDATED_CROP_NAME
        defaultKmMaganiCropShouldNotBeFound("cropName.contains=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameNotContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName does not contain DEFAULT_CROP_NAME
        defaultKmMaganiCropShouldNotBeFound("cropName.doesNotContain=" + DEFAULT_CROP_NAME);

        // Get all the kmMaganiCropList where cropName does not contain UPDATED_CROP_NAME
        defaultKmMaganiCropShouldBeFound("cropName.doesNotContain=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropBalance equals to DEFAULT_CROP_BALANCE
        defaultKmMaganiCropShouldBeFound("cropBalance.equals=" + DEFAULT_CROP_BALANCE);

        // Get all the kmMaganiCropList where cropBalance equals to UPDATED_CROP_BALANCE
        defaultKmMaganiCropShouldNotBeFound("cropBalance.equals=" + UPDATED_CROP_BALANCE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropBalance in DEFAULT_CROP_BALANCE or UPDATED_CROP_BALANCE
        defaultKmMaganiCropShouldBeFound("cropBalance.in=" + DEFAULT_CROP_BALANCE + "," + UPDATED_CROP_BALANCE);

        // Get all the kmMaganiCropList where cropBalance equals to UPDATED_CROP_BALANCE
        defaultKmMaganiCropShouldNotBeFound("cropBalance.in=" + UPDATED_CROP_BALANCE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropBalance is not null
        defaultKmMaganiCropShouldBeFound("cropBalance.specified=true");

        // Get all the kmMaganiCropList where cropBalance is null
        defaultKmMaganiCropShouldNotBeFound("cropBalance.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropBalance is greater than or equal to DEFAULT_CROP_BALANCE
        defaultKmMaganiCropShouldBeFound("cropBalance.greaterThanOrEqual=" + DEFAULT_CROP_BALANCE);

        // Get all the kmMaganiCropList where cropBalance is greater than or equal to UPDATED_CROP_BALANCE
        defaultKmMaganiCropShouldNotBeFound("cropBalance.greaterThanOrEqual=" + UPDATED_CROP_BALANCE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropBalance is less than or equal to DEFAULT_CROP_BALANCE
        defaultKmMaganiCropShouldBeFound("cropBalance.lessThanOrEqual=" + DEFAULT_CROP_BALANCE);

        // Get all the kmMaganiCropList where cropBalance is less than or equal to SMALLER_CROP_BALANCE
        defaultKmMaganiCropShouldNotBeFound("cropBalance.lessThanOrEqual=" + SMALLER_CROP_BALANCE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropBalance is less than DEFAULT_CROP_BALANCE
        defaultKmMaganiCropShouldNotBeFound("cropBalance.lessThan=" + DEFAULT_CROP_BALANCE);

        // Get all the kmMaganiCropList where cropBalance is less than UPDATED_CROP_BALANCE
        defaultKmMaganiCropShouldBeFound("cropBalance.lessThan=" + UPDATED_CROP_BALANCE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropBalance is greater than DEFAULT_CROP_BALANCE
        defaultKmMaganiCropShouldNotBeFound("cropBalance.greaterThan=" + DEFAULT_CROP_BALANCE);

        // Get all the kmMaganiCropList where cropBalance is greater than SMALLER_CROP_BALANCE
        defaultKmMaganiCropShouldBeFound("cropBalance.greaterThan=" + SMALLER_CROP_BALANCE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDue equals to DEFAULT_CROP_DUE
        defaultKmMaganiCropShouldBeFound("cropDue.equals=" + DEFAULT_CROP_DUE);

        // Get all the kmMaganiCropList where cropDue equals to UPDATED_CROP_DUE
        defaultKmMaganiCropShouldNotBeFound("cropDue.equals=" + UPDATED_CROP_DUE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDue in DEFAULT_CROP_DUE or UPDATED_CROP_DUE
        defaultKmMaganiCropShouldBeFound("cropDue.in=" + DEFAULT_CROP_DUE + "," + UPDATED_CROP_DUE);

        // Get all the kmMaganiCropList where cropDue equals to UPDATED_CROP_DUE
        defaultKmMaganiCropShouldNotBeFound("cropDue.in=" + UPDATED_CROP_DUE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDue is not null
        defaultKmMaganiCropShouldBeFound("cropDue.specified=true");

        // Get all the kmMaganiCropList where cropDue is null
        defaultKmMaganiCropShouldNotBeFound("cropDue.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDue is greater than or equal to DEFAULT_CROP_DUE
        defaultKmMaganiCropShouldBeFound("cropDue.greaterThanOrEqual=" + DEFAULT_CROP_DUE);

        // Get all the kmMaganiCropList where cropDue is greater than or equal to UPDATED_CROP_DUE
        defaultKmMaganiCropShouldNotBeFound("cropDue.greaterThanOrEqual=" + UPDATED_CROP_DUE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDue is less than or equal to DEFAULT_CROP_DUE
        defaultKmMaganiCropShouldBeFound("cropDue.lessThanOrEqual=" + DEFAULT_CROP_DUE);

        // Get all the kmMaganiCropList where cropDue is less than or equal to SMALLER_CROP_DUE
        defaultKmMaganiCropShouldNotBeFound("cropDue.lessThanOrEqual=" + SMALLER_CROP_DUE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDue is less than DEFAULT_CROP_DUE
        defaultKmMaganiCropShouldNotBeFound("cropDue.lessThan=" + DEFAULT_CROP_DUE);

        // Get all the kmMaganiCropList where cropDue is less than UPDATED_CROP_DUE
        defaultKmMaganiCropShouldBeFound("cropDue.lessThan=" + UPDATED_CROP_DUE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDue is greater than DEFAULT_CROP_DUE
        defaultKmMaganiCropShouldNotBeFound("cropDue.greaterThan=" + DEFAULT_CROP_DUE);

        // Get all the kmMaganiCropList where cropDue is greater than SMALLER_CROP_DUE
        defaultKmMaganiCropShouldBeFound("cropDue.greaterThan=" + SMALLER_CROP_DUE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDueDate equals to DEFAULT_CROP_DUE_DATE
        defaultKmMaganiCropShouldBeFound("cropDueDate.equals=" + DEFAULT_CROP_DUE_DATE);

        // Get all the kmMaganiCropList where cropDueDate equals to UPDATED_CROP_DUE_DATE
        defaultKmMaganiCropShouldNotBeFound("cropDueDate.equals=" + UPDATED_CROP_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDueDate in DEFAULT_CROP_DUE_DATE or UPDATED_CROP_DUE_DATE
        defaultKmMaganiCropShouldBeFound("cropDueDate.in=" + DEFAULT_CROP_DUE_DATE + "," + UPDATED_CROP_DUE_DATE);

        // Get all the kmMaganiCropList where cropDueDate equals to UPDATED_CROP_DUE_DATE
        defaultKmMaganiCropShouldNotBeFound("cropDueDate.in=" + UPDATED_CROP_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropDueDate is not null
        defaultKmMaganiCropShouldBeFound("cropDueDate.specified=true");

        // Get all the kmMaganiCropList where cropDueDate is null
        defaultKmMaganiCropShouldNotBeFound("cropDueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropVasuliPatraDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropVasuliPatraDate equals to DEFAULT_CROP_VASULI_PATRA_DATE
        defaultKmMaganiCropShouldBeFound("cropVasuliPatraDate.equals=" + DEFAULT_CROP_VASULI_PATRA_DATE);

        // Get all the kmMaganiCropList where cropVasuliPatraDate equals to UPDATED_CROP_VASULI_PATRA_DATE
        defaultKmMaganiCropShouldNotBeFound("cropVasuliPatraDate.equals=" + UPDATED_CROP_VASULI_PATRA_DATE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropVasuliPatraDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropVasuliPatraDate in DEFAULT_CROP_VASULI_PATRA_DATE or UPDATED_CROP_VASULI_PATRA_DATE
        defaultKmMaganiCropShouldBeFound("cropVasuliPatraDate.in=" + DEFAULT_CROP_VASULI_PATRA_DATE + "," + UPDATED_CROP_VASULI_PATRA_DATE);

        // Get all the kmMaganiCropList where cropVasuliPatraDate equals to UPDATED_CROP_VASULI_PATRA_DATE
        defaultKmMaganiCropShouldNotBeFound("cropVasuliPatraDate.in=" + UPDATED_CROP_VASULI_PATRA_DATE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropVasuliPatraDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropVasuliPatraDate is not null
        defaultKmMaganiCropShouldBeFound("cropVasuliPatraDate.specified=true");

        // Get all the kmMaganiCropList where cropVasuliPatraDate is null
        defaultKmMaganiCropShouldNotBeFound("cropVasuliPatraDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmManjuriIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmManjuri equals to DEFAULT_KM_MANJURI
        defaultKmMaganiCropShouldBeFound("kmManjuri.equals=" + DEFAULT_KM_MANJURI);

        // Get all the kmMaganiCropList where kmManjuri equals to UPDATED_KM_MANJURI
        defaultKmMaganiCropShouldNotBeFound("kmManjuri.equals=" + UPDATED_KM_MANJURI);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmManjuriIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmManjuri in DEFAULT_KM_MANJURI or UPDATED_KM_MANJURI
        defaultKmMaganiCropShouldBeFound("kmManjuri.in=" + DEFAULT_KM_MANJURI + "," + UPDATED_KM_MANJURI);

        // Get all the kmMaganiCropList where kmManjuri equals to UPDATED_KM_MANJURI
        defaultKmMaganiCropShouldNotBeFound("kmManjuri.in=" + UPDATED_KM_MANJURI);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmManjuriIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmManjuri is not null
        defaultKmMaganiCropShouldBeFound("kmManjuri.specified=true");

        // Get all the kmMaganiCropList where kmManjuri is null
        defaultKmMaganiCropShouldNotBeFound("kmManjuri.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmManjuriIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmManjuri is greater than or equal to DEFAULT_KM_MANJURI
        defaultKmMaganiCropShouldBeFound("kmManjuri.greaterThanOrEqual=" + DEFAULT_KM_MANJURI);

        // Get all the kmMaganiCropList where kmManjuri is greater than or equal to UPDATED_KM_MANJURI
        defaultKmMaganiCropShouldNotBeFound("kmManjuri.greaterThanOrEqual=" + UPDATED_KM_MANJURI);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmManjuriIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmManjuri is less than or equal to DEFAULT_KM_MANJURI
        defaultKmMaganiCropShouldBeFound("kmManjuri.lessThanOrEqual=" + DEFAULT_KM_MANJURI);

        // Get all the kmMaganiCropList where kmManjuri is less than or equal to SMALLER_KM_MANJURI
        defaultKmMaganiCropShouldNotBeFound("kmManjuri.lessThanOrEqual=" + SMALLER_KM_MANJURI);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmManjuriIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmManjuri is less than DEFAULT_KM_MANJURI
        defaultKmMaganiCropShouldNotBeFound("kmManjuri.lessThan=" + DEFAULT_KM_MANJURI);

        // Get all the kmMaganiCropList where kmManjuri is less than UPDATED_KM_MANJURI
        defaultKmMaganiCropShouldBeFound("kmManjuri.lessThan=" + UPDATED_KM_MANJURI);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmManjuriIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmManjuri is greater than DEFAULT_KM_MANJURI
        defaultKmMaganiCropShouldNotBeFound("kmManjuri.greaterThan=" + DEFAULT_KM_MANJURI);

        // Get all the kmMaganiCropList where kmManjuri is greater than SMALLER_KM_MANJURI
        defaultKmMaganiCropShouldBeFound("kmManjuri.greaterThan=" + SMALLER_KM_MANJURI);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmArea equals to DEFAULT_KM_AREA
        defaultKmMaganiCropShouldBeFound("kmArea.equals=" + DEFAULT_KM_AREA);

        // Get all the kmMaganiCropList where kmArea equals to UPDATED_KM_AREA
        defaultKmMaganiCropShouldNotBeFound("kmArea.equals=" + UPDATED_KM_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmAreaIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmArea in DEFAULT_KM_AREA or UPDATED_KM_AREA
        defaultKmMaganiCropShouldBeFound("kmArea.in=" + DEFAULT_KM_AREA + "," + UPDATED_KM_AREA);

        // Get all the kmMaganiCropList where kmArea equals to UPDATED_KM_AREA
        defaultKmMaganiCropShouldNotBeFound("kmArea.in=" + UPDATED_KM_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmArea is not null
        defaultKmMaganiCropShouldBeFound("kmArea.specified=true");

        // Get all the kmMaganiCropList where kmArea is null
        defaultKmMaganiCropShouldNotBeFound("kmArea.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmArea is greater than or equal to DEFAULT_KM_AREA
        defaultKmMaganiCropShouldBeFound("kmArea.greaterThanOrEqual=" + DEFAULT_KM_AREA);

        // Get all the kmMaganiCropList where kmArea is greater than or equal to UPDATED_KM_AREA
        defaultKmMaganiCropShouldNotBeFound("kmArea.greaterThanOrEqual=" + UPDATED_KM_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmArea is less than or equal to DEFAULT_KM_AREA
        defaultKmMaganiCropShouldBeFound("kmArea.lessThanOrEqual=" + DEFAULT_KM_AREA);

        // Get all the kmMaganiCropList where kmArea is less than or equal to SMALLER_KM_AREA
        defaultKmMaganiCropShouldNotBeFound("kmArea.lessThanOrEqual=" + SMALLER_KM_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmArea is less than DEFAULT_KM_AREA
        defaultKmMaganiCropShouldNotBeFound("kmArea.lessThan=" + DEFAULT_KM_AREA);

        // Get all the kmMaganiCropList where kmArea is less than UPDATED_KM_AREA
        defaultKmMaganiCropShouldBeFound("kmArea.lessThan=" + UPDATED_KM_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where kmArea is greater than DEFAULT_KM_AREA
        defaultKmMaganiCropShouldNotBeFound("kmArea.greaterThan=" + DEFAULT_KM_AREA);

        // Get all the kmMaganiCropList where kmArea is greater than SMALLER_KM_AREA
        defaultKmMaganiCropShouldBeFound("kmArea.greaterThan=" + SMALLER_KM_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararNumber equals to DEFAULT_E_KARAR_NUMBER
        defaultKmMaganiCropShouldBeFound("eKararNumber.equals=" + DEFAULT_E_KARAR_NUMBER);

        // Get all the kmMaganiCropList where eKararNumber equals to UPDATED_E_KARAR_NUMBER
        defaultKmMaganiCropShouldNotBeFound("eKararNumber.equals=" + UPDATED_E_KARAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararNumber in DEFAULT_E_KARAR_NUMBER or UPDATED_E_KARAR_NUMBER
        defaultKmMaganiCropShouldBeFound("eKararNumber.in=" + DEFAULT_E_KARAR_NUMBER + "," + UPDATED_E_KARAR_NUMBER);

        // Get all the kmMaganiCropList where eKararNumber equals to UPDATED_E_KARAR_NUMBER
        defaultKmMaganiCropShouldNotBeFound("eKararNumber.in=" + UPDATED_E_KARAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararNumber is not null
        defaultKmMaganiCropShouldBeFound("eKararNumber.specified=true");

        // Get all the kmMaganiCropList where eKararNumber is null
        defaultKmMaganiCropShouldNotBeFound("eKararNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararNumberContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararNumber contains DEFAULT_E_KARAR_NUMBER
        defaultKmMaganiCropShouldBeFound("eKararNumber.contains=" + DEFAULT_E_KARAR_NUMBER);

        // Get all the kmMaganiCropList where eKararNumber contains UPDATED_E_KARAR_NUMBER
        defaultKmMaganiCropShouldNotBeFound("eKararNumber.contains=" + UPDATED_E_KARAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararNumber does not contain DEFAULT_E_KARAR_NUMBER
        defaultKmMaganiCropShouldNotBeFound("eKararNumber.doesNotContain=" + DEFAULT_E_KARAR_NUMBER);

        // Get all the kmMaganiCropList where eKararNumber does not contain UPDATED_E_KARAR_NUMBER
        defaultKmMaganiCropShouldBeFound("eKararNumber.doesNotContain=" + UPDATED_E_KARAR_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararAmount equals to DEFAULT_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldBeFound("eKararAmount.equals=" + DEFAULT_E_KARAR_AMOUNT);

        // Get all the kmMaganiCropList where eKararAmount equals to UPDATED_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("eKararAmount.equals=" + UPDATED_E_KARAR_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararAmount in DEFAULT_E_KARAR_AMOUNT or UPDATED_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldBeFound("eKararAmount.in=" + DEFAULT_E_KARAR_AMOUNT + "," + UPDATED_E_KARAR_AMOUNT);

        // Get all the kmMaganiCropList where eKararAmount equals to UPDATED_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("eKararAmount.in=" + UPDATED_E_KARAR_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararAmount is not null
        defaultKmMaganiCropShouldBeFound("eKararAmount.specified=true");

        // Get all the kmMaganiCropList where eKararAmount is null
        defaultKmMaganiCropShouldNotBeFound("eKararAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararAmount is greater than or equal to DEFAULT_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldBeFound("eKararAmount.greaterThanOrEqual=" + DEFAULT_E_KARAR_AMOUNT);

        // Get all the kmMaganiCropList where eKararAmount is greater than or equal to UPDATED_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("eKararAmount.greaterThanOrEqual=" + UPDATED_E_KARAR_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararAmount is less than or equal to DEFAULT_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldBeFound("eKararAmount.lessThanOrEqual=" + DEFAULT_E_KARAR_AMOUNT);

        // Get all the kmMaganiCropList where eKararAmount is less than or equal to SMALLER_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("eKararAmount.lessThanOrEqual=" + SMALLER_E_KARAR_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararAmount is less than DEFAULT_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("eKararAmount.lessThan=" + DEFAULT_E_KARAR_AMOUNT);

        // Get all the kmMaganiCropList where eKararAmount is less than UPDATED_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldBeFound("eKararAmount.lessThan=" + UPDATED_E_KARAR_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararAmount is greater than DEFAULT_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("eKararAmount.greaterThan=" + DEFAULT_E_KARAR_AMOUNT);

        // Get all the kmMaganiCropList where eKararAmount is greater than SMALLER_E_KARAR_AMOUNT
        defaultKmMaganiCropShouldBeFound("eKararAmount.greaterThan=" + SMALLER_E_KARAR_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararArea equals to DEFAULT_E_KARAR_AREA
        defaultKmMaganiCropShouldBeFound("eKararArea.equals=" + DEFAULT_E_KARAR_AREA);

        // Get all the kmMaganiCropList where eKararArea equals to UPDATED_E_KARAR_AREA
        defaultKmMaganiCropShouldNotBeFound("eKararArea.equals=" + UPDATED_E_KARAR_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAreaIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararArea in DEFAULT_E_KARAR_AREA or UPDATED_E_KARAR_AREA
        defaultKmMaganiCropShouldBeFound("eKararArea.in=" + DEFAULT_E_KARAR_AREA + "," + UPDATED_E_KARAR_AREA);

        // Get all the kmMaganiCropList where eKararArea equals to UPDATED_E_KARAR_AREA
        defaultKmMaganiCropShouldNotBeFound("eKararArea.in=" + UPDATED_E_KARAR_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararArea is not null
        defaultKmMaganiCropShouldBeFound("eKararArea.specified=true");

        // Get all the kmMaganiCropList where eKararArea is null
        defaultKmMaganiCropShouldNotBeFound("eKararArea.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararArea is greater than or equal to DEFAULT_E_KARAR_AREA
        defaultKmMaganiCropShouldBeFound("eKararArea.greaterThanOrEqual=" + DEFAULT_E_KARAR_AREA);

        // Get all the kmMaganiCropList where eKararArea is greater than or equal to UPDATED_E_KARAR_AREA
        defaultKmMaganiCropShouldNotBeFound("eKararArea.greaterThanOrEqual=" + UPDATED_E_KARAR_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararArea is less than or equal to DEFAULT_E_KARAR_AREA
        defaultKmMaganiCropShouldBeFound("eKararArea.lessThanOrEqual=" + DEFAULT_E_KARAR_AREA);

        // Get all the kmMaganiCropList where eKararArea is less than or equal to SMALLER_E_KARAR_AREA
        defaultKmMaganiCropShouldNotBeFound("eKararArea.lessThanOrEqual=" + SMALLER_E_KARAR_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararArea is less than DEFAULT_E_KARAR_AREA
        defaultKmMaganiCropShouldNotBeFound("eKararArea.lessThan=" + DEFAULT_E_KARAR_AREA);

        // Get all the kmMaganiCropList where eKararArea is less than UPDATED_E_KARAR_AREA
        defaultKmMaganiCropShouldBeFound("eKararArea.lessThan=" + UPDATED_E_KARAR_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByeKararAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where eKararArea is greater than DEFAULT_E_KARAR_AREA
        defaultKmMaganiCropShouldNotBeFound("eKararArea.greaterThan=" + DEFAULT_E_KARAR_AREA);

        // Get all the kmMaganiCropList where eKararArea is greater than SMALLER_E_KARAR_AREA
        defaultKmMaganiCropShouldBeFound("eKararArea.greaterThan=" + SMALLER_E_KARAR_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiArea equals to DEFAULT_MAGANI_AREA
        defaultKmMaganiCropShouldBeFound("maganiArea.equals=" + DEFAULT_MAGANI_AREA);

        // Get all the kmMaganiCropList where maganiArea equals to UPDATED_MAGANI_AREA
        defaultKmMaganiCropShouldNotBeFound("maganiArea.equals=" + UPDATED_MAGANI_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAreaIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiArea in DEFAULT_MAGANI_AREA or UPDATED_MAGANI_AREA
        defaultKmMaganiCropShouldBeFound("maganiArea.in=" + DEFAULT_MAGANI_AREA + "," + UPDATED_MAGANI_AREA);

        // Get all the kmMaganiCropList where maganiArea equals to UPDATED_MAGANI_AREA
        defaultKmMaganiCropShouldNotBeFound("maganiArea.in=" + UPDATED_MAGANI_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiArea is not null
        defaultKmMaganiCropShouldBeFound("maganiArea.specified=true");

        // Get all the kmMaganiCropList where maganiArea is null
        defaultKmMaganiCropShouldNotBeFound("maganiArea.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiArea is greater than or equal to DEFAULT_MAGANI_AREA
        defaultKmMaganiCropShouldBeFound("maganiArea.greaterThanOrEqual=" + DEFAULT_MAGANI_AREA);

        // Get all the kmMaganiCropList where maganiArea is greater than or equal to UPDATED_MAGANI_AREA
        defaultKmMaganiCropShouldNotBeFound("maganiArea.greaterThanOrEqual=" + UPDATED_MAGANI_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiArea is less than or equal to DEFAULT_MAGANI_AREA
        defaultKmMaganiCropShouldBeFound("maganiArea.lessThanOrEqual=" + DEFAULT_MAGANI_AREA);

        // Get all the kmMaganiCropList where maganiArea is less than or equal to SMALLER_MAGANI_AREA
        defaultKmMaganiCropShouldNotBeFound("maganiArea.lessThanOrEqual=" + SMALLER_MAGANI_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiArea is less than DEFAULT_MAGANI_AREA
        defaultKmMaganiCropShouldNotBeFound("maganiArea.lessThan=" + DEFAULT_MAGANI_AREA);

        // Get all the kmMaganiCropList where maganiArea is less than UPDATED_MAGANI_AREA
        defaultKmMaganiCropShouldBeFound("maganiArea.lessThan=" + UPDATED_MAGANI_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiArea is greater than DEFAULT_MAGANI_AREA
        defaultKmMaganiCropShouldNotBeFound("maganiArea.greaterThan=" + DEFAULT_MAGANI_AREA);

        // Get all the kmMaganiCropList where maganiArea is greater than SMALLER_MAGANI_AREA
        defaultKmMaganiCropShouldBeFound("maganiArea.greaterThan=" + SMALLER_MAGANI_AREA);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiAmount equals to DEFAULT_MAGANI_AMOUNT
        defaultKmMaganiCropShouldBeFound("maganiAmount.equals=" + DEFAULT_MAGANI_AMOUNT);

        // Get all the kmMaganiCropList where maganiAmount equals to UPDATED_MAGANI_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("maganiAmount.equals=" + UPDATED_MAGANI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAmountIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiAmount in DEFAULT_MAGANI_AMOUNT or UPDATED_MAGANI_AMOUNT
        defaultKmMaganiCropShouldBeFound("maganiAmount.in=" + DEFAULT_MAGANI_AMOUNT + "," + UPDATED_MAGANI_AMOUNT);

        // Get all the kmMaganiCropList where maganiAmount equals to UPDATED_MAGANI_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("maganiAmount.in=" + UPDATED_MAGANI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiAmount is not null
        defaultKmMaganiCropShouldBeFound("maganiAmount.specified=true");

        // Get all the kmMaganiCropList where maganiAmount is null
        defaultKmMaganiCropShouldNotBeFound("maganiAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiAmount is greater than or equal to DEFAULT_MAGANI_AMOUNT
        defaultKmMaganiCropShouldBeFound("maganiAmount.greaterThanOrEqual=" + DEFAULT_MAGANI_AMOUNT);

        // Get all the kmMaganiCropList where maganiAmount is greater than or equal to UPDATED_MAGANI_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("maganiAmount.greaterThanOrEqual=" + UPDATED_MAGANI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiAmount is less than or equal to DEFAULT_MAGANI_AMOUNT
        defaultKmMaganiCropShouldBeFound("maganiAmount.lessThanOrEqual=" + DEFAULT_MAGANI_AMOUNT);

        // Get all the kmMaganiCropList where maganiAmount is less than or equal to SMALLER_MAGANI_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("maganiAmount.lessThanOrEqual=" + SMALLER_MAGANI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiAmount is less than DEFAULT_MAGANI_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("maganiAmount.lessThan=" + DEFAULT_MAGANI_AMOUNT);

        // Get all the kmMaganiCropList where maganiAmount is less than UPDATED_MAGANI_AMOUNT
        defaultKmMaganiCropShouldBeFound("maganiAmount.lessThan=" + UPDATED_MAGANI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiAmount is greater than DEFAULT_MAGANI_AMOUNT
        defaultKmMaganiCropShouldNotBeFound("maganiAmount.greaterThan=" + DEFAULT_MAGANI_AMOUNT);

        // Get all the kmMaganiCropList where maganiAmount is greater than SMALLER_MAGANI_AMOUNT
        defaultKmMaganiCropShouldBeFound("maganiAmount.greaterThan=" + SMALLER_MAGANI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiShareIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiShare equals to DEFAULT_MAGANI_SHARE
        defaultKmMaganiCropShouldBeFound("maganiShare.equals=" + DEFAULT_MAGANI_SHARE);

        // Get all the kmMaganiCropList where maganiShare equals to UPDATED_MAGANI_SHARE
        defaultKmMaganiCropShouldNotBeFound("maganiShare.equals=" + UPDATED_MAGANI_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiShareIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiShare in DEFAULT_MAGANI_SHARE or UPDATED_MAGANI_SHARE
        defaultKmMaganiCropShouldBeFound("maganiShare.in=" + DEFAULT_MAGANI_SHARE + "," + UPDATED_MAGANI_SHARE);

        // Get all the kmMaganiCropList where maganiShare equals to UPDATED_MAGANI_SHARE
        defaultKmMaganiCropShouldNotBeFound("maganiShare.in=" + UPDATED_MAGANI_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiShareIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiShare is not null
        defaultKmMaganiCropShouldBeFound("maganiShare.specified=true");

        // Get all the kmMaganiCropList where maganiShare is null
        defaultKmMaganiCropShouldNotBeFound("maganiShare.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiShareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiShare is greater than or equal to DEFAULT_MAGANI_SHARE
        defaultKmMaganiCropShouldBeFound("maganiShare.greaterThanOrEqual=" + DEFAULT_MAGANI_SHARE);

        // Get all the kmMaganiCropList where maganiShare is greater than or equal to UPDATED_MAGANI_SHARE
        defaultKmMaganiCropShouldNotBeFound("maganiShare.greaterThanOrEqual=" + UPDATED_MAGANI_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiShareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiShare is less than or equal to DEFAULT_MAGANI_SHARE
        defaultKmMaganiCropShouldBeFound("maganiShare.lessThanOrEqual=" + DEFAULT_MAGANI_SHARE);

        // Get all the kmMaganiCropList where maganiShare is less than or equal to SMALLER_MAGANI_SHARE
        defaultKmMaganiCropShouldNotBeFound("maganiShare.lessThanOrEqual=" + SMALLER_MAGANI_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiShareIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiShare is less than DEFAULT_MAGANI_SHARE
        defaultKmMaganiCropShouldNotBeFound("maganiShare.lessThan=" + DEFAULT_MAGANI_SHARE);

        // Get all the kmMaganiCropList where maganiShare is less than UPDATED_MAGANI_SHARE
        defaultKmMaganiCropShouldBeFound("maganiShare.lessThan=" + UPDATED_MAGANI_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByMaganiShareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where maganiShare is greater than DEFAULT_MAGANI_SHARE
        defaultKmMaganiCropShouldNotBeFound("maganiShare.greaterThan=" + DEFAULT_MAGANI_SHARE);

        // Get all the kmMaganiCropList where maganiShare is greater than SMALLER_MAGANI_SHARE
        defaultKmMaganiCropShouldBeFound("maganiShare.greaterThan=" + SMALLER_MAGANI_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByPacsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where pacsNumber equals to DEFAULT_PACS_NUMBER
        defaultKmMaganiCropShouldBeFound("pacsNumber.equals=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMaganiCropList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKmMaganiCropShouldNotBeFound("pacsNumber.equals=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByPacsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where pacsNumber in DEFAULT_PACS_NUMBER or UPDATED_PACS_NUMBER
        defaultKmMaganiCropShouldBeFound("pacsNumber.in=" + DEFAULT_PACS_NUMBER + "," + UPDATED_PACS_NUMBER);

        // Get all the kmMaganiCropList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKmMaganiCropShouldNotBeFound("pacsNumber.in=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByPacsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where pacsNumber is not null
        defaultKmMaganiCropShouldBeFound("pacsNumber.specified=true");

        // Get all the kmMaganiCropList where pacsNumber is null
        defaultKmMaganiCropShouldNotBeFound("pacsNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByPacsNumberContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where pacsNumber contains DEFAULT_PACS_NUMBER
        defaultKmMaganiCropShouldBeFound("pacsNumber.contains=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMaganiCropList where pacsNumber contains UPDATED_PACS_NUMBER
        defaultKmMaganiCropShouldNotBeFound("pacsNumber.contains=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByPacsNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where pacsNumber does not contain DEFAULT_PACS_NUMBER
        defaultKmMaganiCropShouldNotBeFound("pacsNumber.doesNotContain=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMaganiCropList where pacsNumber does not contain UPDATED_PACS_NUMBER
        defaultKmMaganiCropShouldBeFound("pacsNumber.doesNotContain=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmMaganiIsEqualToSomething() throws Exception {
        KmMagani kmMagani;
        if (TestUtil.findAll(em, KmMagani.class).isEmpty()) {
            kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);
            kmMagani = KmMaganiResourceIT.createEntity(em);
        } else {
            kmMagani = TestUtil.findAll(em, KmMagani.class).get(0);
        }
        em.persist(kmMagani);
        em.flush();
        kmMaganiCrop.setKmMagani(kmMagani);
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);
        Long kmMaganiId = kmMagani.getId();

        // Get all the kmMaganiCropList where kmMagani equals to kmMaganiId
        defaultKmMaganiCropShouldBeFound("kmMaganiId.equals=" + kmMaganiId);

        // Get all the kmMaganiCropList where kmMagani equals to (kmMaganiId + 1)
        defaultKmMaganiCropShouldNotBeFound("kmMaganiId.equals=" + (kmMaganiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKmMaganiCropShouldBeFound(String filter) throws Exception {
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMaganiCrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].cropBalance").value(hasItem(DEFAULT_CROP_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].cropDue").value(hasItem(DEFAULT_CROP_DUE.doubleValue())))
            .andExpect(jsonPath("$.[*].cropDueDate").value(hasItem(DEFAULT_CROP_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].cropVasuliPatraDate").value(hasItem(DEFAULT_CROP_VASULI_PATRA_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmManjuri").value(hasItem(DEFAULT_KM_MANJURI.doubleValue())))
            .andExpect(jsonPath("$.[*].kmArea").value(hasItem(DEFAULT_KM_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].eKararNumber").value(hasItem(DEFAULT_E_KARAR_NUMBER)))
            .andExpect(jsonPath("$.[*].eKararAmount").value(hasItem(DEFAULT_E_KARAR_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].eKararArea").value(hasItem(DEFAULT_E_KARAR_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].maganiArea").value(hasItem(DEFAULT_MAGANI_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].maganiAmount").value(hasItem(DEFAULT_MAGANI_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maganiShare").value(hasItem(DEFAULT_MAGANI_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)));

        // Check, that the count call also returns 1
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKmMaganiCropShouldNotBeFound(String filter) throws Exception {
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKmMaganiCrop() throws Exception {
        // Get the kmMaganiCrop
        restKmMaganiCropMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKmMaganiCrop() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();

        // Update the kmMaganiCrop
        KmMaganiCrop updatedKmMaganiCrop = kmMaganiCropRepository.findById(kmMaganiCrop.getId()).get();
        // Disconnect from session so that the updates on updatedKmMaganiCrop are not directly saved in db
        em.detach(updatedKmMaganiCrop);
        updatedKmMaganiCrop
            .cropName(UPDATED_CROP_NAME)
            .cropBalance(UPDATED_CROP_BALANCE)
            .cropDue(UPDATED_CROP_DUE)
            .cropDueDate(UPDATED_CROP_DUE_DATE)
            .cropVasuliPatraDate(UPDATED_CROP_VASULI_PATRA_DATE)
            .kmManjuri(UPDATED_KM_MANJURI)
            .kmArea(UPDATED_KM_AREA)
            .eKararNumber(UPDATED_E_KARAR_NUMBER)
            .eKararAmount(UPDATED_E_KARAR_AMOUNT)
            .eKararArea(UPDATED_E_KARAR_AREA)
            .maganiArea(UPDATED_MAGANI_AREA)
            .maganiAmount(UPDATED_MAGANI_AMOUNT)
            .maganiShare(UPDATED_MAGANI_SHARE)
            .pacsNumber(UPDATED_PACS_NUMBER);

        restKmMaganiCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKmMaganiCrop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKmMaganiCrop))
            )
            .andExpect(status().isOk());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
        KmMaganiCrop testKmMaganiCrop = kmMaganiCropList.get(kmMaganiCropList.size() - 1);
        assertThat(testKmMaganiCrop.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testKmMaganiCrop.getCropBalance()).isEqualTo(UPDATED_CROP_BALANCE);
        assertThat(testKmMaganiCrop.getCropDue()).isEqualTo(UPDATED_CROP_DUE);
        assertThat(testKmMaganiCrop.getCropDueDate()).isEqualTo(UPDATED_CROP_DUE_DATE);
        assertThat(testKmMaganiCrop.getCropVasuliPatraDate()).isEqualTo(UPDATED_CROP_VASULI_PATRA_DATE);
        assertThat(testKmMaganiCrop.getKmManjuri()).isEqualTo(UPDATED_KM_MANJURI);
        assertThat(testKmMaganiCrop.getKmArea()).isEqualTo(UPDATED_KM_AREA);
        assertThat(testKmMaganiCrop.geteKararNumber()).isEqualTo(UPDATED_E_KARAR_NUMBER);
        assertThat(testKmMaganiCrop.geteKararAmount()).isEqualTo(UPDATED_E_KARAR_AMOUNT);
        assertThat(testKmMaganiCrop.geteKararArea()).isEqualTo(UPDATED_E_KARAR_AREA);
        assertThat(testKmMaganiCrop.getMaganiArea()).isEqualTo(UPDATED_MAGANI_AREA);
        assertThat(testKmMaganiCrop.getMaganiAmount()).isEqualTo(UPDATED_MAGANI_AMOUNT);
        assertThat(testKmMaganiCrop.getMaganiShare()).isEqualTo(UPDATED_MAGANI_SHARE);
        assertThat(testKmMaganiCrop.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kmMaganiCrop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKmMaganiCropWithPatch() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();

        // Update the kmMaganiCrop using partial update
        KmMaganiCrop partialUpdatedKmMaganiCrop = new KmMaganiCrop();
        partialUpdatedKmMaganiCrop.setId(kmMaganiCrop.getId());

        partialUpdatedKmMaganiCrop
            .cropVasuliPatraDate(UPDATED_CROP_VASULI_PATRA_DATE)
            .kmArea(UPDATED_KM_AREA)
            .eKararNumber(UPDATED_E_KARAR_NUMBER)
            .eKararAmount(UPDATED_E_KARAR_AMOUNT);

        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmMaganiCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmMaganiCrop))
            )
            .andExpect(status().isOk());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
        KmMaganiCrop testKmMaganiCrop = kmMaganiCropList.get(kmMaganiCropList.size() - 1);
        assertThat(testKmMaganiCrop.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testKmMaganiCrop.getCropBalance()).isEqualTo(DEFAULT_CROP_BALANCE);
        assertThat(testKmMaganiCrop.getCropDue()).isEqualTo(DEFAULT_CROP_DUE);
        assertThat(testKmMaganiCrop.getCropDueDate()).isEqualTo(DEFAULT_CROP_DUE_DATE);
        assertThat(testKmMaganiCrop.getCropVasuliPatraDate()).isEqualTo(UPDATED_CROP_VASULI_PATRA_DATE);
        assertThat(testKmMaganiCrop.getKmManjuri()).isEqualTo(DEFAULT_KM_MANJURI);
        assertThat(testKmMaganiCrop.getKmArea()).isEqualTo(UPDATED_KM_AREA);
        assertThat(testKmMaganiCrop.geteKararNumber()).isEqualTo(UPDATED_E_KARAR_NUMBER);
        assertThat(testKmMaganiCrop.geteKararAmount()).isEqualTo(UPDATED_E_KARAR_AMOUNT);
        assertThat(testKmMaganiCrop.geteKararArea()).isEqualTo(DEFAULT_E_KARAR_AREA);
        assertThat(testKmMaganiCrop.getMaganiArea()).isEqualTo(DEFAULT_MAGANI_AREA);
        assertThat(testKmMaganiCrop.getMaganiAmount()).isEqualTo(DEFAULT_MAGANI_AMOUNT);
        assertThat(testKmMaganiCrop.getMaganiShare()).isEqualTo(DEFAULT_MAGANI_SHARE);
        assertThat(testKmMaganiCrop.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateKmMaganiCropWithPatch() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();

        // Update the kmMaganiCrop using partial update
        KmMaganiCrop partialUpdatedKmMaganiCrop = new KmMaganiCrop();
        partialUpdatedKmMaganiCrop.setId(kmMaganiCrop.getId());

        partialUpdatedKmMaganiCrop
            .cropName(UPDATED_CROP_NAME)
            .cropBalance(UPDATED_CROP_BALANCE)
            .cropDue(UPDATED_CROP_DUE)
            .cropDueDate(UPDATED_CROP_DUE_DATE)
            .cropVasuliPatraDate(UPDATED_CROP_VASULI_PATRA_DATE)
            .kmManjuri(UPDATED_KM_MANJURI)
            .kmArea(UPDATED_KM_AREA)
            .eKararNumber(UPDATED_E_KARAR_NUMBER)
            .eKararAmount(UPDATED_E_KARAR_AMOUNT)
            .eKararArea(UPDATED_E_KARAR_AREA)
            .maganiArea(UPDATED_MAGANI_AREA)
            .maganiAmount(UPDATED_MAGANI_AMOUNT)
            .maganiShare(UPDATED_MAGANI_SHARE)
            .pacsNumber(UPDATED_PACS_NUMBER);

        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmMaganiCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmMaganiCrop))
            )
            .andExpect(status().isOk());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
        KmMaganiCrop testKmMaganiCrop = kmMaganiCropList.get(kmMaganiCropList.size() - 1);
        assertThat(testKmMaganiCrop.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testKmMaganiCrop.getCropBalance()).isEqualTo(UPDATED_CROP_BALANCE);
        assertThat(testKmMaganiCrop.getCropDue()).isEqualTo(UPDATED_CROP_DUE);
        assertThat(testKmMaganiCrop.getCropDueDate()).isEqualTo(UPDATED_CROP_DUE_DATE);
        assertThat(testKmMaganiCrop.getCropVasuliPatraDate()).isEqualTo(UPDATED_CROP_VASULI_PATRA_DATE);
        assertThat(testKmMaganiCrop.getKmManjuri()).isEqualTo(UPDATED_KM_MANJURI);
        assertThat(testKmMaganiCrop.getKmArea()).isEqualTo(UPDATED_KM_AREA);
        assertThat(testKmMaganiCrop.geteKararNumber()).isEqualTo(UPDATED_E_KARAR_NUMBER);
        assertThat(testKmMaganiCrop.geteKararAmount()).isEqualTo(UPDATED_E_KARAR_AMOUNT);
        assertThat(testKmMaganiCrop.geteKararArea()).isEqualTo(UPDATED_E_KARAR_AREA);
        assertThat(testKmMaganiCrop.getMaganiArea()).isEqualTo(UPDATED_MAGANI_AREA);
        assertThat(testKmMaganiCrop.getMaganiAmount()).isEqualTo(UPDATED_MAGANI_AMOUNT);
        assertThat(testKmMaganiCrop.getMaganiShare()).isEqualTo(UPDATED_MAGANI_SHARE);
        assertThat(testKmMaganiCrop.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kmMaganiCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKmMaganiCrop() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        int databaseSizeBeforeDelete = kmMaganiCropRepository.findAll().size();

        // Delete the kmMaganiCrop
        restKmMaganiCropMockMvc
            .perform(delete(ENTITY_API_URL_ID, kmMaganiCrop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
