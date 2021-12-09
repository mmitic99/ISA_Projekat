package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.AdditionalServiceDTO;
import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.model.AdditionalService;
import isa.FishingBookingApp.model.ReservationEntities;

import java.util.List;

public interface ReservationEntitiesService {
    List<ReservationEntities> getAll();

   ReservationEntities get(Long id);

    List<AdditionalService> getAdditionalServices(Long id);

    AdditionalService createAdditionalService(AdditionalServiceDTO additionalServiceDTO);

    List<ReservationEntities> searchFilterSort(SearchFilterSort searchFilterSort);
}
