package com.shop.report.repository;

import com.shop.report.domain.RequiredProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RequiredProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequiredProductRepository extends JpaRepository<RequiredProduct, Long> {}
