package com.shop.report.service;

import com.shop.report.domain.LiabilityRegistry;
import com.shop.report.repository.LiabilityRegistryRepository;
import com.shop.report.service.dto.LiabilityRegistryDTO;
import com.shop.report.service.mapper.LiabilityRegistryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LiabilityRegistry}.
 */
@Service
@Transactional
public class LiabilityRegistryService {

    private final Logger log = LoggerFactory.getLogger(LiabilityRegistryService.class);

    private final LiabilityRegistryRepository liabilityRegistryRepository;

    private final LiabilityRegistryMapper liabilityRegistryMapper;

    public LiabilityRegistryService(
        LiabilityRegistryRepository liabilityRegistryRepository,
        LiabilityRegistryMapper liabilityRegistryMapper
    ) {
        this.liabilityRegistryRepository = liabilityRegistryRepository;
        this.liabilityRegistryMapper = liabilityRegistryMapper;
    }

    /**
     * Save a liabilityRegistry.
     *
     * @param liabilityRegistryDTO the entity to save.
     * @return the persisted entity.
     */
    public LiabilityRegistryDTO save(LiabilityRegistryDTO liabilityRegistryDTO) {
        log.debug("Request to save LiabilityRegistry : {}", liabilityRegistryDTO);
        LiabilityRegistry liabilityRegistry = liabilityRegistryMapper.toEntity(liabilityRegistryDTO);
        liabilityRegistry = liabilityRegistryRepository.save(liabilityRegistry);
        return liabilityRegistryMapper.toDto(liabilityRegistry);
    }

    /**
     * Update a liabilityRegistry.
     *
     * @param liabilityRegistryDTO the entity to save.
     * @return the persisted entity.
     */
    public LiabilityRegistryDTO update(LiabilityRegistryDTO liabilityRegistryDTO) {
        log.debug("Request to save LiabilityRegistry : {}", liabilityRegistryDTO);
        LiabilityRegistry liabilityRegistry = liabilityRegistryMapper.toEntity(liabilityRegistryDTO);
        liabilityRegistry = liabilityRegistryRepository.save(liabilityRegistry);
        return liabilityRegistryMapper.toDto(liabilityRegistry);
    }

    /**
     * Partially update a liabilityRegistry.
     *
     * @param liabilityRegistryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LiabilityRegistryDTO> partialUpdate(LiabilityRegistryDTO liabilityRegistryDTO) {
        log.debug("Request to partially update LiabilityRegistry : {}", liabilityRegistryDTO);

        return liabilityRegistryRepository
            .findById(liabilityRegistryDTO.getId())
            .map(existingLiabilityRegistry -> {
                liabilityRegistryMapper.partialUpdate(existingLiabilityRegistry, liabilityRegistryDTO);

                return existingLiabilityRegistry;
            })
            .map(liabilityRegistryRepository::save)
            .map(liabilityRegistryMapper::toDto);
    }

    /**
     * Get all the liabilityRegistries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LiabilityRegistryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LiabilityRegistries");
        return liabilityRegistryRepository.findAll(pageable).map(liabilityRegistryMapper::toDto);
    }

    /**
     * Get one liabilityRegistry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LiabilityRegistryDTO> findOne(Long id) {
        log.debug("Request to get LiabilityRegistry : {}", id);
        return liabilityRegistryRepository.findById(id).map(liabilityRegistryMapper::toDto);
    }

    /**
     * Delete the liabilityRegistry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LiabilityRegistry : {}", id);
        liabilityRegistryRepository.deleteById(id);
    }
}
