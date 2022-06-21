package com.shop.report.web.rest;

import com.shop.report.repository.BorrowedDebtTypeRepository;
import com.shop.report.service.BorrowedDebtTypeService;
import com.shop.report.service.dto.BorrowedDebtTypeDTO;
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
 * REST controller for managing {@link com.shop.report.domain.BorrowedDebtType}.
 */
@RestController
@RequestMapping("/api")
public class BorrowedDebtTypeResource {

    private final Logger log = LoggerFactory.getLogger(BorrowedDebtTypeResource.class);

    private static final String ENTITY_NAME = "borrowedDebtType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BorrowedDebtTypeService borrowedDebtTypeService;

    private final BorrowedDebtTypeRepository borrowedDebtTypeRepository;

    public BorrowedDebtTypeResource(
        BorrowedDebtTypeService borrowedDebtTypeService,
        BorrowedDebtTypeRepository borrowedDebtTypeRepository
    ) {
        this.borrowedDebtTypeService = borrowedDebtTypeService;
        this.borrowedDebtTypeRepository = borrowedDebtTypeRepository;
    }

    /**
     * {@code POST  /borrowed-debt-types} : Create a new borrowedDebtType.
     *
     * @param borrowedDebtTypeDTO the borrowedDebtTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new borrowedDebtTypeDTO, or with status {@code 400 (Bad Request)} if the borrowedDebtType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/borrowed-debt-types")
    public ResponseEntity<BorrowedDebtTypeDTO> createBorrowedDebtType(@Valid @RequestBody BorrowedDebtTypeDTO borrowedDebtTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save BorrowedDebtType : {}", borrowedDebtTypeDTO);
        if (borrowedDebtTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new borrowedDebtType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BorrowedDebtTypeDTO result = borrowedDebtTypeService.save(borrowedDebtTypeDTO);
        return ResponseEntity
            .created(new URI("/api/borrowed-debt-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /borrowed-debt-types/:id} : Updates an existing borrowedDebtType.
     *
     * @param id the id of the borrowedDebtTypeDTO to save.
     * @param borrowedDebtTypeDTO the borrowedDebtTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borrowedDebtTypeDTO,
     * or with status {@code 400 (Bad Request)} if the borrowedDebtTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the borrowedDebtTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/borrowed-debt-types/{id}")
    public ResponseEntity<BorrowedDebtTypeDTO> updateBorrowedDebtType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BorrowedDebtTypeDTO borrowedDebtTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BorrowedDebtType : {}, {}", id, borrowedDebtTypeDTO);
        if (borrowedDebtTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borrowedDebtTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borrowedDebtTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BorrowedDebtTypeDTO result = borrowedDebtTypeService.update(borrowedDebtTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borrowedDebtTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /borrowed-debt-types/:id} : Partial updates given fields of an existing borrowedDebtType, field will ignore if it is null
     *
     * @param id the id of the borrowedDebtTypeDTO to save.
     * @param borrowedDebtTypeDTO the borrowedDebtTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borrowedDebtTypeDTO,
     * or with status {@code 400 (Bad Request)} if the borrowedDebtTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the borrowedDebtTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the borrowedDebtTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/borrowed-debt-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BorrowedDebtTypeDTO> partialUpdateBorrowedDebtType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BorrowedDebtTypeDTO borrowedDebtTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BorrowedDebtType partially : {}, {}", id, borrowedDebtTypeDTO);
        if (borrowedDebtTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borrowedDebtTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borrowedDebtTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BorrowedDebtTypeDTO> result = borrowedDebtTypeService.partialUpdate(borrowedDebtTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borrowedDebtTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /borrowed-debt-types} : get all the borrowedDebtTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of borrowedDebtTypes in body.
     */
    @GetMapping("/borrowed-debt-types")
    public ResponseEntity<List<BorrowedDebtTypeDTO>> getAllBorrowedDebtTypes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BorrowedDebtTypes");
        Page<BorrowedDebtTypeDTO> page = borrowedDebtTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /borrowed-debt-types/:id} : get the "id" borrowedDebtType.
     *
     * @param id the id of the borrowedDebtTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the borrowedDebtTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/borrowed-debt-types/{id}")
    public ResponseEntity<BorrowedDebtTypeDTO> getBorrowedDebtType(@PathVariable Long id) {
        log.debug("REST request to get BorrowedDebtType : {}", id);
        Optional<BorrowedDebtTypeDTO> borrowedDebtTypeDTO = borrowedDebtTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(borrowedDebtTypeDTO);
    }

    /**
     * {@code DELETE  /borrowed-debt-types/:id} : delete the "id" borrowedDebtType.
     *
     * @param id the id of the borrowedDebtTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/borrowed-debt-types/{id}")
    public ResponseEntity<Void> deleteBorrowedDebtType(@PathVariable Long id) {
        log.debug("REST request to delete BorrowedDebtType : {}", id);
        borrowedDebtTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
