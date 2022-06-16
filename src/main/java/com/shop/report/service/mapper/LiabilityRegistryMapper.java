package com.shop.report.service.mapper;

import com.shop.report.domain.LiabilityRegistry;
import com.shop.report.service.dto.LiabilityRegistryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LiabilityRegistry} and its DTO {@link LiabilityRegistryDTO}.
 */
@Mapper(componentModel = "spring")
public interface LiabilityRegistryMapper extends EntityMapper<LiabilityRegistryDTO, LiabilityRegistry> {}
