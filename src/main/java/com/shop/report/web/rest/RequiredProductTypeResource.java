package com.shop.report.web.rest;

import com.shop.report.repository.RequiredProductTypeRepository;
import com.shop.report.service.RequiredProductTypeService;
import com.shop.report.service.dto.RequiredProductTypeDTO;
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
 * REST controller for managing {@link com.shop.report.domain.RequiredProductType}.
 */
@RestController
@RequestMapping("/api")
public class RequiredProductTypeResource {

    private final Logger log = LoggerFactory.getLogger(RequiredProductTypeResource.class);

    private static final String ENTITY_NAME = "requiredProductType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequiredProductTypeService requiredProductTypeService;

    private final RequiredProductTypeRepository requiredProductTypeRepository;

    public RequiredProductTypeResource(
        RequiredProductTypeService requiredProductTypeService,
        RequiredProductTypeRepository requiredProductTypeRepository
    ) {
        this.requiredProductTypeService = requiredProductTypeService;
        this.requiredProductTypeRepository = requiredProductTypeRepository;
    }

    /**
     * {@code POST  /required-product-types} : Create a new requiredProductType.
     *
     * @param requiredProductTypeDTO the requiredProductTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requiredProductTypeDTO, or with status {@code 400 (Bad Request)} if the requiredProductType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/required-product-types")
    public ResponseEntity<RequiredProductTypeDTO> createRequiredProductType(
        @Valid @RequestBody RequiredProductTypeDTO requiredProductTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to save RequiredProductType : {}", requiredProductTypeDTO);
        if (requiredProductTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new requiredProductType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequiredProductTypeDTO result = requiredProductTypeService.save(requiredProductTypeDTO);
        return ResponseEntity
            .created(new URI("/api/required-product-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /required-product-types/:id} : Updates an existing requiredProductType.
     *
     * @param id the id of the requiredProductTypeDTO to save.
     * @param requiredProductTypeDTO the requiredProductTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requiredProductTypeDTO,
     * or with status {@code 400 (Bad Request)} if the requiredProductTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requiredProductTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/required-product-types/{id}")
    public ResponseEntity<RequiredProductTypeDTO> updateRequiredProductType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequiredProductTypeDTO requiredProductTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequiredProductType : {}, {}", id, requiredProductTypeDTO);
        if (requiredProductTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requiredProductTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requiredProductTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequiredProductTypeDTO result = requiredProductTypeService.update(requiredProductTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requiredProductTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /required-product-types/:id} : Partial updates given fields of an existing requiredProductType, field will ignore if it is null
     *
     * @param id the id of the requiredProductTypeDTO to save.
     * @param requiredProductTypeDTO the requiredProductTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requiredProductTypeDTO,
     * or with status {@code 400 (Bad Request)} if the requiredProductTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requiredProductTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requiredProductTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/required-product-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequiredProductTypeDTO> partialUpdateRequiredProductType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequiredProductTypeDTO requiredProductTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequiredProductType partially : {}, {}", id, requiredProductTypeDTO);
        if (requiredProductTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requiredProductTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requiredProductTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequiredProductTypeDTO> result = requiredProductTypeService.partialUpdate(requiredProductTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requiredProductTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /required-product-types} : get all the requiredProductTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requiredProductTypes in body.
     */
    @GetMapping("/required-product-types")
    public ResponseEntity<List<RequiredProductTypeDTO>> getAllRequiredProductTypes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RequiredProductTypes");
        Page<RequiredProductTypeDTO> page = requiredProductTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /required-product-types/:id} : get the "id" requiredProductType.
     *
     * @param id the id of the requiredProductTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requiredProductTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/required-product-types/{id}")
    public ResponseEntity<RequiredProductTypeDTO> getRequiredProductType(@PathVariable Long id) {
        log.debug("REST request to get RequiredProductType : {}", id);
        Optional<RequiredProductTypeDTO> requiredProductTypeDTO = requiredProductTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requiredProductTypeDTO);
    }

    /**
     * {@code DELETE  /required-product-types/:id} : delete the "id" requiredProductType.
     *
     * @param id the id of the requiredProductTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/required-product-types/{id}")
    public ResponseEntity<Void> deleteRequiredProductType(@PathVariable Long id) {
        log.debug("REST request to delete RequiredProductType : {}", id);
        requiredProductTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
