package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.repository.CropMasterRepository;
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
 * Integration tests for the {@link CropMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CropMasterResourceIT {

    private static final String DEFAULT_CROP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CROP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/crop-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CropMasterRepository cropMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCropMasterMockMvc;

    private CropMaster cropMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CropMaster createEntity(EntityManager em) {
        CropMaster cropMaster = new CropMaster()
            .cropCode(DEFAULT_CROP_CODE)
            .cropName(DEFAULT_CROP_NAME)
            .categoryCode(DEFAULT_CATEGORY_CODE);
        return cropMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CropMaster createUpdatedEntity(EntityManager em) {
        CropMaster cropMaster = new CropMaster()
            .cropCode(UPDATED_CROP_CODE)
            .cropName(UPDATED_CROP_NAME)
            .categoryCode(UPDATED_CATEGORY_CODE);
        return cropMaster;
    }

    @BeforeEach
    public void initTest() {
        cropMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createCropMaster() throws Exception {
        int databaseSizeBeforeCreate = cropMasterRepository.findAll().size();
        // Create the CropMaster
        restCropMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropMaster)))
            .andExpect(status().isCreated());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CropMaster testCropMaster = cropMasterList.get(cropMasterList.size() - 1);
        assertThat(testCropMaster.getCropCode()).isEqualTo(DEFAULT_CROP_CODE);
        assertThat(testCropMaster.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testCropMaster.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void createCropMasterWithExistingId() throws Exception {
        // Create the CropMaster with an existing ID
        cropMaster.setId(1L);

        int databaseSizeBeforeCreate = cropMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCropMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropMaster)))
            .andExpect(status().isBadRequest());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCropMasters() throws Exception {
        // Initialize the database
        cropMasterRepository.saveAndFlush(cropMaster);

        // Get all the cropMasterList
        restCropMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cropMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropCode").value(hasItem(DEFAULT_CROP_CODE)))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)));
    }

    @Test
    @Transactional
    void getCropMaster() throws Exception {
        // Initialize the database
        cropMasterRepository.saveAndFlush(cropMaster);

        // Get the cropMaster
        restCropMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, cropMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cropMaster.getId().intValue()))
            .andExpect(jsonPath("$.cropCode").value(DEFAULT_CROP_CODE))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME))
            .andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE));
    }

    @Test
    @Transactional
    void getNonExistingCropMaster() throws Exception {
        // Get the cropMaster
        restCropMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCropMaster() throws Exception {
        // Initialize the database
        cropMasterRepository.saveAndFlush(cropMaster);

        int databaseSizeBeforeUpdate = cropMasterRepository.findAll().size();

        // Update the cropMaster
        CropMaster updatedCropMaster = cropMasterRepository.findById(cropMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCropMaster are not directly saved in db
        em.detach(updatedCropMaster);
        updatedCropMaster.cropCode(UPDATED_CROP_CODE).cropName(UPDATED_CROP_NAME).categoryCode(UPDATED_CATEGORY_CODE);

        restCropMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCropMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCropMaster))
            )
            .andExpect(status().isOk());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeUpdate);
        CropMaster testCropMaster = cropMasterList.get(cropMasterList.size() - 1);
        assertThat(testCropMaster.getCropCode()).isEqualTo(UPDATED_CROP_CODE);
        assertThat(testCropMaster.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testCropMaster.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCropMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropMasterRepository.findAll().size();
        cropMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cropMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cropMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCropMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropMasterRepository.findAll().size();
        cropMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cropMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCropMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropMasterRepository.findAll().size();
        cropMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCropMasterWithPatch() throws Exception {
        // Initialize the database
        cropMasterRepository.saveAndFlush(cropMaster);

        int databaseSizeBeforeUpdate = cropMasterRepository.findAll().size();

        // Update the cropMaster using partial update
        CropMaster partialUpdatedCropMaster = new CropMaster();
        partialUpdatedCropMaster.setId(cropMaster.getId());

        restCropMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCropMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCropMaster))
            )
            .andExpect(status().isOk());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeUpdate);
        CropMaster testCropMaster = cropMasterList.get(cropMasterList.size() - 1);
        assertThat(testCropMaster.getCropCode()).isEqualTo(DEFAULT_CROP_CODE);
        assertThat(testCropMaster.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testCropMaster.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void fullUpdateCropMasterWithPatch() throws Exception {
        // Initialize the database
        cropMasterRepository.saveAndFlush(cropMaster);

        int databaseSizeBeforeUpdate = cropMasterRepository.findAll().size();

        // Update the cropMaster using partial update
        CropMaster partialUpdatedCropMaster = new CropMaster();
        partialUpdatedCropMaster.setId(cropMaster.getId());

        partialUpdatedCropMaster.cropCode(UPDATED_CROP_CODE).cropName(UPDATED_CROP_NAME).categoryCode(UPDATED_CATEGORY_CODE);

        restCropMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCropMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCropMaster))
            )
            .andExpect(status().isOk());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeUpdate);
        CropMaster testCropMaster = cropMasterList.get(cropMasterList.size() - 1);
        assertThat(testCropMaster.getCropCode()).isEqualTo(UPDATED_CROP_CODE);
        assertThat(testCropMaster.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testCropMaster.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCropMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropMasterRepository.findAll().size();
        cropMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cropMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cropMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCropMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropMasterRepository.findAll().size();
        cropMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cropMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCropMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropMasterRepository.findAll().size();
        cropMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cropMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CropMaster in the database
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCropMaster() throws Exception {
        // Initialize the database
        cropMasterRepository.saveAndFlush(cropMaster);

        int databaseSizeBeforeDelete = cropMasterRepository.findAll().size();

        // Delete the cropMaster
        restCropMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, cropMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CropMaster> cropMasterList = cropMasterRepository.findAll();
        assertThat(cropMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
