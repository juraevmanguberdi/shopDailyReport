package com.shop.report.repository;

import com.shop.report.domain.OwnerExpense;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OwnerExpense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerExpenseRepository extends JpaRepository<OwnerExpense, Long> {}
