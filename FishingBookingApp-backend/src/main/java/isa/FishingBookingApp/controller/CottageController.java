package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/cottageController")
public class CottageController {

    private CottageService cottageService;

    @Autowired
    public CottageController(CottageService cottageService) {
        this.cottageService = cottageService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cottage> createCottage(@RequestBody CottageDTO newCottageDTO) {
        Cottage newCottage = cottageService.saveOrUpdate(newCottageDTO);
        if (newCottage == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newCottage, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cottage> updateCottage(@RequestBody CottageDTO cottageDTO) {
        if (!cottageService.exists(cottageDTO.getId())){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Cottage updatedCottage = cottageService.saveOrUpdate(cottageDTO);
        return new ResponseEntity<>(updatedCottage, HttpStatus.OK) ;
    }
}
