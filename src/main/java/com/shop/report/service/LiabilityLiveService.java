package com.shop.report.service;

import com.shop.report.domain.LiabilityLive;
import com.shop.report.repository.LiabilityLiveRepository;
import com.shop.report.service.dto.LiabilityLiveDTO;
import com.shop.report.service.mapper.LiabilityLiveMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LiabilityLive}.
 */
@Service
@Transactional
public class LiabilityLiveService {

    private final Logger log = LoggerFactory.getLogger(LiabilityLiveService.class);

    private final LiabilityLiveRepository liabilityLiveRepository;

    private final LiabilityLiveMapper liabilityLiveMapper;

    public LiabilityLiveService(LiabilityLiveRepository liabilityLiveRepository, LiabilityLiveMapper liabilityLiveMapper) {
        this.liabilityLiveRepository = liabilityLiveRepository;
        this.liabilityLiveMapper = liabilityLiveMapper;
    }

    /**
     * Save a liabilityLive.
     *
     * @param liabilityLiveDTO the entity to save.
     * @return the persisted entity.
     */
    public LiabilityLiveDTO save(LiabilityLiveDTO liabilityLiveDTO) {
        log.debug("Request to save LiabilityLive : {}", liabilityLiveDTO);
        LiabilityLive liabilityLive = liabilityLiveMapper.toEntity(liabilityLiveDTO);
        liabilityLive = liabilityLiveRepository.save(liabilityLive);
        return liabilityLiveMapper.toDto(liabilityLive);
    }

    /**
     * Update a liabilityLive.
     *
     * @param liabilityLiveDTO the entity to save.
     * @return the persisted entity.
     */
    public LiabilityLiveDTO update(LiabilityLiveDTO liabilityLiveDTO) {
        log.debug("Request to save LiabilityLive : {}", liabilityLiveDTO);
        LiabilityLive liabilityLive = liabilityLiveMapper.toEntity(liabilityLiveDTO);
        liabilityLive = liabilityLiveRepository.save(liabilityLive);
        return liabilityLiveMapper.toDto(liabilityLive);
    }

    /**
     * Partially update a liabilityLive.
     *
     * @param liabilityLiveDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LiabilityLiveDTO> partialUpdate(LiabilityLiveDTO liabilityLiveDTO) {
        log.debug("Request to partially update LiabilityLive : {}", liabilityLiveDTO);

        return liabilityLiveRepository
            .findById(liabilityLiveDTO.getId())
            .map(existingLiabilityLive -> {
                liabilityLiveMapper.partialUpdate(existingLiabilityLive, liabilityLiveDTO);

                return existingLiabilityLive;
            })
            .map(liabilityLiveRepository::save)
            .map(liabilityLiveMapper::toDto);
    }

    /**
     * Get all the liabilityLives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LiabilityLiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LiabilityLives");
        return liabilityLiveRepository.findAll(pageable).map(liabilityLiveMapper::toDto);
    }

    /**
     * Get one liabilityLive by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LiabilityLiveDTO> findOne(Long id) {
        log.debug("Request to get LiabilityLive : {}", id);
        return liabilityLiveRepository.findById(id).map(liabilityLiveMapper::toDto);
    }

    /**
     * Delete the liabilityLive by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LiabilityLive : {}", id);
        liabilityLiveRepository.deleteById(id);
    }
}
