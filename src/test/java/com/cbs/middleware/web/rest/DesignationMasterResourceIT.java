package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.DesignationMaster;
import com.cbs.middleware.repository.DesignationMasterRepository;
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
 * Integration tests for the {@link DesignationMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DesignationMasterResourceIT {

    private static final String DEFAULT_DESIGNATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/designation-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DesignationMasterRepository designationMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesignationMasterMockMvc;

    private DesignationMaster designationMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DesignationMaster createEntity(EntityManager em) {
        DesignationMaster designationMaster = new DesignationMaster()
            .designationCode(DEFAULT_DESIGNATION_CODE)
            .designationName(DEFAULT_DESIGNATION_NAME);
        return designationMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DesignationMaster createUpdatedEntity(EntityManager em) {
        DesignationMaster designationMaster = new DesignationMaster()
            .designationCode(UPDATED_DESIGNATION_CODE)
            .designationName(UPDATED_DESIGNATION_NAME);
        return designationMaster;
    }

    @BeforeEach
    public void initTest() {
        designationMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createDesignationMaster() throws Exception {
        int databaseSizeBeforeCreate = designationMasterRepository.findAll().size();
        // Create the DesignationMaster
        restDesignationMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(designationMaster))
            )
            .andExpect(status().isCreated());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeCreate + 1);
        DesignationMaster testDesignationMaster = designationMasterList.get(designationMasterList.size() - 1);
        assertThat(testDesignationMaster.getDesignationCode()).isEqualTo(DEFAULT_DESIGNATION_CODE);
        assertThat(testDesignationMaster.getDesignationName()).isEqualTo(DEFAULT_DESIGNATION_NAME);
    }

    @Test
    @Transactional
    void createDesignationMasterWithExistingId() throws Exception {
        // Create the DesignationMaster with an existing ID
        designationMaster.setId(1L);

        int databaseSizeBeforeCreate = designationMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(designationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDesignationMasters() throws Exception {
        // Initialize the database
        designationMasterRepository.saveAndFlush(designationMaster);

        // Get all the designationMasterList
        restDesignationMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].designationCode").value(hasItem(DEFAULT_DESIGNATION_CODE)))
            .andExpect(jsonPath("$.[*].designationName").value(hasItem(DEFAULT_DESIGNATION_NAME)));
    }

    @Test
    @Transactional
    void getDesignationMaster() throws Exception {
        // Initialize the database
        designationMasterRepository.saveAndFlush(designationMaster);

        // Get the designationMaster
        restDesignationMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, designationMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(designationMaster.getId().intValue()))
            .andExpect(jsonPath("$.designationCode").value(DEFAULT_DESIGNATION_CODE))
            .andExpect(jsonPath("$.designationName").value(DEFAULT_DESIGNATION_NAME));
    }

    @Test
    @Transactional
    void getNonExistingDesignationMaster() throws Exception {
        // Get the designationMaster
        restDesignationMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDesignationMaster() throws Exception {
        // Initialize the database
        designationMasterRepository.saveAndFlush(designationMaster);

        int databaseSizeBeforeUpdate = designationMasterRepository.findAll().size();

        // Update the designationMaster
        DesignationMaster updatedDesignationMaster = designationMasterRepository.findById(designationMaster.getId()).get();
        // Disconnect from session so that the updates on updatedDesignationMaster are not directly saved in db
        em.detach(updatedDesignationMaster);
        updatedDesignationMaster.designationCode(UPDATED_DESIGNATION_CODE).designationName(UPDATED_DESIGNATION_NAME);

        restDesignationMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDesignationMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDesignationMaster))
            )
            .andExpect(status().isOk());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeUpdate);
        DesignationMaster testDesignationMaster = designationMasterList.get(designationMasterList.size() - 1);
        assertThat(testDesignationMaster.getDesignationCode()).isEqualTo(UPDATED_DESIGNATION_CODE);
        assertThat(testDesignationMaster.getDesignationName()).isEqualTo(UPDATED_DESIGNATION_NAME);
    }

    @Test
    @Transactional
    void putNonExistingDesignationMaster() throws Exception {
        int databaseSizeBeforeUpdate = designationMasterRepository.findAll().size();
        designationMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, designationMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDesignationMaster() throws Exception {
        int databaseSizeBeforeUpdate = designationMasterRepository.findAll().size();
        designationMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDesignationMaster() throws Exception {
        int databaseSizeBeforeUpdate = designationMasterRepository.findAll().size();
        designationMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(designationMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDesignationMasterWithPatch() throws Exception {
        // Initialize the database
        designationMasterRepository.saveAndFlush(designationMaster);

        int databaseSizeBeforeUpdate = designationMasterRepository.findAll().size();

        // Update the designationMaster using partial update
        DesignationMaster partialUpdatedDesignationMaster = new DesignationMaster();
        partialUpdatedDesignationMaster.setId(designationMaster.getId());

        restDesignationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignationMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignationMaster))
            )
            .andExpect(status().isOk());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeUpdate);
        DesignationMaster testDesignationMaster = designationMasterList.get(designationMasterList.size() - 1);
        assertThat(testDesignationMaster.getDesignationCode()).isEqualTo(DEFAULT_DESIGNATION_CODE);
        assertThat(testDesignationMaster.getDesignationName()).isEqualTo(DEFAULT_DESIGNATION_NAME);
    }

    @Test
    @Transactional
    void fullUpdateDesignationMasterWithPatch() throws Exception {
        // Initialize the database
        designationMasterRepository.saveAndFlush(designationMaster);

        int databaseSizeBeforeUpdate = designationMasterRepository.findAll().size();

        // Update the designationMaster using partial update
        DesignationMaster partialUpdatedDesignationMaster = new DesignationMaster();
        partialUpdatedDesignationMaster.setId(designationMaster.getId());

        partialUpdatedDesignationMaster.designationCode(UPDATED_DESIGNATION_CODE).designationName(UPDATED_DESIGNATION_NAME);

        restDesignationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignationMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignationMaster))
            )
            .andExpect(status().isOk());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeUpdate);
        DesignationMaster testDesignationMaster = designationMasterList.get(designationMasterList.size() - 1);
        assertThat(testDesignationMaster.getDesignationCode()).isEqualTo(UPDATED_DESIGNATION_CODE);
        assertThat(testDesignationMaster.getDesignationName()).isEqualTo(UPDATED_DESIGNATION_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingDesignationMaster() throws Exception {
        int databaseSizeBeforeUpdate = designationMasterRepository.findAll().size();
        designationMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, designationMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDesignationMaster() throws Exception {
        int databaseSizeBeforeUpdate = designationMasterRepository.findAll().size();
        designationMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDesignationMaster() throws Exception {
        int databaseSizeBeforeUpdate = designationMasterRepository.findAll().size();
        designationMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DesignationMaster in the database
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDesignationMaster() throws Exception {
        // Initialize the database
        designationMasterRepository.saveAndFlush(designationMaster);

        int databaseSizeBeforeDelete = designationMasterRepository.findAll().size();

        // Delete the designationMaster
        restDesignationMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, designationMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DesignationMaster> designationMasterList = designationMasterRepository.findAll();
        assertThat(designationMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
