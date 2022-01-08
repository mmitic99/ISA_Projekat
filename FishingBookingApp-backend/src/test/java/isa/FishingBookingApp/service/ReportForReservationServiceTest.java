package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.ReportForReservationDTO;
import isa.FishingBookingApp.model.Penalty;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.repository.PenaltyRepository;
import isa.FishingBookingApp.repository.ReportForReservationRepository;
import isa.FishingBookingApp.repository.ReservationRepository;
import isa.FishingBookingApp.service.impl.ReportForReservationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportForReservationServiceTest {
    @Mock
    private ReportForReservationRepository reportForReservationRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private PenaltyRepository penaltyRepository;

    @InjectMocks
    private ReportForReservationServiceImpl reportForReservationService;

    // Student 2
    @Test
    public void userGetsPenaltyIfDoesntComeTest() {
        ReportForReservationDTO reportForReservationDTO = new ReportForReservationDTO();
        reportForReservationDTO.setReservationId(1L);
        reportForReservationDTO.setDescription("");
        reportForReservationDTO.setRequestForPenalty(true);
        reportForReservationDTO.setCustomerAppeared(false);
        when(reservationRepository.findReservationById(anyLong())).thenReturn(new Reservation());

        reportForReservationService.createNewReport(reportForReservationDTO);

        verify(penaltyRepository, times(1)).save(any(Penalty.class));
    }
}
