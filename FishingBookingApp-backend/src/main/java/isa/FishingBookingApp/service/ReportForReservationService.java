package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.ReportForReservationDTO;
import isa.FishingBookingApp.model.ReportForReservation;

public interface ReportForReservationService {
    ReportForReservation getByReservationId(Long reservationId);

    ReportForReservation createNewReport(ReportForReservationDTO reportForReservationDTO);
}
