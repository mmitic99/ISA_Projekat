package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.UserFromRequestDTO;
import isa.FishingBookingApp.model.Address;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.model.UserRole;
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
    public User saveNewUser(UserFromRequestDTO newUserDTO) throws InterruptedException, MessagingException {

        Address address = new Address();
        address.setCountry(newUserDTO.getCountry());
        address.setCity(newUserDTO.getCity());
        address.setNumber(newUserDTO.getNumber());
        address.setStreet(newUserDTO.getStreet());
        address.setPostalCode(newUserDTO.getPostalCode());

        addressRepository.save(address);

        UserRole role = userRoleRepository.findByName("USER");

        User user = new User();
        user.setEnabled(true);
        user.setVerified(false);
        user.setRole(role);
        user.setMailAddress(newUserDTO.getMailAddress());
        user.setName(newUserDTO.getName());
        user.setSurname(newUserDTO.getSurname());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword1()));
        user.setMobileNumber(newUserDTO.getMobileNumber());
        user.setAddress(address);

        user = this.userRepository.save(user);
        if(user!= null)
            emailService.sendVerificationMail(user);

        return user;
    }

    @Override
    public boolean verifyAccount(Long id) {
        User user = userRepository.getById(id);
        if(!user.isVerified()) {
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
