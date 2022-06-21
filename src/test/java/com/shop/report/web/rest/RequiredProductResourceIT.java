package com.shop.report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.report.IntegrationTest;
import com.shop.report.domain.RequiredProduct;
import com.shop.report.domain.RequiredProductType;
import com.shop.report.repository.RequiredProductRepository;
import com.shop.report.service.dto.RequiredProductDTO;
import com.shop.report.service.mapper.RequiredProductMapper;
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
 * Integration tests for the {@link RequiredProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequiredProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/required-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequiredProductRepository requiredProductRepository;

    @Autowired
    private RequiredProductMapper requiredProductMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequiredProductMockMvc;

    private RequiredProduct requiredProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequiredProduct createEntity(EntityManager em) {
        RequiredProduct requiredProduct = new RequiredProduct().name(DEFAULT_NAME).note(DEFAULT_NOTE).code(DEFAULT_CODE);
        // Add required entity
        RequiredProductType requiredProductType;
        if (TestUtil.findAll(em, RequiredProductType.class).isEmpty()) {
            requiredProductType = RequiredProductTypeResourceIT.createEntity(em);
            em.persist(requiredProductType);
            em.flush();
        } else {
            requiredProductType = TestUtil.findAll(em, RequiredProductType.class).get(0);
        }
        requiredProduct.setRequiredProductType(requiredProductType);
        return requiredProduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequiredProduct createUpdatedEntity(EntityManager em) {
        RequiredProduct requiredProduct = new RequiredProduct().name(UPDATED_NAME).note(UPDATED_NOTE).code(UPDATED_CODE);
        // Add required entity
        RequiredProductType requiredProductType;
        if (TestUtil.findAll(em, RequiredProductType.class).isEmpty()) {
            requiredProductType = RequiredProductTypeResourceIT.createUpdatedEntity(em);
            em.persist(requiredProductType);
            em.flush();
        } else {
            requiredProductType = TestUtil.findAll(em, RequiredProductType.class).get(0);
        }
        requiredProduct.setRequiredProductType(requiredProductType);
        return requiredProduct;
    }

    @BeforeEach
    public void initTest() {
        requiredProduct = createEntity(em);
    }

    @Test
    @Transactional
    void createRequiredProduct() throws Exception {
        int databaseSizeBeforeCreate = requiredProductRepository.findAll().size();
        // Create the RequiredProduct
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(requiredProduct);
        restRequiredProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeCreate + 1);
        RequiredProduct testRequiredProduct = requiredProductList.get(requiredProductList.size() - 1);
        assertThat(testRequiredProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRequiredProduct.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testRequiredProduct.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createRequiredProductWithExistingId() throws Exception {
        // Create the RequiredProduct with an existing ID
        requiredProduct.setId(1L);
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(requiredProduct);

        int databaseSizeBeforeCreate = requiredProductRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequiredProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = requiredProductRepository.findAll().size();
        // set the field null
        requiredProduct.setName(null);

        // Create the RequiredProduct, which fails.
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(requiredProduct);

        restRequiredProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequiredProducts() throws Exception {
        // Initialize the database
        requiredProductRepository.saveAndFlush(requiredProduct);

        // Get all the requiredProductList
        restRequiredProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requiredProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getRequiredProduct() throws Exception {
        // Initialize the database
        requiredProductRepository.saveAndFlush(requiredProduct);

        // Get the requiredProduct
        restRequiredProductMockMvc
            .perform(get(ENTITY_API_URL_ID, requiredProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requiredProduct.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingRequiredProduct() throws Exception {
        // Get the requiredProduct
        restRequiredProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequiredProduct() throws Exception {
        // Initialize the database
        requiredProductRepository.saveAndFlush(requiredProduct);

        int databaseSizeBeforeUpdate = requiredProductRepository.findAll().size();

        // Update the requiredProduct
        RequiredProduct updatedRequiredProduct = requiredProductRepository.findById(requiredProduct.getId()).get();
        // Disconnect from session so that the updates on updatedRequiredProduct are not directly saved in db
        em.detach(updatedRequiredProduct);
        updatedRequiredProduct.name(UPDATED_NAME).note(UPDATED_NOTE).code(UPDATED_CODE);
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(updatedRequiredProduct);

        restRequiredProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requiredProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeUpdate);
        RequiredProduct testRequiredProduct = requiredProductList.get(requiredProductList.size() - 1);
        assertThat(testRequiredProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequiredProduct.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testRequiredProduct.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingRequiredProduct() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductRepository.findAll().size();
        requiredProduct.setId(count.incrementAndGet());

        // Create the RequiredProduct
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(requiredProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequiredProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requiredProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequiredProduct() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductRepository.findAll().size();
        requiredProduct.setId(count.incrementAndGet());

        // Create the RequiredProduct
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(requiredProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequiredProduct() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductRepository.findAll().size();
        requiredProduct.setId(count.incrementAndGet());

        // Create the RequiredProduct
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(requiredProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredProductMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequiredProductWithPatch() throws Exception {
        // Initialize the database
        requiredProductRepository.saveAndFlush(requiredProduct);

        int databaseSizeBeforeUpdate = requiredProductRepository.findAll().size();

        // Update the requiredProduct using partial update
        RequiredProduct partialUpdatedRequiredProduct = new RequiredProduct();
        partialUpdatedRequiredProduct.setId(requiredProduct.getId());

        partialUpdatedRequiredProduct.name(UPDATED_NAME).note(UPDATED_NOTE).code(UPDATED_CODE);

        restRequiredProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequiredProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequiredProduct))
            )
            .andExpect(status().isOk());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeUpdate);
        RequiredProduct testRequiredProduct = requiredProductList.get(requiredProductList.size() - 1);
        assertThat(testRequiredProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequiredProduct.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testRequiredProduct.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateRequiredProductWithPatch() throws Exception {
        // Initialize the database
        requiredProductRepository.saveAndFlush(requiredProduct);

        int databaseSizeBeforeUpdate = requiredProductRepository.findAll().size();

        // Update the requiredProduct using partial update
        RequiredProduct partialUpdatedRequiredProduct = new RequiredProduct();
        partialUpdatedRequiredProduct.setId(requiredProduct.getId());

        partialUpdatedRequiredProduct.name(UPDATED_NAME).note(UPDATED_NOTE).code(UPDATED_CODE);

        restRequiredProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequiredProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequiredProduct))
            )
            .andExpect(status().isOk());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeUpdate);
        RequiredProduct testRequiredProduct = requiredProductList.get(requiredProductList.size() - 1);
        assertThat(testRequiredProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequiredProduct.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testRequiredProduct.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingRequiredProduct() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductRepository.findAll().size();
        requiredProduct.setId(count.incrementAndGet());

        // Create the RequiredProduct
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(requiredProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequiredProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requiredProductDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequiredProduct() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductRepository.findAll().size();
        requiredProduct.setId(count.incrementAndGet());

        // Create the RequiredProduct
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(requiredProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequiredProduct() throws Exception {
        int databaseSizeBeforeUpdate = requiredProductRepository.findAll().size();
        requiredProduct.setId(count.incrementAndGet());

        // Create the RequiredProduct
        RequiredProductDTO requiredProductDTO = requiredProductMapper.toDto(requiredProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequiredProductMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requiredProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequiredProduct in the database
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequiredProduct() throws Exception {
        // Initialize the database
        requiredProductRepository.saveAndFlush(requiredProduct);

        int databaseSizeBeforeDelete = requiredProductRepository.findAll().size();

        // Delete the requiredProduct
        restRequiredProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, requiredProduct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequiredProduct> requiredProductList = requiredProductRepository.findAll();
        assertThat(requiredProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
