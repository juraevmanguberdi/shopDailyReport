package com.shop.report.service.mapper;

import com.shop.report.domain.OwnerExpenseType;
import com.shop.report.service.dto.OwnerExpenseTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OwnerExpenseType} and its DTO {@link OwnerExpenseTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface OwnerExpenseTypeMapper extends EntityMapper<OwnerExpenseTypeDTO, OwnerExpenseType> {}
