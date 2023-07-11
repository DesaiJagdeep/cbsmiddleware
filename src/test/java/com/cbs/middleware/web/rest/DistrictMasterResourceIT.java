package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.DistrictMaster;
import com.cbs.middleware.repository.DistrictMasterRepository;
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
 * Integration tests for the {@link DistrictMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DistrictMasterResourceIT {

    private static final String DEFAULT_DISTRICT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/district-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DistrictMasterRepository districtMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictMasterMockMvc;

    private DistrictMaster districtMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictMaster createEntity(EntityManager em) {
        DistrictMaster districtMaster = new DistrictMaster()
            .districtCode(DEFAULT_DISTRICT_CODE)
            .districtName(DEFAULT_DISTRICT_NAME)
            .stateCode(DEFAULT_STATE_CODE);
        return districtMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictMaster createUpdatedEntity(EntityManager em) {
        DistrictMaster districtMaster = new DistrictMaster()
            .districtCode(UPDATED_DISTRICT_CODE)
            .districtName(UPDATED_DISTRICT_NAME)
            .stateCode(UPDATED_STATE_CODE);
        return districtMaster;
    }

    @BeforeEach
    public void initTest() {
        districtMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createDistrictMaster() throws Exception {
        int databaseSizeBeforeCreate = districtMasterRepository.findAll().size();
        // Create the DistrictMaster
        restDistrictMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtMaster))
            )
            .andExpect(status().isCreated());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeCreate + 1);
        DistrictMaster testDistrictMaster = districtMasterList.get(districtMasterList.size() - 1);
        assertThat(testDistrictMaster.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
        assertThat(testDistrictMaster.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);
        assertThat(testDistrictMaster.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
    }

    @Test
    @Transactional
    void createDistrictMasterWithExistingId() throws Exception {
        // Create the DistrictMaster with an existing ID
        districtMaster.setId(1L);

        int databaseSizeBeforeCreate = districtMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDistrictMasters() throws Exception {
        // Initialize the database
        districtMasterRepository.saveAndFlush(districtMaster);

        // Get all the districtMasterList
        restDistrictMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districtMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtCode").value(hasItem(DEFAULT_DISTRICT_CODE)))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)));
    }

    @Test
    @Transactional
    void getDistrictMaster() throws Exception {
        // Initialize the database
        districtMasterRepository.saveAndFlush(districtMaster);

        // Get the districtMaster
        restDistrictMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, districtMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(districtMaster.getId().intValue()))
            .andExpect(jsonPath("$.districtCode").value(DEFAULT_DISTRICT_CODE))
            .andExpect(jsonPath("$.districtName").value(DEFAULT_DISTRICT_NAME))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE));
    }

    @Test
    @Transactional
    void getNonExistingDistrictMaster() throws Exception {
        // Get the districtMaster
        restDistrictMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDistrictMaster() throws Exception {
        // Initialize the database
        districtMasterRepository.saveAndFlush(districtMaster);

        int databaseSizeBeforeUpdate = districtMasterRepository.findAll().size();

        // Update the districtMaster
        DistrictMaster updatedDistrictMaster = districtMasterRepository.findById(districtMaster.getId()).get();
        // Disconnect from session so that the updates on updatedDistrictMaster are not directly saved in db
        em.detach(updatedDistrictMaster);
        updatedDistrictMaster.districtCode(UPDATED_DISTRICT_CODE).districtName(UPDATED_DISTRICT_NAME).stateCode(UPDATED_STATE_CODE);

        restDistrictMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDistrictMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDistrictMaster))
            )
            .andExpect(status().isOk());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeUpdate);
        DistrictMaster testDistrictMaster = districtMasterList.get(districtMasterList.size() - 1);
        assertThat(testDistrictMaster.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testDistrictMaster.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testDistrictMaster.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    void putNonExistingDistrictMaster() throws Exception {
        int databaseSizeBeforeUpdate = districtMasterRepository.findAll().size();
        districtMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDistrictMaster() throws Exception {
        int databaseSizeBeforeUpdate = districtMasterRepository.findAll().size();
        districtMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDistrictMaster() throws Exception {
        int databaseSizeBeforeUpdate = districtMasterRepository.findAll().size();
        districtMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDistrictMasterWithPatch() throws Exception {
        // Initialize the database
        districtMasterRepository.saveAndFlush(districtMaster);

        int databaseSizeBeforeUpdate = districtMasterRepository.findAll().size();

        // Update the districtMaster using partial update
        DistrictMaster partialUpdatedDistrictMaster = new DistrictMaster();
        partialUpdatedDistrictMaster.setId(districtMaster.getId());

        partialUpdatedDistrictMaster.districtCode(UPDATED_DISTRICT_CODE).districtName(UPDATED_DISTRICT_NAME).stateCode(UPDATED_STATE_CODE);

        restDistrictMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrictMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistrictMaster))
            )
            .andExpect(status().isOk());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeUpdate);
        DistrictMaster testDistrictMaster = districtMasterList.get(districtMasterList.size() - 1);
        assertThat(testDistrictMaster.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testDistrictMaster.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testDistrictMaster.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    void fullUpdateDistrictMasterWithPatch() throws Exception {
        // Initialize the database
        districtMasterRepository.saveAndFlush(districtMaster);

        int databaseSizeBeforeUpdate = districtMasterRepository.findAll().size();

        // Update the districtMaster using partial update
        DistrictMaster partialUpdatedDistrictMaster = new DistrictMaster();
        partialUpdatedDistrictMaster.setId(districtMaster.getId());

        partialUpdatedDistrictMaster.districtCode(UPDATED_DISTRICT_CODE).districtName(UPDATED_DISTRICT_NAME).stateCode(UPDATED_STATE_CODE);

        restDistrictMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrictMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistrictMaster))
            )
            .andExpect(status().isOk());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeUpdate);
        DistrictMaster testDistrictMaster = districtMasterList.get(districtMasterList.size() - 1);
        assertThat(testDistrictMaster.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testDistrictMaster.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testDistrictMaster.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingDistrictMaster() throws Exception {
        int databaseSizeBeforeUpdate = districtMasterRepository.findAll().size();
        districtMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, districtMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDistrictMaster() throws Exception {
        int databaseSizeBeforeUpdate = districtMasterRepository.findAll().size();
        districtMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDistrictMaster() throws Exception {
        int databaseSizeBeforeUpdate = districtMasterRepository.findAll().size();
        districtMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(districtMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DistrictMaster in the database
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDistrictMaster() throws Exception {
        // Initialize the database
        districtMasterRepository.saveAndFlush(districtMaster);

        int databaseSizeBeforeDelete = districtMasterRepository.findAll().size();

        // Delete the districtMaster
        restDistrictMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, districtMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DistrictMaster> districtMasterList = districtMasterRepository.findAll();
        assertThat(districtMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
