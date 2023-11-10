package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.service.ApplicationLogService;
import com.cbs.middleware.service.criteria.ApplicationLogCriteria;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApplicationLogResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicationLogResourceIT {

    private static final String DEFAULT_ERROR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final Long DEFAULT_COLUMN_NUMBER = 1L;
    private static final Long UPDATED_COLUMN_NUMBER = 2L;
    private static final Long SMALLER_COLUMN_NUMBER = 1L - 1L;

    private static final String DEFAULT_SEVIERITY = "AAAAAAAAAA";
    private static final String UPDATED_SEVIERITY = "BBBBBBBBBB";

    private static final String DEFAULT_EXPECTED_SOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_EXPECTED_SOLUTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_ROW_NUMBER = 1L;
    private static final Long UPDATED_ROW_NUMBER = 2L;
    private static final Long SMALLER_ROW_NUMBER = 1L - 1L;

    private static final String DEFAULT_BATCH_ID = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/application-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationLogRepository applicationLogRepository;

    @Mock
    private ApplicationLogRepository applicationLogRepositoryMock;

    @Mock
    private ApplicationLogService applicationLogServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationLogMockMvc;

    private ApplicationLog applicationLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationLog createEntity(EntityManager em) {
        ApplicationLog applicationLog = new ApplicationLog()
            .errorType(DEFAULT_ERROR_TYPE)
            .errorCode(DEFAULT_ERROR_CODE)
            .errorMessage(DEFAULT_ERROR_MESSAGE)
            .columnNumber(DEFAULT_COLUMN_NUMBER)
            .sevierity(DEFAULT_SEVIERITY)
            .expectedSolution(DEFAULT_EXPECTED_SOLUTION)
            .status(DEFAULT_STATUS)
            .rowNumber(DEFAULT_ROW_NUMBER)
            .batchId(DEFAULT_BATCH_ID);
        return applicationLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationLog createUpdatedEntity(EntityManager em) {
        ApplicationLog applicationLog = new ApplicationLog()
            .errorType(UPDATED_ERROR_TYPE)
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .columnNumber(UPDATED_COLUMN_NUMBER)
            .sevierity(UPDATED_SEVIERITY)
            .expectedSolution(UPDATED_EXPECTED_SOLUTION)
            .status(UPDATED_STATUS)
            .rowNumber(UPDATED_ROW_NUMBER)
            .batchId(UPDATED_BATCH_ID);
        return applicationLog;
    }

    @BeforeEach
    public void initTest() {
        applicationLog = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationLog() throws Exception {
        int databaseSizeBeforeCreate = applicationLogRepository.findAll().size();
        // Create the ApplicationLog
        restApplicationLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationLog))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationLog testApplicationLog = applicationLogList.get(applicationLogList.size() - 1);
        assertThat(testApplicationLog.getErrorType()).isEqualTo(DEFAULT_ERROR_TYPE);
        assertThat(testApplicationLog.getErrorCode()).isEqualTo(DEFAULT_ERROR_CODE);
        assertThat(testApplicationLog.getErrorMessage()).isEqualTo(DEFAULT_ERROR_MESSAGE);
        assertThat(testApplicationLog.getColumnNumber()).isEqualTo(DEFAULT_COLUMN_NUMBER);
        assertThat(testApplicationLog.getSevierity()).isEqualTo(DEFAULT_SEVIERITY);
        assertThat(testApplicationLog.getExpectedSolution()).isEqualTo(DEFAULT_EXPECTED_SOLUTION);
        assertThat(testApplicationLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplicationLog.getRowNumber()).isEqualTo(DEFAULT_ROW_NUMBER);
        assertThat(testApplicationLog.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
    }

    @Test
    @Transactional
    void createApplicationLogWithExistingId() throws Exception {
        // Create the ApplicationLog with an existing ID
        applicationLog.setId(1L);

        int databaseSizeBeforeCreate = applicationLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicationLogs() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList
        restApplicationLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorType").value(hasItem(DEFAULT_ERROR_TYPE)))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE)))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)))
            .andExpect(jsonPath("$.[*].columnNumber").value(hasItem(DEFAULT_COLUMN_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].sevierity").value(hasItem(DEFAULT_SEVIERITY)))
            .andExpect(jsonPath("$.[*].expectedSolution").value(hasItem(DEFAULT_EXPECTED_SOLUTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].rowNumber").value(hasItem(DEFAULT_ROW_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)));
    }

    //@SuppressWarnings({ "unchecked" })
    void getAllApplicationLogsWithEagerRelationshipsIsEnabled() throws Exception {
        when(applicationLogServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicationLogMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(applicationLogServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    //@SuppressWarnings({ "unchecked" })
    void getAllApplicationLogsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(applicationLogServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicationLogMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(applicationLogRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getApplicationLog() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get the applicationLog
        restApplicationLogMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationLog.getId().intValue()))
            .andExpect(jsonPath("$.errorType").value(DEFAULT_ERROR_TYPE))
            .andExpect(jsonPath("$.errorCode").value(DEFAULT_ERROR_CODE))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE))
            .andExpect(jsonPath("$.columnNumber").value(DEFAULT_COLUMN_NUMBER.intValue()))
            .andExpect(jsonPath("$.sevierity").value(DEFAULT_SEVIERITY))
            .andExpect(jsonPath("$.expectedSolution").value(DEFAULT_EXPECTED_SOLUTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.rowNumber").value(DEFAULT_ROW_NUMBER.intValue()))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID));
    }

    @Test
    @Transactional
    void getApplicationLogsByIdFiltering() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        Long id = applicationLog.getId();

        defaultApplicationLogShouldBeFound("id.equals=" + id);
        defaultApplicationLogShouldNotBeFound("id.notEquals=" + id);

        defaultApplicationLogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicationLogShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicationLogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicationLogShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorType equals to DEFAULT_ERROR_TYPE
        defaultApplicationLogShouldBeFound("errorType.equals=" + DEFAULT_ERROR_TYPE);

        // Get all the applicationLogList where errorType equals to UPDATED_ERROR_TYPE
        defaultApplicationLogShouldNotBeFound("errorType.equals=" + UPDATED_ERROR_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorTypeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorType in DEFAULT_ERROR_TYPE or UPDATED_ERROR_TYPE
        defaultApplicationLogShouldBeFound("errorType.in=" + DEFAULT_ERROR_TYPE + "," + UPDATED_ERROR_TYPE);

        // Get all the applicationLogList where errorType equals to UPDATED_ERROR_TYPE
        defaultApplicationLogShouldNotBeFound("errorType.in=" + UPDATED_ERROR_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorType is not null
        defaultApplicationLogShouldBeFound("errorType.specified=true");

        // Get all the applicationLogList where errorType is null
        defaultApplicationLogShouldNotBeFound("errorType.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorTypeContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorType contains DEFAULT_ERROR_TYPE
        defaultApplicationLogShouldBeFound("errorType.contains=" + DEFAULT_ERROR_TYPE);

        // Get all the applicationLogList where errorType contains UPDATED_ERROR_TYPE
        defaultApplicationLogShouldNotBeFound("errorType.contains=" + UPDATED_ERROR_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorTypeNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorType does not contain DEFAULT_ERROR_TYPE
        defaultApplicationLogShouldNotBeFound("errorType.doesNotContain=" + DEFAULT_ERROR_TYPE);

        // Get all the applicationLogList where errorType does not contain UPDATED_ERROR_TYPE
        defaultApplicationLogShouldBeFound("errorType.doesNotContain=" + UPDATED_ERROR_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorCode equals to DEFAULT_ERROR_CODE
        defaultApplicationLogShouldBeFound("errorCode.equals=" + DEFAULT_ERROR_CODE);

        // Get all the applicationLogList where errorCode equals to UPDATED_ERROR_CODE
        defaultApplicationLogShouldNotBeFound("errorCode.equals=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorCodeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorCode in DEFAULT_ERROR_CODE or UPDATED_ERROR_CODE
        defaultApplicationLogShouldBeFound("errorCode.in=" + DEFAULT_ERROR_CODE + "," + UPDATED_ERROR_CODE);

        // Get all the applicationLogList where errorCode equals to UPDATED_ERROR_CODE
        defaultApplicationLogShouldNotBeFound("errorCode.in=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorCode is not null
        defaultApplicationLogShouldBeFound("errorCode.specified=true");

        // Get all the applicationLogList where errorCode is null
        defaultApplicationLogShouldNotBeFound("errorCode.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorCodeContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorCode contains DEFAULT_ERROR_CODE
        defaultApplicationLogShouldBeFound("errorCode.contains=" + DEFAULT_ERROR_CODE);

        // Get all the applicationLogList where errorCode contains UPDATED_ERROR_CODE
        defaultApplicationLogShouldNotBeFound("errorCode.contains=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorCodeNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorCode does not contain DEFAULT_ERROR_CODE
        defaultApplicationLogShouldNotBeFound("errorCode.doesNotContain=" + DEFAULT_ERROR_CODE);

        // Get all the applicationLogList where errorCode does not contain UPDATED_ERROR_CODE
        defaultApplicationLogShouldBeFound("errorCode.doesNotContain=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorMessage equals to DEFAULT_ERROR_MESSAGE
        defaultApplicationLogShouldBeFound("errorMessage.equals=" + DEFAULT_ERROR_MESSAGE);

        // Get all the applicationLogList where errorMessage equals to UPDATED_ERROR_MESSAGE
        defaultApplicationLogShouldNotBeFound("errorMessage.equals=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorMessageIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorMessage in DEFAULT_ERROR_MESSAGE or UPDATED_ERROR_MESSAGE
        defaultApplicationLogShouldBeFound("errorMessage.in=" + DEFAULT_ERROR_MESSAGE + "," + UPDATED_ERROR_MESSAGE);

        // Get all the applicationLogList where errorMessage equals to UPDATED_ERROR_MESSAGE
        defaultApplicationLogShouldNotBeFound("errorMessage.in=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorMessage is not null
        defaultApplicationLogShouldBeFound("errorMessage.specified=true");

        // Get all the applicationLogList where errorMessage is null
        defaultApplicationLogShouldNotBeFound("errorMessage.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorMessageContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorMessage contains DEFAULT_ERROR_MESSAGE
        defaultApplicationLogShouldBeFound("errorMessage.contains=" + DEFAULT_ERROR_MESSAGE);

        // Get all the applicationLogList where errorMessage contains UPDATED_ERROR_MESSAGE
        defaultApplicationLogShouldNotBeFound("errorMessage.contains=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByErrorMessageNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where errorMessage does not contain DEFAULT_ERROR_MESSAGE
        defaultApplicationLogShouldNotBeFound("errorMessage.doesNotContain=" + DEFAULT_ERROR_MESSAGE);

        // Get all the applicationLogList where errorMessage does not contain UPDATED_ERROR_MESSAGE
        defaultApplicationLogShouldBeFound("errorMessage.doesNotContain=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByColumnNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where columnNumber equals to DEFAULT_COLUMN_NUMBER
        defaultApplicationLogShouldBeFound("columnNumber.equals=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogList where columnNumber equals to UPDATED_COLUMN_NUMBER
        defaultApplicationLogShouldNotBeFound("columnNumber.equals=" + UPDATED_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByColumnNumberIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where columnNumber in DEFAULT_COLUMN_NUMBER or UPDATED_COLUMN_NUMBER
        defaultApplicationLogShouldBeFound("columnNumber.in=" + DEFAULT_COLUMN_NUMBER + "," + UPDATED_COLUMN_NUMBER);

        // Get all the applicationLogList where columnNumber equals to UPDATED_COLUMN_NUMBER
        defaultApplicationLogShouldNotBeFound("columnNumber.in=" + UPDATED_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByColumnNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where columnNumber is not null
        defaultApplicationLogShouldBeFound("columnNumber.specified=true");

        // Get all the applicationLogList where columnNumber is null
        defaultApplicationLogShouldNotBeFound("columnNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogsByColumnNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where columnNumber is greater than or equal to DEFAULT_COLUMN_NUMBER
        defaultApplicationLogShouldBeFound("columnNumber.greaterThanOrEqual=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogList where columnNumber is greater than or equal to UPDATED_COLUMN_NUMBER
        defaultApplicationLogShouldNotBeFound("columnNumber.greaterThanOrEqual=" + UPDATED_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByColumnNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where columnNumber is less than or equal to DEFAULT_COLUMN_NUMBER
        defaultApplicationLogShouldBeFound("columnNumber.lessThanOrEqual=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogList where columnNumber is less than or equal to SMALLER_COLUMN_NUMBER
        defaultApplicationLogShouldNotBeFound("columnNumber.lessThanOrEqual=" + SMALLER_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByColumnNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where columnNumber is less than DEFAULT_COLUMN_NUMBER
        defaultApplicationLogShouldNotBeFound("columnNumber.lessThan=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogList where columnNumber is less than UPDATED_COLUMN_NUMBER
        defaultApplicationLogShouldBeFound("columnNumber.lessThan=" + UPDATED_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByColumnNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where columnNumber is greater than DEFAULT_COLUMN_NUMBER
        defaultApplicationLogShouldNotBeFound("columnNumber.greaterThan=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogList where columnNumber is greater than SMALLER_COLUMN_NUMBER
        defaultApplicationLogShouldBeFound("columnNumber.greaterThan=" + SMALLER_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsBySevierityIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where sevierity equals to DEFAULT_SEVIERITY
        defaultApplicationLogShouldBeFound("sevierity.equals=" + DEFAULT_SEVIERITY);

        // Get all the applicationLogList where sevierity equals to UPDATED_SEVIERITY
        defaultApplicationLogShouldNotBeFound("sevierity.equals=" + UPDATED_SEVIERITY);
    }

    @Test
    @Transactional
    void getAllApplicationLogsBySevierityIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where sevierity in DEFAULT_SEVIERITY or UPDATED_SEVIERITY
        defaultApplicationLogShouldBeFound("sevierity.in=" + DEFAULT_SEVIERITY + "," + UPDATED_SEVIERITY);

        // Get all the applicationLogList where sevierity equals to UPDATED_SEVIERITY
        defaultApplicationLogShouldNotBeFound("sevierity.in=" + UPDATED_SEVIERITY);
    }

    @Test
    @Transactional
    void getAllApplicationLogsBySevierityIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where sevierity is not null
        defaultApplicationLogShouldBeFound("sevierity.specified=true");

        // Get all the applicationLogList where sevierity is null
        defaultApplicationLogShouldNotBeFound("sevierity.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogsBySevierityContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where sevierity contains DEFAULT_SEVIERITY
        defaultApplicationLogShouldBeFound("sevierity.contains=" + DEFAULT_SEVIERITY);

        // Get all the applicationLogList where sevierity contains UPDATED_SEVIERITY
        defaultApplicationLogShouldNotBeFound("sevierity.contains=" + UPDATED_SEVIERITY);
    }

    @Test
    @Transactional
    void getAllApplicationLogsBySevierityNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where sevierity does not contain DEFAULT_SEVIERITY
        defaultApplicationLogShouldNotBeFound("sevierity.doesNotContain=" + DEFAULT_SEVIERITY);

        // Get all the applicationLogList where sevierity does not contain UPDATED_SEVIERITY
        defaultApplicationLogShouldBeFound("sevierity.doesNotContain=" + UPDATED_SEVIERITY);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByExpectedSolutionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where expectedSolution equals to DEFAULT_EXPECTED_SOLUTION
        defaultApplicationLogShouldBeFound("expectedSolution.equals=" + DEFAULT_EXPECTED_SOLUTION);

        // Get all the applicationLogList where expectedSolution equals to UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogShouldNotBeFound("expectedSolution.equals=" + UPDATED_EXPECTED_SOLUTION);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByExpectedSolutionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where expectedSolution in DEFAULT_EXPECTED_SOLUTION or UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogShouldBeFound("expectedSolution.in=" + DEFAULT_EXPECTED_SOLUTION + "," + UPDATED_EXPECTED_SOLUTION);

        // Get all the applicationLogList where expectedSolution equals to UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogShouldNotBeFound("expectedSolution.in=" + UPDATED_EXPECTED_SOLUTION);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByExpectedSolutionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where expectedSolution is not null
        defaultApplicationLogShouldBeFound("expectedSolution.specified=true");

        // Get all the applicationLogList where expectedSolution is null
        defaultApplicationLogShouldNotBeFound("expectedSolution.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogsByExpectedSolutionContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where expectedSolution contains DEFAULT_EXPECTED_SOLUTION
        defaultApplicationLogShouldBeFound("expectedSolution.contains=" + DEFAULT_EXPECTED_SOLUTION);

        // Get all the applicationLogList where expectedSolution contains UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogShouldNotBeFound("expectedSolution.contains=" + UPDATED_EXPECTED_SOLUTION);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByExpectedSolutionNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where expectedSolution does not contain DEFAULT_EXPECTED_SOLUTION
        defaultApplicationLogShouldNotBeFound("expectedSolution.doesNotContain=" + DEFAULT_EXPECTED_SOLUTION);

        // Get all the applicationLogList where expectedSolution does not contain UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogShouldBeFound("expectedSolution.doesNotContain=" + UPDATED_EXPECTED_SOLUTION);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where status equals to DEFAULT_STATUS
        defaultApplicationLogShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the applicationLogList where status equals to UPDATED_STATUS
        defaultApplicationLogShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApplicationLogShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the applicationLogList where status equals to UPDATED_STATUS
        defaultApplicationLogShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where status is not null
        defaultApplicationLogShouldBeFound("status.specified=true");

        // Get all the applicationLogList where status is null
        defaultApplicationLogShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogsByStatusContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where status contains DEFAULT_STATUS
        defaultApplicationLogShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the applicationLogList where status contains UPDATED_STATUS
        defaultApplicationLogShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where status does not contain DEFAULT_STATUS
        defaultApplicationLogShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the applicationLogList where status does not contain UPDATED_STATUS
        defaultApplicationLogShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByRowNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where rowNumber equals to DEFAULT_ROW_NUMBER
        defaultApplicationLogShouldBeFound("rowNumber.equals=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogList where rowNumber equals to UPDATED_ROW_NUMBER
        defaultApplicationLogShouldNotBeFound("rowNumber.equals=" + UPDATED_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByRowNumberIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where rowNumber in DEFAULT_ROW_NUMBER or UPDATED_ROW_NUMBER
        defaultApplicationLogShouldBeFound("rowNumber.in=" + DEFAULT_ROW_NUMBER + "," + UPDATED_ROW_NUMBER);

        // Get all the applicationLogList where rowNumber equals to UPDATED_ROW_NUMBER
        defaultApplicationLogShouldNotBeFound("rowNumber.in=" + UPDATED_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByRowNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where rowNumber is not null
        defaultApplicationLogShouldBeFound("rowNumber.specified=true");

        // Get all the applicationLogList where rowNumber is null
        defaultApplicationLogShouldNotBeFound("rowNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogsByRowNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where rowNumber is greater than or equal to DEFAULT_ROW_NUMBER
        defaultApplicationLogShouldBeFound("rowNumber.greaterThanOrEqual=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogList where rowNumber is greater than or equal to UPDATED_ROW_NUMBER
        defaultApplicationLogShouldNotBeFound("rowNumber.greaterThanOrEqual=" + UPDATED_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByRowNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where rowNumber is less than or equal to DEFAULT_ROW_NUMBER
        defaultApplicationLogShouldBeFound("rowNumber.lessThanOrEqual=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogList where rowNumber is less than or equal to SMALLER_ROW_NUMBER
        defaultApplicationLogShouldNotBeFound("rowNumber.lessThanOrEqual=" + SMALLER_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByRowNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where rowNumber is less than DEFAULT_ROW_NUMBER
        defaultApplicationLogShouldNotBeFound("rowNumber.lessThan=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogList where rowNumber is less than UPDATED_ROW_NUMBER
        defaultApplicationLogShouldBeFound("rowNumber.lessThan=" + UPDATED_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByRowNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where rowNumber is greater than DEFAULT_ROW_NUMBER
        defaultApplicationLogShouldNotBeFound("rowNumber.greaterThan=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogList where rowNumber is greater than SMALLER_ROW_NUMBER
        defaultApplicationLogShouldBeFound("rowNumber.greaterThan=" + SMALLER_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByBatchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where batchId equals to DEFAULT_BATCH_ID
        defaultApplicationLogShouldBeFound("batchId.equals=" + DEFAULT_BATCH_ID);

        // Get all the applicationLogList where batchId equals to UPDATED_BATCH_ID
        defaultApplicationLogShouldNotBeFound("batchId.equals=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByBatchIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where batchId in DEFAULT_BATCH_ID or UPDATED_BATCH_ID
        defaultApplicationLogShouldBeFound("batchId.in=" + DEFAULT_BATCH_ID + "," + UPDATED_BATCH_ID);

        // Get all the applicationLogList where batchId equals to UPDATED_BATCH_ID
        defaultApplicationLogShouldNotBeFound("batchId.in=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByBatchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where batchId is not null
        defaultApplicationLogShouldBeFound("batchId.specified=true");

        // Get all the applicationLogList where batchId is null
        defaultApplicationLogShouldNotBeFound("batchId.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogsByBatchIdContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where batchId contains DEFAULT_BATCH_ID
        defaultApplicationLogShouldBeFound("batchId.contains=" + DEFAULT_BATCH_ID);

        // Get all the applicationLogList where batchId contains UPDATED_BATCH_ID
        defaultApplicationLogShouldNotBeFound("batchId.contains=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByBatchIdNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        // Get all the applicationLogList where batchId does not contain DEFAULT_BATCH_ID
        defaultApplicationLogShouldNotBeFound("batchId.doesNotContain=" + DEFAULT_BATCH_ID);

        // Get all the applicationLogList where batchId does not contain UPDATED_BATCH_ID
        defaultApplicationLogShouldBeFound("batchId.doesNotContain=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllApplicationLogsByIssFileParserIsEqualToSomething() throws Exception {
        IssFileParser issFileParser;
        if (TestUtil.findAll(em, IssFileParser.class).isEmpty()) {
            applicationLogRepository.saveAndFlush(applicationLog);
            issFileParser = IssFileParserResourceIT.createEntity(em);
        } else {
            issFileParser = TestUtil.findAll(em, IssFileParser.class).get(0);
        }
        em.persist(issFileParser);
        em.flush();
        applicationLog.setIssFileParser(issFileParser);
        applicationLogRepository.saveAndFlush(applicationLog);
        Long issFileParserId = issFileParser.getId();

        // Get all the applicationLogList where issFileParser equals to issFileParserId
        defaultApplicationLogShouldBeFound("issFileParserId.equals=" + issFileParserId);

        // Get all the applicationLogList where issFileParser equals to (issFileParserId + 1)
        defaultApplicationLogShouldNotBeFound("issFileParserId.equals=" + (issFileParserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationLogShouldBeFound(String filter) throws Exception {
        restApplicationLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorType").value(hasItem(DEFAULT_ERROR_TYPE)))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE)))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)))
            .andExpect(jsonPath("$.[*].columnNumber").value(hasItem(DEFAULT_COLUMN_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].sevierity").value(hasItem(DEFAULT_SEVIERITY)))
            .andExpect(jsonPath("$.[*].expectedSolution").value(hasItem(DEFAULT_EXPECTED_SOLUTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].rowNumber").value(hasItem(DEFAULT_ROW_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)));

        // Check, that the count call also returns 1
        restApplicationLogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationLogShouldNotBeFound(String filter) throws Exception {
        restApplicationLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationLogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApplicationLog() throws Exception {
        // Get the applicationLog
        restApplicationLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplicationLog() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();

        // Update the applicationLog
        ApplicationLog updatedApplicationLog = applicationLogRepository.findById(applicationLog.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationLog are not directly saved in db
        em.detach(updatedApplicationLog);
        updatedApplicationLog
            .errorType(UPDATED_ERROR_TYPE)
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .columnNumber(UPDATED_COLUMN_NUMBER)
            .sevierity(UPDATED_SEVIERITY)
            .expectedSolution(UPDATED_EXPECTED_SOLUTION)
            .status(UPDATED_STATUS)
            .rowNumber(UPDATED_ROW_NUMBER)
            .batchId(UPDATED_BATCH_ID);

        restApplicationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicationLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplicationLog))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
        ApplicationLog testApplicationLog = applicationLogList.get(applicationLogList.size() - 1);
        assertThat(testApplicationLog.getErrorType()).isEqualTo(UPDATED_ERROR_TYPE);
        assertThat(testApplicationLog.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testApplicationLog.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
        assertThat(testApplicationLog.getColumnNumber()).isEqualTo(UPDATED_COLUMN_NUMBER);
        assertThat(testApplicationLog.getSevierity()).isEqualTo(UPDATED_SEVIERITY);
        assertThat(testApplicationLog.getExpectedSolution()).isEqualTo(UPDATED_EXPECTED_SOLUTION);
        assertThat(testApplicationLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplicationLog.getRowNumber()).isEqualTo(UPDATED_ROW_NUMBER);
        assertThat(testApplicationLog.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void putNonExistingApplicationLog() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();
        applicationLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationLog() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();
        applicationLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationLog() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();
        applicationLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationLog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationLogWithPatch() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();

        // Update the applicationLog using partial update
        ApplicationLog partialUpdatedApplicationLog = new ApplicationLog();
        partialUpdatedApplicationLog.setId(applicationLog.getId());

        partialUpdatedApplicationLog.errorMessage(UPDATED_ERROR_MESSAGE);

        restApplicationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationLog))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
        ApplicationLog testApplicationLog = applicationLogList.get(applicationLogList.size() - 1);
        assertThat(testApplicationLog.getErrorType()).isEqualTo(DEFAULT_ERROR_TYPE);
        assertThat(testApplicationLog.getErrorCode()).isEqualTo(DEFAULT_ERROR_CODE);
        assertThat(testApplicationLog.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
        assertThat(testApplicationLog.getColumnNumber()).isEqualTo(DEFAULT_COLUMN_NUMBER);
        assertThat(testApplicationLog.getSevierity()).isEqualTo(DEFAULT_SEVIERITY);
        assertThat(testApplicationLog.getExpectedSolution()).isEqualTo(DEFAULT_EXPECTED_SOLUTION);
        assertThat(testApplicationLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplicationLog.getRowNumber()).isEqualTo(DEFAULT_ROW_NUMBER);
        assertThat(testApplicationLog.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
    }

    @Test
    @Transactional
    void fullUpdateApplicationLogWithPatch() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();

        // Update the applicationLog using partial update
        ApplicationLog partialUpdatedApplicationLog = new ApplicationLog();
        partialUpdatedApplicationLog.setId(applicationLog.getId());

        partialUpdatedApplicationLog
            .errorType(UPDATED_ERROR_TYPE)
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .columnNumber(UPDATED_COLUMN_NUMBER)
            .sevierity(UPDATED_SEVIERITY)
            .expectedSolution(UPDATED_EXPECTED_SOLUTION)
            .status(UPDATED_STATUS)
            .rowNumber(UPDATED_ROW_NUMBER)
            .batchId(UPDATED_BATCH_ID);

        restApplicationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationLog))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
        ApplicationLog testApplicationLog = applicationLogList.get(applicationLogList.size() - 1);
        assertThat(testApplicationLog.getErrorType()).isEqualTo(UPDATED_ERROR_TYPE);
        assertThat(testApplicationLog.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testApplicationLog.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
        assertThat(testApplicationLog.getColumnNumber()).isEqualTo(UPDATED_COLUMN_NUMBER);
        assertThat(testApplicationLog.getSevierity()).isEqualTo(UPDATED_SEVIERITY);
        assertThat(testApplicationLog.getExpectedSolution()).isEqualTo(UPDATED_EXPECTED_SOLUTION);
        assertThat(testApplicationLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplicationLog.getRowNumber()).isEqualTo(UPDATED_ROW_NUMBER);
        assertThat(testApplicationLog.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationLog() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();
        applicationLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationLog() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();
        applicationLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationLog() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogRepository.findAll().size();
        applicationLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationLogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(applicationLog))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationLog in the database
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationLog() throws Exception {
        // Initialize the database
        applicationLogRepository.saveAndFlush(applicationLog);

        int databaseSizeBeforeDelete = applicationLogRepository.findAll().size();

        // Delete the applicationLog
        restApplicationLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationLog> applicationLogList = applicationLogRepository.findAll();
        assertThat(applicationLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
