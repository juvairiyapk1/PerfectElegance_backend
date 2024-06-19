package com.perfectElegance.repository;

import com.perfectElegance.modal.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {

}
