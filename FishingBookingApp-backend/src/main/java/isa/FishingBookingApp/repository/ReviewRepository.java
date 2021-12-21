package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findReviewById(Long id);
    List<Review> findReviewByReservationUserMailAddress(String mailAddress);
}
