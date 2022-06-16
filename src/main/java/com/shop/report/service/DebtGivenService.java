package com.shop.report.service;

import com.shop.report.domain.DebtGiven;
import com.shop.report.repository.DebtGivenRepository;
import com.shop.report.service.dto.DebtGivenDTO;
import com.shop.report.service.mapper.DebtGivenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DebtGiven}.
 */
@Service
@Transactional
public class DebtGivenService {

    private final Logger log = LoggerFactory.getLogger(DebtGivenService.class);

    private final DebtGivenRepository debtGivenRepository;

    private final DebtGivenMapper debtGivenMapper;

    public DebtGivenService(DebtGivenRepository debtGivenRepository, DebtGivenMapper debtGivenMapper) {
        this.debtGivenRepository = debtGivenRepository;
        this.debtGivenMapper = debtGivenMapper;
    }

    /**
     * Save a debtGiven.
     *
     * @param debtGivenDTO the entity to save.
     * @return the persisted entity.
     */
    public DebtGivenDTO save(DebtGivenDTO debtGivenDTO) {
        log.debug("Request to save DebtGiven : {}", debtGivenDTO);
        DebtGiven debtGiven = debtGivenMapper.toEntity(debtGivenDTO);
        debtGiven = debtGivenRepository.save(debtGiven);
        return debtGivenMapper.toDto(debtGiven);
    }

    /**
     * Update a debtGiven.
     *
     * @param debtGivenDTO the entity to save.
     * @return the persisted entity.
     */
    public DebtGivenDTO update(DebtGivenDTO debtGivenDTO) {
        log.debug("Request to save DebtGiven : {}", debtGivenDTO);
        DebtGiven debtGiven = debtGivenMapper.toEntity(debtGivenDTO);
        debtGiven = debtGivenRepository.save(debtGiven);
        return debtGivenMapper.toDto(debtGiven);
    }

    /**
     * Partially update a debtGiven.
     *
     * @param debtGivenDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DebtGivenDTO> partialUpdate(DebtGivenDTO debtGivenDTO) {
        log.debug("Request to partially update DebtGiven : {}", debtGivenDTO);

        return debtGivenRepository
            .findById(debtGivenDTO.getId())
            .map(existingDebtGiven -> {
                debtGivenMapper.partialUpdate(existingDebtGiven, debtGivenDTO);

                return existingDebtGiven;
            })
            .map(debtGivenRepository::save)
            .map(debtGivenMapper::toDto);
    }

    /**
     * Get all the debtGivens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DebtGivenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DebtGivens");
        return debtGivenRepository.findAll(pageable).map(debtGivenMapper::toDto);
    }

    /**
     * Get one debtGiven by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DebtGivenDTO> findOne(Long id) {
        log.debug("Request to get DebtGiven : {}", id);
        return debtGivenRepository.findById(id).map(debtGivenMapper::toDto);
    }

    /**
     * Delete the debtGiven by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DebtGiven : {}", id);
        debtGivenRepository.deleteById(id);
    }
}
