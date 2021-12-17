package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.AvailableAppointment;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.SpecialReservation;
import isa.FishingBookingApp.repository.AvailableAppointmentRepository;
import isa.FishingBookingApp.repository.ReservationRepository;
import isa.FishingBookingApp.repository.SpecialReservationRepository;
import isa.FishingBookingApp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private SpecialReservationRepository specialReservationRepository;
    private AvailableAppointmentRepository availableAppointmentRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, AvailableAppointmentRepository availableAppointmentRepository, SpecialReservationRepository specialReservationRepository) {
        this.reservationRepository = reservationRepository;
        this.specialReservationRepository = specialReservationRepository;
        this.availableAppointmentRepository = availableAppointmentRepository;
    }

    @Override
    public List<ReservationEntities> checkIsReservationEntitiesIsAvailable(List<ReservationEntities> reservationEntities, SearchFilterSort searchFilterSort) {
        List<ReservationEntities> retVal = new ArrayList<>();

        for (ReservationEntities reservationEntity : reservationEntities) {
            if(reservationEntityIsAvailable(reservationEntity, searchFilterSort)){
                retVal.add(reservationEntity);
            }
        }

        return retVal;
    }

    private boolean reservationEntityIsAvailable(ReservationEntities reservationEntity, SearchFilterSort searchFilterSort) {
        return DateTimeNotInReservations(reservationEntity, searchFilterSort) && DateTimeInAvailableAppointment(reservationEntity, searchFilterSort)
                && DateTimeNotInSpecialReservations(reservationEntity, searchFilterSort);
    }

    private boolean DateTimeNotInSpecialReservations(ReservationEntities reservationEntity, SearchFilterSort searchFilterSort){
        List<SpecialReservation> reservations = specialReservationRepository.findByReservationEntityId(reservationEntity.getId());

        LocalDateTime start = searchFilterSort.getDateTime();
        LocalDateTime end = start.plusDays(searchFilterSort.getDays());

        for (SpecialReservation reservation : reservations) {
            LocalDateTime startReservation = reservation.getStart();
            LocalDateTime endReservation = startReservation.plusHours(Double.valueOf(reservation.getDurationInHours()).longValue());
            if((start.isBefore(startReservation) && end.isBefore(startReservation)) || (start.isAfter(endReservation) && end.isAfter(endReservation))){
                continue;
            }
            else{
                return false;
            }
        }
        return true;
    }
    private boolean DateTimeNotInReservations(ReservationEntities reservationEntity, SearchFilterSort searchFilterSort){
        List<Reservation> reservations = reservationRepository.findByReservationEntityId(reservationEntity.getId());

        LocalDateTime start = searchFilterSort.getDateTime();
        LocalDateTime end = start.plusDays(searchFilterSort.getDays());

        for (Reservation reservation : reservations) {
            LocalDateTime startReservation = reservation.getStart();
            LocalDateTime endReservation = startReservation.plusHours(Double.valueOf(reservation.getDurationInHours()).longValue());
            if((start.isBefore(startReservation) && end.isBefore(startReservation)) || (start.isAfter(endReservation) && end.isAfter(endReservation))){
                continue;
            }
            else{
                return false;
            }
        }
        return true;
    }

    private boolean DateTimeInAvailableAppointment(ReservationEntities reservationEntity, SearchFilterSort searchFilterSort){
        List<AvailableAppointment> availableAppointments = availableAppointmentRepository.findByEntityId(reservationEntity.getId());

        LocalDateTime start = searchFilterSort.getDateTime();
        LocalDateTime end = start.plusDays(searchFilterSort.getDays());

        for (AvailableAppointment availableAppointment : availableAppointments) {
            LocalDateTime startAvailableAppointment = availableAppointment.getFromTime();
            LocalDateTime endAvailableAppointment = availableAppointment.getToTime();

            if((startAvailableAppointment.isBefore(start) || startAvailableAppointment.isEqual(start))
                    && (endAvailableAppointment.isAfter(end) || endAvailableAppointment.isEqual(end))){
                return true;
            }
        }
        return false;
    }
}
