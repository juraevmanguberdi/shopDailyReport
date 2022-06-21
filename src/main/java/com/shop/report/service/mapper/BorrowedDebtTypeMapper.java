package com.shop.report.service.mapper;

import com.shop.report.domain.BorrowedDebtType;
import com.shop.report.service.dto.BorrowedDebtTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BorrowedDebtType} and its DTO {@link BorrowedDebtTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BorrowedDebtTypeMapper extends EntityMapper<BorrowedDebtTypeDTO, BorrowedDebtType> {}
