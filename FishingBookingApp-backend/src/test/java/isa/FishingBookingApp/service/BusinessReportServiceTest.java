package isa.FishingBookingApp.service;

import isa.FishingBookingApp.model.RegularUser;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.service.impl.BusinessReportServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessReportServiceTest {

    @InjectMocks
    private BusinessReportServiceImpl businessReportService;

    // Student 2
    @Test
    public void testGetIncomesFromCottagesSimple() {
        Reservation reservation1 = new Reservation(new RegularUser(), null, LocalDateTime.now().plusDays(4), 24, 1, 14000);
        Reservation reservation2 = new Reservation(new RegularUser(), null, LocalDateTime.now().plusDays(2), 96, 3, 22000);
        Reservation reservation3 = new Reservation(new RegularUser(), null, LocalDateTime.now().plusDays(9), 96, 3, 9000);

        double income = businessReportService.calculateIncomeFromReservations(Arrays.asList(reservation1, reservation2, reservation3));

        assertEquals(45000, income, 0.5);
    }

}
