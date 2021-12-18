package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoatRepository extends JpaRepository<Boat, Long> {
    @Query("select b from Boat b where b.boatOwner.id = ?1")
    List<Boat> getAllBoatsOfUser(Long id);
}
