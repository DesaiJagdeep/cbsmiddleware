package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CategoryMaster;
import com.cbs.middleware.repository.CategoryMasterRepository;
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
 * Integration tests for the {@link CategoryMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoryMasterResourceIT {

    private static final String DEFAULT_CAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CAST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CAST_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CAST_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/category-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoryMasterRepository categoryMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryMasterMockMvc;

    private CategoryMaster categoryMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryMaster createEntity(EntityManager em) {
        CategoryMaster categoryMaster = new CategoryMaster()
            .castName(DEFAULT_CAST_NAME)
            .castCode(DEFAULT_CAST_CODE)
            .castCategoryCode(DEFAULT_CAST_CATEGORY_CODE);
        return categoryMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryMaster createUpdatedEntity(EntityManager em) {
        CategoryMaster categoryMaster = new CategoryMaster()
            .castName(UPDATED_CAST_NAME)
            .castCode(UPDATED_CAST_CODE)
            .castCategoryCode(UPDATED_CAST_CATEGORY_CODE);
        return categoryMaster;
    }

    @BeforeEach
    public void initTest() {
        categoryMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoryMaster() throws Exception {
        int databaseSizeBeforeCreate = categoryMasterRepository.findAll().size();
        // Create the CategoryMaster
        restCategoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryMaster))
            )
            .andExpect(status().isCreated());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryMaster testCategoryMaster = categoryMasterList.get(categoryMasterList.size() - 1);
        assertThat(testCategoryMaster.getCastName()).isEqualTo(DEFAULT_CAST_NAME);
        assertThat(testCategoryMaster.getCastCode()).isEqualTo(DEFAULT_CAST_CODE);
        assertThat(testCategoryMaster.getCastCategoryCode()).isEqualTo(DEFAULT_CAST_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void createCategoryMasterWithExistingId() throws Exception {
        // Create the CategoryMaster with an existing ID
        categoryMaster.setId(1L);

        int databaseSizeBeforeCreate = categoryMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategoryMasters() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get all the categoryMasterList
        restCategoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].castName").value(hasItem(DEFAULT_CAST_NAME)))
            .andExpect(jsonPath("$.[*].castCode").value(hasItem(DEFAULT_CAST_CODE)))
            .andExpect(jsonPath("$.[*].castCategoryCode").value(hasItem(DEFAULT_CAST_CATEGORY_CODE)));
    }

    @Test
    @Transactional
    void getCategoryMaster() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get the categoryMaster
        restCategoryMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, categoryMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryMaster.getId().intValue()))
            .andExpect(jsonPath("$.castName").value(DEFAULT_CAST_NAME))
            .andExpect(jsonPath("$.castCode").value(DEFAULT_CAST_CODE))
            .andExpect(jsonPath("$.castCategoryCode").value(DEFAULT_CAST_CATEGORY_CODE));
    }

    @Test
    @Transactional
    void getNonExistingCategoryMaster() throws Exception {
        // Get the categoryMaster
        restCategoryMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategoryMaster() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();

        // Update the categoryMaster
        CategoryMaster updatedCategoryMaster = categoryMasterRepository.findById(categoryMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryMaster are not directly saved in db
        em.detach(updatedCategoryMaster);
        updatedCategoryMaster.castName(UPDATED_CAST_NAME).castCode(UPDATED_CAST_CODE).castCategoryCode(UPDATED_CAST_CATEGORY_CODE);

        restCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CategoryMaster testCategoryMaster = categoryMasterList.get(categoryMasterList.size() - 1);
        assertThat(testCategoryMaster.getCastName()).isEqualTo(UPDATED_CAST_NAME);
        assertThat(testCategoryMaster.getCastCode()).isEqualTo(UPDATED_CAST_CODE);
        assertThat(testCategoryMaster.getCastCategoryCode()).isEqualTo(UPDATED_CAST_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();
        categoryMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();
        categoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();
        categoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoryMasterWithPatch() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();

        // Update the categoryMaster using partial update
        CategoryMaster partialUpdatedCategoryMaster = new CategoryMaster();
        partialUpdatedCategoryMaster.setId(categoryMaster.getId());

        restCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CategoryMaster testCategoryMaster = categoryMasterList.get(categoryMasterList.size() - 1);
        assertThat(testCategoryMaster.getCastName()).isEqualTo(DEFAULT_CAST_NAME);
        assertThat(testCategoryMaster.getCastCode()).isEqualTo(DEFAULT_CAST_CODE);
        assertThat(testCategoryMaster.getCastCategoryCode()).isEqualTo(DEFAULT_CAST_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void fullUpdateCategoryMasterWithPatch() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();

        // Update the categoryMaster using partial update
        CategoryMaster partialUpdatedCategoryMaster = new CategoryMaster();
        partialUpdatedCategoryMaster.setId(categoryMaster.getId());

        partialUpdatedCategoryMaster.castName(UPDATED_CAST_NAME).castCode(UPDATED_CAST_CODE).castCategoryCode(UPDATED_CAST_CATEGORY_CODE);

        restCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CategoryMaster testCategoryMaster = categoryMasterList.get(categoryMasterList.size() - 1);
        assertThat(testCategoryMaster.getCastName()).isEqualTo(UPDATED_CAST_NAME);
        assertThat(testCategoryMaster.getCastCode()).isEqualTo(UPDATED_CAST_CODE);
        assertThat(testCategoryMaster.getCastCategoryCode()).isEqualTo(UPDATED_CAST_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();
        categoryMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();
        categoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();
        categoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categoryMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoryMaster() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        int databaseSizeBeforeDelete = categoryMasterRepository.findAll().size();

        // Delete the categoryMaster
        restCategoryMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoryMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
