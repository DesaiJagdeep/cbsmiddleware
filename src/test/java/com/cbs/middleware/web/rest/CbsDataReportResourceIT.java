package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CbsDataReport;
import com.cbs.middleware.repository.CbsDataReportRepository;
import com.cbs.middleware.service.criteria.CbsDataReportCriteria;
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
 * Integration tests for the {@link CbsDataReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbsDataReportResourceIT {

    private static final String DEFAULT_FARMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cbs-data-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CbsDataReportRepository cbsDataReportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbsDataReportMockMvc;

    private CbsDataReport cbsDataReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbsDataReport createEntity(EntityManager em) {
        CbsDataReport cbsDataReport = new CbsDataReport().farmerName(DEFAULT_FARMER_NAME);
        return cbsDataReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbsDataReport createUpdatedEntity(EntityManager em) {
        CbsDataReport cbsDataReport = new CbsDataReport().farmerName(UPDATED_FARMER_NAME);
        return cbsDataReport;
    }

    @BeforeEach
    public void initTest() {
        cbsDataReport = createEntity(em);
    }

    @Test
    @Transactional
    void createCbsDataReport() throws Exception {
        int databaseSizeBeforeCreate = cbsDataReportRepository.findAll().size();
        // Create the CbsDataReport
        restCbsDataReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cbsDataReport)))
            .andExpect(status().isCreated());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeCreate + 1);
        CbsDataReport testCbsDataReport = cbsDataReportList.get(cbsDataReportList.size() - 1);
        assertThat(testCbsDataReport.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
    }

    @Test
    @Transactional
    void createCbsDataReportWithExistingId() throws Exception {
        // Create the CbsDataReport with an existing ID
        cbsDataReport.setId(1L);

        int databaseSizeBeforeCreate = cbsDataReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbsDataReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cbsDataReport)))
            .andExpect(status().isBadRequest());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbsDataReports() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        // Get all the cbsDataReportList
        restCbsDataReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbsDataReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)));
    }

    @Test
    @Transactional
    void getCbsDataReport() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        // Get the cbsDataReport
        restCbsDataReportMockMvc
            .perform(get(ENTITY_API_URL_ID, cbsDataReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbsDataReport.getId().intValue()))
            .andExpect(jsonPath("$.farmerName").value(DEFAULT_FARMER_NAME));
    }

    @Test
    @Transactional
    void getCbsDataReportsByIdFiltering() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        Long id = cbsDataReport.getId();

        defaultCbsDataReportShouldBeFound("id.equals=" + id);
        defaultCbsDataReportShouldNotBeFound("id.notEquals=" + id);

        defaultCbsDataReportShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCbsDataReportShouldNotBeFound("id.greaterThan=" + id);

        defaultCbsDataReportShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCbsDataReportShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCbsDataReportsByFarmerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        // Get all the cbsDataReportList where farmerName equals to DEFAULT_FARMER_NAME
        defaultCbsDataReportShouldBeFound("farmerName.equals=" + DEFAULT_FARMER_NAME);

        // Get all the cbsDataReportList where farmerName equals to UPDATED_FARMER_NAME
        defaultCbsDataReportShouldNotBeFound("farmerName.equals=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllCbsDataReportsByFarmerNameIsInShouldWork() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        // Get all the cbsDataReportList where farmerName in DEFAULT_FARMER_NAME or UPDATED_FARMER_NAME
        defaultCbsDataReportShouldBeFound("farmerName.in=" + DEFAULT_FARMER_NAME + "," + UPDATED_FARMER_NAME);

        // Get all the cbsDataReportList where farmerName equals to UPDATED_FARMER_NAME
        defaultCbsDataReportShouldNotBeFound("farmerName.in=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllCbsDataReportsByFarmerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        // Get all the cbsDataReportList where farmerName is not null
        defaultCbsDataReportShouldBeFound("farmerName.specified=true");

        // Get all the cbsDataReportList where farmerName is null
        defaultCbsDataReportShouldNotBeFound("farmerName.specified=false");
    }

    @Test
    @Transactional
    void getAllCbsDataReportsByFarmerNameContainsSomething() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        // Get all the cbsDataReportList where farmerName contains DEFAULT_FARMER_NAME
        defaultCbsDataReportShouldBeFound("farmerName.contains=" + DEFAULT_FARMER_NAME);

        // Get all the cbsDataReportList where farmerName contains UPDATED_FARMER_NAME
        defaultCbsDataReportShouldNotBeFound("farmerName.contains=" + UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void getAllCbsDataReportsByFarmerNameNotContainsSomething() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        // Get all the cbsDataReportList where farmerName does not contain DEFAULT_FARMER_NAME
        defaultCbsDataReportShouldNotBeFound("farmerName.doesNotContain=" + DEFAULT_FARMER_NAME);

        // Get all the cbsDataReportList where farmerName does not contain UPDATED_FARMER_NAME
        defaultCbsDataReportShouldBeFound("farmerName.doesNotContain=" + UPDATED_FARMER_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCbsDataReportShouldBeFound(String filter) throws Exception {
        restCbsDataReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbsDataReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME)));

        // Check, that the count call also returns 1
        restCbsDataReportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCbsDataReportShouldNotBeFound(String filter) throws Exception {
        restCbsDataReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCbsDataReportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCbsDataReport() throws Exception {
        // Get the cbsDataReport
        restCbsDataReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbsDataReport() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        int databaseSizeBeforeUpdate = cbsDataReportRepository.findAll().size();

        // Update the cbsDataReport
        CbsDataReport updatedCbsDataReport = cbsDataReportRepository.findById(cbsDataReport.getId()).get();
        // Disconnect from session so that the updates on updatedCbsDataReport are not directly saved in db
        em.detach(updatedCbsDataReport);
        updatedCbsDataReport.farmerName(UPDATED_FARMER_NAME);

        restCbsDataReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCbsDataReport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCbsDataReport))
            )
            .andExpect(status().isOk());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeUpdate);
        CbsDataReport testCbsDataReport = cbsDataReportList.get(cbsDataReportList.size() - 1);
        assertThat(testCbsDataReport.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCbsDataReport() throws Exception {
        int databaseSizeBeforeUpdate = cbsDataReportRepository.findAll().size();
        cbsDataReport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbsDataReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbsDataReport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cbsDataReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbsDataReport() throws Exception {
        int databaseSizeBeforeUpdate = cbsDataReportRepository.findAll().size();
        cbsDataReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbsDataReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cbsDataReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbsDataReport() throws Exception {
        int databaseSizeBeforeUpdate = cbsDataReportRepository.findAll().size();
        cbsDataReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbsDataReportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cbsDataReport)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbsDataReportWithPatch() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        int databaseSizeBeforeUpdate = cbsDataReportRepository.findAll().size();

        // Update the cbsDataReport using partial update
        CbsDataReport partialUpdatedCbsDataReport = new CbsDataReport();
        partialUpdatedCbsDataReport.setId(cbsDataReport.getId());

        restCbsDataReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbsDataReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCbsDataReport))
            )
            .andExpect(status().isOk());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeUpdate);
        CbsDataReport testCbsDataReport = cbsDataReportList.get(cbsDataReportList.size() - 1);
        assertThat(testCbsDataReport.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCbsDataReportWithPatch() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        int databaseSizeBeforeUpdate = cbsDataReportRepository.findAll().size();

        // Update the cbsDataReport using partial update
        CbsDataReport partialUpdatedCbsDataReport = new CbsDataReport();
        partialUpdatedCbsDataReport.setId(cbsDataReport.getId());

        partialUpdatedCbsDataReport.farmerName(UPDATED_FARMER_NAME);

        restCbsDataReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbsDataReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCbsDataReport))
            )
            .andExpect(status().isOk());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeUpdate);
        CbsDataReport testCbsDataReport = cbsDataReportList.get(cbsDataReportList.size() - 1);
        assertThat(testCbsDataReport.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCbsDataReport() throws Exception {
        int databaseSizeBeforeUpdate = cbsDataReportRepository.findAll().size();
        cbsDataReport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbsDataReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbsDataReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cbsDataReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbsDataReport() throws Exception {
        int databaseSizeBeforeUpdate = cbsDataReportRepository.findAll().size();
        cbsDataReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbsDataReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cbsDataReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbsDataReport() throws Exception {
        int databaseSizeBeforeUpdate = cbsDataReportRepository.findAll().size();
        cbsDataReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbsDataReportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cbsDataReport))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbsDataReport in the database
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbsDataReport() throws Exception {
        // Initialize the database
        cbsDataReportRepository.saveAndFlush(cbsDataReport);

        int databaseSizeBeforeDelete = cbsDataReportRepository.findAll().size();

        // Delete the cbsDataReport
        restCbsDataReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbsDataReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CbsDataReport> cbsDataReportList = cbsDataReportRepository.findAll();
        assertThat(cbsDataReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
