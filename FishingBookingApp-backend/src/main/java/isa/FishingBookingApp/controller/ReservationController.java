package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.CancelReservationDTO;
import isa.FishingBookingApp.dto.ReservationDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import isa.FishingBookingApp.service.ReservationService;
import isa.FishingBookingApp.util.TokenUtils;
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
    private TokenUtils tokenUtils;

    @Autowired
    public ReservationController(ReservationEntitiesService reservationEntitiesService, ReservationService reservationService, TokenUtils tokenUtils) {
        this.reservationEntitiesService = reservationEntitiesService;
        this.reservationService = reservationService;
        this.tokenUtils = tokenUtils;
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
        try {
            if(!authorizedUser(reservationDTO.getMailAddress(), request)){
                return new ResponseEntity<>("Mail adresa nije u redu", HttpStatus.BAD_REQUEST);
            }
            reservationDTO.setDateTimeFromStrings();
            Reservation reservation = reservationService.reserveEntity(reservationDTO);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAdditionalServices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> checkReservation(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(reservationEntitiesService.getAdditionalServices(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getCurrentReservation/{mailAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getCurrentReservation(@PathVariable String mailAddress, HttpServletRequest request) {
        try {
            if(!authorizedUser(mailAddress, request)){
                return new ResponseEntity<>("Mail adresa nije u redu", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(reservationService.getCurrentReservationForUser(mailAddress), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/cancelReservation", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getCurrentReservation(@RequestBody CancelReservationDTO cancelReservationDTO, HttpServletRequest request) {
        try {
            if(!authorizedUser(cancelReservationDTO.getMailAddress(), request)){
                return new ResponseEntity<>("Mail adresa nije u redu", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(reservationService.cancelReservation(cancelReservationDTO.getReservationId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAllOldReservation/{mailAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getAllOldReservation(@PathVariable String mailAddress, HttpServletRequest request) {
        try {
            if(!authorizedUser(mailAddress, request)){
                return new ResponseEntity<>("Mail adresa nije u redu", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(reservationService.getAllOldReservation(mailAddress), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private boolean authorizedUser(String ownerUsername, HttpServletRequest request) {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String username = tokenUtils.getUsernameFromToken(token.substring(7));
        return username.equals(ownerUsername);
    }
}
