package com.shop.report.repository;

import com.shop.report.domain.BorrowedDebtType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BorrowedDebtType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BorrowedDebtTypeRepository extends JpaRepository<BorrowedDebtType, Long> {}
