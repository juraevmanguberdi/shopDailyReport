package com.shop.report.service.mapper;

import com.shop.report.domain.RequiredProductType;
import com.shop.report.service.dto.RequiredProductTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequiredProductType} and its DTO {@link RequiredProductTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequiredProductTypeMapper extends EntityMapper<RequiredProductTypeDTO, RequiredProductType> {}
