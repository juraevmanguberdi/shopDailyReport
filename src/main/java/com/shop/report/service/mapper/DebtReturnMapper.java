package com.shop.report.service.mapper;

import com.shop.report.domain.Client;
import com.shop.report.domain.DebtReturn;
import com.shop.report.domain.PaymentMethod;
import com.shop.report.service.dto.ClientDTO;
import com.shop.report.service.dto.DebtReturnDTO;
import com.shop.report.service.dto.PaymentMethodDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DebtReturn} and its DTO {@link DebtReturnDTO}.
 */
@Mapper(componentModel = "spring")
public interface DebtReturnMapper extends EntityMapper<DebtReturnDTO, DebtReturn> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    @Mapping(target = "paymentMethod", source = "paymentMethod", qualifiedByName = "paymentMethodId")
    DebtReturnDTO toDto(DebtReturn s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);

    @Named("paymentMethodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentMethodDTO toDtoPaymentMethodId(PaymentMethod paymentMethod);
}
