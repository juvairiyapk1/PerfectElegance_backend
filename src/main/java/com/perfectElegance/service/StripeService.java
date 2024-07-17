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
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


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



    public void saveSubscriptionDetails(Session session,User user) {
        Subscription subscription = new Subscription();
        subscription.setStripeSubscriptionId(session.getId());
        subscription.setUser(user);
        subscription.setStatus("PENDING");
        subscription.setAmount(session.getAmountTotal().doubleValue()/100);
        subscriptionRepository.save(subscription);
        System.out.println("Created subscription with user details successfully");
    }

    public void handlePaymentSuccess(String email,String stripeId) {

        try{

            Subscription subscription= subscriptionRepository.findByStripeSubscriptionId(stripeId);
            if(subscription == null && subscription.getUser() == null){
                throw new UsernameNotFoundException("user not found");
            }

            subscription.setSubscriptionStartDate(LocalDateTime.now());
            subscription.setSubscriptionEndDate(LocalDateTime.now().plusMonths(1).truncatedTo(ChronoUnit.SECONDS));
            subscription.setStatus("ACTIVE");
            subscriptionRepository.save(subscription);

            User user = subscription.getUser();
            System.out.println(user+"inside subscription");
            user.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
            user.setSubscriptionId(subscription.getId());
            user.setSubscribed(true);
            User savedUser = userRepository.save(user);
            System.out.println("User after save: " + savedUser);

            User fetchedUser = userRepository.findById(savedUser.getId()).orElse(null);
            System.out.println("User fetched from database: " + fetchedUser);

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






