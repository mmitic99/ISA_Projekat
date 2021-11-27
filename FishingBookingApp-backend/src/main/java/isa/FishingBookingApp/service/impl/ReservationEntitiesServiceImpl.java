package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationEntitiesServiceImpl implements ReservationEntitiesService {
    private ReservationEntitiesRepository reservationEntitiesRepository;

    @Autowired
    public ReservationEntitiesServiceImpl(ReservationEntitiesRepository reservationEntitiesRepository) {
        this.reservationEntitiesRepository = reservationEntitiesRepository;
    }

    @Override
    public List<ReservationEntities> getAll() {
        return reservationEntitiesRepository.findAll();
    }

    @Override
    public ReservationEntities get(Long id) {
        return reservationEntitiesRepository.findById(id).orElseGet(null);
    }
}
