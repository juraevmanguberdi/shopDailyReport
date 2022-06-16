package com.shop.report.web.rest;

import com.shop.report.repository.AssetLiveRepository;
import com.shop.report.service.AssetLiveService;
import com.shop.report.service.dto.AssetLiveDTO;
import com.shop.report.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.shop.report.domain.AssetLive}.
 */
@RestController
@RequestMapping("/api")
public class AssetLiveResource {

    private final Logger log = LoggerFactory.getLogger(AssetLiveResource.class);

    private static final String ENTITY_NAME = "assetLive";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetLiveService assetLiveService;

    private final AssetLiveRepository assetLiveRepository;

    public AssetLiveResource(AssetLiveService assetLiveService, AssetLiveRepository assetLiveRepository) {
        this.assetLiveService = assetLiveService;
        this.assetLiveRepository = assetLiveRepository;
    }

    /**
     * {@code POST  /asset-lives} : Create a new assetLive.
     *
     * @param assetLiveDTO the assetLiveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetLiveDTO, or with status {@code 400 (Bad Request)} if the assetLive has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-lives")
    public ResponseEntity<AssetLiveDTO> createAssetLive(@RequestBody AssetLiveDTO assetLiveDTO) throws URISyntaxException {
        log.debug("REST request to save AssetLive : {}", assetLiveDTO);
        if (assetLiveDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetLive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetLiveDTO result = assetLiveService.save(assetLiveDTO);
        return ResponseEntity
            .created(new URI("/api/asset-lives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-lives/:id} : Updates an existing assetLive.
     *
     * @param id the id of the assetLiveDTO to save.
     * @param assetLiveDTO the assetLiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetLiveDTO,
     * or with status {@code 400 (Bad Request)} if the assetLiveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetLiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-lives/{id}")
    public ResponseEntity<AssetLiveDTO> updateAssetLive(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetLiveDTO assetLiveDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AssetLive : {}, {}", id, assetLiveDTO);
        if (assetLiveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetLiveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetLiveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssetLiveDTO result = assetLiveService.update(assetLiveDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetLiveDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /asset-lives/:id} : Partial updates given fields of an existing assetLive, field will ignore if it is null
     *
     * @param id the id of the assetLiveDTO to save.
     * @param assetLiveDTO the assetLiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetLiveDTO,
     * or with status {@code 400 (Bad Request)} if the assetLiveDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assetLiveDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetLiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/asset-lives/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetLiveDTO> partialUpdateAssetLive(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetLiveDTO assetLiveDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssetLive partially : {}, {}", id, assetLiveDTO);
        if (assetLiveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetLiveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetLiveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetLiveDTO> result = assetLiveService.partialUpdate(assetLiveDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetLiveDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asset-lives} : get all the assetLives.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetLives in body.
     */
    @GetMapping("/asset-lives")
    public ResponseEntity<List<AssetLiveDTO>> getAllAssetLives(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AssetLives");
        Page<AssetLiveDTO> page = assetLiveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asset-lives/:id} : get the "id" assetLive.
     *
     * @param id the id of the assetLiveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetLiveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-lives/{id}")
    public ResponseEntity<AssetLiveDTO> getAssetLive(@PathVariable Long id) {
        log.debug("REST request to get AssetLive : {}", id);
        Optional<AssetLiveDTO> assetLiveDTO = assetLiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetLiveDTO);
    }

    /**
     * {@code DELETE  /asset-lives/:id} : delete the "id" assetLive.
     *
     * @param id the id of the assetLiveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-lives/{id}")
    public ResponseEntity<Void> deleteAssetLive(@PathVariable Long id) {
        log.debug("REST request to delete AssetLive : {}", id);
        assetLiveService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
