package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CottageRepository extends JpaRepository<Cottage, Long> {

    @Override
    @Query("select c from Cottage c where c.id = ?1 and c.deleted = false")
    Optional<Cottage> findById(Long id);

    @Override
    @Query("select c from Cottage c where c.id = ?1 and c.deleted = false")
    Cottage getById(Long id);

    @Override
    @Query("select c from Cottage c where c.deleted = false")
    List<Cottage> findAll();

    @Query("select c from Cottage c where c.cottageOwner.id = ?1 and c.deleted = false")
    List<Cottage> getAllCottagesOfUser(Long id);

}
