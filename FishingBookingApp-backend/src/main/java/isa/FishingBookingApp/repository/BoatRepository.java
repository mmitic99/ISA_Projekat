package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoatRepository extends JpaRepository<Boat, Long> {

    @Override
    @Query("select b from Boat b where b.id = ?1 and b.deleted = false")
    Optional<Boat> findById(Long id);

    @Override
    @Query("select b from Boat b where b.id = ?1 and b.deleted = false")
    Boat getById(Long id);

    @Override
    @Query("select b from Boat b where b.deleted = false")
    List<Boat> findAll();

    @Query("select b from Boat b where b.boatOwner.id = ?1 and b.deleted = false")
    List<Boat> getAllBoatsOfUser(Long id);
}
