//package com.perfectElegance.service;
//
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.Twilio;
//import com.twilio.type.PhoneNumber;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//
//
//@Service
//public class TwilioService {
//
//    @Value("${twilio.account.sid}")
//    private String accountSid;
//
//    @Value("${twilio.auth.token}")
//    private String authToken;
//
//    @Value("${twilio.phone.number}")
//    private String fromPhoneNumber;
//    @PostConstruct
//    public void init() {
//        Twilio.init(accountSid,authToken);
//    }
//
//    public void sendSMS(String toPhoneNumber,String message){
//
//        Message.creator(
//                new PhoneNumber(toPhoneNumber),
//                new PhoneNumber(fromPhoneNumber),
//                message)
//                .create();
//
//    }
//}
