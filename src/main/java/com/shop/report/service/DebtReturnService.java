package com.shop.report.service;

import com.shop.report.domain.DebtReturn;
import com.shop.report.repository.DebtReturnRepository;
import com.shop.report.service.dto.DebtReturnDTO;
import com.shop.report.service.mapper.DebtReturnMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DebtReturn}.
 */
@Service
@Transactional
public class DebtReturnService {

    private final Logger log = LoggerFactory.getLogger(DebtReturnService.class);

    private final DebtReturnRepository debtReturnRepository;

    private final DebtReturnMapper debtReturnMapper;

    public DebtReturnService(DebtReturnRepository debtReturnRepository, DebtReturnMapper debtReturnMapper) {
        this.debtReturnRepository = debtReturnRepository;
        this.debtReturnMapper = debtReturnMapper;
    }

    /**
     * Save a debtReturn.
     *
     * @param debtReturnDTO the entity to save.
     * @return the persisted entity.
     */
    public DebtReturnDTO save(DebtReturnDTO debtReturnDTO) {
        log.debug("Request to save DebtReturn : {}", debtReturnDTO);
        DebtReturn debtReturn = debtReturnMapper.toEntity(debtReturnDTO);
        debtReturn = debtReturnRepository.save(debtReturn);
        return debtReturnMapper.toDto(debtReturn);
    }

    /**
     * Update a debtReturn.
     *
     * @param debtReturnDTO the entity to save.
     * @return the persisted entity.
     */
    public DebtReturnDTO update(DebtReturnDTO debtReturnDTO) {
        log.debug("Request to save DebtReturn : {}", debtReturnDTO);
        DebtReturn debtReturn = debtReturnMapper.toEntity(debtReturnDTO);
        debtReturn = debtReturnRepository.save(debtReturn);
        return debtReturnMapper.toDto(debtReturn);
    }

    /**
     * Partially update a debtReturn.
     *
     * @param debtReturnDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DebtReturnDTO> partialUpdate(DebtReturnDTO debtReturnDTO) {
        log.debug("Request to partially update DebtReturn : {}", debtReturnDTO);

        return debtReturnRepository
            .findById(debtReturnDTO.getId())
            .map(existingDebtReturn -> {
                debtReturnMapper.partialUpdate(existingDebtReturn, debtReturnDTO);

                return existingDebtReturn;
            })
            .map(debtReturnRepository::save)
            .map(debtReturnMapper::toDto);
    }

    /**
     * Get all the debtReturns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DebtReturnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DebtReturns");
        return debtReturnRepository.findAll(pageable).map(debtReturnMapper::toDto);
    }

    /**
     * Get one debtReturn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DebtReturnDTO> findOne(Long id) {
        log.debug("Request to get DebtReturn : {}", id);
        return debtReturnRepository.findById(id).map(debtReturnMapper::toDto);
    }

    /**
     * Delete the debtReturn by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DebtReturn : {}", id);
        debtReturnRepository.deleteById(id);
    }
}
