package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.IncomeFromEntityDTO;
import isa.FishingBookingApp.dto.TimeRangeDTO;
import isa.FishingBookingApp.model.Boat;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.repository.BoatRepository;
import isa.FishingBookingApp.repository.CottageRepository;
import isa.FishingBookingApp.repository.ReservationRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.service.BusinessReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessReportServiceImpl implements BusinessReportService {
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private CottageRepository cottageRepository;
    private BoatRepository boatRepository;

    @Autowired
    public BusinessReportServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, CottageRepository cottageRepository, BoatRepository boatRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.cottageRepository = cottageRepository;
        this.boatRepository = boatRepository;
    }

    @Override
    public List<IncomeFromEntityDTO> getIncomeReportForOwnerInTimeRange(Long ownerId, TimeRangeDTO timeRangeDTO) {
        List<IncomeFromEntityDTO> retVal;
        User user = userRepository.findUserById(ownerId);
        String userRole = user.getRole().getName();

        if (userRole.equals("ROLE_cottageOwner")) {
            retVal = getIncomesFromCottages(cottageRepository.getAllCottagesOfUser(ownerId), timeRangeDTO);
        }
        else if (userRole.equals("ROLE_boatOwner")) {
            retVal = getIncomesFromBoats(boatRepository.getAllBoatsOfUser(ownerId), timeRangeDTO);
        }
        else {
            return null;
        }

        return retVal;
    }

    private List<IncomeFromEntityDTO> getIncomesFromCottages(List<Cottage> cottages, TimeRangeDTO timeRangeDTO) {
        List<IncomeFromEntityDTO> incomePerEntity = new ArrayList<>();
        for (Cottage cottage : cottages) {
            List<Reservation> allOldReservations = reservationRepository.findAllByReservationEntityIdAndStartLessThanAndDeletedEquals(cottage.getId(), LocalDateTime.now(), false);
            List<Reservation> filteredReservations = filterReservationsWithTimeRange(allOldReservations, timeRangeDTO);
            double income = calculateIncomeFromReservations(filteredReservations);
            int numberOfReservations = filteredReservations.size();
            incomePerEntity.add(new IncomeFromEntityDTO(cottage.getId(), income, numberOfReservations));
        }

        return incomePerEntity;
    }

    private List<IncomeFromEntityDTO> getIncomesFromBoats(List<Boat> boats, TimeRangeDTO timeRangeDTO) {
        List<IncomeFromEntityDTO> incomePerEntity = new ArrayList<>();
        for (Boat boat : boats) {
            List<Reservation> allOldReservations = reservationRepository.findAllByReservationEntityIdAndStartLessThanAndDeletedEquals(boat.getId(), LocalDateTime.now(), false);
            List<Reservation> filteredReservations = filterReservationsWithTimeRange(allOldReservations, timeRangeDTO);
            double income = calculateIncomeFromReservations(filteredReservations);
            int numberOfReservations = filteredReservations.size();
            incomePerEntity.add(new IncomeFromEntityDTO(boat.getId(), income, numberOfReservations));
        }

        return incomePerEntity;
    }

    private List<Reservation> filterReservationsWithTimeRange(List<Reservation> reservations, TimeRangeDTO timeRangeDTO) {
        List<Reservation> filteredReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            LocalDateTime startReservation = reservation.getStart();
            LocalDateTime endReservation = startReservation.plusHours((long)reservation.getDurationInHours());
            if (endReservation.isAfter(timeRangeDTO.getStartDateTime()) && startReservation.isBefore(timeRangeDTO.getEndDateTime())) {
                filteredReservations.add(reservation);
            }
        }

        return filteredReservations;
    }

    public double calculateIncomeFromReservations(List<Reservation> reservations) {
        double income = 0;
        for (Reservation reservation : reservations) {
            income += reservation.getPrice();
        }

        return income;
    }
}
