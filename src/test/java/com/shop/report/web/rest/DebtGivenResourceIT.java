package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.Client;
import com.shop.report.domain.DebtGiven;
import com.shop.report.repository.DebtGivenRepository;
import com.shop.report.service.dto.DebtGivenDTO;
import com.shop.report.service.mapper.DebtGivenMapper;
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
 * Integration tests for the {@link DebtGivenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DebtGivenResourceIT {

    private static final Long DEFAULT_DEBT_AMOUNT = 1L;
    private static final Long UPDATED_DEBT_AMOUNT = 2L;

    private static final LocalDate DEFAULT_DEBT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RETURN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETURN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/debt-givens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DebtGivenRepository debtGivenRepository;

    @Autowired
    private DebtGivenMapper debtGivenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDebtGivenMockMvc;

    private DebtGiven debtGiven;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DebtGiven createEntity(EntityManager em) {
        DebtGiven debtGiven = new DebtGiven()
            .debtAmount(DEFAULT_DEBT_AMOUNT)
            .debtDate(DEFAULT_DEBT_DATE)
            .returnDate(DEFAULT_RETURN_DATE)
            .code(DEFAULT_CODE)
            .notes(DEFAULT_NOTES);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        debtGiven.setClient(client);
        return debtGiven;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DebtGiven createUpdatedEntity(EntityManager em) {
        DebtGiven debtGiven = new DebtGiven()
            .debtAmount(UPDATED_DEBT_AMOUNT)
            .debtDate(UPDATED_DEBT_DATE)
            .returnDate(UPDATED_RETURN_DATE)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        debtGiven.setClient(client);
        return debtGiven;
    }

    @BeforeEach
    public void initTest() {
        debtGiven = createEntity(em);
    }

    @Test
    @Transactional
    void createDebtGiven() throws Exception {
        int databaseSizeBeforeCreate = debtGivenRepository.findAll().size();
        // Create the DebtGiven
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);
        restDebtGivenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtGivenDTO)))
            .andExpect(status().isCreated());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeCreate + 1);
        DebtGiven testDebtGiven = debtGivenList.get(debtGivenList.size() - 1);
        assertThat(testDebtGiven.getDebtAmount()).isEqualTo(DEFAULT_DEBT_AMOUNT);
        assertThat(testDebtGiven.getDebtDate()).isEqualTo(DEFAULT_DEBT_DATE);
        assertThat(testDebtGiven.getReturnDate()).isEqualTo(DEFAULT_RETURN_DATE);
        assertThat(testDebtGiven.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDebtGiven.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createDebtGivenWithExistingId() throws Exception {
        // Create the DebtGiven with an existing ID
        debtGiven.setId(1L);
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        int databaseSizeBeforeCreate = debtGivenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebtGivenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtGivenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDebtAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = debtGivenRepository.findAll().size();
        // set the field null
        debtGiven.setDebtAmount(null);

        // Create the DebtGiven, which fails.
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        restDebtGivenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtGivenDTO)))
            .andExpect(status().isBadRequest());

        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDebtDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = debtGivenRepository.findAll().size();
        // set the field null
        debtGiven.setDebtDate(null);

        // Create the DebtGiven, which fails.
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        restDebtGivenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtGivenDTO)))
            .andExpect(status().isBadRequest());

        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReturnDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = debtGivenRepository.findAll().size();
        // set the field null
        debtGiven.setReturnDate(null);

        // Create the DebtGiven, which fails.
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        restDebtGivenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtGivenDTO)))
            .andExpect(status().isBadRequest());

        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDebtGivens() throws Exception {
        // Initialize the database
        debtGivenRepository.saveAndFlush(debtGiven);

        // Get all the debtGivenList
        restDebtGivenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debtGiven.getId().intValue())))
            .andExpect(jsonPath("$.[*].debtAmount").value(hasItem(DEFAULT_DEBT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].debtDate").value(hasItem(DEFAULT_DEBT_DATE.toString())))
            .andExpect(jsonPath("$.[*].returnDate").value(hasItem(DEFAULT_RETURN_DATE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getDebtGiven() throws Exception {
        // Initialize the database
        debtGivenRepository.saveAndFlush(debtGiven);

        // Get the debtGiven
        restDebtGivenMockMvc
            .perform(get(ENTITY_API_URL_ID, debtGiven.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(debtGiven.getId().intValue()))
            .andExpect(jsonPath("$.debtAmount").value(DEFAULT_DEBT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.debtDate").value(DEFAULT_DEBT_DATE.toString()))
            .andExpect(jsonPath("$.returnDate").value(DEFAULT_RETURN_DATE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingDebtGiven() throws Exception {
        // Get the debtGiven
        restDebtGivenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDebtGiven() throws Exception {
        // Initialize the database
        debtGivenRepository.saveAndFlush(debtGiven);

        int databaseSizeBeforeUpdate = debtGivenRepository.findAll().size();

        // Update the debtGiven
        DebtGiven updatedDebtGiven = debtGivenRepository.findById(debtGiven.getId()).get();
        // Disconnect from session so that the updates on updatedDebtGiven are not directly saved in db
        em.detach(updatedDebtGiven);
        updatedDebtGiven
            .debtAmount(UPDATED_DEBT_AMOUNT)
            .debtDate(UPDATED_DEBT_DATE)
            .returnDate(UPDATED_RETURN_DATE)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(updatedDebtGiven);

        restDebtGivenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, debtGivenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(debtGivenDTO))
            )
            .andExpect(status().isOk());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeUpdate);
        DebtGiven testDebtGiven = debtGivenList.get(debtGivenList.size() - 1);
        assertThat(testDebtGiven.getDebtAmount()).isEqualTo(UPDATED_DEBT_AMOUNT);
        assertThat(testDebtGiven.getDebtDate()).isEqualTo(UPDATED_DEBT_DATE);
        assertThat(testDebtGiven.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testDebtGiven.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDebtGiven.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingDebtGiven() throws Exception {
        int databaseSizeBeforeUpdate = debtGivenRepository.findAll().size();
        debtGiven.setId(count.incrementAndGet());

        // Create the DebtGiven
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDebtGivenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, debtGivenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(debtGivenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDebtGiven() throws Exception {
        int databaseSizeBeforeUpdate = debtGivenRepository.findAll().size();
        debtGiven.setId(count.incrementAndGet());

        // Create the DebtGiven
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebtGivenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(debtGivenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDebtGiven() throws Exception {
        int databaseSizeBeforeUpdate = debtGivenRepository.findAll().size();
        debtGiven.setId(count.incrementAndGet());

        // Create the DebtGiven
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebtGivenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtGivenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDebtGivenWithPatch() throws Exception {
        // Initialize the database
        debtGivenRepository.saveAndFlush(debtGiven);

        int databaseSizeBeforeUpdate = debtGivenRepository.findAll().size();

        // Update the debtGiven using partial update
        DebtGiven partialUpdatedDebtGiven = new DebtGiven();
        partialUpdatedDebtGiven.setId(debtGiven.getId());

        partialUpdatedDebtGiven.debtAmount(UPDATED_DEBT_AMOUNT).code(UPDATED_CODE).notes(UPDATED_NOTES);

        restDebtGivenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDebtGiven.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDebtGiven))
            )
            .andExpect(status().isOk());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeUpdate);
        DebtGiven testDebtGiven = debtGivenList.get(debtGivenList.size() - 1);
        assertThat(testDebtGiven.getDebtAmount()).isEqualTo(UPDATED_DEBT_AMOUNT);
        assertThat(testDebtGiven.getDebtDate()).isEqualTo(DEFAULT_DEBT_DATE);
        assertThat(testDebtGiven.getReturnDate()).isEqualTo(DEFAULT_RETURN_DATE);
        assertThat(testDebtGiven.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDebtGiven.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateDebtGivenWithPatch() throws Exception {
        // Initialize the database
        debtGivenRepository.saveAndFlush(debtGiven);

        int databaseSizeBeforeUpdate = debtGivenRepository.findAll().size();

        // Update the debtGiven using partial update
        DebtGiven partialUpdatedDebtGiven = new DebtGiven();
        partialUpdatedDebtGiven.setId(debtGiven.getId());

        partialUpdatedDebtGiven
            .debtAmount(UPDATED_DEBT_AMOUNT)
            .debtDate(UPDATED_DEBT_DATE)
            .returnDate(UPDATED_RETURN_DATE)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);

        restDebtGivenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDebtGiven.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDebtGiven))
            )
            .andExpect(status().isOk());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeUpdate);
        DebtGiven testDebtGiven = debtGivenList.get(debtGivenList.size() - 1);
        assertThat(testDebtGiven.getDebtAmount()).isEqualTo(UPDATED_DEBT_AMOUNT);
        assertThat(testDebtGiven.getDebtDate()).isEqualTo(UPDATED_DEBT_DATE);
        assertThat(testDebtGiven.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testDebtGiven.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDebtGiven.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingDebtGiven() throws Exception {
        int databaseSizeBeforeUpdate = debtGivenRepository.findAll().size();
        debtGiven.setId(count.incrementAndGet());

        // Create the DebtGiven
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDebtGivenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, debtGivenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(debtGivenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDebtGiven() throws Exception {
        int databaseSizeBeforeUpdate = debtGivenRepository.findAll().size();
        debtGiven.setId(count.incrementAndGet());

        // Create the DebtGiven
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebtGivenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(debtGivenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDebtGiven() throws Exception {
        int databaseSizeBeforeUpdate = debtGivenRepository.findAll().size();
        debtGiven.setId(count.incrementAndGet());

        // Create the DebtGiven
        DebtGivenDTO debtGivenDTO = debtGivenMapper.toDto(debtGiven);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebtGivenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(debtGivenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DebtGiven in the database
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDebtGiven() throws Exception {
        // Initialize the database
        debtGivenRepository.saveAndFlush(debtGiven);

        int databaseSizeBeforeDelete = debtGivenRepository.findAll().size();

        // Delete the debtGiven
        restDebtGivenMockMvc
            .perform(delete(ENTITY_API_URL_ID, debtGiven.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DebtGiven> debtGivenList = debtGivenRepository.findAll();
        assertThat(debtGivenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
