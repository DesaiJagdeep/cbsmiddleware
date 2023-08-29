package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CourtCaseSetting;
import com.cbs.middleware.repository.CourtCaseSettingRepository;
import com.cbs.middleware.service.criteria.CourtCaseSettingCriteria;
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
 * Integration tests for the {@link CourtCaseSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourtCaseSettingResourceIT {

    private static final Instant DEFAULT_DINANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DINANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SHAKHA_VEVSTHAPAK = "AAAAAAAAAA";
    private static final String UPDATED_SHAKHA_VEVSTHAPAK = "BBBBBBBBBB";

    private static final String DEFAULT_SUCHAK = "AAAAAAAAAA";
    private static final String UPDATED_SUCHAK = "BBBBBBBBBB";

    private static final String DEFAULT_AANUMODAK = "AAAAAAAAAA";
    private static final String UPDATED_AANUMODAK = "BBBBBBBBBB";

    private static final String DEFAULT_VASULI_ADHIKARI = "AAAAAAAAAA";
    private static final String UPDATED_VASULI_ADHIKARI = "BBBBBBBBBB";

    private static final String DEFAULT_AR_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_AR_OFFICE = "BBBBBBBBBB";

    private static final Long DEFAULT_THARAV_NUMBER = 1L;
    private static final Long UPDATED_THARAV_NUMBER = 2L;
    private static final Long SMALLER_THARAV_NUMBER = 1L - 1L;

    private static final Instant DEFAULT_THARAV_DINANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THARAV_DINANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_KARJ_FED_NOTICE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KARJ_FED_NOTICE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ONE_ZERO_ONE_NOTICE_ONE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ONE_ZERO_ONE_NOTICE_ONE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ONE_ZERO_ONE_NOTICE_TWO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ONE_ZERO_ONE_NOTICE_TWO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VISHAY_KRAMANK = "AAAAAAAAAA";
    private static final String UPDATED_VISHAY_KRAMANK = "BBBBBBBBBB";

    private static final String DEFAULT_WAR = "AAAAAAAAAA";
    private static final String UPDATED_WAR = "BBBBBBBBBB";

    private static final String DEFAULT_VEL = "AAAAAAAAAA";
    private static final String UPDATED_VEL = "BBBBBBBBBB";

    private static final Instant DEFAULT_MAGANI_NOTICE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MAGANI_NOTICE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_ETAR_KHARCH = 1D;
    private static final Double UPDATED_ETAR_KHARCH = 2D;
    private static final Double SMALLER_ETAR_KHARCH = 1D - 1D;

    private static final Double DEFAULT_NOTICE_KHARCH = 1D;
    private static final Double UPDATED_NOTICE_KHARCH = 2D;
    private static final Double SMALLER_NOTICE_KHARCH = 1D - 1D;

    private static final Double DEFAULT_VASULI_KHARCH = 1D;
    private static final Double UPDATED_VASULI_KHARCH = 2D;
    private static final Double SMALLER_VASULI_KHARCH = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/court-case-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourtCaseSettingRepository courtCaseSettingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourtCaseSettingMockMvc;

    private CourtCaseSetting courtCaseSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCaseSetting createEntity(EntityManager em) {
        CourtCaseSetting courtCaseSetting = new CourtCaseSetting()
            .dinank(DEFAULT_DINANK)
            .shakhaVevsthapak(DEFAULT_SHAKHA_VEVSTHAPAK)
            .suchak(DEFAULT_SUCHAK)
            .aanumodak(DEFAULT_AANUMODAK)
            .vasuliAdhikari(DEFAULT_VASULI_ADHIKARI)
            .arOffice(DEFAULT_AR_OFFICE)
            .tharavNumber(DEFAULT_THARAV_NUMBER)
            .tharavDinank(DEFAULT_THARAV_DINANK)
            .karjFedNotice(DEFAULT_KARJ_FED_NOTICE)
            .oneZeroOneNoticeOne(DEFAULT_ONE_ZERO_ONE_NOTICE_ONE)
            .oneZeroOneNoticeTwo(DEFAULT_ONE_ZERO_ONE_NOTICE_TWO)
            .vishayKramank(DEFAULT_VISHAY_KRAMANK)
            .war(DEFAULT_WAR)
            .vel(DEFAULT_VEL)
            .maganiNotice(DEFAULT_MAGANI_NOTICE)
            .etarKharch(DEFAULT_ETAR_KHARCH)
            .noticeKharch(DEFAULT_NOTICE_KHARCH)
            .vasuliKharch(DEFAULT_VASULI_KHARCH);
        return courtCaseSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCaseSetting createUpdatedEntity(EntityManager em) {
        CourtCaseSetting courtCaseSetting = new CourtCaseSetting()
            .dinank(UPDATED_DINANK)
            .shakhaVevsthapak(UPDATED_SHAKHA_VEVSTHAPAK)
            .suchak(UPDATED_SUCHAK)
            .aanumodak(UPDATED_AANUMODAK)
            .vasuliAdhikari(UPDATED_VASULI_ADHIKARI)
            .arOffice(UPDATED_AR_OFFICE)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDinank(UPDATED_THARAV_DINANK)
            .karjFedNotice(UPDATED_KARJ_FED_NOTICE)
            .oneZeroOneNoticeOne(UPDATED_ONE_ZERO_ONE_NOTICE_ONE)
            .oneZeroOneNoticeTwo(UPDATED_ONE_ZERO_ONE_NOTICE_TWO)
            .vishayKramank(UPDATED_VISHAY_KRAMANK)
            .war(UPDATED_WAR)
            .vel(UPDATED_VEL)
            .maganiNotice(UPDATED_MAGANI_NOTICE)
            .etarKharch(UPDATED_ETAR_KHARCH)
            .noticeKharch(UPDATED_NOTICE_KHARCH)
            .vasuliKharch(UPDATED_VASULI_KHARCH);
        return courtCaseSetting;
    }

    @BeforeEach
    public void initTest() {
        courtCaseSetting = createEntity(em);
    }

    @Test
    @Transactional
    void createCourtCaseSetting() throws Exception {
        int databaseSizeBeforeCreate = courtCaseSettingRepository.findAll().size();
        // Create the CourtCaseSetting
        restCourtCaseSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseSetting))
            )
            .andExpect(status().isCreated());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeCreate + 1);
        CourtCaseSetting testCourtCaseSetting = courtCaseSettingList.get(courtCaseSettingList.size() - 1);
        assertThat(testCourtCaseSetting.getDinank()).isEqualTo(DEFAULT_DINANK);
        assertThat(testCourtCaseSetting.getShakhaVevsthapak()).isEqualTo(DEFAULT_SHAKHA_VEVSTHAPAK);
        assertThat(testCourtCaseSetting.getSuchak()).isEqualTo(DEFAULT_SUCHAK);
        assertThat(testCourtCaseSetting.getAanumodak()).isEqualTo(DEFAULT_AANUMODAK);
        assertThat(testCourtCaseSetting.getVasuliAdhikari()).isEqualTo(DEFAULT_VASULI_ADHIKARI);
        assertThat(testCourtCaseSetting.getArOffice()).isEqualTo(DEFAULT_AR_OFFICE);
        assertThat(testCourtCaseSetting.getTharavNumber()).isEqualTo(DEFAULT_THARAV_NUMBER);
        assertThat(testCourtCaseSetting.getTharavDinank()).isEqualTo(DEFAULT_THARAV_DINANK);
        assertThat(testCourtCaseSetting.getKarjFedNotice()).isEqualTo(DEFAULT_KARJ_FED_NOTICE);
        assertThat(testCourtCaseSetting.getOneZeroOneNoticeOne()).isEqualTo(DEFAULT_ONE_ZERO_ONE_NOTICE_ONE);
        assertThat(testCourtCaseSetting.getOneZeroOneNoticeTwo()).isEqualTo(DEFAULT_ONE_ZERO_ONE_NOTICE_TWO);
        assertThat(testCourtCaseSetting.getVishayKramank()).isEqualTo(DEFAULT_VISHAY_KRAMANK);
        assertThat(testCourtCaseSetting.getWar()).isEqualTo(DEFAULT_WAR);
        assertThat(testCourtCaseSetting.getVel()).isEqualTo(DEFAULT_VEL);
        assertThat(testCourtCaseSetting.getMaganiNotice()).isEqualTo(DEFAULT_MAGANI_NOTICE);
        assertThat(testCourtCaseSetting.getEtarKharch()).isEqualTo(DEFAULT_ETAR_KHARCH);
        assertThat(testCourtCaseSetting.getNoticeKharch()).isEqualTo(DEFAULT_NOTICE_KHARCH);
        assertThat(testCourtCaseSetting.getVasuliKharch()).isEqualTo(DEFAULT_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void createCourtCaseSettingWithExistingId() throws Exception {
        // Create the CourtCaseSetting with an existing ID
        courtCaseSetting.setId(1L);

        int databaseSizeBeforeCreate = courtCaseSettingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourtCaseSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettings() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList
        restCourtCaseSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCaseSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].dinank").value(hasItem(DEFAULT_DINANK.toString())))
            .andExpect(jsonPath("$.[*].shakhaVevsthapak").value(hasItem(DEFAULT_SHAKHA_VEVSTHAPAK)))
            .andExpect(jsonPath("$.[*].suchak").value(hasItem(DEFAULT_SUCHAK)))
            .andExpect(jsonPath("$.[*].aanumodak").value(hasItem(DEFAULT_AANUMODAK)))
            .andExpect(jsonPath("$.[*].vasuliAdhikari").value(hasItem(DEFAULT_VASULI_ADHIKARI)))
            .andExpect(jsonPath("$.[*].arOffice").value(hasItem(DEFAULT_AR_OFFICE)))
            .andExpect(jsonPath("$.[*].tharavNumber").value(hasItem(DEFAULT_THARAV_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].tharavDinank").value(hasItem(DEFAULT_THARAV_DINANK.toString())))
            .andExpect(jsonPath("$.[*].karjFedNotice").value(hasItem(DEFAULT_KARJ_FED_NOTICE.toString())))
            .andExpect(jsonPath("$.[*].oneZeroOneNoticeOne").value(hasItem(DEFAULT_ONE_ZERO_ONE_NOTICE_ONE.toString())))
            .andExpect(jsonPath("$.[*].oneZeroOneNoticeTwo").value(hasItem(DEFAULT_ONE_ZERO_ONE_NOTICE_TWO.toString())))
            .andExpect(jsonPath("$.[*].vishayKramank").value(hasItem(DEFAULT_VISHAY_KRAMANK)))
            .andExpect(jsonPath("$.[*].war").value(hasItem(DEFAULT_WAR)))
            .andExpect(jsonPath("$.[*].vel").value(hasItem(DEFAULT_VEL)))
            .andExpect(jsonPath("$.[*].maganiNotice").value(hasItem(DEFAULT_MAGANI_NOTICE.toString())))
            .andExpect(jsonPath("$.[*].etarKharch").value(hasItem(DEFAULT_ETAR_KHARCH.doubleValue())))
            .andExpect(jsonPath("$.[*].noticeKharch").value(hasItem(DEFAULT_NOTICE_KHARCH.doubleValue())))
            .andExpect(jsonPath("$.[*].vasuliKharch").value(hasItem(DEFAULT_VASULI_KHARCH.doubleValue())));
    }

    @Test
    @Transactional
    void getCourtCaseSetting() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get the courtCaseSetting
        restCourtCaseSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, courtCaseSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courtCaseSetting.getId().intValue()))
            .andExpect(jsonPath("$.dinank").value(DEFAULT_DINANK.toString()))
            .andExpect(jsonPath("$.shakhaVevsthapak").value(DEFAULT_SHAKHA_VEVSTHAPAK))
            .andExpect(jsonPath("$.suchak").value(DEFAULT_SUCHAK))
            .andExpect(jsonPath("$.aanumodak").value(DEFAULT_AANUMODAK))
            .andExpect(jsonPath("$.vasuliAdhikari").value(DEFAULT_VASULI_ADHIKARI))
            .andExpect(jsonPath("$.arOffice").value(DEFAULT_AR_OFFICE))
            .andExpect(jsonPath("$.tharavNumber").value(DEFAULT_THARAV_NUMBER.intValue()))
            .andExpect(jsonPath("$.tharavDinank").value(DEFAULT_THARAV_DINANK.toString()))
            .andExpect(jsonPath("$.karjFedNotice").value(DEFAULT_KARJ_FED_NOTICE.toString()))
            .andExpect(jsonPath("$.oneZeroOneNoticeOne").value(DEFAULT_ONE_ZERO_ONE_NOTICE_ONE.toString()))
            .andExpect(jsonPath("$.oneZeroOneNoticeTwo").value(DEFAULT_ONE_ZERO_ONE_NOTICE_TWO.toString()))
            .andExpect(jsonPath("$.vishayKramank").value(DEFAULT_VISHAY_KRAMANK))
            .andExpect(jsonPath("$.war").value(DEFAULT_WAR))
            .andExpect(jsonPath("$.vel").value(DEFAULT_VEL))
            .andExpect(jsonPath("$.maganiNotice").value(DEFAULT_MAGANI_NOTICE.toString()))
            .andExpect(jsonPath("$.etarKharch").value(DEFAULT_ETAR_KHARCH.doubleValue()))
            .andExpect(jsonPath("$.noticeKharch").value(DEFAULT_NOTICE_KHARCH.doubleValue()))
            .andExpect(jsonPath("$.vasuliKharch").value(DEFAULT_VASULI_KHARCH.doubleValue()));
    }

    @Test
    @Transactional
    void getCourtCaseSettingsByIdFiltering() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        Long id = courtCaseSetting.getId();

        defaultCourtCaseSettingShouldBeFound("id.equals=" + id);
        defaultCourtCaseSettingShouldNotBeFound("id.notEquals=" + id);

        defaultCourtCaseSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourtCaseSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultCourtCaseSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourtCaseSettingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByDinankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where dinank equals to DEFAULT_DINANK
        defaultCourtCaseSettingShouldBeFound("dinank.equals=" + DEFAULT_DINANK);

        // Get all the courtCaseSettingList where dinank equals to UPDATED_DINANK
        defaultCourtCaseSettingShouldNotBeFound("dinank.equals=" + UPDATED_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByDinankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where dinank in DEFAULT_DINANK or UPDATED_DINANK
        defaultCourtCaseSettingShouldBeFound("dinank.in=" + DEFAULT_DINANK + "," + UPDATED_DINANK);

        // Get all the courtCaseSettingList where dinank equals to UPDATED_DINANK
        defaultCourtCaseSettingShouldNotBeFound("dinank.in=" + UPDATED_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByDinankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where dinank is not null
        defaultCourtCaseSettingShouldBeFound("dinank.specified=true");

        // Get all the courtCaseSettingList where dinank is null
        defaultCourtCaseSettingShouldNotBeFound("dinank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByShakhaVevsthapakIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where shakhaVevsthapak equals to DEFAULT_SHAKHA_VEVSTHAPAK
        defaultCourtCaseSettingShouldBeFound("shakhaVevsthapak.equals=" + DEFAULT_SHAKHA_VEVSTHAPAK);

        // Get all the courtCaseSettingList where shakhaVevsthapak equals to UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseSettingShouldNotBeFound("shakhaVevsthapak.equals=" + UPDATED_SHAKHA_VEVSTHAPAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByShakhaVevsthapakIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where shakhaVevsthapak in DEFAULT_SHAKHA_VEVSTHAPAK or UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseSettingShouldBeFound("shakhaVevsthapak.in=" + DEFAULT_SHAKHA_VEVSTHAPAK + "," + UPDATED_SHAKHA_VEVSTHAPAK);

        // Get all the courtCaseSettingList where shakhaVevsthapak equals to UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseSettingShouldNotBeFound("shakhaVevsthapak.in=" + UPDATED_SHAKHA_VEVSTHAPAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByShakhaVevsthapakIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where shakhaVevsthapak is not null
        defaultCourtCaseSettingShouldBeFound("shakhaVevsthapak.specified=true");

        // Get all the courtCaseSettingList where shakhaVevsthapak is null
        defaultCourtCaseSettingShouldNotBeFound("shakhaVevsthapak.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByShakhaVevsthapakContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where shakhaVevsthapak contains DEFAULT_SHAKHA_VEVSTHAPAK
        defaultCourtCaseSettingShouldBeFound("shakhaVevsthapak.contains=" + DEFAULT_SHAKHA_VEVSTHAPAK);

        // Get all the courtCaseSettingList where shakhaVevsthapak contains UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseSettingShouldNotBeFound("shakhaVevsthapak.contains=" + UPDATED_SHAKHA_VEVSTHAPAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByShakhaVevsthapakNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where shakhaVevsthapak does not contain DEFAULT_SHAKHA_VEVSTHAPAK
        defaultCourtCaseSettingShouldNotBeFound("shakhaVevsthapak.doesNotContain=" + DEFAULT_SHAKHA_VEVSTHAPAK);

        // Get all the courtCaseSettingList where shakhaVevsthapak does not contain UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseSettingShouldBeFound("shakhaVevsthapak.doesNotContain=" + UPDATED_SHAKHA_VEVSTHAPAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchak equals to DEFAULT_SUCHAK
        defaultCourtCaseSettingShouldBeFound("suchak.equals=" + DEFAULT_SUCHAK);

        // Get all the courtCaseSettingList where suchak equals to UPDATED_SUCHAK
        defaultCourtCaseSettingShouldNotBeFound("suchak.equals=" + UPDATED_SUCHAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchak in DEFAULT_SUCHAK or UPDATED_SUCHAK
        defaultCourtCaseSettingShouldBeFound("suchak.in=" + DEFAULT_SUCHAK + "," + UPDATED_SUCHAK);

        // Get all the courtCaseSettingList where suchak equals to UPDATED_SUCHAK
        defaultCourtCaseSettingShouldNotBeFound("suchak.in=" + UPDATED_SUCHAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchak is not null
        defaultCourtCaseSettingShouldBeFound("suchak.specified=true");

        // Get all the courtCaseSettingList where suchak is null
        defaultCourtCaseSettingShouldNotBeFound("suchak.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchak contains DEFAULT_SUCHAK
        defaultCourtCaseSettingShouldBeFound("suchak.contains=" + DEFAULT_SUCHAK);

        // Get all the courtCaseSettingList where suchak contains UPDATED_SUCHAK
        defaultCourtCaseSettingShouldNotBeFound("suchak.contains=" + UPDATED_SUCHAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchak does not contain DEFAULT_SUCHAK
        defaultCourtCaseSettingShouldNotBeFound("suchak.doesNotContain=" + DEFAULT_SUCHAK);

        // Get all the courtCaseSettingList where suchak does not contain UPDATED_SUCHAK
        defaultCourtCaseSettingShouldBeFound("suchak.doesNotContain=" + UPDATED_SUCHAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAanumodakIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where aanumodak equals to DEFAULT_AANUMODAK
        defaultCourtCaseSettingShouldBeFound("aanumodak.equals=" + DEFAULT_AANUMODAK);

        // Get all the courtCaseSettingList where aanumodak equals to UPDATED_AANUMODAK
        defaultCourtCaseSettingShouldNotBeFound("aanumodak.equals=" + UPDATED_AANUMODAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAanumodakIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where aanumodak in DEFAULT_AANUMODAK or UPDATED_AANUMODAK
        defaultCourtCaseSettingShouldBeFound("aanumodak.in=" + DEFAULT_AANUMODAK + "," + UPDATED_AANUMODAK);

        // Get all the courtCaseSettingList where aanumodak equals to UPDATED_AANUMODAK
        defaultCourtCaseSettingShouldNotBeFound("aanumodak.in=" + UPDATED_AANUMODAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAanumodakIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where aanumodak is not null
        defaultCourtCaseSettingShouldBeFound("aanumodak.specified=true");

        // Get all the courtCaseSettingList where aanumodak is null
        defaultCourtCaseSettingShouldNotBeFound("aanumodak.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAanumodakContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where aanumodak contains DEFAULT_AANUMODAK
        defaultCourtCaseSettingShouldBeFound("aanumodak.contains=" + DEFAULT_AANUMODAK);

        // Get all the courtCaseSettingList where aanumodak contains UPDATED_AANUMODAK
        defaultCourtCaseSettingShouldNotBeFound("aanumodak.contains=" + UPDATED_AANUMODAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAanumodakNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where aanumodak does not contain DEFAULT_AANUMODAK
        defaultCourtCaseSettingShouldNotBeFound("aanumodak.doesNotContain=" + DEFAULT_AANUMODAK);

        // Get all the courtCaseSettingList where aanumodak does not contain UPDATED_AANUMODAK
        defaultCourtCaseSettingShouldBeFound("aanumodak.doesNotContain=" + UPDATED_AANUMODAK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliAdhikariIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikari equals to DEFAULT_VASULI_ADHIKARI
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikari.equals=" + DEFAULT_VASULI_ADHIKARI);

        // Get all the courtCaseSettingList where vasuliAdhikari equals to UPDATED_VASULI_ADHIKARI
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikari.equals=" + UPDATED_VASULI_ADHIKARI);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliAdhikariIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikari in DEFAULT_VASULI_ADHIKARI or UPDATED_VASULI_ADHIKARI
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikari.in=" + DEFAULT_VASULI_ADHIKARI + "," + UPDATED_VASULI_ADHIKARI);

        // Get all the courtCaseSettingList where vasuliAdhikari equals to UPDATED_VASULI_ADHIKARI
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikari.in=" + UPDATED_VASULI_ADHIKARI);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliAdhikariIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikari is not null
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikari.specified=true");

        // Get all the courtCaseSettingList where vasuliAdhikari is null
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikari.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliAdhikariContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikari contains DEFAULT_VASULI_ADHIKARI
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikari.contains=" + DEFAULT_VASULI_ADHIKARI);

        // Get all the courtCaseSettingList where vasuliAdhikari contains UPDATED_VASULI_ADHIKARI
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikari.contains=" + UPDATED_VASULI_ADHIKARI);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliAdhikariNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikari does not contain DEFAULT_VASULI_ADHIKARI
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikari.doesNotContain=" + DEFAULT_VASULI_ADHIKARI);

        // Get all the courtCaseSettingList where vasuliAdhikari does not contain UPDATED_VASULI_ADHIKARI
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikari.doesNotContain=" + UPDATED_VASULI_ADHIKARI);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOffice equals to DEFAULT_AR_OFFICE
        defaultCourtCaseSettingShouldBeFound("arOffice.equals=" + DEFAULT_AR_OFFICE);

        // Get all the courtCaseSettingList where arOffice equals to UPDATED_AR_OFFICE
        defaultCourtCaseSettingShouldNotBeFound("arOffice.equals=" + UPDATED_AR_OFFICE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOffice in DEFAULT_AR_OFFICE or UPDATED_AR_OFFICE
        defaultCourtCaseSettingShouldBeFound("arOffice.in=" + DEFAULT_AR_OFFICE + "," + UPDATED_AR_OFFICE);

        // Get all the courtCaseSettingList where arOffice equals to UPDATED_AR_OFFICE
        defaultCourtCaseSettingShouldNotBeFound("arOffice.in=" + UPDATED_AR_OFFICE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOffice is not null
        defaultCourtCaseSettingShouldBeFound("arOffice.specified=true");

        // Get all the courtCaseSettingList where arOffice is null
        defaultCourtCaseSettingShouldNotBeFound("arOffice.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOffice contains DEFAULT_AR_OFFICE
        defaultCourtCaseSettingShouldBeFound("arOffice.contains=" + DEFAULT_AR_OFFICE);

        // Get all the courtCaseSettingList where arOffice contains UPDATED_AR_OFFICE
        defaultCourtCaseSettingShouldNotBeFound("arOffice.contains=" + UPDATED_AR_OFFICE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOffice does not contain DEFAULT_AR_OFFICE
        defaultCourtCaseSettingShouldNotBeFound("arOffice.doesNotContain=" + DEFAULT_AR_OFFICE);

        // Get all the courtCaseSettingList where arOffice does not contain UPDATED_AR_OFFICE
        defaultCourtCaseSettingShouldBeFound("arOffice.doesNotContain=" + UPDATED_AR_OFFICE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavNumber equals to DEFAULT_THARAV_NUMBER
        defaultCourtCaseSettingShouldBeFound("tharavNumber.equals=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseSettingList where tharavNumber equals to UPDATED_THARAV_NUMBER
        defaultCourtCaseSettingShouldNotBeFound("tharavNumber.equals=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavNumberIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavNumber in DEFAULT_THARAV_NUMBER or UPDATED_THARAV_NUMBER
        defaultCourtCaseSettingShouldBeFound("tharavNumber.in=" + DEFAULT_THARAV_NUMBER + "," + UPDATED_THARAV_NUMBER);

        // Get all the courtCaseSettingList where tharavNumber equals to UPDATED_THARAV_NUMBER
        defaultCourtCaseSettingShouldNotBeFound("tharavNumber.in=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavNumber is not null
        defaultCourtCaseSettingShouldBeFound("tharavNumber.specified=true");

        // Get all the courtCaseSettingList where tharavNumber is null
        defaultCourtCaseSettingShouldNotBeFound("tharavNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavNumber is greater than or equal to DEFAULT_THARAV_NUMBER
        defaultCourtCaseSettingShouldBeFound("tharavNumber.greaterThanOrEqual=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseSettingList where tharavNumber is greater than or equal to UPDATED_THARAV_NUMBER
        defaultCourtCaseSettingShouldNotBeFound("tharavNumber.greaterThanOrEqual=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavNumber is less than or equal to DEFAULT_THARAV_NUMBER
        defaultCourtCaseSettingShouldBeFound("tharavNumber.lessThanOrEqual=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseSettingList where tharavNumber is less than or equal to SMALLER_THARAV_NUMBER
        defaultCourtCaseSettingShouldNotBeFound("tharavNumber.lessThanOrEqual=" + SMALLER_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavNumber is less than DEFAULT_THARAV_NUMBER
        defaultCourtCaseSettingShouldNotBeFound("tharavNumber.lessThan=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseSettingList where tharavNumber is less than UPDATED_THARAV_NUMBER
        defaultCourtCaseSettingShouldBeFound("tharavNumber.lessThan=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavNumber is greater than DEFAULT_THARAV_NUMBER
        defaultCourtCaseSettingShouldNotBeFound("tharavNumber.greaterThan=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseSettingList where tharavNumber is greater than SMALLER_THARAV_NUMBER
        defaultCourtCaseSettingShouldBeFound("tharavNumber.greaterThan=" + SMALLER_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavDinankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavDinank equals to DEFAULT_THARAV_DINANK
        defaultCourtCaseSettingShouldBeFound("tharavDinank.equals=" + DEFAULT_THARAV_DINANK);

        // Get all the courtCaseSettingList where tharavDinank equals to UPDATED_THARAV_DINANK
        defaultCourtCaseSettingShouldNotBeFound("tharavDinank.equals=" + UPDATED_THARAV_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavDinankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavDinank in DEFAULT_THARAV_DINANK or UPDATED_THARAV_DINANK
        defaultCourtCaseSettingShouldBeFound("tharavDinank.in=" + DEFAULT_THARAV_DINANK + "," + UPDATED_THARAV_DINANK);

        // Get all the courtCaseSettingList where tharavDinank equals to UPDATED_THARAV_DINANK
        defaultCourtCaseSettingShouldNotBeFound("tharavDinank.in=" + UPDATED_THARAV_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByTharavDinankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where tharavDinank is not null
        defaultCourtCaseSettingShouldBeFound("tharavDinank.specified=true");

        // Get all the courtCaseSettingList where tharavDinank is null
        defaultCourtCaseSettingShouldNotBeFound("tharavDinank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByKarjFedNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where karjFedNotice equals to DEFAULT_KARJ_FED_NOTICE
        defaultCourtCaseSettingShouldBeFound("karjFedNotice.equals=" + DEFAULT_KARJ_FED_NOTICE);

        // Get all the courtCaseSettingList where karjFedNotice equals to UPDATED_KARJ_FED_NOTICE
        defaultCourtCaseSettingShouldNotBeFound("karjFedNotice.equals=" + UPDATED_KARJ_FED_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByKarjFedNoticeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where karjFedNotice in DEFAULT_KARJ_FED_NOTICE or UPDATED_KARJ_FED_NOTICE
        defaultCourtCaseSettingShouldBeFound("karjFedNotice.in=" + DEFAULT_KARJ_FED_NOTICE + "," + UPDATED_KARJ_FED_NOTICE);

        // Get all the courtCaseSettingList where karjFedNotice equals to UPDATED_KARJ_FED_NOTICE
        defaultCourtCaseSettingShouldNotBeFound("karjFedNotice.in=" + UPDATED_KARJ_FED_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByKarjFedNoticeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where karjFedNotice is not null
        defaultCourtCaseSettingShouldBeFound("karjFedNotice.specified=true");

        // Get all the courtCaseSettingList where karjFedNotice is null
        defaultCourtCaseSettingShouldNotBeFound("karjFedNotice.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOneZeroOneNoticeOneIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where oneZeroOneNoticeOne equals to DEFAULT_ONE_ZERO_ONE_NOTICE_ONE
        defaultCourtCaseSettingShouldBeFound("oneZeroOneNoticeOne.equals=" + DEFAULT_ONE_ZERO_ONE_NOTICE_ONE);

        // Get all the courtCaseSettingList where oneZeroOneNoticeOne equals to UPDATED_ONE_ZERO_ONE_NOTICE_ONE
        defaultCourtCaseSettingShouldNotBeFound("oneZeroOneNoticeOne.equals=" + UPDATED_ONE_ZERO_ONE_NOTICE_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOneZeroOneNoticeOneIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where oneZeroOneNoticeOne in DEFAULT_ONE_ZERO_ONE_NOTICE_ONE or UPDATED_ONE_ZERO_ONE_NOTICE_ONE
        defaultCourtCaseSettingShouldBeFound(
            "oneZeroOneNoticeOne.in=" + DEFAULT_ONE_ZERO_ONE_NOTICE_ONE + "," + UPDATED_ONE_ZERO_ONE_NOTICE_ONE
        );

        // Get all the courtCaseSettingList where oneZeroOneNoticeOne equals to UPDATED_ONE_ZERO_ONE_NOTICE_ONE
        defaultCourtCaseSettingShouldNotBeFound("oneZeroOneNoticeOne.in=" + UPDATED_ONE_ZERO_ONE_NOTICE_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOneZeroOneNoticeOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where oneZeroOneNoticeOne is not null
        defaultCourtCaseSettingShouldBeFound("oneZeroOneNoticeOne.specified=true");

        // Get all the courtCaseSettingList where oneZeroOneNoticeOne is null
        defaultCourtCaseSettingShouldNotBeFound("oneZeroOneNoticeOne.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOneZeroOneNoticeTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where oneZeroOneNoticeTwo equals to DEFAULT_ONE_ZERO_ONE_NOTICE_TWO
        defaultCourtCaseSettingShouldBeFound("oneZeroOneNoticeTwo.equals=" + DEFAULT_ONE_ZERO_ONE_NOTICE_TWO);

        // Get all the courtCaseSettingList where oneZeroOneNoticeTwo equals to UPDATED_ONE_ZERO_ONE_NOTICE_TWO
        defaultCourtCaseSettingShouldNotBeFound("oneZeroOneNoticeTwo.equals=" + UPDATED_ONE_ZERO_ONE_NOTICE_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOneZeroOneNoticeTwoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where oneZeroOneNoticeTwo in DEFAULT_ONE_ZERO_ONE_NOTICE_TWO or UPDATED_ONE_ZERO_ONE_NOTICE_TWO
        defaultCourtCaseSettingShouldBeFound(
            "oneZeroOneNoticeTwo.in=" + DEFAULT_ONE_ZERO_ONE_NOTICE_TWO + "," + UPDATED_ONE_ZERO_ONE_NOTICE_TWO
        );

        // Get all the courtCaseSettingList where oneZeroOneNoticeTwo equals to UPDATED_ONE_ZERO_ONE_NOTICE_TWO
        defaultCourtCaseSettingShouldNotBeFound("oneZeroOneNoticeTwo.in=" + UPDATED_ONE_ZERO_ONE_NOTICE_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOneZeroOneNoticeTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where oneZeroOneNoticeTwo is not null
        defaultCourtCaseSettingShouldBeFound("oneZeroOneNoticeTwo.specified=true");

        // Get all the courtCaseSettingList where oneZeroOneNoticeTwo is null
        defaultCourtCaseSettingShouldNotBeFound("oneZeroOneNoticeTwo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVishayKramankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vishayKramank equals to DEFAULT_VISHAY_KRAMANK
        defaultCourtCaseSettingShouldBeFound("vishayKramank.equals=" + DEFAULT_VISHAY_KRAMANK);

        // Get all the courtCaseSettingList where vishayKramank equals to UPDATED_VISHAY_KRAMANK
        defaultCourtCaseSettingShouldNotBeFound("vishayKramank.equals=" + UPDATED_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVishayKramankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vishayKramank in DEFAULT_VISHAY_KRAMANK or UPDATED_VISHAY_KRAMANK
        defaultCourtCaseSettingShouldBeFound("vishayKramank.in=" + DEFAULT_VISHAY_KRAMANK + "," + UPDATED_VISHAY_KRAMANK);

        // Get all the courtCaseSettingList where vishayKramank equals to UPDATED_VISHAY_KRAMANK
        defaultCourtCaseSettingShouldNotBeFound("vishayKramank.in=" + UPDATED_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVishayKramankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vishayKramank is not null
        defaultCourtCaseSettingShouldBeFound("vishayKramank.specified=true");

        // Get all the courtCaseSettingList where vishayKramank is null
        defaultCourtCaseSettingShouldNotBeFound("vishayKramank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVishayKramankContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vishayKramank contains DEFAULT_VISHAY_KRAMANK
        defaultCourtCaseSettingShouldBeFound("vishayKramank.contains=" + DEFAULT_VISHAY_KRAMANK);

        // Get all the courtCaseSettingList where vishayKramank contains UPDATED_VISHAY_KRAMANK
        defaultCourtCaseSettingShouldNotBeFound("vishayKramank.contains=" + UPDATED_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVishayKramankNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vishayKramank does not contain DEFAULT_VISHAY_KRAMANK
        defaultCourtCaseSettingShouldNotBeFound("vishayKramank.doesNotContain=" + DEFAULT_VISHAY_KRAMANK);

        // Get all the courtCaseSettingList where vishayKramank does not contain UPDATED_VISHAY_KRAMANK
        defaultCourtCaseSettingShouldBeFound("vishayKramank.doesNotContain=" + UPDATED_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByWarIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where war equals to DEFAULT_WAR
        defaultCourtCaseSettingShouldBeFound("war.equals=" + DEFAULT_WAR);

        // Get all the courtCaseSettingList where war equals to UPDATED_WAR
        defaultCourtCaseSettingShouldNotBeFound("war.equals=" + UPDATED_WAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByWarIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where war in DEFAULT_WAR or UPDATED_WAR
        defaultCourtCaseSettingShouldBeFound("war.in=" + DEFAULT_WAR + "," + UPDATED_WAR);

        // Get all the courtCaseSettingList where war equals to UPDATED_WAR
        defaultCourtCaseSettingShouldNotBeFound("war.in=" + UPDATED_WAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByWarIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where war is not null
        defaultCourtCaseSettingShouldBeFound("war.specified=true");

        // Get all the courtCaseSettingList where war is null
        defaultCourtCaseSettingShouldNotBeFound("war.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByWarContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where war contains DEFAULT_WAR
        defaultCourtCaseSettingShouldBeFound("war.contains=" + DEFAULT_WAR);

        // Get all the courtCaseSettingList where war contains UPDATED_WAR
        defaultCourtCaseSettingShouldNotBeFound("war.contains=" + UPDATED_WAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByWarNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where war does not contain DEFAULT_WAR
        defaultCourtCaseSettingShouldNotBeFound("war.doesNotContain=" + DEFAULT_WAR);

        // Get all the courtCaseSettingList where war does not contain UPDATED_WAR
        defaultCourtCaseSettingShouldBeFound("war.doesNotContain=" + UPDATED_WAR);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVelIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vel equals to DEFAULT_VEL
        defaultCourtCaseSettingShouldBeFound("vel.equals=" + DEFAULT_VEL);

        // Get all the courtCaseSettingList where vel equals to UPDATED_VEL
        defaultCourtCaseSettingShouldNotBeFound("vel.equals=" + UPDATED_VEL);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVelIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vel in DEFAULT_VEL or UPDATED_VEL
        defaultCourtCaseSettingShouldBeFound("vel.in=" + DEFAULT_VEL + "," + UPDATED_VEL);

        // Get all the courtCaseSettingList where vel equals to UPDATED_VEL
        defaultCourtCaseSettingShouldNotBeFound("vel.in=" + UPDATED_VEL);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVelIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vel is not null
        defaultCourtCaseSettingShouldBeFound("vel.specified=true");

        // Get all the courtCaseSettingList where vel is null
        defaultCourtCaseSettingShouldNotBeFound("vel.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVelContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vel contains DEFAULT_VEL
        defaultCourtCaseSettingShouldBeFound("vel.contains=" + DEFAULT_VEL);

        // Get all the courtCaseSettingList where vel contains UPDATED_VEL
        defaultCourtCaseSettingShouldNotBeFound("vel.contains=" + UPDATED_VEL);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVelNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vel does not contain DEFAULT_VEL
        defaultCourtCaseSettingShouldNotBeFound("vel.doesNotContain=" + DEFAULT_VEL);

        // Get all the courtCaseSettingList where vel does not contain UPDATED_VEL
        defaultCourtCaseSettingShouldBeFound("vel.doesNotContain=" + UPDATED_VEL);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMaganiNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where maganiNotice equals to DEFAULT_MAGANI_NOTICE
        defaultCourtCaseSettingShouldBeFound("maganiNotice.equals=" + DEFAULT_MAGANI_NOTICE);

        // Get all the courtCaseSettingList where maganiNotice equals to UPDATED_MAGANI_NOTICE
        defaultCourtCaseSettingShouldNotBeFound("maganiNotice.equals=" + UPDATED_MAGANI_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMaganiNoticeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where maganiNotice in DEFAULT_MAGANI_NOTICE or UPDATED_MAGANI_NOTICE
        defaultCourtCaseSettingShouldBeFound("maganiNotice.in=" + DEFAULT_MAGANI_NOTICE + "," + UPDATED_MAGANI_NOTICE);

        // Get all the courtCaseSettingList where maganiNotice equals to UPDATED_MAGANI_NOTICE
        defaultCourtCaseSettingShouldNotBeFound("maganiNotice.in=" + UPDATED_MAGANI_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMaganiNoticeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where maganiNotice is not null
        defaultCourtCaseSettingShouldBeFound("maganiNotice.specified=true");

        // Get all the courtCaseSettingList where maganiNotice is null
        defaultCourtCaseSettingShouldNotBeFound("maganiNotice.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByEtarKharchIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where etarKharch equals to DEFAULT_ETAR_KHARCH
        defaultCourtCaseSettingShouldBeFound("etarKharch.equals=" + DEFAULT_ETAR_KHARCH);

        // Get all the courtCaseSettingList where etarKharch equals to UPDATED_ETAR_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("etarKharch.equals=" + UPDATED_ETAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByEtarKharchIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where etarKharch in DEFAULT_ETAR_KHARCH or UPDATED_ETAR_KHARCH
        defaultCourtCaseSettingShouldBeFound("etarKharch.in=" + DEFAULT_ETAR_KHARCH + "," + UPDATED_ETAR_KHARCH);

        // Get all the courtCaseSettingList where etarKharch equals to UPDATED_ETAR_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("etarKharch.in=" + UPDATED_ETAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByEtarKharchIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where etarKharch is not null
        defaultCourtCaseSettingShouldBeFound("etarKharch.specified=true");

        // Get all the courtCaseSettingList where etarKharch is null
        defaultCourtCaseSettingShouldNotBeFound("etarKharch.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByEtarKharchIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where etarKharch is greater than or equal to DEFAULT_ETAR_KHARCH
        defaultCourtCaseSettingShouldBeFound("etarKharch.greaterThanOrEqual=" + DEFAULT_ETAR_KHARCH);

        // Get all the courtCaseSettingList where etarKharch is greater than or equal to UPDATED_ETAR_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("etarKharch.greaterThanOrEqual=" + UPDATED_ETAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByEtarKharchIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where etarKharch is less than or equal to DEFAULT_ETAR_KHARCH
        defaultCourtCaseSettingShouldBeFound("etarKharch.lessThanOrEqual=" + DEFAULT_ETAR_KHARCH);

        // Get all the courtCaseSettingList where etarKharch is less than or equal to SMALLER_ETAR_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("etarKharch.lessThanOrEqual=" + SMALLER_ETAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByEtarKharchIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where etarKharch is less than DEFAULT_ETAR_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("etarKharch.lessThan=" + DEFAULT_ETAR_KHARCH);

        // Get all the courtCaseSettingList where etarKharch is less than UPDATED_ETAR_KHARCH
        defaultCourtCaseSettingShouldBeFound("etarKharch.lessThan=" + UPDATED_ETAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByEtarKharchIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where etarKharch is greater than DEFAULT_ETAR_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("etarKharch.greaterThan=" + DEFAULT_ETAR_KHARCH);

        // Get all the courtCaseSettingList where etarKharch is greater than SMALLER_ETAR_KHARCH
        defaultCourtCaseSettingShouldBeFound("etarKharch.greaterThan=" + SMALLER_ETAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeKharchIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeKharch equals to DEFAULT_NOTICE_KHARCH
        defaultCourtCaseSettingShouldBeFound("noticeKharch.equals=" + DEFAULT_NOTICE_KHARCH);

        // Get all the courtCaseSettingList where noticeKharch equals to UPDATED_NOTICE_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("noticeKharch.equals=" + UPDATED_NOTICE_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeKharchIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeKharch in DEFAULT_NOTICE_KHARCH or UPDATED_NOTICE_KHARCH
        defaultCourtCaseSettingShouldBeFound("noticeKharch.in=" + DEFAULT_NOTICE_KHARCH + "," + UPDATED_NOTICE_KHARCH);

        // Get all the courtCaseSettingList where noticeKharch equals to UPDATED_NOTICE_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("noticeKharch.in=" + UPDATED_NOTICE_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeKharchIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeKharch is not null
        defaultCourtCaseSettingShouldBeFound("noticeKharch.specified=true");

        // Get all the courtCaseSettingList where noticeKharch is null
        defaultCourtCaseSettingShouldNotBeFound("noticeKharch.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeKharchIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeKharch is greater than or equal to DEFAULT_NOTICE_KHARCH
        defaultCourtCaseSettingShouldBeFound("noticeKharch.greaterThanOrEqual=" + DEFAULT_NOTICE_KHARCH);

        // Get all the courtCaseSettingList where noticeKharch is greater than or equal to UPDATED_NOTICE_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("noticeKharch.greaterThanOrEqual=" + UPDATED_NOTICE_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeKharchIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeKharch is less than or equal to DEFAULT_NOTICE_KHARCH
        defaultCourtCaseSettingShouldBeFound("noticeKharch.lessThanOrEqual=" + DEFAULT_NOTICE_KHARCH);

        // Get all the courtCaseSettingList where noticeKharch is less than or equal to SMALLER_NOTICE_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("noticeKharch.lessThanOrEqual=" + SMALLER_NOTICE_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeKharchIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeKharch is less than DEFAULT_NOTICE_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("noticeKharch.lessThan=" + DEFAULT_NOTICE_KHARCH);

        // Get all the courtCaseSettingList where noticeKharch is less than UPDATED_NOTICE_KHARCH
        defaultCourtCaseSettingShouldBeFound("noticeKharch.lessThan=" + UPDATED_NOTICE_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeKharchIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeKharch is greater than DEFAULT_NOTICE_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("noticeKharch.greaterThan=" + DEFAULT_NOTICE_KHARCH);

        // Get all the courtCaseSettingList where noticeKharch is greater than SMALLER_NOTICE_KHARCH
        defaultCourtCaseSettingShouldBeFound("noticeKharch.greaterThan=" + SMALLER_NOTICE_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliKharchIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliKharch equals to DEFAULT_VASULI_KHARCH
        defaultCourtCaseSettingShouldBeFound("vasuliKharch.equals=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseSettingList where vasuliKharch equals to UPDATED_VASULI_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("vasuliKharch.equals=" + UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliKharchIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliKharch in DEFAULT_VASULI_KHARCH or UPDATED_VASULI_KHARCH
        defaultCourtCaseSettingShouldBeFound("vasuliKharch.in=" + DEFAULT_VASULI_KHARCH + "," + UPDATED_VASULI_KHARCH);

        // Get all the courtCaseSettingList where vasuliKharch equals to UPDATED_VASULI_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("vasuliKharch.in=" + UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliKharchIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliKharch is not null
        defaultCourtCaseSettingShouldBeFound("vasuliKharch.specified=true");

        // Get all the courtCaseSettingList where vasuliKharch is null
        defaultCourtCaseSettingShouldNotBeFound("vasuliKharch.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliKharchIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliKharch is greater than or equal to DEFAULT_VASULI_KHARCH
        defaultCourtCaseSettingShouldBeFound("vasuliKharch.greaterThanOrEqual=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseSettingList where vasuliKharch is greater than or equal to UPDATED_VASULI_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("vasuliKharch.greaterThanOrEqual=" + UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliKharchIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliKharch is less than or equal to DEFAULT_VASULI_KHARCH
        defaultCourtCaseSettingShouldBeFound("vasuliKharch.lessThanOrEqual=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseSettingList where vasuliKharch is less than or equal to SMALLER_VASULI_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("vasuliKharch.lessThanOrEqual=" + SMALLER_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliKharchIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliKharch is less than DEFAULT_VASULI_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("vasuliKharch.lessThan=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseSettingList where vasuliKharch is less than UPDATED_VASULI_KHARCH
        defaultCourtCaseSettingShouldBeFound("vasuliKharch.lessThan=" + UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliKharchIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliKharch is greater than DEFAULT_VASULI_KHARCH
        defaultCourtCaseSettingShouldNotBeFound("vasuliKharch.greaterThan=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseSettingList where vasuliKharch is greater than SMALLER_VASULI_KHARCH
        defaultCourtCaseSettingShouldBeFound("vasuliKharch.greaterThan=" + SMALLER_VASULI_KHARCH);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourtCaseSettingShouldBeFound(String filter) throws Exception {
        restCourtCaseSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCaseSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].dinank").value(hasItem(DEFAULT_DINANK.toString())))
            .andExpect(jsonPath("$.[*].shakhaVevsthapak").value(hasItem(DEFAULT_SHAKHA_VEVSTHAPAK)))
            .andExpect(jsonPath("$.[*].suchak").value(hasItem(DEFAULT_SUCHAK)))
            .andExpect(jsonPath("$.[*].aanumodak").value(hasItem(DEFAULT_AANUMODAK)))
            .andExpect(jsonPath("$.[*].vasuliAdhikari").value(hasItem(DEFAULT_VASULI_ADHIKARI)))
            .andExpect(jsonPath("$.[*].arOffice").value(hasItem(DEFAULT_AR_OFFICE)))
            .andExpect(jsonPath("$.[*].tharavNumber").value(hasItem(DEFAULT_THARAV_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].tharavDinank").value(hasItem(DEFAULT_THARAV_DINANK.toString())))
            .andExpect(jsonPath("$.[*].karjFedNotice").value(hasItem(DEFAULT_KARJ_FED_NOTICE.toString())))
            .andExpect(jsonPath("$.[*].oneZeroOneNoticeOne").value(hasItem(DEFAULT_ONE_ZERO_ONE_NOTICE_ONE.toString())))
            .andExpect(jsonPath("$.[*].oneZeroOneNoticeTwo").value(hasItem(DEFAULT_ONE_ZERO_ONE_NOTICE_TWO.toString())))
            .andExpect(jsonPath("$.[*].vishayKramank").value(hasItem(DEFAULT_VISHAY_KRAMANK)))
            .andExpect(jsonPath("$.[*].war").value(hasItem(DEFAULT_WAR)))
            .andExpect(jsonPath("$.[*].vel").value(hasItem(DEFAULT_VEL)))
            .andExpect(jsonPath("$.[*].maganiNotice").value(hasItem(DEFAULT_MAGANI_NOTICE.toString())))
            .andExpect(jsonPath("$.[*].etarKharch").value(hasItem(DEFAULT_ETAR_KHARCH.doubleValue())))
            .andExpect(jsonPath("$.[*].noticeKharch").value(hasItem(DEFAULT_NOTICE_KHARCH.doubleValue())))
            .andExpect(jsonPath("$.[*].vasuliKharch").value(hasItem(DEFAULT_VASULI_KHARCH.doubleValue())));

        // Check, that the count call also returns 1
        restCourtCaseSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourtCaseSettingShouldNotBeFound(String filter) throws Exception {
        restCourtCaseSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourtCaseSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourtCaseSetting() throws Exception {
        // Get the courtCaseSetting
        restCourtCaseSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCourtCaseSetting() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        int databaseSizeBeforeUpdate = courtCaseSettingRepository.findAll().size();

        // Update the courtCaseSetting
        CourtCaseSetting updatedCourtCaseSetting = courtCaseSettingRepository.findById(courtCaseSetting.getId()).get();
        // Disconnect from session so that the updates on updatedCourtCaseSetting are not directly saved in db
        em.detach(updatedCourtCaseSetting);
        updatedCourtCaseSetting
            .dinank(UPDATED_DINANK)
            .shakhaVevsthapak(UPDATED_SHAKHA_VEVSTHAPAK)
            .suchak(UPDATED_SUCHAK)
            .aanumodak(UPDATED_AANUMODAK)
            .vasuliAdhikari(UPDATED_VASULI_ADHIKARI)
            .arOffice(UPDATED_AR_OFFICE)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDinank(UPDATED_THARAV_DINANK)
            .karjFedNotice(UPDATED_KARJ_FED_NOTICE)
            .oneZeroOneNoticeOne(UPDATED_ONE_ZERO_ONE_NOTICE_ONE)
            .oneZeroOneNoticeTwo(UPDATED_ONE_ZERO_ONE_NOTICE_TWO)
            .vishayKramank(UPDATED_VISHAY_KRAMANK)
            .war(UPDATED_WAR)
            .vel(UPDATED_VEL)
            .maganiNotice(UPDATED_MAGANI_NOTICE)
            .etarKharch(UPDATED_ETAR_KHARCH)
            .noticeKharch(UPDATED_NOTICE_KHARCH)
            .vasuliKharch(UPDATED_VASULI_KHARCH);

        restCourtCaseSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCourtCaseSetting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCourtCaseSetting))
            )
            .andExpect(status().isOk());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeUpdate);
        CourtCaseSetting testCourtCaseSetting = courtCaseSettingList.get(courtCaseSettingList.size() - 1);
        assertThat(testCourtCaseSetting.getDinank()).isEqualTo(UPDATED_DINANK);
        assertThat(testCourtCaseSetting.getShakhaVevsthapak()).isEqualTo(UPDATED_SHAKHA_VEVSTHAPAK);
        assertThat(testCourtCaseSetting.getSuchak()).isEqualTo(UPDATED_SUCHAK);
        assertThat(testCourtCaseSetting.getAanumodak()).isEqualTo(UPDATED_AANUMODAK);
        assertThat(testCourtCaseSetting.getVasuliAdhikari()).isEqualTo(UPDATED_VASULI_ADHIKARI);
        assertThat(testCourtCaseSetting.getArOffice()).isEqualTo(UPDATED_AR_OFFICE);
        assertThat(testCourtCaseSetting.getTharavNumber()).isEqualTo(UPDATED_THARAV_NUMBER);
        assertThat(testCourtCaseSetting.getTharavDinank()).isEqualTo(UPDATED_THARAV_DINANK);
        assertThat(testCourtCaseSetting.getKarjFedNotice()).isEqualTo(UPDATED_KARJ_FED_NOTICE);
        assertThat(testCourtCaseSetting.getOneZeroOneNoticeOne()).isEqualTo(UPDATED_ONE_ZERO_ONE_NOTICE_ONE);
        assertThat(testCourtCaseSetting.getOneZeroOneNoticeTwo()).isEqualTo(UPDATED_ONE_ZERO_ONE_NOTICE_TWO);
        assertThat(testCourtCaseSetting.getVishayKramank()).isEqualTo(UPDATED_VISHAY_KRAMANK);
        assertThat(testCourtCaseSetting.getWar()).isEqualTo(UPDATED_WAR);
        assertThat(testCourtCaseSetting.getVel()).isEqualTo(UPDATED_VEL);
        assertThat(testCourtCaseSetting.getMaganiNotice()).isEqualTo(UPDATED_MAGANI_NOTICE);
        assertThat(testCourtCaseSetting.getEtarKharch()).isEqualTo(UPDATED_ETAR_KHARCH);
        assertThat(testCourtCaseSetting.getNoticeKharch()).isEqualTo(UPDATED_NOTICE_KHARCH);
        assertThat(testCourtCaseSetting.getVasuliKharch()).isEqualTo(UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void putNonExistingCourtCaseSetting() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseSettingRepository.findAll().size();
        courtCaseSetting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courtCaseSetting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourtCaseSetting() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseSettingRepository.findAll().size();
        courtCaseSetting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourtCaseSetting() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseSettingRepository.findAll().size();
        courtCaseSetting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseSettingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseSetting))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourtCaseSettingWithPatch() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        int databaseSizeBeforeUpdate = courtCaseSettingRepository.findAll().size();

        // Update the courtCaseSetting using partial update
        CourtCaseSetting partialUpdatedCourtCaseSetting = new CourtCaseSetting();
        partialUpdatedCourtCaseSetting.setId(courtCaseSetting.getId());

        partialUpdatedCourtCaseSetting
            .shakhaVevsthapak(UPDATED_SHAKHA_VEVSTHAPAK)
            .suchak(UPDATED_SUCHAK)
            .aanumodak(UPDATED_AANUMODAK)
            .vasuliAdhikari(UPDATED_VASULI_ADHIKARI)
            .arOffice(UPDATED_AR_OFFICE)
            .oneZeroOneNoticeOne(UPDATED_ONE_ZERO_ONE_NOTICE_ONE)
            .vishayKramank(UPDATED_VISHAY_KRAMANK)
            .vel(UPDATED_VEL)
            .etarKharch(UPDATED_ETAR_KHARCH)
            .vasuliKharch(UPDATED_VASULI_KHARCH);

        restCourtCaseSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCaseSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCaseSetting))
            )
            .andExpect(status().isOk());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeUpdate);
        CourtCaseSetting testCourtCaseSetting = courtCaseSettingList.get(courtCaseSettingList.size() - 1);
        assertThat(testCourtCaseSetting.getDinank()).isEqualTo(DEFAULT_DINANK);
        assertThat(testCourtCaseSetting.getShakhaVevsthapak()).isEqualTo(UPDATED_SHAKHA_VEVSTHAPAK);
        assertThat(testCourtCaseSetting.getSuchak()).isEqualTo(UPDATED_SUCHAK);
        assertThat(testCourtCaseSetting.getAanumodak()).isEqualTo(UPDATED_AANUMODAK);
        assertThat(testCourtCaseSetting.getVasuliAdhikari()).isEqualTo(UPDATED_VASULI_ADHIKARI);
        assertThat(testCourtCaseSetting.getArOffice()).isEqualTo(UPDATED_AR_OFFICE);
        assertThat(testCourtCaseSetting.getTharavNumber()).isEqualTo(DEFAULT_THARAV_NUMBER);
        assertThat(testCourtCaseSetting.getTharavDinank()).isEqualTo(DEFAULT_THARAV_DINANK);
        assertThat(testCourtCaseSetting.getKarjFedNotice()).isEqualTo(DEFAULT_KARJ_FED_NOTICE);
        assertThat(testCourtCaseSetting.getOneZeroOneNoticeOne()).isEqualTo(UPDATED_ONE_ZERO_ONE_NOTICE_ONE);
        assertThat(testCourtCaseSetting.getOneZeroOneNoticeTwo()).isEqualTo(DEFAULT_ONE_ZERO_ONE_NOTICE_TWO);
        assertThat(testCourtCaseSetting.getVishayKramank()).isEqualTo(UPDATED_VISHAY_KRAMANK);
        assertThat(testCourtCaseSetting.getWar()).isEqualTo(DEFAULT_WAR);
        assertThat(testCourtCaseSetting.getVel()).isEqualTo(UPDATED_VEL);
        assertThat(testCourtCaseSetting.getMaganiNotice()).isEqualTo(DEFAULT_MAGANI_NOTICE);
        assertThat(testCourtCaseSetting.getEtarKharch()).isEqualTo(UPDATED_ETAR_KHARCH);
        assertThat(testCourtCaseSetting.getNoticeKharch()).isEqualTo(DEFAULT_NOTICE_KHARCH);
        assertThat(testCourtCaseSetting.getVasuliKharch()).isEqualTo(UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void fullUpdateCourtCaseSettingWithPatch() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        int databaseSizeBeforeUpdate = courtCaseSettingRepository.findAll().size();

        // Update the courtCaseSetting using partial update
        CourtCaseSetting partialUpdatedCourtCaseSetting = new CourtCaseSetting();
        partialUpdatedCourtCaseSetting.setId(courtCaseSetting.getId());

        partialUpdatedCourtCaseSetting
            .dinank(UPDATED_DINANK)
            .shakhaVevsthapak(UPDATED_SHAKHA_VEVSTHAPAK)
            .suchak(UPDATED_SUCHAK)
            .aanumodak(UPDATED_AANUMODAK)
            .vasuliAdhikari(UPDATED_VASULI_ADHIKARI)
            .arOffice(UPDATED_AR_OFFICE)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDinank(UPDATED_THARAV_DINANK)
            .karjFedNotice(UPDATED_KARJ_FED_NOTICE)
            .oneZeroOneNoticeOne(UPDATED_ONE_ZERO_ONE_NOTICE_ONE)
            .oneZeroOneNoticeTwo(UPDATED_ONE_ZERO_ONE_NOTICE_TWO)
            .vishayKramank(UPDATED_VISHAY_KRAMANK)
            .war(UPDATED_WAR)
            .vel(UPDATED_VEL)
            .maganiNotice(UPDATED_MAGANI_NOTICE)
            .etarKharch(UPDATED_ETAR_KHARCH)
            .noticeKharch(UPDATED_NOTICE_KHARCH)
            .vasuliKharch(UPDATED_VASULI_KHARCH);

        restCourtCaseSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCaseSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCaseSetting))
            )
            .andExpect(status().isOk());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeUpdate);
        CourtCaseSetting testCourtCaseSetting = courtCaseSettingList.get(courtCaseSettingList.size() - 1);
        assertThat(testCourtCaseSetting.getDinank()).isEqualTo(UPDATED_DINANK);
        assertThat(testCourtCaseSetting.getShakhaVevsthapak()).isEqualTo(UPDATED_SHAKHA_VEVSTHAPAK);
        assertThat(testCourtCaseSetting.getSuchak()).isEqualTo(UPDATED_SUCHAK);
        assertThat(testCourtCaseSetting.getAanumodak()).isEqualTo(UPDATED_AANUMODAK);
        assertThat(testCourtCaseSetting.getVasuliAdhikari()).isEqualTo(UPDATED_VASULI_ADHIKARI);
        assertThat(testCourtCaseSetting.getArOffice()).isEqualTo(UPDATED_AR_OFFICE);
        assertThat(testCourtCaseSetting.getTharavNumber()).isEqualTo(UPDATED_THARAV_NUMBER);
        assertThat(testCourtCaseSetting.getTharavDinank()).isEqualTo(UPDATED_THARAV_DINANK);
        assertThat(testCourtCaseSetting.getKarjFedNotice()).isEqualTo(UPDATED_KARJ_FED_NOTICE);
        assertThat(testCourtCaseSetting.getOneZeroOneNoticeOne()).isEqualTo(UPDATED_ONE_ZERO_ONE_NOTICE_ONE);
        assertThat(testCourtCaseSetting.getOneZeroOneNoticeTwo()).isEqualTo(UPDATED_ONE_ZERO_ONE_NOTICE_TWO);
        assertThat(testCourtCaseSetting.getVishayKramank()).isEqualTo(UPDATED_VISHAY_KRAMANK);
        assertThat(testCourtCaseSetting.getWar()).isEqualTo(UPDATED_WAR);
        assertThat(testCourtCaseSetting.getVel()).isEqualTo(UPDATED_VEL);
        assertThat(testCourtCaseSetting.getMaganiNotice()).isEqualTo(UPDATED_MAGANI_NOTICE);
        assertThat(testCourtCaseSetting.getEtarKharch()).isEqualTo(UPDATED_ETAR_KHARCH);
        assertThat(testCourtCaseSetting.getNoticeKharch()).isEqualTo(UPDATED_NOTICE_KHARCH);
        assertThat(testCourtCaseSetting.getVasuliKharch()).isEqualTo(UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void patchNonExistingCourtCaseSetting() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseSettingRepository.findAll().size();
        courtCaseSetting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courtCaseSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourtCaseSetting() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseSettingRepository.findAll().size();
        courtCaseSetting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourtCaseSetting() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseSettingRepository.findAll().size();
        courtCaseSetting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseSettingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseSetting))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCaseSetting in the database
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourtCaseSetting() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        int databaseSizeBeforeDelete = courtCaseSettingRepository.findAll().size();

        // Delete the courtCaseSetting
        restCourtCaseSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, courtCaseSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourtCaseSetting> courtCaseSettingList = courtCaseSettingRepository.findAll();
        assertThat(courtCaseSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
