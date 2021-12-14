package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.AdditionalServiceDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.AdditionalService;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.repository.AdditionalServiceRepository;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.service.ReservationEntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ReservationEntitiesServiceImpl implements ReservationEntitiesService {
    private ReservationEntitiesRepository reservationEntitiesRepository;
    private AdditionalServiceRepository additionalServiceRepository;

    @Autowired
    public ReservationEntitiesServiceImpl(ReservationEntitiesRepository reservationEntitiesRepository, AdditionalServiceRepository additionalServiceRepository) {
        this.reservationEntitiesRepository = reservationEntitiesRepository;
        this.additionalServiceRepository = additionalServiceRepository;
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
    public List<AdditionalService> getAdditionalServices(Long id) {
        return additionalServiceRepository.getAllAdditionalServicesOfReservationEntity(id);
    }

    @Override
    public AdditionalService createAdditionalService(AdditionalServiceDTO additionalServiceDTO) {
        ReservationEntities reservationEntity = reservationEntitiesRepository.findById(additionalServiceDTO.getReservationEntityId()).orElse(null);
        if (reservationEntity == null) return null;
        AdditionalService additionalService = new AdditionalService(reservationEntity, additionalServiceDTO.getName(), additionalServiceDTO.getDescription(), additionalServiceDTO.getPrice());
        return additionalServiceRepository.save(additionalService);
    }

    @Override
    public List<ReservationEntities> searchFilterSort(SearchFilterSort searchFilterSort) {
        List<ReservationEntities> reservationEntities;
        if (searchFilterSort.getTypes().size() != 0) {
            reservationEntities = filter(searchFilterSort);
        } else {
            reservationEntities = getAll();
        }
        sort(searchFilterSort, reservationEntities);
        List<ReservationEntities> usersRetVal = search(searchFilterSort, reservationEntities);
        return usersRetVal;
    }

    private List<ReservationEntities> search(SearchFilterSort searchFilterSort, List<ReservationEntities> reservationEntities) {
        List<ReservationEntities> reservationEntitiesRetVal = new ArrayList<ReservationEntities>();

        if (searchFilterSort.getSearch().equals("")) {
            reservationEntitiesRetVal = reservationEntities;
        } else {

            for (ReservationEntities reservationEntity : reservationEntities) {
                if (reservationEntity.getName().toLowerCase().contains(searchFilterSort.getSearch().toLowerCase())
                        || reservationEntity.getAddress().getCity().toLowerCase().contains(searchFilterSort.getSearch().toLowerCase())
                        || reservationEntity.getAddress().getStreet().toLowerCase().contains(searchFilterSort.getSearch().toLowerCase())
                        || reservationEntity.getAddress().getCountry().toLowerCase().contains(searchFilterSort.getSearch().toLowerCase())) {
                    reservationEntitiesRetVal.add(reservationEntity);
                }
            }
        }

        return reservationEntitiesRetVal;
    }

    private List<ReservationEntities> filter(SearchFilterSort searchFilterSort) {
        List<ReservationEntities> retVal = new ArrayList<>();
        for (ReservationEntities reservationEntities : getAll()) {

            if (searchFilterSort.getTypes().size() == 0) {
                retVal.add(reservationEntities);
            } else if (searchFilterSort.getTypes().contains(reservationEntities.getType())) {
                retVal.add(reservationEntities);
            }
        }
        return retVal;
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
