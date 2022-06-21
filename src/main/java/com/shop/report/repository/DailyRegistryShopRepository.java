package com.shop.report.repository;

import com.shop.report.domain.DailyRegistryShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Spring Data SQL repository for the DailyRegistryShop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyRegistryShopRepository extends JpaRepository<DailyRegistryShop, Long> {

    Optional<DailyRegistryShop> findByToday(LocalDate today);

    @Modifying
    @Query("update DailyRegistryShop d set d.debtGiven = :debtGiven where d.today = :today")
    void updateByToday(@Param("debtGiven") Long debtGiven, @Param("today") LocalDate today);
}
