package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.ReservationEntities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByReservationEntityId(Long id);

    List<Reservation> findByReservationEntityIdAndDeletedEquals(Long id, boolean deleted);

    List<Reservation> findReservationByUserMailAddressAndStartGreaterThanEqualAndDeletedEquals(String mailAddress, LocalDateTime start, boolean deleted);

    List<Reservation> findReservationByUserMailAddressAndStartLessThanAndDeletedEquals(String mailAddress, LocalDateTime start, boolean deleted);

    Reservation findReservationById(Long id);

    List<Reservation> findReservationByUserMailAddress(String mailAddress);
}
