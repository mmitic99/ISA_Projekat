package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
