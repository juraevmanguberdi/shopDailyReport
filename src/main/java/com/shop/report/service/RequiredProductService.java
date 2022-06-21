package com.shop.report.service;

import com.shop.report.domain.RequiredProduct;
import com.shop.report.repository.RequiredProductRepository;
import com.shop.report.service.dto.RequiredProductDTO;
import com.shop.report.service.mapper.RequiredProductMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequiredProduct}.
 */
@Service
@Transactional
public class RequiredProductService {

    private final Logger log = LoggerFactory.getLogger(RequiredProductService.class);

    private final RequiredProductRepository requiredProductRepository;

    private final RequiredProductMapper requiredProductMapper;

    public RequiredProductService(RequiredProductRepository requiredProductRepository, RequiredProductMapper requiredProductMapper) {
        this.requiredProductRepository = requiredProductRepository;
        this.requiredProductMapper = requiredProductMapper;
    }

    /**
     * Save a requiredProduct.
     *
     * @param requiredProductDTO the entity to save.
     * @return the persisted entity.
     */
    public RequiredProductDTO save(RequiredProductDTO requiredProductDTO) {
        log.debug("Request to save RequiredProduct : {}", requiredProductDTO);
        RequiredProduct requiredProduct = requiredProductMapper.toEntity(requiredProductDTO);
        requiredProduct = requiredProductRepository.save(requiredProduct);
        return requiredProductMapper.toDto(requiredProduct);
    }

    /**
     * Update a requiredProduct.
     *
     * @param requiredProductDTO the entity to save.
     * @return the persisted entity.
     */
    public RequiredProductDTO update(RequiredProductDTO requiredProductDTO) {
        log.debug("Request to save RequiredProduct : {}", requiredProductDTO);
        RequiredProduct requiredProduct = requiredProductMapper.toEntity(requiredProductDTO);
        requiredProduct = requiredProductRepository.save(requiredProduct);
        return requiredProductMapper.toDto(requiredProduct);
    }

    /**
     * Partially update a requiredProduct.
     *
     * @param requiredProductDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequiredProductDTO> partialUpdate(RequiredProductDTO requiredProductDTO) {
        log.debug("Request to partially update RequiredProduct : {}", requiredProductDTO);

        return requiredProductRepository
            .findById(requiredProductDTO.getId())
            .map(existingRequiredProduct -> {
                requiredProductMapper.partialUpdate(existingRequiredProduct, requiredProductDTO);

                return existingRequiredProduct;
            })
            .map(requiredProductRepository::save)
            .map(requiredProductMapper::toDto);
    }

    /**
     * Get all the requiredProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequiredProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequiredProducts");
        return requiredProductRepository.findAll(pageable).map(requiredProductMapper::toDto);
    }

    /**
     * Get one requiredProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequiredProductDTO> findOne(Long id) {
        log.debug("Request to get RequiredProduct : {}", id);
        return requiredProductRepository.findById(id).map(requiredProductMapper::toDto);
    }

    /**
     * Delete the requiredProduct by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequiredProduct : {}", id);
        requiredProductRepository.deleteById(id);
    }
}
