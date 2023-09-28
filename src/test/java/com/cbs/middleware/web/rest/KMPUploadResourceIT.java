package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KMPUpload;
import com.cbs.middleware.repository.KMPUploadRepository;
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
 * Integration tests for the {@link KMPUploadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KMPUploadResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNIQUE_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_FILE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kmp-uploads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KMPUploadRepository kMPUploadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKMPUploadMockMvc;

    private KMPUpload kMPUpload;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KMPUpload createEntity(EntityManager em) {
        KMPUpload kMPUpload = new KMPUpload().fileName(DEFAULT_FILE_NAME).uniqueFileName(DEFAULT_UNIQUE_FILE_NAME);
        return kMPUpload;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KMPUpload createUpdatedEntity(EntityManager em) {
        KMPUpload kMPUpload = new KMPUpload().fileName(UPDATED_FILE_NAME).uniqueFileName(UPDATED_UNIQUE_FILE_NAME);
        return kMPUpload;
    }

    @BeforeEach
    public void initTest() {
        kMPUpload = createEntity(em);
    }

    @Test
    @Transactional
    void createKMPUpload() throws Exception {
        int databaseSizeBeforeCreate = kMPUploadRepository.findAll().size();
        // Create the KMPUpload
        restKMPUploadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kMPUpload)))
            .andExpect(status().isCreated());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeCreate + 1);
        KMPUpload testKMPUpload = kMPUploadList.get(kMPUploadList.size() - 1);
        assertThat(testKMPUpload.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testKMPUpload.getUniqueFileName()).isEqualTo(DEFAULT_UNIQUE_FILE_NAME);
    }

    @Test
    @Transactional
    void createKMPUploadWithExistingId() throws Exception {
        // Create the KMPUpload with an existing ID
        kMPUpload.setId(1L);

        int databaseSizeBeforeCreate = kMPUploadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKMPUploadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kMPUpload)))
            .andExpect(status().isBadRequest());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKMPUploads() throws Exception {
        // Initialize the database
        kMPUploadRepository.saveAndFlush(kMPUpload);

        // Get all the kMPUploadList
        restKMPUploadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kMPUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].uniqueFileName").value(hasItem(DEFAULT_UNIQUE_FILE_NAME)));
    }

    @Test
    @Transactional
    void getKMPUpload() throws Exception {
        // Initialize the database
        kMPUploadRepository.saveAndFlush(kMPUpload);

        // Get the kMPUpload
        restKMPUploadMockMvc
            .perform(get(ENTITY_API_URL_ID, kMPUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kMPUpload.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.uniqueFileName").value(DEFAULT_UNIQUE_FILE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingKMPUpload() throws Exception {
        // Get the kMPUpload
        restKMPUploadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKMPUpload() throws Exception {
        // Initialize the database
        kMPUploadRepository.saveAndFlush(kMPUpload);

        int databaseSizeBeforeUpdate = kMPUploadRepository.findAll().size();

        // Update the kMPUpload
        KMPUpload updatedKMPUpload = kMPUploadRepository.findById(kMPUpload.getId()).get();
        // Disconnect from session so that the updates on updatedKMPUpload are not directly saved in db
        em.detach(updatedKMPUpload);
        updatedKMPUpload.fileName(UPDATED_FILE_NAME).uniqueFileName(UPDATED_UNIQUE_FILE_NAME);

        restKMPUploadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKMPUpload.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKMPUpload))
            )
            .andExpect(status().isOk());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeUpdate);
        KMPUpload testKMPUpload = kMPUploadList.get(kMPUploadList.size() - 1);
        assertThat(testKMPUpload.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testKMPUpload.getUniqueFileName()).isEqualTo(UPDATED_UNIQUE_FILE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingKMPUpload() throws Exception {
        int databaseSizeBeforeUpdate = kMPUploadRepository.findAll().size();
        kMPUpload.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKMPUploadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kMPUpload.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kMPUpload))
            )
            .andExpect(status().isBadRequest());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKMPUpload() throws Exception {
        int databaseSizeBeforeUpdate = kMPUploadRepository.findAll().size();
        kMPUpload.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKMPUploadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kMPUpload))
            )
            .andExpect(status().isBadRequest());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKMPUpload() throws Exception {
        int databaseSizeBeforeUpdate = kMPUploadRepository.findAll().size();
        kMPUpload.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKMPUploadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kMPUpload)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKMPUploadWithPatch() throws Exception {
        // Initialize the database
        kMPUploadRepository.saveAndFlush(kMPUpload);

        int databaseSizeBeforeUpdate = kMPUploadRepository.findAll().size();

        // Update the kMPUpload using partial update
        KMPUpload partialUpdatedKMPUpload = new KMPUpload();
        partialUpdatedKMPUpload.setId(kMPUpload.getId());

        partialUpdatedKMPUpload.fileName(UPDATED_FILE_NAME).uniqueFileName(UPDATED_UNIQUE_FILE_NAME);

        restKMPUploadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKMPUpload.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKMPUpload))
            )
            .andExpect(status().isOk());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeUpdate);
        KMPUpload testKMPUpload = kMPUploadList.get(kMPUploadList.size() - 1);
        assertThat(testKMPUpload.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testKMPUpload.getUniqueFileName()).isEqualTo(UPDATED_UNIQUE_FILE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateKMPUploadWithPatch() throws Exception {
        // Initialize the database
        kMPUploadRepository.saveAndFlush(kMPUpload);

        int databaseSizeBeforeUpdate = kMPUploadRepository.findAll().size();

        // Update the kMPUpload using partial update
        KMPUpload partialUpdatedKMPUpload = new KMPUpload();
        partialUpdatedKMPUpload.setId(kMPUpload.getId());

        partialUpdatedKMPUpload.fileName(UPDATED_FILE_NAME).uniqueFileName(UPDATED_UNIQUE_FILE_NAME);

        restKMPUploadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKMPUpload.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKMPUpload))
            )
            .andExpect(status().isOk());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeUpdate);
        KMPUpload testKMPUpload = kMPUploadList.get(kMPUploadList.size() - 1);
        assertThat(testKMPUpload.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testKMPUpload.getUniqueFileName()).isEqualTo(UPDATED_UNIQUE_FILE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingKMPUpload() throws Exception {
        int databaseSizeBeforeUpdate = kMPUploadRepository.findAll().size();
        kMPUpload.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKMPUploadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kMPUpload.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kMPUpload))
            )
            .andExpect(status().isBadRequest());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKMPUpload() throws Exception {
        int databaseSizeBeforeUpdate = kMPUploadRepository.findAll().size();
        kMPUpload.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKMPUploadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kMPUpload))
            )
            .andExpect(status().isBadRequest());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKMPUpload() throws Exception {
        int databaseSizeBeforeUpdate = kMPUploadRepository.findAll().size();
        kMPUpload.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKMPUploadMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kMPUpload))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KMPUpload in the database
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKMPUpload() throws Exception {
        // Initialize the database
        kMPUploadRepository.saveAndFlush(kMPUpload);

        int databaseSizeBeforeDelete = kMPUploadRepository.findAll().size();

        // Delete the kMPUpload
        restKMPUploadMockMvc
            .perform(delete(ENTITY_API_URL_ID, kMPUpload.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KMPUpload> kMPUploadList = kMPUploadRepository.findAll();
        assertThat(kMPUploadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
