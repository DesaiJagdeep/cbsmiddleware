package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CropHangam;
import com.cbs.middleware.repository.CropHangamRepository;
import com.cbs.middleware.service.criteria.CropHangamCriteria;
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
 * Integration tests for the {@link CropHangamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CropHangamResourceIT {

    private static final String DEFAULT_HANGAM = "AAAAAAAAAA";
    private static final String UPDATED_HANGAM = "BBBBBBBBBB";

    private static final String DEFAULT_HANGAM_MR = "AAAAAAAAAA";
    private static final String UPDATED_HANGAM_MR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/crop-hangams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CropHangamRepository cropHangamRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCropHangamMockMvc;

    private CropHangam cropHangam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CropHangam createEntity(EntityManager em) {
        CropHangam cropHangam = new CropHangam().hangam(DEFAULT_HANGAM).hangamMr(DEFAULT_HANGAM_MR);
        return cropHangam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CropHangam createUpdatedEntity(EntityManager em) {
        CropHangam cropHangam = new CropHangam().hangam(UPDATED_HANGAM).hangamMr(UPDATED_HANGAM_MR);
        return cropHangam;
    }

    @BeforeEach
    public void initTest() {
        cropHangam = createEntity(em);
    }

    @Test
    @Transactional
    void createCropHangam() throws Exception {
        int databaseSizeBeforeCreate = cropHangamRepository.findAll().size();
        // Create the CropHangam
        restCropHangamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropHangam)))
            .andExpect(status().isCreated());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeCreate + 1);
        CropHangam testCropHangam = cropHangamList.get(cropHangamList.size() - 1);
        assertThat(testCropHangam.getHangam()).isEqualTo(DEFAULT_HANGAM);
        assertThat(testCropHangam.getHangamMr()).isEqualTo(DEFAULT_HANGAM_MR);
    }

    @Test
    @Transactional
    void createCropHangamWithExistingId() throws Exception {
        // Create the CropHangam with an existing ID
        cropHangam.setId(1L);

        int databaseSizeBeforeCreate = cropHangamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCropHangamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropHangam)))
            .andExpect(status().isBadRequest());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCropHangams() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList
        restCropHangamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cropHangam.getId().intValue())))
            .andExpect(jsonPath("$.[*].hangam").value(hasItem(DEFAULT_HANGAM)))
            .andExpect(jsonPath("$.[*].hangamMr").value(hasItem(DEFAULT_HANGAM_MR)));
    }

    @Test
    @Transactional
    void getCropHangam() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get the cropHangam
        restCropHangamMockMvc
            .perform(get(ENTITY_API_URL_ID, cropHangam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cropHangam.getId().intValue()))
            .andExpect(jsonPath("$.hangam").value(DEFAULT_HANGAM))
            .andExpect(jsonPath("$.hangamMr").value(DEFAULT_HANGAM_MR));
    }

    @Test
    @Transactional
    void getCropHangamsByIdFiltering() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        Long id = cropHangam.getId();

        defaultCropHangamShouldBeFound("id.equals=" + id);
        defaultCropHangamShouldNotBeFound("id.notEquals=" + id);

        defaultCropHangamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCropHangamShouldNotBeFound("id.greaterThan=" + id);

        defaultCropHangamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCropHangamShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamIsEqualToSomething() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangam equals to DEFAULT_HANGAM
        defaultCropHangamShouldBeFound("hangam.equals=" + DEFAULT_HANGAM);

        // Get all the cropHangamList where hangam equals to UPDATED_HANGAM
        defaultCropHangamShouldNotBeFound("hangam.equals=" + UPDATED_HANGAM);
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamIsInShouldWork() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangam in DEFAULT_HANGAM or UPDATED_HANGAM
        defaultCropHangamShouldBeFound("hangam.in=" + DEFAULT_HANGAM + "," + UPDATED_HANGAM);

        // Get all the cropHangamList where hangam equals to UPDATED_HANGAM
        defaultCropHangamShouldNotBeFound("hangam.in=" + UPDATED_HANGAM);
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamIsNullOrNotNull() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangam is not null
        defaultCropHangamShouldBeFound("hangam.specified=true");

        // Get all the cropHangamList where hangam is null
        defaultCropHangamShouldNotBeFound("hangam.specified=false");
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamContainsSomething() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangam contains DEFAULT_HANGAM
        defaultCropHangamShouldBeFound("hangam.contains=" + DEFAULT_HANGAM);

        // Get all the cropHangamList where hangam contains UPDATED_HANGAM
        defaultCropHangamShouldNotBeFound("hangam.contains=" + UPDATED_HANGAM);
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamNotContainsSomething() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangam does not contain DEFAULT_HANGAM
        defaultCropHangamShouldNotBeFound("hangam.doesNotContain=" + DEFAULT_HANGAM);

        // Get all the cropHangamList where hangam does not contain UPDATED_HANGAM
        defaultCropHangamShouldBeFound("hangam.doesNotContain=" + UPDATED_HANGAM);
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamMrIsEqualToSomething() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangamMr equals to DEFAULT_HANGAM_MR
        defaultCropHangamShouldBeFound("hangamMr.equals=" + DEFAULT_HANGAM_MR);

        // Get all the cropHangamList where hangamMr equals to UPDATED_HANGAM_MR
        defaultCropHangamShouldNotBeFound("hangamMr.equals=" + UPDATED_HANGAM_MR);
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamMrIsInShouldWork() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangamMr in DEFAULT_HANGAM_MR or UPDATED_HANGAM_MR
        defaultCropHangamShouldBeFound("hangamMr.in=" + DEFAULT_HANGAM_MR + "," + UPDATED_HANGAM_MR);

        // Get all the cropHangamList where hangamMr equals to UPDATED_HANGAM_MR
        defaultCropHangamShouldNotBeFound("hangamMr.in=" + UPDATED_HANGAM_MR);
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangamMr is not null
        defaultCropHangamShouldBeFound("hangamMr.specified=true");

        // Get all the cropHangamList where hangamMr is null
        defaultCropHangamShouldNotBeFound("hangamMr.specified=false");
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamMrContainsSomething() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangamMr contains DEFAULT_HANGAM_MR
        defaultCropHangamShouldBeFound("hangamMr.contains=" + DEFAULT_HANGAM_MR);

        // Get all the cropHangamList where hangamMr contains UPDATED_HANGAM_MR
        defaultCropHangamShouldNotBeFound("hangamMr.contains=" + UPDATED_HANGAM_MR);
    }

    @Test
    @Transactional
    void getAllCropHangamsByHangamMrNotContainsSomething() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        // Get all the cropHangamList where hangamMr does not contain DEFAULT_HANGAM_MR
        defaultCropHangamShouldNotBeFound("hangamMr.doesNotContain=" + DEFAULT_HANGAM_MR);

        // Get all the cropHangamList where hangamMr does not contain UPDATED_HANGAM_MR
        defaultCropHangamShouldBeFound("hangamMr.doesNotContain=" + UPDATED_HANGAM_MR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCropHangamShouldBeFound(String filter) throws Exception {
        restCropHangamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cropHangam.getId().intValue())))
            .andExpect(jsonPath("$.[*].hangam").value(hasItem(DEFAULT_HANGAM)))
            .andExpect(jsonPath("$.[*].hangamMr").value(hasItem(DEFAULT_HANGAM_MR)));

        // Check, that the count call also returns 1
        restCropHangamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCropHangamShouldNotBeFound(String filter) throws Exception {
        restCropHangamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCropHangamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCropHangam() throws Exception {
        // Get the cropHangam
        restCropHangamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCropHangam() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        int databaseSizeBeforeUpdate = cropHangamRepository.findAll().size();

        // Update the cropHangam
        CropHangam updatedCropHangam = cropHangamRepository.findById(cropHangam.getId()).get();
        // Disconnect from session so that the updates on updatedCropHangam are not directly saved in db
        em.detach(updatedCropHangam);
        updatedCropHangam.hangam(UPDATED_HANGAM).hangamMr(UPDATED_HANGAM_MR);

        restCropHangamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCropHangam.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCropHangam))
            )
            .andExpect(status().isOk());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeUpdate);
        CropHangam testCropHangam = cropHangamList.get(cropHangamList.size() - 1);
        assertThat(testCropHangam.getHangam()).isEqualTo(UPDATED_HANGAM);
        assertThat(testCropHangam.getHangamMr()).isEqualTo(UPDATED_HANGAM_MR);
    }

    @Test
    @Transactional
    void putNonExistingCropHangam() throws Exception {
        int databaseSizeBeforeUpdate = cropHangamRepository.findAll().size();
        cropHangam.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropHangamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cropHangam.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cropHangam))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCropHangam() throws Exception {
        int databaseSizeBeforeUpdate = cropHangamRepository.findAll().size();
        cropHangam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropHangamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cropHangam))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCropHangam() throws Exception {
        int databaseSizeBeforeUpdate = cropHangamRepository.findAll().size();
        cropHangam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropHangamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cropHangam)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCropHangamWithPatch() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        int databaseSizeBeforeUpdate = cropHangamRepository.findAll().size();

        // Update the cropHangam using partial update
        CropHangam partialUpdatedCropHangam = new CropHangam();
        partialUpdatedCropHangam.setId(cropHangam.getId());

        partialUpdatedCropHangam.hangam(UPDATED_HANGAM);

        restCropHangamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCropHangam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCropHangam))
            )
            .andExpect(status().isOk());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeUpdate);
        CropHangam testCropHangam = cropHangamList.get(cropHangamList.size() - 1);
        assertThat(testCropHangam.getHangam()).isEqualTo(UPDATED_HANGAM);
        assertThat(testCropHangam.getHangamMr()).isEqualTo(DEFAULT_HANGAM_MR);
    }

    @Test
    @Transactional
    void fullUpdateCropHangamWithPatch() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        int databaseSizeBeforeUpdate = cropHangamRepository.findAll().size();

        // Update the cropHangam using partial update
        CropHangam partialUpdatedCropHangam = new CropHangam();
        partialUpdatedCropHangam.setId(cropHangam.getId());

        partialUpdatedCropHangam.hangam(UPDATED_HANGAM).hangamMr(UPDATED_HANGAM_MR);

        restCropHangamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCropHangam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCropHangam))
            )
            .andExpect(status().isOk());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeUpdate);
        CropHangam testCropHangam = cropHangamList.get(cropHangamList.size() - 1);
        assertThat(testCropHangam.getHangam()).isEqualTo(UPDATED_HANGAM);
        assertThat(testCropHangam.getHangamMr()).isEqualTo(UPDATED_HANGAM_MR);
    }

    @Test
    @Transactional
    void patchNonExistingCropHangam() throws Exception {
        int databaseSizeBeforeUpdate = cropHangamRepository.findAll().size();
        cropHangam.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropHangamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cropHangam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cropHangam))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCropHangam() throws Exception {
        int databaseSizeBeforeUpdate = cropHangamRepository.findAll().size();
        cropHangam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropHangamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cropHangam))
            )
            .andExpect(status().isBadRequest());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCropHangam() throws Exception {
        int databaseSizeBeforeUpdate = cropHangamRepository.findAll().size();
        cropHangam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropHangamMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cropHangam))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CropHangam in the database
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCropHangam() throws Exception {
        // Initialize the database
        cropHangamRepository.saveAndFlush(cropHangam);

        int databaseSizeBeforeDelete = cropHangamRepository.findAll().size();

        // Delete the cropHangam
        restCropHangamMockMvc
            .perform(delete(ENTITY_API_URL_ID, cropHangam.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CropHangam> cropHangamList = cropHangamRepository.findAll();
        assertThat(cropHangamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
