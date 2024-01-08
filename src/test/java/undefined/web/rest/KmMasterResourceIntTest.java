package com.cbs.middleware.web.rest;

import com.cbs.middleware.CbsMiddlewareApp;

import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.KmMasterRepository;
import com.cbs.middleware.service.KmMasterService;
import com.cbs.middleware.web.rest.errors.ExceptionTranslator;
import com.cbs.middleware.service.dto.KmMasterCriteria;
import com.cbs.middleware.service.KmMasterQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KmMasterResource REST controller.
 *
 * @see KmMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CbsMiddlewareApp.class)
public class KmMasterResourceIntTest {

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

    private static final String DEFAULT_CASTE = "AAAAAAAAAA";
    private static final String UPDATED_CASTE = "BBBBBBBBBB";

    private static final String DEFAULT_CASTE_MR = "AAAAAAAAAA";
    private static final String UPDATED_CASTE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_HECT = "AAAAAAAAAA";
    private static final String UPDATED_AREA_HECT = "BBBBBBBBBB";

    private static final Long DEFAULT_AADHAR_NO = 1L;
    private static final Long UPDATED_AADHAR_NO = 2L;

    private static final String DEFAULT_AADHAR_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_NO_MR = "BBBBBBBBBB";

    private static final Long DEFAULT_PAN_NO = 1L;
    private static final Long UPDATED_PAN_NO = 2L;

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

    private static final String DEFAULT_SAVING_NO = "AAAAAAAAAA";
    private static final String UPDATED_SAVING_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SAVING_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_SAVING_NO_MR = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRY_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_ENTRY_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_MEMBER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PACS_MEMBER_CODE = "BBBBBBBBBB";

    @Autowired
    private KmMasterRepository kmMasterRepository;

    @Autowired
    private KmMasterService kmMasterService;

    @Autowired
    private KmMasterQueryService kmMasterQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKmMasterMockMvc;

    private KmMaster kmMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KmMasterResource kmMasterResource = new KmMasterResource(kmMasterService, kmMasterQueryService);
        this.restKmMasterMockMvc = MockMvcBuilders.standaloneSetup(kmMasterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmMaster createEntity(EntityManager em) {
        KmMaster kmMaster = new KmMaster();
        kmMaster.setBranchCode(DEFAULT_BRANCH_CODE);
        kmMaster.setFarmerName(DEFAULT_FARMER_NAME);
        kmMaster.setFarmerNameMr(DEFAULT_FARMER_NAME_MR);
        kmMaster.setFarmerAddress(DEFAULT_FARMER_ADDRESS);
        kmMaster.setFarmerAddressMr(DEFAULT_FARMER_ADDRESS_MR);
        kmMaster.setGender(DEFAULT_GENDER);
        kmMaster.setCaste(DEFAULT_CASTE);
        kmMaster.setCasteMr(DEFAULT_CASTE_MR);
        kmMaster.setPacsNumber(DEFAULT_PACS_NUMBER);
        kmMaster.setAreaHect(DEFAULT_AREA_HECT);
        kmMaster.setAadharNo(DEFAULT_AADHAR_NO);
        kmMaster.setAadharNoMr(DEFAULT_AADHAR_NO_MR);
        kmMaster.setPanNo(DEFAULT_PAN_NO);
        kmMaster.setPanNoMr(DEFAULT_PAN_NO_MR);
        kmMaster.setMobileNo(DEFAULT_MOBILE_NO);
        kmMaster.setMobileNoMr(DEFAULT_MOBILE_NO_MR);
        kmMaster.setKccNo(DEFAULT_KCC_NO);
        kmMaster.setKccNoMr(DEFAULT_KCC_NO_MR);
        kmMaster.setSavingNo(DEFAULT_SAVING_NO);
        kmMaster.setSavingNoMr(DEFAULT_SAVING_NO_MR);
        kmMaster.setEntryFlag(DEFAULT_ENTRY_FLAG);
        kmMaster.setPacsMemberCode(DEFAULT_PACS_MEMBER_CODE);
        return kmMaster;
    }

    @Before
    public void initTest() {
        kmMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createKmMaster() throws Exception {
        int databaseSizeBeforeCreate = kmMasterRepository.findAll().size();

        // Create the KmMaster
        restKmMasterMockMvc.perform(post("/api/km-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmMaster)))
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
        assertThat(testKmMaster.getCaste()).isEqualTo(DEFAULT_CASTE);
        assertThat(testKmMaster.getCasteMr()).isEqualTo(DEFAULT_CASTE_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHect()).isEqualTo(DEFAULT_AREA_HECT);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(DEFAULT_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(DEFAULT_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(DEFAULT_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(DEFAULT_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(DEFAULT_MOBILE_NO_MR);
        assertThat(testKmMaster.getKccNo()).isEqualTo(DEFAULT_KCC_NO);
        assertThat(testKmMaster.getKccNoMr()).isEqualTo(DEFAULT_KCC_NO_MR);
        assertThat(testKmMaster.getSavingNo()).isEqualTo(DEFAULT_SAVING_NO);
        assertThat(testKmMaster.getSavingNoMr()).isEqualTo(DEFAULT_SAVING_NO_MR);
        assertThat(testKmMaster.getEntryFlag()).isEqualTo(DEFAULT_ENTRY_FLAG);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(DEFAULT_PACS_MEMBER_CODE);
    }

    @Test
    @Transactional
    public void createKmMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kmMasterRepository.findAll().size();

        // Create the KmMaster with an existing ID
        kmMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmMasterMockMvc.perform(post("/api/km-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKmMasters() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList
        restKmMasterMockMvc.perform(get("/api/km-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE.toString())))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].farmerNameMr").value(hasItem(DEFAULT_FARMER_NAME_MR.toString())))
            .andExpect(jsonPath("$.[*].farmerAddress").value(hasItem(DEFAULT_FARMER_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].farmerAddressMr").value(hasItem(DEFAULT_FARMER_ADDRESS_MR.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].caste").value(hasItem(DEFAULT_CASTE.toString())))
            .andExpect(jsonPath("$.[*].casteMr").value(hasItem(DEFAULT_CASTE_MR.toString())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].areaHect").value(hasItem(DEFAULT_AREA_HECT.toString())))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO.intValue())))
            .andExpect(jsonPath("$.[*].aadharNoMr").value(hasItem(DEFAULT_AADHAR_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO.intValue())))
            .andExpect(jsonPath("$.[*].panNoMr").value(hasItem(DEFAULT_PAN_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
            .andExpect(jsonPath("$.[*].mobileNoMr").value(hasItem(DEFAULT_MOBILE_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].kccNo").value(hasItem(DEFAULT_KCC_NO.toString())))
            .andExpect(jsonPath("$.[*].kccNoMr").value(hasItem(DEFAULT_KCC_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].savingNo").value(hasItem(DEFAULT_SAVING_NO.toString())))
            .andExpect(jsonPath("$.[*].savingNoMr").value(hasItem(DEFAULT_SAVING_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].entryFlag").value(hasItem(DEFAULT_ENTRY_FLAG.toString())))
            .andExpect(jsonPath("$.[*].pacsMemberCode").value(hasItem(DEFAULT_PACS_MEMBER_CODE.toString())));
    }

    @Test
    @Transactional
    public void getKmMaster() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get the kmMaster
        restKmMasterMockMvc.perform(get("/api/km-masters/{id}", kmMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kmMaster.getId().intValue()))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE.toString()))
            .andExpect(jsonPath("$.farmerName").value(DEFAULT_FARMER_NAME.toString()))
            .andExpect(jsonPath("$.farmerNameMr").value(DEFAULT_FARMER_NAME_MR.toString()))
            .andExpect(jsonPath("$.farmerAddress").value(DEFAULT_FARMER_ADDRESS.toString()))
            .andExpect(jsonPath("$.farmerAddressMr").value(DEFAULT_FARMER_ADDRESS_MR.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.caste").value(DEFAULT_CASTE.toString()))
            .andExpect(jsonPath("$.casteMr").value(DEFAULT_CASTE_MR.toString()))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER.toString()))
            .andExpect(jsonPath("$.areaHect").value(DEFAULT_AREA_HECT.toString()))
            .andExpect(jsonPath("$.aadharNo").value(DEFAULT_AADHAR_NO.intValue()))
            .andExpect(jsonPath("$.aadharNoMr").value(DEFAULT_AADHAR_NO_MR.toString()))
            .andExpect(jsonPath("$.panNo").value(DEFAULT_PAN_NO.intValue()))
            .andExpect(jsonPath("$.panNoMr").value(DEFAULT_PAN_NO_MR.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.mobileNoMr").value(DEFAULT_MOBILE_NO_MR.toString()))
            .andExpect(jsonPath("$.kccNo").value(DEFAULT_KCC_NO.toString()))
            .andExpect(jsonPath("$.kccNoMr").value(DEFAULT_KCC_NO_MR.toString()))
            .andExpect(jsonPath("$.savingNo").value(DEFAULT_SAVING_NO.toString()))
            .andExpect(jsonPath("$.savingNoMr").value(DEFAULT_SAVING_NO_MR.toString()))
            .andExpect(jsonPath("$.entryFlag").value(DEFAULT_ENTRY_FLAG.toString()))
            .andExpect(jsonPath("$.pacsMemberCode").value(DEFAULT_PACS_MEMBER_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllKmMastersByBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCode equals to DEFAULT_BRANCH_CODE
        defaultKmMasterShouldBeFound("branchCode.equals=" + DEFAULT_BRANCH_CODE);

        // Get all the kmMasterList where branchCode equals to UPDATED_BRANCH_CODE
        defaultKmMasterShouldNotBeFound("branchCode.equals=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    public void getAllKmMastersByBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCode in DEFAULT_BRANCH_CODE or UPDATED_BRANCH_CODE
        defaultKmMasterShouldBeFound("branchCode.in=" + DEFAULT_BRANCH_CODE + "," + UPDATED_BRANCH_CODE);

        // Get all the kmMasterList where branchCode equals to UPDATED_BRANCH_CODE
        defaultKmMasterShouldNotBeFound("branchCode.in=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    public void getAllKmMastersByBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where branchCode is not null
        defaultKmMasterShouldBeFound("branchCode.specified=true");

        // Get all the kmMasterList where branchCode is null
        defaultKmMasterShouldNotBeFound("branchCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerName equals to DEFAULT_FARMER_NAME
        defaultKmMasterShouldBeFound("farmerName.equals=" + DEFAULT_FARMER_NAME);

        // Get all the kmMasterList where farmerName equals to UPDATED_FARMER_NAME
        defaultKmMasterShouldNotBeFound("farmerName.equals=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerName in DEFAULT_FARMER_NAME or UPDATED_FARMER_NAME
        defaultKmMasterShouldBeFound("farmerName.in=" + DEFAULT_FARMER_NAME + "," + UPDATED_FARMER_NAME);

        // Get all the kmMasterList where farmerName equals to UPDATED_FARMER_NAME
        defaultKmMasterShouldNotBeFound("farmerName.in=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerName is not null
        defaultKmMasterShouldBeFound("farmerName.specified=true");

        // Get all the kmMasterList where farmerName is null
        defaultKmMasterShouldNotBeFound("farmerName.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerNameMr equals to DEFAULT_FARMER_NAME_MR
        defaultKmMasterShouldBeFound("farmerNameMr.equals=" + DEFAULT_FARMER_NAME_MR);

        // Get all the kmMasterList where farmerNameMr equals to UPDATED_FARMER_NAME_MR
        defaultKmMasterShouldNotBeFound("farmerNameMr.equals=" + UPDATED_FARMER_NAME_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerNameMr in DEFAULT_FARMER_NAME_MR or UPDATED_FARMER_NAME_MR
        defaultKmMasterShouldBeFound("farmerNameMr.in=" + DEFAULT_FARMER_NAME_MR + "," + UPDATED_FARMER_NAME_MR);

        // Get all the kmMasterList where farmerNameMr equals to UPDATED_FARMER_NAME_MR
        defaultKmMasterShouldNotBeFound("farmerNameMr.in=" + UPDATED_FARMER_NAME_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerNameMr is not null
        defaultKmMasterShouldBeFound("farmerNameMr.specified=true");

        // Get all the kmMasterList where farmerNameMr is null
        defaultKmMasterShouldNotBeFound("farmerNameMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddress equals to DEFAULT_FARMER_ADDRESS
        defaultKmMasterShouldBeFound("farmerAddress.equals=" + DEFAULT_FARMER_ADDRESS);

        // Get all the kmMasterList where farmerAddress equals to UPDATED_FARMER_ADDRESS
        defaultKmMasterShouldNotBeFound("farmerAddress.equals=" + UPDATED_FARMER_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerAddressIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddress in DEFAULT_FARMER_ADDRESS or UPDATED_FARMER_ADDRESS
        defaultKmMasterShouldBeFound("farmerAddress.in=" + DEFAULT_FARMER_ADDRESS + "," + UPDATED_FARMER_ADDRESS);

        // Get all the kmMasterList where farmerAddress equals to UPDATED_FARMER_ADDRESS
        defaultKmMasterShouldNotBeFound("farmerAddress.in=" + UPDATED_FARMER_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddress is not null
        defaultKmMasterShouldBeFound("farmerAddress.specified=true");

        // Get all the kmMasterList where farmerAddress is null
        defaultKmMasterShouldNotBeFound("farmerAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerAddressMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddressMr equals to DEFAULT_FARMER_ADDRESS_MR
        defaultKmMasterShouldBeFound("farmerAddressMr.equals=" + DEFAULT_FARMER_ADDRESS_MR);

        // Get all the kmMasterList where farmerAddressMr equals to UPDATED_FARMER_ADDRESS_MR
        defaultKmMasterShouldNotBeFound("farmerAddressMr.equals=" + UPDATED_FARMER_ADDRESS_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerAddressMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddressMr in DEFAULT_FARMER_ADDRESS_MR or UPDATED_FARMER_ADDRESS_MR
        defaultKmMasterShouldBeFound("farmerAddressMr.in=" + DEFAULT_FARMER_ADDRESS_MR + "," + UPDATED_FARMER_ADDRESS_MR);

        // Get all the kmMasterList where farmerAddressMr equals to UPDATED_FARMER_ADDRESS_MR
        defaultKmMasterShouldNotBeFound("farmerAddressMr.in=" + UPDATED_FARMER_ADDRESS_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByFarmerAddressMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where farmerAddressMr is not null
        defaultKmMasterShouldBeFound("farmerAddressMr.specified=true");

        // Get all the kmMasterList where farmerAddressMr is null
        defaultKmMasterShouldNotBeFound("farmerAddressMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where gender equals to DEFAULT_GENDER
        defaultKmMasterShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the kmMasterList where gender equals to UPDATED_GENDER
        defaultKmMasterShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllKmMastersByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultKmMasterShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the kmMasterList where gender equals to UPDATED_GENDER
        defaultKmMasterShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllKmMastersByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where gender is not null
        defaultKmMasterShouldBeFound("gender.specified=true");

        // Get all the kmMasterList where gender is null
        defaultKmMasterShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByCasteIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where caste equals to DEFAULT_CASTE
        defaultKmMasterShouldBeFound("caste.equals=" + DEFAULT_CASTE);

        // Get all the kmMasterList where caste equals to UPDATED_CASTE
        defaultKmMasterShouldNotBeFound("caste.equals=" + UPDATED_CASTE);
    }

    @Test
    @Transactional
    public void getAllKmMastersByCasteIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where caste in DEFAULT_CASTE or UPDATED_CASTE
        defaultKmMasterShouldBeFound("caste.in=" + DEFAULT_CASTE + "," + UPDATED_CASTE);

        // Get all the kmMasterList where caste equals to UPDATED_CASTE
        defaultKmMasterShouldNotBeFound("caste.in=" + UPDATED_CASTE);
    }

    @Test
    @Transactional
    public void getAllKmMastersByCasteIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where caste is not null
        defaultKmMasterShouldBeFound("caste.specified=true");

        // Get all the kmMasterList where caste is null
        defaultKmMasterShouldNotBeFound("caste.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByCasteMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where casteMr equals to DEFAULT_CASTE_MR
        defaultKmMasterShouldBeFound("casteMr.equals=" + DEFAULT_CASTE_MR);

        // Get all the kmMasterList where casteMr equals to UPDATED_CASTE_MR
        defaultKmMasterShouldNotBeFound("casteMr.equals=" + UPDATED_CASTE_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByCasteMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where casteMr in DEFAULT_CASTE_MR or UPDATED_CASTE_MR
        defaultKmMasterShouldBeFound("casteMr.in=" + DEFAULT_CASTE_MR + "," + UPDATED_CASTE_MR);

        // Get all the kmMasterList where casteMr equals to UPDATED_CASTE_MR
        defaultKmMasterShouldNotBeFound("casteMr.in=" + UPDATED_CASTE_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByCasteMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where casteMr is not null
        defaultKmMasterShouldBeFound("casteMr.specified=true");

        // Get all the kmMasterList where casteMr is null
        defaultKmMasterShouldNotBeFound("casteMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByPacsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsNumber equals to DEFAULT_PACS_NUMBER
        defaultKmMasterShouldBeFound("pacsNumber.equals=" + DEFAULT_PACS_NUMBER);

        // Get all the kmMasterList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKmMasterShouldNotBeFound("pacsNumber.equals=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllKmMastersByPacsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsNumber in DEFAULT_PACS_NUMBER or UPDATED_PACS_NUMBER
        defaultKmMasterShouldBeFound("pacsNumber.in=" + DEFAULT_PACS_NUMBER + "," + UPDATED_PACS_NUMBER);

        // Get all the kmMasterList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKmMasterShouldNotBeFound("pacsNumber.in=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllKmMastersByPacsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsNumber is not null
        defaultKmMasterShouldBeFound("pacsNumber.specified=true");

        // Get all the kmMasterList where pacsNumber is null
        defaultKmMasterShouldNotBeFound("pacsNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByAreaHectIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHect equals to DEFAULT_AREA_HECT
        defaultKmMasterShouldBeFound("areaHect.equals=" + DEFAULT_AREA_HECT);

        // Get all the kmMasterList where areaHect equals to UPDATED_AREA_HECT
        defaultKmMasterShouldNotBeFound("areaHect.equals=" + UPDATED_AREA_HECT);
    }

    @Test
    @Transactional
    public void getAllKmMastersByAreaHectIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHect in DEFAULT_AREA_HECT or UPDATED_AREA_HECT
        defaultKmMasterShouldBeFound("areaHect.in=" + DEFAULT_AREA_HECT + "," + UPDATED_AREA_HECT);

        // Get all the kmMasterList where areaHect equals to UPDATED_AREA_HECT
        defaultKmMasterShouldNotBeFound("areaHect.in=" + UPDATED_AREA_HECT);
    }

    @Test
    @Transactional
    public void getAllKmMastersByAreaHectIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where areaHect is not null
        defaultKmMasterShouldBeFound("areaHect.specified=true");

        // Get all the kmMasterList where areaHect is null
        defaultKmMasterShouldNotBeFound("areaHect.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByAadharNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo equals to DEFAULT_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.equals=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo equals to UPDATED_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.equals=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByAadharNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo in DEFAULT_AADHAR_NO or UPDATED_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.in=" + DEFAULT_AADHAR_NO + "," + UPDATED_AADHAR_NO);

        // Get all the kmMasterList where aadharNo equals to UPDATED_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.in=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByAadharNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo is not null
        defaultKmMasterShouldBeFound("aadharNo.specified=true");

        // Get all the kmMasterList where aadharNo is null
        defaultKmMasterShouldNotBeFound("aadharNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByAadharNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo greater than or equals to DEFAULT_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.greaterOrEqualThan=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo greater than or equals to UPDATED_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.greaterOrEqualThan=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByAadharNoIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNo less than or equals to DEFAULT_AADHAR_NO
        defaultKmMasterShouldNotBeFound("aadharNo.lessThan=" + DEFAULT_AADHAR_NO);

        // Get all the kmMasterList where aadharNo less than or equals to UPDATED_AADHAR_NO
        defaultKmMasterShouldBeFound("aadharNo.lessThan=" + UPDATED_AADHAR_NO);
    }


    @Test
    @Transactional
    public void getAllKmMastersByAadharNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNoMr equals to DEFAULT_AADHAR_NO_MR
        defaultKmMasterShouldBeFound("aadharNoMr.equals=" + DEFAULT_AADHAR_NO_MR);

        // Get all the kmMasterList where aadharNoMr equals to UPDATED_AADHAR_NO_MR
        defaultKmMasterShouldNotBeFound("aadharNoMr.equals=" + UPDATED_AADHAR_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByAadharNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNoMr in DEFAULT_AADHAR_NO_MR or UPDATED_AADHAR_NO_MR
        defaultKmMasterShouldBeFound("aadharNoMr.in=" + DEFAULT_AADHAR_NO_MR + "," + UPDATED_AADHAR_NO_MR);

        // Get all the kmMasterList where aadharNoMr equals to UPDATED_AADHAR_NO_MR
        defaultKmMasterShouldNotBeFound("aadharNoMr.in=" + UPDATED_AADHAR_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByAadharNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where aadharNoMr is not null
        defaultKmMasterShouldBeFound("aadharNoMr.specified=true");

        // Get all the kmMasterList where aadharNoMr is null
        defaultKmMasterShouldNotBeFound("aadharNoMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByPanNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo equals to DEFAULT_PAN_NO
        defaultKmMasterShouldBeFound("panNo.equals=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo equals to UPDATED_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.equals=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByPanNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo in DEFAULT_PAN_NO or UPDATED_PAN_NO
        defaultKmMasterShouldBeFound("panNo.in=" + DEFAULT_PAN_NO + "," + UPDATED_PAN_NO);

        // Get all the kmMasterList where panNo equals to UPDATED_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.in=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByPanNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo is not null
        defaultKmMasterShouldBeFound("panNo.specified=true");

        // Get all the kmMasterList where panNo is null
        defaultKmMasterShouldNotBeFound("panNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByPanNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo greater than or equals to DEFAULT_PAN_NO
        defaultKmMasterShouldBeFound("panNo.greaterOrEqualThan=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo greater than or equals to UPDATED_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.greaterOrEqualThan=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByPanNoIsLessThanSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNo less than or equals to DEFAULT_PAN_NO
        defaultKmMasterShouldNotBeFound("panNo.lessThan=" + DEFAULT_PAN_NO);

        // Get all the kmMasterList where panNo less than or equals to UPDATED_PAN_NO
        defaultKmMasterShouldBeFound("panNo.lessThan=" + UPDATED_PAN_NO);
    }


    @Test
    @Transactional
    public void getAllKmMastersByPanNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNoMr equals to DEFAULT_PAN_NO_MR
        defaultKmMasterShouldBeFound("panNoMr.equals=" + DEFAULT_PAN_NO_MR);

        // Get all the kmMasterList where panNoMr equals to UPDATED_PAN_NO_MR
        defaultKmMasterShouldNotBeFound("panNoMr.equals=" + UPDATED_PAN_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByPanNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNoMr in DEFAULT_PAN_NO_MR or UPDATED_PAN_NO_MR
        defaultKmMasterShouldBeFound("panNoMr.in=" + DEFAULT_PAN_NO_MR + "," + UPDATED_PAN_NO_MR);

        // Get all the kmMasterList where panNoMr equals to UPDATED_PAN_NO_MR
        defaultKmMasterShouldNotBeFound("panNoMr.in=" + UPDATED_PAN_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByPanNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where panNoMr is not null
        defaultKmMasterShouldBeFound("panNoMr.specified=true");

        // Get all the kmMasterList where panNoMr is null
        defaultKmMasterShouldNotBeFound("panNoMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultKmMasterShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the kmMasterList where mobileNo equals to UPDATED_MOBILE_NO
        defaultKmMasterShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultKmMasterShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the kmMasterList where mobileNo equals to UPDATED_MOBILE_NO
        defaultKmMasterShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNo is not null
        defaultKmMasterShouldBeFound("mobileNo.specified=true");

        // Get all the kmMasterList where mobileNo is null
        defaultKmMasterShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByMobileNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNoMr equals to DEFAULT_MOBILE_NO_MR
        defaultKmMasterShouldBeFound("mobileNoMr.equals=" + DEFAULT_MOBILE_NO_MR);

        // Get all the kmMasterList where mobileNoMr equals to UPDATED_MOBILE_NO_MR
        defaultKmMasterShouldNotBeFound("mobileNoMr.equals=" + UPDATED_MOBILE_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByMobileNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNoMr in DEFAULT_MOBILE_NO_MR or UPDATED_MOBILE_NO_MR
        defaultKmMasterShouldBeFound("mobileNoMr.in=" + DEFAULT_MOBILE_NO_MR + "," + UPDATED_MOBILE_NO_MR);

        // Get all the kmMasterList where mobileNoMr equals to UPDATED_MOBILE_NO_MR
        defaultKmMasterShouldNotBeFound("mobileNoMr.in=" + UPDATED_MOBILE_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByMobileNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where mobileNoMr is not null
        defaultKmMasterShouldBeFound("mobileNoMr.specified=true");

        // Get all the kmMasterList where mobileNoMr is null
        defaultKmMasterShouldNotBeFound("mobileNoMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByKccNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNo equals to DEFAULT_KCC_NO
        defaultKmMasterShouldBeFound("kccNo.equals=" + DEFAULT_KCC_NO);

        // Get all the kmMasterList where kccNo equals to UPDATED_KCC_NO
        defaultKmMasterShouldNotBeFound("kccNo.equals=" + UPDATED_KCC_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByKccNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNo in DEFAULT_KCC_NO or UPDATED_KCC_NO
        defaultKmMasterShouldBeFound("kccNo.in=" + DEFAULT_KCC_NO + "," + UPDATED_KCC_NO);

        // Get all the kmMasterList where kccNo equals to UPDATED_KCC_NO
        defaultKmMasterShouldNotBeFound("kccNo.in=" + UPDATED_KCC_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersByKccNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNo is not null
        defaultKmMasterShouldBeFound("kccNo.specified=true");

        // Get all the kmMasterList where kccNo is null
        defaultKmMasterShouldNotBeFound("kccNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByKccNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNoMr equals to DEFAULT_KCC_NO_MR
        defaultKmMasterShouldBeFound("kccNoMr.equals=" + DEFAULT_KCC_NO_MR);

        // Get all the kmMasterList where kccNoMr equals to UPDATED_KCC_NO_MR
        defaultKmMasterShouldNotBeFound("kccNoMr.equals=" + UPDATED_KCC_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByKccNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNoMr in DEFAULT_KCC_NO_MR or UPDATED_KCC_NO_MR
        defaultKmMasterShouldBeFound("kccNoMr.in=" + DEFAULT_KCC_NO_MR + "," + UPDATED_KCC_NO_MR);

        // Get all the kmMasterList where kccNoMr equals to UPDATED_KCC_NO_MR
        defaultKmMasterShouldNotBeFound("kccNoMr.in=" + UPDATED_KCC_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersByKccNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where kccNoMr is not null
        defaultKmMasterShouldBeFound("kccNoMr.specified=true");

        // Get all the kmMasterList where kccNoMr is null
        defaultKmMasterShouldNotBeFound("kccNoMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersBySavingNoIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo equals to DEFAULT_SAVING_NO
        defaultKmMasterShouldBeFound("savingNo.equals=" + DEFAULT_SAVING_NO);

        // Get all the kmMasterList where savingNo equals to UPDATED_SAVING_NO
        defaultKmMasterShouldNotBeFound("savingNo.equals=" + UPDATED_SAVING_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersBySavingNoIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo in DEFAULT_SAVING_NO or UPDATED_SAVING_NO
        defaultKmMasterShouldBeFound("savingNo.in=" + DEFAULT_SAVING_NO + "," + UPDATED_SAVING_NO);

        // Get all the kmMasterList where savingNo equals to UPDATED_SAVING_NO
        defaultKmMasterShouldNotBeFound("savingNo.in=" + UPDATED_SAVING_NO);
    }

    @Test
    @Transactional
    public void getAllKmMastersBySavingNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNo is not null
        defaultKmMasterShouldBeFound("savingNo.specified=true");

        // Get all the kmMasterList where savingNo is null
        defaultKmMasterShouldNotBeFound("savingNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersBySavingNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNoMr equals to DEFAULT_SAVING_NO_MR
        defaultKmMasterShouldBeFound("savingNoMr.equals=" + DEFAULT_SAVING_NO_MR);

        // Get all the kmMasterList where savingNoMr equals to UPDATED_SAVING_NO_MR
        defaultKmMasterShouldNotBeFound("savingNoMr.equals=" + UPDATED_SAVING_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersBySavingNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNoMr in DEFAULT_SAVING_NO_MR or UPDATED_SAVING_NO_MR
        defaultKmMasterShouldBeFound("savingNoMr.in=" + DEFAULT_SAVING_NO_MR + "," + UPDATED_SAVING_NO_MR);

        // Get all the kmMasterList where savingNoMr equals to UPDATED_SAVING_NO_MR
        defaultKmMasterShouldNotBeFound("savingNoMr.in=" + UPDATED_SAVING_NO_MR);
    }

    @Test
    @Transactional
    public void getAllKmMastersBySavingNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where savingNoMr is not null
        defaultKmMasterShouldBeFound("savingNoMr.specified=true");

        // Get all the kmMasterList where savingNoMr is null
        defaultKmMasterShouldNotBeFound("savingNoMr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByEntryFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where entryFlag equals to DEFAULT_ENTRY_FLAG
        defaultKmMasterShouldBeFound("entryFlag.equals=" + DEFAULT_ENTRY_FLAG);

        // Get all the kmMasterList where entryFlag equals to UPDATED_ENTRY_FLAG
        defaultKmMasterShouldNotBeFound("entryFlag.equals=" + UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    public void getAllKmMastersByEntryFlagIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where entryFlag in DEFAULT_ENTRY_FLAG or UPDATED_ENTRY_FLAG
        defaultKmMasterShouldBeFound("entryFlag.in=" + DEFAULT_ENTRY_FLAG + "," + UPDATED_ENTRY_FLAG);

        // Get all the kmMasterList where entryFlag equals to UPDATED_ENTRY_FLAG
        defaultKmMasterShouldNotBeFound("entryFlag.in=" + UPDATED_ENTRY_FLAG);
    }

    @Test
    @Transactional
    public void getAllKmMastersByEntryFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where entryFlag is not null
        defaultKmMasterShouldBeFound("entryFlag.specified=true");

        // Get all the kmMasterList where entryFlag is null
        defaultKmMasterShouldNotBeFound("entryFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllKmMastersByPacsMemberCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCode equals to DEFAULT_PACS_MEMBER_CODE
        defaultKmMasterShouldBeFound("pacsMemberCode.equals=" + DEFAULT_PACS_MEMBER_CODE);

        // Get all the kmMasterList where pacsMemberCode equals to UPDATED_PACS_MEMBER_CODE
        defaultKmMasterShouldNotBeFound("pacsMemberCode.equals=" + UPDATED_PACS_MEMBER_CODE);
    }

    @Test
    @Transactional
    public void getAllKmMastersByPacsMemberCodeIsInShouldWork() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCode in DEFAULT_PACS_MEMBER_CODE or UPDATED_PACS_MEMBER_CODE
        defaultKmMasterShouldBeFound("pacsMemberCode.in=" + DEFAULT_PACS_MEMBER_CODE + "," + UPDATED_PACS_MEMBER_CODE);

        // Get all the kmMasterList where pacsMemberCode equals to UPDATED_PACS_MEMBER_CODE
        defaultKmMasterShouldNotBeFound("pacsMemberCode.in=" + UPDATED_PACS_MEMBER_CODE);
    }

    @Test
    @Transactional
    public void getAllKmMastersByPacsMemberCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMasterRepository.saveAndFlush(kmMaster);

        // Get all the kmMasterList where pacsMemberCode is not null
        defaultKmMasterShouldBeFound("pacsMemberCode.specified=true");

        // Get all the kmMasterList where pacsMemberCode is null
        defaultKmMasterShouldNotBeFound("pacsMemberCode.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultKmMasterShouldBeFound(String filter) throws Exception {
        restKmMasterMockMvc.perform(get("/api/km-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE.toString())))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].farmerNameMr").value(hasItem(DEFAULT_FARMER_NAME_MR.toString())))
            .andExpect(jsonPath("$.[*].farmerAddress").value(hasItem(DEFAULT_FARMER_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].farmerAddressMr").value(hasItem(DEFAULT_FARMER_ADDRESS_MR.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].caste").value(hasItem(DEFAULT_CASTE.toString())))
            .andExpect(jsonPath("$.[*].casteMr").value(hasItem(DEFAULT_CASTE_MR.toString())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].areaHect").value(hasItem(DEFAULT_AREA_HECT.toString())))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO.intValue())))
            .andExpect(jsonPath("$.[*].aadharNoMr").value(hasItem(DEFAULT_AADHAR_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO.intValue())))
            .andExpect(jsonPath("$.[*].panNoMr").value(hasItem(DEFAULT_PAN_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
            .andExpect(jsonPath("$.[*].mobileNoMr").value(hasItem(DEFAULT_MOBILE_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].kccNo").value(hasItem(DEFAULT_KCC_NO.toString())))
            .andExpect(jsonPath("$.[*].kccNoMr").value(hasItem(DEFAULT_KCC_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].savingNo").value(hasItem(DEFAULT_SAVING_NO.toString())))
            .andExpect(jsonPath("$.[*].savingNoMr").value(hasItem(DEFAULT_SAVING_NO_MR.toString())))
            .andExpect(jsonPath("$.[*].entryFlag").value(hasItem(DEFAULT_ENTRY_FLAG.toString())))
            .andExpect(jsonPath("$.[*].pacsMemberCode").value(hasItem(DEFAULT_PACS_MEMBER_CODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultKmMasterShouldNotBeFound(String filter) throws Exception {
        restKmMasterMockMvc.perform(get("/api/km-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingKmMaster() throws Exception {
        // Get the kmMaster
        restKmMasterMockMvc.perform(get("/api/km-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKmMaster() throws Exception {
        // Initialize the database
        kmMasterService.save(kmMaster);

        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();

        // Update the kmMaster
        KmMaster updatedKmMaster = kmMasterRepository.findOne(kmMaster.getId());
        updatedKmMaster.setBranchCode(UPDATED_BRANCH_CODE);
        updatedKmMaster.setFarmerName(UPDATED_FARMER_NAME);
        updatedKmMaster.setFarmerNameMr(UPDATED_FARMER_NAME_MR);
        updatedKmMaster.setFarmerAddress(UPDATED_FARMER_ADDRESS);
        updatedKmMaster.setFarmerAddressMr(UPDATED_FARMER_ADDRESS_MR);
        updatedKmMaster.setGender(UPDATED_GENDER);
        updatedKmMaster.setCaste(UPDATED_CASTE);
        updatedKmMaster.setCasteMr(UPDATED_CASTE_MR);
        updatedKmMaster.setPacsNumber(UPDATED_PACS_NUMBER);
        updatedKmMaster.setAreaHect(UPDATED_AREA_HECT);
        updatedKmMaster.setAadharNo(UPDATED_AADHAR_NO);
        updatedKmMaster.setAadharNoMr(UPDATED_AADHAR_NO_MR);
        updatedKmMaster.setPanNo(UPDATED_PAN_NO);
        updatedKmMaster.setPanNoMr(UPDATED_PAN_NO_MR);
        updatedKmMaster.setMobileNo(UPDATED_MOBILE_NO);
        updatedKmMaster.setMobileNoMr(UPDATED_MOBILE_NO_MR);
        updatedKmMaster.setKccNo(UPDATED_KCC_NO);
        updatedKmMaster.setKccNoMr(UPDATED_KCC_NO_MR);
        updatedKmMaster.setSavingNo(UPDATED_SAVING_NO);
        updatedKmMaster.setSavingNoMr(UPDATED_SAVING_NO_MR);
        updatedKmMaster.setEntryFlag(UPDATED_ENTRY_FLAG);
        updatedKmMaster.setPacsMemberCode(UPDATED_PACS_MEMBER_CODE);

        restKmMasterMockMvc.perform(put("/api/km-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKmMaster)))
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
        assertThat(testKmMaster.getCaste()).isEqualTo(UPDATED_CASTE);
        assertThat(testKmMaster.getCasteMr()).isEqualTo(UPDATED_CASTE_MR);
        assertThat(testKmMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKmMaster.getAreaHect()).isEqualTo(UPDATED_AREA_HECT);
        assertThat(testKmMaster.getAadharNo()).isEqualTo(UPDATED_AADHAR_NO);
        assertThat(testKmMaster.getAadharNoMr()).isEqualTo(UPDATED_AADHAR_NO_MR);
        assertThat(testKmMaster.getPanNo()).isEqualTo(UPDATED_PAN_NO);
        assertThat(testKmMaster.getPanNoMr()).isEqualTo(UPDATED_PAN_NO_MR);
        assertThat(testKmMaster.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testKmMaster.getMobileNoMr()).isEqualTo(UPDATED_MOBILE_NO_MR);
        assertThat(testKmMaster.getKccNo()).isEqualTo(UPDATED_KCC_NO);
        assertThat(testKmMaster.getKccNoMr()).isEqualTo(UPDATED_KCC_NO_MR);
        assertThat(testKmMaster.getSavingNo()).isEqualTo(UPDATED_SAVING_NO);
        assertThat(testKmMaster.getSavingNoMr()).isEqualTo(UPDATED_SAVING_NO_MR);
        assertThat(testKmMaster.getEntryFlag()).isEqualTo(UPDATED_ENTRY_FLAG);
        assertThat(testKmMaster.getPacsMemberCode()).isEqualTo(UPDATED_PACS_MEMBER_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingKmMaster() throws Exception {
        int databaseSizeBeforeUpdate = kmMasterRepository.findAll().size();

        // Create the KmMaster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKmMasterMockMvc.perform(put("/api/km-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kmMaster)))
            .andExpect(status().isCreated());

        // Validate the KmMaster in the database
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKmMaster() throws Exception {
        // Initialize the database
        kmMasterService.save(kmMaster);

        int databaseSizeBeforeDelete = kmMasterRepository.findAll().size();

        // Get the kmMaster
        restKmMasterMockMvc.perform(delete("/api/km-masters/{id}", kmMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KmMaster> kmMasterList = kmMasterRepository.findAll();
        assertThat(kmMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmMaster.class);
        KmMaster kmMaster1 = new KmMaster();
        kmMaster1.setId(1L);
        KmMaster kmMaster2 = new KmMaster();
        kmMaster2.setId(kmMaster1.getId());
        assertThat(kmMaster1).isEqualTo(kmMaster2);
        kmMaster2.setId(2L);
        assertThat(kmMaster1).isNotEqualTo(kmMaster2);
        kmMaster1.setId(null);
        assertThat(kmMaster1).isNotEqualTo(kmMaster2);
    }
}
