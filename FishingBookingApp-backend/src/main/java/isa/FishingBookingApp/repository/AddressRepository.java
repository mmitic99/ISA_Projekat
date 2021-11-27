package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
