package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/cottageController")
public class CottageController {

    private CottageService cottageService;

    @Autowired
    public CottageController(CottageService cottageService){
        this.cottageService = cottageService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cottage createCottage(CottageDTO newCottageDTO){
        return cottageService.saveOrUpdate(newCottageDTO);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cottage updateCottage(CottageDTO cottageDTO){
        return cottageService.saveOrUpdate(cottageDTO);
    }
}
