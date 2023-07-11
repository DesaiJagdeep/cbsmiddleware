package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.RelativeMaster;
import com.cbs.middleware.repository.RelativeMasterRepository;
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
 * Integration tests for the {@link RelativeMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelativeMasterResourceIT {

    private static final Integer DEFAULT_RELATIVE_CODE = 1;
    private static final Integer UPDATED_RELATIVE_CODE = 2;

    private static final String DEFAULT_RELATIVE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RELATIVE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/relative-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RelativeMasterRepository relativeMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelativeMasterMockMvc;

    private RelativeMaster relativeMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelativeMaster createEntity(EntityManager em) {
        RelativeMaster relativeMaster = new RelativeMaster().relativeCode(DEFAULT_RELATIVE_CODE).relativeName(DEFAULT_RELATIVE_NAME);
        return relativeMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelativeMaster createUpdatedEntity(EntityManager em) {
        RelativeMaster relativeMaster = new RelativeMaster().relativeCode(UPDATED_RELATIVE_CODE).relativeName(UPDATED_RELATIVE_NAME);
        return relativeMaster;
    }

    @BeforeEach
    public void initTest() {
        relativeMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createRelativeMaster() throws Exception {
        int databaseSizeBeforeCreate = relativeMasterRepository.findAll().size();
        // Create the RelativeMaster
        restRelativeMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relativeMaster))
            )
            .andExpect(status().isCreated());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeCreate + 1);
        RelativeMaster testRelativeMaster = relativeMasterList.get(relativeMasterList.size() - 1);
        assertThat(testRelativeMaster.getRelativeCode()).isEqualTo(DEFAULT_RELATIVE_CODE);
        assertThat(testRelativeMaster.getRelativeName()).isEqualTo(DEFAULT_RELATIVE_NAME);
    }

    @Test
    @Transactional
    void createRelativeMasterWithExistingId() throws Exception {
        // Create the RelativeMaster with an existing ID
        relativeMaster.setId(1L);

        int databaseSizeBeforeCreate = relativeMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelativeMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relativeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRelativeMasters() throws Exception {
        // Initialize the database
        relativeMasterRepository.saveAndFlush(relativeMaster);

        // Get all the relativeMasterList
        restRelativeMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relativeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].relativeCode").value(hasItem(DEFAULT_RELATIVE_CODE)))
            .andExpect(jsonPath("$.[*].relativeName").value(hasItem(DEFAULT_RELATIVE_NAME)));
    }

    @Test
    @Transactional
    void getRelativeMaster() throws Exception {
        // Initialize the database
        relativeMasterRepository.saveAndFlush(relativeMaster);

        // Get the relativeMaster
        restRelativeMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, relativeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relativeMaster.getId().intValue()))
            .andExpect(jsonPath("$.relativeCode").value(DEFAULT_RELATIVE_CODE))
            .andExpect(jsonPath("$.relativeName").value(DEFAULT_RELATIVE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRelativeMaster() throws Exception {
        // Get the relativeMaster
        restRelativeMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRelativeMaster() throws Exception {
        // Initialize the database
        relativeMasterRepository.saveAndFlush(relativeMaster);

        int databaseSizeBeforeUpdate = relativeMasterRepository.findAll().size();

        // Update the relativeMaster
        RelativeMaster updatedRelativeMaster = relativeMasterRepository.findById(relativeMaster.getId()).get();
        // Disconnect from session so that the updates on updatedRelativeMaster are not directly saved in db
        em.detach(updatedRelativeMaster);
        updatedRelativeMaster.relativeCode(UPDATED_RELATIVE_CODE).relativeName(UPDATED_RELATIVE_NAME);

        restRelativeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRelativeMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRelativeMaster))
            )
            .andExpect(status().isOk());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeUpdate);
        RelativeMaster testRelativeMaster = relativeMasterList.get(relativeMasterList.size() - 1);
        assertThat(testRelativeMaster.getRelativeCode()).isEqualTo(UPDATED_RELATIVE_CODE);
        assertThat(testRelativeMaster.getRelativeName()).isEqualTo(UPDATED_RELATIVE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRelativeMaster() throws Exception {
        int databaseSizeBeforeUpdate = relativeMasterRepository.findAll().size();
        relativeMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelativeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relativeMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relativeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelativeMaster() throws Exception {
        int databaseSizeBeforeUpdate = relativeMasterRepository.findAll().size();
        relativeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelativeMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relativeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelativeMaster() throws Exception {
        int databaseSizeBeforeUpdate = relativeMasterRepository.findAll().size();
        relativeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelativeMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relativeMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelativeMasterWithPatch() throws Exception {
        // Initialize the database
        relativeMasterRepository.saveAndFlush(relativeMaster);

        int databaseSizeBeforeUpdate = relativeMasterRepository.findAll().size();

        // Update the relativeMaster using partial update
        RelativeMaster partialUpdatedRelativeMaster = new RelativeMaster();
        partialUpdatedRelativeMaster.setId(relativeMaster.getId());

        partialUpdatedRelativeMaster.relativeName(UPDATED_RELATIVE_NAME);

        restRelativeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelativeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelativeMaster))
            )
            .andExpect(status().isOk());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeUpdate);
        RelativeMaster testRelativeMaster = relativeMasterList.get(relativeMasterList.size() - 1);
        assertThat(testRelativeMaster.getRelativeCode()).isEqualTo(DEFAULT_RELATIVE_CODE);
        assertThat(testRelativeMaster.getRelativeName()).isEqualTo(UPDATED_RELATIVE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRelativeMasterWithPatch() throws Exception {
        // Initialize the database
        relativeMasterRepository.saveAndFlush(relativeMaster);

        int databaseSizeBeforeUpdate = relativeMasterRepository.findAll().size();

        // Update the relativeMaster using partial update
        RelativeMaster partialUpdatedRelativeMaster = new RelativeMaster();
        partialUpdatedRelativeMaster.setId(relativeMaster.getId());

        partialUpdatedRelativeMaster.relativeCode(UPDATED_RELATIVE_CODE).relativeName(UPDATED_RELATIVE_NAME);

        restRelativeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelativeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelativeMaster))
            )
            .andExpect(status().isOk());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeUpdate);
        RelativeMaster testRelativeMaster = relativeMasterList.get(relativeMasterList.size() - 1);
        assertThat(testRelativeMaster.getRelativeCode()).isEqualTo(UPDATED_RELATIVE_CODE);
        assertThat(testRelativeMaster.getRelativeName()).isEqualTo(UPDATED_RELATIVE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRelativeMaster() throws Exception {
        int databaseSizeBeforeUpdate = relativeMasterRepository.findAll().size();
        relativeMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelativeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relativeMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relativeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelativeMaster() throws Exception {
        int databaseSizeBeforeUpdate = relativeMasterRepository.findAll().size();
        relativeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelativeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relativeMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelativeMaster() throws Exception {
        int databaseSizeBeforeUpdate = relativeMasterRepository.findAll().size();
        relativeMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelativeMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(relativeMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelativeMaster in the database
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelativeMaster() throws Exception {
        // Initialize the database
        relativeMasterRepository.saveAndFlush(relativeMaster);

        int databaseSizeBeforeDelete = relativeMasterRepository.findAll().size();

        // Delete the relativeMaster
        restRelativeMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, relativeMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RelativeMaster> relativeMasterList = relativeMasterRepository.findAll();
        assertThat(relativeMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
