package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.OccupationMaster;
import com.cbs.middleware.repository.OccupationMasterRepository;
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
 * Integration tests for the {@link OccupationMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OccupationMasterResourceIT {

    private static final Integer DEFAULT_OCCUPATION_CODE = 1;
    private static final Integer UPDATED_OCCUPATION_CODE = 2;

    private static final String DEFAULT_OCCUPATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/occupation-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OccupationMasterRepository occupationMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOccupationMasterMockMvc;

    private OccupationMaster occupationMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OccupationMaster createEntity(EntityManager em) {
        OccupationMaster occupationMaster = new OccupationMaster()
            .occupationCode(DEFAULT_OCCUPATION_CODE)
            .occupationName(DEFAULT_OCCUPATION_NAME);
        return occupationMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OccupationMaster createUpdatedEntity(EntityManager em) {
        OccupationMaster occupationMaster = new OccupationMaster()
            .occupationCode(UPDATED_OCCUPATION_CODE)
            .occupationName(UPDATED_OCCUPATION_NAME);
        return occupationMaster;
    }

    @BeforeEach
    public void initTest() {
        occupationMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createOccupationMaster() throws Exception {
        int databaseSizeBeforeCreate = occupationMasterRepository.findAll().size();
        // Create the OccupationMaster
        restOccupationMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(occupationMaster))
            )
            .andExpect(status().isCreated());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeCreate + 1);
        OccupationMaster testOccupationMaster = occupationMasterList.get(occupationMasterList.size() - 1);
        assertThat(testOccupationMaster.getOccupationCode()).isEqualTo(DEFAULT_OCCUPATION_CODE);
        assertThat(testOccupationMaster.getOccupationName()).isEqualTo(DEFAULT_OCCUPATION_NAME);
    }

    @Test
    @Transactional
    void createOccupationMasterWithExistingId() throws Exception {
        // Create the OccupationMaster with an existing ID
        occupationMaster.setId(1L);

        int databaseSizeBeforeCreate = occupationMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOccupationMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(occupationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOccupationMasters() throws Exception {
        // Initialize the database
        occupationMasterRepository.saveAndFlush(occupationMaster);

        // Get all the occupationMasterList
        restOccupationMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(occupationMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].occupationCode").value(hasItem(DEFAULT_OCCUPATION_CODE)))
            .andExpect(jsonPath("$.[*].occupationName").value(hasItem(DEFAULT_OCCUPATION_NAME)));
    }

    @Test
    @Transactional
    void getOccupationMaster() throws Exception {
        // Initialize the database
        occupationMasterRepository.saveAndFlush(occupationMaster);

        // Get the occupationMaster
        restOccupationMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, occupationMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(occupationMaster.getId().intValue()))
            .andExpect(jsonPath("$.occupationCode").value(DEFAULT_OCCUPATION_CODE))
            .andExpect(jsonPath("$.occupationName").value(DEFAULT_OCCUPATION_NAME));
    }

    @Test
    @Transactional
    void getNonExistingOccupationMaster() throws Exception {
        // Get the occupationMaster
        restOccupationMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOccupationMaster() throws Exception {
        // Initialize the database
        occupationMasterRepository.saveAndFlush(occupationMaster);

        int databaseSizeBeforeUpdate = occupationMasterRepository.findAll().size();

        // Update the occupationMaster
        OccupationMaster updatedOccupationMaster = occupationMasterRepository.findById(occupationMaster.getId()).get();
        // Disconnect from session so that the updates on updatedOccupationMaster are not directly saved in db
        em.detach(updatedOccupationMaster);
        updatedOccupationMaster.occupationCode(UPDATED_OCCUPATION_CODE).occupationName(UPDATED_OCCUPATION_NAME);

        restOccupationMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOccupationMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOccupationMaster))
            )
            .andExpect(status().isOk());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeUpdate);
        OccupationMaster testOccupationMaster = occupationMasterList.get(occupationMasterList.size() - 1);
        assertThat(testOccupationMaster.getOccupationCode()).isEqualTo(UPDATED_OCCUPATION_CODE);
        assertThat(testOccupationMaster.getOccupationName()).isEqualTo(UPDATED_OCCUPATION_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOccupationMaster() throws Exception {
        int databaseSizeBeforeUpdate = occupationMasterRepository.findAll().size();
        occupationMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOccupationMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, occupationMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(occupationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOccupationMaster() throws Exception {
        int databaseSizeBeforeUpdate = occupationMasterRepository.findAll().size();
        occupationMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOccupationMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(occupationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOccupationMaster() throws Exception {
        int databaseSizeBeforeUpdate = occupationMasterRepository.findAll().size();
        occupationMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOccupationMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(occupationMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOccupationMasterWithPatch() throws Exception {
        // Initialize the database
        occupationMasterRepository.saveAndFlush(occupationMaster);

        int databaseSizeBeforeUpdate = occupationMasterRepository.findAll().size();

        // Update the occupationMaster using partial update
        OccupationMaster partialUpdatedOccupationMaster = new OccupationMaster();
        partialUpdatedOccupationMaster.setId(occupationMaster.getId());

        partialUpdatedOccupationMaster.occupationCode(UPDATED_OCCUPATION_CODE).occupationName(UPDATED_OCCUPATION_NAME);

        restOccupationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOccupationMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOccupationMaster))
            )
            .andExpect(status().isOk());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeUpdate);
        OccupationMaster testOccupationMaster = occupationMasterList.get(occupationMasterList.size() - 1);
        assertThat(testOccupationMaster.getOccupationCode()).isEqualTo(UPDATED_OCCUPATION_CODE);
        assertThat(testOccupationMaster.getOccupationName()).isEqualTo(UPDATED_OCCUPATION_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOccupationMasterWithPatch() throws Exception {
        // Initialize the database
        occupationMasterRepository.saveAndFlush(occupationMaster);

        int databaseSizeBeforeUpdate = occupationMasterRepository.findAll().size();

        // Update the occupationMaster using partial update
        OccupationMaster partialUpdatedOccupationMaster = new OccupationMaster();
        partialUpdatedOccupationMaster.setId(occupationMaster.getId());

        partialUpdatedOccupationMaster.occupationCode(UPDATED_OCCUPATION_CODE).occupationName(UPDATED_OCCUPATION_NAME);

        restOccupationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOccupationMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOccupationMaster))
            )
            .andExpect(status().isOk());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeUpdate);
        OccupationMaster testOccupationMaster = occupationMasterList.get(occupationMasterList.size() - 1);
        assertThat(testOccupationMaster.getOccupationCode()).isEqualTo(UPDATED_OCCUPATION_CODE);
        assertThat(testOccupationMaster.getOccupationName()).isEqualTo(UPDATED_OCCUPATION_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOccupationMaster() throws Exception {
        int databaseSizeBeforeUpdate = occupationMasterRepository.findAll().size();
        occupationMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOccupationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, occupationMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(occupationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOccupationMaster() throws Exception {
        int databaseSizeBeforeUpdate = occupationMasterRepository.findAll().size();
        occupationMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOccupationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(occupationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOccupationMaster() throws Exception {
        int databaseSizeBeforeUpdate = occupationMasterRepository.findAll().size();
        occupationMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOccupationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(occupationMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OccupationMaster in the database
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOccupationMaster() throws Exception {
        // Initialize the database
        occupationMasterRepository.saveAndFlush(occupationMaster);

        int databaseSizeBeforeDelete = occupationMasterRepository.findAll().size();

        // Delete the occupationMaster
        restOccupationMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, occupationMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OccupationMaster> occupationMasterList = occupationMasterRepository.findAll();
        assertThat(occupationMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
