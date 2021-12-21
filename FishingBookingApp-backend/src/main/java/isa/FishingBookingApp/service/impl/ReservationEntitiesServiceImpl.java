package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.AdditionalServiceDTO;
import isa.FishingBookingApp.dto.AvailableAppointmentDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.AdditionalService;
import isa.FishingBookingApp.model.AvailableAppointment;
import isa.FishingBookingApp.model.Reservation;

import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.repository.AdditionalServiceRepository;
import isa.FishingBookingApp.repository.AvailableAppointmentRepository;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.repository.ReservationRepository;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import isa.FishingBookingApp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReservationEntitiesServiceImpl implements ReservationEntitiesService {
    private ReservationEntitiesRepository reservationEntitiesRepository;
    private AdditionalServiceRepository additionalServiceRepository;
    private AvailableAppointmentRepository availableAppointmentRepository;
    private ReservationRepository reservationRepository;
    private ReviewService reviewService;

    @Autowired
    public ReservationEntitiesServiceImpl(ReservationEntitiesRepository reservationEntitiesRepository, AdditionalServiceRepository additionalServiceRepository, AvailableAppointmentRepository availableAppointmentRepository, ReservationRepository reservationRepository, ReviewService reviewService) {
        this.reservationEntitiesRepository = reservationEntitiesRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.availableAppointmentRepository = availableAppointmentRepository;
        this.reservationRepository = reservationRepository;
        this.reviewService = reviewService;
    }

    @Override
    public List<ReservationEntities> getAll() {
        return reservationEntitiesRepository.findAll();
    }

    @Override
    public ReservationEntities get(Long id) {
        return reservationEntitiesRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<AdditionalService> getAdditionalServices(Long id) {
        return additionalServiceRepository.findAdditionalServiceByReservationEntityId(id);
    }

    @Override
    public List<AvailableAppointment> getAvailableAppointmentsOfEntity(Long id) {
        return availableAppointmentRepository.findByEntityId(id);
    }

    @Override
    public AdditionalService createAdditionalService(AdditionalServiceDTO additionalServiceDTO) {
        ReservationEntities reservationEntity = reservationEntitiesRepository.findById(additionalServiceDTO.getReservationEntityId()).orElse(null);
        if (reservationEntity == null) return null;
        AdditionalService additionalService = new AdditionalService(reservationEntity, additionalServiceDTO.getName(), additionalServiceDTO.getDescription(), additionalServiceDTO.getPrice());
        return additionalServiceRepository.save(additionalService);
    }

    @Override
    public AvailableAppointment createAvailableAppointment(AvailableAppointmentDTO availableAppointmentDTO) {
        ReservationEntities reservationEntity = reservationEntitiesRepository.findById(availableAppointmentDTO.getEntityId()).orElse(null);
        availableAppointmentDTO.setDateTimesFromStrings();
        if (reservationEntity == null || availableAppointmentDTO.getFromTime() == null || availableAppointmentDTO.getToTime() == null
                || overlapsWithOtherEntityAppointments(reservationEntity.getId(), availableAppointmentDTO))
            return null;
        AvailableAppointment availableAppointment = new AvailableAppointment(reservationEntity, availableAppointmentDTO.getFromTime(), availableAppointmentDTO.getToTime());
        return availableAppointmentRepository.save(availableAppointment);
    }

    private boolean overlapsWithOtherEntityAppointments(Long id, AvailableAppointmentDTO newAvailableAppointment) {
        LocalDateTime newStart = newAvailableAppointment.getFromTime();
        LocalDateTime newEnd = newAvailableAppointment.getToTime();

        List<AvailableAppointment> appointmentsOfEntity = availableAppointmentRepository.findByEntityId(id);
        if (appointmentsOfEntity.size() == 0) return false;

        for (AvailableAppointment appointment : appointmentsOfEntity) {
            LocalDateTime appointmentStart = appointment.getFromTime();
            LocalDateTime appointmentEnd = appointment.getToTime();
            if ((newStart.isBefore(appointmentStart) && newEnd.isBefore(appointmentStart)) || (newStart.isAfter(appointmentEnd) && newEnd.isAfter(appointmentEnd))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<ReservationEntities> searchFilterSort(SearchFilterSort searchFilterSort) {
        List<ReservationEntities> reservationEntities;
        if (searchFilterSort.getTypes().size() != 0) {
            reservationEntities = filter(searchFilterSort);
        } else {
            reservationEntities = getAll();
        }
        sort(searchFilterSort, reservationEntities);
        List<ReservationEntities> usersRetVal = search(searchFilterSort, reservationEntities);
        return usersRetVal;
    }

    @Override
    public Set<ReservationEntities> getPossibleReservationEntitiesForComplaint(String mailAddress) {
        List<Reservation> reservations = reservationRepository.findReservationByUserMailAddressAndStartLessThanAndDeletedEquals(mailAddress, LocalDateTime.now(), false);
        Set<ReservationEntities> retVal = new HashSet<>();
        for (Reservation reservation : reservations) {
            retVal.add(reservation.getReservationEntity());
        }
        return retVal;
    }

    private List<ReservationEntities> search(SearchFilterSort searchFilterSort, List<ReservationEntities> reservationEntities) {
        List<ReservationEntities> reservationEntitiesRetVal = new ArrayList<ReservationEntities>();

        if (searchFilterSort.getSearch().equals("")) {
            reservationEntitiesRetVal = reservationEntities;
        } else {

            for (ReservationEntities reservationEntity : reservationEntities) {
                if (reservationEntity.getName().toLowerCase().contains(searchFilterSort.getSearch().toLowerCase())
                        || reservationEntity.getAddress().getCity().toLowerCase().contains(searchFilterSort.getSearch().toLowerCase())
                        || reservationEntity.getAddress().getStreet().toLowerCase().contains(searchFilterSort.getSearch().toLowerCase())
                        || reservationEntity.getAddress().getCountry().toLowerCase().contains(searchFilterSort.getSearch().toLowerCase())) {
                    reservationEntitiesRetVal.add(reservationEntity);
                }
            }
        }

        return reservationEntitiesRetVal;
    }

    private List<ReservationEntities> filter(SearchFilterSort searchFilterSort) {
        List<ReservationEntities> retVal = new ArrayList<>();
        for (ReservationEntities reservationEntities : getAll()) {

            if (searchFilterSort.getTypes().size() == 0) {
                retVal.add(reservationEntities);
            } else if (searchFilterSort.getTypes().contains(reservationEntities.getType())) {
                retVal.add(reservationEntities);
            }
        }
        return retVal;
    }

    private void sort(SearchFilterSort searchFilterSort, List<ReservationEntities> reservationEntities) {
        switch (searchFilterSort.getSort()) {
            case "na":
                Collections.sort(reservationEntities, (re1, re2) -> re1.getName().compareToIgnoreCase(re2.getName()));
                break;
            case "nd":
                Collections.sort(reservationEntities, (re1, re2) -> re2.getName().compareToIgnoreCase(re1.getName()));
                break;
            case "la":
                Collections.sort(reservationEntities, (re1, re2) -> re1.getAddress().getCity()
                        .compareToIgnoreCase(re2.getAddress().getCity()));
                break;
            case "ld":
                Collections.sort(reservationEntities, (re1, re2) -> re2.getAddress().getCity()
                        .compareToIgnoreCase(re1.getAddress().getCity()));
                break;
            case "ra":
                Collections.sort(reservationEntities, (re1, re2) -> Double.compare(reviewService.getAvgMarksForEntity(re1.getId())
                        , (reviewService.getAvgMarksForEntity(re2.getId()))));
                break;
            case "rd":
                Collections.sort(reservationEntities, (re1, re2) -> Double.compare(reviewService.getAvgMarksForEntity(re2.getId())
                        , (reviewService.getAvgMarksForEntity(re1.getId()))));
                break;

            default:
                break;
        }
    }
}
