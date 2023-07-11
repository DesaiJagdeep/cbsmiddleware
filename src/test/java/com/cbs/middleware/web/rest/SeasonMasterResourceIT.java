package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.repository.SeasonMasterRepository;
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
 * Integration tests for the {@link SeasonMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeasonMasterResourceIT {

    private static final Integer DEFAULT_SEASON_CODE = 1;
    private static final Integer UPDATED_SEASON_CODE = 2;

    private static final String DEFAULT_SEASON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEASON_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/season-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeasonMasterRepository seasonMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeasonMasterMockMvc;

    private SeasonMaster seasonMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeasonMaster createEntity(EntityManager em) {
        SeasonMaster seasonMaster = new SeasonMaster().seasonCode(DEFAULT_SEASON_CODE).seasonName(DEFAULT_SEASON_NAME);
        return seasonMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeasonMaster createUpdatedEntity(EntityManager em) {
        SeasonMaster seasonMaster = new SeasonMaster().seasonCode(UPDATED_SEASON_CODE).seasonName(UPDATED_SEASON_NAME);
        return seasonMaster;
    }

    @BeforeEach
    public void initTest() {
        seasonMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createSeasonMaster() throws Exception {
        int databaseSizeBeforeCreate = seasonMasterRepository.findAll().size();
        // Create the SeasonMaster
        restSeasonMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seasonMaster)))
            .andExpect(status().isCreated());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeCreate + 1);
        SeasonMaster testSeasonMaster = seasonMasterList.get(seasonMasterList.size() - 1);
        assertThat(testSeasonMaster.getSeasonCode()).isEqualTo(DEFAULT_SEASON_CODE);
        assertThat(testSeasonMaster.getSeasonName()).isEqualTo(DEFAULT_SEASON_NAME);
    }

    @Test
    @Transactional
    void createSeasonMasterWithExistingId() throws Exception {
        // Create the SeasonMaster with an existing ID
        seasonMaster.setId(1L);

        int databaseSizeBeforeCreate = seasonMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeasonMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seasonMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSeasonMasters() throws Exception {
        // Initialize the database
        seasonMasterRepository.saveAndFlush(seasonMaster);

        // Get all the seasonMasterList
        restSeasonMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seasonMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].seasonCode").value(hasItem(DEFAULT_SEASON_CODE)))
            .andExpect(jsonPath("$.[*].seasonName").value(hasItem(DEFAULT_SEASON_NAME)));
    }

    @Test
    @Transactional
    void getSeasonMaster() throws Exception {
        // Initialize the database
        seasonMasterRepository.saveAndFlush(seasonMaster);

        // Get the seasonMaster
        restSeasonMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, seasonMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seasonMaster.getId().intValue()))
            .andExpect(jsonPath("$.seasonCode").value(DEFAULT_SEASON_CODE))
            .andExpect(jsonPath("$.seasonName").value(DEFAULT_SEASON_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSeasonMaster() throws Exception {
        // Get the seasonMaster
        restSeasonMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSeasonMaster() throws Exception {
        // Initialize the database
        seasonMasterRepository.saveAndFlush(seasonMaster);

        int databaseSizeBeforeUpdate = seasonMasterRepository.findAll().size();

        // Update the seasonMaster
        SeasonMaster updatedSeasonMaster = seasonMasterRepository.findById(seasonMaster.getId()).get();
        // Disconnect from session so that the updates on updatedSeasonMaster are not directly saved in db
        em.detach(updatedSeasonMaster);
        updatedSeasonMaster.seasonCode(UPDATED_SEASON_CODE).seasonName(UPDATED_SEASON_NAME);

        restSeasonMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSeasonMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSeasonMaster))
            )
            .andExpect(status().isOk());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeUpdate);
        SeasonMaster testSeasonMaster = seasonMasterList.get(seasonMasterList.size() - 1);
        assertThat(testSeasonMaster.getSeasonCode()).isEqualTo(UPDATED_SEASON_CODE);
        assertThat(testSeasonMaster.getSeasonName()).isEqualTo(UPDATED_SEASON_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSeasonMaster() throws Exception {
        int databaseSizeBeforeUpdate = seasonMasterRepository.findAll().size();
        seasonMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeasonMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seasonMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seasonMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeasonMaster() throws Exception {
        int databaseSizeBeforeUpdate = seasonMasterRepository.findAll().size();
        seasonMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeasonMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seasonMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeasonMaster() throws Exception {
        int databaseSizeBeforeUpdate = seasonMasterRepository.findAll().size();
        seasonMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeasonMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seasonMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeasonMasterWithPatch() throws Exception {
        // Initialize the database
        seasonMasterRepository.saveAndFlush(seasonMaster);

        int databaseSizeBeforeUpdate = seasonMasterRepository.findAll().size();

        // Update the seasonMaster using partial update
        SeasonMaster partialUpdatedSeasonMaster = new SeasonMaster();
        partialUpdatedSeasonMaster.setId(seasonMaster.getId());

        restSeasonMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeasonMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeasonMaster))
            )
            .andExpect(status().isOk());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeUpdate);
        SeasonMaster testSeasonMaster = seasonMasterList.get(seasonMasterList.size() - 1);
        assertThat(testSeasonMaster.getSeasonCode()).isEqualTo(DEFAULT_SEASON_CODE);
        assertThat(testSeasonMaster.getSeasonName()).isEqualTo(DEFAULT_SEASON_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSeasonMasterWithPatch() throws Exception {
        // Initialize the database
        seasonMasterRepository.saveAndFlush(seasonMaster);

        int databaseSizeBeforeUpdate = seasonMasterRepository.findAll().size();

        // Update the seasonMaster using partial update
        SeasonMaster partialUpdatedSeasonMaster = new SeasonMaster();
        partialUpdatedSeasonMaster.setId(seasonMaster.getId());

        partialUpdatedSeasonMaster.seasonCode(UPDATED_SEASON_CODE).seasonName(UPDATED_SEASON_NAME);

        restSeasonMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeasonMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeasonMaster))
            )
            .andExpect(status().isOk());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeUpdate);
        SeasonMaster testSeasonMaster = seasonMasterList.get(seasonMasterList.size() - 1);
        assertThat(testSeasonMaster.getSeasonCode()).isEqualTo(UPDATED_SEASON_CODE);
        assertThat(testSeasonMaster.getSeasonName()).isEqualTo(UPDATED_SEASON_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSeasonMaster() throws Exception {
        int databaseSizeBeforeUpdate = seasonMasterRepository.findAll().size();
        seasonMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeasonMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seasonMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seasonMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeasonMaster() throws Exception {
        int databaseSizeBeforeUpdate = seasonMasterRepository.findAll().size();
        seasonMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeasonMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seasonMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeasonMaster() throws Exception {
        int databaseSizeBeforeUpdate = seasonMasterRepository.findAll().size();
        seasonMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeasonMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seasonMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeasonMaster in the database
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeasonMaster() throws Exception {
        // Initialize the database
        seasonMasterRepository.saveAndFlush(seasonMaster);

        int databaseSizeBeforeDelete = seasonMasterRepository.findAll().size();

        // Delete the seasonMaster
        restSeasonMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, seasonMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeasonMaster> seasonMasterList = seasonMasterRepository.findAll();
        assertThat(seasonMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
