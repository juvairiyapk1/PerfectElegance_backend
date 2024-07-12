//package com.perfectElegance.controller;
//
//import com.perfectElegance.modal.Subscription;
//import com.perfectElegance.service.SubscriptionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/user/subscriptions")
//public class SubscriptionsController {
//
//    @Autowired
//    private SubscriptionService service;
//
//    @GetMapping
//    public List<Subscription>getAllSubscriptions(){
//        return service.getAllSubscriptions();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Integer id) {
//        Subscription subscription = service.getSubscriptionById(id);
//        if (subscription != null) {
//            return ResponseEntity.ok(subscription);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    public Subscription createSubscription(@RequestBody Subscription subscription) {
//        return service.saveSubscription(subscription);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Subscription> updateSubscription(@PathVariable Integer id, @RequestBody Subscription subscriptionDetails) {
//        Subscription subscription = service.getSubscriptionById(id);
//        if (subscription != null) {
//            subscription.setSubscriptionType(subscriptionDetails.getSubscriptionType());
//            subscription.setStartDate(subscriptionDetails.getStartDate());
//            subscription.setEndDate(subscriptionDetails.getEndDate());
//            subscription.setUser(subscriptionDetails.getUser());
//            return ResponseEntity.ok(service.saveSubscription(subscription));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSubscription(@PathVariable Integer id) {
//        Subscription subscription = service.getSubscriptionById(id);
//        if (subscription != null) {
//            service.deleteSubscription(id);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//}
