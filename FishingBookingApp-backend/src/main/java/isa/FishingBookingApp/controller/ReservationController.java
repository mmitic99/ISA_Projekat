package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.ReservationDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import isa.FishingBookingApp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "api/reservation")
public class ReservationController {

    private ReservationEntitiesService reservationEntitiesService;
    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationEntitiesService reservationEntitiesService, ReservationService reservationService) {
        this.reservationEntitiesService = reservationEntitiesService;
        this.reservationService = reservationService;
    }


    @GetMapping(value = "/checkReservation", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> checkReservation(@RequestParam String sort, @RequestParam List<String> types, @RequestParam String search, @RequestParam String date, @RequestParam String time, @RequestParam int days, @RequestParam int guests) {
        try {
            SearchFilterSort searchFilterSort = new SearchFilterSort(sort, types, search, date, time, days, guests);
            List<ReservationEntities> reservationEntities = reservationEntitiesService.searchFilterSort(searchFilterSort);
            return new ResponseEntity<>(reservationService.checkIsReservationEntitiesIsAvailable(reservationEntities, searchFilterSort), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/reserve", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> reserve(@RequestBody ReservationDTO reservationDTO, HttpServletRequest request) {
        reservationDTO.setDateTimeFromStrings();

        //TODO:

        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }
}
