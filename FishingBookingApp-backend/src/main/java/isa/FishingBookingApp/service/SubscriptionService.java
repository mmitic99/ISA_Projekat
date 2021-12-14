package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.dto.SubscriptionDTO;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> getSubscribedByMailAddress(String mailAddress);

    Subscription unsubscribe(SubscriptionDTO subscriptionDTO);

    List<Subscription> searchFilterSort(SearchFilterSort searchFilterSort);
}
