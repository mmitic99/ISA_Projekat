package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.CottageDTO;
import isa.FishingBookingApp.model.Cottage;

import java.util.List;

public interface CottageService {
    Cottage get(Long id);

    boolean exists(Long id);

    List<Cottage> getAll();

    List<Cottage> getAllOfUser(Long id);

    Cottage saveOrUpdate(CottageDTO newCottageDTO);

    Cottage updateTransactional(CottageDTO cottageDTO);

    boolean delete(Long id);

}
