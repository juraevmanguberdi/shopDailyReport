package com.shop.report.service.mapper;

import com.shop.report.domain.BorrowedDebt;
import com.shop.report.domain.BorrowedDebtType;
import com.shop.report.service.dto.BorrowedDebtDTO;
import com.shop.report.service.dto.BorrowedDebtTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BorrowedDebt} and its DTO {@link BorrowedDebtDTO}.
 */
@Mapper(componentModel = "spring")
public interface BorrowedDebtMapper extends EntityMapper<BorrowedDebtDTO, BorrowedDebt> {
    @Mapping(target = "borrowedDebtType", source = "borrowedDebtType", qualifiedByName = "borrowedDebtTypeId")
    BorrowedDebtDTO toDto(BorrowedDebt s);

    @Named("borrowedDebtTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BorrowedDebtTypeDTO toDtoBorrowedDebtTypeId(BorrowedDebtType borrowedDebtType);
}
