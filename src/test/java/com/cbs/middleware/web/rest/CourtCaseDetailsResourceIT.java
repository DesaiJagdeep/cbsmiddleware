package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CourtCaseDetails;
import com.cbs.middleware.repository.CourtCaseDetailsRepository;
import com.cbs.middleware.service.criteria.CourtCaseDetailsCriteria;
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
 * Integration tests for the {@link CourtCaseDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourtCaseDetailsResourceIT {

    private static final Long DEFAULT_KRAMANK = 1L;
    private static final Long UPDATED_KRAMANK = 2L;
    private static final Long SMALLER_KRAMANK = 1L - 1L;

    private static final Instant DEFAULT_DINANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DINANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CASE_DINANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CASE_DINANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SABHASAD = "AAAAAAAAAA";
    private static final String UPDATED_SABHASAD = "BBBBBBBBBB";

    private static final String DEFAULT_SABHASAD_ACC_NO = "AAAAAAAAAA";
    private static final String UPDATED_SABHASAD_ACC_NO = "BBBBBBBBBB";

    private static final String DEFAULT_KARJ_PRAKAR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_KARJ_PRAKAR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_KARJ_PRAKAR = "AAAAAAAAAA";
    private static final String UPDATED_KARJ_PRAKAR = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATE_MILALE = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATE_MILALE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CERTIFICATE_DINNANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CERTIFICATE_DINNANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_CERTIFICATE_RAKKAM = 1D;
    private static final Double UPDATED_CERTIFICATE_RAKKAM = 2D;
    private static final Double SMALLER_CERTIFICATE_RAKKAM = 1D - 1D;

    private static final Double DEFAULT_YENEBAKI = 1D;
    private static final Double UPDATED_YENEBAKI = 2D;
    private static final Double SMALLER_YENEBAKI = 1D - 1D;

    private static final Double DEFAULT_VYAJ = 1D;
    private static final Double UPDATED_VYAJ = 2D;
    private static final Double SMALLER_VYAJ = 1D - 1D;

    private static final Double DEFAULT_ETAR = 1D;
    private static final Double UPDATED_ETAR = 2D;
    private static final Double SMALLER_ETAR = 1D - 1D;

    private static final String DEFAULT_DIMMAND_MILALE = "AAAAAAAAAA";
    private static final String UPDATED_DIMMAND_MILALE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DIMMAND_DINNANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DIMMAND_DINNANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_JAPTI_AADHESH = "AAAAAAAAAA";
    private static final String UPDATED_JAPTI_AADHESH = "BBBBBBBBBB";

    private static final Instant DEFAULT_JAPTI_AADHESH_DINNANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JAPTI_AADHESH_DINNANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_STHAVR = 1D;
    private static final Double UPDATED_STHAVR = 2D;
    private static final Double SMALLER_STHAVR = 1D - 1D;

    private static final Double DEFAULT_JANGAM = 1D;
    private static final Double UPDATED_JANGAM = 2D;
    private static final Double SMALLER_JANGAM = 1D - 1D;

    private static final String DEFAULT_VIKRI_AADHESH = "AAAAAAAAAA";
    private static final String UPDATED_VIKRI_AADHESH = "BBBBBBBBBB";

    private static final Instant DEFAULT_VIKRI_ADDHESH_DINNANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VIKRI_ADDHESH_DINNANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ETAR_TAPSHIL = "AAAAAAAAAA";
    private static final String UPDATED_ETAR_TAPSHIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/court-case-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourtCaseDetailsRepository courtCaseDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourtCaseDetailsMockMvc;

    private CourtCaseDetails courtCaseDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCaseDetails createEntity(EntityManager em) {
        CourtCaseDetails courtCaseDetails = new CourtCaseDetails()
            .kramank(DEFAULT_KRAMANK)
            .dinank(DEFAULT_DINANK)
            .caseDinank(DEFAULT_CASE_DINANK)
            .sabhasad(DEFAULT_SABHASAD)
            .sabhasadAccNo(DEFAULT_SABHASAD_ACC_NO)
            .karjPrakarType(DEFAULT_KARJ_PRAKAR_TYPE)
            .karjPrakar(DEFAULT_KARJ_PRAKAR)
            .certificateMilale(DEFAULT_CERTIFICATE_MILALE)
            .certificateDinnank(DEFAULT_CERTIFICATE_DINNANK)
            .certificateRakkam(DEFAULT_CERTIFICATE_RAKKAM)
            .yenebaki(DEFAULT_YENEBAKI)
            .vyaj(DEFAULT_VYAJ)
            .etar(DEFAULT_ETAR)
            .dimmandMilale(DEFAULT_DIMMAND_MILALE)
            .dimmandDinnank(DEFAULT_DIMMAND_DINNANK)
            .japtiAadhesh(DEFAULT_JAPTI_AADHESH)
            .japtiAadheshDinnank(DEFAULT_JAPTI_AADHESH_DINNANK)
            .sthavr(DEFAULT_STHAVR)
            .jangam(DEFAULT_JANGAM)
            .vikriAadhesh(DEFAULT_VIKRI_AADHESH)
            .vikriAddheshDinnank(DEFAULT_VIKRI_ADDHESH_DINNANK)
            .etarTapshil(DEFAULT_ETAR_TAPSHIL);
        return courtCaseDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCaseDetails createUpdatedEntity(EntityManager em) {
        CourtCaseDetails courtCaseDetails = new CourtCaseDetails()
            .kramank(UPDATED_KRAMANK)
            .dinank(UPDATED_DINANK)
            .caseDinank(UPDATED_CASE_DINANK)
            .sabhasad(UPDATED_SABHASAD)
            .sabhasadAccNo(UPDATED_SABHASAD_ACC_NO)
            .karjPrakarType(UPDATED_KARJ_PRAKAR_TYPE)
            .karjPrakar(UPDATED_KARJ_PRAKAR)
            .certificateMilale(UPDATED_CERTIFICATE_MILALE)
            .certificateDinnank(UPDATED_CERTIFICATE_DINNANK)
            .certificateRakkam(UPDATED_CERTIFICATE_RAKKAM)
            .yenebaki(UPDATED_YENEBAKI)
            .vyaj(UPDATED_VYAJ)
            .etar(UPDATED_ETAR)
            .dimmandMilale(UPDATED_DIMMAND_MILALE)
            .dimmandDinnank(UPDATED_DIMMAND_DINNANK)
            .japtiAadhesh(UPDATED_JAPTI_AADHESH)
            .japtiAadheshDinnank(UPDATED_JAPTI_AADHESH_DINNANK)
            .sthavr(UPDATED_STHAVR)
            .jangam(UPDATED_JANGAM)
            .vikriAadhesh(UPDATED_VIKRI_AADHESH)
            .vikriAddheshDinnank(UPDATED_VIKRI_ADDHESH_DINNANK)
            .etarTapshil(UPDATED_ETAR_TAPSHIL);
        return courtCaseDetails;
    }

    @BeforeEach
    public void initTest() {
        courtCaseDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createCourtCaseDetails() throws Exception {
        int databaseSizeBeforeCreate = courtCaseDetailsRepository.findAll().size();
        // Create the CourtCaseDetails
        restCourtCaseDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseDetails))
            )
            .andExpect(status().isCreated());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CourtCaseDetails testCourtCaseDetails = courtCaseDetailsList.get(courtCaseDetailsList.size() - 1);
        assertThat(testCourtCaseDetails.getKramank()).isEqualTo(DEFAULT_KRAMANK);
        assertThat(testCourtCaseDetails.getDinank()).isEqualTo(DEFAULT_DINANK);
        assertThat(testCourtCaseDetails.getCaseDinank()).isEqualTo(DEFAULT_CASE_DINANK);
        assertThat(testCourtCaseDetails.getSabhasad()).isEqualTo(DEFAULT_SABHASAD);
        assertThat(testCourtCaseDetails.getSabhasadAccNo()).isEqualTo(DEFAULT_SABHASAD_ACC_NO);
        assertThat(testCourtCaseDetails.getKarjPrakarType()).isEqualTo(DEFAULT_KARJ_PRAKAR_TYPE);
        assertThat(testCourtCaseDetails.getKarjPrakar()).isEqualTo(DEFAULT_KARJ_PRAKAR);
        assertThat(testCourtCaseDetails.getCertificateMilale()).isEqualTo(DEFAULT_CERTIFICATE_MILALE);
        assertThat(testCourtCaseDetails.getCertificateDinnank()).isEqualTo(DEFAULT_CERTIFICATE_DINNANK);
        assertThat(testCourtCaseDetails.getCertificateRakkam()).isEqualTo(DEFAULT_CERTIFICATE_RAKKAM);
        assertThat(testCourtCaseDetails.getYenebaki()).isEqualTo(DEFAULT_YENEBAKI);
        assertThat(testCourtCaseDetails.getVyaj()).isEqualTo(DEFAULT_VYAJ);
        assertThat(testCourtCaseDetails.getEtar()).isEqualTo(DEFAULT_ETAR);
        assertThat(testCourtCaseDetails.getDimmandMilale()).isEqualTo(DEFAULT_DIMMAND_MILALE);
        assertThat(testCourtCaseDetails.getDimmandDinnank()).isEqualTo(DEFAULT_DIMMAND_DINNANK);
        assertThat(testCourtCaseDetails.getJaptiAadhesh()).isEqualTo(DEFAULT_JAPTI_AADHESH);
        assertThat(testCourtCaseDetails.getJaptiAadheshDinnank()).isEqualTo(DEFAULT_JAPTI_AADHESH_DINNANK);
        assertThat(testCourtCaseDetails.getSthavr()).isEqualTo(DEFAULT_STHAVR);
        assertThat(testCourtCaseDetails.getJangam()).isEqualTo(DEFAULT_JANGAM);
        assertThat(testCourtCaseDetails.getVikriAadhesh()).isEqualTo(DEFAULT_VIKRI_AADHESH);
        assertThat(testCourtCaseDetails.getVikriAddheshDinnank()).isEqualTo(DEFAULT_VIKRI_ADDHESH_DINNANK);
        assertThat(testCourtCaseDetails.getEtarTapshil()).isEqualTo(DEFAULT_ETAR_TAPSHIL);
    }

    @Test
    @Transactional
    void createCourtCaseDetailsWithExistingId() throws Exception {
        // Create the CourtCaseDetails with an existing ID
        courtCaseDetails.setId(1L);

        int databaseSizeBeforeCreate = courtCaseDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourtCaseDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetails() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList
        restCourtCaseDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCaseDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].kramank").value(hasItem(DEFAULT_KRAMANK.intValue())))
            .andExpect(jsonPath("$.[*].dinank").value(hasItem(DEFAULT_DINANK.toString())))
            .andExpect(jsonPath("$.[*].caseDinank").value(hasItem(DEFAULT_CASE_DINANK.toString())))
            .andExpect(jsonPath("$.[*].sabhasad").value(hasItem(DEFAULT_SABHASAD)))
            .andExpect(jsonPath("$.[*].sabhasadAccNo").value(hasItem(DEFAULT_SABHASAD_ACC_NO)))
            .andExpect(jsonPath("$.[*].karjPrakarType").value(hasItem(DEFAULT_KARJ_PRAKAR_TYPE)))
            .andExpect(jsonPath("$.[*].karjPrakar").value(hasItem(DEFAULT_KARJ_PRAKAR)))
            .andExpect(jsonPath("$.[*].certificateMilale").value(hasItem(DEFAULT_CERTIFICATE_MILALE)))
            .andExpect(jsonPath("$.[*].certificateDinnank").value(hasItem(DEFAULT_CERTIFICATE_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].certificateRakkam").value(hasItem(DEFAULT_CERTIFICATE_RAKKAM.doubleValue())))
            .andExpect(jsonPath("$.[*].yenebaki").value(hasItem(DEFAULT_YENEBAKI.doubleValue())))
            .andExpect(jsonPath("$.[*].vyaj").value(hasItem(DEFAULT_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].etar").value(hasItem(DEFAULT_ETAR.doubleValue())))
            .andExpect(jsonPath("$.[*].dimmandMilale").value(hasItem(DEFAULT_DIMMAND_MILALE)))
            .andExpect(jsonPath("$.[*].dimmandDinnank").value(hasItem(DEFAULT_DIMMAND_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].japtiAadhesh").value(hasItem(DEFAULT_JAPTI_AADHESH)))
            .andExpect(jsonPath("$.[*].japtiAadheshDinnank").value(hasItem(DEFAULT_JAPTI_AADHESH_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].sthavr").value(hasItem(DEFAULT_STHAVR.doubleValue())))
            .andExpect(jsonPath("$.[*].jangam").value(hasItem(DEFAULT_JANGAM.doubleValue())))
            .andExpect(jsonPath("$.[*].vikriAadhesh").value(hasItem(DEFAULT_VIKRI_AADHESH)))
            .andExpect(jsonPath("$.[*].vikriAddheshDinnank").value(hasItem(DEFAULT_VIKRI_ADDHESH_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].etarTapshil").value(hasItem(DEFAULT_ETAR_TAPSHIL)));
    }

    @Test
    @Transactional
    void getCourtCaseDetails() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get the courtCaseDetails
        restCourtCaseDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, courtCaseDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courtCaseDetails.getId().intValue()))
            .andExpect(jsonPath("$.kramank").value(DEFAULT_KRAMANK.intValue()))
            .andExpect(jsonPath("$.dinank").value(DEFAULT_DINANK.toString()))
            .andExpect(jsonPath("$.caseDinank").value(DEFAULT_CASE_DINANK.toString()))
            .andExpect(jsonPath("$.sabhasad").value(DEFAULT_SABHASAD))
            .andExpect(jsonPath("$.sabhasadAccNo").value(DEFAULT_SABHASAD_ACC_NO))
            .andExpect(jsonPath("$.karjPrakarType").value(DEFAULT_KARJ_PRAKAR_TYPE))
            .andExpect(jsonPath("$.karjPrakar").value(DEFAULT_KARJ_PRAKAR))
            .andExpect(jsonPath("$.certificateMilale").value(DEFAULT_CERTIFICATE_MILALE))
            .andExpect(jsonPath("$.certificateDinnank").value(DEFAULT_CERTIFICATE_DINNANK.toString()))
            .andExpect(jsonPath("$.certificateRakkam").value(DEFAULT_CERTIFICATE_RAKKAM.doubleValue()))
            .andExpect(jsonPath("$.yenebaki").value(DEFAULT_YENEBAKI.doubleValue()))
            .andExpect(jsonPath("$.vyaj").value(DEFAULT_VYAJ.doubleValue()))
            .andExpect(jsonPath("$.etar").value(DEFAULT_ETAR.doubleValue()))
            .andExpect(jsonPath("$.dimmandMilale").value(DEFAULT_DIMMAND_MILALE))
            .andExpect(jsonPath("$.dimmandDinnank").value(DEFAULT_DIMMAND_DINNANK.toString()))
            .andExpect(jsonPath("$.japtiAadhesh").value(DEFAULT_JAPTI_AADHESH))
            .andExpect(jsonPath("$.japtiAadheshDinnank").value(DEFAULT_JAPTI_AADHESH_DINNANK.toString()))
            .andExpect(jsonPath("$.sthavr").value(DEFAULT_STHAVR.doubleValue()))
            .andExpect(jsonPath("$.jangam").value(DEFAULT_JANGAM.doubleValue()))
            .andExpect(jsonPath("$.vikriAadhesh").value(DEFAULT_VIKRI_AADHESH))
            .andExpect(jsonPath("$.vikriAddheshDinnank").value(DEFAULT_VIKRI_ADDHESH_DINNANK.toString()))
            .andExpect(jsonPath("$.etarTapshil").value(DEFAULT_ETAR_TAPSHIL));
    }

    @Test
    @Transactional
    void getCourtCaseDetailsByIdFiltering() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        Long id = courtCaseDetails.getId();

        defaultCourtCaseDetailsShouldBeFound("id.equals=" + id);
        defaultCourtCaseDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultCourtCaseDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourtCaseDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultCourtCaseDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourtCaseDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKramankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where kramank equals to DEFAULT_KRAMANK
        defaultCourtCaseDetailsShouldBeFound("kramank.equals=" + DEFAULT_KRAMANK);

        // Get all the courtCaseDetailsList where kramank equals to UPDATED_KRAMANK
        defaultCourtCaseDetailsShouldNotBeFound("kramank.equals=" + UPDATED_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKramankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where kramank in DEFAULT_KRAMANK or UPDATED_KRAMANK
        defaultCourtCaseDetailsShouldBeFound("kramank.in=" + DEFAULT_KRAMANK + "," + UPDATED_KRAMANK);

        // Get all the courtCaseDetailsList where kramank equals to UPDATED_KRAMANK
        defaultCourtCaseDetailsShouldNotBeFound("kramank.in=" + UPDATED_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKramankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where kramank is not null
        defaultCourtCaseDetailsShouldBeFound("kramank.specified=true");

        // Get all the courtCaseDetailsList where kramank is null
        defaultCourtCaseDetailsShouldNotBeFound("kramank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKramankIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where kramank is greater than or equal to DEFAULT_KRAMANK
        defaultCourtCaseDetailsShouldBeFound("kramank.greaterThanOrEqual=" + DEFAULT_KRAMANK);

        // Get all the courtCaseDetailsList where kramank is greater than or equal to UPDATED_KRAMANK
        defaultCourtCaseDetailsShouldNotBeFound("kramank.greaterThanOrEqual=" + UPDATED_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKramankIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where kramank is less than or equal to DEFAULT_KRAMANK
        defaultCourtCaseDetailsShouldBeFound("kramank.lessThanOrEqual=" + DEFAULT_KRAMANK);

        // Get all the courtCaseDetailsList where kramank is less than or equal to SMALLER_KRAMANK
        defaultCourtCaseDetailsShouldNotBeFound("kramank.lessThanOrEqual=" + SMALLER_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKramankIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where kramank is less than DEFAULT_KRAMANK
        defaultCourtCaseDetailsShouldNotBeFound("kramank.lessThan=" + DEFAULT_KRAMANK);

        // Get all the courtCaseDetailsList where kramank is less than UPDATED_KRAMANK
        defaultCourtCaseDetailsShouldBeFound("kramank.lessThan=" + UPDATED_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKramankIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where kramank is greater than DEFAULT_KRAMANK
        defaultCourtCaseDetailsShouldNotBeFound("kramank.greaterThan=" + DEFAULT_KRAMANK);

        // Get all the courtCaseDetailsList where kramank is greater than SMALLER_KRAMANK
        defaultCourtCaseDetailsShouldBeFound("kramank.greaterThan=" + SMALLER_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDinankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dinank equals to DEFAULT_DINANK
        defaultCourtCaseDetailsShouldBeFound("dinank.equals=" + DEFAULT_DINANK);

        // Get all the courtCaseDetailsList where dinank equals to UPDATED_DINANK
        defaultCourtCaseDetailsShouldNotBeFound("dinank.equals=" + UPDATED_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDinankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dinank in DEFAULT_DINANK or UPDATED_DINANK
        defaultCourtCaseDetailsShouldBeFound("dinank.in=" + DEFAULT_DINANK + "," + UPDATED_DINANK);

        // Get all the courtCaseDetailsList where dinank equals to UPDATED_DINANK
        defaultCourtCaseDetailsShouldNotBeFound("dinank.in=" + UPDATED_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDinankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dinank is not null
        defaultCourtCaseDetailsShouldBeFound("dinank.specified=true");

        // Get all the courtCaseDetailsList where dinank is null
        defaultCourtCaseDetailsShouldNotBeFound("dinank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCaseDinankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where caseDinank equals to DEFAULT_CASE_DINANK
        defaultCourtCaseDetailsShouldBeFound("caseDinank.equals=" + DEFAULT_CASE_DINANK);

        // Get all the courtCaseDetailsList where caseDinank equals to UPDATED_CASE_DINANK
        defaultCourtCaseDetailsShouldNotBeFound("caseDinank.equals=" + UPDATED_CASE_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCaseDinankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where caseDinank in DEFAULT_CASE_DINANK or UPDATED_CASE_DINANK
        defaultCourtCaseDetailsShouldBeFound("caseDinank.in=" + DEFAULT_CASE_DINANK + "," + UPDATED_CASE_DINANK);

        // Get all the courtCaseDetailsList where caseDinank equals to UPDATED_CASE_DINANK
        defaultCourtCaseDetailsShouldNotBeFound("caseDinank.in=" + UPDATED_CASE_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCaseDinankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where caseDinank is not null
        defaultCourtCaseDetailsShouldBeFound("caseDinank.specified=true");

        // Get all the courtCaseDetailsList where caseDinank is null
        defaultCourtCaseDetailsShouldNotBeFound("caseDinank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasad equals to DEFAULT_SABHASAD
        defaultCourtCaseDetailsShouldBeFound("sabhasad.equals=" + DEFAULT_SABHASAD);

        // Get all the courtCaseDetailsList where sabhasad equals to UPDATED_SABHASAD
        defaultCourtCaseDetailsShouldNotBeFound("sabhasad.equals=" + UPDATED_SABHASAD);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasad in DEFAULT_SABHASAD or UPDATED_SABHASAD
        defaultCourtCaseDetailsShouldBeFound("sabhasad.in=" + DEFAULT_SABHASAD + "," + UPDATED_SABHASAD);

        // Get all the courtCaseDetailsList where sabhasad equals to UPDATED_SABHASAD
        defaultCourtCaseDetailsShouldNotBeFound("sabhasad.in=" + UPDATED_SABHASAD);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasad is not null
        defaultCourtCaseDetailsShouldBeFound("sabhasad.specified=true");

        // Get all the courtCaseDetailsList where sabhasad is null
        defaultCourtCaseDetailsShouldNotBeFound("sabhasad.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasad contains DEFAULT_SABHASAD
        defaultCourtCaseDetailsShouldBeFound("sabhasad.contains=" + DEFAULT_SABHASAD);

        // Get all the courtCaseDetailsList where sabhasad contains UPDATED_SABHASAD
        defaultCourtCaseDetailsShouldNotBeFound("sabhasad.contains=" + UPDATED_SABHASAD);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasad does not contain DEFAULT_SABHASAD
        defaultCourtCaseDetailsShouldNotBeFound("sabhasad.doesNotContain=" + DEFAULT_SABHASAD);

        // Get all the courtCaseDetailsList where sabhasad does not contain UPDATED_SABHASAD
        defaultCourtCaseDetailsShouldBeFound("sabhasad.doesNotContain=" + UPDATED_SABHASAD);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadAccNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasadAccNo equals to DEFAULT_SABHASAD_ACC_NO
        defaultCourtCaseDetailsShouldBeFound("sabhasadAccNo.equals=" + DEFAULT_SABHASAD_ACC_NO);

        // Get all the courtCaseDetailsList where sabhasadAccNo equals to UPDATED_SABHASAD_ACC_NO
        defaultCourtCaseDetailsShouldNotBeFound("sabhasadAccNo.equals=" + UPDATED_SABHASAD_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadAccNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasadAccNo in DEFAULT_SABHASAD_ACC_NO or UPDATED_SABHASAD_ACC_NO
        defaultCourtCaseDetailsShouldBeFound("sabhasadAccNo.in=" + DEFAULT_SABHASAD_ACC_NO + "," + UPDATED_SABHASAD_ACC_NO);

        // Get all the courtCaseDetailsList where sabhasadAccNo equals to UPDATED_SABHASAD_ACC_NO
        defaultCourtCaseDetailsShouldNotBeFound("sabhasadAccNo.in=" + UPDATED_SABHASAD_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadAccNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasadAccNo is not null
        defaultCourtCaseDetailsShouldBeFound("sabhasadAccNo.specified=true");

        // Get all the courtCaseDetailsList where sabhasadAccNo is null
        defaultCourtCaseDetailsShouldNotBeFound("sabhasadAccNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadAccNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasadAccNo contains DEFAULT_SABHASAD_ACC_NO
        defaultCourtCaseDetailsShouldBeFound("sabhasadAccNo.contains=" + DEFAULT_SABHASAD_ACC_NO);

        // Get all the courtCaseDetailsList where sabhasadAccNo contains UPDATED_SABHASAD_ACC_NO
        defaultCourtCaseDetailsShouldNotBeFound("sabhasadAccNo.contains=" + UPDATED_SABHASAD_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySabhasadAccNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sabhasadAccNo does not contain DEFAULT_SABHASAD_ACC_NO
        defaultCourtCaseDetailsShouldNotBeFound("sabhasadAccNo.doesNotContain=" + DEFAULT_SABHASAD_ACC_NO);

        // Get all the courtCaseDetailsList where sabhasadAccNo does not contain UPDATED_SABHASAD_ACC_NO
        defaultCourtCaseDetailsShouldBeFound("sabhasadAccNo.doesNotContain=" + UPDATED_SABHASAD_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakarType equals to DEFAULT_KARJ_PRAKAR_TYPE
        defaultCourtCaseDetailsShouldBeFound("karjPrakarType.equals=" + DEFAULT_KARJ_PRAKAR_TYPE);

        // Get all the courtCaseDetailsList where karjPrakarType equals to UPDATED_KARJ_PRAKAR_TYPE
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakarType.equals=" + UPDATED_KARJ_PRAKAR_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarTypeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakarType in DEFAULT_KARJ_PRAKAR_TYPE or UPDATED_KARJ_PRAKAR_TYPE
        defaultCourtCaseDetailsShouldBeFound("karjPrakarType.in=" + DEFAULT_KARJ_PRAKAR_TYPE + "," + UPDATED_KARJ_PRAKAR_TYPE);

        // Get all the courtCaseDetailsList where karjPrakarType equals to UPDATED_KARJ_PRAKAR_TYPE
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakarType.in=" + UPDATED_KARJ_PRAKAR_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakarType is not null
        defaultCourtCaseDetailsShouldBeFound("karjPrakarType.specified=true");

        // Get all the courtCaseDetailsList where karjPrakarType is null
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakarType.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarTypeContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakarType contains DEFAULT_KARJ_PRAKAR_TYPE
        defaultCourtCaseDetailsShouldBeFound("karjPrakarType.contains=" + DEFAULT_KARJ_PRAKAR_TYPE);

        // Get all the courtCaseDetailsList where karjPrakarType contains UPDATED_KARJ_PRAKAR_TYPE
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakarType.contains=" + UPDATED_KARJ_PRAKAR_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarTypeNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakarType does not contain DEFAULT_KARJ_PRAKAR_TYPE
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakarType.doesNotContain=" + DEFAULT_KARJ_PRAKAR_TYPE);

        // Get all the courtCaseDetailsList where karjPrakarType does not contain UPDATED_KARJ_PRAKAR_TYPE
        defaultCourtCaseDetailsShouldBeFound("karjPrakarType.doesNotContain=" + UPDATED_KARJ_PRAKAR_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakar equals to DEFAULT_KARJ_PRAKAR
        defaultCourtCaseDetailsShouldBeFound("karjPrakar.equals=" + DEFAULT_KARJ_PRAKAR);

        // Get all the courtCaseDetailsList where karjPrakar equals to UPDATED_KARJ_PRAKAR
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakar.equals=" + UPDATED_KARJ_PRAKAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakar in DEFAULT_KARJ_PRAKAR or UPDATED_KARJ_PRAKAR
        defaultCourtCaseDetailsShouldBeFound("karjPrakar.in=" + DEFAULT_KARJ_PRAKAR + "," + UPDATED_KARJ_PRAKAR);

        // Get all the courtCaseDetailsList where karjPrakar equals to UPDATED_KARJ_PRAKAR
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakar.in=" + UPDATED_KARJ_PRAKAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakar is not null
        defaultCourtCaseDetailsShouldBeFound("karjPrakar.specified=true");

        // Get all the courtCaseDetailsList where karjPrakar is null
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakar.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakar contains DEFAULT_KARJ_PRAKAR
        defaultCourtCaseDetailsShouldBeFound("karjPrakar.contains=" + DEFAULT_KARJ_PRAKAR);

        // Get all the courtCaseDetailsList where karjPrakar contains UPDATED_KARJ_PRAKAR
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakar.contains=" + UPDATED_KARJ_PRAKAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByKarjPrakarNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where karjPrakar does not contain DEFAULT_KARJ_PRAKAR
        defaultCourtCaseDetailsShouldNotBeFound("karjPrakar.doesNotContain=" + DEFAULT_KARJ_PRAKAR);

        // Get all the courtCaseDetailsList where karjPrakar does not contain UPDATED_KARJ_PRAKAR
        defaultCourtCaseDetailsShouldBeFound("karjPrakar.doesNotContain=" + UPDATED_KARJ_PRAKAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateMilaleIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateMilale equals to DEFAULT_CERTIFICATE_MILALE
        defaultCourtCaseDetailsShouldBeFound("certificateMilale.equals=" + DEFAULT_CERTIFICATE_MILALE);

        // Get all the courtCaseDetailsList where certificateMilale equals to UPDATED_CERTIFICATE_MILALE
        defaultCourtCaseDetailsShouldNotBeFound("certificateMilale.equals=" + UPDATED_CERTIFICATE_MILALE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateMilaleIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateMilale in DEFAULT_CERTIFICATE_MILALE or UPDATED_CERTIFICATE_MILALE
        defaultCourtCaseDetailsShouldBeFound("certificateMilale.in=" + DEFAULT_CERTIFICATE_MILALE + "," + UPDATED_CERTIFICATE_MILALE);

        // Get all the courtCaseDetailsList where certificateMilale equals to UPDATED_CERTIFICATE_MILALE
        defaultCourtCaseDetailsShouldNotBeFound("certificateMilale.in=" + UPDATED_CERTIFICATE_MILALE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateMilaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateMilale is not null
        defaultCourtCaseDetailsShouldBeFound("certificateMilale.specified=true");

        // Get all the courtCaseDetailsList where certificateMilale is null
        defaultCourtCaseDetailsShouldNotBeFound("certificateMilale.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateMilaleContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateMilale contains DEFAULT_CERTIFICATE_MILALE
        defaultCourtCaseDetailsShouldBeFound("certificateMilale.contains=" + DEFAULT_CERTIFICATE_MILALE);

        // Get all the courtCaseDetailsList where certificateMilale contains UPDATED_CERTIFICATE_MILALE
        defaultCourtCaseDetailsShouldNotBeFound("certificateMilale.contains=" + UPDATED_CERTIFICATE_MILALE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateMilaleNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateMilale does not contain DEFAULT_CERTIFICATE_MILALE
        defaultCourtCaseDetailsShouldNotBeFound("certificateMilale.doesNotContain=" + DEFAULT_CERTIFICATE_MILALE);

        // Get all the courtCaseDetailsList where certificateMilale does not contain UPDATED_CERTIFICATE_MILALE
        defaultCourtCaseDetailsShouldBeFound("certificateMilale.doesNotContain=" + UPDATED_CERTIFICATE_MILALE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateDinnankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateDinnank equals to DEFAULT_CERTIFICATE_DINNANK
        defaultCourtCaseDetailsShouldBeFound("certificateDinnank.equals=" + DEFAULT_CERTIFICATE_DINNANK);

        // Get all the courtCaseDetailsList where certificateDinnank equals to UPDATED_CERTIFICATE_DINNANK
        defaultCourtCaseDetailsShouldNotBeFound("certificateDinnank.equals=" + UPDATED_CERTIFICATE_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateDinnankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateDinnank in DEFAULT_CERTIFICATE_DINNANK or UPDATED_CERTIFICATE_DINNANK
        defaultCourtCaseDetailsShouldBeFound("certificateDinnank.in=" + DEFAULT_CERTIFICATE_DINNANK + "," + UPDATED_CERTIFICATE_DINNANK);

        // Get all the courtCaseDetailsList where certificateDinnank equals to UPDATED_CERTIFICATE_DINNANK
        defaultCourtCaseDetailsShouldNotBeFound("certificateDinnank.in=" + UPDATED_CERTIFICATE_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateDinnankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateDinnank is not null
        defaultCourtCaseDetailsShouldBeFound("certificateDinnank.specified=true");

        // Get all the courtCaseDetailsList where certificateDinnank is null
        defaultCourtCaseDetailsShouldNotBeFound("certificateDinnank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateRakkamIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateRakkam equals to DEFAULT_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldBeFound("certificateRakkam.equals=" + DEFAULT_CERTIFICATE_RAKKAM);

        // Get all the courtCaseDetailsList where certificateRakkam equals to UPDATED_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldNotBeFound("certificateRakkam.equals=" + UPDATED_CERTIFICATE_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateRakkamIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateRakkam in DEFAULT_CERTIFICATE_RAKKAM or UPDATED_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldBeFound("certificateRakkam.in=" + DEFAULT_CERTIFICATE_RAKKAM + "," + UPDATED_CERTIFICATE_RAKKAM);

        // Get all the courtCaseDetailsList where certificateRakkam equals to UPDATED_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldNotBeFound("certificateRakkam.in=" + UPDATED_CERTIFICATE_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateRakkamIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateRakkam is not null
        defaultCourtCaseDetailsShouldBeFound("certificateRakkam.specified=true");

        // Get all the courtCaseDetailsList where certificateRakkam is null
        defaultCourtCaseDetailsShouldNotBeFound("certificateRakkam.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateRakkamIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateRakkam is greater than or equal to DEFAULT_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldBeFound("certificateRakkam.greaterThanOrEqual=" + DEFAULT_CERTIFICATE_RAKKAM);

        // Get all the courtCaseDetailsList where certificateRakkam is greater than or equal to UPDATED_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldNotBeFound("certificateRakkam.greaterThanOrEqual=" + UPDATED_CERTIFICATE_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateRakkamIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateRakkam is less than or equal to DEFAULT_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldBeFound("certificateRakkam.lessThanOrEqual=" + DEFAULT_CERTIFICATE_RAKKAM);

        // Get all the courtCaseDetailsList where certificateRakkam is less than or equal to SMALLER_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldNotBeFound("certificateRakkam.lessThanOrEqual=" + SMALLER_CERTIFICATE_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateRakkamIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateRakkam is less than DEFAULT_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldNotBeFound("certificateRakkam.lessThan=" + DEFAULT_CERTIFICATE_RAKKAM);

        // Get all the courtCaseDetailsList where certificateRakkam is less than UPDATED_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldBeFound("certificateRakkam.lessThan=" + UPDATED_CERTIFICATE_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByCertificateRakkamIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where certificateRakkam is greater than DEFAULT_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldNotBeFound("certificateRakkam.greaterThan=" + DEFAULT_CERTIFICATE_RAKKAM);

        // Get all the courtCaseDetailsList where certificateRakkam is greater than SMALLER_CERTIFICATE_RAKKAM
        defaultCourtCaseDetailsShouldBeFound("certificateRakkam.greaterThan=" + SMALLER_CERTIFICATE_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByYenebakiIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where yenebaki equals to DEFAULT_YENEBAKI
        defaultCourtCaseDetailsShouldBeFound("yenebaki.equals=" + DEFAULT_YENEBAKI);

        // Get all the courtCaseDetailsList where yenebaki equals to UPDATED_YENEBAKI
        defaultCourtCaseDetailsShouldNotBeFound("yenebaki.equals=" + UPDATED_YENEBAKI);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByYenebakiIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where yenebaki in DEFAULT_YENEBAKI or UPDATED_YENEBAKI
        defaultCourtCaseDetailsShouldBeFound("yenebaki.in=" + DEFAULT_YENEBAKI + "," + UPDATED_YENEBAKI);

        // Get all the courtCaseDetailsList where yenebaki equals to UPDATED_YENEBAKI
        defaultCourtCaseDetailsShouldNotBeFound("yenebaki.in=" + UPDATED_YENEBAKI);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByYenebakiIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where yenebaki is not null
        defaultCourtCaseDetailsShouldBeFound("yenebaki.specified=true");

        // Get all the courtCaseDetailsList where yenebaki is null
        defaultCourtCaseDetailsShouldNotBeFound("yenebaki.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByYenebakiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where yenebaki is greater than or equal to DEFAULT_YENEBAKI
        defaultCourtCaseDetailsShouldBeFound("yenebaki.greaterThanOrEqual=" + DEFAULT_YENEBAKI);

        // Get all the courtCaseDetailsList where yenebaki is greater than or equal to UPDATED_YENEBAKI
        defaultCourtCaseDetailsShouldNotBeFound("yenebaki.greaterThanOrEqual=" + UPDATED_YENEBAKI);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByYenebakiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where yenebaki is less than or equal to DEFAULT_YENEBAKI
        defaultCourtCaseDetailsShouldBeFound("yenebaki.lessThanOrEqual=" + DEFAULT_YENEBAKI);

        // Get all the courtCaseDetailsList where yenebaki is less than or equal to SMALLER_YENEBAKI
        defaultCourtCaseDetailsShouldNotBeFound("yenebaki.lessThanOrEqual=" + SMALLER_YENEBAKI);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByYenebakiIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where yenebaki is less than DEFAULT_YENEBAKI
        defaultCourtCaseDetailsShouldNotBeFound("yenebaki.lessThan=" + DEFAULT_YENEBAKI);

        // Get all the courtCaseDetailsList where yenebaki is less than UPDATED_YENEBAKI
        defaultCourtCaseDetailsShouldBeFound("yenebaki.lessThan=" + UPDATED_YENEBAKI);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByYenebakiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where yenebaki is greater than DEFAULT_YENEBAKI
        defaultCourtCaseDetailsShouldNotBeFound("yenebaki.greaterThan=" + DEFAULT_YENEBAKI);

        // Get all the courtCaseDetailsList where yenebaki is greater than SMALLER_YENEBAKI
        defaultCourtCaseDetailsShouldBeFound("yenebaki.greaterThan=" + SMALLER_YENEBAKI);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVyajIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vyaj equals to DEFAULT_VYAJ
        defaultCourtCaseDetailsShouldBeFound("vyaj.equals=" + DEFAULT_VYAJ);

        // Get all the courtCaseDetailsList where vyaj equals to UPDATED_VYAJ
        defaultCourtCaseDetailsShouldNotBeFound("vyaj.equals=" + UPDATED_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVyajIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vyaj in DEFAULT_VYAJ or UPDATED_VYAJ
        defaultCourtCaseDetailsShouldBeFound("vyaj.in=" + DEFAULT_VYAJ + "," + UPDATED_VYAJ);

        // Get all the courtCaseDetailsList where vyaj equals to UPDATED_VYAJ
        defaultCourtCaseDetailsShouldNotBeFound("vyaj.in=" + UPDATED_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVyajIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vyaj is not null
        defaultCourtCaseDetailsShouldBeFound("vyaj.specified=true");

        // Get all the courtCaseDetailsList where vyaj is null
        defaultCourtCaseDetailsShouldNotBeFound("vyaj.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVyajIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vyaj is greater than or equal to DEFAULT_VYAJ
        defaultCourtCaseDetailsShouldBeFound("vyaj.greaterThanOrEqual=" + DEFAULT_VYAJ);

        // Get all the courtCaseDetailsList where vyaj is greater than or equal to UPDATED_VYAJ
        defaultCourtCaseDetailsShouldNotBeFound("vyaj.greaterThanOrEqual=" + UPDATED_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVyajIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vyaj is less than or equal to DEFAULT_VYAJ
        defaultCourtCaseDetailsShouldBeFound("vyaj.lessThanOrEqual=" + DEFAULT_VYAJ);

        // Get all the courtCaseDetailsList where vyaj is less than or equal to SMALLER_VYAJ
        defaultCourtCaseDetailsShouldNotBeFound("vyaj.lessThanOrEqual=" + SMALLER_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVyajIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vyaj is less than DEFAULT_VYAJ
        defaultCourtCaseDetailsShouldNotBeFound("vyaj.lessThan=" + DEFAULT_VYAJ);

        // Get all the courtCaseDetailsList where vyaj is less than UPDATED_VYAJ
        defaultCourtCaseDetailsShouldBeFound("vyaj.lessThan=" + UPDATED_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVyajIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vyaj is greater than DEFAULT_VYAJ
        defaultCourtCaseDetailsShouldNotBeFound("vyaj.greaterThan=" + DEFAULT_VYAJ);

        // Get all the courtCaseDetailsList where vyaj is greater than SMALLER_VYAJ
        defaultCourtCaseDetailsShouldBeFound("vyaj.greaterThan=" + SMALLER_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etar equals to DEFAULT_ETAR
        defaultCourtCaseDetailsShouldBeFound("etar.equals=" + DEFAULT_ETAR);

        // Get all the courtCaseDetailsList where etar equals to UPDATED_ETAR
        defaultCourtCaseDetailsShouldNotBeFound("etar.equals=" + UPDATED_ETAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etar in DEFAULT_ETAR or UPDATED_ETAR
        defaultCourtCaseDetailsShouldBeFound("etar.in=" + DEFAULT_ETAR + "," + UPDATED_ETAR);

        // Get all the courtCaseDetailsList where etar equals to UPDATED_ETAR
        defaultCourtCaseDetailsShouldNotBeFound("etar.in=" + UPDATED_ETAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etar is not null
        defaultCourtCaseDetailsShouldBeFound("etar.specified=true");

        // Get all the courtCaseDetailsList where etar is null
        defaultCourtCaseDetailsShouldNotBeFound("etar.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etar is greater than or equal to DEFAULT_ETAR
        defaultCourtCaseDetailsShouldBeFound("etar.greaterThanOrEqual=" + DEFAULT_ETAR);

        // Get all the courtCaseDetailsList where etar is greater than or equal to UPDATED_ETAR
        defaultCourtCaseDetailsShouldNotBeFound("etar.greaterThanOrEqual=" + UPDATED_ETAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etar is less than or equal to DEFAULT_ETAR
        defaultCourtCaseDetailsShouldBeFound("etar.lessThanOrEqual=" + DEFAULT_ETAR);

        // Get all the courtCaseDetailsList where etar is less than or equal to SMALLER_ETAR
        defaultCourtCaseDetailsShouldNotBeFound("etar.lessThanOrEqual=" + SMALLER_ETAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etar is less than DEFAULT_ETAR
        defaultCourtCaseDetailsShouldNotBeFound("etar.lessThan=" + DEFAULT_ETAR);

        // Get all the courtCaseDetailsList where etar is less than UPDATED_ETAR
        defaultCourtCaseDetailsShouldBeFound("etar.lessThan=" + UPDATED_ETAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etar is greater than DEFAULT_ETAR
        defaultCourtCaseDetailsShouldNotBeFound("etar.greaterThan=" + DEFAULT_ETAR);

        // Get all the courtCaseDetailsList where etar is greater than SMALLER_ETAR
        defaultCourtCaseDetailsShouldBeFound("etar.greaterThan=" + SMALLER_ETAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDimmandMilaleIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dimmandMilale equals to DEFAULT_DIMMAND_MILALE
        defaultCourtCaseDetailsShouldBeFound("dimmandMilale.equals=" + DEFAULT_DIMMAND_MILALE);

        // Get all the courtCaseDetailsList where dimmandMilale equals to UPDATED_DIMMAND_MILALE
        defaultCourtCaseDetailsShouldNotBeFound("dimmandMilale.equals=" + UPDATED_DIMMAND_MILALE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDimmandMilaleIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dimmandMilale in DEFAULT_DIMMAND_MILALE or UPDATED_DIMMAND_MILALE
        defaultCourtCaseDetailsShouldBeFound("dimmandMilale.in=" + DEFAULT_DIMMAND_MILALE + "," + UPDATED_DIMMAND_MILALE);

        // Get all the courtCaseDetailsList where dimmandMilale equals to UPDATED_DIMMAND_MILALE
        defaultCourtCaseDetailsShouldNotBeFound("dimmandMilale.in=" + UPDATED_DIMMAND_MILALE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDimmandMilaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dimmandMilale is not null
        defaultCourtCaseDetailsShouldBeFound("dimmandMilale.specified=true");

        // Get all the courtCaseDetailsList where dimmandMilale is null
        defaultCourtCaseDetailsShouldNotBeFound("dimmandMilale.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDimmandMilaleContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dimmandMilale contains DEFAULT_DIMMAND_MILALE
        defaultCourtCaseDetailsShouldBeFound("dimmandMilale.contains=" + DEFAULT_DIMMAND_MILALE);

        // Get all the courtCaseDetailsList where dimmandMilale contains UPDATED_DIMMAND_MILALE
        defaultCourtCaseDetailsShouldNotBeFound("dimmandMilale.contains=" + UPDATED_DIMMAND_MILALE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDimmandMilaleNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dimmandMilale does not contain DEFAULT_DIMMAND_MILALE
        defaultCourtCaseDetailsShouldNotBeFound("dimmandMilale.doesNotContain=" + DEFAULT_DIMMAND_MILALE);

        // Get all the courtCaseDetailsList where dimmandMilale does not contain UPDATED_DIMMAND_MILALE
        defaultCourtCaseDetailsShouldBeFound("dimmandMilale.doesNotContain=" + UPDATED_DIMMAND_MILALE);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDimmandDinnankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dimmandDinnank equals to DEFAULT_DIMMAND_DINNANK
        defaultCourtCaseDetailsShouldBeFound("dimmandDinnank.equals=" + DEFAULT_DIMMAND_DINNANK);

        // Get all the courtCaseDetailsList where dimmandDinnank equals to UPDATED_DIMMAND_DINNANK
        defaultCourtCaseDetailsShouldNotBeFound("dimmandDinnank.equals=" + UPDATED_DIMMAND_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDimmandDinnankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dimmandDinnank in DEFAULT_DIMMAND_DINNANK or UPDATED_DIMMAND_DINNANK
        defaultCourtCaseDetailsShouldBeFound("dimmandDinnank.in=" + DEFAULT_DIMMAND_DINNANK + "," + UPDATED_DIMMAND_DINNANK);

        // Get all the courtCaseDetailsList where dimmandDinnank equals to UPDATED_DIMMAND_DINNANK
        defaultCourtCaseDetailsShouldNotBeFound("dimmandDinnank.in=" + UPDATED_DIMMAND_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByDimmandDinnankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where dimmandDinnank is not null
        defaultCourtCaseDetailsShouldBeFound("dimmandDinnank.specified=true");

        // Get all the courtCaseDetailsList where dimmandDinnank is null
        defaultCourtCaseDetailsShouldNotBeFound("dimmandDinnank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJaptiAadheshIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where japtiAadhesh equals to DEFAULT_JAPTI_AADHESH
        defaultCourtCaseDetailsShouldBeFound("japtiAadhesh.equals=" + DEFAULT_JAPTI_AADHESH);

        // Get all the courtCaseDetailsList where japtiAadhesh equals to UPDATED_JAPTI_AADHESH
        defaultCourtCaseDetailsShouldNotBeFound("japtiAadhesh.equals=" + UPDATED_JAPTI_AADHESH);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJaptiAadheshIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where japtiAadhesh in DEFAULT_JAPTI_AADHESH or UPDATED_JAPTI_AADHESH
        defaultCourtCaseDetailsShouldBeFound("japtiAadhesh.in=" + DEFAULT_JAPTI_AADHESH + "," + UPDATED_JAPTI_AADHESH);

        // Get all the courtCaseDetailsList where japtiAadhesh equals to UPDATED_JAPTI_AADHESH
        defaultCourtCaseDetailsShouldNotBeFound("japtiAadhesh.in=" + UPDATED_JAPTI_AADHESH);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJaptiAadheshIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where japtiAadhesh is not null
        defaultCourtCaseDetailsShouldBeFound("japtiAadhesh.specified=true");

        // Get all the courtCaseDetailsList where japtiAadhesh is null
        defaultCourtCaseDetailsShouldNotBeFound("japtiAadhesh.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJaptiAadheshContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where japtiAadhesh contains DEFAULT_JAPTI_AADHESH
        defaultCourtCaseDetailsShouldBeFound("japtiAadhesh.contains=" + DEFAULT_JAPTI_AADHESH);

        // Get all the courtCaseDetailsList where japtiAadhesh contains UPDATED_JAPTI_AADHESH
        defaultCourtCaseDetailsShouldNotBeFound("japtiAadhesh.contains=" + UPDATED_JAPTI_AADHESH);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJaptiAadheshNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where japtiAadhesh does not contain DEFAULT_JAPTI_AADHESH
        defaultCourtCaseDetailsShouldNotBeFound("japtiAadhesh.doesNotContain=" + DEFAULT_JAPTI_AADHESH);

        // Get all the courtCaseDetailsList where japtiAadhesh does not contain UPDATED_JAPTI_AADHESH
        defaultCourtCaseDetailsShouldBeFound("japtiAadhesh.doesNotContain=" + UPDATED_JAPTI_AADHESH);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJaptiAadheshDinnankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where japtiAadheshDinnank equals to DEFAULT_JAPTI_AADHESH_DINNANK
        defaultCourtCaseDetailsShouldBeFound("japtiAadheshDinnank.equals=" + DEFAULT_JAPTI_AADHESH_DINNANK);

        // Get all the courtCaseDetailsList where japtiAadheshDinnank equals to UPDATED_JAPTI_AADHESH_DINNANK
        defaultCourtCaseDetailsShouldNotBeFound("japtiAadheshDinnank.equals=" + UPDATED_JAPTI_AADHESH_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJaptiAadheshDinnankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where japtiAadheshDinnank in DEFAULT_JAPTI_AADHESH_DINNANK or UPDATED_JAPTI_AADHESH_DINNANK
        defaultCourtCaseDetailsShouldBeFound(
            "japtiAadheshDinnank.in=" + DEFAULT_JAPTI_AADHESH_DINNANK + "," + UPDATED_JAPTI_AADHESH_DINNANK
        );

        // Get all the courtCaseDetailsList where japtiAadheshDinnank equals to UPDATED_JAPTI_AADHESH_DINNANK
        defaultCourtCaseDetailsShouldNotBeFound("japtiAadheshDinnank.in=" + UPDATED_JAPTI_AADHESH_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJaptiAadheshDinnankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where japtiAadheshDinnank is not null
        defaultCourtCaseDetailsShouldBeFound("japtiAadheshDinnank.specified=true");

        // Get all the courtCaseDetailsList where japtiAadheshDinnank is null
        defaultCourtCaseDetailsShouldNotBeFound("japtiAadheshDinnank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySthavrIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sthavr equals to DEFAULT_STHAVR
        defaultCourtCaseDetailsShouldBeFound("sthavr.equals=" + DEFAULT_STHAVR);

        // Get all the courtCaseDetailsList where sthavr equals to UPDATED_STHAVR
        defaultCourtCaseDetailsShouldNotBeFound("sthavr.equals=" + UPDATED_STHAVR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySthavrIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sthavr in DEFAULT_STHAVR or UPDATED_STHAVR
        defaultCourtCaseDetailsShouldBeFound("sthavr.in=" + DEFAULT_STHAVR + "," + UPDATED_STHAVR);

        // Get all the courtCaseDetailsList where sthavr equals to UPDATED_STHAVR
        defaultCourtCaseDetailsShouldNotBeFound("sthavr.in=" + UPDATED_STHAVR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySthavrIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sthavr is not null
        defaultCourtCaseDetailsShouldBeFound("sthavr.specified=true");

        // Get all the courtCaseDetailsList where sthavr is null
        defaultCourtCaseDetailsShouldNotBeFound("sthavr.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySthavrIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sthavr is greater than or equal to DEFAULT_STHAVR
        defaultCourtCaseDetailsShouldBeFound("sthavr.greaterThanOrEqual=" + DEFAULT_STHAVR);

        // Get all the courtCaseDetailsList where sthavr is greater than or equal to UPDATED_STHAVR
        defaultCourtCaseDetailsShouldNotBeFound("sthavr.greaterThanOrEqual=" + UPDATED_STHAVR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySthavrIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sthavr is less than or equal to DEFAULT_STHAVR
        defaultCourtCaseDetailsShouldBeFound("sthavr.lessThanOrEqual=" + DEFAULT_STHAVR);

        // Get all the courtCaseDetailsList where sthavr is less than or equal to SMALLER_STHAVR
        defaultCourtCaseDetailsShouldNotBeFound("sthavr.lessThanOrEqual=" + SMALLER_STHAVR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySthavrIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sthavr is less than DEFAULT_STHAVR
        defaultCourtCaseDetailsShouldNotBeFound("sthavr.lessThan=" + DEFAULT_STHAVR);

        // Get all the courtCaseDetailsList where sthavr is less than UPDATED_STHAVR
        defaultCourtCaseDetailsShouldBeFound("sthavr.lessThan=" + UPDATED_STHAVR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsBySthavrIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where sthavr is greater than DEFAULT_STHAVR
        defaultCourtCaseDetailsShouldNotBeFound("sthavr.greaterThan=" + DEFAULT_STHAVR);

        // Get all the courtCaseDetailsList where sthavr is greater than SMALLER_STHAVR
        defaultCourtCaseDetailsShouldBeFound("sthavr.greaterThan=" + SMALLER_STHAVR);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJangamIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where jangam equals to DEFAULT_JANGAM
        defaultCourtCaseDetailsShouldBeFound("jangam.equals=" + DEFAULT_JANGAM);

        // Get all the courtCaseDetailsList where jangam equals to UPDATED_JANGAM
        defaultCourtCaseDetailsShouldNotBeFound("jangam.equals=" + UPDATED_JANGAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJangamIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where jangam in DEFAULT_JANGAM or UPDATED_JANGAM
        defaultCourtCaseDetailsShouldBeFound("jangam.in=" + DEFAULT_JANGAM + "," + UPDATED_JANGAM);

        // Get all the courtCaseDetailsList where jangam equals to UPDATED_JANGAM
        defaultCourtCaseDetailsShouldNotBeFound("jangam.in=" + UPDATED_JANGAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJangamIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where jangam is not null
        defaultCourtCaseDetailsShouldBeFound("jangam.specified=true");

        // Get all the courtCaseDetailsList where jangam is null
        defaultCourtCaseDetailsShouldNotBeFound("jangam.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJangamIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where jangam is greater than or equal to DEFAULT_JANGAM
        defaultCourtCaseDetailsShouldBeFound("jangam.greaterThanOrEqual=" + DEFAULT_JANGAM);

        // Get all the courtCaseDetailsList where jangam is greater than or equal to UPDATED_JANGAM
        defaultCourtCaseDetailsShouldNotBeFound("jangam.greaterThanOrEqual=" + UPDATED_JANGAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJangamIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where jangam is less than or equal to DEFAULT_JANGAM
        defaultCourtCaseDetailsShouldBeFound("jangam.lessThanOrEqual=" + DEFAULT_JANGAM);

        // Get all the courtCaseDetailsList where jangam is less than or equal to SMALLER_JANGAM
        defaultCourtCaseDetailsShouldNotBeFound("jangam.lessThanOrEqual=" + SMALLER_JANGAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJangamIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where jangam is less than DEFAULT_JANGAM
        defaultCourtCaseDetailsShouldNotBeFound("jangam.lessThan=" + DEFAULT_JANGAM);

        // Get all the courtCaseDetailsList where jangam is less than UPDATED_JANGAM
        defaultCourtCaseDetailsShouldBeFound("jangam.lessThan=" + UPDATED_JANGAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByJangamIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where jangam is greater than DEFAULT_JANGAM
        defaultCourtCaseDetailsShouldNotBeFound("jangam.greaterThan=" + DEFAULT_JANGAM);

        // Get all the courtCaseDetailsList where jangam is greater than SMALLER_JANGAM
        defaultCourtCaseDetailsShouldBeFound("jangam.greaterThan=" + SMALLER_JANGAM);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVikriAadheshIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vikriAadhesh equals to DEFAULT_VIKRI_AADHESH
        defaultCourtCaseDetailsShouldBeFound("vikriAadhesh.equals=" + DEFAULT_VIKRI_AADHESH);

        // Get all the courtCaseDetailsList where vikriAadhesh equals to UPDATED_VIKRI_AADHESH
        defaultCourtCaseDetailsShouldNotBeFound("vikriAadhesh.equals=" + UPDATED_VIKRI_AADHESH);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVikriAadheshIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vikriAadhesh in DEFAULT_VIKRI_AADHESH or UPDATED_VIKRI_AADHESH
        defaultCourtCaseDetailsShouldBeFound("vikriAadhesh.in=" + DEFAULT_VIKRI_AADHESH + "," + UPDATED_VIKRI_AADHESH);

        // Get all the courtCaseDetailsList where vikriAadhesh equals to UPDATED_VIKRI_AADHESH
        defaultCourtCaseDetailsShouldNotBeFound("vikriAadhesh.in=" + UPDATED_VIKRI_AADHESH);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVikriAadheshIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vikriAadhesh is not null
        defaultCourtCaseDetailsShouldBeFound("vikriAadhesh.specified=true");

        // Get all the courtCaseDetailsList where vikriAadhesh is null
        defaultCourtCaseDetailsShouldNotBeFound("vikriAadhesh.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVikriAadheshContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vikriAadhesh contains DEFAULT_VIKRI_AADHESH
        defaultCourtCaseDetailsShouldBeFound("vikriAadhesh.contains=" + DEFAULT_VIKRI_AADHESH);

        // Get all the courtCaseDetailsList where vikriAadhesh contains UPDATED_VIKRI_AADHESH
        defaultCourtCaseDetailsShouldNotBeFound("vikriAadhesh.contains=" + UPDATED_VIKRI_AADHESH);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVikriAadheshNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vikriAadhesh does not contain DEFAULT_VIKRI_AADHESH
        defaultCourtCaseDetailsShouldNotBeFound("vikriAadhesh.doesNotContain=" + DEFAULT_VIKRI_AADHESH);

        // Get all the courtCaseDetailsList where vikriAadhesh does not contain UPDATED_VIKRI_AADHESH
        defaultCourtCaseDetailsShouldBeFound("vikriAadhesh.doesNotContain=" + UPDATED_VIKRI_AADHESH);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVikriAddheshDinnankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vikriAddheshDinnank equals to DEFAULT_VIKRI_ADDHESH_DINNANK
        defaultCourtCaseDetailsShouldBeFound("vikriAddheshDinnank.equals=" + DEFAULT_VIKRI_ADDHESH_DINNANK);

        // Get all the courtCaseDetailsList where vikriAddheshDinnank equals to UPDATED_VIKRI_ADDHESH_DINNANK
        defaultCourtCaseDetailsShouldNotBeFound("vikriAddheshDinnank.equals=" + UPDATED_VIKRI_ADDHESH_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVikriAddheshDinnankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vikriAddheshDinnank in DEFAULT_VIKRI_ADDHESH_DINNANK or UPDATED_VIKRI_ADDHESH_DINNANK
        defaultCourtCaseDetailsShouldBeFound(
            "vikriAddheshDinnank.in=" + DEFAULT_VIKRI_ADDHESH_DINNANK + "," + UPDATED_VIKRI_ADDHESH_DINNANK
        );

        // Get all the courtCaseDetailsList where vikriAddheshDinnank equals to UPDATED_VIKRI_ADDHESH_DINNANK
        defaultCourtCaseDetailsShouldNotBeFound("vikriAddheshDinnank.in=" + UPDATED_VIKRI_ADDHESH_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByVikriAddheshDinnankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where vikriAddheshDinnank is not null
        defaultCourtCaseDetailsShouldBeFound("vikriAddheshDinnank.specified=true");

        // Get all the courtCaseDetailsList where vikriAddheshDinnank is null
        defaultCourtCaseDetailsShouldNotBeFound("vikriAddheshDinnank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarTapshilIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etarTapshil equals to DEFAULT_ETAR_TAPSHIL
        defaultCourtCaseDetailsShouldBeFound("etarTapshil.equals=" + DEFAULT_ETAR_TAPSHIL);

        // Get all the courtCaseDetailsList where etarTapshil equals to UPDATED_ETAR_TAPSHIL
        defaultCourtCaseDetailsShouldNotBeFound("etarTapshil.equals=" + UPDATED_ETAR_TAPSHIL);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarTapshilIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etarTapshil in DEFAULT_ETAR_TAPSHIL or UPDATED_ETAR_TAPSHIL
        defaultCourtCaseDetailsShouldBeFound("etarTapshil.in=" + DEFAULT_ETAR_TAPSHIL + "," + UPDATED_ETAR_TAPSHIL);

        // Get all the courtCaseDetailsList where etarTapshil equals to UPDATED_ETAR_TAPSHIL
        defaultCourtCaseDetailsShouldNotBeFound("etarTapshil.in=" + UPDATED_ETAR_TAPSHIL);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarTapshilIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etarTapshil is not null
        defaultCourtCaseDetailsShouldBeFound("etarTapshil.specified=true");

        // Get all the courtCaseDetailsList where etarTapshil is null
        defaultCourtCaseDetailsShouldNotBeFound("etarTapshil.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarTapshilContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etarTapshil contains DEFAULT_ETAR_TAPSHIL
        defaultCourtCaseDetailsShouldBeFound("etarTapshil.contains=" + DEFAULT_ETAR_TAPSHIL);

        // Get all the courtCaseDetailsList where etarTapshil contains UPDATED_ETAR_TAPSHIL
        defaultCourtCaseDetailsShouldNotBeFound("etarTapshil.contains=" + UPDATED_ETAR_TAPSHIL);
    }

    @Test
    @Transactional
    void getAllCourtCaseDetailsByEtarTapshilNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        // Get all the courtCaseDetailsList where etarTapshil does not contain DEFAULT_ETAR_TAPSHIL
        defaultCourtCaseDetailsShouldNotBeFound("etarTapshil.doesNotContain=" + DEFAULT_ETAR_TAPSHIL);

        // Get all the courtCaseDetailsList where etarTapshil does not contain UPDATED_ETAR_TAPSHIL
        defaultCourtCaseDetailsShouldBeFound("etarTapshil.doesNotContain=" + UPDATED_ETAR_TAPSHIL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourtCaseDetailsShouldBeFound(String filter) throws Exception {
        restCourtCaseDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCaseDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].kramank").value(hasItem(DEFAULT_KRAMANK.intValue())))
            .andExpect(jsonPath("$.[*].dinank").value(hasItem(DEFAULT_DINANK.toString())))
            .andExpect(jsonPath("$.[*].caseDinank").value(hasItem(DEFAULT_CASE_DINANK.toString())))
            .andExpect(jsonPath("$.[*].sabhasad").value(hasItem(DEFAULT_SABHASAD)))
            .andExpect(jsonPath("$.[*].sabhasadAccNo").value(hasItem(DEFAULT_SABHASAD_ACC_NO)))
            .andExpect(jsonPath("$.[*].karjPrakarType").value(hasItem(DEFAULT_KARJ_PRAKAR_TYPE)))
            .andExpect(jsonPath("$.[*].karjPrakar").value(hasItem(DEFAULT_KARJ_PRAKAR)))
            .andExpect(jsonPath("$.[*].certificateMilale").value(hasItem(DEFAULT_CERTIFICATE_MILALE)))
            .andExpect(jsonPath("$.[*].certificateDinnank").value(hasItem(DEFAULT_CERTIFICATE_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].certificateRakkam").value(hasItem(DEFAULT_CERTIFICATE_RAKKAM.doubleValue())))
            .andExpect(jsonPath("$.[*].yenebaki").value(hasItem(DEFAULT_YENEBAKI.doubleValue())))
            .andExpect(jsonPath("$.[*].vyaj").value(hasItem(DEFAULT_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].etar").value(hasItem(DEFAULT_ETAR.doubleValue())))
            .andExpect(jsonPath("$.[*].dimmandMilale").value(hasItem(DEFAULT_DIMMAND_MILALE)))
            .andExpect(jsonPath("$.[*].dimmandDinnank").value(hasItem(DEFAULT_DIMMAND_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].japtiAadhesh").value(hasItem(DEFAULT_JAPTI_AADHESH)))
            .andExpect(jsonPath("$.[*].japtiAadheshDinnank").value(hasItem(DEFAULT_JAPTI_AADHESH_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].sthavr").value(hasItem(DEFAULT_STHAVR.doubleValue())))
            .andExpect(jsonPath("$.[*].jangam").value(hasItem(DEFAULT_JANGAM.doubleValue())))
            .andExpect(jsonPath("$.[*].vikriAadhesh").value(hasItem(DEFAULT_VIKRI_AADHESH)))
            .andExpect(jsonPath("$.[*].vikriAddheshDinnank").value(hasItem(DEFAULT_VIKRI_ADDHESH_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].etarTapshil").value(hasItem(DEFAULT_ETAR_TAPSHIL)));

        // Check, that the count call also returns 1
        restCourtCaseDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourtCaseDetailsShouldNotBeFound(String filter) throws Exception {
        restCourtCaseDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourtCaseDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourtCaseDetails() throws Exception {
        // Get the courtCaseDetails
        restCourtCaseDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCourtCaseDetails() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        int databaseSizeBeforeUpdate = courtCaseDetailsRepository.findAll().size();

        // Update the courtCaseDetails
        CourtCaseDetails updatedCourtCaseDetails = courtCaseDetailsRepository.findById(courtCaseDetails.getId()).get();
        // Disconnect from session so that the updates on updatedCourtCaseDetails are not directly saved in db
        em.detach(updatedCourtCaseDetails);
        updatedCourtCaseDetails
            .kramank(UPDATED_KRAMANK)
            .dinank(UPDATED_DINANK)
            .caseDinank(UPDATED_CASE_DINANK)
            .sabhasad(UPDATED_SABHASAD)
            .sabhasadAccNo(UPDATED_SABHASAD_ACC_NO)
            .karjPrakarType(UPDATED_KARJ_PRAKAR_TYPE)
            .karjPrakar(UPDATED_KARJ_PRAKAR)
            .certificateMilale(UPDATED_CERTIFICATE_MILALE)
            .certificateDinnank(UPDATED_CERTIFICATE_DINNANK)
            .certificateRakkam(UPDATED_CERTIFICATE_RAKKAM)
            .yenebaki(UPDATED_YENEBAKI)
            .vyaj(UPDATED_VYAJ)
            .etar(UPDATED_ETAR)
            .dimmandMilale(UPDATED_DIMMAND_MILALE)
            .dimmandDinnank(UPDATED_DIMMAND_DINNANK)
            .japtiAadhesh(UPDATED_JAPTI_AADHESH)
            .japtiAadheshDinnank(UPDATED_JAPTI_AADHESH_DINNANK)
            .sthavr(UPDATED_STHAVR)
            .jangam(UPDATED_JANGAM)
            .vikriAadhesh(UPDATED_VIKRI_AADHESH)
            .vikriAddheshDinnank(UPDATED_VIKRI_ADDHESH_DINNANK)
            .etarTapshil(UPDATED_ETAR_TAPSHIL);

        restCourtCaseDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCourtCaseDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCourtCaseDetails))
            )
            .andExpect(status().isOk());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeUpdate);
        CourtCaseDetails testCourtCaseDetails = courtCaseDetailsList.get(courtCaseDetailsList.size() - 1);
        assertThat(testCourtCaseDetails.getKramank()).isEqualTo(UPDATED_KRAMANK);
        assertThat(testCourtCaseDetails.getDinank()).isEqualTo(UPDATED_DINANK);
        assertThat(testCourtCaseDetails.getCaseDinank()).isEqualTo(UPDATED_CASE_DINANK);
        assertThat(testCourtCaseDetails.getSabhasad()).isEqualTo(UPDATED_SABHASAD);
        assertThat(testCourtCaseDetails.getSabhasadAccNo()).isEqualTo(UPDATED_SABHASAD_ACC_NO);
        assertThat(testCourtCaseDetails.getKarjPrakarType()).isEqualTo(UPDATED_KARJ_PRAKAR_TYPE);
        assertThat(testCourtCaseDetails.getKarjPrakar()).isEqualTo(UPDATED_KARJ_PRAKAR);
        assertThat(testCourtCaseDetails.getCertificateMilale()).isEqualTo(UPDATED_CERTIFICATE_MILALE);
        assertThat(testCourtCaseDetails.getCertificateDinnank()).isEqualTo(UPDATED_CERTIFICATE_DINNANK);
        assertThat(testCourtCaseDetails.getCertificateRakkam()).isEqualTo(UPDATED_CERTIFICATE_RAKKAM);
        assertThat(testCourtCaseDetails.getYenebaki()).isEqualTo(UPDATED_YENEBAKI);
        assertThat(testCourtCaseDetails.getVyaj()).isEqualTo(UPDATED_VYAJ);
        assertThat(testCourtCaseDetails.getEtar()).isEqualTo(UPDATED_ETAR);
        assertThat(testCourtCaseDetails.getDimmandMilale()).isEqualTo(UPDATED_DIMMAND_MILALE);
        assertThat(testCourtCaseDetails.getDimmandDinnank()).isEqualTo(UPDATED_DIMMAND_DINNANK);
        assertThat(testCourtCaseDetails.getJaptiAadhesh()).isEqualTo(UPDATED_JAPTI_AADHESH);
        assertThat(testCourtCaseDetails.getJaptiAadheshDinnank()).isEqualTo(UPDATED_JAPTI_AADHESH_DINNANK);
        assertThat(testCourtCaseDetails.getSthavr()).isEqualTo(UPDATED_STHAVR);
        assertThat(testCourtCaseDetails.getJangam()).isEqualTo(UPDATED_JANGAM);
        assertThat(testCourtCaseDetails.getVikriAadhesh()).isEqualTo(UPDATED_VIKRI_AADHESH);
        assertThat(testCourtCaseDetails.getVikriAddheshDinnank()).isEqualTo(UPDATED_VIKRI_ADDHESH_DINNANK);
        assertThat(testCourtCaseDetails.getEtarTapshil()).isEqualTo(UPDATED_ETAR_TAPSHIL);
    }

    @Test
    @Transactional
    void putNonExistingCourtCaseDetails() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseDetailsRepository.findAll().size();
        courtCaseDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courtCaseDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourtCaseDetails() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseDetailsRepository.findAll().size();
        courtCaseDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourtCaseDetails() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseDetailsRepository.findAll().size();
        courtCaseDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourtCaseDetailsWithPatch() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        int databaseSizeBeforeUpdate = courtCaseDetailsRepository.findAll().size();

        // Update the courtCaseDetails using partial update
        CourtCaseDetails partialUpdatedCourtCaseDetails = new CourtCaseDetails();
        partialUpdatedCourtCaseDetails.setId(courtCaseDetails.getId());

        partialUpdatedCourtCaseDetails
            .dinank(UPDATED_DINANK)
            .caseDinank(UPDATED_CASE_DINANK)
            .sabhasadAccNo(UPDATED_SABHASAD_ACC_NO)
            .karjPrakar(UPDATED_KARJ_PRAKAR)
            .yenebaki(UPDATED_YENEBAKI)
            .dimmandMilale(UPDATED_DIMMAND_MILALE)
            .japtiAadhesh(UPDATED_JAPTI_AADHESH)
            .japtiAadheshDinnank(UPDATED_JAPTI_AADHESH_DINNANK)
            .jangam(UPDATED_JANGAM)
            .vikriAddheshDinnank(UPDATED_VIKRI_ADDHESH_DINNANK)
            .etarTapshil(UPDATED_ETAR_TAPSHIL);

        restCourtCaseDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCaseDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCaseDetails))
            )
            .andExpect(status().isOk());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeUpdate);
        CourtCaseDetails testCourtCaseDetails = courtCaseDetailsList.get(courtCaseDetailsList.size() - 1);
        assertThat(testCourtCaseDetails.getKramank()).isEqualTo(DEFAULT_KRAMANK);
        assertThat(testCourtCaseDetails.getDinank()).isEqualTo(UPDATED_DINANK);
        assertThat(testCourtCaseDetails.getCaseDinank()).isEqualTo(UPDATED_CASE_DINANK);
        assertThat(testCourtCaseDetails.getSabhasad()).isEqualTo(DEFAULT_SABHASAD);
        assertThat(testCourtCaseDetails.getSabhasadAccNo()).isEqualTo(UPDATED_SABHASAD_ACC_NO);
        assertThat(testCourtCaseDetails.getKarjPrakarType()).isEqualTo(DEFAULT_KARJ_PRAKAR_TYPE);
        assertThat(testCourtCaseDetails.getKarjPrakar()).isEqualTo(UPDATED_KARJ_PRAKAR);
        assertThat(testCourtCaseDetails.getCertificateMilale()).isEqualTo(DEFAULT_CERTIFICATE_MILALE);
        assertThat(testCourtCaseDetails.getCertificateDinnank()).isEqualTo(DEFAULT_CERTIFICATE_DINNANK);
        assertThat(testCourtCaseDetails.getCertificateRakkam()).isEqualTo(DEFAULT_CERTIFICATE_RAKKAM);
        assertThat(testCourtCaseDetails.getYenebaki()).isEqualTo(UPDATED_YENEBAKI);
        assertThat(testCourtCaseDetails.getVyaj()).isEqualTo(DEFAULT_VYAJ);
        assertThat(testCourtCaseDetails.getEtar()).isEqualTo(DEFAULT_ETAR);
        assertThat(testCourtCaseDetails.getDimmandMilale()).isEqualTo(UPDATED_DIMMAND_MILALE);
        assertThat(testCourtCaseDetails.getDimmandDinnank()).isEqualTo(DEFAULT_DIMMAND_DINNANK);
        assertThat(testCourtCaseDetails.getJaptiAadhesh()).isEqualTo(UPDATED_JAPTI_AADHESH);
        assertThat(testCourtCaseDetails.getJaptiAadheshDinnank()).isEqualTo(UPDATED_JAPTI_AADHESH_DINNANK);
        assertThat(testCourtCaseDetails.getSthavr()).isEqualTo(DEFAULT_STHAVR);
        assertThat(testCourtCaseDetails.getJangam()).isEqualTo(UPDATED_JANGAM);
        assertThat(testCourtCaseDetails.getVikriAadhesh()).isEqualTo(DEFAULT_VIKRI_AADHESH);
        assertThat(testCourtCaseDetails.getVikriAddheshDinnank()).isEqualTo(UPDATED_VIKRI_ADDHESH_DINNANK);
        assertThat(testCourtCaseDetails.getEtarTapshil()).isEqualTo(UPDATED_ETAR_TAPSHIL);
    }

    @Test
    @Transactional
    void fullUpdateCourtCaseDetailsWithPatch() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        int databaseSizeBeforeUpdate = courtCaseDetailsRepository.findAll().size();

        // Update the courtCaseDetails using partial update
        CourtCaseDetails partialUpdatedCourtCaseDetails = new CourtCaseDetails();
        partialUpdatedCourtCaseDetails.setId(courtCaseDetails.getId());

        partialUpdatedCourtCaseDetails
            .kramank(UPDATED_KRAMANK)
            .dinank(UPDATED_DINANK)
            .caseDinank(UPDATED_CASE_DINANK)
            .sabhasad(UPDATED_SABHASAD)
            .sabhasadAccNo(UPDATED_SABHASAD_ACC_NO)
            .karjPrakarType(UPDATED_KARJ_PRAKAR_TYPE)
            .karjPrakar(UPDATED_KARJ_PRAKAR)
            .certificateMilale(UPDATED_CERTIFICATE_MILALE)
            .certificateDinnank(UPDATED_CERTIFICATE_DINNANK)
            .certificateRakkam(UPDATED_CERTIFICATE_RAKKAM)
            .yenebaki(UPDATED_YENEBAKI)
            .vyaj(UPDATED_VYAJ)
            .etar(UPDATED_ETAR)
            .dimmandMilale(UPDATED_DIMMAND_MILALE)
            .dimmandDinnank(UPDATED_DIMMAND_DINNANK)
            .japtiAadhesh(UPDATED_JAPTI_AADHESH)
            .japtiAadheshDinnank(UPDATED_JAPTI_AADHESH_DINNANK)
            .sthavr(UPDATED_STHAVR)
            .jangam(UPDATED_JANGAM)
            .vikriAadhesh(UPDATED_VIKRI_AADHESH)
            .vikriAddheshDinnank(UPDATED_VIKRI_ADDHESH_DINNANK)
            .etarTapshil(UPDATED_ETAR_TAPSHIL);

        restCourtCaseDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCaseDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCaseDetails))
            )
            .andExpect(status().isOk());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeUpdate);
        CourtCaseDetails testCourtCaseDetails = courtCaseDetailsList.get(courtCaseDetailsList.size() - 1);
        assertThat(testCourtCaseDetails.getKramank()).isEqualTo(UPDATED_KRAMANK);
        assertThat(testCourtCaseDetails.getDinank()).isEqualTo(UPDATED_DINANK);
        assertThat(testCourtCaseDetails.getCaseDinank()).isEqualTo(UPDATED_CASE_DINANK);
        assertThat(testCourtCaseDetails.getSabhasad()).isEqualTo(UPDATED_SABHASAD);
        assertThat(testCourtCaseDetails.getSabhasadAccNo()).isEqualTo(UPDATED_SABHASAD_ACC_NO);
        assertThat(testCourtCaseDetails.getKarjPrakarType()).isEqualTo(UPDATED_KARJ_PRAKAR_TYPE);
        assertThat(testCourtCaseDetails.getKarjPrakar()).isEqualTo(UPDATED_KARJ_PRAKAR);
        assertThat(testCourtCaseDetails.getCertificateMilale()).isEqualTo(UPDATED_CERTIFICATE_MILALE);
        assertThat(testCourtCaseDetails.getCertificateDinnank()).isEqualTo(UPDATED_CERTIFICATE_DINNANK);
        assertThat(testCourtCaseDetails.getCertificateRakkam()).isEqualTo(UPDATED_CERTIFICATE_RAKKAM);
        assertThat(testCourtCaseDetails.getYenebaki()).isEqualTo(UPDATED_YENEBAKI);
        assertThat(testCourtCaseDetails.getVyaj()).isEqualTo(UPDATED_VYAJ);
        assertThat(testCourtCaseDetails.getEtar()).isEqualTo(UPDATED_ETAR);
        assertThat(testCourtCaseDetails.getDimmandMilale()).isEqualTo(UPDATED_DIMMAND_MILALE);
        assertThat(testCourtCaseDetails.getDimmandDinnank()).isEqualTo(UPDATED_DIMMAND_DINNANK);
        assertThat(testCourtCaseDetails.getJaptiAadhesh()).isEqualTo(UPDATED_JAPTI_AADHESH);
        assertThat(testCourtCaseDetails.getJaptiAadheshDinnank()).isEqualTo(UPDATED_JAPTI_AADHESH_DINNANK);
        assertThat(testCourtCaseDetails.getSthavr()).isEqualTo(UPDATED_STHAVR);
        assertThat(testCourtCaseDetails.getJangam()).isEqualTo(UPDATED_JANGAM);
        assertThat(testCourtCaseDetails.getVikriAadhesh()).isEqualTo(UPDATED_VIKRI_AADHESH);
        assertThat(testCourtCaseDetails.getVikriAddheshDinnank()).isEqualTo(UPDATED_VIKRI_ADDHESH_DINNANK);
        assertThat(testCourtCaseDetails.getEtarTapshil()).isEqualTo(UPDATED_ETAR_TAPSHIL);
    }

    @Test
    @Transactional
    void patchNonExistingCourtCaseDetails() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseDetailsRepository.findAll().size();
        courtCaseDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courtCaseDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourtCaseDetails() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseDetailsRepository.findAll().size();
        courtCaseDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourtCaseDetails() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseDetailsRepository.findAll().size();
        courtCaseDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCaseDetails in the database
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourtCaseDetails() throws Exception {
        // Initialize the database
        courtCaseDetailsRepository.saveAndFlush(courtCaseDetails);

        int databaseSizeBeforeDelete = courtCaseDetailsRepository.findAll().size();

        // Delete the courtCaseDetails
        restCourtCaseDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, courtCaseDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourtCaseDetails> courtCaseDetailsList = courtCaseDetailsRepository.findAll();
        assertThat(courtCaseDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
