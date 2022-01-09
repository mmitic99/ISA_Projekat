package isa.FishingBookingApp.service;

import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.repository.ReservationRepository;
import isa.FishingBookingApp.service.impl.ReservationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static isa.FishingBookingApp.constants.ReservationConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {
    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    ReservationServiceImpl reservationService;

    // Student 1
    @Test
    public void testDateTimeNotInReservations() {
        ReservationEntities reservationEntities1 = new ReservationEntities();
        reservationEntities1.setId(RESERVATION_ENTITIES_1_ID);
        ReservationEntities reservationEntities2 = new ReservationEntities();
        reservationEntities2.setId(RESERVATION_ENTITIES_2_ID);
        Reservation reservation1 = new Reservation();
        reservation1.setStart(RESERVATION_1_DATE_TIME_START);
        reservation1.setDurationInHours(RESERVATION_1_DURATION);
        Reservation reservation2 = new Reservation();
        reservation2.setStart(RESERVATION_2_DATE_TIME_START);
        reservation2.setDurationInHours(RESERVATION_2_DURATION);

        when(reservationRepository.findByReservationEntityIdAndDeletedEquals(RESERVATION_ENTITIES_1_ID, false)).thenReturn(new ArrayList<Reservation>() {{
            add(reservation1);
            add(reservation2);
        }});
        when(reservationRepository.findByReservationEntityIdAndDeletedEquals(RESERVATION_ENTITIES_2_ID, false)).thenReturn(new ArrayList<Reservation>() {{
            add(reservation1);
            add(reservation2);
        }});

        assertThat(reservationService.dateTimeNotInReservations(reservationEntities1, CHECK_RESERVATION_1_DATE_TIME_START, RESERVATION_DURATION)).isEqualTo(true);
        assertThat(reservationService.dateTimeNotInReservations(reservationEntities2, CHECK_RESERVATION_2_DATE_TIME_START, RESERVATION_DURATION)).isEqualTo(false);
    }
}
