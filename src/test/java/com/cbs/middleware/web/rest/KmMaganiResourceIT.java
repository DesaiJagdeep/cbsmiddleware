package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KmMagani;
import com.cbs.middleware.repository.KmMaganiRepository;
import com.cbs.middleware.service.criteria.KmMaganiCriteria;
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
 * Integration tests for the {@link KmMaganiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KmMaganiResourceIT {

    private static final String DEFAULT_KM_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_KM_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_SHARE = 1D;
    private static final Double UPDATED_SHARE = 2D;
    private static final Double SMALLER_SHARE = 1D - 1D;

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_KM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MAGANI_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MAGANI_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/km-maganis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KmMaganiRepository kmMaganiRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKmMaganiMockMvc;

    private KmMagani kmMagani;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmMagani createEntity(EntityManager em) {
        KmMagani kmMagani = new KmMagani()
            .kmNumber(DEFAULT_KM_NUMBER)
            .memberNumber(DEFAULT_MEMBER_NUMBER)
            .memberName(DEFAULT_MEMBER_NAME)
            .pacsNumber(DEFAULT_PACS_NUMBER)
            .share(DEFAULT_SHARE)
            .financialYear(DEFAULT_FINANCIAL_YEAR)
            .kmDate(DEFAULT_KM_DATE)
            .maganiDate(DEFAULT_MAGANI_DATE);
        return kmMagani;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmMagani createUpdatedEntity(EntityManager em) {
        KmMagani kmMagani = new KmMagani()
            .kmNumber(UPDATED_KM_NUMBER)
            .memberNumber(UPDATED_MEMBER_NUMBER)
            .memberName(UPDATED_MEMBER_NAME)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .share(UPDATED_SHARE)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .kmDate(UPDATED_KM_DATE)
            .maganiDate(UPDATED_MAGANI_DATE);
        return kmMagani;
    }

    @BeforeEach
    public void initTest() {
        kmMagani = createEntity(em);
    }

    @Test
    @Transactional
    void createKmMagani() throws Exception {
        int databaseSizeBeforeCreate = kmMaganiRepository.findAll().size();
        // Create the KmMagani
        restKmMaganiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMagani)))
            .andExpect(status().isCreated());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeCreate + 1);
        KmMagani testKmMagani = kmMaganiList.get(kmMaganiList.size() - 1);
        assertThat(testKmMagani.getKmNumber()).isEqualTo(DEFAULT_KM_NUMBER);
        assertThat(testKmMagani.getMemberNumber()).isEqualTo(DEFAULT_MEMBER_NUMBER);
        assertThat(testKmMagani.getMemberName()).isEqualTo(DEFAULT_MEMBER_NAME);
        assertThat(testKmMagani.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKmMagani.getShare()).isEqualTo(DEFAULT_SHARE);
        assertThat(testKmMagani.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testKmMagani.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
        assertThat(testKmMagani.getMaganiDate()).isEqualTo(DEFAULT_MAGANI_DATE);
    }

    @Test
    @Transactional
    void createKmMaganiWithExistingId() throws Exception {
        // Create the KmMagani with an existing ID
        kmMagani.setId(1L);

        int databaseSizeBeforeCreate = kmMaganiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmMaganiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMagani)))
            .andExpect(status().isBadRequest());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKmMaganis() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList
        restKmMaganiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMagani.getId().intValue())))
            .andExpect(jsonPath("$.[*].kmNumber").value(hasItem(DEFAULT_KM_NUMBER)))
            .andExpect(jsonPath("$.[*].memberNumber").value(hasItem(DEFAULT_MEMBER_NUMBER)))
            .andExpect(jsonPath("$.[*].memberName").value(hasItem(DEFAULT_MEMBER_NAME)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].share").value(hasItem(DEFAULT_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].maganiDate").value(hasItem(DEFAULT_MAGANI_DATE.toString())));
    }

    @Test
    @Transactional
    void getKmMagani() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get the kmMagani
        restKmMaganiMockMvc
            .perform(get(ENTITY_API_URL_ID, kmMagani.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kmMagani.getId().intValue()))
            .andExpect(jsonPath("$.kmNumber").value(DEFAULT_KM_NUMBER))
            .andExpect(jsonPath("$.memberNumber").value(DEFAULT_MEMBER_NUMBER))
            .andExpect(jsonPath("$.memberName").value(DEFAULT_MEMBER_NAME))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER))
            .andExpect(jsonPath("$.share").value(DEFAULT_SHARE.doubleValue()))
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.kmDate").value(DEFAULT_KM_DATE.toString()))
            .andExpect(jsonPath("$.maganiDate").value(DEFAULT_MAGANI_DATE.toString()));
    }

    @Test
    @Transactional
    void getKmMaganisByIdFiltering() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        Long id = kmMagani.getId();

        defaultKmMaganiShouldBeFound("id.equals=" + id);
        defaultKmMaganiShouldNotBeFound("id.notEquals=" + id);

        defaultKmMaganiShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKmMaganiShouldNotBeFound("id.greaterThan=" + id);

        defaultKmMaganiShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKmMaganiShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKmMaganisByKmNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where kmNumber equals to DEFAULT_KM_NUMBER
        defaultKmMaganiShouldBeFound("kmNumber.equals=" + DEFAULT_KM_NUMBER);

        // Get all the kmMaganiList where kmNumber equals to UPDATED_KM_NUMBER
        defaultKmMaganiShouldNotBeFound("kmNumber.equals=" + UPDATED_KM_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByKmNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where kmNumber in DEFAULT_KM_NUMBER or UPDATED_KM_NUMBER
        defaultKmMaganiShouldBeFound("kmNumber.in=" + DEFAULT_KM_NUMBER + "," + UPDATED_KM_NUMBER);

        // Get all the kmMaganiList where kmNumber equals to UPDATED_KM_NUMBER
        defaultKmMaganiShouldNotBeFound("kmNumber.in=" + UPDATED_KM_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByKmNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where kmNumber is not null
        defaultKmMaganiShouldBeFound("kmNumber.specified=true");

        // Get all the kmMaganiList where kmNumber is null
        defaultKmMaganiShouldNotBeFound("kmNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganisByKmNumberContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where kmNumber contains DEFAULT_KM_NUMBER
        defaultKmMaganiShouldBeFound("kmNumber.contains=" + DEFAULT_KM_NUMBER);

        // Get all the kmMaganiList where kmNumber contains UPDATED_KM_NUMBER
        defaultKmMaganiShouldNotBeFound("kmNumber.contains=" + UPDATED_KM_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByKmNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where kmNumber does not contain DEFAULT_KM_NUMBER
        defaultKmMaganiShouldNotBeFound("kmNumber.doesNotContain=" + DEFAULT_KM_NUMBER);

        // Get all the kmMaganiList where kmNumber does not contain UPDATED_KM_NUMBER
        defaultKmMaganiShouldBeFound("kmNumber.doesNotContain=" + UPDATED_KM_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberNumber equals to DEFAULT_MEMBER_NUMBER
        defaultKmMaganiShouldBeFound("memberNumber.equals=" + DEFAULT_MEMBER_NUMBER);

        // Get all the kmMaganiList where memberNumber equals to UPDATED_MEMBER_NUMBER
        defaultKmMaganiShouldNotBeFound("memberNumber.equals=" + UPDATED_MEMBER_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberNumber in DEFAULT_MEMBER_NUMBER or UPDATED_MEMBER_NUMBER
        defaultKmMaganiShouldBeFound("memberNumber.in=" + DEFAULT_MEMBER_NUMBER + "," + UPDATED_MEMBER_NUMBER);

        // Get all the kmMaganiList where memberNumber equals to UPDATED_MEMBER_NUMBER
        defaultKmMaganiShouldNotBeFound("memberNumber.in=" + UPDATED_MEMBER_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberNumber is not null
        defaultKmMaganiShouldBeFound("memberNumber.specified=true");

        // Get all the kmMaganiList where memberNumber is null
        defaultKmMaganiShouldNotBeFound("memberNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNumberContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberNumber contains DEFAULT_MEMBER_NUMBER
        defaultKmMaganiShouldBeFound("memberNumber.contains=" + DEFAULT_MEMBER_NUMBER);

        // Get all the kmMaganiList where memberNumber contains UPDATED_MEMBER_NUMBER
        defaultKmMaganiShouldNotBeFound("memberNumber.contains=" + UPDATED_MEMBER_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberNumber does not contain DEFAULT_MEMBER_NUMBER
        defaultKmMaganiShouldNotBeFound("memberNumber.doesNotContain=" + DEFAULT_MEMBER_NUMBER);

        // Get all the kmMaganiList where memberNumber does not contain UPDATED_MEMBER_NUMBER
        defaultKmMaganiShouldBeFound("memberNumber.doesNotContain=" + UPDATED_MEMBER_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberName equals to DEFAULT_MEMBER_NAME
        defaultKmMaganiShouldBeFound("memberName.equals=" + DEFAULT_MEMBER_NAME);

        // Get all the kmMaganiList where memberName equals to UPDATED_MEMBER_NAME
        defaultKmMaganiShouldNotBeFound("memberName.equals=" + UPDATED_MEMBER_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberName in DEFAULT_MEMBER_NAME or UPDATED_MEMBER_NAME
        defaultKmMaganiShouldBeFound("memberName.in=" + DEFAULT_MEMBER_NAME + "," + UPDATED_MEMBER_NAME);

        // Get all the kmMaganiList where memberName equals to UPDATED_MEMBER_NAME
        defaultKmMaganiShouldNotBeFound("memberName.in=" + UPDATED_MEMBER_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberName is not null
        defaultKmMaganiShouldBeFound("memberName.specified=true");

        // Get all the kmMaganiList where memberName is null
        defaultKmMaganiShouldNotBeFound("memberName.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNameContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberName contains DEFAULT_MEMBER_NAME
        defaultKmMaganiShouldBeFound("memberName.contains=" + DEFAULT_MEMBER_NAME);

        // Get all the kmMaganiList where memberName contains UPDATED_MEMBER_NAME
        defaultKmMaganiShouldNotBeFound("memberName.contains=" + UPDATED_MEMBER_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMemberNameNotContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where memberName does not contain DEFAULT_MEMBER_NAME
        defaultKmMaganiShouldNotBeFound("memberName.doesNotContain=" + DEFAULT_MEMBER_NAME);

        // Get all the kmMaganiList where memberName does not contain UPDATED_MEMBER_NAME
        defaultKmMaganiShouldBeFound("memberName.doesNotContain=" + UPDATED_MEMBER_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganisByPacsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where pacsNumber equals to DEFAULT_PACS_NUMBER
        defaultKmMaganiShouldBeFound("pacsNumber.equals=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMaganiList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKmMaganiShouldNotBeFound("pacsNumber.equals=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByPacsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where pacsNumber in DEFAULT_PACS_NUMBER or UPDATED_PACS_NUMBER
        defaultKmMaganiShouldBeFound("pacsNumber.in=" + DEFAULT_PACS_NUMBER + "," + UPDATED_PACS_NUMBER);

        // Get all the kmMaganiList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKmMaganiShouldNotBeFound("pacsNumber.in=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByPacsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where pacsNumber is not null
        defaultKmMaganiShouldBeFound("pacsNumber.specified=true");

        // Get all the kmMaganiList where pacsNumber is null
        defaultKmMaganiShouldNotBeFound("pacsNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganisByPacsNumberContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where pacsNumber contains DEFAULT_PACS_NUMBER
        defaultKmMaganiShouldBeFound("pacsNumber.contains=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMaganiList where pacsNumber contains UPDATED_PACS_NUMBER
        defaultKmMaganiShouldNotBeFound("pacsNumber.contains=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByPacsNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where pacsNumber does not contain DEFAULT_PACS_NUMBER
        defaultKmMaganiShouldNotBeFound("pacsNumber.doesNotContain=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMaganiList where pacsNumber does not contain UPDATED_PACS_NUMBER
        defaultKmMaganiShouldBeFound("pacsNumber.doesNotContain=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMaganisByShareIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where share equals to DEFAULT_SHARE
        defaultKmMaganiShouldBeFound("share.equals=" + DEFAULT_SHARE);

        // Get all the kmMaganiList where share equals to UPDATED_SHARE
        defaultKmMaganiShouldNotBeFound("share.equals=" + UPDATED_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByShareIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where share in DEFAULT_SHARE or UPDATED_SHARE
        defaultKmMaganiShouldBeFound("share.in=" + DEFAULT_SHARE + "," + UPDATED_SHARE);

        // Get all the kmMaganiList where share equals to UPDATED_SHARE
        defaultKmMaganiShouldNotBeFound("share.in=" + UPDATED_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByShareIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where share is not null
        defaultKmMaganiShouldBeFound("share.specified=true");

        // Get all the kmMaganiList where share is null
        defaultKmMaganiShouldNotBeFound("share.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganisByShareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where share is greater than or equal to DEFAULT_SHARE
        defaultKmMaganiShouldBeFound("share.greaterThanOrEqual=" + DEFAULT_SHARE);

        // Get all the kmMaganiList where share is greater than or equal to UPDATED_SHARE
        defaultKmMaganiShouldNotBeFound("share.greaterThanOrEqual=" + UPDATED_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByShareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where share is less than or equal to DEFAULT_SHARE
        defaultKmMaganiShouldBeFound("share.lessThanOrEqual=" + DEFAULT_SHARE);

        // Get all the kmMaganiList where share is less than or equal to SMALLER_SHARE
        defaultKmMaganiShouldNotBeFound("share.lessThanOrEqual=" + SMALLER_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByShareIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where share is less than DEFAULT_SHARE
        defaultKmMaganiShouldNotBeFound("share.lessThan=" + DEFAULT_SHARE);

        // Get all the kmMaganiList where share is less than UPDATED_SHARE
        defaultKmMaganiShouldBeFound("share.lessThan=" + UPDATED_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByShareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where share is greater than DEFAULT_SHARE
        defaultKmMaganiShouldNotBeFound("share.greaterThan=" + DEFAULT_SHARE);

        // Get all the kmMaganiList where share is greater than SMALLER_SHARE
        defaultKmMaganiShouldBeFound("share.greaterThan=" + SMALLER_SHARE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultKmMaganiShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kmMaganiList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKmMaganiShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKmMaganisByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultKmMaganiShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the kmMaganiList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKmMaganiShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKmMaganisByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where financialYear is not null
        defaultKmMaganiShouldBeFound("financialYear.specified=true");

        // Get all the kmMaganiList where financialYear is null
        defaultKmMaganiShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganisByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultKmMaganiShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kmMaganiList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultKmMaganiShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKmMaganisByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultKmMaganiShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kmMaganiList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultKmMaganiShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKmMaganisByKmDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where kmDate equals to DEFAULT_KM_DATE
        defaultKmMaganiShouldBeFound("kmDate.equals=" + DEFAULT_KM_DATE);

        // Get all the kmMaganiList where kmDate equals to UPDATED_KM_DATE
        defaultKmMaganiShouldNotBeFound("kmDate.equals=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByKmDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where kmDate in DEFAULT_KM_DATE or UPDATED_KM_DATE
        defaultKmMaganiShouldBeFound("kmDate.in=" + DEFAULT_KM_DATE + "," + UPDATED_KM_DATE);

        // Get all the kmMaganiList where kmDate equals to UPDATED_KM_DATE
        defaultKmMaganiShouldNotBeFound("kmDate.in=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByKmDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where kmDate is not null
        defaultKmMaganiShouldBeFound("kmDate.specified=true");

        // Get all the kmMaganiList where kmDate is null
        defaultKmMaganiShouldNotBeFound("kmDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganisByMaganiDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where maganiDate equals to DEFAULT_MAGANI_DATE
        defaultKmMaganiShouldBeFound("maganiDate.equals=" + DEFAULT_MAGANI_DATE);

        // Get all the kmMaganiList where maganiDate equals to UPDATED_MAGANI_DATE
        defaultKmMaganiShouldNotBeFound("maganiDate.equals=" + UPDATED_MAGANI_DATE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMaganiDateIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where maganiDate in DEFAULT_MAGANI_DATE or UPDATED_MAGANI_DATE
        defaultKmMaganiShouldBeFound("maganiDate.in=" + DEFAULT_MAGANI_DATE + "," + UPDATED_MAGANI_DATE);

        // Get all the kmMaganiList where maganiDate equals to UPDATED_MAGANI_DATE
        defaultKmMaganiShouldNotBeFound("maganiDate.in=" + UPDATED_MAGANI_DATE);
    }

    @Test
    @Transactional
    void getAllKmMaganisByMaganiDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        // Get all the kmMaganiList where maganiDate is not null
        defaultKmMaganiShouldBeFound("maganiDate.specified=true");

        // Get all the kmMaganiList where maganiDate is null
        defaultKmMaganiShouldNotBeFound("maganiDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKmMaganiShouldBeFound(String filter) throws Exception {
        restKmMaganiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMagani.getId().intValue())))
            .andExpect(jsonPath("$.[*].kmNumber").value(hasItem(DEFAULT_KM_NUMBER)))
            .andExpect(jsonPath("$.[*].memberNumber").value(hasItem(DEFAULT_MEMBER_NUMBER)))
            .andExpect(jsonPath("$.[*].memberName").value(hasItem(DEFAULT_MEMBER_NAME)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].share").value(hasItem(DEFAULT_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].maganiDate").value(hasItem(DEFAULT_MAGANI_DATE.toString())));

        // Check, that the count call also returns 1
        restKmMaganiMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKmMaganiShouldNotBeFound(String filter) throws Exception {
        restKmMaganiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKmMaganiMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKmMagani() throws Exception {
        // Get the kmMagani
        restKmMaganiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKmMagani() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        int databaseSizeBeforeUpdate = kmMaganiRepository.findAll().size();

        // Update the kmMagani
        KmMagani updatedKmMagani = kmMaganiRepository.findById(kmMagani.getId()).get();
        // Disconnect from session so that the updates on updatedKmMagani are not directly saved in db
        em.detach(updatedKmMagani);
        updatedKmMagani
            .kmNumber(UPDATED_KM_NUMBER)
            .memberNumber(UPDATED_MEMBER_NUMBER)
            .memberName(UPDATED_MEMBER_NAME)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .share(UPDATED_SHARE)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .kmDate(UPDATED_KM_DATE)
            .maganiDate(UPDATED_MAGANI_DATE);

        restKmMaganiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKmMagani.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKmMagani))
            )
            .andExpect(status().isOk());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeUpdate);
        KmMagani testKmMagani = kmMaganiList.get(kmMaganiList.size() - 1);
        assertThat(testKmMagani.getKmNumber()).isEqualTo(UPDATED_KM_NUMBER);
        assertThat(testKmMagani.getMemberNumber()).isEqualTo(UPDATED_MEMBER_NUMBER);
        assertThat(testKmMagani.getMemberName()).isEqualTo(UPDATED_MEMBER_NAME);
        assertThat(testKmMagani.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMagani.getShare()).isEqualTo(UPDATED_SHARE);
        assertThat(testKmMagani.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testKmMagani.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKmMagani.getMaganiDate()).isEqualTo(UPDATED_MAGANI_DATE);
    }

    @Test
    @Transactional
    void putNonExistingKmMagani() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiRepository.findAll().size();
        kmMagani.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmMaganiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kmMagani.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmMagani))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKmMagani() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiRepository.findAll().size();
        kmMagani.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmMagani))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKmMagani() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiRepository.findAll().size();
        kmMagani.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMagani)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKmMaganiWithPatch() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        int databaseSizeBeforeUpdate = kmMaganiRepository.findAll().size();

        // Update the kmMagani using partial update
        KmMagani partialUpdatedKmMagani = new KmMagani();
        partialUpdatedKmMagani.setId(kmMagani.getId());

        partialUpdatedKmMagani.pacsNumber(UPDATED_PACS_NUMBER);

        restKmMaganiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmMagani.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmMagani))
            )
            .andExpect(status().isOk());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeUpdate);
        KmMagani testKmMagani = kmMaganiList.get(kmMaganiList.size() - 1);
        assertThat(testKmMagani.getKmNumber()).isEqualTo(DEFAULT_KM_NUMBER);
        assertThat(testKmMagani.getMemberNumber()).isEqualTo(DEFAULT_MEMBER_NUMBER);
        assertThat(testKmMagani.getMemberName()).isEqualTo(DEFAULT_MEMBER_NAME);
        assertThat(testKmMagani.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMagani.getShare()).isEqualTo(DEFAULT_SHARE);
        assertThat(testKmMagani.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testKmMagani.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
        assertThat(testKmMagani.getMaganiDate()).isEqualTo(DEFAULT_MAGANI_DATE);
    }

    @Test
    @Transactional
    void fullUpdateKmMaganiWithPatch() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        int databaseSizeBeforeUpdate = kmMaganiRepository.findAll().size();

        // Update the kmMagani using partial update
        KmMagani partialUpdatedKmMagani = new KmMagani();
        partialUpdatedKmMagani.setId(kmMagani.getId());

        partialUpdatedKmMagani
            .kmNumber(UPDATED_KM_NUMBER)
            .memberNumber(UPDATED_MEMBER_NUMBER)
            .memberName(UPDATED_MEMBER_NAME)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .share(UPDATED_SHARE)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .kmDate(UPDATED_KM_DATE)
            .maganiDate(UPDATED_MAGANI_DATE);

        restKmMaganiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmMagani.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmMagani))
            )
            .andExpect(status().isOk());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeUpdate);
        KmMagani testKmMagani = kmMaganiList.get(kmMaganiList.size() - 1);
        assertThat(testKmMagani.getKmNumber()).isEqualTo(UPDATED_KM_NUMBER);
        assertThat(testKmMagani.getMemberNumber()).isEqualTo(UPDATED_MEMBER_NUMBER);
        assertThat(testKmMagani.getMemberName()).isEqualTo(UPDATED_MEMBER_NAME);
        assertThat(testKmMagani.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMagani.getShare()).isEqualTo(UPDATED_SHARE);
        assertThat(testKmMagani.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testKmMagani.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKmMagani.getMaganiDate()).isEqualTo(UPDATED_MAGANI_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingKmMagani() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiRepository.findAll().size();
        kmMagani.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmMaganiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kmMagani.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmMagani))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKmMagani() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiRepository.findAll().size();
        kmMagani.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmMagani))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKmMagani() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiRepository.findAll().size();
        kmMagani.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kmMagani)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmMagani in the database
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKmMagani() throws Exception {
        // Initialize the database
        kmMaganiRepository.saveAndFlush(kmMagani);

        int databaseSizeBeforeDelete = kmMaganiRepository.findAll().size();

        // Delete the kmMagani
        restKmMaganiMockMvc
            .perform(delete(ENTITY_API_URL_ID, kmMagani.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KmMagani> kmMaganiList = kmMaganiRepository.findAll();
        assertThat(kmMaganiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
