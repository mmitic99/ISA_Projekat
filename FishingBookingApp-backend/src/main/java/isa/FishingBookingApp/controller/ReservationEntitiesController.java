package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/api/reservationEntities", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationEntitiesController {
    private ReservationEntitiesService reservationEntitiesService;

    @Autowired
    public ReservationEntitiesController(ReservationEntitiesService reservationEntitiesService) {
        this.reservationEntitiesService=reservationEntitiesService;
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping("/getAll")
    public List<ReservationEntities> getAll() {
        return reservationEntitiesService.getAll();
    }
}
