package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.Expense;
import com.shop.report.domain.ExpenseType;
import com.shop.report.repository.ExpenseRepository;
import com.shop.report.service.dto.ExpenseDTO;
import com.shop.report.service.mapper.ExpenseMapper;
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
 * Integration tests for the {@link ExpenseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExpenseResourceIT {

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final LocalDate DEFAULT_EXPENSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPENSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/expenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpenseMockMvc;

    private Expense expense;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expense createEntity(EntityManager em) {
        Expense expense = new Expense().amount(DEFAULT_AMOUNT).expenseDate(DEFAULT_EXPENSE_DATE).notes(DEFAULT_NOTES).code(DEFAULT_CODE);
        // Add required entity
        ExpenseType expenseType;
        if (TestUtil.findAll(em, ExpenseType.class).isEmpty()) {
            expenseType = ExpenseTypeResourceIT.createEntity(em);
            em.persist(expenseType);
            em.flush();
        } else {
            expenseType = TestUtil.findAll(em, ExpenseType.class).get(0);
        }
        expense.setExpenseType(expenseType);
        return expense;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expense createUpdatedEntity(EntityManager em) {
        Expense expense = new Expense().amount(UPDATED_AMOUNT).expenseDate(UPDATED_EXPENSE_DATE).notes(UPDATED_NOTES).code(UPDATED_CODE);
        // Add required entity
        ExpenseType expenseType;
        if (TestUtil.findAll(em, ExpenseType.class).isEmpty()) {
            expenseType = ExpenseTypeResourceIT.createUpdatedEntity(em);
            em.persist(expenseType);
            em.flush();
        } else {
            expenseType = TestUtil.findAll(em, ExpenseType.class).get(0);
        }
        expense.setExpenseType(expenseType);
        return expense;
    }

    @BeforeEach
    public void initTest() {
        expense = createEntity(em);
    }

    @Test
    @Transactional
    void createExpense() throws Exception {
        int databaseSizeBeforeCreate = expenseRepository.findAll().size();
        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);
        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenseDTO)))
            .andExpect(status().isCreated());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeCreate + 1);
        Expense testExpense = expenseList.get(expenseList.size() - 1);
        assertThat(testExpense.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testExpense.getExpenseDate()).isEqualTo(DEFAULT_EXPENSE_DATE);
        assertThat(testExpense.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testExpense.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createExpenseWithExistingId() throws Exception {
        // Create the Expense with an existing ID
        expense.setId(1L);
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        int databaseSizeBeforeCreate = expenseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = expenseRepository.findAll().size();
        // set the field null
        expense.setAmount(null);

        // Create the Expense, which fails.
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenseDTO)))
            .andExpect(status().isBadRequest());

        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExpenses() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expense.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].expenseDate").value(hasItem(DEFAULT_EXPENSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getExpense() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get the expense
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL_ID, expense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expense.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.expenseDate").value(DEFAULT_EXPENSE_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingExpense() throws Exception {
        // Get the expense
        restExpenseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExpense() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();

        // Update the expense
        Expense updatedExpense = expenseRepository.findById(expense.getId()).get();
        // Disconnect from session so that the updates on updatedExpense are not directly saved in db
        em.detach(updatedExpense);
        updatedExpense.amount(UPDATED_AMOUNT).expenseDate(UPDATED_EXPENSE_DATE).notes(UPDATED_NOTES).code(UPDATED_CODE);
        ExpenseDTO expenseDTO = expenseMapper.toDto(updatedExpense);

        restExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expenseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expenseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
        Expense testExpense = expenseList.get(expenseList.size() - 1);
        assertThat(testExpense.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testExpense.getExpenseDate()).isEqualTo(UPDATED_EXPENSE_DATE);
        assertThat(testExpense.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testExpense.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expenseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExpenseWithPatch() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();

        // Update the expense using partial update
        Expense partialUpdatedExpense = new Expense();
        partialUpdatedExpense.setId(expense.getId());

        partialUpdatedExpense.notes(UPDATED_NOTES);

        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpense))
            )
            .andExpect(status().isOk());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
        Expense testExpense = expenseList.get(expenseList.size() - 1);
        assertThat(testExpense.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testExpense.getExpenseDate()).isEqualTo(DEFAULT_EXPENSE_DATE);
        assertThat(testExpense.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testExpense.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateExpenseWithPatch() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();

        // Update the expense using partial update
        Expense partialUpdatedExpense = new Expense();
        partialUpdatedExpense.setId(expense.getId());

        partialUpdatedExpense.amount(UPDATED_AMOUNT).expenseDate(UPDATED_EXPENSE_DATE).notes(UPDATED_NOTES).code(UPDATED_CODE);

        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpense))
            )
            .andExpect(status().isOk());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
        Expense testExpense = expenseList.get(expenseList.size() - 1);
        assertThat(testExpense.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testExpense.getExpenseDate()).isEqualTo(UPDATED_EXPENSE_DATE);
        assertThat(testExpense.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testExpense.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, expenseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // Create the Expense
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(expenseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExpense() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        int databaseSizeBeforeDelete = expenseRepository.findAll().size();

        // Delete the expense
        restExpenseMockMvc
            .perform(delete(ENTITY_API_URL_ID, expense.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
