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
                .setSuccessUrl("http://localhost:4200/user/success?token=" + token)
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


}
