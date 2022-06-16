package com.shop.report.service;

import com.shop.report.domain.AssetLive;
import com.shop.report.repository.AssetLiveRepository;
import com.shop.report.service.dto.AssetLiveDTO;
import com.shop.report.service.mapper.AssetLiveMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AssetLive}.
 */
@Service
@Transactional
public class AssetLiveService {

    private final Logger log = LoggerFactory.getLogger(AssetLiveService.class);

    private final AssetLiveRepository assetLiveRepository;

    private final AssetLiveMapper assetLiveMapper;

    public AssetLiveService(AssetLiveRepository assetLiveRepository, AssetLiveMapper assetLiveMapper) {
        this.assetLiveRepository = assetLiveRepository;
        this.assetLiveMapper = assetLiveMapper;
    }

    /**
     * Save a assetLive.
     *
     * @param assetLiveDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetLiveDTO save(AssetLiveDTO assetLiveDTO) {
        log.debug("Request to save AssetLive : {}", assetLiveDTO);
        AssetLive assetLive = assetLiveMapper.toEntity(assetLiveDTO);
        assetLive = assetLiveRepository.save(assetLive);
        return assetLiveMapper.toDto(assetLive);
    }

    /**
     * Update a assetLive.
     *
     * @param assetLiveDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetLiveDTO update(AssetLiveDTO assetLiveDTO) {
        log.debug("Request to save AssetLive : {}", assetLiveDTO);
        AssetLive assetLive = assetLiveMapper.toEntity(assetLiveDTO);
        assetLive = assetLiveRepository.save(assetLive);
        return assetLiveMapper.toDto(assetLive);
    }

    /**
     * Partially update a assetLive.
     *
     * @param assetLiveDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssetLiveDTO> partialUpdate(AssetLiveDTO assetLiveDTO) {
        log.debug("Request to partially update AssetLive : {}", assetLiveDTO);

        return assetLiveRepository
            .findById(assetLiveDTO.getId())
            .map(existingAssetLive -> {
                assetLiveMapper.partialUpdate(existingAssetLive, assetLiveDTO);

                return existingAssetLive;
            })
            .map(assetLiveRepository::save)
            .map(assetLiveMapper::toDto);
    }

    /**
     * Get all the assetLives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetLiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssetLives");
        return assetLiveRepository.findAll(pageable).map(assetLiveMapper::toDto);
    }

    /**
     * Get one assetLive by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssetLiveDTO> findOne(Long id) {
        log.debug("Request to get AssetLive : {}", id);
        return assetLiveRepository.findById(id).map(assetLiveMapper::toDto);
    }

    /**
     * Delete the assetLive by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssetLive : {}", id);
        assetLiveRepository.deleteById(id);
    }
}
