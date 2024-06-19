package com.perfectElegance.service;

import com.perfectElegance.Dto.MailBody;
import com.perfectElegance.modal.ForgotPassword;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.ForgotPasswordRepository;
import com.perfectElegance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class OTPService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private EmailService emailService;
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;


    public Integer generateOTP(){
        Random random = new Random();
         return random.nextInt(100_000,999_999);
    }

    public void resendOtp(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        forgotPasswordRepository.deleteAll();


        int otp=generateOTP();
        MailBody mailBody=MailBody.builder()
                .to(email)
                .text("This is OTP for your registration : "+ otp)
                .subject("OTP for For Registration")
                .build();

        ForgotPassword fb=ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis()+300000))
                .user(user)
                .build();
        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fb);

    }

//    public void sendOTPViaSMS(String phoneNumber,String otp){
//        String message = "Your OTP is :  " + otp;
//        twilioService.sendSMS(phoneNumber,message);
//    }


}
