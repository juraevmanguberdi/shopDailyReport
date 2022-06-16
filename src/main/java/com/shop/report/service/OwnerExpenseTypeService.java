package com.shop.report.service;

import com.shop.report.domain.OwnerExpenseType;
import com.shop.report.repository.OwnerExpenseTypeRepository;
import com.shop.report.service.dto.OwnerExpenseTypeDTO;
import com.shop.report.service.mapper.OwnerExpenseTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OwnerExpenseType}.
 */
@Service
@Transactional
public class OwnerExpenseTypeService {

    private final Logger log = LoggerFactory.getLogger(OwnerExpenseTypeService.class);

    private final OwnerExpenseTypeRepository ownerExpenseTypeRepository;

    private final OwnerExpenseTypeMapper ownerExpenseTypeMapper;

    public OwnerExpenseTypeService(OwnerExpenseTypeRepository ownerExpenseTypeRepository, OwnerExpenseTypeMapper ownerExpenseTypeMapper) {
        this.ownerExpenseTypeRepository = ownerExpenseTypeRepository;
        this.ownerExpenseTypeMapper = ownerExpenseTypeMapper;
    }

    /**
     * Save a ownerExpenseType.
     *
     * @param ownerExpenseTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public OwnerExpenseTypeDTO save(OwnerExpenseTypeDTO ownerExpenseTypeDTO) {
        log.debug("Request to save OwnerExpenseType : {}", ownerExpenseTypeDTO);
        OwnerExpenseType ownerExpenseType = ownerExpenseTypeMapper.toEntity(ownerExpenseTypeDTO);
        ownerExpenseType = ownerExpenseTypeRepository.save(ownerExpenseType);
        return ownerExpenseTypeMapper.toDto(ownerExpenseType);
    }

    /**
     * Update a ownerExpenseType.
     *
     * @param ownerExpenseTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public OwnerExpenseTypeDTO update(OwnerExpenseTypeDTO ownerExpenseTypeDTO) {
        log.debug("Request to save OwnerExpenseType : {}", ownerExpenseTypeDTO);
        OwnerExpenseType ownerExpenseType = ownerExpenseTypeMapper.toEntity(ownerExpenseTypeDTO);
        ownerExpenseType = ownerExpenseTypeRepository.save(ownerExpenseType);
        return ownerExpenseTypeMapper.toDto(ownerExpenseType);
    }

    /**
     * Partially update a ownerExpenseType.
     *
     * @param ownerExpenseTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OwnerExpenseTypeDTO> partialUpdate(OwnerExpenseTypeDTO ownerExpenseTypeDTO) {
        log.debug("Request to partially update OwnerExpenseType : {}", ownerExpenseTypeDTO);

        return ownerExpenseTypeRepository
            .findById(ownerExpenseTypeDTO.getId())
            .map(existingOwnerExpenseType -> {
                ownerExpenseTypeMapper.partialUpdate(existingOwnerExpenseType, ownerExpenseTypeDTO);

                return existingOwnerExpenseType;
            })
            .map(ownerExpenseTypeRepository::save)
            .map(ownerExpenseTypeMapper::toDto);
    }

    /**
     * Get all the ownerExpenseTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OwnerExpenseTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OwnerExpenseTypes");
        return ownerExpenseTypeRepository.findAll(pageable).map(ownerExpenseTypeMapper::toDto);
    }

    /**
     * Get one ownerExpenseType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OwnerExpenseTypeDTO> findOne(Long id) {
        log.debug("Request to get OwnerExpenseType : {}", id);
        return ownerExpenseTypeRepository.findById(id).map(ownerExpenseTypeMapper::toDto);
    }

    /**
     * Delete the ownerExpenseType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OwnerExpenseType : {}", id);
        ownerExpenseTypeRepository.deleteById(id);
    }
}
