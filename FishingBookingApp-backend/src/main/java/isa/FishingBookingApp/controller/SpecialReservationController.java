package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.SpecialReservationDTO;
import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.service.BoatService;
import isa.FishingBookingApp.service.CottageService;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import isa.FishingBookingApp.service.SpecialReservationService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/specialReservation")
public class SpecialReservationController {
    private SpecialReservationService specialReservationService;
    private ReservationEntitiesService reservationEntitiesService;
    private CottageService cottageService;
    private BoatService boatService;
    private TokenUtils tokenUtils;

    @Autowired
    public SpecialReservationController(SpecialReservationService specialReservationService, ReservationEntitiesService reservationEntitiesService, CottageService cottageService, BoatService boatService, TokenUtils tokenUtils) {
        this.specialReservationService = specialReservationService;
        this.reservationEntitiesService = reservationEntitiesService;
        this.cottageService = cottageService;
        this.boatService = boatService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping(value = "")
    @PreAuthorize("hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<Object> createSpecialReservation(@RequestBody SpecialReservationDTO specialReservationDTO, HttpServletRequest request) {
        try {
            ReservationEntities reservationEntity = reservationEntitiesService.get(specialReservationDTO.getReservationEntityId());
            specialReservationDTO.setMailAddress(ownerUsernameOfReservationEntity(reservationEntity));
            if (!tokenUtils.isUserAuthorizedAndTokenNotExpired(specialReservationDTO.getMailAddress(), request)) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            specialReservationDTO.setDateTimesFromStrings();
            SpecialReservation specialReservation = specialReservationService.createSpecialReservation(specialReservationDTO);
            return new ResponseEntity<>(specialReservation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "take/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> takeSpecialReservation(@PathVariable Long id, HttpServletRequest request) {
        try {
            String token = tokenUtils.getAuthHeaderFromHeader(request);
            String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));
            Reservation reservation = specialReservationService.takeSpecialReservation(id, mailAddress);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    private String ownerUsernameOfReservationEntity(ReservationEntities entity) {
        if (entity == null) return "";

        if (entity.getType().equals("cottage")) {
            Cottage cottage = cottageService.get(entity.getId());
            return cottage.getCottageOwner().getMailAddress();
        } else if (entity.getType().equals("boat")) {
            Boat boat = boatService.get(entity.getId());
            return boat.getBoatOwner().getMailAddress();
        }

        return "";
    }
}
