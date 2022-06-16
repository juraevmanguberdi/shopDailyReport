package com.shop.report.repository;

import com.shop.report.domain.ExpenseType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExpenseType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {}
