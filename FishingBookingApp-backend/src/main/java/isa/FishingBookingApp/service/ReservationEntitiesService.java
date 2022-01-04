package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.AdditionalServiceDTO;
import isa.FishingBookingApp.dto.AvailableAppointmentDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.AdditionalService;
import isa.FishingBookingApp.model.AvailableAppointment;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.ReservationEntities;

import java.util.List;
import java.util.Set;

public interface ReservationEntitiesService {
    List<ReservationEntities> getAll();

   ReservationEntities get(Long id);

    List<AdditionalService> getAdditionalServices(Long id);

    List<AvailableAppointment> getAvailableAppointmentsOfEntity(Long id);

    Reservation getCurrentReservationOfEntity(Long id);

    AdditionalService createAdditionalService(AdditionalServiceDTO additionalServiceDTO);

    AvailableAppointment createAvailableAppointment(AvailableAppointmentDTO availableAppointmentDTO);

    List<ReservationEntities> searchFilterSort(SearchFilterSort searchFilterSort);

    Set<ReservationEntities> getPossibleReservationEntitiesForComplaint(String mailAddress);

    boolean isReservationEntityHavingFutureReservations(Long entityId);
}
