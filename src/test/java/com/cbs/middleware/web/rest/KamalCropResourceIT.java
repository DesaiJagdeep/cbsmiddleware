package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.domain.KamalCrop;
import com.cbs.middleware.domain.KamalSociety;
import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.repository.KamalCropRepository;
import com.cbs.middleware.service.criteria.KamalCropCriteria;
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
 * Integration tests for the {@link KamalCropResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KamalCropResourceIT {

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kamal-crops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KamalCropRepository kamalCropRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKamalCropMockMvc;

    private KamalCrop kamalCrop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalCrop createEntity(EntityManager em) {
        KamalCrop kamalCrop = new KamalCrop().pacsNumber(DEFAULT_PACS_NUMBER).financialYear(DEFAULT_FINANCIAL_YEAR);
        return kamalCrop;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalCrop createUpdatedEntity(EntityManager em) {
        KamalCrop kamalCrop = new KamalCrop().pacsNumber(UPDATED_PACS_NUMBER).financialYear(UPDATED_FINANCIAL_YEAR);
        return kamalCrop;
    }

    @BeforeEach
    public void initTest() {
        kamalCrop = createEntity(em);
    }

    @Test
    @Transactional
    void createKamalCrop() throws Exception {
        int databaseSizeBeforeCreate = kamalCropRepository.findAll().size();
        // Create the KamalCrop
        restKamalCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalCrop)))
            .andExpect(status().isCreated());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeCreate + 1);
        KamalCrop testKamalCrop = kamalCropList.get(kamalCropList.size() - 1);
        assertThat(testKamalCrop.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKamalCrop.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void createKamalCropWithExistingId() throws Exception {
        // Create the KamalCrop with an existing ID
        kamalCrop.setId(1L);

        int databaseSizeBeforeCreate = kamalCropRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKamalCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalCrop)))
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKamalCrops() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalCrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)));
    }

    @Test
    @Transactional
    void getKamalCrop() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get the kamalCrop
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL_ID, kamalCrop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kamalCrop.getId().intValue()))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER))
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR));
    }

    @Test
    @Transactional
    void getKamalCropsByIdFiltering() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        Long id = kamalCrop.getId();

        defaultKamalCropShouldBeFound("id.equals=" + id);
        defaultKamalCropShouldNotBeFound("id.notEquals=" + id);

        defaultKamalCropShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKamalCropShouldNotBeFound("id.greaterThan=" + id);

        defaultKamalCropShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKamalCropShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber equals to DEFAULT_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.equals=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.equals=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber in DEFAULT_PACS_NUMBER or UPDATED_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.in=" + DEFAULT_PACS_NUMBER + "," + UPDATED_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.in=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber is not null
        defaultKamalCropShouldBeFound("pacsNumber.specified=true");

        // Get all the kamalCropList where pacsNumber is null
        defaultKamalCropShouldNotBeFound("pacsNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber contains DEFAULT_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.contains=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber contains UPDATED_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.contains=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByPacsNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where pacsNumber does not contain DEFAULT_PACS_NUMBER
        defaultKamalCropShouldNotBeFound("pacsNumber.doesNotContain=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalCropList where pacsNumber does not contain UPDATED_PACS_NUMBER
        defaultKamalCropShouldBeFound("pacsNumber.doesNotContain=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultKamalCropShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalCropList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the kamalCropList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear is not null
        defaultKamalCropShouldBeFound("financialYear.specified=true");

        // Get all the kamalCropList where financialYear is null
        defaultKamalCropShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultKamalCropShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalCropList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        // Get all the kamalCropList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultKamalCropShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalCropList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultKamalCropShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalCropsByKamalSocietyIsEqualToSomething() throws Exception {
        KamalSociety kamalSociety;
        if (TestUtil.findAll(em, KamalSociety.class).isEmpty()) {
            kamalCropRepository.saveAndFlush(kamalCrop);
            kamalSociety = KamalSocietyResourceIT.createEntity(em);
        } else {
            kamalSociety = TestUtil.findAll(em, KamalSociety.class).get(0);
        }
        em.persist(kamalSociety);
        em.flush();
        kamalCrop.setKamalSociety(kamalSociety);
        kamalCropRepository.saveAndFlush(kamalCrop);
        Long kamalSocietyId = kamalSociety.getId();

        // Get all the kamalCropList where kamalSociety equals to kamalSocietyId
        defaultKamalCropShouldBeFound("kamalSocietyId.equals=" + kamalSocietyId);

        // Get all the kamalCropList where kamalSociety equals to (kamalSocietyId + 1)
        defaultKamalCropShouldNotBeFound("kamalSocietyId.equals=" + (kamalSocietyId + 1));
    }

    @Test
    @Transactional
    void getAllKamalCropsByFarmerTypeMasterIsEqualToSomething() throws Exception {
        FarmerTypeMaster farmerTypeMaster;
        if (TestUtil.findAll(em, FarmerTypeMaster.class).isEmpty()) {
            kamalCropRepository.saveAndFlush(kamalCrop);
            farmerTypeMaster = FarmerTypeMasterResourceIT.createEntity(em);
        } else {
            farmerTypeMaster = TestUtil.findAll(em, FarmerTypeMaster.class).get(0);
        }
        em.persist(farmerTypeMaster);
        em.flush();
        kamalCrop.setFarmerTypeMaster(farmerTypeMaster);
        kamalCropRepository.saveAndFlush(kamalCrop);
        Long farmerTypeMasterId = farmerTypeMaster.getId();

        // Get all the kamalCropList where farmerTypeMaster equals to farmerTypeMasterId
        defaultKamalCropShouldBeFound("farmerTypeMasterId.equals=" + farmerTypeMasterId);

        // Get all the kamalCropList where farmerTypeMaster equals to (farmerTypeMasterId + 1)
        defaultKamalCropShouldNotBeFound("farmerTypeMasterId.equals=" + (farmerTypeMasterId + 1));
    }

    @Test
    @Transactional
    void getAllKamalCropsBySeasonMasterIsEqualToSomething() throws Exception {
        SeasonMaster seasonMaster;
        if (TestUtil.findAll(em, SeasonMaster.class).isEmpty()) {
            kamalCropRepository.saveAndFlush(kamalCrop);
            seasonMaster = SeasonMasterResourceIT.createEntity(em);
        } else {
            seasonMaster = TestUtil.findAll(em, SeasonMaster.class).get(0);
        }
        em.persist(seasonMaster);
        em.flush();
        kamalCrop.setSeasonMaster(seasonMaster);
        kamalCropRepository.saveAndFlush(kamalCrop);
        Long seasonMasterId = seasonMaster.getId();

        // Get all the kamalCropList where seasonMaster equals to seasonMasterId
        defaultKamalCropShouldBeFound("seasonMasterId.equals=" + seasonMasterId);

        // Get all the kamalCropList where seasonMaster equals to (seasonMasterId + 1)
        defaultKamalCropShouldNotBeFound("seasonMasterId.equals=" + (seasonMasterId + 1));
    }

    @Test
    @Transactional
    void getAllKamalCropsByCropMasterIsEqualToSomething() throws Exception {
        CropMaster cropMaster;
        if (TestUtil.findAll(em, CropMaster.class).isEmpty()) {
            kamalCropRepository.saveAndFlush(kamalCrop);
            cropMaster = CropMasterResourceIT.createEntity(em);
        } else {
            cropMaster = TestUtil.findAll(em, CropMaster.class).get(0);
        }
        em.persist(cropMaster);
        em.flush();
        kamalCrop.setCropMaster(cropMaster);
        kamalCropRepository.saveAndFlush(kamalCrop);
        Long cropMasterId = cropMaster.getId();

        // Get all the kamalCropList where cropMaster equals to cropMasterId
        defaultKamalCropShouldBeFound("cropMasterId.equals=" + cropMasterId);

        // Get all the kamalCropList where cropMaster equals to (cropMasterId + 1)
        defaultKamalCropShouldNotBeFound("cropMasterId.equals=" + (cropMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKamalCropShouldBeFound(String filter) throws Exception {
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalCrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)));

        // Check, that the count call also returns 1
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKamalCropShouldNotBeFound(String filter) throws Exception {
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKamalCropMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKamalCrop() throws Exception {
        // Get the kamalCrop
        restKamalCropMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKamalCrop() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();

        // Update the kamalCrop
        KamalCrop updatedKamalCrop = kamalCropRepository.findById(kamalCrop.getId()).get();
        // Disconnect from session so that the updates on updatedKamalCrop are not directly saved in db
        em.detach(updatedKamalCrop);
        updatedKamalCrop.pacsNumber(UPDATED_PACS_NUMBER).financialYear(UPDATED_FINANCIAL_YEAR);

        restKamalCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKamalCrop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKamalCrop))
            )
            .andExpect(status().isOk());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
        KamalCrop testKamalCrop = kamalCropList.get(kamalCropList.size() - 1);
        assertThat(testKamalCrop.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalCrop.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void putNonExistingKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kamalCrop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalCrop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKamalCropWithPatch() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();

        // Update the kamalCrop using partial update
        KamalCrop partialUpdatedKamalCrop = new KamalCrop();
        partialUpdatedKamalCrop.setId(kamalCrop.getId());

        partialUpdatedKamalCrop.pacsNumber(UPDATED_PACS_NUMBER).financialYear(UPDATED_FINANCIAL_YEAR);

        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalCrop))
            )
            .andExpect(status().isOk());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
        KamalCrop testKamalCrop = kamalCropList.get(kamalCropList.size() - 1);
        assertThat(testKamalCrop.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalCrop.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void fullUpdateKamalCropWithPatch() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();

        // Update the kamalCrop using partial update
        KamalCrop partialUpdatedKamalCrop = new KamalCrop();
        partialUpdatedKamalCrop.setId(kamalCrop.getId());

        partialUpdatedKamalCrop.pacsNumber(UPDATED_PACS_NUMBER).financialYear(UPDATED_FINANCIAL_YEAR);

        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalCrop))
            )
            .andExpect(status().isOk());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
        KamalCrop testKamalCrop = kamalCropList.get(kamalCropList.size() - 1);
        assertThat(testKamalCrop.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalCrop.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void patchNonExistingKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kamalCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKamalCrop() throws Exception {
        int databaseSizeBeforeUpdate = kamalCropRepository.findAll().size();
        kamalCrop.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalCropMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kamalCrop))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalCrop in the database
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKamalCrop() throws Exception {
        // Initialize the database
        kamalCropRepository.saveAndFlush(kamalCrop);

        int databaseSizeBeforeDelete = kamalCropRepository.findAll().size();

        // Delete the kamalCrop
        restKamalCropMockMvc
            .perform(delete(ENTITY_API_URL_ID, kamalCrop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KamalCrop> kamalCropList = kamalCropRepository.findAll();
        assertThat(kamalCropList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
