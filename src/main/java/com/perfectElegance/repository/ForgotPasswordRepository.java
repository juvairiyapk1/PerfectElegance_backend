package com.perfectElegance.repository;

import com.perfectElegance.modal.ForgotPassword;
import com.perfectElegance.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Integer> {

    @Query("select fb from ForgotPassword fb where fb.otp=?1 and fb.user=?2 ")
    Optional<ForgotPassword>findByOtpAndUser(Integer otp, User user);
}
