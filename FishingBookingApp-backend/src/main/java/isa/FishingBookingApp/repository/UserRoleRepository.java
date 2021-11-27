package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(String role);
}
