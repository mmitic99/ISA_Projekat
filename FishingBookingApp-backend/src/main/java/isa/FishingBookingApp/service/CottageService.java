package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Cottage;

import java.util.List;

public interface CottageService {
    Cottage get(Long id);

    boolean exists(Long id);

    List<Cottage> getAll();

    Cottage saveOrUpdate(CottageDTO newCottageDTO);

}
