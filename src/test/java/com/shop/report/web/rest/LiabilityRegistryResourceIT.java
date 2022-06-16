package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.LiabilityRegistry;
import com.shop.report.repository.LiabilityRegistryRepository;
import com.shop.report.service.dto.LiabilityRegistryDTO;
import com.shop.report.service.mapper.LiabilityRegistryMapper;
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
 * Integration tests for the {@link LiabilityRegistryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LiabilityRegistryResourceIT {

    private static final LocalDate DEFAULT_TODAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TODAY = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_TOTAL_LIABILITIES = 1L;
    private static final Long UPDATED_TOTAL_LIABILITIES = 2L;

    private static final Long DEFAULT_SUPPLIER = 1L;
    private static final Long UPDATED_SUPPLIER = 2L;

    private static final Long DEFAULT_BANK_LOAN = 1L;
    private static final Long UPDATED_BANK_LOAN = 2L;

    private static final Long DEFAULT_OTHER = 1L;
    private static final Long UPDATED_OTHER = 2L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/liability-registries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LiabilityRegistryRepository liabilityRegistryRepository;

    @Autowired
    private LiabilityRegistryMapper liabilityRegistryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLiabilityRegistryMockMvc;

    private LiabilityRegistry liabilityRegistry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LiabilityRegistry createEntity(EntityManager em) {
        LiabilityRegistry liabilityRegistry = new LiabilityRegistry()
            .today(DEFAULT_TODAY)
            .totalLiabilities(DEFAULT_TOTAL_LIABILITIES)
            .supplier(DEFAULT_SUPPLIER)
            .bankLoan(DEFAULT_BANK_LOAN)
            .other(DEFAULT_OTHER)
            .code(DEFAULT_CODE)
            .notes(DEFAULT_NOTES);
        return liabilityRegistry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LiabilityRegistry createUpdatedEntity(EntityManager em) {
        LiabilityRegistry liabilityRegistry = new LiabilityRegistry()
            .today(UPDATED_TODAY)
            .totalLiabilities(UPDATED_TOTAL_LIABILITIES)
            .supplier(UPDATED_SUPPLIER)
            .bankLoan(UPDATED_BANK_LOAN)
            .other(UPDATED_OTHER)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);
        return liabilityRegistry;
    }

    @BeforeEach
    public void initTest() {
        liabilityRegistry = createEntity(em);
    }

    @Test
    @Transactional
    void createLiabilityRegistry() throws Exception {
        int databaseSizeBeforeCreate = liabilityRegistryRepository.findAll().size();
        // Create the LiabilityRegistry
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(liabilityRegistry);
        restLiabilityRegistryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeCreate + 1);
        LiabilityRegistry testLiabilityRegistry = liabilityRegistryList.get(liabilityRegistryList.size() - 1);
        assertThat(testLiabilityRegistry.getToday()).isEqualTo(DEFAULT_TODAY);
        assertThat(testLiabilityRegistry.getTotalLiabilities()).isEqualTo(DEFAULT_TOTAL_LIABILITIES);
        assertThat(testLiabilityRegistry.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
        assertThat(testLiabilityRegistry.getBankLoan()).isEqualTo(DEFAULT_BANK_LOAN);
        assertThat(testLiabilityRegistry.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testLiabilityRegistry.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLiabilityRegistry.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createLiabilityRegistryWithExistingId() throws Exception {
        // Create the LiabilityRegistry with an existing ID
        liabilityRegistry.setId(1L);
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(liabilityRegistry);

        int databaseSizeBeforeCreate = liabilityRegistryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLiabilityRegistryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTodayIsRequired() throws Exception {
        int databaseSizeBeforeTest = liabilityRegistryRepository.findAll().size();
        // set the field null
        liabilityRegistry.setToday(null);

        // Create the LiabilityRegistry, which fails.
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(liabilityRegistry);

        restLiabilityRegistryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLiabilityRegistries() throws Exception {
        // Initialize the database
        liabilityRegistryRepository.saveAndFlush(liabilityRegistry);

        // Get all the liabilityRegistryList
        restLiabilityRegistryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(liabilityRegistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].today").value(hasItem(DEFAULT_TODAY.toString())))
            .andExpect(jsonPath("$.[*].totalLiabilities").value(hasItem(DEFAULT_TOTAL_LIABILITIES.intValue())))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER.intValue())))
            .andExpect(jsonPath("$.[*].bankLoan").value(hasItem(DEFAULT_BANK_LOAN.intValue())))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getLiabilityRegistry() throws Exception {
        // Initialize the database
        liabilityRegistryRepository.saveAndFlush(liabilityRegistry);

        // Get the liabilityRegistry
        restLiabilityRegistryMockMvc
            .perform(get(ENTITY_API_URL_ID, liabilityRegistry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(liabilityRegistry.getId().intValue()))
            .andExpect(jsonPath("$.today").value(DEFAULT_TODAY.toString()))
            .andExpect(jsonPath("$.totalLiabilities").value(DEFAULT_TOTAL_LIABILITIES.intValue()))
            .andExpect(jsonPath("$.supplier").value(DEFAULT_SUPPLIER.intValue()))
            .andExpect(jsonPath("$.bankLoan").value(DEFAULT_BANK_LOAN.intValue()))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingLiabilityRegistry() throws Exception {
        // Get the liabilityRegistry
        restLiabilityRegistryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLiabilityRegistry() throws Exception {
        // Initialize the database
        liabilityRegistryRepository.saveAndFlush(liabilityRegistry);

        int databaseSizeBeforeUpdate = liabilityRegistryRepository.findAll().size();

        // Update the liabilityRegistry
        LiabilityRegistry updatedLiabilityRegistry = liabilityRegistryRepository.findById(liabilityRegistry.getId()).get();
        // Disconnect from session so that the updates on updatedLiabilityRegistry are not directly saved in db
        em.detach(updatedLiabilityRegistry);
        updatedLiabilityRegistry
            .today(UPDATED_TODAY)
            .totalLiabilities(UPDATED_TOTAL_LIABILITIES)
            .supplier(UPDATED_SUPPLIER)
            .bankLoan(UPDATED_BANK_LOAN)
            .other(UPDATED_OTHER)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(updatedLiabilityRegistry);

        restLiabilityRegistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, liabilityRegistryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isOk());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeUpdate);
        LiabilityRegistry testLiabilityRegistry = liabilityRegistryList.get(liabilityRegistryList.size() - 1);
        assertThat(testLiabilityRegistry.getToday()).isEqualTo(UPDATED_TODAY);
        assertThat(testLiabilityRegistry.getTotalLiabilities()).isEqualTo(UPDATED_TOTAL_LIABILITIES);
        assertThat(testLiabilityRegistry.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testLiabilityRegistry.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testLiabilityRegistry.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testLiabilityRegistry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLiabilityRegistry.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingLiabilityRegistry() throws Exception {
        int databaseSizeBeforeUpdate = liabilityRegistryRepository.findAll().size();
        liabilityRegistry.setId(count.incrementAndGet());

        // Create the LiabilityRegistry
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(liabilityRegistry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLiabilityRegistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, liabilityRegistryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLiabilityRegistry() throws Exception {
        int databaseSizeBeforeUpdate = liabilityRegistryRepository.findAll().size();
        liabilityRegistry.setId(count.incrementAndGet());

        // Create the LiabilityRegistry
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(liabilityRegistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiabilityRegistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLiabilityRegistry() throws Exception {
        int databaseSizeBeforeUpdate = liabilityRegistryRepository.findAll().size();
        liabilityRegistry.setId(count.incrementAndGet());

        // Create the LiabilityRegistry
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(liabilityRegistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiabilityRegistryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLiabilityRegistryWithPatch() throws Exception {
        // Initialize the database
        liabilityRegistryRepository.saveAndFlush(liabilityRegistry);

        int databaseSizeBeforeUpdate = liabilityRegistryRepository.findAll().size();

        // Update the liabilityRegistry using partial update
        LiabilityRegistry partialUpdatedLiabilityRegistry = new LiabilityRegistry();
        partialUpdatedLiabilityRegistry.setId(liabilityRegistry.getId());

        partialUpdatedLiabilityRegistry.today(UPDATED_TODAY).bankLoan(UPDATED_BANK_LOAN).code(UPDATED_CODE);

        restLiabilityRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLiabilityRegistry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLiabilityRegistry))
            )
            .andExpect(status().isOk());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeUpdate);
        LiabilityRegistry testLiabilityRegistry = liabilityRegistryList.get(liabilityRegistryList.size() - 1);
        assertThat(testLiabilityRegistry.getToday()).isEqualTo(UPDATED_TODAY);
        assertThat(testLiabilityRegistry.getTotalLiabilities()).isEqualTo(DEFAULT_TOTAL_LIABILITIES);
        assertThat(testLiabilityRegistry.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
        assertThat(testLiabilityRegistry.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testLiabilityRegistry.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testLiabilityRegistry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLiabilityRegistry.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateLiabilityRegistryWithPatch() throws Exception {
        // Initialize the database
        liabilityRegistryRepository.saveAndFlush(liabilityRegistry);

        int databaseSizeBeforeUpdate = liabilityRegistryRepository.findAll().size();

        // Update the liabilityRegistry using partial update
        LiabilityRegistry partialUpdatedLiabilityRegistry = new LiabilityRegistry();
        partialUpdatedLiabilityRegistry.setId(liabilityRegistry.getId());

        partialUpdatedLiabilityRegistry
            .today(UPDATED_TODAY)
            .totalLiabilities(UPDATED_TOTAL_LIABILITIES)
            .supplier(UPDATED_SUPPLIER)
            .bankLoan(UPDATED_BANK_LOAN)
            .other(UPDATED_OTHER)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);

        restLiabilityRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLiabilityRegistry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLiabilityRegistry))
            )
            .andExpect(status().isOk());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeUpdate);
        LiabilityRegistry testLiabilityRegistry = liabilityRegistryList.get(liabilityRegistryList.size() - 1);
        assertThat(testLiabilityRegistry.getToday()).isEqualTo(UPDATED_TODAY);
        assertThat(testLiabilityRegistry.getTotalLiabilities()).isEqualTo(UPDATED_TOTAL_LIABILITIES);
        assertThat(testLiabilityRegistry.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testLiabilityRegistry.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testLiabilityRegistry.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testLiabilityRegistry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLiabilityRegistry.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingLiabilityRegistry() throws Exception {
        int databaseSizeBeforeUpdate = liabilityRegistryRepository.findAll().size();
        liabilityRegistry.setId(count.incrementAndGet());

        // Create the LiabilityRegistry
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(liabilityRegistry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLiabilityRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, liabilityRegistryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLiabilityRegistry() throws Exception {
        int databaseSizeBeforeUpdate = liabilityRegistryRepository.findAll().size();
        liabilityRegistry.setId(count.incrementAndGet());

        // Create the LiabilityRegistry
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(liabilityRegistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiabilityRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLiabilityRegistry() throws Exception {
        int databaseSizeBeforeUpdate = liabilityRegistryRepository.findAll().size();
        liabilityRegistry.setId(count.incrementAndGet());

        // Create the LiabilityRegistry
        LiabilityRegistryDTO liabilityRegistryDTO = liabilityRegistryMapper.toDto(liabilityRegistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiabilityRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(liabilityRegistryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LiabilityRegistry in the database
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLiabilityRegistry() throws Exception {
        // Initialize the database
        liabilityRegistryRepository.saveAndFlush(liabilityRegistry);

        int databaseSizeBeforeDelete = liabilityRegistryRepository.findAll().size();

        // Delete the liabilityRegistry
        restLiabilityRegistryMockMvc
            .perform(delete(ENTITY_API_URL_ID, liabilityRegistry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LiabilityRegistry> liabilityRegistryList = liabilityRegistryRepository.findAll();
        assertThat(liabilityRegistryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
