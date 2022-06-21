package com.shop.report.service.mapper;

import com.shop.report.domain.OwnerExpense;
import com.shop.report.domain.OwnerExpenseType;
import com.shop.report.service.dto.OwnerExpenseDTO;
import com.shop.report.service.dto.OwnerExpenseTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OwnerExpense} and its DTO {@link OwnerExpenseDTO}.
 */
@Mapper(componentModel = "spring")
public interface OwnerExpenseMapper extends EntityMapper<OwnerExpenseDTO, OwnerExpense> {
    OwnerExpenseDTO toDto(OwnerExpense s);

    @Named("ownerExpenseTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OwnerExpenseTypeDTO toDtoOwnerExpenseTypeId(OwnerExpenseType ownerExpenseType);
}
