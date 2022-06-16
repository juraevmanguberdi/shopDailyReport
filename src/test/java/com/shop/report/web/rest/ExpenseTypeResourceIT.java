package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.ExpenseType;
import com.shop.report.repository.ExpenseTypeRepository;
import com.shop.report.service.dto.ExpenseTypeDTO;
import com.shop.report.service.mapper.ExpenseTypeMapper;
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
 * Integration tests for the {@link ExpenseTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExpenseTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/expense-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private ExpenseTypeMapper expenseTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpenseTypeMockMvc;

    private ExpenseType expenseType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpenseType createEntity(EntityManager em) {
        ExpenseType expenseType = new ExpenseType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .notes(DEFAULT_NOTES)
            .createdDate(DEFAULT_CREATED_DATE);
        return expenseType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpenseType createUpdatedEntity(EntityManager em) {
        ExpenseType expenseType = new ExpenseType()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES)
            .createdDate(UPDATED_CREATED_DATE);
        return expenseType;
    }

    @BeforeEach
    public void initTest() {
        expenseType = createEntity(em);
    }

    @Test
    @Transactional
    void createExpenseType() throws Exception {
        int databaseSizeBeforeCreate = expenseTypeRepository.findAll().size();
        // Create the ExpenseType
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(expenseType);
        restExpenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ExpenseType testExpenseType = expenseTypeList.get(expenseTypeList.size() - 1);
        assertThat(testExpenseType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExpenseType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testExpenseType.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testExpenseType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createExpenseTypeWithExistingId() throws Exception {
        // Create the ExpenseType with an existing ID
        expenseType.setId(1L);
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(expenseType);

        int databaseSizeBeforeCreate = expenseTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = expenseTypeRepository.findAll().size();
        // set the field null
        expenseType.setName(null);

        // Create the ExpenseType, which fails.
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(expenseType);

        restExpenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExpenseTypes() throws Exception {
        // Initialize the database
        expenseTypeRepository.saveAndFlush(expenseType);

        // Get all the expenseTypeList
        restExpenseTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getExpenseType() throws Exception {
        // Initialize the database
        expenseTypeRepository.saveAndFlush(expenseType);

        // Get the expenseType
        restExpenseTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, expenseType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expenseType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingExpenseType() throws Exception {
        // Get the expenseType
        restExpenseTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExpenseType() throws Exception {
        // Initialize the database
        expenseTypeRepository.saveAndFlush(expenseType);

        int databaseSizeBeforeUpdate = expenseTypeRepository.findAll().size();

        // Update the expenseType
        ExpenseType updatedExpenseType = expenseTypeRepository.findById(expenseType.getId()).get();
        // Disconnect from session so that the updates on updatedExpenseType are not directly saved in db
        em.detach(updatedExpenseType);
        updatedExpenseType.name(UPDATED_NAME).code(UPDATED_CODE).notes(UPDATED_NOTES).createdDate(UPDATED_CREATED_DATE);
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(updatedExpenseType);

        restExpenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expenseTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeUpdate);
        ExpenseType testExpenseType = expenseTypeList.get(expenseTypeList.size() - 1);
        assertThat(testExpenseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExpenseType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testExpenseType.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testExpenseType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = expenseTypeRepository.findAll().size();
        expenseType.setId(count.incrementAndGet());

        // Create the ExpenseType
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(expenseType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expenseTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = expenseTypeRepository.findAll().size();
        expenseType.setId(count.incrementAndGet());

        // Create the ExpenseType
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(expenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = expenseTypeRepository.findAll().size();
        expenseType.setId(count.incrementAndGet());

        // Create the ExpenseType
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(expenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExpenseTypeWithPatch() throws Exception {
        // Initialize the database
        expenseTypeRepository.saveAndFlush(expenseType);

        int databaseSizeBeforeUpdate = expenseTypeRepository.findAll().size();

        // Update the expenseType using partial update
        ExpenseType partialUpdatedExpenseType = new ExpenseType();
        partialUpdatedExpenseType.setId(expenseType.getId());

        partialUpdatedExpenseType.name(UPDATED_NAME);

        restExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpenseType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpenseType))
            )
            .andExpect(status().isOk());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeUpdate);
        ExpenseType testExpenseType = expenseTypeList.get(expenseTypeList.size() - 1);
        assertThat(testExpenseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExpenseType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testExpenseType.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testExpenseType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateExpenseTypeWithPatch() throws Exception {
        // Initialize the database
        expenseTypeRepository.saveAndFlush(expenseType);

        int databaseSizeBeforeUpdate = expenseTypeRepository.findAll().size();

        // Update the expenseType using partial update
        ExpenseType partialUpdatedExpenseType = new ExpenseType();
        partialUpdatedExpenseType.setId(expenseType.getId());

        partialUpdatedExpenseType.name(UPDATED_NAME).code(UPDATED_CODE).notes(UPDATED_NOTES).createdDate(UPDATED_CREATED_DATE);

        restExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpenseType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpenseType))
            )
            .andExpect(status().isOk());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeUpdate);
        ExpenseType testExpenseType = expenseTypeList.get(expenseTypeList.size() - 1);
        assertThat(testExpenseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExpenseType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testExpenseType.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testExpenseType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = expenseTypeRepository.findAll().size();
        expenseType.setId(count.incrementAndGet());

        // Create the ExpenseType
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(expenseType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, expenseTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = expenseTypeRepository.findAll().size();
        expenseType.setId(count.incrementAndGet());

        // Create the ExpenseType
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(expenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExpenseType() throws Exception {
        int databaseSizeBeforeUpdate = expenseTypeRepository.findAll().size();
        expenseType.setId(count.incrementAndGet());

        // Create the ExpenseType
        ExpenseTypeDTO expenseTypeDTO = expenseTypeMapper.toDto(expenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(expenseTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExpenseType in the database
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExpenseType() throws Exception {
        // Initialize the database
        expenseTypeRepository.saveAndFlush(expenseType);

        int databaseSizeBeforeDelete = expenseTypeRepository.findAll().size();

        // Delete the expenseType
        restExpenseTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, expenseType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExpenseType> expenseTypeList = expenseTypeRepository.findAll();
        assertThat(expenseTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
