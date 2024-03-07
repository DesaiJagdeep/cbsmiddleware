package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KmMagani;
import com.cbs.middleware.domain.KmMaganiCrop;
import com.cbs.middleware.repository.KmMaganiCropRepository;
import com.cbs.middleware.service.criteria.KmMaganiCropCriteria;
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
 * Integration tests for the {@link KmMaganiCropResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KmMaganiCropResourceIT {

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/km-magani-crops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KmMaganiCropRepository kmMaganiCropRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKmMaganiCropMockMvc;

    private KmMaganiCrop kmMaganiCrop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmMaganiCrop createEntity(EntityManager em) {
        KmMaganiCrop kmMaganiCrop = new KmMaganiCrop().cropName(DEFAULT_CROP_NAME);
        return kmMaganiCrop;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KmMaganiCrop createUpdatedEntity(EntityManager em) {
        KmMaganiCrop kmMaganiCrop = new KmMaganiCrop().cropName(UPDATED_CROP_NAME);
        return kmMaganiCrop;
    }

    @BeforeEach
    public void initTest() {
        kmMaganiCrop = createEntity(em);
    }

    @Test
    @Transactional
    void createKmMaganiCrop() throws Exception {
        int databaseSizeBeforeCreate = kmMaganiCropRepository.findAll().size();
        // Create the KmMaganiCrop
        restKmMaganiCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop)))
            .andExpect(status().isCreated());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeCreate + 1);
        KmMaganiCrop testKmMaganiCrop = kmMaganiCropList.get(kmMaganiCropList.size() - 1);
        assertThat(testKmMaganiCrop.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
    }

    @Test
    @Transactional
    void createKmMaganiCropWithExistingId() throws Exception {
        // Create the KmMaganiCrop with an existing ID
        kmMaganiCrop.setId(1L);

        int databaseSizeBeforeCreate = kmMaganiCropRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKmMaganiCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop)))
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKmMaganiCrops() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMaganiCrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)));
    }

    @Test
    @Transactional
    void getKmMaganiCrop() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get the kmMaganiCrop
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL_ID, kmMaganiCrop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kmMaganiCrop.getId().intValue()))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME));
    }

    @Test
    @Transactional
    void getKmMaganiCropsByIdFiltering() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        Long id = kmMaganiCrop.getId();

        defaultKmMaganiCropShouldBeFound("id.equals=" + id);
        defaultKmMaganiCropShouldNotBeFound("id.notEquals=" + id);

        defaultKmMaganiCropShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKmMaganiCropShouldNotBeFound("id.greaterThan=" + id);

        defaultKmMaganiCropShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKmMaganiCropShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName equals to DEFAULT_CROP_NAME
        defaultKmMaganiCropShouldBeFound("cropName.equals=" + DEFAULT_CROP_NAME);

        // Get all the kmMaganiCropList where cropName equals to UPDATED_CROP_NAME
        defaultKmMaganiCropShouldNotBeFound("cropName.equals=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameIsInShouldWork() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName in DEFAULT_CROP_NAME or UPDATED_CROP_NAME
        defaultKmMaganiCropShouldBeFound("cropName.in=" + DEFAULT_CROP_NAME + "," + UPDATED_CROP_NAME);

        // Get all the kmMaganiCropList where cropName equals to UPDATED_CROP_NAME
        defaultKmMaganiCropShouldNotBeFound("cropName.in=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName is not null
        defaultKmMaganiCropShouldBeFound("cropName.specified=true");

        // Get all the kmMaganiCropList where cropName is null
        defaultKmMaganiCropShouldNotBeFound("cropName.specified=false");
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName contains DEFAULT_CROP_NAME
        defaultKmMaganiCropShouldBeFound("cropName.contains=" + DEFAULT_CROP_NAME);

        // Get all the kmMaganiCropList where cropName contains UPDATED_CROP_NAME
        defaultKmMaganiCropShouldNotBeFound("cropName.contains=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByCropNameNotContainsSomething() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        // Get all the kmMaganiCropList where cropName does not contain DEFAULT_CROP_NAME
        defaultKmMaganiCropShouldNotBeFound("cropName.doesNotContain=" + DEFAULT_CROP_NAME);

        // Get all the kmMaganiCropList where cropName does not contain UPDATED_CROP_NAME
        defaultKmMaganiCropShouldBeFound("cropName.doesNotContain=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllKmMaganiCropsByKmMaganiIsEqualToSomething() throws Exception {
        KmMagani kmMagani;
        if (TestUtil.findAll(em, KmMagani.class).isEmpty()) {
            kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);
            kmMagani = KmMaganiResourceIT.createEntity(em);
        } else {
            kmMagani = TestUtil.findAll(em, KmMagani.class).get(0);
        }
        em.persist(kmMagani);
        em.flush();
        kmMaganiCrop.setKmMagani(kmMagani);
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);
        Long kmMaganiId = kmMagani.getId();

        // Get all the kmMaganiCropList where kmMagani equals to kmMaganiId
        defaultKmMaganiCropShouldBeFound("kmMaganiId.equals=" + kmMaganiId);

        // Get all the kmMaganiCropList where kmMagani equals to (kmMaganiId + 1)
        defaultKmMaganiCropShouldNotBeFound("kmMaganiId.equals=" + (kmMaganiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKmMaganiCropShouldBeFound(String filter) throws Exception {
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kmMaganiCrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)));

        // Check, that the count call also returns 1
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKmMaganiCropShouldNotBeFound(String filter) throws Exception {
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKmMaganiCropMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKmMaganiCrop() throws Exception {
        // Get the kmMaganiCrop
        restKmMaganiCropMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKmMaganiCrop() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();

        // Update the kmMaganiCrop
        KmMaganiCrop updatedKmMaganiCrop = kmMaganiCropRepository.findById(kmMaganiCrop.getId()).get();
        // Disconnect from session so that the updates on updatedKmMaganiCrop are not directly saved in db
        em.detach(updatedKmMaganiCrop);
        updatedKmMaganiCrop.cropName(UPDATED_CROP_NAME);

        restKmMaganiCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKmMaganiCrop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKmMaganiCrop))
            )
            .andExpect(status().isOk());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
        KmMaganiCrop testKmMaganiCrop = kmMaganiCropList.get(kmMaganiCropList.size() - 1);
        assertThat(testKmMaganiCrop.getCropName()).isEqualTo(UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void putNonExistingKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kmMaganiCrop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKmMaganiCropWithPatch() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();

        // Update the kmMaganiCrop using partial update
        KmMaganiCrop partialUpdatedKmMaganiCrop = new KmMaganiCrop();
        partialUpdatedKmMaganiCrop.setId(kmMaganiCrop.getId());

        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmMaganiCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmMaganiCrop))
            )
            .andExpect(status().isOk());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
        KmMaganiCrop testKmMaganiCrop = kmMaganiCropList.get(kmMaganiCropList.size() - 1);
        assertThat(testKmMaganiCrop.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
    }

    @Test
    @Transactional
    void fullUpdateKmMaganiCropWithPatch() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();

        // Update the kmMaganiCrop using partial update
        KmMaganiCrop partialUpdatedKmMaganiCrop = new KmMaganiCrop();
        partialUpdatedKmMaganiCrop.setId(kmMaganiCrop.getId());

        partialUpdatedKmMaganiCrop.cropName(UPDATED_CROP_NAME);

        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKmMaganiCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKmMaganiCrop))
            )
            .andExpect(status().isOk());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
        KmMaganiCrop testKmMaganiCrop = kmMaganiCropList.get(kmMaganiCropList.size() - 1);
        assertThat(testKmMaganiCrop.getCropName()).isEqualTo(UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kmMaganiCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKmMaganiCrop() throws Exception {
        int databaseSizeBeforeUpdate = kmMaganiCropRepository.findAll().size();
        kmMaganiCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKmMaganiCropMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kmMaganiCrop))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KmMaganiCrop in the database
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKmMaganiCrop() throws Exception {
        // Initialize the database
        kmMaganiCropRepository.saveAndFlush(kmMaganiCrop);

        int databaseSizeBeforeDelete = kmMaganiCropRepository.findAll().size();

        // Delete the kmMaganiCrop
        restKmMaganiCropMockMvc
            .perform(delete(ENTITY_API_URL_ID, kmMaganiCrop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KmMaganiCrop> kmMaganiCropList = kmMaganiCropRepository.findAll();
        assertThat(kmMaganiCropList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
