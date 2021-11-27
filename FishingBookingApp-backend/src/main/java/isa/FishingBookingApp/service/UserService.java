package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.UserFromRequestDTO;
import isa.FishingBookingApp.model.User;

import javax.mail.MessagingException;

public interface UserService {
    User findByMailAddress(String mailAddress);

    User saveNewUser(UserFromRequestDTO newUserDTO) throws InterruptedException, MessagingException;
}
