package isa.FishingBookingApp.service;

import java.util.List;

import isa.FishingBookingApp.dto.ClassDTO;
import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Cottage;
import isa.FishingBookingApp.model.FishingClass;

public interface ClassService {
    FishingClass get(Long id);

    boolean exists(Long id);

    List<FishingClass> getAll();

    List<FishingClass> getAllOfUser(Long id);

    FishingClass saveOrUpdate(ClassDTO newClassDTO);

    boolean delete(Long id);

}
