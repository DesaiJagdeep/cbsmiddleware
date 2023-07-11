package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.LandTypeMaster;
import com.cbs.middleware.repository.LandTypeMasterRepository;
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
 * Integration tests for the {@link LandTypeMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LandTypeMasterResourceIT {

    private static final Integer DEFAULT_LAND_TYPE_CODE = 1;
    private static final Integer UPDATED_LAND_TYPE_CODE = 2;

    private static final String DEFAULT_LAND_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LAND_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/land-type-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LandTypeMasterRepository landTypeMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLandTypeMasterMockMvc;

    private LandTypeMaster landTypeMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandTypeMaster createEntity(EntityManager em) {
        LandTypeMaster landTypeMaster = new LandTypeMaster().landTypeCode(DEFAULT_LAND_TYPE_CODE).landType(DEFAULT_LAND_TYPE);
        return landTypeMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LandTypeMaster createUpdatedEntity(EntityManager em) {
        LandTypeMaster landTypeMaster = new LandTypeMaster().landTypeCode(UPDATED_LAND_TYPE_CODE).landType(UPDATED_LAND_TYPE);
        return landTypeMaster;
    }

    @BeforeEach
    public void initTest() {
        landTypeMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createLandTypeMaster() throws Exception {
        int databaseSizeBeforeCreate = landTypeMasterRepository.findAll().size();
        // Create the LandTypeMaster
        restLandTypeMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landTypeMaster))
            )
            .andExpect(status().isCreated());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeCreate + 1);
        LandTypeMaster testLandTypeMaster = landTypeMasterList.get(landTypeMasterList.size() - 1);
        assertThat(testLandTypeMaster.getLandTypeCode()).isEqualTo(DEFAULT_LAND_TYPE_CODE);
        assertThat(testLandTypeMaster.getLandType()).isEqualTo(DEFAULT_LAND_TYPE);
    }

    @Test
    @Transactional
    void createLandTypeMasterWithExistingId() throws Exception {
        // Create the LandTypeMaster with an existing ID
        landTypeMaster.setId(1L);

        int databaseSizeBeforeCreate = landTypeMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandTypeMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLandTypeMasters() throws Exception {
        // Initialize the database
        landTypeMasterRepository.saveAndFlush(landTypeMaster);

        // Get all the landTypeMasterList
        restLandTypeMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landTypeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].landTypeCode").value(hasItem(DEFAULT_LAND_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].landType").value(hasItem(DEFAULT_LAND_TYPE)));
    }

    @Test
    @Transactional
    void getLandTypeMaster() throws Exception {
        // Initialize the database
        landTypeMasterRepository.saveAndFlush(landTypeMaster);

        // Get the landTypeMaster
        restLandTypeMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, landTypeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(landTypeMaster.getId().intValue()))
            .andExpect(jsonPath("$.landTypeCode").value(DEFAULT_LAND_TYPE_CODE))
            .andExpect(jsonPath("$.landType").value(DEFAULT_LAND_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingLandTypeMaster() throws Exception {
        // Get the landTypeMaster
        restLandTypeMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLandTypeMaster() throws Exception {
        // Initialize the database
        landTypeMasterRepository.saveAndFlush(landTypeMaster);

        int databaseSizeBeforeUpdate = landTypeMasterRepository.findAll().size();

        // Update the landTypeMaster
        LandTypeMaster updatedLandTypeMaster = landTypeMasterRepository.findById(landTypeMaster.getId()).get();
        // Disconnect from session so that the updates on updatedLandTypeMaster are not directly saved in db
        em.detach(updatedLandTypeMaster);
        updatedLandTypeMaster.landTypeCode(UPDATED_LAND_TYPE_CODE).landType(UPDATED_LAND_TYPE);

        restLandTypeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLandTypeMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLandTypeMaster))
            )
            .andExpect(status().isOk());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeUpdate);
        LandTypeMaster testLandTypeMaster = landTypeMasterList.get(landTypeMasterList.size() - 1);
        assertThat(testLandTypeMaster.getLandTypeCode()).isEqualTo(UPDATED_LAND_TYPE_CODE);
        assertThat(testLandTypeMaster.getLandType()).isEqualTo(UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingLandTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = landTypeMasterRepository.findAll().size();
        landTypeMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandTypeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landTypeMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLandTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = landTypeMasterRepository.findAll().size();
        landTypeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandTypeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(landTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLandTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = landTypeMasterRepository.findAll().size();
        landTypeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandTypeMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(landTypeMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLandTypeMasterWithPatch() throws Exception {
        // Initialize the database
        landTypeMasterRepository.saveAndFlush(landTypeMaster);

        int databaseSizeBeforeUpdate = landTypeMasterRepository.findAll().size();

        // Update the landTypeMaster using partial update
        LandTypeMaster partialUpdatedLandTypeMaster = new LandTypeMaster();
        partialUpdatedLandTypeMaster.setId(landTypeMaster.getId());

        partialUpdatedLandTypeMaster.landTypeCode(UPDATED_LAND_TYPE_CODE);

        restLandTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandTypeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLandTypeMaster))
            )
            .andExpect(status().isOk());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeUpdate);
        LandTypeMaster testLandTypeMaster = landTypeMasterList.get(landTypeMasterList.size() - 1);
        assertThat(testLandTypeMaster.getLandTypeCode()).isEqualTo(UPDATED_LAND_TYPE_CODE);
        assertThat(testLandTypeMaster.getLandType()).isEqualTo(DEFAULT_LAND_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateLandTypeMasterWithPatch() throws Exception {
        // Initialize the database
        landTypeMasterRepository.saveAndFlush(landTypeMaster);

        int databaseSizeBeforeUpdate = landTypeMasterRepository.findAll().size();

        // Update the landTypeMaster using partial update
        LandTypeMaster partialUpdatedLandTypeMaster = new LandTypeMaster();
        partialUpdatedLandTypeMaster.setId(landTypeMaster.getId());

        partialUpdatedLandTypeMaster.landTypeCode(UPDATED_LAND_TYPE_CODE).landType(UPDATED_LAND_TYPE);

        restLandTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandTypeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLandTypeMaster))
            )
            .andExpect(status().isOk());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeUpdate);
        LandTypeMaster testLandTypeMaster = landTypeMasterList.get(landTypeMasterList.size() - 1);
        assertThat(testLandTypeMaster.getLandTypeCode()).isEqualTo(UPDATED_LAND_TYPE_CODE);
        assertThat(testLandTypeMaster.getLandType()).isEqualTo(UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingLandTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = landTypeMasterRepository.findAll().size();
        landTypeMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, landTypeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(landTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLandTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = landTypeMasterRepository.findAll().size();
        landTypeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(landTypeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLandTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = landTypeMasterRepository.findAll().size();
        landTypeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandTypeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(landTypeMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LandTypeMaster in the database
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLandTypeMaster() throws Exception {
        // Initialize the database
        landTypeMasterRepository.saveAndFlush(landTypeMaster);

        int databaseSizeBeforeDelete = landTypeMasterRepository.findAll().size();

        // Delete the landTypeMaster
        restLandTypeMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, landTypeMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LandTypeMaster> landTypeMasterList = landTypeMasterRepository.findAll();
        assertThat(landTypeMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
