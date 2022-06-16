package com.shop.report.repository;

import com.shop.report.domain.AssetLive;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AssetLive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetLiveRepository extends JpaRepository<AssetLive, Long> {}
