package com.shop.report.service.mapper;

import com.shop.report.domain.Client;
import com.shop.report.domain.DebtGiven;
import com.shop.report.service.dto.ClientDTO;
import com.shop.report.service.dto.DebtGivenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DebtGiven} and its DTO {@link DebtGivenDTO}.
 */
@Mapper(componentModel = "spring")
public interface DebtGivenMapper extends EntityMapper<DebtGivenDTO, DebtGiven> {
    DebtGivenDTO toDto(DebtGiven s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
