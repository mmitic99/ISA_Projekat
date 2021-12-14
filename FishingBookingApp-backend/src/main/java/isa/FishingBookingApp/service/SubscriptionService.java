package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.SubscriptionDTO;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> getSubscribedByUsername(String mailAddress);

    Subscription unsubscribe(SubscriptionDTO subscriptionDTO);
}
