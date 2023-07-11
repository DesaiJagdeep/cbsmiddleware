package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CastCategoryMaster;
import com.cbs.middleware.repository.CastCategoryMasterRepository;
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
 * Integration tests for the {@link CastCategoryMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CastCategoryMasterResourceIT {

    private static final Integer DEFAULT_CAST_CATEGORY_CODE = 1;
    private static final Integer UPDATED_CAST_CATEGORY_CODE = 2;

    private static final String DEFAULT_CAST_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAST_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cast-category-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CastCategoryMasterRepository castCategoryMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCastCategoryMasterMockMvc;

    private CastCategoryMaster castCategoryMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CastCategoryMaster createEntity(EntityManager em) {
        CastCategoryMaster castCategoryMaster = new CastCategoryMaster()
            .castCategoryCode(DEFAULT_CAST_CATEGORY_CODE)
            .castCategoryName(DEFAULT_CAST_CATEGORY_NAME);
        return castCategoryMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CastCategoryMaster createUpdatedEntity(EntityManager em) {
        CastCategoryMaster castCategoryMaster = new CastCategoryMaster()
            .castCategoryCode(UPDATED_CAST_CATEGORY_CODE)
            .castCategoryName(UPDATED_CAST_CATEGORY_NAME);
        return castCategoryMaster;
    }

    @BeforeEach
    public void initTest() {
        castCategoryMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createCastCategoryMaster() throws Exception {
        int databaseSizeBeforeCreate = castCategoryMasterRepository.findAll().size();
        // Create the CastCategoryMaster
        restCastCategoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(castCategoryMaster))
            )
            .andExpect(status().isCreated());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CastCategoryMaster testCastCategoryMaster = castCategoryMasterList.get(castCategoryMasterList.size() - 1);
        assertThat(testCastCategoryMaster.getCastCategoryCode()).isEqualTo(DEFAULT_CAST_CATEGORY_CODE);
        assertThat(testCastCategoryMaster.getCastCategoryName()).isEqualTo(DEFAULT_CAST_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void createCastCategoryMasterWithExistingId() throws Exception {
        // Create the CastCategoryMaster with an existing ID
        castCategoryMaster.setId(1L);

        int databaseSizeBeforeCreate = castCategoryMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCastCategoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(castCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCastCategoryMasters() throws Exception {
        // Initialize the database
        castCategoryMasterRepository.saveAndFlush(castCategoryMaster);

        // Get all the castCategoryMasterList
        restCastCategoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(castCategoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].castCategoryCode").value(hasItem(DEFAULT_CAST_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].castCategoryName").value(hasItem(DEFAULT_CAST_CATEGORY_NAME)));
    }

    @Test
    @Transactional
    void getCastCategoryMaster() throws Exception {
        // Initialize the database
        castCategoryMasterRepository.saveAndFlush(castCategoryMaster);

        // Get the castCategoryMaster
        restCastCategoryMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, castCategoryMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(castCategoryMaster.getId().intValue()))
            .andExpect(jsonPath("$.castCategoryCode").value(DEFAULT_CAST_CATEGORY_CODE))
            .andExpect(jsonPath("$.castCategoryName").value(DEFAULT_CAST_CATEGORY_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCastCategoryMaster() throws Exception {
        // Get the castCategoryMaster
        restCastCategoryMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCastCategoryMaster() throws Exception {
        // Initialize the database
        castCategoryMasterRepository.saveAndFlush(castCategoryMaster);

        int databaseSizeBeforeUpdate = castCategoryMasterRepository.findAll().size();

        // Update the castCategoryMaster
        CastCategoryMaster updatedCastCategoryMaster = castCategoryMasterRepository.findById(castCategoryMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCastCategoryMaster are not directly saved in db
        em.detach(updatedCastCategoryMaster);
        updatedCastCategoryMaster.castCategoryCode(UPDATED_CAST_CATEGORY_CODE).castCategoryName(UPDATED_CAST_CATEGORY_NAME);

        restCastCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCastCategoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCastCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CastCategoryMaster testCastCategoryMaster = castCategoryMasterList.get(castCategoryMasterList.size() - 1);
        assertThat(testCastCategoryMaster.getCastCategoryCode()).isEqualTo(UPDATED_CAST_CATEGORY_CODE);
        assertThat(testCastCategoryMaster.getCastCategoryName()).isEqualTo(UPDATED_CAST_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCastCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = castCategoryMasterRepository.findAll().size();
        castCategoryMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCastCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, castCategoryMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(castCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCastCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = castCategoryMasterRepository.findAll().size();
        castCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCastCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(castCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCastCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = castCategoryMasterRepository.findAll().size();
        castCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCastCategoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(castCategoryMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCastCategoryMasterWithPatch() throws Exception {
        // Initialize the database
        castCategoryMasterRepository.saveAndFlush(castCategoryMaster);

        int databaseSizeBeforeUpdate = castCategoryMasterRepository.findAll().size();

        // Update the castCategoryMaster using partial update
        CastCategoryMaster partialUpdatedCastCategoryMaster = new CastCategoryMaster();
        partialUpdatedCastCategoryMaster.setId(castCategoryMaster.getId());

        partialUpdatedCastCategoryMaster.castCategoryName(UPDATED_CAST_CATEGORY_NAME);

        restCastCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCastCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCastCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CastCategoryMaster testCastCategoryMaster = castCategoryMasterList.get(castCategoryMasterList.size() - 1);
        assertThat(testCastCategoryMaster.getCastCategoryCode()).isEqualTo(DEFAULT_CAST_CATEGORY_CODE);
        assertThat(testCastCategoryMaster.getCastCategoryName()).isEqualTo(UPDATED_CAST_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCastCategoryMasterWithPatch() throws Exception {
        // Initialize the database
        castCategoryMasterRepository.saveAndFlush(castCategoryMaster);

        int databaseSizeBeforeUpdate = castCategoryMasterRepository.findAll().size();

        // Update the castCategoryMaster using partial update
        CastCategoryMaster partialUpdatedCastCategoryMaster = new CastCategoryMaster();
        partialUpdatedCastCategoryMaster.setId(castCategoryMaster.getId());

        partialUpdatedCastCategoryMaster.castCategoryCode(UPDATED_CAST_CATEGORY_CODE).castCategoryName(UPDATED_CAST_CATEGORY_NAME);

        restCastCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCastCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCastCategoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CastCategoryMaster testCastCategoryMaster = castCategoryMasterList.get(castCategoryMasterList.size() - 1);
        assertThat(testCastCategoryMaster.getCastCategoryCode()).isEqualTo(UPDATED_CAST_CATEGORY_CODE);
        assertThat(testCastCategoryMaster.getCastCategoryName()).isEqualTo(UPDATED_CAST_CATEGORY_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCastCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = castCategoryMasterRepository.findAll().size();
        castCategoryMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCastCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, castCategoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(castCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCastCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = castCategoryMasterRepository.findAll().size();
        castCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCastCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(castCategoryMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCastCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = castCategoryMasterRepository.findAll().size();
        castCategoryMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCastCategoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(castCategoryMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CastCategoryMaster in the database
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCastCategoryMaster() throws Exception {
        // Initialize the database
        castCategoryMasterRepository.saveAndFlush(castCategoryMaster);

        int databaseSizeBeforeDelete = castCategoryMasterRepository.findAll().size();

        // Delete the castCategoryMaster
        restCastCategoryMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, castCategoryMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CastCategoryMaster> castCategoryMasterList = castCategoryMasterRepository.findAll();
        assertThat(castCategoryMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
