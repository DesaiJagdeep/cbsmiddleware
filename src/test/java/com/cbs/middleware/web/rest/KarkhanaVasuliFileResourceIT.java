package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.FactoryMaster;
import com.cbs.middleware.domain.KarkhanaVasuliFile;
import com.cbs.middleware.domain.KarkhanaVasuliRecords;
import com.cbs.middleware.repository.KarkhanaVasuliFileRepository;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link KarkhanaVasuliFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KarkhanaVasuliFileResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNIQUE_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_MR = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_MR = "BBBBBBBBBB";

    private static final String DEFAULT_HANGAM = "AAAAAAAAAA";
    private static final String UPDATED_HANGAM = "BBBBBBBBBB";

    private static final String DEFAULT_HANGAM_MR = "AAAAAAAAAA";
    private static final String UPDATED_HANGAM_MR = "BBBBBBBBBB";

    private static final String DEFAULT_FACTORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FACTORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FACTORY_NAME_MR = "AAAAAAAAAA";
    private static final String UPDATED_FACTORY_NAME_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;
    private static final Double SMALLER_TOTAL_AMOUNT = 1D - 1D;

    private static final String DEFAULT_TOTAL_AMOUNT_MR = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_AMOUNT_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_BRANCH_CODE = 1L;
    private static final Long UPDATED_BRANCH_CODE = 2L;
    private static final Long SMALLER_BRANCH_CODE = 1L - 1L;

    private static final String DEFAULT_PACS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/karkhana-vasuli-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KarkhanaVasuliFileRepository karkhanaVasuliFileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKarkhanaVasuliFileMockMvc;

    private KarkhanaVasuliFile karkhanaVasuliFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KarkhanaVasuliFile createEntity(EntityManager em) {
        KarkhanaVasuliFile karkhanaVasuliFile = new KarkhanaVasuliFile()
            .fileName(DEFAULT_FILE_NAME)
            .uniqueFileName(DEFAULT_UNIQUE_FILE_NAME)
            .address(DEFAULT_ADDRESS)
            .addressMr(DEFAULT_ADDRESS_MR)
            .hangam(DEFAULT_HANGAM)
            .hangamMr(DEFAULT_HANGAM_MR)
            .factoryName(DEFAULT_FACTORY_NAME)
            .factoryNameMr(DEFAULT_FACTORY_NAME_MR)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .totalAmountMr(DEFAULT_TOTAL_AMOUNT_MR)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .branchCode(DEFAULT_BRANCH_CODE)
            .pacsName(DEFAULT_PACS_NAME);
        return karkhanaVasuliFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KarkhanaVasuliFile createUpdatedEntity(EntityManager em) {
        KarkhanaVasuliFile karkhanaVasuliFile = new KarkhanaVasuliFile()
            .fileName(UPDATED_FILE_NAME)
            .uniqueFileName(UPDATED_UNIQUE_FILE_NAME)
            .address(UPDATED_ADDRESS)
            .addressMr(UPDATED_ADDRESS_MR)
            .hangam(UPDATED_HANGAM)
            .hangamMr(UPDATED_HANGAM_MR)
            .factoryName(UPDATED_FACTORY_NAME)
            .factoryNameMr(UPDATED_FACTORY_NAME_MR)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountMr(UPDATED_TOTAL_AMOUNT_MR)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .branchCode(UPDATED_BRANCH_CODE)
            .pacsName(UPDATED_PACS_NAME);
        return karkhanaVasuliFile;
    }

    @BeforeEach
    public void initTest() {
        karkhanaVasuliFile = createEntity(em);
    }

    @Test
    @Transactional
    void createKarkhanaVasuliFile() throws Exception {
        int databaseSizeBeforeCreate = karkhanaVasuliFileRepository.findAll().size();
        // Create the KarkhanaVasuliFile
        restKarkhanaVasuliFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliFile))
            )
            .andExpect(status().isCreated());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeCreate + 1);
        KarkhanaVasuliFile testKarkhanaVasuliFile = karkhanaVasuliFileList.get(karkhanaVasuliFileList.size() - 1);
        assertThat(testKarkhanaVasuliFile.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testKarkhanaVasuliFile.getUniqueFileName()).isEqualTo(DEFAULT_UNIQUE_FILE_NAME);
        assertThat(testKarkhanaVasuliFile.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testKarkhanaVasuliFile.getAddressMr()).isEqualTo(DEFAULT_ADDRESS_MR);
        assertThat(testKarkhanaVasuliFile.getHangam()).isEqualTo(DEFAULT_HANGAM);
        assertThat(testKarkhanaVasuliFile.getHangamMr()).isEqualTo(DEFAULT_HANGAM_MR);
        assertThat(testKarkhanaVasuliFile.getFactoryName()).isEqualTo(DEFAULT_FACTORY_NAME);
        assertThat(testKarkhanaVasuliFile.getFactoryNameMr()).isEqualTo(DEFAULT_FACTORY_NAME_MR);
        assertThat(testKarkhanaVasuliFile.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testKarkhanaVasuliFile.getTotalAmountMr()).isEqualTo(DEFAULT_TOTAL_AMOUNT_MR);
        assertThat(testKarkhanaVasuliFile.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testKarkhanaVasuliFile.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testKarkhanaVasuliFile.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testKarkhanaVasuliFile.getPacsName()).isEqualTo(DEFAULT_PACS_NAME);
    }

    @Test
    @Transactional
    void createKarkhanaVasuliFileWithExistingId() throws Exception {
        // Create the KarkhanaVasuliFile with an existing ID
        karkhanaVasuliFile.setId(1L);

        int databaseSizeBeforeCreate = karkhanaVasuliFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKarkhanaVasuliFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFiles() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList
        restKarkhanaVasuliFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(karkhanaVasuliFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].uniqueFileName").value(hasItem(DEFAULT_UNIQUE_FILE_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].addressMr").value(hasItem(DEFAULT_ADDRESS_MR)))
            .andExpect(jsonPath("$.[*].hangam").value(hasItem(DEFAULT_HANGAM)))
            .andExpect(jsonPath("$.[*].hangamMr").value(hasItem(DEFAULT_HANGAM_MR)))
            .andExpect(jsonPath("$.[*].factoryName").value(hasItem(DEFAULT_FACTORY_NAME)))
            .andExpect(jsonPath("$.[*].factoryNameMr").value(hasItem(DEFAULT_FACTORY_NAME_MR)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmountMr").value(hasItem(DEFAULT_TOTAL_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE.intValue())))
            .andExpect(jsonPath("$.[*].pacsName").value(hasItem(DEFAULT_PACS_NAME)));
    }

    @Test
    @Transactional
    void getKarkhanaVasuliFile() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get the karkhanaVasuliFile
        restKarkhanaVasuliFileMockMvc
            .perform(get(ENTITY_API_URL_ID, karkhanaVasuliFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(karkhanaVasuliFile.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.uniqueFileName").value(DEFAULT_UNIQUE_FILE_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.addressMr").value(DEFAULT_ADDRESS_MR))
            .andExpect(jsonPath("$.hangam").value(DEFAULT_HANGAM))
            .andExpect(jsonPath("$.hangamMr").value(DEFAULT_HANGAM_MR))
            .andExpect(jsonPath("$.factoryName").value(DEFAULT_FACTORY_NAME))
            .andExpect(jsonPath("$.factoryNameMr").value(DEFAULT_FACTORY_NAME_MR))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalAmountMr").value(DEFAULT_TOTAL_AMOUNT_MR))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE.intValue()))
            .andExpect(jsonPath("$.pacsName").value(DEFAULT_PACS_NAME));
    }

    @Test
    @Transactional
    void getKarkhanaVasuliFilesByIdFiltering() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        Long id = karkhanaVasuliFile.getId();

        defaultKarkhanaVasuliFileShouldBeFound("id.equals=" + id);
        defaultKarkhanaVasuliFileShouldNotBeFound("id.notEquals=" + id);

        defaultKarkhanaVasuliFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKarkhanaVasuliFileShouldNotBeFound("id.greaterThan=" + id);

        defaultKarkhanaVasuliFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKarkhanaVasuliFileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where fileName equals to DEFAULT_FILE_NAME
        defaultKarkhanaVasuliFileShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the karkhanaVasuliFileList where fileName equals to UPDATED_FILE_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultKarkhanaVasuliFileShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the karkhanaVasuliFileList where fileName equals to UPDATED_FILE_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where fileName is not null
        defaultKarkhanaVasuliFileShouldBeFound("fileName.specified=true");

        // Get all the karkhanaVasuliFileList where fileName is null
        defaultKarkhanaVasuliFileShouldNotBeFound("fileName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFileNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where fileName contains DEFAULT_FILE_NAME
        defaultKarkhanaVasuliFileShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the karkhanaVasuliFileList where fileName contains UPDATED_FILE_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where fileName does not contain DEFAULT_FILE_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the karkhanaVasuliFileList where fileName does not contain UPDATED_FILE_NAME
        defaultKarkhanaVasuliFileShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByUniqueFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where uniqueFileName equals to DEFAULT_UNIQUE_FILE_NAME
        defaultKarkhanaVasuliFileShouldBeFound("uniqueFileName.equals=" + DEFAULT_UNIQUE_FILE_NAME);

        // Get all the karkhanaVasuliFileList where uniqueFileName equals to UPDATED_UNIQUE_FILE_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("uniqueFileName.equals=" + UPDATED_UNIQUE_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByUniqueFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where uniqueFileName in DEFAULT_UNIQUE_FILE_NAME or UPDATED_UNIQUE_FILE_NAME
        defaultKarkhanaVasuliFileShouldBeFound("uniqueFileName.in=" + DEFAULT_UNIQUE_FILE_NAME + "," + UPDATED_UNIQUE_FILE_NAME);

        // Get all the karkhanaVasuliFileList where uniqueFileName equals to UPDATED_UNIQUE_FILE_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("uniqueFileName.in=" + UPDATED_UNIQUE_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByUniqueFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where uniqueFileName is not null
        defaultKarkhanaVasuliFileShouldBeFound("uniqueFileName.specified=true");

        // Get all the karkhanaVasuliFileList where uniqueFileName is null
        defaultKarkhanaVasuliFileShouldNotBeFound("uniqueFileName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByUniqueFileNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where uniqueFileName contains DEFAULT_UNIQUE_FILE_NAME
        defaultKarkhanaVasuliFileShouldBeFound("uniqueFileName.contains=" + DEFAULT_UNIQUE_FILE_NAME);

        // Get all the karkhanaVasuliFileList where uniqueFileName contains UPDATED_UNIQUE_FILE_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("uniqueFileName.contains=" + UPDATED_UNIQUE_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByUniqueFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where uniqueFileName does not contain DEFAULT_UNIQUE_FILE_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("uniqueFileName.doesNotContain=" + DEFAULT_UNIQUE_FILE_NAME);

        // Get all the karkhanaVasuliFileList where uniqueFileName does not contain UPDATED_UNIQUE_FILE_NAME
        defaultKarkhanaVasuliFileShouldBeFound("uniqueFileName.doesNotContain=" + UPDATED_UNIQUE_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where address equals to DEFAULT_ADDRESS
        defaultKarkhanaVasuliFileShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the karkhanaVasuliFileList where address equals to UPDATED_ADDRESS
        defaultKarkhanaVasuliFileShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultKarkhanaVasuliFileShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the karkhanaVasuliFileList where address equals to UPDATED_ADDRESS
        defaultKarkhanaVasuliFileShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where address is not null
        defaultKarkhanaVasuliFileShouldBeFound("address.specified=true");

        // Get all the karkhanaVasuliFileList where address is null
        defaultKarkhanaVasuliFileShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where address contains DEFAULT_ADDRESS
        defaultKarkhanaVasuliFileShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the karkhanaVasuliFileList where address contains UPDATED_ADDRESS
        defaultKarkhanaVasuliFileShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where address does not contain DEFAULT_ADDRESS
        defaultKarkhanaVasuliFileShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the karkhanaVasuliFileList where address does not contain UPDATED_ADDRESS
        defaultKarkhanaVasuliFileShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressMrIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where addressMr equals to DEFAULT_ADDRESS_MR
        defaultKarkhanaVasuliFileShouldBeFound("addressMr.equals=" + DEFAULT_ADDRESS_MR);

        // Get all the karkhanaVasuliFileList where addressMr equals to UPDATED_ADDRESS_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("addressMr.equals=" + UPDATED_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressMrIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where addressMr in DEFAULT_ADDRESS_MR or UPDATED_ADDRESS_MR
        defaultKarkhanaVasuliFileShouldBeFound("addressMr.in=" + DEFAULT_ADDRESS_MR + "," + UPDATED_ADDRESS_MR);

        // Get all the karkhanaVasuliFileList where addressMr equals to UPDATED_ADDRESS_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("addressMr.in=" + UPDATED_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where addressMr is not null
        defaultKarkhanaVasuliFileShouldBeFound("addressMr.specified=true");

        // Get all the karkhanaVasuliFileList where addressMr is null
        defaultKarkhanaVasuliFileShouldNotBeFound("addressMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressMrContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where addressMr contains DEFAULT_ADDRESS_MR
        defaultKarkhanaVasuliFileShouldBeFound("addressMr.contains=" + DEFAULT_ADDRESS_MR);

        // Get all the karkhanaVasuliFileList where addressMr contains UPDATED_ADDRESS_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("addressMr.contains=" + UPDATED_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByAddressMrNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where addressMr does not contain DEFAULT_ADDRESS_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("addressMr.doesNotContain=" + DEFAULT_ADDRESS_MR);

        // Get all the karkhanaVasuliFileList where addressMr does not contain UPDATED_ADDRESS_MR
        defaultKarkhanaVasuliFileShouldBeFound("addressMr.doesNotContain=" + UPDATED_ADDRESS_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangam equals to DEFAULT_HANGAM
        defaultKarkhanaVasuliFileShouldBeFound("hangam.equals=" + DEFAULT_HANGAM);

        // Get all the karkhanaVasuliFileList where hangam equals to UPDATED_HANGAM
        defaultKarkhanaVasuliFileShouldNotBeFound("hangam.equals=" + UPDATED_HANGAM);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangam in DEFAULT_HANGAM or UPDATED_HANGAM
        defaultKarkhanaVasuliFileShouldBeFound("hangam.in=" + DEFAULT_HANGAM + "," + UPDATED_HANGAM);

        // Get all the karkhanaVasuliFileList where hangam equals to UPDATED_HANGAM
        defaultKarkhanaVasuliFileShouldNotBeFound("hangam.in=" + UPDATED_HANGAM);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangam is not null
        defaultKarkhanaVasuliFileShouldBeFound("hangam.specified=true");

        // Get all the karkhanaVasuliFileList where hangam is null
        defaultKarkhanaVasuliFileShouldNotBeFound("hangam.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangam contains DEFAULT_HANGAM
        defaultKarkhanaVasuliFileShouldBeFound("hangam.contains=" + DEFAULT_HANGAM);

        // Get all the karkhanaVasuliFileList where hangam contains UPDATED_HANGAM
        defaultKarkhanaVasuliFileShouldNotBeFound("hangam.contains=" + UPDATED_HANGAM);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangam does not contain DEFAULT_HANGAM
        defaultKarkhanaVasuliFileShouldNotBeFound("hangam.doesNotContain=" + DEFAULT_HANGAM);

        // Get all the karkhanaVasuliFileList where hangam does not contain UPDATED_HANGAM
        defaultKarkhanaVasuliFileShouldBeFound("hangam.doesNotContain=" + UPDATED_HANGAM);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamMrIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangamMr equals to DEFAULT_HANGAM_MR
        defaultKarkhanaVasuliFileShouldBeFound("hangamMr.equals=" + DEFAULT_HANGAM_MR);

        // Get all the karkhanaVasuliFileList where hangamMr equals to UPDATED_HANGAM_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("hangamMr.equals=" + UPDATED_HANGAM_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamMrIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangamMr in DEFAULT_HANGAM_MR or UPDATED_HANGAM_MR
        defaultKarkhanaVasuliFileShouldBeFound("hangamMr.in=" + DEFAULT_HANGAM_MR + "," + UPDATED_HANGAM_MR);

        // Get all the karkhanaVasuliFileList where hangamMr equals to UPDATED_HANGAM_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("hangamMr.in=" + UPDATED_HANGAM_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangamMr is not null
        defaultKarkhanaVasuliFileShouldBeFound("hangamMr.specified=true");

        // Get all the karkhanaVasuliFileList where hangamMr is null
        defaultKarkhanaVasuliFileShouldNotBeFound("hangamMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamMrContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangamMr contains DEFAULT_HANGAM_MR
        defaultKarkhanaVasuliFileShouldBeFound("hangamMr.contains=" + DEFAULT_HANGAM_MR);

        // Get all the karkhanaVasuliFileList where hangamMr contains UPDATED_HANGAM_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("hangamMr.contains=" + UPDATED_HANGAM_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByHangamMrNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where hangamMr does not contain DEFAULT_HANGAM_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("hangamMr.doesNotContain=" + DEFAULT_HANGAM_MR);

        // Get all the karkhanaVasuliFileList where hangamMr does not contain UPDATED_HANGAM_MR
        defaultKarkhanaVasuliFileShouldBeFound("hangamMr.doesNotContain=" + UPDATED_HANGAM_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryName equals to DEFAULT_FACTORY_NAME
        defaultKarkhanaVasuliFileShouldBeFound("factoryName.equals=" + DEFAULT_FACTORY_NAME);

        // Get all the karkhanaVasuliFileList where factoryName equals to UPDATED_FACTORY_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryName.equals=" + UPDATED_FACTORY_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryName in DEFAULT_FACTORY_NAME or UPDATED_FACTORY_NAME
        defaultKarkhanaVasuliFileShouldBeFound("factoryName.in=" + DEFAULT_FACTORY_NAME + "," + UPDATED_FACTORY_NAME);

        // Get all the karkhanaVasuliFileList where factoryName equals to UPDATED_FACTORY_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryName.in=" + UPDATED_FACTORY_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryName is not null
        defaultKarkhanaVasuliFileShouldBeFound("factoryName.specified=true");

        // Get all the karkhanaVasuliFileList where factoryName is null
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryName contains DEFAULT_FACTORY_NAME
        defaultKarkhanaVasuliFileShouldBeFound("factoryName.contains=" + DEFAULT_FACTORY_NAME);

        // Get all the karkhanaVasuliFileList where factoryName contains UPDATED_FACTORY_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryName.contains=" + UPDATED_FACTORY_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryName does not contain DEFAULT_FACTORY_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryName.doesNotContain=" + DEFAULT_FACTORY_NAME);

        // Get all the karkhanaVasuliFileList where factoryName does not contain UPDATED_FACTORY_NAME
        defaultKarkhanaVasuliFileShouldBeFound("factoryName.doesNotContain=" + UPDATED_FACTORY_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameMrIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryNameMr equals to DEFAULT_FACTORY_NAME_MR
        defaultKarkhanaVasuliFileShouldBeFound("factoryNameMr.equals=" + DEFAULT_FACTORY_NAME_MR);

        // Get all the karkhanaVasuliFileList where factoryNameMr equals to UPDATED_FACTORY_NAME_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryNameMr.equals=" + UPDATED_FACTORY_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameMrIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryNameMr in DEFAULT_FACTORY_NAME_MR or UPDATED_FACTORY_NAME_MR
        defaultKarkhanaVasuliFileShouldBeFound("factoryNameMr.in=" + DEFAULT_FACTORY_NAME_MR + "," + UPDATED_FACTORY_NAME_MR);

        // Get all the karkhanaVasuliFileList where factoryNameMr equals to UPDATED_FACTORY_NAME_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryNameMr.in=" + UPDATED_FACTORY_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryNameMr is not null
        defaultKarkhanaVasuliFileShouldBeFound("factoryNameMr.specified=true");

        // Get all the karkhanaVasuliFileList where factoryNameMr is null
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryNameMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameMrContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryNameMr contains DEFAULT_FACTORY_NAME_MR
        defaultKarkhanaVasuliFileShouldBeFound("factoryNameMr.contains=" + DEFAULT_FACTORY_NAME_MR);

        // Get all the karkhanaVasuliFileList where factoryNameMr contains UPDATED_FACTORY_NAME_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryNameMr.contains=" + UPDATED_FACTORY_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryNameMrNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where factoryNameMr does not contain DEFAULT_FACTORY_NAME_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryNameMr.doesNotContain=" + DEFAULT_FACTORY_NAME_MR);

        // Get all the karkhanaVasuliFileList where factoryNameMr does not contain UPDATED_FACTORY_NAME_MR
        defaultKarkhanaVasuliFileShouldBeFound("factoryNameMr.doesNotContain=" + UPDATED_FACTORY_NAME_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the karkhanaVasuliFileList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the karkhanaVasuliFileList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmount is not null
        defaultKarkhanaVasuliFileShouldBeFound("totalAmount.specified=true");

        // Get all the karkhanaVasuliFileList where totalAmount is null
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmount is greater than or equal to DEFAULT_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldBeFound("totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the karkhanaVasuliFileList where totalAmount is greater than or equal to UPDATED_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmount is less than or equal to DEFAULT_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldBeFound("totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the karkhanaVasuliFileList where totalAmount is less than or equal to SMALLER_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmount is less than DEFAULT_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the karkhanaVasuliFileList where totalAmount is less than UPDATED_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldBeFound("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmount is greater than DEFAULT_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the karkhanaVasuliFileList where totalAmount is greater than SMALLER_TOTAL_AMOUNT
        defaultKarkhanaVasuliFileShouldBeFound("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountMrIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmountMr equals to DEFAULT_TOTAL_AMOUNT_MR
        defaultKarkhanaVasuliFileShouldBeFound("totalAmountMr.equals=" + DEFAULT_TOTAL_AMOUNT_MR);

        // Get all the karkhanaVasuliFileList where totalAmountMr equals to UPDATED_TOTAL_AMOUNT_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmountMr.equals=" + UPDATED_TOTAL_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountMrIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmountMr in DEFAULT_TOTAL_AMOUNT_MR or UPDATED_TOTAL_AMOUNT_MR
        defaultKarkhanaVasuliFileShouldBeFound("totalAmountMr.in=" + DEFAULT_TOTAL_AMOUNT_MR + "," + UPDATED_TOTAL_AMOUNT_MR);

        // Get all the karkhanaVasuliFileList where totalAmountMr equals to UPDATED_TOTAL_AMOUNT_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmountMr.in=" + UPDATED_TOTAL_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmountMr is not null
        defaultKarkhanaVasuliFileShouldBeFound("totalAmountMr.specified=true");

        // Get all the karkhanaVasuliFileList where totalAmountMr is null
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmountMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountMrContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmountMr contains DEFAULT_TOTAL_AMOUNT_MR
        defaultKarkhanaVasuliFileShouldBeFound("totalAmountMr.contains=" + DEFAULT_TOTAL_AMOUNT_MR);

        // Get all the karkhanaVasuliFileList where totalAmountMr contains UPDATED_TOTAL_AMOUNT_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmountMr.contains=" + UPDATED_TOTAL_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByTotalAmountMrNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where totalAmountMr does not contain DEFAULT_TOTAL_AMOUNT_MR
        defaultKarkhanaVasuliFileShouldNotBeFound("totalAmountMr.doesNotContain=" + DEFAULT_TOTAL_AMOUNT_MR);

        // Get all the karkhanaVasuliFileList where totalAmountMr does not contain UPDATED_TOTAL_AMOUNT_MR
        defaultKarkhanaVasuliFileShouldBeFound("totalAmountMr.doesNotContain=" + UPDATED_TOTAL_AMOUNT_MR);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where fromDate equals to DEFAULT_FROM_DATE
        defaultKarkhanaVasuliFileShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the karkhanaVasuliFileList where fromDate equals to UPDATED_FROM_DATE
        defaultKarkhanaVasuliFileShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultKarkhanaVasuliFileShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the karkhanaVasuliFileList where fromDate equals to UPDATED_FROM_DATE
        defaultKarkhanaVasuliFileShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where fromDate is not null
        defaultKarkhanaVasuliFileShouldBeFound("fromDate.specified=true");

        // Get all the karkhanaVasuliFileList where fromDate is null
        defaultKarkhanaVasuliFileShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where toDate equals to DEFAULT_TO_DATE
        defaultKarkhanaVasuliFileShouldBeFound("toDate.equals=" + DEFAULT_TO_DATE);

        // Get all the karkhanaVasuliFileList where toDate equals to UPDATED_TO_DATE
        defaultKarkhanaVasuliFileShouldNotBeFound("toDate.equals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByToDateIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where toDate in DEFAULT_TO_DATE or UPDATED_TO_DATE
        defaultKarkhanaVasuliFileShouldBeFound("toDate.in=" + DEFAULT_TO_DATE + "," + UPDATED_TO_DATE);

        // Get all the karkhanaVasuliFileList where toDate equals to UPDATED_TO_DATE
        defaultKarkhanaVasuliFileShouldNotBeFound("toDate.in=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where toDate is not null
        defaultKarkhanaVasuliFileShouldBeFound("toDate.specified=true");

        // Get all the karkhanaVasuliFileList where toDate is null
        defaultKarkhanaVasuliFileShouldNotBeFound("toDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where branchCode equals to DEFAULT_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldBeFound("branchCode.equals=" + DEFAULT_BRANCH_CODE);

        // Get all the karkhanaVasuliFileList where branchCode equals to UPDATED_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldNotBeFound("branchCode.equals=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where branchCode in DEFAULT_BRANCH_CODE or UPDATED_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldBeFound("branchCode.in=" + DEFAULT_BRANCH_CODE + "," + UPDATED_BRANCH_CODE);

        // Get all the karkhanaVasuliFileList where branchCode equals to UPDATED_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldNotBeFound("branchCode.in=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where branchCode is not null
        defaultKarkhanaVasuliFileShouldBeFound("branchCode.specified=true");

        // Get all the karkhanaVasuliFileList where branchCode is null
        defaultKarkhanaVasuliFileShouldNotBeFound("branchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByBranchCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where branchCode is greater than or equal to DEFAULT_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldBeFound("branchCode.greaterThanOrEqual=" + DEFAULT_BRANCH_CODE);

        // Get all the karkhanaVasuliFileList where branchCode is greater than or equal to UPDATED_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldNotBeFound("branchCode.greaterThanOrEqual=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByBranchCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where branchCode is less than or equal to DEFAULT_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldBeFound("branchCode.lessThanOrEqual=" + DEFAULT_BRANCH_CODE);

        // Get all the karkhanaVasuliFileList where branchCode is less than or equal to SMALLER_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldNotBeFound("branchCode.lessThanOrEqual=" + SMALLER_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByBranchCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where branchCode is less than DEFAULT_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldNotBeFound("branchCode.lessThan=" + DEFAULT_BRANCH_CODE);

        // Get all the karkhanaVasuliFileList where branchCode is less than UPDATED_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldBeFound("branchCode.lessThan=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByBranchCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where branchCode is greater than DEFAULT_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldNotBeFound("branchCode.greaterThan=" + DEFAULT_BRANCH_CODE);

        // Get all the karkhanaVasuliFileList where branchCode is greater than SMALLER_BRANCH_CODE
        defaultKarkhanaVasuliFileShouldBeFound("branchCode.greaterThan=" + SMALLER_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByPacsNameIsEqualToSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where pacsName equals to DEFAULT_PACS_NAME
        defaultKarkhanaVasuliFileShouldBeFound("pacsName.equals=" + DEFAULT_PACS_NAME);

        // Get all the karkhanaVasuliFileList where pacsName equals to UPDATED_PACS_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("pacsName.equals=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByPacsNameIsInShouldWork() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where pacsName in DEFAULT_PACS_NAME or UPDATED_PACS_NAME
        defaultKarkhanaVasuliFileShouldBeFound("pacsName.in=" + DEFAULT_PACS_NAME + "," + UPDATED_PACS_NAME);

        // Get all the karkhanaVasuliFileList where pacsName equals to UPDATED_PACS_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("pacsName.in=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByPacsNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where pacsName is not null
        defaultKarkhanaVasuliFileShouldBeFound("pacsName.specified=true");

        // Get all the karkhanaVasuliFileList where pacsName is null
        defaultKarkhanaVasuliFileShouldNotBeFound("pacsName.specified=false");
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByPacsNameContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where pacsName contains DEFAULT_PACS_NAME
        defaultKarkhanaVasuliFileShouldBeFound("pacsName.contains=" + DEFAULT_PACS_NAME);

        // Get all the karkhanaVasuliFileList where pacsName contains UPDATED_PACS_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("pacsName.contains=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByPacsNameNotContainsSomething() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        // Get all the karkhanaVasuliFileList where pacsName does not contain DEFAULT_PACS_NAME
        defaultKarkhanaVasuliFileShouldNotBeFound("pacsName.doesNotContain=" + DEFAULT_PACS_NAME);

        // Get all the karkhanaVasuliFileList where pacsName does not contain UPDATED_PACS_NAME
        defaultKarkhanaVasuliFileShouldBeFound("pacsName.doesNotContain=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByFactoryMasterIsEqualToSomething() throws Exception {
        FactoryMaster factoryMaster;
        if (TestUtil.findAll(em, FactoryMaster.class).isEmpty()) {
            karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);
            factoryMaster = FactoryMasterResourceIT.createEntity(em);
        } else {
            factoryMaster = TestUtil.findAll(em, FactoryMaster.class).get(0);
        }
        em.persist(factoryMaster);
        em.flush();
        karkhanaVasuliFile.setFactoryMaster(factoryMaster);
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);
        Long factoryMasterId = factoryMaster.getId();
        // Get all the karkhanaVasuliFileList where factoryMaster equals to factoryMasterId
        defaultKarkhanaVasuliFileShouldBeFound("factoryMasterId.equals=" + factoryMasterId);

        // Get all the karkhanaVasuliFileList where factoryMaster equals to (factoryMasterId + 1)
        defaultKarkhanaVasuliFileShouldNotBeFound("factoryMasterId.equals=" + (factoryMasterId + 1));
    }

    @Test
    @Transactional
    void getAllKarkhanaVasuliFilesByKarkhanaVasuliRecordsIsEqualToSomething() throws Exception {
        KarkhanaVasuliRecords karkhanaVasuliRecords;
        if (TestUtil.findAll(em, KarkhanaVasuliRecords.class).isEmpty()) {
            karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);
            karkhanaVasuliRecords = KarkhanaVasuliRecordsResourceIT.createEntity(em);
        } else {
            karkhanaVasuliRecords = TestUtil.findAll(em, KarkhanaVasuliRecords.class).get(0);
        }
        em.persist(karkhanaVasuliRecords);
        em.flush();
        karkhanaVasuliFile.addKarkhanaVasuliRecords(karkhanaVasuliRecords);
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);
        Long karkhanaVasuliRecordsId = karkhanaVasuliRecords.getId();
        // Get all the karkhanaVasuliFileList where karkhanaVasuliRecords equals to karkhanaVasuliRecordsId
        defaultKarkhanaVasuliFileShouldBeFound("karkhanaVasuliRecordsId.equals=" + karkhanaVasuliRecordsId);

        // Get all the karkhanaVasuliFileList where karkhanaVasuliRecords equals to (karkhanaVasuliRecordsId + 1)
        defaultKarkhanaVasuliFileShouldNotBeFound("karkhanaVasuliRecordsId.equals=" + (karkhanaVasuliRecordsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKarkhanaVasuliFileShouldBeFound(String filter) throws Exception {
        restKarkhanaVasuliFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(karkhanaVasuliFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].uniqueFileName").value(hasItem(DEFAULT_UNIQUE_FILE_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].addressMr").value(hasItem(DEFAULT_ADDRESS_MR)))
            .andExpect(jsonPath("$.[*].hangam").value(hasItem(DEFAULT_HANGAM)))
            .andExpect(jsonPath("$.[*].hangamMr").value(hasItem(DEFAULT_HANGAM_MR)))
            .andExpect(jsonPath("$.[*].factoryName").value(hasItem(DEFAULT_FACTORY_NAME)))
            .andExpect(jsonPath("$.[*].factoryNameMr").value(hasItem(DEFAULT_FACTORY_NAME_MR)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmountMr").value(hasItem(DEFAULT_TOTAL_AMOUNT_MR)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE.intValue())))
            .andExpect(jsonPath("$.[*].pacsName").value(hasItem(DEFAULT_PACS_NAME)));

        // Check, that the count call also returns 1
        restKarkhanaVasuliFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKarkhanaVasuliFileShouldNotBeFound(String filter) throws Exception {
        restKarkhanaVasuliFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKarkhanaVasuliFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKarkhanaVasuliFile() throws Exception {
        // Get the karkhanaVasuliFile
        restKarkhanaVasuliFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKarkhanaVasuliFile() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        int databaseSizeBeforeUpdate = karkhanaVasuliFileRepository.findAll().size();

        // Update the karkhanaVasuliFile
        KarkhanaVasuliFile updatedKarkhanaVasuliFile = karkhanaVasuliFileRepository.findById(karkhanaVasuliFile.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKarkhanaVasuliFile are not directly saved in db
        em.detach(updatedKarkhanaVasuliFile);
        updatedKarkhanaVasuliFile
            .fileName(UPDATED_FILE_NAME)
            .uniqueFileName(UPDATED_UNIQUE_FILE_NAME)
            .address(UPDATED_ADDRESS)
            .addressMr(UPDATED_ADDRESS_MR)
            .hangam(UPDATED_HANGAM)
            .hangamMr(UPDATED_HANGAM_MR)
            .factoryName(UPDATED_FACTORY_NAME)
            .factoryNameMr(UPDATED_FACTORY_NAME_MR)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountMr(UPDATED_TOTAL_AMOUNT_MR)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .branchCode(UPDATED_BRANCH_CODE)
            .pacsName(UPDATED_PACS_NAME);

        restKarkhanaVasuliFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKarkhanaVasuliFile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKarkhanaVasuliFile))
            )
            .andExpect(status().isOk());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeUpdate);
        KarkhanaVasuliFile testKarkhanaVasuliFile = karkhanaVasuliFileList.get(karkhanaVasuliFileList.size() - 1);
        assertThat(testKarkhanaVasuliFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testKarkhanaVasuliFile.getUniqueFileName()).isEqualTo(UPDATED_UNIQUE_FILE_NAME);
        assertThat(testKarkhanaVasuliFile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testKarkhanaVasuliFile.getAddressMr()).isEqualTo(UPDATED_ADDRESS_MR);
        assertThat(testKarkhanaVasuliFile.getHangam()).isEqualTo(UPDATED_HANGAM);
        assertThat(testKarkhanaVasuliFile.getHangamMr()).isEqualTo(UPDATED_HANGAM_MR);
        assertThat(testKarkhanaVasuliFile.getFactoryName()).isEqualTo(UPDATED_FACTORY_NAME);
        assertThat(testKarkhanaVasuliFile.getFactoryNameMr()).isEqualTo(UPDATED_FACTORY_NAME_MR);
        assertThat(testKarkhanaVasuliFile.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testKarkhanaVasuliFile.getTotalAmountMr()).isEqualTo(UPDATED_TOTAL_AMOUNT_MR);
        assertThat(testKarkhanaVasuliFile.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testKarkhanaVasuliFile.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testKarkhanaVasuliFile.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testKarkhanaVasuliFile.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void putNonExistingKarkhanaVasuliFile() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliFileRepository.findAll().size();
        karkhanaVasuliFile.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKarkhanaVasuliFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, karkhanaVasuliFile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKarkhanaVasuliFile() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliFileRepository.findAll().size();
        karkhanaVasuliFile.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKarkhanaVasuliFile() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliFileRepository.findAll().size();
        karkhanaVasuliFile.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliFileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliFile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKarkhanaVasuliFileWithPatch() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        int databaseSizeBeforeUpdate = karkhanaVasuliFileRepository.findAll().size();

        // Update the karkhanaVasuliFile using partial update
        KarkhanaVasuliFile partialUpdatedKarkhanaVasuliFile = new KarkhanaVasuliFile();
        partialUpdatedKarkhanaVasuliFile.setId(karkhanaVasuliFile.getId());

        partialUpdatedKarkhanaVasuliFile
            .fileName(UPDATED_FILE_NAME)
            .uniqueFileName(UPDATED_UNIQUE_FILE_NAME)
            .hangam(UPDATED_HANGAM)
            .hangamMr(UPDATED_HANGAM_MR)
            .factoryName(UPDATED_FACTORY_NAME)
            .factoryNameMr(UPDATED_FACTORY_NAME_MR)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .branchCode(UPDATED_BRANCH_CODE);

        restKarkhanaVasuliFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKarkhanaVasuliFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKarkhanaVasuliFile))
            )
            .andExpect(status().isOk());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeUpdate);
        KarkhanaVasuliFile testKarkhanaVasuliFile = karkhanaVasuliFileList.get(karkhanaVasuliFileList.size() - 1);
        assertThat(testKarkhanaVasuliFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testKarkhanaVasuliFile.getUniqueFileName()).isEqualTo(UPDATED_UNIQUE_FILE_NAME);
        assertThat(testKarkhanaVasuliFile.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testKarkhanaVasuliFile.getAddressMr()).isEqualTo(DEFAULT_ADDRESS_MR);
        assertThat(testKarkhanaVasuliFile.getHangam()).isEqualTo(UPDATED_HANGAM);
        assertThat(testKarkhanaVasuliFile.getHangamMr()).isEqualTo(UPDATED_HANGAM_MR);
        assertThat(testKarkhanaVasuliFile.getFactoryName()).isEqualTo(UPDATED_FACTORY_NAME);
        assertThat(testKarkhanaVasuliFile.getFactoryNameMr()).isEqualTo(UPDATED_FACTORY_NAME_MR);
        assertThat(testKarkhanaVasuliFile.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testKarkhanaVasuliFile.getTotalAmountMr()).isEqualTo(DEFAULT_TOTAL_AMOUNT_MR);
        assertThat(testKarkhanaVasuliFile.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testKarkhanaVasuliFile.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testKarkhanaVasuliFile.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testKarkhanaVasuliFile.getPacsName()).isEqualTo(DEFAULT_PACS_NAME);
    }

    @Test
    @Transactional
    void fullUpdateKarkhanaVasuliFileWithPatch() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        int databaseSizeBeforeUpdate = karkhanaVasuliFileRepository.findAll().size();

        // Update the karkhanaVasuliFile using partial update
        KarkhanaVasuliFile partialUpdatedKarkhanaVasuliFile = new KarkhanaVasuliFile();
        partialUpdatedKarkhanaVasuliFile.setId(karkhanaVasuliFile.getId());

        partialUpdatedKarkhanaVasuliFile
            .fileName(UPDATED_FILE_NAME)
            .uniqueFileName(UPDATED_UNIQUE_FILE_NAME)
            .address(UPDATED_ADDRESS)
            .addressMr(UPDATED_ADDRESS_MR)
            .hangam(UPDATED_HANGAM)
            .hangamMr(UPDATED_HANGAM_MR)
            .factoryName(UPDATED_FACTORY_NAME)
            .factoryNameMr(UPDATED_FACTORY_NAME_MR)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountMr(UPDATED_TOTAL_AMOUNT_MR)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .branchCode(UPDATED_BRANCH_CODE)
            .pacsName(UPDATED_PACS_NAME);

        restKarkhanaVasuliFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKarkhanaVasuliFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKarkhanaVasuliFile))
            )
            .andExpect(status().isOk());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeUpdate);
        KarkhanaVasuliFile testKarkhanaVasuliFile = karkhanaVasuliFileList.get(karkhanaVasuliFileList.size() - 1);
        assertThat(testKarkhanaVasuliFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testKarkhanaVasuliFile.getUniqueFileName()).isEqualTo(UPDATED_UNIQUE_FILE_NAME);
        assertThat(testKarkhanaVasuliFile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testKarkhanaVasuliFile.getAddressMr()).isEqualTo(UPDATED_ADDRESS_MR);
        assertThat(testKarkhanaVasuliFile.getHangam()).isEqualTo(UPDATED_HANGAM);
        assertThat(testKarkhanaVasuliFile.getHangamMr()).isEqualTo(UPDATED_HANGAM_MR);
        assertThat(testKarkhanaVasuliFile.getFactoryName()).isEqualTo(UPDATED_FACTORY_NAME);
        assertThat(testKarkhanaVasuliFile.getFactoryNameMr()).isEqualTo(UPDATED_FACTORY_NAME_MR);
        assertThat(testKarkhanaVasuliFile.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testKarkhanaVasuliFile.getTotalAmountMr()).isEqualTo(UPDATED_TOTAL_AMOUNT_MR);
        assertThat(testKarkhanaVasuliFile.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testKarkhanaVasuliFile.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testKarkhanaVasuliFile.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testKarkhanaVasuliFile.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingKarkhanaVasuliFile() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliFileRepository.findAll().size();
        karkhanaVasuliFile.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKarkhanaVasuliFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, karkhanaVasuliFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKarkhanaVasuliFile() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliFileRepository.findAll().size();
        karkhanaVasuliFile.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKarkhanaVasuliFile() throws Exception {
        int databaseSizeBeforeUpdate = karkhanaVasuliFileRepository.findAll().size();
        karkhanaVasuliFile.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKarkhanaVasuliFileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(karkhanaVasuliFile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KarkhanaVasuliFile in the database
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKarkhanaVasuliFile() throws Exception {
        // Initialize the database
        karkhanaVasuliFileRepository.saveAndFlush(karkhanaVasuliFile);

        int databaseSizeBeforeDelete = karkhanaVasuliFileRepository.findAll().size();

        // Delete the karkhanaVasuliFile
        restKarkhanaVasuliFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, karkhanaVasuliFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KarkhanaVasuliFile> karkhanaVasuliFileList = karkhanaVasuliFileRepository.findAll();
        assertThat(karkhanaVasuliFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
