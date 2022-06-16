package com.shop.report.web.rest;

import com.shop.report.repository.LiabilityLiveRepository;
import com.shop.report.service.LiabilityLiveService;
import com.shop.report.service.dto.LiabilityLiveDTO;
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
 * REST controller for managing {@link com.shop.report.domain.LiabilityLive}.
 */
@RestController
@RequestMapping("/api")
public class LiabilityLiveResource {

    private final Logger log = LoggerFactory.getLogger(LiabilityLiveResource.class);

    private static final String ENTITY_NAME = "liabilityLive";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LiabilityLiveService liabilityLiveService;

    private final LiabilityLiveRepository liabilityLiveRepository;

    public LiabilityLiveResource(LiabilityLiveService liabilityLiveService, LiabilityLiveRepository liabilityLiveRepository) {
        this.liabilityLiveService = liabilityLiveService;
        this.liabilityLiveRepository = liabilityLiveRepository;
    }

    /**
     * {@code POST  /liability-lives} : Create a new liabilityLive.
     *
     * @param liabilityLiveDTO the liabilityLiveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new liabilityLiveDTO, or with status {@code 400 (Bad Request)} if the liabilityLive has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/liability-lives")
    public ResponseEntity<LiabilityLiveDTO> createLiabilityLive(@RequestBody LiabilityLiveDTO liabilityLiveDTO) throws URISyntaxException {
        log.debug("REST request to save LiabilityLive : {}", liabilityLiveDTO);
        if (liabilityLiveDTO.getId() != null) {
            throw new BadRequestAlertException("A new liabilityLive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LiabilityLiveDTO result = liabilityLiveService.save(liabilityLiveDTO);
        return ResponseEntity
            .created(new URI("/api/liability-lives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /liability-lives/:id} : Updates an existing liabilityLive.
     *
     * @param id the id of the liabilityLiveDTO to save.
     * @param liabilityLiveDTO the liabilityLiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated liabilityLiveDTO,
     * or with status {@code 400 (Bad Request)} if the liabilityLiveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the liabilityLiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/liability-lives/{id}")
    public ResponseEntity<LiabilityLiveDTO> updateLiabilityLive(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LiabilityLiveDTO liabilityLiveDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LiabilityLive : {}, {}", id, liabilityLiveDTO);
        if (liabilityLiveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, liabilityLiveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!liabilityLiveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LiabilityLiveDTO result = liabilityLiveService.update(liabilityLiveDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, liabilityLiveDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /liability-lives/:id} : Partial updates given fields of an existing liabilityLive, field will ignore if it is null
     *
     * @param id the id of the liabilityLiveDTO to save.
     * @param liabilityLiveDTO the liabilityLiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated liabilityLiveDTO,
     * or with status {@code 400 (Bad Request)} if the liabilityLiveDTO is not valid,
     * or with status {@code 404 (Not Found)} if the liabilityLiveDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the liabilityLiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/liability-lives/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LiabilityLiveDTO> partialUpdateLiabilityLive(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LiabilityLiveDTO liabilityLiveDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LiabilityLive partially : {}, {}", id, liabilityLiveDTO);
        if (liabilityLiveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, liabilityLiveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!liabilityLiveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LiabilityLiveDTO> result = liabilityLiveService.partialUpdate(liabilityLiveDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, liabilityLiveDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /liability-lives} : get all the liabilityLives.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of liabilityLives in body.
     */
    @GetMapping("/liability-lives")
    public ResponseEntity<List<LiabilityLiveDTO>> getAllLiabilityLives(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LiabilityLives");
        Page<LiabilityLiveDTO> page = liabilityLiveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /liability-lives/:id} : get the "id" liabilityLive.
     *
     * @param id the id of the liabilityLiveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the liabilityLiveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/liability-lives/{id}")
    public ResponseEntity<LiabilityLiveDTO> getLiabilityLive(@PathVariable Long id) {
        log.debug("REST request to get LiabilityLive : {}", id);
        Optional<LiabilityLiveDTO> liabilityLiveDTO = liabilityLiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(liabilityLiveDTO);
    }

    /**
     * {@code DELETE  /liability-lives/:id} : delete the "id" liabilityLive.
     *
     * @param id the id of the liabilityLiveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/liability-lives/{id}")
    public ResponseEntity<Void> deleteLiabilityLive(@PathVariable Long id) {
        log.debug("REST request to delete LiabilityLive : {}", id);
        liabilityLiveService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
