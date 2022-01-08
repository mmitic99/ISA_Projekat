package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.dto.SubscriptionDTO;
import isa.FishingBookingApp.model.Subscription;
import isa.FishingBookingApp.model.User;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> getSubscribedByMailAddress(String mailAddress);

    Subscription unsubscribe(SubscriptionDTO subscriptionDTO);

    List<Subscription> searchFilterSort(SearchFilterSort searchFilterSort);

    Subscription subscribe(SubscriptionDTO subscriptionDTO);

    List<Subscription> getAll();

    Subscription getByUserAndEntity(String mailAddress, Long reservationEntityId);

    List<User> getUsersSubscribedToReservationEntity(Long id);
}
