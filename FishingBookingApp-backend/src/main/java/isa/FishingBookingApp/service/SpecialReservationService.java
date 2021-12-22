package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.SpecialReservationDTO;
import isa.FishingBookingApp.model.SpecialReservation;

public interface SpecialReservationService {
    SpecialReservation createSpecialReservation(SpecialReservationDTO specialReservationDTO) throws Exception;
}
