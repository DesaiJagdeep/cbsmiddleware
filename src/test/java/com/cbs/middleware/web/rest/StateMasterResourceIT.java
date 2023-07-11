package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.StateMaster;
import com.cbs.middleware.repository.StateMasterRepository;
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
 * Integration tests for the {@link StateMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StateMasterResourceIT {

    private static final String DEFAULT_STATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/state-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StateMasterRepository stateMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStateMasterMockMvc;

    private StateMaster stateMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateMaster createEntity(EntityManager em) {
        StateMaster stateMaster = new StateMaster().stateCode(DEFAULT_STATE_CODE).stateName(DEFAULT_STATE_NAME);
        return stateMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateMaster createUpdatedEntity(EntityManager em) {
        StateMaster stateMaster = new StateMaster().stateCode(UPDATED_STATE_CODE).stateName(UPDATED_STATE_NAME);
        return stateMaster;
    }

    @BeforeEach
    public void initTest() {
        stateMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createStateMaster() throws Exception {
        int databaseSizeBeforeCreate = stateMasterRepository.findAll().size();
        // Create the StateMaster
        restStateMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateMaster)))
            .andExpect(status().isCreated());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeCreate + 1);
        StateMaster testStateMaster = stateMasterList.get(stateMasterList.size() - 1);
        assertThat(testStateMaster.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testStateMaster.getStateName()).isEqualTo(DEFAULT_STATE_NAME);
    }

    @Test
    @Transactional
    void createStateMasterWithExistingId() throws Exception {
        // Create the StateMaster with an existing ID
        stateMaster.setId(1L);

        int databaseSizeBeforeCreate = stateMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateMaster)))
            .andExpect(status().isBadRequest());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStateMasters() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get all the stateMasterList
        restStateMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].stateName").value(hasItem(DEFAULT_STATE_NAME)));
    }

    @Test
    @Transactional
    void getStateMaster() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get the stateMaster
        restStateMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, stateMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stateMaster.getId().intValue()))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE))
            .andExpect(jsonPath("$.stateName").value(DEFAULT_STATE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingStateMaster() throws Exception {
        // Get the stateMaster
        restStateMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStateMaster() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();

        // Update the stateMaster
        StateMaster updatedStateMaster = stateMasterRepository.findById(stateMaster.getId()).get();
        // Disconnect from session so that the updates on updatedStateMaster are not directly saved in db
        em.detach(updatedStateMaster);
        updatedStateMaster.stateCode(UPDATED_STATE_CODE).stateName(UPDATED_STATE_NAME);

        restStateMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStateMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStateMaster))
            )
            .andExpect(status().isOk());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
        StateMaster testStateMaster = stateMasterList.get(stateMasterList.size() - 1);
        assertThat(testStateMaster.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testStateMaster.getStateName()).isEqualTo(UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingStateMaster() throws Exception {
        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();
        stateMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stateMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stateMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStateMaster() throws Exception {
        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();
        stateMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stateMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStateMaster() throws Exception {
        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();
        stateMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStateMasterWithPatch() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();

        // Update the stateMaster using partial update
        StateMaster partialUpdatedStateMaster = new StateMaster();
        partialUpdatedStateMaster.setId(stateMaster.getId());

        restStateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStateMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStateMaster))
            )
            .andExpect(status().isOk());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
        StateMaster testStateMaster = stateMasterList.get(stateMasterList.size() - 1);
        assertThat(testStateMaster.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testStateMaster.getStateName()).isEqualTo(DEFAULT_STATE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateStateMasterWithPatch() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();

        // Update the stateMaster using partial update
        StateMaster partialUpdatedStateMaster = new StateMaster();
        partialUpdatedStateMaster.setId(stateMaster.getId());

        partialUpdatedStateMaster.stateCode(UPDATED_STATE_CODE).stateName(UPDATED_STATE_NAME);

        restStateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStateMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStateMaster))
            )
            .andExpect(status().isOk());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
        StateMaster testStateMaster = stateMasterList.get(stateMasterList.size() - 1);
        assertThat(testStateMaster.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testStateMaster.getStateName()).isEqualTo(UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingStateMaster() throws Exception {
        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();
        stateMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stateMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stateMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStateMaster() throws Exception {
        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();
        stateMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stateMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStateMaster() throws Exception {
        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();
        stateMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stateMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStateMaster() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        int databaseSizeBeforeDelete = stateMasterRepository.findAll().size();

        // Delete the stateMaster
        restStateMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, stateMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
