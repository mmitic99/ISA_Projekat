package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.BoatDTO;
import isa.FishingBookingApp.model.Boat;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.service.BoatService;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import isa.FishingBookingApp.service.UserService;
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
@RequestMapping(value = "api/boat")
public class BoatController {

    private BoatService boatService;
    private UserService userService;
    private TokenUtils tokenUtils;
    private ReservationEntitiesService reservationEntitiesService;

    @Autowired
    public BoatController(BoatService boatService, TokenUtils tokenUtils, UserService userService, ReservationEntitiesService reservationEntitiesService) {
        this.boatService = boatService;
        this.tokenUtils = tokenUtils;
        this.userService = userService;
        this.reservationEntitiesService = reservationEntitiesService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<Boat> createBoat(@RequestBody BoatDTO newBoatDTO, HttpServletRequest request) {
        if (!authorizedUser(newBoatDTO.getBoatOwnerUsername(), request)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Boat newBoat = boatService.saveOrUpdate(newBoatDTO);
        if (newBoat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newBoat, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<Boat> updateBoat(@RequestBody BoatDTO boatDTO, HttpServletRequest request) {
        if (!authorizedUser(boatDTO.getBoatOwnerUsername(), request)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Boat updatedBoat = boatService.updateTransactional(boatDTO);
        if (updatedBoat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedBoat, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/allBoats", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<List<Boat>> getBoatsOfUser(@PathVariable String username, HttpServletRequest request) {
        if (!authorizedUser(username, request)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        User user = userService.findByMailAddress(username);
        List<Boat> allBoatsOfUser = boatService.getAllOfUser(user.getId());
        return new ResponseEntity<>(allBoatsOfUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('boatOwner')")
    public ResponseEntity<Boat> deleteBoat(@PathVariable Long id, HttpServletRequest request) {
        Boat boat = boatService.get(id);
        if (boat == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!authorizedUser(boat.getBoatOwner().getUsername(), request)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        if (boatService.delete(id)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    private boolean authorizedUser(String cottageOwnerUsername, HttpServletRequest request) {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String username = tokenUtils.getUsernameFromToken(token.substring(7));
        return username.equals(cottageOwnerUsername);
    }
}
