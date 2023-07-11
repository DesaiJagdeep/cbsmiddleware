package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.BankMaster;
import com.cbs.middleware.repository.BankMasterRepository;
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
 * Integration tests for the {@link BankMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankMasterResourceIT {

    private static final String DEFAULT_BANK_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankMasterRepository bankMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankMasterMockMvc;

    private BankMaster bankMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankMaster createEntity(EntityManager em) {
        BankMaster bankMaster = new BankMaster().bankCode(DEFAULT_BANK_CODE).bankName(DEFAULT_BANK_NAME);
        return bankMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankMaster createUpdatedEntity(EntityManager em) {
        BankMaster bankMaster = new BankMaster().bankCode(UPDATED_BANK_CODE).bankName(UPDATED_BANK_NAME);
        return bankMaster;
    }

    @BeforeEach
    public void initTest() {
        bankMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createBankMaster() throws Exception {
        int databaseSizeBeforeCreate = bankMasterRepository.findAll().size();
        // Create the BankMaster
        restBankMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankMaster)))
            .andExpect(status().isCreated());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeCreate + 1);
        BankMaster testBankMaster = bankMasterList.get(bankMasterList.size() - 1);
        assertThat(testBankMaster.getBankCode()).isEqualTo(DEFAULT_BANK_CODE);
        assertThat(testBankMaster.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
    }

    @Test
    @Transactional
    void createBankMasterWithExistingId() throws Exception {
        // Create the BankMaster with an existing ID
        bankMaster.setId(1L);

        int databaseSizeBeforeCreate = bankMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankMaster)))
            .andExpect(status().isBadRequest());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankMasters() throws Exception {
        // Initialize the database
        bankMasterRepository.saveAndFlush(bankMaster);

        // Get all the bankMasterList
        restBankMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankCode").value(hasItem(DEFAULT_BANK_CODE)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)));
    }

    @Test
    @Transactional
    void getBankMaster() throws Exception {
        // Initialize the database
        bankMasterRepository.saveAndFlush(bankMaster);

        // Get the bankMaster
        restBankMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, bankMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankMaster.getId().intValue()))
            .andExpect(jsonPath("$.bankCode").value(DEFAULT_BANK_CODE))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME));
    }

    @Test
    @Transactional
    void getNonExistingBankMaster() throws Exception {
        // Get the bankMaster
        restBankMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankMaster() throws Exception {
        // Initialize the database
        bankMasterRepository.saveAndFlush(bankMaster);

        int databaseSizeBeforeUpdate = bankMasterRepository.findAll().size();

        // Update the bankMaster
        BankMaster updatedBankMaster = bankMasterRepository.findById(bankMaster.getId()).get();
        // Disconnect from session so that the updates on updatedBankMaster are not directly saved in db
        em.detach(updatedBankMaster);
        updatedBankMaster.bankCode(UPDATED_BANK_CODE).bankName(UPDATED_BANK_NAME);

        restBankMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankMaster))
            )
            .andExpect(status().isOk());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeUpdate);
        BankMaster testBankMaster = bankMasterList.get(bankMasterList.size() - 1);
        assertThat(testBankMaster.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
        assertThat(testBankMaster.getBankName()).isEqualTo(UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBankMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankMasterRepository.findAll().size();
        bankMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankMasterRepository.findAll().size();
        bankMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankMasterRepository.findAll().size();
        bankMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankMasterWithPatch() throws Exception {
        // Initialize the database
        bankMasterRepository.saveAndFlush(bankMaster);

        int databaseSizeBeforeUpdate = bankMasterRepository.findAll().size();

        // Update the bankMaster using partial update
        BankMaster partialUpdatedBankMaster = new BankMaster();
        partialUpdatedBankMaster.setId(bankMaster.getId());

        restBankMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankMaster))
            )
            .andExpect(status().isOk());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeUpdate);
        BankMaster testBankMaster = bankMasterList.get(bankMasterList.size() - 1);
        assertThat(testBankMaster.getBankCode()).isEqualTo(DEFAULT_BANK_CODE);
        assertThat(testBankMaster.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBankMasterWithPatch() throws Exception {
        // Initialize the database
        bankMasterRepository.saveAndFlush(bankMaster);

        int databaseSizeBeforeUpdate = bankMasterRepository.findAll().size();

        // Update the bankMaster using partial update
        BankMaster partialUpdatedBankMaster = new BankMaster();
        partialUpdatedBankMaster.setId(bankMaster.getId());

        partialUpdatedBankMaster.bankCode(UPDATED_BANK_CODE).bankName(UPDATED_BANK_NAME);

        restBankMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankMaster))
            )
            .andExpect(status().isOk());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeUpdate);
        BankMaster testBankMaster = bankMasterList.get(bankMasterList.size() - 1);
        assertThat(testBankMaster.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
        assertThat(testBankMaster.getBankName()).isEqualTo(UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBankMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankMasterRepository.findAll().size();
        bankMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankMasterRepository.findAll().size();
        bankMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankMaster() throws Exception {
        int databaseSizeBeforeUpdate = bankMasterRepository.findAll().size();
        bankMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankMaster in the database
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankMaster() throws Exception {
        // Initialize the database
        bankMasterRepository.saveAndFlush(bankMaster);

        int databaseSizeBeforeDelete = bankMasterRepository.findAll().size();

        // Delete the bankMaster
        restBankMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankMaster> bankMasterList = bankMasterRepository.findAll();
        assertThat(bankMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
