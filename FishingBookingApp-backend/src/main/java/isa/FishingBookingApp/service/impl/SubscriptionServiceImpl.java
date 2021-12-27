package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.dto.SubscriptionDTO;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.Subscription;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.repository.SubscriptionRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private SubscriptionRepository subscriptionRepository;
    private UserRepository userRepository;
    private ReservationEntitiesRepository reservationEntitiesRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserRepository userRepository, ReservationEntitiesRepository reservationEntitiesRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.reservationEntitiesRepository = reservationEntitiesRepository;
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
    @Transactional(readOnly = false)
    public Subscription subscribe(SubscriptionDTO subscriptionDTO) {
        User user = userRepository.findByMailAddressTransactional(subscriptionDTO.getMailAddress());
        ReservationEntities reservationEntities = reservationEntitiesRepository.findReservationEntitiesById(subscriptionDTO.getReservationEntityId());
        return subscriptionRepository.save(new Subscription(user, reservationEntities));
    }

    @Override
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription getByUserAndEntity(String mailAddress, Long reservationEntityId) {
        return subscriptionRepository.findByUserMailAddressAndReservationEntitiesId(mailAddress, reservationEntityId);
    }

    @Override
    public List<User> getUsersSubscribedToReservationEntity(Long id) {
        ArrayList<User> subscribedUsers = new ArrayList<>();
        for(Subscription subscription : subscriptionRepository.findAllByReservationEntitiesId(id)) {
            subscribedUsers.add(subscription.getUser());
        }

        return subscribedUsers;
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
