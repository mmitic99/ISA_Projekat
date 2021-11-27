package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMailAddress(String username);
}
