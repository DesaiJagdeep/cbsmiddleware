package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KamalSociety;
import com.cbs.middleware.repository.KamalSocietyRepository;
import com.cbs.middleware.service.criteria.KamalSocietyCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link KamalSocietyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KamalSocietyResourceIT {

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_KM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/kamal-societies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KamalSocietyRepository kamalSocietyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKamalSocietyMockMvc;

    private KamalSociety kamalSociety;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalSociety createEntity(EntityManager em) {
        KamalSociety kamalSociety = new KamalSociety().financialYear(DEFAULT_FINANCIAL_YEAR).kmDate(DEFAULT_KM_DATE);
        return kamalSociety;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalSociety createUpdatedEntity(EntityManager em) {
        KamalSociety kamalSociety = new KamalSociety().financialYear(UPDATED_FINANCIAL_YEAR).kmDate(UPDATED_KM_DATE);
        return kamalSociety;
    }

    @BeforeEach
    public void initTest() {
        kamalSociety = createEntity(em);
    }

    @Test
    @Transactional
    void createKamalSociety() throws Exception {
        int databaseSizeBeforeCreate = kamalSocietyRepository.findAll().size();
        // Create the KamalSociety
        restKamalSocietyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalSociety)))
            .andExpect(status().isCreated());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeCreate + 1);
        KamalSociety testKamalSociety = kamalSocietyList.get(kamalSocietyList.size() - 1);
        assertThat(testKamalSociety.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testKamalSociety.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
    }

    @Test
    @Transactional
    void createKamalSocietyWithExistingId() throws Exception {
        // Create the KamalSociety with an existing ID
        kamalSociety.setId(1L);

        int databaseSizeBeforeCreate = kamalSocietyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKamalSocietyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalSociety)))
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFinancialYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = kamalSocietyRepository.findAll().size();
        // set the field null
        kamalSociety.setFinancialYear(null);

        // Create the KamalSociety, which fails.

        restKamalSocietyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalSociety)))
            .andExpect(status().isBadRequest());

        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKamalSocieties() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalSociety.getId().intValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())));
    }

    @Test
    @Transactional
    void getKamalSociety() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get the kamalSociety
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL_ID, kamalSociety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kamalSociety.getId().intValue()))
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.kmDate").value(DEFAULT_KM_DATE.toString()));
    }

    @Test
    @Transactional
    void getKamalSocietiesByIdFiltering() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        Long id = kamalSociety.getId();

        defaultKamalSocietyShouldBeFound("id.equals=" + id);
        defaultKamalSocietyShouldNotBeFound("id.notEquals=" + id);

        defaultKamalSocietyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKamalSocietyShouldNotBeFound("id.greaterThan=" + id);

        defaultKamalSocietyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKamalSocietyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultKamalSocietyShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalSocietyList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the kamalSocietyList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear is not null
        defaultKamalSocietyShouldBeFound("financialYear.specified=true");

        // Get all the kamalSocietyList where financialYear is null
        defaultKamalSocietyShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultKamalSocietyShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalSocietyList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultKamalSocietyShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalSocietyList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDate equals to DEFAULT_KM_DATE
        defaultKamalSocietyShouldBeFound("kmDate.equals=" + DEFAULT_KM_DATE);

        // Get all the kamalSocietyList where kmDate equals to UPDATED_KM_DATE
        defaultKamalSocietyShouldNotBeFound("kmDate.equals=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDate in DEFAULT_KM_DATE or UPDATED_KM_DATE
        defaultKamalSocietyShouldBeFound("kmDate.in=" + DEFAULT_KM_DATE + "," + UPDATED_KM_DATE);

        // Get all the kamalSocietyList where kmDate equals to UPDATED_KM_DATE
        defaultKamalSocietyShouldNotBeFound("kmDate.in=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDate is not null
        defaultKamalSocietyShouldBeFound("kmDate.specified=true");

        // Get all the kamalSocietyList where kmDate is null
        defaultKamalSocietyShouldNotBeFound("kmDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKamalSocietyShouldBeFound(String filter) throws Exception {
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalSociety.getId().intValue())))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())));

        // Check, that the count call also returns 1
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKamalSocietyShouldNotBeFound(String filter) throws Exception {
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKamalSociety() throws Exception {
        // Get the kamalSociety
        restKamalSocietyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKamalSociety() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();

        // Update the kamalSociety
        KamalSociety updatedKamalSociety = kamalSocietyRepository.findById(kamalSociety.getId()).get();
        // Disconnect from session so that the updates on updatedKamalSociety are not directly saved in db
        em.detach(updatedKamalSociety);
        updatedKamalSociety.financialYear(UPDATED_FINANCIAL_YEAR).kmDate(UPDATED_KM_DATE);

        restKamalSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKamalSociety.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKamalSociety))
            )
            .andExpect(status().isOk());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
        KamalSociety testKamalSociety = kamalSocietyList.get(kamalSocietyList.size() - 1);
        assertThat(testKamalSociety.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testKamalSociety.getKmDate()).isEqualTo(UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void putNonExistingKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kamalSociety.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalSociety)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKamalSocietyWithPatch() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();

        // Update the kamalSociety using partial update
        KamalSociety partialUpdatedKamalSociety = new KamalSociety();
        partialUpdatedKamalSociety.setId(kamalSociety.getId());

        partialUpdatedKamalSociety.kmDate(UPDATED_KM_DATE);

        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalSociety.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalSociety))
            )
            .andExpect(status().isOk());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
        KamalSociety testKamalSociety = kamalSocietyList.get(kamalSocietyList.size() - 1);
        assertThat(testKamalSociety.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testKamalSociety.getKmDate()).isEqualTo(UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void fullUpdateKamalSocietyWithPatch() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();

        // Update the kamalSociety using partial update
        KamalSociety partialUpdatedKamalSociety = new KamalSociety();
        partialUpdatedKamalSociety.setId(kamalSociety.getId());

        partialUpdatedKamalSociety.financialYear(UPDATED_FINANCIAL_YEAR).kmDate(UPDATED_KM_DATE);

        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalSociety.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalSociety))
            )
            .andExpect(status().isOk());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
        KamalSociety testKamalSociety = kamalSocietyList.get(kamalSocietyList.size() - 1);
        assertThat(testKamalSociety.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testKamalSociety.getKmDate()).isEqualTo(UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kamalSociety.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKamalSociety() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        int databaseSizeBeforeDelete = kamalSocietyRepository.findAll().size();

        // Delete the kamalSociety
        restKamalSocietyMockMvc
            .perform(delete(ENTITY_API_URL_ID, kamalSociety.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
