package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
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

    @Override
    public List<ReservationEntities> searchFilterSort(SearchFilterSort searchFilterSort) {
        List<ReservationEntities> reservationEntities = getAll();
        sort(searchFilterSort, reservationEntities);
        return reservationEntities;
    }

    private void sort(SearchFilterSort searchFilterSort, List<ReservationEntities> reservationEntities) {
        switch (searchFilterSort.getSort()) {
            case "na":
                Collections.sort(reservationEntities, (re1, re2) -> re1.getName().compareToIgnoreCase(re2.getName()));
                break;
            case "nd":
                Collections.sort(reservationEntities, (re1, u2) -> u2.getName().compareToIgnoreCase(re1.getName()));
                break;
            case "la":
                Collections.sort(reservationEntities, (re1, re2) -> re1.getAddress().getCity()
                        .compareToIgnoreCase(re2.getAddress().getCity()));
                break;
            case "ld":
                Collections.sort(reservationEntities, (re1, re2) -> re2.getAddress().getCity()
                        .compareToIgnoreCase(re1.getAddress().getCity()));
                break;
            case "ra":
                break;
            case "rd":
                break;

            default:
                break;
        }
    }
}
