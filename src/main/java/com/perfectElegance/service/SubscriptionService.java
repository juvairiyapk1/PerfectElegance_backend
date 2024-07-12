package com.perfectElegance.service;

import com.perfectElegance.modal.Subscription;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.SubscriptionRepository;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;



//    public void deleteSubscriptionBySubscriptionId(String subscriptionId) {
//        subscriptionRepository.deleteBySubscriptionId(subscriptionId); // Assuming you have this method
//    }


}
