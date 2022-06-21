package com.shop.report.service;

import com.shop.report.domain.BorrowedDebtType;
import com.shop.report.repository.BorrowedDebtTypeRepository;
import com.shop.report.service.dto.BorrowedDebtTypeDTO;
import com.shop.report.service.mapper.BorrowedDebtTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BorrowedDebtType}.
 */
@Service
@Transactional
public class BorrowedDebtTypeService {

    private final Logger log = LoggerFactory.getLogger(BorrowedDebtTypeService.class);

    private final BorrowedDebtTypeRepository borrowedDebtTypeRepository;

    private final BorrowedDebtTypeMapper borrowedDebtTypeMapper;

    public BorrowedDebtTypeService(BorrowedDebtTypeRepository borrowedDebtTypeRepository, BorrowedDebtTypeMapper borrowedDebtTypeMapper) {
        this.borrowedDebtTypeRepository = borrowedDebtTypeRepository;
        this.borrowedDebtTypeMapper = borrowedDebtTypeMapper;
    }

    /**
     * Save a borrowedDebtType.
     *
     * @param borrowedDebtTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public BorrowedDebtTypeDTO save(BorrowedDebtTypeDTO borrowedDebtTypeDTO) {
        log.debug("Request to save BorrowedDebtType : {}", borrowedDebtTypeDTO);
        BorrowedDebtType borrowedDebtType = borrowedDebtTypeMapper.toEntity(borrowedDebtTypeDTO);
        borrowedDebtType = borrowedDebtTypeRepository.save(borrowedDebtType);
        return borrowedDebtTypeMapper.toDto(borrowedDebtType);
    }

    /**
     * Update a borrowedDebtType.
     *
     * @param borrowedDebtTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public BorrowedDebtTypeDTO update(BorrowedDebtTypeDTO borrowedDebtTypeDTO) {
        log.debug("Request to save BorrowedDebtType : {}", borrowedDebtTypeDTO);
        BorrowedDebtType borrowedDebtType = borrowedDebtTypeMapper.toEntity(borrowedDebtTypeDTO);
        borrowedDebtType = borrowedDebtTypeRepository.save(borrowedDebtType);
        return borrowedDebtTypeMapper.toDto(borrowedDebtType);
    }

    /**
     * Partially update a borrowedDebtType.
     *
     * @param borrowedDebtTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BorrowedDebtTypeDTO> partialUpdate(BorrowedDebtTypeDTO borrowedDebtTypeDTO) {
        log.debug("Request to partially update BorrowedDebtType : {}", borrowedDebtTypeDTO);

        return borrowedDebtTypeRepository
            .findById(borrowedDebtTypeDTO.getId())
            .map(existingBorrowedDebtType -> {
                borrowedDebtTypeMapper.partialUpdate(existingBorrowedDebtType, borrowedDebtTypeDTO);

                return existingBorrowedDebtType;
            })
            .map(borrowedDebtTypeRepository::save)
            .map(borrowedDebtTypeMapper::toDto);
    }

    /**
     * Get all the borrowedDebtTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BorrowedDebtTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BorrowedDebtTypes");
        return borrowedDebtTypeRepository.findAll(pageable).map(borrowedDebtTypeMapper::toDto);
    }

    /**
     * Get one borrowedDebtType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BorrowedDebtTypeDTO> findOne(Long id) {
        log.debug("Request to get BorrowedDebtType : {}", id);
        return borrowedDebtTypeRepository.findById(id).map(borrowedDebtTypeMapper::toDto);
    }

    /**
     * Delete the borrowedDebtType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BorrowedDebtType : {}", id);
        borrowedDebtTypeRepository.deleteById(id);
    }
}
