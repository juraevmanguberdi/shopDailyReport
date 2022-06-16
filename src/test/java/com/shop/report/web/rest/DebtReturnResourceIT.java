package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.Client;
import com.shop.report.domain.DebtReturn;
import com.shop.report.domain.PaymentMethod;
import com.shop.report.repository.DebtReturnRepository;
import com.shop.report.service.dto.DebtReturnDTO;
import com.shop.report.service.mapper.DebtReturnMapper;
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
 * Integration tests for the {@link DebtReturnResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DebtReturnResourceIT {

    private static final Long DEFAULT_RETURN_AMOUNT = 1L;
    private static final Long UPDATED_RETURN_AMOUNT = 2L;

    private static final LocalDate DEFAULT_RETURN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETURN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/debt-returns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DebtReturnRepository debtReturnRepository;

    @Autowired
    private DebtReturnMapper debtReturnMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDebtReturnMockMvc;

    private DebtReturn debtReturn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DebtReturn createEntity(EntityManager em) {
        DebtReturn debtReturn = new DebtReturn().returnAmount(DEFAULT_RETURN_AMOUNT).returnDate(DEFAULT_RETURN_DATE).code(DEFAULT_CODE);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        debtReturn.setClient(client);
        // Add required entity
        PaymentMethod paymentMethod;
        if (TestUtil.findAll(em, PaymentMethod.class).isEmpty()) {
            paymentMethod = PaymentMethodResourceIT.createEntity(em);
            em.persist(paymentMethod);
            em.flush();
        } else {
            paymentMethod = TestUtil.findAll(em, PaymentMethod.class).get(0);
        }
        debtReturn.setPaymentMethod(paymentMethod);
        return debtReturn;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DebtReturn createUpdatedEntity(EntityManager em) {
        DebtReturn debtReturn = new DebtReturn().returnAmount(UPDATED_RETURN_AMOUNT).returnDate(UPDATED_RETURN_DATE).code(UPDATED_CODE);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        debtReturn.setClient(client);
        // Add required entity
        PaymentMethod paymentMethod;
        if (TestUtil.findAll(em, PaymentMethod.class).isEmpty()) {
            paymentMethod = PaymentMethodResourceIT.createUpdatedEntity(em);
            em.persist(paymentMethod);
            em.flush();
        } else {
            paymentMethod = TestUtil.findAll(em, PaymentMethod.class).get(0);
        }
        debtReturn.setPaymentMethod(paymentMethod);
        return debtReturn;
    }

    @BeforeEach
    public void initTest() {
        debtReturn = createEntity(em);
    }

    @Test
    @Transactional
    void createDebtReturn() throws Exception {
        int databaseSizeBeforeCreate = debtReturnRepository.findAll().size();
        // Create the DebtReturn
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);
        restDebtReturnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtReturnDTO)))
            .andExpect(status().isCreated());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeCreate + 1);
        DebtReturn testDebtReturn = debtReturnList.get(debtReturnList.size() - 1);
        assertThat(testDebtReturn.getReturnAmount()).isEqualTo(DEFAULT_RETURN_AMOUNT);
        assertThat(testDebtReturn.getReturnDate()).isEqualTo(DEFAULT_RETURN_DATE);
        assertThat(testDebtReturn.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createDebtReturnWithExistingId() throws Exception {
        // Create the DebtReturn with an existing ID
        debtReturn.setId(1L);
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);

        int databaseSizeBeforeCreate = debtReturnRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebtReturnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtReturnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReturnAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = debtReturnRepository.findAll().size();
        // set the field null
        debtReturn.setReturnAmount(null);

        // Create the DebtReturn, which fails.
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);

        restDebtReturnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtReturnDTO)))
            .andExpect(status().isBadRequest());

        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReturnDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = debtReturnRepository.findAll().size();
        // set the field null
        debtReturn.setReturnDate(null);

        // Create the DebtReturn, which fails.
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);

        restDebtReturnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtReturnDTO)))
            .andExpect(status().isBadRequest());

        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDebtReturns() throws Exception {
        // Initialize the database
        debtReturnRepository.saveAndFlush(debtReturn);

        // Get all the debtReturnList
        restDebtReturnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debtReturn.getId().intValue())))
            .andExpect(jsonPath("$.[*].returnAmount").value(hasItem(DEFAULT_RETURN_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].returnDate").value(hasItem(DEFAULT_RETURN_DATE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getDebtReturn() throws Exception {
        // Initialize the database
        debtReturnRepository.saveAndFlush(debtReturn);

        // Get the debtReturn
        restDebtReturnMockMvc
            .perform(get(ENTITY_API_URL_ID, debtReturn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(debtReturn.getId().intValue()))
            .andExpect(jsonPath("$.returnAmount").value(DEFAULT_RETURN_AMOUNT.intValue()))
            .andExpect(jsonPath("$.returnDate").value(DEFAULT_RETURN_DATE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingDebtReturn() throws Exception {
        // Get the debtReturn
        restDebtReturnMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDebtReturn() throws Exception {
        // Initialize the database
        debtReturnRepository.saveAndFlush(debtReturn);

        int databaseSizeBeforeUpdate = debtReturnRepository.findAll().size();

        // Update the debtReturn
        DebtReturn updatedDebtReturn = debtReturnRepository.findById(debtReturn.getId()).get();
        // Disconnect from session so that the updates on updatedDebtReturn are not directly saved in db
        em.detach(updatedDebtReturn);
        updatedDebtReturn.returnAmount(UPDATED_RETURN_AMOUNT).returnDate(UPDATED_RETURN_DATE).code(UPDATED_CODE);
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(updatedDebtReturn);

        restDebtReturnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, debtReturnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(debtReturnDTO))
            )
            .andExpect(status().isOk());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeUpdate);
        DebtReturn testDebtReturn = debtReturnList.get(debtReturnList.size() - 1);
        assertThat(testDebtReturn.getReturnAmount()).isEqualTo(UPDATED_RETURN_AMOUNT);
        assertThat(testDebtReturn.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testDebtReturn.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingDebtReturn() throws Exception {
        int databaseSizeBeforeUpdate = debtReturnRepository.findAll().size();
        debtReturn.setId(count.incrementAndGet());

        // Create the DebtReturn
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDebtReturnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, debtReturnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(debtReturnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDebtReturn() throws Exception {
        int databaseSizeBeforeUpdate = debtReturnRepository.findAll().size();
        debtReturn.setId(count.incrementAndGet());

        // Create the DebtReturn
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebtReturnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(debtReturnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDebtReturn() throws Exception {
        int databaseSizeBeforeUpdate = debtReturnRepository.findAll().size();
        debtReturn.setId(count.incrementAndGet());

        // Create the DebtReturn
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebtReturnMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(debtReturnDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDebtReturnWithPatch() throws Exception {
        // Initialize the database
        debtReturnRepository.saveAndFlush(debtReturn);

        int databaseSizeBeforeUpdate = debtReturnRepository.findAll().size();

        // Update the debtReturn using partial update
        DebtReturn partialUpdatedDebtReturn = new DebtReturn();
        partialUpdatedDebtReturn.setId(debtReturn.getId());

        partialUpdatedDebtReturn.returnDate(UPDATED_RETURN_DATE);

        restDebtReturnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDebtReturn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDebtReturn))
            )
            .andExpect(status().isOk());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeUpdate);
        DebtReturn testDebtReturn = debtReturnList.get(debtReturnList.size() - 1);
        assertThat(testDebtReturn.getReturnAmount()).isEqualTo(DEFAULT_RETURN_AMOUNT);
        assertThat(testDebtReturn.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testDebtReturn.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateDebtReturnWithPatch() throws Exception {
        // Initialize the database
        debtReturnRepository.saveAndFlush(debtReturn);

        int databaseSizeBeforeUpdate = debtReturnRepository.findAll().size();

        // Update the debtReturn using partial update
        DebtReturn partialUpdatedDebtReturn = new DebtReturn();
        partialUpdatedDebtReturn.setId(debtReturn.getId());

        partialUpdatedDebtReturn.returnAmount(UPDATED_RETURN_AMOUNT).returnDate(UPDATED_RETURN_DATE).code(UPDATED_CODE);

        restDebtReturnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDebtReturn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDebtReturn))
            )
            .andExpect(status().isOk());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeUpdate);
        DebtReturn testDebtReturn = debtReturnList.get(debtReturnList.size() - 1);
        assertThat(testDebtReturn.getReturnAmount()).isEqualTo(UPDATED_RETURN_AMOUNT);
        assertThat(testDebtReturn.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testDebtReturn.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingDebtReturn() throws Exception {
        int databaseSizeBeforeUpdate = debtReturnRepository.findAll().size();
        debtReturn.setId(count.incrementAndGet());

        // Create the DebtReturn
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDebtReturnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, debtReturnDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(debtReturnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDebtReturn() throws Exception {
        int databaseSizeBeforeUpdate = debtReturnRepository.findAll().size();
        debtReturn.setId(count.incrementAndGet());

        // Create the DebtReturn
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebtReturnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(debtReturnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDebtReturn() throws Exception {
        int databaseSizeBeforeUpdate = debtReturnRepository.findAll().size();
        debtReturn.setId(count.incrementAndGet());

        // Create the DebtReturn
        DebtReturnDTO debtReturnDTO = debtReturnMapper.toDto(debtReturn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebtReturnMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(debtReturnDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DebtReturn in the database
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDebtReturn() throws Exception {
        // Initialize the database
        debtReturnRepository.saveAndFlush(debtReturn);

        int databaseSizeBeforeDelete = debtReturnRepository.findAll().size();

        // Delete the debtReturn
        restDebtReturnMockMvc
            .perform(delete(ENTITY_API_URL_ID, debtReturn.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DebtReturn> debtReturnList = debtReturnRepository.findAll();
        assertThat(debtReturnList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
