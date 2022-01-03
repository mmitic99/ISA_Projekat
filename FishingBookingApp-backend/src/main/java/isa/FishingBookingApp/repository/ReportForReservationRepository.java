package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.ReportForReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportForReservationRepository extends JpaRepository<ReportForReservation, Long> {
    ReportForReservation findByReservationId(Long reservationId);
    boolean existsByReservationId(Long reservationId);
}
