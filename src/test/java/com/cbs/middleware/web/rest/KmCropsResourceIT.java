package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KmCrops;
import com.cbs.middleware.domain.KmDetails;
import com.cbs.middleware.repository.KmCropsRepository;
import javax.persistence.EntityManager;
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
 * Integration tests for the {@link KmCropsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KmCropsResourceIT {

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_HECTOR = 1D;
    private static final Double UPDATED_HECTOR = 2D;
    private static final Double SMALLER_HECTOR = 1D - 1D;

    private static final String DEFAULT_HECTOR_MR = "AAAAAAAAAA";
    private static final String UPDATED_HECTOR_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_ARE = 1D;
    private static final Double UPDATED_ARE = 2D;
    private static final Double SMALLER_ARE = 1D - 1D;

    private static final String DEFAULT_ARE_MR = "AAAAAAAAAA";
    private static final String UPDATED_ARE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_PRVIOUS_AMT = 1D;
    private static final Double UPDATED_PRVIOUS_AMT = 2D;
    private static final Double SMALLER_PRVIOUS_AMT = 1D - 1D;

    private static final String DEFAULT_PREVIOUS_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_AMT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DEMAND = 1D;
    private static final Double UPDATED_DEMAND = 2D;
    private static final Double SMALLER_DEMAND = 1D - 1D;

    private static final String DEFAULT_DEMAND_MR = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND_MR = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIETY = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETY = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIETY_MR = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETY_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BANK_AMT = 1D;
    private static final Double UPDATED_BANK_AMT = 2D;
    private static final Double SMALLER_BANK_AMT = 1D - 1D;

    private static final String DEFAULT_BANK_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_BANK_AMT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_NO_OF_TREE = 1D;
    private static final Double UPDATED_NO_OF_TREE = 2D;
    private static final Double SMALLER_NO_OF_TREE = 1D - 1D;

    private static final String DEFAULT_NO_OF_TREE_MR = "AAAAAAAAAA";
    private static final String UPDATED_NO_OF_TREE_MR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/km-crops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KmCropsRepository kmCropsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKmCropsMockMvc;

    private KmCrops kmCrops;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmCrops createEntity(EntityManager em) {
        KmCrops kmCrops = new KmCrops()
            .cropName(DEFAULT_CROP_NAME)
            .cropNameMr(DEFAULT_CROP_NAME_MR)
            .hector(DEFAULT_HECTOR)
            .hectorMr(DEFAULT_HECTOR_MR)
            .are(DEFAULT_ARE)
            .areMr(DEFAULT_ARE_MR)
            .prviousAmt(DEFAULT_PRVIOUS_AMT)
            .previousAmtMr(DEFAULT_PREVIOUS_AMT_MR)
            .demand(DEFAULT_DEMAND)
            .demandMr(DEFAULT_DEMAND_MR)
            .society(DEFAULT_SOCIETY)
            .societyMr(DEFAULT_SOCIETY_MR)
            .bankAmt(DEFAULT_BANK_AMT)
            .bankAmtMr(DEFAULT_BANK_AMT_MR)
            .noOfTree(DEFAULT_NO_OF_TREE)
            .noOfTreeMr(DEFAULT_NO_OF_TREE_MR);
        return kmCrops;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmCrops createUpdatedEntity(EntityManager em) {
        KmCrops kmCrops = new KmCrops()
            .cropName(UPDATED_CROP_NAME)
            .cropNameMr(UPDATED_CROP_NAME_MR)
            .hector(UPDATED_HECTOR)
            .hectorMr(UPDATED_HECTOR_MR)
            .are(UPDATED_ARE)
            .areMr(UPDATED_ARE_MR)
            .prviousAmt(UPDATED_PRVIOUS_AMT)
            .previousAmtMr(UPDATED_PREVIOUS_AMT_MR)
            .demand(UPDATED_DEMAND)
            .demandMr(UPDATED_DEMAND_MR)
            .society(UPDATED_SOCIETY)
            .societyMr(UPDATED_SOCIETY_MR)
            .bankAmt(UPDATED_BANK_AMT)
            .bankAmtMr(UPDATED_BANK_AMT_MR)
            .noOfTree(UPDATED_NO_OF_TREE)
            .noOfTreeMr(UPDATED_NO_OF_TREE_MR);
        return kmCrops;
    }

    @BeforeEach
    public void initTest() {
        kmCrops = createEntity(em);
    }

    @Test
    @Transactional
    void createKmCrops() throws Exception {
        int databaseSizeBeforeCreate = kmCropsRepository.findAll().size();
        // Create the KmCrops
        restKmCropsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmCrops)))
            .andExpect(status().isCreated());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeCreate + 1);
        KmCrops testKmCrops = kmCropsList.get(kmCropsList.size() - 1);
        assertThat(testKmCrops.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testKmCrops.getCropNameMr()).isEqualTo(DEFAULT_CROP_NAME_MR);
        assertThat(testKmCrops.getHector()).isEqualTo(DEFAULT_HECTOR);
        assertThat(testKmCrops.getHectorMr()).isEqualTo(DEFAULT_HECTOR_MR);
        assertThat(testKmCrops.getAre()).isEqualTo(DEFAULT_ARE);
        assertThat(testKmCrops.getAreMr()).isEqualTo(DEFAULT_ARE_MR);
        assertThat(testKmCrops.getPrviousAmt()).isEqualTo(DEFAULT_PRVIOUS_AMT);
        assertThat(testKmCrops.getPreviousAmtMr()).isEqualTo(DEFAULT_PREVIOUS_AMT_MR);
        assertThat(testKmCrops.getDemand()).isEqualTo(DEFAULT_DEMAND);
        assertThat(testKmCrops.getDemandMr()).isEqualTo(DEFAULT_DEMAND_MR);
        assertThat(testKmCrops.getSociety()).isEqualTo(DEFAULT_SOCIETY);
        assertThat(testKmCrops.getSocietyMr()).isEqualTo(DEFAULT_SOCIETY_MR);
        assertThat(testKmCrops.getBankAmt()).isEqualTo(DEFAULT_BANK_AMT);
        assertThat(testKmCrops.getBankAmtMr()).isEqualTo(DEFAULT_BANK_AMT_MR);
        assertThat(testKmCrops.getNoOfTree()).isEqualTo(DEFAULT_NO_OF_TREE);
        assertThat(testKmCrops.getNoOfTreeMr()).isEqualTo(DEFAULT_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void createKmCropsWithExistingId() throws Exception {
        // Create the KmCrops with an existing ID
        kmCrops.setId(1L);

        int databaseSizeBeforeCreate = kmCropsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmCropsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmCrops)))
            .andExpect(status().isBadRequest());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKmCrops() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList
        restKmCropsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmCrops.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].cropNameMr").value(hasItem(DEFAULT_CROP_NAME_MR)))
            .andExpect(jsonPath("$.[*].hector").value(hasItem(DEFAULT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].hectorMr").value(hasItem(DEFAULT_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].areMr").value(hasItem(DEFAULT_ARE_MR)))
            .andExpect(jsonPath("$.[*].prviousAmt").value(hasItem(DEFAULT_PRVIOUS_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].previousAmtMr").value(hasItem(DEFAULT_PREVIOUS_AMT_MR)))
            .andExpect(jsonPath("$.[*].demand").value(hasItem(DEFAULT_DEMAND.doubleValue())))
            .andExpect(jsonPath("$.[*].demandMr").value(hasItem(DEFAULT_DEMAND_MR)))
            .andExpect(jsonPath("$.[*].society").value(hasItem(DEFAULT_SOCIETY)))
            .andExpect(jsonPath("$.[*].societyMr").value(hasItem(DEFAULT_SOCIETY_MR)))
            .andExpect(jsonPath("$.[*].bankAmt").value(hasItem(DEFAULT_BANK_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].bankAmtMr").value(hasItem(DEFAULT_BANK_AMT_MR)))
            .andExpect(jsonPath("$.[*].noOfTree").value(hasItem(DEFAULT_NO_OF_TREE.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfTreeMr").value(hasItem(DEFAULT_NO_OF_TREE_MR)));
    }

    @Test
    @Transactional
    void getKmCrops() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get the kmCrops
        restKmCropsMockMvc
            .perform(get(ENTITY_API_URL_ID, kmCrops.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kmCrops.getId().intValue()))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME))
            .andExpect(jsonPath("$.cropNameMr").value(DEFAULT_CROP_NAME_MR))
            .andExpect(jsonPath("$.hector").value(DEFAULT_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.hectorMr").value(DEFAULT_HECTOR_MR))
            .andExpect(jsonPath("$.are").value(DEFAULT_ARE.doubleValue()))
            .andExpect(jsonPath("$.areMr").value(DEFAULT_ARE_MR))
            .andExpect(jsonPath("$.prviousAmt").value(DEFAULT_PRVIOUS_AMT.doubleValue()))
            .andExpect(jsonPath("$.previousAmtMr").value(DEFAULT_PREVIOUS_AMT_MR))
            .andExpect(jsonPath("$.demand").value(DEFAULT_DEMAND.doubleValue()))
            .andExpect(jsonPath("$.demandMr").value(DEFAULT_DEMAND_MR))
            .andExpect(jsonPath("$.society").value(DEFAULT_SOCIETY))
            .andExpect(jsonPath("$.societyMr").value(DEFAULT_SOCIETY_MR))
            .andExpect(jsonPath("$.bankAmt").value(DEFAULT_BANK_AMT.doubleValue()))
            .andExpect(jsonPath("$.bankAmtMr").value(DEFAULT_BANK_AMT_MR))
            .andExpect(jsonPath("$.noOfTree").value(DEFAULT_NO_OF_TREE.doubleValue()))
            .andExpect(jsonPath("$.noOfTreeMr").value(DEFAULT_NO_OF_TREE_MR));
    }

    @Test
    @Transactional
    void getKmCropsByIdFiltering() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        Long id = kmCrops.getId();

        defaultKmCropsShouldBeFound("id.equals=" + id);
        defaultKmCropsShouldNotBeFound("id.notEquals=" + id);

        defaultKmCropsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKmCropsShouldNotBeFound("id.greaterThan=" + id);

        defaultKmCropsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKmCropsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropName equals to DEFAULT_CROP_NAME
        defaultKmCropsShouldBeFound("cropName.equals=" + DEFAULT_CROP_NAME);

        // Get all the kmCropsList where cropName equals to UPDATED_CROP_NAME
        defaultKmCropsShouldNotBeFound("cropName.equals=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropName in DEFAULT_CROP_NAME or UPDATED_CROP_NAME
        defaultKmCropsShouldBeFound("cropName.in=" + DEFAULT_CROP_NAME + "," + UPDATED_CROP_NAME);

        // Get all the kmCropsList where cropName equals to UPDATED_CROP_NAME
        defaultKmCropsShouldNotBeFound("cropName.in=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropName is not null
        defaultKmCropsShouldBeFound("cropName.specified=true");

        // Get all the kmCropsList where cropName is null
        defaultKmCropsShouldNotBeFound("cropName.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropName contains DEFAULT_CROP_NAME
        defaultKmCropsShouldBeFound("cropName.contains=" + DEFAULT_CROP_NAME);

        // Get all the kmCropsList where cropName contains UPDATED_CROP_NAME
        defaultKmCropsShouldNotBeFound("cropName.contains=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropName does not contain DEFAULT_CROP_NAME
        defaultKmCropsShouldNotBeFound("cropName.doesNotContain=" + DEFAULT_CROP_NAME);

        // Get all the kmCropsList where cropName does not contain UPDATED_CROP_NAME
        defaultKmCropsShouldBeFound("cropName.doesNotContain=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropNameMr equals to DEFAULT_CROP_NAME_MR
        defaultKmCropsShouldBeFound("cropNameMr.equals=" + DEFAULT_CROP_NAME_MR);

        // Get all the kmCropsList where cropNameMr equals to UPDATED_CROP_NAME_MR
        defaultKmCropsShouldNotBeFound("cropNameMr.equals=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropNameMr in DEFAULT_CROP_NAME_MR or UPDATED_CROP_NAME_MR
        defaultKmCropsShouldBeFound("cropNameMr.in=" + DEFAULT_CROP_NAME_MR + "," + UPDATED_CROP_NAME_MR);

        // Get all the kmCropsList where cropNameMr equals to UPDATED_CROP_NAME_MR
        defaultKmCropsShouldNotBeFound("cropNameMr.in=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropNameMr is not null
        defaultKmCropsShouldBeFound("cropNameMr.specified=true");

        // Get all the kmCropsList where cropNameMr is null
        defaultKmCropsShouldNotBeFound("cropNameMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameMrContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropNameMr contains DEFAULT_CROP_NAME_MR
        defaultKmCropsShouldBeFound("cropNameMr.contains=" + DEFAULT_CROP_NAME_MR);

        // Get all the kmCropsList where cropNameMr contains UPDATED_CROP_NAME_MR
        defaultKmCropsShouldNotBeFound("cropNameMr.contains=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByCropNameMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropNameMr does not contain DEFAULT_CROP_NAME_MR
        defaultKmCropsShouldNotBeFound("cropNameMr.doesNotContain=" + DEFAULT_CROP_NAME_MR);

        // Get all the kmCropsList where cropNameMr does not contain UPDATED_CROP_NAME_MR
        defaultKmCropsShouldBeFound("cropNameMr.doesNotContain=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector equals to DEFAULT_HECTOR
        defaultKmCropsShouldBeFound("hector.equals=" + DEFAULT_HECTOR);

        // Get all the kmCropsList where hector equals to UPDATED_HECTOR
        defaultKmCropsShouldNotBeFound("hector.equals=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector in DEFAULT_HECTOR or UPDATED_HECTOR
        defaultKmCropsShouldBeFound("hector.in=" + DEFAULT_HECTOR + "," + UPDATED_HECTOR);

        // Get all the kmCropsList where hector equals to UPDATED_HECTOR
        defaultKmCropsShouldNotBeFound("hector.in=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector is not null
        defaultKmCropsShouldBeFound("hector.specified=true");

        // Get all the kmCropsList where hector is null
        defaultKmCropsShouldNotBeFound("hector.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector is greater than or equal to DEFAULT_HECTOR
        defaultKmCropsShouldBeFound("hector.greaterThanOrEqual=" + DEFAULT_HECTOR);

        // Get all the kmCropsList where hector is greater than or equal to UPDATED_HECTOR
        defaultKmCropsShouldNotBeFound("hector.greaterThanOrEqual=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector is less than or equal to DEFAULT_HECTOR
        defaultKmCropsShouldBeFound("hector.lessThanOrEqual=" + DEFAULT_HECTOR);

        // Get all the kmCropsList where hector is less than or equal to SMALLER_HECTOR
        defaultKmCropsShouldNotBeFound("hector.lessThanOrEqual=" + SMALLER_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector is less than DEFAULT_HECTOR
        defaultKmCropsShouldNotBeFound("hector.lessThan=" + DEFAULT_HECTOR);

        // Get all the kmCropsList where hector is less than UPDATED_HECTOR
        defaultKmCropsShouldBeFound("hector.lessThan=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector is greater than DEFAULT_HECTOR
        defaultKmCropsShouldNotBeFound("hector.greaterThan=" + DEFAULT_HECTOR);

        // Get all the kmCropsList where hector is greater than SMALLER_HECTOR
        defaultKmCropsShouldBeFound("hector.greaterThan=" + SMALLER_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hectorMr equals to DEFAULT_HECTOR_MR
        defaultKmCropsShouldBeFound("hectorMr.equals=" + DEFAULT_HECTOR_MR);

        // Get all the kmCropsList where hectorMr equals to UPDATED_HECTOR_MR
        defaultKmCropsShouldNotBeFound("hectorMr.equals=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hectorMr in DEFAULT_HECTOR_MR or UPDATED_HECTOR_MR
        defaultKmCropsShouldBeFound("hectorMr.in=" + DEFAULT_HECTOR_MR + "," + UPDATED_HECTOR_MR);

        // Get all the kmCropsList where hectorMr equals to UPDATED_HECTOR_MR
        defaultKmCropsShouldNotBeFound("hectorMr.in=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hectorMr is not null
        defaultKmCropsShouldBeFound("hectorMr.specified=true");

        // Get all the kmCropsList where hectorMr is null
        defaultKmCropsShouldNotBeFound("hectorMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorMrContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hectorMr contains DEFAULT_HECTOR_MR
        defaultKmCropsShouldBeFound("hectorMr.contains=" + DEFAULT_HECTOR_MR);

        // Get all the kmCropsList where hectorMr contains UPDATED_HECTOR_MR
        defaultKmCropsShouldNotBeFound("hectorMr.contains=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByHectorMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hectorMr does not contain DEFAULT_HECTOR_MR
        defaultKmCropsShouldNotBeFound("hectorMr.doesNotContain=" + DEFAULT_HECTOR_MR);

        // Get all the kmCropsList where hectorMr does not contain UPDATED_HECTOR_MR
        defaultKmCropsShouldBeFound("hectorMr.doesNotContain=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are equals to DEFAULT_ARE
        defaultKmCropsShouldBeFound("are.equals=" + DEFAULT_ARE);

        // Get all the kmCropsList where are equals to UPDATED_ARE
        defaultKmCropsShouldNotBeFound("are.equals=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are in DEFAULT_ARE or UPDATED_ARE
        defaultKmCropsShouldBeFound("are.in=" + DEFAULT_ARE + "," + UPDATED_ARE);

        // Get all the kmCropsList where are equals to UPDATED_ARE
        defaultKmCropsShouldNotBeFound("are.in=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are is not null
        defaultKmCropsShouldBeFound("are.specified=true");

        // Get all the kmCropsList where are is null
        defaultKmCropsShouldNotBeFound("are.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByAreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are is greater than or equal to DEFAULT_ARE
        defaultKmCropsShouldBeFound("are.greaterThanOrEqual=" + DEFAULT_ARE);

        // Get all the kmCropsList where are is greater than or equal to UPDATED_ARE
        defaultKmCropsShouldNotBeFound("are.greaterThanOrEqual=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are is less than or equal to DEFAULT_ARE
        defaultKmCropsShouldBeFound("are.lessThanOrEqual=" + DEFAULT_ARE);

        // Get all the kmCropsList where are is less than or equal to SMALLER_ARE
        defaultKmCropsShouldNotBeFound("are.lessThanOrEqual=" + SMALLER_ARE);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreIsLessThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are is less than DEFAULT_ARE
        defaultKmCropsShouldNotBeFound("are.lessThan=" + DEFAULT_ARE);

        // Get all the kmCropsList where are is less than UPDATED_ARE
        defaultKmCropsShouldBeFound("are.lessThan=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are is greater than DEFAULT_ARE
        defaultKmCropsShouldNotBeFound("are.greaterThan=" + DEFAULT_ARE);

        // Get all the kmCropsList where are is greater than SMALLER_ARE
        defaultKmCropsShouldBeFound("are.greaterThan=" + SMALLER_ARE);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where areMr equals to DEFAULT_ARE_MR
        defaultKmCropsShouldBeFound("areMr.equals=" + DEFAULT_ARE_MR);

        // Get all the kmCropsList where areMr equals to UPDATED_ARE_MR
        defaultKmCropsShouldNotBeFound("areMr.equals=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where areMr in DEFAULT_ARE_MR or UPDATED_ARE_MR
        defaultKmCropsShouldBeFound("areMr.in=" + DEFAULT_ARE_MR + "," + UPDATED_ARE_MR);

        // Get all the kmCropsList where areMr equals to UPDATED_ARE_MR
        defaultKmCropsShouldNotBeFound("areMr.in=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where areMr is not null
        defaultKmCropsShouldBeFound("areMr.specified=true");

        // Get all the kmCropsList where areMr is null
        defaultKmCropsShouldNotBeFound("areMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByAreMrContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where areMr contains DEFAULT_ARE_MR
        defaultKmCropsShouldBeFound("areMr.contains=" + DEFAULT_ARE_MR);

        // Get all the kmCropsList where areMr contains UPDATED_ARE_MR
        defaultKmCropsShouldNotBeFound("areMr.contains=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByAreMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where areMr does not contain DEFAULT_ARE_MR
        defaultKmCropsShouldNotBeFound("areMr.doesNotContain=" + DEFAULT_ARE_MR);

        // Get all the kmCropsList where areMr does not contain UPDATED_ARE_MR
        defaultKmCropsShouldBeFound("areMr.doesNotContain=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByPrviousAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt equals to DEFAULT_PRVIOUS_AMT
        defaultKmCropsShouldBeFound("prviousAmt.equals=" + DEFAULT_PRVIOUS_AMT);

        // Get all the kmCropsList where prviousAmt equals to UPDATED_PRVIOUS_AMT
        defaultKmCropsShouldNotBeFound("prviousAmt.equals=" + UPDATED_PRVIOUS_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByPrviousAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt in DEFAULT_PRVIOUS_AMT or UPDATED_PRVIOUS_AMT
        defaultKmCropsShouldBeFound("prviousAmt.in=" + DEFAULT_PRVIOUS_AMT + "," + UPDATED_PRVIOUS_AMT);

        // Get all the kmCropsList where prviousAmt equals to UPDATED_PRVIOUS_AMT
        defaultKmCropsShouldNotBeFound("prviousAmt.in=" + UPDATED_PRVIOUS_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByPrviousAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt is not null
        defaultKmCropsShouldBeFound("prviousAmt.specified=true");

        // Get all the kmCropsList where prviousAmt is null
        defaultKmCropsShouldNotBeFound("prviousAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByPrviousAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt is greater than or equal to DEFAULT_PRVIOUS_AMT
        defaultKmCropsShouldBeFound("prviousAmt.greaterThanOrEqual=" + DEFAULT_PRVIOUS_AMT);

        // Get all the kmCropsList where prviousAmt is greater than or equal to UPDATED_PRVIOUS_AMT
        defaultKmCropsShouldNotBeFound("prviousAmt.greaterThanOrEqual=" + UPDATED_PRVIOUS_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByPrviousAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt is less than or equal to DEFAULT_PRVIOUS_AMT
        defaultKmCropsShouldBeFound("prviousAmt.lessThanOrEqual=" + DEFAULT_PRVIOUS_AMT);

        // Get all the kmCropsList where prviousAmt is less than or equal to SMALLER_PRVIOUS_AMT
        defaultKmCropsShouldNotBeFound("prviousAmt.lessThanOrEqual=" + SMALLER_PRVIOUS_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByPrviousAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt is less than DEFAULT_PRVIOUS_AMT
        defaultKmCropsShouldNotBeFound("prviousAmt.lessThan=" + DEFAULT_PRVIOUS_AMT);

        // Get all the kmCropsList where prviousAmt is less than UPDATED_PRVIOUS_AMT
        defaultKmCropsShouldBeFound("prviousAmt.lessThan=" + UPDATED_PRVIOUS_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByPrviousAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt is greater than DEFAULT_PRVIOUS_AMT
        defaultKmCropsShouldNotBeFound("prviousAmt.greaterThan=" + DEFAULT_PRVIOUS_AMT);

        // Get all the kmCropsList where prviousAmt is greater than SMALLER_PRVIOUS_AMT
        defaultKmCropsShouldBeFound("prviousAmt.greaterThan=" + SMALLER_PRVIOUS_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByPreviousAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where previousAmtMr equals to DEFAULT_PREVIOUS_AMT_MR
        defaultKmCropsShouldBeFound("previousAmtMr.equals=" + DEFAULT_PREVIOUS_AMT_MR);

        // Get all the kmCropsList where previousAmtMr equals to UPDATED_PREVIOUS_AMT_MR
        defaultKmCropsShouldNotBeFound("previousAmtMr.equals=" + UPDATED_PREVIOUS_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByPreviousAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where previousAmtMr in DEFAULT_PREVIOUS_AMT_MR or UPDATED_PREVIOUS_AMT_MR
        defaultKmCropsShouldBeFound("previousAmtMr.in=" + DEFAULT_PREVIOUS_AMT_MR + "," + UPDATED_PREVIOUS_AMT_MR);

        // Get all the kmCropsList where previousAmtMr equals to UPDATED_PREVIOUS_AMT_MR
        defaultKmCropsShouldNotBeFound("previousAmtMr.in=" + UPDATED_PREVIOUS_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByPreviousAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where previousAmtMr is not null
        defaultKmCropsShouldBeFound("previousAmtMr.specified=true");

        // Get all the kmCropsList where previousAmtMr is null
        defaultKmCropsShouldNotBeFound("previousAmtMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByPreviousAmtMrContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where previousAmtMr contains DEFAULT_PREVIOUS_AMT_MR
        defaultKmCropsShouldBeFound("previousAmtMr.contains=" + DEFAULT_PREVIOUS_AMT_MR);

        // Get all the kmCropsList where previousAmtMr contains UPDATED_PREVIOUS_AMT_MR
        defaultKmCropsShouldNotBeFound("previousAmtMr.contains=" + UPDATED_PREVIOUS_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByPreviousAmtMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where previousAmtMr does not contain DEFAULT_PREVIOUS_AMT_MR
        defaultKmCropsShouldNotBeFound("previousAmtMr.doesNotContain=" + DEFAULT_PREVIOUS_AMT_MR);

        // Get all the kmCropsList where previousAmtMr does not contain UPDATED_PREVIOUS_AMT_MR
        defaultKmCropsShouldBeFound("previousAmtMr.doesNotContain=" + UPDATED_PREVIOUS_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand equals to DEFAULT_DEMAND
        defaultKmCropsShouldBeFound("demand.equals=" + DEFAULT_DEMAND);

        // Get all the kmCropsList where demand equals to UPDATED_DEMAND
        defaultKmCropsShouldNotBeFound("demand.equals=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand in DEFAULT_DEMAND or UPDATED_DEMAND
        defaultKmCropsShouldBeFound("demand.in=" + DEFAULT_DEMAND + "," + UPDATED_DEMAND);

        // Get all the kmCropsList where demand equals to UPDATED_DEMAND
        defaultKmCropsShouldNotBeFound("demand.in=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand is not null
        defaultKmCropsShouldBeFound("demand.specified=true");

        // Get all the kmCropsList where demand is null
        defaultKmCropsShouldNotBeFound("demand.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand is greater than or equal to DEFAULT_DEMAND
        defaultKmCropsShouldBeFound("demand.greaterThanOrEqual=" + DEFAULT_DEMAND);

        // Get all the kmCropsList where demand is greater than or equal to UPDATED_DEMAND
        defaultKmCropsShouldNotBeFound("demand.greaterThanOrEqual=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand is less than or equal to DEFAULT_DEMAND
        defaultKmCropsShouldBeFound("demand.lessThanOrEqual=" + DEFAULT_DEMAND);

        // Get all the kmCropsList where demand is less than or equal to SMALLER_DEMAND
        defaultKmCropsShouldNotBeFound("demand.lessThanOrEqual=" + SMALLER_DEMAND);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandIsLessThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand is less than DEFAULT_DEMAND
        defaultKmCropsShouldNotBeFound("demand.lessThan=" + DEFAULT_DEMAND);

        // Get all the kmCropsList where demand is less than UPDATED_DEMAND
        defaultKmCropsShouldBeFound("demand.lessThan=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand is greater than DEFAULT_DEMAND
        defaultKmCropsShouldNotBeFound("demand.greaterThan=" + DEFAULT_DEMAND);

        // Get all the kmCropsList where demand is greater than SMALLER_DEMAND
        defaultKmCropsShouldBeFound("demand.greaterThan=" + SMALLER_DEMAND);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demandMr equals to DEFAULT_DEMAND_MR
        defaultKmCropsShouldBeFound("demandMr.equals=" + DEFAULT_DEMAND_MR);

        // Get all the kmCropsList where demandMr equals to UPDATED_DEMAND_MR
        defaultKmCropsShouldNotBeFound("demandMr.equals=" + UPDATED_DEMAND_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demandMr in DEFAULT_DEMAND_MR or UPDATED_DEMAND_MR
        defaultKmCropsShouldBeFound("demandMr.in=" + DEFAULT_DEMAND_MR + "," + UPDATED_DEMAND_MR);

        // Get all the kmCropsList where demandMr equals to UPDATED_DEMAND_MR
        defaultKmCropsShouldNotBeFound("demandMr.in=" + UPDATED_DEMAND_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demandMr is not null
        defaultKmCropsShouldBeFound("demandMr.specified=true");

        // Get all the kmCropsList where demandMr is null
        defaultKmCropsShouldNotBeFound("demandMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandMrContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demandMr contains DEFAULT_DEMAND_MR
        defaultKmCropsShouldBeFound("demandMr.contains=" + DEFAULT_DEMAND_MR);

        // Get all the kmCropsList where demandMr contains UPDATED_DEMAND_MR
        defaultKmCropsShouldNotBeFound("demandMr.contains=" + UPDATED_DEMAND_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByDemandMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demandMr does not contain DEFAULT_DEMAND_MR
        defaultKmCropsShouldNotBeFound("demandMr.doesNotContain=" + DEFAULT_DEMAND_MR);

        // Get all the kmCropsList where demandMr does not contain UPDATED_DEMAND_MR
        defaultKmCropsShouldBeFound("demandMr.doesNotContain=" + UPDATED_DEMAND_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where society equals to DEFAULT_SOCIETY
        defaultKmCropsShouldBeFound("society.equals=" + DEFAULT_SOCIETY);

        // Get all the kmCropsList where society equals to UPDATED_SOCIETY
        defaultKmCropsShouldNotBeFound("society.equals=" + UPDATED_SOCIETY);
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where society in DEFAULT_SOCIETY or UPDATED_SOCIETY
        defaultKmCropsShouldBeFound("society.in=" + DEFAULT_SOCIETY + "," + UPDATED_SOCIETY);

        // Get all the kmCropsList where society equals to UPDATED_SOCIETY
        defaultKmCropsShouldNotBeFound("society.in=" + UPDATED_SOCIETY);
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where society is not null
        defaultKmCropsShouldBeFound("society.specified=true");

        // Get all the kmCropsList where society is null
        defaultKmCropsShouldNotBeFound("society.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where society contains DEFAULT_SOCIETY
        defaultKmCropsShouldBeFound("society.contains=" + DEFAULT_SOCIETY);

        // Get all the kmCropsList where society contains UPDATED_SOCIETY
        defaultKmCropsShouldNotBeFound("society.contains=" + UPDATED_SOCIETY);
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where society does not contain DEFAULT_SOCIETY
        defaultKmCropsShouldNotBeFound("society.doesNotContain=" + DEFAULT_SOCIETY);

        // Get all the kmCropsList where society does not contain UPDATED_SOCIETY
        defaultKmCropsShouldBeFound("society.doesNotContain=" + UPDATED_SOCIETY);
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where societyMr equals to DEFAULT_SOCIETY_MR
        defaultKmCropsShouldBeFound("societyMr.equals=" + DEFAULT_SOCIETY_MR);

        // Get all the kmCropsList where societyMr equals to UPDATED_SOCIETY_MR
        defaultKmCropsShouldNotBeFound("societyMr.equals=" + UPDATED_SOCIETY_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where societyMr in DEFAULT_SOCIETY_MR or UPDATED_SOCIETY_MR
        defaultKmCropsShouldBeFound("societyMr.in=" + DEFAULT_SOCIETY_MR + "," + UPDATED_SOCIETY_MR);

        // Get all the kmCropsList where societyMr equals to UPDATED_SOCIETY_MR
        defaultKmCropsShouldNotBeFound("societyMr.in=" + UPDATED_SOCIETY_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where societyMr is not null
        defaultKmCropsShouldBeFound("societyMr.specified=true");

        // Get all the kmCropsList where societyMr is null
        defaultKmCropsShouldNotBeFound("societyMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyMrContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where societyMr contains DEFAULT_SOCIETY_MR
        defaultKmCropsShouldBeFound("societyMr.contains=" + DEFAULT_SOCIETY_MR);

        // Get all the kmCropsList where societyMr contains UPDATED_SOCIETY_MR
        defaultKmCropsShouldNotBeFound("societyMr.contains=" + UPDATED_SOCIETY_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsBySocietyMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where societyMr does not contain DEFAULT_SOCIETY_MR
        defaultKmCropsShouldNotBeFound("societyMr.doesNotContain=" + DEFAULT_SOCIETY_MR);

        // Get all the kmCropsList where societyMr does not contain UPDATED_SOCIETY_MR
        defaultKmCropsShouldBeFound("societyMr.doesNotContain=" + UPDATED_SOCIETY_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt equals to DEFAULT_BANK_AMT
        defaultKmCropsShouldBeFound("bankAmt.equals=" + DEFAULT_BANK_AMT);

        // Get all the kmCropsList where bankAmt equals to UPDATED_BANK_AMT
        defaultKmCropsShouldNotBeFound("bankAmt.equals=" + UPDATED_BANK_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt in DEFAULT_BANK_AMT or UPDATED_BANK_AMT
        defaultKmCropsShouldBeFound("bankAmt.in=" + DEFAULT_BANK_AMT + "," + UPDATED_BANK_AMT);

        // Get all the kmCropsList where bankAmt equals to UPDATED_BANK_AMT
        defaultKmCropsShouldNotBeFound("bankAmt.in=" + UPDATED_BANK_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt is not null
        defaultKmCropsShouldBeFound("bankAmt.specified=true");

        // Get all the kmCropsList where bankAmt is null
        defaultKmCropsShouldNotBeFound("bankAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt is greater than or equal to DEFAULT_BANK_AMT
        defaultKmCropsShouldBeFound("bankAmt.greaterThanOrEqual=" + DEFAULT_BANK_AMT);

        // Get all the kmCropsList where bankAmt is greater than or equal to UPDATED_BANK_AMT
        defaultKmCropsShouldNotBeFound("bankAmt.greaterThanOrEqual=" + UPDATED_BANK_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt is less than or equal to DEFAULT_BANK_AMT
        defaultKmCropsShouldBeFound("bankAmt.lessThanOrEqual=" + DEFAULT_BANK_AMT);

        // Get all the kmCropsList where bankAmt is less than or equal to SMALLER_BANK_AMT
        defaultKmCropsShouldNotBeFound("bankAmt.lessThanOrEqual=" + SMALLER_BANK_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt is less than DEFAULT_BANK_AMT
        defaultKmCropsShouldNotBeFound("bankAmt.lessThan=" + DEFAULT_BANK_AMT);

        // Get all the kmCropsList where bankAmt is less than UPDATED_BANK_AMT
        defaultKmCropsShouldBeFound("bankAmt.lessThan=" + UPDATED_BANK_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt is greater than DEFAULT_BANK_AMT
        defaultKmCropsShouldNotBeFound("bankAmt.greaterThan=" + DEFAULT_BANK_AMT);

        // Get all the kmCropsList where bankAmt is greater than SMALLER_BANK_AMT
        defaultKmCropsShouldBeFound("bankAmt.greaterThan=" + SMALLER_BANK_AMT);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmtMr equals to DEFAULT_BANK_AMT_MR
        defaultKmCropsShouldBeFound("bankAmtMr.equals=" + DEFAULT_BANK_AMT_MR);

        // Get all the kmCropsList where bankAmtMr equals to UPDATED_BANK_AMT_MR
        defaultKmCropsShouldNotBeFound("bankAmtMr.equals=" + UPDATED_BANK_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmtMr in DEFAULT_BANK_AMT_MR or UPDATED_BANK_AMT_MR
        defaultKmCropsShouldBeFound("bankAmtMr.in=" + DEFAULT_BANK_AMT_MR + "," + UPDATED_BANK_AMT_MR);

        // Get all the kmCropsList where bankAmtMr equals to UPDATED_BANK_AMT_MR
        defaultKmCropsShouldNotBeFound("bankAmtMr.in=" + UPDATED_BANK_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmtMr is not null
        defaultKmCropsShouldBeFound("bankAmtMr.specified=true");

        // Get all the kmCropsList where bankAmtMr is null
        defaultKmCropsShouldNotBeFound("bankAmtMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtMrContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmtMr contains DEFAULT_BANK_AMT_MR
        defaultKmCropsShouldBeFound("bankAmtMr.contains=" + DEFAULT_BANK_AMT_MR);

        // Get all the kmCropsList where bankAmtMr contains UPDATED_BANK_AMT_MR
        defaultKmCropsShouldNotBeFound("bankAmtMr.contains=" + UPDATED_BANK_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByBankAmtMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmtMr does not contain DEFAULT_BANK_AMT_MR
        defaultKmCropsShouldNotBeFound("bankAmtMr.doesNotContain=" + DEFAULT_BANK_AMT_MR);

        // Get all the kmCropsList where bankAmtMr does not contain UPDATED_BANK_AMT_MR
        defaultKmCropsShouldBeFound("bankAmtMr.doesNotContain=" + UPDATED_BANK_AMT_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree equals to DEFAULT_NO_OF_TREE
        defaultKmCropsShouldBeFound("noOfTree.equals=" + DEFAULT_NO_OF_TREE);

        // Get all the kmCropsList where noOfTree equals to UPDATED_NO_OF_TREE
        defaultKmCropsShouldNotBeFound("noOfTree.equals=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree in DEFAULT_NO_OF_TREE or UPDATED_NO_OF_TREE
        defaultKmCropsShouldBeFound("noOfTree.in=" + DEFAULT_NO_OF_TREE + "," + UPDATED_NO_OF_TREE);

        // Get all the kmCropsList where noOfTree equals to UPDATED_NO_OF_TREE
        defaultKmCropsShouldNotBeFound("noOfTree.in=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree is not null
        defaultKmCropsShouldBeFound("noOfTree.specified=true");

        // Get all the kmCropsList where noOfTree is null
        defaultKmCropsShouldNotBeFound("noOfTree.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree is greater than or equal to DEFAULT_NO_OF_TREE
        defaultKmCropsShouldBeFound("noOfTree.greaterThanOrEqual=" + DEFAULT_NO_OF_TREE);

        // Get all the kmCropsList where noOfTree is greater than or equal to UPDATED_NO_OF_TREE
        defaultKmCropsShouldNotBeFound("noOfTree.greaterThanOrEqual=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree is less than or equal to DEFAULT_NO_OF_TREE
        defaultKmCropsShouldBeFound("noOfTree.lessThanOrEqual=" + DEFAULT_NO_OF_TREE);

        // Get all the kmCropsList where noOfTree is less than or equal to SMALLER_NO_OF_TREE
        defaultKmCropsShouldNotBeFound("noOfTree.lessThanOrEqual=" + SMALLER_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeIsLessThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree is less than DEFAULT_NO_OF_TREE
        defaultKmCropsShouldNotBeFound("noOfTree.lessThan=" + DEFAULT_NO_OF_TREE);

        // Get all the kmCropsList where noOfTree is less than UPDATED_NO_OF_TREE
        defaultKmCropsShouldBeFound("noOfTree.lessThan=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree is greater than DEFAULT_NO_OF_TREE
        defaultKmCropsShouldNotBeFound("noOfTree.greaterThan=" + DEFAULT_NO_OF_TREE);

        // Get all the kmCropsList where noOfTree is greater than SMALLER_NO_OF_TREE
        defaultKmCropsShouldBeFound("noOfTree.greaterThan=" + SMALLER_NO_OF_TREE);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTreeMr equals to DEFAULT_NO_OF_TREE_MR
        defaultKmCropsShouldBeFound("noOfTreeMr.equals=" + DEFAULT_NO_OF_TREE_MR);

        // Get all the kmCropsList where noOfTreeMr equals to UPDATED_NO_OF_TREE_MR
        defaultKmCropsShouldNotBeFound("noOfTreeMr.equals=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTreeMr in DEFAULT_NO_OF_TREE_MR or UPDATED_NO_OF_TREE_MR
        defaultKmCropsShouldBeFound("noOfTreeMr.in=" + DEFAULT_NO_OF_TREE_MR + "," + UPDATED_NO_OF_TREE_MR);

        // Get all the kmCropsList where noOfTreeMr equals to UPDATED_NO_OF_TREE_MR
        defaultKmCropsShouldNotBeFound("noOfTreeMr.in=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTreeMr is not null
        defaultKmCropsShouldBeFound("noOfTreeMr.specified=true");

        // Get all the kmCropsList where noOfTreeMr is null
        defaultKmCropsShouldNotBeFound("noOfTreeMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeMrContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTreeMr contains DEFAULT_NO_OF_TREE_MR
        defaultKmCropsShouldBeFound("noOfTreeMr.contains=" + DEFAULT_NO_OF_TREE_MR);

        // Get all the kmCropsList where noOfTreeMr contains UPDATED_NO_OF_TREE_MR
        defaultKmCropsShouldNotBeFound("noOfTreeMr.contains=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByNoOfTreeMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTreeMr does not contain DEFAULT_NO_OF_TREE_MR
        defaultKmCropsShouldNotBeFound("noOfTreeMr.doesNotContain=" + DEFAULT_NO_OF_TREE_MR);

        // Get all the kmCropsList where noOfTreeMr does not contain UPDATED_NO_OF_TREE_MR
        defaultKmCropsShouldBeFound("noOfTreeMr.doesNotContain=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void getAllKmCropsByKmDetailsIsEqualToSomething() throws Exception {
        KmDetails kmDetails;
        if (TestUtil.findAll(em, KmDetails.class).isEmpty()) {
            kmCropsRepository.saveAndFlush(kmCrops);
            kmDetails = KmDetailsResourceIT.createEntity(em);
        } else {
            kmDetails = TestUtil.findAll(em, KmDetails.class).get(0);
        }
        em.persist(kmDetails);
        em.flush();
        kmCrops.setKmDetails(kmDetails);
        kmCropsRepository.saveAndFlush(kmCrops);
        Long kmDetailsId = kmDetails.getId();
        // Get all the kmCropsList where kmDetails equals to kmDetailsId
        defaultKmCropsShouldBeFound("kmDetailsId.equals=" + kmDetailsId);

        // Get all the kmCropsList where kmDetails equals to (kmDetailsId + 1)
        defaultKmCropsShouldNotBeFound("kmDetailsId.equals=" + (kmDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKmCropsShouldBeFound(String filter) throws Exception {
        restKmCropsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmCrops.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].cropNameMr").value(hasItem(DEFAULT_CROP_NAME_MR)))
            .andExpect(jsonPath("$.[*].hector").value(hasItem(DEFAULT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].hectorMr").value(hasItem(DEFAULT_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].areMr").value(hasItem(DEFAULT_ARE_MR)))
            .andExpect(jsonPath("$.[*].prviousAmt").value(hasItem(DEFAULT_PRVIOUS_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].previousAmtMr").value(hasItem(DEFAULT_PREVIOUS_AMT_MR)))
            .andExpect(jsonPath("$.[*].demand").value(hasItem(DEFAULT_DEMAND.doubleValue())))
            .andExpect(jsonPath("$.[*].demandMr").value(hasItem(DEFAULT_DEMAND_MR)))
            .andExpect(jsonPath("$.[*].society").value(hasItem(DEFAULT_SOCIETY)))
            .andExpect(jsonPath("$.[*].societyMr").value(hasItem(DEFAULT_SOCIETY_MR)))
            .andExpect(jsonPath("$.[*].bankAmt").value(hasItem(DEFAULT_BANK_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].bankAmtMr").value(hasItem(DEFAULT_BANK_AMT_MR)))
            .andExpect(jsonPath("$.[*].noOfTree").value(hasItem(DEFAULT_NO_OF_TREE.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfTreeMr").value(hasItem(DEFAULT_NO_OF_TREE_MR)));

        // Check, that the count call also returns 1
        restKmCropsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKmCropsShouldNotBeFound(String filter) throws Exception {
        restKmCropsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKmCropsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKmCrops() throws Exception {
        // Get the kmCrops
        restKmCropsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKmCrops() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();

        // Update the kmCrops
        KmCrops updatedKmCrops = kmCropsRepository.findById(kmCrops.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKmCrops are not directly saved in db
        em.detach(updatedKmCrops);
        updatedKmCrops
            .cropName(UPDATED_CROP_NAME)
            .cropNameMr(UPDATED_CROP_NAME_MR)
            .hector(UPDATED_HECTOR)
            .hectorMr(UPDATED_HECTOR_MR)
            .are(UPDATED_ARE)
            .areMr(UPDATED_ARE_MR)
            .prviousAmt(UPDATED_PRVIOUS_AMT)
            .previousAmtMr(UPDATED_PREVIOUS_AMT_MR)
            .demand(UPDATED_DEMAND)
            .demandMr(UPDATED_DEMAND_MR)
            .society(UPDATED_SOCIETY)
            .societyMr(UPDATED_SOCIETY_MR)
            .bankAmt(UPDATED_BANK_AMT)
            .bankAmtMr(UPDATED_BANK_AMT_MR)
            .noOfTree(UPDATED_NO_OF_TREE)
            .noOfTreeMr(UPDATED_NO_OF_TREE_MR);

        restKmCropsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKmCrops.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKmCrops))
            )
            .andExpect(status().isOk());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate);
        KmCrops testKmCrops = kmCropsList.get(kmCropsList.size() - 1);
        assertThat(testKmCrops.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testKmCrops.getCropNameMr()).isEqualTo(UPDATED_CROP_NAME_MR);
        assertThat(testKmCrops.getHector()).isEqualTo(UPDATED_HECTOR);
        assertThat(testKmCrops.getHectorMr()).isEqualTo(UPDATED_HECTOR_MR);
        assertThat(testKmCrops.getAre()).isEqualTo(UPDATED_ARE);
        assertThat(testKmCrops.getAreMr()).isEqualTo(UPDATED_ARE_MR);
        assertThat(testKmCrops.getPrviousAmt()).isEqualTo(UPDATED_PRVIOUS_AMT);
        assertThat(testKmCrops.getPreviousAmtMr()).isEqualTo(UPDATED_PREVIOUS_AMT_MR);
        assertThat(testKmCrops.getDemand()).isEqualTo(UPDATED_DEMAND);
        assertThat(testKmCrops.getDemandMr()).isEqualTo(UPDATED_DEMAND_MR);
        assertThat(testKmCrops.getSociety()).isEqualTo(UPDATED_SOCIETY);
        assertThat(testKmCrops.getSocietyMr()).isEqualTo(UPDATED_SOCIETY_MR);
        assertThat(testKmCrops.getBankAmt()).isEqualTo(UPDATED_BANK_AMT);
        assertThat(testKmCrops.getBankAmtMr()).isEqualTo(UPDATED_BANK_AMT_MR);
        assertThat(testKmCrops.getNoOfTree()).isEqualTo(UPDATED_NO_OF_TREE);
        assertThat(testKmCrops.getNoOfTreeMr()).isEqualTo(UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void putNonExistingKmCrops() throws Exception {
        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();
        kmCrops.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmCropsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kmCrops.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmCrops))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKmCrops() throws Exception {
        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();
        kmCrops.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmCropsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmCrops))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKmCrops() throws Exception {
        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();
        kmCrops.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmCropsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmCrops)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKmCropsWithPatch() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();

        // Update the kmCrops using partial update
        KmCrops partialUpdatedKmCrops = new KmCrops();
        partialUpdatedKmCrops.setId(kmCrops.getId());

        partialUpdatedKmCrops
            .cropName(UPDATED_CROP_NAME)
            .cropNameMr(UPDATED_CROP_NAME_MR)
            .hector(UPDATED_HECTOR)
            .prviousAmt(UPDATED_PRVIOUS_AMT)
            .previousAmtMr(UPDATED_PREVIOUS_AMT_MR)
            .demand(UPDATED_DEMAND)
            .demandMr(UPDATED_DEMAND_MR)
            .society(UPDATED_SOCIETY)
            .societyMr(UPDATED_SOCIETY_MR)
            .bankAmt(UPDATED_BANK_AMT)
            .bankAmtMr(UPDATED_BANK_AMT_MR)
            .noOfTree(UPDATED_NO_OF_TREE)
            .noOfTreeMr(UPDATED_NO_OF_TREE_MR);

        restKmCropsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmCrops.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmCrops))
            )
            .andExpect(status().isOk());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate);
        KmCrops testKmCrops = kmCropsList.get(kmCropsList.size() - 1);
        assertThat(testKmCrops.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testKmCrops.getCropNameMr()).isEqualTo(UPDATED_CROP_NAME_MR);
        assertThat(testKmCrops.getHector()).isEqualTo(UPDATED_HECTOR);
        assertThat(testKmCrops.getHectorMr()).isEqualTo(DEFAULT_HECTOR_MR);
        assertThat(testKmCrops.getAre()).isEqualTo(DEFAULT_ARE);
        assertThat(testKmCrops.getAreMr()).isEqualTo(DEFAULT_ARE_MR);
        assertThat(testKmCrops.getPrviousAmt()).isEqualTo(UPDATED_PRVIOUS_AMT);
        assertThat(testKmCrops.getPreviousAmtMr()).isEqualTo(UPDATED_PREVIOUS_AMT_MR);
        assertThat(testKmCrops.getDemand()).isEqualTo(UPDATED_DEMAND);
        assertThat(testKmCrops.getDemandMr()).isEqualTo(UPDATED_DEMAND_MR);
        assertThat(testKmCrops.getSociety()).isEqualTo(UPDATED_SOCIETY);
        assertThat(testKmCrops.getSocietyMr()).isEqualTo(UPDATED_SOCIETY_MR);
        assertThat(testKmCrops.getBankAmt()).isEqualTo(UPDATED_BANK_AMT);
        assertThat(testKmCrops.getBankAmtMr()).isEqualTo(UPDATED_BANK_AMT_MR);
        assertThat(testKmCrops.getNoOfTree()).isEqualTo(UPDATED_NO_OF_TREE);
        assertThat(testKmCrops.getNoOfTreeMr()).isEqualTo(UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void fullUpdateKmCropsWithPatch() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();

        // Update the kmCrops using partial update
        KmCrops partialUpdatedKmCrops = new KmCrops();
        partialUpdatedKmCrops.setId(kmCrops.getId());

        partialUpdatedKmCrops
            .cropName(UPDATED_CROP_NAME)
            .cropNameMr(UPDATED_CROP_NAME_MR)
            .hector(UPDATED_HECTOR)
            .hectorMr(UPDATED_HECTOR_MR)
            .are(UPDATED_ARE)
            .areMr(UPDATED_ARE_MR)
            .prviousAmt(UPDATED_PRVIOUS_AMT)
            .previousAmtMr(UPDATED_PREVIOUS_AMT_MR)
            .demand(UPDATED_DEMAND)
            .demandMr(UPDATED_DEMAND_MR)
            .society(UPDATED_SOCIETY)
            .societyMr(UPDATED_SOCIETY_MR)
            .bankAmt(UPDATED_BANK_AMT)
            .bankAmtMr(UPDATED_BANK_AMT_MR)
            .noOfTree(UPDATED_NO_OF_TREE)
            .noOfTreeMr(UPDATED_NO_OF_TREE_MR);

        restKmCropsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmCrops.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmCrops))
            )
            .andExpect(status().isOk());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate);
        KmCrops testKmCrops = kmCropsList.get(kmCropsList.size() - 1);
        assertThat(testKmCrops.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testKmCrops.getCropNameMr()).isEqualTo(UPDATED_CROP_NAME_MR);
        assertThat(testKmCrops.getHector()).isEqualTo(UPDATED_HECTOR);
        assertThat(testKmCrops.getHectorMr()).isEqualTo(UPDATED_HECTOR_MR);
        assertThat(testKmCrops.getAre()).isEqualTo(UPDATED_ARE);
        assertThat(testKmCrops.getAreMr()).isEqualTo(UPDATED_ARE_MR);
        assertThat(testKmCrops.getPrviousAmt()).isEqualTo(UPDATED_PRVIOUS_AMT);
        assertThat(testKmCrops.getPreviousAmtMr()).isEqualTo(UPDATED_PREVIOUS_AMT_MR);
        assertThat(testKmCrops.getDemand()).isEqualTo(UPDATED_DEMAND);
        assertThat(testKmCrops.getDemandMr()).isEqualTo(UPDATED_DEMAND_MR);
        assertThat(testKmCrops.getSociety()).isEqualTo(UPDATED_SOCIETY);
        assertThat(testKmCrops.getSocietyMr()).isEqualTo(UPDATED_SOCIETY_MR);
        assertThat(testKmCrops.getBankAmt()).isEqualTo(UPDATED_BANK_AMT);
        assertThat(testKmCrops.getBankAmtMr()).isEqualTo(UPDATED_BANK_AMT_MR);
        assertThat(testKmCrops.getNoOfTree()).isEqualTo(UPDATED_NO_OF_TREE);
        assertThat(testKmCrops.getNoOfTreeMr()).isEqualTo(UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    void patchNonExistingKmCrops() throws Exception {
        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();
        kmCrops.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmCropsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kmCrops.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmCrops))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKmCrops() throws Exception {
        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();
        kmCrops.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmCropsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmCrops))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKmCrops() throws Exception {
        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();
        kmCrops.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmCropsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kmCrops)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKmCrops() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        int databaseSizeBeforeDelete = kmCropsRepository.findAll().size();

        // Delete the kmCrops
        restKmCropsMockMvc
            .perform(delete(ENTITY_API_URL_ID, kmCrops.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
