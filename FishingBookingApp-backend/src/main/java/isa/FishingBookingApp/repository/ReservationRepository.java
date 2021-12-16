package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReservationEntityId(Long id);
}
