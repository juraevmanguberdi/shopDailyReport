package com.shop.report.repository;

import com.shop.report.domain.DebtGiven;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DebtGiven entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DebtGivenRepository extends JpaRepository<DebtGiven, Long> {}
