package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
}
