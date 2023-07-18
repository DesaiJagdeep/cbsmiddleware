package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.ActivityType;
import com.cbs.middleware.repository.ActivityTypeRepository;
import com.cbs.middleware.service.criteria.ActivityTypeCriteria;
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
 * Integration tests for the {@link ActivityTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivityTypeResourceIT {

    private static final String DEFAULT_ACTIVITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTIVITY_TYPE_CODE = 1;
    private static final Integer UPDATED_ACTIVITY_TYPE_CODE = 2;
    private static final Integer SMALLER_ACTIVITY_TYPE_CODE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/activity-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityTypeMockMvc;

    private ActivityType activityType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityType createEntity(EntityManager em) {
        ActivityType activityType = new ActivityType().activityType(DEFAULT_ACTIVITY_TYPE).activityTypeCode(DEFAULT_ACTIVITY_TYPE_CODE);
        return activityType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityType createUpdatedEntity(EntityManager em) {
        ActivityType activityType = new ActivityType().activityType(UPDATED_ACTIVITY_TYPE).activityTypeCode(UPDATED_ACTIVITY_TYPE_CODE);
        return activityType;
    }

    @BeforeEach
    public void initTest() {
        activityType = createEntity(em);
    }

    @Test
    @Transactional
    void createActivityType() throws Exception {
        int databaseSizeBeforeCreate = activityTypeRepository.findAll().size();
        // Create the ActivityType
        restActivityTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityType)))
            .andExpect(status().isCreated());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityType testActivityType = activityTypeList.get(activityTypeList.size() - 1);
        assertThat(testActivityType.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testActivityType.getActivityTypeCode()).isEqualTo(DEFAULT_ACTIVITY_TYPE_CODE);
    }

    @Test
    @Transactional
    void createActivityTypeWithExistingId() throws Exception {
        // Create the ActivityType with an existing ID
        activityType.setId(1L);

        int databaseSizeBeforeCreate = activityTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityType)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivityTypes() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE)))
            .andExpect(jsonPath("$.[*].activityTypeCode").value(hasItem(DEFAULT_ACTIVITY_TYPE_CODE)));
    }

    @Test
    @Transactional
    void getActivityType() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get the activityType
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, activityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityType.getId().intValue()))
            .andExpect(jsonPath("$.activityType").value(DEFAULT_ACTIVITY_TYPE))
            .andExpect(jsonPath("$.activityTypeCode").value(DEFAULT_ACTIVITY_TYPE_CODE));
    }

    @Test
    @Transactional
    void getActivityTypesByIdFiltering() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        Long id = activityType.getId();

        defaultActivityTypeShouldBeFound("id.equals=" + id);
        defaultActivityTypeShouldNotBeFound("id.notEquals=" + id);

        defaultActivityTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActivityTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultActivityTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActivityTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType equals to DEFAULT_ACTIVITY_TYPE
        defaultActivityTypeShouldBeFound("activityType.equals=" + DEFAULT_ACTIVITY_TYPE);

        // Get all the activityTypeList where activityType equals to UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldNotBeFound("activityType.equals=" + UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType in DEFAULT_ACTIVITY_TYPE or UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldBeFound("activityType.in=" + DEFAULT_ACTIVITY_TYPE + "," + UPDATED_ACTIVITY_TYPE);

        // Get all the activityTypeList where activityType equals to UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldNotBeFound("activityType.in=" + UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType is not null
        defaultActivityTypeShouldBeFound("activityType.specified=true");

        // Get all the activityTypeList where activityType is null
        defaultActivityTypeShouldNotBeFound("activityType.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeContainsSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType contains DEFAULT_ACTIVITY_TYPE
        defaultActivityTypeShouldBeFound("activityType.contains=" + DEFAULT_ACTIVITY_TYPE);

        // Get all the activityTypeList where activityType contains UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldNotBeFound("activityType.contains=" + UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeNotContainsSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityType does not contain DEFAULT_ACTIVITY_TYPE
        defaultActivityTypeShouldNotBeFound("activityType.doesNotContain=" + DEFAULT_ACTIVITY_TYPE);

        // Get all the activityTypeList where activityType does not contain UPDATED_ACTIVITY_TYPE
        defaultActivityTypeShouldBeFound("activityType.doesNotContain=" + UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeCode equals to DEFAULT_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldBeFound("activityTypeCode.equals=" + DEFAULT_ACTIVITY_TYPE_CODE);

        // Get all the activityTypeList where activityTypeCode equals to UPDATED_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldNotBeFound("activityTypeCode.equals=" + UPDATED_ACTIVITY_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeCode in DEFAULT_ACTIVITY_TYPE_CODE or UPDATED_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldBeFound("activityTypeCode.in=" + DEFAULT_ACTIVITY_TYPE_CODE + "," + UPDATED_ACTIVITY_TYPE_CODE);

        // Get all the activityTypeList where activityTypeCode equals to UPDATED_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldNotBeFound("activityTypeCode.in=" + UPDATED_ACTIVITY_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeCode is not null
        defaultActivityTypeShouldBeFound("activityTypeCode.specified=true");

        // Get all the activityTypeList where activityTypeCode is null
        defaultActivityTypeShouldNotBeFound("activityTypeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeCode is greater than or equal to DEFAULT_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldBeFound("activityTypeCode.greaterThanOrEqual=" + DEFAULT_ACTIVITY_TYPE_CODE);

        // Get all the activityTypeList where activityTypeCode is greater than or equal to UPDATED_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldNotBeFound("activityTypeCode.greaterThanOrEqual=" + UPDATED_ACTIVITY_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeCode is less than or equal to DEFAULT_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldBeFound("activityTypeCode.lessThanOrEqual=" + DEFAULT_ACTIVITY_TYPE_CODE);

        // Get all the activityTypeList where activityTypeCode is less than or equal to SMALLER_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldNotBeFound("activityTypeCode.lessThanOrEqual=" + SMALLER_ACTIVITY_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeCode is less than DEFAULT_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldNotBeFound("activityTypeCode.lessThan=" + DEFAULT_ACTIVITY_TYPE_CODE);

        // Get all the activityTypeList where activityTypeCode is less than UPDATED_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldBeFound("activityTypeCode.lessThan=" + UPDATED_ACTIVITY_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllActivityTypesByActivityTypeCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        // Get all the activityTypeList where activityTypeCode is greater than DEFAULT_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldNotBeFound("activityTypeCode.greaterThan=" + DEFAULT_ACTIVITY_TYPE_CODE);

        // Get all the activityTypeList where activityTypeCode is greater than SMALLER_ACTIVITY_TYPE_CODE
        defaultActivityTypeShouldBeFound("activityTypeCode.greaterThan=" + SMALLER_ACTIVITY_TYPE_CODE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActivityTypeShouldBeFound(String filter) throws Exception {
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE)))
            .andExpect(jsonPath("$.[*].activityTypeCode").value(hasItem(DEFAULT_ACTIVITY_TYPE_CODE)));

        // Check, that the count call also returns 1
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActivityTypeShouldNotBeFound(String filter) throws Exception {
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActivityTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingActivityType() throws Exception {
        // Get the activityType
        restActivityTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActivityType() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();

        // Update the activityType
        ActivityType updatedActivityType = activityTypeRepository.findById(activityType.getId()).get();
        // Disconnect from session so that the updates on updatedActivityType are not directly saved in db
        em.detach(updatedActivityType);
        updatedActivityType.activityType(UPDATED_ACTIVITY_TYPE).activityTypeCode(UPDATED_ACTIVITY_TYPE_CODE);

        restActivityTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivityType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivityType))
            )
            .andExpect(status().isOk());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
        ActivityType testActivityType = activityTypeList.get(activityTypeList.size() - 1);
        assertThat(testActivityType.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivityType.getActivityTypeCode()).isEqualTo(UPDATED_ACTIVITY_TYPE_CODE);
    }

    @Test
    @Transactional
    void putNonExistingActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activityType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityTypeWithPatch() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();

        // Update the activityType using partial update
        ActivityType partialUpdatedActivityType = new ActivityType();
        partialUpdatedActivityType.setId(activityType.getId());

        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityType))
            )
            .andExpect(status().isOk());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
        ActivityType testActivityType = activityTypeList.get(activityTypeList.size() - 1);
        assertThat(testActivityType.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testActivityType.getActivityTypeCode()).isEqualTo(DEFAULT_ACTIVITY_TYPE_CODE);
    }

    @Test
    @Transactional
    void fullUpdateActivityTypeWithPatch() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();

        // Update the activityType using partial update
        ActivityType partialUpdatedActivityType = new ActivityType();
        partialUpdatedActivityType.setId(activityType.getId());

        partialUpdatedActivityType.activityType(UPDATED_ACTIVITY_TYPE).activityTypeCode(UPDATED_ACTIVITY_TYPE_CODE);

        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityType))
            )
            .andExpect(status().isOk());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
        ActivityType testActivityType = activityTypeList.get(activityTypeList.size() - 1);
        assertThat(testActivityType.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivityType.getActivityTypeCode()).isEqualTo(UPDATED_ACTIVITY_TYPE_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activityType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivityType() throws Exception {
        int databaseSizeBeforeUpdate = activityTypeRepository.findAll().size();
        activityType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activityType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityType in the database
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivityType() throws Exception {
        // Initialize the database
        activityTypeRepository.saveAndFlush(activityType);

        int databaseSizeBeforeDelete = activityTypeRepository.findAll().size();

        // Delete the activityType
        restActivityTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, activityType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityType> activityTypeList = activityTypeRepository.findAll();
        assertThat(activityTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
