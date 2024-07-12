package com.perfectElegance.repository;

import com.perfectElegance.modal.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    Optional<Payment> findByUserId(Integer userId);

}
