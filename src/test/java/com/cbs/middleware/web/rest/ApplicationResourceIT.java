package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.service.ApplicationService;
import com.cbs.middleware.service.criteria.ApplicationCriteria;
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
 * Integration tests for the {@link ApplicationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicationResourceIT {

    private static final String DEFAULT_BATCH_ID = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UNIQUE_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_RECORD_STATUS = 1;
    private static final Integer UPDATED_RECORD_STATUS = 2;
    private static final Integer SMALLER_RECORD_STATUS = 1 - 1;

    private static final Integer DEFAULT_APPLICATION_STATUS = 1;
    private static final Integer UPDATED_APPLICATION_STATUS = 2;
    private static final Integer SMALLER_APPLICATION_STATUS = 1 - 1;

    private static final Long DEFAULT_KCC_STATUS = 1L;
    private static final Long UPDATED_KCC_STATUS = 2L;
    private static final Long SMALLER_KCC_STATUS = 1L - 1L;

    private static final String DEFAULT_RECIPIENT_UNIQUE_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECIPIENT_UNIQUE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/applications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationRepository applicationRepositoryMock;

    @Mock
    private ApplicationService applicationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationMockMvc;

    private Application application;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createEntity(EntityManager em) {
        Application application = new Application()
            .batchId(DEFAULT_BATCH_ID)
            .uniqueId(DEFAULT_UNIQUE_ID)
            .recordStatus(DEFAULT_RECORD_STATUS)
            .applicationStatus(DEFAULT_APPLICATION_STATUS)
            .kccStatus(DEFAULT_KCC_STATUS)
            .recipientUniqueId(DEFAULT_RECIPIENT_UNIQUE_ID)
            .farmerId(DEFAULT_FARMER_ID);
        return application;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createUpdatedEntity(EntityManager em) {
        Application application = new Application()
            .batchId(UPDATED_BATCH_ID)
            .uniqueId(UPDATED_UNIQUE_ID)
            .recordStatus(UPDATED_RECORD_STATUS)
            .applicationStatus(UPDATED_APPLICATION_STATUS)
            .kccStatus(UPDATED_KCC_STATUS)
            .recipientUniqueId(UPDATED_RECIPIENT_UNIQUE_ID)
            .farmerId(UPDATED_FARMER_ID);
        return application;
    }

    @BeforeEach
    public void initTest() {
        application = createEntity(em);
    }

    @Test
    @Transactional
    void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();
        // Create the Application
        restApplicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate + 1);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
        assertThat(testApplication.getUniqueId()).isEqualTo(DEFAULT_UNIQUE_ID);
        assertThat(testApplication.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
        assertThat(testApplication.getApplicationStatus()).isEqualTo(DEFAULT_APPLICATION_STATUS);
        assertThat(testApplication.getKccStatus()).isEqualTo(DEFAULT_KCC_STATUS);
        assertThat(testApplication.getRecipientUniqueId()).isEqualTo(DEFAULT_RECIPIENT_UNIQUE_ID);
        assertThat(testApplication.getFarmerId()).isEqualTo(DEFAULT_FARMER_ID);
    }

    @Test
    @Transactional
    void createApplicationWithExistingId() throws Exception {
        // Create the Application with an existing ID
        application.setId(1L);

        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplications() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)))
            .andExpect(jsonPath("$.[*].uniqueId").value(hasItem(DEFAULT_UNIQUE_ID)))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].applicationStatus").value(hasItem(DEFAULT_APPLICATION_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].kccStatus").value(hasItem(DEFAULT_KCC_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].recipientUniqueId").value(hasItem(DEFAULT_RECIPIENT_UNIQUE_ID)))
            .andExpect(jsonPath("$.[*].farmerId").value(hasItem(DEFAULT_FARMER_ID)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(applicationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(applicationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(applicationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(applicationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get the application
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL_ID, application.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(application.getId().intValue()))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID))
            .andExpect(jsonPath("$.uniqueId").value(DEFAULT_UNIQUE_ID))
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS.intValue()))
            .andExpect(jsonPath("$.applicationStatus").value(DEFAULT_APPLICATION_STATUS.intValue()))
            .andExpect(jsonPath("$.kccStatus").value(DEFAULT_KCC_STATUS.intValue()))
            .andExpect(jsonPath("$.recipientUniqueId").value(DEFAULT_RECIPIENT_UNIQUE_ID))
            .andExpect(jsonPath("$.farmerId").value(DEFAULT_FARMER_ID));
    }

    @Test
    @Transactional
    void getApplicationsByIdFiltering() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        Long id = application.getId();

        defaultApplicationShouldBeFound("id.equals=" + id);
        defaultApplicationShouldNotBeFound("id.notEquals=" + id);

        defaultApplicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicationShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApplicationsByBatchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where batchId equals to DEFAULT_BATCH_ID
        defaultApplicationShouldBeFound("batchId.equals=" + DEFAULT_BATCH_ID);

        // Get all the applicationList where batchId equals to UPDATED_BATCH_ID
        defaultApplicationShouldNotBeFound("batchId.equals=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByBatchIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where batchId in DEFAULT_BATCH_ID or UPDATED_BATCH_ID
        defaultApplicationShouldBeFound("batchId.in=" + DEFAULT_BATCH_ID + "," + UPDATED_BATCH_ID);

        // Get all the applicationList where batchId equals to UPDATED_BATCH_ID
        defaultApplicationShouldNotBeFound("batchId.in=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByBatchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where batchId is not null
        defaultApplicationShouldBeFound("batchId.specified=true");

        // Get all the applicationList where batchId is null
        defaultApplicationShouldNotBeFound("batchId.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationsByBatchIdContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where batchId contains DEFAULT_BATCH_ID
        defaultApplicationShouldBeFound("batchId.contains=" + DEFAULT_BATCH_ID);

        // Get all the applicationList where batchId contains UPDATED_BATCH_ID
        defaultApplicationShouldNotBeFound("batchId.contains=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByBatchIdNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where batchId does not contain DEFAULT_BATCH_ID
        defaultApplicationShouldNotBeFound("batchId.doesNotContain=" + DEFAULT_BATCH_ID);

        // Get all the applicationList where batchId does not contain UPDATED_BATCH_ID
        defaultApplicationShouldBeFound("batchId.doesNotContain=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByUniqueIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where uniqueId equals to DEFAULT_UNIQUE_ID
        defaultApplicationShouldBeFound("uniqueId.equals=" + DEFAULT_UNIQUE_ID);

        // Get all the applicationList where uniqueId equals to UPDATED_UNIQUE_ID
        defaultApplicationShouldNotBeFound("uniqueId.equals=" + UPDATED_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByUniqueIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where uniqueId in DEFAULT_UNIQUE_ID or UPDATED_UNIQUE_ID
        defaultApplicationShouldBeFound("uniqueId.in=" + DEFAULT_UNIQUE_ID + "," + UPDATED_UNIQUE_ID);

        // Get all the applicationList where uniqueId equals to UPDATED_UNIQUE_ID
        defaultApplicationShouldNotBeFound("uniqueId.in=" + UPDATED_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByUniqueIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where uniqueId is not null
        defaultApplicationShouldBeFound("uniqueId.specified=true");

        // Get all the applicationList where uniqueId is null
        defaultApplicationShouldNotBeFound("uniqueId.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationsByUniqueIdContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where uniqueId contains DEFAULT_UNIQUE_ID
        defaultApplicationShouldBeFound("uniqueId.contains=" + DEFAULT_UNIQUE_ID);

        // Get all the applicationList where uniqueId contains UPDATED_UNIQUE_ID
        defaultApplicationShouldNotBeFound("uniqueId.contains=" + UPDATED_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByUniqueIdNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where uniqueId does not contain DEFAULT_UNIQUE_ID
        defaultApplicationShouldNotBeFound("uniqueId.doesNotContain=" + DEFAULT_UNIQUE_ID);

        // Get all the applicationList where uniqueId does not contain UPDATED_UNIQUE_ID
        defaultApplicationShouldBeFound("uniqueId.doesNotContain=" + UPDATED_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecordStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recordStatus equals to DEFAULT_RECORD_STATUS
        defaultApplicationShouldBeFound("recordStatus.equals=" + DEFAULT_RECORD_STATUS);

        // Get all the applicationList where recordStatus equals to UPDATED_RECORD_STATUS
        defaultApplicationShouldNotBeFound("recordStatus.equals=" + UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecordStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recordStatus in DEFAULT_RECORD_STATUS or UPDATED_RECORD_STATUS
        defaultApplicationShouldBeFound("recordStatus.in=" + DEFAULT_RECORD_STATUS + "," + UPDATED_RECORD_STATUS);

        // Get all the applicationList where recordStatus equals to UPDATED_RECORD_STATUS
        defaultApplicationShouldNotBeFound("recordStatus.in=" + UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecordStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recordStatus is not null
        defaultApplicationShouldBeFound("recordStatus.specified=true");

        // Get all the applicationList where recordStatus is null
        defaultApplicationShouldNotBeFound("recordStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationsByRecordStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recordStatus is greater than or equal to DEFAULT_RECORD_STATUS
        defaultApplicationShouldBeFound("recordStatus.greaterThanOrEqual=" + DEFAULT_RECORD_STATUS);

        // Get all the applicationList where recordStatus is greater than or equal to UPDATED_RECORD_STATUS
        defaultApplicationShouldNotBeFound("recordStatus.greaterThanOrEqual=" + UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecordStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recordStatus is less than or equal to DEFAULT_RECORD_STATUS
        defaultApplicationShouldBeFound("recordStatus.lessThanOrEqual=" + DEFAULT_RECORD_STATUS);

        // Get all the applicationList where recordStatus is less than or equal to SMALLER_RECORD_STATUS
        defaultApplicationShouldNotBeFound("recordStatus.lessThanOrEqual=" + SMALLER_RECORD_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecordStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recordStatus is less than DEFAULT_RECORD_STATUS
        defaultApplicationShouldNotBeFound("recordStatus.lessThan=" + DEFAULT_RECORD_STATUS);

        // Get all the applicationList where recordStatus is less than UPDATED_RECORD_STATUS
        defaultApplicationShouldBeFound("recordStatus.lessThan=" + UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecordStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recordStatus is greater than DEFAULT_RECORD_STATUS
        defaultApplicationShouldNotBeFound("recordStatus.greaterThan=" + DEFAULT_RECORD_STATUS);

        // Get all the applicationList where recordStatus is greater than SMALLER_RECORD_STATUS
        defaultApplicationShouldBeFound("recordStatus.greaterThan=" + SMALLER_RECORD_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByApplicationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationStatus equals to DEFAULT_APPLICATION_STATUS
        defaultApplicationShouldBeFound("applicationStatus.equals=" + DEFAULT_APPLICATION_STATUS);

        // Get all the applicationList where applicationStatus equals to UPDATED_APPLICATION_STATUS
        defaultApplicationShouldNotBeFound("applicationStatus.equals=" + UPDATED_APPLICATION_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByApplicationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationStatus in DEFAULT_APPLICATION_STATUS or UPDATED_APPLICATION_STATUS
        defaultApplicationShouldBeFound("applicationStatus.in=" + DEFAULT_APPLICATION_STATUS + "," + UPDATED_APPLICATION_STATUS);

        // Get all the applicationList where applicationStatus equals to UPDATED_APPLICATION_STATUS
        defaultApplicationShouldNotBeFound("applicationStatus.in=" + UPDATED_APPLICATION_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByApplicationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationStatus is not null
        defaultApplicationShouldBeFound("applicationStatus.specified=true");

        // Get all the applicationList where applicationStatus is null
        defaultApplicationShouldNotBeFound("applicationStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationsByApplicationStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationStatus is greater than or equal to DEFAULT_APPLICATION_STATUS
        defaultApplicationShouldBeFound("applicationStatus.greaterThanOrEqual=" + DEFAULT_APPLICATION_STATUS);

        // Get all the applicationList where applicationStatus is greater than or equal to UPDATED_APPLICATION_STATUS
        defaultApplicationShouldNotBeFound("applicationStatus.greaterThanOrEqual=" + UPDATED_APPLICATION_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByApplicationStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationStatus is less than or equal to DEFAULT_APPLICATION_STATUS
        defaultApplicationShouldBeFound("applicationStatus.lessThanOrEqual=" + DEFAULT_APPLICATION_STATUS);

        // Get all the applicationList where applicationStatus is less than or equal to SMALLER_APPLICATION_STATUS
        defaultApplicationShouldNotBeFound("applicationStatus.lessThanOrEqual=" + SMALLER_APPLICATION_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByApplicationStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationStatus is less than DEFAULT_APPLICATION_STATUS
        defaultApplicationShouldNotBeFound("applicationStatus.lessThan=" + DEFAULT_APPLICATION_STATUS);

        // Get all the applicationList where applicationStatus is less than UPDATED_APPLICATION_STATUS
        defaultApplicationShouldBeFound("applicationStatus.lessThan=" + UPDATED_APPLICATION_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByApplicationStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationStatus is greater than DEFAULT_APPLICATION_STATUS
        defaultApplicationShouldNotBeFound("applicationStatus.greaterThan=" + DEFAULT_APPLICATION_STATUS);

        // Get all the applicationList where applicationStatus is greater than SMALLER_APPLICATION_STATUS
        defaultApplicationShouldBeFound("applicationStatus.greaterThan=" + SMALLER_APPLICATION_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByKccStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where kccStatus equals to DEFAULT_KCC_STATUS
        defaultApplicationShouldBeFound("kccStatus.equals=" + DEFAULT_KCC_STATUS);

        // Get all the applicationList where kccStatus equals to UPDATED_KCC_STATUS
        defaultApplicationShouldNotBeFound("kccStatus.equals=" + UPDATED_KCC_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByKccStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where kccStatus in DEFAULT_KCC_STATUS or UPDATED_KCC_STATUS
        defaultApplicationShouldBeFound("kccStatus.in=" + DEFAULT_KCC_STATUS + "," + UPDATED_KCC_STATUS);

        // Get all the applicationList where kccStatus equals to UPDATED_KCC_STATUS
        defaultApplicationShouldNotBeFound("kccStatus.in=" + UPDATED_KCC_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByKccStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where kccStatus is not null
        defaultApplicationShouldBeFound("kccStatus.specified=true");

        // Get all the applicationList where kccStatus is null
        defaultApplicationShouldNotBeFound("kccStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationsByKccStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where kccStatus is greater than or equal to DEFAULT_KCC_STATUS
        defaultApplicationShouldBeFound("kccStatus.greaterThanOrEqual=" + DEFAULT_KCC_STATUS);

        // Get all the applicationList where kccStatus is greater than or equal to UPDATED_KCC_STATUS
        defaultApplicationShouldNotBeFound("kccStatus.greaterThanOrEqual=" + UPDATED_KCC_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByKccStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where kccStatus is less than or equal to DEFAULT_KCC_STATUS
        defaultApplicationShouldBeFound("kccStatus.lessThanOrEqual=" + DEFAULT_KCC_STATUS);

        // Get all the applicationList where kccStatus is less than or equal to SMALLER_KCC_STATUS
        defaultApplicationShouldNotBeFound("kccStatus.lessThanOrEqual=" + SMALLER_KCC_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByKccStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where kccStatus is less than DEFAULT_KCC_STATUS
        defaultApplicationShouldNotBeFound("kccStatus.lessThan=" + DEFAULT_KCC_STATUS);

        // Get all the applicationList where kccStatus is less than UPDATED_KCC_STATUS
        defaultApplicationShouldBeFound("kccStatus.lessThan=" + UPDATED_KCC_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByKccStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where kccStatus is greater than DEFAULT_KCC_STATUS
        defaultApplicationShouldNotBeFound("kccStatus.greaterThan=" + DEFAULT_KCC_STATUS);

        // Get all the applicationList where kccStatus is greater than SMALLER_KCC_STATUS
        defaultApplicationShouldBeFound("kccStatus.greaterThan=" + SMALLER_KCC_STATUS);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecipientUniqueIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recipientUniqueId equals to DEFAULT_RECIPIENT_UNIQUE_ID
        defaultApplicationShouldBeFound("recipientUniqueId.equals=" + DEFAULT_RECIPIENT_UNIQUE_ID);

        // Get all the applicationList where recipientUniqueId equals to UPDATED_RECIPIENT_UNIQUE_ID
        defaultApplicationShouldNotBeFound("recipientUniqueId.equals=" + UPDATED_RECIPIENT_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecipientUniqueIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recipientUniqueId in DEFAULT_RECIPIENT_UNIQUE_ID or UPDATED_RECIPIENT_UNIQUE_ID
        defaultApplicationShouldBeFound("recipientUniqueId.in=" + DEFAULT_RECIPIENT_UNIQUE_ID + "," + UPDATED_RECIPIENT_UNIQUE_ID);

        // Get all the applicationList where recipientUniqueId equals to UPDATED_RECIPIENT_UNIQUE_ID
        defaultApplicationShouldNotBeFound("recipientUniqueId.in=" + UPDATED_RECIPIENT_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecipientUniqueIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recipientUniqueId is not null
        defaultApplicationShouldBeFound("recipientUniqueId.specified=true");

        // Get all the applicationList where recipientUniqueId is null
        defaultApplicationShouldNotBeFound("recipientUniqueId.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationsByRecipientUniqueIdContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recipientUniqueId contains DEFAULT_RECIPIENT_UNIQUE_ID
        defaultApplicationShouldBeFound("recipientUniqueId.contains=" + DEFAULT_RECIPIENT_UNIQUE_ID);

        // Get all the applicationList where recipientUniqueId contains UPDATED_RECIPIENT_UNIQUE_ID
        defaultApplicationShouldNotBeFound("recipientUniqueId.contains=" + UPDATED_RECIPIENT_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByRecipientUniqueIdNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where recipientUniqueId does not contain DEFAULT_RECIPIENT_UNIQUE_ID
        defaultApplicationShouldNotBeFound("recipientUniqueId.doesNotContain=" + DEFAULT_RECIPIENT_UNIQUE_ID);

        // Get all the applicationList where recipientUniqueId does not contain UPDATED_RECIPIENT_UNIQUE_ID
        defaultApplicationShouldBeFound("recipientUniqueId.doesNotContain=" + UPDATED_RECIPIENT_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByFarmerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where farmerId equals to DEFAULT_FARMER_ID
        defaultApplicationShouldBeFound("farmerId.equals=" + DEFAULT_FARMER_ID);

        // Get all the applicationList where farmerId equals to UPDATED_FARMER_ID
        defaultApplicationShouldNotBeFound("farmerId.equals=" + UPDATED_FARMER_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByFarmerIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where farmerId in DEFAULT_FARMER_ID or UPDATED_FARMER_ID
        defaultApplicationShouldBeFound("farmerId.in=" + DEFAULT_FARMER_ID + "," + UPDATED_FARMER_ID);

        // Get all the applicationList where farmerId equals to UPDATED_FARMER_ID
        defaultApplicationShouldNotBeFound("farmerId.in=" + UPDATED_FARMER_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByFarmerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where farmerId is not null
        defaultApplicationShouldBeFound("farmerId.specified=true");

        // Get all the applicationList where farmerId is null
        defaultApplicationShouldNotBeFound("farmerId.specified=false");
    }

    @Test
    @Transactional
    void getAllApplicationsByFarmerIdContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where farmerId contains DEFAULT_FARMER_ID
        defaultApplicationShouldBeFound("farmerId.contains=" + DEFAULT_FARMER_ID);

        // Get all the applicationList where farmerId contains UPDATED_FARMER_ID
        defaultApplicationShouldNotBeFound("farmerId.contains=" + UPDATED_FARMER_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByFarmerIdNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where farmerId does not contain DEFAULT_FARMER_ID
        defaultApplicationShouldNotBeFound("farmerId.doesNotContain=" + DEFAULT_FARMER_ID);

        // Get all the applicationList where farmerId does not contain UPDATED_FARMER_ID
        defaultApplicationShouldBeFound("farmerId.doesNotContain=" + UPDATED_FARMER_ID);
    }

    @Test
    @Transactional
    void getAllApplicationsByIssFileParserIsEqualToSomething() throws Exception {
        IssFileParser issFileParser;
        if (TestUtil.findAll(em, IssFileParser.class).isEmpty()) {
            applicationRepository.saveAndFlush(application);
            issFileParser = IssFileParserResourceIT.createEntity(em);
        } else {
            issFileParser = TestUtil.findAll(em, IssFileParser.class).get(0);
        }
        em.persist(issFileParser);
        em.flush();
        application.setIssFileParser(issFileParser);
        applicationRepository.saveAndFlush(application);
        Long issFileParserId = issFileParser.getId();

        // Get all the applicationList where issFileParser equals to issFileParserId
        defaultApplicationShouldBeFound("issFileParserId.equals=" + issFileParserId);

        // Get all the applicationList where issFileParser equals to (issFileParserId + 1)
        defaultApplicationShouldNotBeFound("issFileParserId.equals=" + (issFileParserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationShouldBeFound(String filter) throws Exception {
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)))
            .andExpect(jsonPath("$.[*].uniqueId").value(hasItem(DEFAULT_UNIQUE_ID)))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].applicationStatus").value(hasItem(DEFAULT_APPLICATION_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].kccStatus").value(hasItem(DEFAULT_KCC_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].recipientUniqueId").value(hasItem(DEFAULT_RECIPIENT_UNIQUE_ID)))
            .andExpect(jsonPath("$.[*].farmerId").value(hasItem(DEFAULT_FARMER_ID)));

        // Check, that the count call also returns 1
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationShouldNotBeFound(String filter) throws Exception {
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        Application updatedApplication = applicationRepository.findById(application.getId()).get();
        // Disconnect from session so that the updates on updatedApplication are not directly saved in db
        em.detach(updatedApplication);
        updatedApplication
            .batchId(UPDATED_BATCH_ID)
            .uniqueId(UPDATED_UNIQUE_ID)
            .recordStatus(UPDATED_RECORD_STATUS)
            .applicationStatus(UPDATED_APPLICATION_STATUS)
            .kccStatus(UPDATED_KCC_STATUS)
            .recipientUniqueId(UPDATED_RECIPIENT_UNIQUE_ID)
            .farmerId(UPDATED_FARMER_ID);

        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplication.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplication))
            )
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testApplication.getUniqueId()).isEqualTo(UPDATED_UNIQUE_ID);
        assertThat(testApplication.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testApplication.getApplicationStatus()).isEqualTo(UPDATED_APPLICATION_STATUS);
        assertThat(testApplication.getKccStatus()).isEqualTo(UPDATED_KCC_STATUS);
        assertThat(testApplication.getRecipientUniqueId()).isEqualTo(UPDATED_RECIPIENT_UNIQUE_ID);
        assertThat(testApplication.getFarmerId()).isEqualTo(UPDATED_FARMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, application.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(application))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(application))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationWithPatch() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application using partial update
        Application partialUpdatedApplication = new Application();
        partialUpdatedApplication.setId(application.getId());

        partialUpdatedApplication.uniqueId(UPDATED_UNIQUE_ID).farmerId(UPDATED_FARMER_ID);

        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplication))
            )
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
        assertThat(testApplication.getUniqueId()).isEqualTo(UPDATED_UNIQUE_ID);
        assertThat(testApplication.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
        assertThat(testApplication.getApplicationStatus()).isEqualTo(DEFAULT_APPLICATION_STATUS);
        assertThat(testApplication.getKccStatus()).isEqualTo(DEFAULT_KCC_STATUS);
        assertThat(testApplication.getRecipientUniqueId()).isEqualTo(DEFAULT_RECIPIENT_UNIQUE_ID);
        assertThat(testApplication.getFarmerId()).isEqualTo(UPDATED_FARMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateApplicationWithPatch() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application using partial update
        Application partialUpdatedApplication = new Application();
        partialUpdatedApplication.setId(application.getId());

        partialUpdatedApplication
            .batchId(UPDATED_BATCH_ID)
            .uniqueId(UPDATED_UNIQUE_ID)
            .recordStatus(UPDATED_RECORD_STATUS)
            .applicationStatus(UPDATED_APPLICATION_STATUS)
            .kccStatus(UPDATED_KCC_STATUS)
            .recipientUniqueId(UPDATED_RECIPIENT_UNIQUE_ID)
            .farmerId(UPDATED_FARMER_ID);

        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplication))
            )
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testApplication.getUniqueId()).isEqualTo(UPDATED_UNIQUE_ID);
        assertThat(testApplication.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testApplication.getApplicationStatus()).isEqualTo(UPDATED_APPLICATION_STATUS);
        assertThat(testApplication.getKccStatus()).isEqualTo(UPDATED_KCC_STATUS);
        assertThat(testApplication.getRecipientUniqueId()).isEqualTo(UPDATED_RECIPIENT_UNIQUE_ID);
        assertThat(testApplication.getFarmerId()).isEqualTo(UPDATED_FARMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, application.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(application))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(application))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(application))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Delete the application
        restApplicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, application.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
