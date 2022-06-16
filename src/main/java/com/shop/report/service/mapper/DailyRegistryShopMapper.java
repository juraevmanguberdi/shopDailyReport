package com.shop.report.service.mapper;

import com.shop.report.domain.DailyRegistryShop;
import com.shop.report.service.dto.DailyRegistryShopDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DailyRegistryShop} and its DTO {@link DailyRegistryShopDTO}.
 */
@Mapper(componentModel = "spring")
public interface DailyRegistryShopMapper extends EntityMapper<DailyRegistryShopDTO, DailyRegistryShop> {}
