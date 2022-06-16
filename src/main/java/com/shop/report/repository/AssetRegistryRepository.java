package com.shop.report.repository;

import com.shop.report.domain.AssetRegistry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AssetRegistry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetRegistryRepository extends JpaRepository<AssetRegistry, Long> {}
