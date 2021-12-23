package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.SpecialReservationDTO;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.SpecialReservation;

import java.util.List;

public interface SpecialReservationService {
    SpecialReservation createSpecialReservation(SpecialReservationDTO specialReservationDTO) throws Exception;

    Reservation takeSpecialReservation(Long id, String mailAddress) throws Exception;

    List<SpecialReservation> getAllSpecialReservationByEntityId(Long id);
}
