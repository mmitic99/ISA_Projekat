package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.AvailableAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableAppointmentRepository extends JpaRepository<AvailableAppointment, Long> {
}
