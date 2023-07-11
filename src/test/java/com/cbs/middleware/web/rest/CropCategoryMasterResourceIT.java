package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CropCategoryMaster;
import com.cbs.middleware.repository.CropCategoryMasterRepository;
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
 * Integration tests for the {@link CropCategoryMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CropCategoryMasterResourceIT {

    private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/crop-category-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CropCategoryMasterRepository cropCategoryMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCropCategoryMasterMockMvc;

    private CropCategoryMaster cropCategoryMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CropCategoryMaster createEntity(EntityManager em) {
        CropCategoryMaster cropCategoryMaster = new CropCategoryMaster()
            .categoryCode(DEFAULT_CATEGORY_CODE)
            .categoryName(DEFAULT_CATEGORY_NAME);
        return cropCategoryMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CropCategoryMaster createUpdatedEntity(EntityManager em) {
        CropCategoryMaster cropCategoryMaster = new CropCategoryMaster()
            .categoryCode(UPDATED_CATEGORY_CODE)
            .categoryName(UPDATED_CATEGORY_NAME);
        return cropCategoryMaster;
    }

    @BeforeEach
    public void initTest() {
        cropCategoryMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createCropCategoryMaster() throws Exception {
        int databaseSizeBeforeCreate = cropCategoryMasterRepository.findAll().size();
        // Create the CropCategoryMaster
        restCropCategoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropCategoryMaster))
            )
            .andExpect(status().isCreated());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CropCategoryMaster testCropCategoryMaster = cropCategoryMasterList.get(cropCategoryMasterList.size() - 1);
        assertThat(testCropCategoryMaster.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
        assertThat(testCropCategoryMaster.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void createCropCategoryMasterWithExistingId() throws Exception {
        // Create the CropCategoryMaster with an existing ID
        cropCategoryMaster.setId(1L);

        int databaseSizeBeforeCreate = cropCategoryMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCropCategoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCropCategoryMasters() throws Exception {
        // Initialize the database
        cropCategoryMasterRepository.saveAndFlush(cropCategoryMaster);

        // Get all the cropCategoryMasterList
        restCropCategoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cropCategoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)));
    }

    @Test
    @Transactional
    void getCropCategoryMaster() throws Exception {
        // Initialize the database
        cropCategoryMasterRepository.saveAndFlush(cropCategoryMaster);

        // Get the cropCategoryMaster
        restCropCategoryMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, cropCategoryMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cropCategoryMaster.getId().intValue()))
            .andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCropCategoryMaster() throws Exception {
        // Get the cropCategoryMaster
        restCropCategoryMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCropCategoryMaster() throws Exception {
        // Initialize the database
        cropCategoryMasterRepository.saveAndFlush(cropCategoryMaster);

        int databaseSizeBeforeUpdate = cropCategoryMasterRepository.findAll().size();

        // Update the cropCategoryMaster
        CropCategoryMaster updatedCropCategoryMaster = cropCategoryMasterRepository.findById(cropCategoryMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCropCategoryMaster are not directly saved in db
        em.detach(updatedCropCategoryMaster);
        updatedCropCategoryMaster.categoryCode(UPDATED_CATEGORY_CODE).categoryName(UPDATED_CATEGORY_NAME);

        restCropCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCropCategoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCropCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CropCategoryMaster testCropCategoryMaster = cropCategoryMasterList.get(cropCategoryMasterList.size() - 1);
        assertThat(testCropCategoryMaster.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testCropCategoryMaster.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCropCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropCategoryMasterRepository.findAll().size();
        cropCategoryMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cropCategoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cropCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCropCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropCategoryMasterRepository.findAll().size();
        cropCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cropCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCropCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropCategoryMasterRepository.findAll().size();
        cropCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropCategoryMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCropCategoryMasterWithPatch() throws Exception {
        // Initialize the database
        cropCategoryMasterRepository.saveAndFlush(cropCategoryMaster);

        int databaseSizeBeforeUpdate = cropCategoryMasterRepository.findAll().size();

        // Update the cropCategoryMaster using partial update
        CropCategoryMaster partialUpdatedCropCategoryMaster = new CropCategoryMaster();
        partialUpdatedCropCategoryMaster.setId(cropCategoryMaster.getId());

        partialUpdatedCropCategoryMaster.categoryCode(UPDATED_CATEGORY_CODE);

        restCropCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCropCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCropCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CropCategoryMaster testCropCategoryMaster = cropCategoryMasterList.get(cropCategoryMasterList.size() - 1);
        assertThat(testCropCategoryMaster.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testCropCategoryMaster.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCropCategoryMasterWithPatch() throws Exception {
        // Initialize the database
        cropCategoryMasterRepository.saveAndFlush(cropCategoryMaster);

        int databaseSizeBeforeUpdate = cropCategoryMasterRepository.findAll().size();

        // Update the cropCategoryMaster using partial update
        CropCategoryMaster partialUpdatedCropCategoryMaster = new CropCategoryMaster();
        partialUpdatedCropCategoryMaster.setId(cropCategoryMaster.getId());

        partialUpdatedCropCategoryMaster.categoryCode(UPDATED_CATEGORY_CODE).categoryName(UPDATED_CATEGORY_NAME);

        restCropCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCropCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCropCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CropCategoryMaster testCropCategoryMaster = cropCategoryMasterList.get(cropCategoryMasterList.size() - 1);
        assertThat(testCropCategoryMaster.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testCropCategoryMaster.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCropCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropCategoryMasterRepository.findAll().size();
        cropCategoryMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cropCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cropCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCropCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropCategoryMasterRepository.findAll().size();
        cropCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cropCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCropCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = cropCategoryMasterRepository.findAll().size();
        cropCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cropCategoryMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CropCategoryMaster in the database
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCropCategoryMaster() throws Exception {
        // Initialize the database
        cropCategoryMasterRepository.saveAndFlush(cropCategoryMaster);

        int databaseSizeBeforeDelete = cropCategoryMasterRepository.findAll().size();

        // Delete the cropCategoryMaster
        restCropCategoryMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, cropCategoryMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CropCategoryMaster> cropCategoryMasterList = cropCategoryMasterRepository.findAll();
        assertThat(cropCategoryMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
