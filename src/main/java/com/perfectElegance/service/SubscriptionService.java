package com.perfectElegance.service;

import com.perfectElegance.modal.Subscription;
import com.perfectElegance.repository.SubscriptionRepository;
import com.perfectElegance.Dto.SubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;


    public List<SubscriptionDto> getSubscribers() {
        System.out.println("hi inside the service");
        List<Subscription> subscriptions=subscriptionRepository.findAll();
        List<SubscriptionDto> subscriptionDtos=subscriptions.stream()
                        .map(subscription -> {
                            SubscriptionDto dto = new SubscriptionDto();
                            dto.setUser(subscription.getUser().getEmail());
                            dto.setSubscriptionStartDate(subscription.getSubscriptionStartDate());
                            dto.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
                            dto.setAmount(subscription.getAmount());
                            dto.setStatus(subscription.getStatus());
                             return dto;
                        })
                .filter(dto -> "ACTIVE".equals(dto.getStatus()))
                .collect(Collectors.toList());
        System.out.println(subscriptionDtos);

        return subscriptionDtos;

    }
}
