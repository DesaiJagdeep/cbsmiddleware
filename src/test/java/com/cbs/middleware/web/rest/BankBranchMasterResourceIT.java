package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.repository.BankBranchMasterRepository;
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
 * Integration tests for the {@link BankBranchMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankBranchMasterResourceIT {

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-branch-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankBranchMasterRepository bankBranchMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankBranchMasterMockMvc;

    private BankBranchMaster bankBranchMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankBranchMaster createEntity(EntityManager em) {
        BankBranchMaster bankBranchMaster = new BankBranchMaster()
            .branchCode(DEFAULT_BRANCH_CODE)
            .branchName(DEFAULT_BRANCH_NAME)
            .branchAddress(DEFAULT_BRANCH_ADDRESS)
            .bankCode(DEFAULT_BANK_CODE);
        return bankBranchMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankBranchMaster createUpdatedEntity(EntityManager em) {
        BankBranchMaster bankBranchMaster = new BankBranchMaster()
            .branchCode(UPDATED_BRANCH_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .bankCode(UPDATED_BANK_CODE);
        return bankBranchMaster;
    }

    @BeforeEach
    public void initTest() {
        bankBranchMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createBankBranchMaster() throws Exception {
        int databaseSizeBeforeCreate = bankBranchMasterRepository.findAll().size();
        // Create the BankBranchMaster
        restBankBranchMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankBranchMaster))
            )
            .andExpect(status().isCreated());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeCreate + 1);
        BankBranchMaster testBankBranchMaster = bankBranchMasterList.get(bankBranchMasterList.size() - 1);
        assertThat(testBankBranchMaster.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testBankBranchMaster.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testBankBranchMaster.getBranchAddress()).isEqualTo(DEFAULT_BRANCH_ADDRESS);
        assertThat(testBankBranchMaster.getBankCode()).isEqualTo(DEFAULT_BANK_CODE);
    }

    @Test
    @Transactional
    void createBankBranchMasterWithExistingId() throws Exception {
        // Create the BankBranchMaster with an existing ID
        bankBranchMaster.setId(1L);

        int databaseSizeBeforeCreate = bankBranchMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankBranchMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankBranchMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankBranchMasters() throws Exception {
        // Initialize the database
        bankBranchMasterRepository.saveAndFlush(bankBranchMaster);

        // Get all the bankBranchMasterList
        restBankBranchMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankBranchMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].branchAddress").value(hasItem(DEFAULT_BRANCH_ADDRESS)))
            .andExpect(jsonPath("$.[*].bankCode").value(hasItem(DEFAULT_BANK_CODE)));
    }

    @Test
    @Transactional
    void getBankBranchMaster() throws Exception {
        // Initialize the database
        bankBranchMasterRepository.saveAndFlush(bankBranchMaster);

        // Get the bankBranchMaster
        restBankBranchMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, bankBranchMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankBranchMaster.getId().intValue()))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.branchAddress").value(DEFAULT_BRANCH_ADDRESS))
            .andExpect(jsonPath("$.bankCode").value(DEFAULT_BANK_CODE));
    }

    @Test
    @Transactional
    void getNonExistingBankBranchMaster() throws Exception {
        // Get the bankBranchMaster
        restBankBranchMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankBranchMaster() throws Exception {
        // Initialize the database
        bankBranchMasterRepository.saveAndFlush(bankBranchMaster);

        int databaseSizeBeforeUpdate = bankBranchMasterRepository.findAll().size();

        // Update the bankBranchMaster
        BankBranchMaster updatedBankBranchMaster = bankBranchMasterRepository.findById(bankBranchMaster.getId()).get();
        // Disconnect from session so that the updates on updatedBankBranchMaster are not directly saved in db
        em.detach(updatedBankBranchMaster);
        updatedBankBranchMaster
            .branchCode(UPDATED_BRANCH_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .bankCode(UPDATED_BANK_CODE);

        restBankBranchMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankBranchMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankBranchMaster))
            )
            .andExpect(status().isOk());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeUpdate);
        BankBranchMaster testBankBranchMaster = bankBranchMasterList.get(bankBranchMasterList.size() - 1);
        assertThat(testBankBranchMaster.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testBankBranchMaster.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBankBranchMaster.getBranchAddress()).isEqualTo(UPDATED_BRANCH_ADDRESS);
        assertThat(testBankBranchMaster.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void putNonExistingBankBranchMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchMasterRepository.findAll().size();
        bankBranchMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankBranchMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankBranchMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankBranchMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchMasterRepository.findAll().size();
        bankBranchMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankBranchMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankBranchMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchMasterRepository.findAll().size();
        bankBranchMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankBranchMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankBranchMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankBranchMasterWithPatch() throws Exception {
        // Initialize the database
        bankBranchMasterRepository.saveAndFlush(bankBranchMaster);

        int databaseSizeBeforeUpdate = bankBranchMasterRepository.findAll().size();

        // Update the bankBranchMaster using partial update
        BankBranchMaster partialUpdatedBankBranchMaster = new BankBranchMaster();
        partialUpdatedBankBranchMaster.setId(bankBranchMaster.getId());

        partialUpdatedBankBranchMaster.branchName(UPDATED_BRANCH_NAME).branchAddress(UPDATED_BRANCH_ADDRESS);

        restBankBranchMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankBranchMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankBranchMaster))
            )
            .andExpect(status().isOk());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeUpdate);
        BankBranchMaster testBankBranchMaster = bankBranchMasterList.get(bankBranchMasterList.size() - 1);
        assertThat(testBankBranchMaster.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testBankBranchMaster.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBankBranchMaster.getBranchAddress()).isEqualTo(UPDATED_BRANCH_ADDRESS);
        assertThat(testBankBranchMaster.getBankCode()).isEqualTo(DEFAULT_BANK_CODE);
    }

    @Test
    @Transactional
    void fullUpdateBankBranchMasterWithPatch() throws Exception {
        // Initialize the database
        bankBranchMasterRepository.saveAndFlush(bankBranchMaster);

        int databaseSizeBeforeUpdate = bankBranchMasterRepository.findAll().size();

        // Update the bankBranchMaster using partial update
        BankBranchMaster partialUpdatedBankBranchMaster = new BankBranchMaster();
        partialUpdatedBankBranchMaster.setId(bankBranchMaster.getId());

        partialUpdatedBankBranchMaster
            .branchCode(UPDATED_BRANCH_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .bankCode(UPDATED_BANK_CODE);

        restBankBranchMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankBranchMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankBranchMaster))
            )
            .andExpect(status().isOk());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeUpdate);
        BankBranchMaster testBankBranchMaster = bankBranchMasterList.get(bankBranchMasterList.size() - 1);
        assertThat(testBankBranchMaster.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testBankBranchMaster.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBankBranchMaster.getBranchAddress()).isEqualTo(UPDATED_BRANCH_ADDRESS);
        assertThat(testBankBranchMaster.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingBankBranchMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchMasterRepository.findAll().size();
        bankBranchMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankBranchMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankBranchMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankBranchMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchMasterRepository.findAll().size();
        bankBranchMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankBranchMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankBranchMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankBranchMasterRepository.findAll().size();
        bankBranchMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankBranchMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankBranchMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankBranchMaster in the database
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankBranchMaster() throws Exception {
        // Initialize the database
        bankBranchMasterRepository.saveAndFlush(bankBranchMaster);

        int databaseSizeBeforeDelete = bankBranchMasterRepository.findAll().size();

        // Delete the bankBranchMaster
        restBankBranchMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankBranchMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankBranchMaster> bankBranchMasterList = bankBranchMasterRepository.findAll();
        assertThat(bankBranchMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
