package com.shop.report.web.rest;

import com.shop.report.repository.DebtReturnRepository;
import com.shop.report.service.DebtReturnService;
import com.shop.report.service.dto.DebtReturnDTO;
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
 * REST controller for managing {@link com.shop.report.domain.DebtReturn}.
 */
@RestController
@RequestMapping("/api")
public class DebtReturnResource {

    private final Logger log = LoggerFactory.getLogger(DebtReturnResource.class);

    private static final String ENTITY_NAME = "debtReturn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DebtReturnService debtReturnService;

    private final DebtReturnRepository debtReturnRepository;

    public DebtReturnResource(DebtReturnService debtReturnService, DebtReturnRepository debtReturnRepository) {
        this.debtReturnService = debtReturnService;
        this.debtReturnRepository = debtReturnRepository;
    }

    /**
     * {@code POST  /debt-returns} : Create a new debtReturn.
     *
     * @param debtReturnDTO the debtReturnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new debtReturnDTO, or with status {@code 400 (Bad Request)} if the debtReturn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/debt-returns")
    public ResponseEntity<DebtReturnDTO> createDebtReturn(@Valid @RequestBody DebtReturnDTO debtReturnDTO) throws URISyntaxException {
        log.debug("REST request to save DebtReturn : {}", debtReturnDTO);
        if (debtReturnDTO.getId() != null) {
            throw new BadRequestAlertException("A new debtReturn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DebtReturnDTO result = debtReturnService.save(debtReturnDTO);
        return ResponseEntity
            .created(new URI("/api/debt-returns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /debt-returns/:id} : Updates an existing debtReturn.
     *
     * @param id the id of the debtReturnDTO to save.
     * @param debtReturnDTO the debtReturnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated debtReturnDTO,
     * or with status {@code 400 (Bad Request)} if the debtReturnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the debtReturnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/debt-returns/{id}")
    public ResponseEntity<DebtReturnDTO> updateDebtReturn(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DebtReturnDTO debtReturnDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DebtReturn : {}, {}", id, debtReturnDTO);
        if (debtReturnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, debtReturnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!debtReturnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DebtReturnDTO result = debtReturnService.update(debtReturnDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, debtReturnDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /debt-returns/:id} : Partial updates given fields of an existing debtReturn, field will ignore if it is null
     *
     * @param id the id of the debtReturnDTO to save.
     * @param debtReturnDTO the debtReturnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated debtReturnDTO,
     * or with status {@code 400 (Bad Request)} if the debtReturnDTO is not valid,
     * or with status {@code 404 (Not Found)} if the debtReturnDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the debtReturnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/debt-returns/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DebtReturnDTO> partialUpdateDebtReturn(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DebtReturnDTO debtReturnDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DebtReturn partially : {}, {}", id, debtReturnDTO);
        if (debtReturnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, debtReturnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!debtReturnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DebtReturnDTO> result = debtReturnService.partialUpdate(debtReturnDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, debtReturnDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /debt-returns} : get all the debtReturns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of debtReturns in body.
     */
    @GetMapping("/debt-returns")
    public ResponseEntity<List<DebtReturnDTO>> getAllDebtReturns(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DebtReturns");
        Page<DebtReturnDTO> page = debtReturnService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /debt-returns/:id} : get the "id" debtReturn.
     *
     * @param id the id of the debtReturnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the debtReturnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/debt-returns/{id}")
    public ResponseEntity<DebtReturnDTO> getDebtReturn(@PathVariable Long id) {
        log.debug("REST request to get DebtReturn : {}", id);
        Optional<DebtReturnDTO> debtReturnDTO = debtReturnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(debtReturnDTO);
    }

    /**
     * {@code DELETE  /debt-returns/:id} : delete the "id" debtReturn.
     *
     * @param id the id of the debtReturnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/debt-returns/{id}")
    public ResponseEntity<Void> deleteDebtReturn(@PathVariable Long id) {
        log.debug("REST request to delete DebtReturn : {}", id);
        debtReturnService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
