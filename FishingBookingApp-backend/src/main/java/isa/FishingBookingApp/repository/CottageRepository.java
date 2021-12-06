package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
    @Query("select c from Cottage c where c.cottageOwner.id = ?1")
    public List<Cottage> getAllCottagesOfUser(Long id);
}
