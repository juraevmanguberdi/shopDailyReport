package com.shop.report.web.rest;

import com.shop.report.repository.BorrowedDebtRepository;
import com.shop.report.service.BorrowedDebtService;
import com.shop.report.service.dto.BorrowedDebtDTO;
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
 * REST controller for managing {@link com.shop.report.domain.BorrowedDebt}.
 */
@RestController
@RequestMapping("/api")
public class BorrowedDebtResource {

    private final Logger log = LoggerFactory.getLogger(BorrowedDebtResource.class);

    private static final String ENTITY_NAME = "borrowedDebt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BorrowedDebtService borrowedDebtService;

    private final BorrowedDebtRepository borrowedDebtRepository;

    public BorrowedDebtResource(BorrowedDebtService borrowedDebtService, BorrowedDebtRepository borrowedDebtRepository) {
        this.borrowedDebtService = borrowedDebtService;
        this.borrowedDebtRepository = borrowedDebtRepository;
    }

    /**
     * {@code POST  /borrowed-debts} : Create a new borrowedDebt.
     *
     * @param borrowedDebtDTO the borrowedDebtDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new borrowedDebtDTO, or with status {@code 400 (Bad Request)} if the borrowedDebt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/borrowed-debts")
    public ResponseEntity<BorrowedDebtDTO> createBorrowedDebt(@Valid @RequestBody BorrowedDebtDTO borrowedDebtDTO)
        throws URISyntaxException {
        log.debug("REST request to save BorrowedDebt : {}", borrowedDebtDTO);
        if (borrowedDebtDTO.getId() != null) {
            throw new BadRequestAlertException("A new borrowedDebt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BorrowedDebtDTO result = borrowedDebtService.save(borrowedDebtDTO);
        return ResponseEntity
            .created(new URI("/api/borrowed-debts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /borrowed-debts/:id} : Updates an existing borrowedDebt.
     *
     * @param id the id of the borrowedDebtDTO to save.
     * @param borrowedDebtDTO the borrowedDebtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borrowedDebtDTO,
     * or with status {@code 400 (Bad Request)} if the borrowedDebtDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the borrowedDebtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/borrowed-debts/{id}")
    public ResponseEntity<BorrowedDebtDTO> updateBorrowedDebt(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BorrowedDebtDTO borrowedDebtDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BorrowedDebt : {}, {}", id, borrowedDebtDTO);
        if (borrowedDebtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borrowedDebtDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borrowedDebtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BorrowedDebtDTO result = borrowedDebtService.update(borrowedDebtDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borrowedDebtDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /borrowed-debts/:id} : Partial updates given fields of an existing borrowedDebt, field will ignore if it is null
     *
     * @param id the id of the borrowedDebtDTO to save.
     * @param borrowedDebtDTO the borrowedDebtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borrowedDebtDTO,
     * or with status {@code 400 (Bad Request)} if the borrowedDebtDTO is not valid,
     * or with status {@code 404 (Not Found)} if the borrowedDebtDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the borrowedDebtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/borrowed-debts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BorrowedDebtDTO> partialUpdateBorrowedDebt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BorrowedDebtDTO borrowedDebtDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BorrowedDebt partially : {}, {}", id, borrowedDebtDTO);
        if (borrowedDebtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borrowedDebtDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borrowedDebtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BorrowedDebtDTO> result = borrowedDebtService.partialUpdate(borrowedDebtDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borrowedDebtDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /borrowed-debts} : get all the borrowedDebts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of borrowedDebts in body.
     */
    @GetMapping("/borrowed-debts")
    public ResponseEntity<List<BorrowedDebtDTO>> getAllBorrowedDebts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BorrowedDebts");
        Page<BorrowedDebtDTO> page = borrowedDebtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /borrowed-debts/:id} : get the "id" borrowedDebt.
     *
     * @param id the id of the borrowedDebtDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the borrowedDebtDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/borrowed-debts/{id}")
    public ResponseEntity<BorrowedDebtDTO> getBorrowedDebt(@PathVariable Long id) {
        log.debug("REST request to get BorrowedDebt : {}", id);
        Optional<BorrowedDebtDTO> borrowedDebtDTO = borrowedDebtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(borrowedDebtDTO);
    }

    /**
     * {@code DELETE  /borrowed-debts/:id} : delete the "id" borrowedDebt.
     *
     * @param id the id of the borrowedDebtDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/borrowed-debts/{id}")
    public ResponseEntity<Void> deleteBorrowedDebt(@PathVariable Long id) {
        log.debug("REST request to delete BorrowedDebt : {}", id);
        borrowedDebtService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
