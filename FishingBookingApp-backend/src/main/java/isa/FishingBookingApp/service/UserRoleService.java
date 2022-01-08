package isa.FishingBookingApp.service;

import isa.FishingBookingApp.model.UserRole;
import org.springframework.stereotype.Service;

@Service
public interface UserRoleService {
    UserRole findByName(String role);
}
