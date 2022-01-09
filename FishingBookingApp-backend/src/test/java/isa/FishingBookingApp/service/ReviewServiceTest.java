package isa.FishingBookingApp.service;

import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.repository.ReviewRepository;
import isa.FishingBookingApp.service.impl.ReviewServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static isa.FishingBookingApp.constants.ReservationConstants.RESERVATION_ENTITIES_1_ID;
import static isa.FishingBookingApp.constants.ReservationConstants.RESERVATION_ENTITIES_2_ID;
import static isa.FishingBookingApp.constants.ReviewConstants.MARK_1;
import static isa.FishingBookingApp.constants.ReviewConstants.MARK_2;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceTest {
    @Mock
    ReservationEntitiesRepository reservationEntitiesRepository;

    @Mock
    ReviewRepository reviewRepository;

    @InjectMocks
    ReviewServiceImpl reviewService;

    // Student 1
    @Test
    public void testGetMarksForReservationEntities() {
        ReservationEntities reservationEntities1 = new ReservationEntities();
        reservationEntities1.setId(RESERVATION_ENTITIES_1_ID);
        ReservationEntities reservationEntities2 = new ReservationEntities();
        reservationEntities2.setId(RESERVATION_ENTITIES_2_ID);
        when(reservationEntitiesRepository.findAll()).thenReturn(new ArrayList<ReservationEntities>() {{
            add(reservationEntities1);
            add(reservationEntities2);
        }});
        when(reviewRepository.getAverageMarksByReservationEntitiesId(RESERVATION_ENTITIES_1_ID)).thenReturn(MARK_1);
        when(reviewRepository.getAverageMarksByReservationEntitiesId(RESERVATION_ENTITIES_2_ID)).thenReturn(MARK_2);

        assertThat(reviewService.getMarksForReservationEntities().size()).isEqualTo(2);
        assertThat(reviewService.getMarksForReservationEntities().get(0).getAvgMark()).isEqualTo(MARK_1);
        assertThat(reviewService.getMarksForReservationEntities().get(1).getAvgMark()).isEqualTo(MARK_2);
    }
}
