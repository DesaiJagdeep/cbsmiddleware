package com.cbs.middleware.web.rest;

import com.cbs.middleware.CbsMiddlewareApp;

import com.cbs.middleware.domain.KmCrops;
import com.cbs.middleware.repository.KmCropsRepository;
import com.cbs.middleware.service.KmCropsService;
import com.cbs.middleware.web.rest.errors.ExceptionTranslator;
import com.cbs.middleware.service.dto.KmCropsCriteria;
import com.cbs.middleware.service.KmCropsQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KmCropsResource REST controller.
 *
 * @see KmCropsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CbsMiddlewareApp.class)
public class KmCropsResourceIntTest {

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_HECTOR = 1D;
    private static final Double UPDATED_HECTOR = 2D;

    private static final String DEFAULT_HECTOR_MR = "AAAAAAAAAA";
    private static final String UPDATED_HECTOR_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_ARE = 1D;
    private static final Double UPDATED_ARE = 2D;

    private static final String DEFAULT_ARE_MR = "AAAAAAAAAA";
    private static final String UPDATED_ARE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_PRVIOUS_AMT = 1D;
    private static final Double UPDATED_PRVIOUS_AMT = 2D;

    private static final String DEFAULT_PREVIOUS_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_AMT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DEMAND = 1D;
    private static final Double UPDATED_DEMAND = 2D;

    private static final String DEFAULT_DEMAND_MR = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND_MR = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIETY = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETY = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIETY_MR = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETY_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BANK_AMT = 1D;
    private static final Double UPDATED_BANK_AMT = 2D;

    private static final String DEFAULT_BANK_AMT_MR = "AAAAAAAAAA";
    private static final String UPDATED_BANK_AMT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_NO_OF_TREE = 1D;
    private static final Double UPDATED_NO_OF_TREE = 2D;

    private static final String DEFAULT_NO_OF_TREE_MR = "AAAAAAAAAA";
    private static final String UPDATED_NO_OF_TREE_MR = "BBBBBBBBBB";

    @Autowired
    private KmCropsRepository kmCropsRepository;

    @Autowired
    private KmCropsService kmCropsService;

    @Autowired
    private KmCropsQueryService kmCropsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKmCropsMockMvc;

    private KmCrops kmCrops;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KmCropsResource kmCropsResource = new KmCropsResource(kmCropsService, kmCropsQueryService);
        this.restKmCropsMockMvc = MockMvcBuilders.standaloneSetup(kmCropsResource)
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
    public static KmCrops createEntity(EntityManager em) {
        KmCrops kmCrops = new KmCrops();
        kmCrops.setCropName(DEFAULT_CROP_NAME);
        kmCrops.setCropNameMr(DEFAULT_CROP_NAME_MR);
        kmCrops.setHector(DEFAULT_HECTOR);
        kmCrops.setHectorMr(DEFAULT_HECTOR_MR);
        kmCrops.setAre(DEFAULT_ARE);
        kmCrops.setAreMr(DEFAULT_ARE_MR);
        kmCrops.setPrviousAmt(DEFAULT_PRVIOUS_AMT);
        kmCrops.setPreviousAmtMr(DEFAULT_PREVIOUS_AMT_MR);
        kmCrops.setDemand(DEFAULT_DEMAND);
        kmCrops.setDemandMr(DEFAULT_DEMAND_MR);
        kmCrops.setSociety(DEFAULT_SOCIETY);
        kmCrops.setSocietyMr(DEFAULT_SOCIETY_MR);
        kmCrops.setBankAmt(DEFAULT_BANK_AMT);
        kmCrops.setBankAmtMr(DEFAULT_BANK_AMT_MR);
        kmCrops.setNoOfTree(DEFAULT_NO_OF_TREE);
        kmCrops.setNoOfTreeMr(DEFAULT_NO_OF_TREE_MR);
        return kmCrops;
    }

    @Before
    public void initTest() {
        kmCrops = createEntity(em);
    }

    @Test
    @Transactional
    public void createKmCrops() throws Exception {
        int databaseSizeBeforeCreate = kmCropsRepository.findAll().size();

        // Create the KmCrops
        restKmCropsMockMvc.perform(post("/api/km-crops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmCrops)))
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
    public void createKmCropsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kmCropsRepository.findAll().size();

        // Create the KmCrops with an existing ID
        kmCrops.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmCropsMockMvc.perform(post("/api/km-crops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmCrops)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKmCrops() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList
        restKmCropsMockMvc.perform(get("/api/km-crops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmCrops.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME.toString())))
            .andExpect(jsonPath("$.[*].cropNameMr").value(hasItem(DEFAULT_CROP_NAME_MR.toString())))
            .andExpect(jsonPath("$.[*].hector").value(hasItem(DEFAULT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].hectorMr").value(hasItem(DEFAULT_HECTOR_MR.toString())))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].areMr").value(hasItem(DEFAULT_ARE_MR.toString())))
            .andExpect(jsonPath("$.[*].prviousAmt").value(hasItem(DEFAULT_PRVIOUS_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].previousAmtMr").value(hasItem(DEFAULT_PREVIOUS_AMT_MR.toString())))
            .andExpect(jsonPath("$.[*].demand").value(hasItem(DEFAULT_DEMAND.doubleValue())))
            .andExpect(jsonPath("$.[*].demandMr").value(hasItem(DEFAULT_DEMAND_MR.toString())))
            .andExpect(jsonPath("$.[*].society").value(hasItem(DEFAULT_SOCIETY.toString())))
            .andExpect(jsonPath("$.[*].societyMr").value(hasItem(DEFAULT_SOCIETY_MR.toString())))
            .andExpect(jsonPath("$.[*].bankAmt").value(hasItem(DEFAULT_BANK_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].bankAmtMr").value(hasItem(DEFAULT_BANK_AMT_MR.toString())))
            .andExpect(jsonPath("$.[*].noOfTree").value(hasItem(DEFAULT_NO_OF_TREE.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfTreeMr").value(hasItem(DEFAULT_NO_OF_TREE_MR.toString())));
    }

    @Test
    @Transactional
    public void getKmCrops() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get the kmCrops
        restKmCropsMockMvc.perform(get("/api/km-crops/{id}", kmCrops.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kmCrops.getId().intValue()))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME.toString()))
            .andExpect(jsonPath("$.cropNameMr").value(DEFAULT_CROP_NAME_MR.toString()))
            .andExpect(jsonPath("$.hector").value(DEFAULT_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.hectorMr").value(DEFAULT_HECTOR_MR.toString()))
            .andExpect(jsonPath("$.are").value(DEFAULT_ARE.doubleValue()))
            .andExpect(jsonPath("$.areMr").value(DEFAULT_ARE_MR.toString()))
            .andExpect(jsonPath("$.prviousAmt").value(DEFAULT_PRVIOUS_AMT.doubleValue()))
            .andExpect(jsonPath("$.previousAmtMr").value(DEFAULT_PREVIOUS_AMT_MR.toString()))
            .andExpect(jsonPath("$.demand").value(DEFAULT_DEMAND.doubleValue()))
            .andExpect(jsonPath("$.demandMr").value(DEFAULT_DEMAND_MR.toString()))
            .andExpect(jsonPath("$.society").value(DEFAULT_SOCIETY.toString()))
            .andExpect(jsonPath("$.societyMr").value(DEFAULT_SOCIETY_MR.toString()))
            .andExpect(jsonPath("$.bankAmt").value(DEFAULT_BANK_AMT.doubleValue()))
            .andExpect(jsonPath("$.bankAmtMr").value(DEFAULT_BANK_AMT_MR.toString()))
            .andExpect(jsonPath("$.noOfTree").value(DEFAULT_NO_OF_TREE.doubleValue()))
            .andExpect(jsonPath("$.noOfTreeMr").value(DEFAULT_NO_OF_TREE_MR.toString()));
    }

    @Test
    @Transactional
    public void getAllKmCropsByCropNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropName equals to DEFAULT_CROP_NAME
        defaultKmCropsShouldBeFound("cropName.equals=" + DEFAULT_CROP_NAME);

        // Get all the kmCropsList where cropName equals to UPDATED_CROP_NAME
        defaultKmCropsShouldNotBeFound("cropName.equals=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    public void getAllKmCropsByCropNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropName in DEFAULT_CROP_NAME or UPDATED_CROP_NAME
        defaultKmCropsShouldBeFound("cropName.in=" + DEFAULT_CROP_NAME + "," + UPDATED_CROP_NAME);

        // Get all the kmCropsList where cropName equals to UPDATED_CROP_NAME
        defaultKmCropsShouldNotBeFound("cropName.in=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    public void getAllKmCropsByCropNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropName is not null
        defaultKmCropsShouldBeFound("cropName.specified=true");

        // Get all the kmCropsList where cropName is null
        defaultKmCropsShouldNotBeFound("cropName.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByCropNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropNameMr equals to DEFAULT_CROP_NAME_MR
        defaultKmCropsShouldBeFound("cropNameMr.equals=" + DEFAULT_CROP_NAME_MR);

        // Get all the kmCropsList where cropNameMr equals to UPDATED_CROP_NAME_MR
        defaultKmCropsShouldNotBeFound("cropNameMr.equals=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByCropNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropNameMr in DEFAULT_CROP_NAME_MR or UPDATED_CROP_NAME_MR
        defaultKmCropsShouldBeFound("cropNameMr.in=" + DEFAULT_CROP_NAME_MR + "," + UPDATED_CROP_NAME_MR);

        // Get all the kmCropsList where cropNameMr equals to UPDATED_CROP_NAME_MR
        defaultKmCropsShouldNotBeFound("cropNameMr.in=" + UPDATED_CROP_NAME_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByCropNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where cropNameMr is not null
        defaultKmCropsShouldBeFound("cropNameMr.specified=true");

        // Get all the kmCropsList where cropNameMr is null
        defaultKmCropsShouldNotBeFound("cropNameMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector equals to DEFAULT_HECTOR
        defaultKmCropsShouldBeFound("hector.equals=" + DEFAULT_HECTOR);

        // Get all the kmCropsList where hector equals to UPDATED_HECTOR
        defaultKmCropsShouldNotBeFound("hector.equals=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector in DEFAULT_HECTOR or UPDATED_HECTOR
        defaultKmCropsShouldBeFound("hector.in=" + DEFAULT_HECTOR + "," + UPDATED_HECTOR);

        // Get all the kmCropsList where hector equals to UPDATED_HECTOR
        defaultKmCropsShouldNotBeFound("hector.in=" + UPDATED_HECTOR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hector is not null
        defaultKmCropsShouldBeFound("hector.specified=true");

        // Get all the kmCropsList where hector is null
        defaultKmCropsShouldNotBeFound("hector.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByHectorMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hectorMr equals to DEFAULT_HECTOR_MR
        defaultKmCropsShouldBeFound("hectorMr.equals=" + DEFAULT_HECTOR_MR);

        // Get all the kmCropsList where hectorMr equals to UPDATED_HECTOR_MR
        defaultKmCropsShouldNotBeFound("hectorMr.equals=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByHectorMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hectorMr in DEFAULT_HECTOR_MR or UPDATED_HECTOR_MR
        defaultKmCropsShouldBeFound("hectorMr.in=" + DEFAULT_HECTOR_MR + "," + UPDATED_HECTOR_MR);

        // Get all the kmCropsList where hectorMr equals to UPDATED_HECTOR_MR
        defaultKmCropsShouldNotBeFound("hectorMr.in=" + UPDATED_HECTOR_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByHectorMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where hectorMr is not null
        defaultKmCropsShouldBeFound("hectorMr.specified=true");

        // Get all the kmCropsList where hectorMr is null
        defaultKmCropsShouldNotBeFound("hectorMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByAreIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are equals to DEFAULT_ARE
        defaultKmCropsShouldBeFound("are.equals=" + DEFAULT_ARE);

        // Get all the kmCropsList where are equals to UPDATED_ARE
        defaultKmCropsShouldNotBeFound("are.equals=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    public void getAllKmCropsByAreIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are in DEFAULT_ARE or UPDATED_ARE
        defaultKmCropsShouldBeFound("are.in=" + DEFAULT_ARE + "," + UPDATED_ARE);

        // Get all the kmCropsList where are equals to UPDATED_ARE
        defaultKmCropsShouldNotBeFound("are.in=" + UPDATED_ARE);
    }

    @Test
    @Transactional
    public void getAllKmCropsByAreIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where are is not null
        defaultKmCropsShouldBeFound("are.specified=true");

        // Get all the kmCropsList where are is null
        defaultKmCropsShouldNotBeFound("are.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByAreMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where areMr equals to DEFAULT_ARE_MR
        defaultKmCropsShouldBeFound("areMr.equals=" + DEFAULT_ARE_MR);

        // Get all the kmCropsList where areMr equals to UPDATED_ARE_MR
        defaultKmCropsShouldNotBeFound("areMr.equals=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByAreMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where areMr in DEFAULT_ARE_MR or UPDATED_ARE_MR
        defaultKmCropsShouldBeFound("areMr.in=" + DEFAULT_ARE_MR + "," + UPDATED_ARE_MR);

        // Get all the kmCropsList where areMr equals to UPDATED_ARE_MR
        defaultKmCropsShouldNotBeFound("areMr.in=" + UPDATED_ARE_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByAreMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where areMr is not null
        defaultKmCropsShouldBeFound("areMr.specified=true");

        // Get all the kmCropsList where areMr is null
        defaultKmCropsShouldNotBeFound("areMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByPrviousAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt equals to DEFAULT_PRVIOUS_AMT
        defaultKmCropsShouldBeFound("prviousAmt.equals=" + DEFAULT_PRVIOUS_AMT);

        // Get all the kmCropsList where prviousAmt equals to UPDATED_PRVIOUS_AMT
        defaultKmCropsShouldNotBeFound("prviousAmt.equals=" + UPDATED_PRVIOUS_AMT);
    }

    @Test
    @Transactional
    public void getAllKmCropsByPrviousAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt in DEFAULT_PRVIOUS_AMT or UPDATED_PRVIOUS_AMT
        defaultKmCropsShouldBeFound("prviousAmt.in=" + DEFAULT_PRVIOUS_AMT + "," + UPDATED_PRVIOUS_AMT);

        // Get all the kmCropsList where prviousAmt equals to UPDATED_PRVIOUS_AMT
        defaultKmCropsShouldNotBeFound("prviousAmt.in=" + UPDATED_PRVIOUS_AMT);
    }

    @Test
    @Transactional
    public void getAllKmCropsByPrviousAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where prviousAmt is not null
        defaultKmCropsShouldBeFound("prviousAmt.specified=true");

        // Get all the kmCropsList where prviousAmt is null
        defaultKmCropsShouldNotBeFound("prviousAmt.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByPreviousAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where previousAmtMr equals to DEFAULT_PREVIOUS_AMT_MR
        defaultKmCropsShouldBeFound("previousAmtMr.equals=" + DEFAULT_PREVIOUS_AMT_MR);

        // Get all the kmCropsList where previousAmtMr equals to UPDATED_PREVIOUS_AMT_MR
        defaultKmCropsShouldNotBeFound("previousAmtMr.equals=" + UPDATED_PREVIOUS_AMT_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByPreviousAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where previousAmtMr in DEFAULT_PREVIOUS_AMT_MR or UPDATED_PREVIOUS_AMT_MR
        defaultKmCropsShouldBeFound("previousAmtMr.in=" + DEFAULT_PREVIOUS_AMT_MR + "," + UPDATED_PREVIOUS_AMT_MR);

        // Get all the kmCropsList where previousAmtMr equals to UPDATED_PREVIOUS_AMT_MR
        defaultKmCropsShouldNotBeFound("previousAmtMr.in=" + UPDATED_PREVIOUS_AMT_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByPreviousAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where previousAmtMr is not null
        defaultKmCropsShouldBeFound("previousAmtMr.specified=true");

        // Get all the kmCropsList where previousAmtMr is null
        defaultKmCropsShouldNotBeFound("previousAmtMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByDemandIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand equals to DEFAULT_DEMAND
        defaultKmCropsShouldBeFound("demand.equals=" + DEFAULT_DEMAND);

        // Get all the kmCropsList where demand equals to UPDATED_DEMAND
        defaultKmCropsShouldNotBeFound("demand.equals=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    public void getAllKmCropsByDemandIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand in DEFAULT_DEMAND or UPDATED_DEMAND
        defaultKmCropsShouldBeFound("demand.in=" + DEFAULT_DEMAND + "," + UPDATED_DEMAND);

        // Get all the kmCropsList where demand equals to UPDATED_DEMAND
        defaultKmCropsShouldNotBeFound("demand.in=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    public void getAllKmCropsByDemandIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demand is not null
        defaultKmCropsShouldBeFound("demand.specified=true");

        // Get all the kmCropsList where demand is null
        defaultKmCropsShouldNotBeFound("demand.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByDemandMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demandMr equals to DEFAULT_DEMAND_MR
        defaultKmCropsShouldBeFound("demandMr.equals=" + DEFAULT_DEMAND_MR);

        // Get all the kmCropsList where demandMr equals to UPDATED_DEMAND_MR
        defaultKmCropsShouldNotBeFound("demandMr.equals=" + UPDATED_DEMAND_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByDemandMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demandMr in DEFAULT_DEMAND_MR or UPDATED_DEMAND_MR
        defaultKmCropsShouldBeFound("demandMr.in=" + DEFAULT_DEMAND_MR + "," + UPDATED_DEMAND_MR);

        // Get all the kmCropsList where demandMr equals to UPDATED_DEMAND_MR
        defaultKmCropsShouldNotBeFound("demandMr.in=" + UPDATED_DEMAND_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByDemandMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where demandMr is not null
        defaultKmCropsShouldBeFound("demandMr.specified=true");

        // Get all the kmCropsList where demandMr is null
        defaultKmCropsShouldNotBeFound("demandMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsBySocietyIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where society equals to DEFAULT_SOCIETY
        defaultKmCropsShouldBeFound("society.equals=" + DEFAULT_SOCIETY);

        // Get all the kmCropsList where society equals to UPDATED_SOCIETY
        defaultKmCropsShouldNotBeFound("society.equals=" + UPDATED_SOCIETY);
    }

    @Test
    @Transactional
    public void getAllKmCropsBySocietyIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where society in DEFAULT_SOCIETY or UPDATED_SOCIETY
        defaultKmCropsShouldBeFound("society.in=" + DEFAULT_SOCIETY + "," + UPDATED_SOCIETY);

        // Get all the kmCropsList where society equals to UPDATED_SOCIETY
        defaultKmCropsShouldNotBeFound("society.in=" + UPDATED_SOCIETY);
    }

    @Test
    @Transactional
    public void getAllKmCropsBySocietyIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where society is not null
        defaultKmCropsShouldBeFound("society.specified=true");

        // Get all the kmCropsList where society is null
        defaultKmCropsShouldNotBeFound("society.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsBySocietyMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where societyMr equals to DEFAULT_SOCIETY_MR
        defaultKmCropsShouldBeFound("societyMr.equals=" + DEFAULT_SOCIETY_MR);

        // Get all the kmCropsList where societyMr equals to UPDATED_SOCIETY_MR
        defaultKmCropsShouldNotBeFound("societyMr.equals=" + UPDATED_SOCIETY_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsBySocietyMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where societyMr in DEFAULT_SOCIETY_MR or UPDATED_SOCIETY_MR
        defaultKmCropsShouldBeFound("societyMr.in=" + DEFAULT_SOCIETY_MR + "," + UPDATED_SOCIETY_MR);

        // Get all the kmCropsList where societyMr equals to UPDATED_SOCIETY_MR
        defaultKmCropsShouldNotBeFound("societyMr.in=" + UPDATED_SOCIETY_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsBySocietyMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where societyMr is not null
        defaultKmCropsShouldBeFound("societyMr.specified=true");

        // Get all the kmCropsList where societyMr is null
        defaultKmCropsShouldNotBeFound("societyMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByBankAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt equals to DEFAULT_BANK_AMT
        defaultKmCropsShouldBeFound("bankAmt.equals=" + DEFAULT_BANK_AMT);

        // Get all the kmCropsList where bankAmt equals to UPDATED_BANK_AMT
        defaultKmCropsShouldNotBeFound("bankAmt.equals=" + UPDATED_BANK_AMT);
    }

    @Test
    @Transactional
    public void getAllKmCropsByBankAmtIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt in DEFAULT_BANK_AMT or UPDATED_BANK_AMT
        defaultKmCropsShouldBeFound("bankAmt.in=" + DEFAULT_BANK_AMT + "," + UPDATED_BANK_AMT);

        // Get all the kmCropsList where bankAmt equals to UPDATED_BANK_AMT
        defaultKmCropsShouldNotBeFound("bankAmt.in=" + UPDATED_BANK_AMT);
    }

    @Test
    @Transactional
    public void getAllKmCropsByBankAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmt is not null
        defaultKmCropsShouldBeFound("bankAmt.specified=true");

        // Get all the kmCropsList where bankAmt is null
        defaultKmCropsShouldNotBeFound("bankAmt.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByBankAmtMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmtMr equals to DEFAULT_BANK_AMT_MR
        defaultKmCropsShouldBeFound("bankAmtMr.equals=" + DEFAULT_BANK_AMT_MR);

        // Get all the kmCropsList where bankAmtMr equals to UPDATED_BANK_AMT_MR
        defaultKmCropsShouldNotBeFound("bankAmtMr.equals=" + UPDATED_BANK_AMT_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByBankAmtMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmtMr in DEFAULT_BANK_AMT_MR or UPDATED_BANK_AMT_MR
        defaultKmCropsShouldBeFound("bankAmtMr.in=" + DEFAULT_BANK_AMT_MR + "," + UPDATED_BANK_AMT_MR);

        // Get all the kmCropsList where bankAmtMr equals to UPDATED_BANK_AMT_MR
        defaultKmCropsShouldNotBeFound("bankAmtMr.in=" + UPDATED_BANK_AMT_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByBankAmtMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where bankAmtMr is not null
        defaultKmCropsShouldBeFound("bankAmtMr.specified=true");

        // Get all the kmCropsList where bankAmtMr is null
        defaultKmCropsShouldNotBeFound("bankAmtMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByNoOfTreeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree equals to DEFAULT_NO_OF_TREE
        defaultKmCropsShouldBeFound("noOfTree.equals=" + DEFAULT_NO_OF_TREE);

        // Get all the kmCropsList where noOfTree equals to UPDATED_NO_OF_TREE
        defaultKmCropsShouldNotBeFound("noOfTree.equals=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    public void getAllKmCropsByNoOfTreeIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree in DEFAULT_NO_OF_TREE or UPDATED_NO_OF_TREE
        defaultKmCropsShouldBeFound("noOfTree.in=" + DEFAULT_NO_OF_TREE + "," + UPDATED_NO_OF_TREE);

        // Get all the kmCropsList where noOfTree equals to UPDATED_NO_OF_TREE
        defaultKmCropsShouldNotBeFound("noOfTree.in=" + UPDATED_NO_OF_TREE);
    }

    @Test
    @Transactional
    public void getAllKmCropsByNoOfTreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTree is not null
        defaultKmCropsShouldBeFound("noOfTree.specified=true");

        // Get all the kmCropsList where noOfTree is null
        defaultKmCropsShouldNotBeFound("noOfTree.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmCropsByNoOfTreeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTreeMr equals to DEFAULT_NO_OF_TREE_MR
        defaultKmCropsShouldBeFound("noOfTreeMr.equals=" + DEFAULT_NO_OF_TREE_MR);

        // Get all the kmCropsList where noOfTreeMr equals to UPDATED_NO_OF_TREE_MR
        defaultKmCropsShouldNotBeFound("noOfTreeMr.equals=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByNoOfTreeMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTreeMr in DEFAULT_NO_OF_TREE_MR or UPDATED_NO_OF_TREE_MR
        defaultKmCropsShouldBeFound("noOfTreeMr.in=" + DEFAULT_NO_OF_TREE_MR + "," + UPDATED_NO_OF_TREE_MR);

        // Get all the kmCropsList where noOfTreeMr equals to UPDATED_NO_OF_TREE_MR
        defaultKmCropsShouldNotBeFound("noOfTreeMr.in=" + UPDATED_NO_OF_TREE_MR);
    }

    @Test
    @Transactional
    public void getAllKmCropsByNoOfTreeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmCropsRepository.saveAndFlush(kmCrops);

        // Get all the kmCropsList where noOfTreeMr is not null
        defaultKmCropsShouldBeFound("noOfTreeMr.specified=true");

        // Get all the kmCropsList where noOfTreeMr is null
        defaultKmCropsShouldNotBeFound("noOfTreeMr.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultKmCropsShouldBeFound(String filter) throws Exception {
        restKmCropsMockMvc.perform(get("/api/km-crops?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmCrops.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME.toString())))
            .andExpect(jsonPath("$.[*].cropNameMr").value(hasItem(DEFAULT_CROP_NAME_MR.toString())))
            .andExpect(jsonPath("$.[*].hector").value(hasItem(DEFAULT_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].hectorMr").value(hasItem(DEFAULT_HECTOR_MR.toString())))
            .andExpect(jsonPath("$.[*].are").value(hasItem(DEFAULT_ARE.doubleValue())))
            .andExpect(jsonPath("$.[*].areMr").value(hasItem(DEFAULT_ARE_MR.toString())))
            .andExpect(jsonPath("$.[*].prviousAmt").value(hasItem(DEFAULT_PRVIOUS_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].previousAmtMr").value(hasItem(DEFAULT_PREVIOUS_AMT_MR.toString())))
            .andExpect(jsonPath("$.[*].demand").value(hasItem(DEFAULT_DEMAND.doubleValue())))
            .andExpect(jsonPath("$.[*].demandMr").value(hasItem(DEFAULT_DEMAND_MR.toString())))
            .andExpect(jsonPath("$.[*].society").value(hasItem(DEFAULT_SOCIETY.toString())))
            .andExpect(jsonPath("$.[*].societyMr").value(hasItem(DEFAULT_SOCIETY_MR.toString())))
            .andExpect(jsonPath("$.[*].bankAmt").value(hasItem(DEFAULT_BANK_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].bankAmtMr").value(hasItem(DEFAULT_BANK_AMT_MR.toString())))
            .andExpect(jsonPath("$.[*].noOfTree").value(hasItem(DEFAULT_NO_OF_TREE.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfTreeMr").value(hasItem(DEFAULT_NO_OF_TREE_MR.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultKmCropsShouldNotBeFound(String filter) throws Exception {
        restKmCropsMockMvc.perform(get("/api/km-crops?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingKmCrops() throws Exception {
        // Get the kmCrops
        restKmCropsMockMvc.perform(get("/api/km-crops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKmCrops() throws Exception {
        // Initialize the database
        kmCropsService.save(kmCrops);

        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();

        // Update the kmCrops
        KmCrops updatedKmCrops = kmCropsRepository.findOne(kmCrops.getId());
        updatedKmCrops.setCropName(UPDATED_CROP_NAME);
        updatedKmCrops.setCropNameMr(UPDATED_CROP_NAME_MR);
        updatedKmCrops.setHector(UPDATED_HECTOR);
        updatedKmCrops.setHectorMr(UPDATED_HECTOR_MR);
        updatedKmCrops.setAre(UPDATED_ARE);
        updatedKmCrops.setAreMr(UPDATED_ARE_MR);
        updatedKmCrops.setPrviousAmt(UPDATED_PRVIOUS_AMT);
        updatedKmCrops.setPreviousAmtMr(UPDATED_PREVIOUS_AMT_MR);
        updatedKmCrops.setDemand(UPDATED_DEMAND);
        updatedKmCrops.setDemandMr(UPDATED_DEMAND_MR);
        updatedKmCrops.setSociety(UPDATED_SOCIETY);
        updatedKmCrops.setSocietyMr(UPDATED_SOCIETY_MR);
        updatedKmCrops.setBankAmt(UPDATED_BANK_AMT);
        updatedKmCrops.setBankAmtMr(UPDATED_BANK_AMT_MR);
        updatedKmCrops.setNoOfTree(UPDATED_NO_OF_TREE);
        updatedKmCrops.setNoOfTreeMr(UPDATED_NO_OF_TREE_MR);

        restKmCropsMockMvc.perform(put("/api/km-crops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKmCrops)))
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
    public void updateNonExistingKmCrops() throws Exception {
        int databaseSizeBeforeUpdate = kmCropsRepository.findAll().size();

        // Create the KmCrops

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKmCropsMockMvc.perform(put("/api/km-crops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmCrops)))
            .andExpect(status().isCreated());

        // Validate the KmCrops in the database
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKmCrops() throws Exception {
        // Initialize the database
        kmCropsService.save(kmCrops);

        int databaseSizeBeforeDelete = kmCropsRepository.findAll().size();

        // Get the kmCrops
        restKmCropsMockMvc.perform(delete("/api/km-crops/{id}", kmCrops.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KmCrops> kmCropsList = kmCropsRepository.findAll();
        assertThat(kmCropsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmCrops.class);
        KmCrops kmCrops1 = new KmCrops();
        kmCrops1.setId(1L);
        KmCrops kmCrops2 = new KmCrops();
        kmCrops2.setId(kmCrops1.getId());
        assertThat(kmCrops1).isEqualTo(kmCrops2);
        kmCrops2.setId(2L);
        assertThat(kmCrops1).isNotEqualTo(kmCrops2);
        kmCrops1.setId(null);
        assertThat(kmCrops1).isNotEqualTo(kmCrops2);
    }
}
