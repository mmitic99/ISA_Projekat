package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.BoatDTO;
import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Address;
import isa.FishingBookingApp.model.Boat;
import isa.FishingBookingApp.model.BoatOwner;
import isa.FishingBookingApp.repository.*;
import isa.FishingBookingApp.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoatServiceImpl implements BoatService {
    private BoatRepository boatRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private AvailableAppointmentRepository availableAppointmentRepository;
    private EntityImageRepository entityImageRepository;
    private SubscriptionRepository subscriptionRepository;
    private SpecialReservationRepository specialReservationRepository;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository, UserRepository userRepository, AddressRepository addressRepository, AvailableAppointmentRepository availableAppointmentRepository, EntityImageRepository entityImageRepository, SubscriptionRepository subscriptionRepository, SpecialReservationRepository specialReservationRepository) {
        this.boatRepository = boatRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.availableAppointmentRepository = availableAppointmentRepository;
        this.entityImageRepository = entityImageRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.specialReservationRepository = specialReservationRepository;
    }

    @Override
    public Boat get(Long id) {
        return boatRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Long id) {
        return boatRepository.findById(id).orElse(null) != null;
    }

    @Override
    public List<Boat> getAll() {
        return boatRepository.findAll();
    }

    @Override
    public List<Boat> getAllOfUser(Long id) {
        return boatRepository.getAllBoatsOfUser(id);
    }

    @Override
    public Boat saveOrUpdate(BoatDTO newBoatDTO) {
        Address address = saveOrUpdateAddress(newBoatDTO);
        Boat boat = new Boat();
        boat.setId(newBoatDTO.getId());
        boat.setAddress(address);
        BoatOwner user = userRepository.findBoatOwnerById(newBoatDTO.getBoatOwnerId());
        if (user == null) {
            return null;
        }
        boat.setBoatOwner(user);
        boat.setName(newBoatDTO.getName());
        boat.setPrice(newBoatDTO.getPrice());
        boat.setPromotionalDescription(newBoatDTO.getPromotionalDescription());
        boat.setRulesOfConduct(newBoatDTO.getRulesOfConduct());
        boat.setBoatType(newBoatDTO.getBoatType());
        boat.setBoatLength(newBoatDTO.getBoatLength());
        boat.setNumberOfEngines(newBoatDTO.getNumberOfEngines());
        boat.setEnginePower(newBoatDTO.getEnginePower());
        boat.setMaxSpeed(newBoatDTO.getMaxSpeed());
        boat.setNavigationEquipment(newBoatDTO.getNavigationEquipment());
        boat.setFishingEquipment(newBoatDTO.getFishingEquipment());
        boat.setCapacity(newBoatDTO.getCapacity());
        boat.setCancellationConditions(newBoatDTO.getCancellationConditions());
        return boatRepository.save(boat);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Boat boat = boatRepository.findById(id).orElse(null);
        if (boat == null)   return false;
        availableAppointmentRepository.deleteAllByEntityId(id);
        entityImageRepository.deleteAllByEntityId(id);
        subscriptionRepository.deleteAllByReservationEntitiesId(id);
        specialReservationRepository.deleteAllByReservationEntityId(id);
        boat.setDeleted(true);
        boatRepository.save(boat);
        return true;
    }

    private Address saveOrUpdateAddress(BoatDTO boatDTO) {
        Address address = new Address(boatDTO.getLatitude(), boatDTO.getLongitude(), boatDTO.getStreet(), boatDTO.getNumber(), boatDTO.getCity(), boatDTO.getPostalCode(), boatDTO.getCountry());
        address.setAddress_id(boatDTO.getAddressId());
        return addressRepository.save(address);
    }
}
