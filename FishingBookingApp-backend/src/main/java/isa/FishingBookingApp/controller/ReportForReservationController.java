package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.ReportForReservationDTO;
import isa.FishingBookingApp.model.ReportForReservation;
import isa.FishingBookingApp.service.ReportForReservationService;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import isa.FishingBookingApp.service.ReservationService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/reportForReservation")
public class ReportForReservationController {
    private ReservationEntitiesService reservationEntitiesService;
    private ReservationService reservationService;
    private TokenUtils tokenUtils;
    private ReportForReservationService reportForReservationService;

    @Autowired
    public ReportForReservationController(ReservationEntitiesService reservationEntitiesService, ReservationService reservationService, TokenUtils tokenUtils, ReportForReservationService reportForReservationService) {
        this.reservationEntitiesService = reservationEntitiesService;
        this.reservationService = reservationService;
        this.tokenUtils = tokenUtils;
        this.reportForReservationService = reportForReservationService;
    }

    @GetMapping("/{reservationId}")
    @PreAuthorize("hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<Object> getReportForReservation(@PathVariable Long reservationId) {
        return new ResponseEntity<>(reportForReservationService.getByReservationId(reservationId), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<Object> createReportForReservation(@RequestBody ReportForReservationDTO reportDTO) {
        ReportForReservation report = reportForReservationService.createNewReport(reportDTO);
        if (report == null) return new ResponseEntity<>("Neuspešno kreiranje izveštaja.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
