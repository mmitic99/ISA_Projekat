package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.UserFromRequestDTO;
import isa.FishingBookingApp.model.RegularUser;
import isa.FishingBookingApp.model.User;

import javax.mail.MessagingException;

public interface UserService {
    User findByMailAddress(String mailAddress);

    RegularUser saveNewUser(UserFromRequestDTO newUserDTO) throws InterruptedException, MessagingException;

    User saveSpecificUser(UserFromRequestDTO newUserDTO);

    boolean verifyAccount(Long id);

    User editUser(UserFromRequestDTO userDTO);
}
