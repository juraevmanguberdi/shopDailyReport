package com.shop.report.web.rest;

import com.shop.report.repository.LiabilityRegistryRepository;
import com.shop.report.service.LiabilityRegistryService;
import com.shop.report.service.dto.LiabilityRegistryDTO;
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
 * REST controller for managing {@link com.shop.report.domain.LiabilityRegistry}.
 */
@RestController
@RequestMapping("/api")
public class LiabilityRegistryResource {

    private final Logger log = LoggerFactory.getLogger(LiabilityRegistryResource.class);

    private static final String ENTITY_NAME = "liabilityRegistry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LiabilityRegistryService liabilityRegistryService;

    private final LiabilityRegistryRepository liabilityRegistryRepository;

    public LiabilityRegistryResource(
        LiabilityRegistryService liabilityRegistryService,
        LiabilityRegistryRepository liabilityRegistryRepository
    ) {
        this.liabilityRegistryService = liabilityRegistryService;
        this.liabilityRegistryRepository = liabilityRegistryRepository;
    }

    /**
     * {@code POST  /liability-registries} : Create a new liabilityRegistry.
     *
     * @param liabilityRegistryDTO the liabilityRegistryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new liabilityRegistryDTO, or with status {@code 400 (Bad Request)} if the liabilityRegistry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/liability-registries")
    public ResponseEntity<LiabilityRegistryDTO> createLiabilityRegistry(@Valid @RequestBody LiabilityRegistryDTO liabilityRegistryDTO)
        throws URISyntaxException {
        log.debug("REST request to save LiabilityRegistry : {}", liabilityRegistryDTO);
        if (liabilityRegistryDTO.getId() != null) {
            throw new BadRequestAlertException("A new liabilityRegistry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LiabilityRegistryDTO result = liabilityRegistryService.save(liabilityRegistryDTO);
        return ResponseEntity
            .created(new URI("/api/liability-registries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /liability-registries/:id} : Updates an existing liabilityRegistry.
     *
     * @param id the id of the liabilityRegistryDTO to save.
     * @param liabilityRegistryDTO the liabilityRegistryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated liabilityRegistryDTO,
     * or with status {@code 400 (Bad Request)} if the liabilityRegistryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the liabilityRegistryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/liability-registries/{id}")
    public ResponseEntity<LiabilityRegistryDTO> updateLiabilityRegistry(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LiabilityRegistryDTO liabilityRegistryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LiabilityRegistry : {}, {}", id, liabilityRegistryDTO);
        if (liabilityRegistryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, liabilityRegistryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!liabilityRegistryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LiabilityRegistryDTO result = liabilityRegistryService.update(liabilityRegistryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, liabilityRegistryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /liability-registries/:id} : Partial updates given fields of an existing liabilityRegistry, field will ignore if it is null
     *
     * @param id the id of the liabilityRegistryDTO to save.
     * @param liabilityRegistryDTO the liabilityRegistryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated liabilityRegistryDTO,
     * or with status {@code 400 (Bad Request)} if the liabilityRegistryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the liabilityRegistryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the liabilityRegistryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/liability-registries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LiabilityRegistryDTO> partialUpdateLiabilityRegistry(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LiabilityRegistryDTO liabilityRegistryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LiabilityRegistry partially : {}, {}", id, liabilityRegistryDTO);
        if (liabilityRegistryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, liabilityRegistryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!liabilityRegistryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LiabilityRegistryDTO> result = liabilityRegistryService.partialUpdate(liabilityRegistryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, liabilityRegistryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /liability-registries} : get all the liabilityRegistries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of liabilityRegistries in body.
     */
    @GetMapping("/liability-registries")
    public ResponseEntity<List<LiabilityRegistryDTO>> getAllLiabilityRegistries(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LiabilityRegistries");
        Page<LiabilityRegistryDTO> page = liabilityRegistryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /liability-registries/:id} : get the "id" liabilityRegistry.
     *
     * @param id the id of the liabilityRegistryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the liabilityRegistryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/liability-registries/{id}")
    public ResponseEntity<LiabilityRegistryDTO> getLiabilityRegistry(@PathVariable Long id) {
        log.debug("REST request to get LiabilityRegistry : {}", id);
        Optional<LiabilityRegistryDTO> liabilityRegistryDTO = liabilityRegistryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(liabilityRegistryDTO);
    }

    /**
     * {@code DELETE  /liability-registries/:id} : delete the "id" liabilityRegistry.
     *
     * @param id the id of the liabilityRegistryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/liability-registries/{id}")
    public ResponseEntity<Void> deleteLiabilityRegistry(@PathVariable Long id) {
        log.debug("REST request to delete LiabilityRegistry : {}", id);
        liabilityRegistryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
