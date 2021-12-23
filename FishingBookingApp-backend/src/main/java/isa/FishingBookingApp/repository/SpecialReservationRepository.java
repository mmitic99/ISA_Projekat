package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.SpecialReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SpecialReservationRepository extends JpaRepository<SpecialReservation, Long> {
    List<SpecialReservation> findByReservationEntityId(Long id);

    List<SpecialReservation> findByReservationEntityIdAndValidFromBeforeAndValidToAfter(Long id, LocalDateTime validFrom, LocalDateTime validTo);
}
