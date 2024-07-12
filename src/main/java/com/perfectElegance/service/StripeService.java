package com.perfectElegance.service;

import com.perfectElegance.modal.User;
import com.perfectElegance.modal.Subscription;
import com.perfectElegance.repository.SubscriptionRepository;
import com.perfectElegance.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


@Service
public class StripeService {

    @Autowired
    private UserDetailsServiceIMPL userService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${stripe.secretKey}")
    private String secretKey;

    @PostConstruct
    public void  init(){
        Stripe.apiKey = secretKey;
    }


    private PaymentMethod attachCustomerToPaymentMethod(Customer customer,PaymentMethod paymentMethod){
        try{
            PaymentMethod resource = PaymentMethod.retrieve(paymentMethod.getId());
            PaymentMethodAttachParams params =
                    PaymentMethodAttachParams.builder().setCustomer(customer.getId()).build();
            paymentMethod = resource.attach(params);
            return paymentMethod;

        }catch (StripeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

//    private Subscription createSubscription(StripeSubscriptionDto stripeSubscriptionDto,
//                                            PaymentMethod paymentMethod,Customer customer){
//        try {
//            SubscriptionCreateParams params =
//                    SubscriptionCreateParams.builder()
//                            .setCustomer(customer.getId())
//                            .setDefaultPaymentMethod(paymentMethod.getId())
//                            .addItem(
//                                    SubscriptionCreateParams.Item.builder()
//                                            .setPrice(stripeSubscriptionDto.getPriceId())
//                                            .build()
//                            )
//                            .build();
//            subscription = Subscription.create(params);
//            return subscription;
//        }catch (StripeException e){
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }
//    public Subscription cancelSubscription(String subscriptionId) throws StripeException {
//        Subscription resource = Subscription.retrieve(subscriptionId);
//        SubscriptionCancelParams params = SubscriptionCancelParams.builder().build();
//
//        Subscription subscription = resource.cancel(params);
//       return subscription;
//
//    }

//    public String checkout(String header, String priceId) {
//        Stripe.apiKey = secretKey;
//        try {
//            User user = userService.findUserByHeader(header);
//            String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//
//
//            // Retrieve the customer's subscriptions
//            Map<String, Object> params1 = new HashMap<>();
//            params1.put("email", customerEmail);
//
//            Customer customer =Customer.retrieve(String.valueOf(user.getSubscriptionId()));
//            Map<String, Object> subParams = new HashMap<>();
//            subParams.put("customer", customer.getId());
//            SubscriptionCollection subscriptions = com.stripe.model.Subscription.list(subParams);
//
//            for (com.stripe.model.Subscription subscription : subscriptions.getData()) {
//                if ("active".equals(subscription.getStatus())) {
//                    // Customer already has an active subscription
//                    return "You already have an active subscription.";
//                }
//            }
//
//
//            SessionCreateParams params = SessionCreateParams.builder()
//                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
//                    .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
//                    .setSuccessUrl("http://localhost:4200/user/success")
//                    .setCancelUrl("http://localhost:4200/user/cancel")
//                    .addLineItem(SessionCreateParams.LineItem.builder()
//                            .setQuantity(1L)
//                            .setPrice(priceId)
//                            .build())
////                    .setCustomerEmail(SecurityContextHolder.getContext().getAuthentication().getName())
//                    .build();
//
//            Session session = Session.create(params);
//            System.out.println("session: " + session.getId());
//            saveSubscriptionDetails(session, user);
//            return session.getId();
//
//        } catch (UsernameNotFoundException e) {
//            throw new UsernameNotFoundException(e.getMessage());
//        } catch (StripeException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }


    public void saveSubscriptionDetails(Session session,User user) {
        Subscription subscription = new Subscription();
        subscription.setStripeSubscriptionId(session.getId());
        subscription.setUser(user);
        subscription.setStatus("PENDING");
        subscriptionRepository.save(subscription);
        System.out.println("Created subscription with user details successfully");
    }

    public void handlePaymentSuccess(String email,String stripeId) {

        try{

            com.perfectElegance.modal.Subscription subscription = subscriptionRepository.findByStripeSubscriptionId(stripeId);
            if(subscription == null && subscription.getUser() == null){
                throw new UsernameNotFoundException("user not found");
            }

            subscription.setSubscriptionStartDate(LocalDateTime.now());
            subscription.setSubscriptionEndDate(LocalDateTime.now().plusMonths(1).truncatedTo(ChronoUnit.SECONDS));
            subscription.setStatus("ACTIVE");
            subscriptionRepository.save(subscription);

            User user = subscription.getUser();
            user.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
            user.setSubscriptionId(subscription.getId());
            user.setSubscribed(true);
            userRepository.save(user);

        }catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("user not found");
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        System.out.println("Subscription for blue tick successfully done , only for one month");
    }



    private int parseDurationToDays(String duration) {

        System.out.println("Parsing duration: " + duration);

        if (duration.contains("month")) {
            return Integer.parseInt(duration.split(" ")[0]) * 30;
        } else if (duration.contains("year")) {
            return Integer.parseInt(duration.split(" ")[0]) * 365;
        } else if (duration.contains("3 months")) {
            return 3 * 30;
        } else if (duration.contains("6 months")) {
            return 6 * 30;
        }
        return 0;
    }




}

//    public SessionDto createSessionPayment(SessionDto sessionDto){
//       try {
//
//           Customer customer = findOrCreateCustomer("test@gmail.com","testUser");
//           String clintUrl="http//localhost:4200";
//           SessionCreateParams.Builder sessionCreateParamsBuilder =
//                   SessionCreateParams.builder()
//                           .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
//                           .setCustomer(customer.getId())
//                           .setSuccessUrl(clintUrl+"/user/success?session_id={CHECKOUT_SESSION_ID}")
//                           .setCancelUrl(clintUrl+"/user/failure");
////           add item and amount
//
//           sessionCreateParamsBuilder.addLineItem(
//                   SessionCreateParams.LineItem.builder()
//                           .setQuantity(1L)
//                           .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
//                                   .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                           .putMetadata("app_id","123")
//                                           .putMetadata("user_id",sessionDto.getUserId())
//                                           .setName("perfectElegancePackage")
//                                           .build()
//                                   ).setCurrency("INR")
////                                   .setUnitAmountDecimal(BigDecimal.valueOf())
//                                   .build())
//                           .build())
//                   .build();
//
//           SessionCreateParams.PaymentIntentData paymentIntentData =
//                   SessionCreateParams.PaymentIntentData.builder()
//                           .putMetadata("app_id","123")
//                           .putMetadata("user_id",sessionDto.getUserId())
//                           .build();
//           sessionCreateParamsBuilder.setPaymentIntentData(paymentIntentData);
//
//           Session session = Session.create(sessionCreateParamsBuilder.build());
//           sessionDto.setSessionUrl(session.getUrl());
//           sessionDto.setSessionId(session.getId());
//       }catch (StripeException e){
//           sessionDto.setMessage(e.getMessage());
//       }
//
//
//        return sessionDto;
//    }
//
//    private Customer findOrCreateCustomer(String email, String userName) throws StripeException {
//        CustomerSearchParams params =
//                CustomerSearchParams.builder()
//                        .setQuery("email"+email+"'")
//                        .build();
//
//        CustomerSearchResult search = Customer.search(params);
//        Customer customer;
//        if (search.getData().isEmpty()){
//            CustomerCreateParams customerCreateParams=
//                    CustomerCreateParams.builder()
//                            .setName(userName)
//                            .setEmail(email)
//                            .build();
//            customer=Customer.create(customerCreateParams);
//        }else {
//            customer=search.getData().get(0);
//        }
//      return customer;
//    }
//





