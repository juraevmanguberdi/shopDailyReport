package com.shop.report.web.rest;

import com.shop.report.repository.DailyRegistryShopRepository;
import com.shop.report.service.DailyRegistryShopService;
import com.shop.report.service.dto.DailyRegistryShopDTO;
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
 * REST controller for managing {@link com.shop.report.domain.DailyRegistryShop}.
 */
@RestController
@RequestMapping("/api")
public class DailyRegistryShopResource {

    private final Logger log = LoggerFactory.getLogger(DailyRegistryShopResource.class);

    private static final String ENTITY_NAME = "dailyRegistryShop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyRegistryShopService dailyRegistryShopService;

    private final DailyRegistryShopRepository dailyRegistryShopRepository;

    public DailyRegistryShopResource(
        DailyRegistryShopService dailyRegistryShopService,
        DailyRegistryShopRepository dailyRegistryShopRepository
    ) {
        this.dailyRegistryShopService = dailyRegistryShopService;
        this.dailyRegistryShopRepository = dailyRegistryShopRepository;
    }

    /**
     * {@code POST  /daily-registry-shops} : Create a new dailyRegistryShop.
     *
     * @param dailyRegistryShopDTO the dailyRegistryShopDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyRegistryShopDTO, or with status {@code 400 (Bad Request)} if the dailyRegistryShop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-registry-shops")
    public ResponseEntity<DailyRegistryShopDTO> createDailyRegistryShop(@Valid @RequestBody DailyRegistryShopDTO dailyRegistryShopDTO)
        throws URISyntaxException {
        log.debug("REST request to save DailyRegistryShop : {}", dailyRegistryShopDTO);
        if (dailyRegistryShopDTO.getId() != null) {
            throw new BadRequestAlertException("A new dailyRegistryShop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyRegistryShopDTO result = dailyRegistryShopService.save(dailyRegistryShopDTO);
        return ResponseEntity
            .created(new URI("/api/daily-registry-shops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-registry-shops/:id} : Updates an existing dailyRegistryShop.
     *
     * @param id the id of the dailyRegistryShopDTO to save.
     * @param dailyRegistryShopDTO the dailyRegistryShopDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyRegistryShopDTO,
     * or with status {@code 400 (Bad Request)} if the dailyRegistryShopDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyRegistryShopDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-registry-shops/{id}")
    public ResponseEntity<DailyRegistryShopDTO> updateDailyRegistryShop(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DailyRegistryShopDTO dailyRegistryShopDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DailyRegistryShop : {}, {}", id, dailyRegistryShopDTO);
        if (dailyRegistryShopDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyRegistryShopDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyRegistryShopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DailyRegistryShopDTO result = dailyRegistryShopService.update(dailyRegistryShopDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyRegistryShopDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /daily-registry-shops/:id} : Partial updates given fields of an existing dailyRegistryShop, field will ignore if it is null
     *
     * @param id the id of the dailyRegistryShopDTO to save.
     * @param dailyRegistryShopDTO the dailyRegistryShopDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyRegistryShopDTO,
     * or with status {@code 400 (Bad Request)} if the dailyRegistryShopDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dailyRegistryShopDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dailyRegistryShopDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/daily-registry-shops/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DailyRegistryShopDTO> partialUpdateDailyRegistryShop(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DailyRegistryShopDTO dailyRegistryShopDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DailyRegistryShop partially : {}, {}", id, dailyRegistryShopDTO);
        if (dailyRegistryShopDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyRegistryShopDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyRegistryShopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DailyRegistryShopDTO> result = dailyRegistryShopService.partialUpdate(dailyRegistryShopDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyRegistryShopDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /daily-registry-shops} : get all the dailyRegistryShops.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyRegistryShops in body.
     */
    @GetMapping("/daily-registry-shops")
    public ResponseEntity<List<DailyRegistryShopDTO>> getAllDailyRegistryShops(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of DailyRegistryShops");
        Page<DailyRegistryShopDTO> page = dailyRegistryShopService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /daily-registry-shops/:id} : get the "id" dailyRegistryShop.
     *
     * @param id the id of the dailyRegistryShopDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyRegistryShopDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-registry-shops/{id}")
    public ResponseEntity<DailyRegistryShopDTO> getDailyRegistryShop(@PathVariable Long id) {
        log.debug("REST request to get DailyRegistryShop : {}", id);
        Optional<DailyRegistryShopDTO> dailyRegistryShopDTO = dailyRegistryShopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dailyRegistryShopDTO);
    }

    /**
     * {@code DELETE  /daily-registry-shops/:id} : delete the "id" dailyRegistryShop.
     *
     * @param id the id of the dailyRegistryShopDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-registry-shops/{id}")
    public ResponseEntity<Void> deleteDailyRegistryShop(@PathVariable Long id) {
        log.debug("REST request to delete DailyRegistryShop : {}", id);
        dailyRegistryShopService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
