package com.shop.report.web.rest;

import com.shop.report.repository.OwnerExpenseTypeRepository;
import com.shop.report.service.OwnerExpenseTypeService;
import com.shop.report.service.dto.OwnerExpenseTypeDTO;
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
 * REST controller for managing {@link com.shop.report.domain.OwnerExpenseType}.
 */
@RestController
@RequestMapping("/api")
public class OwnerExpenseTypeResource {

    private final Logger log = LoggerFactory.getLogger(OwnerExpenseTypeResource.class);

    private static final String ENTITY_NAME = "ownerExpenseType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OwnerExpenseTypeService ownerExpenseTypeService;

    private final OwnerExpenseTypeRepository ownerExpenseTypeRepository;

    public OwnerExpenseTypeResource(
        OwnerExpenseTypeService ownerExpenseTypeService,
        OwnerExpenseTypeRepository ownerExpenseTypeRepository
    ) {
        this.ownerExpenseTypeService = ownerExpenseTypeService;
        this.ownerExpenseTypeRepository = ownerExpenseTypeRepository;
    }

    /**
     * {@code POST  /owner-expense-types} : Create a new ownerExpenseType.
     *
     * @param ownerExpenseTypeDTO the ownerExpenseTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ownerExpenseTypeDTO, or with status {@code 400 (Bad Request)} if the ownerExpenseType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/owner-expense-types")
    public ResponseEntity<OwnerExpenseTypeDTO> createOwnerExpenseType(@Valid @RequestBody OwnerExpenseTypeDTO ownerExpenseTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save OwnerExpenseType : {}", ownerExpenseTypeDTO);
        if (ownerExpenseTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new ownerExpenseType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OwnerExpenseTypeDTO result = ownerExpenseTypeService.save(ownerExpenseTypeDTO);
        return ResponseEntity
            .created(new URI("/api/owner-expense-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /owner-expense-types/:id} : Updates an existing ownerExpenseType.
     *
     * @param id the id of the ownerExpenseTypeDTO to save.
     * @param ownerExpenseTypeDTO the ownerExpenseTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ownerExpenseTypeDTO,
     * or with status {@code 400 (Bad Request)} if the ownerExpenseTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ownerExpenseTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/owner-expense-types/{id}")
    public ResponseEntity<OwnerExpenseTypeDTO> updateOwnerExpenseType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OwnerExpenseTypeDTO ownerExpenseTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OwnerExpenseType : {}, {}", id, ownerExpenseTypeDTO);
        if (ownerExpenseTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ownerExpenseTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ownerExpenseTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OwnerExpenseTypeDTO result = ownerExpenseTypeService.update(ownerExpenseTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ownerExpenseTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /owner-expense-types/:id} : Partial updates given fields of an existing ownerExpenseType, field will ignore if it is null
     *
     * @param id the id of the ownerExpenseTypeDTO to save.
     * @param ownerExpenseTypeDTO the ownerExpenseTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ownerExpenseTypeDTO,
     * or with status {@code 400 (Bad Request)} if the ownerExpenseTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ownerExpenseTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ownerExpenseTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/owner-expense-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OwnerExpenseTypeDTO> partialUpdateOwnerExpenseType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OwnerExpenseTypeDTO ownerExpenseTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OwnerExpenseType partially : {}, {}", id, ownerExpenseTypeDTO);
        if (ownerExpenseTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ownerExpenseTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ownerExpenseTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OwnerExpenseTypeDTO> result = ownerExpenseTypeService.partialUpdate(ownerExpenseTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ownerExpenseTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /owner-expense-types} : get all the ownerExpenseTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ownerExpenseTypes in body.
     */
    @GetMapping("/owner-expense-types")
    public ResponseEntity<List<OwnerExpenseTypeDTO>> getAllOwnerExpenseTypes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of OwnerExpenseTypes");
        Page<OwnerExpenseTypeDTO> page = ownerExpenseTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /owner-expense-types/:id} : get the "id" ownerExpenseType.
     *
     * @param id the id of the ownerExpenseTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ownerExpenseTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/owner-expense-types/{id}")
    public ResponseEntity<OwnerExpenseTypeDTO> getOwnerExpenseType(@PathVariable Long id) {
        log.debug("REST request to get OwnerExpenseType : {}", id);
        Optional<OwnerExpenseTypeDTO> ownerExpenseTypeDTO = ownerExpenseTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ownerExpenseTypeDTO);
    }

    /**
     * {@code DELETE  /owner-expense-types/:id} : delete the "id" ownerExpenseType.
     *
     * @param id the id of the ownerExpenseTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/owner-expense-types/{id}")
    public ResponseEntity<Void> deleteOwnerExpenseType(@PathVariable Long id) {
        log.debug("REST request to delete OwnerExpenseType : {}", id);
        ownerExpenseTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
