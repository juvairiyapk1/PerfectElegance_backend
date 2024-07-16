package com.perfectElegance.repository;

import com.perfectElegance.modal.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {



    Subscription findByStripeSubscriptionId(String stripeId);

    long countBySubscriptionStartDateBetween(LocalDateTime startOfMonth, LocalDateTime endOfMonth);


    @Query("SELECT SUM(s.amount) FROM Subscription s WHERE s.subscriptionStartDate BETWEEN :startDate AND :endDate")
    Double calculateRevenueForPeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
