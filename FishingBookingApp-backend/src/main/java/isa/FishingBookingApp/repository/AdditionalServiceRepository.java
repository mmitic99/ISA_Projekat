package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long>  {
    @Query("select adds from AdditionalService adds where adds.reservationEntity.id = ?1")
    List<AdditionalService> getAllAdditionalServicesOfReservationEntity(Long id);
}
