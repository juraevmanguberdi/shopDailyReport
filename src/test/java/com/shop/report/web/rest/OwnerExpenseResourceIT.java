package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.OwnerExpense;
import com.shop.report.domain.OwnerExpenseType;
import com.shop.report.repository.OwnerExpenseRepository;
import com.shop.report.service.dto.OwnerExpenseDTO;
import com.shop.report.service.mapper.OwnerExpenseMapper;
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
 * Integration tests for the {@link OwnerExpenseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OwnerExpenseResourceIT {

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPENSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPENSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/owner-expenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OwnerExpenseRepository ownerExpenseRepository;

    @Autowired
    private OwnerExpenseMapper ownerExpenseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOwnerExpenseMockMvc;

    private OwnerExpense ownerExpense;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OwnerExpense createEntity(EntityManager em) {
        OwnerExpense ownerExpense = new OwnerExpense()
            .amount(DEFAULT_AMOUNT)
            .code(DEFAULT_CODE)
            .expenseDate(DEFAULT_EXPENSE_DATE)
            .notes(DEFAULT_NOTES);
        // Add required entity
        OwnerExpenseType ownerExpenseType;
        if (TestUtil.findAll(em, OwnerExpenseType.class).isEmpty()) {
            ownerExpenseType = OwnerExpenseTypeResourceIT.createEntity(em);
            em.persist(ownerExpenseType);
            em.flush();
        } else {
            ownerExpenseType = TestUtil.findAll(em, OwnerExpenseType.class).get(0);
        }
        ownerExpense.setOwnerExpenseType(ownerExpenseType);
        return ownerExpense;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OwnerExpense createUpdatedEntity(EntityManager em) {
        OwnerExpense ownerExpense = new OwnerExpense()
            .amount(UPDATED_AMOUNT)
            .code(UPDATED_CODE)
            .expenseDate(UPDATED_EXPENSE_DATE)
            .notes(UPDATED_NOTES);
        // Add required entity
        OwnerExpenseType ownerExpenseType;
        if (TestUtil.findAll(em, OwnerExpenseType.class).isEmpty()) {
            ownerExpenseType = OwnerExpenseTypeResourceIT.createUpdatedEntity(em);
            em.persist(ownerExpenseType);
            em.flush();
        } else {
            ownerExpenseType = TestUtil.findAll(em, OwnerExpenseType.class).get(0);
        }
        ownerExpense.setOwnerExpenseType(ownerExpenseType);
        return ownerExpense;
    }

    @BeforeEach
    public void initTest() {
        ownerExpense = createEntity(em);
    }

    @Test
    @Transactional
    void createOwnerExpense() throws Exception {
        int databaseSizeBeforeCreate = ownerExpenseRepository.findAll().size();
        // Create the OwnerExpense
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(ownerExpense);
        restOwnerExpenseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeCreate + 1);
        OwnerExpense testOwnerExpense = ownerExpenseList.get(ownerExpenseList.size() - 1);
        assertThat(testOwnerExpense.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testOwnerExpense.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOwnerExpense.getExpenseDate()).isEqualTo(DEFAULT_EXPENSE_DATE);
        assertThat(testOwnerExpense.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createOwnerExpenseWithExistingId() throws Exception {
        // Create the OwnerExpense with an existing ID
        ownerExpense.setId(1L);
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(ownerExpense);

        int databaseSizeBeforeCreate = ownerExpenseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerExpenseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerExpenseRepository.findAll().size();
        // set the field null
        ownerExpense.setAmount(null);

        // Create the OwnerExpense, which fails.
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(ownerExpense);

        restOwnerExpenseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isBadRequest());

        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOwnerExpenses() throws Exception {
        // Initialize the database
        ownerExpenseRepository.saveAndFlush(ownerExpense);

        // Get all the ownerExpenseList
        restOwnerExpenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerExpense.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].expenseDate").value(hasItem(DEFAULT_EXPENSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getOwnerExpense() throws Exception {
        // Initialize the database
        ownerExpenseRepository.saveAndFlush(ownerExpense);

        // Get the ownerExpense
        restOwnerExpenseMockMvc
            .perform(get(ENTITY_API_URL_ID, ownerExpense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ownerExpense.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.expenseDate").value(DEFAULT_EXPENSE_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingOwnerExpense() throws Exception {
        // Get the ownerExpense
        restOwnerExpenseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOwnerExpense() throws Exception {
        // Initialize the database
        ownerExpenseRepository.saveAndFlush(ownerExpense);

        int databaseSizeBeforeUpdate = ownerExpenseRepository.findAll().size();

        // Update the ownerExpense
        OwnerExpense updatedOwnerExpense = ownerExpenseRepository.findById(ownerExpense.getId()).get();
        // Disconnect from session so that the updates on updatedOwnerExpense are not directly saved in db
        em.detach(updatedOwnerExpense);
        updatedOwnerExpense.amount(UPDATED_AMOUNT).code(UPDATED_CODE).expenseDate(UPDATED_EXPENSE_DATE).notes(UPDATED_NOTES);
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(updatedOwnerExpense);

        restOwnerExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ownerExpenseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isOk());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeUpdate);
        OwnerExpense testOwnerExpense = ownerExpenseList.get(ownerExpenseList.size() - 1);
        assertThat(testOwnerExpense.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOwnerExpense.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOwnerExpense.getExpenseDate()).isEqualTo(UPDATED_EXPENSE_DATE);
        assertThat(testOwnerExpense.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingOwnerExpense() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseRepository.findAll().size();
        ownerExpense.setId(count.incrementAndGet());

        // Create the OwnerExpense
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(ownerExpense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ownerExpenseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOwnerExpense() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseRepository.findAll().size();
        ownerExpense.setId(count.incrementAndGet());

        // Create the OwnerExpense
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(ownerExpense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOwnerExpense() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseRepository.findAll().size();
        ownerExpense.setId(count.incrementAndGet());

        // Create the OwnerExpense
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(ownerExpense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerExpenseMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOwnerExpenseWithPatch() throws Exception {
        // Initialize the database
        ownerExpenseRepository.saveAndFlush(ownerExpense);

        int databaseSizeBeforeUpdate = ownerExpenseRepository.findAll().size();

        // Update the ownerExpense using partial update
        OwnerExpense partialUpdatedOwnerExpense = new OwnerExpense();
        partialUpdatedOwnerExpense.setId(ownerExpense.getId());

        partialUpdatedOwnerExpense.amount(UPDATED_AMOUNT).notes(UPDATED_NOTES);

        restOwnerExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOwnerExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOwnerExpense))
            )
            .andExpect(status().isOk());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeUpdate);
        OwnerExpense testOwnerExpense = ownerExpenseList.get(ownerExpenseList.size() - 1);
        assertThat(testOwnerExpense.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOwnerExpense.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOwnerExpense.getExpenseDate()).isEqualTo(DEFAULT_EXPENSE_DATE);
        assertThat(testOwnerExpense.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateOwnerExpenseWithPatch() throws Exception {
        // Initialize the database
        ownerExpenseRepository.saveAndFlush(ownerExpense);

        int databaseSizeBeforeUpdate = ownerExpenseRepository.findAll().size();

        // Update the ownerExpense using partial update
        OwnerExpense partialUpdatedOwnerExpense = new OwnerExpense();
        partialUpdatedOwnerExpense.setId(ownerExpense.getId());

        partialUpdatedOwnerExpense.amount(UPDATED_AMOUNT).code(UPDATED_CODE).expenseDate(UPDATED_EXPENSE_DATE).notes(UPDATED_NOTES);

        restOwnerExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOwnerExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOwnerExpense))
            )
            .andExpect(status().isOk());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeUpdate);
        OwnerExpense testOwnerExpense = ownerExpenseList.get(ownerExpenseList.size() - 1);
        assertThat(testOwnerExpense.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOwnerExpense.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOwnerExpense.getExpenseDate()).isEqualTo(UPDATED_EXPENSE_DATE);
        assertThat(testOwnerExpense.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingOwnerExpense() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseRepository.findAll().size();
        ownerExpense.setId(count.incrementAndGet());

        // Create the OwnerExpense
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(ownerExpense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ownerExpenseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOwnerExpense() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseRepository.findAll().size();
        ownerExpense.setId(count.incrementAndGet());

        // Create the OwnerExpense
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(ownerExpense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOwnerExpense() throws Exception {
        int databaseSizeBeforeUpdate = ownerExpenseRepository.findAll().size();
        ownerExpense.setId(count.incrementAndGet());

        // Create the OwnerExpense
        OwnerExpenseDTO ownerExpenseDTO = ownerExpenseMapper.toDto(ownerExpense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ownerExpenseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OwnerExpense in the database
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOwnerExpense() throws Exception {
        // Initialize the database
        ownerExpenseRepository.saveAndFlush(ownerExpense);

        int databaseSizeBeforeDelete = ownerExpenseRepository.findAll().size();

        // Delete the ownerExpense
        restOwnerExpenseMockMvc
            .perform(delete(ENTITY_API_URL_ID, ownerExpense.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OwnerExpense> ownerExpenseList = ownerExpenseRepository.findAll();
        assertThat(ownerExpenseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
