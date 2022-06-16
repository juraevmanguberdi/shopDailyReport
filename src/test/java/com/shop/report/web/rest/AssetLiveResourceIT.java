package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.AssetLive;
import com.shop.report.repository.AssetLiveRepository;
import com.shop.report.service.dto.AssetLiveDTO;
import com.shop.report.service.mapper.AssetLiveMapper;
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
 * Integration tests for the {@link AssetLiveResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetLiveResourceIT {

    private static final Long DEFAULT_TOTAL_ASSETS = 1L;
    private static final Long UPDATED_TOTAL_ASSETS = 2L;

    private static final Long DEFAULT_CURRENT_ASSETS = 1L;
    private static final Long UPDATED_CURRENT_ASSETS = 2L;

    private static final Long DEFAULT_MONEY_TOTAL = 1L;
    private static final Long UPDATED_MONEY_TOTAL = 2L;

    private static final Long DEFAULT_CASH_SHOP = 1L;
    private static final Long UPDATED_CASH_SHOP = 2L;

    private static final Long DEFAULT_CASH_OWNER = 1L;
    private static final Long UPDATED_CASH_OWNER = 2L;

    private static final Long DEFAULT_BANK_ACCOUNT = 1L;
    private static final Long UPDATED_BANK_ACCOUNT = 2L;

    private static final Long DEFAULT_GOODS = 1L;
    private static final Long UPDATED_GOODS = 2L;

    private static final Long DEFAULT_DEBITOR = 1L;
    private static final Long UPDATED_DEBITOR = 2L;

    private static final Long DEFAULT_LONG_TERM_ASSETS = 1L;
    private static final Long UPDATED_LONG_TERM_ASSETS = 2L;

    private static final Long DEFAULT_TRANSPORT = 1L;
    private static final Long UPDATED_TRANSPORT = 2L;

    private static final Long DEFAULT_EQUIPMENT = 1L;
    private static final Long UPDATED_EQUIPMENT = 2L;

    private static final Long DEFAULT_REAL_ESTATE = 1L;
    private static final Long UPDATED_REAL_ESTATE = 2L;

    private static final Long DEFAULT_OTHER = 1L;
    private static final Long UPDATED_OTHER = 2L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/asset-lives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssetLiveRepository assetLiveRepository;

    @Autowired
    private AssetLiveMapper assetLiveMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetLiveMockMvc;

    private AssetLive assetLive;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetLive createEntity(EntityManager em) {
        AssetLive assetLive = new AssetLive()
            .totalAssets(DEFAULT_TOTAL_ASSETS)
            .currentAssets(DEFAULT_CURRENT_ASSETS)
            .moneyTotal(DEFAULT_MONEY_TOTAL)
            .cashShop(DEFAULT_CASH_SHOP)
            .cashOwner(DEFAULT_CASH_OWNER)
            .bankAccount(DEFAULT_BANK_ACCOUNT)
            .goods(DEFAULT_GOODS)
            .debitor(DEFAULT_DEBITOR)
            .longTermAssets(DEFAULT_LONG_TERM_ASSETS)
            .transport(DEFAULT_TRANSPORT)
            .equipment(DEFAULT_EQUIPMENT)
            .realEstate(DEFAULT_REAL_ESTATE)
            .other(DEFAULT_OTHER)
            .code(DEFAULT_CODE)
            .notes(DEFAULT_NOTES);
        return assetLive;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetLive createUpdatedEntity(EntityManager em) {
        AssetLive assetLive = new AssetLive()
            .totalAssets(UPDATED_TOTAL_ASSETS)
            .currentAssets(UPDATED_CURRENT_ASSETS)
            .moneyTotal(UPDATED_MONEY_TOTAL)
            .cashShop(UPDATED_CASH_SHOP)
            .cashOwner(UPDATED_CASH_OWNER)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .goods(UPDATED_GOODS)
            .debitor(UPDATED_DEBITOR)
            .longTermAssets(UPDATED_LONG_TERM_ASSETS)
            .transport(UPDATED_TRANSPORT)
            .equipment(UPDATED_EQUIPMENT)
            .realEstate(UPDATED_REAL_ESTATE)
            .other(UPDATED_OTHER)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);
        return assetLive;
    }

    @BeforeEach
    public void initTest() {
        assetLive = createEntity(em);
    }

    @Test
    @Transactional
    void createAssetLive() throws Exception {
        int databaseSizeBeforeCreate = assetLiveRepository.findAll().size();
        // Create the AssetLive
        AssetLiveDTO assetLiveDTO = assetLiveMapper.toDto(assetLive);
        restAssetLiveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetLiveDTO)))
            .andExpect(status().isCreated());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeCreate + 1);
        AssetLive testAssetLive = assetLiveList.get(assetLiveList.size() - 1);
        assertThat(testAssetLive.getTotalAssets()).isEqualTo(DEFAULT_TOTAL_ASSETS);
        assertThat(testAssetLive.getCurrentAssets()).isEqualTo(DEFAULT_CURRENT_ASSETS);
        assertThat(testAssetLive.getMoneyTotal()).isEqualTo(DEFAULT_MONEY_TOTAL);
        assertThat(testAssetLive.getCashShop()).isEqualTo(DEFAULT_CASH_SHOP);
        assertThat(testAssetLive.getCashOwner()).isEqualTo(DEFAULT_CASH_OWNER);
        assertThat(testAssetLive.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testAssetLive.getGoods()).isEqualTo(DEFAULT_GOODS);
        assertThat(testAssetLive.getDebitor()).isEqualTo(DEFAULT_DEBITOR);
        assertThat(testAssetLive.getLongTermAssets()).isEqualTo(DEFAULT_LONG_TERM_ASSETS);
        assertThat(testAssetLive.getTransport()).isEqualTo(DEFAULT_TRANSPORT);
        assertThat(testAssetLive.getEquipment()).isEqualTo(DEFAULT_EQUIPMENT);
        assertThat(testAssetLive.getRealEstate()).isEqualTo(DEFAULT_REAL_ESTATE);
        assertThat(testAssetLive.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testAssetLive.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAssetLive.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createAssetLiveWithExistingId() throws Exception {
        // Create the AssetLive with an existing ID
        assetLive.setId(1L);
        AssetLiveDTO assetLiveDTO = assetLiveMapper.toDto(assetLive);

        int databaseSizeBeforeCreate = assetLiveRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetLiveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetLiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssetLives() throws Exception {
        // Initialize the database
        assetLiveRepository.saveAndFlush(assetLive);

        // Get all the assetLiveList
        restAssetLiveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetLive.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAssets").value(hasItem(DEFAULT_TOTAL_ASSETS.intValue())))
            .andExpect(jsonPath("$.[*].currentAssets").value(hasItem(DEFAULT_CURRENT_ASSETS.intValue())))
            .andExpect(jsonPath("$.[*].moneyTotal").value(hasItem(DEFAULT_MONEY_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].cashShop").value(hasItem(DEFAULT_CASH_SHOP.intValue())))
            .andExpect(jsonPath("$.[*].cashOwner").value(hasItem(DEFAULT_CASH_OWNER.intValue())))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].goods").value(hasItem(DEFAULT_GOODS.intValue())))
            .andExpect(jsonPath("$.[*].debitor").value(hasItem(DEFAULT_DEBITOR.intValue())))
            .andExpect(jsonPath("$.[*].longTermAssets").value(hasItem(DEFAULT_LONG_TERM_ASSETS.intValue())))
            .andExpect(jsonPath("$.[*].transport").value(hasItem(DEFAULT_TRANSPORT.intValue())))
            .andExpect(jsonPath("$.[*].equipment").value(hasItem(DEFAULT_EQUIPMENT.intValue())))
            .andExpect(jsonPath("$.[*].realEstate").value(hasItem(DEFAULT_REAL_ESTATE.intValue())))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getAssetLive() throws Exception {
        // Initialize the database
        assetLiveRepository.saveAndFlush(assetLive);

        // Get the assetLive
        restAssetLiveMockMvc
            .perform(get(ENTITY_API_URL_ID, assetLive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetLive.getId().intValue()))
            .andExpect(jsonPath("$.totalAssets").value(DEFAULT_TOTAL_ASSETS.intValue()))
            .andExpect(jsonPath("$.currentAssets").value(DEFAULT_CURRENT_ASSETS.intValue()))
            .andExpect(jsonPath("$.moneyTotal").value(DEFAULT_MONEY_TOTAL.intValue()))
            .andExpect(jsonPath("$.cashShop").value(DEFAULT_CASH_SHOP.intValue()))
            .andExpect(jsonPath("$.cashOwner").value(DEFAULT_CASH_OWNER.intValue()))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT.intValue()))
            .andExpect(jsonPath("$.goods").value(DEFAULT_GOODS.intValue()))
            .andExpect(jsonPath("$.debitor").value(DEFAULT_DEBITOR.intValue()))
            .andExpect(jsonPath("$.longTermAssets").value(DEFAULT_LONG_TERM_ASSETS.intValue()))
            .andExpect(jsonPath("$.transport").value(DEFAULT_TRANSPORT.intValue()))
            .andExpect(jsonPath("$.equipment").value(DEFAULT_EQUIPMENT.intValue()))
            .andExpect(jsonPath("$.realEstate").value(DEFAULT_REAL_ESTATE.intValue()))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingAssetLive() throws Exception {
        // Get the assetLive
        restAssetLiveMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAssetLive() throws Exception {
        // Initialize the database
        assetLiveRepository.saveAndFlush(assetLive);

        int databaseSizeBeforeUpdate = assetLiveRepository.findAll().size();

        // Update the assetLive
        AssetLive updatedAssetLive = assetLiveRepository.findById(assetLive.getId()).get();
        // Disconnect from session so that the updates on updatedAssetLive are not directly saved in db
        em.detach(updatedAssetLive);
        updatedAssetLive
            .totalAssets(UPDATED_TOTAL_ASSETS)
            .currentAssets(UPDATED_CURRENT_ASSETS)
            .moneyTotal(UPDATED_MONEY_TOTAL)
            .cashShop(UPDATED_CASH_SHOP)
            .cashOwner(UPDATED_CASH_OWNER)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .goods(UPDATED_GOODS)
            .debitor(UPDATED_DEBITOR)
            .longTermAssets(UPDATED_LONG_TERM_ASSETS)
            .transport(UPDATED_TRANSPORT)
            .equipment(UPDATED_EQUIPMENT)
            .realEstate(UPDATED_REAL_ESTATE)
            .other(UPDATED_OTHER)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);
        AssetLiveDTO assetLiveDTO = assetLiveMapper.toDto(updatedAssetLive);

        restAssetLiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetLiveDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetLiveDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeUpdate);
        AssetLive testAssetLive = assetLiveList.get(assetLiveList.size() - 1);
        assertThat(testAssetLive.getTotalAssets()).isEqualTo(UPDATED_TOTAL_ASSETS);
        assertThat(testAssetLive.getCurrentAssets()).isEqualTo(UPDATED_CURRENT_ASSETS);
        assertThat(testAssetLive.getMoneyTotal()).isEqualTo(UPDATED_MONEY_TOTAL);
        assertThat(testAssetLive.getCashShop()).isEqualTo(UPDATED_CASH_SHOP);
        assertThat(testAssetLive.getCashOwner()).isEqualTo(UPDATED_CASH_OWNER);
        assertThat(testAssetLive.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testAssetLive.getGoods()).isEqualTo(UPDATED_GOODS);
        assertThat(testAssetLive.getDebitor()).isEqualTo(UPDATED_DEBITOR);
        assertThat(testAssetLive.getLongTermAssets()).isEqualTo(UPDATED_LONG_TERM_ASSETS);
        assertThat(testAssetLive.getTransport()).isEqualTo(UPDATED_TRANSPORT);
        assertThat(testAssetLive.getEquipment()).isEqualTo(UPDATED_EQUIPMENT);
        assertThat(testAssetLive.getRealEstate()).isEqualTo(UPDATED_REAL_ESTATE);
        assertThat(testAssetLive.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testAssetLive.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAssetLive.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingAssetLive() throws Exception {
        int databaseSizeBeforeUpdate = assetLiveRepository.findAll().size();
        assetLive.setId(count.incrementAndGet());

        // Create the AssetLive
        AssetLiveDTO assetLiveDTO = assetLiveMapper.toDto(assetLive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetLiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetLiveDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetLiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetLive() throws Exception {
        int databaseSizeBeforeUpdate = assetLiveRepository.findAll().size();
        assetLive.setId(count.incrementAndGet());

        // Create the AssetLive
        AssetLiveDTO assetLiveDTO = assetLiveMapper.toDto(assetLive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetLiveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetLiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetLive() throws Exception {
        int databaseSizeBeforeUpdate = assetLiveRepository.findAll().size();
        assetLive.setId(count.incrementAndGet());

        // Create the AssetLive
        AssetLiveDTO assetLiveDTO = assetLiveMapper.toDto(assetLive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetLiveMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetLiveDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetLiveWithPatch() throws Exception {
        // Initialize the database
        assetLiveRepository.saveAndFlush(assetLive);

        int databaseSizeBeforeUpdate = assetLiveRepository.findAll().size();

        // Update the assetLive using partial update
        AssetLive partialUpdatedAssetLive = new AssetLive();
        partialUpdatedAssetLive.setId(assetLive.getId());

        partialUpdatedAssetLive
            .currentAssets(UPDATED_CURRENT_ASSETS)
            .cashShop(UPDATED_CASH_SHOP)
            .cashOwner(UPDATED_CASH_OWNER)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .equipment(UPDATED_EQUIPMENT)
            .realEstate(UPDATED_REAL_ESTATE)
            .notes(UPDATED_NOTES);

        restAssetLiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetLive.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetLive))
            )
            .andExpect(status().isOk());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeUpdate);
        AssetLive testAssetLive = assetLiveList.get(assetLiveList.size() - 1);
        assertThat(testAssetLive.getTotalAssets()).isEqualTo(DEFAULT_TOTAL_ASSETS);
        assertThat(testAssetLive.getCurrentAssets()).isEqualTo(UPDATED_CURRENT_ASSETS);
        assertThat(testAssetLive.getMoneyTotal()).isEqualTo(DEFAULT_MONEY_TOTAL);
        assertThat(testAssetLive.getCashShop()).isEqualTo(UPDATED_CASH_SHOP);
        assertThat(testAssetLive.getCashOwner()).isEqualTo(UPDATED_CASH_OWNER);
        assertThat(testAssetLive.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testAssetLive.getGoods()).isEqualTo(DEFAULT_GOODS);
        assertThat(testAssetLive.getDebitor()).isEqualTo(DEFAULT_DEBITOR);
        assertThat(testAssetLive.getLongTermAssets()).isEqualTo(DEFAULT_LONG_TERM_ASSETS);
        assertThat(testAssetLive.getTransport()).isEqualTo(DEFAULT_TRANSPORT);
        assertThat(testAssetLive.getEquipment()).isEqualTo(UPDATED_EQUIPMENT);
        assertThat(testAssetLive.getRealEstate()).isEqualTo(UPDATED_REAL_ESTATE);
        assertThat(testAssetLive.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testAssetLive.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAssetLive.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateAssetLiveWithPatch() throws Exception {
        // Initialize the database
        assetLiveRepository.saveAndFlush(assetLive);

        int databaseSizeBeforeUpdate = assetLiveRepository.findAll().size();

        // Update the assetLive using partial update
        AssetLive partialUpdatedAssetLive = new AssetLive();
        partialUpdatedAssetLive.setId(assetLive.getId());

        partialUpdatedAssetLive
            .totalAssets(UPDATED_TOTAL_ASSETS)
            .currentAssets(UPDATED_CURRENT_ASSETS)
            .moneyTotal(UPDATED_MONEY_TOTAL)
            .cashShop(UPDATED_CASH_SHOP)
            .cashOwner(UPDATED_CASH_OWNER)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .goods(UPDATED_GOODS)
            .debitor(UPDATED_DEBITOR)
            .longTermAssets(UPDATED_LONG_TERM_ASSETS)
            .transport(UPDATED_TRANSPORT)
            .equipment(UPDATED_EQUIPMENT)
            .realEstate(UPDATED_REAL_ESTATE)
            .other(UPDATED_OTHER)
            .code(UPDATED_CODE)
            .notes(UPDATED_NOTES);

        restAssetLiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetLive.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetLive))
            )
            .andExpect(status().isOk());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeUpdate);
        AssetLive testAssetLive = assetLiveList.get(assetLiveList.size() - 1);
        assertThat(testAssetLive.getTotalAssets()).isEqualTo(UPDATED_TOTAL_ASSETS);
        assertThat(testAssetLive.getCurrentAssets()).isEqualTo(UPDATED_CURRENT_ASSETS);
        assertThat(testAssetLive.getMoneyTotal()).isEqualTo(UPDATED_MONEY_TOTAL);
        assertThat(testAssetLive.getCashShop()).isEqualTo(UPDATED_CASH_SHOP);
        assertThat(testAssetLive.getCashOwner()).isEqualTo(UPDATED_CASH_OWNER);
        assertThat(testAssetLive.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testAssetLive.getGoods()).isEqualTo(UPDATED_GOODS);
        assertThat(testAssetLive.getDebitor()).isEqualTo(UPDATED_DEBITOR);
        assertThat(testAssetLive.getLongTermAssets()).isEqualTo(UPDATED_LONG_TERM_ASSETS);
        assertThat(testAssetLive.getTransport()).isEqualTo(UPDATED_TRANSPORT);
        assertThat(testAssetLive.getEquipment()).isEqualTo(UPDATED_EQUIPMENT);
        assertThat(testAssetLive.getRealEstate()).isEqualTo(UPDATED_REAL_ESTATE);
        assertThat(testAssetLive.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testAssetLive.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAssetLive.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingAssetLive() throws Exception {
        int databaseSizeBeforeUpdate = assetLiveRepository.findAll().size();
        assetLive.setId(count.incrementAndGet());

        // Create the AssetLive
        AssetLiveDTO assetLiveDTO = assetLiveMapper.toDto(assetLive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetLiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetLiveDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetLiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetLive() throws Exception {
        int databaseSizeBeforeUpdate = assetLiveRepository.findAll().size();
        assetLive.setId(count.incrementAndGet());

        // Create the AssetLive
        AssetLiveDTO assetLiveDTO = assetLiveMapper.toDto(assetLive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetLiveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetLiveDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetLive() throws Exception {
        int databaseSizeBeforeUpdate = assetLiveRepository.findAll().size();
        assetLive.setId(count.incrementAndGet());

        // Create the AssetLive
        AssetLiveDTO assetLiveDTO = assetLiveMapper.toDto(assetLive);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetLiveMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assetLiveDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetLive in the database
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetLive() throws Exception {
        // Initialize the database
        assetLiveRepository.saveAndFlush(assetLive);

        int databaseSizeBeforeDelete = assetLiveRepository.findAll().size();

        // Delete the assetLive
        restAssetLiveMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetLive.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssetLive> assetLiveList = assetLiveRepository.findAll();
        assertThat(assetLiveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
