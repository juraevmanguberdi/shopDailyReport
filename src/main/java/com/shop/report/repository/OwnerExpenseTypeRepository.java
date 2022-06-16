package com.shop.report.repository;

import com.shop.report.domain.OwnerExpenseType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OwnerExpenseType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerExpenseTypeRepository extends JpaRepository<OwnerExpenseType, Long> {}
