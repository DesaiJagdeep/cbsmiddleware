package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CourtCaseSetting;
import com.cbs.middleware.repository.CourtCaseSettingRepository;
import com.cbs.middleware.service.criteria.CourtCaseSettingCriteria;
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
 * Integration tests for the {@link CourtCaseSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourtCaseSettingResourceIT {

    private static final String DEFAULT_VASULI_ADHIKARI_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VASULI_ADHIKARI_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AR_OFFICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_OFFICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHAIRMAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHAIRMAN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SACHIV_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SACHIV_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUCHAK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUCHAK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ANUMODAK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ANUMODAK_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_VASULI_EXPENSE = 1D;
    private static final Double UPDATED_VASULI_EXPENSE = 2D;
    private static final Double SMALLER_VASULI_EXPENSE = 1D - 1D;

    private static final Double DEFAULT_OTHER_EXPENSE = 1D;
    private static final Double UPDATED_OTHER_EXPENSE = 2D;
    private static final Double SMALLER_OTHER_EXPENSE = 1D - 1D;

    private static final Double DEFAULT_NOTICE_EXPENSE = 1D;
    private static final Double UPDATED_NOTICE_EXPENSE = 2D;
    private static final Double SMALLER_NOTICE_EXPENSE = 1D - 1D;

    private static final Long DEFAULT_MEETING_NO = 1L;
    private static final Long UPDATED_MEETING_NO = 2L;
    private static final Long SMALLER_MEETING_NO = 1L - 1L;

    private static final LocalDate DEFAULT_MEETING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MEETING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MEETING_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_SUBJECT_NO = 1L;
    private static final Long UPDATED_SUBJECT_NO = 2L;
    private static final Long SMALLER_SUBJECT_NO = 1L - 1L;

    private static final String DEFAULT_MEETING_DAY = "AAAAAAAAAA";
    private static final String UPDATED_MEETING_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_MEETING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_MEETING_TIME = "BBBBBBBBBB";

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
            .vasuliAdhikariName(DEFAULT_VASULI_ADHIKARI_NAME)
            .arOfficeName(DEFAULT_AR_OFFICE_NAME)
            .chairmanName(DEFAULT_CHAIRMAN_NAME)
            .sachivName(DEFAULT_SACHIV_NAME)
            .suchakName(DEFAULT_SUCHAK_NAME)
            .anumodakName(DEFAULT_ANUMODAK_NAME)
            .vasuliExpense(DEFAULT_VASULI_EXPENSE)
            .otherExpense(DEFAULT_OTHER_EXPENSE)
            .noticeExpense(DEFAULT_NOTICE_EXPENSE)
            .meetingNo(DEFAULT_MEETING_NO)
            .meetingDate(DEFAULT_MEETING_DATE)
            .subjectNo(DEFAULT_SUBJECT_NO)
            .meetingDay(DEFAULT_MEETING_DAY)
            .meetingTime(DEFAULT_MEETING_TIME);
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
            .vasuliAdhikariName(UPDATED_VASULI_ADHIKARI_NAME)
            .arOfficeName(UPDATED_AR_OFFICE_NAME)
            .chairmanName(UPDATED_CHAIRMAN_NAME)
            .sachivName(UPDATED_SACHIV_NAME)
            .suchakName(UPDATED_SUCHAK_NAME)
            .anumodakName(UPDATED_ANUMODAK_NAME)
            .vasuliExpense(UPDATED_VASULI_EXPENSE)
            .otherExpense(UPDATED_OTHER_EXPENSE)
            .noticeExpense(UPDATED_NOTICE_EXPENSE)
            .meetingNo(UPDATED_MEETING_NO)
            .meetingDate(UPDATED_MEETING_DATE)
            .subjectNo(UPDATED_SUBJECT_NO)
            .meetingDay(UPDATED_MEETING_DAY)
            .meetingTime(UPDATED_MEETING_TIME);
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
        assertThat(testCourtCaseSetting.getVasuliAdhikariName()).isEqualTo(DEFAULT_VASULI_ADHIKARI_NAME);
        assertThat(testCourtCaseSetting.getArOfficeName()).isEqualTo(DEFAULT_AR_OFFICE_NAME);
        assertThat(testCourtCaseSetting.getChairmanName()).isEqualTo(DEFAULT_CHAIRMAN_NAME);
        assertThat(testCourtCaseSetting.getSachivName()).isEqualTo(DEFAULT_SACHIV_NAME);
        assertThat(testCourtCaseSetting.getSuchakName()).isEqualTo(DEFAULT_SUCHAK_NAME);
        assertThat(testCourtCaseSetting.getAnumodakName()).isEqualTo(DEFAULT_ANUMODAK_NAME);
        assertThat(testCourtCaseSetting.getVasuliExpense()).isEqualTo(DEFAULT_VASULI_EXPENSE);
        assertThat(testCourtCaseSetting.getOtherExpense()).isEqualTo(DEFAULT_OTHER_EXPENSE);
        assertThat(testCourtCaseSetting.getNoticeExpense()).isEqualTo(DEFAULT_NOTICE_EXPENSE);
        assertThat(testCourtCaseSetting.getMeetingNo()).isEqualTo(DEFAULT_MEETING_NO);
        assertThat(testCourtCaseSetting.getMeetingDate()).isEqualTo(DEFAULT_MEETING_DATE);
        assertThat(testCourtCaseSetting.getSubjectNo()).isEqualTo(DEFAULT_SUBJECT_NO);
        assertThat(testCourtCaseSetting.getMeetingDay()).isEqualTo(DEFAULT_MEETING_DAY);
        assertThat(testCourtCaseSetting.getMeetingTime()).isEqualTo(DEFAULT_MEETING_TIME);
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
            .andExpect(jsonPath("$.[*].vasuliAdhikariName").value(hasItem(DEFAULT_VASULI_ADHIKARI_NAME)))
            .andExpect(jsonPath("$.[*].arOfficeName").value(hasItem(DEFAULT_AR_OFFICE_NAME)))
            .andExpect(jsonPath("$.[*].chairmanName").value(hasItem(DEFAULT_CHAIRMAN_NAME)))
            .andExpect(jsonPath("$.[*].sachivName").value(hasItem(DEFAULT_SACHIV_NAME)))
            .andExpect(jsonPath("$.[*].suchakName").value(hasItem(DEFAULT_SUCHAK_NAME)))
            .andExpect(jsonPath("$.[*].anumodakName").value(hasItem(DEFAULT_ANUMODAK_NAME)))
            .andExpect(jsonPath("$.[*].vasuliExpense").value(hasItem(DEFAULT_VASULI_EXPENSE.doubleValue())))
            .andExpect(jsonPath("$.[*].otherExpense").value(hasItem(DEFAULT_OTHER_EXPENSE.doubleValue())))
            .andExpect(jsonPath("$.[*].noticeExpense").value(hasItem(DEFAULT_NOTICE_EXPENSE.doubleValue())))
            .andExpect(jsonPath("$.[*].meetingNo").value(hasItem(DEFAULT_MEETING_NO.intValue())))
            .andExpect(jsonPath("$.[*].meetingDate").value(hasItem(DEFAULT_MEETING_DATE.toString())))
            .andExpect(jsonPath("$.[*].subjectNo").value(hasItem(DEFAULT_SUBJECT_NO.intValue())))
            .andExpect(jsonPath("$.[*].meetingDay").value(hasItem(DEFAULT_MEETING_DAY)))
            .andExpect(jsonPath("$.[*].meetingTime").value(hasItem(DEFAULT_MEETING_TIME)));
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
            .andExpect(jsonPath("$.vasuliAdhikariName").value(DEFAULT_VASULI_ADHIKARI_NAME))
            .andExpect(jsonPath("$.arOfficeName").value(DEFAULT_AR_OFFICE_NAME))
            .andExpect(jsonPath("$.chairmanName").value(DEFAULT_CHAIRMAN_NAME))
            .andExpect(jsonPath("$.sachivName").value(DEFAULT_SACHIV_NAME))
            .andExpect(jsonPath("$.suchakName").value(DEFAULT_SUCHAK_NAME))
            .andExpect(jsonPath("$.anumodakName").value(DEFAULT_ANUMODAK_NAME))
            .andExpect(jsonPath("$.vasuliExpense").value(DEFAULT_VASULI_EXPENSE.doubleValue()))
            .andExpect(jsonPath("$.otherExpense").value(DEFAULT_OTHER_EXPENSE.doubleValue()))
            .andExpect(jsonPath("$.noticeExpense").value(DEFAULT_NOTICE_EXPENSE.doubleValue()))
            .andExpect(jsonPath("$.meetingNo").value(DEFAULT_MEETING_NO.intValue()))
            .andExpect(jsonPath("$.meetingDate").value(DEFAULT_MEETING_DATE.toString()))
            .andExpect(jsonPath("$.subjectNo").value(DEFAULT_SUBJECT_NO.intValue()))
            .andExpect(jsonPath("$.meetingDay").value(DEFAULT_MEETING_DAY))
            .andExpect(jsonPath("$.meetingTime").value(DEFAULT_MEETING_TIME));
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
    void getAllCourtCaseSettingsByVasuliAdhikariNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikariName equals to DEFAULT_VASULI_ADHIKARI_NAME
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikariName.equals=" + DEFAULT_VASULI_ADHIKARI_NAME);

        // Get all the courtCaseSettingList where vasuliAdhikariName equals to UPDATED_VASULI_ADHIKARI_NAME
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikariName.equals=" + UPDATED_VASULI_ADHIKARI_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliAdhikariNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikariName in DEFAULT_VASULI_ADHIKARI_NAME or UPDATED_VASULI_ADHIKARI_NAME
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikariName.in=" + DEFAULT_VASULI_ADHIKARI_NAME + "," + UPDATED_VASULI_ADHIKARI_NAME);

        // Get all the courtCaseSettingList where vasuliAdhikariName equals to UPDATED_VASULI_ADHIKARI_NAME
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikariName.in=" + UPDATED_VASULI_ADHIKARI_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliAdhikariNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikariName is not null
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikariName.specified=true");

        // Get all the courtCaseSettingList where vasuliAdhikariName is null
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikariName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliAdhikariNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikariName contains DEFAULT_VASULI_ADHIKARI_NAME
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikariName.contains=" + DEFAULT_VASULI_ADHIKARI_NAME);

        // Get all the courtCaseSettingList where vasuliAdhikariName contains UPDATED_VASULI_ADHIKARI_NAME
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikariName.contains=" + UPDATED_VASULI_ADHIKARI_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliAdhikariNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliAdhikariName does not contain DEFAULT_VASULI_ADHIKARI_NAME
        defaultCourtCaseSettingShouldNotBeFound("vasuliAdhikariName.doesNotContain=" + DEFAULT_VASULI_ADHIKARI_NAME);

        // Get all the courtCaseSettingList where vasuliAdhikariName does not contain UPDATED_VASULI_ADHIKARI_NAME
        defaultCourtCaseSettingShouldBeFound("vasuliAdhikariName.doesNotContain=" + UPDATED_VASULI_ADHIKARI_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOfficeName equals to DEFAULT_AR_OFFICE_NAME
        defaultCourtCaseSettingShouldBeFound("arOfficeName.equals=" + DEFAULT_AR_OFFICE_NAME);

        // Get all the courtCaseSettingList where arOfficeName equals to UPDATED_AR_OFFICE_NAME
        defaultCourtCaseSettingShouldNotBeFound("arOfficeName.equals=" + UPDATED_AR_OFFICE_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOfficeName in DEFAULT_AR_OFFICE_NAME or UPDATED_AR_OFFICE_NAME
        defaultCourtCaseSettingShouldBeFound("arOfficeName.in=" + DEFAULT_AR_OFFICE_NAME + "," + UPDATED_AR_OFFICE_NAME);

        // Get all the courtCaseSettingList where arOfficeName equals to UPDATED_AR_OFFICE_NAME
        defaultCourtCaseSettingShouldNotBeFound("arOfficeName.in=" + UPDATED_AR_OFFICE_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOfficeName is not null
        defaultCourtCaseSettingShouldBeFound("arOfficeName.specified=true");

        // Get all the courtCaseSettingList where arOfficeName is null
        defaultCourtCaseSettingShouldNotBeFound("arOfficeName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOfficeName contains DEFAULT_AR_OFFICE_NAME
        defaultCourtCaseSettingShouldBeFound("arOfficeName.contains=" + DEFAULT_AR_OFFICE_NAME);

        // Get all the courtCaseSettingList where arOfficeName contains UPDATED_AR_OFFICE_NAME
        defaultCourtCaseSettingShouldNotBeFound("arOfficeName.contains=" + UPDATED_AR_OFFICE_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByArOfficeNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where arOfficeName does not contain DEFAULT_AR_OFFICE_NAME
        defaultCourtCaseSettingShouldNotBeFound("arOfficeName.doesNotContain=" + DEFAULT_AR_OFFICE_NAME);

        // Get all the courtCaseSettingList where arOfficeName does not contain UPDATED_AR_OFFICE_NAME
        defaultCourtCaseSettingShouldBeFound("arOfficeName.doesNotContain=" + UPDATED_AR_OFFICE_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByChairmanNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where chairmanName equals to DEFAULT_CHAIRMAN_NAME
        defaultCourtCaseSettingShouldBeFound("chairmanName.equals=" + DEFAULT_CHAIRMAN_NAME);

        // Get all the courtCaseSettingList where chairmanName equals to UPDATED_CHAIRMAN_NAME
        defaultCourtCaseSettingShouldNotBeFound("chairmanName.equals=" + UPDATED_CHAIRMAN_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByChairmanNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where chairmanName in DEFAULT_CHAIRMAN_NAME or UPDATED_CHAIRMAN_NAME
        defaultCourtCaseSettingShouldBeFound("chairmanName.in=" + DEFAULT_CHAIRMAN_NAME + "," + UPDATED_CHAIRMAN_NAME);

        // Get all the courtCaseSettingList where chairmanName equals to UPDATED_CHAIRMAN_NAME
        defaultCourtCaseSettingShouldNotBeFound("chairmanName.in=" + UPDATED_CHAIRMAN_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByChairmanNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where chairmanName is not null
        defaultCourtCaseSettingShouldBeFound("chairmanName.specified=true");

        // Get all the courtCaseSettingList where chairmanName is null
        defaultCourtCaseSettingShouldNotBeFound("chairmanName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByChairmanNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where chairmanName contains DEFAULT_CHAIRMAN_NAME
        defaultCourtCaseSettingShouldBeFound("chairmanName.contains=" + DEFAULT_CHAIRMAN_NAME);

        // Get all the courtCaseSettingList where chairmanName contains UPDATED_CHAIRMAN_NAME
        defaultCourtCaseSettingShouldNotBeFound("chairmanName.contains=" + UPDATED_CHAIRMAN_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByChairmanNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where chairmanName does not contain DEFAULT_CHAIRMAN_NAME
        defaultCourtCaseSettingShouldNotBeFound("chairmanName.doesNotContain=" + DEFAULT_CHAIRMAN_NAME);

        // Get all the courtCaseSettingList where chairmanName does not contain UPDATED_CHAIRMAN_NAME
        defaultCourtCaseSettingShouldBeFound("chairmanName.doesNotContain=" + UPDATED_CHAIRMAN_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySachivNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where sachivName equals to DEFAULT_SACHIV_NAME
        defaultCourtCaseSettingShouldBeFound("sachivName.equals=" + DEFAULT_SACHIV_NAME);

        // Get all the courtCaseSettingList where sachivName equals to UPDATED_SACHIV_NAME
        defaultCourtCaseSettingShouldNotBeFound("sachivName.equals=" + UPDATED_SACHIV_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySachivNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where sachivName in DEFAULT_SACHIV_NAME or UPDATED_SACHIV_NAME
        defaultCourtCaseSettingShouldBeFound("sachivName.in=" + DEFAULT_SACHIV_NAME + "," + UPDATED_SACHIV_NAME);

        // Get all the courtCaseSettingList where sachivName equals to UPDATED_SACHIV_NAME
        defaultCourtCaseSettingShouldNotBeFound("sachivName.in=" + UPDATED_SACHIV_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySachivNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where sachivName is not null
        defaultCourtCaseSettingShouldBeFound("sachivName.specified=true");

        // Get all the courtCaseSettingList where sachivName is null
        defaultCourtCaseSettingShouldNotBeFound("sachivName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySachivNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where sachivName contains DEFAULT_SACHIV_NAME
        defaultCourtCaseSettingShouldBeFound("sachivName.contains=" + DEFAULT_SACHIV_NAME);

        // Get all the courtCaseSettingList where sachivName contains UPDATED_SACHIV_NAME
        defaultCourtCaseSettingShouldNotBeFound("sachivName.contains=" + UPDATED_SACHIV_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySachivNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where sachivName does not contain DEFAULT_SACHIV_NAME
        defaultCourtCaseSettingShouldNotBeFound("sachivName.doesNotContain=" + DEFAULT_SACHIV_NAME);

        // Get all the courtCaseSettingList where sachivName does not contain UPDATED_SACHIV_NAME
        defaultCourtCaseSettingShouldBeFound("sachivName.doesNotContain=" + UPDATED_SACHIV_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchakName equals to DEFAULT_SUCHAK_NAME
        defaultCourtCaseSettingShouldBeFound("suchakName.equals=" + DEFAULT_SUCHAK_NAME);

        // Get all the courtCaseSettingList where suchakName equals to UPDATED_SUCHAK_NAME
        defaultCourtCaseSettingShouldNotBeFound("suchakName.equals=" + UPDATED_SUCHAK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchakName in DEFAULT_SUCHAK_NAME or UPDATED_SUCHAK_NAME
        defaultCourtCaseSettingShouldBeFound("suchakName.in=" + DEFAULT_SUCHAK_NAME + "," + UPDATED_SUCHAK_NAME);

        // Get all the courtCaseSettingList where suchakName equals to UPDATED_SUCHAK_NAME
        defaultCourtCaseSettingShouldNotBeFound("suchakName.in=" + UPDATED_SUCHAK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchakName is not null
        defaultCourtCaseSettingShouldBeFound("suchakName.specified=true");

        // Get all the courtCaseSettingList where suchakName is null
        defaultCourtCaseSettingShouldNotBeFound("suchakName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchakName contains DEFAULT_SUCHAK_NAME
        defaultCourtCaseSettingShouldBeFound("suchakName.contains=" + DEFAULT_SUCHAK_NAME);

        // Get all the courtCaseSettingList where suchakName contains UPDATED_SUCHAK_NAME
        defaultCourtCaseSettingShouldNotBeFound("suchakName.contains=" + UPDATED_SUCHAK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySuchakNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where suchakName does not contain DEFAULT_SUCHAK_NAME
        defaultCourtCaseSettingShouldNotBeFound("suchakName.doesNotContain=" + DEFAULT_SUCHAK_NAME);

        // Get all the courtCaseSettingList where suchakName does not contain UPDATED_SUCHAK_NAME
        defaultCourtCaseSettingShouldBeFound("suchakName.doesNotContain=" + UPDATED_SUCHAK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAnumodakNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where anumodakName equals to DEFAULT_ANUMODAK_NAME
        defaultCourtCaseSettingShouldBeFound("anumodakName.equals=" + DEFAULT_ANUMODAK_NAME);

        // Get all the courtCaseSettingList where anumodakName equals to UPDATED_ANUMODAK_NAME
        defaultCourtCaseSettingShouldNotBeFound("anumodakName.equals=" + UPDATED_ANUMODAK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAnumodakNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where anumodakName in DEFAULT_ANUMODAK_NAME or UPDATED_ANUMODAK_NAME
        defaultCourtCaseSettingShouldBeFound("anumodakName.in=" + DEFAULT_ANUMODAK_NAME + "," + UPDATED_ANUMODAK_NAME);

        // Get all the courtCaseSettingList where anumodakName equals to UPDATED_ANUMODAK_NAME
        defaultCourtCaseSettingShouldNotBeFound("anumodakName.in=" + UPDATED_ANUMODAK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAnumodakNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where anumodakName is not null
        defaultCourtCaseSettingShouldBeFound("anumodakName.specified=true");

        // Get all the courtCaseSettingList where anumodakName is null
        defaultCourtCaseSettingShouldNotBeFound("anumodakName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAnumodakNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where anumodakName contains DEFAULT_ANUMODAK_NAME
        defaultCourtCaseSettingShouldBeFound("anumodakName.contains=" + DEFAULT_ANUMODAK_NAME);

        // Get all the courtCaseSettingList where anumodakName contains UPDATED_ANUMODAK_NAME
        defaultCourtCaseSettingShouldNotBeFound("anumodakName.contains=" + UPDATED_ANUMODAK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByAnumodakNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where anumodakName does not contain DEFAULT_ANUMODAK_NAME
        defaultCourtCaseSettingShouldNotBeFound("anumodakName.doesNotContain=" + DEFAULT_ANUMODAK_NAME);

        // Get all the courtCaseSettingList where anumodakName does not contain UPDATED_ANUMODAK_NAME
        defaultCourtCaseSettingShouldBeFound("anumodakName.doesNotContain=" + UPDATED_ANUMODAK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliExpenseIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliExpense equals to DEFAULT_VASULI_EXPENSE
        defaultCourtCaseSettingShouldBeFound("vasuliExpense.equals=" + DEFAULT_VASULI_EXPENSE);

        // Get all the courtCaseSettingList where vasuliExpense equals to UPDATED_VASULI_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("vasuliExpense.equals=" + UPDATED_VASULI_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliExpenseIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliExpense in DEFAULT_VASULI_EXPENSE or UPDATED_VASULI_EXPENSE
        defaultCourtCaseSettingShouldBeFound("vasuliExpense.in=" + DEFAULT_VASULI_EXPENSE + "," + UPDATED_VASULI_EXPENSE);

        // Get all the courtCaseSettingList where vasuliExpense equals to UPDATED_VASULI_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("vasuliExpense.in=" + UPDATED_VASULI_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliExpenseIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliExpense is not null
        defaultCourtCaseSettingShouldBeFound("vasuliExpense.specified=true");

        // Get all the courtCaseSettingList where vasuliExpense is null
        defaultCourtCaseSettingShouldNotBeFound("vasuliExpense.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliExpenseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliExpense is greater than or equal to DEFAULT_VASULI_EXPENSE
        defaultCourtCaseSettingShouldBeFound("vasuliExpense.greaterThanOrEqual=" + DEFAULT_VASULI_EXPENSE);

        // Get all the courtCaseSettingList where vasuliExpense is greater than or equal to UPDATED_VASULI_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("vasuliExpense.greaterThanOrEqual=" + UPDATED_VASULI_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliExpenseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliExpense is less than or equal to DEFAULT_VASULI_EXPENSE
        defaultCourtCaseSettingShouldBeFound("vasuliExpense.lessThanOrEqual=" + DEFAULT_VASULI_EXPENSE);

        // Get all the courtCaseSettingList where vasuliExpense is less than or equal to SMALLER_VASULI_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("vasuliExpense.lessThanOrEqual=" + SMALLER_VASULI_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliExpenseIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliExpense is less than DEFAULT_VASULI_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("vasuliExpense.lessThan=" + DEFAULT_VASULI_EXPENSE);

        // Get all the courtCaseSettingList where vasuliExpense is less than UPDATED_VASULI_EXPENSE
        defaultCourtCaseSettingShouldBeFound("vasuliExpense.lessThan=" + UPDATED_VASULI_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByVasuliExpenseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where vasuliExpense is greater than DEFAULT_VASULI_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("vasuliExpense.greaterThan=" + DEFAULT_VASULI_EXPENSE);

        // Get all the courtCaseSettingList where vasuliExpense is greater than SMALLER_VASULI_EXPENSE
        defaultCourtCaseSettingShouldBeFound("vasuliExpense.greaterThan=" + SMALLER_VASULI_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOtherExpenseIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where otherExpense equals to DEFAULT_OTHER_EXPENSE
        defaultCourtCaseSettingShouldBeFound("otherExpense.equals=" + DEFAULT_OTHER_EXPENSE);

        // Get all the courtCaseSettingList where otherExpense equals to UPDATED_OTHER_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("otherExpense.equals=" + UPDATED_OTHER_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOtherExpenseIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where otherExpense in DEFAULT_OTHER_EXPENSE or UPDATED_OTHER_EXPENSE
        defaultCourtCaseSettingShouldBeFound("otherExpense.in=" + DEFAULT_OTHER_EXPENSE + "," + UPDATED_OTHER_EXPENSE);

        // Get all the courtCaseSettingList where otherExpense equals to UPDATED_OTHER_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("otherExpense.in=" + UPDATED_OTHER_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOtherExpenseIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where otherExpense is not null
        defaultCourtCaseSettingShouldBeFound("otherExpense.specified=true");

        // Get all the courtCaseSettingList where otherExpense is null
        defaultCourtCaseSettingShouldNotBeFound("otherExpense.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOtherExpenseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where otherExpense is greater than or equal to DEFAULT_OTHER_EXPENSE
        defaultCourtCaseSettingShouldBeFound("otherExpense.greaterThanOrEqual=" + DEFAULT_OTHER_EXPENSE);

        // Get all the courtCaseSettingList where otherExpense is greater than or equal to UPDATED_OTHER_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("otherExpense.greaterThanOrEqual=" + UPDATED_OTHER_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOtherExpenseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where otherExpense is less than or equal to DEFAULT_OTHER_EXPENSE
        defaultCourtCaseSettingShouldBeFound("otherExpense.lessThanOrEqual=" + DEFAULT_OTHER_EXPENSE);

        // Get all the courtCaseSettingList where otherExpense is less than or equal to SMALLER_OTHER_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("otherExpense.lessThanOrEqual=" + SMALLER_OTHER_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOtherExpenseIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where otherExpense is less than DEFAULT_OTHER_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("otherExpense.lessThan=" + DEFAULT_OTHER_EXPENSE);

        // Get all the courtCaseSettingList where otherExpense is less than UPDATED_OTHER_EXPENSE
        defaultCourtCaseSettingShouldBeFound("otherExpense.lessThan=" + UPDATED_OTHER_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByOtherExpenseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where otherExpense is greater than DEFAULT_OTHER_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("otherExpense.greaterThan=" + DEFAULT_OTHER_EXPENSE);

        // Get all the courtCaseSettingList where otherExpense is greater than SMALLER_OTHER_EXPENSE
        defaultCourtCaseSettingShouldBeFound("otherExpense.greaterThan=" + SMALLER_OTHER_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeExpenseIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeExpense equals to DEFAULT_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldBeFound("noticeExpense.equals=" + DEFAULT_NOTICE_EXPENSE);

        // Get all the courtCaseSettingList where noticeExpense equals to UPDATED_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("noticeExpense.equals=" + UPDATED_NOTICE_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeExpenseIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeExpense in DEFAULT_NOTICE_EXPENSE or UPDATED_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldBeFound("noticeExpense.in=" + DEFAULT_NOTICE_EXPENSE + "," + UPDATED_NOTICE_EXPENSE);

        // Get all the courtCaseSettingList where noticeExpense equals to UPDATED_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("noticeExpense.in=" + UPDATED_NOTICE_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeExpenseIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeExpense is not null
        defaultCourtCaseSettingShouldBeFound("noticeExpense.specified=true");

        // Get all the courtCaseSettingList where noticeExpense is null
        defaultCourtCaseSettingShouldNotBeFound("noticeExpense.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeExpenseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeExpense is greater than or equal to DEFAULT_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldBeFound("noticeExpense.greaterThanOrEqual=" + DEFAULT_NOTICE_EXPENSE);

        // Get all the courtCaseSettingList where noticeExpense is greater than or equal to UPDATED_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("noticeExpense.greaterThanOrEqual=" + UPDATED_NOTICE_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeExpenseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeExpense is less than or equal to DEFAULT_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldBeFound("noticeExpense.lessThanOrEqual=" + DEFAULT_NOTICE_EXPENSE);

        // Get all the courtCaseSettingList where noticeExpense is less than or equal to SMALLER_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("noticeExpense.lessThanOrEqual=" + SMALLER_NOTICE_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeExpenseIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeExpense is less than DEFAULT_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("noticeExpense.lessThan=" + DEFAULT_NOTICE_EXPENSE);

        // Get all the courtCaseSettingList where noticeExpense is less than UPDATED_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldBeFound("noticeExpense.lessThan=" + UPDATED_NOTICE_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByNoticeExpenseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where noticeExpense is greater than DEFAULT_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldNotBeFound("noticeExpense.greaterThan=" + DEFAULT_NOTICE_EXPENSE);

        // Get all the courtCaseSettingList where noticeExpense is greater than SMALLER_NOTICE_EXPENSE
        defaultCourtCaseSettingShouldBeFound("noticeExpense.greaterThan=" + SMALLER_NOTICE_EXPENSE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingNo equals to DEFAULT_MEETING_NO
        defaultCourtCaseSettingShouldBeFound("meetingNo.equals=" + DEFAULT_MEETING_NO);

        // Get all the courtCaseSettingList where meetingNo equals to UPDATED_MEETING_NO
        defaultCourtCaseSettingShouldNotBeFound("meetingNo.equals=" + UPDATED_MEETING_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingNo in DEFAULT_MEETING_NO or UPDATED_MEETING_NO
        defaultCourtCaseSettingShouldBeFound("meetingNo.in=" + DEFAULT_MEETING_NO + "," + UPDATED_MEETING_NO);

        // Get all the courtCaseSettingList where meetingNo equals to UPDATED_MEETING_NO
        defaultCourtCaseSettingShouldNotBeFound("meetingNo.in=" + UPDATED_MEETING_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingNo is not null
        defaultCourtCaseSettingShouldBeFound("meetingNo.specified=true");

        // Get all the courtCaseSettingList where meetingNo is null
        defaultCourtCaseSettingShouldNotBeFound("meetingNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingNo is greater than or equal to DEFAULT_MEETING_NO
        defaultCourtCaseSettingShouldBeFound("meetingNo.greaterThanOrEqual=" + DEFAULT_MEETING_NO);

        // Get all the courtCaseSettingList where meetingNo is greater than or equal to UPDATED_MEETING_NO
        defaultCourtCaseSettingShouldNotBeFound("meetingNo.greaterThanOrEqual=" + UPDATED_MEETING_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingNo is less than or equal to DEFAULT_MEETING_NO
        defaultCourtCaseSettingShouldBeFound("meetingNo.lessThanOrEqual=" + DEFAULT_MEETING_NO);

        // Get all the courtCaseSettingList where meetingNo is less than or equal to SMALLER_MEETING_NO
        defaultCourtCaseSettingShouldNotBeFound("meetingNo.lessThanOrEqual=" + SMALLER_MEETING_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingNoIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingNo is less than DEFAULT_MEETING_NO
        defaultCourtCaseSettingShouldNotBeFound("meetingNo.lessThan=" + DEFAULT_MEETING_NO);

        // Get all the courtCaseSettingList where meetingNo is less than UPDATED_MEETING_NO
        defaultCourtCaseSettingShouldBeFound("meetingNo.lessThan=" + UPDATED_MEETING_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingNo is greater than DEFAULT_MEETING_NO
        defaultCourtCaseSettingShouldNotBeFound("meetingNo.greaterThan=" + DEFAULT_MEETING_NO);

        // Get all the courtCaseSettingList where meetingNo is greater than SMALLER_MEETING_NO
        defaultCourtCaseSettingShouldBeFound("meetingNo.greaterThan=" + SMALLER_MEETING_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDate equals to DEFAULT_MEETING_DATE
        defaultCourtCaseSettingShouldBeFound("meetingDate.equals=" + DEFAULT_MEETING_DATE);

        // Get all the courtCaseSettingList where meetingDate equals to UPDATED_MEETING_DATE
        defaultCourtCaseSettingShouldNotBeFound("meetingDate.equals=" + UPDATED_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDate in DEFAULT_MEETING_DATE or UPDATED_MEETING_DATE
        defaultCourtCaseSettingShouldBeFound("meetingDate.in=" + DEFAULT_MEETING_DATE + "," + UPDATED_MEETING_DATE);

        // Get all the courtCaseSettingList where meetingDate equals to UPDATED_MEETING_DATE
        defaultCourtCaseSettingShouldNotBeFound("meetingDate.in=" + UPDATED_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDate is not null
        defaultCourtCaseSettingShouldBeFound("meetingDate.specified=true");

        // Get all the courtCaseSettingList where meetingDate is null
        defaultCourtCaseSettingShouldNotBeFound("meetingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDate is greater than or equal to DEFAULT_MEETING_DATE
        defaultCourtCaseSettingShouldBeFound("meetingDate.greaterThanOrEqual=" + DEFAULT_MEETING_DATE);

        // Get all the courtCaseSettingList where meetingDate is greater than or equal to UPDATED_MEETING_DATE
        defaultCourtCaseSettingShouldNotBeFound("meetingDate.greaterThanOrEqual=" + UPDATED_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDate is less than or equal to DEFAULT_MEETING_DATE
        defaultCourtCaseSettingShouldBeFound("meetingDate.lessThanOrEqual=" + DEFAULT_MEETING_DATE);

        // Get all the courtCaseSettingList where meetingDate is less than or equal to SMALLER_MEETING_DATE
        defaultCourtCaseSettingShouldNotBeFound("meetingDate.lessThanOrEqual=" + SMALLER_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDate is less than DEFAULT_MEETING_DATE
        defaultCourtCaseSettingShouldNotBeFound("meetingDate.lessThan=" + DEFAULT_MEETING_DATE);

        // Get all the courtCaseSettingList where meetingDate is less than UPDATED_MEETING_DATE
        defaultCourtCaseSettingShouldBeFound("meetingDate.lessThan=" + UPDATED_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDate is greater than DEFAULT_MEETING_DATE
        defaultCourtCaseSettingShouldNotBeFound("meetingDate.greaterThan=" + DEFAULT_MEETING_DATE);

        // Get all the courtCaseSettingList where meetingDate is greater than SMALLER_MEETING_DATE
        defaultCourtCaseSettingShouldBeFound("meetingDate.greaterThan=" + SMALLER_MEETING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySubjectNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where subjectNo equals to DEFAULT_SUBJECT_NO
        defaultCourtCaseSettingShouldBeFound("subjectNo.equals=" + DEFAULT_SUBJECT_NO);

        // Get all the courtCaseSettingList where subjectNo equals to UPDATED_SUBJECT_NO
        defaultCourtCaseSettingShouldNotBeFound("subjectNo.equals=" + UPDATED_SUBJECT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySubjectNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where subjectNo in DEFAULT_SUBJECT_NO or UPDATED_SUBJECT_NO
        defaultCourtCaseSettingShouldBeFound("subjectNo.in=" + DEFAULT_SUBJECT_NO + "," + UPDATED_SUBJECT_NO);

        // Get all the courtCaseSettingList where subjectNo equals to UPDATED_SUBJECT_NO
        defaultCourtCaseSettingShouldNotBeFound("subjectNo.in=" + UPDATED_SUBJECT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySubjectNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where subjectNo is not null
        defaultCourtCaseSettingShouldBeFound("subjectNo.specified=true");

        // Get all the courtCaseSettingList where subjectNo is null
        defaultCourtCaseSettingShouldNotBeFound("subjectNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySubjectNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where subjectNo is greater than or equal to DEFAULT_SUBJECT_NO
        defaultCourtCaseSettingShouldBeFound("subjectNo.greaterThanOrEqual=" + DEFAULT_SUBJECT_NO);

        // Get all the courtCaseSettingList where subjectNo is greater than or equal to UPDATED_SUBJECT_NO
        defaultCourtCaseSettingShouldNotBeFound("subjectNo.greaterThanOrEqual=" + UPDATED_SUBJECT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySubjectNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where subjectNo is less than or equal to DEFAULT_SUBJECT_NO
        defaultCourtCaseSettingShouldBeFound("subjectNo.lessThanOrEqual=" + DEFAULT_SUBJECT_NO);

        // Get all the courtCaseSettingList where subjectNo is less than or equal to SMALLER_SUBJECT_NO
        defaultCourtCaseSettingShouldNotBeFound("subjectNo.lessThanOrEqual=" + SMALLER_SUBJECT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySubjectNoIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where subjectNo is less than DEFAULT_SUBJECT_NO
        defaultCourtCaseSettingShouldNotBeFound("subjectNo.lessThan=" + DEFAULT_SUBJECT_NO);

        // Get all the courtCaseSettingList where subjectNo is less than UPDATED_SUBJECT_NO
        defaultCourtCaseSettingShouldBeFound("subjectNo.lessThan=" + UPDATED_SUBJECT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsBySubjectNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where subjectNo is greater than DEFAULT_SUBJECT_NO
        defaultCourtCaseSettingShouldNotBeFound("subjectNo.greaterThan=" + DEFAULT_SUBJECT_NO);

        // Get all the courtCaseSettingList where subjectNo is greater than SMALLER_SUBJECT_NO
        defaultCourtCaseSettingShouldBeFound("subjectNo.greaterThan=" + SMALLER_SUBJECT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDayIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDay equals to DEFAULT_MEETING_DAY
        defaultCourtCaseSettingShouldBeFound("meetingDay.equals=" + DEFAULT_MEETING_DAY);

        // Get all the courtCaseSettingList where meetingDay equals to UPDATED_MEETING_DAY
        defaultCourtCaseSettingShouldNotBeFound("meetingDay.equals=" + UPDATED_MEETING_DAY);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDayIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDay in DEFAULT_MEETING_DAY or UPDATED_MEETING_DAY
        defaultCourtCaseSettingShouldBeFound("meetingDay.in=" + DEFAULT_MEETING_DAY + "," + UPDATED_MEETING_DAY);

        // Get all the courtCaseSettingList where meetingDay equals to UPDATED_MEETING_DAY
        defaultCourtCaseSettingShouldNotBeFound("meetingDay.in=" + UPDATED_MEETING_DAY);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDay is not null
        defaultCourtCaseSettingShouldBeFound("meetingDay.specified=true");

        // Get all the courtCaseSettingList where meetingDay is null
        defaultCourtCaseSettingShouldNotBeFound("meetingDay.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDayContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDay contains DEFAULT_MEETING_DAY
        defaultCourtCaseSettingShouldBeFound("meetingDay.contains=" + DEFAULT_MEETING_DAY);

        // Get all the courtCaseSettingList where meetingDay contains UPDATED_MEETING_DAY
        defaultCourtCaseSettingShouldNotBeFound("meetingDay.contains=" + UPDATED_MEETING_DAY);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingDayNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingDay does not contain DEFAULT_MEETING_DAY
        defaultCourtCaseSettingShouldNotBeFound("meetingDay.doesNotContain=" + DEFAULT_MEETING_DAY);

        // Get all the courtCaseSettingList where meetingDay does not contain UPDATED_MEETING_DAY
        defaultCourtCaseSettingShouldBeFound("meetingDay.doesNotContain=" + UPDATED_MEETING_DAY);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingTime equals to DEFAULT_MEETING_TIME
        defaultCourtCaseSettingShouldBeFound("meetingTime.equals=" + DEFAULT_MEETING_TIME);

        // Get all the courtCaseSettingList where meetingTime equals to UPDATED_MEETING_TIME
        defaultCourtCaseSettingShouldNotBeFound("meetingTime.equals=" + UPDATED_MEETING_TIME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingTimeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingTime in DEFAULT_MEETING_TIME or UPDATED_MEETING_TIME
        defaultCourtCaseSettingShouldBeFound("meetingTime.in=" + DEFAULT_MEETING_TIME + "," + UPDATED_MEETING_TIME);

        // Get all the courtCaseSettingList where meetingTime equals to UPDATED_MEETING_TIME
        defaultCourtCaseSettingShouldNotBeFound("meetingTime.in=" + UPDATED_MEETING_TIME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingTime is not null
        defaultCourtCaseSettingShouldBeFound("meetingTime.specified=true");

        // Get all the courtCaseSettingList where meetingTime is null
        defaultCourtCaseSettingShouldNotBeFound("meetingTime.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingTimeContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingTime contains DEFAULT_MEETING_TIME
        defaultCourtCaseSettingShouldBeFound("meetingTime.contains=" + DEFAULT_MEETING_TIME);

        // Get all the courtCaseSettingList where meetingTime contains UPDATED_MEETING_TIME
        defaultCourtCaseSettingShouldNotBeFound("meetingTime.contains=" + UPDATED_MEETING_TIME);
    }

    @Test
    @Transactional
    void getAllCourtCaseSettingsByMeetingTimeNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseSettingRepository.saveAndFlush(courtCaseSetting);

        // Get all the courtCaseSettingList where meetingTime does not contain DEFAULT_MEETING_TIME
        defaultCourtCaseSettingShouldNotBeFound("meetingTime.doesNotContain=" + DEFAULT_MEETING_TIME);

        // Get all the courtCaseSettingList where meetingTime does not contain UPDATED_MEETING_TIME
        defaultCourtCaseSettingShouldBeFound("meetingTime.doesNotContain=" + UPDATED_MEETING_TIME);
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
            .andExpect(jsonPath("$.[*].vasuliAdhikariName").value(hasItem(DEFAULT_VASULI_ADHIKARI_NAME)))
            .andExpect(jsonPath("$.[*].arOfficeName").value(hasItem(DEFAULT_AR_OFFICE_NAME)))
            .andExpect(jsonPath("$.[*].chairmanName").value(hasItem(DEFAULT_CHAIRMAN_NAME)))
            .andExpect(jsonPath("$.[*].sachivName").value(hasItem(DEFAULT_SACHIV_NAME)))
            .andExpect(jsonPath("$.[*].suchakName").value(hasItem(DEFAULT_SUCHAK_NAME)))
            .andExpect(jsonPath("$.[*].anumodakName").value(hasItem(DEFAULT_ANUMODAK_NAME)))
            .andExpect(jsonPath("$.[*].vasuliExpense").value(hasItem(DEFAULT_VASULI_EXPENSE.doubleValue())))
            .andExpect(jsonPath("$.[*].otherExpense").value(hasItem(DEFAULT_OTHER_EXPENSE.doubleValue())))
            .andExpect(jsonPath("$.[*].noticeExpense").value(hasItem(DEFAULT_NOTICE_EXPENSE.doubleValue())))
            .andExpect(jsonPath("$.[*].meetingNo").value(hasItem(DEFAULT_MEETING_NO.intValue())))
            .andExpect(jsonPath("$.[*].meetingDate").value(hasItem(DEFAULT_MEETING_DATE.toString())))
            .andExpect(jsonPath("$.[*].subjectNo").value(hasItem(DEFAULT_SUBJECT_NO.intValue())))
            .andExpect(jsonPath("$.[*].meetingDay").value(hasItem(DEFAULT_MEETING_DAY)))
            .andExpect(jsonPath("$.[*].meetingTime").value(hasItem(DEFAULT_MEETING_TIME)));

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
            .vasuliAdhikariName(UPDATED_VASULI_ADHIKARI_NAME)
            .arOfficeName(UPDATED_AR_OFFICE_NAME)
            .chairmanName(UPDATED_CHAIRMAN_NAME)
            .sachivName(UPDATED_SACHIV_NAME)
            .suchakName(UPDATED_SUCHAK_NAME)
            .anumodakName(UPDATED_ANUMODAK_NAME)
            .vasuliExpense(UPDATED_VASULI_EXPENSE)
            .otherExpense(UPDATED_OTHER_EXPENSE)
            .noticeExpense(UPDATED_NOTICE_EXPENSE)
            .meetingNo(UPDATED_MEETING_NO)
            .meetingDate(UPDATED_MEETING_DATE)
            .subjectNo(UPDATED_SUBJECT_NO)
            .meetingDay(UPDATED_MEETING_DAY)
            .meetingTime(UPDATED_MEETING_TIME);

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
        assertThat(testCourtCaseSetting.getVasuliAdhikariName()).isEqualTo(UPDATED_VASULI_ADHIKARI_NAME);
        assertThat(testCourtCaseSetting.getArOfficeName()).isEqualTo(UPDATED_AR_OFFICE_NAME);
        assertThat(testCourtCaseSetting.getChairmanName()).isEqualTo(UPDATED_CHAIRMAN_NAME);
        assertThat(testCourtCaseSetting.getSachivName()).isEqualTo(UPDATED_SACHIV_NAME);
        assertThat(testCourtCaseSetting.getSuchakName()).isEqualTo(UPDATED_SUCHAK_NAME);
        assertThat(testCourtCaseSetting.getAnumodakName()).isEqualTo(UPDATED_ANUMODAK_NAME);
        assertThat(testCourtCaseSetting.getVasuliExpense()).isEqualTo(UPDATED_VASULI_EXPENSE);
        assertThat(testCourtCaseSetting.getOtherExpense()).isEqualTo(UPDATED_OTHER_EXPENSE);
        assertThat(testCourtCaseSetting.getNoticeExpense()).isEqualTo(UPDATED_NOTICE_EXPENSE);
        assertThat(testCourtCaseSetting.getMeetingNo()).isEqualTo(UPDATED_MEETING_NO);
        assertThat(testCourtCaseSetting.getMeetingDate()).isEqualTo(UPDATED_MEETING_DATE);
        assertThat(testCourtCaseSetting.getSubjectNo()).isEqualTo(UPDATED_SUBJECT_NO);
        assertThat(testCourtCaseSetting.getMeetingDay()).isEqualTo(UPDATED_MEETING_DAY);
        assertThat(testCourtCaseSetting.getMeetingTime()).isEqualTo(UPDATED_MEETING_TIME);
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
            .arOfficeName(UPDATED_AR_OFFICE_NAME)
            .chairmanName(UPDATED_CHAIRMAN_NAME)
            .sachivName(UPDATED_SACHIV_NAME)
            .suchakName(UPDATED_SUCHAK_NAME)
            .anumodakName(UPDATED_ANUMODAK_NAME)
            .meetingNo(UPDATED_MEETING_NO)
            .subjectNo(UPDATED_SUBJECT_NO)
            .meetingTime(UPDATED_MEETING_TIME);

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
        assertThat(testCourtCaseSetting.getVasuliAdhikariName()).isEqualTo(DEFAULT_VASULI_ADHIKARI_NAME);
        assertThat(testCourtCaseSetting.getArOfficeName()).isEqualTo(UPDATED_AR_OFFICE_NAME);
        assertThat(testCourtCaseSetting.getChairmanName()).isEqualTo(UPDATED_CHAIRMAN_NAME);
        assertThat(testCourtCaseSetting.getSachivName()).isEqualTo(UPDATED_SACHIV_NAME);
        assertThat(testCourtCaseSetting.getSuchakName()).isEqualTo(UPDATED_SUCHAK_NAME);
        assertThat(testCourtCaseSetting.getAnumodakName()).isEqualTo(UPDATED_ANUMODAK_NAME);
        assertThat(testCourtCaseSetting.getVasuliExpense()).isEqualTo(DEFAULT_VASULI_EXPENSE);
        assertThat(testCourtCaseSetting.getOtherExpense()).isEqualTo(DEFAULT_OTHER_EXPENSE);
        assertThat(testCourtCaseSetting.getNoticeExpense()).isEqualTo(DEFAULT_NOTICE_EXPENSE);
        assertThat(testCourtCaseSetting.getMeetingNo()).isEqualTo(UPDATED_MEETING_NO);
        assertThat(testCourtCaseSetting.getMeetingDate()).isEqualTo(DEFAULT_MEETING_DATE);
        assertThat(testCourtCaseSetting.getSubjectNo()).isEqualTo(UPDATED_SUBJECT_NO);
        assertThat(testCourtCaseSetting.getMeetingDay()).isEqualTo(DEFAULT_MEETING_DAY);
        assertThat(testCourtCaseSetting.getMeetingTime()).isEqualTo(UPDATED_MEETING_TIME);
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
            .vasuliAdhikariName(UPDATED_VASULI_ADHIKARI_NAME)
            .arOfficeName(UPDATED_AR_OFFICE_NAME)
            .chairmanName(UPDATED_CHAIRMAN_NAME)
            .sachivName(UPDATED_SACHIV_NAME)
            .suchakName(UPDATED_SUCHAK_NAME)
            .anumodakName(UPDATED_ANUMODAK_NAME)
            .vasuliExpense(UPDATED_VASULI_EXPENSE)
            .otherExpense(UPDATED_OTHER_EXPENSE)
            .noticeExpense(UPDATED_NOTICE_EXPENSE)
            .meetingNo(UPDATED_MEETING_NO)
            .meetingDate(UPDATED_MEETING_DATE)
            .subjectNo(UPDATED_SUBJECT_NO)
            .meetingDay(UPDATED_MEETING_DAY)
            .meetingTime(UPDATED_MEETING_TIME);

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
        assertThat(testCourtCaseSetting.getVasuliAdhikariName()).isEqualTo(UPDATED_VASULI_ADHIKARI_NAME);
        assertThat(testCourtCaseSetting.getArOfficeName()).isEqualTo(UPDATED_AR_OFFICE_NAME);
        assertThat(testCourtCaseSetting.getChairmanName()).isEqualTo(UPDATED_CHAIRMAN_NAME);
        assertThat(testCourtCaseSetting.getSachivName()).isEqualTo(UPDATED_SACHIV_NAME);
        assertThat(testCourtCaseSetting.getSuchakName()).isEqualTo(UPDATED_SUCHAK_NAME);
        assertThat(testCourtCaseSetting.getAnumodakName()).isEqualTo(UPDATED_ANUMODAK_NAME);
        assertThat(testCourtCaseSetting.getVasuliExpense()).isEqualTo(UPDATED_VASULI_EXPENSE);
        assertThat(testCourtCaseSetting.getOtherExpense()).isEqualTo(UPDATED_OTHER_EXPENSE);
        assertThat(testCourtCaseSetting.getNoticeExpense()).isEqualTo(UPDATED_NOTICE_EXPENSE);
        assertThat(testCourtCaseSetting.getMeetingNo()).isEqualTo(UPDATED_MEETING_NO);
        assertThat(testCourtCaseSetting.getMeetingDate()).isEqualTo(UPDATED_MEETING_DATE);
        assertThat(testCourtCaseSetting.getSubjectNo()).isEqualTo(UPDATED_SUBJECT_NO);
        assertThat(testCourtCaseSetting.getMeetingDay()).isEqualTo(UPDATED_MEETING_DAY);
        assertThat(testCourtCaseSetting.getMeetingTime()).isEqualTo(UPDATED_MEETING_TIME);
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
