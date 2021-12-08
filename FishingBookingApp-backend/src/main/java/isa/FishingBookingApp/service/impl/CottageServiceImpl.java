package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Address;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.model.CottageOwner;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.repository.AddressRepository;
import isa.FishingBookingApp.repository.CottageRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CottageServiceImpl implements CottageService {

    private CottageRepository cottageRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    @Autowired
    public CottageServiceImpl(CottageRepository cottageRepository, UserRepository userRepository, AddressRepository addressRepository) {
        this.cottageRepository = cottageRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Cottage get(Long id) {
        return cottageRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Long id) {
        return cottageRepository.existsById(id);
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
    public boolean delete(Long id) {
        cottageRepository.deleteById(id);
        return !exists(id);
    }

    private Address saveOrUpdateAddress(CottageDTO newCottageDTO) {
        Address address = new Address(newCottageDTO.getLatitude(), newCottageDTO.getLongitude(), newCottageDTO.getStreet(), newCottageDTO.getNumber(), newCottageDTO.getCity(), newCottageDTO.getPostalCode(), newCottageDTO.getCountry());
        address.setAddress_id(newCottageDTO.getAddressId());
        return addressRepository.save(address);
    }
}
