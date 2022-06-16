package com.shop.report.repository;

import com.shop.report.domain.LiabilityLive;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LiabilityLive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LiabilityLiveRepository extends JpaRepository<LiabilityLive, Long> {}
