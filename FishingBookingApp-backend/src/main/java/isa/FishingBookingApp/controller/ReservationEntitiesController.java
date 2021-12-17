package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.AdditionalServiceDTO;
import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.dto.EntityImageDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.AdditionalService;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.model.EntityImage;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.service.CottageService;
import isa.FishingBookingApp.service.EntityImageService;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import isa.FishingBookingApp.service.ReservationService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(value = "api/reservationEntities")
public class ReservationEntitiesController {
    private ReservationEntitiesService reservationEntitiesService;
    private EntityImageService entityImageService;
    private CottageService cottageService;
    private ReservationService reservationService;
    private TokenUtils tokenUtils;

    @Autowired
    public ReservationEntitiesController(ReservationEntitiesService reservationEntitiesService, EntityImageService entityImageService, CottageService cottageService, ReservationService reservationService, TokenUtils tokenUtils) {
        this.reservationEntitiesService = reservationEntitiesService;
        this.entityImageService = entityImageService;
        this.cottageService = cottageService;
        this.reservationService = reservationService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationEntities> getAll() {
        return reservationEntitiesService.getAll();
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationEntities getOne(@PathVariable Long id) {
        return reservationEntitiesService.get(id);
    }

    @GetMapping(value = "/searchFilterSort", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationEntities> searchFilterSort(@RequestParam String sort, @RequestParam List<String> types, @RequestParam String search) {
        SearchFilterSort searchFilterSort = new SearchFilterSort(sort, types, search);
        return reservationEntitiesService.searchFilterSort(searchFilterSort);
    }

    @GetMapping(value = "/images/{entityId}")
    public ResponseEntity<List<String>> getEntityImages(@PathVariable Long entityId) {
        ReservationEntities entity = reservationEntitiesService.get(entityId);
        if (entity == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<EntityImage> images = entityImageService.getImagesOfReservationEntity(entityId);
        if (images == null) return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);

        ArrayList<String> base64Images = new ArrayList<String>();
        for (EntityImage image : images) {
            base64Images.add(Base64.getEncoder().encodeToString(image.getContent()));
        }

        return new ResponseEntity<>(base64Images, HttpStatus.OK);
    }

    @GetMapping(value = "/additionalServices/{entityId}")
    public ResponseEntity<List<AdditionalService>> getAdditionalServices(@PathVariable Long entityId) {
        ArrayList<AdditionalService> additionalServices = (ArrayList<AdditionalService>) reservationEntitiesService.getAdditionalServices(entityId);

        if (additionalServices == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(additionalServices, HttpStatus.OK);
    }

    @PostMapping(value = "/imageUpload/{entityId}")
    @PreAuthorize("hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<EntityImageDTO> uploadEntityImage(@RequestParam MultipartFile multipartImage, @PathVariable Long entityId) {
        ReservationEntities entity = reservationEntitiesService.get(entityId);
        if (entity == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        EntityImage image = new EntityImage();
        image.setName(multipartImage.getName());
        try {
            image.setContent(multipartImage.getBytes());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        image.setEntity(entity);
        image = entityImageService.save(image);
        EntityImageDTO imageDTO = new EntityImageDTO();
        imageDTO.setBase64Image(Base64.getEncoder().encodeToString(image.getContent()));

        if (image == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(imageDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/oneImage/{entityId}")
    public ResponseEntity<EntityImageDTO> getOneEntityImage(@PathVariable Long entityId) {
        ReservationEntities entity = reservationEntitiesService.get(entityId);
        if (entity == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        EntityImage image = entityImageService.getOneImageOfReservationEntity(entityId);
        if (image == null) return new ResponseEntity<>(HttpStatus.OK);
        EntityImageDTO imageDTO = new EntityImageDTO();
        imageDTO.setBase64Image(Base64.getEncoder().encodeToString(image.getContent()));

        return new ResponseEntity<>(imageDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/additionalServices")
    @PreAuthorize("hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<AdditionalService> createAdditionalService(@RequestBody AdditionalServiceDTO additionalServiceDTO, HttpServletRequest request) {
        String userMailAddress = "";
        ReservationEntities entity = reservationEntitiesService.get(additionalServiceDTO.getReservationEntityId());
        if (entity == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        if (entity.getType().equals("cottage")) {
            Cottage cottage = cottageService.get(entity.getId());
            userMailAddress = cottage.getCottageOwner().getMailAddress();
        } else if (entity.getType().equals("boat")) {
            // TODO: implementirati kada se budu implementirale stvari za brod
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (!authorizedUser(userMailAddress, request)) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        AdditionalService additionalService = reservationEntitiesService.createAdditionalService(additionalServiceDTO);
        if (additionalService == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(additionalService, HttpStatus.OK);
    }

    private boolean authorizedUser(String ownerUsername, HttpServletRequest request) {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String username = tokenUtils.getUsernameFromToken(token.substring(7));
        return username.equals(ownerUsername);
    }

}
