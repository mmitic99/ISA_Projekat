package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.RequestForDeletingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestForDeletingAccountRepository extends JpaRepository<RequestForDeletingAccount, Long> {
    List<RequestForDeletingAccount> findByUserMailAddress(String mailAddress);
}
