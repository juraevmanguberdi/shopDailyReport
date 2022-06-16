package com.shop.report.repository;

import com.shop.report.domain.DebtReturn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DebtReturn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DebtReturnRepository extends JpaRepository<DebtReturn, Long> {}
