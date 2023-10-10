package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.service.criteria.IssPortalFileCriteria;
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
 * Integration tests for the {@link IssPortalFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IssPortalFileResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_EXTENSION = "BBBBBBBBBB";

    private static final Long DEFAULT_BRANCH_CODE = 1L;
    private static final Long UPDATED_BRANCH_CODE = 2L;
    private static final Long SMALLER_BRANCH_CODE = 1L - 1L;

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM_DISBURSEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DISBURSEMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM_DISBURSEMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO_DISBURSEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DISBURSEMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO_DISBURSEMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_PACS_CODE = 1L;
    private static final Long UPDATED_PACS_CODE = 2L;
    private static final Long SMALLER_PACS_CODE = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION_COUNT = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_COUNT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/iss-portal-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssPortalFileRepository issPortalFileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssPortalFileMockMvc;

    private IssPortalFile issPortalFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssPortalFile createEntity(EntityManager em) {
        IssPortalFile issPortalFile = new IssPortalFile()
            .fileName(DEFAULT_FILE_NAME)
            .fileExtension(DEFAULT_FILE_EXTENSION)
            .branchCode(DEFAULT_BRANCH_CODE)
            .financialYear(DEFAULT_FINANCIAL_YEAR)
            .fromDisbursementDate(DEFAULT_FROM_DISBURSEMENT_DATE)
            .toDisbursementDate(DEFAULT_TO_DISBURSEMENT_DATE)
            .pacsCode(DEFAULT_PACS_CODE)
            .status(DEFAULT_STATUS)
            .applicationCount(DEFAULT_APPLICATION_COUNT)
            .notes(DEFAULT_NOTES);
        return issPortalFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssPortalFile createUpdatedEntity(EntityManager em) {
        IssPortalFile issPortalFile = new IssPortalFile()
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .branchCode(UPDATED_BRANCH_CODE)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .fromDisbursementDate(UPDATED_FROM_DISBURSEMENT_DATE)
            .toDisbursementDate(UPDATED_TO_DISBURSEMENT_DATE)
            .pacsCode(UPDATED_PACS_CODE)
            .status(UPDATED_STATUS)
            .applicationCount(UPDATED_APPLICATION_COUNT)
            .notes(UPDATED_NOTES);
        return issPortalFile;
    }

    @BeforeEach
    public void initTest() {
        issPortalFile = createEntity(em);
    }

    @Test
    @Transactional
    void createIssPortalFile() throws Exception {
        int databaseSizeBeforeCreate = issPortalFileRepository.findAll().size();
        // Create the IssPortalFile
        restIssPortalFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issPortalFile)))
            .andExpect(status().isCreated());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeCreate + 1);
        IssPortalFile testIssPortalFile = issPortalFileList.get(issPortalFileList.size() - 1);
        assertThat(testIssPortalFile.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testIssPortalFile.getFileExtension()).isEqualTo(DEFAULT_FILE_EXTENSION);
        assertThat(testIssPortalFile.getSchemeWiseBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testIssPortalFile.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testIssPortalFile.getFromDisbursementDate()).isEqualTo(DEFAULT_FROM_DISBURSEMENT_DATE);
        assertThat(testIssPortalFile.getToDisbursementDate()).isEqualTo(DEFAULT_TO_DISBURSEMENT_DATE);
        assertThat(testIssPortalFile.getPacsCode()).isEqualTo(DEFAULT_PACS_CODE);
        assertThat(testIssPortalFile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testIssPortalFile.getApplicationCount()).isEqualTo(DEFAULT_APPLICATION_COUNT);
        assertThat(testIssPortalFile.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createIssPortalFileWithExistingId() throws Exception {
        // Create the IssPortalFile with an existing ID
        issPortalFile.setId(1L);

        int databaseSizeBeforeCreate = issPortalFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssPortalFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issPortalFile)))
            .andExpect(status().isBadRequest());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIssPortalFiles() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList
        restIssPortalFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issPortalFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileExtension").value(hasItem(DEFAULT_FILE_EXTENSION)))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE.intValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].fromDisbursementDate").value(hasItem(DEFAULT_FROM_DISBURSEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDisbursementDate").value(hasItem(DEFAULT_TO_DISBURSEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].pacsCode").value(hasItem(DEFAULT_PACS_CODE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].applicationCount").value(hasItem(DEFAULT_APPLICATION_COUNT)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getIssPortalFile() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get the issPortalFile
        restIssPortalFileMockMvc
            .perform(get(ENTITY_API_URL_ID, issPortalFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issPortalFile.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileExtension").value(DEFAULT_FILE_EXTENSION))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE.intValue()))
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.fromDisbursementDate").value(DEFAULT_FROM_DISBURSEMENT_DATE.toString()))
            .andExpect(jsonPath("$.toDisbursementDate").value(DEFAULT_TO_DISBURSEMENT_DATE.toString()))
            .andExpect(jsonPath("$.pacsCode").value(DEFAULT_PACS_CODE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.applicationCount").value(DEFAULT_APPLICATION_COUNT))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getIssPortalFilesByIdFiltering() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        Long id = issPortalFile.getId();

        defaultIssPortalFileShouldBeFound("id.equals=" + id);
        defaultIssPortalFileShouldNotBeFound("id.notEquals=" + id);

        defaultIssPortalFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIssPortalFileShouldNotBeFound("id.greaterThan=" + id);

        defaultIssPortalFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIssPortalFileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileName equals to DEFAULT_FILE_NAME
        defaultIssPortalFileShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the issPortalFileList where fileName equals to UPDATED_FILE_NAME
        defaultIssPortalFileShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultIssPortalFileShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the issPortalFileList where fileName equals to UPDATED_FILE_NAME
        defaultIssPortalFileShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileName is not null
        defaultIssPortalFileShouldBeFound("fileName.specified=true");

        // Get all the issPortalFileList where fileName is null
        defaultIssPortalFileShouldNotBeFound("fileName.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileNameContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileName contains DEFAULT_FILE_NAME
        defaultIssPortalFileShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the issPortalFileList where fileName contains UPDATED_FILE_NAME
        defaultIssPortalFileShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileName does not contain DEFAULT_FILE_NAME
        defaultIssPortalFileShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the issPortalFileList where fileName does not contain UPDATED_FILE_NAME
        defaultIssPortalFileShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileExtensionIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileExtension equals to DEFAULT_FILE_EXTENSION
        defaultIssPortalFileShouldBeFound("fileExtension.equals=" + DEFAULT_FILE_EXTENSION);

        // Get all the issPortalFileList where fileExtension equals to UPDATED_FILE_EXTENSION
        defaultIssPortalFileShouldNotBeFound("fileExtension.equals=" + UPDATED_FILE_EXTENSION);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileExtensionIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileExtension in DEFAULT_FILE_EXTENSION or UPDATED_FILE_EXTENSION
        defaultIssPortalFileShouldBeFound("fileExtension.in=" + DEFAULT_FILE_EXTENSION + "," + UPDATED_FILE_EXTENSION);

        // Get all the issPortalFileList where fileExtension equals to UPDATED_FILE_EXTENSION
        defaultIssPortalFileShouldNotBeFound("fileExtension.in=" + UPDATED_FILE_EXTENSION);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileExtensionIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileExtension is not null
        defaultIssPortalFileShouldBeFound("fileExtension.specified=true");

        // Get all the issPortalFileList where fileExtension is null
        defaultIssPortalFileShouldNotBeFound("fileExtension.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileExtensionContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileExtension contains DEFAULT_FILE_EXTENSION
        defaultIssPortalFileShouldBeFound("fileExtension.contains=" + DEFAULT_FILE_EXTENSION);

        // Get all the issPortalFileList where fileExtension contains UPDATED_FILE_EXTENSION
        defaultIssPortalFileShouldNotBeFound("fileExtension.contains=" + UPDATED_FILE_EXTENSION);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFileExtensionNotContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fileExtension does not contain DEFAULT_FILE_EXTENSION
        defaultIssPortalFileShouldNotBeFound("fileExtension.doesNotContain=" + DEFAULT_FILE_EXTENSION);

        // Get all the issPortalFileList where fileExtension does not contain UPDATED_FILE_EXTENSION
        defaultIssPortalFileShouldBeFound("fileExtension.doesNotContain=" + UPDATED_FILE_EXTENSION);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where branchCode equals to DEFAULT_BRANCH_CODE
        defaultIssPortalFileShouldBeFound("branchCode.equals=" + DEFAULT_BRANCH_CODE);

        // Get all the issPortalFileList where branchCode equals to UPDATED_BRANCH_CODE
        defaultIssPortalFileShouldNotBeFound("branchCode.equals=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where branchCode in DEFAULT_BRANCH_CODE or UPDATED_BRANCH_CODE
        defaultIssPortalFileShouldBeFound("branchCode.in=" + DEFAULT_BRANCH_CODE + "," + UPDATED_BRANCH_CODE);

        // Get all the issPortalFileList where branchCode equals to UPDATED_BRANCH_CODE
        defaultIssPortalFileShouldNotBeFound("branchCode.in=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where branchCode is not null
        defaultIssPortalFileShouldBeFound("branchCode.specified=true");

        // Get all the issPortalFileList where branchCode is null
        defaultIssPortalFileShouldNotBeFound("branchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByBranchCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where branchCode is greater than or equal to DEFAULT_BRANCH_CODE
        defaultIssPortalFileShouldBeFound("branchCode.greaterThanOrEqual=" + DEFAULT_BRANCH_CODE);

        // Get all the issPortalFileList where branchCode is greater than or equal to UPDATED_BRANCH_CODE
        defaultIssPortalFileShouldNotBeFound("branchCode.greaterThanOrEqual=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByBranchCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where branchCode is less than or equal to DEFAULT_BRANCH_CODE
        defaultIssPortalFileShouldBeFound("branchCode.lessThanOrEqual=" + DEFAULT_BRANCH_CODE);

        // Get all the issPortalFileList where branchCode is less than or equal to SMALLER_BRANCH_CODE
        defaultIssPortalFileShouldNotBeFound("branchCode.lessThanOrEqual=" + SMALLER_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByBranchCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where branchCode is less than DEFAULT_BRANCH_CODE
        defaultIssPortalFileShouldNotBeFound("branchCode.lessThan=" + DEFAULT_BRANCH_CODE);

        // Get all the issPortalFileList where branchCode is less than UPDATED_BRANCH_CODE
        defaultIssPortalFileShouldBeFound("branchCode.lessThan=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByBranchCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where branchCode is greater than DEFAULT_BRANCH_CODE
        defaultIssPortalFileShouldNotBeFound("branchCode.greaterThan=" + DEFAULT_BRANCH_CODE);

        // Get all the issPortalFileList where branchCode is greater than SMALLER_BRANCH_CODE
        defaultIssPortalFileShouldBeFound("branchCode.greaterThan=" + SMALLER_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultIssPortalFileShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the issPortalFileList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultIssPortalFileShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultIssPortalFileShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the issPortalFileList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultIssPortalFileShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where financialYear is not null
        defaultIssPortalFileShouldBeFound("financialYear.specified=true");

        // Get all the issPortalFileList where financialYear is null
        defaultIssPortalFileShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultIssPortalFileShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the issPortalFileList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultIssPortalFileShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultIssPortalFileShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the issPortalFileList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultIssPortalFileShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFromDisbursementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fromDisbursementDate equals to DEFAULT_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("fromDisbursementDate.equals=" + DEFAULT_FROM_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where fromDisbursementDate equals to UPDATED_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("fromDisbursementDate.equals=" + UPDATED_FROM_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFromDisbursementDateIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fromDisbursementDate in DEFAULT_FROM_DISBURSEMENT_DATE or UPDATED_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound(
            "fromDisbursementDate.in=" + DEFAULT_FROM_DISBURSEMENT_DATE + "," + UPDATED_FROM_DISBURSEMENT_DATE
        );

        // Get all the issPortalFileList where fromDisbursementDate equals to UPDATED_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("fromDisbursementDate.in=" + UPDATED_FROM_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFromDisbursementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fromDisbursementDate is not null
        defaultIssPortalFileShouldBeFound("fromDisbursementDate.specified=true");

        // Get all the issPortalFileList where fromDisbursementDate is null
        defaultIssPortalFileShouldNotBeFound("fromDisbursementDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFromDisbursementDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fromDisbursementDate is greater than or equal to DEFAULT_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("fromDisbursementDate.greaterThanOrEqual=" + DEFAULT_FROM_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where fromDisbursementDate is greater than or equal to UPDATED_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("fromDisbursementDate.greaterThanOrEqual=" + UPDATED_FROM_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFromDisbursementDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fromDisbursementDate is less than or equal to DEFAULT_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("fromDisbursementDate.lessThanOrEqual=" + DEFAULT_FROM_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where fromDisbursementDate is less than or equal to SMALLER_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("fromDisbursementDate.lessThanOrEqual=" + SMALLER_FROM_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFromDisbursementDateIsLessThanSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fromDisbursementDate is less than DEFAULT_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("fromDisbursementDate.lessThan=" + DEFAULT_FROM_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where fromDisbursementDate is less than UPDATED_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("fromDisbursementDate.lessThan=" + UPDATED_FROM_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByFromDisbursementDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where fromDisbursementDate is greater than DEFAULT_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("fromDisbursementDate.greaterThan=" + DEFAULT_FROM_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where fromDisbursementDate is greater than SMALLER_FROM_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("fromDisbursementDate.greaterThan=" + SMALLER_FROM_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByToDisbursementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where toDisbursementDate equals to DEFAULT_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("toDisbursementDate.equals=" + DEFAULT_TO_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where toDisbursementDate equals to UPDATED_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("toDisbursementDate.equals=" + UPDATED_TO_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByToDisbursementDateIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where toDisbursementDate in DEFAULT_TO_DISBURSEMENT_DATE or UPDATED_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("toDisbursementDate.in=" + DEFAULT_TO_DISBURSEMENT_DATE + "," + UPDATED_TO_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where toDisbursementDate equals to UPDATED_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("toDisbursementDate.in=" + UPDATED_TO_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByToDisbursementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where toDisbursementDate is not null
        defaultIssPortalFileShouldBeFound("toDisbursementDate.specified=true");

        // Get all the issPortalFileList where toDisbursementDate is null
        defaultIssPortalFileShouldNotBeFound("toDisbursementDate.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByToDisbursementDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where toDisbursementDate is greater than or equal to DEFAULT_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("toDisbursementDate.greaterThanOrEqual=" + DEFAULT_TO_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where toDisbursementDate is greater than or equal to UPDATED_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("toDisbursementDate.greaterThanOrEqual=" + UPDATED_TO_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByToDisbursementDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where toDisbursementDate is less than or equal to DEFAULT_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("toDisbursementDate.lessThanOrEqual=" + DEFAULT_TO_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where toDisbursementDate is less than or equal to SMALLER_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("toDisbursementDate.lessThanOrEqual=" + SMALLER_TO_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByToDisbursementDateIsLessThanSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where toDisbursementDate is less than DEFAULT_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("toDisbursementDate.lessThan=" + DEFAULT_TO_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where toDisbursementDate is less than UPDATED_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("toDisbursementDate.lessThan=" + UPDATED_TO_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByToDisbursementDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where toDisbursementDate is greater than DEFAULT_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldNotBeFound("toDisbursementDate.greaterThan=" + DEFAULT_TO_DISBURSEMENT_DATE);

        // Get all the issPortalFileList where toDisbursementDate is greater than SMALLER_TO_DISBURSEMENT_DATE
        defaultIssPortalFileShouldBeFound("toDisbursementDate.greaterThan=" + SMALLER_TO_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByPacsCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where pacsCode equals to DEFAULT_PACS_CODE
        defaultIssPortalFileShouldBeFound("pacsCode.equals=" + DEFAULT_PACS_CODE);

        // Get all the issPortalFileList where pacsCode equals to UPDATED_PACS_CODE
        defaultIssPortalFileShouldNotBeFound("pacsCode.equals=" + UPDATED_PACS_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByPacsCodeIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where pacsCode in DEFAULT_PACS_CODE or UPDATED_PACS_CODE
        defaultIssPortalFileShouldBeFound("pacsCode.in=" + DEFAULT_PACS_CODE + "," + UPDATED_PACS_CODE);

        // Get all the issPortalFileList where pacsCode equals to UPDATED_PACS_CODE
        defaultIssPortalFileShouldNotBeFound("pacsCode.in=" + UPDATED_PACS_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByPacsCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where pacsCode is not null
        defaultIssPortalFileShouldBeFound("pacsCode.specified=true");

        // Get all the issPortalFileList where pacsCode is null
        defaultIssPortalFileShouldNotBeFound("pacsCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByPacsCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where pacsCode is greater than or equal to DEFAULT_PACS_CODE
        defaultIssPortalFileShouldBeFound("pacsCode.greaterThanOrEqual=" + DEFAULT_PACS_CODE);

        // Get all the issPortalFileList where pacsCode is greater than or equal to UPDATED_PACS_CODE
        defaultIssPortalFileShouldNotBeFound("pacsCode.greaterThanOrEqual=" + UPDATED_PACS_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByPacsCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where pacsCode is less than or equal to DEFAULT_PACS_CODE
        defaultIssPortalFileShouldBeFound("pacsCode.lessThanOrEqual=" + DEFAULT_PACS_CODE);

        // Get all the issPortalFileList where pacsCode is less than or equal to SMALLER_PACS_CODE
        defaultIssPortalFileShouldNotBeFound("pacsCode.lessThanOrEqual=" + SMALLER_PACS_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByPacsCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where pacsCode is less than DEFAULT_PACS_CODE
        defaultIssPortalFileShouldNotBeFound("pacsCode.lessThan=" + DEFAULT_PACS_CODE);

        // Get all the issPortalFileList where pacsCode is less than UPDATED_PACS_CODE
        defaultIssPortalFileShouldBeFound("pacsCode.lessThan=" + UPDATED_PACS_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByPacsCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where pacsCode is greater than DEFAULT_PACS_CODE
        defaultIssPortalFileShouldNotBeFound("pacsCode.greaterThan=" + DEFAULT_PACS_CODE);

        // Get all the issPortalFileList where pacsCode is greater than SMALLER_PACS_CODE
        defaultIssPortalFileShouldBeFound("pacsCode.greaterThan=" + SMALLER_PACS_CODE);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where status equals to DEFAULT_STATUS
        defaultIssPortalFileShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the issPortalFileList where status equals to UPDATED_STATUS
        defaultIssPortalFileShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultIssPortalFileShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the issPortalFileList where status equals to UPDATED_STATUS
        defaultIssPortalFileShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where status is not null
        defaultIssPortalFileShouldBeFound("status.specified=true");

        // Get all the issPortalFileList where status is null
        defaultIssPortalFileShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByStatusContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where status contains DEFAULT_STATUS
        defaultIssPortalFileShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the issPortalFileList where status contains UPDATED_STATUS
        defaultIssPortalFileShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where status does not contain DEFAULT_STATUS
        defaultIssPortalFileShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the issPortalFileList where status does not contain UPDATED_STATUS
        defaultIssPortalFileShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByApplicationCountIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where applicationCount equals to DEFAULT_APPLICATION_COUNT
        defaultIssPortalFileShouldBeFound("applicationCount.equals=" + DEFAULT_APPLICATION_COUNT);

        // Get all the issPortalFileList where applicationCount equals to UPDATED_APPLICATION_COUNT
        defaultIssPortalFileShouldNotBeFound("applicationCount.equals=" + UPDATED_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByApplicationCountIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where applicationCount in DEFAULT_APPLICATION_COUNT or UPDATED_APPLICATION_COUNT
        defaultIssPortalFileShouldBeFound("applicationCount.in=" + DEFAULT_APPLICATION_COUNT + "," + UPDATED_APPLICATION_COUNT);

        // Get all the issPortalFileList where applicationCount equals to UPDATED_APPLICATION_COUNT
        defaultIssPortalFileShouldNotBeFound("applicationCount.in=" + UPDATED_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByApplicationCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where applicationCount is not null
        defaultIssPortalFileShouldBeFound("applicationCount.specified=true");

        // Get all the issPortalFileList where applicationCount is null
        defaultIssPortalFileShouldNotBeFound("applicationCount.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByApplicationCountContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where applicationCount contains DEFAULT_APPLICATION_COUNT
        defaultIssPortalFileShouldBeFound("applicationCount.contains=" + DEFAULT_APPLICATION_COUNT);

        // Get all the issPortalFileList where applicationCount contains UPDATED_APPLICATION_COUNT
        defaultIssPortalFileShouldNotBeFound("applicationCount.contains=" + UPDATED_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByApplicationCountNotContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where applicationCount does not contain DEFAULT_APPLICATION_COUNT
        defaultIssPortalFileShouldNotBeFound("applicationCount.doesNotContain=" + DEFAULT_APPLICATION_COUNT);

        // Get all the issPortalFileList where applicationCount does not contain UPDATED_APPLICATION_COUNT
        defaultIssPortalFileShouldBeFound("applicationCount.doesNotContain=" + UPDATED_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where notes equals to DEFAULT_NOTES
        defaultIssPortalFileShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the issPortalFileList where notes equals to UPDATED_NOTES
        defaultIssPortalFileShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultIssPortalFileShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the issPortalFileList where notes equals to UPDATED_NOTES
        defaultIssPortalFileShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where notes is not null
        defaultIssPortalFileShouldBeFound("notes.specified=true");

        // Get all the issPortalFileList where notes is null
        defaultIssPortalFileShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByNotesContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where notes contains DEFAULT_NOTES
        defaultIssPortalFileShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the issPortalFileList where notes contains UPDATED_NOTES
        defaultIssPortalFileShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllIssPortalFilesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        // Get all the issPortalFileList where notes does not contain DEFAULT_NOTES
        defaultIssPortalFileShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the issPortalFileList where notes does not contain UPDATED_NOTES
        defaultIssPortalFileShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIssPortalFileShouldBeFound(String filter) throws Exception {
        restIssPortalFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issPortalFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileExtension").value(hasItem(DEFAULT_FILE_EXTENSION)))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE.intValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].fromDisbursementDate").value(hasItem(DEFAULT_FROM_DISBURSEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDisbursementDate").value(hasItem(DEFAULT_TO_DISBURSEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].pacsCode").value(hasItem(DEFAULT_PACS_CODE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].applicationCount").value(hasItem(DEFAULT_APPLICATION_COUNT)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));

        // Check, that the count call also returns 1
        restIssPortalFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIssPortalFileShouldNotBeFound(String filter) throws Exception {
        restIssPortalFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIssPortalFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIssPortalFile() throws Exception {
        // Get the issPortalFile
        restIssPortalFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIssPortalFile() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        int databaseSizeBeforeUpdate = issPortalFileRepository.findAll().size();

        // Update the issPortalFile
        IssPortalFile updatedIssPortalFile = issPortalFileRepository.findById(issPortalFile.getId()).get();
        // Disconnect from session so that the updates on updatedIssPortalFile are not directly saved in db
        em.detach(updatedIssPortalFile);
        updatedIssPortalFile
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .branchCode(UPDATED_BRANCH_CODE)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .fromDisbursementDate(UPDATED_FROM_DISBURSEMENT_DATE)
            .toDisbursementDate(UPDATED_TO_DISBURSEMENT_DATE)
            .pacsCode(UPDATED_PACS_CODE)
            .status(UPDATED_STATUS)
            .applicationCount(UPDATED_APPLICATION_COUNT)
            .notes(UPDATED_NOTES);

        restIssPortalFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIssPortalFile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIssPortalFile))
            )
            .andExpect(status().isOk());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeUpdate);
        IssPortalFile testIssPortalFile = issPortalFileList.get(issPortalFileList.size() - 1);
        assertThat(testIssPortalFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testIssPortalFile.getFileExtension()).isEqualTo(UPDATED_FILE_EXTENSION);
        assertThat(testIssPortalFile.getSchemeWiseBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testIssPortalFile.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testIssPortalFile.getFromDisbursementDate()).isEqualTo(UPDATED_FROM_DISBURSEMENT_DATE);
        assertThat(testIssPortalFile.getToDisbursementDate()).isEqualTo(UPDATED_TO_DISBURSEMENT_DATE);
        assertThat(testIssPortalFile.getPacsCode()).isEqualTo(UPDATED_PACS_CODE);
        assertThat(testIssPortalFile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testIssPortalFile.getApplicationCount()).isEqualTo(UPDATED_APPLICATION_COUNT);
        assertThat(testIssPortalFile.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingIssPortalFile() throws Exception {
        int databaseSizeBeforeUpdate = issPortalFileRepository.findAll().size();
        issPortalFile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssPortalFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, issPortalFile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issPortalFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIssPortalFile() throws Exception {
        int databaseSizeBeforeUpdate = issPortalFileRepository.findAll().size();
        issPortalFile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssPortalFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issPortalFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIssPortalFile() throws Exception {
        int databaseSizeBeforeUpdate = issPortalFileRepository.findAll().size();
        issPortalFile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssPortalFileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issPortalFile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIssPortalFileWithPatch() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        int databaseSizeBeforeUpdate = issPortalFileRepository.findAll().size();

        // Update the issPortalFile using partial update
        IssPortalFile partialUpdatedIssPortalFile = new IssPortalFile();
        partialUpdatedIssPortalFile.setId(issPortalFile.getId());

        partialUpdatedIssPortalFile
            .fileName(UPDATED_FILE_NAME)
            .toDisbursementDate(UPDATED_TO_DISBURSEMENT_DATE)
            .pacsCode(UPDATED_PACS_CODE);

        restIssPortalFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssPortalFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssPortalFile))
            )
            .andExpect(status().isOk());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeUpdate);
        IssPortalFile testIssPortalFile = issPortalFileList.get(issPortalFileList.size() - 1);
        assertThat(testIssPortalFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testIssPortalFile.getFileExtension()).isEqualTo(DEFAULT_FILE_EXTENSION);
        assertThat(testIssPortalFile.getSchemeWiseBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testIssPortalFile.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testIssPortalFile.getFromDisbursementDate()).isEqualTo(DEFAULT_FROM_DISBURSEMENT_DATE);
        assertThat(testIssPortalFile.getToDisbursementDate()).isEqualTo(UPDATED_TO_DISBURSEMENT_DATE);
        assertThat(testIssPortalFile.getPacsCode()).isEqualTo(UPDATED_PACS_CODE);
        assertThat(testIssPortalFile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testIssPortalFile.getApplicationCount()).isEqualTo(DEFAULT_APPLICATION_COUNT);
        assertThat(testIssPortalFile.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateIssPortalFileWithPatch() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        int databaseSizeBeforeUpdate = issPortalFileRepository.findAll().size();

        // Update the issPortalFile using partial update
        IssPortalFile partialUpdatedIssPortalFile = new IssPortalFile();
        partialUpdatedIssPortalFile.setId(issPortalFile.getId());

        partialUpdatedIssPortalFile
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .branchCode(UPDATED_BRANCH_CODE)
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .fromDisbursementDate(UPDATED_FROM_DISBURSEMENT_DATE)
            .toDisbursementDate(UPDATED_TO_DISBURSEMENT_DATE)
            .pacsCode(UPDATED_PACS_CODE)
            .status(UPDATED_STATUS)
            .applicationCount(UPDATED_APPLICATION_COUNT)
            .notes(UPDATED_NOTES);

        restIssPortalFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssPortalFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssPortalFile))
            )
            .andExpect(status().isOk());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeUpdate);
        IssPortalFile testIssPortalFile = issPortalFileList.get(issPortalFileList.size() - 1);
        assertThat(testIssPortalFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testIssPortalFile.getFileExtension()).isEqualTo(UPDATED_FILE_EXTENSION);
        assertThat(testIssPortalFile.getSchemeWiseBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testIssPortalFile.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testIssPortalFile.getFromDisbursementDate()).isEqualTo(UPDATED_FROM_DISBURSEMENT_DATE);
        assertThat(testIssPortalFile.getToDisbursementDate()).isEqualTo(UPDATED_TO_DISBURSEMENT_DATE);
        assertThat(testIssPortalFile.getPacsCode()).isEqualTo(UPDATED_PACS_CODE);
        assertThat(testIssPortalFile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testIssPortalFile.getApplicationCount()).isEqualTo(UPDATED_APPLICATION_COUNT);
        assertThat(testIssPortalFile.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingIssPortalFile() throws Exception {
        int databaseSizeBeforeUpdate = issPortalFileRepository.findAll().size();
        issPortalFile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssPortalFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, issPortalFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issPortalFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIssPortalFile() throws Exception {
        int databaseSizeBeforeUpdate = issPortalFileRepository.findAll().size();
        issPortalFile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssPortalFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issPortalFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIssPortalFile() throws Exception {
        int databaseSizeBeforeUpdate = issPortalFileRepository.findAll().size();
        issPortalFile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssPortalFileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(issPortalFile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IssPortalFile in the database
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIssPortalFile() throws Exception {
        // Initialize the database
        issPortalFileRepository.saveAndFlush(issPortalFile);

        int databaseSizeBeforeDelete = issPortalFileRepository.findAll().size();

        // Delete the issPortalFile
        restIssPortalFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, issPortalFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssPortalFile> issPortalFileList = issPortalFileRepository.findAll();
        assertThat(issPortalFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
