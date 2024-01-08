package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.KmMasterRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link KmMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KmMasterResourceIT {

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_NAME_MR = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_ADDRESS_MR = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_ADDRESS_MR = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_CAST = "AAAAAAAAAA";
    private static final String UPDATED_CAST = "BBBBBBBBBB";

    private static final String DEFAULT_CAST_MR = "AAAAAAAAAA";
    private static final String UPDATED_CAST_MR = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_HECTOR = "AAAAAAAAAA";
    private static final String UPDATED_AREA_HECTOR = "BBBBBBBBBB";

    private static final Long DEFAULT_AADHAR_NO = 1L;
    private static final Long UPDATED_AADHAR_NO = 2L;
    private static final Long SMALLER_AADHAR_NO = 1L - 1L;

    private static final String DEFAULT_AADHAR_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_NO_MR = "BBBBBBBBBB";

    private static final Long DEFAULT_PAN_NO = 1L;
    private static final Long UPDATED_PAN_NO = 2L;
    private static final Long SMALLER_PAN_NO = 1L - 1L;

    private static final String DEFAULT_PAN_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NO_MR = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO_MR = "BBBBBBBBBB";

    private static final Long DEFAULT_SAVING_NO = 1L;
    private static final Long UPDATED_SAVING_NO = 2L;
    private static final Long SMALLER_SAVING_NO = 1L - 1L;

    private static final String DEFAULT_SAVING_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_SAVING_NO_MR = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_MEMBER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PACS_MEMBER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRY_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_ENTRY_FLAG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/km-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KmMasterRepository kmMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKmMasterMockMvc;

    private KmMaster kmMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmMaster createEntity(EntityManager em) {
        KmMaster kmMaster = new KmMaster()
            .branchCode(DEFAULT_BRANCH_CODE)
            .farmerName(DEFAULT_FARMER_NAME)
            .farmerNameMr(DEFAULT_FARMER_NAME_MR)
            .farmerAddress(DEFAULT_FARMER_ADDRESS)
            .farmerAddressMr(DEFAULT_FARMER_ADDRESS_MR)
            .gender(DEFAULT_GENDER)
            .cast(DEFAULT_CAST)
            .castMr(DEFAULT_CAST_MR)
            .pacsNumber(DEFAULT_PACS_NUMBER)
            .areaHector(DEFAULT_AREA_HECTOR)
            .aadharNo(DEFAULT_AADHAR_NO)
            .aadharNoMr(DEFAULT_AADHAR_NO_MR)
            .panNo(DEFAULT_PAN_NO)
            .panNoMr(DEFAULT_PAN_NO_MR)
            .mobileNo(DEFAULT_MOBILE_NO)
            .mobileNoMr(DEFAULT_MOBILE_NO_MR)
            .savingNo(DEFAULT_SAVING_NO)
            .savingNoMr(DEFAULT_SAVING_NO_MR)
            .pacsMemberCode(DEFAULT_PACS_MEMBER_CODE)
            .entryFlag(DEFAULT_ENTRY_FLAG);
        return kmMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmMaster createUpdatedEntity(EntityManager em) {
        KmMaster kmMaster = new KmMaster()
            .branchCode(UPDATED_BRANCH_CODE)
            .farmerName(UPDATED_FARMER_NAME)
            .farmerNameMr(UPDATED_FARMER_NAME_MR)
            .farmerAddress(UPDATED_FARMER_ADDRESS)
            .farmerAddressMr(UPDATED_FARMER_ADDRESS_MR)
            .gender(UPDATED_GENDER)
            .cast(UPDATED_CAST)
            .castMr(UPDATED_CAST_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .areaHector(UPDATED_AREA_HECTOR)
            .aadharNo(UPDATED_AADHAR_NO)
            .aadharNoMr(UPDATED_AADHAR_NO_MR)
            .panNo(UPDATED_PAN_NO)
            .panNoMr(UPDATED_PAN_NO_MR)
            .mobileNo(UPDATED_MOBILE_NO)
            .mobileNoMr(UPDATED_MOBILE_NO_MR)
            .savingNo(UPDATED_SAVING_NO)
            .savingNoMr(UPDATED_SAVING_NO_MR)
            .pacsMemberCode(UPDATED_PACS_MEMBER_CODE)
            .entryFlag(UPDATED_ENTRY_FLAG);
        return kmMaster;
    }

    @BeforeEach
    public void initTest() {
        kmMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createKmMaster() throws Exception {
        int databaseSizeBeforeCreate = kmMasterRepository.findAll().size();
        // Create the KmMaster
        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isCreated());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeCreate + 1);
        KmMaster testKmMaster = kmMasterList.get(kmMasterList.size() - 1);
        assertThat(testKmMaster.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testKmMaster.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testKmMaster.getFarmerNameMr()).isEqualTo(DEFAULT_FARMER_NAME_MR);
        assertThat(testKmMaster.getFarmerAddress()).isEqualTo(DEFAULT_FARMER_ADDRESS);
        assertThat(testKmMaster.getFarmerAddressMr()).isEqualTo(DEFAULT_FARMER_ADDRESS_MR);
        assertThat(testKmMaster.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testKmMaster.getCast()).isEqualTo(DEFAULT_CAST);
        assertThat(testKmMaster.getCastMr()).isEqualTo(DEFAULT_CAST_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHector()).isEqualTo(DEFAULT_AREA_HECTOR);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(DEFAULT_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(DEFAULT_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(DEFAULT_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(DEFAULT_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(DEFAULT_MOBILE_NO_MR);
        assertThat(testKmMaster.getSavingNo()).isEqualTo(DEFAULT_SAVING_NO);
        assertThat(testKmMaster.getSavingNoMr()).isEqualTo(DEFAULT_SAVING_NO_MR);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(DEFAULT_PACS_MEMBER_CODE);
        assertThat(testKmMaster.getEntryFlag()).isEqualTo(DEFAULT_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void createKmMasterWithExistingId() throws Exception {
        // Create the KmMaster with an existing ID
        kmMaster.setId(1L);

        int databaseSizeBeforeCreate = kmMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKmMasters() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList
        restKmMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)))
            .andExpect(jsonPath("$.[*].farmerNameMr").value(hasItem(DEFAULT_FARMER_NAME_MR)))
            .andExpect(jsonPath("$.[*].farmerAddress").value(hasItem(DEFAULT_FARMER_ADDRESS)))
            .andExpect(jsonPath("$.[*].farmerAddressMr").value(hasItem(DEFAULT_FARMER_ADDRESS_MR)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].cast").value(hasItem(DEFAULT_CAST)))
            .andExpect(jsonPath("$.[*].castMr").value(hasItem(DEFAULT_CAST_MR)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].areaHector").value(hasItem(DEFAULT_AREA_HECTOR)))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO.intValue())))
            .andExpect(jsonPath("$.[*].aadharNoMr").value(hasItem(DEFAULT_AADHAR_NO_MR)))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO.intValue())))
            .andExpect(jsonPath("$.[*].panNoMr").value(hasItem(DEFAULT_PAN_NO_MR)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].mobileNoMr").value(hasItem(DEFAULT_MOBILE_NO_MR)))
            .andExpect(jsonPath("$.[*].savingNo").value(hasItem(DEFAULT_SAVING_NO.intValue())))
            .andExpect(jsonPath("$.[*].savingNoMr").value(hasItem(DEFAULT_SAVING_NO_MR)))
            .andExpect(jsonPath("$.[*].pacsMemberCode").value(hasItem(DEFAULT_PACS_MEMBER_CODE)))
            .andExpect(jsonPath("$.[*].entryFlag").value(hasItem(DEFAULT_ENTRY_FLAG)));
    }

    @Test
    @Transactional
    void getKmMaster() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get the kmMaster
        restKmMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, kmMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kmMaster.getId().intValue()))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE))
            .andExpect(jsonPath("$.farmerName").value(DEFAULT_FARMER_NAME))
            .andExpect(jsonPath("$.farmerNameMr").value(DEFAULT_FARMER_NAME_MR))
            .andExpect(jsonPath("$.farmerAddress").value(DEFAULT_FARMER_ADDRESS))
            .andExpect(jsonPath("$.farmerAddressMr").value(DEFAULT_FARMER_ADDRESS_MR))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.cast").value(DEFAULT_CAST))
            .andExpect(jsonPath("$.castMr").value(DEFAULT_CAST_MR))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER))
            .andExpect(jsonPath("$.areaHector").value(DEFAULT_AREA_HECTOR))
            .andExpect(jsonPath("$.aadharNo").value(DEFAULT_AADHAR_NO.intValue()))
            .andExpect(jsonPath("$.aadharNoMr").value(DEFAULT_AADHAR_NO_MR))
            .andExpect(jsonPath("$.panNo").value(DEFAULT_PAN_NO.intValue()))
            .andExpect(jsonPath("$.panNoMr").value(DEFAULT_PAN_NO_MR))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.mobileNoMr").value(DEFAULT_MOBILE_NO_MR))
            .andExpect(jsonPath("$.savingNo").value(DEFAULT_SAVING_NO.intValue()))
            .andExpect(jsonPath("$.savingNoMr").value(DEFAULT_SAVING_NO_MR))
            .andExpect(jsonPath("$.pacsMemberCode").value(DEFAULT_PACS_MEMBER_CODE))
            .andExpect(jsonPath("$.entryFlag").value(DEFAULT_ENTRY_FLAG));
    }

    @Test
    @Transactional
    void getKmMastersByIdFiltering() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        Long id = kmMaster.getId();

        defaultKmMasterShouldBeFound("id.equals=" + id);
        defaultKmMasterShouldNotBeFound("id.notEquals=" + id);

        defaultKmMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKmMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultKmMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKmMasterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKmMastersByBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCode equals to DEFAULT_BRANCH_CODE
        defaultKmMasterShouldBeFound("branchCode.equals=" + DEFAULT_BRANCH_CODE);

        // Get all the kmMasterList where branchCode equals to UPDATED_BRANCH_CODE
        defaultKmMasterShouldNotBeFound("branchCode.equals=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKmMastersByBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCode in DEFAULT_BRANCH_CODE or UPDATED_BRANCH_CODE
        defaultKmMasterShouldBeFound("branchCode.in=" + DEFAULT_BRANCH_CODE + "," + UPDATED_BRANCH_CODE);

        // Get all the kmMasterList where branchCode equals to UPDATED_BRANCH_CODE
        defaultKmMasterShouldNotBeFound("branchCode.in=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKmMastersByBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCode is not null
        defaultKmMasterShouldBeFound("branchCode.specified=true");

        // Get all the kmMasterList where branchCode is null
        defaultKmMasterShouldNotBeFound("branchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByBranchCodeContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCode contains DEFAULT_BRANCH_CODE
        defaultKmMasterShouldBeFound("branchCode.contains=" + DEFAULT_BRANCH_CODE);

        // Get all the kmMasterList where branchCode contains UPDATED_BRANCH_CODE
        defaultKmMasterShouldNotBeFound("branchCode.contains=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKmMastersByBranchCodeNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCode does not contain DEFAULT_BRANCH_CODE
        defaultKmMasterShouldNotBeFound("branchCode.doesNotContain=" + DEFAULT_BRANCH_CODE);

        // Get all the kmMasterList where branchCode does not contain UPDATED_BRANCH_CODE
        defaultKmMasterShouldBeFound("branchCode.doesNotContain=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerName equals to DEFAULT_FARMER_NAME
        defaultKmMasterShouldBeFound("farmerName.equals=" + DEFAULT_FARMER_NAME);

        // Get all the kmMasterList where farmerName equals to UPDATED_FARMER_NAME
        defaultKmMasterShouldNotBeFound("farmerName.equals=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerName in DEFAULT_FARMER_NAME or UPDATED_FARMER_NAME
        defaultKmMasterShouldBeFound("farmerName.in=" + DEFAULT_FARMER_NAME + "," + UPDATED_FARMER_NAME);

        // Get all the kmMasterList where farmerName equals to UPDATED_FARMER_NAME
        defaultKmMasterShouldNotBeFound("farmerName.in=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerName is not null
        defaultKmMasterShouldBeFound("farmerName.specified=true");

        // Get all the kmMasterList where farmerName is null
        defaultKmMasterShouldNotBeFound("farmerName.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerName contains DEFAULT_FARMER_NAME
        defaultKmMasterShouldBeFound("farmerName.contains=" + DEFAULT_FARMER_NAME);

        // Get all the kmMasterList where farmerName contains UPDATED_FARMER_NAME
        defaultKmMasterShouldNotBeFound("farmerName.contains=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerName does not contain DEFAULT_FARMER_NAME
        defaultKmMasterShouldNotBeFound("farmerName.doesNotContain=" + DEFAULT_FARMER_NAME);

        // Get all the kmMasterList where farmerName does not contain UPDATED_FARMER_NAME
        defaultKmMasterShouldBeFound("farmerName.doesNotContain=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerNameMr equals to DEFAULT_FARMER_NAME_MR
        defaultKmMasterShouldBeFound("farmerNameMr.equals=" + DEFAULT_FARMER_NAME_MR);

        // Get all the kmMasterList where farmerNameMr equals to UPDATED_FARMER_NAME_MR
        defaultKmMasterShouldNotBeFound("farmerNameMr.equals=" + UPDATED_FARMER_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerNameMr in DEFAULT_FARMER_NAME_MR or UPDATED_FARMER_NAME_MR
        defaultKmMasterShouldBeFound("farmerNameMr.in=" + DEFAULT_FARMER_NAME_MR + "," + UPDATED_FARMER_NAME_MR);

        // Get all the kmMasterList where farmerNameMr equals to UPDATED_FARMER_NAME_MR
        defaultKmMasterShouldNotBeFound("farmerNameMr.in=" + UPDATED_FARMER_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerNameMr is not null
        defaultKmMasterShouldBeFound("farmerNameMr.specified=true");

        // Get all the kmMasterList where farmerNameMr is null
        defaultKmMasterShouldNotBeFound("farmerNameMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerNameMr contains DEFAULT_FARMER_NAME_MR
        defaultKmMasterShouldBeFound("farmerNameMr.contains=" + DEFAULT_FARMER_NAME_MR);

        // Get all the kmMasterList where farmerNameMr contains UPDATED_FARMER_NAME_MR
        defaultKmMasterShouldNotBeFound("farmerNameMr.contains=" + UPDATED_FARMER_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerNameMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerNameMr does not contain DEFAULT_FARMER_NAME_MR
        defaultKmMasterShouldNotBeFound("farmerNameMr.doesNotContain=" + DEFAULT_FARMER_NAME_MR);

        // Get all the kmMasterList where farmerNameMr does not contain UPDATED_FARMER_NAME_MR
        defaultKmMasterShouldBeFound("farmerNameMr.doesNotContain=" + UPDATED_FARMER_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddress equals to DEFAULT_FARMER_ADDRESS
        defaultKmMasterShouldBeFound("farmerAddress.equals=" + DEFAULT_FARMER_ADDRESS);

        // Get all the kmMasterList where farmerAddress equals to UPDATED_FARMER_ADDRESS
        defaultKmMasterShouldNotBeFound("farmerAddress.equals=" + UPDATED_FARMER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddress in DEFAULT_FARMER_ADDRESS or UPDATED_FARMER_ADDRESS
        defaultKmMasterShouldBeFound("farmerAddress.in=" + DEFAULT_FARMER_ADDRESS + "," + UPDATED_FARMER_ADDRESS);

        // Get all the kmMasterList where farmerAddress equals to UPDATED_FARMER_ADDRESS
        defaultKmMasterShouldNotBeFound("farmerAddress.in=" + UPDATED_FARMER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddress is not null
        defaultKmMasterShouldBeFound("farmerAddress.specified=true");

        // Get all the kmMasterList where farmerAddress is null
        defaultKmMasterShouldNotBeFound("farmerAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddress contains DEFAULT_FARMER_ADDRESS
        defaultKmMasterShouldBeFound("farmerAddress.contains=" + DEFAULT_FARMER_ADDRESS);

        // Get all the kmMasterList where farmerAddress contains UPDATED_FARMER_ADDRESS
        defaultKmMasterShouldNotBeFound("farmerAddress.contains=" + UPDATED_FARMER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddress does not contain DEFAULT_FARMER_ADDRESS
        defaultKmMasterShouldNotBeFound("farmerAddress.doesNotContain=" + DEFAULT_FARMER_ADDRESS);

        // Get all the kmMasterList where farmerAddress does not contain UPDATED_FARMER_ADDRESS
        defaultKmMasterShouldBeFound("farmerAddress.doesNotContain=" + UPDATED_FARMER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddressMr equals to DEFAULT_FARMER_ADDRESS_MR
        defaultKmMasterShouldBeFound("farmerAddressMr.equals=" + DEFAULT_FARMER_ADDRESS_MR);

        // Get all the kmMasterList where farmerAddressMr equals to UPDATED_FARMER_ADDRESS_MR
        defaultKmMasterShouldNotBeFound("farmerAddressMr.equals=" + UPDATED_FARMER_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddressMr in DEFAULT_FARMER_ADDRESS_MR or UPDATED_FARMER_ADDRESS_MR
        defaultKmMasterShouldBeFound("farmerAddressMr.in=" + DEFAULT_FARMER_ADDRESS_MR + "," + UPDATED_FARMER_ADDRESS_MR);

        // Get all the kmMasterList where farmerAddressMr equals to UPDATED_FARMER_ADDRESS_MR
        defaultKmMasterShouldNotBeFound("farmerAddressMr.in=" + UPDATED_FARMER_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddressMr is not null
        defaultKmMasterShouldBeFound("farmerAddressMr.specified=true");

        // Get all the kmMasterList where farmerAddressMr is null
        defaultKmMasterShouldNotBeFound("farmerAddressMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddressMr contains DEFAULT_FARMER_ADDRESS_MR
        defaultKmMasterShouldBeFound("farmerAddressMr.contains=" + DEFAULT_FARMER_ADDRESS_MR);

        // Get all the kmMasterList where farmerAddressMr contains UPDATED_FARMER_ADDRESS_MR
        defaultKmMasterShouldNotBeFound("farmerAddressMr.contains=" + UPDATED_FARMER_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerAddressMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddressMr does not contain DEFAULT_FARMER_ADDRESS_MR
        defaultKmMasterShouldNotBeFound("farmerAddressMr.doesNotContain=" + DEFAULT_FARMER_ADDRESS_MR);

        // Get all the kmMasterList where farmerAddressMr does not contain UPDATED_FARMER_ADDRESS_MR
        defaultKmMasterShouldBeFound("farmerAddressMr.doesNotContain=" + UPDATED_FARMER_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where gender equals to DEFAULT_GENDER
        defaultKmMasterShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the kmMasterList where gender equals to UPDATED_GENDER
        defaultKmMasterShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllKmMastersByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultKmMasterShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the kmMasterList where gender equals to UPDATED_GENDER
        defaultKmMasterShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllKmMastersByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where gender is not null
        defaultKmMasterShouldBeFound("gender.specified=true");

        // Get all the kmMasterList where gender is null
        defaultKmMasterShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByGenderContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where gender contains DEFAULT_GENDER
        defaultKmMasterShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the kmMasterList where gender contains UPDATED_GENDER
        defaultKmMasterShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllKmMastersByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where gender does not contain DEFAULT_GENDER
        defaultKmMasterShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the kmMasterList where gender does not contain UPDATED_GENDER
        defaultKmMasterShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllKmMastersByCastIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where cast equals to DEFAULT_CAST
        defaultKmMasterShouldBeFound("cast.equals=" + DEFAULT_CAST);

        // Get all the kmMasterList where cast equals to UPDATED_CAST
        defaultKmMasterShouldNotBeFound("cast.equals=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllKmMastersByCastIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where cast in DEFAULT_CAST or UPDATED_CAST
        defaultKmMasterShouldBeFound("cast.in=" + DEFAULT_CAST + "," + UPDATED_CAST);

        // Get all the kmMasterList where cast equals to UPDATED_CAST
        defaultKmMasterShouldNotBeFound("cast.in=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllKmMastersByCastIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where cast is not null
        defaultKmMasterShouldBeFound("cast.specified=true");

        // Get all the kmMasterList where cast is null
        defaultKmMasterShouldNotBeFound("cast.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByCastContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where cast contains DEFAULT_CAST
        defaultKmMasterShouldBeFound("cast.contains=" + DEFAULT_CAST);

        // Get all the kmMasterList where cast contains UPDATED_CAST
        defaultKmMasterShouldNotBeFound("cast.contains=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllKmMastersByCastNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where cast does not contain DEFAULT_CAST
        defaultKmMasterShouldNotBeFound("cast.doesNotContain=" + DEFAULT_CAST);

        // Get all the kmMasterList where cast does not contain UPDATED_CAST
        defaultKmMasterShouldBeFound("cast.doesNotContain=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllKmMastersByCastMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where castMr equals to DEFAULT_CAST_MR
        defaultKmMasterShouldBeFound("castMr.equals=" + DEFAULT_CAST_MR);

        // Get all the kmMasterList where castMr equals to UPDATED_CAST_MR
        defaultKmMasterShouldNotBeFound("castMr.equals=" + UPDATED_CAST_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByCastMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where castMr in DEFAULT_CAST_MR or UPDATED_CAST_MR
        defaultKmMasterShouldBeFound("castMr.in=" + DEFAULT_CAST_MR + "," + UPDATED_CAST_MR);

        // Get all the kmMasterList where castMr equals to UPDATED_CAST_MR
        defaultKmMasterShouldNotBeFound("castMr.in=" + UPDATED_CAST_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByCastMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where castMr is not null
        defaultKmMasterShouldBeFound("castMr.specified=true");

        // Get all the kmMasterList where castMr is null
        defaultKmMasterShouldNotBeFound("castMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByCastMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where castMr contains DEFAULT_CAST_MR
        defaultKmMasterShouldBeFound("castMr.contains=" + DEFAULT_CAST_MR);

        // Get all the kmMasterList where castMr contains UPDATED_CAST_MR
        defaultKmMasterShouldNotBeFound("castMr.contains=" + UPDATED_CAST_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByCastMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where castMr does not contain DEFAULT_CAST_MR
        defaultKmMasterShouldNotBeFound("castMr.doesNotContain=" + DEFAULT_CAST_MR);

        // Get all the kmMasterList where castMr does not contain UPDATED_CAST_MR
        defaultKmMasterShouldBeFound("castMr.doesNotContain=" + UPDATED_CAST_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsNumber equals to DEFAULT_PACS_NUMBER
        defaultKmMasterShouldBeFound("pacsNumber.equals=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMasterList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKmMasterShouldNotBeFound("pacsNumber.equals=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsNumber in DEFAULT_PACS_NUMBER or UPDATED_PACS_NUMBER
        defaultKmMasterShouldBeFound("pacsNumber.in=" + DEFAULT_PACS_NUMBER + "," + UPDATED_PACS_NUMBER);

        // Get all the kmMasterList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKmMasterShouldNotBeFound("pacsNumber.in=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsNumber is not null
        defaultKmMasterShouldBeFound("pacsNumber.specified=true");

        // Get all the kmMasterList where pacsNumber is null
        defaultKmMasterShouldNotBeFound("pacsNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsNumberContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsNumber contains DEFAULT_PACS_NUMBER
        defaultKmMasterShouldBeFound("pacsNumber.contains=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMasterList where pacsNumber contains UPDATED_PACS_NUMBER
        defaultKmMasterShouldNotBeFound("pacsNumber.contains=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsNumber does not contain DEFAULT_PACS_NUMBER
        defaultKmMasterShouldNotBeFound("pacsNumber.doesNotContain=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMasterList where pacsNumber does not contain UPDATED_PACS_NUMBER
        defaultKmMasterShouldBeFound("pacsNumber.doesNotContain=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHector equals to DEFAULT_AREA_HECTOR
        defaultKmMasterShouldBeFound("areaHector.equals=" + DEFAULT_AREA_HECTOR);

        // Get all the kmMasterList where areaHector equals to UPDATED_AREA_HECTOR
        defaultKmMasterShouldNotBeFound("areaHector.equals=" + UPDATED_AREA_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHector in DEFAULT_AREA_HECTOR or UPDATED_AREA_HECTOR
        defaultKmMasterShouldBeFound("areaHector.in=" + DEFAULT_AREA_HECTOR + "," + UPDATED_AREA_HECTOR);

        // Get all the kmMasterList where areaHector equals to UPDATED_AREA_HECTOR
        defaultKmMasterShouldNotBeFound("areaHector.in=" + UPDATED_AREA_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHector is not null
        defaultKmMasterShouldBeFound("areaHector.specified=true");

        // Get all the kmMasterList where areaHector is null
        defaultKmMasterShouldNotBeFound("areaHector.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHector contains DEFAULT_AREA_HECTOR
        defaultKmMasterShouldBeFound("areaHector.contains=" + DEFAULT_AREA_HECTOR);

        // Get all the kmMasterList where areaHector contains UPDATED_AREA_HECTOR
        defaultKmMasterShouldNotBeFound("areaHector.contains=" + UPDATED_AREA_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHector does not contain DEFAULT_AREA_HECTOR
        defaultKmMasterShouldNotBeFound("areaHector.doesNotContain=" + DEFAULT_AREA_HECTOR);

        // Get all the kmMasterList where areaHector does not contain UPDATED_AREA_HECTOR
        defaultKmMasterShouldBeFound("areaHector.doesNotContain=" + UPDATED_AREA_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo equals to DEFAULT_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.equals=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo equals to UPDATED_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.equals=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo in DEFAULT_AADHAR_NO or UPDATED_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.in=" + DEFAULT_AADHAR_NO + "," + UPDATED_AADHAR_NO);

        // Get all the kmMasterList where aadharNo equals to UPDATED_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.in=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo is not null
        defaultKmMasterShouldBeFound("aadharNo.specified=true");

        // Get all the kmMasterList where aadharNo is null
        defaultKmMasterShouldNotBeFound("aadharNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo is greater than or equal to DEFAULT_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.greaterThanOrEqual=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo is greater than or equal to UPDATED_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.greaterThanOrEqual=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo is less than or equal to DEFAULT_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.lessThanOrEqual=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo is less than or equal to SMALLER_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.lessThanOrEqual=" + SMALLER_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo is less than DEFAULT_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.lessThan=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo is less than UPDATED_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.lessThan=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo is greater than DEFAULT_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.greaterThan=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo is greater than SMALLER_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.greaterThan=" + SMALLER_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNoMr equals to DEFAULT_AADHAR_NO_MR
        defaultKmMasterShouldBeFound("aadharNoMr.equals=" + DEFAULT_AADHAR_NO_MR);

        // Get all the kmMasterList where aadharNoMr equals to UPDATED_AADHAR_NO_MR
        defaultKmMasterShouldNotBeFound("aadharNoMr.equals=" + UPDATED_AADHAR_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNoMr in DEFAULT_AADHAR_NO_MR or UPDATED_AADHAR_NO_MR
        defaultKmMasterShouldBeFound("aadharNoMr.in=" + DEFAULT_AADHAR_NO_MR + "," + UPDATED_AADHAR_NO_MR);

        // Get all the kmMasterList where aadharNoMr equals to UPDATED_AADHAR_NO_MR
        defaultKmMasterShouldNotBeFound("aadharNoMr.in=" + UPDATED_AADHAR_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNoMr is not null
        defaultKmMasterShouldBeFound("aadharNoMr.specified=true");

        // Get all the kmMasterList where aadharNoMr is null
        defaultKmMasterShouldNotBeFound("aadharNoMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNoMr contains DEFAULT_AADHAR_NO_MR
        defaultKmMasterShouldBeFound("aadharNoMr.contains=" + DEFAULT_AADHAR_NO_MR);

        // Get all the kmMasterList where aadharNoMr contains UPDATED_AADHAR_NO_MR
        defaultKmMasterShouldNotBeFound("aadharNoMr.contains=" + UPDATED_AADHAR_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNoMr does not contain DEFAULT_AADHAR_NO_MR
        defaultKmMasterShouldNotBeFound("aadharNoMr.doesNotContain=" + DEFAULT_AADHAR_NO_MR);

        // Get all the kmMasterList where aadharNoMr does not contain UPDATED_AADHAR_NO_MR
        defaultKmMasterShouldBeFound("aadharNoMr.doesNotContain=" + UPDATED_AADHAR_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo equals to DEFAULT_PAN_NO
        defaultKmMasterShouldBeFound("panNo.equals=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo equals to UPDATED_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.equals=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo in DEFAULT_PAN_NO or UPDATED_PAN_NO
        defaultKmMasterShouldBeFound("panNo.in=" + DEFAULT_PAN_NO + "," + UPDATED_PAN_NO);

        // Get all the kmMasterList where panNo equals to UPDATED_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.in=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo is not null
        defaultKmMasterShouldBeFound("panNo.specified=true");

        // Get all the kmMasterList where panNo is null
        defaultKmMasterShouldNotBeFound("panNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo is greater than or equal to DEFAULT_PAN_NO
        defaultKmMasterShouldBeFound("panNo.greaterThanOrEqual=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo is greater than or equal to UPDATED_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.greaterThanOrEqual=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo is less than or equal to DEFAULT_PAN_NO
        defaultKmMasterShouldBeFound("panNo.lessThanOrEqual=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo is less than or equal to SMALLER_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.lessThanOrEqual=" + SMALLER_PAN_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo is less than DEFAULT_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.lessThan=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo is less than UPDATED_PAN_NO
        defaultKmMasterShouldBeFound("panNo.lessThan=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo is greater than DEFAULT_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.greaterThan=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo is greater than SMALLER_PAN_NO
        defaultKmMasterShouldBeFound("panNo.greaterThan=" + SMALLER_PAN_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNoMr equals to DEFAULT_PAN_NO_MR
        defaultKmMasterShouldBeFound("panNoMr.equals=" + DEFAULT_PAN_NO_MR);

        // Get all the kmMasterList where panNoMr equals to UPDATED_PAN_NO_MR
        defaultKmMasterShouldNotBeFound("panNoMr.equals=" + UPDATED_PAN_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNoMr in DEFAULT_PAN_NO_MR or UPDATED_PAN_NO_MR
        defaultKmMasterShouldBeFound("panNoMr.in=" + DEFAULT_PAN_NO_MR + "," + UPDATED_PAN_NO_MR);

        // Get all the kmMasterList where panNoMr equals to UPDATED_PAN_NO_MR
        defaultKmMasterShouldNotBeFound("panNoMr.in=" + UPDATED_PAN_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNoMr is not null
        defaultKmMasterShouldBeFound("panNoMr.specified=true");

        // Get all the kmMasterList where panNoMr is null
        defaultKmMasterShouldNotBeFound("panNoMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNoMr contains DEFAULT_PAN_NO_MR
        defaultKmMasterShouldBeFound("panNoMr.contains=" + DEFAULT_PAN_NO_MR);

        // Get all the kmMasterList where panNoMr contains UPDATED_PAN_NO_MR
        defaultKmMasterShouldNotBeFound("panNoMr.contains=" + UPDATED_PAN_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNoMr does not contain DEFAULT_PAN_NO_MR
        defaultKmMasterShouldNotBeFound("panNoMr.doesNotContain=" + DEFAULT_PAN_NO_MR);

        // Get all the kmMasterList where panNoMr does not contain UPDATED_PAN_NO_MR
        defaultKmMasterShouldBeFound("panNoMr.doesNotContain=" + UPDATED_PAN_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultKmMasterShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the kmMasterList where mobileNo equals to UPDATED_MOBILE_NO
        defaultKmMasterShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultKmMasterShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the kmMasterList where mobileNo equals to UPDATED_MOBILE_NO
        defaultKmMasterShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNo is not null
        defaultKmMasterShouldBeFound("mobileNo.specified=true");

        // Get all the kmMasterList where mobileNo is null
        defaultKmMasterShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNo contains DEFAULT_MOBILE_NO
        defaultKmMasterShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the kmMasterList where mobileNo contains UPDATED_MOBILE_NO
        defaultKmMasterShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultKmMasterShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the kmMasterList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultKmMasterShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNoMr equals to DEFAULT_MOBILE_NO_MR
        defaultKmMasterShouldBeFound("mobileNoMr.equals=" + DEFAULT_MOBILE_NO_MR);

        // Get all the kmMasterList where mobileNoMr equals to UPDATED_MOBILE_NO_MR
        defaultKmMasterShouldNotBeFound("mobileNoMr.equals=" + UPDATED_MOBILE_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNoMr in DEFAULT_MOBILE_NO_MR or UPDATED_MOBILE_NO_MR
        defaultKmMasterShouldBeFound("mobileNoMr.in=" + DEFAULT_MOBILE_NO_MR + "," + UPDATED_MOBILE_NO_MR);

        // Get all the kmMasterList where mobileNoMr equals to UPDATED_MOBILE_NO_MR
        defaultKmMasterShouldNotBeFound("mobileNoMr.in=" + UPDATED_MOBILE_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNoMr is not null
        defaultKmMasterShouldBeFound("mobileNoMr.specified=true");

        // Get all the kmMasterList where mobileNoMr is null
        defaultKmMasterShouldNotBeFound("mobileNoMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNoMr contains DEFAULT_MOBILE_NO_MR
        defaultKmMasterShouldBeFound("mobileNoMr.contains=" + DEFAULT_MOBILE_NO_MR);

        // Get all the kmMasterList where mobileNoMr contains UPDATED_MOBILE_NO_MR
        defaultKmMasterShouldNotBeFound("mobileNoMr.contains=" + UPDATED_MOBILE_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByMobileNoMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNoMr does not contain DEFAULT_MOBILE_NO_MR
        defaultKmMasterShouldNotBeFound("mobileNoMr.doesNotContain=" + DEFAULT_MOBILE_NO_MR);

        // Get all the kmMasterList where mobileNoMr does not contain UPDATED_MOBILE_NO_MR
        defaultKmMasterShouldBeFound("mobileNoMr.doesNotContain=" + UPDATED_MOBILE_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo equals to DEFAULT_SAVING_NO
        defaultKmMasterShouldBeFound("savingNo.equals=" + DEFAULT_SAVING_NO);

        // Get all the kmMasterList where savingNo equals to UPDATED_SAVING_NO
        defaultKmMasterShouldNotBeFound("savingNo.equals=" + UPDATED_SAVING_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo in DEFAULT_SAVING_NO or UPDATED_SAVING_NO
        defaultKmMasterShouldBeFound("savingNo.in=" + DEFAULT_SAVING_NO + "," + UPDATED_SAVING_NO);

        // Get all the kmMasterList where savingNo equals to UPDATED_SAVING_NO
        defaultKmMasterShouldNotBeFound("savingNo.in=" + UPDATED_SAVING_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo is not null
        defaultKmMasterShouldBeFound("savingNo.specified=true");

        // Get all the kmMasterList where savingNo is null
        defaultKmMasterShouldNotBeFound("savingNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo is greater than or equal to DEFAULT_SAVING_NO
        defaultKmMasterShouldBeFound("savingNo.greaterThanOrEqual=" + DEFAULT_SAVING_NO);

        // Get all the kmMasterList where savingNo is greater than or equal to UPDATED_SAVING_NO
        defaultKmMasterShouldNotBeFound("savingNo.greaterThanOrEqual=" + UPDATED_SAVING_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo is less than or equal to DEFAULT_SAVING_NO
        defaultKmMasterShouldBeFound("savingNo.lessThanOrEqual=" + DEFAULT_SAVING_NO);

        // Get all the kmMasterList where savingNo is less than or equal to SMALLER_SAVING_NO
        defaultKmMasterShouldNotBeFound("savingNo.lessThanOrEqual=" + SMALLER_SAVING_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo is less than DEFAULT_SAVING_NO
        defaultKmMasterShouldNotBeFound("savingNo.lessThan=" + DEFAULT_SAVING_NO);

        // Get all the kmMasterList where savingNo is less than UPDATED_SAVING_NO
        defaultKmMasterShouldBeFound("savingNo.lessThan=" + UPDATED_SAVING_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo is greater than DEFAULT_SAVING_NO
        defaultKmMasterShouldNotBeFound("savingNo.greaterThan=" + DEFAULT_SAVING_NO);

        // Get all the kmMasterList where savingNo is greater than SMALLER_SAVING_NO
        defaultKmMasterShouldBeFound("savingNo.greaterThan=" + SMALLER_SAVING_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNoMr equals to DEFAULT_SAVING_NO_MR
        defaultKmMasterShouldBeFound("savingNoMr.equals=" + DEFAULT_SAVING_NO_MR);

        // Get all the kmMasterList where savingNoMr equals to UPDATED_SAVING_NO_MR
        defaultKmMasterShouldNotBeFound("savingNoMr.equals=" + UPDATED_SAVING_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNoMr in DEFAULT_SAVING_NO_MR or UPDATED_SAVING_NO_MR
        defaultKmMasterShouldBeFound("savingNoMr.in=" + DEFAULT_SAVING_NO_MR + "," + UPDATED_SAVING_NO_MR);

        // Get all the kmMasterList where savingNoMr equals to UPDATED_SAVING_NO_MR
        defaultKmMasterShouldNotBeFound("savingNoMr.in=" + UPDATED_SAVING_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNoMr is not null
        defaultKmMasterShouldBeFound("savingNoMr.specified=true");

        // Get all the kmMasterList where savingNoMr is null
        defaultKmMasterShouldNotBeFound("savingNoMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNoMr contains DEFAULT_SAVING_NO_MR
        defaultKmMasterShouldBeFound("savingNoMr.contains=" + DEFAULT_SAVING_NO_MR);

        // Get all the kmMasterList where savingNoMr contains UPDATED_SAVING_NO_MR
        defaultKmMasterShouldNotBeFound("savingNoMr.contains=" + UPDATED_SAVING_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingNoMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNoMr does not contain DEFAULT_SAVING_NO_MR
        defaultKmMasterShouldNotBeFound("savingNoMr.doesNotContain=" + DEFAULT_SAVING_NO_MR);

        // Get all the kmMasterList where savingNoMr does not contain UPDATED_SAVING_NO_MR
        defaultKmMasterShouldBeFound("savingNoMr.doesNotContain=" + UPDATED_SAVING_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsMemberCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCode equals to DEFAULT_PACS_MEMBER_CODE
        defaultKmMasterShouldBeFound("pacsMemberCode.equals=" + DEFAULT_PACS_MEMBER_CODE);

        // Get all the kmMasterList where pacsMemberCode equals to UPDATED_PACS_MEMBER_CODE
        defaultKmMasterShouldNotBeFound("pacsMemberCode.equals=" + UPDATED_PACS_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsMemberCodeIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCode in DEFAULT_PACS_MEMBER_CODE or UPDATED_PACS_MEMBER_CODE
        defaultKmMasterShouldBeFound("pacsMemberCode.in=" + DEFAULT_PACS_MEMBER_CODE + "," + UPDATED_PACS_MEMBER_CODE);

        // Get all the kmMasterList where pacsMemberCode equals to UPDATED_PACS_MEMBER_CODE
        defaultKmMasterShouldNotBeFound("pacsMemberCode.in=" + UPDATED_PACS_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsMemberCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCode is not null
        defaultKmMasterShouldBeFound("pacsMemberCode.specified=true");

        // Get all the kmMasterList where pacsMemberCode is null
        defaultKmMasterShouldNotBeFound("pacsMemberCode.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsMemberCodeContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCode contains DEFAULT_PACS_MEMBER_CODE
        defaultKmMasterShouldBeFound("pacsMemberCode.contains=" + DEFAULT_PACS_MEMBER_CODE);

        // Get all the kmMasterList where pacsMemberCode contains UPDATED_PACS_MEMBER_CODE
        defaultKmMasterShouldNotBeFound("pacsMemberCode.contains=" + UPDATED_PACS_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsMemberCodeNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCode does not contain DEFAULT_PACS_MEMBER_CODE
        defaultKmMasterShouldNotBeFound("pacsMemberCode.doesNotContain=" + DEFAULT_PACS_MEMBER_CODE);

        // Get all the kmMasterList where pacsMemberCode does not contain UPDATED_PACS_MEMBER_CODE
        defaultKmMasterShouldBeFound("pacsMemberCode.doesNotContain=" + UPDATED_PACS_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKmMastersByEntryFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where entryFlag equals to DEFAULT_ENTRY_FLAG
        defaultKmMasterShouldBeFound("entryFlag.equals=" + DEFAULT_ENTRY_FLAG);

        // Get all the kmMasterList where entryFlag equals to UPDATED_ENTRY_FLAG
        defaultKmMasterShouldNotBeFound("entryFlag.equals=" + UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void getAllKmMastersByEntryFlagIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where entryFlag in DEFAULT_ENTRY_FLAG or UPDATED_ENTRY_FLAG
        defaultKmMasterShouldBeFound("entryFlag.in=" + DEFAULT_ENTRY_FLAG + "," + UPDATED_ENTRY_FLAG);

        // Get all the kmMasterList where entryFlag equals to UPDATED_ENTRY_FLAG
        defaultKmMasterShouldNotBeFound("entryFlag.in=" + UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void getAllKmMastersByEntryFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where entryFlag is not null
        defaultKmMasterShouldBeFound("entryFlag.specified=true");

        // Get all the kmMasterList where entryFlag is null
        defaultKmMasterShouldNotBeFound("entryFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByEntryFlagContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where entryFlag contains DEFAULT_ENTRY_FLAG
        defaultKmMasterShouldBeFound("entryFlag.contains=" + DEFAULT_ENTRY_FLAG);

        // Get all the kmMasterList where entryFlag contains UPDATED_ENTRY_FLAG
        defaultKmMasterShouldNotBeFound("entryFlag.contains=" + UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void getAllKmMastersByEntryFlagNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where entryFlag does not contain DEFAULT_ENTRY_FLAG
        defaultKmMasterShouldNotBeFound("entryFlag.doesNotContain=" + DEFAULT_ENTRY_FLAG);

        // Get all the kmMasterList where entryFlag does not contain UPDATED_ENTRY_FLAG
        defaultKmMasterShouldBeFound("entryFlag.doesNotContain=" + UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void getAllKmMastersByFarmerTypeMasterIsEqualToSomething() throws Exception {
        FarmerTypeMaster farmerTypeMaster;
        if (TestUtil.findAll(em, FarmerTypeMaster.class).isEmpty()) {
            kmMasterRepository.saveAndFlush(kmMaster);
            farmerTypeMaster = FarmerTypeMasterResourceIT.createEntity(em);
        } else {
            farmerTypeMaster = TestUtil.findAll(em, FarmerTypeMaster.class).get(0);
        }
        em.persist(farmerTypeMaster);
        em.flush();
        kmMaster.setFarmerTypeMaster(farmerTypeMaster);
        kmMasterRepository.saveAndFlush(kmMaster);
        Long farmerTypeMasterId = farmerTypeMaster.getId();
        // Get all the kmMasterList where farmerTypeMaster equals to farmerTypeMasterId
        defaultKmMasterShouldBeFound("farmerTypeMasterId.equals=" + farmerTypeMasterId);

        // Get all the kmMasterList where farmerTypeMaster equals to (farmerTypeMasterId + 1)
        defaultKmMasterShouldNotBeFound("farmerTypeMasterId.equals=" + (farmerTypeMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKmMasterShouldBeFound(String filter) throws Exception {
        restKmMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)))
            .andExpect(jsonPath("$.[*].farmerNameMr").value(hasItem(DEFAULT_FARMER_NAME_MR)))
            .andExpect(jsonPath("$.[*].farmerAddress").value(hasItem(DEFAULT_FARMER_ADDRESS)))
            .andExpect(jsonPath("$.[*].farmerAddressMr").value(hasItem(DEFAULT_FARMER_ADDRESS_MR)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].cast").value(hasItem(DEFAULT_CAST)))
            .andExpect(jsonPath("$.[*].castMr").value(hasItem(DEFAULT_CAST_MR)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].areaHector").value(hasItem(DEFAULT_AREA_HECTOR)))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO.intValue())))
            .andExpect(jsonPath("$.[*].aadharNoMr").value(hasItem(DEFAULT_AADHAR_NO_MR)))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO.intValue())))
            .andExpect(jsonPath("$.[*].panNoMr").value(hasItem(DEFAULT_PAN_NO_MR)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].mobileNoMr").value(hasItem(DEFAULT_MOBILE_NO_MR)))
            .andExpect(jsonPath("$.[*].savingNo").value(hasItem(DEFAULT_SAVING_NO.intValue())))
            .andExpect(jsonPath("$.[*].savingNoMr").value(hasItem(DEFAULT_SAVING_NO_MR)))
            .andExpect(jsonPath("$.[*].pacsMemberCode").value(hasItem(DEFAULT_PACS_MEMBER_CODE)))
            .andExpect(jsonPath("$.[*].entryFlag").value(hasItem(DEFAULT_ENTRY_FLAG)));

        // Check, that the count call also returns 1
        restKmMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKmMasterShouldNotBeFound(String filter) throws Exception {
        restKmMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKmMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKmMaster() throws Exception {
        // Get the kmMaster
        restKmMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKmMaster() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();

        // Update the kmMaster
        KmMaster updatedKmMaster = kmMasterRepository.findById(kmMaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKmMaster are not directly saved in db
        em.detach(updatedKmMaster);
        updatedKmMaster
            .branchCode(UPDATED_BRANCH_CODE)
            .farmerName(UPDATED_FARMER_NAME)
            .farmerNameMr(UPDATED_FARMER_NAME_MR)
            .farmerAddress(UPDATED_FARMER_ADDRESS)
            .farmerAddressMr(UPDATED_FARMER_ADDRESS_MR)
            .gender(UPDATED_GENDER)
            .cast(UPDATED_CAST)
            .castMr(UPDATED_CAST_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .areaHector(UPDATED_AREA_HECTOR)
            .aadharNo(UPDATED_AADHAR_NO)
            .aadharNoMr(UPDATED_AADHAR_NO_MR)
            .panNo(UPDATED_PAN_NO)
            .panNoMr(UPDATED_PAN_NO_MR)
            .mobileNo(UPDATED_MOBILE_NO)
            .mobileNoMr(UPDATED_MOBILE_NO_MR)
            .savingNo(UPDATED_SAVING_NO)
            .savingNoMr(UPDATED_SAVING_NO_MR)
            .pacsMemberCode(UPDATED_PACS_MEMBER_CODE)
            .entryFlag(UPDATED_ENTRY_FLAG);

        restKmMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKmMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKmMaster))
            )
            .andExpect(status().isOk());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate);
        KmMaster testKmMaster = kmMasterList.get(kmMasterList.size() - 1);
        assertThat(testKmMaster.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testKmMaster.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testKmMaster.getFarmerNameMr()).isEqualTo(UPDATED_FARMER_NAME_MR);
        assertThat(testKmMaster.getFarmerAddress()).isEqualTo(UPDATED_FARMER_ADDRESS);
        assertThat(testKmMaster.getFarmerAddressMr()).isEqualTo(UPDATED_FARMER_ADDRESS_MR);
        assertThat(testKmMaster.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testKmMaster.getCast()).isEqualTo(UPDATED_CAST);
        assertThat(testKmMaster.getCastMr()).isEqualTo(UPDATED_CAST_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHector()).isEqualTo(UPDATED_AREA_HECTOR);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(UPDATED_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(UPDATED_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(UPDATED_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(UPDATED_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(UPDATED_MOBILE_NO_MR);
        assertThat(testKmMaster.getSavingNo()).isEqualTo(UPDATED_SAVING_NO);
        assertThat(testKmMaster.getSavingNoMr()).isEqualTo(UPDATED_SAVING_NO_MR);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(UPDATED_PACS_MEMBER_CODE);
        assertThat(testKmMaster.getEntryFlag()).isEqualTo(UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void putNonExistingKmMaster() throws Exception {
        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();
        kmMaster.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kmMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKmMaster() throws Exception {
        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();
        kmMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKmMaster() throws Exception {
        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();
        kmMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKmMasterWithPatch() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();

        // Update the kmMaster using partial update
        KmMaster partialUpdatedKmMaster = new KmMaster();
        partialUpdatedKmMaster.setId(kmMaster.getId());

        partialUpdatedKmMaster
            .branchCode(UPDATED_BRANCH_CODE)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .areaHector(UPDATED_AREA_HECTOR)
            .aadharNoMr(UPDATED_AADHAR_NO_MR)
            .panNo(UPDATED_PAN_NO)
            .panNoMr(UPDATED_PAN_NO_MR)
            .mobileNo(UPDATED_MOBILE_NO)
            .mobileNoMr(UPDATED_MOBILE_NO_MR)
            .savingNo(UPDATED_SAVING_NO)
            .savingNoMr(UPDATED_SAVING_NO_MR)
            .entryFlag(UPDATED_ENTRY_FLAG);

        restKmMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmMaster))
            )
            .andExpect(status().isOk());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate);
        KmMaster testKmMaster = kmMasterList.get(kmMasterList.size() - 1);
        assertThat(testKmMaster.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testKmMaster.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testKmMaster.getFarmerNameMr()).isEqualTo(DEFAULT_FARMER_NAME_MR);
        assertThat(testKmMaster.getFarmerAddress()).isEqualTo(DEFAULT_FARMER_ADDRESS);
        assertThat(testKmMaster.getFarmerAddressMr()).isEqualTo(DEFAULT_FARMER_ADDRESS_MR);
        assertThat(testKmMaster.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testKmMaster.getCast()).isEqualTo(DEFAULT_CAST);
        assertThat(testKmMaster.getCastMr()).isEqualTo(DEFAULT_CAST_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHector()).isEqualTo(UPDATED_AREA_HECTOR);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(DEFAULT_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(UPDATED_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(UPDATED_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(UPDATED_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(UPDATED_MOBILE_NO_MR);
        assertThat(testKmMaster.getSavingNo()).isEqualTo(UPDATED_SAVING_NO);
        assertThat(testKmMaster.getSavingNoMr()).isEqualTo(UPDATED_SAVING_NO_MR);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(DEFAULT_PACS_MEMBER_CODE);
        assertThat(testKmMaster.getEntryFlag()).isEqualTo(UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void fullUpdateKmMasterWithPatch() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();

        // Update the kmMaster using partial update
        KmMaster partialUpdatedKmMaster = new KmMaster();
        partialUpdatedKmMaster.setId(kmMaster.getId());

        partialUpdatedKmMaster
            .branchCode(UPDATED_BRANCH_CODE)
            .farmerName(UPDATED_FARMER_NAME)
            .farmerNameMr(UPDATED_FARMER_NAME_MR)
            .farmerAddress(UPDATED_FARMER_ADDRESS)
            .farmerAddressMr(UPDATED_FARMER_ADDRESS_MR)
            .gender(UPDATED_GENDER)
            .cast(UPDATED_CAST)
            .castMr(UPDATED_CAST_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .areaHector(UPDATED_AREA_HECTOR)
            .aadharNo(UPDATED_AADHAR_NO)
            .aadharNoMr(UPDATED_AADHAR_NO_MR)
            .panNo(UPDATED_PAN_NO)
            .panNoMr(UPDATED_PAN_NO_MR)
            .mobileNo(UPDATED_MOBILE_NO)
            .mobileNoMr(UPDATED_MOBILE_NO_MR)
            .savingNo(UPDATED_SAVING_NO)
            .savingNoMr(UPDATED_SAVING_NO_MR)
            .pacsMemberCode(UPDATED_PACS_MEMBER_CODE)
            .entryFlag(UPDATED_ENTRY_FLAG);

        restKmMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmMaster))
            )
            .andExpect(status().isOk());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate);
        KmMaster testKmMaster = kmMasterList.get(kmMasterList.size() - 1);
        assertThat(testKmMaster.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testKmMaster.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testKmMaster.getFarmerNameMr()).isEqualTo(UPDATED_FARMER_NAME_MR);
        assertThat(testKmMaster.getFarmerAddress()).isEqualTo(UPDATED_FARMER_ADDRESS);
        assertThat(testKmMaster.getFarmerAddressMr()).isEqualTo(UPDATED_FARMER_ADDRESS_MR);
        assertThat(testKmMaster.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testKmMaster.getCast()).isEqualTo(UPDATED_CAST);
        assertThat(testKmMaster.getCastMr()).isEqualTo(UPDATED_CAST_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHector()).isEqualTo(UPDATED_AREA_HECTOR);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(UPDATED_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(UPDATED_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(UPDATED_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(UPDATED_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(UPDATED_MOBILE_NO_MR);
        assertThat(testKmMaster.getSavingNo()).isEqualTo(UPDATED_SAVING_NO);
        assertThat(testKmMaster.getSavingNoMr()).isEqualTo(UPDATED_SAVING_NO_MR);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(UPDATED_PACS_MEMBER_CODE);
        assertThat(testKmMaster.getEntryFlag()).isEqualTo(UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void patchNonExistingKmMaster() throws Exception {
        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();
        kmMaster.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kmMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKmMaster() throws Exception {
        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();
        kmMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKmMaster() throws Exception {
        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();
        kmMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMasterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKmMaster() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        int databaseSizeBeforeDelete = kmMasterRepository.findAll().size();

        // Delete the kmMaster
        restKmMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, kmMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
