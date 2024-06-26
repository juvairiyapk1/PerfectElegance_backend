package com.perfectElegance.repository;

import com.perfectElegance.modal.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

  Optional<User>findByEmail(String email);

    void deleteByEmail(String email);
    @Transactional
    @Modifying
    @Query("Update User u set u.password = ?2 where u.email = ?1")
    void updatePassword(String email,String password);

  @Query("SELECT u FROM User u WHERE u.id <> :loggedInUserId AND u.role <> 'ADMIN' AND u.blocked = false AND u.gender <> :gender")
  List<User> findAllExceptAdminAndLoggedInUserAndBlocked(@Param("loggedInUserId") Integer loggedInUserId, @Param("gender") String gender);


}
