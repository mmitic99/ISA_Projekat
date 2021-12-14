package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.dto.SubscriptionDTO;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.Subscription;
import isa.FishingBookingApp.repository.SubscriptionRepository;
import isa.FishingBookingApp.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Subscription> getSubscribedByMailAddress(String mailAddress) {
        return subscriptionRepository.findByUserMailAddress(mailAddress);
    }

    @Override
    public Subscription unsubscribe(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = subscriptionRepository.findByUserMailAddressAndReservationEntitiesId(subscriptionDTO.getMailAddress(), subscriptionDTO.getReservationEntityId());
        subscriptionRepository.delete(subscription);
        return subscription;
    }

    @Override
    public List<Subscription> searchFilterSort(SearchFilterSort searchFilterSort) {
        List<Subscription> subscriptions;
        if (searchFilterSort.getTypes().size() != 0) {
            subscriptions = filter(searchFilterSort);
        } else {
            subscriptions = getSubscribedByMailAddress(searchFilterSort.getMailAddress());
        }
        return subscriptions;
    }

    private List<Subscription> filter(SearchFilterSort searchFilterSort) {
        List<Subscription> retVal = new ArrayList<>();
        for (Subscription subscription : getSubscribedByMailAddress(searchFilterSort.getMailAddress())) {

            if (searchFilterSort.getTypes().size() == 0) {
                retVal.add(subscription);
            } else if (searchFilterSort.getTypes().contains(subscription.getReservationEntities().getType())) {
                retVal.add(subscription);
            }
        }
        return retVal;
    }
}
