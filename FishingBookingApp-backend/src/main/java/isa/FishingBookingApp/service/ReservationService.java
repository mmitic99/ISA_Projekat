package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.ReservationEntities;

import java.util.List;

public interface ReservationService {
    List<ReservationEntities> checkIsReservationEntitiesIsAvailable(List<ReservationEntities> reservationEntities, SearchFilterSort searchFilterSort);
}
