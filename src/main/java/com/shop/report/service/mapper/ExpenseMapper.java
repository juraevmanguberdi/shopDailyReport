package com.shop.report.service.mapper;

import com.shop.report.domain.Expense;
import com.shop.report.domain.ExpenseType;
import com.shop.report.service.dto.ExpenseDTO;
import com.shop.report.service.dto.ExpenseTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Expense} and its DTO {@link ExpenseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExpenseMapper extends EntityMapper<ExpenseDTO, Expense> {
    ExpenseDTO toDto(Expense s);

    @Named("expenseTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExpenseTypeDTO toDtoExpenseTypeId(ExpenseType expenseType);
}
