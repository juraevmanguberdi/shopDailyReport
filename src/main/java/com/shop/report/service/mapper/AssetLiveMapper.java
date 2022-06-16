package com.shop.report.service.mapper;

import com.shop.report.domain.AssetLive;
import com.shop.report.service.dto.AssetLiveDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetLive} and its DTO {@link AssetLiveDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssetLiveMapper extends EntityMapper<AssetLiveDTO, AssetLive> {}
