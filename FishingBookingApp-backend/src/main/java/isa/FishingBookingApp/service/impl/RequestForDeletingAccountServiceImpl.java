package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.RequestForDeletingAccountDTO;
import isa.FishingBookingApp.model.RequestForDeletingAccount;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.repository.RequestForDeletingAccountRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.service.RequestForDeletingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestForDeletingAccountServiceImpl implements RequestForDeletingAccountService {
    private UserRepository userRepository;
    private RequestForDeletingAccountRepository requestForDeletingAccountRepository;

    @Autowired
    public RequestForDeletingAccountServiceImpl(UserRepository userRepository, RequestForDeletingAccountRepository requestForDeletingAccountRepository) {
        this.userRepository = userRepository;
        this.requestForDeletingAccountRepository = requestForDeletingAccountRepository;
    }

    @Override
    public RequestForDeletingAccount createNewRequest(RequestForDeletingAccountDTO requestForDeletingAccountDTO) {
        List<RequestForDeletingAccount> existRequest = requestForDeletingAccountRepository.findByUserMailAddress(requestForDeletingAccountDTO.getMailAddress());
        User user = userRepository.findByMailAddress(requestForDeletingAccountDTO.getMailAddress());

        Boolean everyRequestIsNotApproved = CheckRequests(existRequest);

        if (user != null && (existRequest.size()==0 || (existRequest.size()!=0 && everyRequestIsNotApproved))) {
            RequestForDeletingAccount requestForDeletingAccount = new RequestForDeletingAccount(user, requestForDeletingAccountDTO.getExplanation());
            return requestForDeletingAccountRepository.save(requestForDeletingAccount);
        }
        return null;
    }

    private Boolean CheckRequests(List<RequestForDeletingAccount> existRequest) {
        boolean everyRequestIsNotApproved = true;

        for (RequestForDeletingAccount request :
                existRequest) {
            if(!request.isNotApproved()){
                everyRequestIsNotApproved = false;
            }
        }
        return everyRequestIsNotApproved;
    }
}
