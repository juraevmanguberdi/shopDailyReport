package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.BorrowedDebt;
import com.shop.report.domain.BorrowedDebtType;
import com.shop.report.repository.BorrowedDebtRepository;
import com.shop.report.service.dto.BorrowedDebtDTO;
import com.shop.report.service.mapper.BorrowedDebtMapper;
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
 * Integration tests for the {@link BorrowedDebtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BorrowedDebtResourceIT {

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/borrowed-debts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BorrowedDebtRepository borrowedDebtRepository;

    @Autowired
    private BorrowedDebtMapper borrowedDebtMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBorrowedDebtMockMvc;

    private BorrowedDebt borrowedDebt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BorrowedDebt createEntity(EntityManager em) {
        BorrowedDebt borrowedDebt = new BorrowedDebt().amount(DEFAULT_AMOUNT).notes(DEFAULT_NOTES).code(DEFAULT_CODE).date(DEFAULT_DATE);
        // Add required entity
        BorrowedDebtType borrowedDebtType;
        if (TestUtil.findAll(em, BorrowedDebtType.class).isEmpty()) {
            borrowedDebtType = BorrowedDebtTypeResourceIT.createEntity(em);
            em.persist(borrowedDebtType);
            em.flush();
        } else {
            borrowedDebtType = TestUtil.findAll(em, BorrowedDebtType.class).get(0);
        }
        borrowedDebt.setBorrowedDebtType(borrowedDebtType);
        return borrowedDebt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BorrowedDebt createUpdatedEntity(EntityManager em) {
        BorrowedDebt borrowedDebt = new BorrowedDebt().amount(UPDATED_AMOUNT).notes(UPDATED_NOTES).code(UPDATED_CODE).date(UPDATED_DATE);
        // Add required entity
        BorrowedDebtType borrowedDebtType;
        if (TestUtil.findAll(em, BorrowedDebtType.class).isEmpty()) {
            borrowedDebtType = BorrowedDebtTypeResourceIT.createUpdatedEntity(em);
            em.persist(borrowedDebtType);
            em.flush();
        } else {
            borrowedDebtType = TestUtil.findAll(em, BorrowedDebtType.class).get(0);
        }
        borrowedDebt.setBorrowedDebtType(borrowedDebtType);
        return borrowedDebt;
    }

    @BeforeEach
    public void initTest() {
        borrowedDebt = createEntity(em);
    }

    @Test
    @Transactional
    void createBorrowedDebt() throws Exception {
        int databaseSizeBeforeCreate = borrowedDebtRepository.findAll().size();
        // Create the BorrowedDebt
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(borrowedDebt);
        restBorrowedDebtMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeCreate + 1);
        BorrowedDebt testBorrowedDebt = borrowedDebtList.get(borrowedDebtList.size() - 1);
        assertThat(testBorrowedDebt.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBorrowedDebt.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testBorrowedDebt.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBorrowedDebt.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createBorrowedDebtWithExistingId() throws Exception {
        // Create the BorrowedDebt with an existing ID
        borrowedDebt.setId(1L);
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(borrowedDebt);

        int databaseSizeBeforeCreate = borrowedDebtRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBorrowedDebtMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = borrowedDebtRepository.findAll().size();
        // set the field null
        borrowedDebt.setAmount(null);

        // Create the BorrowedDebt, which fails.
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(borrowedDebt);

        restBorrowedDebtMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isBadRequest());

        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBorrowedDebts() throws Exception {
        // Initialize the database
        borrowedDebtRepository.saveAndFlush(borrowedDebt);

        // Get all the borrowedDebtList
        restBorrowedDebtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borrowedDebt.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getBorrowedDebt() throws Exception {
        // Initialize the database
        borrowedDebtRepository.saveAndFlush(borrowedDebt);

        // Get the borrowedDebt
        restBorrowedDebtMockMvc
            .perform(get(ENTITY_API_URL_ID, borrowedDebt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(borrowedDebt.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBorrowedDebt() throws Exception {
        // Get the borrowedDebt
        restBorrowedDebtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBorrowedDebt() throws Exception {
        // Initialize the database
        borrowedDebtRepository.saveAndFlush(borrowedDebt);

        int databaseSizeBeforeUpdate = borrowedDebtRepository.findAll().size();

        // Update the borrowedDebt
        BorrowedDebt updatedBorrowedDebt = borrowedDebtRepository.findById(borrowedDebt.getId()).get();
        // Disconnect from session so that the updates on updatedBorrowedDebt are not directly saved in db
        em.detach(updatedBorrowedDebt);
        updatedBorrowedDebt.amount(UPDATED_AMOUNT).notes(UPDATED_NOTES).code(UPDATED_CODE).date(UPDATED_DATE);
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(updatedBorrowedDebt);

        restBorrowedDebtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, borrowedDebtDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isOk());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeUpdate);
        BorrowedDebt testBorrowedDebt = borrowedDebtList.get(borrowedDebtList.size() - 1);
        assertThat(testBorrowedDebt.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBorrowedDebt.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBorrowedDebt.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBorrowedDebt.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingBorrowedDebt() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtRepository.findAll().size();
        borrowedDebt.setId(count.incrementAndGet());

        // Create the BorrowedDebt
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(borrowedDebt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBorrowedDebtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, borrowedDebtDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBorrowedDebt() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtRepository.findAll().size();
        borrowedDebt.setId(count.incrementAndGet());

        // Create the BorrowedDebt
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(borrowedDebt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedDebtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBorrowedDebt() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtRepository.findAll().size();
        borrowedDebt.setId(count.incrementAndGet());

        // Create the BorrowedDebt
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(borrowedDebt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedDebtMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBorrowedDebtWithPatch() throws Exception {
        // Initialize the database
        borrowedDebtRepository.saveAndFlush(borrowedDebt);

        int databaseSizeBeforeUpdate = borrowedDebtRepository.findAll().size();

        // Update the borrowedDebt using partial update
        BorrowedDebt partialUpdatedBorrowedDebt = new BorrowedDebt();
        partialUpdatedBorrowedDebt.setId(borrowedDebt.getId());

        restBorrowedDebtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBorrowedDebt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBorrowedDebt))
            )
            .andExpect(status().isOk());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeUpdate);
        BorrowedDebt testBorrowedDebt = borrowedDebtList.get(borrowedDebtList.size() - 1);
        assertThat(testBorrowedDebt.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBorrowedDebt.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testBorrowedDebt.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBorrowedDebt.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateBorrowedDebtWithPatch() throws Exception {
        // Initialize the database
        borrowedDebtRepository.saveAndFlush(borrowedDebt);

        int databaseSizeBeforeUpdate = borrowedDebtRepository.findAll().size();

        // Update the borrowedDebt using partial update
        BorrowedDebt partialUpdatedBorrowedDebt = new BorrowedDebt();
        partialUpdatedBorrowedDebt.setId(borrowedDebt.getId());

        partialUpdatedBorrowedDebt.amount(UPDATED_AMOUNT).notes(UPDATED_NOTES).code(UPDATED_CODE).date(UPDATED_DATE);

        restBorrowedDebtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBorrowedDebt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBorrowedDebt))
            )
            .andExpect(status().isOk());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeUpdate);
        BorrowedDebt testBorrowedDebt = borrowedDebtList.get(borrowedDebtList.size() - 1);
        assertThat(testBorrowedDebt.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBorrowedDebt.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBorrowedDebt.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBorrowedDebt.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingBorrowedDebt() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtRepository.findAll().size();
        borrowedDebt.setId(count.incrementAndGet());

        // Create the BorrowedDebt
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(borrowedDebt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBorrowedDebtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, borrowedDebtDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBorrowedDebt() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtRepository.findAll().size();
        borrowedDebt.setId(count.incrementAndGet());

        // Create the BorrowedDebt
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(borrowedDebt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedDebtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBorrowedDebt() throws Exception {
        int databaseSizeBeforeUpdate = borrowedDebtRepository.findAll().size();
        borrowedDebt.setId(count.incrementAndGet());

        // Create the BorrowedDebt
        BorrowedDebtDTO borrowedDebtDTO = borrowedDebtMapper.toDto(borrowedDebt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedDebtMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borrowedDebtDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BorrowedDebt in the database
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBorrowedDebt() throws Exception {
        // Initialize the database
        borrowedDebtRepository.saveAndFlush(borrowedDebt);

        int databaseSizeBeforeDelete = borrowedDebtRepository.findAll().size();

        // Delete the borrowedDebt
        restBorrowedDebtMockMvc
            .perform(delete(ENTITY_API_URL_ID, borrowedDebt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BorrowedDebt> borrowedDebtList = borrowedDebtRepository.findAll();
        assertThat(borrowedDebtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
