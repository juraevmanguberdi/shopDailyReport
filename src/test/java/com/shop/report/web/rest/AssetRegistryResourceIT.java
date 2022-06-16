package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.AssetRegistry;
import com.shop.report.repository.AssetRegistryRepository;
import com.shop.report.service.dto.AssetRegistryDTO;
import com.shop.report.service.mapper.AssetRegistryMapper;
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
 * Integration tests for the {@link AssetRegistryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetRegistryResourceIT {

    private static final LocalDate DEFAULT_TODAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TODAY = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/asset-registries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssetRegistryRepository assetRegistryRepository;

    @Autowired
    private AssetRegistryMapper assetRegistryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetRegistryMockMvc;

    private AssetRegistry assetRegistry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetRegistry createEntity(EntityManager em) {
        AssetRegistry assetRegistry = new AssetRegistry()
            .today(DEFAULT_TODAY)
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
        return assetRegistry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetRegistry createUpdatedEntity(EntityManager em) {
        AssetRegistry assetRegistry = new AssetRegistry()
            .today(UPDATED_TODAY)
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
        return assetRegistry;
    }

    @BeforeEach
    public void initTest() {
        assetRegistry = createEntity(em);
    }

    @Test
    @Transactional
    void createAssetRegistry() throws Exception {
        int databaseSizeBeforeCreate = assetRegistryRepository.findAll().size();
        // Create the AssetRegistry
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(assetRegistry);
        restAssetRegistryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeCreate + 1);
        AssetRegistry testAssetRegistry = assetRegistryList.get(assetRegistryList.size() - 1);
        assertThat(testAssetRegistry.getToday()).isEqualTo(DEFAULT_TODAY);
        assertThat(testAssetRegistry.getTotalAssets()).isEqualTo(DEFAULT_TOTAL_ASSETS);
        assertThat(testAssetRegistry.getCurrentAssets()).isEqualTo(DEFAULT_CURRENT_ASSETS);
        assertThat(testAssetRegistry.getMoneyTotal()).isEqualTo(DEFAULT_MONEY_TOTAL);
        assertThat(testAssetRegistry.getCashShop()).isEqualTo(DEFAULT_CASH_SHOP);
        assertThat(testAssetRegistry.getCashOwner()).isEqualTo(DEFAULT_CASH_OWNER);
        assertThat(testAssetRegistry.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testAssetRegistry.getGoods()).isEqualTo(DEFAULT_GOODS);
        assertThat(testAssetRegistry.getDebitor()).isEqualTo(DEFAULT_DEBITOR);
        assertThat(testAssetRegistry.getLongTermAssets()).isEqualTo(DEFAULT_LONG_TERM_ASSETS);
        assertThat(testAssetRegistry.getTransport()).isEqualTo(DEFAULT_TRANSPORT);
        assertThat(testAssetRegistry.getEquipment()).isEqualTo(DEFAULT_EQUIPMENT);
        assertThat(testAssetRegistry.getRealEstate()).isEqualTo(DEFAULT_REAL_ESTATE);
        assertThat(testAssetRegistry.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testAssetRegistry.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAssetRegistry.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createAssetRegistryWithExistingId() throws Exception {
        // Create the AssetRegistry with an existing ID
        assetRegistry.setId(1L);
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(assetRegistry);

        int databaseSizeBeforeCreate = assetRegistryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetRegistryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTodayIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRegistryRepository.findAll().size();
        // set the field null
        assetRegistry.setToday(null);

        // Create the AssetRegistry, which fails.
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(assetRegistry);

        restAssetRegistryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssetRegistries() throws Exception {
        // Initialize the database
        assetRegistryRepository.saveAndFlush(assetRegistry);

        // Get all the assetRegistryList
        restAssetRegistryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetRegistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].today").value(hasItem(DEFAULT_TODAY.toString())))
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
    void getAssetRegistry() throws Exception {
        // Initialize the database
        assetRegistryRepository.saveAndFlush(assetRegistry);

        // Get the assetRegistry
        restAssetRegistryMockMvc
            .perform(get(ENTITY_API_URL_ID, assetRegistry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetRegistry.getId().intValue()))
            .andExpect(jsonPath("$.today").value(DEFAULT_TODAY.toString()))
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
    void getNonExistingAssetRegistry() throws Exception {
        // Get the assetRegistry
        restAssetRegistryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAssetRegistry() throws Exception {
        // Initialize the database
        assetRegistryRepository.saveAndFlush(assetRegistry);

        int databaseSizeBeforeUpdate = assetRegistryRepository.findAll().size();

        // Update the assetRegistry
        AssetRegistry updatedAssetRegistry = assetRegistryRepository.findById(assetRegistry.getId()).get();
        // Disconnect from session so that the updates on updatedAssetRegistry are not directly saved in db
        em.detach(updatedAssetRegistry);
        updatedAssetRegistry
            .today(UPDATED_TODAY)
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
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(updatedAssetRegistry);

        restAssetRegistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetRegistryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeUpdate);
        AssetRegistry testAssetRegistry = assetRegistryList.get(assetRegistryList.size() - 1);
        assertThat(testAssetRegistry.getToday()).isEqualTo(UPDATED_TODAY);
        assertThat(testAssetRegistry.getTotalAssets()).isEqualTo(UPDATED_TOTAL_ASSETS);
        assertThat(testAssetRegistry.getCurrentAssets()).isEqualTo(UPDATED_CURRENT_ASSETS);
        assertThat(testAssetRegistry.getMoneyTotal()).isEqualTo(UPDATED_MONEY_TOTAL);
        assertThat(testAssetRegistry.getCashShop()).isEqualTo(UPDATED_CASH_SHOP);
        assertThat(testAssetRegistry.getCashOwner()).isEqualTo(UPDATED_CASH_OWNER);
        assertThat(testAssetRegistry.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testAssetRegistry.getGoods()).isEqualTo(UPDATED_GOODS);
        assertThat(testAssetRegistry.getDebitor()).isEqualTo(UPDATED_DEBITOR);
        assertThat(testAssetRegistry.getLongTermAssets()).isEqualTo(UPDATED_LONG_TERM_ASSETS);
        assertThat(testAssetRegistry.getTransport()).isEqualTo(UPDATED_TRANSPORT);
        assertThat(testAssetRegistry.getEquipment()).isEqualTo(UPDATED_EQUIPMENT);
        assertThat(testAssetRegistry.getRealEstate()).isEqualTo(UPDATED_REAL_ESTATE);
        assertThat(testAssetRegistry.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testAssetRegistry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAssetRegistry.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingAssetRegistry() throws Exception {
        int databaseSizeBeforeUpdate = assetRegistryRepository.findAll().size();
        assetRegistry.setId(count.incrementAndGet());

        // Create the AssetRegistry
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(assetRegistry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetRegistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetRegistryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetRegistry() throws Exception {
        int databaseSizeBeforeUpdate = assetRegistryRepository.findAll().size();
        assetRegistry.setId(count.incrementAndGet());

        // Create the AssetRegistry
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(assetRegistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetRegistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetRegistry() throws Exception {
        int databaseSizeBeforeUpdate = assetRegistryRepository.findAll().size();
        assetRegistry.setId(count.incrementAndGet());

        // Create the AssetRegistry
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(assetRegistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetRegistryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetRegistryWithPatch() throws Exception {
        // Initialize the database
        assetRegistryRepository.saveAndFlush(assetRegistry);

        int databaseSizeBeforeUpdate = assetRegistryRepository.findAll().size();

        // Update the assetRegistry using partial update
        AssetRegistry partialUpdatedAssetRegistry = new AssetRegistry();
        partialUpdatedAssetRegistry.setId(assetRegistry.getId());

        partialUpdatedAssetRegistry
            .currentAssets(UPDATED_CURRENT_ASSETS)
            .cashOwner(UPDATED_CASH_OWNER)
            .debitor(UPDATED_DEBITOR)
            .longTermAssets(UPDATED_LONG_TERM_ASSETS)
            .realEstate(UPDATED_REAL_ESTATE)
            .code(UPDATED_CODE);

        restAssetRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetRegistry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetRegistry))
            )
            .andExpect(status().isOk());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeUpdate);
        AssetRegistry testAssetRegistry = assetRegistryList.get(assetRegistryList.size() - 1);
        assertThat(testAssetRegistry.getToday()).isEqualTo(DEFAULT_TODAY);
        assertThat(testAssetRegistry.getTotalAssets()).isEqualTo(DEFAULT_TOTAL_ASSETS);
        assertThat(testAssetRegistry.getCurrentAssets()).isEqualTo(UPDATED_CURRENT_ASSETS);
        assertThat(testAssetRegistry.getMoneyTotal()).isEqualTo(DEFAULT_MONEY_TOTAL);
        assertThat(testAssetRegistry.getCashShop()).isEqualTo(DEFAULT_CASH_SHOP);
        assertThat(testAssetRegistry.getCashOwner()).isEqualTo(UPDATED_CASH_OWNER);
        assertThat(testAssetRegistry.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testAssetRegistry.getGoods()).isEqualTo(DEFAULT_GOODS);
        assertThat(testAssetRegistry.getDebitor()).isEqualTo(UPDATED_DEBITOR);
        assertThat(testAssetRegistry.getLongTermAssets()).isEqualTo(UPDATED_LONG_TERM_ASSETS);
        assertThat(testAssetRegistry.getTransport()).isEqualTo(DEFAULT_TRANSPORT);
        assertThat(testAssetRegistry.getEquipment()).isEqualTo(DEFAULT_EQUIPMENT);
        assertThat(testAssetRegistry.getRealEstate()).isEqualTo(UPDATED_REAL_ESTATE);
        assertThat(testAssetRegistry.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testAssetRegistry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAssetRegistry.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateAssetRegistryWithPatch() throws Exception {
        // Initialize the database
        assetRegistryRepository.saveAndFlush(assetRegistry);

        int databaseSizeBeforeUpdate = assetRegistryRepository.findAll().size();

        // Update the assetRegistry using partial update
        AssetRegistry partialUpdatedAssetRegistry = new AssetRegistry();
        partialUpdatedAssetRegistry.setId(assetRegistry.getId());

        partialUpdatedAssetRegistry
            .today(UPDATED_TODAY)
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

        restAssetRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetRegistry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetRegistry))
            )
            .andExpect(status().isOk());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeUpdate);
        AssetRegistry testAssetRegistry = assetRegistryList.get(assetRegistryList.size() - 1);
        assertThat(testAssetRegistry.getToday()).isEqualTo(UPDATED_TODAY);
        assertThat(testAssetRegistry.getTotalAssets()).isEqualTo(UPDATED_TOTAL_ASSETS);
        assertThat(testAssetRegistry.getCurrentAssets()).isEqualTo(UPDATED_CURRENT_ASSETS);
        assertThat(testAssetRegistry.getMoneyTotal()).isEqualTo(UPDATED_MONEY_TOTAL);
        assertThat(testAssetRegistry.getCashShop()).isEqualTo(UPDATED_CASH_SHOP);
        assertThat(testAssetRegistry.getCashOwner()).isEqualTo(UPDATED_CASH_OWNER);
        assertThat(testAssetRegistry.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testAssetRegistry.getGoods()).isEqualTo(UPDATED_GOODS);
        assertThat(testAssetRegistry.getDebitor()).isEqualTo(UPDATED_DEBITOR);
        assertThat(testAssetRegistry.getLongTermAssets()).isEqualTo(UPDATED_LONG_TERM_ASSETS);
        assertThat(testAssetRegistry.getTransport()).isEqualTo(UPDATED_TRANSPORT);
        assertThat(testAssetRegistry.getEquipment()).isEqualTo(UPDATED_EQUIPMENT);
        assertThat(testAssetRegistry.getRealEstate()).isEqualTo(UPDATED_REAL_ESTATE);
        assertThat(testAssetRegistry.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testAssetRegistry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAssetRegistry.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingAssetRegistry() throws Exception {
        int databaseSizeBeforeUpdate = assetRegistryRepository.findAll().size();
        assetRegistry.setId(count.incrementAndGet());

        // Create the AssetRegistry
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(assetRegistry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetRegistryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetRegistry() throws Exception {
        int databaseSizeBeforeUpdate = assetRegistryRepository.findAll().size();
        assetRegistry.setId(count.incrementAndGet());

        // Create the AssetRegistry
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(assetRegistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetRegistry() throws Exception {
        int databaseSizeBeforeUpdate = assetRegistryRepository.findAll().size();
        assetRegistry.setId(count.incrementAndGet());

        // Create the AssetRegistry
        AssetRegistryDTO assetRegistryDTO = assetRegistryMapper.toDto(assetRegistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetRegistryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetRegistryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetRegistry in the database
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetRegistry() throws Exception {
        // Initialize the database
        assetRegistryRepository.saveAndFlush(assetRegistry);

        int databaseSizeBeforeDelete = assetRegistryRepository.findAll().size();

        // Delete the assetRegistry
        restAssetRegistryMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetRegistry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssetRegistry> assetRegistryList = assetRegistryRepository.findAll();
        assertThat(assetRegistryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
