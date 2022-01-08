package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.SpecialReservationDTO;
import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.repository.*;
import isa.FishingBookingApp.service.ReservationService;
import isa.FishingBookingApp.service.SpecialReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SpecialReservationServiceImpl implements SpecialReservationService {
    private ReservationEntitiesRepository reservationEntitiesRepository;
    private SpecialReservationRepository specialReservationRepository;
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private AdditionalServiceRepository additionalServiceRepository;
    private ReservationService reservationService;
    private EmailService emailService;

    public SpecialReservationServiceImpl(ReservationEntitiesRepository reservationEntitiesRepository, SpecialReservationRepository specialReservationRepository, ReservationRepository reservationRepository, UserRepository userRepository, AdditionalServiceRepository additionalServiceRepository, ReservationService reservationService, EmailService emailService) {
        this.reservationEntitiesRepository = reservationEntitiesRepository;
        this.specialReservationRepository = specialReservationRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.reservationService = reservationService;
        this.emailService = emailService;
    }


    @Override
    @Transactional(readOnly = false)
    public SpecialReservation createSpecialReservation(SpecialReservationDTO specialReservationDTO) throws Exception {
        ReservationEntities reservationEntity = reservationEntitiesRepository.findReservationEntitiesByIdTransactional(specialReservationDTO.getReservationEntityId());
        User user = userRepository.findByMailAddress(specialReservationDTO.getMailAddress());
        Set<AdditionalService> additionalServices = getAdditionalServices(specialReservationDTO.getAdditionalServicesId(), reservationEntity.getId());
        SpecialReservation specialReservation = new SpecialReservation(user, reservationEntity, specialReservationDTO.getStartDateTime(), specialReservationDTO.getDays() * 24.0, specialReservationDTO.getMaxPeople(), additionalServices, specialReservationDTO.getPrice(), specialReservationDTO.getValidFromDateTime(), specialReservationDTO.getValidToDateTime());

        if (!reservationService.reservationEntityIsAvailable(reservationEntity, specialReservationDTO.getStartDateTime(), specialReservationDTO.getDays())) {
            throw new Exception("Termin koji želite da zakažete nije dostupan.");
        } else {
            specialReservationRepository.save(specialReservation);
            emailService.sendCreatedActionInfo(specialReservation);
            return specialReservation;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Reservation takeSpecialReservation(Long id, String mailAddress) throws Exception {
        SpecialReservation specialReservation = specialReservationRepository.findSpecialReservationsById(id);
        if (specialReservation == null) throw new Exception("Akcija koju želite da rezervišete ne postoji ili ju je neko već rezervisao.");

        User user = userRepository.findByMailAddress(mailAddress);
        if (user == null) throw new Exception("Nepostojeći korisnik.");

        Reservation reservation = new Reservation(user, specialReservation.getReservationEntity(), specialReservation.getStart(), specialReservation.getDurationInHours(), specialReservation.getMaxPeople(), specialReservation.getPrice(), specialReservation.getAdditionalServices());
        Reservation createdReservation = reservationRepository.save(reservation);
        specialReservationRepository.delete(specialReservation);
        emailService.sendReservationInfo(createdReservation, true);

        return createdReservation;
    }

    @Override
    public List<SpecialReservation> getAllSpecialReservationByEntityId(Long id) {
        return specialReservationRepository.findByReservationEntityIdAndValidFromBeforeAndValidToAfter(id, LocalDateTime.now(), LocalDateTime.now());
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
