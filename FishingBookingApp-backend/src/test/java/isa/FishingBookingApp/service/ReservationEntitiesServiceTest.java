package isa.FishingBookingApp.service;

import isa.FishingBookingApp.constants.ReservationEntitiesConstants;
import isa.FishingBookingApp.dto.AdditionalServiceDTO;
import isa.FishingBookingApp.model.AdditionalService;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.repository.AdditionalServiceRepository;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.service.impl.ReservationEntitiesServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationEntitiesServiceTest {

    @Mock
    ReservationEntitiesRepository reservationEntitiesRepository;

    @Mock
    AdditionalServiceRepository additionalServiceRepository;

    @InjectMocks
    ReservationEntitiesServiceImpl reservationEntitiesService;

    // Student 2
    @Test
    public void createAdditionalServiceTest() {
        AdditionalServiceDTO additionalServiceDTO = new AdditionalServiceDTO();
        additionalServiceDTO.setReservationEntityId(ReservationEntitiesConstants.RESERVATION_ENTITY_ID_FOR_NEW_ADDITIONAL_SERVICE);
        additionalServiceDTO.setDescription(ReservationEntitiesConstants.NEW_ADDITIONAL_SERVICE_DESCRIPTION);
        additionalServiceDTO.setName(ReservationEntitiesConstants.NEW_ADDITIONAL_SERVICE_NAME);
        additionalServiceDTO.setPrice(ReservationEntitiesConstants.NEW_ADDITIONAL_SERVICE_PRICE);
        when(reservationEntitiesRepository.findById(eq(ReservationEntitiesConstants.RESERVATION_ENTITY_ID_FOR_NEW_ADDITIONAL_SERVICE))).thenReturn(Optional.of(new ReservationEntities()));
        when(additionalServiceRepository.save(any(AdditionalService.class))).thenReturn(new AdditionalService(new ReservationEntities(), ReservationEntitiesConstants.NEW_ADDITIONAL_SERVICE_NAME, ReservationEntitiesConstants.NEW_ADDITIONAL_SERVICE_DESCRIPTION, ReservationEntitiesConstants.NEW_ADDITIONAL_SERVICE_PRICE));

        AdditionalService additionalService = reservationEntitiesService.createAdditionalService(additionalServiceDTO);

        assertThat(additionalService.getName()).isEqualTo(ReservationEntitiesConstants.NEW_ADDITIONAL_SERVICE_NAME);
        assertThat(additionalService.getDescription()).isEqualTo(ReservationEntitiesConstants.NEW_ADDITIONAL_SERVICE_DESCRIPTION);
        verify(additionalServiceRepository, times(1)).save(any(AdditionalService.class));
    }

}
