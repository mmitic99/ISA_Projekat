package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long>  {
    List<AdditionalService> findAdditionalServiceByReservationEntityId(Long id);

    AdditionalService findAdditionalServiceById(Long id);
}
