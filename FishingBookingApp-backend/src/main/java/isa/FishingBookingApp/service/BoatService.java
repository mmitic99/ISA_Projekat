package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.BoatDTO;
import isa.FishingBookingApp.model.Boat;

import java.util.List;

public interface BoatService {
    Boat get(Long id);

    boolean exists(Long id);

    List<Boat> getAll();

    List<Boat> getAllOfUser(Long id);

    Boat saveOrUpdate(BoatDTO newBoatDTO);

    Boat updateTransactional(BoatDTO boatDTO);

    boolean delete(Long id);
}
