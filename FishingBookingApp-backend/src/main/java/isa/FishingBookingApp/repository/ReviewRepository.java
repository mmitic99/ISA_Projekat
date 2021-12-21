package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findReviewById(Long id);
    List<Review> findReviewByReservationUserMailAddress(String mailAddress);

    //List<Review> findReviewByReservationId(Long id);

    @Query("SELECT AVG(r.mark) FROM Review r WHERE r.reservation.reservationEntity.id = ?1")
    Double getAverageMarksByReservationEntitiesId(Long id);
}
