package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.DailyRegistryShop;
import com.shop.report.repository.DailyRegistryShopRepository;
import com.shop.report.service.dto.DailyRegistryShopDTO;
import com.shop.report.service.mapper.DailyRegistryShopMapper;
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
 * Integration tests for the {@link DailyRegistryShopResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DailyRegistryShopResourceIT {

    private static final LocalDate DEFAULT_TODAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TODAY = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_SALES = 1L;
    private static final Long UPDATED_SALES = 2L;

    private static final Long DEFAULT_GOODS = 1L;
    private static final Long UPDATED_GOODS = 2L;

    private static final Long DEFAULT_CASH_SHOP = 1L;
    private static final Long UPDATED_CASH_SHOP = 2L;

    private static final Long DEFAULT_CLICK = 1L;
    private static final Long UPDATED_CLICK = 2L;

    private static final Long DEFAULT_TERMINAL = 1L;
    private static final Long UPDATED_TERMINAL = 2L;

    private static final Long DEFAULT_DEBT_RETURN = 1L;
    private static final Long UPDATED_DEBT_RETURN = 2L;

    private static final Long DEFAULT_DEBT_GIVEN = 1L;
    private static final Long UPDATED_DEBT_GIVEN = 2L;

    private static final Long DEFAULT_EXPENSE = 1L;
    private static final Long UPDATED_EXPENSE = 2L;

    private static final Long DEFAULT_BALANCE = 1L;
    private static final Long UPDATED_BALANCE = 2L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/daily-registry-shops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DailyRegistryShopRepository dailyRegistryShopRepository;

    @Autowired
    private DailyRegistryShopMapper dailyRegistryShopMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDailyRegistryShopMockMvc;

    private DailyRegistryShop dailyRegistryShop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyRegistryShop createEntity(EntityManager em) {
        DailyRegistryShop dailyRegistryShop = new DailyRegistryShop()
            .today(DEFAULT_TODAY)
            .sales(DEFAULT_SALES)
            .goods(DEFAULT_GOODS)
            .cashShop(DEFAULT_CASH_SHOP)
            .click(DEFAULT_CLICK)
            .terminal(DEFAULT_TERMINAL)
            .debtReturn(DEFAULT_DEBT_RETURN)
            .debtGiven(DEFAULT_DEBT_GIVEN)
            .expense(DEFAULT_EXPENSE)
            .balance(DEFAULT_BALANCE)
            .code(DEFAULT_CODE);
        return dailyRegistryShop;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyRegistryShop createUpdatedEntity(EntityManager em) {
        DailyRegistryShop dailyRegistryShop = new DailyRegistryShop()
            .today(UPDATED_TODAY)
            .sales(UPDATED_SALES)
            .goods(UPDATED_GOODS)
            .cashShop(UPDATED_CASH_SHOP)
            .click(UPDATED_CLICK)
            .terminal(UPDATED_TERMINAL)
            .debtReturn(UPDATED_DEBT_RETURN)
            .debtGiven(UPDATED_DEBT_GIVEN)
            .expense(UPDATED_EXPENSE)
            .balance(UPDATED_BALANCE)
            .code(UPDATED_CODE);
        return dailyRegistryShop;
    }

    @BeforeEach
    public void initTest() {
        dailyRegistryShop = createEntity(em);
    }

    @Test
    @Transactional
    void createDailyRegistryShop() throws Exception {
        int databaseSizeBeforeCreate = dailyRegistryShopRepository.findAll().size();
        // Create the DailyRegistryShop
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);
        restDailyRegistryShopMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeCreate + 1);
        DailyRegistryShop testDailyRegistryShop = dailyRegistryShopList.get(dailyRegistryShopList.size() - 1);
        assertThat(testDailyRegistryShop.getToday()).isEqualTo(DEFAULT_TODAY);
        assertThat(testDailyRegistryShop.getSales()).isEqualTo(DEFAULT_SALES);
        assertThat(testDailyRegistryShop.getGoods()).isEqualTo(DEFAULT_GOODS);
        assertThat(testDailyRegistryShop.getCashShop()).isEqualTo(DEFAULT_CASH_SHOP);
        assertThat(testDailyRegistryShop.getClick()).isEqualTo(DEFAULT_CLICK);
        assertThat(testDailyRegistryShop.getTerminal()).isEqualTo(DEFAULT_TERMINAL);
        assertThat(testDailyRegistryShop.getDebtReturn()).isEqualTo(DEFAULT_DEBT_RETURN);
        assertThat(testDailyRegistryShop.getDebtGiven()).isEqualTo(DEFAULT_DEBT_GIVEN);
        assertThat(testDailyRegistryShop.getExpense()).isEqualTo(DEFAULT_EXPENSE);
        assertThat(testDailyRegistryShop.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testDailyRegistryShop.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createDailyRegistryShopWithExistingId() throws Exception {
        // Create the DailyRegistryShop with an existing ID
        dailyRegistryShop.setId(1L);
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        int databaseSizeBeforeCreate = dailyRegistryShopRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyRegistryShopMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTodayIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyRegistryShopRepository.findAll().size();
        // set the field null
        dailyRegistryShop.setToday(null);

        // Create the DailyRegistryShop, which fails.
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        restDailyRegistryShopMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSalesIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyRegistryShopRepository.findAll().size();
        // set the field null
        dailyRegistryShop.setSales(null);

        // Create the DailyRegistryShop, which fails.
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        restDailyRegistryShopMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGoodsIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyRegistryShopRepository.findAll().size();
        // set the field null
        dailyRegistryShop.setGoods(null);

        // Create the DailyRegistryShop, which fails.
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        restDailyRegistryShopMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDailyRegistryShops() throws Exception {
        // Initialize the database
        dailyRegistryShopRepository.saveAndFlush(dailyRegistryShop);

        // Get all the dailyRegistryShopList
        restDailyRegistryShopMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyRegistryShop.getId().intValue())))
            .andExpect(jsonPath("$.[*].today").value(hasItem(DEFAULT_TODAY.toString())))
            .andExpect(jsonPath("$.[*].sales").value(hasItem(DEFAULT_SALES.intValue())))
            .andExpect(jsonPath("$.[*].goods").value(hasItem(DEFAULT_GOODS.intValue())))
            .andExpect(jsonPath("$.[*].cashShop").value(hasItem(DEFAULT_CASH_SHOP.intValue())))
            .andExpect(jsonPath("$.[*].click").value(hasItem(DEFAULT_CLICK.intValue())))
            .andExpect(jsonPath("$.[*].terminal").value(hasItem(DEFAULT_TERMINAL.intValue())))
            .andExpect(jsonPath("$.[*].debtReturn").value(hasItem(DEFAULT_DEBT_RETURN.intValue())))
            .andExpect(jsonPath("$.[*].debtGiven").value(hasItem(DEFAULT_DEBT_GIVEN.intValue())))
            .andExpect(jsonPath("$.[*].expense").value(hasItem(DEFAULT_EXPENSE.intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getDailyRegistryShop() throws Exception {
        // Initialize the database
        dailyRegistryShopRepository.saveAndFlush(dailyRegistryShop);

        // Get the dailyRegistryShop
        restDailyRegistryShopMockMvc
            .perform(get(ENTITY_API_URL_ID, dailyRegistryShop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyRegistryShop.getId().intValue()))
            .andExpect(jsonPath("$.today").value(DEFAULT_TODAY.toString()))
            .andExpect(jsonPath("$.sales").value(DEFAULT_SALES.intValue()))
            .andExpect(jsonPath("$.goods").value(DEFAULT_GOODS.intValue()))
            .andExpect(jsonPath("$.cashShop").value(DEFAULT_CASH_SHOP.intValue()))
            .andExpect(jsonPath("$.click").value(DEFAULT_CLICK.intValue()))
            .andExpect(jsonPath("$.terminal").value(DEFAULT_TERMINAL.intValue()))
            .andExpect(jsonPath("$.debtReturn").value(DEFAULT_DEBT_RETURN.intValue()))
            .andExpect(jsonPath("$.debtGiven").value(DEFAULT_DEBT_GIVEN.intValue()))
            .andExpect(jsonPath("$.expense").value(DEFAULT_EXPENSE.intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingDailyRegistryShop() throws Exception {
        // Get the dailyRegistryShop
        restDailyRegistryShopMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDailyRegistryShop() throws Exception {
        // Initialize the database
        dailyRegistryShopRepository.saveAndFlush(dailyRegistryShop);

        int databaseSizeBeforeUpdate = dailyRegistryShopRepository.findAll().size();

        // Update the dailyRegistryShop
        DailyRegistryShop updatedDailyRegistryShop = dailyRegistryShopRepository.findById(dailyRegistryShop.getId()).get();
        // Disconnect from session so that the updates on updatedDailyRegistryShop are not directly saved in db
        em.detach(updatedDailyRegistryShop);
        updatedDailyRegistryShop
            .today(UPDATED_TODAY)
            .sales(UPDATED_SALES)
            .goods(UPDATED_GOODS)
            .cashShop(UPDATED_CASH_SHOP)
            .click(UPDATED_CLICK)
            .terminal(UPDATED_TERMINAL)
            .debtReturn(UPDATED_DEBT_RETURN)
            .debtGiven(UPDATED_DEBT_GIVEN)
            .expense(UPDATED_EXPENSE)
            .balance(UPDATED_BALANCE)
            .code(UPDATED_CODE);
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(updatedDailyRegistryShop);

        restDailyRegistryShopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyRegistryShopDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isOk());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeUpdate);
        DailyRegistryShop testDailyRegistryShop = dailyRegistryShopList.get(dailyRegistryShopList.size() - 1);
        assertThat(testDailyRegistryShop.getToday()).isEqualTo(UPDATED_TODAY);
        assertThat(testDailyRegistryShop.getSales()).isEqualTo(UPDATED_SALES);
        assertThat(testDailyRegistryShop.getGoods()).isEqualTo(UPDATED_GOODS);
        assertThat(testDailyRegistryShop.getCashShop()).isEqualTo(UPDATED_CASH_SHOP);
        assertThat(testDailyRegistryShop.getClick()).isEqualTo(UPDATED_CLICK);
        assertThat(testDailyRegistryShop.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testDailyRegistryShop.getDebtReturn()).isEqualTo(UPDATED_DEBT_RETURN);
        assertThat(testDailyRegistryShop.getDebtGiven()).isEqualTo(UPDATED_DEBT_GIVEN);
        assertThat(testDailyRegistryShop.getExpense()).isEqualTo(UPDATED_EXPENSE);
        assertThat(testDailyRegistryShop.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testDailyRegistryShop.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingDailyRegistryShop() throws Exception {
        int databaseSizeBeforeUpdate = dailyRegistryShopRepository.findAll().size();
        dailyRegistryShop.setId(count.incrementAndGet());

        // Create the DailyRegistryShop
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyRegistryShopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyRegistryShopDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDailyRegistryShop() throws Exception {
        int databaseSizeBeforeUpdate = dailyRegistryShopRepository.findAll().size();
        dailyRegistryShop.setId(count.incrementAndGet());

        // Create the DailyRegistryShop
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyRegistryShopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDailyRegistryShop() throws Exception {
        int databaseSizeBeforeUpdate = dailyRegistryShopRepository.findAll().size();
        dailyRegistryShop.setId(count.incrementAndGet());

        // Create the DailyRegistryShop
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyRegistryShopMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDailyRegistryShopWithPatch() throws Exception {
        // Initialize the database
        dailyRegistryShopRepository.saveAndFlush(dailyRegistryShop);

        int databaseSizeBeforeUpdate = dailyRegistryShopRepository.findAll().size();

        // Update the dailyRegistryShop using partial update
        DailyRegistryShop partialUpdatedDailyRegistryShop = new DailyRegistryShop();
        partialUpdatedDailyRegistryShop.setId(dailyRegistryShop.getId());

        partialUpdatedDailyRegistryShop
            .cashShop(UPDATED_CASH_SHOP)
            .terminal(UPDATED_TERMINAL)
            .debtReturn(UPDATED_DEBT_RETURN)
            .expense(UPDATED_EXPENSE)
            .balance(UPDATED_BALANCE);

        restDailyRegistryShopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyRegistryShop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyRegistryShop))
            )
            .andExpect(status().isOk());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeUpdate);
        DailyRegistryShop testDailyRegistryShop = dailyRegistryShopList.get(dailyRegistryShopList.size() - 1);
        assertThat(testDailyRegistryShop.getToday()).isEqualTo(DEFAULT_TODAY);
        assertThat(testDailyRegistryShop.getSales()).isEqualTo(DEFAULT_SALES);
        assertThat(testDailyRegistryShop.getGoods()).isEqualTo(DEFAULT_GOODS);
        assertThat(testDailyRegistryShop.getCashShop()).isEqualTo(UPDATED_CASH_SHOP);
        assertThat(testDailyRegistryShop.getClick()).isEqualTo(DEFAULT_CLICK);
        assertThat(testDailyRegistryShop.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testDailyRegistryShop.getDebtReturn()).isEqualTo(UPDATED_DEBT_RETURN);
        assertThat(testDailyRegistryShop.getDebtGiven()).isEqualTo(DEFAULT_DEBT_GIVEN);
        assertThat(testDailyRegistryShop.getExpense()).isEqualTo(UPDATED_EXPENSE);
        assertThat(testDailyRegistryShop.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testDailyRegistryShop.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateDailyRegistryShopWithPatch() throws Exception {
        // Initialize the database
        dailyRegistryShopRepository.saveAndFlush(dailyRegistryShop);

        int databaseSizeBeforeUpdate = dailyRegistryShopRepository.findAll().size();

        // Update the dailyRegistryShop using partial update
        DailyRegistryShop partialUpdatedDailyRegistryShop = new DailyRegistryShop();
        partialUpdatedDailyRegistryShop.setId(dailyRegistryShop.getId());

        partialUpdatedDailyRegistryShop
            .today(UPDATED_TODAY)
            .sales(UPDATED_SALES)
            .goods(UPDATED_GOODS)
            .cashShop(UPDATED_CASH_SHOP)
            .click(UPDATED_CLICK)
            .terminal(UPDATED_TERMINAL)
            .debtReturn(UPDATED_DEBT_RETURN)
            .debtGiven(UPDATED_DEBT_GIVEN)
            .expense(UPDATED_EXPENSE)
            .balance(UPDATED_BALANCE)
            .code(UPDATED_CODE);

        restDailyRegistryShopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyRegistryShop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyRegistryShop))
            )
            .andExpect(status().isOk());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeUpdate);
        DailyRegistryShop testDailyRegistryShop = dailyRegistryShopList.get(dailyRegistryShopList.size() - 1);
        assertThat(testDailyRegistryShop.getToday()).isEqualTo(UPDATED_TODAY);
        assertThat(testDailyRegistryShop.getSales()).isEqualTo(UPDATED_SALES);
        assertThat(testDailyRegistryShop.getGoods()).isEqualTo(UPDATED_GOODS);
        assertThat(testDailyRegistryShop.getCashShop()).isEqualTo(UPDATED_CASH_SHOP);
        assertThat(testDailyRegistryShop.getClick()).isEqualTo(UPDATED_CLICK);
        assertThat(testDailyRegistryShop.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testDailyRegistryShop.getDebtReturn()).isEqualTo(UPDATED_DEBT_RETURN);
        assertThat(testDailyRegistryShop.getDebtGiven()).isEqualTo(UPDATED_DEBT_GIVEN);
        assertThat(testDailyRegistryShop.getExpense()).isEqualTo(UPDATED_EXPENSE);
        assertThat(testDailyRegistryShop.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testDailyRegistryShop.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingDailyRegistryShop() throws Exception {
        int databaseSizeBeforeUpdate = dailyRegistryShopRepository.findAll().size();
        dailyRegistryShop.setId(count.incrementAndGet());

        // Create the DailyRegistryShop
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyRegistryShopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dailyRegistryShopDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDailyRegistryShop() throws Exception {
        int databaseSizeBeforeUpdate = dailyRegistryShopRepository.findAll().size();
        dailyRegistryShop.setId(count.incrementAndGet());

        // Create the DailyRegistryShop
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyRegistryShopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDailyRegistryShop() throws Exception {
        int databaseSizeBeforeUpdate = dailyRegistryShopRepository.findAll().size();
        dailyRegistryShop.setId(count.incrementAndGet());

        // Create the DailyRegistryShop
        DailyRegistryShopDTO dailyRegistryShopDTO = dailyRegistryShopMapper.toDto(dailyRegistryShop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyRegistryShopMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyRegistryShopDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyRegistryShop in the database
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDailyRegistryShop() throws Exception {
        // Initialize the database
        dailyRegistryShopRepository.saveAndFlush(dailyRegistryShop);

        int databaseSizeBeforeDelete = dailyRegistryShopRepository.findAll().size();

        // Delete the dailyRegistryShop
        restDailyRegistryShopMockMvc
            .perform(delete(ENTITY_API_URL_ID, dailyRegistryShop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyRegistryShop> dailyRegistryShopList = dailyRegistryShopRepository.findAll();
        assertThat(dailyRegistryShopList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
