package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.BatchTransaction;
import com.cbs.middleware.repository.BatchTransactionRepository;
import com.cbs.middleware.service.criteria.BatchTransactionCriteria;
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
 * Integration tests for the {@link BatchTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BatchTransactionResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_BATCH_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_DETAILS = "BBBBBBBBBB";

    private static final Long DEFAULT_APPLICATION_COUNT = 1L;
    private static final Long UPDATED_APPLICATION_COUNT = 2L;
    private static final Long SMALLER_APPLICATION_COUNT = 1L - 1L;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_BATCH_ID = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BATCH_ACK_ID = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_ACK_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/batch-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BatchTransactionRepository batchTransactionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBatchTransactionMockMvc;

    private BatchTransaction batchTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchTransaction createEntity(EntityManager em) {
        BatchTransaction batchTransaction = new BatchTransaction()
            .status(DEFAULT_STATUS)
            .batchDetails(DEFAULT_BATCH_DETAILS)
            .applicationCount(DEFAULT_APPLICATION_COUNT)
            .notes(DEFAULT_NOTES)
            .batchId(DEFAULT_BATCH_ID)
            .batchAckId(DEFAULT_BATCH_ACK_ID);
        return batchTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchTransaction createUpdatedEntity(EntityManager em) {
        BatchTransaction batchTransaction = new BatchTransaction()
            .status(UPDATED_STATUS)
            .batchDetails(UPDATED_BATCH_DETAILS)
            .applicationCount(UPDATED_APPLICATION_COUNT)
            .notes(UPDATED_NOTES)
            .batchId(UPDATED_BATCH_ID)
            .batchAckId(UPDATED_BATCH_ACK_ID);
        return batchTransaction;
    }

    @BeforeEach
    public void initTest() {
        batchTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createBatchTransaction() throws Exception {
        int databaseSizeBeforeCreate = batchTransactionRepository.findAll().size();
        // Create the BatchTransaction
        restBatchTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(batchTransaction))
            )
            .andExpect(status().isCreated());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        BatchTransaction testBatchTransaction = batchTransactionList.get(batchTransactionList.size() - 1);
        assertThat(testBatchTransaction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBatchTransaction.getBatchDetails()).isEqualTo(DEFAULT_BATCH_DETAILS);
        assertThat(testBatchTransaction.getApplicationCount()).isEqualTo(DEFAULT_APPLICATION_COUNT);
        assertThat(testBatchTransaction.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testBatchTransaction.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
        assertThat(testBatchTransaction.getBatchAckId()).isEqualTo(DEFAULT_BATCH_ACK_ID);
    }

    @Test
    @Transactional
    void createBatchTransactionWithExistingId() throws Exception {
        // Create the BatchTransaction with an existing ID
        batchTransaction.setId(1L);

        int databaseSizeBeforeCreate = batchTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatchTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(batchTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBatchTransactions() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList
        restBatchTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batchTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].batchDetails").value(hasItem(DEFAULT_BATCH_DETAILS)))
            .andExpect(jsonPath("$.[*].applicationCount").value(hasItem(DEFAULT_APPLICATION_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)))
            .andExpect(jsonPath("$.[*].batchAckId").value(hasItem(DEFAULT_BATCH_ACK_ID)));
    }

    @Test
    @Transactional
    void getBatchTransaction() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get the batchTransaction
        restBatchTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, batchTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(batchTransaction.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.batchDetails").value(DEFAULT_BATCH_DETAILS))
            .andExpect(jsonPath("$.applicationCount").value(DEFAULT_APPLICATION_COUNT.intValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID))
            .andExpect(jsonPath("$.batchAckId").value(DEFAULT_BATCH_ACK_ID));
    }

    @Test
    @Transactional
    void getBatchTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        Long id = batchTransaction.getId();

        defaultBatchTransactionShouldBeFound("id.equals=" + id);
        defaultBatchTransactionShouldNotBeFound("id.notEquals=" + id);

        defaultBatchTransactionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBatchTransactionShouldNotBeFound("id.greaterThan=" + id);

        defaultBatchTransactionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBatchTransactionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where status equals to DEFAULT_STATUS
        defaultBatchTransactionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the batchTransactionList where status equals to UPDATED_STATUS
        defaultBatchTransactionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBatchTransactionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the batchTransactionList where status equals to UPDATED_STATUS
        defaultBatchTransactionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where status is not null
        defaultBatchTransactionShouldBeFound("status.specified=true");

        // Get all the batchTransactionList where status is null
        defaultBatchTransactionShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByStatusContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where status contains DEFAULT_STATUS
        defaultBatchTransactionShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the batchTransactionList where status contains UPDATED_STATUS
        defaultBatchTransactionShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where status does not contain DEFAULT_STATUS
        defaultBatchTransactionShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the batchTransactionList where status does not contain UPDATED_STATUS
        defaultBatchTransactionShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchDetails equals to DEFAULT_BATCH_DETAILS
        defaultBatchTransactionShouldBeFound("batchDetails.equals=" + DEFAULT_BATCH_DETAILS);

        // Get all the batchTransactionList where batchDetails equals to UPDATED_BATCH_DETAILS
        defaultBatchTransactionShouldNotBeFound("batchDetails.equals=" + UPDATED_BATCH_DETAILS);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchDetails in DEFAULT_BATCH_DETAILS or UPDATED_BATCH_DETAILS
        defaultBatchTransactionShouldBeFound("batchDetails.in=" + DEFAULT_BATCH_DETAILS + "," + UPDATED_BATCH_DETAILS);

        // Get all the batchTransactionList where batchDetails equals to UPDATED_BATCH_DETAILS
        defaultBatchTransactionShouldNotBeFound("batchDetails.in=" + UPDATED_BATCH_DETAILS);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchDetails is not null
        defaultBatchTransactionShouldBeFound("batchDetails.specified=true");

        // Get all the batchTransactionList where batchDetails is null
        defaultBatchTransactionShouldNotBeFound("batchDetails.specified=false");
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchDetailsContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchDetails contains DEFAULT_BATCH_DETAILS
        defaultBatchTransactionShouldBeFound("batchDetails.contains=" + DEFAULT_BATCH_DETAILS);

        // Get all the batchTransactionList where batchDetails contains UPDATED_BATCH_DETAILS
        defaultBatchTransactionShouldNotBeFound("batchDetails.contains=" + UPDATED_BATCH_DETAILS);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchDetails does not contain DEFAULT_BATCH_DETAILS
        defaultBatchTransactionShouldNotBeFound("batchDetails.doesNotContain=" + DEFAULT_BATCH_DETAILS);

        // Get all the batchTransactionList where batchDetails does not contain UPDATED_BATCH_DETAILS
        defaultBatchTransactionShouldBeFound("batchDetails.doesNotContain=" + UPDATED_BATCH_DETAILS);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByApplicationCountIsEqualToSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where applicationCount equals to DEFAULT_APPLICATION_COUNT
        defaultBatchTransactionShouldBeFound("applicationCount.equals=" + DEFAULT_APPLICATION_COUNT);

        // Get all the batchTransactionList where applicationCount equals to UPDATED_APPLICATION_COUNT
        defaultBatchTransactionShouldNotBeFound("applicationCount.equals=" + UPDATED_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByApplicationCountIsInShouldWork() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where applicationCount in DEFAULT_APPLICATION_COUNT or UPDATED_APPLICATION_COUNT
        defaultBatchTransactionShouldBeFound("applicationCount.in=" + DEFAULT_APPLICATION_COUNT + "," + UPDATED_APPLICATION_COUNT);

        // Get all the batchTransactionList where applicationCount equals to UPDATED_APPLICATION_COUNT
        defaultBatchTransactionShouldNotBeFound("applicationCount.in=" + UPDATED_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByApplicationCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where applicationCount is not null
        defaultBatchTransactionShouldBeFound("applicationCount.specified=true");

        // Get all the batchTransactionList where applicationCount is null
        defaultBatchTransactionShouldNotBeFound("applicationCount.specified=false");
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByApplicationCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where applicationCount is greater than or equal to DEFAULT_APPLICATION_COUNT
        defaultBatchTransactionShouldBeFound("applicationCount.greaterThanOrEqual=" + DEFAULT_APPLICATION_COUNT);

        // Get all the batchTransactionList where applicationCount is greater than or equal to UPDATED_APPLICATION_COUNT
        defaultBatchTransactionShouldNotBeFound("applicationCount.greaterThanOrEqual=" + UPDATED_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByApplicationCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where applicationCount is less than or equal to DEFAULT_APPLICATION_COUNT
        defaultBatchTransactionShouldBeFound("applicationCount.lessThanOrEqual=" + DEFAULT_APPLICATION_COUNT);

        // Get all the batchTransactionList where applicationCount is less than or equal to SMALLER_APPLICATION_COUNT
        defaultBatchTransactionShouldNotBeFound("applicationCount.lessThanOrEqual=" + SMALLER_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByApplicationCountIsLessThanSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where applicationCount is less than DEFAULT_APPLICATION_COUNT
        defaultBatchTransactionShouldNotBeFound("applicationCount.lessThan=" + DEFAULT_APPLICATION_COUNT);

        // Get all the batchTransactionList where applicationCount is less than UPDATED_APPLICATION_COUNT
        defaultBatchTransactionShouldBeFound("applicationCount.lessThan=" + UPDATED_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByApplicationCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where applicationCount is greater than DEFAULT_APPLICATION_COUNT
        defaultBatchTransactionShouldNotBeFound("applicationCount.greaterThan=" + DEFAULT_APPLICATION_COUNT);

        // Get all the batchTransactionList where applicationCount is greater than SMALLER_APPLICATION_COUNT
        defaultBatchTransactionShouldBeFound("applicationCount.greaterThan=" + SMALLER_APPLICATION_COUNT);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where notes equals to DEFAULT_NOTES
        defaultBatchTransactionShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the batchTransactionList where notes equals to UPDATED_NOTES
        defaultBatchTransactionShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultBatchTransactionShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the batchTransactionList where notes equals to UPDATED_NOTES
        defaultBatchTransactionShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where notes is not null
        defaultBatchTransactionShouldBeFound("notes.specified=true");

        // Get all the batchTransactionList where notes is null
        defaultBatchTransactionShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByNotesContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where notes contains DEFAULT_NOTES
        defaultBatchTransactionShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the batchTransactionList where notes contains UPDATED_NOTES
        defaultBatchTransactionShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where notes does not contain DEFAULT_NOTES
        defaultBatchTransactionShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the batchTransactionList where notes does not contain UPDATED_NOTES
        defaultBatchTransactionShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchId equals to DEFAULT_BATCH_ID
        defaultBatchTransactionShouldBeFound("batchId.equals=" + DEFAULT_BATCH_ID);

        // Get all the batchTransactionList where batchId equals to UPDATED_BATCH_ID
        defaultBatchTransactionShouldNotBeFound("batchId.equals=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchIdIsInShouldWork() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchId in DEFAULT_BATCH_ID or UPDATED_BATCH_ID
        defaultBatchTransactionShouldBeFound("batchId.in=" + DEFAULT_BATCH_ID + "," + UPDATED_BATCH_ID);

        // Get all the batchTransactionList where batchId equals to UPDATED_BATCH_ID
        defaultBatchTransactionShouldNotBeFound("batchId.in=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchId is not null
        defaultBatchTransactionShouldBeFound("batchId.specified=true");

        // Get all the batchTransactionList where batchId is null
        defaultBatchTransactionShouldNotBeFound("batchId.specified=false");
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchIdContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchId contains DEFAULT_BATCH_ID
        defaultBatchTransactionShouldBeFound("batchId.contains=" + DEFAULT_BATCH_ID);

        // Get all the batchTransactionList where batchId contains UPDATED_BATCH_ID
        defaultBatchTransactionShouldNotBeFound("batchId.contains=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchIdNotContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchId does not contain DEFAULT_BATCH_ID
        defaultBatchTransactionShouldNotBeFound("batchId.doesNotContain=" + DEFAULT_BATCH_ID);

        // Get all the batchTransactionList where batchId does not contain UPDATED_BATCH_ID
        defaultBatchTransactionShouldBeFound("batchId.doesNotContain=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchAckIdIsEqualToSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchAckId equals to DEFAULT_BATCH_ACK_ID
        defaultBatchTransactionShouldBeFound("batchAckId.equals=" + DEFAULT_BATCH_ACK_ID);

        // Get all the batchTransactionList where batchAckId equals to UPDATED_BATCH_ACK_ID
        defaultBatchTransactionShouldNotBeFound("batchAckId.equals=" + UPDATED_BATCH_ACK_ID);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchAckIdIsInShouldWork() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchAckId in DEFAULT_BATCH_ACK_ID or UPDATED_BATCH_ACK_ID
        defaultBatchTransactionShouldBeFound("batchAckId.in=" + DEFAULT_BATCH_ACK_ID + "," + UPDATED_BATCH_ACK_ID);

        // Get all the batchTransactionList where batchAckId equals to UPDATED_BATCH_ACK_ID
        defaultBatchTransactionShouldNotBeFound("batchAckId.in=" + UPDATED_BATCH_ACK_ID);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchAckIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchAckId is not null
        defaultBatchTransactionShouldBeFound("batchAckId.specified=true");

        // Get all the batchTransactionList where batchAckId is null
        defaultBatchTransactionShouldNotBeFound("batchAckId.specified=false");
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchAckIdContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchAckId contains DEFAULT_BATCH_ACK_ID
        defaultBatchTransactionShouldBeFound("batchAckId.contains=" + DEFAULT_BATCH_ACK_ID);

        // Get all the batchTransactionList where batchAckId contains UPDATED_BATCH_ACK_ID
        defaultBatchTransactionShouldNotBeFound("batchAckId.contains=" + UPDATED_BATCH_ACK_ID);
    }

    @Test
    @Transactional
    void getAllBatchTransactionsByBatchAckIdNotContainsSomething() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        // Get all the batchTransactionList where batchAckId does not contain DEFAULT_BATCH_ACK_ID
        defaultBatchTransactionShouldNotBeFound("batchAckId.doesNotContain=" + DEFAULT_BATCH_ACK_ID);

        // Get all the batchTransactionList where batchAckId does not contain UPDATED_BATCH_ACK_ID
        defaultBatchTransactionShouldBeFound("batchAckId.doesNotContain=" + UPDATED_BATCH_ACK_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBatchTransactionShouldBeFound(String filter) throws Exception {
        restBatchTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batchTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].batchDetails").value(hasItem(DEFAULT_BATCH_DETAILS)))
            .andExpect(jsonPath("$.[*].applicationCount").value(hasItem(DEFAULT_APPLICATION_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)))
            .andExpect(jsonPath("$.[*].batchAckId").value(hasItem(DEFAULT_BATCH_ACK_ID)));

        // Check, that the count call also returns 1
        restBatchTransactionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBatchTransactionShouldNotBeFound(String filter) throws Exception {
        restBatchTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBatchTransactionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBatchTransaction() throws Exception {
        // Get the batchTransaction
        restBatchTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBatchTransaction() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        int databaseSizeBeforeUpdate = batchTransactionRepository.findAll().size();

        // Update the batchTransaction
        BatchTransaction updatedBatchTransaction = batchTransactionRepository.findById(batchTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedBatchTransaction are not directly saved in db
        em.detach(updatedBatchTransaction);
        updatedBatchTransaction
            .status(UPDATED_STATUS)
            .batchDetails(UPDATED_BATCH_DETAILS)
            .applicationCount(UPDATED_APPLICATION_COUNT)
            .notes(UPDATED_NOTES)
            .batchId(UPDATED_BATCH_ID)
            .batchAckId(UPDATED_BATCH_ACK_ID);

        restBatchTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBatchTransaction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBatchTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeUpdate);
        BatchTransaction testBatchTransaction = batchTransactionList.get(batchTransactionList.size() - 1);
        assertThat(testBatchTransaction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBatchTransaction.getBatchDetails()).isEqualTo(UPDATED_BATCH_DETAILS);
        assertThat(testBatchTransaction.getApplicationCount()).isEqualTo(UPDATED_APPLICATION_COUNT);
        assertThat(testBatchTransaction.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBatchTransaction.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testBatchTransaction.getBatchAckId()).isEqualTo(UPDATED_BATCH_ACK_ID);
    }

    @Test
    @Transactional
    void putNonExistingBatchTransaction() throws Exception {
        int databaseSizeBeforeUpdate = batchTransactionRepository.findAll().size();
        batchTransaction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, batchTransaction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batchTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBatchTransaction() throws Exception {
        int databaseSizeBeforeUpdate = batchTransactionRepository.findAll().size();
        batchTransaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batchTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBatchTransaction() throws Exception {
        int databaseSizeBeforeUpdate = batchTransactionRepository.findAll().size();
        batchTransaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchTransactionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(batchTransaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBatchTransactionWithPatch() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        int databaseSizeBeforeUpdate = batchTransactionRepository.findAll().size();

        // Update the batchTransaction using partial update
        BatchTransaction partialUpdatedBatchTransaction = new BatchTransaction();
        partialUpdatedBatchTransaction.setId(batchTransaction.getId());

        partialUpdatedBatchTransaction
            .status(UPDATED_STATUS)
            .batchDetails(UPDATED_BATCH_DETAILS)
            .applicationCount(UPDATED_APPLICATION_COUNT)
            .notes(UPDATED_NOTES)
            .batchId(UPDATED_BATCH_ID)
            .batchAckId(UPDATED_BATCH_ACK_ID);

        restBatchTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBatchTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeUpdate);
        BatchTransaction testBatchTransaction = batchTransactionList.get(batchTransactionList.size() - 1);
        assertThat(testBatchTransaction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBatchTransaction.getBatchDetails()).isEqualTo(UPDATED_BATCH_DETAILS);
        assertThat(testBatchTransaction.getApplicationCount()).isEqualTo(UPDATED_APPLICATION_COUNT);
        assertThat(testBatchTransaction.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBatchTransaction.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testBatchTransaction.getBatchAckId()).isEqualTo(UPDATED_BATCH_ACK_ID);
    }

    @Test
    @Transactional
    void fullUpdateBatchTransactionWithPatch() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        int databaseSizeBeforeUpdate = batchTransactionRepository.findAll().size();

        // Update the batchTransaction using partial update
        BatchTransaction partialUpdatedBatchTransaction = new BatchTransaction();
        partialUpdatedBatchTransaction.setId(batchTransaction.getId());

        partialUpdatedBatchTransaction
            .status(UPDATED_STATUS)
            .batchDetails(UPDATED_BATCH_DETAILS)
            .applicationCount(UPDATED_APPLICATION_COUNT)
            .notes(UPDATED_NOTES)
            .batchId(UPDATED_BATCH_ID)
            .batchAckId(UPDATED_BATCH_ACK_ID);

        restBatchTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBatchTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeUpdate);
        BatchTransaction testBatchTransaction = batchTransactionList.get(batchTransactionList.size() - 1);
        assertThat(testBatchTransaction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBatchTransaction.getBatchDetails()).isEqualTo(UPDATED_BATCH_DETAILS);
        assertThat(testBatchTransaction.getApplicationCount()).isEqualTo(UPDATED_APPLICATION_COUNT);
        assertThat(testBatchTransaction.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBatchTransaction.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testBatchTransaction.getBatchAckId()).isEqualTo(UPDATED_BATCH_ACK_ID);
    }

    @Test
    @Transactional
    void patchNonExistingBatchTransaction() throws Exception {
        int databaseSizeBeforeUpdate = batchTransactionRepository.findAll().size();
        batchTransaction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, batchTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(batchTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBatchTransaction() throws Exception {
        int databaseSizeBeforeUpdate = batchTransactionRepository.findAll().size();
        batchTransaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(batchTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBatchTransaction() throws Exception {
        int databaseSizeBeforeUpdate = batchTransactionRepository.findAll().size();
        batchTransaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(batchTransaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BatchTransaction in the database
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBatchTransaction() throws Exception {
        // Initialize the database
        batchTransactionRepository.saveAndFlush(batchTransaction);

        int databaseSizeBeforeDelete = batchTransactionRepository.findAll().size();

        // Delete the batchTransaction
        restBatchTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, batchTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BatchTransaction> batchTransactionList = batchTransactionRepository.findAll();
        assertThat(batchTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
