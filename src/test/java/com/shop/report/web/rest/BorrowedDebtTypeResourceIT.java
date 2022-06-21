package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.BorrowedDebtType;
import com.shop.report.repository.BorrowedDebtTypeRepository;
import com.shop.report.service.dto.BorrowedDebtTypeDTO;
import com.shop.report.service.mapper.BorrowedDebtTypeMapper;
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
 * Integration tests for the {@link BorrowedDebtTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BorrowedDebtTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/borrowed-debt-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BorrowedDebtTypeRepository borrowedDebtTypeRepository;

    @Autowired
    private BorrowedDebtTypeMapper borrowedDebtTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBorrowedDebtTypeMockMvc;

    private BorrowedDebtType borrowedDebtType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BorrowedDebtType createEntity(EntityManager em) {
        BorrowedDebtType borrowedDebtType = new BorrowedDebtType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .notes(DEFAULT_NOTES)
            .createdDate(DEFAULT_CREATED_DATE);
        return borrowedDebtType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BorrowedDebtType createUpdatedEntity(EntityManager em) {
        BorrowedDebtType borrowedDebtType = new BorrowedDebtType()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES)
            .createdDate(UPDATED_CREATED_DATE);
        return borrowedDebtType;
    }

    @BeforeEach
    public void initTest() {
        borrowedDebtType = createEntity(em);
    }

    @Test
    @Transactional
    void createBorrowedDebtType() throws Exception {
        int databaseSizeBeforeCreate = borrowedDebtTypeRepository.findAll().size();
        // Create the BorrowedDebtType
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(borrowedDebtType);
        restBorrowedDebtTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BorrowedDebtType testBorrowedDebtType = borrowedDebtTypeList.get(borrowedDebtTypeList.size() - 1);
        assertThat(testBorrowedDebtType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBorrowedDebtType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBorrowedDebtType.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testBorrowedDebtType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createBorrowedDebtTypeWithExistingId() throws Exception {
        // Create the BorrowedDebtType with an existing ID
        borrowedDebtType.setId(1L);
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(borrowedDebtType);

        int databaseSizeBeforeCreate = borrowedDebtTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBorrowedDebtTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = borrowedDebtTypeRepository.findAll().size();
        // set the field null
        borrowedDebtType.setName(null);

        // Create the BorrowedDebtType, which fails.
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(borrowedDebtType);

        restBorrowedDebtTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBorrowedDebtTypes() throws Exception {
        // Initialize the database
        borrowedDebtTypeRepository.saveAndFlush(borrowedDebtType);

        // Get all the borrowedDebtTypeList
        restBorrowedDebtTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borrowedDebtType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getBorrowedDebtType() throws Exception {
        // Initialize the database
        borrowedDebtTypeRepository.saveAndFlush(borrowedDebtType);

        // Get the borrowedDebtType
        restBorrowedDebtTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, borrowedDebtType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(borrowedDebtType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBorrowedDebtType() throws Exception {
        // Get the borrowedDebtType
        restBorrowedDebtTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBorrowedDebtType() throws Exception {
        // Initialize the database
        borrowedDebtTypeRepository.saveAndFlush(borrowedDebtType);

        int databaseSizeBeforeUpdate = borrowedDebtTypeRepository.findAll().size();

        // Update the borrowedDebtType
        BorrowedDebtType updatedBorrowedDebtType = borrowedDebtTypeRepository.findById(borrowedDebtType.getId()).get();
        // Disconnect from session so that the updates on updatedBorrowedDebtType are not directly saved in db
        em.detach(updatedBorrowedDebtType);
        updatedBorrowedDebtType.name(UPDATED_NAME).code(UPDATED_CODE).notes(UPDATED_NOTES).createdDate(UPDATED_CREATED_DATE);
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(updatedBorrowedDebtType);

        restBorrowedDebtTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, borrowedDebtTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeUpdate);
        BorrowedDebtType testBorrowedDebtType = borrowedDebtTypeList.get(borrowedDebtTypeList.size() - 1);
        assertThat(testBorrowedDebtType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBorrowedDebtType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBorrowedDebtType.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBorrowedDebtType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingBorrowedDebtType() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtTypeRepository.findAll().size();
        borrowedDebtType.setId(count.incrementAndGet());

        // Create the BorrowedDebtType
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(borrowedDebtType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBorrowedDebtTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, borrowedDebtTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBorrowedDebtType() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtTypeRepository.findAll().size();
        borrowedDebtType.setId(count.incrementAndGet());

        // Create the BorrowedDebtType
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(borrowedDebtType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedDebtTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBorrowedDebtType() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtTypeRepository.findAll().size();
        borrowedDebtType.setId(count.incrementAndGet());

        // Create the BorrowedDebtType
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(borrowedDebtType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedDebtTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBorrowedDebtTypeWithPatch() throws Exception {
        // Initialize the database
        borrowedDebtTypeRepository.saveAndFlush(borrowedDebtType);

        int databaseSizeBeforeUpdate = borrowedDebtTypeRepository.findAll().size();

        // Update the borrowedDebtType using partial update
        BorrowedDebtType partialUpdatedBorrowedDebtType = new BorrowedDebtType();
        partialUpdatedBorrowedDebtType.setId(borrowedDebtType.getId());

        partialUpdatedBorrowedDebtType.name(UPDATED_NAME).code(UPDATED_CODE);

        restBorrowedDebtTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBorrowedDebtType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBorrowedDebtType))
            )
            .andExpect(status().isOk());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeUpdate);
        BorrowedDebtType testBorrowedDebtType = borrowedDebtTypeList.get(borrowedDebtTypeList.size() - 1);
        assertThat(testBorrowedDebtType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBorrowedDebtType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBorrowedDebtType.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testBorrowedDebtType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateBorrowedDebtTypeWithPatch() throws Exception {
        // Initialize the database
        borrowedDebtTypeRepository.saveAndFlush(borrowedDebtType);

        int databaseSizeBeforeUpdate = borrowedDebtTypeRepository.findAll().size();

        // Update the borrowedDebtType using partial update
        BorrowedDebtType partialUpdatedBorrowedDebtType = new BorrowedDebtType();
        partialUpdatedBorrowedDebtType.setId(borrowedDebtType.getId());

        partialUpdatedBorrowedDebtType.name(UPDATED_NAME).code(UPDATED_CODE).notes(UPDATED_NOTES).createdDate(UPDATED_CREATED_DATE);

        restBorrowedDebtTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBorrowedDebtType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBorrowedDebtType))
            )
            .andExpect(status().isOk());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeUpdate);
        BorrowedDebtType testBorrowedDebtType = borrowedDebtTypeList.get(borrowedDebtTypeList.size() - 1);
        assertThat(testBorrowedDebtType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBorrowedDebtType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBorrowedDebtType.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBorrowedDebtType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingBorrowedDebtType() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtTypeRepository.findAll().size();
        borrowedDebtType.setId(count.incrementAndGet());

        // Create the BorrowedDebtType
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(borrowedDebtType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBorrowedDebtTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, borrowedDebtTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBorrowedDebtType() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtTypeRepository.findAll().size();
        borrowedDebtType.setId(count.incrementAndGet());

        // Create the BorrowedDebtType
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(borrowedDebtType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedDebtTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBorrowedDebtType() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtTypeRepository.findAll().size();
        borrowedDebtType.setId(count.incrementAndGet());

        // Create the BorrowedDebtType
        BorrowedDebtTypeDTO borrowedDebtTypeDTO = borrowedDebtTypeMapper.toDto(borrowedDebtType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedDebtTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BorrowedDebtType in the database
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBorrowedDebtType() throws Exception {
        // Initialize the database
        borrowedDebtTypeRepository.saveAndFlush(borrowedDebtType);

        int databaseSizeBeforeDelete = borrowedDebtTypeRepository.findAll().size();

        // Delete the borrowedDebtType
        restBorrowedDebtTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, borrowedDebtType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BorrowedDebtType> borrowedDebtTypeList = borrowedDebtTypeRepository.findAll();
        assertThat(borrowedDebtTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
