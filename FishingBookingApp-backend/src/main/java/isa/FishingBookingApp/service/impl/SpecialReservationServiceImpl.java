package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.ReservationDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.dto.SpecialReservationDTO;
import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.repository.AdditionalServiceRepository;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.repository.SpecialReservationRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.service.ReservationService;
import isa.FishingBookingApp.service.SpecialReservationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SpecialReservationServiceImpl implements SpecialReservationService {
    private ReservationEntitiesRepository reservationEntitiesRepository;
    private SpecialReservationRepository specialReservationRepository;
    private UserRepository userRepository;
    private AdditionalServiceRepository additionalServiceRepository;
    private ReservationService reservationService;

    public SpecialReservationServiceImpl(ReservationEntitiesRepository reservationEntitiesRepository, SpecialReservationRepository specialReservationRepository, UserRepository userRepository, AdditionalServiceRepository additionalServiceRepository, ReservationService reservationService) {
        this.reservationEntitiesRepository = reservationEntitiesRepository;
        this.specialReservationRepository = specialReservationRepository;
        this.userRepository = userRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.reservationService = reservationService;
    }


    @Override
    public SpecialReservation createSpecialReservation(SpecialReservationDTO specialReservationDTO) throws Exception {
        ReservationEntities reservationEntity = reservationEntitiesRepository.findReservationEntitiesById(specialReservationDTO.getReservationEntityId());
        User user = userRepository.findByMailAddress(specialReservationDTO.getMailAddress());
        Set<AdditionalService> additionalServices = getAdditionalServices(specialReservationDTO.getAdditionalServicesId(), reservationEntity.getId());
        SpecialReservation specialReservation = new SpecialReservation(user, reservationEntity, specialReservationDTO.getStartDateTime(), specialReservationDTO.getDays() * 24, specialReservationDTO.getMaxPeople(), additionalServices, specialReservationDTO.getPrice(), specialReservationDTO.getValidFromDateTime(), specialReservationDTO.getValidToDateTime());

        // provera za 4.4
        if (!reservationService.reservationEntityIsAvailable(reservationEntity, specialReservationDTO.getStartDateTime(), specialReservationDTO.getDays())) {
            throw new Exception("Termin koji želite da zakažete je zauzet.");
        } else {
            specialReservationRepository.save(specialReservation);
            // TODO: poslati mejl
            return specialReservation;
        }
    }

    private Set<AdditionalService> getAdditionalServices(List<Long> additionalServicesIds, Long entityId) {
        List<AdditionalService> additionalServices = additionalServiceRepository.findAllById(additionalServicesIds);
        Set<AdditionalService> validAdditionalServices = new HashSet<>();

        for (AdditionalService additionalService : additionalServices) {
            if (additionalService.getReservationEntity().getId().equals(entityId)) {
                validAdditionalServices.add(additionalService);
            }
        }

        return validAdditionalServices;
    }
}
