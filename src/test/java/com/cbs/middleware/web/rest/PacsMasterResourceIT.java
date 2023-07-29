package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.PacsMaster;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.service.PacsMasterService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PacsMasterResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PacsMasterResourceIT {

    private static final String DEFAULT_PACS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pacs-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PacsMasterRepository pacsMasterRepository;

    @Mock
    private PacsMasterRepository pacsMasterRepositoryMock;

    @Mock
    private PacsMasterService pacsMasterServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPacsMasterMockMvc;

    private PacsMaster pacsMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PacsMaster createEntity(EntityManager em) {
        PacsMaster pacsMaster = new PacsMaster().pacsName(DEFAULT_PACS_NAME).pacsNumber(DEFAULT_PACS_NUMBER);
        return pacsMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PacsMaster createUpdatedEntity(EntityManager em) {
        PacsMaster pacsMaster = new PacsMaster().pacsName(UPDATED_PACS_NAME).pacsNumber(UPDATED_PACS_NUMBER);
        return pacsMaster;
    }

    @BeforeEach
    public void initTest() {
        pacsMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createPacsMaster() throws Exception {
        int databaseSizeBeforeCreate = pacsMasterRepository.findAll().size();
        // Create the PacsMaster
        restPacsMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacsMaster)))
            .andExpect(status().isCreated());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeCreate + 1);
        PacsMaster testPacsMaster = pacsMasterList.get(pacsMasterList.size() - 1);
        assertThat(testPacsMaster.getPacsName()).isEqualTo(DEFAULT_PACS_NAME);
        assertThat(testPacsMaster.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
    }

    @Test
    @Transactional
    void createPacsMasterWithExistingId() throws Exception {
        // Create the PacsMaster with an existing ID
        pacsMaster.setId(1L);

        int databaseSizeBeforeCreate = pacsMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacsMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacsMaster)))
            .andExpect(status().isBadRequest());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPacsMasters() throws Exception {
        // Initialize the database
        pacsMasterRepository.saveAndFlush(pacsMaster);

        // Get all the pacsMasterList
        restPacsMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacsMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacsName").value(hasItem(DEFAULT_PACS_NAME)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPacsMastersWithEagerRelationshipsIsEnabled() throws Exception {
        when(pacsMasterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPacsMasterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pacsMasterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPacsMastersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pacsMasterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPacsMasterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(pacsMasterRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPacsMaster() throws Exception {
        // Initialize the database
        pacsMasterRepository.saveAndFlush(pacsMaster);

        // Get the pacsMaster
        restPacsMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, pacsMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pacsMaster.getId().intValue()))
            .andExpect(jsonPath("$.pacsName").value(DEFAULT_PACS_NAME))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingPacsMaster() throws Exception {
        // Get the pacsMaster
        restPacsMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPacsMaster() throws Exception {
        // Initialize the database
        pacsMasterRepository.saveAndFlush(pacsMaster);

        int databaseSizeBeforeUpdate = pacsMasterRepository.findAll().size();

        // Update the pacsMaster
        PacsMaster updatedPacsMaster = pacsMasterRepository.findById(pacsMaster.getId()).get();
        // Disconnect from session so that the updates on updatedPacsMaster are not directly saved in db
        em.detach(updatedPacsMaster);
        updatedPacsMaster.pacsName(UPDATED_PACS_NAME).pacsNumber(UPDATED_PACS_NUMBER);

        restPacsMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPacsMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPacsMaster))
            )
            .andExpect(status().isOk());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeUpdate);
        PacsMaster testPacsMaster = pacsMasterList.get(pacsMasterList.size() - 1);
        assertThat(testPacsMaster.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
        assertThat(testPacsMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingPacsMaster() throws Exception {
        int databaseSizeBeforeUpdate = pacsMasterRepository.findAll().size();
        pacsMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacsMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pacsMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pacsMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPacsMaster() throws Exception {
        int databaseSizeBeforeUpdate = pacsMasterRepository.findAll().size();
        pacsMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacsMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pacsMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPacsMaster() throws Exception {
        int databaseSizeBeforeUpdate = pacsMasterRepository.findAll().size();
        pacsMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacsMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacsMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePacsMasterWithPatch() throws Exception {
        // Initialize the database
        pacsMasterRepository.saveAndFlush(pacsMaster);

        int databaseSizeBeforeUpdate = pacsMasterRepository.findAll().size();

        // Update the pacsMaster using partial update
        PacsMaster partialUpdatedPacsMaster = new PacsMaster();
        partialUpdatedPacsMaster.setId(pacsMaster.getId());

        partialUpdatedPacsMaster.pacsName(UPDATED_PACS_NAME).pacsNumber(UPDATED_PACS_NUMBER);

        restPacsMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPacsMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPacsMaster))
            )
            .andExpect(status().isOk());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeUpdate);
        PacsMaster testPacsMaster = pacsMasterList.get(pacsMasterList.size() - 1);
        assertThat(testPacsMaster.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
        assertThat(testPacsMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdatePacsMasterWithPatch() throws Exception {
        // Initialize the database
        pacsMasterRepository.saveAndFlush(pacsMaster);

        int databaseSizeBeforeUpdate = pacsMasterRepository.findAll().size();

        // Update the pacsMaster using partial update
        PacsMaster partialUpdatedPacsMaster = new PacsMaster();
        partialUpdatedPacsMaster.setId(pacsMaster.getId());

        partialUpdatedPacsMaster.pacsName(UPDATED_PACS_NAME).pacsNumber(UPDATED_PACS_NUMBER);

        restPacsMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPacsMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPacsMaster))
            )
            .andExpect(status().isOk());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeUpdate);
        PacsMaster testPacsMaster = pacsMasterList.get(pacsMasterList.size() - 1);
        assertThat(testPacsMaster.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
        assertThat(testPacsMaster.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingPacsMaster() throws Exception {
        int databaseSizeBeforeUpdate = pacsMasterRepository.findAll().size();
        pacsMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacsMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pacsMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pacsMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPacsMaster() throws Exception {
        int databaseSizeBeforeUpdate = pacsMasterRepository.findAll().size();
        pacsMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacsMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pacsMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPacsMaster() throws Exception {
        int databaseSizeBeforeUpdate = pacsMasterRepository.findAll().size();
        pacsMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacsMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pacsMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PacsMaster in the database
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePacsMaster() throws Exception {
        // Initialize the database
        pacsMasterRepository.saveAndFlush(pacsMaster);

        int databaseSizeBeforeDelete = pacsMasterRepository.findAll().size();

        // Delete the pacsMaster
        restPacsMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, pacsMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PacsMaster> pacsMasterList = pacsMasterRepository.findAll();
        assertThat(pacsMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
