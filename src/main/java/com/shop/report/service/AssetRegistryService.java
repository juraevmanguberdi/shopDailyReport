package com.shop.report.service;

import com.shop.report.domain.AssetRegistry;
import com.shop.report.repository.AssetRegistryRepository;
import com.shop.report.service.dto.AssetRegistryDTO;
import com.shop.report.service.mapper.AssetRegistryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AssetRegistry}.
 */
@Service
@Transactional
public class AssetRegistryService {

    private final Logger log = LoggerFactory.getLogger(AssetRegistryService.class);

    private final AssetRegistryRepository assetRegistryRepository;

    private final AssetRegistryMapper assetRegistryMapper;

    public AssetRegistryService(AssetRegistryRepository assetRegistryRepository, AssetRegistryMapper assetRegistryMapper) {
        this.assetRegistryRepository = assetRegistryRepository;
        this.assetRegistryMapper = assetRegistryMapper;
    }

    /**
     * Save a assetRegistry.
     *
     * @param assetRegistryDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetRegistryDTO save(AssetRegistryDTO assetRegistryDTO) {
        log.debug("Request to save AssetRegistry : {}", assetRegistryDTO);
        AssetRegistry assetRegistry = assetRegistryMapper.toEntity(assetRegistryDTO);
        assetRegistry = assetRegistryRepository.save(assetRegistry);
        return assetRegistryMapper.toDto(assetRegistry);
    }

    /**
     * Update a assetRegistry.
     *
     * @param assetRegistryDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetRegistryDTO update(AssetRegistryDTO assetRegistryDTO) {
        log.debug("Request to save AssetRegistry : {}", assetRegistryDTO);
        AssetRegistry assetRegistry = assetRegistryMapper.toEntity(assetRegistryDTO);
        assetRegistry = assetRegistryRepository.save(assetRegistry);
        return assetRegistryMapper.toDto(assetRegistry);
    }

    /**
     * Partially update a assetRegistry.
     *
     * @param assetRegistryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssetRegistryDTO> partialUpdate(AssetRegistryDTO assetRegistryDTO) {
        log.debug("Request to partially update AssetRegistry : {}", assetRegistryDTO);

        return assetRegistryRepository
            .findById(assetRegistryDTO.getId())
            .map(existingAssetRegistry -> {
                assetRegistryMapper.partialUpdate(existingAssetRegistry, assetRegistryDTO);

                return existingAssetRegistry;
            })
            .map(assetRegistryRepository::save)
            .map(assetRegistryMapper::toDto);
    }

    /**
     * Get all the assetRegistries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetRegistryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssetRegistries");
        return assetRegistryRepository.findAll(pageable).map(assetRegistryMapper::toDto);
    }

    /**
     * Get one assetRegistry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssetRegistryDTO> findOne(Long id) {
        log.debug("Request to get AssetRegistry : {}", id);
        return assetRegistryRepository.findById(id).map(assetRegistryMapper::toDto);
    }

    /**
     * Delete the assetRegistry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssetRegistry : {}", id);
        assetRegistryRepository.deleteById(id);
    }
}
