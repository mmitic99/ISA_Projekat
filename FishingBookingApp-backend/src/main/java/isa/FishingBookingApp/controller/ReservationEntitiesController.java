package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.EntityImage;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.service.EntityImageService;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/reservationEntities")
public class ReservationEntitiesController {
    private ReservationEntitiesService reservationEntitiesService;
    private EntityImageService entityImageService;

    @Autowired
    public ReservationEntitiesController(ReservationEntitiesService reservationEntitiesService, EntityImageService entityImageService) {
        this.reservationEntitiesService=reservationEntitiesService;
        this.entityImageService = entityImageService;
    }

    @GetMapping(value="/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationEntities> getAll() {
        return reservationEntitiesService.getAll();
    }

    @GetMapping(value="/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationEntities getOne(@PathVariable Long id) {
        return reservationEntitiesService.get(id);
    }

    @GetMapping(value="/searchFilterSort", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationEntities> searchFilterSort(@RequestParam String sort, @RequestParam List<String> types, @RequestParam String search) {
        SearchFilterSort searchFilterSort = new SearchFilterSort(sort, types, search);
        return reservationEntitiesService.searchFilterSort(searchFilterSort);
    }

    @PostMapping(value = "/imageUpload/{entityId}")
    public ResponseEntity<EntityImage> uploadEntityImage(@RequestParam MultipartFile multipartImage, @PathVariable Long entityId) {
        ReservationEntities entity = reservationEntitiesService.get(entityId);
        if (entity == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        EntityImage image = new EntityImage();
        image.setName(multipartImage.getName());
        try{
            image.setContent(multipartImage.getBytes());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        image.setEntity(entity);
        image = entityImageService.save(image);

        if (image == null)  return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
