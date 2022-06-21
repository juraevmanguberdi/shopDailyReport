package com.shop.report.web.rest;

import com.shop.report.repository.RequiredProductRepository;
import com.shop.report.service.RequiredProductService;
import com.shop.report.service.dto.RequiredProductDTO;
import com.shop.report.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.shop.report.domain.RequiredProduct}.
 */
@RestController
@RequestMapping("/api")
public class RequiredProductResource {

    private final Logger log = LoggerFactory.getLogger(RequiredProductResource.class);

    private static final String ENTITY_NAME = "requiredProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequiredProductService requiredProductService;

    private final RequiredProductRepository requiredProductRepository;

    public RequiredProductResource(RequiredProductService requiredProductService, RequiredProductRepository requiredProductRepository) {
        this.requiredProductService = requiredProductService;
        this.requiredProductRepository = requiredProductRepository;
    }

    /**
     * {@code POST  /required-products} : Create a new requiredProduct.
     *
     * @param requiredProductDTO the requiredProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requiredProductDTO, or with status {@code 400 (Bad Request)} if the requiredProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/required-products")
    public ResponseEntity<RequiredProductDTO> createRequiredProduct(@Valid @RequestBody RequiredProductDTO requiredProductDTO)
        throws URISyntaxException {
        log.debug("REST request to save RequiredProduct : {}", requiredProductDTO);
        if (requiredProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new requiredProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequiredProductDTO result = requiredProductService.save(requiredProductDTO);
        return ResponseEntity
            .created(new URI("/api/required-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /required-products/:id} : Updates an existing requiredProduct.
     *
     * @param id the id of the requiredProductDTO to save.
     * @param requiredProductDTO the requiredProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requiredProductDTO,
     * or with status {@code 400 (Bad Request)} if the requiredProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requiredProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/required-products/{id}")
    public ResponseEntity<RequiredProductDTO> updateRequiredProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequiredProductDTO requiredProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequiredProduct : {}, {}", id, requiredProductDTO);
        if (requiredProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requiredProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requiredProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequiredProductDTO result = requiredProductService.update(requiredProductDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requiredProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /required-products/:id} : Partial updates given fields of an existing requiredProduct, field will ignore if it is null
     *
     * @param id the id of the requiredProductDTO to save.
     * @param requiredProductDTO the requiredProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requiredProductDTO,
     * or with status {@code 400 (Bad Request)} if the requiredProductDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requiredProductDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requiredProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/required-products/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequiredProductDTO> partialUpdateRequiredProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequiredProductDTO requiredProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequiredProduct partially : {}, {}", id, requiredProductDTO);
        if (requiredProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requiredProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requiredProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequiredProductDTO> result = requiredProductService.partialUpdate(requiredProductDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requiredProductDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /required-products} : get all the requiredProducts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requiredProducts in body.
     */
    @GetMapping("/required-products")
    public ResponseEntity<List<RequiredProductDTO>> getAllRequiredProducts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RequiredProducts");
        Page<RequiredProductDTO> page = requiredProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /required-products/:id} : get the "id" requiredProduct.
     *
     * @param id the id of the requiredProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requiredProductDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/required-products/{id}")
    public ResponseEntity<RequiredProductDTO> getRequiredProduct(@PathVariable Long id) {
        log.debug("REST request to get RequiredProduct : {}", id);
        Optional<RequiredProductDTO> requiredProductDTO = requiredProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requiredProductDTO);
    }

    /**
     * {@code DELETE  /required-products/:id} : delete the "id" requiredProduct.
     *
     * @param id the id of the requiredProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/required-products/{id}")
    public ResponseEntity<Void> deleteRequiredProduct(@PathVariable Long id) {
        log.debug("REST request to delete RequiredProduct : {}", id);
        requiredProductService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
