package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.ReportForReservationDTO;
import isa.FishingBookingApp.model.Penalty;
import isa.FishingBookingApp.model.ReportForReservation;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.repository.PenaltyRepository;
import isa.FishingBookingApp.repository.ReportForReservationRepository;
import isa.FishingBookingApp.repository.ReservationRepository;
import isa.FishingBookingApp.service.ReportForReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReportForReservationServiceImpl implements ReportForReservationService {

    private ReportForReservationRepository reportForReservationRepository;
    private ReservationRepository reservationRepository;
    private PenaltyRepository penaltyRepository;

    @Autowired
    public ReportForReservationServiceImpl(ReportForReservationRepository reportForReservationRepository, ReservationRepository reservationRepository, PenaltyRepository penaltyRepository) {
        this.reportForReservationRepository = reportForReservationRepository;
        this.reservationRepository = reservationRepository;
        this.penaltyRepository = penaltyRepository;
    }

    @Override
    public ReportForReservation getByReservationId(Long reservationId) {
        return reportForReservationRepository.findByReservationId(reservationId);
    }

    @Override
    public ReportForReservation createNewReport(ReportForReservationDTO reportForReservationDTO) {
        Reservation reservation = reservationRepository.findReservationById(reportForReservationDTO.getReservationId());
        if (reservation == null || reportForReservationRepository.existsByReservationId(reservation.getId())){
            return null;
        }
        ReportForReservation newReport = new ReportForReservation(reservation, reportForReservationDTO.getDescription(), reportForReservationDTO.getType(), reportForReservationDTO.isCustomerAppeared(), reportForReservationDTO.isRequestForPenalty());
        // Ukoliko je oznaceno da klijent nije ni dosao automatski dobija penal
        if (newReport.isCustomerAppeared() == false) {
            createPenaltyForUser(newReport.getReservation().getUser());
        }
        return reportForReservationRepository.save(newReport);
    }

    private void createPenaltyForUser(User user) {
        Penalty penalty = new Penalty(user, LocalDateTime.now());
        penaltyRepository.save(penalty);
    }
}
