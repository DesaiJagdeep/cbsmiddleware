package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KamalMaryadaPatrak;
import com.cbs.middleware.repository.KamalMaryadaPatrakRepository;
import com.cbs.middleware.service.criteria.KamalMaryadaPatrakCriteria;
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
 * Integration tests for the {@link KamalMaryadaPatrakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KamalMaryadaPatrakResourceIT {

    private static final String DEFAULT_CROP_LOAN = "AAAAAAAAAA";
    private static final String UPDATED_CROP_LOAN = "BBBBBBBBBB";

    private static final String DEFAULT_KM_CHART = "AAAAAAAAAA";
    private static final String UPDATED_KM_CHART = "BBBBBBBBBB";

    private static final String DEFAULT_DEMAND = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND = "BBBBBBBBBB";

    private static final String DEFAULT_KM_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_KM_SUMMARY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_KM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_KM_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_KM_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO_KM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_KM_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO_KM_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_COVER_PAGE = false;
    private static final Boolean UPDATED_COVER_PAGE = true;

    private static final String DEFAULT_CROP_TYPE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CROP_TYPE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CROP_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FROM_HECTOR = "AAAAAAAAAA";
    private static final String UPDATED_FROM_HECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_TO_HECTOR = "AAAAAAAAAA";
    private static final String UPDATED_TO_HECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULTER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUCHAK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUCHAK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ANUMODAK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ANUMODAK_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MEETING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MEETING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MEETING_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_RESOLUTION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kamal-maryada-patraks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KamalMaryadaPatrakRepository kamalMaryadaPatrakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKamalMaryadaPatrakMockMvc;

    private KamalMaryadaPatrak kamalMaryadaPatrak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalMaryadaPatrak createEntity(EntityManager em) {
        KamalMaryadaPatrak kamalMaryadaPatrak = new KamalMaryadaPatrak()
            .cropLoan(DEFAULT_CROP_LOAN)
            .kmChart(DEFAULT_KM_CHART)
            .demand(DEFAULT_DEMAND)
            .kmSummary(DEFAULT_KM_SUMMARY)
            .kmDate(DEFAULT_KM_DATE)
            .toKMDate(DEFAULT_TO_KM_DATE)
            .coverPage(DEFAULT_COVER_PAGE)
            .cropTypeNumber(DEFAULT_CROP_TYPE_NUMBER)
            .cropType(DEFAULT_CROP_TYPE)
            .fromHector(DEFAULT_FROM_HECTOR)
            .toHector(DEFAULT_TO_HECTOR)
            .defaulterName(DEFAULT_DEFAULTER_NAME)
            .suchakName(DEFAULT_SUCHAK_NAME)
            .anumodakName(DEFAULT_ANUMODAK_NAME)
            .meetingDate(DEFAULT_MEETING_DATE)
            .resolutionNumber(DEFAULT_RESOLUTION_NUMBER);
        return kamalMaryadaPatrak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalMaryadaPatrak createUpdatedEntity(EntityManager em) {
        KamalMaryadaPatrak kamalMaryadaPatrak = new KamalMaryadaPatrak()
            .cropLoan(UPDATED_CROP_LOAN)
            .kmChart(UPDATED_KM_CHART)
            .demand(UPDATED_DEMAND)
            .kmSummary(UPDATED_KM_SUMMARY)
            .kmDate(UPDATED_KM_DATE)
            .toKMDate(UPDATED_TO_KM_DATE)
            .coverPage(UPDATED_COVER_PAGE)
            .cropTypeNumber(UPDATED_CROP_TYPE_NUMBER)
            .cropType(UPDATED_CROP_TYPE)
            .fromHector(UPDATED_FROM_HECTOR)
            .toHector(UPDATED_TO_HECTOR)
            .defaulterName(UPDATED_DEFAULTER_NAME)
            .suchakName(UPDATED_SUCHAK_NAME)
            .anumodakName(UPDATED_ANUMODAK_NAME)
            .meetingDate(UPDATED_MEETING_DATE)
            .resolutionNumber(UPDATED_RESOLUTION_NUMBER);
        return kamalMaryadaPatrak;
    }

    @BeforeEach
    public void initTest() {
        kamalMaryadaPatrak = createEntity(em);
    }

    @Test
    @Transactional
    void createKamalMaryadaPatrak() throws Exception {
        int databaseSizeBeforeCreate = kamalMaryadaPatrakRepository.findAll().size();
        // Create the KamalMaryadaPatrak
        restKamalMaryadaPatrakMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalMaryadaPatrak))
            )
            .andExpect(status().isCreated());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeCreate + 1);
        KamalMaryadaPatrak testKamalMaryadaPatrak = kamalMaryadaPatrakList.get(kamalMaryadaPatrakList.size() - 1);
        assertThat(testKamalMaryadaPatrak.getCropLoan()).isEqualTo(DEFAULT_CROP_LOAN);
        assertThat(testKamalMaryadaPatrak.getKmChart()).isEqualTo(DEFAULT_KM_CHART);
        assertThat(testKamalMaryadaPatrak.getDemand()).isEqualTo(DEFAULT_DEMAND);
        assertThat(testKamalMaryadaPatrak.getKmSummary()).isEqualTo(DEFAULT_KM_SUMMARY);
        assertThat(testKamalMaryadaPatrak.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
        assertThat(testKamalMaryadaPatrak.getToKMDate()).isEqualTo(DEFAULT_TO_KM_DATE);
        assertThat(testKamalMaryadaPatrak.getCoverPage()).isEqualTo(DEFAULT_COVER_PAGE);
        assertThat(testKamalMaryadaPatrak.getCropTypeNumber()).isEqualTo(DEFAULT_CROP_TYPE_NUMBER);
        assertThat(testKamalMaryadaPatrak.getCropType()).isEqualTo(DEFAULT_CROP_TYPE);
        assertThat(testKamalMaryadaPatrak.getFromHector()).isEqualTo(DEFAULT_FROM_HECTOR);
        assertThat(testKamalMaryadaPatrak.getToHector()).isEqualTo(DEFAULT_TO_HECTOR);
        assertThat(testKamalMaryadaPatrak.getDefaulterName()).isEqualTo(DEFAULT_DEFAULTER_NAME);
        assertThat(testKamalMaryadaPatrak.getSuchakName()).isEqualTo(DEFAULT_SUCHAK_NAME);
        assertThat(testKamalMaryadaPatrak.getAnumodakName()).isEqualTo(DEFAULT_ANUMODAK_NAME);
        assertThat(testKamalMaryadaPatrak.getMeetingDate()).isEqualTo(DEFAULT_MEETING_DATE);
        assertThat(testKamalMaryadaPatrak.getResolutionNumber()).isEqualTo(DEFAULT_RESOLUTION_NUMBER);
    }

    @Test
    @Transactional
    void createKamalMaryadaPatrakWithExistingId() throws Exception {
        // Create the KamalMaryadaPatrak with an existing ID
        kamalMaryadaPatrak.setId(1L);

        int databaseSizeBeforeCreate = kamalMaryadaPatrakRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKamalMaryadaPatrakMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalMaryadaPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraks() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList
        restKamalMaryadaPatrakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalMaryadaPatrak.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropLoan").value(hasItem(DEFAULT_CROP_LOAN)))
            .andExpect(jsonPath("$.[*].kmChart").value(hasItem(DEFAULT_KM_CHART)))
            .andExpect(jsonPath("$.[*].demand").value(hasItem(DEFAULT_DEMAND)))
            .andExpect(jsonPath("$.[*].kmSummary").value(hasItem(DEFAULT_KM_SUMMARY)))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toKMDate").value(hasItem(DEFAULT_TO_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].coverPage").value(hasItem(DEFAULT_COVER_PAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].cropTypeNumber").value(hasItem(DEFAULT_CROP_TYPE_NUMBER)))
            .andExpect(jsonPath("$.[*].cropType").value(hasItem(DEFAULT_CROP_TYPE)))
            .andExpect(jsonPath("$.[*].fromHector").value(hasItem(DEFAULT_FROM_HECTOR)))
            .andExpect(jsonPath("$.[*].toHector").value(hasItem(DEFAULT_TO_HECTOR)))
            .andExpect(jsonPath("$.[*].defaulterName").value(hasItem(DEFAULT_DEFAULTER_NAME)))
            .andExpect(jsonPath("$.[*].suchakName").value(hasItem(DEFAULT_SUCHAK_NAME)))
            .andExpect(jsonPath("$.[*].anumodakName").value(hasItem(DEFAULT_ANUMODAK_NAME)))
            .andExpect(jsonPath("$.[*].meetingDate").value(hasItem(DEFAULT_MEETING_DATE.toString())))
            .andExpect(jsonPath("$.[*].resolutionNumber").value(hasItem(DEFAULT_RESOLUTION_NUMBER)));
    }

    @Test
    @Transactional
    void getKamalMaryadaPatrak() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get the kamalMaryadaPatrak
        restKamalMaryadaPatrakMockMvc
            .perform(get(ENTITY_API_URL_ID, kamalMaryadaPatrak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kamalMaryadaPatrak.getId().intValue()))
            .andExpect(jsonPath("$.cropLoan").value(DEFAULT_CROP_LOAN))
            .andExpect(jsonPath("$.kmChart").value(DEFAULT_KM_CHART))
            .andExpect(jsonPath("$.demand").value(DEFAULT_DEMAND))
            .andExpect(jsonPath("$.kmSummary").value(DEFAULT_KM_SUMMARY))
            .andExpect(jsonPath("$.kmDate").value(DEFAULT_KM_DATE.toString()))
            .andExpect(jsonPath("$.toKMDate").value(DEFAULT_TO_KM_DATE.toString()))
            .andExpect(jsonPath("$.coverPage").value(DEFAULT_COVER_PAGE.booleanValue()))
            .andExpect(jsonPath("$.cropTypeNumber").value(DEFAULT_CROP_TYPE_NUMBER))
            .andExpect(jsonPath("$.cropType").value(DEFAULT_CROP_TYPE))
            .andExpect(jsonPath("$.fromHector").value(DEFAULT_FROM_HECTOR))
            .andExpect(jsonPath("$.toHector").value(DEFAULT_TO_HECTOR))
            .andExpect(jsonPath("$.defaulterName").value(DEFAULT_DEFAULTER_NAME))
            .andExpect(jsonPath("$.suchakName").value(DEFAULT_SUCHAK_NAME))
            .andExpect(jsonPath("$.anumodakName").value(DEFAULT_ANUMODAK_NAME))
            .andExpect(jsonPath("$.meetingDate").value(DEFAULT_MEETING_DATE.toString()))
            .andExpect(jsonPath("$.resolutionNumber").value(DEFAULT_RESOLUTION_NUMBER));
    }

    @Test
    @Transactional
    void getKamalMaryadaPatraksByIdFiltering() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        Long id = kamalMaryadaPatrak.getId();

        defaultKamalMaryadaPatrakShouldBeFound("id.equals=" + id);
        defaultKamalMaryadaPatrakShouldNotBeFound("id.notEquals=" + id);

        defaultKamalMaryadaPatrakShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKamalMaryadaPatrakShouldNotBeFound("id.greaterThan=" + id);

        defaultKamalMaryadaPatrakShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKamalMaryadaPatrakShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropLoan equals to DEFAULT_CROP_LOAN
        defaultKamalMaryadaPatrakShouldBeFound("cropLoan.equals=" + DEFAULT_CROP_LOAN);

        // Get all the kamalMaryadaPatrakList where cropLoan equals to UPDATED_CROP_LOAN
        defaultKamalMaryadaPatrakShouldNotBeFound("cropLoan.equals=" + UPDATED_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropLoanIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropLoan in DEFAULT_CROP_LOAN or UPDATED_CROP_LOAN
        defaultKamalMaryadaPatrakShouldBeFound("cropLoan.in=" + DEFAULT_CROP_LOAN + "," + UPDATED_CROP_LOAN);

        // Get all the kamalMaryadaPatrakList where cropLoan equals to UPDATED_CROP_LOAN
        defaultKamalMaryadaPatrakShouldNotBeFound("cropLoan.in=" + UPDATED_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropLoan is not null
        defaultKamalMaryadaPatrakShouldBeFound("cropLoan.specified=true");

        // Get all the kamalMaryadaPatrakList where cropLoan is null
        defaultKamalMaryadaPatrakShouldNotBeFound("cropLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropLoanContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropLoan contains DEFAULT_CROP_LOAN
        defaultKamalMaryadaPatrakShouldBeFound("cropLoan.contains=" + DEFAULT_CROP_LOAN);

        // Get all the kamalMaryadaPatrakList where cropLoan contains UPDATED_CROP_LOAN
        defaultKamalMaryadaPatrakShouldNotBeFound("cropLoan.contains=" + UPDATED_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropLoanNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropLoan does not contain DEFAULT_CROP_LOAN
        defaultKamalMaryadaPatrakShouldNotBeFound("cropLoan.doesNotContain=" + DEFAULT_CROP_LOAN);

        // Get all the kamalMaryadaPatrakList where cropLoan does not contain UPDATED_CROP_LOAN
        defaultKamalMaryadaPatrakShouldBeFound("cropLoan.doesNotContain=" + UPDATED_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmChartIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmChart equals to DEFAULT_KM_CHART
        defaultKamalMaryadaPatrakShouldBeFound("kmChart.equals=" + DEFAULT_KM_CHART);

        // Get all the kamalMaryadaPatrakList where kmChart equals to UPDATED_KM_CHART
        defaultKamalMaryadaPatrakShouldNotBeFound("kmChart.equals=" + UPDATED_KM_CHART);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmChartIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmChart in DEFAULT_KM_CHART or UPDATED_KM_CHART
        defaultKamalMaryadaPatrakShouldBeFound("kmChart.in=" + DEFAULT_KM_CHART + "," + UPDATED_KM_CHART);

        // Get all the kamalMaryadaPatrakList where kmChart equals to UPDATED_KM_CHART
        defaultKamalMaryadaPatrakShouldNotBeFound("kmChart.in=" + UPDATED_KM_CHART);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmChartIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmChart is not null
        defaultKamalMaryadaPatrakShouldBeFound("kmChart.specified=true");

        // Get all the kamalMaryadaPatrakList where kmChart is null
        defaultKamalMaryadaPatrakShouldNotBeFound("kmChart.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmChartContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmChart contains DEFAULT_KM_CHART
        defaultKamalMaryadaPatrakShouldBeFound("kmChart.contains=" + DEFAULT_KM_CHART);

        // Get all the kamalMaryadaPatrakList where kmChart contains UPDATED_KM_CHART
        defaultKamalMaryadaPatrakShouldNotBeFound("kmChart.contains=" + UPDATED_KM_CHART);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmChartNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmChart does not contain DEFAULT_KM_CHART
        defaultKamalMaryadaPatrakShouldNotBeFound("kmChart.doesNotContain=" + DEFAULT_KM_CHART);

        // Get all the kamalMaryadaPatrakList where kmChart does not contain UPDATED_KM_CHART
        defaultKamalMaryadaPatrakShouldBeFound("kmChart.doesNotContain=" + UPDATED_KM_CHART);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDemandIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where demand equals to DEFAULT_DEMAND
        defaultKamalMaryadaPatrakShouldBeFound("demand.equals=" + DEFAULT_DEMAND);

        // Get all the kamalMaryadaPatrakList where demand equals to UPDATED_DEMAND
        defaultKamalMaryadaPatrakShouldNotBeFound("demand.equals=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDemandIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where demand in DEFAULT_DEMAND or UPDATED_DEMAND
        defaultKamalMaryadaPatrakShouldBeFound("demand.in=" + DEFAULT_DEMAND + "," + UPDATED_DEMAND);

        // Get all the kamalMaryadaPatrakList where demand equals to UPDATED_DEMAND
        defaultKamalMaryadaPatrakShouldNotBeFound("demand.in=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDemandIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where demand is not null
        defaultKamalMaryadaPatrakShouldBeFound("demand.specified=true");

        // Get all the kamalMaryadaPatrakList where demand is null
        defaultKamalMaryadaPatrakShouldNotBeFound("demand.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDemandContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where demand contains DEFAULT_DEMAND
        defaultKamalMaryadaPatrakShouldBeFound("demand.contains=" + DEFAULT_DEMAND);

        // Get all the kamalMaryadaPatrakList where demand contains UPDATED_DEMAND
        defaultKamalMaryadaPatrakShouldNotBeFound("demand.contains=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDemandNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where demand does not contain DEFAULT_DEMAND
        defaultKamalMaryadaPatrakShouldNotBeFound("demand.doesNotContain=" + DEFAULT_DEMAND);

        // Get all the kamalMaryadaPatrakList where demand does not contain UPDATED_DEMAND
        defaultKamalMaryadaPatrakShouldBeFound("demand.doesNotContain=" + UPDATED_DEMAND);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmSummaryIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmSummary equals to DEFAULT_KM_SUMMARY
        defaultKamalMaryadaPatrakShouldBeFound("kmSummary.equals=" + DEFAULT_KM_SUMMARY);

        // Get all the kamalMaryadaPatrakList where kmSummary equals to UPDATED_KM_SUMMARY
        defaultKamalMaryadaPatrakShouldNotBeFound("kmSummary.equals=" + UPDATED_KM_SUMMARY);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmSummaryIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmSummary in DEFAULT_KM_SUMMARY or UPDATED_KM_SUMMARY
        defaultKamalMaryadaPatrakShouldBeFound("kmSummary.in=" + DEFAULT_KM_SUMMARY + "," + UPDATED_KM_SUMMARY);

        // Get all the kamalMaryadaPatrakList where kmSummary equals to UPDATED_KM_SUMMARY
        defaultKamalMaryadaPatrakShouldNotBeFound("kmSummary.in=" + UPDATED_KM_SUMMARY);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmSummaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmSummary is not null
        defaultKamalMaryadaPatrakShouldBeFound("kmSummary.specified=true");

        // Get all the kamalMaryadaPatrakList where kmSummary is null
        defaultKamalMaryadaPatrakShouldNotBeFound("kmSummary.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmSummaryContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmSummary contains DEFAULT_KM_SUMMARY
        defaultKamalMaryadaPatrakShouldBeFound("kmSummary.contains=" + DEFAULT_KM_SUMMARY);

        // Get all the kamalMaryadaPatrakList where kmSummary contains UPDATED_KM_SUMMARY
        defaultKamalMaryadaPatrakShouldNotBeFound("kmSummary.contains=" + UPDATED_KM_SUMMARY);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmSummaryNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmSummary does not contain DEFAULT_KM_SUMMARY
        defaultKamalMaryadaPatrakShouldNotBeFound("kmSummary.doesNotContain=" + DEFAULT_KM_SUMMARY);

        // Get all the kamalMaryadaPatrakList where kmSummary does not contain UPDATED_KM_SUMMARY
        defaultKamalMaryadaPatrakShouldBeFound("kmSummary.doesNotContain=" + UPDATED_KM_SUMMARY);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmDate equals to DEFAULT_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("kmDate.equals=" + DEFAULT_KM_DATE);

        // Get all the kamalMaryadaPatrakList where kmDate equals to UPDATED_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("kmDate.equals=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmDate in DEFAULT_KM_DATE or UPDATED_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("kmDate.in=" + DEFAULT_KM_DATE + "," + UPDATED_KM_DATE);

        // Get all the kamalMaryadaPatrakList where kmDate equals to UPDATED_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("kmDate.in=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmDate is not null
        defaultKamalMaryadaPatrakShouldBeFound("kmDate.specified=true");

        // Get all the kamalMaryadaPatrakList where kmDate is null
        defaultKamalMaryadaPatrakShouldNotBeFound("kmDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmDate is greater than or equal to DEFAULT_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("kmDate.greaterThanOrEqual=" + DEFAULT_KM_DATE);

        // Get all the kamalMaryadaPatrakList where kmDate is greater than or equal to UPDATED_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("kmDate.greaterThanOrEqual=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmDate is less than or equal to DEFAULT_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("kmDate.lessThanOrEqual=" + DEFAULT_KM_DATE);

        // Get all the kamalMaryadaPatrakList where kmDate is less than or equal to SMALLER_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("kmDate.lessThanOrEqual=" + SMALLER_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmDateIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmDate is less than DEFAULT_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("kmDate.lessThan=" + DEFAULT_KM_DATE);

        // Get all the kamalMaryadaPatrakList where kmDate is less than UPDATED_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("kmDate.lessThan=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByKmDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where kmDate is greater than DEFAULT_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("kmDate.greaterThan=" + DEFAULT_KM_DATE);

        // Get all the kamalMaryadaPatrakList where kmDate is greater than SMALLER_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("kmDate.greaterThan=" + SMALLER_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToKMDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toKMDate equals to DEFAULT_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("toKMDate.equals=" + DEFAULT_TO_KM_DATE);

        // Get all the kamalMaryadaPatrakList where toKMDate equals to UPDATED_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("toKMDate.equals=" + UPDATED_TO_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToKMDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toKMDate in DEFAULT_TO_KM_DATE or UPDATED_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("toKMDate.in=" + DEFAULT_TO_KM_DATE + "," + UPDATED_TO_KM_DATE);

        // Get all the kamalMaryadaPatrakList where toKMDate equals to UPDATED_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("toKMDate.in=" + UPDATED_TO_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToKMDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toKMDate is not null
        defaultKamalMaryadaPatrakShouldBeFound("toKMDate.specified=true");

        // Get all the kamalMaryadaPatrakList where toKMDate is null
        defaultKamalMaryadaPatrakShouldNotBeFound("toKMDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToKMDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toKMDate is greater than or equal to DEFAULT_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("toKMDate.greaterThanOrEqual=" + DEFAULT_TO_KM_DATE);

        // Get all the kamalMaryadaPatrakList where toKMDate is greater than or equal to UPDATED_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("toKMDate.greaterThanOrEqual=" + UPDATED_TO_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToKMDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toKMDate is less than or equal to DEFAULT_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("toKMDate.lessThanOrEqual=" + DEFAULT_TO_KM_DATE);

        // Get all the kamalMaryadaPatrakList where toKMDate is less than or equal to SMALLER_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("toKMDate.lessThanOrEqual=" + SMALLER_TO_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToKMDateIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toKMDate is less than DEFAULT_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("toKMDate.lessThan=" + DEFAULT_TO_KM_DATE);

        // Get all the kamalMaryadaPatrakList where toKMDate is less than UPDATED_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("toKMDate.lessThan=" + UPDATED_TO_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToKMDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toKMDate is greater than DEFAULT_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("toKMDate.greaterThan=" + DEFAULT_TO_KM_DATE);

        // Get all the kamalMaryadaPatrakList where toKMDate is greater than SMALLER_TO_KM_DATE
        defaultKamalMaryadaPatrakShouldBeFound("toKMDate.greaterThan=" + SMALLER_TO_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCoverPageIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where coverPage equals to DEFAULT_COVER_PAGE
        defaultKamalMaryadaPatrakShouldBeFound("coverPage.equals=" + DEFAULT_COVER_PAGE);

        // Get all the kamalMaryadaPatrakList where coverPage equals to UPDATED_COVER_PAGE
        defaultKamalMaryadaPatrakShouldNotBeFound("coverPage.equals=" + UPDATED_COVER_PAGE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCoverPageIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where coverPage in DEFAULT_COVER_PAGE or UPDATED_COVER_PAGE
        defaultKamalMaryadaPatrakShouldBeFound("coverPage.in=" + DEFAULT_COVER_PAGE + "," + UPDATED_COVER_PAGE);

        // Get all the kamalMaryadaPatrakList where coverPage equals to UPDATED_COVER_PAGE
        defaultKamalMaryadaPatrakShouldNotBeFound("coverPage.in=" + UPDATED_COVER_PAGE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCoverPageIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where coverPage is not null
        defaultKamalMaryadaPatrakShouldBeFound("coverPage.specified=true");

        // Get all the kamalMaryadaPatrakList where coverPage is null
        defaultKamalMaryadaPatrakShouldNotBeFound("coverPage.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropTypeNumber equals to DEFAULT_CROP_TYPE_NUMBER
        defaultKamalMaryadaPatrakShouldBeFound("cropTypeNumber.equals=" + DEFAULT_CROP_TYPE_NUMBER);

        // Get all the kamalMaryadaPatrakList where cropTypeNumber equals to UPDATED_CROP_TYPE_NUMBER
        defaultKamalMaryadaPatrakShouldNotBeFound("cropTypeNumber.equals=" + UPDATED_CROP_TYPE_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropTypeNumber in DEFAULT_CROP_TYPE_NUMBER or UPDATED_CROP_TYPE_NUMBER
        defaultKamalMaryadaPatrakShouldBeFound("cropTypeNumber.in=" + DEFAULT_CROP_TYPE_NUMBER + "," + UPDATED_CROP_TYPE_NUMBER);

        // Get all the kamalMaryadaPatrakList where cropTypeNumber equals to UPDATED_CROP_TYPE_NUMBER
        defaultKamalMaryadaPatrakShouldNotBeFound("cropTypeNumber.in=" + UPDATED_CROP_TYPE_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropTypeNumber is not null
        defaultKamalMaryadaPatrakShouldBeFound("cropTypeNumber.specified=true");

        // Get all the kamalMaryadaPatrakList where cropTypeNumber is null
        defaultKamalMaryadaPatrakShouldNotBeFound("cropTypeNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropTypeNumber contains DEFAULT_CROP_TYPE_NUMBER
        defaultKamalMaryadaPatrakShouldBeFound("cropTypeNumber.contains=" + DEFAULT_CROP_TYPE_NUMBER);

        // Get all the kamalMaryadaPatrakList where cropTypeNumber contains UPDATED_CROP_TYPE_NUMBER
        defaultKamalMaryadaPatrakShouldNotBeFound("cropTypeNumber.contains=" + UPDATED_CROP_TYPE_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropTypeNumber does not contain DEFAULT_CROP_TYPE_NUMBER
        defaultKamalMaryadaPatrakShouldNotBeFound("cropTypeNumber.doesNotContain=" + DEFAULT_CROP_TYPE_NUMBER);

        // Get all the kamalMaryadaPatrakList where cropTypeNumber does not contain UPDATED_CROP_TYPE_NUMBER
        defaultKamalMaryadaPatrakShouldBeFound("cropTypeNumber.doesNotContain=" + UPDATED_CROP_TYPE_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropType equals to DEFAULT_CROP_TYPE
        defaultKamalMaryadaPatrakShouldBeFound("cropType.equals=" + DEFAULT_CROP_TYPE);

        // Get all the kamalMaryadaPatrakList where cropType equals to UPDATED_CROP_TYPE
        defaultKamalMaryadaPatrakShouldNotBeFound("cropType.equals=" + UPDATED_CROP_TYPE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropType in DEFAULT_CROP_TYPE or UPDATED_CROP_TYPE
        defaultKamalMaryadaPatrakShouldBeFound("cropType.in=" + DEFAULT_CROP_TYPE + "," + UPDATED_CROP_TYPE);

        // Get all the kamalMaryadaPatrakList where cropType equals to UPDATED_CROP_TYPE
        defaultKamalMaryadaPatrakShouldNotBeFound("cropType.in=" + UPDATED_CROP_TYPE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropType is not null
        defaultKamalMaryadaPatrakShouldBeFound("cropType.specified=true");

        // Get all the kamalMaryadaPatrakList where cropType is null
        defaultKamalMaryadaPatrakShouldNotBeFound("cropType.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropType contains DEFAULT_CROP_TYPE
        defaultKamalMaryadaPatrakShouldBeFound("cropType.contains=" + DEFAULT_CROP_TYPE);

        // Get all the kamalMaryadaPatrakList where cropType contains UPDATED_CROP_TYPE
        defaultKamalMaryadaPatrakShouldNotBeFound("cropType.contains=" + UPDATED_CROP_TYPE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByCropTypeNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where cropType does not contain DEFAULT_CROP_TYPE
        defaultKamalMaryadaPatrakShouldNotBeFound("cropType.doesNotContain=" + DEFAULT_CROP_TYPE);

        // Get all the kamalMaryadaPatrakList where cropType does not contain UPDATED_CROP_TYPE
        defaultKamalMaryadaPatrakShouldBeFound("cropType.doesNotContain=" + UPDATED_CROP_TYPE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByFromHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where fromHector equals to DEFAULT_FROM_HECTOR
        defaultKamalMaryadaPatrakShouldBeFound("fromHector.equals=" + DEFAULT_FROM_HECTOR);

        // Get all the kamalMaryadaPatrakList where fromHector equals to UPDATED_FROM_HECTOR
        defaultKamalMaryadaPatrakShouldNotBeFound("fromHector.equals=" + UPDATED_FROM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByFromHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where fromHector in DEFAULT_FROM_HECTOR or UPDATED_FROM_HECTOR
        defaultKamalMaryadaPatrakShouldBeFound("fromHector.in=" + DEFAULT_FROM_HECTOR + "," + UPDATED_FROM_HECTOR);

        // Get all the kamalMaryadaPatrakList where fromHector equals to UPDATED_FROM_HECTOR
        defaultKamalMaryadaPatrakShouldNotBeFound("fromHector.in=" + UPDATED_FROM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByFromHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where fromHector is not null
        defaultKamalMaryadaPatrakShouldBeFound("fromHector.specified=true");

        // Get all the kamalMaryadaPatrakList where fromHector is null
        defaultKamalMaryadaPatrakShouldNotBeFound("fromHector.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByFromHectorContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where fromHector contains DEFAULT_FROM_HECTOR
        defaultKamalMaryadaPatrakShouldBeFound("fromHector.contains=" + DEFAULT_FROM_HECTOR);

        // Get all the kamalMaryadaPatrakList where fromHector contains UPDATED_FROM_HECTOR
        defaultKamalMaryadaPatrakShouldNotBeFound("fromHector.contains=" + UPDATED_FROM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByFromHectorNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where fromHector does not contain DEFAULT_FROM_HECTOR
        defaultKamalMaryadaPatrakShouldNotBeFound("fromHector.doesNotContain=" + DEFAULT_FROM_HECTOR);

        // Get all the kamalMaryadaPatrakList where fromHector does not contain UPDATED_FROM_HECTOR
        defaultKamalMaryadaPatrakShouldBeFound("fromHector.doesNotContain=" + UPDATED_FROM_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toHector equals to DEFAULT_TO_HECTOR
        defaultKamalMaryadaPatrakShouldBeFound("toHector.equals=" + DEFAULT_TO_HECTOR);

        // Get all the kamalMaryadaPatrakList where toHector equals to UPDATED_TO_HECTOR
        defaultKamalMaryadaPatrakShouldNotBeFound("toHector.equals=" + UPDATED_TO_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toHector in DEFAULT_TO_HECTOR or UPDATED_TO_HECTOR
        defaultKamalMaryadaPatrakShouldBeFound("toHector.in=" + DEFAULT_TO_HECTOR + "," + UPDATED_TO_HECTOR);

        // Get all the kamalMaryadaPatrakList where toHector equals to UPDATED_TO_HECTOR
        defaultKamalMaryadaPatrakShouldNotBeFound("toHector.in=" + UPDATED_TO_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toHector is not null
        defaultKamalMaryadaPatrakShouldBeFound("toHector.specified=true");

        // Get all the kamalMaryadaPatrakList where toHector is null
        defaultKamalMaryadaPatrakShouldNotBeFound("toHector.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToHectorContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toHector contains DEFAULT_TO_HECTOR
        defaultKamalMaryadaPatrakShouldBeFound("toHector.contains=" + DEFAULT_TO_HECTOR);

        // Get all the kamalMaryadaPatrakList where toHector contains UPDATED_TO_HECTOR
        defaultKamalMaryadaPatrakShouldNotBeFound("toHector.contains=" + UPDATED_TO_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByToHectorNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where toHector does not contain DEFAULT_TO_HECTOR
        defaultKamalMaryadaPatrakShouldNotBeFound("toHector.doesNotContain=" + DEFAULT_TO_HECTOR);

        // Get all the kamalMaryadaPatrakList where toHector does not contain UPDATED_TO_HECTOR
        defaultKamalMaryadaPatrakShouldBeFound("toHector.doesNotContain=" + UPDATED_TO_HECTOR);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDefaulterNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where defaulterName equals to DEFAULT_DEFAULTER_NAME
        defaultKamalMaryadaPatrakShouldBeFound("defaulterName.equals=" + DEFAULT_DEFAULTER_NAME);

        // Get all the kamalMaryadaPatrakList where defaulterName equals to UPDATED_DEFAULTER_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("defaulterName.equals=" + UPDATED_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDefaulterNameIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where defaulterName in DEFAULT_DEFAULTER_NAME or UPDATED_DEFAULTER_NAME
        defaultKamalMaryadaPatrakShouldBeFound("defaulterName.in=" + DEFAULT_DEFAULTER_NAME + "," + UPDATED_DEFAULTER_NAME);

        // Get all the kamalMaryadaPatrakList where defaulterName equals to UPDATED_DEFAULTER_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("defaulterName.in=" + UPDATED_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDefaulterNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where defaulterName is not null
        defaultKamalMaryadaPatrakShouldBeFound("defaulterName.specified=true");

        // Get all the kamalMaryadaPatrakList where defaulterName is null
        defaultKamalMaryadaPatrakShouldNotBeFound("defaulterName.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDefaulterNameContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where defaulterName contains DEFAULT_DEFAULTER_NAME
        defaultKamalMaryadaPatrakShouldBeFound("defaulterName.contains=" + DEFAULT_DEFAULTER_NAME);

        // Get all the kamalMaryadaPatrakList where defaulterName contains UPDATED_DEFAULTER_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("defaulterName.contains=" + UPDATED_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByDefaulterNameNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where defaulterName does not contain DEFAULT_DEFAULTER_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("defaulterName.doesNotContain=" + DEFAULT_DEFAULTER_NAME);

        // Get all the kamalMaryadaPatrakList where defaulterName does not contain UPDATED_DEFAULTER_NAME
        defaultKamalMaryadaPatrakShouldBeFound("defaulterName.doesNotContain=" + UPDATED_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksBySuchakNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where suchakName equals to DEFAULT_SUCHAK_NAME
        defaultKamalMaryadaPatrakShouldBeFound("suchakName.equals=" + DEFAULT_SUCHAK_NAME);

        // Get all the kamalMaryadaPatrakList where suchakName equals to UPDATED_SUCHAK_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("suchakName.equals=" + UPDATED_SUCHAK_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksBySuchakNameIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where suchakName in DEFAULT_SUCHAK_NAME or UPDATED_SUCHAK_NAME
        defaultKamalMaryadaPatrakShouldBeFound("suchakName.in=" + DEFAULT_SUCHAK_NAME + "," + UPDATED_SUCHAK_NAME);

        // Get all the kamalMaryadaPatrakList where suchakName equals to UPDATED_SUCHAK_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("suchakName.in=" + UPDATED_SUCHAK_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksBySuchakNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where suchakName is not null
        defaultKamalMaryadaPatrakShouldBeFound("suchakName.specified=true");

        // Get all the kamalMaryadaPatrakList where suchakName is null
        defaultKamalMaryadaPatrakShouldNotBeFound("suchakName.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksBySuchakNameContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where suchakName contains DEFAULT_SUCHAK_NAME
        defaultKamalMaryadaPatrakShouldBeFound("suchakName.contains=" + DEFAULT_SUCHAK_NAME);

        // Get all the kamalMaryadaPatrakList where suchakName contains UPDATED_SUCHAK_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("suchakName.contains=" + UPDATED_SUCHAK_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksBySuchakNameNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where suchakName does not contain DEFAULT_SUCHAK_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("suchakName.doesNotContain=" + DEFAULT_SUCHAK_NAME);

        // Get all the kamalMaryadaPatrakList where suchakName does not contain UPDATED_SUCHAK_NAME
        defaultKamalMaryadaPatrakShouldBeFound("suchakName.doesNotContain=" + UPDATED_SUCHAK_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByAnumodakNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where anumodakName equals to DEFAULT_ANUMODAK_NAME
        defaultKamalMaryadaPatrakShouldBeFound("anumodakName.equals=" + DEFAULT_ANUMODAK_NAME);

        // Get all the kamalMaryadaPatrakList where anumodakName equals to UPDATED_ANUMODAK_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("anumodakName.equals=" + UPDATED_ANUMODAK_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByAnumodakNameIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where anumodakName in DEFAULT_ANUMODAK_NAME or UPDATED_ANUMODAK_NAME
        defaultKamalMaryadaPatrakShouldBeFound("anumodakName.in=" + DEFAULT_ANUMODAK_NAME + "," + UPDATED_ANUMODAK_NAME);

        // Get all the kamalMaryadaPatrakList where anumodakName equals to UPDATED_ANUMODAK_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("anumodakName.in=" + UPDATED_ANUMODAK_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByAnumodakNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where anumodakName is not null
        defaultKamalMaryadaPatrakShouldBeFound("anumodakName.specified=true");

        // Get all the kamalMaryadaPatrakList where anumodakName is null
        defaultKamalMaryadaPatrakShouldNotBeFound("anumodakName.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByAnumodakNameContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where anumodakName contains DEFAULT_ANUMODAK_NAME
        defaultKamalMaryadaPatrakShouldBeFound("anumodakName.contains=" + DEFAULT_ANUMODAK_NAME);

        // Get all the kamalMaryadaPatrakList where anumodakName contains UPDATED_ANUMODAK_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("anumodakName.contains=" + UPDATED_ANUMODAK_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByAnumodakNameNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where anumodakName does not contain DEFAULT_ANUMODAK_NAME
        defaultKamalMaryadaPatrakShouldNotBeFound("anumodakName.doesNotContain=" + DEFAULT_ANUMODAK_NAME);

        // Get all the kamalMaryadaPatrakList where anumodakName does not contain UPDATED_ANUMODAK_NAME
        defaultKamalMaryadaPatrakShouldBeFound("anumodakName.doesNotContain=" + UPDATED_ANUMODAK_NAME);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByMeetingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where meetingDate equals to DEFAULT_MEETING_DATE
        defaultKamalMaryadaPatrakShouldBeFound("meetingDate.equals=" + DEFAULT_MEETING_DATE);

        // Get all the kamalMaryadaPatrakList where meetingDate equals to UPDATED_MEETING_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("meetingDate.equals=" + UPDATED_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByMeetingDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where meetingDate in DEFAULT_MEETING_DATE or UPDATED_MEETING_DATE
        defaultKamalMaryadaPatrakShouldBeFound("meetingDate.in=" + DEFAULT_MEETING_DATE + "," + UPDATED_MEETING_DATE);

        // Get all the kamalMaryadaPatrakList where meetingDate equals to UPDATED_MEETING_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("meetingDate.in=" + UPDATED_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByMeetingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where meetingDate is not null
        defaultKamalMaryadaPatrakShouldBeFound("meetingDate.specified=true");

        // Get all the kamalMaryadaPatrakList where meetingDate is null
        defaultKamalMaryadaPatrakShouldNotBeFound("meetingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByMeetingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where meetingDate is greater than or equal to DEFAULT_MEETING_DATE
        defaultKamalMaryadaPatrakShouldBeFound("meetingDate.greaterThanOrEqual=" + DEFAULT_MEETING_DATE);

        // Get all the kamalMaryadaPatrakList where meetingDate is greater than or equal to UPDATED_MEETING_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("meetingDate.greaterThanOrEqual=" + UPDATED_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByMeetingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where meetingDate is less than or equal to DEFAULT_MEETING_DATE
        defaultKamalMaryadaPatrakShouldBeFound("meetingDate.lessThanOrEqual=" + DEFAULT_MEETING_DATE);

        // Get all the kamalMaryadaPatrakList where meetingDate is less than or equal to SMALLER_MEETING_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("meetingDate.lessThanOrEqual=" + SMALLER_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByMeetingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where meetingDate is less than DEFAULT_MEETING_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("meetingDate.lessThan=" + DEFAULT_MEETING_DATE);

        // Get all the kamalMaryadaPatrakList where meetingDate is less than UPDATED_MEETING_DATE
        defaultKamalMaryadaPatrakShouldBeFound("meetingDate.lessThan=" + UPDATED_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByMeetingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where meetingDate is greater than DEFAULT_MEETING_DATE
        defaultKamalMaryadaPatrakShouldNotBeFound("meetingDate.greaterThan=" + DEFAULT_MEETING_DATE);

        // Get all the kamalMaryadaPatrakList where meetingDate is greater than SMALLER_MEETING_DATE
        defaultKamalMaryadaPatrakShouldBeFound("meetingDate.greaterThan=" + SMALLER_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByResolutionNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where resolutionNumber equals to DEFAULT_RESOLUTION_NUMBER
        defaultKamalMaryadaPatrakShouldBeFound("resolutionNumber.equals=" + DEFAULT_RESOLUTION_NUMBER);

        // Get all the kamalMaryadaPatrakList where resolutionNumber equals to UPDATED_RESOLUTION_NUMBER
        defaultKamalMaryadaPatrakShouldNotBeFound("resolutionNumber.equals=" + UPDATED_RESOLUTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByResolutionNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where resolutionNumber in DEFAULT_RESOLUTION_NUMBER or UPDATED_RESOLUTION_NUMBER
        defaultKamalMaryadaPatrakShouldBeFound("resolutionNumber.in=" + DEFAULT_RESOLUTION_NUMBER + "," + UPDATED_RESOLUTION_NUMBER);

        // Get all the kamalMaryadaPatrakList where resolutionNumber equals to UPDATED_RESOLUTION_NUMBER
        defaultKamalMaryadaPatrakShouldNotBeFound("resolutionNumber.in=" + UPDATED_RESOLUTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByResolutionNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where resolutionNumber is not null
        defaultKamalMaryadaPatrakShouldBeFound("resolutionNumber.specified=true");

        // Get all the kamalMaryadaPatrakList where resolutionNumber is null
        defaultKamalMaryadaPatrakShouldNotBeFound("resolutionNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByResolutionNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where resolutionNumber contains DEFAULT_RESOLUTION_NUMBER
        defaultKamalMaryadaPatrakShouldBeFound("resolutionNumber.contains=" + DEFAULT_RESOLUTION_NUMBER);

        // Get all the kamalMaryadaPatrakList where resolutionNumber contains UPDATED_RESOLUTION_NUMBER
        defaultKamalMaryadaPatrakShouldNotBeFound("resolutionNumber.contains=" + UPDATED_RESOLUTION_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalMaryadaPatraksByResolutionNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        // Get all the kamalMaryadaPatrakList where resolutionNumber does not contain DEFAULT_RESOLUTION_NUMBER
        defaultKamalMaryadaPatrakShouldNotBeFound("resolutionNumber.doesNotContain=" + DEFAULT_RESOLUTION_NUMBER);

        // Get all the kamalMaryadaPatrakList where resolutionNumber does not contain UPDATED_RESOLUTION_NUMBER
        defaultKamalMaryadaPatrakShouldBeFound("resolutionNumber.doesNotContain=" + UPDATED_RESOLUTION_NUMBER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKamalMaryadaPatrakShouldBeFound(String filter) throws Exception {
        restKamalMaryadaPatrakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalMaryadaPatrak.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropLoan").value(hasItem(DEFAULT_CROP_LOAN)))
            .andExpect(jsonPath("$.[*].kmChart").value(hasItem(DEFAULT_KM_CHART)))
            .andExpect(jsonPath("$.[*].demand").value(hasItem(DEFAULT_DEMAND)))
            .andExpect(jsonPath("$.[*].kmSummary").value(hasItem(DEFAULT_KM_SUMMARY)))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toKMDate").value(hasItem(DEFAULT_TO_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].coverPage").value(hasItem(DEFAULT_COVER_PAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].cropTypeNumber").value(hasItem(DEFAULT_CROP_TYPE_NUMBER)))
            .andExpect(jsonPath("$.[*].cropType").value(hasItem(DEFAULT_CROP_TYPE)))
            .andExpect(jsonPath("$.[*].fromHector").value(hasItem(DEFAULT_FROM_HECTOR)))
            .andExpect(jsonPath("$.[*].toHector").value(hasItem(DEFAULT_TO_HECTOR)))
            .andExpect(jsonPath("$.[*].defaulterName").value(hasItem(DEFAULT_DEFAULTER_NAME)))
            .andExpect(jsonPath("$.[*].suchakName").value(hasItem(DEFAULT_SUCHAK_NAME)))
            .andExpect(jsonPath("$.[*].anumodakName").value(hasItem(DEFAULT_ANUMODAK_NAME)))
            .andExpect(jsonPath("$.[*].meetingDate").value(hasItem(DEFAULT_MEETING_DATE.toString())))
            .andExpect(jsonPath("$.[*].resolutionNumber").value(hasItem(DEFAULT_RESOLUTION_NUMBER)));

        // Check, that the count call also returns 1
        restKamalMaryadaPatrakMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKamalMaryadaPatrakShouldNotBeFound(String filter) throws Exception {
        restKamalMaryadaPatrakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKamalMaryadaPatrakMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKamalMaryadaPatrak() throws Exception {
        // Get the kamalMaryadaPatrak
        restKamalMaryadaPatrakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKamalMaryadaPatrak() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        int databaseSizeBeforeUpdate = kamalMaryadaPatrakRepository.findAll().size();

        // Update the kamalMaryadaPatrak
        KamalMaryadaPatrak updatedKamalMaryadaPatrak = kamalMaryadaPatrakRepository.findById(kamalMaryadaPatrak.getId()).get();
        // Disconnect from session so that the updates on updatedKamalMaryadaPatrak are not directly saved in db
        em.detach(updatedKamalMaryadaPatrak);
        updatedKamalMaryadaPatrak
            .cropLoan(UPDATED_CROP_LOAN)
            .kmChart(UPDATED_KM_CHART)
            .demand(UPDATED_DEMAND)
            .kmSummary(UPDATED_KM_SUMMARY)
            .kmDate(UPDATED_KM_DATE)
            .toKMDate(UPDATED_TO_KM_DATE)
            .coverPage(UPDATED_COVER_PAGE)
            .cropTypeNumber(UPDATED_CROP_TYPE_NUMBER)
            .cropType(UPDATED_CROP_TYPE)
            .fromHector(UPDATED_FROM_HECTOR)
            .toHector(UPDATED_TO_HECTOR)
            .defaulterName(UPDATED_DEFAULTER_NAME)
            .suchakName(UPDATED_SUCHAK_NAME)
            .anumodakName(UPDATED_ANUMODAK_NAME)
            .meetingDate(UPDATED_MEETING_DATE)
            .resolutionNumber(UPDATED_RESOLUTION_NUMBER);

        restKamalMaryadaPatrakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKamalMaryadaPatrak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKamalMaryadaPatrak))
            )
            .andExpect(status().isOk());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeUpdate);
        KamalMaryadaPatrak testKamalMaryadaPatrak = kamalMaryadaPatrakList.get(kamalMaryadaPatrakList.size() - 1);
        assertThat(testKamalMaryadaPatrak.getCropLoan()).isEqualTo(UPDATED_CROP_LOAN);
        assertThat(testKamalMaryadaPatrak.getKmChart()).isEqualTo(UPDATED_KM_CHART);
        assertThat(testKamalMaryadaPatrak.getDemand()).isEqualTo(UPDATED_DEMAND);
        assertThat(testKamalMaryadaPatrak.getKmSummary()).isEqualTo(UPDATED_KM_SUMMARY);
        assertThat(testKamalMaryadaPatrak.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKamalMaryadaPatrak.getToKMDate()).isEqualTo(UPDATED_TO_KM_DATE);
        assertThat(testKamalMaryadaPatrak.getCoverPage()).isEqualTo(UPDATED_COVER_PAGE);
        assertThat(testKamalMaryadaPatrak.getCropTypeNumber()).isEqualTo(UPDATED_CROP_TYPE_NUMBER);
        assertThat(testKamalMaryadaPatrak.getCropType()).isEqualTo(UPDATED_CROP_TYPE);
        assertThat(testKamalMaryadaPatrak.getFromHector()).isEqualTo(UPDATED_FROM_HECTOR);
        assertThat(testKamalMaryadaPatrak.getToHector()).isEqualTo(UPDATED_TO_HECTOR);
        assertThat(testKamalMaryadaPatrak.getDefaulterName()).isEqualTo(UPDATED_DEFAULTER_NAME);
        assertThat(testKamalMaryadaPatrak.getSuchakName()).isEqualTo(UPDATED_SUCHAK_NAME);
        assertThat(testKamalMaryadaPatrak.getAnumodakName()).isEqualTo(UPDATED_ANUMODAK_NAME);
        assertThat(testKamalMaryadaPatrak.getMeetingDate()).isEqualTo(UPDATED_MEETING_DATE);
        assertThat(testKamalMaryadaPatrak.getResolutionNumber()).isEqualTo(UPDATED_RESOLUTION_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingKamalMaryadaPatrak() throws Exception {
        int databaseSizeBeforeUpdate = kamalMaryadaPatrakRepository.findAll().size();
        kamalMaryadaPatrak.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalMaryadaPatrakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kamalMaryadaPatrak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalMaryadaPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKamalMaryadaPatrak() throws Exception {
        int databaseSizeBeforeUpdate = kamalMaryadaPatrakRepository.findAll().size();
        kamalMaryadaPatrak.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalMaryadaPatrakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalMaryadaPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKamalMaryadaPatrak() throws Exception {
        int databaseSizeBeforeUpdate = kamalMaryadaPatrakRepository.findAll().size();
        kamalMaryadaPatrak.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalMaryadaPatrakMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalMaryadaPatrak))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKamalMaryadaPatrakWithPatch() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        int databaseSizeBeforeUpdate = kamalMaryadaPatrakRepository.findAll().size();

        // Update the kamalMaryadaPatrak using partial update
        KamalMaryadaPatrak partialUpdatedKamalMaryadaPatrak = new KamalMaryadaPatrak();
        partialUpdatedKamalMaryadaPatrak.setId(kamalMaryadaPatrak.getId());

        partialUpdatedKamalMaryadaPatrak
            .cropLoan(UPDATED_CROP_LOAN)
            .kmDate(UPDATED_KM_DATE)
            .cropType(UPDATED_CROP_TYPE)
            .fromHector(UPDATED_FROM_HECTOR)
            .toHector(UPDATED_TO_HECTOR)
            .suchakName(UPDATED_SUCHAK_NAME)
            .anumodakName(UPDATED_ANUMODAK_NAME)
            .meetingDate(UPDATED_MEETING_DATE)
            .resolutionNumber(UPDATED_RESOLUTION_NUMBER);

        restKamalMaryadaPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalMaryadaPatrak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalMaryadaPatrak))
            )
            .andExpect(status().isOk());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeUpdate);
        KamalMaryadaPatrak testKamalMaryadaPatrak = kamalMaryadaPatrakList.get(kamalMaryadaPatrakList.size() - 1);
        assertThat(testKamalMaryadaPatrak.getCropLoan()).isEqualTo(UPDATED_CROP_LOAN);
        assertThat(testKamalMaryadaPatrak.getKmChart()).isEqualTo(DEFAULT_KM_CHART);
        assertThat(testKamalMaryadaPatrak.getDemand()).isEqualTo(DEFAULT_DEMAND);
        assertThat(testKamalMaryadaPatrak.getKmSummary()).isEqualTo(DEFAULT_KM_SUMMARY);
        assertThat(testKamalMaryadaPatrak.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKamalMaryadaPatrak.getToKMDate()).isEqualTo(DEFAULT_TO_KM_DATE);
        assertThat(testKamalMaryadaPatrak.getCoverPage()).isEqualTo(DEFAULT_COVER_PAGE);
        assertThat(testKamalMaryadaPatrak.getCropTypeNumber()).isEqualTo(DEFAULT_CROP_TYPE_NUMBER);
        assertThat(testKamalMaryadaPatrak.getCropType()).isEqualTo(UPDATED_CROP_TYPE);
        assertThat(testKamalMaryadaPatrak.getFromHector()).isEqualTo(UPDATED_FROM_HECTOR);
        assertThat(testKamalMaryadaPatrak.getToHector()).isEqualTo(UPDATED_TO_HECTOR);
        assertThat(testKamalMaryadaPatrak.getDefaulterName()).isEqualTo(DEFAULT_DEFAULTER_NAME);
        assertThat(testKamalMaryadaPatrak.getSuchakName()).isEqualTo(UPDATED_SUCHAK_NAME);
        assertThat(testKamalMaryadaPatrak.getAnumodakName()).isEqualTo(UPDATED_ANUMODAK_NAME);
        assertThat(testKamalMaryadaPatrak.getMeetingDate()).isEqualTo(UPDATED_MEETING_DATE);
        assertThat(testKamalMaryadaPatrak.getResolutionNumber()).isEqualTo(UPDATED_RESOLUTION_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateKamalMaryadaPatrakWithPatch() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        int databaseSizeBeforeUpdate = kamalMaryadaPatrakRepository.findAll().size();

        // Update the kamalMaryadaPatrak using partial update
        KamalMaryadaPatrak partialUpdatedKamalMaryadaPatrak = new KamalMaryadaPatrak();
        partialUpdatedKamalMaryadaPatrak.setId(kamalMaryadaPatrak.getId());

        partialUpdatedKamalMaryadaPatrak
            .cropLoan(UPDATED_CROP_LOAN)
            .kmChart(UPDATED_KM_CHART)
            .demand(UPDATED_DEMAND)
            .kmSummary(UPDATED_KM_SUMMARY)
            .kmDate(UPDATED_KM_DATE)
            .toKMDate(UPDATED_TO_KM_DATE)
            .coverPage(UPDATED_COVER_PAGE)
            .cropTypeNumber(UPDATED_CROP_TYPE_NUMBER)
            .cropType(UPDATED_CROP_TYPE)
            .fromHector(UPDATED_FROM_HECTOR)
            .toHector(UPDATED_TO_HECTOR)
            .defaulterName(UPDATED_DEFAULTER_NAME)
            .suchakName(UPDATED_SUCHAK_NAME)
            .anumodakName(UPDATED_ANUMODAK_NAME)
            .meetingDate(UPDATED_MEETING_DATE)
            .resolutionNumber(UPDATED_RESOLUTION_NUMBER);

        restKamalMaryadaPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalMaryadaPatrak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalMaryadaPatrak))
            )
            .andExpect(status().isOk());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeUpdate);
        KamalMaryadaPatrak testKamalMaryadaPatrak = kamalMaryadaPatrakList.get(kamalMaryadaPatrakList.size() - 1);
        assertThat(testKamalMaryadaPatrak.getCropLoan()).isEqualTo(UPDATED_CROP_LOAN);
        assertThat(testKamalMaryadaPatrak.getKmChart()).isEqualTo(UPDATED_KM_CHART);
        assertThat(testKamalMaryadaPatrak.getDemand()).isEqualTo(UPDATED_DEMAND);
        assertThat(testKamalMaryadaPatrak.getKmSummary()).isEqualTo(UPDATED_KM_SUMMARY);
        assertThat(testKamalMaryadaPatrak.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKamalMaryadaPatrak.getToKMDate()).isEqualTo(UPDATED_TO_KM_DATE);
        assertThat(testKamalMaryadaPatrak.getCoverPage()).isEqualTo(UPDATED_COVER_PAGE);
        assertThat(testKamalMaryadaPatrak.getCropTypeNumber()).isEqualTo(UPDATED_CROP_TYPE_NUMBER);
        assertThat(testKamalMaryadaPatrak.getCropType()).isEqualTo(UPDATED_CROP_TYPE);
        assertThat(testKamalMaryadaPatrak.getFromHector()).isEqualTo(UPDATED_FROM_HECTOR);
        assertThat(testKamalMaryadaPatrak.getToHector()).isEqualTo(UPDATED_TO_HECTOR);
        assertThat(testKamalMaryadaPatrak.getDefaulterName()).isEqualTo(UPDATED_DEFAULTER_NAME);
        assertThat(testKamalMaryadaPatrak.getSuchakName()).isEqualTo(UPDATED_SUCHAK_NAME);
        assertThat(testKamalMaryadaPatrak.getAnumodakName()).isEqualTo(UPDATED_ANUMODAK_NAME);
        assertThat(testKamalMaryadaPatrak.getMeetingDate()).isEqualTo(UPDATED_MEETING_DATE);
        assertThat(testKamalMaryadaPatrak.getResolutionNumber()).isEqualTo(UPDATED_RESOLUTION_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingKamalMaryadaPatrak() throws Exception {
        int databaseSizeBeforeUpdate = kamalMaryadaPatrakRepository.findAll().size();
        kamalMaryadaPatrak.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalMaryadaPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kamalMaryadaPatrak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalMaryadaPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKamalMaryadaPatrak() throws Exception {
        int databaseSizeBeforeUpdate = kamalMaryadaPatrakRepository.findAll().size();
        kamalMaryadaPatrak.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalMaryadaPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalMaryadaPatrak))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKamalMaryadaPatrak() throws Exception {
        int databaseSizeBeforeUpdate = kamalMaryadaPatrakRepository.findAll().size();
        kamalMaryadaPatrak.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalMaryadaPatrakMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalMaryadaPatrak))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalMaryadaPatrak in the database
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKamalMaryadaPatrak() throws Exception {
        // Initialize the database
        kamalMaryadaPatrakRepository.saveAndFlush(kamalMaryadaPatrak);

        int databaseSizeBeforeDelete = kamalMaryadaPatrakRepository.findAll().size();

        // Delete the kamalMaryadaPatrak
        restKamalMaryadaPatrakMockMvc
            .perform(delete(ENTITY_API_URL_ID, kamalMaryadaPatrak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KamalMaryadaPatrak> kamalMaryadaPatrakList = kamalMaryadaPatrakRepository.findAll();
        assertThat(kamalMaryadaPatrakList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
