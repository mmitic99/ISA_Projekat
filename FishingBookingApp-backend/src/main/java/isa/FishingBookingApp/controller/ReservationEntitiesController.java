package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/reservationEntities")
public class ReservationEntitiesController {
    private ReservationEntitiesService reservationEntitiesService;

    @Autowired
    public ReservationEntitiesController(ReservationEntitiesService reservationEntitiesService) {
        this.reservationEntitiesService=reservationEntitiesService;
    }

    @GetMapping(value="/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationEntities> getAll() {
        return reservationEntitiesService.getAll();
    }

    @GetMapping(value="/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationEntities getOne(@PathVariable Long id) {
        System.out.println(id);
        return reservationEntitiesService.get(id);
    }
}
