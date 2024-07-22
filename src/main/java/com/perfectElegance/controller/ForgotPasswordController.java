package com.perfectElegance.controller;


import com.perfectElegance.Dto.MailBody;
import com.perfectElegance.modal.ForgotPassword;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.ForgotPasswordRepository;
import com.perfectElegance.repository.UserRepository;
import com.perfectElegance.service.EmailService;
import com.perfectElegance.service.OTPService;
import com.perfectElegance.utils.ChangePassword;
import com.perfectElegance.utils.EditPassword;
import com.perfectElegance.utils.ResendOtpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/otp")
public class ForgotPasswordController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    send mail for email verification
    @PostMapping("/verifyEmail")
    public ResponseEntity<Map<String, String>>verifyEmail(@RequestBody Map<String, String> request){

        System.out.println("I am inside verify email");
        String email = request.get("email");

        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Please enter valid email"));

        int otp=otpService.generateOTP();
        MailBody mailBody=MailBody.builder()
                .to(email)
                .text("This is OTP for your forgot password request : "+ otp)
                .subject("OTP for Forgot Password Request")
                .build();

        ForgotPassword fb=ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis()+300000))
                .user(user)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fb);
        System.out.println("otp is sented");
        Map<String, String> response = new HashMap<>();
        response.put("message", "Email sent for verification");

        return ResponseEntity.ok(response);
    }



    @PostMapping("/changePassword")
    public ResponseEntity<Map<String, String>> changePasswordHandler(@RequestBody ChangePassword changePassword) {
        String email = changePassword.email();
        String password = changePassword.password();
        String repeatPassword = changePassword.repeatPassword();

        Map<String, String> response = new HashMap<>();

        if (!Objects.equals(password, repeatPassword)) {
            response.put("message", "Passwords do not match. Please enter the password again!");
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(password);
        userRepository.updatePassword(email, encodedPassword);

        response.put("message", "Password has been changed!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/editPassword")
    public ResponseEntity<Map<String, String>> editPasswordHandle(@RequestBody EditPassword editPassword) {

        System.out.println("inside edit password");
        String email = editPassword.email();
        String currentPassword = editPassword.currentPassword();
        String newPassword = editPassword.newPassword();
        String repeatPassword = editPassword.repeatPassword();

        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if current password is correct
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            response.put("message", "Current password is incorrect");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // Check if new password and repeat password match
        if (!Objects.equals(newPassword, repeatPassword)) {
            response.put("message", "New password and repeat password do not match");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Check if new password is different from current password
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            response.put("message", "New password must be different from current password");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Encode and update the new password
        String encodedPassword = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(email, encodedPassword);

        response.put("message", "Password has been changed successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify_otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Integer otp = Integer.valueOf(request.get("otp"));

        System.out.println("Starting OTP verification for email: " + email + " with OTP: " + otp);

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Please enter valid email"));

            ForgotPassword fb = forgotPasswordRepository.findByOtpAndUser(otp, user)
                    .orElseThrow(() -> new RuntimeException("Invalid OTP for email " + email));

            if (fb.getExpirationTime().before(Date.from(Instant.now()))) {
                forgotPasswordRepository.deleteById(fb.getFpId());
                Map<String, String> response = new HashMap<>();
                response.put("message", "OTP has expired!");
                return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
            }

            user.setVerified(true);
            System.out.println("User verified successfully");
            forgotPasswordRepository.deleteById(fb.getFpId());

            Map<String, String> response = new HashMap<>();
            response.put("message", "OTP is verified!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "OTP verification failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ResendOtpRequest request) {
        try {
            System.out.println("inside resent otp");
            otpService.resendOtp(request.getEmail());
            return new ResponseEntity<>("OTP has been resent", HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
