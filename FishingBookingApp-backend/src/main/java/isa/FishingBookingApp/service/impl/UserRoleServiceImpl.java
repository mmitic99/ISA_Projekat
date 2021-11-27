package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.model.UserRole;
import isa.FishingBookingApp.repository.UserRoleRepository;
import isa.FishingBookingApp.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole findByName(String role) {
        return userRoleRepository.findByName(role);
    }
}
