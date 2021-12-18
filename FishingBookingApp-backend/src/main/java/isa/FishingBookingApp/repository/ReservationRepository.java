package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReservationEntityIdAndDeletedEquals(Long id, boolean deleted);

    List<Reservation> findReservationByUserMailAddressAndStartGreaterThanEqualAndDeletedEquals(String mailAddress, LocalDateTime start, boolean deleted);

    Reservation findReservationById(Long id);
}
