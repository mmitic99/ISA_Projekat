package isa.FishingBookingApp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import isa.FishingBookingApp.dto.ClassDTO;
import isa.FishingBookingApp.model.Address;
import isa.FishingBookingApp.model.FishingClass;
import isa.FishingBookingApp.model.FishingInstructor;
import isa.FishingBookingApp.repository.AddressRepository;
import isa.FishingBookingApp.repository.ClassRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.service.ClassService;

@Service
public class ClassServiceImpl implements ClassService {
	
	private ClassRepository classRepository;
	private UserRepository userRepository;
	private AddressRepository addressRepository;
	
	@Override
    public FishingClass get(Long id) {
        return classRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Long id) {
        return classRepository.existsById(id);
    }

    @Override
    public List<FishingClass> getAll() {
        return classRepository.findAll();
    }

    @Override
    public List<FishingClass> getAllOfUser(Long id) {
        return classRepository.getAllClassesOfUser(id);
    }

    @Override
    public FishingClass saveOrUpdate(ClassDTO newClassDTO) {
        Address address = saveOrUpdateAddress(newClassDTO);
        FishingClass class1= new FishingClass();
        class1.setId(newClassDTO.getId());
        class1.setAddress(address);
        FishingInstructor user = userRepository.findFishingInstructorById(newClassDTO.getFishingInstructorId());
        if (user == null) {
            return null;
        }
        class1.setFishingInstructor(user);
        class1.setName(newClassDTO.getName());
        class1.setPrice(newClassDTO.getPrice());
        class1.setAddress(addressRepository.getById(newClassDTO.getAddressId()));
        class1.setBio(newClassDTO.getBio());
        class1.setGear(newClassDTO.getGear());
        class1.setIfcanceled(newClassDTO.getIfcanceled());
        class1.setPromotionalDescription(newClassDTO.getPromotionalDescription());
        class1.setRulesOfConduct(newClassDTO.getRulesOfConduct());
        return classRepository.save(class1);
    }

    @Override
    public boolean delete(Long id) {
        classRepository.deleteById(id);
        return !exists(id);
    }

    private Address saveOrUpdateAddress(ClassDTO newClassDTO) {
        Address address = new Address(newClassDTO.getLatitude(), newClassDTO.getLongitude(), newClassDTO.getStreet(), newClassDTO.getNumber(), newClassDTO.getCity(), newClassDTO.getPostalCode(), newClassDTO.getCountry());
        address.setAddress_id(newClassDTO.getAddressId());
        return addressRepository.save(address);
    }
}
