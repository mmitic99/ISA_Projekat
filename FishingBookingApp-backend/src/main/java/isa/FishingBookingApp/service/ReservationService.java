package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.ReservationDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.ReservationEntities;

import java.util.List;

public interface ReservationService {
    List<ReservationEntities> checkIsReservationEntitiesIsAvailable(List<ReservationEntities> reservationEntities, SearchFilterSort searchFilterSort);

    Reservation reserveEntity(ReservationDTO reservationDTO) throws Exception;

    List<Reservation> getCurrentReservationForUser(String mailAddress);

    Reservation cancelReservation(Long id) throws Exception;
}