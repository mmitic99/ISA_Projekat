package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
