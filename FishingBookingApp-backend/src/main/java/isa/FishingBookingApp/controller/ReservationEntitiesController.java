package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.*;
import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.service.*;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    private BoatService boatService;
    private ComplaintService complaintService;
    private ReviewService reviewService;
    private BusinessReportService businessReportService;

    @Autowired
    public ReservationEntitiesController(ReservationEntitiesService reservationEntitiesService, EntityImageService entityImageService, CottageService cottageService, ReservationService reservationService, TokenUtils tokenUtils, BoatService boatService, ComplaintService complaintService, ReviewService reviewService, BusinessReportService businessReportService) {
        this.reservationEntitiesService = reservationEntitiesService;
        this.entityImageService = entityImageService;
        this.cottageService = cottageService;
        this.reservationService = reservationService;
        this.tokenUtils = tokenUtils;
        this.boatService = boatService;
        this.complaintService = complaintService;
        this.reviewService = reviewService;
        this.businessReportService = businessReportService;
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

    @GetMapping(value = "/availableAppointments/{entityId}")
    public ResponseEntity<List<AvailableAppointment>> getAvailableAppointments(@PathVariable Long entityId) {
        ArrayList<AvailableAppointment> availableAppointments = (ArrayList<AvailableAppointment>) reservationEntitiesService.getAvailableAppointmentsOfEntity(entityId);

        if (availableAppointments == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(availableAppointments, HttpStatus.OK);
    }

    @GetMapping(value = "/currentReservation/{entityId}")
    @PreAuthorize("hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<Reservation> getCurrentReservationOfEntity(@PathVariable Long entityId){
        Reservation reservation = reservationEntitiesService.getCurrentReservationOfEntity(entityId);

        return new ResponseEntity<>(reservation, HttpStatus.OK);
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
        String userMailAddress = getMailAddressOfEntityOwner(additionalServiceDTO.getReservationEntityId());
        if (!tokenUtils.isUserAuthorizedAndTokenNotExpired(userMailAddress, request))
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        AdditionalService additionalService = reservationEntitiesService.createAdditionalService(additionalServiceDTO);
        if (additionalService == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(additionalService, HttpStatus.OK);
    }

    @PostMapping(value = "availableAppointments")
    @PreAuthorize("hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<AvailableAppointment> createAvailableAppointment(@RequestBody AvailableAppointmentDTO availableAppointmentDTO, HttpServletRequest request) {
        String userMailAddress = getMailAddressOfEntityOwner(availableAppointmentDTO.getEntityId());
        if (!tokenUtils.isUserAuthorizedAndTokenNotExpired(userMailAddress, request))
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        AvailableAppointment availableAppointment = reservationEntitiesService.createAvailableAppointment(availableAppointmentDTO);
        if (availableAppointment == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(availableAppointment, HttpStatus.OK);
    }

    @GetMapping(value = "/getPossibleReservationEntitiesForComplaint/{mailAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getPossibleReservationEntitiesForComplaint(@PathVariable String mailAddress, HttpServletRequest request) {
        try {
            if (!tokenUtils.isUserAuthorizedAndTokenNotExpired(mailAddress, request)) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(reservationEntitiesService.getPossibleReservationEntitiesForComplaint(mailAddress), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/addComplaint", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> addComplaint(@RequestBody ComplaintDTO complaintDTO, HttpServletRequest request) {
        try {
            if (!tokenUtils.isUserAuthorizedAndTokenNotExpired(complaintDTO.getMailAddress(), request)) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(complaintService.addComplaint(complaintDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private String getMailAddressOfEntityOwner(Long entityId) {
        String userMailAddress = "";
        ReservationEntities entity = reservationEntitiesService.get(entityId);
        if (entity == null) return userMailAddress;

        if (entity.getType().equals("cottage")) {
            Cottage cottage = cottageService.get(entity.getId());
            userMailAddress = cottage.getCottageOwner().getMailAddress();
        } else if (entity.getType().equals("boat")) {
            Boat boat = boatService.get(entity.getId());
            userMailAddress = boat.getBoatOwner().getMailAddress();
        }

        return userMailAddress;
    }

    @GetMapping(value = "/getMarksForReservationEntities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMarksForReservationEntities(HttpServletRequest request) {
        try {
            return new ResponseEntity<>(reviewService.getMarksForReservationEntities(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getMarksForReservationEntitiesOfUser/{userId}")
    @PreAuthorize("hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<Object> getMarksForReservationEntitiesOfUser(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(reviewService.getMarksForReservationEntitiesOfOwner(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getIncomesForReservationEntitiesOfUser/{userId}")
    @PreAuthorize("hasRole('cottageOwner')" + "|| hasRole('boatOwner')")
    public ResponseEntity<Object> getIncomesFromReservationEntitiesOfUser(@PathVariable Long userId, @RequestParam String startDate, @RequestParam String startTime, @RequestParam String endDate, @RequestParam String endTime) {
        try {
            TimeRangeDTO timeRangeDTO = new TimeRangeDTO(startDate, startTime, endDate, endTime);
            timeRangeDTO.setDateTimesFromStrings();
            return new ResponseEntity<>(businessReportService.getIncomeReportForOwnerInTimeRange(userId, timeRangeDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
