package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.KmMasterRepository;
import com.cbs.middleware.service.criteria.KmMasterCriteria;
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
 * Integration tests for the {@link KmMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KmMasterResourceIT {

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_CODE_MR = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE_MR = "BBBBBBBBBB";

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

    private static final String DEFAULT_GENDER_MR = "AAAAAAAAAA";
    private static final String UPDATED_GENDER_MR = "BBBBBBBBBB";

    private static final String DEFAULT_CASTE = "AAAAAAAAAA";
    private static final String UPDATED_CASTE = "BBBBBBBBBB";

    private static final String DEFAULT_CASTE_MR = "AAAAAAAAAA";
    private static final String UPDATED_CASTE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_AREA_HECTOR = 1D;
    private static final Double UPDATED_AREA_HECTOR = 2D;
    private static final Double SMALLER_AREA_HECTOR = 1D - 1D;

    private static final String DEFAULT_AREA_HECTOR_MR = "AAAAAAAAAA";
    private static final String UPDATED_AREA_HECTOR_MR = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_NO = "dddddddddddd";
    private static final String UPDATED_AADHAR_NO = "ddddddddddddB";

    private static final String DEFAULT_AADHAR_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_NO_MR = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_NO = "ZZMDB4295L";
    private static final String UPDATED_PAN_NO = "FVWHD4470N";

    private static final String DEFAULT_PAN_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NO_MR = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO_MR = "BBBBBBBBBB";

    private static final String DEFAULT_KCC_NO = "AAAAAAAAAA";
    private static final String UPDATED_KCC_NO = "BBBBBBBBBB";

    private static final String DEFAULT_KCC_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_KCC_NO_MR = "BBBBBBBBBB";

    private static final Long DEFAULT_SAVING_AC_NO = 1L;
    private static final Long UPDATED_SAVING_AC_NO = 2L;
    private static final Long SMALLER_SAVING_AC_NO = 1L - 1L;

    private static final String DEFAULT_SAVING_AC_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_SAVING_AC_NO_MR = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_MEMBER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PACS_MEMBER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_MEMBER_CODE_MR = "AAAAAAAAAA";
    private static final String UPDATED_PACS_MEMBER_CODE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRY_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_ENTRY_FLAG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/km-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

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
            .branchCodeMr(DEFAULT_BRANCH_CODE_MR)
            .farmerName(DEFAULT_FARMER_NAME)
            .farmerNameMr(DEFAULT_FARMER_NAME_MR)
            .farmerAddress(DEFAULT_FARMER_ADDRESS)
            .farmerAddressMr(DEFAULT_FARMER_ADDRESS_MR)
            .gender(DEFAULT_GENDER)
            .genderMr(DEFAULT_GENDER_MR)
            .caste(DEFAULT_CASTE)
            .casteMr(DEFAULT_CASTE_MR)
            .pacsNumber(DEFAULT_PACS_NUMBER)
            .areaHector(DEFAULT_AREA_HECTOR)
            .areaHectorMr(DEFAULT_AREA_HECTOR_MR)
            .aadharNo(DEFAULT_AADHAR_NO)
            .aadharNoMr(DEFAULT_AADHAR_NO_MR)
            .panNo(DEFAULT_PAN_NO)
            .panNoMr(DEFAULT_PAN_NO_MR)
            .mobileNo(DEFAULT_MOBILE_NO)
            .mobileNoMr(DEFAULT_MOBILE_NO_MR)
            .kccNo(DEFAULT_KCC_NO)
            .kccNoMr(DEFAULT_KCC_NO_MR)
            .savingAcNo(DEFAULT_SAVING_AC_NO)
            .savingAcNoMr(DEFAULT_SAVING_AC_NO_MR)
            .pacsMemberCode(DEFAULT_PACS_MEMBER_CODE)
            .pacsMemberCodeMr(DEFAULT_PACS_MEMBER_CODE_MR)
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
            .branchCodeMr(UPDATED_BRANCH_CODE_MR)
            .farmerName(UPDATED_FARMER_NAME)
            .farmerNameMr(UPDATED_FARMER_NAME_MR)
            .farmerAddress(UPDATED_FARMER_ADDRESS)
            .farmerAddressMr(UPDATED_FARMER_ADDRESS_MR)
            .gender(UPDATED_GENDER)
            .genderMr(UPDATED_GENDER_MR)
            .caste(UPDATED_CASTE)
            .casteMr(UPDATED_CASTE_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .areaHector(UPDATED_AREA_HECTOR)
            .areaHectorMr(UPDATED_AREA_HECTOR_MR)
            .aadharNo(UPDATED_AADHAR_NO)
            .aadharNoMr(UPDATED_AADHAR_NO_MR)
            .panNo(UPDATED_PAN_NO)
            .panNoMr(UPDATED_PAN_NO_MR)
            .mobileNo(UPDATED_MOBILE_NO)
            .mobileNoMr(UPDATED_MOBILE_NO_MR)
            .kccNo(UPDATED_KCC_NO)
            .kccNoMr(UPDATED_KCC_NO_MR)
            .savingAcNo(UPDATED_SAVING_AC_NO)
            .savingAcNoMr(UPDATED_SAVING_AC_NO_MR)
            .pacsMemberCode(UPDATED_PACS_MEMBER_CODE)
            .pacsMemberCodeMr(UPDATED_PACS_MEMBER_CODE_MR)
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
        assertThat(testKmMaster.getBranchCodeMr()).isEqualTo(DEFAULT_BRANCH_CODE_MR);
        assertThat(testKmMaster.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testKmMaster.getFarmerNameMr()).isEqualTo(DEFAULT_FARMER_NAME_MR);
        assertThat(testKmMaster.getFarmerAddress()).isEqualTo(DEFAULT_FARMER_ADDRESS);
        assertThat(testKmMaster.getFarmerAddressMr()).isEqualTo(DEFAULT_FARMER_ADDRESS_MR);
        assertThat(testKmMaster.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testKmMaster.getGenderMr()).isEqualTo(DEFAULT_GENDER_MR);
        assertThat(testKmMaster.getCaste()).isEqualTo(DEFAULT_CASTE);
        assertThat(testKmMaster.getCasteMr()).isEqualTo(DEFAULT_CASTE_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHector()).isEqualTo(DEFAULT_AREA_HECTOR);
        assertThat(testKmMaster.getAreaHectorMr()).isEqualTo(DEFAULT_AREA_HECTOR_MR);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(DEFAULT_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(DEFAULT_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(DEFAULT_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(DEFAULT_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(DEFAULT_MOBILE_NO_MR);
        assertThat(testKmMaster.getKccNo()).isEqualTo(DEFAULT_KCC_NO);
        assertThat(testKmMaster.getKccNoMr()).isEqualTo(DEFAULT_KCC_NO_MR);
        assertThat(testKmMaster.getSavingAcNo()).isEqualTo(DEFAULT_SAVING_AC_NO);
        assertThat(testKmMaster.getSavingAcNoMr()).isEqualTo(DEFAULT_SAVING_AC_NO_MR);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(DEFAULT_PACS_MEMBER_CODE);
        assertThat(testKmMaster.getPacsMemberCodeMr()).isEqualTo(DEFAULT_PACS_MEMBER_CODE_MR);
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
    void checkBranchCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setBranchCode(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFarmerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setFarmerName(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFarmerAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setFarmerAddress(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setGender(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCasteIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setCaste(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAreaHectorIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setAreaHector(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAadharNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setAadharNo(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPanNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setPanNo(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setMobileNo(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKccNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setKccNo(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSavingAcNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setSavingAcNo(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPacsMemberCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kmMasterRepository.findAll().size();
        // set the field null
        kmMaster.setPacsMemberCode(null);

        // Create the KmMaster, which fails.

        restKmMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].branchCodeMr").value(hasItem(DEFAULT_BRANCH_CODE_MR)))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)))
            .andExpect(jsonPath("$.[*].farmerNameMr").value(hasItem(DEFAULT_FARMER_NAME_MR)))
            .andExpect(jsonPath("$.[*].farmerAddress").value(hasItem(DEFAULT_FARMER_ADDRESS)))
            .andExpect(jsonPath("$.[*].farmerAddressMr").value(hasItem(DEFAULT_FARMER_ADDRESS_MR)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].genderMr").value(hasItem(DEFAULT_GENDER_MR)))
            .andExpect(jsonPath("$.[*].caste").value(hasItem(DEFAULT_CASTE)))
            .andExpect(jsonPath("$.[*].casteMr").value(hasItem(DEFAULT_CASTE_MR)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].areaHector").value(hasItem(DEFAULT_AREA_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].areaHectorMr").value(hasItem(DEFAULT_AREA_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO)))
            .andExpect(jsonPath("$.[*].aadharNoMr").value(hasItem(DEFAULT_AADHAR_NO_MR)))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO)))
            .andExpect(jsonPath("$.[*].panNoMr").value(hasItem(DEFAULT_PAN_NO_MR)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].mobileNoMr").value(hasItem(DEFAULT_MOBILE_NO_MR)))
            .andExpect(jsonPath("$.[*].kccNo").value(hasItem(DEFAULT_KCC_NO)))
            .andExpect(jsonPath("$.[*].kccNoMr").value(hasItem(DEFAULT_KCC_NO_MR)))
            .andExpect(jsonPath("$.[*].savingAcNo").value(hasItem(DEFAULT_SAVING_AC_NO.intValue())))
            .andExpect(jsonPath("$.[*].savingAcNoMr").value(hasItem(DEFAULT_SAVING_AC_NO_MR)))
            .andExpect(jsonPath("$.[*].pacsMemberCode").value(hasItem(DEFAULT_PACS_MEMBER_CODE)))
            .andExpect(jsonPath("$.[*].pacsMemberCodeMr").value(hasItem(DEFAULT_PACS_MEMBER_CODE_MR)))
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
            .andExpect(jsonPath("$.branchCodeMr").value(DEFAULT_BRANCH_CODE_MR))
            .andExpect(jsonPath("$.farmerName").value(DEFAULT_FARMER_NAME))
            .andExpect(jsonPath("$.farmerNameMr").value(DEFAULT_FARMER_NAME_MR))
            .andExpect(jsonPath("$.farmerAddress").value(DEFAULT_FARMER_ADDRESS))
            .andExpect(jsonPath("$.farmerAddressMr").value(DEFAULT_FARMER_ADDRESS_MR))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.genderMr").value(DEFAULT_GENDER_MR))
            .andExpect(jsonPath("$.caste").value(DEFAULT_CASTE))
            .andExpect(jsonPath("$.casteMr").value(DEFAULT_CASTE_MR))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER))
            .andExpect(jsonPath("$.areaHector").value(DEFAULT_AREA_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.areaHectorMr").value(DEFAULT_AREA_HECTOR_MR))
            .andExpect(jsonPath("$.aadharNo").value(DEFAULT_AADHAR_NO))
            .andExpect(jsonPath("$.aadharNoMr").value(DEFAULT_AADHAR_NO_MR))
            .andExpect(jsonPath("$.panNo").value(DEFAULT_PAN_NO))
            .andExpect(jsonPath("$.panNoMr").value(DEFAULT_PAN_NO_MR))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.mobileNoMr").value(DEFAULT_MOBILE_NO_MR))
            .andExpect(jsonPath("$.kccNo").value(DEFAULT_KCC_NO))
            .andExpect(jsonPath("$.kccNoMr").value(DEFAULT_KCC_NO_MR))
            .andExpect(jsonPath("$.savingAcNo").value(DEFAULT_SAVING_AC_NO.intValue()))
            .andExpect(jsonPath("$.savingAcNoMr").value(DEFAULT_SAVING_AC_NO_MR))
            .andExpect(jsonPath("$.pacsMemberCode").value(DEFAULT_PACS_MEMBER_CODE))
            .andExpect(jsonPath("$.pacsMemberCodeMr").value(DEFAULT_PACS_MEMBER_CODE_MR))
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
    void getAllKmMastersByBranchCodeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCodeMr equals to DEFAULT_BRANCH_CODE_MR
        defaultKmMasterShouldBeFound("branchCodeMr.equals=" + DEFAULT_BRANCH_CODE_MR);

        // Get all the kmMasterList where branchCodeMr equals to UPDATED_BRANCH_CODE_MR
        defaultKmMasterShouldNotBeFound("branchCodeMr.equals=" + UPDATED_BRANCH_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByBranchCodeMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCodeMr in DEFAULT_BRANCH_CODE_MR or UPDATED_BRANCH_CODE_MR
        defaultKmMasterShouldBeFound("branchCodeMr.in=" + DEFAULT_BRANCH_CODE_MR + "," + UPDATED_BRANCH_CODE_MR);

        // Get all the kmMasterList where branchCodeMr equals to UPDATED_BRANCH_CODE_MR
        defaultKmMasterShouldNotBeFound("branchCodeMr.in=" + UPDATED_BRANCH_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByBranchCodeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCodeMr is not null
        defaultKmMasterShouldBeFound("branchCodeMr.specified=true");

        // Get all the kmMasterList where branchCodeMr is null
        defaultKmMasterShouldNotBeFound("branchCodeMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByBranchCodeMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCodeMr contains DEFAULT_BRANCH_CODE_MR
        defaultKmMasterShouldBeFound("branchCodeMr.contains=" + DEFAULT_BRANCH_CODE_MR);

        // Get all the kmMasterList where branchCodeMr contains UPDATED_BRANCH_CODE_MR
        defaultKmMasterShouldNotBeFound("branchCodeMr.contains=" + UPDATED_BRANCH_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByBranchCodeMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCodeMr does not contain DEFAULT_BRANCH_CODE_MR
        defaultKmMasterShouldNotBeFound("branchCodeMr.doesNotContain=" + DEFAULT_BRANCH_CODE_MR);

        // Get all the kmMasterList where branchCodeMr does not contain UPDATED_BRANCH_CODE_MR
        defaultKmMasterShouldBeFound("branchCodeMr.doesNotContain=" + UPDATED_BRANCH_CODE_MR);
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
    void getAllKmMastersByGenderMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where genderMr equals to DEFAULT_GENDER_MR
        defaultKmMasterShouldBeFound("genderMr.equals=" + DEFAULT_GENDER_MR);

        // Get all the kmMasterList where genderMr equals to UPDATED_GENDER_MR
        defaultKmMasterShouldNotBeFound("genderMr.equals=" + UPDATED_GENDER_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByGenderMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where genderMr in DEFAULT_GENDER_MR or UPDATED_GENDER_MR
        defaultKmMasterShouldBeFound("genderMr.in=" + DEFAULT_GENDER_MR + "," + UPDATED_GENDER_MR);

        // Get all the kmMasterList where genderMr equals to UPDATED_GENDER_MR
        defaultKmMasterShouldNotBeFound("genderMr.in=" + UPDATED_GENDER_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByGenderMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where genderMr is not null
        defaultKmMasterShouldBeFound("genderMr.specified=true");

        // Get all the kmMasterList where genderMr is null
        defaultKmMasterShouldNotBeFound("genderMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByGenderMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where genderMr contains DEFAULT_GENDER_MR
        defaultKmMasterShouldBeFound("genderMr.contains=" + DEFAULT_GENDER_MR);

        // Get all the kmMasterList where genderMr contains UPDATED_GENDER_MR
        defaultKmMasterShouldNotBeFound("genderMr.contains=" + UPDATED_GENDER_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByGenderMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where genderMr does not contain DEFAULT_GENDER_MR
        defaultKmMasterShouldNotBeFound("genderMr.doesNotContain=" + DEFAULT_GENDER_MR);

        // Get all the kmMasterList where genderMr does not contain UPDATED_GENDER_MR
        defaultKmMasterShouldBeFound("genderMr.doesNotContain=" + UPDATED_GENDER_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where caste equals to DEFAULT_CASTE
        defaultKmMasterShouldBeFound("caste.equals=" + DEFAULT_CASTE);

        // Get all the kmMasterList where caste equals to UPDATED_CASTE
        defaultKmMasterShouldNotBeFound("caste.equals=" + UPDATED_CASTE);
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where caste in DEFAULT_CASTE or UPDATED_CASTE
        defaultKmMasterShouldBeFound("caste.in=" + DEFAULT_CASTE + "," + UPDATED_CASTE);

        // Get all the kmMasterList where caste equals to UPDATED_CASTE
        defaultKmMasterShouldNotBeFound("caste.in=" + UPDATED_CASTE);
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where caste is not null
        defaultKmMasterShouldBeFound("caste.specified=true");

        // Get all the kmMasterList where caste is null
        defaultKmMasterShouldNotBeFound("caste.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where caste contains DEFAULT_CASTE
        defaultKmMasterShouldBeFound("caste.contains=" + DEFAULT_CASTE);

        // Get all the kmMasterList where caste contains UPDATED_CASTE
        defaultKmMasterShouldNotBeFound("caste.contains=" + UPDATED_CASTE);
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where caste does not contain DEFAULT_CASTE
        defaultKmMasterShouldNotBeFound("caste.doesNotContain=" + DEFAULT_CASTE);

        // Get all the kmMasterList where caste does not contain UPDATED_CASTE
        defaultKmMasterShouldBeFound("caste.doesNotContain=" + UPDATED_CASTE);
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where casteMr equals to DEFAULT_CASTE_MR
        defaultKmMasterShouldBeFound("casteMr.equals=" + DEFAULT_CASTE_MR);

        // Get all the kmMasterList where casteMr equals to UPDATED_CASTE_MR
        defaultKmMasterShouldNotBeFound("casteMr.equals=" + UPDATED_CASTE_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where casteMr in DEFAULT_CASTE_MR or UPDATED_CASTE_MR
        defaultKmMasterShouldBeFound("casteMr.in=" + DEFAULT_CASTE_MR + "," + UPDATED_CASTE_MR);

        // Get all the kmMasterList where casteMr equals to UPDATED_CASTE_MR
        defaultKmMasterShouldNotBeFound("casteMr.in=" + UPDATED_CASTE_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where casteMr is not null
        defaultKmMasterShouldBeFound("casteMr.specified=true");

        // Get all the kmMasterList where casteMr is null
        defaultKmMasterShouldNotBeFound("casteMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where casteMr contains DEFAULT_CASTE_MR
        defaultKmMasterShouldBeFound("casteMr.contains=" + DEFAULT_CASTE_MR);

        // Get all the kmMasterList where casteMr contains UPDATED_CASTE_MR
        defaultKmMasterShouldNotBeFound("casteMr.contains=" + UPDATED_CASTE_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByCasteMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where casteMr does not contain DEFAULT_CASTE_MR
        defaultKmMasterShouldNotBeFound("casteMr.doesNotContain=" + DEFAULT_CASTE_MR);

        // Get all the kmMasterList where casteMr does not contain UPDATED_CASTE_MR
        defaultKmMasterShouldBeFound("casteMr.doesNotContain=" + UPDATED_CASTE_MR);
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
    void getAllKmMastersByAreaHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHector is greater than or equal to DEFAULT_AREA_HECTOR
        defaultKmMasterShouldBeFound("areaHector.greaterThanOrEqual=" + DEFAULT_AREA_HECTOR);

        // Get all the kmMasterList where areaHector is greater than or equal to UPDATED_AREA_HECTOR
        defaultKmMasterShouldNotBeFound("areaHector.greaterThanOrEqual=" + UPDATED_AREA_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHector is less than or equal to DEFAULT_AREA_HECTOR
        defaultKmMasterShouldBeFound("areaHector.lessThanOrEqual=" + DEFAULT_AREA_HECTOR);

        // Get all the kmMasterList where areaHector is less than or equal to SMALLER_AREA_HECTOR
        defaultKmMasterShouldNotBeFound("areaHector.lessThanOrEqual=" + SMALLER_AREA_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHector is less than DEFAULT_AREA_HECTOR
        defaultKmMasterShouldNotBeFound("areaHector.lessThan=" + DEFAULT_AREA_HECTOR);

        // Get all the kmMasterList where areaHector is less than UPDATED_AREA_HECTOR
        defaultKmMasterShouldBeFound("areaHector.lessThan=" + UPDATED_AREA_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHector is greater than DEFAULT_AREA_HECTOR
        defaultKmMasterShouldNotBeFound("areaHector.greaterThan=" + DEFAULT_AREA_HECTOR);

        // Get all the kmMasterList where areaHector is greater than SMALLER_AREA_HECTOR
        defaultKmMasterShouldBeFound("areaHector.greaterThan=" + SMALLER_AREA_HECTOR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHectorMr equals to DEFAULT_AREA_HECTOR_MR
        defaultKmMasterShouldBeFound("areaHectorMr.equals=" + DEFAULT_AREA_HECTOR_MR);

        // Get all the kmMasterList where areaHectorMr equals to UPDATED_AREA_HECTOR_MR
        defaultKmMasterShouldNotBeFound("areaHectorMr.equals=" + UPDATED_AREA_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHectorMr in DEFAULT_AREA_HECTOR_MR or UPDATED_AREA_HECTOR_MR
        defaultKmMasterShouldBeFound("areaHectorMr.in=" + DEFAULT_AREA_HECTOR_MR + "," + UPDATED_AREA_HECTOR_MR);

        // Get all the kmMasterList where areaHectorMr equals to UPDATED_AREA_HECTOR_MR
        defaultKmMasterShouldNotBeFound("areaHectorMr.in=" + UPDATED_AREA_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHectorMr is not null
        defaultKmMasterShouldBeFound("areaHectorMr.specified=true");

        // Get all the kmMasterList where areaHectorMr is null
        defaultKmMasterShouldNotBeFound("areaHectorMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHectorMr contains DEFAULT_AREA_HECTOR_MR
        defaultKmMasterShouldBeFound("areaHectorMr.contains=" + DEFAULT_AREA_HECTOR_MR);

        // Get all the kmMasterList where areaHectorMr contains UPDATED_AREA_HECTOR_MR
        defaultKmMasterShouldNotBeFound("areaHectorMr.contains=" + UPDATED_AREA_HECTOR_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByAreaHectorMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHectorMr does not contain DEFAULT_AREA_HECTOR_MR
        defaultKmMasterShouldNotBeFound("areaHectorMr.doesNotContain=" + DEFAULT_AREA_HECTOR_MR);

        // Get all the kmMasterList where areaHectorMr does not contain UPDATED_AREA_HECTOR_MR
        defaultKmMasterShouldBeFound("areaHectorMr.doesNotContain=" + UPDATED_AREA_HECTOR_MR);
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
    void getAllKmMastersByAadharNoContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo contains DEFAULT_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.contains=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo contains UPDATED_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.contains=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByAadharNoNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo does not contain DEFAULT_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.doesNotContain=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo does not contain UPDATED_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.doesNotContain=" + UPDATED_AADHAR_NO);
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
    void getAllKmMastersByPanNoContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo contains DEFAULT_PAN_NO
        defaultKmMasterShouldBeFound("panNo.contains=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo contains UPDATED_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.contains=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByPanNoNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo does not contain DEFAULT_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.doesNotContain=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo does not contain UPDATED_PAN_NO
        defaultKmMasterShouldBeFound("panNo.doesNotContain=" + UPDATED_PAN_NO);
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
    void getAllKmMastersByKccNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNo equals to DEFAULT_KCC_NO
        defaultKmMasterShouldBeFound("kccNo.equals=" + DEFAULT_KCC_NO);

        // Get all the kmMasterList where kccNo equals to UPDATED_KCC_NO
        defaultKmMasterShouldNotBeFound("kccNo.equals=" + UPDATED_KCC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByKccNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNo in DEFAULT_KCC_NO or UPDATED_KCC_NO
        defaultKmMasterShouldBeFound("kccNo.in=" + DEFAULT_KCC_NO + "," + UPDATED_KCC_NO);

        // Get all the kmMasterList where kccNo equals to UPDATED_KCC_NO
        defaultKmMasterShouldNotBeFound("kccNo.in=" + UPDATED_KCC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByKccNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNo is not null
        defaultKmMasterShouldBeFound("kccNo.specified=true");

        // Get all the kmMasterList where kccNo is null
        defaultKmMasterShouldNotBeFound("kccNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByKccNoContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNo contains DEFAULT_KCC_NO
        defaultKmMasterShouldBeFound("kccNo.contains=" + DEFAULT_KCC_NO);

        // Get all the kmMasterList where kccNo contains UPDATED_KCC_NO
        defaultKmMasterShouldNotBeFound("kccNo.contains=" + UPDATED_KCC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByKccNoNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNo does not contain DEFAULT_KCC_NO
        defaultKmMasterShouldNotBeFound("kccNo.doesNotContain=" + DEFAULT_KCC_NO);

        // Get all the kmMasterList where kccNo does not contain UPDATED_KCC_NO
        defaultKmMasterShouldBeFound("kccNo.doesNotContain=" + UPDATED_KCC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersByKccNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNoMr equals to DEFAULT_KCC_NO_MR
        defaultKmMasterShouldBeFound("kccNoMr.equals=" + DEFAULT_KCC_NO_MR);

        // Get all the kmMasterList where kccNoMr equals to UPDATED_KCC_NO_MR
        defaultKmMasterShouldNotBeFound("kccNoMr.equals=" + UPDATED_KCC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByKccNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNoMr in DEFAULT_KCC_NO_MR or UPDATED_KCC_NO_MR
        defaultKmMasterShouldBeFound("kccNoMr.in=" + DEFAULT_KCC_NO_MR + "," + UPDATED_KCC_NO_MR);

        // Get all the kmMasterList where kccNoMr equals to UPDATED_KCC_NO_MR
        defaultKmMasterShouldNotBeFound("kccNoMr.in=" + UPDATED_KCC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByKccNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNoMr is not null
        defaultKmMasterShouldBeFound("kccNoMr.specified=true");

        // Get all the kmMasterList where kccNoMr is null
        defaultKmMasterShouldNotBeFound("kccNoMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByKccNoMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNoMr contains DEFAULT_KCC_NO_MR
        defaultKmMasterShouldBeFound("kccNoMr.contains=" + DEFAULT_KCC_NO_MR);

        // Get all the kmMasterList where kccNoMr contains UPDATED_KCC_NO_MR
        defaultKmMasterShouldNotBeFound("kccNoMr.contains=" + UPDATED_KCC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByKccNoMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNoMr does not contain DEFAULT_KCC_NO_MR
        defaultKmMasterShouldNotBeFound("kccNoMr.doesNotContain=" + DEFAULT_KCC_NO_MR);

        // Get all the kmMasterList where kccNoMr does not contain UPDATED_KCC_NO_MR
        defaultKmMasterShouldBeFound("kccNoMr.doesNotContain=" + UPDATED_KCC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNo equals to DEFAULT_SAVING_AC_NO
        defaultKmMasterShouldBeFound("savingAcNo.equals=" + DEFAULT_SAVING_AC_NO);

        // Get all the kmMasterList where savingAcNo equals to UPDATED_SAVING_AC_NO
        defaultKmMasterShouldNotBeFound("savingAcNo.equals=" + UPDATED_SAVING_AC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNo in DEFAULT_SAVING_AC_NO or UPDATED_SAVING_AC_NO
        defaultKmMasterShouldBeFound("savingAcNo.in=" + DEFAULT_SAVING_AC_NO + "," + UPDATED_SAVING_AC_NO);

        // Get all the kmMasterList where savingAcNo equals to UPDATED_SAVING_AC_NO
        defaultKmMasterShouldNotBeFound("savingAcNo.in=" + UPDATED_SAVING_AC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNo is not null
        defaultKmMasterShouldBeFound("savingAcNo.specified=true");

        // Get all the kmMasterList where savingAcNo is null
        defaultKmMasterShouldNotBeFound("savingAcNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNo is greater than or equal to DEFAULT_SAVING_AC_NO
        defaultKmMasterShouldBeFound("savingAcNo.greaterThanOrEqual=" + DEFAULT_SAVING_AC_NO);

        // Get all the kmMasterList where savingAcNo is greater than or equal to UPDATED_SAVING_AC_NO
        defaultKmMasterShouldNotBeFound("savingAcNo.greaterThanOrEqual=" + UPDATED_SAVING_AC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNo is less than or equal to DEFAULT_SAVING_AC_NO
        defaultKmMasterShouldBeFound("savingAcNo.lessThanOrEqual=" + DEFAULT_SAVING_AC_NO);

        // Get all the kmMasterList where savingAcNo is less than or equal to SMALLER_SAVING_AC_NO
        defaultKmMasterShouldNotBeFound("savingAcNo.lessThanOrEqual=" + SMALLER_SAVING_AC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNo is less than DEFAULT_SAVING_AC_NO
        defaultKmMasterShouldNotBeFound("savingAcNo.lessThan=" + DEFAULT_SAVING_AC_NO);

        // Get all the kmMasterList where savingAcNo is less than UPDATED_SAVING_AC_NO
        defaultKmMasterShouldBeFound("savingAcNo.lessThan=" + UPDATED_SAVING_AC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNo is greater than DEFAULT_SAVING_AC_NO
        defaultKmMasterShouldNotBeFound("savingAcNo.greaterThan=" + DEFAULT_SAVING_AC_NO);

        // Get all the kmMasterList where savingAcNo is greater than SMALLER_SAVING_AC_NO
        defaultKmMasterShouldBeFound("savingAcNo.greaterThan=" + SMALLER_SAVING_AC_NO);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNoMr equals to DEFAULT_SAVING_AC_NO_MR
        defaultKmMasterShouldBeFound("savingAcNoMr.equals=" + DEFAULT_SAVING_AC_NO_MR);

        // Get all the kmMasterList where savingAcNoMr equals to UPDATED_SAVING_AC_NO_MR
        defaultKmMasterShouldNotBeFound("savingAcNoMr.equals=" + UPDATED_SAVING_AC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNoMr in DEFAULT_SAVING_AC_NO_MR or UPDATED_SAVING_AC_NO_MR
        defaultKmMasterShouldBeFound("savingAcNoMr.in=" + DEFAULT_SAVING_AC_NO_MR + "," + UPDATED_SAVING_AC_NO_MR);

        // Get all the kmMasterList where savingAcNoMr equals to UPDATED_SAVING_AC_NO_MR
        defaultKmMasterShouldNotBeFound("savingAcNoMr.in=" + UPDATED_SAVING_AC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNoMr is not null
        defaultKmMasterShouldBeFound("savingAcNoMr.specified=true");

        // Get all the kmMasterList where savingAcNoMr is null
        defaultKmMasterShouldNotBeFound("savingAcNoMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNoMr contains DEFAULT_SAVING_AC_NO_MR
        defaultKmMasterShouldBeFound("savingAcNoMr.contains=" + DEFAULT_SAVING_AC_NO_MR);

        // Get all the kmMasterList where savingAcNoMr contains UPDATED_SAVING_AC_NO_MR
        defaultKmMasterShouldNotBeFound("savingAcNoMr.contains=" + UPDATED_SAVING_AC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersBySavingAcNoMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingAcNoMr does not contain DEFAULT_SAVING_AC_NO_MR
        defaultKmMasterShouldNotBeFound("savingAcNoMr.doesNotContain=" + DEFAULT_SAVING_AC_NO_MR);

        // Get all the kmMasterList where savingAcNoMr does not contain UPDATED_SAVING_AC_NO_MR
        defaultKmMasterShouldBeFound("savingAcNoMr.doesNotContain=" + UPDATED_SAVING_AC_NO_MR);
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
    void getAllKmMastersByPacsMemberCodeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCodeMr equals to DEFAULT_PACS_MEMBER_CODE_MR
        defaultKmMasterShouldBeFound("pacsMemberCodeMr.equals=" + DEFAULT_PACS_MEMBER_CODE_MR);

        // Get all the kmMasterList where pacsMemberCodeMr equals to UPDATED_PACS_MEMBER_CODE_MR
        defaultKmMasterShouldNotBeFound("pacsMemberCodeMr.equals=" + UPDATED_PACS_MEMBER_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsMemberCodeMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCodeMr in DEFAULT_PACS_MEMBER_CODE_MR or UPDATED_PACS_MEMBER_CODE_MR
        defaultKmMasterShouldBeFound("pacsMemberCodeMr.in=" + DEFAULT_PACS_MEMBER_CODE_MR + "," + UPDATED_PACS_MEMBER_CODE_MR);

        // Get all the kmMasterList where pacsMemberCodeMr equals to UPDATED_PACS_MEMBER_CODE_MR
        defaultKmMasterShouldNotBeFound("pacsMemberCodeMr.in=" + UPDATED_PACS_MEMBER_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsMemberCodeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCodeMr is not null
        defaultKmMasterShouldBeFound("pacsMemberCodeMr.specified=true");

        // Get all the kmMasterList where pacsMemberCodeMr is null
        defaultKmMasterShouldNotBeFound("pacsMemberCodeMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsMemberCodeMrContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCodeMr contains DEFAULT_PACS_MEMBER_CODE_MR
        defaultKmMasterShouldBeFound("pacsMemberCodeMr.contains=" + DEFAULT_PACS_MEMBER_CODE_MR);

        // Get all the kmMasterList where pacsMemberCodeMr contains UPDATED_PACS_MEMBER_CODE_MR
        defaultKmMasterShouldNotBeFound("pacsMemberCodeMr.contains=" + UPDATED_PACS_MEMBER_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKmMastersByPacsMemberCodeMrNotContainsSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCodeMr does not contain DEFAULT_PACS_MEMBER_CODE_MR
        defaultKmMasterShouldNotBeFound("pacsMemberCodeMr.doesNotContain=" + DEFAULT_PACS_MEMBER_CODE_MR);

        // Get all the kmMasterList where pacsMemberCodeMr does not contain UPDATED_PACS_MEMBER_CODE_MR
        defaultKmMasterShouldBeFound("pacsMemberCodeMr.doesNotContain=" + UPDATED_PACS_MEMBER_CODE_MR);
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
            .andExpect(jsonPath("$.[*].branchCodeMr").value(hasItem(DEFAULT_BRANCH_CODE_MR)))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)))
            .andExpect(jsonPath("$.[*].farmerNameMr").value(hasItem(DEFAULT_FARMER_NAME_MR)))
            .andExpect(jsonPath("$.[*].farmerAddress").value(hasItem(DEFAULT_FARMER_ADDRESS)))
            .andExpect(jsonPath("$.[*].farmerAddressMr").value(hasItem(DEFAULT_FARMER_ADDRESS_MR)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].genderMr").value(hasItem(DEFAULT_GENDER_MR)))
            .andExpect(jsonPath("$.[*].caste").value(hasItem(DEFAULT_CASTE)))
            .andExpect(jsonPath("$.[*].casteMr").value(hasItem(DEFAULT_CASTE_MR)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].areaHector").value(hasItem(DEFAULT_AREA_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].areaHectorMr").value(hasItem(DEFAULT_AREA_HECTOR_MR)))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO)))
            .andExpect(jsonPath("$.[*].aadharNoMr").value(hasItem(DEFAULT_AADHAR_NO_MR)))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO)))
            .andExpect(jsonPath("$.[*].panNoMr").value(hasItem(DEFAULT_PAN_NO_MR)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].mobileNoMr").value(hasItem(DEFAULT_MOBILE_NO_MR)))
            .andExpect(jsonPath("$.[*].kccNo").value(hasItem(DEFAULT_KCC_NO)))
            .andExpect(jsonPath("$.[*].kccNoMr").value(hasItem(DEFAULT_KCC_NO_MR)))
            .andExpect(jsonPath("$.[*].savingAcNo").value(hasItem(DEFAULT_SAVING_AC_NO.intValue())))
            .andExpect(jsonPath("$.[*].savingAcNoMr").value(hasItem(DEFAULT_SAVING_AC_NO_MR)))
            .andExpect(jsonPath("$.[*].pacsMemberCode").value(hasItem(DEFAULT_PACS_MEMBER_CODE)))
            .andExpect(jsonPath("$.[*].pacsMemberCodeMr").value(hasItem(DEFAULT_PACS_MEMBER_CODE_MR)))
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
        KmMaster updatedKmMaster = kmMasterRepository.findById(kmMaster.getId()).get();
        // Disconnect from session so that the updates on updatedKmMaster are not directly saved in db
        em.detach(updatedKmMaster);
        updatedKmMaster
            .branchCode(UPDATED_BRANCH_CODE)
            .branchCodeMr(UPDATED_BRANCH_CODE_MR)
            .farmerName(UPDATED_FARMER_NAME)
            .farmerNameMr(UPDATED_FARMER_NAME_MR)
            .farmerAddress(UPDATED_FARMER_ADDRESS)
            .farmerAddressMr(UPDATED_FARMER_ADDRESS_MR)
            .gender(UPDATED_GENDER)
            .genderMr(UPDATED_GENDER_MR)
            .caste(UPDATED_CASTE)
            .casteMr(UPDATED_CASTE_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .areaHector(UPDATED_AREA_HECTOR)
            .areaHectorMr(UPDATED_AREA_HECTOR_MR)
            .aadharNo(UPDATED_AADHAR_NO)
            .aadharNoMr(UPDATED_AADHAR_NO_MR)
            .panNo(UPDATED_PAN_NO)
            .panNoMr(UPDATED_PAN_NO_MR)
            .mobileNo(UPDATED_MOBILE_NO)
            .mobileNoMr(UPDATED_MOBILE_NO_MR)
            .kccNo(UPDATED_KCC_NO)
            .kccNoMr(UPDATED_KCC_NO_MR)
            .savingAcNo(UPDATED_SAVING_AC_NO)
            .savingAcNoMr(UPDATED_SAVING_AC_NO_MR)
            .pacsMemberCode(UPDATED_PACS_MEMBER_CODE)
            .pacsMemberCodeMr(UPDATED_PACS_MEMBER_CODE_MR)
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
        assertThat(testKmMaster.getBranchCodeMr()).isEqualTo(UPDATED_BRANCH_CODE_MR);
        assertThat(testKmMaster.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testKmMaster.getFarmerNameMr()).isEqualTo(UPDATED_FARMER_NAME_MR);
        assertThat(testKmMaster.getFarmerAddress()).isEqualTo(UPDATED_FARMER_ADDRESS);
        assertThat(testKmMaster.getFarmerAddressMr()).isEqualTo(UPDATED_FARMER_ADDRESS_MR);
        assertThat(testKmMaster.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testKmMaster.getGenderMr()).isEqualTo(UPDATED_GENDER_MR);
        assertThat(testKmMaster.getCaste()).isEqualTo(UPDATED_CASTE);
        assertThat(testKmMaster.getCasteMr()).isEqualTo(UPDATED_CASTE_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHector()).isEqualTo(UPDATED_AREA_HECTOR);
        assertThat(testKmMaster.getAreaHectorMr()).isEqualTo(UPDATED_AREA_HECTOR_MR);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(UPDATED_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(UPDATED_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(UPDATED_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(UPDATED_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(UPDATED_MOBILE_NO_MR);
        assertThat(testKmMaster.getKccNo()).isEqualTo(UPDATED_KCC_NO);
        assertThat(testKmMaster.getKccNoMr()).isEqualTo(UPDATED_KCC_NO_MR);
        assertThat(testKmMaster.getSavingAcNo()).isEqualTo(UPDATED_SAVING_AC_NO);
        assertThat(testKmMaster.getSavingAcNoMr()).isEqualTo(UPDATED_SAVING_AC_NO_MR);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(UPDATED_PACS_MEMBER_CODE);
        assertThat(testKmMaster.getPacsMemberCodeMr()).isEqualTo(UPDATED_PACS_MEMBER_CODE_MR);
        assertThat(testKmMaster.getEntryFlag()).isEqualTo(UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void putNonExistingKmMaster() throws Exception {
        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();
        kmMaster.setId(count.incrementAndGet());

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
        kmMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
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
        kmMaster.setId(count.incrementAndGet());

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
            .branchCodeMr(UPDATED_BRANCH_CODE_MR)
            .farmerNameMr(UPDATED_FARMER_NAME_MR)
            .farmerAddress(UPDATED_FARMER_ADDRESS)
            .gender(UPDATED_GENDER)
            .genderMr(UPDATED_GENDER_MR)
            .casteMr(UPDATED_CASTE_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .areaHectorMr(UPDATED_AREA_HECTOR_MR)
            .aadharNo(UPDATED_AADHAR_NO)
            .aadharNoMr(UPDATED_AADHAR_NO_MR)
            .kccNo(UPDATED_KCC_NO)
            .kccNoMr(UPDATED_KCC_NO_MR)
            .pacsMemberCode(UPDATED_PACS_MEMBER_CODE);

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
        assertThat(testKmMaster.getBranchCodeMr()).isEqualTo(UPDATED_BRANCH_CODE_MR);
        assertThat(testKmMaster.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testKmMaster.getFarmerNameMr()).isEqualTo(UPDATED_FARMER_NAME_MR);
        assertThat(testKmMaster.getFarmerAddress()).isEqualTo(UPDATED_FARMER_ADDRESS);
        assertThat(testKmMaster.getFarmerAddressMr()).isEqualTo(DEFAULT_FARMER_ADDRESS_MR);
        assertThat(testKmMaster.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testKmMaster.getGenderMr()).isEqualTo(UPDATED_GENDER_MR);
        assertThat(testKmMaster.getCaste()).isEqualTo(DEFAULT_CASTE);
        assertThat(testKmMaster.getCasteMr()).isEqualTo(UPDATED_CASTE_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHector()).isEqualTo(DEFAULT_AREA_HECTOR);
        assertThat(testKmMaster.getAreaHectorMr()).isEqualTo(UPDATED_AREA_HECTOR_MR);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(UPDATED_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(UPDATED_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(DEFAULT_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(DEFAULT_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(DEFAULT_MOBILE_NO_MR);
        assertThat(testKmMaster.getKccNo()).isEqualTo(UPDATED_KCC_NO);
        assertThat(testKmMaster.getKccNoMr()).isEqualTo(UPDATED_KCC_NO_MR);
        assertThat(testKmMaster.getSavingAcNo()).isEqualTo(DEFAULT_SAVING_AC_NO);
        assertThat(testKmMaster.getSavingAcNoMr()).isEqualTo(DEFAULT_SAVING_AC_NO_MR);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(UPDATED_PACS_MEMBER_CODE);
        assertThat(testKmMaster.getPacsMemberCodeMr()).isEqualTo(DEFAULT_PACS_MEMBER_CODE_MR);
        assertThat(testKmMaster.getEntryFlag()).isEqualTo(DEFAULT_ENTRY_FLAG);
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
            .branchCodeMr(UPDATED_BRANCH_CODE_MR)
            .farmerName(UPDATED_FARMER_NAME)
            .farmerNameMr(UPDATED_FARMER_NAME_MR)
            .farmerAddress(UPDATED_FARMER_ADDRESS)
            .farmerAddressMr(UPDATED_FARMER_ADDRESS_MR)
            .gender(UPDATED_GENDER)
            .genderMr(UPDATED_GENDER_MR)
            .caste(UPDATED_CASTE)
            .casteMr(UPDATED_CASTE_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .areaHector(UPDATED_AREA_HECTOR)
            .areaHectorMr(UPDATED_AREA_HECTOR_MR)
            .aadharNo(UPDATED_AADHAR_NO)
            .aadharNoMr(UPDATED_AADHAR_NO_MR)
            .panNo(UPDATED_PAN_NO)
            .panNoMr(UPDATED_PAN_NO_MR)
            .mobileNo(UPDATED_MOBILE_NO)
            .mobileNoMr(UPDATED_MOBILE_NO_MR)
            .kccNo(UPDATED_KCC_NO)
            .kccNoMr(UPDATED_KCC_NO_MR)
            .savingAcNo(UPDATED_SAVING_AC_NO)
            .savingAcNoMr(UPDATED_SAVING_AC_NO_MR)
            .pacsMemberCode(UPDATED_PACS_MEMBER_CODE)
            .pacsMemberCodeMr(UPDATED_PACS_MEMBER_CODE_MR)
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
        assertThat(testKmMaster.getBranchCodeMr()).isEqualTo(UPDATED_BRANCH_CODE_MR);
        assertThat(testKmMaster.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testKmMaster.getFarmerNameMr()).isEqualTo(UPDATED_FARMER_NAME_MR);
        assertThat(testKmMaster.getFarmerAddress()).isEqualTo(UPDATED_FARMER_ADDRESS);
        assertThat(testKmMaster.getFarmerAddressMr()).isEqualTo(UPDATED_FARMER_ADDRESS_MR);
        assertThat(testKmMaster.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testKmMaster.getGenderMr()).isEqualTo(UPDATED_GENDER_MR);
        assertThat(testKmMaster.getCaste()).isEqualTo(UPDATED_CASTE);
        assertThat(testKmMaster.getCasteMr()).isEqualTo(UPDATED_CASTE_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHector()).isEqualTo(UPDATED_AREA_HECTOR);
        assertThat(testKmMaster.getAreaHectorMr()).isEqualTo(UPDATED_AREA_HECTOR_MR);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(UPDATED_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(UPDATED_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(UPDATED_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(UPDATED_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(UPDATED_MOBILE_NO_MR);
        assertThat(testKmMaster.getKccNo()).isEqualTo(UPDATED_KCC_NO);
        assertThat(testKmMaster.getKccNoMr()).isEqualTo(UPDATED_KCC_NO_MR);
        assertThat(testKmMaster.getSavingAcNo()).isEqualTo(UPDATED_SAVING_AC_NO);
        assertThat(testKmMaster.getSavingAcNoMr()).isEqualTo(UPDATED_SAVING_AC_NO_MR);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(UPDATED_PACS_MEMBER_CODE);
        assertThat(testKmMaster.getPacsMemberCodeMr()).isEqualTo(UPDATED_PACS_MEMBER_CODE_MR);
        assertThat(testKmMaster.getEntryFlag()).isEqualTo(UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    void patchNonExistingKmMaster() throws Exception {
        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();
        kmMaster.setId(count.incrementAndGet());

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
        kmMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
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
        kmMaster.setId(count.incrementAndGet());

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
