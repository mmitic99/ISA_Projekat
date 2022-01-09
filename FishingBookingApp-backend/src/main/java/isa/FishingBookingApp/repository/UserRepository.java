package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.BoatOwner;
import isa.FishingBookingApp.model.CottageOwner;
import isa.FishingBookingApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMailAddress(String mailAddress);
    CottageOwner findCottageOwnerById(Long id);
    BoatOwner findBoatOwnerById(Long id);
    User findUserById(Long id);
}
