package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.service.CottageService;
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
@RequestMapping(value = "api/cottage")
public class CottageController {

    private CottageService cottageService;
    private UserService userService;
    private TokenUtils tokenUtils;

    @Autowired
    public CottageController(CottageService cottageService, TokenUtils tokenUtils, UserService userService) {
        this.cottageService = cottageService;
        this.tokenUtils = tokenUtils;
        this.userService = userService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<Cottage> createCottage(@RequestBody CottageDTO newCottageDTO, HttpServletRequest request) {
        if (!authorizedUser(newCottageDTO.getCottageOwnerUsername(), request)){
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
        if (!authorizedUser(cottageDTO.getCottageOwnerUsername(), request)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        if (!cottageService.exists(cottageDTO.getId())){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        // TODO: uraditi proveru da li je moguce izmeniti vikendicu (ne sme biti rezervisana)
        Cottage updatedCottage = cottageService.saveOrUpdate(cottageDTO);
        return new ResponseEntity<>(updatedCottage, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/allCottages", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<List<Cottage>> getCottagesOfUser(@PathVariable String username, HttpServletRequest request) {
        if (!authorizedUser(username, request)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        User user = userService.findByMailAddress(username);
        List<Cottage> allCottagesOfUser = cottageService.getAllOfUser(user.getId());
        return new ResponseEntity<>(allCottagesOfUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('cottageOwner')")
    public ResponseEntity<Cottage> deleteCottage(@PathVariable Long id, HttpServletRequest request) {
        Cottage cottage = cottageService.get(id);
        if (cottage == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!authorizedUser(cottage.getCottageOwner().getUsername(), request)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        // TODO: uraditi proveru da li je moguce obrisati vikendicu (ne sme biti rezervisana)
        if (cottageService.delete(id)){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    private boolean authorizedUser(String cottageOwnerUsername, HttpServletRequest request) {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String username = tokenUtils.getUsernameFromToken(token.substring(7));
        return username.equals(cottageOwnerUsername);
    }
}
