package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KarkhanaVasuli;
import com.cbs.middleware.repository.KarkhanaVasuliRepository;
import com.cbs.middleware.service.criteria.KarkhanaVasuliCriteria;
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
 * Integration tests for the {@link KarkhanaVasuliResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KarkhanaVasuliResourceIT {

    private static final String DEFAULT_KHATA_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_KHATA_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIETY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TALUKA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TALUKA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULTER_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/karkhana-vasulis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KarkhanaVasuliRepository karkhanaVasuliRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKarkhanaVasuliMockMvc;

    private KarkhanaVasuli karkhanaVasuli;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KarkhanaVasuli createEntity(EntityManager em) {
        KarkhanaVasuli karkhanaVasuli = new KarkhanaVasuli()
            .khataNumber(DEFAULT_KHATA_NUMBER)
            .name(DEFAULT_NAME)
            .societyName(DEFAULT_SOCIETY_NAME)
            .talukaName(DEFAULT_TALUKA_NAME)
            .branchName(DEFAULT_BRANCH_NAME)
            .defaulterName(DEFAULT_DEFAULTER_NAME);
        return karkhanaVasuli;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KarkhanaVasuli createUpdatedEntity(EntityManager em) {
        KarkhanaVasuli karkhanaVasuli = new KarkhanaVasuli()
            .khataNumber(UPDATED_KHATA_NUMBER)
            .name(UPDATED_NAME)
            .societyName(UPDATED_SOCIETY_NAME)
            .talukaName(UPDATED_TALUKA_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .defaulterName(UPDATED_DEFAULTER_NAME);
        return karkhanaVasuli;
    }

    @BeforeEach
    public void initTest() {
        karkhanaVasuli = createEntity(em);
    }

    @Test
    @Transactional
    void createKarkhanaVasuli() throws Exception {
        int databaseSizeBeforeCreate = karkhanaVasuliRepository.findAll().size();
        // Create the KarkhanaVasuli
        restKarkhanaVasuliMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(karkhanaVasuli))
            )
            .andExpect(status().isCreated());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeCreate + 1);
        KarkhanaVasuli testKarkhanaVasuli = karkhanaVasuliList.get(karkhanaVasuliList.size() - 1);
        assertThat(testKarkhanaVasuli.getKhataNumber()).isEqualTo(DEFAULT_KHATA_NUMBER);
        assertThat(testKarkhanaVasuli.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKarkhanaVasuli.getSocietyName()).isEqualTo(DEFAULT_SOCIETY_NAME);
        assertThat(testKarkhanaVasuli.getTalukaName()).isEqualTo(DEFAULT_TALUKA_NAME);
        assertThat(testKarkhanaVasuli.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testKarkhanaVasuli.getDefaulterName()).isEqualTo(DEFAULT_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void createKarkhanaVasuliWithExistingId() throws Exception {
        // Create the KarkhanaVasuli with an existing ID
        karkhanaVasuli.setId(1L);

        int databaseSizeBeforeCreate = karkhanaVasuliRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKarkhanaVasuliMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(karkhanaVasuli))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulis() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList
        restKarkhanaVasuliMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(karkhanaVasuli.getId().intValue())))
            .andExpect(jsonPath("$.[*].khataNumber").value(hasItem(DEFAULT_KHATA_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].societyName").value(hasItem(DEFAULT_SOCIETY_NAME)))
            .andExpect(jsonPath("$.[*].talukaName").value(hasItem(DEFAULT_TALUKA_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].defaulterName").value(hasItem(DEFAULT_DEFAULTER_NAME)));
    }

    @Test
    @Transactional
    void getKarkhanaVasuli() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get the karkhanaVasuli
        restKarkhanaVasuliMockMvc
            .perform(get(ENTITY_API_URL_ID, karkhanaVasuli.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(karkhanaVasuli.getId().intValue()))
            .andExpect(jsonPath("$.khataNumber").value(DEFAULT_KHATA_NUMBER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.societyName").value(DEFAULT_SOCIETY_NAME))
            .andExpect(jsonPath("$.talukaName").value(DEFAULT_TALUKA_NAME))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.defaulterName").value(DEFAULT_DEFAULTER_NAME));
    }

    @Test
    @Transactional
    void getKarkhanaVasulisByIdFiltering() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        Long id = karkhanaVasuli.getId();

        defaultKarkhanaVasuliShouldBeFound("id.equals=" + id);
        defaultKarkhanaVasuliShouldNotBeFound("id.notEquals=" + id);

        defaultKarkhanaVasuliShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKarkhanaVasuliShouldNotBeFound("id.greaterThan=" + id);

        defaultKarkhanaVasuliShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKarkhanaVasuliShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByKhataNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where khataNumber equals to DEFAULT_KHATA_NUMBER
        defaultKarkhanaVasuliShouldBeFound("khataNumber.equals=" + DEFAULT_KHATA_NUMBER);

        // Get all the karkhanaVasuliList where khataNumber equals to UPDATED_KHATA_NUMBER
        defaultKarkhanaVasuliShouldNotBeFound("khataNumber.equals=" + UPDATED_KHATA_NUMBER);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByKhataNumberIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where khataNumber in DEFAULT_KHATA_NUMBER or UPDATED_KHATA_NUMBER
        defaultKarkhanaVasuliShouldBeFound("khataNumber.in=" + DEFAULT_KHATA_NUMBER + "," + UPDATED_KHATA_NUMBER);

        // Get all the karkhanaVasuliList where khataNumber equals to UPDATED_KHATA_NUMBER
        defaultKarkhanaVasuliShouldNotBeFound("khataNumber.in=" + UPDATED_KHATA_NUMBER);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByKhataNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where khataNumber is not null
        defaultKarkhanaVasuliShouldBeFound("khataNumber.specified=true");

        // Get all the karkhanaVasuliList where khataNumber is null
        defaultKarkhanaVasuliShouldNotBeFound("khataNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByKhataNumberContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where khataNumber contains DEFAULT_KHATA_NUMBER
        defaultKarkhanaVasuliShouldBeFound("khataNumber.contains=" + DEFAULT_KHATA_NUMBER);

        // Get all the karkhanaVasuliList where khataNumber contains UPDATED_KHATA_NUMBER
        defaultKarkhanaVasuliShouldNotBeFound("khataNumber.contains=" + UPDATED_KHATA_NUMBER);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByKhataNumberNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where khataNumber does not contain DEFAULT_KHATA_NUMBER
        defaultKarkhanaVasuliShouldNotBeFound("khataNumber.doesNotContain=" + DEFAULT_KHATA_NUMBER);

        // Get all the karkhanaVasuliList where khataNumber does not contain UPDATED_KHATA_NUMBER
        defaultKarkhanaVasuliShouldBeFound("khataNumber.doesNotContain=" + UPDATED_KHATA_NUMBER);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where name equals to DEFAULT_NAME
        defaultKarkhanaVasuliShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the karkhanaVasuliList where name equals to UPDATED_NAME
        defaultKarkhanaVasuliShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where name in DEFAULT_NAME or UPDATED_NAME
        defaultKarkhanaVasuliShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the karkhanaVasuliList where name equals to UPDATED_NAME
        defaultKarkhanaVasuliShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where name is not null
        defaultKarkhanaVasuliShouldBeFound("name.specified=true");

        // Get all the karkhanaVasuliList where name is null
        defaultKarkhanaVasuliShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where name contains DEFAULT_NAME
        defaultKarkhanaVasuliShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the karkhanaVasuliList where name contains UPDATED_NAME
        defaultKarkhanaVasuliShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where name does not contain DEFAULT_NAME
        defaultKarkhanaVasuliShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the karkhanaVasuliList where name does not contain UPDATED_NAME
        defaultKarkhanaVasuliShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisBySocietyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where societyName equals to DEFAULT_SOCIETY_NAME
        defaultKarkhanaVasuliShouldBeFound("societyName.equals=" + DEFAULT_SOCIETY_NAME);

        // Get all the karkhanaVasuliList where societyName equals to UPDATED_SOCIETY_NAME
        defaultKarkhanaVasuliShouldNotBeFound("societyName.equals=" + UPDATED_SOCIETY_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisBySocietyNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where societyName in DEFAULT_SOCIETY_NAME or UPDATED_SOCIETY_NAME
        defaultKarkhanaVasuliShouldBeFound("societyName.in=" + DEFAULT_SOCIETY_NAME + "," + UPDATED_SOCIETY_NAME);

        // Get all the karkhanaVasuliList where societyName equals to UPDATED_SOCIETY_NAME
        defaultKarkhanaVasuliShouldNotBeFound("societyName.in=" + UPDATED_SOCIETY_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisBySocietyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where societyName is not null
        defaultKarkhanaVasuliShouldBeFound("societyName.specified=true");

        // Get all the karkhanaVasuliList where societyName is null
        defaultKarkhanaVasuliShouldNotBeFound("societyName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisBySocietyNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where societyName contains DEFAULT_SOCIETY_NAME
        defaultKarkhanaVasuliShouldBeFound("societyName.contains=" + DEFAULT_SOCIETY_NAME);

        // Get all the karkhanaVasuliList where societyName contains UPDATED_SOCIETY_NAME
        defaultKarkhanaVasuliShouldNotBeFound("societyName.contains=" + UPDATED_SOCIETY_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisBySocietyNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where societyName does not contain DEFAULT_SOCIETY_NAME
        defaultKarkhanaVasuliShouldNotBeFound("societyName.doesNotContain=" + DEFAULT_SOCIETY_NAME);

        // Get all the karkhanaVasuliList where societyName does not contain UPDATED_SOCIETY_NAME
        defaultKarkhanaVasuliShouldBeFound("societyName.doesNotContain=" + UPDATED_SOCIETY_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByTalukaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where talukaName equals to DEFAULT_TALUKA_NAME
        defaultKarkhanaVasuliShouldBeFound("talukaName.equals=" + DEFAULT_TALUKA_NAME);

        // Get all the karkhanaVasuliList where talukaName equals to UPDATED_TALUKA_NAME
        defaultKarkhanaVasuliShouldNotBeFound("talukaName.equals=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByTalukaNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where talukaName in DEFAULT_TALUKA_NAME or UPDATED_TALUKA_NAME
        defaultKarkhanaVasuliShouldBeFound("talukaName.in=" + DEFAULT_TALUKA_NAME + "," + UPDATED_TALUKA_NAME);

        // Get all the karkhanaVasuliList where talukaName equals to UPDATED_TALUKA_NAME
        defaultKarkhanaVasuliShouldNotBeFound("talukaName.in=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByTalukaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where talukaName is not null
        defaultKarkhanaVasuliShouldBeFound("talukaName.specified=true");

        // Get all the karkhanaVasuliList where talukaName is null
        defaultKarkhanaVasuliShouldNotBeFound("talukaName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByTalukaNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where talukaName contains DEFAULT_TALUKA_NAME
        defaultKarkhanaVasuliShouldBeFound("talukaName.contains=" + DEFAULT_TALUKA_NAME);

        // Get all the karkhanaVasuliList where talukaName contains UPDATED_TALUKA_NAME
        defaultKarkhanaVasuliShouldNotBeFound("talukaName.contains=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByTalukaNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where talukaName does not contain DEFAULT_TALUKA_NAME
        defaultKarkhanaVasuliShouldNotBeFound("talukaName.doesNotContain=" + DEFAULT_TALUKA_NAME);

        // Get all the karkhanaVasuliList where talukaName does not contain UPDATED_TALUKA_NAME
        defaultKarkhanaVasuliShouldBeFound("talukaName.doesNotContain=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where branchName equals to DEFAULT_BRANCH_NAME
        defaultKarkhanaVasuliShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the karkhanaVasuliList where branchName equals to UPDATED_BRANCH_NAME
        defaultKarkhanaVasuliShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultKarkhanaVasuliShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the karkhanaVasuliList where branchName equals to UPDATED_BRANCH_NAME
        defaultKarkhanaVasuliShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where branchName is not null
        defaultKarkhanaVasuliShouldBeFound("branchName.specified=true");

        // Get all the karkhanaVasuliList where branchName is null
        defaultKarkhanaVasuliShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where branchName contains DEFAULT_BRANCH_NAME
        defaultKarkhanaVasuliShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the karkhanaVasuliList where branchName contains UPDATED_BRANCH_NAME
        defaultKarkhanaVasuliShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultKarkhanaVasuliShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the karkhanaVasuliList where branchName does not contain UPDATED_BRANCH_NAME
        defaultKarkhanaVasuliShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByDefaulterNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where defaulterName equals to DEFAULT_DEFAULTER_NAME
        defaultKarkhanaVasuliShouldBeFound("defaulterName.equals=" + DEFAULT_DEFAULTER_NAME);

        // Get all the karkhanaVasuliList where defaulterName equals to UPDATED_DEFAULTER_NAME
        defaultKarkhanaVasuliShouldNotBeFound("defaulterName.equals=" + UPDATED_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByDefaulterNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where defaulterName in DEFAULT_DEFAULTER_NAME or UPDATED_DEFAULTER_NAME
        defaultKarkhanaVasuliShouldBeFound("defaulterName.in=" + DEFAULT_DEFAULTER_NAME + "," + UPDATED_DEFAULTER_NAME);

        // Get all the karkhanaVasuliList where defaulterName equals to UPDATED_DEFAULTER_NAME
        defaultKarkhanaVasuliShouldNotBeFound("defaulterName.in=" + UPDATED_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByDefaulterNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where defaulterName is not null
        defaultKarkhanaVasuliShouldBeFound("defaulterName.specified=true");

        // Get all the karkhanaVasuliList where defaulterName is null
        defaultKarkhanaVasuliShouldNotBeFound("defaulterName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByDefaulterNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where defaulterName contains DEFAULT_DEFAULTER_NAME
        defaultKarkhanaVasuliShouldBeFound("defaulterName.contains=" + DEFAULT_DEFAULTER_NAME);

        // Get all the karkhanaVasuliList where defaulterName contains UPDATED_DEFAULTER_NAME
        defaultKarkhanaVasuliShouldNotBeFound("defaulterName.contains=" + UPDATED_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasulisByDefaulterNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        // Get all the karkhanaVasuliList where defaulterName does not contain DEFAULT_DEFAULTER_NAME
        defaultKarkhanaVasuliShouldNotBeFound("defaulterName.doesNotContain=" + DEFAULT_DEFAULTER_NAME);

        // Get all the karkhanaVasuliList where defaulterName does not contain UPDATED_DEFAULTER_NAME
        defaultKarkhanaVasuliShouldBeFound("defaulterName.doesNotContain=" + UPDATED_DEFAULTER_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKarkhanaVasuliShouldBeFound(String filter) throws Exception {
        restKarkhanaVasuliMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(karkhanaVasuli.getId().intValue())))
            .andExpect(jsonPath("$.[*].khataNumber").value(hasItem(DEFAULT_KHATA_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].societyName").value(hasItem(DEFAULT_SOCIETY_NAME)))
            .andExpect(jsonPath("$.[*].talukaName").value(hasItem(DEFAULT_TALUKA_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].defaulterName").value(hasItem(DEFAULT_DEFAULTER_NAME)));

        // Check, that the count call also returns 1
        restKarkhanaVasuliMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKarkhanaVasuliShouldNotBeFound(String filter) throws Exception {
        restKarkhanaVasuliMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKarkhanaVasuliMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKarkhanaVasuli() throws Exception {
        // Get the karkhanaVasuli
        restKarkhanaVasuliMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKarkhanaVasuli() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        int databaseSizeBeforeUpdate = karkhanaVasuliRepository.findAll().size();

        // Update the karkhanaVasuli
        KarkhanaVasuli updatedKarkhanaVasuli = karkhanaVasuliRepository.findById(karkhanaVasuli.getId()).get();
        // Disconnect from session so that the updates on updatedKarkhanaVasuli are not directly saved in db
        em.detach(updatedKarkhanaVasuli);
        updatedKarkhanaVasuli
            .khataNumber(UPDATED_KHATA_NUMBER)
            .name(UPDATED_NAME)
            .societyName(UPDATED_SOCIETY_NAME)
            .talukaName(UPDATED_TALUKA_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .defaulterName(UPDATED_DEFAULTER_NAME);

        restKarkhanaVasuliMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKarkhanaVasuli.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKarkhanaVasuli))
            )
            .andExpect(status().isOk());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeUpdate);
        KarkhanaVasuli testKarkhanaVasuli = karkhanaVasuliList.get(karkhanaVasuliList.size() - 1);
        assertThat(testKarkhanaVasuli.getKhataNumber()).isEqualTo(UPDATED_KHATA_NUMBER);
        assertThat(testKarkhanaVasuli.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKarkhanaVasuli.getSocietyName()).isEqualTo(UPDATED_SOCIETY_NAME);
        assertThat(testKarkhanaVasuli.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testKarkhanaVasuli.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testKarkhanaVasuli.getDefaulterName()).isEqualTo(UPDATED_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void putNonExistingKarkhanaVasuli() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRepository.findAll().size();
        karkhanaVasuli.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKarkhanaVasuliMockMvc
            .perform(
                put(ENTITY_API_URL_ID, karkhanaVasuli.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuli))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKarkhanaVasuli() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRepository.findAll().size();
        karkhanaVasuli.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuli))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKarkhanaVasuli() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRepository.findAll().size();
        karkhanaVasuli.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(karkhanaVasuli)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKarkhanaVasuliWithPatch() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        int databaseSizeBeforeUpdate = karkhanaVasuliRepository.findAll().size();

        // Update the karkhanaVasuli using partial update
        KarkhanaVasuli partialUpdatedKarkhanaVasuli = new KarkhanaVasuli();
        partialUpdatedKarkhanaVasuli.setId(karkhanaVasuli.getId());

        partialUpdatedKarkhanaVasuli.khataNumber(UPDATED_KHATA_NUMBER).name(UPDATED_NAME).talukaName(UPDATED_TALUKA_NAME);

        restKarkhanaVasuliMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKarkhanaVasuli.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKarkhanaVasuli))
            )
            .andExpect(status().isOk());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeUpdate);
        KarkhanaVasuli testKarkhanaVasuli = karkhanaVasuliList.get(karkhanaVasuliList.size() - 1);
        assertThat(testKarkhanaVasuli.getKhataNumber()).isEqualTo(UPDATED_KHATA_NUMBER);
        assertThat(testKarkhanaVasuli.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKarkhanaVasuli.getSocietyName()).isEqualTo(DEFAULT_SOCIETY_NAME);
        assertThat(testKarkhanaVasuli.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testKarkhanaVasuli.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testKarkhanaVasuli.getDefaulterName()).isEqualTo(DEFAULT_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void fullUpdateKarkhanaVasuliWithPatch() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        int databaseSizeBeforeUpdate = karkhanaVasuliRepository.findAll().size();

        // Update the karkhanaVasuli using partial update
        KarkhanaVasuli partialUpdatedKarkhanaVasuli = new KarkhanaVasuli();
        partialUpdatedKarkhanaVasuli.setId(karkhanaVasuli.getId());

        partialUpdatedKarkhanaVasuli
            .khataNumber(UPDATED_KHATA_NUMBER)
            .name(UPDATED_NAME)
            .societyName(UPDATED_SOCIETY_NAME)
            .talukaName(UPDATED_TALUKA_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .defaulterName(UPDATED_DEFAULTER_NAME);

        restKarkhanaVasuliMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKarkhanaVasuli.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKarkhanaVasuli))
            )
            .andExpect(status().isOk());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeUpdate);
        KarkhanaVasuli testKarkhanaVasuli = karkhanaVasuliList.get(karkhanaVasuliList.size() - 1);
        assertThat(testKarkhanaVasuli.getKhataNumber()).isEqualTo(UPDATED_KHATA_NUMBER);
        assertThat(testKarkhanaVasuli.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKarkhanaVasuli.getSocietyName()).isEqualTo(UPDATED_SOCIETY_NAME);
        assertThat(testKarkhanaVasuli.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testKarkhanaVasuli.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testKarkhanaVasuli.getDefaulterName()).isEqualTo(UPDATED_DEFAULTER_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingKarkhanaVasuli() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRepository.findAll().size();
        karkhanaVasuli.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKarkhanaVasuliMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, karkhanaVasuli.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuli))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKarkhanaVasuli() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRepository.findAll().size();
        karkhanaVasuli.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuli))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKarkhanaVasuli() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRepository.findAll().size();
        karkhanaVasuli.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(karkhanaVasuli))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KarkhanaVasuli in the database
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKarkhanaVasuli() throws Exception {
        // Initialize the database
        karkhanaVasuliRepository.saveAndFlush(karkhanaVasuli);

        int databaseSizeBeforeDelete = karkhanaVasuliRepository.findAll().size();

        // Delete the karkhanaVasuli
        restKarkhanaVasuliMockMvc
            .perform(delete(ENTITY_API_URL_ID, karkhanaVasuli.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KarkhanaVasuli> karkhanaVasuliList = karkhanaVasuliRepository.findAll();
        assertThat(karkhanaVasuliList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
