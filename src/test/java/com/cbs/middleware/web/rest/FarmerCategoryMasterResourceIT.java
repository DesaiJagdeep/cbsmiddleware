package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.FarmerCategoryMaster;
import com.cbs.middleware.repository.FarmerCategoryMasterRepository;
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
 * Integration tests for the {@link FarmerCategoryMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FarmerCategoryMasterResourceIT {

    private static final Integer DEFAULT_FARMER_CATEGORY_CODE = 1;
    private static final Integer UPDATED_FARMER_CATEGORY_CODE = 2;

    private static final String DEFAULT_FARMER_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_CATEGORY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/farmer-category-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FarmerCategoryMasterRepository farmerCategoryMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFarmerCategoryMasterMockMvc;

    private FarmerCategoryMaster farmerCategoryMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FarmerCategoryMaster createEntity(EntityManager em) {
        FarmerCategoryMaster farmerCategoryMaster = new FarmerCategoryMaster()
            .farmerCategoryCode(DEFAULT_FARMER_CATEGORY_CODE)
            .farmerCategory(DEFAULT_FARMER_CATEGORY);
        return farmerCategoryMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FarmerCategoryMaster createUpdatedEntity(EntityManager em) {
        FarmerCategoryMaster farmerCategoryMaster = new FarmerCategoryMaster()
            .farmerCategoryCode(UPDATED_FARMER_CATEGORY_CODE)
            .farmerCategory(UPDATED_FARMER_CATEGORY);
        return farmerCategoryMaster;
    }

    @BeforeEach
    public void initTest() {
        farmerCategoryMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createFarmerCategoryMaster() throws Exception {
        int databaseSizeBeforeCreate = farmerCategoryMasterRepository.findAll().size();
        // Create the FarmerCategoryMaster
        restFarmerCategoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farmerCategoryMaster))
            )
            .andExpect(status().isCreated());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeCreate + 1);
        FarmerCategoryMaster testFarmerCategoryMaster = farmerCategoryMasterList.get(farmerCategoryMasterList.size() - 1);
        assertThat(testFarmerCategoryMaster.getFarmerCategoryCode()).isEqualTo(DEFAULT_FARMER_CATEGORY_CODE);
        assertThat(testFarmerCategoryMaster.getFarmerCategory()).isEqualTo(DEFAULT_FARMER_CATEGORY);
    }

    @Test
    @Transactional
    void createFarmerCategoryMasterWithExistingId() throws Exception {
        // Create the FarmerCategoryMaster with an existing ID
        farmerCategoryMaster.setId(1L);

        int databaseSizeBeforeCreate = farmerCategoryMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmerCategoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farmerCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFarmerCategoryMasters() throws Exception {
        // Initialize the database
        farmerCategoryMasterRepository.saveAndFlush(farmerCategoryMaster);

        // Get all the farmerCategoryMasterList
        restFarmerCategoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farmerCategoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].farmerCategoryCode").value(hasItem(DEFAULT_FARMER_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].farmerCategory").value(hasItem(DEFAULT_FARMER_CATEGORY)));
    }

    @Test
    @Transactional
    void getFarmerCategoryMaster() throws Exception {
        // Initialize the database
        farmerCategoryMasterRepository.saveAndFlush(farmerCategoryMaster);

        // Get the farmerCategoryMaster
        restFarmerCategoryMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, farmerCategoryMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(farmerCategoryMaster.getId().intValue()))
            .andExpect(jsonPath("$.farmerCategoryCode").value(DEFAULT_FARMER_CATEGORY_CODE))
            .andExpect(jsonPath("$.farmerCategory").value(DEFAULT_FARMER_CATEGORY));
    }

    @Test
    @Transactional
    void getNonExistingFarmerCategoryMaster() throws Exception {
        // Get the farmerCategoryMaster
        restFarmerCategoryMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFarmerCategoryMaster() throws Exception {
        // Initialize the database
        farmerCategoryMasterRepository.saveAndFlush(farmerCategoryMaster);

        int databaseSizeBeforeUpdate = farmerCategoryMasterRepository.findAll().size();

        // Update the farmerCategoryMaster
        FarmerCategoryMaster updatedFarmerCategoryMaster = farmerCategoryMasterRepository.findById(farmerCategoryMaster.getId()).get();
        // Disconnect from session so that the updates on updatedFarmerCategoryMaster are not directly saved in db
        em.detach(updatedFarmerCategoryMaster);
        updatedFarmerCategoryMaster.farmerCategoryCode(UPDATED_FARMER_CATEGORY_CODE).farmerCategory(UPDATED_FARMER_CATEGORY);

        restFarmerCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFarmerCategoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFarmerCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        FarmerCategoryMaster testFarmerCategoryMaster = farmerCategoryMasterList.get(farmerCategoryMasterList.size() - 1);
        assertThat(testFarmerCategoryMaster.getFarmerCategoryCode()).isEqualTo(UPDATED_FARMER_CATEGORY_CODE);
        assertThat(testFarmerCategoryMaster.getFarmerCategory()).isEqualTo(UPDATED_FARMER_CATEGORY);
    }

    @Test
    @Transactional
    void putNonExistingFarmerCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerCategoryMasterRepository.findAll().size();
        farmerCategoryMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmerCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, farmerCategoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farmerCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFarmerCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerCategoryMasterRepository.findAll().size();
        farmerCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farmerCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFarmerCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerCategoryMasterRepository.findAll().size();
        farmerCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmerCategoryMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFarmerCategoryMasterWithPatch() throws Exception {
        // Initialize the database
        farmerCategoryMasterRepository.saveAndFlush(farmerCategoryMaster);

        int databaseSizeBeforeUpdate = farmerCategoryMasterRepository.findAll().size();

        // Update the farmerCategoryMaster using partial update
        FarmerCategoryMaster partialUpdatedFarmerCategoryMaster = new FarmerCategoryMaster();
        partialUpdatedFarmerCategoryMaster.setId(farmerCategoryMaster.getId());

        restFarmerCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarmerCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFarmerCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        FarmerCategoryMaster testFarmerCategoryMaster = farmerCategoryMasterList.get(farmerCategoryMasterList.size() - 1);
        assertThat(testFarmerCategoryMaster.getFarmerCategoryCode()).isEqualTo(DEFAULT_FARMER_CATEGORY_CODE);
        assertThat(testFarmerCategoryMaster.getFarmerCategory()).isEqualTo(DEFAULT_FARMER_CATEGORY);
    }

    @Test
    @Transactional
    void fullUpdateFarmerCategoryMasterWithPatch() throws Exception {
        // Initialize the database
        farmerCategoryMasterRepository.saveAndFlush(farmerCategoryMaster);

        int databaseSizeBeforeUpdate = farmerCategoryMasterRepository.findAll().size();

        // Update the farmerCategoryMaster using partial update
        FarmerCategoryMaster partialUpdatedFarmerCategoryMaster = new FarmerCategoryMaster();
        partialUpdatedFarmerCategoryMaster.setId(farmerCategoryMaster.getId());

        partialUpdatedFarmerCategoryMaster.farmerCategoryCode(UPDATED_FARMER_CATEGORY_CODE).farmerCategory(UPDATED_FARMER_CATEGORY);

        restFarmerCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarmerCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFarmerCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        FarmerCategoryMaster testFarmerCategoryMaster = farmerCategoryMasterList.get(farmerCategoryMasterList.size() - 1);
        assertThat(testFarmerCategoryMaster.getFarmerCategoryCode()).isEqualTo(UPDATED_FARMER_CATEGORY_CODE);
        assertThat(testFarmerCategoryMaster.getFarmerCategory()).isEqualTo(UPDATED_FARMER_CATEGORY);
    }

    @Test
    @Transactional
    void patchNonExistingFarmerCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerCategoryMasterRepository.findAll().size();
        farmerCategoryMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmerCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, farmerCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farmerCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFarmerCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerCategoryMasterRepository.findAll().size();
        farmerCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farmerCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFarmerCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerCategoryMasterRepository.findAll().size();
        farmerCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farmerCategoryMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FarmerCategoryMaster in the database
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFarmerCategoryMaster() throws Exception {
        // Initialize the database
        farmerCategoryMasterRepository.saveAndFlush(farmerCategoryMaster);

        int databaseSizeBeforeDelete = farmerCategoryMasterRepository.findAll().size();

        // Delete the farmerCategoryMaster
        restFarmerCategoryMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, farmerCategoryMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FarmerCategoryMaster> farmerCategoryMasterList = farmerCategoryMasterRepository.findAll();
        assertThat(farmerCategoryMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
