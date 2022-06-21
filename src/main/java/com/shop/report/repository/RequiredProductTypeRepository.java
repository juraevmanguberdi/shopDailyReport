package com.shop.report.repository;

import com.shop.report.domain.RequiredProductType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RequiredProductType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequiredProductTypeRepository extends JpaRepository<RequiredProductType, Long> {}
