package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.RequestForDeletingAccountDTO;
import isa.FishingBookingApp.model.RequestForDeletingAccount;

public interface RequestForDeletingAccountService {
    RequestForDeletingAccount createNewRequest(RequestForDeletingAccountDTO requestForDeletingAccountDTO);
}
