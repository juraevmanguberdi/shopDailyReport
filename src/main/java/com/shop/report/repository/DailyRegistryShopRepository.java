package com.shop.report.repository;

import com.shop.report.domain.DailyRegistryShop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DailyRegistryShop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyRegistryShopRepository extends JpaRepository<DailyRegistryShop, Long> {}
