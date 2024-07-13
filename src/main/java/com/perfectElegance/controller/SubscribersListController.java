package com.perfectElegance.controller;

import com.perfectElegance.modal.Subscription;
import com.perfectElegance.service.SubscriptionService;
import com.perfectElegance.Dto.SubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class SubscribersListController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/subscribers")
    public ResponseEntity<List<SubscriptionDto>> getAllSubscribers(){
        System.out.println("hi  subscribers");
        List<SubscriptionDto>subscriptions= subscriptionService.getSubscribers();
        System.out.println(subscriptions+"inside controller");
        return ResponseEntity.ok(subscriptions);
    }


}
