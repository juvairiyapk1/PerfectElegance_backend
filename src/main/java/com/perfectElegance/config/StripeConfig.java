//package com.perfectElegance.config;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import com.stripe.Stripe;
//
//@Configuration
//public class StripeConfig {
//
//    @Value("${stripe.secretKey}")
//    private String secretKey;
//
//
//    @PostConstruct
//    public void  initSecretKey(){
//        Stripe.apiKey = secretKey;
//    }
//
//}
