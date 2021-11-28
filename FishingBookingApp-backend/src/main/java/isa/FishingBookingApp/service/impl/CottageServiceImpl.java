package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Address;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.model.CottageOwner;
import isa.FishingBookingApp.repository.CottageRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CottageServiceImpl implements CottageService {

    private CottageRepository cottageRepository;
    private UserRepository userRepository;

    @Autowired
    public CottageServiceImpl(CottageRepository cottageRepository, UserRepository userRepository) {
        this.cottageRepository = cottageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cottage get(Long id) {
        return cottageRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<Cottage> getAll() {
        return cottageRepository.findAll();
    }

    @Override
    public Cottage saveOrUpdate(CottageDTO newCottageDTO) {
        Address adress = new Address(newCottageDTO.getLatitude(), newCottageDTO.getLongitude(), newCottageDTO.getStreet(), newCottageDTO.getNumber(), newCottageDTO.getCity(), newCottageDTO.getPostalCode(), newCottageDTO.getCountry());
        Cottage cottage = new Cottage();
        cottage.setAddress(adress);
        cottage.setCottageOwner((CottageOwner) userRepository.getById(newCottageDTO.getCottageOwnerId()));
        cottage.setName(newCottageDTO.getName());
        cottage.setPrice(newCottageDTO.getPrice());
        cottage.setBedsPerRoom(newCottageDTO.getBedsPerRoom());
        cottage.setNumberOfRooms(newCottageDTO.getNumberOfRooms());
        cottage.setPromotionalDescription(newCottageDTO.getPromotionalDescription());
        return cottageRepository.save(cottage);
    }
}
