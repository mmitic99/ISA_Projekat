package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.SpecialReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.List;

public interface SpecialReservationRepository extends JpaRepository<SpecialReservation, Long> {
    List<SpecialReservation> findByReservationEntityId(Long id);

    List<SpecialReservation> findByReservationEntityIdAndValidFromBeforeAndValidToAfter(Long id, LocalDateTime validFrom, LocalDateTime validTo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    SpecialReservation findSpecialReservationsById(Long id);
}
