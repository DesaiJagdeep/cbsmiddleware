package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
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
 * Integration tests for the {@link AccountHolderMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountHolderMasterResourceIT {

    private static final Integer DEFAULT_ACCOUNT_HOLDER_CODE = 1;
    private static final Integer UPDATED_ACCOUNT_HOLDER_CODE = 2;

    private static final String DEFAULT_ACCOUNT_HOLDER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/account-holder-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountHolderMasterRepository accountHolderMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountHolderMasterMockMvc;

    private AccountHolderMaster accountHolderMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountHolderMaster createEntity(EntityManager em) {
        AccountHolderMaster accountHolderMaster = new AccountHolderMaster()
            .accountHolderCode(DEFAULT_ACCOUNT_HOLDER_CODE)
            .accountHolder(DEFAULT_ACCOUNT_HOLDER);
        return accountHolderMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountHolderMaster createUpdatedEntity(EntityManager em) {
        AccountHolderMaster accountHolderMaster = new AccountHolderMaster()
            .accountHolderCode(UPDATED_ACCOUNT_HOLDER_CODE)
            .accountHolder(UPDATED_ACCOUNT_HOLDER);
        return accountHolderMaster;
    }

    @BeforeEach
    public void initTest() {
        accountHolderMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountHolderMaster() throws Exception {
        int databaseSizeBeforeCreate = accountHolderMasterRepository.findAll().size();
        // Create the AccountHolderMaster
        restAccountHolderMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountHolderMaster))
            )
            .andExpect(status().isCreated());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeCreate + 1);
        AccountHolderMaster testAccountHolderMaster = accountHolderMasterList.get(accountHolderMasterList.size() - 1);
        assertThat(testAccountHolderMaster.getAccountHolderCode()).isEqualTo(DEFAULT_ACCOUNT_HOLDER_CODE);
        assertThat(testAccountHolderMaster.getAccountHolder()).isEqualTo(DEFAULT_ACCOUNT_HOLDER);
    }

    @Test
    @Transactional
    void createAccountHolderMasterWithExistingId() throws Exception {
        // Create the AccountHolderMaster with an existing ID
        accountHolderMaster.setId(1L);

        int databaseSizeBeforeCreate = accountHolderMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountHolderMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountHolderMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccountHolderMasters() throws Exception {
        // Initialize the database
        accountHolderMasterRepository.saveAndFlush(accountHolderMaster);

        // Get all the accountHolderMasterList
        restAccountHolderMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountHolderMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountHolderCode").value(hasItem(DEFAULT_ACCOUNT_HOLDER_CODE)))
            .andExpect(jsonPath("$.[*].accountHolder").value(hasItem(DEFAULT_ACCOUNT_HOLDER)));
    }

    @Test
    @Transactional
    void getAccountHolderMaster() throws Exception {
        // Initialize the database
        accountHolderMasterRepository.saveAndFlush(accountHolderMaster);

        // Get the accountHolderMaster
        restAccountHolderMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, accountHolderMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountHolderMaster.getId().intValue()))
            .andExpect(jsonPath("$.accountHolderCode").value(DEFAULT_ACCOUNT_HOLDER_CODE))
            .andExpect(jsonPath("$.accountHolder").value(DEFAULT_ACCOUNT_HOLDER));
    }

    @Test
    @Transactional
    void getNonExistingAccountHolderMaster() throws Exception {
        // Get the accountHolderMaster
        restAccountHolderMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccountHolderMaster() throws Exception {
        // Initialize the database
        accountHolderMasterRepository.saveAndFlush(accountHolderMaster);

        int databaseSizeBeforeUpdate = accountHolderMasterRepository.findAll().size();

        // Update the accountHolderMaster
        AccountHolderMaster updatedAccountHolderMaster = accountHolderMasterRepository.findById(accountHolderMaster.getId()).get();
        // Disconnect from session so that the updates on updatedAccountHolderMaster are not directly saved in db
        em.detach(updatedAccountHolderMaster);
        updatedAccountHolderMaster.accountHolderCode(UPDATED_ACCOUNT_HOLDER_CODE).accountHolder(UPDATED_ACCOUNT_HOLDER);

        restAccountHolderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccountHolderMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccountHolderMaster))
            )
            .andExpect(status().isOk());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeUpdate);
        AccountHolderMaster testAccountHolderMaster = accountHolderMasterList.get(accountHolderMasterList.size() - 1);
        assertThat(testAccountHolderMaster.getAccountHolderCode()).isEqualTo(UPDATED_ACCOUNT_HOLDER_CODE);
        assertThat(testAccountHolderMaster.getAccountHolder()).isEqualTo(UPDATED_ACCOUNT_HOLDER);
    }

    @Test
    @Transactional
    void putNonExistingAccountHolderMaster() throws Exception {
        int databaseSizeBeforeUpdate = accountHolderMasterRepository.findAll().size();
        accountHolderMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountHolderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountHolderMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountHolderMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountHolderMaster() throws Exception {
        int databaseSizeBeforeUpdate = accountHolderMasterRepository.findAll().size();
        accountHolderMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountHolderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountHolderMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountHolderMaster() throws Exception {
        int databaseSizeBeforeUpdate = accountHolderMasterRepository.findAll().size();
        accountHolderMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountHolderMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountHolderMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountHolderMasterWithPatch() throws Exception {
        // Initialize the database
        accountHolderMasterRepository.saveAndFlush(accountHolderMaster);

        int databaseSizeBeforeUpdate = accountHolderMasterRepository.findAll().size();

        // Update the accountHolderMaster using partial update
        AccountHolderMaster partialUpdatedAccountHolderMaster = new AccountHolderMaster();
        partialUpdatedAccountHolderMaster.setId(accountHolderMaster.getId());

        partialUpdatedAccountHolderMaster.accountHolderCode(UPDATED_ACCOUNT_HOLDER_CODE).accountHolder(UPDATED_ACCOUNT_HOLDER);

        restAccountHolderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountHolderMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountHolderMaster))
            )
            .andExpect(status().isOk());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeUpdate);
        AccountHolderMaster testAccountHolderMaster = accountHolderMasterList.get(accountHolderMasterList.size() - 1);
        assertThat(testAccountHolderMaster.getAccountHolderCode()).isEqualTo(UPDATED_ACCOUNT_HOLDER_CODE);
        assertThat(testAccountHolderMaster.getAccountHolder()).isEqualTo(UPDATED_ACCOUNT_HOLDER);
    }

    @Test
    @Transactional
    void fullUpdateAccountHolderMasterWithPatch() throws Exception {
        // Initialize the database
        accountHolderMasterRepository.saveAndFlush(accountHolderMaster);

        int databaseSizeBeforeUpdate = accountHolderMasterRepository.findAll().size();

        // Update the accountHolderMaster using partial update
        AccountHolderMaster partialUpdatedAccountHolderMaster = new AccountHolderMaster();
        partialUpdatedAccountHolderMaster.setId(accountHolderMaster.getId());

        partialUpdatedAccountHolderMaster.accountHolderCode(UPDATED_ACCOUNT_HOLDER_CODE).accountHolder(UPDATED_ACCOUNT_HOLDER);

        restAccountHolderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountHolderMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountHolderMaster))
            )
            .andExpect(status().isOk());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeUpdate);
        AccountHolderMaster testAccountHolderMaster = accountHolderMasterList.get(accountHolderMasterList.size() - 1);
        assertThat(testAccountHolderMaster.getAccountHolderCode()).isEqualTo(UPDATED_ACCOUNT_HOLDER_CODE);
        assertThat(testAccountHolderMaster.getAccountHolder()).isEqualTo(UPDATED_ACCOUNT_HOLDER);
    }

    @Test
    @Transactional
    void patchNonExistingAccountHolderMaster() throws Exception {
        int databaseSizeBeforeUpdate = accountHolderMasterRepository.findAll().size();
        accountHolderMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountHolderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountHolderMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountHolderMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountHolderMaster() throws Exception {
        int databaseSizeBeforeUpdate = accountHolderMasterRepository.findAll().size();
        accountHolderMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountHolderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountHolderMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountHolderMaster() throws Exception {
        int databaseSizeBeforeUpdate = accountHolderMasterRepository.findAll().size();
        accountHolderMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountHolderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountHolderMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountHolderMaster in the database
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountHolderMaster() throws Exception {
        // Initialize the database
        accountHolderMasterRepository.saveAndFlush(accountHolderMaster);

        int databaseSizeBeforeDelete = accountHolderMasterRepository.findAll().size();

        // Delete the accountHolderMaster
        restAccountHolderMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountHolderMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountHolderMaster> accountHolderMasterList = accountHolderMasterRepository.findAll();
        assertThat(accountHolderMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
