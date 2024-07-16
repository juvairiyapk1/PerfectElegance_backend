package com.perfectElegance.controller;

import com.perfectElegance.Dto.*;
import com.perfectElegance.modal.User;
import com.perfectElegance.service.JwtService;
import com.perfectElegance.service.StripeService;
import com.perfectElegance.service.UserDetailsServiceIMPL;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PlanListParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/user")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @Autowired
    private JwtService jwtService;

    @Value("${stripe.secretKey}")
    private String secretKey;

    @Autowired
    private UserDetailsServiceIMPL userService;


//    @PostMapping("/createCustomer")
//    public CustomerData index(@RequestBody CustomerData data)throws StripeException{
//        Stripe.apiKey=secretKey;
//        CustomerCreateParams params =
//                CustomerCreateParams.builder()
//                        .setName(data.getName())
//                        .setEmail(data.getEmail())
//                        .build();
//        Customer customer = Customer.create(params);
//        data.setCustomerId(customer.getId());
//        return data;
//    }

//    @PostMapping("/customerSubscription")
//    @ResponseBody
//    public StripeSubscriptionResponse subscription(@RequestBody StripeSubscriptionDto stripeSubscriptionDto){
//        return stripeService.createSubscription(stripeSubscriptionDto);
//    }

//    @PostMapping("/session/payment")
//    @ResponseBody
//    public SessionDto sessionPayment(@RequestBody SessionDto sessionDto){
//        System.out.println("jiiiiii");
//        System.out.println(sessionDto+"sessionDto");
//        return stripeService.createSessionPayment(sessionDto);
//    }

//    @DeleteMapping("/cancelSubscription/{id}")
//    @ResponseBody
//    public SubscriptionCancelRecord cancelSubscription(@PathVariable String id) throws StripeException {
////        Subscription subscription = stripeService.cancelSubscription(id);
//        if(nonNull(subscription)){
//         return new SubscriptionCancelRecord(subscription.getStatus());
//        }
//        return null;
//    }


//    @PostMapping("/checkout-session")
//    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestHeader("") @RequestBody Map<String, String> payload) {
//        String priceId = payload.get("priceId");
//        String successUrl = payload.get("successUrl");
//        String cancelUrl = payload.get("cancelUrl");
//        String userId = payload.get("userId");
//
//        System.out.println("Price ID: " + priceId);
//        System.out.println("Success URL: " + successUrl);
//        System.out.println("Cancel URL: " + cancelUrl);
//        System.out.println("User ID: " + userId);
//
//        try {
//            SessionCreateParams params = SessionCreateParams.builder()
//                    .setSuccessUrl(successUrl)
//                    .setCancelUrl(cancelUrl)
//                    .addLineItem(
//                            SessionCreateParams.LineItem.builder()
//                                    .setPrice(priceId)
//                                    .setQuantity(1L)
//                                    .build()
//                    )
//                    .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
//                    .putMetadata("userId", userId)
//                    .build();
//
//            Session session = Session.create(params);
//
//            Map<String, String> responseData = new HashMap<>();
//            responseData.put("sessionId", session.getId());
//            responseData.put("userId", userId);
//
//            return ResponseEntity.ok(responseData);
//        } catch (StripeException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error creating checkout session"));
//        }
//    }


//    @PostMapping("/checkout-session")
//    public ResponseEntity<String> createSubscriptionIntent(@RequestHeader("Authorization") String header, @RequestBody Map<String, String> body) {
//        String priceId = body.get("priceId");
//        return ResponseEntity.ok(stripeService.checkout(header, priceId));
//    }

//    @PostMapping("/checkout-session")
//    public ResponseEntity<Map<String, String>> createSubscriptionIntent(@RequestHeader("Authorization") String header, @RequestBody Map<String, String> body) {
//        String priceId = body.get("priceId");
//        String sessionId;
//        try {
//            sessionId = stripeService.checkout(header, priceId);
//            Map<String, String> response = new HashMap<>();
//            response.put("sessionId", sessionId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
//        }
//    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> checkout(@RequestHeader("Authorization") String header,@RequestBody Map<String, String> requestBody) {
        Stripe.apiKey = secretKey;
        System.out.println(header);
        User user = userService.findUserByHeader(header);
        if(user.getSubscriptionId() != null){
            return ResponseEntity.badRequest().body(null);
        }

        String priceId = requestBody.get("priceId");
        if (priceId == null || priceId.isEmpty()) {
            return ResponseEntity.badRequest().body("Price ID is required");
        }
        String token = header.startsWith("Bearer ") ? header.substring(7) : header;

        String checkoutId = UUID.randomUUID().toString();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("http://localhost:4200/user/success?token=" + token )
                .setCancelUrl("http://localhost:4200/user/cancel?token=" + token )
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice(priceId)
                        .setQuantity(1L)
                        .build())
                .setClientReferenceId(user.getId().toString())
//                .putMetadata("checkoutId",checkoutId)
                .build();

        try {
            Session session = Session.create(params);
            System.out.println(session.getUrl());
            stripeService.saveSubscriptionDetails(session,user);
            Map<String, String> responseData = new HashMap<>();
            responseData.put("url", session.getUrl());
            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/stripe-success")
    public void stripeSuccess(@RequestParam("session_id") String sessionId, @RequestParam("user_id") Integer userId, HttpServletResponse response) throws IOException {
        // Verify the session with Stripe
        try {
            Session session = Session.retrieve(sessionId);
            if (session.getPaymentStatus().equals("paid")) {
                // Update user's subscription status
                User user = userService.findUserById(userId);
//                user.setSubscriptionId(session.getSubscription());
                userService.saveUser(user);

                // Generate a new JWT
                String jwt = jwtService.generateToken(user);

                // Redirect to the frontend success page with the new JWT
                response.sendRedirect("http://localhost:4200/user/success?token=" + jwt);
            } else {
                response.sendRedirect("http://localhost:4200/user/payment-failed");
            }
        } catch (StripeException e) {
            e.printStackTrace();
            response.sendRedirect("http://localhost:4200/user/payment-failed");
        }
    }


}
