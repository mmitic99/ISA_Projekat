package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.UserDTO;
import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.repository.AddressRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.repository.UserRoleRepository;
import isa.FishingBookingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private UserRoleRepository userRoleRepository;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, UserRoleRepository userRoleRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userRoleRepository = userRoleRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByMailAddress(String mailAddress) {
        return userRepository.findByMailAddress(mailAddress);
    }

    @Override
    public RegularUser saveNewUser(UserDTO newUserDTO) throws InterruptedException, MessagingException {
        Address address = saveUserAdress(newUserDTO);
        RegularUser user = new RegularUser(createUser(newUserDTO, address));
        UserRole role = userRoleRepository.findByName("ROLE_USER");
        user.setRole(role);

        user = this.userRepository.save(user);
        if (user != null)
            emailService.sendVerificationMail(user);

        return user;
    }

    public User saveSpecificUser(UserDTO newUserDTO) {
        Address address = saveUserAdress(newUserDTO);
        UserRole role = userRoleRepository.findByName(newUserDTO.getUserRole());
        if (role == null) return null;

        User user = createUser(newUserDTO, address);
        user.setRole(role);
        if (role.getName().equals("ROLE_cottageOwner")) {
            CottageOwner cottageOwner = new CottageOwner(user);
            cottageOwner.setExplanationOfReg(newUserDTO.getExplanationOfReg());
            return this.userRepository.save(cottageOwner);
        } else if (role.getName().equals("ROLE_boatOwner")) {
            BoatOwner boatOwner = new BoatOwner(user);
            boatOwner.setExplanationOfReg(newUserDTO.getExplanationOfReg());
            return this.userRepository.save(boatOwner);
        }

        return null;
    }

    public User createUser(UserDTO newUserDTO, Address address) {
        User user = new User();
        user.setEnabled(true);
        user.setVerified(false);
        user.setMailAddress(newUserDTO.getMailAddress());
        user.setName(newUserDTO.getName());
        user.setSurname(newUserDTO.getSurname());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword1()));
        user.setMobileNumber(newUserDTO.getMobileNumber());
        user.setAddress(address);

        return user;
    }

    private Address saveUserAdress(UserDTO newUserDTO) {
        Address address = new Address();
        address.setCountry(newUserDTO.getCountry());
        address.setCity(newUserDTO.getCity());
        address.setNumber(newUserDTO.getNumber());
        address.setStreet(newUserDTO.getStreet());
        address.setPostalCode(newUserDTO.getPostalCode());
        addressRepository.save(address);

        return address;
    }

    @Override
    public boolean verifyAccount(Long id) {
        User user = userRepository.getById(id);
        if (!user.isVerified() && user.getRole().getName().equals("ROLE_USER")) {
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User editUser(UserDTO userDTO) {
        User user = findByMailAddress(userDTO.getMailAddress());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setMobileNumber(userDTO.getMobileNumber());
        userRepository.save(user);
        updateAddress(userDTO, user);
        return user;
    }

    @Override
    public boolean changePassword(String mailAddress, String newPassword) {
        User user = userRepository.findByMailAddress(mailAddress);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    private void updateAddress(UserDTO userDTO, User user) {
        Address address = new Address(0, 0, userDTO.getStreet(), userDTO.getNumber(), userDTO.getCity(), userDTO.getPostalCode(), userDTO.getCountry());
        address.setAddress_id(user.getAddress().getAddress_id());
        addressRepository.save(address);
    }
}
