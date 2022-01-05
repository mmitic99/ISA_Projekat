package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.repository.*;
import isa.FishingBookingApp.service.CottageService;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class CottageServiceImpl implements CottageService {

    private CottageRepository cottageRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private AvailableAppointmentRepository availableAppointmentRepository;
    private EntityImageRepository entityImageRepository;
    private SubscriptionRepository subscriptionRepository;
    private SpecialReservationRepository specialReservationRepository;
    private ReservationEntitiesService reservationEntitiesService;
    private ReservationEntitiesRepository reservationEntitiesRepository;

    @Autowired
    public CottageServiceImpl(CottageRepository cottageRepository, UserRepository userRepository, AddressRepository addressRepository, AvailableAppointmentRepository availableAppointmentRepository, EntityImageRepository entityImageRepository, SubscriptionRepository subscriptionRepository, SpecialReservationRepository specialReservationRepository, ReservationEntitiesService reservationEntitiesService, ReservationEntitiesRepository reservationEntitiesRepository) {
        this.cottageRepository = cottageRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.availableAppointmentRepository = availableAppointmentRepository;
        this.entityImageRepository = entityImageRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.specialReservationRepository = specialReservationRepository;
        this.reservationEntitiesService = reservationEntitiesService;
        this.reservationEntitiesRepository = reservationEntitiesRepository;
    }

    @Override
    public Cottage get(Long id) {
        return cottageRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Long id) {
        return cottageRepository.findById(id).orElse(null) != null;
    }

    @Override
    public List<Cottage> getAll() {
        return cottageRepository.findAll();
    }

    @Override
    public List<Cottage> getAllOfUser(Long id) {
        return cottageRepository.getAllCottagesOfUser(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Cottage saveOrUpdate(CottageDTO newCottageDTO) {
        Address address = saveOrUpdateAddress(newCottageDTO);
        Cottage cottage = new Cottage();
        cottage.setId(newCottageDTO.getId());
        cottage.setAddress(address);
        CottageOwner user = userRepository.findCottageOwnerById(newCottageDTO.getCottageOwnerId());
        if (user == null) {
            return null;
        }
        cottage.setCottageOwner(user);
        cottage.setName(newCottageDTO.getName());
        cottage.setPrice(newCottageDTO.getPrice());
        cottage.setBedsPerRoom(newCottageDTO.getBedsPerRoom());
        cottage.setNumberOfRooms(newCottageDTO.getNumberOfRooms());
        cottage.setPromotionalDescription(newCottageDTO.getPromotionalDescription());
        cottage.setRulesOfConduct(newCottageDTO.getRulesOfConduct());
        return cottageRepository.save(cottage);
    }

    @Override
    @Transactional(readOnly = false)
    public Cottage updateTransactional(CottageDTO cottageDTO) {
        ReservationEntities reservationEntity = reservationEntitiesRepository.findReservationEntitiesByIdTransactional(cottageDTO.getId());
        if (reservationEntity == null)    return null;

        if (reservationEntitiesService.isReservationEntityHavingFutureReservations(cottageDTO.getId())) {
            return null;
        }

        return saveOrUpdate(cottageDTO);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Cottage cottage = cottageRepository.findById(id).orElse(null);
        if (cottage == null)   return false;
        if (reservationEntitiesService.isReservationEntityHavingFutureReservations(id)) {
            return false;
        }
        availableAppointmentRepository.deleteAllByEntityId(id);
        entityImageRepository.deleteAllByEntityId(id);
        subscriptionRepository.deleteAllByReservationEntitiesId(id);
        specialReservationRepository.deleteAllByReservationEntityId(id);
        cottage.setDeleted(true);
        cottageRepository.save(cottage);
        return true;
    }

    private Address saveOrUpdateAddress(CottageDTO newCottageDTO) {
        Address address = new Address(newCottageDTO.getLatitude(), newCottageDTO.getLongitude(), newCottageDTO.getStreet(), newCottageDTO.getNumber(), newCottageDTO.getCity(), newCottageDTO.getPostalCode(), newCottageDTO.getCountry());
        address.setAddress_id(newCottageDTO.getAddressId());
        return addressRepository.save(address);
    }
}
