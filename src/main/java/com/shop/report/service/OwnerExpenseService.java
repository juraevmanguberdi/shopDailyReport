package com.shop.report.service;

import com.shop.report.domain.OwnerExpense;
import com.shop.report.repository.OwnerExpenseRepository;
import com.shop.report.service.dto.OwnerExpenseDTO;
import com.shop.report.service.mapper.OwnerExpenseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OwnerExpense}.
 */
@Service
@Transactional
public class OwnerExpenseService {

    private final Logger log = LoggerFactory.getLogger(OwnerExpenseService.class);

    private final OwnerExpenseRepository ownerExpenseRepository;

    private final OwnerExpenseMapper ownerExpenseMapper;

    public OwnerExpenseService(OwnerExpenseRepository ownerExpenseRepository, OwnerExpenseMapper ownerExpenseMapper) {
        this.ownerExpenseRepository = ownerExpenseRepository;
        this.ownerExpenseMapper = ownerExpenseMapper;
    }

    /**
     * Save a ownerExpense.
     *
     * @param ownerExpenseDTO the entity to save.
     * @return the persisted entity.
     */
    public OwnerExpenseDTO save(OwnerExpenseDTO ownerExpenseDTO) {
        log.debug("Request to save OwnerExpense : {}", ownerExpenseDTO);
        OwnerExpense ownerExpense = ownerExpenseMapper.toEntity(ownerExpenseDTO);
        ownerExpense = ownerExpenseRepository.save(ownerExpense);
        return ownerExpenseMapper.toDto(ownerExpense);
    }

    /**
     * Update a ownerExpense.
     *
     * @param ownerExpenseDTO the entity to save.
     * @return the persisted entity.
     */
    public OwnerExpenseDTO update(OwnerExpenseDTO ownerExpenseDTO) {
        log.debug("Request to save OwnerExpense : {}", ownerExpenseDTO);
        OwnerExpense ownerExpense = ownerExpenseMapper.toEntity(ownerExpenseDTO);
        ownerExpense = ownerExpenseRepository.save(ownerExpense);
        return ownerExpenseMapper.toDto(ownerExpense);
    }

    /**
     * Partially update a ownerExpense.
     *
     * @param ownerExpenseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OwnerExpenseDTO> partialUpdate(OwnerExpenseDTO ownerExpenseDTO) {
        log.debug("Request to partially update OwnerExpense : {}", ownerExpenseDTO);

        return ownerExpenseRepository
            .findById(ownerExpenseDTO.getId())
            .map(existingOwnerExpense -> {
                ownerExpenseMapper.partialUpdate(existingOwnerExpense, ownerExpenseDTO);

                return existingOwnerExpense;
            })
            .map(ownerExpenseRepository::save)
            .map(ownerExpenseMapper::toDto);
    }

    /**
     * Get all the ownerExpenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OwnerExpenseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OwnerExpenses");
        return ownerExpenseRepository.findAll(pageable).map(ownerExpenseMapper::toDto);
    }

    /**
     * Get one ownerExpense by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OwnerExpenseDTO> findOne(Long id) {
        log.debug("Request to get OwnerExpense : {}", id);
        return ownerExpenseRepository.findById(id).map(ownerExpenseMapper::toDto);
    }

    /**
     * Delete the ownerExpense by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OwnerExpense : {}", id);
        ownerExpenseRepository.deleteById(id);
    }
}
