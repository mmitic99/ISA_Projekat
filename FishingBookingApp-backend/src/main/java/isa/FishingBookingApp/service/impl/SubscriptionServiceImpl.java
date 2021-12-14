package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.SubscriptionDTO;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.Subscription;
import isa.FishingBookingApp.repository.SubscriptionRepository;
import isa.FishingBookingApp.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Subscription> getSubscribedByUsername(String mailAddress) {
        return subscriptionRepository.findByUserMailAddress(mailAddress);
    }

    @Override
    public Subscription unsubscribe(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = subscriptionRepository.findByUserMailAddressAndReservationEntitiesId(subscriptionDTO.getMailAddress(), subscriptionDTO.getReservationEntityId());
        subscriptionRepository.delete(subscription);
        return subscription;
    }
}
