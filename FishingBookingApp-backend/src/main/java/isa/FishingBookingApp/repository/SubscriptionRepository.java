package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserMailAddress(String mailAddress);

    Subscription findByUserMailAddressAndReservationEntitiesId(String mailAddress, Long id);

    List<Subscription> findAllByReservationEntitiesId(Long id);

    void deleteAllByReservationEntitiesId(Long id);
}
