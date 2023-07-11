package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.TalukaMaster;
import com.cbs.middleware.repository.TalukaMasterRepository;
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
 * Integration tests for the {@link TalukaMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TalukaMasterResourceIT {

    private static final String DEFAULT_TALUKA_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TALUKA_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TALUKA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TALUKA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/taluka-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TalukaMasterRepository talukaMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTalukaMasterMockMvc;

    private TalukaMaster talukaMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TalukaMaster createEntity(EntityManager em) {
        TalukaMaster talukaMaster = new TalukaMaster()
            .talukaCode(DEFAULT_TALUKA_CODE)
            .talukaName(DEFAULT_TALUKA_NAME)
            .districtCode(DEFAULT_DISTRICT_CODE);
        return talukaMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TalukaMaster createUpdatedEntity(EntityManager em) {
        TalukaMaster talukaMaster = new TalukaMaster()
            .talukaCode(UPDATED_TALUKA_CODE)
            .talukaName(UPDATED_TALUKA_NAME)
            .districtCode(UPDATED_DISTRICT_CODE);
        return talukaMaster;
    }

    @BeforeEach
    public void initTest() {
        talukaMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createTalukaMaster() throws Exception {
        int databaseSizeBeforeCreate = talukaMasterRepository.findAll().size();
        // Create the TalukaMaster
        restTalukaMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(talukaMaster)))
            .andExpect(status().isCreated());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeCreate + 1);
        TalukaMaster testTalukaMaster = talukaMasterList.get(talukaMasterList.size() - 1);
        assertThat(testTalukaMaster.getTalukaCode()).isEqualTo(DEFAULT_TALUKA_CODE);
        assertThat(testTalukaMaster.getTalukaName()).isEqualTo(DEFAULT_TALUKA_NAME);
        assertThat(testTalukaMaster.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
    }

    @Test
    @Transactional
    void createTalukaMasterWithExistingId() throws Exception {
        // Create the TalukaMaster with an existing ID
        talukaMaster.setId(1L);

        int databaseSizeBeforeCreate = talukaMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalukaMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(talukaMaster)))
            .andExpect(status().isBadRequest());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTalukaMasters() throws Exception {
        // Initialize the database
        talukaMasterRepository.saveAndFlush(talukaMaster);

        // Get all the talukaMasterList
        restTalukaMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talukaMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].talukaCode").value(hasItem(DEFAULT_TALUKA_CODE)))
            .andExpect(jsonPath("$.[*].talukaName").value(hasItem(DEFAULT_TALUKA_NAME)))
            .andExpect(jsonPath("$.[*].districtCode").value(hasItem(DEFAULT_DISTRICT_CODE)));
    }

    @Test
    @Transactional
    void getTalukaMaster() throws Exception {
        // Initialize the database
        talukaMasterRepository.saveAndFlush(talukaMaster);

        // Get the talukaMaster
        restTalukaMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, talukaMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(talukaMaster.getId().intValue()))
            .andExpect(jsonPath("$.talukaCode").value(DEFAULT_TALUKA_CODE))
            .andExpect(jsonPath("$.talukaName").value(DEFAULT_TALUKA_NAME))
            .andExpect(jsonPath("$.districtCode").value(DEFAULT_DISTRICT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingTalukaMaster() throws Exception {
        // Get the talukaMaster
        restTalukaMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTalukaMaster() throws Exception {
        // Initialize the database
        talukaMasterRepository.saveAndFlush(talukaMaster);

        int databaseSizeBeforeUpdate = talukaMasterRepository.findAll().size();

        // Update the talukaMaster
        TalukaMaster updatedTalukaMaster = talukaMasterRepository.findById(talukaMaster.getId()).get();
        // Disconnect from session so that the updates on updatedTalukaMaster are not directly saved in db
        em.detach(updatedTalukaMaster);
        updatedTalukaMaster.talukaCode(UPDATED_TALUKA_CODE).talukaName(UPDATED_TALUKA_NAME).districtCode(UPDATED_DISTRICT_CODE);

        restTalukaMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTalukaMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTalukaMaster))
            )
            .andExpect(status().isOk());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeUpdate);
        TalukaMaster testTalukaMaster = talukaMasterList.get(talukaMasterList.size() - 1);
        assertThat(testTalukaMaster.getTalukaCode()).isEqualTo(UPDATED_TALUKA_CODE);
        assertThat(testTalukaMaster.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testTalukaMaster.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
    }

    @Test
    @Transactional
    void putNonExistingTalukaMaster() throws Exception {
        int databaseSizeBeforeUpdate = talukaMasterRepository.findAll().size();
        talukaMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTalukaMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, talukaMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(talukaMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTalukaMaster() throws Exception {
        int databaseSizeBeforeUpdate = talukaMasterRepository.findAll().size();
        talukaMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTalukaMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(talukaMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTalukaMaster() throws Exception {
        int databaseSizeBeforeUpdate = talukaMasterRepository.findAll().size();
        talukaMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTalukaMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(talukaMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTalukaMasterWithPatch() throws Exception {
        // Initialize the database
        talukaMasterRepository.saveAndFlush(talukaMaster);

        int databaseSizeBeforeUpdate = talukaMasterRepository.findAll().size();

        // Update the talukaMaster using partial update
        TalukaMaster partialUpdatedTalukaMaster = new TalukaMaster();
        partialUpdatedTalukaMaster.setId(talukaMaster.getId());

        partialUpdatedTalukaMaster.talukaName(UPDATED_TALUKA_NAME);

        restTalukaMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTalukaMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTalukaMaster))
            )
            .andExpect(status().isOk());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeUpdate);
        TalukaMaster testTalukaMaster = talukaMasterList.get(talukaMasterList.size() - 1);
        assertThat(testTalukaMaster.getTalukaCode()).isEqualTo(DEFAULT_TALUKA_CODE);
        assertThat(testTalukaMaster.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testTalukaMaster.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateTalukaMasterWithPatch() throws Exception {
        // Initialize the database
        talukaMasterRepository.saveAndFlush(talukaMaster);

        int databaseSizeBeforeUpdate = talukaMasterRepository.findAll().size();

        // Update the talukaMaster using partial update
        TalukaMaster partialUpdatedTalukaMaster = new TalukaMaster();
        partialUpdatedTalukaMaster.setId(talukaMaster.getId());

        partialUpdatedTalukaMaster.talukaCode(UPDATED_TALUKA_CODE).talukaName(UPDATED_TALUKA_NAME).districtCode(UPDATED_DISTRICT_CODE);

        restTalukaMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTalukaMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTalukaMaster))
            )
            .andExpect(status().isOk());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeUpdate);
        TalukaMaster testTalukaMaster = talukaMasterList.get(talukaMasterList.size() - 1);
        assertThat(testTalukaMaster.getTalukaCode()).isEqualTo(UPDATED_TALUKA_CODE);
        assertThat(testTalukaMaster.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testTalukaMaster.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingTalukaMaster() throws Exception {
        int databaseSizeBeforeUpdate = talukaMasterRepository.findAll().size();
        talukaMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTalukaMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, talukaMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(talukaMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTalukaMaster() throws Exception {
        int databaseSizeBeforeUpdate = talukaMasterRepository.findAll().size();
        talukaMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTalukaMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(talukaMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTalukaMaster() throws Exception {
        int databaseSizeBeforeUpdate = talukaMasterRepository.findAll().size();
        talukaMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTalukaMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(talukaMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TalukaMaster in the database
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTalukaMaster() throws Exception {
        // Initialize the database
        talukaMasterRepository.saveAndFlush(talukaMaster);

        int databaseSizeBeforeDelete = talukaMasterRepository.findAll().size();

        // Delete the talukaMaster
        restTalukaMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, talukaMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();
        assertThat(talukaMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
