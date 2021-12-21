package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.ReservationDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.repository.*;
import isa.FishingBookingApp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private SpecialReservationRepository specialReservationRepository;
    private AvailableAppointmentRepository availableAppointmentRepository;
    private ReservationEntitiesRepository reservationEntitiesRepository;
    private UserRepository userRepository;
    private AdditionalServiceRepository additionalServiceRepository;
    private AdditionalServiceReservationRepository additionalServiceReservationRepository;
    private EmailService emailService;


    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, SpecialReservationRepository specialReservationRepository, AvailableAppointmentRepository availableAppointmentRepository, ReservationEntitiesRepository reservationEntitiesRepository, UserRepository userRepository, AdditionalServiceRepository additionalServiceRepository, AdditionalServiceReservationRepository additionalServiceReservationRepository, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.specialReservationRepository = specialReservationRepository;
        this.availableAppointmentRepository = availableAppointmentRepository;
        this.reservationEntitiesRepository = reservationEntitiesRepository;
        this.userRepository = userRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.additionalServiceReservationRepository = additionalServiceReservationRepository;
        this.emailService = emailService;
    }

    @Override
    public List<ReservationEntities> checkIsReservationEntitiesIsAvailable(List<ReservationEntities> reservationEntities, SearchFilterSort searchFilterSort) {
        List<ReservationEntities> retVal = new ArrayList<>();

        for (ReservationEntities reservationEntity : reservationEntities) {
            if (reservationEntityIsAvailable(reservationEntity, searchFilterSort)) {
                retVal.add(reservationEntity);
            }
        }

        return retVal;
    }

    @Override
    public Reservation reserveEntity(ReservationDTO reservationDTO) throws Exception {
        ReservationEntities reservationEntities = reservationEntitiesRepository.findReservationEntitiesById(reservationDTO.getReservationEntitiesId());
        User user = userRepository.findByMailAddress(reservationDTO.getMailAddress());
        Reservation reservation = new Reservation(user, reservationEntities, reservationDTO.getDateTime(), reservationDTO.getDays() * 24, reservationDTO.getGuests(), 0);

        // provera za 4.4
        if (!reservationEntityIsAvailable(reservationEntities, new SearchFilterSort(reservationDTO.getMailAddress(), reservationDTO.getDays(), reservationDTO.getGuests(), reservationDTO.getDateTime()))) {
            throw new Exception("Neko je rezervisao pre vas, pokušajte sa drugim entitetom");
        } else {
            reservationRepository.save(reservation);
            List<AdditionalService> additionalServices = reserveAdditionalServices(reservation, reservationDTO);
            addPriceToReservation(reservation, additionalServices);
            reservationRepository.save(reservation);
            emailService.sendReservationInfo(reservation, additionalServices);
            return reservation;
        }
    }

    @Override
    public List<Reservation> getCurrentReservationForUser(String mailAddress) {
        return reservationRepository.findReservationByUserMailAddressAndStartGreaterThanEqualAndDeletedEquals(mailAddress, LocalDateTime.now(), false);
    }

    @Override
    public Reservation cancelReservation(Long id) throws Exception {
        Reservation reservation = reservationRepository.findReservationById(id);
        LocalDateTime dateTime = LocalDateTime.now().plusDays(3);
        if (reservation == null) {
            throw new Exception("Nepostojeca rezervacija");
        } else if (!dateTime.isBefore(reservation.getStart())) {
            throw new Exception("Rezervaciju je moguće otkazati 3 dana ranije");
        } else {
            reservation.setDeleted(true);
            return reservationRepository.save(reservation);
        }
    }

    @Override
    public List<Reservation> getAllOldReservation(String mailAddress) {
        List<Reservation> retval = reservationRepository.findReservationByUserMailAddressAndStartLessThanAndDeletedEquals(mailAddress, LocalDateTime.now(), false);
        return retval;
    }

    @Override
    public List<Reservation> searchFilterSort(SearchFilterSort searchFilterSort) {
        List<Reservation> reservations;
        if (searchFilterSort.getTypes().size() != 0) {
            reservations = filter(searchFilterSort);
        } else {
            reservations = getAllOldReservation(searchFilterSort.getMailAddress());
        }
        sort(searchFilterSort, reservations);
        return reservations;
    }

    private void sort(SearchFilterSort searchFilterSort, List<Reservation> reservations) {
        switch (searchFilterSort.getSort()) {
            case "da":
                Collections.sort(reservations, (re1, re2) -> re1.getStart().compareTo(re2.getStart()));
                break;
            case "dd":
                Collections.sort(reservations, (re1, re2) -> re2.getStart().compareTo(re1.getStart()));
                break;
            case "pa":
                Collections.sort(reservations, (re1, re2) -> Double.compare(re1.getPrice(), re2.getPrice()));
                break;
            case "pd":
                Collections.sort(reservations, (re1, re2) -> Double.compare(re2.getPrice(), re1.getPrice()));
                break;
            case "dua":
                Collections.sort(reservations, (re1, re2) -> Double.compare(re1.getDurationInHours(), re2.getDurationInHours()));
                break;
            case "dud":
                Collections.sort(reservations, (re1, re2) -> Double.compare(re2.getDurationInHours(), re1.getDurationInHours()));
                break;

            default:
                break;
        }
    }

    private List<Reservation> filter(SearchFilterSort searchFilterSort) {
        List<Reservation> retVal = new ArrayList<>();
        for (Reservation reservation : getAllOldReservation(searchFilterSort.getMailAddress())) {

            if (searchFilterSort.getTypes().size() == 0) {
                retVal.add(reservation);
            } else if (searchFilterSort.getTypes().contains(reservation.getReservationEntity().getType())) {
                retVal.add(reservation);
            }
        }
        return retVal;
    }

    private void addPriceToReservation(Reservation reservation, List<AdditionalService> additionalServices) {
        reservation.setPrice(reservation.getReservationEntity().getPrice() * reservation.getDurationInHours() / 24);
        for (AdditionalService additionalService : additionalServices) {
            reservation.setPrice(reservation.getPrice() + additionalService.getPrice() * reservation.getDurationInHours() / 24);
        }
    }

    private List<AdditionalService> reserveAdditionalServices(Reservation reservation, ReservationDTO reservationDTO) throws Exception {
        List<AdditionalService> retVal = new ArrayList<>();
        for (Long id : reservationDTO.getAdditionalServicesId()) {
            AdditionalService additionalService = additionalServiceRepository.findAdditionalServiceById(id);
            if (additionalService != null) {
                additionalServiceReservationRepository.save(new AdditionalServiceReservation(additionalService, reservation));
                retVal.add(additionalService);
            } else {
                throw new Exception("Dodatni servis nije poznat");
            }
        }
        return retVal;
    }

    private boolean reservationEntityIsAvailable(ReservationEntities reservationEntity, SearchFilterSort searchFilterSort) {
        return DateTimeNotInReservations(reservationEntity, searchFilterSort) && DateTimeInAvailableAppointment(reservationEntity, searchFilterSort)
                && DateTimeNotInSpecialReservations(reservationEntity, searchFilterSort);
    }

    private boolean DateTimeNotInSpecialReservations(ReservationEntities reservationEntity, SearchFilterSort searchFilterSort) {
        List<SpecialReservation> reservations = specialReservationRepository.findByReservationEntityId(reservationEntity.getId());

        LocalDateTime start = searchFilterSort.getDateTime();
        LocalDateTime end = start.plusDays(searchFilterSort.getDays());

        for (SpecialReservation reservation : reservations) {
            LocalDateTime startReservation = reservation.getStart();
            LocalDateTime endReservation = startReservation.plusHours(Double.valueOf(reservation.getDurationInHours()).longValue());
            if ((start.isBefore(startReservation) && end.isBefore(startReservation)) || (start.isAfter(endReservation) && end.isAfter(endReservation))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean DateTimeNotInReservations(ReservationEntities reservationEntity, SearchFilterSort searchFilterSort) {
        List<Reservation> reservations = reservationRepository.findByReservationEntityIdAndDeletedEquals(reservationEntity.getId(), false);

        LocalDateTime start = searchFilterSort.getDateTime();
        LocalDateTime end = start.plusDays(searchFilterSort.getDays());

        for (Reservation reservation : reservations) {
            LocalDateTime startReservation = reservation.getStart();
            LocalDateTime endReservation = startReservation.plusHours(Double.valueOf(reservation.getDurationInHours()).longValue());
            if ((start.isBefore(startReservation) && end.isBefore(startReservation)) || (start.isAfter(endReservation) && end.isAfter(endReservation))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean DateTimeInAvailableAppointment(ReservationEntities reservationEntity, SearchFilterSort searchFilterSort) {
        List<AvailableAppointment> availableAppointments = availableAppointmentRepository.findByEntityId(reservationEntity.getId());

        LocalDateTime start = searchFilterSort.getDateTime();
        LocalDateTime end = start.plusDays(searchFilterSort.getDays());

        for (AvailableAppointment availableAppointment : availableAppointments) {
            LocalDateTime startAvailableAppointment = availableAppointment.getFromTime();
            LocalDateTime endAvailableAppointment = availableAppointment.getToTime();

            if ((startAvailableAppointment.isBefore(start) || startAvailableAppointment.isEqual(start))
                    && (endAvailableAppointment.isAfter(end) || endAvailableAppointment.isEqual(end))) {
                return true;
            }
        }
        return false;
    }
}
