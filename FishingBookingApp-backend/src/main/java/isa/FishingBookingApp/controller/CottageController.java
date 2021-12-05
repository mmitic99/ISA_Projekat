package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.service.CottageService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/cottageController")
public class CottageController {

    private CottageService cottageService;
    private TokenUtils tokenUtils;

    @Autowired
    public CottageController(CottageService cottageService, TokenUtils tokenUtils) {
        this.cottageService = cottageService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<Cottage> createCottage(@RequestBody CottageDTO newCottageDTO, HttpServletRequest request) {
        if (!authorizedUser(newCottageDTO, request)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Cottage newCottage = cottageService.saveOrUpdate(newCottageDTO);
        if (newCottage == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newCottage, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<Cottage> updateCottage(@RequestBody CottageDTO cottageDTO, HttpServletRequest request) {
        if (!authorizedUser(cottageDTO, request)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        if (!cottageService.exists(cottageDTO.getId())){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Cottage updatedCottage = cottageService.saveOrUpdate(cottageDTO);
        return new ResponseEntity<>(updatedCottage, HttpStatus.OK);
    }

    private boolean authorizedUser(CottageDTO cottageDTO, HttpServletRequest request) {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String username = tokenUtils.getUsernameFromToken(token.substring(7));
        return username.equals(cottageDTO.getCottageOwnerUsername());
    }
}
