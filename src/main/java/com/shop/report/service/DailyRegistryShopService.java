package com.shop.report.service;

import com.shop.report.domain.DailyRegistryShop;
import com.shop.report.repository.DailyRegistryShopRepository;
import com.shop.report.service.dto.DailyRegistryShopDTO;
import com.shop.report.service.mapper.DailyRegistryShopMapper;

import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DailyRegistryShop}.
 */
@Service
@Transactional
public class DailyRegistryShopService {

    private final Logger log = LoggerFactory.getLogger(DailyRegistryShopService.class);

    private final DailyRegistryShopRepository dailyRegistryShopRepository;

    private final DailyRegistryShopMapper dailyRegistryShopMapper;

    public DailyRegistryShopService(
        DailyRegistryShopRepository dailyRegistryShopRepository,
        DailyRegistryShopMapper dailyRegistryShopMapper
    ) {
        this.dailyRegistryShopRepository = dailyRegistryShopRepository;
        this.dailyRegistryShopMapper = dailyRegistryShopMapper;
    }

    /**
     * Save a dailyRegistryShop.
     *
     * @param dailyRegistryShopDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyRegistryShopDTO save(DailyRegistryShopDTO dailyRegistryShopDTO) {
        log.debug("Request to save DailyRegistryShop : {}", dailyRegistryShopDTO);
        DailyRegistryShop dailyRegistryShop = dailyRegistryShopMapper.toEntity(dailyRegistryShopDTO);
        dailyRegistryShop = dailyRegistryShopRepository.save(dailyRegistryShop);
        return dailyRegistryShopMapper.toDto(dailyRegistryShop);
    }

    /**
     * Update a dailyRegistryShop.
     *
     * @param dailyRegistryShopDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyRegistryShopDTO update(DailyRegistryShopDTO dailyRegistryShopDTO) {
        log.debug("Request to save DailyRegistryShop : {}", dailyRegistryShopDTO);
        DailyRegistryShop dailyRegistryShop = dailyRegistryShopMapper.toEntity(dailyRegistryShopDTO);
        dailyRegistryShop = dailyRegistryShopRepository.save(dailyRegistryShop);
        return dailyRegistryShopMapper.toDto(dailyRegistryShop);
    }

    /**
     * Partially update a dailyRegistryShop.
     *
     * @param dailyRegistryShopDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DailyRegistryShopDTO> partialUpdate(DailyRegistryShopDTO dailyRegistryShopDTO) {
        log.debug("Request to partially update DailyRegistryShop : {}", dailyRegistryShopDTO);

        return dailyRegistryShopRepository
            .findById(dailyRegistryShopDTO.getId())
            .map(existingDailyRegistryShop -> {
                dailyRegistryShopMapper.partialUpdate(existingDailyRegistryShop, dailyRegistryShopDTO);

                return existingDailyRegistryShop;
            })
            .map(dailyRegistryShopRepository::save)
            .map(dailyRegistryShopMapper::toDto);
    }

    /**
     * Get all the dailyRegistryShops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DailyRegistryShopDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DailyRegistryShops");
        return dailyRegistryShopRepository.findAll(pageable).map(dailyRegistryShopMapper::toDto);
    }

    /**
     * Get one dailyRegistryShop by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DailyRegistryShopDTO> findOne(Long id) {
        log.debug("Request to get DailyRegistryShop : {}", id);
        return dailyRegistryShopRepository.findById(id).map(dailyRegistryShopMapper::toDto);
    }
    @Transactional(readOnly = true)
    public Optional<DailyRegistryShopDTO> findOneByToday(LocalDate date) {
        log.debug("Request to get DailyRegistryShop : {}", date);
        return dailyRegistryShopRepository.findByToday(date).map(dailyRegistryShopMapper::toDto);
    }


    /**
     * Delete the dailyRegistryShop by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DailyRegistryShop : {}", id);
        dailyRegistryShopRepository.deleteById(id);
    }

    public void updateByToday( Long debtGiven, LocalDate today) {
        dailyRegistryShopRepository.updateByToday(debtGiven, today);
    }
}
