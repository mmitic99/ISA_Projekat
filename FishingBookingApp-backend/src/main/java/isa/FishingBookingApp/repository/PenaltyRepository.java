package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}
