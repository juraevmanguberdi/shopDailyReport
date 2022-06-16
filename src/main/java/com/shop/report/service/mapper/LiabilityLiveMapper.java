package com.shop.report.service.mapper;

import com.shop.report.domain.LiabilityLive;
import com.shop.report.service.dto.LiabilityLiveDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LiabilityLive} and its DTO {@link LiabilityLiveDTO}.
 */
@Mapper(componentModel = "spring")
public interface LiabilityLiveMapper extends EntityMapper<LiabilityLiveDTO, LiabilityLive> {}
