package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.IsCalculateTemp;
import com.cbs.middleware.repository.IsCalculateTempRepository;
import com.cbs.middleware.service.criteria.IsCalculateTempCriteria;
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
 * Integration tests for the {@link IsCalculateTempResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IsCalculateTempResourceIT {

    private static final Integer DEFAULT_SERIAL_NO = 1;
    private static final Integer UPDATED_SERIAL_NO = 2;
    private static final Integer SMALLER_SERIAL_NO = 1 - 1;

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/is-calculate-temps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IsCalculateTempRepository isCalculateTempRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIsCalculateTempMockMvc;

    private IsCalculateTemp isCalculateTemp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsCalculateTemp createEntity(EntityManager em) {
        IsCalculateTemp isCalculateTemp = new IsCalculateTemp().serialNo(DEFAULT_SERIAL_NO).financialYear(DEFAULT_FINANCIAL_YEAR);
        return isCalculateTemp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsCalculateTemp createUpdatedEntity(EntityManager em) {
        IsCalculateTemp isCalculateTemp = new IsCalculateTemp().serialNo(UPDATED_SERIAL_NO).financialYear(UPDATED_FINANCIAL_YEAR);
        return isCalculateTemp;
    }

    @BeforeEach
    public void initTest() {
        isCalculateTemp = createEntity(em);
    }

    @Test
    @Transactional
    void createIsCalculateTemp() throws Exception {
        int databaseSizeBeforeCreate = isCalculateTempRepository.findAll().size();
        // Create the IsCalculateTemp
        restIsCalculateTempMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isCreated());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeCreate + 1);
        IsCalculateTemp testIsCalculateTemp = isCalculateTempList.get(isCalculateTempList.size() - 1);
        assertThat(testIsCalculateTemp.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testIsCalculateTemp.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void createIsCalculateTempWithExistingId() throws Exception {
        // Create the IsCalculateTemp with an existing ID
        isCalculateTemp.setId(1L);

        int databaseSizeBeforeCreate = isCalculateTempRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsCalculateTempMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIsCalculateTemps() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isCalculateTemp.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)));
    }

    @Test
    @Transactional
    void getIsCalculateTemp() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get the isCalculateTemp
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL_ID, isCalculateTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(isCalculateTemp.getId().intValue()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO))
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR));
    }

    @Test
    @Transactional
    void getIsCalculateTempsByIdFiltering() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        Long id = isCalculateTemp.getId();

        defaultIsCalculateTempShouldBeFound("id.equals=" + id);
        defaultIsCalculateTempShouldNotBeFound("id.notEquals=" + id);

        defaultIsCalculateTempShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIsCalculateTempShouldNotBeFound("id.greaterThan=" + id);

        defaultIsCalculateTempShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIsCalculateTempShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo equals to DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.equals=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo equals to UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.equals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo in DEFAULT_SERIAL_NO or UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.in=" + DEFAULT_SERIAL_NO + "," + UPDATED_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo equals to UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.in=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is not null
        defaultIsCalculateTempShouldBeFound("serialNo.specified=true");

        // Get all the isCalculateTempList where serialNo is null
        defaultIsCalculateTempShouldNotBeFound("serialNo.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is greater than or equal to DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.greaterThanOrEqual=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo is greater than or equal to UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.greaterThanOrEqual=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is less than or equal to DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.lessThanOrEqual=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo is less than or equal to SMALLER_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.lessThanOrEqual=" + SMALLER_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsLessThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is less than DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.lessThan=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo is less than UPDATED_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.lessThan=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsBySerialNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where serialNo is greater than DEFAULT_SERIAL_NO
        defaultIsCalculateTempShouldNotBeFound("serialNo.greaterThan=" + DEFAULT_SERIAL_NO);

        // Get all the isCalculateTempList where serialNo is greater than SMALLER_SERIAL_NO
        defaultIsCalculateTempShouldBeFound("serialNo.greaterThan=" + SMALLER_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultIsCalculateTempShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the isCalculateTempList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the isCalculateTempList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear is not null
        defaultIsCalculateTempShouldBeFound("financialYear.specified=true");

        // Get all the isCalculateTempList where financialYear is null
        defaultIsCalculateTempShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultIsCalculateTempShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the isCalculateTempList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllIsCalculateTempsByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        // Get all the isCalculateTempList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultIsCalculateTempShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the isCalculateTempList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultIsCalculateTempShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIsCalculateTempShouldBeFound(String filter) throws Exception {
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isCalculateTemp.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)));

        // Check, that the count call also returns 1
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIsCalculateTempShouldNotBeFound(String filter) throws Exception {
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIsCalculateTempMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIsCalculateTemp() throws Exception {
        // Get the isCalculateTemp
        restIsCalculateTempMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIsCalculateTemp() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();

        // Update the isCalculateTemp
        IsCalculateTemp updatedIsCalculateTemp = isCalculateTempRepository.findById(isCalculateTemp.getId()).get();
        // Disconnect from session so that the updates on updatedIsCalculateTemp are not directly saved in db
        em.detach(updatedIsCalculateTemp);
        updatedIsCalculateTemp.serialNo(UPDATED_SERIAL_NO).financialYear(UPDATED_FINANCIAL_YEAR);

        restIsCalculateTempMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIsCalculateTemp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIsCalculateTemp))
            )
            .andExpect(status().isOk());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
        IsCalculateTemp testIsCalculateTemp = isCalculateTempList.get(isCalculateTempList.size() - 1);
        assertThat(testIsCalculateTemp.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testIsCalculateTemp.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void putNonExistingIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                put(ENTITY_API_URL_ID, isCalculateTemp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIsCalculateTempWithPatch() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();

        // Update the isCalculateTemp using partial update
        IsCalculateTemp partialUpdatedIsCalculateTemp = new IsCalculateTemp();
        partialUpdatedIsCalculateTemp.setId(isCalculateTemp.getId());

        partialUpdatedIsCalculateTemp.serialNo(UPDATED_SERIAL_NO);

        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsCalculateTemp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsCalculateTemp))
            )
            .andExpect(status().isOk());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
        IsCalculateTemp testIsCalculateTemp = isCalculateTempList.get(isCalculateTempList.size() - 1);
        assertThat(testIsCalculateTemp.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testIsCalculateTemp.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void fullUpdateIsCalculateTempWithPatch() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();

        // Update the isCalculateTemp using partial update
        IsCalculateTemp partialUpdatedIsCalculateTemp = new IsCalculateTemp();
        partialUpdatedIsCalculateTemp.setId(isCalculateTemp.getId());

        partialUpdatedIsCalculateTemp.serialNo(UPDATED_SERIAL_NO).financialYear(UPDATED_FINANCIAL_YEAR);

        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsCalculateTemp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsCalculateTemp))
            )
            .andExpect(status().isOk());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
        IsCalculateTemp testIsCalculateTemp = isCalculateTempList.get(isCalculateTempList.size() - 1);
        assertThat(testIsCalculateTemp.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testIsCalculateTemp.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void patchNonExistingIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, isCalculateTemp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIsCalculateTemp() throws Exception {
        int databaseSizeBeforeUpdate = isCalculateTempRepository.findAll().size();
        isCalculateTemp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsCalculateTempMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isCalculateTemp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IsCalculateTemp in the database
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIsCalculateTemp() throws Exception {
        // Initialize the database
        isCalculateTempRepository.saveAndFlush(isCalculateTemp);

        int databaseSizeBeforeDelete = isCalculateTempRepository.findAll().size();

        // Delete the isCalculateTemp
        restIsCalculateTempMockMvc
            .perform(delete(ENTITY_API_URL_ID, isCalculateTemp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.findAll();
        assertThat(isCalculateTempList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
