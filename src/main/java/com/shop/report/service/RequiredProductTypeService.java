package com.shop.report.service;

import com.shop.report.domain.RequiredProductType;
import com.shop.report.repository.RequiredProductTypeRepository;
import com.shop.report.service.dto.RequiredProductTypeDTO;
import com.shop.report.service.mapper.RequiredProductTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequiredProductType}.
 */
@Service
@Transactional
public class RequiredProductTypeService {

    private final Logger log = LoggerFactory.getLogger(RequiredProductTypeService.class);

    private final RequiredProductTypeRepository requiredProductTypeRepository;

    private final RequiredProductTypeMapper requiredProductTypeMapper;

    public RequiredProductTypeService(
        RequiredProductTypeRepository requiredProductTypeRepository,
        RequiredProductTypeMapper requiredProductTypeMapper
    ) {
        this.requiredProductTypeRepository = requiredProductTypeRepository;
        this.requiredProductTypeMapper = requiredProductTypeMapper;
    }

    /**
     * Save a requiredProductType.
     *
     * @param requiredProductTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public RequiredProductTypeDTO save(RequiredProductTypeDTO requiredProductTypeDTO) {
        log.debug("Request to save RequiredProductType : {}", requiredProductTypeDTO);
        RequiredProductType requiredProductType = requiredProductTypeMapper.toEntity(requiredProductTypeDTO);
        requiredProductType = requiredProductTypeRepository.save(requiredProductType);
        return requiredProductTypeMapper.toDto(requiredProductType);
    }

    /**
     * Update a requiredProductType.
     *
     * @param requiredProductTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public RequiredProductTypeDTO update(RequiredProductTypeDTO requiredProductTypeDTO) {
        log.debug("Request to save RequiredProductType : {}", requiredProductTypeDTO);
        RequiredProductType requiredProductType = requiredProductTypeMapper.toEntity(requiredProductTypeDTO);
        requiredProductType = requiredProductTypeRepository.save(requiredProductType);
        return requiredProductTypeMapper.toDto(requiredProductType);
    }

    /**
     * Partially update a requiredProductType.
     *
     * @param requiredProductTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequiredProductTypeDTO> partialUpdate(RequiredProductTypeDTO requiredProductTypeDTO) {
        log.debug("Request to partially update RequiredProductType : {}", requiredProductTypeDTO);

        return requiredProductTypeRepository
            .findById(requiredProductTypeDTO.getId())
            .map(existingRequiredProductType -> {
                requiredProductTypeMapper.partialUpdate(existingRequiredProductType, requiredProductTypeDTO);

                return existingRequiredProductType;
            })
            .map(requiredProductTypeRepository::save)
            .map(requiredProductTypeMapper::toDto);
    }

    /**
     * Get all the requiredProductTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequiredProductTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequiredProductTypes");
        return requiredProductTypeRepository.findAll(pageable).map(requiredProductTypeMapper::toDto);
    }

    /**
     * Get one requiredProductType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequiredProductTypeDTO> findOne(Long id) {
        log.debug("Request to get RequiredProductType : {}", id);
        return requiredProductTypeRepository.findById(id).map(requiredProductTypeMapper::toDto);
    }

    /**
     * Delete the requiredProductType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequiredProductType : {}", id);
        requiredProductTypeRepository.deleteById(id);
    }
}
