package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.RequestForDeletingAccountDTO;
import isa.FishingBookingApp.model.RequestForDeletingAccount;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.repository.RequestForDeletingAccountRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.service.impl.RequestForDeletingAccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static isa.FishingBookingApp.constants.RequestForDeletingAccountConstants.EXPLANATION;
import static isa.FishingBookingApp.constants.UserConstants.DB_USER_MAIL_ADDRESS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestForDeletingAccountConstantsServiceTest {
    @Mock
    RequestForDeletingAccountRepository requestForDeletingAccountRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    RequestForDeletingAccountServiceImpl requestForDeletingAccountService;

    // Student 1
    @Test
    public void testCreatingNewRequest() {
        RequestForDeletingAccountDTO requestForDeletingAccountDTO = new RequestForDeletingAccountDTO(DB_USER_MAIL_ADDRESS, EXPLANATION);
        RequestForDeletingAccount request1 = new RequestForDeletingAccount(new User(), EXPLANATION);
        request1.setNotApproved(true);
        when(requestForDeletingAccountRepository.findByUserMailAddress(DB_USER_MAIL_ADDRESS)).thenReturn(new ArrayList<RequestForDeletingAccount>(){{add(request1);}});
        when(userRepository.findByMailAddress(DB_USER_MAIL_ADDRESS)).thenReturn(new User());
        when(requestForDeletingAccountRepository.save(any(RequestForDeletingAccount.class))).thenReturn(new RequestForDeletingAccount(new User(), EXPLANATION));

        RequestForDeletingAccount requestForDeletingAccount = requestForDeletingAccountService.createNewRequest(requestForDeletingAccountDTO);

        assertThat(requestForDeletingAccount.isNotApproved()).isEqualTo(false);
        assertThat(requestForDeletingAccount.getExplanation()).isEqualTo(requestForDeletingAccountDTO.getExplanation());
        verify(requestForDeletingAccountRepository, times(1)).save(any(RequestForDeletingAccount.class));
    }
}
