package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}