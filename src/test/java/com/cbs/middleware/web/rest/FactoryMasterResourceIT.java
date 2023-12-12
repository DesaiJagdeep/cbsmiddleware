package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.FactoryMaster;
import com.cbs.middleware.repository.FactoryMasterRepository;
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
 * Integration tests for the {@link FactoryMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FactoryMasterResourceIT {

    private static final String DEFAULT_FACTORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FACTORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FACTORY_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_FACTORY_NAME_MR = "BBBBBBBBBB";

    private static final Long DEFAULT_FACTORY_CODE = 1L;
    private static final Long UPDATED_FACTORY_CODE = 2L;
    private static final Long SMALLER_FACTORY_CODE = 1L - 1L;

    private static final String DEFAULT_FACTORY_CODE_MR = "AAAAAAAAAA";
    private static final String UPDATED_FACTORY_CODE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_FACTORY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FACTORY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_FACTORY_ADDRESS_MR = "AAAAAAAAAA";
    private static final String UPDATED_FACTORY_ADDRESS_MR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/factory-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FactoryMasterRepository factoryMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactoryMasterMockMvc;

    private FactoryMaster factoryMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactoryMaster createEntity(EntityManager em) {
        FactoryMaster factoryMaster = new FactoryMaster()
            .factoryName(DEFAULT_FACTORY_NAME)
            .factoryNameMr(DEFAULT_FACTORY_NAME_MR)
            .factoryCode(DEFAULT_FACTORY_CODE)
            .factoryCodeMr(DEFAULT_FACTORY_CODE_MR)
            .factoryAddress(DEFAULT_FACTORY_ADDRESS)
            .factoryAddressMr(DEFAULT_FACTORY_ADDRESS_MR)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return factoryMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactoryMaster createUpdatedEntity(EntityManager em) {
        FactoryMaster factoryMaster = new FactoryMaster()
            .factoryName(UPDATED_FACTORY_NAME)
            .factoryNameMr(UPDATED_FACTORY_NAME_MR)
            .factoryCode(UPDATED_FACTORY_CODE)
            .factoryCodeMr(UPDATED_FACTORY_CODE_MR)
            .factoryAddress(UPDATED_FACTORY_ADDRESS)
            .factoryAddressMr(UPDATED_FACTORY_ADDRESS_MR)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return factoryMaster;
    }

    @BeforeEach
    public void initTest() {
        factoryMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createFactoryMaster() throws Exception {
        int databaseSizeBeforeCreate = factoryMasterRepository.findAll().size();
        // Create the FactoryMaster
        restFactoryMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factoryMaster)))
            .andExpect(status().isCreated());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeCreate + 1);
        FactoryMaster testFactoryMaster = factoryMasterList.get(factoryMasterList.size() - 1);
        assertThat(testFactoryMaster.getFactoryName()).isEqualTo(DEFAULT_FACTORY_NAME);
        assertThat(testFactoryMaster.getFactoryNameMr()).isEqualTo(DEFAULT_FACTORY_NAME_MR);
        assertThat(testFactoryMaster.getFactoryCode()).isEqualTo(DEFAULT_FACTORY_CODE);
        assertThat(testFactoryMaster.getFactoryCodeMr()).isEqualTo(DEFAULT_FACTORY_CODE_MR);
        assertThat(testFactoryMaster.getFactoryAddress()).isEqualTo(DEFAULT_FACTORY_ADDRESS);
        assertThat(testFactoryMaster.getFactoryAddressMr()).isEqualTo(DEFAULT_FACTORY_ADDRESS_MR);
        assertThat(testFactoryMaster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFactoryMaster.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createFactoryMasterWithExistingId() throws Exception {
        // Create the FactoryMaster with an existing ID
        factoryMaster.setId(1L);

        int databaseSizeBeforeCreate = factoryMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactoryMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factoryMaster)))
            .andExpect(status().isBadRequest());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFactoryMasters() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList
        restFactoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].factoryName").value(hasItem(DEFAULT_FACTORY_NAME)))
            .andExpect(jsonPath("$.[*].factoryNameMr").value(hasItem(DEFAULT_FACTORY_NAME_MR)))
            .andExpect(jsonPath("$.[*].factoryCode").value(hasItem(DEFAULT_FACTORY_CODE.intValue())))
            .andExpect(jsonPath("$.[*].factoryCodeMr").value(hasItem(DEFAULT_FACTORY_CODE_MR)))
            .andExpect(jsonPath("$.[*].factoryAddress").value(hasItem(DEFAULT_FACTORY_ADDRESS)))
            .andExpect(jsonPath("$.[*].factoryAddressMr").value(hasItem(DEFAULT_FACTORY_ADDRESS_MR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getFactoryMaster() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get the factoryMaster
        restFactoryMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, factoryMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factoryMaster.getId().intValue()))
            .andExpect(jsonPath("$.factoryName").value(DEFAULT_FACTORY_NAME))
            .andExpect(jsonPath("$.factoryNameMr").value(DEFAULT_FACTORY_NAME_MR))
            .andExpect(jsonPath("$.factoryCode").value(DEFAULT_FACTORY_CODE.intValue()))
            .andExpect(jsonPath("$.factoryCodeMr").value(DEFAULT_FACTORY_CODE_MR))
            .andExpect(jsonPath("$.factoryAddress").value(DEFAULT_FACTORY_ADDRESS))
            .andExpect(jsonPath("$.factoryAddressMr").value(DEFAULT_FACTORY_ADDRESS_MR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getFactoryMastersByIdFiltering() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        Long id = factoryMaster.getId();

        defaultFactoryMasterShouldBeFound("id.equals=" + id);
        defaultFactoryMasterShouldNotBeFound("id.notEquals=" + id);

        defaultFactoryMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFactoryMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultFactoryMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFactoryMasterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryName equals to DEFAULT_FACTORY_NAME
        defaultFactoryMasterShouldBeFound("factoryName.equals=" + DEFAULT_FACTORY_NAME);

        // Get all the factoryMasterList where factoryName equals to UPDATED_FACTORY_NAME
        defaultFactoryMasterShouldNotBeFound("factoryName.equals=" + UPDATED_FACTORY_NAME);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryName in DEFAULT_FACTORY_NAME or UPDATED_FACTORY_NAME
        defaultFactoryMasterShouldBeFound("factoryName.in=" + DEFAULT_FACTORY_NAME + "," + UPDATED_FACTORY_NAME);

        // Get all the factoryMasterList where factoryName equals to UPDATED_FACTORY_NAME
        defaultFactoryMasterShouldNotBeFound("factoryName.in=" + UPDATED_FACTORY_NAME);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryName is not null
        defaultFactoryMasterShouldBeFound("factoryName.specified=true");

        // Get all the factoryMasterList where factoryName is null
        defaultFactoryMasterShouldNotBeFound("factoryName.specified=false");
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryName contains DEFAULT_FACTORY_NAME
        defaultFactoryMasterShouldBeFound("factoryName.contains=" + DEFAULT_FACTORY_NAME);

        // Get all the factoryMasterList where factoryName contains UPDATED_FACTORY_NAME
        defaultFactoryMasterShouldNotBeFound("factoryName.contains=" + UPDATED_FACTORY_NAME);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameNotContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryName does not contain DEFAULT_FACTORY_NAME
        defaultFactoryMasterShouldNotBeFound("factoryName.doesNotContain=" + DEFAULT_FACTORY_NAME);

        // Get all the factoryMasterList where factoryName does not contain UPDATED_FACTORY_NAME
        defaultFactoryMasterShouldBeFound("factoryName.doesNotContain=" + UPDATED_FACTORY_NAME);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryNameMr equals to DEFAULT_FACTORY_NAME_MR
        defaultFactoryMasterShouldBeFound("factoryNameMr.equals=" + DEFAULT_FACTORY_NAME_MR);

        // Get all the factoryMasterList where factoryNameMr equals to UPDATED_FACTORY_NAME_MR
        defaultFactoryMasterShouldNotBeFound("factoryNameMr.equals=" + UPDATED_FACTORY_NAME_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryNameMr in DEFAULT_FACTORY_NAME_MR or UPDATED_FACTORY_NAME_MR
        defaultFactoryMasterShouldBeFound("factoryNameMr.in=" + DEFAULT_FACTORY_NAME_MR + "," + UPDATED_FACTORY_NAME_MR);

        // Get all the factoryMasterList where factoryNameMr equals to UPDATED_FACTORY_NAME_MR
        defaultFactoryMasterShouldNotBeFound("factoryNameMr.in=" + UPDATED_FACTORY_NAME_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryNameMr is not null
        defaultFactoryMasterShouldBeFound("factoryNameMr.specified=true");

        // Get all the factoryMasterList where factoryNameMr is null
        defaultFactoryMasterShouldNotBeFound("factoryNameMr.specified=false");
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameMrContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryNameMr contains DEFAULT_FACTORY_NAME_MR
        defaultFactoryMasterShouldBeFound("factoryNameMr.contains=" + DEFAULT_FACTORY_NAME_MR);

        // Get all the factoryMasterList where factoryNameMr contains UPDATED_FACTORY_NAME_MR
        defaultFactoryMasterShouldNotBeFound("factoryNameMr.contains=" + UPDATED_FACTORY_NAME_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryNameMrNotContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryNameMr does not contain DEFAULT_FACTORY_NAME_MR
        defaultFactoryMasterShouldNotBeFound("factoryNameMr.doesNotContain=" + DEFAULT_FACTORY_NAME_MR);

        // Get all the factoryMasterList where factoryNameMr does not contain UPDATED_FACTORY_NAME_MR
        defaultFactoryMasterShouldBeFound("factoryNameMr.doesNotContain=" + UPDATED_FACTORY_NAME_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCode equals to DEFAULT_FACTORY_CODE
        defaultFactoryMasterShouldBeFound("factoryCode.equals=" + DEFAULT_FACTORY_CODE);

        // Get all the factoryMasterList where factoryCode equals to UPDATED_FACTORY_CODE
        defaultFactoryMasterShouldNotBeFound("factoryCode.equals=" + UPDATED_FACTORY_CODE);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeIsInShouldWork() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCode in DEFAULT_FACTORY_CODE or UPDATED_FACTORY_CODE
        defaultFactoryMasterShouldBeFound("factoryCode.in=" + DEFAULT_FACTORY_CODE + "," + UPDATED_FACTORY_CODE);

        // Get all the factoryMasterList where factoryCode equals to UPDATED_FACTORY_CODE
        defaultFactoryMasterShouldNotBeFound("factoryCode.in=" + UPDATED_FACTORY_CODE);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCode is not null
        defaultFactoryMasterShouldBeFound("factoryCode.specified=true");

        // Get all the factoryMasterList where factoryCode is null
        defaultFactoryMasterShouldNotBeFound("factoryCode.specified=false");
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCode is greater than or equal to DEFAULT_FACTORY_CODE
        defaultFactoryMasterShouldBeFound("factoryCode.greaterThanOrEqual=" + DEFAULT_FACTORY_CODE);

        // Get all the factoryMasterList where factoryCode is greater than or equal to UPDATED_FACTORY_CODE
        defaultFactoryMasterShouldNotBeFound("factoryCode.greaterThanOrEqual=" + UPDATED_FACTORY_CODE);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCode is less than or equal to DEFAULT_FACTORY_CODE
        defaultFactoryMasterShouldBeFound("factoryCode.lessThanOrEqual=" + DEFAULT_FACTORY_CODE);

        // Get all the factoryMasterList where factoryCode is less than or equal to SMALLER_FACTORY_CODE
        defaultFactoryMasterShouldNotBeFound("factoryCode.lessThanOrEqual=" + SMALLER_FACTORY_CODE);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCode is less than DEFAULT_FACTORY_CODE
        defaultFactoryMasterShouldNotBeFound("factoryCode.lessThan=" + DEFAULT_FACTORY_CODE);

        // Get all the factoryMasterList where factoryCode is less than UPDATED_FACTORY_CODE
        defaultFactoryMasterShouldBeFound("factoryCode.lessThan=" + UPDATED_FACTORY_CODE);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCode is greater than DEFAULT_FACTORY_CODE
        defaultFactoryMasterShouldNotBeFound("factoryCode.greaterThan=" + DEFAULT_FACTORY_CODE);

        // Get all the factoryMasterList where factoryCode is greater than SMALLER_FACTORY_CODE
        defaultFactoryMasterShouldBeFound("factoryCode.greaterThan=" + SMALLER_FACTORY_CODE);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCodeMr equals to DEFAULT_FACTORY_CODE_MR
        defaultFactoryMasterShouldBeFound("factoryCodeMr.equals=" + DEFAULT_FACTORY_CODE_MR);

        // Get all the factoryMasterList where factoryCodeMr equals to UPDATED_FACTORY_CODE_MR
        defaultFactoryMasterShouldNotBeFound("factoryCodeMr.equals=" + UPDATED_FACTORY_CODE_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeMrIsInShouldWork() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCodeMr in DEFAULT_FACTORY_CODE_MR or UPDATED_FACTORY_CODE_MR
        defaultFactoryMasterShouldBeFound("factoryCodeMr.in=" + DEFAULT_FACTORY_CODE_MR + "," + UPDATED_FACTORY_CODE_MR);

        // Get all the factoryMasterList where factoryCodeMr equals to UPDATED_FACTORY_CODE_MR
        defaultFactoryMasterShouldNotBeFound("factoryCodeMr.in=" + UPDATED_FACTORY_CODE_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCodeMr is not null
        defaultFactoryMasterShouldBeFound("factoryCodeMr.specified=true");

        // Get all the factoryMasterList where factoryCodeMr is null
        defaultFactoryMasterShouldNotBeFound("factoryCodeMr.specified=false");
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeMrContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCodeMr contains DEFAULT_FACTORY_CODE_MR
        defaultFactoryMasterShouldBeFound("factoryCodeMr.contains=" + DEFAULT_FACTORY_CODE_MR);

        // Get all the factoryMasterList where factoryCodeMr contains UPDATED_FACTORY_CODE_MR
        defaultFactoryMasterShouldNotBeFound("factoryCodeMr.contains=" + UPDATED_FACTORY_CODE_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryCodeMrNotContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryCodeMr does not contain DEFAULT_FACTORY_CODE_MR
        defaultFactoryMasterShouldNotBeFound("factoryCodeMr.doesNotContain=" + DEFAULT_FACTORY_CODE_MR);

        // Get all the factoryMasterList where factoryCodeMr does not contain UPDATED_FACTORY_CODE_MR
        defaultFactoryMasterShouldBeFound("factoryCodeMr.doesNotContain=" + UPDATED_FACTORY_CODE_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddress equals to DEFAULT_FACTORY_ADDRESS
        defaultFactoryMasterShouldBeFound("factoryAddress.equals=" + DEFAULT_FACTORY_ADDRESS);

        // Get all the factoryMasterList where factoryAddress equals to UPDATED_FACTORY_ADDRESS
        defaultFactoryMasterShouldNotBeFound("factoryAddress.equals=" + UPDATED_FACTORY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressIsInShouldWork() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddress in DEFAULT_FACTORY_ADDRESS or UPDATED_FACTORY_ADDRESS
        defaultFactoryMasterShouldBeFound("factoryAddress.in=" + DEFAULT_FACTORY_ADDRESS + "," + UPDATED_FACTORY_ADDRESS);

        // Get all the factoryMasterList where factoryAddress equals to UPDATED_FACTORY_ADDRESS
        defaultFactoryMasterShouldNotBeFound("factoryAddress.in=" + UPDATED_FACTORY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddress is not null
        defaultFactoryMasterShouldBeFound("factoryAddress.specified=true");

        // Get all the factoryMasterList where factoryAddress is null
        defaultFactoryMasterShouldNotBeFound("factoryAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddress contains DEFAULT_FACTORY_ADDRESS
        defaultFactoryMasterShouldBeFound("factoryAddress.contains=" + DEFAULT_FACTORY_ADDRESS);

        // Get all the factoryMasterList where factoryAddress contains UPDATED_FACTORY_ADDRESS
        defaultFactoryMasterShouldNotBeFound("factoryAddress.contains=" + UPDATED_FACTORY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressNotContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddress does not contain DEFAULT_FACTORY_ADDRESS
        defaultFactoryMasterShouldNotBeFound("factoryAddress.doesNotContain=" + DEFAULT_FACTORY_ADDRESS);

        // Get all the factoryMasterList where factoryAddress does not contain UPDATED_FACTORY_ADDRESS
        defaultFactoryMasterShouldBeFound("factoryAddress.doesNotContain=" + UPDATED_FACTORY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressMrIsEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddressMr equals to DEFAULT_FACTORY_ADDRESS_MR
        defaultFactoryMasterShouldBeFound("factoryAddressMr.equals=" + DEFAULT_FACTORY_ADDRESS_MR);

        // Get all the factoryMasterList where factoryAddressMr equals to UPDATED_FACTORY_ADDRESS_MR
        defaultFactoryMasterShouldNotBeFound("factoryAddressMr.equals=" + UPDATED_FACTORY_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressMrIsInShouldWork() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddressMr in DEFAULT_FACTORY_ADDRESS_MR or UPDATED_FACTORY_ADDRESS_MR
        defaultFactoryMasterShouldBeFound("factoryAddressMr.in=" + DEFAULT_FACTORY_ADDRESS_MR + "," + UPDATED_FACTORY_ADDRESS_MR);

        // Get all the factoryMasterList where factoryAddressMr equals to UPDATED_FACTORY_ADDRESS_MR
        defaultFactoryMasterShouldNotBeFound("factoryAddressMr.in=" + UPDATED_FACTORY_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddressMr is not null
        defaultFactoryMasterShouldBeFound("factoryAddressMr.specified=true");

        // Get all the factoryMasterList where factoryAddressMr is null
        defaultFactoryMasterShouldNotBeFound("factoryAddressMr.specified=false");
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressMrContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddressMr contains DEFAULT_FACTORY_ADDRESS_MR
        defaultFactoryMasterShouldBeFound("factoryAddressMr.contains=" + DEFAULT_FACTORY_ADDRESS_MR);

        // Get all the factoryMasterList where factoryAddressMr contains UPDATED_FACTORY_ADDRESS_MR
        defaultFactoryMasterShouldNotBeFound("factoryAddressMr.contains=" + UPDATED_FACTORY_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByFactoryAddressMrNotContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where factoryAddressMr does not contain DEFAULT_FACTORY_ADDRESS_MR
        defaultFactoryMasterShouldNotBeFound("factoryAddressMr.doesNotContain=" + DEFAULT_FACTORY_ADDRESS_MR);

        // Get all the factoryMasterList where factoryAddressMr does not contain UPDATED_FACTORY_ADDRESS_MR
        defaultFactoryMasterShouldBeFound("factoryAddressMr.doesNotContain=" + UPDATED_FACTORY_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where description equals to DEFAULT_DESCRIPTION
        defaultFactoryMasterShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the factoryMasterList where description equals to UPDATED_DESCRIPTION
        defaultFactoryMasterShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFactoryMasterShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the factoryMasterList where description equals to UPDATED_DESCRIPTION
        defaultFactoryMasterShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where description is not null
        defaultFactoryMasterShouldBeFound("description.specified=true");

        // Get all the factoryMasterList where description is null
        defaultFactoryMasterShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllFactoryMastersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where description contains DEFAULT_DESCRIPTION
        defaultFactoryMasterShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the factoryMasterList where description contains UPDATED_DESCRIPTION
        defaultFactoryMasterShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where description does not contain DEFAULT_DESCRIPTION
        defaultFactoryMasterShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the factoryMasterList where description does not contain UPDATED_DESCRIPTION
        defaultFactoryMasterShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where status equals to DEFAULT_STATUS
        defaultFactoryMasterShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the factoryMasterList where status equals to UPDATED_STATUS
        defaultFactoryMasterShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultFactoryMasterShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the factoryMasterList where status equals to UPDATED_STATUS
        defaultFactoryMasterShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllFactoryMastersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        // Get all the factoryMasterList where status is not null
        defaultFactoryMasterShouldBeFound("status.specified=true");

        // Get all the factoryMasterList where status is null
        defaultFactoryMasterShouldNotBeFound("status.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFactoryMasterShouldBeFound(String filter) throws Exception {
        restFactoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].factoryName").value(hasItem(DEFAULT_FACTORY_NAME)))
            .andExpect(jsonPath("$.[*].factoryNameMr").value(hasItem(DEFAULT_FACTORY_NAME_MR)))
            .andExpect(jsonPath("$.[*].factoryCode").value(hasItem(DEFAULT_FACTORY_CODE.intValue())))
            .andExpect(jsonPath("$.[*].factoryCodeMr").value(hasItem(DEFAULT_FACTORY_CODE_MR)))
            .andExpect(jsonPath("$.[*].factoryAddress").value(hasItem(DEFAULT_FACTORY_ADDRESS)))
            .andExpect(jsonPath("$.[*].factoryAddressMr").value(hasItem(DEFAULT_FACTORY_ADDRESS_MR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));

        // Check, that the count call also returns 1
        restFactoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFactoryMasterShouldNotBeFound(String filter) throws Exception {
        restFactoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFactoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFactoryMaster() throws Exception {
        // Get the factoryMaster
        restFactoryMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFactoryMaster() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        int databaseSizeBeforeUpdate = factoryMasterRepository.findAll().size();

        // Update the factoryMaster
        FactoryMaster updatedFactoryMaster = factoryMasterRepository.findById(factoryMaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFactoryMaster are not directly saved in db
        em.detach(updatedFactoryMaster);
        updatedFactoryMaster
            .factoryName(UPDATED_FACTORY_NAME)
            .factoryNameMr(UPDATED_FACTORY_NAME_MR)
            .factoryCode(UPDATED_FACTORY_CODE)
            .factoryCodeMr(UPDATED_FACTORY_CODE_MR)
            .factoryAddress(UPDATED_FACTORY_ADDRESS)
            .factoryAddressMr(UPDATED_FACTORY_ADDRESS_MR)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restFactoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFactoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFactoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeUpdate);
        FactoryMaster testFactoryMaster = factoryMasterList.get(factoryMasterList.size() - 1);
        assertThat(testFactoryMaster.getFactoryName()).isEqualTo(UPDATED_FACTORY_NAME);
        assertThat(testFactoryMaster.getFactoryNameMr()).isEqualTo(UPDATED_FACTORY_NAME_MR);
        assertThat(testFactoryMaster.getFactoryCode()).isEqualTo(UPDATED_FACTORY_CODE);
        assertThat(testFactoryMaster.getFactoryCodeMr()).isEqualTo(UPDATED_FACTORY_CODE_MR);
        assertThat(testFactoryMaster.getFactoryAddress()).isEqualTo(UPDATED_FACTORY_ADDRESS);
        assertThat(testFactoryMaster.getFactoryAddressMr()).isEqualTo(UPDATED_FACTORY_ADDRESS_MR);
        assertThat(testFactoryMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFactoryMaster.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingFactoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = factoryMasterRepository.findAll().size();
        factoryMaster.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = factoryMasterRepository.findAll().size();
        factoryMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = factoryMasterRepository.findAll().size();
        factoryMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactoryMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factoryMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFactoryMasterWithPatch() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        int databaseSizeBeforeUpdate = factoryMasterRepository.findAll().size();

        // Update the factoryMaster using partial update
        FactoryMaster partialUpdatedFactoryMaster = new FactoryMaster();
        partialUpdatedFactoryMaster.setId(factoryMaster.getId());

        partialUpdatedFactoryMaster
            .factoryNameMr(UPDATED_FACTORY_NAME_MR)
            .factoryCode(UPDATED_FACTORY_CODE)
            .factoryAddressMr(UPDATED_FACTORY_ADDRESS_MR);

        restFactoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeUpdate);
        FactoryMaster testFactoryMaster = factoryMasterList.get(factoryMasterList.size() - 1);
        assertThat(testFactoryMaster.getFactoryName()).isEqualTo(DEFAULT_FACTORY_NAME);
        assertThat(testFactoryMaster.getFactoryNameMr()).isEqualTo(UPDATED_FACTORY_NAME_MR);
        assertThat(testFactoryMaster.getFactoryCode()).isEqualTo(UPDATED_FACTORY_CODE);
        assertThat(testFactoryMaster.getFactoryCodeMr()).isEqualTo(DEFAULT_FACTORY_CODE_MR);
        assertThat(testFactoryMaster.getFactoryAddress()).isEqualTo(DEFAULT_FACTORY_ADDRESS);
        assertThat(testFactoryMaster.getFactoryAddressMr()).isEqualTo(UPDATED_FACTORY_ADDRESS_MR);
        assertThat(testFactoryMaster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFactoryMaster.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateFactoryMasterWithPatch() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        int databaseSizeBeforeUpdate = factoryMasterRepository.findAll().size();

        // Update the factoryMaster using partial update
        FactoryMaster partialUpdatedFactoryMaster = new FactoryMaster();
        partialUpdatedFactoryMaster.setId(factoryMaster.getId());

        partialUpdatedFactoryMaster
            .factoryName(UPDATED_FACTORY_NAME)
            .factoryNameMr(UPDATED_FACTORY_NAME_MR)
            .factoryCode(UPDATED_FACTORY_CODE)
            .factoryCodeMr(UPDATED_FACTORY_CODE_MR)
            .factoryAddress(UPDATED_FACTORY_ADDRESS)
            .factoryAddressMr(UPDATED_FACTORY_ADDRESS_MR)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restFactoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeUpdate);
        FactoryMaster testFactoryMaster = factoryMasterList.get(factoryMasterList.size() - 1);
        assertThat(testFactoryMaster.getFactoryName()).isEqualTo(UPDATED_FACTORY_NAME);
        assertThat(testFactoryMaster.getFactoryNameMr()).isEqualTo(UPDATED_FACTORY_NAME_MR);
        assertThat(testFactoryMaster.getFactoryCode()).isEqualTo(UPDATED_FACTORY_CODE);
        assertThat(testFactoryMaster.getFactoryCodeMr()).isEqualTo(UPDATED_FACTORY_CODE_MR);
        assertThat(testFactoryMaster.getFactoryAddress()).isEqualTo(UPDATED_FACTORY_ADDRESS);
        assertThat(testFactoryMaster.getFactoryAddressMr()).isEqualTo(UPDATED_FACTORY_ADDRESS_MR);
        assertThat(testFactoryMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFactoryMaster.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingFactoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = factoryMasterRepository.findAll().size();
        factoryMaster.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = factoryMasterRepository.findAll().size();
        factoryMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = factoryMasterRepository.findAll().size();
        factoryMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(factoryMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FactoryMaster in the database
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactoryMaster() throws Exception {
        // Initialize the database
        factoryMasterRepository.saveAndFlush(factoryMaster);

        int databaseSizeBeforeDelete = factoryMasterRepository.findAll().size();

        // Delete the factoryMaster
        restFactoryMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, factoryMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FactoryMaster> factoryMasterList = factoryMasterRepository.findAll();
        assertThat(factoryMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
