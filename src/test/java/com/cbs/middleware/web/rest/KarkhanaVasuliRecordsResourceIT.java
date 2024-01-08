package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KarkhanaVasuliFile;
import com.cbs.middleware.domain.KarkhanaVasuliRecords;
import com.cbs.middleware.repository.KarkhanaVasuliRecordsRepository;
import com.cbs.middleware.service.criteria.KarkhanaVasuliRecordsCriteria;
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
 * Integration tests for the {@link KarkhanaVasuliRecordsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KarkhanaVasuliRecordsResourceIT {

    private static final Long DEFAULT_FACTORY_MEMBER_CODE = 1L;
    private static final Long UPDATED_FACTORY_MEMBER_CODE = 2L;
    private static final Long SMALLER_FACTORY_MEMBER_CODE = 1L - 1L;

    private static final String DEFAULT_KARKHANA_MEMBER_CODE_MR = "AAAAAAAAAA";
    private static final String UPDATED_KARKHANA_MEMBER_CODE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_NAME_MR = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_NAME_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final String DEFAULT_AMOUNT_MR = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT_MR = "BBBBBBBBBB";

    private static final Long DEFAULT_SAVING_ACC_NO = 1L;
    private static final Long UPDATED_SAVING_ACC_NO = 2L;
    private static final Long SMALLER_SAVING_ACC_NO = 1L - 1L;

    private static final String DEFAULT_SAVING_ACC_NO_MR = "AAAAAAAAAA";
    private static final String UPDATED_SAVING_ACC_NO_MR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/karkhana-vasuli-records";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KarkhanaVasuliRecordsRepository karkhanaVasuliRecordsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKarkhanaVasuliRecordsMockMvc;

    private KarkhanaVasuliRecords karkhanaVasuliRecords;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KarkhanaVasuliRecords createEntity(EntityManager em) {
        KarkhanaVasuliRecords karkhanaVasuliRecords = new KarkhanaVasuliRecords()
            .factoryMemberCode(DEFAULT_FACTORY_MEMBER_CODE)
            .karkhanaMemberCodeMr(DEFAULT_KARKHANA_MEMBER_CODE_MR)
            .memberName(DEFAULT_MEMBER_NAME)
            .memberNameMr(DEFAULT_MEMBER_NAME_MR)
            .villageName(DEFAULT_VILLAGE_NAME)
            .villageNameMr(DEFAULT_VILLAGE_NAME_MR)
            .amount(DEFAULT_AMOUNT)
            .amountMr(DEFAULT_AMOUNT_MR)
            .savingAccNo(DEFAULT_SAVING_ACC_NO)
            .savingAccNoMr(DEFAULT_SAVING_ACC_NO_MR)
            .status(DEFAULT_STATUS);
        return karkhanaVasuliRecords;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KarkhanaVasuliRecords createUpdatedEntity(EntityManager em) {
        KarkhanaVasuliRecords karkhanaVasuliRecords = new KarkhanaVasuliRecords()
            .factoryMemberCode(UPDATED_FACTORY_MEMBER_CODE)
            .karkhanaMemberCodeMr(UPDATED_KARKHANA_MEMBER_CODE_MR)
            .memberName(UPDATED_MEMBER_NAME)
            .memberNameMr(UPDATED_MEMBER_NAME_MR)
            .villageName(UPDATED_VILLAGE_NAME)
            .villageNameMr(UPDATED_VILLAGE_NAME_MR)
            .amount(UPDATED_AMOUNT)
            .amountMr(UPDATED_AMOUNT_MR)
            .savingAccNo(UPDATED_SAVING_ACC_NO)
            .savingAccNoMr(UPDATED_SAVING_ACC_NO_MR)
            .status(UPDATED_STATUS);
        return karkhanaVasuliRecords;
    }

    @BeforeEach
    public void initTest() {
        karkhanaVasuliRecords = createEntity(em);
    }

    @Test
    @Transactional
    void createKarkhanaVasuliRecords() throws Exception {
        int databaseSizeBeforeCreate = karkhanaVasuliRecordsRepository.findAll().size();
        // Create the KarkhanaVasuliRecords
        restKarkhanaVasuliRecordsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliRecords))
            )
            .andExpect(status().isCreated());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeCreate + 1);
        KarkhanaVasuliRecords testKarkhanaVasuliRecords = karkhanaVasuliRecordsList.get(karkhanaVasuliRecordsList.size() - 1);
        assertThat(testKarkhanaVasuliRecords.getFactoryMemberCode()).isEqualTo(DEFAULT_FACTORY_MEMBER_CODE);
        assertThat(testKarkhanaVasuliRecords.getKarkhanaMemberCodeMr()).isEqualTo(DEFAULT_KARKHANA_MEMBER_CODE_MR);
        assertThat(testKarkhanaVasuliRecords.getMemberName()).isEqualTo(DEFAULT_MEMBER_NAME);
        assertThat(testKarkhanaVasuliRecords.getMemberNameMr()).isEqualTo(DEFAULT_MEMBER_NAME_MR);
        assertThat(testKarkhanaVasuliRecords.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
        assertThat(testKarkhanaVasuliRecords.getVillageNameMr()).isEqualTo(DEFAULT_VILLAGE_NAME_MR);
        assertThat(testKarkhanaVasuliRecords.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testKarkhanaVasuliRecords.getAmountMr()).isEqualTo(DEFAULT_AMOUNT_MR);
        assertThat(testKarkhanaVasuliRecords.getSavingAccNo()).isEqualTo(DEFAULT_SAVING_ACC_NO);
        assertThat(testKarkhanaVasuliRecords.getSavingAccNoMr()).isEqualTo(DEFAULT_SAVING_ACC_NO_MR);
        assertThat(testKarkhanaVasuliRecords.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createKarkhanaVasuliRecordsWithExistingId() throws Exception {
        // Create the KarkhanaVasuliRecords with an existing ID
        karkhanaVasuliRecords.setId(1L);

        int databaseSizeBeforeCreate = karkhanaVasuliRecordsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKarkhanaVasuliRecordsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliRecords))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecords() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList
        restKarkhanaVasuliRecordsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(karkhanaVasuliRecords.getId().intValue())))
            .andExpect(jsonPath("$.[*].factoryMemberCode").value(hasItem(DEFAULT_FACTORY_MEMBER_CODE.intValue())))
            .andExpect(jsonPath("$.[*].karkhanaMemberCodeMr").value(hasItem(DEFAULT_KARKHANA_MEMBER_CODE_MR)))
            .andExpect(jsonPath("$.[*].memberName").value(hasItem(DEFAULT_MEMBER_NAME)))
            .andExpect(jsonPath("$.[*].memberNameMr").value(hasItem(DEFAULT_MEMBER_NAME_MR)))
            .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME)))
            .andExpect(jsonPath("$.[*].villageNameMr").value(hasItem(DEFAULT_VILLAGE_NAME_MR)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountMr").value(hasItem(DEFAULT_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].savingAccNo").value(hasItem(DEFAULT_SAVING_ACC_NO.intValue())))
            .andExpect(jsonPath("$.[*].savingAccNoMr").value(hasItem(DEFAULT_SAVING_ACC_NO_MR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getKarkhanaVasuliRecords() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get the karkhanaVasuliRecords
        restKarkhanaVasuliRecordsMockMvc
            .perform(get(ENTITY_API_URL_ID, karkhanaVasuliRecords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(karkhanaVasuliRecords.getId().intValue()))
            .andExpect(jsonPath("$.factoryMemberCode").value(DEFAULT_FACTORY_MEMBER_CODE.intValue()))
            .andExpect(jsonPath("$.karkhanaMemberCodeMr").value(DEFAULT_KARKHANA_MEMBER_CODE_MR))
            .andExpect(jsonPath("$.memberName").value(DEFAULT_MEMBER_NAME))
            .andExpect(jsonPath("$.memberNameMr").value(DEFAULT_MEMBER_NAME_MR))
            .andExpect(jsonPath("$.villageName").value(DEFAULT_VILLAGE_NAME))
            .andExpect(jsonPath("$.villageNameMr").value(DEFAULT_VILLAGE_NAME_MR))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountMr").value(DEFAULT_AMOUNT_MR))
            .andExpect(jsonPath("$.savingAccNo").value(DEFAULT_SAVING_ACC_NO.intValue()))
            .andExpect(jsonPath("$.savingAccNoMr").value(DEFAULT_SAVING_ACC_NO_MR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getKarkhanaVasuliRecordsByIdFiltering() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        Long id = karkhanaVasuliRecords.getId();

        defaultKarkhanaVasuliRecordsShouldBeFound("id.equals=" + id);
        defaultKarkhanaVasuliRecordsShouldNotBeFound("id.notEquals=" + id);

        defaultKarkhanaVasuliRecordsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKarkhanaVasuliRecordsShouldNotBeFound("id.greaterThan=" + id);

        defaultKarkhanaVasuliRecordsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKarkhanaVasuliRecordsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByFactoryMemberCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode equals to DEFAULT_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldBeFound("factoryMemberCode.equals=" + DEFAULT_FACTORY_MEMBER_CODE);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode equals to UPDATED_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldNotBeFound("factoryMemberCode.equals=" + UPDATED_FACTORY_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByFactoryMemberCodeIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode in DEFAULT_FACTORY_MEMBER_CODE or UPDATED_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldBeFound(
            "factoryMemberCode.in=" + DEFAULT_FACTORY_MEMBER_CODE + "," + UPDATED_FACTORY_MEMBER_CODE
        );

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode equals to UPDATED_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldNotBeFound("factoryMemberCode.in=" + UPDATED_FACTORY_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByFactoryMemberCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("factoryMemberCode.specified=true");

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("factoryMemberCode.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByFactoryMemberCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is greater than or equal to DEFAULT_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldBeFound("factoryMemberCode.greaterThanOrEqual=" + DEFAULT_FACTORY_MEMBER_CODE);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is greater than or equal to UPDATED_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldNotBeFound("factoryMemberCode.greaterThanOrEqual=" + UPDATED_FACTORY_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByFactoryMemberCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is less than or equal to DEFAULT_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldBeFound("factoryMemberCode.lessThanOrEqual=" + DEFAULT_FACTORY_MEMBER_CODE);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is less than or equal to SMALLER_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldNotBeFound("factoryMemberCode.lessThanOrEqual=" + SMALLER_FACTORY_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByFactoryMemberCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is less than DEFAULT_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldNotBeFound("factoryMemberCode.lessThan=" + DEFAULT_FACTORY_MEMBER_CODE);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is less than UPDATED_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldBeFound("factoryMemberCode.lessThan=" + UPDATED_FACTORY_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByFactoryMemberCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is greater than DEFAULT_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldNotBeFound("factoryMemberCode.greaterThan=" + DEFAULT_FACTORY_MEMBER_CODE);

        // Get all the karkhanaVasuliRecordsList where factoryMemberCode is greater than SMALLER_FACTORY_MEMBER_CODE
        defaultKarkhanaVasuliRecordsShouldBeFound("factoryMemberCode.greaterThan=" + SMALLER_FACTORY_MEMBER_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByKarkhanaMemberCodeMrIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr equals to DEFAULT_KARKHANA_MEMBER_CODE_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("karkhanaMemberCodeMr.equals=" + DEFAULT_KARKHANA_MEMBER_CODE_MR);

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr equals to UPDATED_KARKHANA_MEMBER_CODE_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("karkhanaMemberCodeMr.equals=" + UPDATED_KARKHANA_MEMBER_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByKarkhanaMemberCodeMrIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr in DEFAULT_KARKHANA_MEMBER_CODE_MR or UPDATED_KARKHANA_MEMBER_CODE_MR
        defaultKarkhanaVasuliRecordsShouldBeFound(
            "karkhanaMemberCodeMr.in=" + DEFAULT_KARKHANA_MEMBER_CODE_MR + "," + UPDATED_KARKHANA_MEMBER_CODE_MR
        );

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr equals to UPDATED_KARKHANA_MEMBER_CODE_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("karkhanaMemberCodeMr.in=" + UPDATED_KARKHANA_MEMBER_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByKarkhanaMemberCodeMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("karkhanaMemberCodeMr.specified=true");

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("karkhanaMemberCodeMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByKarkhanaMemberCodeMrContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr contains DEFAULT_KARKHANA_MEMBER_CODE_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("karkhanaMemberCodeMr.contains=" + DEFAULT_KARKHANA_MEMBER_CODE_MR);

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr contains UPDATED_KARKHANA_MEMBER_CODE_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("karkhanaMemberCodeMr.contains=" + UPDATED_KARKHANA_MEMBER_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByKarkhanaMemberCodeMrNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr does not contain DEFAULT_KARKHANA_MEMBER_CODE_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("karkhanaMemberCodeMr.doesNotContain=" + DEFAULT_KARKHANA_MEMBER_CODE_MR);

        // Get all the karkhanaVasuliRecordsList where karkhanaMemberCodeMr does not contain UPDATED_KARKHANA_MEMBER_CODE_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("karkhanaMemberCodeMr.doesNotContain=" + UPDATED_KARKHANA_MEMBER_CODE_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberName equals to DEFAULT_MEMBER_NAME
        defaultKarkhanaVasuliRecordsShouldBeFound("memberName.equals=" + DEFAULT_MEMBER_NAME);

        // Get all the karkhanaVasuliRecordsList where memberName equals to UPDATED_MEMBER_NAME
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberName.equals=" + UPDATED_MEMBER_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberName in DEFAULT_MEMBER_NAME or UPDATED_MEMBER_NAME
        defaultKarkhanaVasuliRecordsShouldBeFound("memberName.in=" + DEFAULT_MEMBER_NAME + "," + UPDATED_MEMBER_NAME);

        // Get all the karkhanaVasuliRecordsList where memberName equals to UPDATED_MEMBER_NAME
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberName.in=" + UPDATED_MEMBER_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberName is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("memberName.specified=true");

        // Get all the karkhanaVasuliRecordsList where memberName is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberName contains DEFAULT_MEMBER_NAME
        defaultKarkhanaVasuliRecordsShouldBeFound("memberName.contains=" + DEFAULT_MEMBER_NAME);

        // Get all the karkhanaVasuliRecordsList where memberName contains UPDATED_MEMBER_NAME
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberName.contains=" + UPDATED_MEMBER_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberName does not contain DEFAULT_MEMBER_NAME
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberName.doesNotContain=" + DEFAULT_MEMBER_NAME);

        // Get all the karkhanaVasuliRecordsList where memberName does not contain UPDATED_MEMBER_NAME
        defaultKarkhanaVasuliRecordsShouldBeFound("memberName.doesNotContain=" + UPDATED_MEMBER_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberNameMr equals to DEFAULT_MEMBER_NAME_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("memberNameMr.equals=" + DEFAULT_MEMBER_NAME_MR);

        // Get all the karkhanaVasuliRecordsList where memberNameMr equals to UPDATED_MEMBER_NAME_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberNameMr.equals=" + UPDATED_MEMBER_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberNameMr in DEFAULT_MEMBER_NAME_MR or UPDATED_MEMBER_NAME_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("memberNameMr.in=" + DEFAULT_MEMBER_NAME_MR + "," + UPDATED_MEMBER_NAME_MR);

        // Get all the karkhanaVasuliRecordsList where memberNameMr equals to UPDATED_MEMBER_NAME_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberNameMr.in=" + UPDATED_MEMBER_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberNameMr is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("memberNameMr.specified=true");

        // Get all the karkhanaVasuliRecordsList where memberNameMr is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberNameMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameMrContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberNameMr contains DEFAULT_MEMBER_NAME_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("memberNameMr.contains=" + DEFAULT_MEMBER_NAME_MR);

        // Get all the karkhanaVasuliRecordsList where memberNameMr contains UPDATED_MEMBER_NAME_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberNameMr.contains=" + UPDATED_MEMBER_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByMemberNameMrNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where memberNameMr does not contain DEFAULT_MEMBER_NAME_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("memberNameMr.doesNotContain=" + DEFAULT_MEMBER_NAME_MR);

        // Get all the karkhanaVasuliRecordsList where memberNameMr does not contain UPDATED_MEMBER_NAME_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("memberNameMr.doesNotContain=" + UPDATED_MEMBER_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageName equals to DEFAULT_VILLAGE_NAME
        defaultKarkhanaVasuliRecordsShouldBeFound("villageName.equals=" + DEFAULT_VILLAGE_NAME);

        // Get all the karkhanaVasuliRecordsList where villageName equals to UPDATED_VILLAGE_NAME
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageName.equals=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageName in DEFAULT_VILLAGE_NAME or UPDATED_VILLAGE_NAME
        defaultKarkhanaVasuliRecordsShouldBeFound("villageName.in=" + DEFAULT_VILLAGE_NAME + "," + UPDATED_VILLAGE_NAME);

        // Get all the karkhanaVasuliRecordsList where villageName equals to UPDATED_VILLAGE_NAME
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageName.in=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageName is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("villageName.specified=true");

        // Get all the karkhanaVasuliRecordsList where villageName is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageName contains DEFAULT_VILLAGE_NAME
        defaultKarkhanaVasuliRecordsShouldBeFound("villageName.contains=" + DEFAULT_VILLAGE_NAME);

        // Get all the karkhanaVasuliRecordsList where villageName contains UPDATED_VILLAGE_NAME
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageName.contains=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageName does not contain DEFAULT_VILLAGE_NAME
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageName.doesNotContain=" + DEFAULT_VILLAGE_NAME);

        // Get all the karkhanaVasuliRecordsList where villageName does not contain UPDATED_VILLAGE_NAME
        defaultKarkhanaVasuliRecordsShouldBeFound("villageName.doesNotContain=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageNameMr equals to DEFAULT_VILLAGE_NAME_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("villageNameMr.equals=" + DEFAULT_VILLAGE_NAME_MR);

        // Get all the karkhanaVasuliRecordsList where villageNameMr equals to UPDATED_VILLAGE_NAME_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageNameMr.equals=" + UPDATED_VILLAGE_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageNameMr in DEFAULT_VILLAGE_NAME_MR or UPDATED_VILLAGE_NAME_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("villageNameMr.in=" + DEFAULT_VILLAGE_NAME_MR + "," + UPDATED_VILLAGE_NAME_MR);

        // Get all the karkhanaVasuliRecordsList where villageNameMr equals to UPDATED_VILLAGE_NAME_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageNameMr.in=" + UPDATED_VILLAGE_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageNameMr is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("villageNameMr.specified=true");

        // Get all the karkhanaVasuliRecordsList where villageNameMr is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageNameMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameMrContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageNameMr contains DEFAULT_VILLAGE_NAME_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("villageNameMr.contains=" + DEFAULT_VILLAGE_NAME_MR);

        // Get all the karkhanaVasuliRecordsList where villageNameMr contains UPDATED_VILLAGE_NAME_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageNameMr.contains=" + UPDATED_VILLAGE_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByVillageNameMrNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where villageNameMr does not contain DEFAULT_VILLAGE_NAME_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("villageNameMr.doesNotContain=" + DEFAULT_VILLAGE_NAME_MR);

        // Get all the karkhanaVasuliRecordsList where villageNameMr does not contain UPDATED_VILLAGE_NAME_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("villageNameMr.doesNotContain=" + UPDATED_VILLAGE_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amount equals to DEFAULT_AMOUNT
        defaultKarkhanaVasuliRecordsShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the karkhanaVasuliRecordsList where amount equals to UPDATED_AMOUNT
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultKarkhanaVasuliRecordsShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the karkhanaVasuliRecordsList where amount equals to UPDATED_AMOUNT
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amount is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("amount.specified=true");

        // Get all the karkhanaVasuliRecordsList where amount is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultKarkhanaVasuliRecordsShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the karkhanaVasuliRecordsList where amount is greater than or equal to UPDATED_AMOUNT
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amount is less than or equal to DEFAULT_AMOUNT
        defaultKarkhanaVasuliRecordsShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the karkhanaVasuliRecordsList where amount is less than or equal to SMALLER_AMOUNT
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amount is less than DEFAULT_AMOUNT
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the karkhanaVasuliRecordsList where amount is less than UPDATED_AMOUNT
        defaultKarkhanaVasuliRecordsShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amount is greater than DEFAULT_AMOUNT
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the karkhanaVasuliRecordsList where amount is greater than SMALLER_AMOUNT
        defaultKarkhanaVasuliRecordsShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountMrIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amountMr equals to DEFAULT_AMOUNT_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("amountMr.equals=" + DEFAULT_AMOUNT_MR);

        // Get all the karkhanaVasuliRecordsList where amountMr equals to UPDATED_AMOUNT_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amountMr.equals=" + UPDATED_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountMrIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amountMr in DEFAULT_AMOUNT_MR or UPDATED_AMOUNT_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("amountMr.in=" + DEFAULT_AMOUNT_MR + "," + UPDATED_AMOUNT_MR);

        // Get all the karkhanaVasuliRecordsList where amountMr equals to UPDATED_AMOUNT_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amountMr.in=" + UPDATED_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amountMr is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("amountMr.specified=true");

        // Get all the karkhanaVasuliRecordsList where amountMr is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amountMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountMrContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amountMr contains DEFAULT_AMOUNT_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("amountMr.contains=" + DEFAULT_AMOUNT_MR);

        // Get all the karkhanaVasuliRecordsList where amountMr contains UPDATED_AMOUNT_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amountMr.contains=" + UPDATED_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByAmountMrNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where amountMr does not contain DEFAULT_AMOUNT_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("amountMr.doesNotContain=" + DEFAULT_AMOUNT_MR);

        // Get all the karkhanaVasuliRecordsList where amountMr does not contain UPDATED_AMOUNT_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("amountMr.doesNotContain=" + UPDATED_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNo equals to DEFAULT_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNo.equals=" + DEFAULT_SAVING_ACC_NO);

        // Get all the karkhanaVasuliRecordsList where savingAccNo equals to UPDATED_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNo.equals=" + UPDATED_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNo in DEFAULT_SAVING_ACC_NO or UPDATED_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNo.in=" + DEFAULT_SAVING_ACC_NO + "," + UPDATED_SAVING_ACC_NO);

        // Get all the karkhanaVasuliRecordsList where savingAccNo equals to UPDATED_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNo.in=" + UPDATED_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNo is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNo.specified=true");

        // Get all the karkhanaVasuliRecordsList where savingAccNo is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNo.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNo is greater than or equal to DEFAULT_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNo.greaterThanOrEqual=" + DEFAULT_SAVING_ACC_NO);

        // Get all the karkhanaVasuliRecordsList where savingAccNo is greater than or equal to UPDATED_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNo.greaterThanOrEqual=" + UPDATED_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNo is less than or equal to DEFAULT_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNo.lessThanOrEqual=" + DEFAULT_SAVING_ACC_NO);

        // Get all the karkhanaVasuliRecordsList where savingAccNo is less than or equal to SMALLER_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNo.lessThanOrEqual=" + SMALLER_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoIsLessThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNo is less than DEFAULT_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNo.lessThan=" + DEFAULT_SAVING_ACC_NO);

        // Get all the karkhanaVasuliRecordsList where savingAccNo is less than UPDATED_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNo.lessThan=" + UPDATED_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNo is greater than DEFAULT_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNo.greaterThan=" + DEFAULT_SAVING_ACC_NO);

        // Get all the karkhanaVasuliRecordsList where savingAccNo is greater than SMALLER_SAVING_ACC_NO
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNo.greaterThan=" + SMALLER_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoMrIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr equals to DEFAULT_SAVING_ACC_NO_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNoMr.equals=" + DEFAULT_SAVING_ACC_NO_MR);

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr equals to UPDATED_SAVING_ACC_NO_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNoMr.equals=" + UPDATED_SAVING_ACC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoMrIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr in DEFAULT_SAVING_ACC_NO_MR or UPDATED_SAVING_ACC_NO_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNoMr.in=" + DEFAULT_SAVING_ACC_NO_MR + "," + UPDATED_SAVING_ACC_NO_MR);

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr equals to UPDATED_SAVING_ACC_NO_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNoMr.in=" + UPDATED_SAVING_ACC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNoMr.specified=true");

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNoMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoMrContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr contains DEFAULT_SAVING_ACC_NO_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNoMr.contains=" + DEFAULT_SAVING_ACC_NO_MR);

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr contains UPDATED_SAVING_ACC_NO_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNoMr.contains=" + UPDATED_SAVING_ACC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsBySavingAccNoMrNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr does not contain DEFAULT_SAVING_ACC_NO_MR
        defaultKarkhanaVasuliRecordsShouldNotBeFound("savingAccNoMr.doesNotContain=" + DEFAULT_SAVING_ACC_NO_MR);

        // Get all the karkhanaVasuliRecordsList where savingAccNoMr does not contain UPDATED_SAVING_ACC_NO_MR
        defaultKarkhanaVasuliRecordsShouldBeFound("savingAccNoMr.doesNotContain=" + UPDATED_SAVING_ACC_NO_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where status equals to DEFAULT_STATUS
        defaultKarkhanaVasuliRecordsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the karkhanaVasuliRecordsList where status equals to UPDATED_STATUS
        defaultKarkhanaVasuliRecordsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultKarkhanaVasuliRecordsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the karkhanaVasuliRecordsList where status equals to UPDATED_STATUS
        defaultKarkhanaVasuliRecordsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        // Get all the karkhanaVasuliRecordsList where status is not null
        defaultKarkhanaVasuliRecordsShouldBeFound("status.specified=true");

        // Get all the karkhanaVasuliRecordsList where status is null
        defaultKarkhanaVasuliRecordsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliRecordsByKarkhanaVasuliFileIsEqualToSomething() throws Exception {
        KarkhanaVasuliFile karkhanaVasuliFile;
        if (TestUtil.findAll(em, KarkhanaVasuliFile.class).isEmpty()) {
            karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);
            karkhanaVasuliFile = KarkhanaVasuliFileResourceIT.createEntity(em);
        } else {
            karkhanaVasuliFile = TestUtil.findAll(em, KarkhanaVasuliFile.class).get(0);
        }
        em.persist(karkhanaVasuliFile);
        em.flush();
        karkhanaVasuliRecords.setKarkhanaVasuliFile(karkhanaVasuliFile);
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);
        Long karkhanaVasuliFileId = karkhanaVasuliFile.getId();

        // Get all the karkhanaVasuliRecordsList where karkhanaVasuliFile equals to karkhanaVasuliFileId
        defaultKarkhanaVasuliRecordsShouldBeFound("karkhanaVasuliFileId.equals=" + karkhanaVasuliFileId);

        // Get all the karkhanaVasuliRecordsList where karkhanaVasuliFile equals to (karkhanaVasuliFileId + 1)
        defaultKarkhanaVasuliRecordsShouldNotBeFound("karkhanaVasuliFileId.equals=" + (karkhanaVasuliFileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKarkhanaVasuliRecordsShouldBeFound(String filter) throws Exception {
        restKarkhanaVasuliRecordsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(karkhanaVasuliRecords.getId().intValue())))
            .andExpect(jsonPath("$.[*].factoryMemberCode").value(hasItem(DEFAULT_FACTORY_MEMBER_CODE.intValue())))
            .andExpect(jsonPath("$.[*].karkhanaMemberCodeMr").value(hasItem(DEFAULT_KARKHANA_MEMBER_CODE_MR)))
            .andExpect(jsonPath("$.[*].memberName").value(hasItem(DEFAULT_MEMBER_NAME)))
            .andExpect(jsonPath("$.[*].memberNameMr").value(hasItem(DEFAULT_MEMBER_NAME_MR)))
            .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME)))
            .andExpect(jsonPath("$.[*].villageNameMr").value(hasItem(DEFAULT_VILLAGE_NAME_MR)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountMr").value(hasItem(DEFAULT_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].savingAccNo").value(hasItem(DEFAULT_SAVING_ACC_NO.intValue())))
            .andExpect(jsonPath("$.[*].savingAccNoMr").value(hasItem(DEFAULT_SAVING_ACC_NO_MR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));

        // Check, that the count call also returns 1
        restKarkhanaVasuliRecordsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKarkhanaVasuliRecordsShouldNotBeFound(String filter) throws Exception {
        restKarkhanaVasuliRecordsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKarkhanaVasuliRecordsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKarkhanaVasuliRecords() throws Exception {
        // Get the karkhanaVasuliRecords
        restKarkhanaVasuliRecordsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKarkhanaVasuliRecords() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        int databaseSizeBeforeUpdate = karkhanaVasuliRecordsRepository.findAll().size();

        // Update the karkhanaVasuliRecords
        KarkhanaVasuliRecords updatedKarkhanaVasuliRecords = karkhanaVasuliRecordsRepository.findById(karkhanaVasuliRecords.getId()).get();
        // Disconnect from session so that the updates on updatedKarkhanaVasuliRecords are not directly saved in db
        em.detach(updatedKarkhanaVasuliRecords);
        updatedKarkhanaVasuliRecords
            .factoryMemberCode(UPDATED_FACTORY_MEMBER_CODE)
            .karkhanaMemberCodeMr(UPDATED_KARKHANA_MEMBER_CODE_MR)
            .memberName(UPDATED_MEMBER_NAME)
            .memberNameMr(UPDATED_MEMBER_NAME_MR)
            .villageName(UPDATED_VILLAGE_NAME)
            .villageNameMr(UPDATED_VILLAGE_NAME_MR)
            .amount(UPDATED_AMOUNT)
            .amountMr(UPDATED_AMOUNT_MR)
            .savingAccNo(UPDATED_SAVING_ACC_NO)
            .savingAccNoMr(UPDATED_SAVING_ACC_NO_MR)
            .status(UPDATED_STATUS);

        restKarkhanaVasuliRecordsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKarkhanaVasuliRecords.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKarkhanaVasuliRecords))
            )
            .andExpect(status().isOk());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeUpdate);
        KarkhanaVasuliRecords testKarkhanaVasuliRecords = karkhanaVasuliRecordsList.get(karkhanaVasuliRecordsList.size() - 1);
        assertThat(testKarkhanaVasuliRecords.getFactoryMemberCode()).isEqualTo(UPDATED_FACTORY_MEMBER_CODE);
        assertThat(testKarkhanaVasuliRecords.getKarkhanaMemberCodeMr()).isEqualTo(UPDATED_KARKHANA_MEMBER_CODE_MR);
        assertThat(testKarkhanaVasuliRecords.getMemberName()).isEqualTo(UPDATED_MEMBER_NAME);
        assertThat(testKarkhanaVasuliRecords.getMemberNameMr()).isEqualTo(UPDATED_MEMBER_NAME_MR);
        assertThat(testKarkhanaVasuliRecords.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testKarkhanaVasuliRecords.getVillageNameMr()).isEqualTo(UPDATED_VILLAGE_NAME_MR);
        assertThat(testKarkhanaVasuliRecords.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testKarkhanaVasuliRecords.getAmountMr()).isEqualTo(UPDATED_AMOUNT_MR);
        assertThat(testKarkhanaVasuliRecords.getSavingAccNo()).isEqualTo(UPDATED_SAVING_ACC_NO);
        assertThat(testKarkhanaVasuliRecords.getSavingAccNoMr()).isEqualTo(UPDATED_SAVING_ACC_NO_MR);
        assertThat(testKarkhanaVasuliRecords.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingKarkhanaVasuliRecords() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRecordsRepository.findAll().size();
        karkhanaVasuliRecords.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKarkhanaVasuliRecordsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, karkhanaVasuliRecords.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliRecords))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKarkhanaVasuliRecords() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRecordsRepository.findAll().size();
        karkhanaVasuliRecords.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliRecordsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliRecords))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKarkhanaVasuliRecords() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRecordsRepository.findAll().size();
        karkhanaVasuliRecords.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliRecordsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliRecords))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKarkhanaVasuliRecordsWithPatch() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        int databaseSizeBeforeUpdate = karkhanaVasuliRecordsRepository.findAll().size();

        // Update the karkhanaVasuliRecords using partial update
        KarkhanaVasuliRecords partialUpdatedKarkhanaVasuliRecords = new KarkhanaVasuliRecords();
        partialUpdatedKarkhanaVasuliRecords.setId(karkhanaVasuliRecords.getId());

        partialUpdatedKarkhanaVasuliRecords
            .factoryMemberCode(UPDATED_FACTORY_MEMBER_CODE)
            .memberName(UPDATED_MEMBER_NAME)
            .memberNameMr(UPDATED_MEMBER_NAME_MR)
            .villageName(UPDATED_VILLAGE_NAME)
            .villageNameMr(UPDATED_VILLAGE_NAME_MR)
            .savingAccNoMr(UPDATED_SAVING_ACC_NO_MR);

        restKarkhanaVasuliRecordsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKarkhanaVasuliRecords.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKarkhanaVasuliRecords))
            )
            .andExpect(status().isOk());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeUpdate);
        KarkhanaVasuliRecords testKarkhanaVasuliRecords = karkhanaVasuliRecordsList.get(karkhanaVasuliRecordsList.size() - 1);
        assertThat(testKarkhanaVasuliRecords.getFactoryMemberCode()).isEqualTo(UPDATED_FACTORY_MEMBER_CODE);
        assertThat(testKarkhanaVasuliRecords.getKarkhanaMemberCodeMr()).isEqualTo(DEFAULT_KARKHANA_MEMBER_CODE_MR);
        assertThat(testKarkhanaVasuliRecords.getMemberName()).isEqualTo(UPDATED_MEMBER_NAME);
        assertThat(testKarkhanaVasuliRecords.getMemberNameMr()).isEqualTo(UPDATED_MEMBER_NAME_MR);
        assertThat(testKarkhanaVasuliRecords.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testKarkhanaVasuliRecords.getVillageNameMr()).isEqualTo(UPDATED_VILLAGE_NAME_MR);
        assertThat(testKarkhanaVasuliRecords.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testKarkhanaVasuliRecords.getAmountMr()).isEqualTo(DEFAULT_AMOUNT_MR);
        assertThat(testKarkhanaVasuliRecords.getSavingAccNo()).isEqualTo(DEFAULT_SAVING_ACC_NO);
        assertThat(testKarkhanaVasuliRecords.getSavingAccNoMr()).isEqualTo(UPDATED_SAVING_ACC_NO_MR);
        assertThat(testKarkhanaVasuliRecords.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateKarkhanaVasuliRecordsWithPatch() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        int databaseSizeBeforeUpdate = karkhanaVasuliRecordsRepository.findAll().size();

        // Update the karkhanaVasuliRecords using partial update
        KarkhanaVasuliRecords partialUpdatedKarkhanaVasuliRecords = new KarkhanaVasuliRecords();
        partialUpdatedKarkhanaVasuliRecords.setId(karkhanaVasuliRecords.getId());

        partialUpdatedKarkhanaVasuliRecords
            .factoryMemberCode(UPDATED_FACTORY_MEMBER_CODE)
            .karkhanaMemberCodeMr(UPDATED_KARKHANA_MEMBER_CODE_MR)
            .memberName(UPDATED_MEMBER_NAME)
            .memberNameMr(UPDATED_MEMBER_NAME_MR)
            .villageName(UPDATED_VILLAGE_NAME)
            .villageNameMr(UPDATED_VILLAGE_NAME_MR)
            .amount(UPDATED_AMOUNT)
            .amountMr(UPDATED_AMOUNT_MR)
            .savingAccNo(UPDATED_SAVING_ACC_NO)
            .savingAccNoMr(UPDATED_SAVING_ACC_NO_MR)
            .status(UPDATED_STATUS);

        restKarkhanaVasuliRecordsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKarkhanaVasuliRecords.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKarkhanaVasuliRecords))
            )
            .andExpect(status().isOk());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeUpdate);
        KarkhanaVasuliRecords testKarkhanaVasuliRecords = karkhanaVasuliRecordsList.get(karkhanaVasuliRecordsList.size() - 1);
        assertThat(testKarkhanaVasuliRecords.getFactoryMemberCode()).isEqualTo(UPDATED_FACTORY_MEMBER_CODE);
        assertThat(testKarkhanaVasuliRecords.getKarkhanaMemberCodeMr()).isEqualTo(UPDATED_KARKHANA_MEMBER_CODE_MR);
        assertThat(testKarkhanaVasuliRecords.getMemberName()).isEqualTo(UPDATED_MEMBER_NAME);
        assertThat(testKarkhanaVasuliRecords.getMemberNameMr()).isEqualTo(UPDATED_MEMBER_NAME_MR);
        assertThat(testKarkhanaVasuliRecords.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testKarkhanaVasuliRecords.getVillageNameMr()).isEqualTo(UPDATED_VILLAGE_NAME_MR);
        assertThat(testKarkhanaVasuliRecords.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testKarkhanaVasuliRecords.getAmountMr()).isEqualTo(UPDATED_AMOUNT_MR);
        assertThat(testKarkhanaVasuliRecords.getSavingAccNo()).isEqualTo(UPDATED_SAVING_ACC_NO);
        assertThat(testKarkhanaVasuliRecords.getSavingAccNoMr()).isEqualTo(UPDATED_SAVING_ACC_NO_MR);
        assertThat(testKarkhanaVasuliRecords.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingKarkhanaVasuliRecords() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRecordsRepository.findAll().size();
        karkhanaVasuliRecords.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKarkhanaVasuliRecordsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, karkhanaVasuliRecords.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliRecords))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKarkhanaVasuliRecords() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRecordsRepository.findAll().size();
        karkhanaVasuliRecords.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliRecordsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliRecords))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKarkhanaVasuliRecords() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliRecordsRepository.findAll().size();
        karkhanaVasuliRecords.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliRecordsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliRecords))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KarkhanaVasuliRecords in the database
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKarkhanaVasuliRecords() throws Exception {
        // Initialize the database
        karkhanaVasuliRecordsRepository.saveAndFlush(karkhanaVasuliRecords);

        int databaseSizeBeforeDelete = karkhanaVasuliRecordsRepository.findAll().size();

        // Delete the karkhanaVasuliRecords
        restKarkhanaVasuliRecordsMockMvc
            .perform(delete(ENTITY_API_URL_ID, karkhanaVasuliRecords.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KarkhanaVasuliRecords> karkhanaVasuliRecordsList = karkhanaVasuliRecordsRepository.findAll();
        assertThat(karkhanaVasuliRecordsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
