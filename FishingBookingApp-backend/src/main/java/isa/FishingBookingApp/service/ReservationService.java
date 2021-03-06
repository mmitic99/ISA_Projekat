package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.ReservationDTO;
import isa.FishingBookingApp.dto.ReservationForClientDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.ReservationEntities;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    List<ReservationEntities> checkReservationEntitiesIsAvailable(List<ReservationEntities> reservationEntities, SearchFilterSort searchFilterSort);

    Reservation getById(Long id);

    Reservation reserveEntity(ReservationDTO reservationDTO) throws Exception;

    Reservation reserveEntityForClient(ReservationForClientDTO reservationForClientDTO) throws Exception;

    List<Reservation> getCurrentReservationForUser(String mailAddress);

    Reservation cancelReservation(Long id) throws Exception;

    List<Reservation> getAllReservationsOfEntity(Long entityId);

    List<Reservation> getAllOldReservation(String mailAddress);

    List<Reservation> searchFilterSort(SearchFilterSort searchFilterSort);

    boolean reservationEntityIsAvailable(ReservationEntities reservationEntity, LocalDateTime start, int days);
}
