package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.ApplicationLogHistory;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.ApplicationLogHistoryRepository;
import com.cbs.middleware.service.ApplicationLogHistoryService;
import com.cbs.middleware.service.criteria.ApplicationLogHistoryCriteria;
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
 * Integration tests for the {@link ApplicationLogHistoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicationLogHistoryResourceIT {

    private static final String DEFAULT_ERROR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final Long DEFAULT_ROW_NUMBER = 1L;
    private static final Long UPDATED_ROW_NUMBER = 2L;
    private static final Long SMALLER_ROW_NUMBER = 1L - 1L;

    private static final Long DEFAULT_COLUMN_NUMBER = 1L;
    private static final Long UPDATED_COLUMN_NUMBER = 2L;
    private static final Long SMALLER_COLUMN_NUMBER = 1L - 1L;

    private static final String DEFAULT_SEVIERITY = "AAAAAAAAAA";
    private static final String UPDATED_SEVIERITY = "BBBBBBBBBB";

    private static final String DEFAULT_EXPECTED_SOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_EXPECTED_SOLUTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/application-log-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationLogHistoryRepository applicationLogHistoryRepository;

    @Mock
    private ApplicationLogHistoryRepository applicationLogHistoryRepositoryMock;

    @Mock
    private ApplicationLogHistoryService applicationLogHistoryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationLogHistoryMockMvc;

    private ApplicationLogHistory applicationLogHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationLogHistory createEntity(EntityManager em) {
        ApplicationLogHistory applicationLogHistory = new ApplicationLogHistory()
            .errorType(DEFAULT_ERROR_TYPE)
            .errorCode(DEFAULT_ERROR_CODE)
            .errorMessage(DEFAULT_ERROR_MESSAGE)
            .rowNumber(DEFAULT_ROW_NUMBER)
            .columnNumber(DEFAULT_COLUMN_NUMBER)
            .sevierity(DEFAULT_SEVIERITY)
            .expectedSolution(DEFAULT_EXPECTED_SOLUTION)
            .status(DEFAULT_STATUS);
        return applicationLogHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationLogHistory createUpdatedEntity(EntityManager em) {
        ApplicationLogHistory applicationLogHistory = new ApplicationLogHistory()
            .errorType(UPDATED_ERROR_TYPE)
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .rowNumber(UPDATED_ROW_NUMBER)
            .columnNumber(UPDATED_COLUMN_NUMBER)
            .sevierity(UPDATED_SEVIERITY)
            .expectedSolution(UPDATED_EXPECTED_SOLUTION)
            .status(UPDATED_STATUS);
        return applicationLogHistory;
    }

    @BeforeEach
    public void initTest() {
        applicationLogHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationLogHistory() throws Exception {
        int databaseSizeBeforeCreate = applicationLogHistoryRepository.findAll().size();
        // Create the ApplicationLogHistory
        restApplicationLogHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationLogHistory))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationLogHistory testApplicationLogHistory = applicationLogHistoryList.get(applicationLogHistoryList.size() - 1);
        assertThat(testApplicationLogHistory.getErrorType()).isEqualTo(DEFAULT_ERROR_TYPE);
        assertThat(testApplicationLogHistory.getErrorCode()).isEqualTo(DEFAULT_ERROR_CODE);
        assertThat(testApplicationLogHistory.getErrorMessage()).isEqualTo(DEFAULT_ERROR_MESSAGE);
        assertThat(testApplicationLogHistory.getRowNumber()).isEqualTo(DEFAULT_ROW_NUMBER);
        assertThat(testApplicationLogHistory.getColumnNumber()).isEqualTo(DEFAULT_COLUMN_NUMBER);
        assertThat(testApplicationLogHistory.getSevierity()).isEqualTo(DEFAULT_SEVIERITY);
        assertThat(testApplicationLogHistory.getExpectedSolution()).isEqualTo(DEFAULT_EXPECTED_SOLUTION);
        assertThat(testApplicationLogHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createApplicationLogHistoryWithExistingId() throws Exception {
        // Create the ApplicationLogHistory with an existing ID
        applicationLogHistory.setId(1L);

        int databaseSizeBeforeCreate = applicationLogHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationLogHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationLogHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistories() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList
        restApplicationLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationLogHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorType").value(hasItem(DEFAULT_ERROR_TYPE)))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE)))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)))
            .andExpect(jsonPath("$.[*].rowNumber").value(hasItem(DEFAULT_ROW_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].columnNumber").value(hasItem(DEFAULT_COLUMN_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].sevierity").value(hasItem(DEFAULT_SEVIERITY)))
            .andExpect(jsonPath("$.[*].expectedSolution").value(hasItem(DEFAULT_EXPECTED_SOLUTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicationLogHistoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(applicationLogHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicationLogHistoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(applicationLogHistoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicationLogHistoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(applicationLogHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicationLogHistoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(applicationLogHistoryRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getApplicationLogHistory() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get the applicationLogHistory
        restApplicationLogHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationLogHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationLogHistory.getId().intValue()))
            .andExpect(jsonPath("$.errorType").value(DEFAULT_ERROR_TYPE))
            .andExpect(jsonPath("$.errorCode").value(DEFAULT_ERROR_CODE))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE))
            .andExpect(jsonPath("$.rowNumber").value(DEFAULT_ROW_NUMBER.intValue()))
            .andExpect(jsonPath("$.columnNumber").value(DEFAULT_COLUMN_NUMBER.intValue()))
            .andExpect(jsonPath("$.sevierity").value(DEFAULT_SEVIERITY))
            .andExpect(jsonPath("$.expectedSolution").value(DEFAULT_EXPECTED_SOLUTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getApplicationLogHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        Long id = applicationLogHistory.getId();

        defaultApplicationLogHistoryShouldBeFound("id.equals=" + id);
        defaultApplicationLogHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultApplicationLogHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicationLogHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicationLogHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicationLogHistoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorType equals to DEFAULT_ERROR_TYPE
        defaultApplicationLogHistoryShouldBeFound("errorType.equals=" + DEFAULT_ERROR_TYPE);

        // Get all the applicationLogHistoryList where errorType equals to UPDATED_ERROR_TYPE
        defaultApplicationLogHistoryShouldNotBeFound("errorType.equals=" + UPDATED_ERROR_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorTypeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorType in DEFAULT_ERROR_TYPE or UPDATED_ERROR_TYPE
        defaultApplicationLogHistoryShouldBeFound("errorType.in=" + DEFAULT_ERROR_TYPE + "," + UPDATED_ERROR_TYPE);

        // Get all the applicationLogHistoryList where errorType equals to UPDATED_ERROR_TYPE
        defaultApplicationLogHistoryShouldNotBeFound("errorType.in=" + UPDATED_ERROR_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorType is not null
        defaultApplicationLogHistoryShouldBeFound("errorType.specified=true");

        // Get all the applicationLogHistoryList where errorType is null
        defaultApplicationLogHistoryShouldNotBeFound("errorType.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorTypeContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorType contains DEFAULT_ERROR_TYPE
        defaultApplicationLogHistoryShouldBeFound("errorType.contains=" + DEFAULT_ERROR_TYPE);

        // Get all the applicationLogHistoryList where errorType contains UPDATED_ERROR_TYPE
        defaultApplicationLogHistoryShouldNotBeFound("errorType.contains=" + UPDATED_ERROR_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorTypeNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorType does not contain DEFAULT_ERROR_TYPE
        defaultApplicationLogHistoryShouldNotBeFound("errorType.doesNotContain=" + DEFAULT_ERROR_TYPE);

        // Get all the applicationLogHistoryList where errorType does not contain UPDATED_ERROR_TYPE
        defaultApplicationLogHistoryShouldBeFound("errorType.doesNotContain=" + UPDATED_ERROR_TYPE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorCode equals to DEFAULT_ERROR_CODE
        defaultApplicationLogHistoryShouldBeFound("errorCode.equals=" + DEFAULT_ERROR_CODE);

        // Get all the applicationLogHistoryList where errorCode equals to UPDATED_ERROR_CODE
        defaultApplicationLogHistoryShouldNotBeFound("errorCode.equals=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorCodeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorCode in DEFAULT_ERROR_CODE or UPDATED_ERROR_CODE
        defaultApplicationLogHistoryShouldBeFound("errorCode.in=" + DEFAULT_ERROR_CODE + "," + UPDATED_ERROR_CODE);

        // Get all the applicationLogHistoryList where errorCode equals to UPDATED_ERROR_CODE
        defaultApplicationLogHistoryShouldNotBeFound("errorCode.in=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorCode is not null
        defaultApplicationLogHistoryShouldBeFound("errorCode.specified=true");

        // Get all the applicationLogHistoryList where errorCode is null
        defaultApplicationLogHistoryShouldNotBeFound("errorCode.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorCodeContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorCode contains DEFAULT_ERROR_CODE
        defaultApplicationLogHistoryShouldBeFound("errorCode.contains=" + DEFAULT_ERROR_CODE);

        // Get all the applicationLogHistoryList where errorCode contains UPDATED_ERROR_CODE
        defaultApplicationLogHistoryShouldNotBeFound("errorCode.contains=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorCodeNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorCode does not contain DEFAULT_ERROR_CODE
        defaultApplicationLogHistoryShouldNotBeFound("errorCode.doesNotContain=" + DEFAULT_ERROR_CODE);

        // Get all the applicationLogHistoryList where errorCode does not contain UPDATED_ERROR_CODE
        defaultApplicationLogHistoryShouldBeFound("errorCode.doesNotContain=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorMessage equals to DEFAULT_ERROR_MESSAGE
        defaultApplicationLogHistoryShouldBeFound("errorMessage.equals=" + DEFAULT_ERROR_MESSAGE);

        // Get all the applicationLogHistoryList where errorMessage equals to UPDATED_ERROR_MESSAGE
        defaultApplicationLogHistoryShouldNotBeFound("errorMessage.equals=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorMessageIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorMessage in DEFAULT_ERROR_MESSAGE or UPDATED_ERROR_MESSAGE
        defaultApplicationLogHistoryShouldBeFound("errorMessage.in=" + DEFAULT_ERROR_MESSAGE + "," + UPDATED_ERROR_MESSAGE);

        // Get all the applicationLogHistoryList where errorMessage equals to UPDATED_ERROR_MESSAGE
        defaultApplicationLogHistoryShouldNotBeFound("errorMessage.in=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorMessage is not null
        defaultApplicationLogHistoryShouldBeFound("errorMessage.specified=true");

        // Get all the applicationLogHistoryList where errorMessage is null
        defaultApplicationLogHistoryShouldNotBeFound("errorMessage.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorMessageContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorMessage contains DEFAULT_ERROR_MESSAGE
        defaultApplicationLogHistoryShouldBeFound("errorMessage.contains=" + DEFAULT_ERROR_MESSAGE);

        // Get all the applicationLogHistoryList where errorMessage contains UPDATED_ERROR_MESSAGE
        defaultApplicationLogHistoryShouldNotBeFound("errorMessage.contains=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByErrorMessageNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where errorMessage does not contain DEFAULT_ERROR_MESSAGE
        defaultApplicationLogHistoryShouldNotBeFound("errorMessage.doesNotContain=" + DEFAULT_ERROR_MESSAGE);

        // Get all the applicationLogHistoryList where errorMessage does not contain UPDATED_ERROR_MESSAGE
        defaultApplicationLogHistoryShouldBeFound("errorMessage.doesNotContain=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByRowNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where rowNumber equals to DEFAULT_ROW_NUMBER
        defaultApplicationLogHistoryShouldBeFound("rowNumber.equals=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogHistoryList where rowNumber equals to UPDATED_ROW_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("rowNumber.equals=" + UPDATED_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByRowNumberIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where rowNumber in DEFAULT_ROW_NUMBER or UPDATED_ROW_NUMBER
        defaultApplicationLogHistoryShouldBeFound("rowNumber.in=" + DEFAULT_ROW_NUMBER + "," + UPDATED_ROW_NUMBER);

        // Get all the applicationLogHistoryList where rowNumber equals to UPDATED_ROW_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("rowNumber.in=" + UPDATED_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByRowNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where rowNumber is not null
        defaultApplicationLogHistoryShouldBeFound("rowNumber.specified=true");

        // Get all the applicationLogHistoryList where rowNumber is null
        defaultApplicationLogHistoryShouldNotBeFound("rowNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByRowNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where rowNumber is greater than or equal to DEFAULT_ROW_NUMBER
        defaultApplicationLogHistoryShouldBeFound("rowNumber.greaterThanOrEqual=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogHistoryList where rowNumber is greater than or equal to UPDATED_ROW_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("rowNumber.greaterThanOrEqual=" + UPDATED_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByRowNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where rowNumber is less than or equal to DEFAULT_ROW_NUMBER
        defaultApplicationLogHistoryShouldBeFound("rowNumber.lessThanOrEqual=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogHistoryList where rowNumber is less than or equal to SMALLER_ROW_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("rowNumber.lessThanOrEqual=" + SMALLER_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByRowNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where rowNumber is less than DEFAULT_ROW_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("rowNumber.lessThan=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogHistoryList where rowNumber is less than UPDATED_ROW_NUMBER
        defaultApplicationLogHistoryShouldBeFound("rowNumber.lessThan=" + UPDATED_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByRowNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where rowNumber is greater than DEFAULT_ROW_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("rowNumber.greaterThan=" + DEFAULT_ROW_NUMBER);

        // Get all the applicationLogHistoryList where rowNumber is greater than SMALLER_ROW_NUMBER
        defaultApplicationLogHistoryShouldBeFound("rowNumber.greaterThan=" + SMALLER_ROW_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByColumnNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where columnNumber equals to DEFAULT_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldBeFound("columnNumber.equals=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogHistoryList where columnNumber equals to UPDATED_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("columnNumber.equals=" + UPDATED_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByColumnNumberIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where columnNumber in DEFAULT_COLUMN_NUMBER or UPDATED_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldBeFound("columnNumber.in=" + DEFAULT_COLUMN_NUMBER + "," + UPDATED_COLUMN_NUMBER);

        // Get all the applicationLogHistoryList where columnNumber equals to UPDATED_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("columnNumber.in=" + UPDATED_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByColumnNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where columnNumber is not null
        defaultApplicationLogHistoryShouldBeFound("columnNumber.specified=true");

        // Get all the applicationLogHistoryList where columnNumber is null
        defaultApplicationLogHistoryShouldNotBeFound("columnNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByColumnNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where columnNumber is greater than or equal to DEFAULT_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldBeFound("columnNumber.greaterThanOrEqual=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogHistoryList where columnNumber is greater than or equal to UPDATED_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("columnNumber.greaterThanOrEqual=" + UPDATED_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByColumnNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where columnNumber is less than or equal to DEFAULT_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldBeFound("columnNumber.lessThanOrEqual=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogHistoryList where columnNumber is less than or equal to SMALLER_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("columnNumber.lessThanOrEqual=" + SMALLER_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByColumnNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where columnNumber is less than DEFAULT_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("columnNumber.lessThan=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogHistoryList where columnNumber is less than UPDATED_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldBeFound("columnNumber.lessThan=" + UPDATED_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByColumnNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where columnNumber is greater than DEFAULT_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldNotBeFound("columnNumber.greaterThan=" + DEFAULT_COLUMN_NUMBER);

        // Get all the applicationLogHistoryList where columnNumber is greater than SMALLER_COLUMN_NUMBER
        defaultApplicationLogHistoryShouldBeFound("columnNumber.greaterThan=" + SMALLER_COLUMN_NUMBER);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesBySevierityIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where sevierity equals to DEFAULT_SEVIERITY
        defaultApplicationLogHistoryShouldBeFound("sevierity.equals=" + DEFAULT_SEVIERITY);

        // Get all the applicationLogHistoryList where sevierity equals to UPDATED_SEVIERITY
        defaultApplicationLogHistoryShouldNotBeFound("sevierity.equals=" + UPDATED_SEVIERITY);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesBySevierityIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where sevierity in DEFAULT_SEVIERITY or UPDATED_SEVIERITY
        defaultApplicationLogHistoryShouldBeFound("sevierity.in=" + DEFAULT_SEVIERITY + "," + UPDATED_SEVIERITY);

        // Get all the applicationLogHistoryList where sevierity equals to UPDATED_SEVIERITY
        defaultApplicationLogHistoryShouldNotBeFound("sevierity.in=" + UPDATED_SEVIERITY);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesBySevierityIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where sevierity is not null
        defaultApplicationLogHistoryShouldBeFound("sevierity.specified=true");

        // Get all the applicationLogHistoryList where sevierity is null
        defaultApplicationLogHistoryShouldNotBeFound("sevierity.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesBySevierityContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where sevierity contains DEFAULT_SEVIERITY
        defaultApplicationLogHistoryShouldBeFound("sevierity.contains=" + DEFAULT_SEVIERITY);

        // Get all the applicationLogHistoryList where sevierity contains UPDATED_SEVIERITY
        defaultApplicationLogHistoryShouldNotBeFound("sevierity.contains=" + UPDATED_SEVIERITY);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesBySevierityNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where sevierity does not contain DEFAULT_SEVIERITY
        defaultApplicationLogHistoryShouldNotBeFound("sevierity.doesNotContain=" + DEFAULT_SEVIERITY);

        // Get all the applicationLogHistoryList where sevierity does not contain UPDATED_SEVIERITY
        defaultApplicationLogHistoryShouldBeFound("sevierity.doesNotContain=" + UPDATED_SEVIERITY);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByExpectedSolutionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where expectedSolution equals to DEFAULT_EXPECTED_SOLUTION
        defaultApplicationLogHistoryShouldBeFound("expectedSolution.equals=" + DEFAULT_EXPECTED_SOLUTION);

        // Get all the applicationLogHistoryList where expectedSolution equals to UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogHistoryShouldNotBeFound("expectedSolution.equals=" + UPDATED_EXPECTED_SOLUTION);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByExpectedSolutionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where expectedSolution in DEFAULT_EXPECTED_SOLUTION or UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogHistoryShouldBeFound("expectedSolution.in=" + DEFAULT_EXPECTED_SOLUTION + "," + UPDATED_EXPECTED_SOLUTION);

        // Get all the applicationLogHistoryList where expectedSolution equals to UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogHistoryShouldNotBeFound("expectedSolution.in=" + UPDATED_EXPECTED_SOLUTION);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByExpectedSolutionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where expectedSolution is not null
        defaultApplicationLogHistoryShouldBeFound("expectedSolution.specified=true");

        // Get all the applicationLogHistoryList where expectedSolution is null
        defaultApplicationLogHistoryShouldNotBeFound("expectedSolution.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByExpectedSolutionContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where expectedSolution contains DEFAULT_EXPECTED_SOLUTION
        defaultApplicationLogHistoryShouldBeFound("expectedSolution.contains=" + DEFAULT_EXPECTED_SOLUTION);

        // Get all the applicationLogHistoryList where expectedSolution contains UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogHistoryShouldNotBeFound("expectedSolution.contains=" + UPDATED_EXPECTED_SOLUTION);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByExpectedSolutionNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where expectedSolution does not contain DEFAULT_EXPECTED_SOLUTION
        defaultApplicationLogHistoryShouldNotBeFound("expectedSolution.doesNotContain=" + DEFAULT_EXPECTED_SOLUTION);

        // Get all the applicationLogHistoryList where expectedSolution does not contain UPDATED_EXPECTED_SOLUTION
        defaultApplicationLogHistoryShouldBeFound("expectedSolution.doesNotContain=" + UPDATED_EXPECTED_SOLUTION);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where status equals to DEFAULT_STATUS
        defaultApplicationLogHistoryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the applicationLogHistoryList where status equals to UPDATED_STATUS
        defaultApplicationLogHistoryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApplicationLogHistoryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the applicationLogHistoryList where status equals to UPDATED_STATUS
        defaultApplicationLogHistoryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where status is not null
        defaultApplicationLogHistoryShouldBeFound("status.specified=true");

        // Get all the applicationLogHistoryList where status is null
        defaultApplicationLogHistoryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByStatusContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where status contains DEFAULT_STATUS
        defaultApplicationLogHistoryShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the applicationLogHistoryList where status contains UPDATED_STATUS
        defaultApplicationLogHistoryShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        // Get all the applicationLogHistoryList where status does not contain DEFAULT_STATUS
        defaultApplicationLogHistoryShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the applicationLogHistoryList where status does not contain UPDATED_STATUS
        defaultApplicationLogHistoryShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationLogHistoriesByIssFileParserIsEqualToSomething() throws Exception {
        IssFileParser issFileParser;
        if (TestUtil.findAll(em, IssFileParser.class).isEmpty()) {
            applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);
            issFileParser = IssFileParserResourceIT.createEntity(em);
        } else {
            issFileParser = TestUtil.findAll(em, IssFileParser.class).get(0);
        }
        em.persist(issFileParser);
        em.flush();
        applicationLogHistory.setIssFileParser(issFileParser);
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);
        Long issFileParserId = issFileParser.getId();

        // Get all the applicationLogHistoryList where issFileParser equals to issFileParserId
        defaultApplicationLogHistoryShouldBeFound("issFileParserId.equals=" + issFileParserId);

        // Get all the applicationLogHistoryList where issFileParser equals to (issFileParserId + 1)
        defaultApplicationLogHistoryShouldNotBeFound("issFileParserId.equals=" + (issFileParserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationLogHistoryShouldBeFound(String filter) throws Exception {
        restApplicationLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationLogHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorType").value(hasItem(DEFAULT_ERROR_TYPE)))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE)))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)))
            .andExpect(jsonPath("$.[*].rowNumber").value(hasItem(DEFAULT_ROW_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].columnNumber").value(hasItem(DEFAULT_COLUMN_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].sevierity").value(hasItem(DEFAULT_SEVIERITY)))
            .andExpect(jsonPath("$.[*].expectedSolution").value(hasItem(DEFAULT_EXPECTED_SOLUTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restApplicationLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationLogHistoryShouldNotBeFound(String filter) throws Exception {
        restApplicationLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationLogHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApplicationLogHistory() throws Exception {
        // Get the applicationLogHistory
        restApplicationLogHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplicationLogHistory() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        int databaseSizeBeforeUpdate = applicationLogHistoryRepository.findAll().size();

        // Update the applicationLogHistory
        ApplicationLogHistory updatedApplicationLogHistory = applicationLogHistoryRepository.findById(applicationLogHistory.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationLogHistory are not directly saved in db
        em.detach(updatedApplicationLogHistory);
        updatedApplicationLogHistory
            .errorType(UPDATED_ERROR_TYPE)
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .rowNumber(UPDATED_ROW_NUMBER)
            .columnNumber(UPDATED_COLUMN_NUMBER)
            .sevierity(UPDATED_SEVIERITY)
            .expectedSolution(UPDATED_EXPECTED_SOLUTION)
            .status(UPDATED_STATUS);

        restApplicationLogHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicationLogHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplicationLogHistory))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeUpdate);
        ApplicationLogHistory testApplicationLogHistory = applicationLogHistoryList.get(applicationLogHistoryList.size() - 1);
        assertThat(testApplicationLogHistory.getErrorType()).isEqualTo(UPDATED_ERROR_TYPE);
        assertThat(testApplicationLogHistory.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testApplicationLogHistory.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
        assertThat(testApplicationLogHistory.getRowNumber()).isEqualTo(UPDATED_ROW_NUMBER);
        assertThat(testApplicationLogHistory.getColumnNumber()).isEqualTo(UPDATED_COLUMN_NUMBER);
        assertThat(testApplicationLogHistory.getSevierity()).isEqualTo(UPDATED_SEVIERITY);
        assertThat(testApplicationLogHistory.getExpectedSolution()).isEqualTo(UPDATED_EXPECTED_SOLUTION);
        assertThat(testApplicationLogHistory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingApplicationLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogHistoryRepository.findAll().size();
        applicationLogHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationLogHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationLogHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationLogHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogHistoryRepository.findAll().size();
        applicationLogHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationLogHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationLogHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogHistoryRepository.findAll().size();
        applicationLogHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationLogHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationLogHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationLogHistoryWithPatch() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        int databaseSizeBeforeUpdate = applicationLogHistoryRepository.findAll().size();

        // Update the applicationLogHistory using partial update
        ApplicationLogHistory partialUpdatedApplicationLogHistory = new ApplicationLogHistory();
        partialUpdatedApplicationLogHistory.setId(applicationLogHistory.getId());

        partialUpdatedApplicationLogHistory.columnNumber(UPDATED_COLUMN_NUMBER);

        restApplicationLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationLogHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationLogHistory))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeUpdate);
        ApplicationLogHistory testApplicationLogHistory = applicationLogHistoryList.get(applicationLogHistoryList.size() - 1);
        assertThat(testApplicationLogHistory.getErrorType()).isEqualTo(DEFAULT_ERROR_TYPE);
        assertThat(testApplicationLogHistory.getErrorCode()).isEqualTo(DEFAULT_ERROR_CODE);
        assertThat(testApplicationLogHistory.getErrorMessage()).isEqualTo(DEFAULT_ERROR_MESSAGE);
        assertThat(testApplicationLogHistory.getRowNumber()).isEqualTo(DEFAULT_ROW_NUMBER);
        assertThat(testApplicationLogHistory.getColumnNumber()).isEqualTo(UPDATED_COLUMN_NUMBER);
        assertThat(testApplicationLogHistory.getSevierity()).isEqualTo(DEFAULT_SEVIERITY);
        assertThat(testApplicationLogHistory.getExpectedSolution()).isEqualTo(DEFAULT_EXPECTED_SOLUTION);
        assertThat(testApplicationLogHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateApplicationLogHistoryWithPatch() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        int databaseSizeBeforeUpdate = applicationLogHistoryRepository.findAll().size();

        // Update the applicationLogHistory using partial update
        ApplicationLogHistory partialUpdatedApplicationLogHistory = new ApplicationLogHistory();
        partialUpdatedApplicationLogHistory.setId(applicationLogHistory.getId());

        partialUpdatedApplicationLogHistory
            .errorType(UPDATED_ERROR_TYPE)
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .rowNumber(UPDATED_ROW_NUMBER)
            .columnNumber(UPDATED_COLUMN_NUMBER)
            .sevierity(UPDATED_SEVIERITY)
            .expectedSolution(UPDATED_EXPECTED_SOLUTION)
            .status(UPDATED_STATUS);

        restApplicationLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationLogHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationLogHistory))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeUpdate);
        ApplicationLogHistory testApplicationLogHistory = applicationLogHistoryList.get(applicationLogHistoryList.size() - 1);
        assertThat(testApplicationLogHistory.getErrorType()).isEqualTo(UPDATED_ERROR_TYPE);
        assertThat(testApplicationLogHistory.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testApplicationLogHistory.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
        assertThat(testApplicationLogHistory.getRowNumber()).isEqualTo(UPDATED_ROW_NUMBER);
        assertThat(testApplicationLogHistory.getColumnNumber()).isEqualTo(UPDATED_COLUMN_NUMBER);
        assertThat(testApplicationLogHistory.getSevierity()).isEqualTo(UPDATED_SEVIERITY);
        assertThat(testApplicationLogHistory.getExpectedSolution()).isEqualTo(UPDATED_EXPECTED_SOLUTION);
        assertThat(testApplicationLogHistory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogHistoryRepository.findAll().size();
        applicationLogHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationLogHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationLogHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogHistoryRepository.findAll().size();
        applicationLogHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationLogHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationLogHistory() throws Exception {
        int databaseSizeBeforeUpdate = applicationLogHistoryRepository.findAll().size();
        applicationLogHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationLogHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationLogHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationLogHistory in the database
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationLogHistory() throws Exception {
        // Initialize the database
        applicationLogHistoryRepository.saveAndFlush(applicationLogHistory);

        int databaseSizeBeforeDelete = applicationLogHistoryRepository.findAll().size();

        // Delete the applicationLogHistory
        restApplicationLogHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationLogHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationLogHistory> applicationLogHistoryList = applicationLogHistoryRepository.findAll();
        assertThat(applicationLogHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
