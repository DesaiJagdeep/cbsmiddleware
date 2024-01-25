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

    private static final Integer DEFAULT_PACS_NUMBER = 1;
    private static final Integer UPDATED_PACS_NUMBER = 2;
    private static final Integer SMALLER_PACS_NUMBER = 1 - 1;

    private static final Integer DEFAULT_MEM_NO = 1;
    private static final Integer UPDATED_MEM_NO = 2;
    private static final Integer SMALLER_MEM_NO = 1 - 1;

    private static final Double DEFAULT_MEM_HECTOR = 1D;
    private static final Double UPDATED_MEM_HECTOR = 2D;
    private static final Double SMALLER_MEM_HECTOR = 1D - 1D;

    private static final String DEFAULT_MEM_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEM_NO_MR = "BBBBBBBBBB";

    private static final String DEFAULT_MEM_HECTOR_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEM_HECTOR_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_MEM_AAR = 1D;
    private static final Double UPDATED_MEM_AAR = 2D;
    private static final Double SMALLER_MEM_AAR = 1D - 1D;

    private static final String DEFAULT_MEM_AAR_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEM_AAR_MR = "BBBBBBBBBB";

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
            .memNo(DEFAULT_MEM_NO)
            .memHector(DEFAULT_MEM_HECTOR)
            .memNoMr(DEFAULT_MEM_NO_MR)
            .memHectorMr(DEFAULT_MEM_HECTOR_MR)
            .memAar(DEFAULT_MEM_AAR)
            .memAarMr(DEFAULT_MEM_AAR_MR);
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
            .memNo(UPDATED_MEM_NO)
            .memHector(UPDATED_MEM_HECTOR)
            .memNoMr(UPDATED_MEM_NO_MR)
            .memHectorMr(UPDATED_MEM_HECTOR_MR)
            .memAar(UPDATED_MEM_AAR)
            .memAarMr(UPDATED_MEM_AAR_MR);
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
        assertThat(testKamalCrop.getMemNo()).isEqualTo(DEFAULT_MEM_NO);
        assertThat(testKamalCrop.getMemHector()).isEqualTo(DEFAULT_MEM_HECTOR);
        assertThat(testKamalCrop.getMemNoMr()).isEqualTo(DEFAULT_MEM_NO_MR);
        assertThat(testKamalCrop.getMemHectorMr()).isEqualTo(DEFAULT_MEM_HECTOR_MR);
        assertThat(testKamalCrop.getMemAar()).isEqualTo(DEFAULT_MEM_AAR);
        assertThat(testKamalCrop.getMemAarMr()).isEqualTo(DEFAULT_MEM_AAR_MR);
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
    void checkPacsNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = kamalCropRepository.findAll().size();
        // set the field null
        kamalCrop.setPacsNumber(null);

        // Create the KamalCrop, which fails.

        restKamalCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalCrop)))
            .andExpect(status().isBadRequest());

        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMemNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = kamalCropRepository.findAll().size();
        // set the field null
        kamalCrop.setMemNo(null);

        // Create the KamalCrop, which fails.

        restKamalCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalCrop)))
            .andExpect(status().isBadRequest());

        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMemHectorIsRequired() throws Exception {
        int databaseSizeBeforeTest = kamalCropRepository.findAll().size();
        // set the field null
        kamalCrop.setMemHector(null);

        // Create the KamalCrop, which fails.

        restKamalCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalCrop)))
            .andExpect(status().isBadRequest());

        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].memNo").value(hasItem(DEFAULT_MEM_NO)))
            .andExpect(jsonPath("$.[*].memHector").value(hasItem(DEFAULT_MEM_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].memNoMr").value(hasItem(DEFAULT_MEM_NO_MR)))
            .andExpect(jsonPath("$.[*].memHectorMr").value(hasItem(DEFAULT_MEM_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].memAar").value(hasItem(DEFAULT_MEM_AAR.doubleValue())))
            .andExpect(jsonPath("$.[*].memAarMr").value(hasItem(DEFAULT_MEM_AAR_MR)));
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
            .andExpect(jsonPath("$.memNo").value(DEFAULT_MEM_NO))
            .andExpect(jsonPath("$.memHector").value(DEFAULT_MEM_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.memNoMr").value(DEFAULT_MEM_NO_MR))
            .andExpect(jsonPath("$.memHectorMr").value(DEFAULT_MEM_HECTOR_MR))
            .andExpect(jsonPath("$.memAar").value(DEFAULT_MEM_AAR.doubleValue()))
            .andExpect(jsonPath("$.memAarMr").value(DEFAULT_MEM_AAR_MR));
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
    void getAllKamalCropsByPacsNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber is greater than or equal to DEFAULT_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.greaterThanOrEqual=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber is greater than or equal to UPDATED_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.greaterThanOrEqual=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber is less than or equal to DEFAULT_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.lessThanOrEqual=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber is less than or equal to SMALLER_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.lessThanOrEqual=" + SMALLER_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber is less than DEFAULT_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.lessThan=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber is less than UPDATED_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.lessThan=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber is greater than DEFAULT_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.greaterThan=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber is greater than SMALLER_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.greaterThan=" + SMALLER_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNo equals to DEFAULT_MEM_NO
        defaultKamalCropShouldBeFound("memNo.equals=" + DEFAULT_MEM_NO);

        // Get all the kamalCropList where memNo equals to UPDATED_MEM_NO
        defaultKamalCropShouldNotBeFound("memNo.equals=" + UPDATED_MEM_NO);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNo in DEFAULT_MEM_NO or UPDATED_MEM_NO
        defaultKamalCropShouldBeFound("memNo.in=" + DEFAULT_MEM_NO + "," + UPDATED_MEM_NO);

        // Get all the kamalCropList where memNo equals to UPDATED_MEM_NO
        defaultKamalCropShouldNotBeFound("memNo.in=" + UPDATED_MEM_NO);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNo is not null
        defaultKamalCropShouldBeFound("memNo.specified=true");

        // Get all the kamalCropList where memNo is null
        defaultKamalCropShouldNotBeFound("memNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNo is greater than or equal to DEFAULT_MEM_NO
        defaultKamalCropShouldBeFound("memNo.greaterThanOrEqual=" + DEFAULT_MEM_NO);

        // Get all the kamalCropList where memNo is greater than or equal to UPDATED_MEM_NO
        defaultKamalCropShouldNotBeFound("memNo.greaterThanOrEqual=" + UPDATED_MEM_NO);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNo is less than or equal to DEFAULT_MEM_NO
        defaultKamalCropShouldBeFound("memNo.lessThanOrEqual=" + DEFAULT_MEM_NO);

        // Get all the kamalCropList where memNo is less than or equal to SMALLER_MEM_NO
        defaultKamalCropShouldNotBeFound("memNo.lessThanOrEqual=" + SMALLER_MEM_NO);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNo is less than DEFAULT_MEM_NO
        defaultKamalCropShouldNotBeFound("memNo.lessThan=" + DEFAULT_MEM_NO);

        // Get all the kamalCropList where memNo is less than UPDATED_MEM_NO
        defaultKamalCropShouldBeFound("memNo.lessThan=" + UPDATED_MEM_NO);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNo is greater than DEFAULT_MEM_NO
        defaultKamalCropShouldNotBeFound("memNo.greaterThan=" + DEFAULT_MEM_NO);

        // Get all the kamalCropList where memNo is greater than SMALLER_MEM_NO
        defaultKamalCropShouldBeFound("memNo.greaterThan=" + SMALLER_MEM_NO);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHector equals to DEFAULT_MEM_HECTOR
        defaultKamalCropShouldBeFound("memHector.equals=" + DEFAULT_MEM_HECTOR);

        // Get all the kamalCropList where memHector equals to UPDATED_MEM_HECTOR
        defaultKamalCropShouldNotBeFound("memHector.equals=" + UPDATED_MEM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHector in DEFAULT_MEM_HECTOR or UPDATED_MEM_HECTOR
        defaultKamalCropShouldBeFound("memHector.in=" + DEFAULT_MEM_HECTOR + "," + UPDATED_MEM_HECTOR);

        // Get all the kamalCropList where memHector equals to UPDATED_MEM_HECTOR
        defaultKamalCropShouldNotBeFound("memHector.in=" + UPDATED_MEM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHector is not null
        defaultKamalCropShouldBeFound("memHector.specified=true");

        // Get all the kamalCropList where memHector is null
        defaultKamalCropShouldNotBeFound("memHector.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHector is greater than or equal to DEFAULT_MEM_HECTOR
        defaultKamalCropShouldBeFound("memHector.greaterThanOrEqual=" + DEFAULT_MEM_HECTOR);

        // Get all the kamalCropList where memHector is greater than or equal to UPDATED_MEM_HECTOR
        defaultKamalCropShouldNotBeFound("memHector.greaterThanOrEqual=" + UPDATED_MEM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHector is less than or equal to DEFAULT_MEM_HECTOR
        defaultKamalCropShouldBeFound("memHector.lessThanOrEqual=" + DEFAULT_MEM_HECTOR);

        // Get all the kamalCropList where memHector is less than or equal to SMALLER_MEM_HECTOR
        defaultKamalCropShouldNotBeFound("memHector.lessThanOrEqual=" + SMALLER_MEM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHector is less than DEFAULT_MEM_HECTOR
        defaultKamalCropShouldNotBeFound("memHector.lessThan=" + DEFAULT_MEM_HECTOR);

        // Get all the kamalCropList where memHector is less than UPDATED_MEM_HECTOR
        defaultKamalCropShouldBeFound("memHector.lessThan=" + UPDATED_MEM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHector is greater than DEFAULT_MEM_HECTOR
        defaultKamalCropShouldNotBeFound("memHector.greaterThan=" + DEFAULT_MEM_HECTOR);

        // Get all the kamalCropList where memHector is greater than SMALLER_MEM_HECTOR
        defaultKamalCropShouldBeFound("memHector.greaterThan=" + SMALLER_MEM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNoMr equals to DEFAULT_MEM_NO_MR
        defaultKamalCropShouldBeFound("memNoMr.equals=" + DEFAULT_MEM_NO_MR);

        // Get all the kamalCropList where memNoMr equals to UPDATED_MEM_NO_MR
        defaultKamalCropShouldNotBeFound("memNoMr.equals=" + UPDATED_MEM_NO_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNoMr in DEFAULT_MEM_NO_MR or UPDATED_MEM_NO_MR
        defaultKamalCropShouldBeFound("memNoMr.in=" + DEFAULT_MEM_NO_MR + "," + UPDATED_MEM_NO_MR);

        // Get all the kamalCropList where memNoMr equals to UPDATED_MEM_NO_MR
        defaultKamalCropShouldNotBeFound("memNoMr.in=" + UPDATED_MEM_NO_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNoMr is not null
        defaultKamalCropShouldBeFound("memNoMr.specified=true");

        // Get all the kamalCropList where memNoMr is null
        defaultKamalCropShouldNotBeFound("memNoMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoMrContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNoMr contains DEFAULT_MEM_NO_MR
        defaultKamalCropShouldBeFound("memNoMr.contains=" + DEFAULT_MEM_NO_MR);

        // Get all the kamalCropList where memNoMr contains UPDATED_MEM_NO_MR
        defaultKamalCropShouldNotBeFound("memNoMr.contains=" + UPDATED_MEM_NO_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemNoMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memNoMr does not contain DEFAULT_MEM_NO_MR
        defaultKamalCropShouldNotBeFound("memNoMr.doesNotContain=" + DEFAULT_MEM_NO_MR);

        // Get all the kamalCropList where memNoMr does not contain UPDATED_MEM_NO_MR
        defaultKamalCropShouldBeFound("memNoMr.doesNotContain=" + UPDATED_MEM_NO_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHectorMr equals to DEFAULT_MEM_HECTOR_MR
        defaultKamalCropShouldBeFound("memHectorMr.equals=" + DEFAULT_MEM_HECTOR_MR);

        // Get all the kamalCropList where memHectorMr equals to UPDATED_MEM_HECTOR_MR
        defaultKamalCropShouldNotBeFound("memHectorMr.equals=" + UPDATED_MEM_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHectorMr in DEFAULT_MEM_HECTOR_MR or UPDATED_MEM_HECTOR_MR
        defaultKamalCropShouldBeFound("memHectorMr.in=" + DEFAULT_MEM_HECTOR_MR + "," + UPDATED_MEM_HECTOR_MR);

        // Get all the kamalCropList where memHectorMr equals to UPDATED_MEM_HECTOR_MR
        defaultKamalCropShouldNotBeFound("memHectorMr.in=" + UPDATED_MEM_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHectorMr is not null
        defaultKamalCropShouldBeFound("memHectorMr.specified=true");

        // Get all the kamalCropList where memHectorMr is null
        defaultKamalCropShouldNotBeFound("memHectorMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorMrContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHectorMr contains DEFAULT_MEM_HECTOR_MR
        defaultKamalCropShouldBeFound("memHectorMr.contains=" + DEFAULT_MEM_HECTOR_MR);

        // Get all the kamalCropList where memHectorMr contains UPDATED_MEM_HECTOR_MR
        defaultKamalCropShouldNotBeFound("memHectorMr.contains=" + UPDATED_MEM_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemHectorMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memHectorMr does not contain DEFAULT_MEM_HECTOR_MR
        defaultKamalCropShouldNotBeFound("memHectorMr.doesNotContain=" + DEFAULT_MEM_HECTOR_MR);

        // Get all the kamalCropList where memHectorMr does not contain UPDATED_MEM_HECTOR_MR
        defaultKamalCropShouldBeFound("memHectorMr.doesNotContain=" + UPDATED_MEM_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAar equals to DEFAULT_MEM_AAR
        defaultKamalCropShouldBeFound("memAar.equals=" + DEFAULT_MEM_AAR);

        // Get all the kamalCropList where memAar equals to UPDATED_MEM_AAR
        defaultKamalCropShouldNotBeFound("memAar.equals=" + UPDATED_MEM_AAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAar in DEFAULT_MEM_AAR or UPDATED_MEM_AAR
        defaultKamalCropShouldBeFound("memAar.in=" + DEFAULT_MEM_AAR + "," + UPDATED_MEM_AAR);

        // Get all the kamalCropList where memAar equals to UPDATED_MEM_AAR
        defaultKamalCropShouldNotBeFound("memAar.in=" + UPDATED_MEM_AAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAar is not null
        defaultKamalCropShouldBeFound("memAar.specified=true");

        // Get all the kamalCropList where memAar is null
        defaultKamalCropShouldNotBeFound("memAar.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAar is greater than or equal to DEFAULT_MEM_AAR
        defaultKamalCropShouldBeFound("memAar.greaterThanOrEqual=" + DEFAULT_MEM_AAR);

        // Get all the kamalCropList where memAar is greater than or equal to UPDATED_MEM_AAR
        defaultKamalCropShouldNotBeFound("memAar.greaterThanOrEqual=" + UPDATED_MEM_AAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAar is less than or equal to DEFAULT_MEM_AAR
        defaultKamalCropShouldBeFound("memAar.lessThanOrEqual=" + DEFAULT_MEM_AAR);

        // Get all the kamalCropList where memAar is less than or equal to SMALLER_MEM_AAR
        defaultKamalCropShouldNotBeFound("memAar.lessThanOrEqual=" + SMALLER_MEM_AAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAar is less than DEFAULT_MEM_AAR
        defaultKamalCropShouldNotBeFound("memAar.lessThan=" + DEFAULT_MEM_AAR);

        // Get all the kamalCropList where memAar is less than UPDATED_MEM_AAR
        defaultKamalCropShouldBeFound("memAar.lessThan=" + UPDATED_MEM_AAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAar is greater than DEFAULT_MEM_AAR
        defaultKamalCropShouldNotBeFound("memAar.greaterThan=" + DEFAULT_MEM_AAR);

        // Get all the kamalCropList where memAar is greater than SMALLER_MEM_AAR
        defaultKamalCropShouldBeFound("memAar.greaterThan=" + SMALLER_MEM_AAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAarMr equals to DEFAULT_MEM_AAR_MR
        defaultKamalCropShouldBeFound("memAarMr.equals=" + DEFAULT_MEM_AAR_MR);

        // Get all the kamalCropList where memAarMr equals to UPDATED_MEM_AAR_MR
        defaultKamalCropShouldNotBeFound("memAarMr.equals=" + UPDATED_MEM_AAR_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAarMr in DEFAULT_MEM_AAR_MR or UPDATED_MEM_AAR_MR
        defaultKamalCropShouldBeFound("memAarMr.in=" + DEFAULT_MEM_AAR_MR + "," + UPDATED_MEM_AAR_MR);

        // Get all the kamalCropList where memAarMr equals to UPDATED_MEM_AAR_MR
        defaultKamalCropShouldNotBeFound("memAarMr.in=" + UPDATED_MEM_AAR_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAarMr is not null
        defaultKamalCropShouldBeFound("memAarMr.specified=true");

        // Get all the kamalCropList where memAarMr is null
        defaultKamalCropShouldNotBeFound("memAarMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarMrContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAarMr contains DEFAULT_MEM_AAR_MR
        defaultKamalCropShouldBeFound("memAarMr.contains=" + DEFAULT_MEM_AAR_MR);

        // Get all the kamalCropList where memAarMr contains UPDATED_MEM_AAR_MR
        defaultKamalCropShouldNotBeFound("memAarMr.contains=" + UPDATED_MEM_AAR_MR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByMemAarMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where memAarMr does not contain DEFAULT_MEM_AAR_MR
        defaultKamalCropShouldNotBeFound("memAarMr.doesNotContain=" + DEFAULT_MEM_AAR_MR);

        // Get all the kamalCropList where memAarMr does not contain UPDATED_MEM_AAR_MR
        defaultKamalCropShouldBeFound("memAarMr.doesNotContain=" + UPDATED_MEM_AAR_MR);
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
            .andExpect(jsonPath("$.[*].memNo").value(hasItem(DEFAULT_MEM_NO)))
            .andExpect(jsonPath("$.[*].memHector").value(hasItem(DEFAULT_MEM_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].memNoMr").value(hasItem(DEFAULT_MEM_NO_MR)))
            .andExpect(jsonPath("$.[*].memHectorMr").value(hasItem(DEFAULT_MEM_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].memAar").value(hasItem(DEFAULT_MEM_AAR.doubleValue())))
            .andExpect(jsonPath("$.[*].memAarMr").value(hasItem(DEFAULT_MEM_AAR_MR)));

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
            .memNo(UPDATED_MEM_NO)
            .memHector(UPDATED_MEM_HECTOR)
            .memNoMr(UPDATED_MEM_NO_MR)
            .memHectorMr(UPDATED_MEM_HECTOR_MR)
            .memAar(UPDATED_MEM_AAR)
            .memAarMr(UPDATED_MEM_AAR_MR);

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
        assertThat(testKamalCrop.getMemNo()).isEqualTo(UPDATED_MEM_NO);
        assertThat(testKamalCrop.getMemHector()).isEqualTo(UPDATED_MEM_HECTOR);
        assertThat(testKamalCrop.getMemNoMr()).isEqualTo(UPDATED_MEM_NO_MR);
        assertThat(testKamalCrop.getMemHectorMr()).isEqualTo(UPDATED_MEM_HECTOR_MR);
        assertThat(testKamalCrop.getMemAar()).isEqualTo(UPDATED_MEM_AAR);
        assertThat(testKamalCrop.getMemAarMr()).isEqualTo(UPDATED_MEM_AAR_MR);
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
            .memNo(UPDATED_MEM_NO)
            .memNoMr(UPDATED_MEM_NO_MR)
            .memAar(UPDATED_MEM_AAR)
            .memAarMr(UPDATED_MEM_AAR_MR);

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
        assertThat(testKamalCrop.getMemNo()).isEqualTo(UPDATED_MEM_NO);
        assertThat(testKamalCrop.getMemHector()).isEqualTo(DEFAULT_MEM_HECTOR);
        assertThat(testKamalCrop.getMemNoMr()).isEqualTo(UPDATED_MEM_NO_MR);
        assertThat(testKamalCrop.getMemHectorMr()).isEqualTo(DEFAULT_MEM_HECTOR_MR);
        assertThat(testKamalCrop.getMemAar()).isEqualTo(UPDATED_MEM_AAR);
        assertThat(testKamalCrop.getMemAarMr()).isEqualTo(UPDATED_MEM_AAR_MR);
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
            .memNo(UPDATED_MEM_NO)
            .memHector(UPDATED_MEM_HECTOR)
            .memNoMr(UPDATED_MEM_NO_MR)
            .memHectorMr(UPDATED_MEM_HECTOR_MR)
            .memAar(UPDATED_MEM_AAR)
            .memAarMr(UPDATED_MEM_AAR_MR);

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
        assertThat(testKamalCrop.getMemNo()).isEqualTo(UPDATED_MEM_NO);
        assertThat(testKamalCrop.getMemHector()).isEqualTo(UPDATED_MEM_HECTOR);
        assertThat(testKamalCrop.getMemNoMr()).isEqualTo(UPDATED_MEM_NO_MR);
        assertThat(testKamalCrop.getMemHectorMr()).isEqualTo(UPDATED_MEM_HECTOR_MR);
        assertThat(testKamalCrop.getMemAar()).isEqualTo(UPDATED_MEM_AAR);
        assertThat(testKamalCrop.getMemAarMr()).isEqualTo(UPDATED_MEM_AAR_MR);
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
