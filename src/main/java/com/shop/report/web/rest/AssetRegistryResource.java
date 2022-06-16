package com.shop.report.web.rest;

import com.shop.report.repository.AssetRegistryRepository;
import com.shop.report.service.AssetRegistryService;
import com.shop.report.service.dto.AssetRegistryDTO;
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
 * REST controller for managing {@link com.shop.report.domain.AssetRegistry}.
 */
@RestController
@RequestMapping("/api")
public class AssetRegistryResource {

    private final Logger log = LoggerFactory.getLogger(AssetRegistryResource.class);

    private static final String ENTITY_NAME = "assetRegistry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetRegistryService assetRegistryService;

    private final AssetRegistryRepository assetRegistryRepository;

    public AssetRegistryResource(AssetRegistryService assetRegistryService, AssetRegistryRepository assetRegistryRepository) {
        this.assetRegistryService = assetRegistryService;
        this.assetRegistryRepository = assetRegistryRepository;
    }

    /**
     * {@code POST  /asset-registries} : Create a new assetRegistry.
     *
     * @param assetRegistryDTO the assetRegistryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetRegistryDTO, or with status {@code 400 (Bad Request)} if the assetRegistry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-registries")
    public ResponseEntity<AssetRegistryDTO> createAssetRegistry(@Valid @RequestBody AssetRegistryDTO assetRegistryDTO)
        throws URISyntaxException {
        log.debug("REST request to save AssetRegistry : {}", assetRegistryDTO);
        if (assetRegistryDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetRegistry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetRegistryDTO result = assetRegistryService.save(assetRegistryDTO);
        return ResponseEntity
            .created(new URI("/api/asset-registries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-registries/:id} : Updates an existing assetRegistry.
     *
     * @param id the id of the assetRegistryDTO to save.
     * @param assetRegistryDTO the assetRegistryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetRegistryDTO,
     * or with status {@code 400 (Bad Request)} if the assetRegistryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetRegistryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-registries/{id}")
    public ResponseEntity<AssetRegistryDTO> updateAssetRegistry(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssetRegistryDTO assetRegistryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AssetRegistry : {}, {}", id, assetRegistryDTO);
        if (assetRegistryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetRegistryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetRegistryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssetRegistryDTO result = assetRegistryService.update(assetRegistryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetRegistryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /asset-registries/:id} : Partial updates given fields of an existing assetRegistry, field will ignore if it is null
     *
     * @param id the id of the assetRegistryDTO to save.
     * @param assetRegistryDTO the assetRegistryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetRegistryDTO,
     * or with status {@code 400 (Bad Request)} if the assetRegistryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assetRegistryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetRegistryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/asset-registries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetRegistryDTO> partialUpdateAssetRegistry(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssetRegistryDTO assetRegistryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssetRegistry partially : {}, {}", id, assetRegistryDTO);
        if (assetRegistryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetRegistryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetRegistryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetRegistryDTO> result = assetRegistryService.partialUpdate(assetRegistryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetRegistryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asset-registries} : get all the assetRegistries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetRegistries in body.
     */
    @GetMapping("/asset-registries")
    public ResponseEntity<List<AssetRegistryDTO>> getAllAssetRegistries(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AssetRegistries");
        Page<AssetRegistryDTO> page = assetRegistryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asset-registries/:id} : get the "id" assetRegistry.
     *
     * @param id the id of the assetRegistryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetRegistryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-registries/{id}")
    public ResponseEntity<AssetRegistryDTO> getAssetRegistry(@PathVariable Long id) {
        log.debug("REST request to get AssetRegistry : {}", id);
        Optional<AssetRegistryDTO> assetRegistryDTO = assetRegistryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetRegistryDTO);
    }

    /**
     * {@code DELETE  /asset-registries/:id} : delete the "id" assetRegistry.
     *
     * @param id the id of the assetRegistryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-registries/{id}")
    public ResponseEntity<Void> deleteAssetRegistry(@PathVariable Long id) {
        log.debug("REST request to delete AssetRegistry : {}", id);
        assetRegistryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
