package isa.FishingBookingApp.repository;

import isa.FishingBookingApp.model.ReservationEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface ReservationEntitiesRepository extends JpaRepository<ReservationEntities, Long> {

    @Override
    @Query("select re from ReservationEntities re where re.id = ?1 and re.deleted = false")
    Optional<ReservationEntities> findById(Long id);

    @Override
    @Query("select re from ReservationEntities re where re.id = ?1 and re.deleted = false")
    ReservationEntities getById(Long id);

    @Override
    @Query("select re from ReservationEntities re where re.deleted = false")
    List<ReservationEntities> findAll();

    @Query("select re from ReservationEntities re where re.id = ?1 and re.deleted = false")
    ReservationEntities findReservationEntitiesById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select re from ReservationEntities re where re.id = :id and re.deleted = false")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    ReservationEntities findReservationEntitiesByIdTransactional(Long id);
}
