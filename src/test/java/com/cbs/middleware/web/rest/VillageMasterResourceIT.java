package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.TalukaMaster;
import com.cbs.middleware.domain.VillageMaster;
import com.cbs.middleware.repository.VillageMasterRepository;
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
 * Integration tests for the {@link VillageMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VillageMasterResourceIT {

    private static final String DEFAULT_VILLAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_NAME_MR = "BBBBBBBBBB";

    private static final Long DEFAULT_VILLAGE_CODE = 1L;
    private static final Long UPDATED_VILLAGE_CODE = 2L;
    private static final Long SMALLER_VILLAGE_CODE = 1L - 1L;

    private static final String DEFAULT_VILLAGE_CODE_MR = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_CODE_MR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/village-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VillageMasterRepository villageMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVillageMasterMockMvc;

    private VillageMaster villageMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VillageMaster createEntity(EntityManager em) {
        VillageMaster villageMaster = new VillageMaster()
            .villageName(DEFAULT_VILLAGE_NAME)
            .villageNameMr(DEFAULT_VILLAGE_NAME_MR)
            .villageCode(DEFAULT_VILLAGE_CODE)
            .villageCodeMr(DEFAULT_VILLAGE_CODE_MR);
        return villageMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VillageMaster createUpdatedEntity(EntityManager em) {
        VillageMaster villageMaster = new VillageMaster()
            .villageName(UPDATED_VILLAGE_NAME)
            .villageNameMr(UPDATED_VILLAGE_NAME_MR)
            .villageCode(UPDATED_VILLAGE_CODE)
            .villageCodeMr(UPDATED_VILLAGE_CODE_MR);
        return villageMaster;
    }

    @BeforeEach
    public void initTest() {
        villageMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createVillageMaster() throws Exception {
        int databaseSizeBeforeCreate = villageMasterRepository.findAll().size();
        // Create the VillageMaster
        restVillageMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(villageMaster)))
            .andExpect(status().isCreated());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeCreate + 1);
        VillageMaster testVillageMaster = villageMasterList.get(villageMasterList.size() - 1);
        assertThat(testVillageMaster.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
        assertThat(testVillageMaster.getVillageNameMr()).isEqualTo(DEFAULT_VILLAGE_NAME_MR);
        assertThat(testVillageMaster.getVillageCode()).isEqualTo(DEFAULT_VILLAGE_CODE);
        assertThat(testVillageMaster.getVillageCodeMr()).isEqualTo(DEFAULT_VILLAGE_CODE_MR);
    }

    @Test
    @Transactional
    void createVillageMasterWithExistingId() throws Exception {
        // Create the VillageMaster with an existing ID
        villageMaster.setId(1L);

        int databaseSizeBeforeCreate = villageMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVillageMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(villageMaster)))
            .andExpect(status().isBadRequest());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVillageNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = villageMasterRepository.findAll().size();
        // set the field null
        villageMaster.setVillageName(null);

        // Create the VillageMaster, which fails.

        restVillageMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(villageMaster)))
            .andExpect(status().isBadRequest());

        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVillageMasters() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList
        restVillageMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(villageMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME)))
            .andExpect(jsonPath("$.[*].villageNameMr").value(hasItem(DEFAULT_VILLAGE_NAME_MR)))
            .andExpect(jsonPath("$.[*].villageCode").value(hasItem(DEFAULT_VILLAGE_CODE.intValue())))
            .andExpect(jsonPath("$.[*].villageCodeMr").value(hasItem(DEFAULT_VILLAGE_CODE_MR)));
    }

    @Test
    @Transactional
    void getVillageMaster() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get the villageMaster
        restVillageMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, villageMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(villageMaster.getId().intValue()))
            .andExpect(jsonPath("$.villageName").value(DEFAULT_VILLAGE_NAME))
            .andExpect(jsonPath("$.villageNameMr").value(DEFAULT_VILLAGE_NAME_MR))
            .andExpect(jsonPath("$.villageCode").value(DEFAULT_VILLAGE_CODE.intValue()))
            .andExpect(jsonPath("$.villageCodeMr").value(DEFAULT_VILLAGE_CODE_MR));
    }

    @Test
    @Transactional
    void getVillageMastersByIdFiltering() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        Long id = villageMaster.getId();

        defaultVillageMasterShouldBeFound("id.equals=" + id);
        defaultVillageMasterShouldNotBeFound("id.notEquals=" + id);

        defaultVillageMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVillageMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultVillageMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVillageMasterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageName equals to DEFAULT_VILLAGE_NAME
        defaultVillageMasterShouldBeFound("villageName.equals=" + DEFAULT_VILLAGE_NAME);

        // Get all the villageMasterList where villageName equals to UPDATED_VILLAGE_NAME
        defaultVillageMasterShouldNotBeFound("villageName.equals=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameIsInShouldWork() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageName in DEFAULT_VILLAGE_NAME or UPDATED_VILLAGE_NAME
        defaultVillageMasterShouldBeFound("villageName.in=" + DEFAULT_VILLAGE_NAME + "," + UPDATED_VILLAGE_NAME);

        // Get all the villageMasterList where villageName equals to UPDATED_VILLAGE_NAME
        defaultVillageMasterShouldNotBeFound("villageName.in=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageName is not null
        defaultVillageMasterShouldBeFound("villageName.specified=true");

        // Get all the villageMasterList where villageName is null
        defaultVillageMasterShouldNotBeFound("villageName.specified=false");
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameContainsSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageName contains DEFAULT_VILLAGE_NAME
        defaultVillageMasterShouldBeFound("villageName.contains=" + DEFAULT_VILLAGE_NAME);

        // Get all the villageMasterList where villageName contains UPDATED_VILLAGE_NAME
        defaultVillageMasterShouldNotBeFound("villageName.contains=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameNotContainsSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageName does not contain DEFAULT_VILLAGE_NAME
        defaultVillageMasterShouldNotBeFound("villageName.doesNotContain=" + DEFAULT_VILLAGE_NAME);

        // Get all the villageMasterList where villageName does not contain UPDATED_VILLAGE_NAME
        defaultVillageMasterShouldBeFound("villageName.doesNotContain=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageNameMr equals to DEFAULT_VILLAGE_NAME_MR
        defaultVillageMasterShouldBeFound("villageNameMr.equals=" + DEFAULT_VILLAGE_NAME_MR);

        // Get all the villageMasterList where villageNameMr equals to UPDATED_VILLAGE_NAME_MR
        defaultVillageMasterShouldNotBeFound("villageNameMr.equals=" + UPDATED_VILLAGE_NAME_MR);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageNameMr in DEFAULT_VILLAGE_NAME_MR or UPDATED_VILLAGE_NAME_MR
        defaultVillageMasterShouldBeFound("villageNameMr.in=" + DEFAULT_VILLAGE_NAME_MR + "," + UPDATED_VILLAGE_NAME_MR);

        // Get all the villageMasterList where villageNameMr equals to UPDATED_VILLAGE_NAME_MR
        defaultVillageMasterShouldNotBeFound("villageNameMr.in=" + UPDATED_VILLAGE_NAME_MR);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageNameMr is not null
        defaultVillageMasterShouldBeFound("villageNameMr.specified=true");

        // Get all the villageMasterList where villageNameMr is null
        defaultVillageMasterShouldNotBeFound("villageNameMr.specified=false");
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameMrContainsSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageNameMr contains DEFAULT_VILLAGE_NAME_MR
        defaultVillageMasterShouldBeFound("villageNameMr.contains=" + DEFAULT_VILLAGE_NAME_MR);

        // Get all the villageMasterList where villageNameMr contains UPDATED_VILLAGE_NAME_MR
        defaultVillageMasterShouldNotBeFound("villageNameMr.contains=" + UPDATED_VILLAGE_NAME_MR);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageNameMrNotContainsSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageNameMr does not contain DEFAULT_VILLAGE_NAME_MR
        defaultVillageMasterShouldNotBeFound("villageNameMr.doesNotContain=" + DEFAULT_VILLAGE_NAME_MR);

        // Get all the villageMasterList where villageNameMr does not contain UPDATED_VILLAGE_NAME_MR
        defaultVillageMasterShouldBeFound("villageNameMr.doesNotContain=" + UPDATED_VILLAGE_NAME_MR);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCode equals to DEFAULT_VILLAGE_CODE
        defaultVillageMasterShouldBeFound("villageCode.equals=" + DEFAULT_VILLAGE_CODE);

        // Get all the villageMasterList where villageCode equals to UPDATED_VILLAGE_CODE
        defaultVillageMasterShouldNotBeFound("villageCode.equals=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeIsInShouldWork() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCode in DEFAULT_VILLAGE_CODE or UPDATED_VILLAGE_CODE
        defaultVillageMasterShouldBeFound("villageCode.in=" + DEFAULT_VILLAGE_CODE + "," + UPDATED_VILLAGE_CODE);

        // Get all the villageMasterList where villageCode equals to UPDATED_VILLAGE_CODE
        defaultVillageMasterShouldNotBeFound("villageCode.in=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCode is not null
        defaultVillageMasterShouldBeFound("villageCode.specified=true");

        // Get all the villageMasterList where villageCode is null
        defaultVillageMasterShouldNotBeFound("villageCode.specified=false");
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCode is greater than or equal to DEFAULT_VILLAGE_CODE
        defaultVillageMasterShouldBeFound("villageCode.greaterThanOrEqual=" + DEFAULT_VILLAGE_CODE);

        // Get all the villageMasterList where villageCode is greater than or equal to UPDATED_VILLAGE_CODE
        defaultVillageMasterShouldNotBeFound("villageCode.greaterThanOrEqual=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCode is less than or equal to DEFAULT_VILLAGE_CODE
        defaultVillageMasterShouldBeFound("villageCode.lessThanOrEqual=" + DEFAULT_VILLAGE_CODE);

        // Get all the villageMasterList where villageCode is less than or equal to SMALLER_VILLAGE_CODE
        defaultVillageMasterShouldNotBeFound("villageCode.lessThanOrEqual=" + SMALLER_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCode is less than DEFAULT_VILLAGE_CODE
        defaultVillageMasterShouldNotBeFound("villageCode.lessThan=" + DEFAULT_VILLAGE_CODE);

        // Get all the villageMasterList where villageCode is less than UPDATED_VILLAGE_CODE
        defaultVillageMasterShouldBeFound("villageCode.lessThan=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCode is greater than DEFAULT_VILLAGE_CODE
        defaultVillageMasterShouldNotBeFound("villageCode.greaterThan=" + DEFAULT_VILLAGE_CODE);

        // Get all the villageMasterList where villageCode is greater than SMALLER_VILLAGE_CODE
        defaultVillageMasterShouldBeFound("villageCode.greaterThan=" + SMALLER_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCodeMr equals to DEFAULT_VILLAGE_CODE_MR
        defaultVillageMasterShouldBeFound("villageCodeMr.equals=" + DEFAULT_VILLAGE_CODE_MR);

        // Get all the villageMasterList where villageCodeMr equals to UPDATED_VILLAGE_CODE_MR
        defaultVillageMasterShouldNotBeFound("villageCodeMr.equals=" + UPDATED_VILLAGE_CODE_MR);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeMrIsInShouldWork() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCodeMr in DEFAULT_VILLAGE_CODE_MR or UPDATED_VILLAGE_CODE_MR
        defaultVillageMasterShouldBeFound("villageCodeMr.in=" + DEFAULT_VILLAGE_CODE_MR + "," + UPDATED_VILLAGE_CODE_MR);

        // Get all the villageMasterList where villageCodeMr equals to UPDATED_VILLAGE_CODE_MR
        defaultVillageMasterShouldNotBeFound("villageCodeMr.in=" + UPDATED_VILLAGE_CODE_MR);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCodeMr is not null
        defaultVillageMasterShouldBeFound("villageCodeMr.specified=true");

        // Get all the villageMasterList where villageCodeMr is null
        defaultVillageMasterShouldNotBeFound("villageCodeMr.specified=false");
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeMrContainsSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCodeMr contains DEFAULT_VILLAGE_CODE_MR
        defaultVillageMasterShouldBeFound("villageCodeMr.contains=" + DEFAULT_VILLAGE_CODE_MR);

        // Get all the villageMasterList where villageCodeMr contains UPDATED_VILLAGE_CODE_MR
        defaultVillageMasterShouldNotBeFound("villageCodeMr.contains=" + UPDATED_VILLAGE_CODE_MR);
    }

    @Test
    @Transactional
    void getAllVillageMastersByVillageCodeMrNotContainsSomething() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        // Get all the villageMasterList where villageCodeMr does not contain DEFAULT_VILLAGE_CODE_MR
        defaultVillageMasterShouldNotBeFound("villageCodeMr.doesNotContain=" + DEFAULT_VILLAGE_CODE_MR);

        // Get all the villageMasterList where villageCodeMr does not contain UPDATED_VILLAGE_CODE_MR
        defaultVillageMasterShouldBeFound("villageCodeMr.doesNotContain=" + UPDATED_VILLAGE_CODE_MR);
    }

    @Test
    @Transactional
    void getAllVillageMastersByTalukaMasterIsEqualToSomething() throws Exception {
        TalukaMaster talukaMaster;
        if (TestUtil.findAll(em, TalukaMaster.class).isEmpty()) {
            villageMasterRepository.saveAndFlush(villageMaster);
            talukaMaster = TalukaMasterResourceIT.createEntity(em);
        } else {
            talukaMaster = TestUtil.findAll(em, TalukaMaster.class).get(0);
        }
        em.persist(talukaMaster);
        em.flush();
        villageMaster.setTalukaMaster(talukaMaster);
        villageMasterRepository.saveAndFlush(villageMaster);
        Long talukaMasterId = talukaMaster.getId();
        // Get all the villageMasterList where talukaMaster equals to talukaMasterId
        defaultVillageMasterShouldBeFound("talukaMasterId.equals=" + talukaMasterId);

        // Get all the villageMasterList where talukaMaster equals to (talukaMasterId + 1)
        defaultVillageMasterShouldNotBeFound("talukaMasterId.equals=" + (talukaMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVillageMasterShouldBeFound(String filter) throws Exception {
        restVillageMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(villageMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME)))
            .andExpect(jsonPath("$.[*].villageNameMr").value(hasItem(DEFAULT_VILLAGE_NAME_MR)))
            .andExpect(jsonPath("$.[*].villageCode").value(hasItem(DEFAULT_VILLAGE_CODE.intValue())))
            .andExpect(jsonPath("$.[*].villageCodeMr").value(hasItem(DEFAULT_VILLAGE_CODE_MR)));

        // Check, that the count call also returns 1
        restVillageMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVillageMasterShouldNotBeFound(String filter) throws Exception {
        restVillageMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVillageMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVillageMaster() throws Exception {
        // Get the villageMaster
        restVillageMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVillageMaster() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        int databaseSizeBeforeUpdate = villageMasterRepository.findAll().size();

        // Update the villageMaster
        VillageMaster updatedVillageMaster = villageMasterRepository.findById(villageMaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVillageMaster are not directly saved in db
        em.detach(updatedVillageMaster);
        updatedVillageMaster
            .villageName(UPDATED_VILLAGE_NAME)
            .villageNameMr(UPDATED_VILLAGE_NAME_MR)
            .villageCode(UPDATED_VILLAGE_CODE)
            .villageCodeMr(UPDATED_VILLAGE_CODE_MR);

        restVillageMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVillageMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVillageMaster))
            )
            .andExpect(status().isOk());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeUpdate);
        VillageMaster testVillageMaster = villageMasterList.get(villageMasterList.size() - 1);
        assertThat(testVillageMaster.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testVillageMaster.getVillageNameMr()).isEqualTo(UPDATED_VILLAGE_NAME_MR);
        assertThat(testVillageMaster.getVillageCode()).isEqualTo(UPDATED_VILLAGE_CODE);
        assertThat(testVillageMaster.getVillageCodeMr()).isEqualTo(UPDATED_VILLAGE_CODE_MR);
    }

    @Test
    @Transactional
    void putNonExistingVillageMaster() throws Exception {
        int databaseSizeBeforeUpdate = villageMasterRepository.findAll().size();
        villageMaster.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVillageMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, villageMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(villageMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVillageMaster() throws Exception {
        int databaseSizeBeforeUpdate = villageMasterRepository.findAll().size();
        villageMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVillageMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(villageMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVillageMaster() throws Exception {
        int databaseSizeBeforeUpdate = villageMasterRepository.findAll().size();
        villageMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVillageMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(villageMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVillageMasterWithPatch() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        int databaseSizeBeforeUpdate = villageMasterRepository.findAll().size();

        // Update the villageMaster using partial update
        VillageMaster partialUpdatedVillageMaster = new VillageMaster();
        partialUpdatedVillageMaster.setId(villageMaster.getId());

        partialUpdatedVillageMaster.villageCode(UPDATED_VILLAGE_CODE);

        restVillageMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVillageMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVillageMaster))
            )
            .andExpect(status().isOk());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeUpdate);
        VillageMaster testVillageMaster = villageMasterList.get(villageMasterList.size() - 1);
        assertThat(testVillageMaster.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
        assertThat(testVillageMaster.getVillageNameMr()).isEqualTo(DEFAULT_VILLAGE_NAME_MR);
        assertThat(testVillageMaster.getVillageCode()).isEqualTo(UPDATED_VILLAGE_CODE);
        assertThat(testVillageMaster.getVillageCodeMr()).isEqualTo(DEFAULT_VILLAGE_CODE_MR);
    }

    @Test
    @Transactional
    void fullUpdateVillageMasterWithPatch() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        int databaseSizeBeforeUpdate = villageMasterRepository.findAll().size();

        // Update the villageMaster using partial update
        VillageMaster partialUpdatedVillageMaster = new VillageMaster();
        partialUpdatedVillageMaster.setId(villageMaster.getId());

        partialUpdatedVillageMaster
            .villageName(UPDATED_VILLAGE_NAME)
            .villageNameMr(UPDATED_VILLAGE_NAME_MR)
            .villageCode(UPDATED_VILLAGE_CODE)
            .villageCodeMr(UPDATED_VILLAGE_CODE_MR);

        restVillageMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVillageMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVillageMaster))
            )
            .andExpect(status().isOk());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeUpdate);
        VillageMaster testVillageMaster = villageMasterList.get(villageMasterList.size() - 1);
        assertThat(testVillageMaster.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testVillageMaster.getVillageNameMr()).isEqualTo(UPDATED_VILLAGE_NAME_MR);
        assertThat(testVillageMaster.getVillageCode()).isEqualTo(UPDATED_VILLAGE_CODE);
        assertThat(testVillageMaster.getVillageCodeMr()).isEqualTo(UPDATED_VILLAGE_CODE_MR);
    }

    @Test
    @Transactional
    void patchNonExistingVillageMaster() throws Exception {
        int databaseSizeBeforeUpdate = villageMasterRepository.findAll().size();
        villageMaster.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVillageMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, villageMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(villageMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVillageMaster() throws Exception {
        int databaseSizeBeforeUpdate = villageMasterRepository.findAll().size();
        villageMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVillageMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(villageMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVillageMaster() throws Exception {
        int databaseSizeBeforeUpdate = villageMasterRepository.findAll().size();
        villageMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVillageMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(villageMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VillageMaster in the database
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVillageMaster() throws Exception {
        // Initialize the database
        villageMasterRepository.saveAndFlush(villageMaster);

        int databaseSizeBeforeDelete = villageMasterRepository.findAll().size();

        // Delete the villageMaster
        restVillageMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, villageMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VillageMaster> villageMasterList = villageMasterRepository.findAll();
        assertThat(villageMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
