package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.UserDTO;
import isa.FishingBookingApp.model.RegularUser;
import isa.FishingBookingApp.model.User;

import javax.mail.MessagingException;

public interface UserService {
    User findByMailAddress(String mailAddress);

    RegularUser saveNewUser(UserDTO newUserDTO) throws InterruptedException, MessagingException;

    User saveSpecificUser(UserDTO newUserDTO);

    boolean verifyAccount(Long id);

    User editUser(UserDTO userDTO);

    boolean changePassword(String mailAddress, String newPassword1);
}
