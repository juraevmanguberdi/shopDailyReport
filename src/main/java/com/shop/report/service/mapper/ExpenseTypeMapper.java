package com.shop.report.service.mapper;

import com.shop.report.domain.ExpenseType;
import com.shop.report.service.dto.ExpenseTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExpenseType} and its DTO {@link ExpenseTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExpenseTypeMapper extends EntityMapper<ExpenseTypeDTO, ExpenseType> {}
