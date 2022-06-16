package com.shop.report.web.rest;

import com.shop.report.repository.ExpenseTypeRepository;
import com.shop.report.service.ExpenseTypeService;
import com.shop.report.service.dto.ExpenseTypeDTO;
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
 * REST controller for managing {@link com.shop.report.domain.ExpenseType}.
 */
@RestController
@RequestMapping("/api")
public class ExpenseTypeResource {

    private final Logger log = LoggerFactory.getLogger(ExpenseTypeResource.class);

    private static final String ENTITY_NAME = "expenseType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpenseTypeService expenseTypeService;

    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeResource(ExpenseTypeService expenseTypeService, ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeService = expenseTypeService;
        this.expenseTypeRepository = expenseTypeRepository;
    }

    /**
     * {@code POST  /expense-types} : Create a new expenseType.
     *
     * @param expenseTypeDTO the expenseTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expenseTypeDTO, or with status {@code 400 (Bad Request)} if the expenseType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expense-types")
    public ResponseEntity<ExpenseTypeDTO> createExpenseType(@Valid @RequestBody ExpenseTypeDTO expenseTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ExpenseType : {}", expenseTypeDTO);
        if (expenseTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new expenseType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpenseTypeDTO result = expenseTypeService.save(expenseTypeDTO);
        return ResponseEntity
            .created(new URI("/api/expense-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expense-types/:id} : Updates an existing expenseType.
     *
     * @param id the id of the expenseTypeDTO to save.
     * @param expenseTypeDTO the expenseTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenseTypeDTO,
     * or with status {@code 400 (Bad Request)} if the expenseTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expenseTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expense-types/{id}")
    public ResponseEntity<ExpenseTypeDTO> updateExpenseType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExpenseTypeDTO expenseTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExpenseType : {}, {}", id, expenseTypeDTO);
        if (expenseTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expenseTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expenseTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExpenseTypeDTO result = expenseTypeService.update(expenseTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenseTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /expense-types/:id} : Partial updates given fields of an existing expenseType, field will ignore if it is null
     *
     * @param id the id of the expenseTypeDTO to save.
     * @param expenseTypeDTO the expenseTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenseTypeDTO,
     * or with status {@code 400 (Bad Request)} if the expenseTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the expenseTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the expenseTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/expense-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExpenseTypeDTO> partialUpdateExpenseType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExpenseTypeDTO expenseTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExpenseType partially : {}, {}", id, expenseTypeDTO);
        if (expenseTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expenseTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expenseTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExpenseTypeDTO> result = expenseTypeService.partialUpdate(expenseTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenseTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /expense-types} : get all the expenseTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expenseTypes in body.
     */
    @GetMapping("/expense-types")
    public ResponseEntity<List<ExpenseTypeDTO>> getAllExpenseTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ExpenseTypes");
        Page<ExpenseTypeDTO> page = expenseTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expense-types/:id} : get the "id" expenseType.
     *
     * @param id the id of the expenseTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expenseTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expense-types/{id}")
    public ResponseEntity<ExpenseTypeDTO> getExpenseType(@PathVariable Long id) {
        log.debug("REST request to get ExpenseType : {}", id);
        Optional<ExpenseTypeDTO> expenseTypeDTO = expenseTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expenseTypeDTO);
    }

    /**
     * {@code DELETE  /expense-types/:id} : delete the "id" expenseType.
     *
     * @param id the id of the expenseTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expense-types/{id}")
    public ResponseEntity<Void> deleteExpenseType(@PathVariable Long id) {
        log.debug("REST request to delete ExpenseType : {}", id);
        expenseTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
