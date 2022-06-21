package com.shop.report.service;

import com.shop.report.domain.BorrowedDebt;
import com.shop.report.repository.BorrowedDebtRepository;
import com.shop.report.service.dto.BorrowedDebtDTO;
import com.shop.report.service.mapper.BorrowedDebtMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BorrowedDebt}.
 */
@Service
@Transactional
public class BorrowedDebtService {

    private final Logger log = LoggerFactory.getLogger(BorrowedDebtService.class);

    private final BorrowedDebtRepository borrowedDebtRepository;

    private final BorrowedDebtMapper borrowedDebtMapper;

    public BorrowedDebtService(BorrowedDebtRepository borrowedDebtRepository, BorrowedDebtMapper borrowedDebtMapper) {
        this.borrowedDebtRepository = borrowedDebtRepository;
        this.borrowedDebtMapper = borrowedDebtMapper;
    }

    /**
     * Save a borrowedDebt.
     *
     * @param borrowedDebtDTO the entity to save.
     * @return the persisted entity.
     */
    public BorrowedDebtDTO save(BorrowedDebtDTO borrowedDebtDTO) {
        log.debug("Request to save BorrowedDebt : {}", borrowedDebtDTO);
        BorrowedDebt borrowedDebt = borrowedDebtMapper.toEntity(borrowedDebtDTO);
        borrowedDebt = borrowedDebtRepository.save(borrowedDebt);
        return borrowedDebtMapper.toDto(borrowedDebt);
    }

    /**
     * Update a borrowedDebt.
     *
     * @param borrowedDebtDTO the entity to save.
     * @return the persisted entity.
     */
    public BorrowedDebtDTO update(BorrowedDebtDTO borrowedDebtDTO) {
        log.debug("Request to save BorrowedDebt : {}", borrowedDebtDTO);
        BorrowedDebt borrowedDebt = borrowedDebtMapper.toEntity(borrowedDebtDTO);
        borrowedDebt = borrowedDebtRepository.save(borrowedDebt);
        return borrowedDebtMapper.toDto(borrowedDebt);
    }

    /**
     * Partially update a borrowedDebt.
     *
     * @param borrowedDebtDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BorrowedDebtDTO> partialUpdate(BorrowedDebtDTO borrowedDebtDTO) {
        log.debug("Request to partially update BorrowedDebt : {}", borrowedDebtDTO);

        return borrowedDebtRepository
            .findById(borrowedDebtDTO.getId())
            .map(existingBorrowedDebt -> {
                borrowedDebtMapper.partialUpdate(existingBorrowedDebt, borrowedDebtDTO);

                return existingBorrowedDebt;
            })
            .map(borrowedDebtRepository::save)
            .map(borrowedDebtMapper::toDto);
    }

    /**
     * Get all the borrowedDebts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BorrowedDebtDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BorrowedDebts");
        return borrowedDebtRepository.findAll(pageable).map(borrowedDebtMapper::toDto);
    }

    /**
     * Get one borrowedDebt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BorrowedDebtDTO> findOne(Long id) {
        log.debug("Request to get BorrowedDebt : {}", id);
        return borrowedDebtRepository.findById(id).map(borrowedDebtMapper::toDto);
    }

    /**
     * Delete the borrowedDebt by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BorrowedDebt : {}", id);
        borrowedDebtRepository.deleteById(id);
    }
}
