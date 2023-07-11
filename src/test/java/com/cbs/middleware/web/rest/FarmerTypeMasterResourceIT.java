package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.repository.FarmerTypeMasterRepository;
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
 * Integration tests for the {@link FarmerTypeMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FarmerTypeMasterResourceIT {

    private static final Integer DEFAULT_FARMER_TYPE_CODE = 1;
    private static final Integer UPDATED_FARMER_TYPE_CODE = 2;

    private static final String DEFAULT_FARMER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/farmer-type-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FarmerTypeMasterRepository farmerTypeMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFarmerTypeMasterMockMvc;

    private FarmerTypeMaster farmerTypeMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FarmerTypeMaster createEntity(EntityManager em) {
        FarmerTypeMaster farmerTypeMaster = new FarmerTypeMaster().farmerTypeCode(DEFAULT_FARMER_TYPE_CODE).farmerType(DEFAULT_FARMER_TYPE);
        return farmerTypeMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FarmerTypeMaster createUpdatedEntity(EntityManager em) {
        FarmerTypeMaster farmerTypeMaster = new FarmerTypeMaster().farmerTypeCode(UPDATED_FARMER_TYPE_CODE).farmerType(UPDATED_FARMER_TYPE);
        return farmerTypeMaster;
    }

    @BeforeEach
    public void initTest() {
        farmerTypeMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createFarmerTypeMaster() throws Exception {
        int databaseSizeBeforeCreate = farmerTypeMasterRepository.findAll().size();
        // Create the FarmerTypeMaster
        restFarmerTypeMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmerTypeMaster))
            )
            .andExpect(status().isCreated());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeCreate + 1);
        FarmerTypeMaster testFarmerTypeMaster = farmerTypeMasterList.get(farmerTypeMasterList.size() - 1);
        assertThat(testFarmerTypeMaster.getFarmerTypeCode()).isEqualTo(DEFAULT_FARMER_TYPE_CODE);
        assertThat(testFarmerTypeMaster.getFarmerType()).isEqualTo(DEFAULT_FARMER_TYPE);
    }

    @Test
    @Transactional
    void createFarmerTypeMasterWithExistingId() throws Exception {
        // Create the FarmerTypeMaster with an existing ID
        farmerTypeMaster.setId(1L);

        int databaseSizeBeforeCreate = farmerTypeMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmerTypeMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmerTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFarmerTypeMasters() throws Exception {
        // Initialize the database
        farmerTypeMasterRepository.saveAndFlush(farmerTypeMaster);

        // Get all the farmerTypeMasterList
        restFarmerTypeMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farmerTypeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].farmerTypeCode").value(hasItem(DEFAULT_FARMER_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].farmerType").value(hasItem(DEFAULT_FARMER_TYPE)));
    }

    @Test
    @Transactional
    void getFarmerTypeMaster() throws Exception {
        // Initialize the database
        farmerTypeMasterRepository.saveAndFlush(farmerTypeMaster);

        // Get the farmerTypeMaster
        restFarmerTypeMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, farmerTypeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(farmerTypeMaster.getId().intValue()))
            .andExpect(jsonPath("$.farmerTypeCode").value(DEFAULT_FARMER_TYPE_CODE))
            .andExpect(jsonPath("$.farmerType").value(DEFAULT_FARMER_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingFarmerTypeMaster() throws Exception {
        // Get the farmerTypeMaster
        restFarmerTypeMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFarmerTypeMaster() throws Exception {
        // Initialize the database
        farmerTypeMasterRepository.saveAndFlush(farmerTypeMaster);

        int databaseSizeBeforeUpdate = farmerTypeMasterRepository.findAll().size();

        // Update the farmerTypeMaster
        FarmerTypeMaster updatedFarmerTypeMaster = farmerTypeMasterRepository.findById(farmerTypeMaster.getId()).get();
        // Disconnect from session so that the updates on updatedFarmerTypeMaster are not directly saved in db
        em.detach(updatedFarmerTypeMaster);
        updatedFarmerTypeMaster.farmerTypeCode(UPDATED_FARMER_TYPE_CODE).farmerType(UPDATED_FARMER_TYPE);

        restFarmerTypeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFarmerTypeMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFarmerTypeMaster))
            )
            .andExpect(status().isOk());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeUpdate);
        FarmerTypeMaster testFarmerTypeMaster = farmerTypeMasterList.get(farmerTypeMasterList.size() - 1);
        assertThat(testFarmerTypeMaster.getFarmerTypeCode()).isEqualTo(UPDATED_FARMER_TYPE_CODE);
        assertThat(testFarmerTypeMaster.getFarmerType()).isEqualTo(UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFarmerTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerTypeMasterRepository.findAll().size();
        farmerTypeMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmerTypeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, farmerTypeMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farmerTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFarmerTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerTypeMasterRepository.findAll().size();
        farmerTypeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerTypeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farmerTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFarmerTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerTypeMasterRepository.findAll().size();
        farmerTypeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerTypeMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmerTypeMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFarmerTypeMasterWithPatch() throws Exception {
        // Initialize the database
        farmerTypeMasterRepository.saveAndFlush(farmerTypeMaster);

        int databaseSizeBeforeUpdate = farmerTypeMasterRepository.findAll().size();

        // Update the farmerTypeMaster using partial update
        FarmerTypeMaster partialUpdatedFarmerTypeMaster = new FarmerTypeMaster();
        partialUpdatedFarmerTypeMaster.setId(farmerTypeMaster.getId());

        restFarmerTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarmerTypeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFarmerTypeMaster))
            )
            .andExpect(status().isOk());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeUpdate);
        FarmerTypeMaster testFarmerTypeMaster = farmerTypeMasterList.get(farmerTypeMasterList.size() - 1);
        assertThat(testFarmerTypeMaster.getFarmerTypeCode()).isEqualTo(DEFAULT_FARMER_TYPE_CODE);
        assertThat(testFarmerTypeMaster.getFarmerType()).isEqualTo(DEFAULT_FARMER_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFarmerTypeMasterWithPatch() throws Exception {
        // Initialize the database
        farmerTypeMasterRepository.saveAndFlush(farmerTypeMaster);

        int databaseSizeBeforeUpdate = farmerTypeMasterRepository.findAll().size();

        // Update the farmerTypeMaster using partial update
        FarmerTypeMaster partialUpdatedFarmerTypeMaster = new FarmerTypeMaster();
        partialUpdatedFarmerTypeMaster.setId(farmerTypeMaster.getId());

        partialUpdatedFarmerTypeMaster.farmerTypeCode(UPDATED_FARMER_TYPE_CODE).farmerType(UPDATED_FARMER_TYPE);

        restFarmerTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarmerTypeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFarmerTypeMaster))
            )
            .andExpect(status().isOk());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeUpdate);
        FarmerTypeMaster testFarmerTypeMaster = farmerTypeMasterList.get(farmerTypeMasterList.size() - 1);
        assertThat(testFarmerTypeMaster.getFarmerTypeCode()).isEqualTo(UPDATED_FARMER_TYPE_CODE);
        assertThat(testFarmerTypeMaster.getFarmerType()).isEqualTo(UPDATED_FARMER_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFarmerTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerTypeMasterRepository.findAll().size();
        farmerTypeMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmerTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, farmerTypeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farmerTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFarmerTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerTypeMasterRepository.findAll().size();
        farmerTypeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farmerTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFarmerTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerTypeMasterRepository.findAll().size();
        farmerTypeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farmerTypeMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FarmerTypeMaster in the database
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFarmerTypeMaster() throws Exception {
        // Initialize the database
        farmerTypeMasterRepository.saveAndFlush(farmerTypeMaster);

        int databaseSizeBeforeDelete = farmerTypeMasterRepository.findAll().size();

        // Delete the farmerTypeMaster
        restFarmerTypeMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, farmerTypeMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FarmerTypeMaster> farmerTypeMasterList = farmerTypeMasterRepository.findAll();
        assertThat(farmerTypeMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
