package com.perfectElegance.repository;

import com.perfectElegance.modal.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

  Optional<User> findByEmail(String email);


  @Transactional
  @Modifying
  @Query("Update User u set u.password = ?2 where u.email = ?1")
  void updatePassword(String email, String password);

  @Query("SELECT u FROM User u WHERE u.id <> :loggedInUserId AND u.role <> 'ADMIN' AND u.blocked = false AND u.gender <> :gender")
  List<User> findAllExceptAdminAndLoggedInUserAndBlocked(@Param("loggedInUserId") Integer loggedInUserId, @Param("gender") String gender, Pageable pageable);


  long countByBlocked(boolean blocked);


  Long countByIsSubscribed(boolean isSubscribed);

  @Query("SELECT COUNT(u) FROM User u WHERE u.subscriptionEndDate IS NOT NULL AND u.subscriptionEndDate > :start AND u.subscriptionEndDate <= :end")
  long countActiveSubscriptionsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


  long countByCreatedAtBetween(LocalDateTime startOfMonth, LocalDateTime endOfMonth);

}
