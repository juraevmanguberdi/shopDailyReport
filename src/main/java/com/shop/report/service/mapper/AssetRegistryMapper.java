package com.shop.report.service.mapper;

import com.shop.report.domain.AssetRegistry;
import com.shop.report.service.dto.AssetRegistryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetRegistry} and its DTO {@link AssetRegistryDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssetRegistryMapper extends EntityMapper<AssetRegistryDTO, AssetRegistry> {}
