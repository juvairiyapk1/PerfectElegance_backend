package com.perfectElegance.service;

import com.perfectElegance.modal.Subscription;
import com.perfectElegance.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription>getAllSubscriptions(){
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(Integer id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public Subscription saveSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Integer id) {
        subscriptionRepository.deleteById(id);
    }


}
