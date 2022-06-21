package com.shop.report.service.mapper;

import com.shop.report.domain.RequiredProduct;
import com.shop.report.domain.RequiredProductType;
import com.shop.report.service.dto.RequiredProductDTO;
import com.shop.report.service.dto.RequiredProductTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequiredProduct} and its DTO {@link RequiredProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequiredProductMapper extends EntityMapper<RequiredProductDTO, RequiredProduct> {
    @Mapping(target = "requiredProductType", source = "requiredProductType", qualifiedByName = "requiredProductTypeId")
    RequiredProductDTO toDto(RequiredProduct s);

    @Named("requiredProductTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequiredProductTypeDTO toDtoRequiredProductTypeId(RequiredProductType requiredProductType);
}
