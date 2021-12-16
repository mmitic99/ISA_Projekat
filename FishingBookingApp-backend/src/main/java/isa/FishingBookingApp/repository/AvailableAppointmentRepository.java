package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.AvailableAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableAppointmentRepository extends JpaRepository<AvailableAppointment, Long> {
    List<AvailableAppointment> findByEntityId(Long id);
}
