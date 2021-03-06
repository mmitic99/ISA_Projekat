package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.ReservationDTO;
import isa.FishingBookingApp.dto.ReservationForClientDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.repository.*;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import isa.FishingBookingApp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private EmailService emailService;
    private ReservationEntitiesService reservationEntitiesService;


    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, SpecialReservationRepository specialReservationRepository, AvailableAppointmentRepository availableAppointmentRepository, ReservationEntitiesRepository reservationEntitiesRepository, UserRepository userRepository, AdditionalServiceRepository additionalServiceRepository, EmailService emailService, ReservationEntitiesService reservationEntitiesService) {
        this.reservationRepository = reservationRepository;
        this.specialReservationRepository = specialReservationRepository;
        this.availableAppointmentRepository = availableAppointmentRepository;
        this.reservationEntitiesRepository = reservationEntitiesRepository;
        this.userRepository = userRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.emailService = emailService;
        this.reservationEntitiesService = reservationEntitiesService;
    }

    @Override
    public List<ReservationEntities> checkReservationEntitiesIsAvailable(List<ReservationEntities> reservationEntities, SearchFilterSort searchFilterSort) {
        List<ReservationEntities> retVal = new ArrayList<>();

        for (ReservationEntities reservationEntity : reservationEntities) {
            if (reservationEntityIsAvailable(reservationEntity, searchFilterSort.getDateTime(), searchFilterSort.getDays())) {
                retVal.add(reservationEntity);
            }
        }

        return retVal;
    }

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public Reservation reserveEntity(ReservationDTO reservationDTO) throws Exception {
        ReservationEntities reservationEntities = reservationEntitiesRepository.findReservationEntitiesByIdTransactional(reservationDTO.getReservationEntitiesId());
        User user = userRepository.findByMailAddress(reservationDTO.getMailAddress());
        Reservation reservation = new Reservation(user, reservationEntities, reservationDTO.getDateTime(), reservationDTO.getDays() * 24.0, reservationDTO.getGuests(), 0);

        if (!reservationEntityIsAvailable(reservationEntities, reservationDTO.getDateTime(), reservationDTO.getDays())) {
            throw new Exception("Entitet koji ??elite da rezervi??ete nije dostupan za uneto vreme.");
        } else {
            reserveAdditionalServices(reservation, reservationDTO);
            addPriceToReservation(reservation);
            reservationRepository.save(reservation);
            emailService.sendReservationInfo(reservation, false);
            return reservation;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Reservation reserveEntityForClient(ReservationForClientDTO reservationForClientDTO) throws Exception {
        Reservation currentReservation = reservationEntitiesService.getCurrentReservationOfEntity(reservationForClientDTO.getReservationEntityId());
        if (currentReservation == null || !currentReservation.getUser().getMailAddress().equals(reservationForClientDTO.getUserMailAddress())) return null;
        ReservationDTO reservationDTO = new ReservationDTO(reservationForClientDTO.getUserMailAddress(), reservationForClientDTO.getDays(), reservationForClientDTO.getMaxPeople(), reservationForClientDTO.getStartDate(), reservationForClientDTO.getStartTime(), reservationForClientDTO.getStartDateTime(), reservationForClientDTO.getReservationEntityId(), reservationForClientDTO.getAdditionalServicesId());
        return reserveEntity(reservationDTO);
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
            throw new Exception("Rezervaciju je mogu??e otkazati 3 dana ranije");
        } else {
            reservation.setDeleted(true);
            return reservationRepository.save(reservation);
        }
    }

    @Override
    public List<Reservation> getAllReservationsOfEntity(Long entityId) {
        return reservationRepository.findAllByReservationEntityId(entityId);
    }

    @Override
    public List<Reservation> getAllOldReservation(String mailAddress) {
        return reservationRepository.findReservationByUserMailAddressAndStartLessThanAndDeletedEquals(mailAddress, LocalDateTime.now(), false);
    }

    @Override
    public List<Reservation> searchFilterSort(SearchFilterSort searchFilterSort) {
        List<Reservation> reservations;
        if (searchFilterSort.getTypes().size() != 0) {
            reservations = filter(searchFilterSort);
        } else {
            if (searchFilterSort.isOldReservation()) {
                reservations = getAllOldReservation(searchFilterSort.getMailAddress());
            } else {
                reservations = getCurrentReservationForUser(searchFilterSort.getMailAddress());
            }
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
        List<Reservation> reservations;
        if (searchFilterSort.isOldReservation()) {
            reservations = getAllOldReservation(searchFilterSort.getMailAddress());
        } else {
            reservations = getCurrentReservationForUser(searchFilterSort.getMailAddress());
        }
        for (Reservation reservation : reservations) {

            if (searchFilterSort.getTypes().isEmpty()) {
                retVal.add(reservation);
            } else if (searchFilterSort.getTypes().contains(reservation.getReservationEntity().getType())) {
                retVal.add(reservation);
            }
        }
        return retVal;
    }

    private void addPriceToReservation(Reservation reservation) {
        reservation.setPrice(reservation.getReservationEntity().getPrice() * reservation.getDurationInHours() / 24);
        for (AdditionalService additionalService : reservation.getAdditionalServices()) {
            reservation.setPrice(reservation.getPrice() + additionalService.getPrice() * reservation.getDurationInHours() / 24);
        }
    }

    private void reserveAdditionalServices(Reservation reservation, ReservationDTO reservationDTO) throws Exception {
        for (Long id : reservationDTO.getAdditionalServicesId()) {
            AdditionalService additionalService = additionalServiceRepository.findAdditionalServiceById(id);
            if (additionalService != null) {
                reservation.getAdditionalServices().add(additionalService);
            } else {
                throw new Exception("Dodatni servis nije poznat");
            }
        }
    }

    public boolean reservationEntityIsAvailable(ReservationEntities reservationEntity, LocalDateTime start, int days) {
        return dateTimeNotInReservations(reservationEntity, start, days) && dateTimeInAvailableAppointment(reservationEntity, start, days)
                && dateTimeNotInSpecialReservations(reservationEntity, start, days);
    }

    private boolean dateTimeNotInSpecialReservations(ReservationEntities reservationEntity, LocalDateTime start, int days) {
        List<SpecialReservation> reservations = specialReservationRepository.findByReservationEntityId(reservationEntity.getId());

        LocalDateTime end = start.plusDays(days);

        for (SpecialReservation reservation : reservations) {
            LocalDateTime startReservation = reservation.getStart();
            LocalDateTime endReservation = startReservation.plusHours((long)reservation.getDurationInHours());
            if ((start.isBefore(startReservation) && end.isBefore(startReservation)) || (start.isAfter(endReservation) && end.isAfter(endReservation))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean dateTimeNotInReservations(ReservationEntities reservationEntity, LocalDateTime start, int days) {
        List<Reservation> reservations = reservationRepository.findByReservationEntityIdAndDeletedEquals(reservationEntity.getId(), false);

        LocalDateTime end = start.plusDays(days);

        for (Reservation reservation : reservations) {
            LocalDateTime startReservation = reservation.getStart();
            LocalDateTime endReservation = startReservation.plusHours((long)reservation.getDurationInHours());
            if ((start.isBefore(startReservation) && end.isBefore(startReservation)) || (start.isAfter(endReservation) && end.isAfter(endReservation))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean dateTimeInAvailableAppointment(ReservationEntities reservationEntity, LocalDateTime start, int days) {
        List<AvailableAppointment> availableAppointments = availableAppointmentRepository.findByEntityId(reservationEntity.getId());

        LocalDateTime end = start.plusDays(days);

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
