package com.shop.report.repository;

import com.shop.report.domain.BorrowedDebt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BorrowedDebt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BorrowedDebtRepository extends JpaRepository<BorrowedDebt, Long> {}
