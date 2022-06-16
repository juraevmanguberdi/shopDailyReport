package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.OwnerExpenseType;
import com.shop.report.repository.OwnerExpenseTypeRepository;
import com.shop.report.service.dto.OwnerExpenseTypeDTO;
import com.shop.report.service.mapper.OwnerExpenseTypeMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link OwnerExpenseTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OwnerExpenseTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/owner-expense-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OwnerExpenseTypeRepository ownerExpenseTypeRepository;

    @Autowired
    private OwnerExpenseTypeMapper ownerExpenseTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOwnerExpenseTypeMockMvc;

    private OwnerExpenseType ownerExpenseType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OwnerExpenseType createEntity(EntityManager em) {
        OwnerExpenseType ownerExpenseType = new OwnerExpenseType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .notes(DEFAULT_NOTES)
            .createdDate(DEFAULT_CREATED_DATE);
        return ownerExpenseType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OwnerExpenseType createUpdatedEntity(EntityManager em) {
        OwnerExpenseType ownerExpenseType = new OwnerExpenseType()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES)
            .createdDate(UPDATED_CREATED_DATE);
        return ownerExpenseType;
    }

    @BeforeEach
    public void initTest() {
        ownerExpenseType = createEntity(em);
    }

    @Test
    @Transactional
    void createOwnerExpenseType() throws Exception {
        int databaseSizeBeforeCreate = ownerExpenseTypeRepository.findAll().size();
        // Create the OwnerExpenseType
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(ownerExpenseType);
        restOwnerExpenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OwnerExpenseType testOwnerExpenseType = ownerExpenseTypeList.get(ownerExpenseTypeList.size() - 1);
        assertThat(testOwnerExpenseType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOwnerExpenseType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOwnerExpenseType.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testOwnerExpenseType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createOwnerExpenseTypeWithExistingId() throws Exception {
        // Create the OwnerExpenseType with an existing ID
        ownerExpenseType.setId(1L);
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(ownerExpenseType);

        int databaseSizeBeforeCreate = ownerExpenseTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerExpenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerExpenseTypeRepository.findAll().size();
        // set the field null
        ownerExpenseType.setName(null);

        // Create the OwnerExpenseType, which fails.
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(ownerExpenseType);

        restOwnerExpenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOwnerExpenseTypes() throws Exception {
        // Initialize the database
        ownerExpenseTypeRepository.saveAndFlush(ownerExpenseType);

        // Get all the ownerExpenseTypeList
        restOwnerExpenseTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerExpenseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOwnerExpenseType() throws Exception {
        // Initialize the database
        ownerExpenseTypeRepository.saveAndFlush(ownerExpenseType);

        // Get the ownerExpenseType
        restOwnerExpenseTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, ownerExpenseType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ownerExpenseType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOwnerExpenseType() throws Exception {
        // Get the ownerExpenseType
        restOwnerExpenseTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOwnerExpenseType() throws Exception {
        // Initialize the database
        ownerExpenseTypeRepository.saveAndFlush(ownerExpenseType);

        int databaseSizeBeforeUpdate = ownerExpenseTypeRepository.findAll().size();

        // Update the ownerExpenseType
        OwnerExpenseType updatedOwnerExpenseType = ownerExpenseTypeRepository.findById(ownerExpenseType.getId()).get();
        // Disconnect from session so that the updates on updatedOwnerExpenseType are not directly saved in db
        em.detach(updatedOwnerExpenseType);
        updatedOwnerExpenseType.name(UPDATED_NAME).code(UPDATED_CODE).notes(UPDATED_NOTES).createdDate(UPDATED_CREATED_DATE);
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(updatedOwnerExpenseType);

        restOwnerExpenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ownerExpenseTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeUpdate);
        OwnerExpenseType testOwnerExpenseType = ownerExpenseTypeList.get(ownerExpenseTypeList.size() - 1);
        assertThat(testOwnerExpenseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOwnerExpenseType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOwnerExpenseType.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testOwnerExpenseType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOwnerExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseTypeRepository.findAll().size();
        ownerExpenseType.setId(count.incrementAndGet());

        // Create the OwnerExpenseType
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(ownerExpenseType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerExpenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ownerExpenseTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOwnerExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseTypeRepository.findAll().size();
        ownerExpenseType.setId(count.incrementAndGet());

        // Create the OwnerExpenseType
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(ownerExpenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerExpenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOwnerExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseTypeRepository.findAll().size();
        ownerExpenseType.setId(count.incrementAndGet());

        // Create the OwnerExpenseType
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(ownerExpenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerExpenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOwnerExpenseTypeWithPatch() throws Exception {
        // Initialize the database
        ownerExpenseTypeRepository.saveAndFlush(ownerExpenseType);

        int databaseSizeBeforeUpdate = ownerExpenseTypeRepository.findAll().size();

        // Update the ownerExpenseType using partial update
        OwnerExpenseType partialUpdatedOwnerExpenseType = new OwnerExpenseType();
        partialUpdatedOwnerExpenseType.setId(ownerExpenseType.getId());

        partialUpdatedOwnerExpenseType.name(UPDATED_NAME);

        restOwnerExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOwnerExpenseType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOwnerExpenseType))
            )
            .andExpect(status().isOk());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeUpdate);
        OwnerExpenseType testOwnerExpenseType = ownerExpenseTypeList.get(ownerExpenseTypeList.size() - 1);
        assertThat(testOwnerExpenseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOwnerExpenseType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOwnerExpenseType.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testOwnerExpenseType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOwnerExpenseTypeWithPatch() throws Exception {
        // Initialize the database
        ownerExpenseTypeRepository.saveAndFlush(ownerExpenseType);

        int databaseSizeBeforeUpdate = ownerExpenseTypeRepository.findAll().size();

        // Update the ownerExpenseType using partial update
        OwnerExpenseType partialUpdatedOwnerExpenseType = new OwnerExpenseType();
        partialUpdatedOwnerExpenseType.setId(ownerExpenseType.getId());

        partialUpdatedOwnerExpenseType.name(UPDATED_NAME).code(UPDATED_CODE).notes(UPDATED_NOTES).createdDate(UPDATED_CREATED_DATE);

        restOwnerExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOwnerExpenseType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOwnerExpenseType))
            )
            .andExpect(status().isOk());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeUpdate);
        OwnerExpenseType testOwnerExpenseType = ownerExpenseTypeList.get(ownerExpenseTypeList.size() - 1);
        assertThat(testOwnerExpenseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOwnerExpenseType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOwnerExpenseType.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testOwnerExpenseType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOwnerExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseTypeRepository.findAll().size();
        ownerExpenseType.setId(count.incrementAndGet());

        // Create the OwnerExpenseType
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(ownerExpenseType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ownerExpenseTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOwnerExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseTypeRepository.findAll().size();
        ownerExpenseType.setId(count.incrementAndGet());

        // Create the OwnerExpenseType
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(ownerExpenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOwnerExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseTypeRepository.findAll().size();
        ownerExpenseType.setId(count.incrementAndGet());

        // Create the OwnerExpenseType
        OwnerExpenseTypeDTO ownerExpenseTypeDTO = ownerExpenseTypeMapper.toDto(ownerExpenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OwnerExpenseType in the database
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOwnerExpenseType() throws Exception {
        // Initialize the database
        ownerExpenseTypeRepository.saveAndFlush(ownerExpenseType);

        int databaseSizeBeforeDelete = ownerExpenseTypeRepository.findAll().size();

        // Delete the ownerExpenseType
        restOwnerExpenseTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, ownerExpenseType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OwnerExpenseType> ownerExpenseTypeList = ownerExpenseTypeRepository.findAll();
        assertThat(ownerExpenseTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
