package com.perfectElegance.controller;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfectElegance.modal.Subscription;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.SubscriptionRepository;
import com.perfectElegance.repository.UserRepository;
import com.perfectElegance.service.StripeService;
import com.perfectElegance.service.SubscriptionService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class WebhookController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserDetailsService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private StripeService stripeService;




    private void handleSubscriptionCreated(Map<String, Object> sessionData) {
        String subscriptionId = (String) sessionData.get("subscription");
        String customerId = (String) sessionData.get("customer");
        System.out.println(customerId+"customerID");

        Map<String, String> metadata = (Map<String, String>) sessionData.get("metadata");
        String userId = metadata.get("userId");

        if (userId == null) {
            throw new RuntimeException("User ID not found in session metadata");
        }

        // Find the user by ID
        User user = userRepository.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId));


        Subscription subscription = new Subscription();
        subscription.setStripeSubscriptionId(subscriptionId);
        subscription.setUser(user);
        subscription.setStatus("active");

        subscriptionRepository.save(subscription);

    }



    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, HttpServletRequest request) throws JsonProcessingException {
        System.out.println("Webhook endpoint hit");

        // Log the Stripe signature header
        String sigHeader = request.getHeader("Stripe-Signature");
        System.out.println("Stripe-Signature id: " + request.getRequestId());
        System.out.println("Stripe-Signature if: " + payload.contains("id"));

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payload);
            String email = jsonNode.path("data").path("object").path("customer_details").path("email").asText();
            String stripeId = jsonNode.path("data").path("object").path("id").asText();
            System.out.println("Stripe-Signature id: " + stripeId);

            // Verify the signature
            Event event;

            event = Webhook.constructEvent(payload, sigHeader, "whsec_d672024609e675ea591446403df3ff62b0a411ea9125144978cc4f70569d03af");
            System.out.println("Event verified successfully");


            // Handle different event types
            switch (event.getType()) {
                case "checkout.session.completed":
                    System.out.println("Handling checkout.session.completed");
                    stripeService.handlePaymentSuccess(email,stripeId);
                    break;

                case "payment_intent.payment_failed":
                    System.out.println("Handling payment_intent.payment_failed");

//                  paymentService.handlePaymentFailure(email);
                    break;

                default:
                    System.out.println("called the default :" + event.getType());
                    break;
            }

        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("Signature verification failed: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok("Webhook handled");
    }



}
