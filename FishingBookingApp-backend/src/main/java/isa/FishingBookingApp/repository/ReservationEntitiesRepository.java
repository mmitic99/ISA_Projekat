package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.ReservationEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationEntitiesRepository extends JpaRepository<ReservationEntities, Long> {
    ReservationEntities findReservationEntitiesById(Long id);
}
