package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.RequiredProductType;
import com.shop.report.repository.RequiredProductTypeRepository;
import com.shop.report.service.dto.RequiredProductTypeDTO;
import com.shop.report.service.mapper.RequiredProductTypeMapper;
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
 * Integration tests for the {@link RequiredProductTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequiredProductTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/required-product-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequiredProductTypeRepository requiredProductTypeRepository;

    @Autowired
    private RequiredProductTypeMapper requiredProductTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequiredProductTypeMockMvc;

    private RequiredProductType requiredProductType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequiredProductType createEntity(EntityManager em) {
        RequiredProductType requiredProductType = new RequiredProductType().name(DEFAULT_NAME).code(DEFAULT_CODE).notes(DEFAULT_NOTES);
        return requiredProductType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequiredProductType createUpdatedEntity(EntityManager em) {
        RequiredProductType requiredProductType = new RequiredProductType().name(UPDATED_NAME).code(UPDATED_CODE).notes(UPDATED_NOTES);
        return requiredProductType;
    }

    @BeforeEach
    public void initTest() {
        requiredProductType = createEntity(em);
    }

    @Test
    @Transactional
    void createRequiredProductType() throws Exception {
        int databaseSizeBeforeCreate = requiredProductTypeRepository.findAll().size();
        // Create the RequiredProductType
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(requiredProductType);
        restRequiredProductTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RequiredProductType testRequiredProductType = requiredProductTypeList.get(requiredProductTypeList.size() - 1);
        assertThat(testRequiredProductType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRequiredProductType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRequiredProductType.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createRequiredProductTypeWithExistingId() throws Exception {
        // Create the RequiredProductType with an existing ID
        requiredProductType.setId(1L);
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(requiredProductType);

        int databaseSizeBeforeCreate = requiredProductTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequiredProductTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = requiredProductTypeRepository.findAll().size();
        // set the field null
        requiredProductType.setName(null);

        // Create the RequiredProductType, which fails.
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(requiredProductType);

        restRequiredProductTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequiredProductTypes() throws Exception {
        // Initialize the database
        requiredProductTypeRepository.saveAndFlush(requiredProductType);

        // Get all the requiredProductTypeList
        restRequiredProductTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requiredProductType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getRequiredProductType() throws Exception {
        // Initialize the database
        requiredProductTypeRepository.saveAndFlush(requiredProductType);

        // Get the requiredProductType
        restRequiredProductTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, requiredProductType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requiredProductType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingRequiredProductType() throws Exception {
        // Get the requiredProductType
        restRequiredProductTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequiredProductType() throws Exception {
        // Initialize the database
        requiredProductTypeRepository.saveAndFlush(requiredProductType);

        int databaseSizeBeforeUpdate = requiredProductTypeRepository.findAll().size();

        // Update the requiredProductType
        RequiredProductType updatedRequiredProductType = requiredProductTypeRepository.findById(requiredProductType.getId()).get();
        // Disconnect from session so that the updates on updatedRequiredProductType are not directly saved in db
        em.detach(updatedRequiredProductType);
        updatedRequiredProductType.name(UPDATED_NAME).code(UPDATED_CODE).notes(UPDATED_NOTES);
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(updatedRequiredProductType);

        restRequiredProductTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requiredProductTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeUpdate);
        RequiredProductType testRequiredProductType = requiredProductTypeList.get(requiredProductTypeList.size() - 1);
        assertThat(testRequiredProductType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequiredProductType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRequiredProductType.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingRequiredProductType() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductTypeRepository.findAll().size();
        requiredProductType.setId(count.incrementAndGet());

        // Create the RequiredProductType
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(requiredProductType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequiredProductTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requiredProductTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequiredProductType() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductTypeRepository.findAll().size();
        requiredProductType.setId(count.incrementAndGet());

        // Create the RequiredProductType
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(requiredProductType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredProductTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequiredProductType() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductTypeRepository.findAll().size();
        requiredProductType.setId(count.incrementAndGet());

        // Create the RequiredProductType
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(requiredProductType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredProductTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequiredProductTypeWithPatch() throws Exception {
        // Initialize the database
        requiredProductTypeRepository.saveAndFlush(requiredProductType);

        int databaseSizeBeforeUpdate = requiredProductTypeRepository.findAll().size();

        // Update the requiredProductType using partial update
        RequiredProductType partialUpdatedRequiredProductType = new RequiredProductType();
        partialUpdatedRequiredProductType.setId(requiredProductType.getId());

        partialUpdatedRequiredProductType.code(UPDATED_CODE).notes(UPDATED_NOTES);

        restRequiredProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequiredProductType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequiredProductType))
            )
            .andExpect(status().isOk());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeUpdate);
        RequiredProductType testRequiredProductType = requiredProductTypeList.get(requiredProductTypeList.size() - 1);
        assertThat(testRequiredProductType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRequiredProductType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRequiredProductType.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateRequiredProductTypeWithPatch() throws Exception {
        // Initialize the database
        requiredProductTypeRepository.saveAndFlush(requiredProductType);

        int databaseSizeBeforeUpdate = requiredProductTypeRepository.findAll().size();

        // Update the requiredProductType using partial update
        RequiredProductType partialUpdatedRequiredProductType = new RequiredProductType();
        partialUpdatedRequiredProductType.setId(requiredProductType.getId());

        partialUpdatedRequiredProductType.name(UPDATED_NAME).code(UPDATED_CODE).notes(UPDATED_NOTES);

        restRequiredProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequiredProductType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequiredProductType))
            )
            .andExpect(status().isOk());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeUpdate);
        RequiredProductType testRequiredProductType = requiredProductTypeList.get(requiredProductTypeList.size() - 1);
        assertThat(testRequiredProductType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequiredProductType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRequiredProductType.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingRequiredProductType() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductTypeRepository.findAll().size();
        requiredProductType.setId(count.incrementAndGet());

        // Create the RequiredProductType
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(requiredProductType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequiredProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requiredProductTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequiredProductType() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductTypeRepository.findAll().size();
        requiredProductType.setId(count.incrementAndGet());

        // Create the RequiredProductType
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(requiredProductType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequiredProductType() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductTypeRepository.findAll().size();
        requiredProductType.setId(count.incrementAndGet());

        // Create the RequiredProductType
        RequiredProductTypeDTO requiredProductTypeDTO = requiredProductTypeMapper.toDto(requiredProductType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequiredProductType in the database
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequiredProductType() throws Exception {
        // Initialize the database
        requiredProductTypeRepository.saveAndFlush(requiredProductType);

        int databaseSizeBeforeDelete = requiredProductTypeRepository.findAll().size();

        // Delete the requiredProductType
        restRequiredProductTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, requiredProductType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequiredProductType> requiredProductTypeList = requiredProductTypeRepository.findAll();
        assertThat(requiredProductTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
