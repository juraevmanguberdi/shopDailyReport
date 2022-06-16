package com.shop.report.repository;

import com.shop.report.domain.LiabilityRegistry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LiabilityRegistry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LiabilityRegistryRepository extends JpaRepository<LiabilityRegistry, Long> {}
