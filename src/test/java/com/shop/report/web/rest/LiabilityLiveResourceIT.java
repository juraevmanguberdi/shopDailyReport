package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.LiabilityLive;
import com.shop.report.repository.LiabilityLiveRepository;
import com.shop.report.service.dto.LiabilityLiveDTO;
import com.shop.report.service.mapper.LiabilityLiveMapper;
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
 * Integration tests for the {@link LiabilityLiveResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LiabilityLiveResourceIT {

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

    private static final String ENTITY_API_URL = "/api/liability-lives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LiabilityLiveRepository liabilityLiveRepository;

    @Autowired
    private LiabilityLiveMapper liabilityLiveMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLiabilityLiveMockMvc;

    private LiabilityLive liabilityLive;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LiabilityLive createEntity(EntityManager em) {
        LiabilityLive liabilityLive = new LiabilityLive()
            .totalLiabilities(DEFAULT_TOTAL_LIABILITIES)
            .supplier(DEFAULT_SUPPLIER)
            .bankLoan(DEFAULT_BANK_LOAN)
            .other(DEFAULT_OTHER)
            .code(DEFAULT_CODE)
            .notes(DEFAULT_NOTES);
        return liabilityLive;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LiabilityLive createUpdatedEntity(EntityManager em) {
        LiabilityLive liabilityLive = new LiabilityLive()
            .totalLiabilities(UPDATED_TOTAL_LIABILITIES)
            .supplier(UPDATED_SUPPLIER)
            .bankLoan(UPDATED_BANK_LOAN)
            .other(UPDATED_OTHER)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);
        return liabilityLive;
    }

    @BeforeEach
    public void initTest() {
        liabilityLive = createEntity(em);
    }

    @Test
    @Transactional
    void createLiabilityLive() throws Exception {
        int databaseSizeBeforeCreate = liabilityLiveRepository.findAll().size();
        // Create the LiabilityLive
        LiabilityLiveDTO liabilityLiveDTO = liabilityLiveMapper.toDto(liabilityLive);
        restLiabilityLiveMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(liabilityLiveDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeCreate + 1);
        LiabilityLive testLiabilityLive = liabilityLiveList.get(liabilityLiveList.size() - 1);
        assertThat(testLiabilityLive.getTotalLiabilities()).isEqualTo(DEFAULT_TOTAL_LIABILITIES);
        assertThat(testLiabilityLive.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
        assertThat(testLiabilityLive.getBankLoan()).isEqualTo(DEFAULT_BANK_LOAN);
        assertThat(testLiabilityLive.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testLiabilityLive.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLiabilityLive.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createLiabilityLiveWithExistingId() throws Exception {
        // Create the LiabilityLive with an existing ID
        liabilityLive.setId(1L);
        LiabilityLiveDTO liabilityLiveDTO = liabilityLiveMapper.toDto(liabilityLive);

        int databaseSizeBeforeCreate = liabilityLiveRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLiabilityLiveMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(liabilityLiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLiabilityLives() throws Exception {
        // Initialize the database
        liabilityLiveRepository.saveAndFlush(liabilityLive);

        // Get all the liabilityLiveList
        restLiabilityLiveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(liabilityLive.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalLiabilities").value(hasItem(DEFAULT_TOTAL_LIABILITIES.intValue())))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER.intValue())))
            .andExpect(jsonPath("$.[*].bankLoan").value(hasItem(DEFAULT_BANK_LOAN.intValue())))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getLiabilityLive() throws Exception {
        // Initialize the database
        liabilityLiveRepository.saveAndFlush(liabilityLive);

        // Get the liabilityLive
        restLiabilityLiveMockMvc
            .perform(get(ENTITY_API_URL_ID, liabilityLive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(liabilityLive.getId().intValue()))
            .andExpect(jsonPath("$.totalLiabilities").value(DEFAULT_TOTAL_LIABILITIES.intValue()))
            .andExpect(jsonPath("$.supplier").value(DEFAULT_SUPPLIER.intValue()))
            .andExpect(jsonPath("$.bankLoan").value(DEFAULT_BANK_LOAN.intValue()))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingLiabilityLive() throws Exception {
        // Get the liabilityLive
        restLiabilityLiveMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLiabilityLive() throws Exception {
        // Initialize the database
        liabilityLiveRepository.saveAndFlush(liabilityLive);

        int databaseSizeBeforeUpdate = liabilityLiveRepository.findAll().size();

        // Update the liabilityLive
        LiabilityLive updatedLiabilityLive = liabilityLiveRepository.findById(liabilityLive.getId()).get();
        // Disconnect from session so that the updates on updatedLiabilityLive are not directly saved in db
        em.detach(updatedLiabilityLive);
        updatedLiabilityLive
            .totalLiabilities(UPDATED_TOTAL_LIABILITIES)
            .supplier(UPDATED_SUPPLIER)
            .bankLoan(UPDATED_BANK_LOAN)
            .other(UPDATED_OTHER)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);
        LiabilityLiveDTO liabilityLiveDTO = liabilityLiveMapper.toDto(updatedLiabilityLive);

        restLiabilityLiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, liabilityLiveDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liabilityLiveDTO))
            )
            .andExpect(status().isOk());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeUpdate);
        LiabilityLive testLiabilityLive = liabilityLiveList.get(liabilityLiveList.size() - 1);
        assertThat(testLiabilityLive.getTotalLiabilities()).isEqualTo(UPDATED_TOTAL_LIABILITIES);
        assertThat(testLiabilityLive.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testLiabilityLive.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testLiabilityLive.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testLiabilityLive.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLiabilityLive.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingLiabilityLive() throws Exception {
        int databaseSizeBeforeUpdate = liabilityLiveRepository.findAll().size();
        liabilityLive.setId(count.incrementAndGet());

        // Create the LiabilityLive
        LiabilityLiveDTO liabilityLiveDTO = liabilityLiveMapper.toDto(liabilityLive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLiabilityLiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, liabilityLiveDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liabilityLiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLiabilityLive() throws Exception {
        int databaseSizeBeforeUpdate = liabilityLiveRepository.findAll().size();
        liabilityLive.setId(count.incrementAndGet());

        // Create the LiabilityLive
        LiabilityLiveDTO liabilityLiveDTO = liabilityLiveMapper.toDto(liabilityLive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiabilityLiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(liabilityLiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLiabilityLive() throws Exception {
        int databaseSizeBeforeUpdate = liabilityLiveRepository.findAll().size();
        liabilityLive.setId(count.incrementAndGet());

        // Create the LiabilityLive
        LiabilityLiveDTO liabilityLiveDTO = liabilityLiveMapper.toDto(liabilityLive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiabilityLiveMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(liabilityLiveDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLiabilityLiveWithPatch() throws Exception {
        // Initialize the database
        liabilityLiveRepository.saveAndFlush(liabilityLive);

        int databaseSizeBeforeUpdate = liabilityLiveRepository.findAll().size();

        // Update the liabilityLive using partial update
        LiabilityLive partialUpdatedLiabilityLive = new LiabilityLive();
        partialUpdatedLiabilityLive.setId(liabilityLive.getId());

        partialUpdatedLiabilityLive.supplier(UPDATED_SUPPLIER).bankLoan(UPDATED_BANK_LOAN).code(UPDATED_CODE);

        restLiabilityLiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLiabilityLive.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLiabilityLive))
            )
            .andExpect(status().isOk());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeUpdate);
        LiabilityLive testLiabilityLive = liabilityLiveList.get(liabilityLiveList.size() - 1);
        assertThat(testLiabilityLive.getTotalLiabilities()).isEqualTo(DEFAULT_TOTAL_LIABILITIES);
        assertThat(testLiabilityLive.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testLiabilityLive.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testLiabilityLive.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testLiabilityLive.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLiabilityLive.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateLiabilityLiveWithPatch() throws Exception {
        // Initialize the database
        liabilityLiveRepository.saveAndFlush(liabilityLive);

        int databaseSizeBeforeUpdate = liabilityLiveRepository.findAll().size();

        // Update the liabilityLive using partial update
        LiabilityLive partialUpdatedLiabilityLive = new LiabilityLive();
        partialUpdatedLiabilityLive.setId(liabilityLive.getId());

        partialUpdatedLiabilityLive
            .totalLiabilities(UPDATED_TOTAL_LIABILITIES)
            .supplier(UPDATED_SUPPLIER)
            .bankLoan(UPDATED_BANK_LOAN)
            .other(UPDATED_OTHER)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);

        restLiabilityLiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLiabilityLive.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLiabilityLive))
            )
            .andExpect(status().isOk());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeUpdate);
        LiabilityLive testLiabilityLive = liabilityLiveList.get(liabilityLiveList.size() - 1);
        assertThat(testLiabilityLive.getTotalLiabilities()).isEqualTo(UPDATED_TOTAL_LIABILITIES);
        assertThat(testLiabilityLive.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testLiabilityLive.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testLiabilityLive.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testLiabilityLive.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLiabilityLive.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingLiabilityLive() throws Exception {
        int databaseSizeBeforeUpdate = liabilityLiveRepository.findAll().size();
        liabilityLive.setId(count.incrementAndGet());

        // Create the LiabilityLive
        LiabilityLiveDTO liabilityLiveDTO = liabilityLiveMapper.toDto(liabilityLive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLiabilityLiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, liabilityLiveDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(liabilityLiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLiabilityLive() throws Exception {
        int databaseSizeBeforeUpdate = liabilityLiveRepository.findAll().size();
        liabilityLive.setId(count.incrementAndGet());

        // Create the LiabilityLive
        LiabilityLiveDTO liabilityLiveDTO = liabilityLiveMapper.toDto(liabilityLive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiabilityLiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(liabilityLiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLiabilityLive() throws Exception {
        int databaseSizeBeforeUpdate = liabilityLiveRepository.findAll().size();
        liabilityLive.setId(count.incrementAndGet());

        // Create the LiabilityLive
        LiabilityLiveDTO liabilityLiveDTO = liabilityLiveMapper.toDto(liabilityLive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLiabilityLiveMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(liabilityLiveDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LiabilityLive in the database
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLiabilityLive() throws Exception {
        // Initialize the database
        liabilityLiveRepository.saveAndFlush(liabilityLive);

        int databaseSizeBeforeDelete = liabilityLiveRepository.findAll().size();

        // Delete the liabilityLive
        restLiabilityLiveMockMvc
            .perform(delete(ENTITY_API_URL_ID, liabilityLive.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LiabilityLive> liabilityLiveList = liabilityLiveRepository.findAll();
        assertThat(liabilityLiveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
