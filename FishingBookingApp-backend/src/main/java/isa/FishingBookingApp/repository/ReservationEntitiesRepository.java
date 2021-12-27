package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.ReservationEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface ReservationEntitiesRepository extends JpaRepository<ReservationEntities, Long> {

    ReservationEntities findReservationEntitiesById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select re from ReservationEntities re where re.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    ReservationEntities findReservationEntitiesByIdTransactional(Long id);
}
