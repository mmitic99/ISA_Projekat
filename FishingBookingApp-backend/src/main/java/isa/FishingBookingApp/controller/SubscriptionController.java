package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.SubscriptionDTO;
import isa.FishingBookingApp.service.SubscriptionService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/subscription", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {
    private SubscriptionService subscriptionService;
    private TokenUtils tokenUtils;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, TokenUtils tokenUtils) {
        this.subscriptionService = subscriptionService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping(value="subscribedReservationEntities")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getSubscribed(HttpServletRequest request){
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));
        return new ResponseEntity<>(subscriptionService.getSubscribedByUsername(mailAddress), HttpStatus.OK);
    }

    @PutMapping(value = "subscription")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> subscription(@RequestBody SubscriptionDTO subscriptionDTO, HttpServletRequest request){
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));
        if(!mailAddress.equals(subscriptionDTO.getMailAddress())){
            return new ResponseEntity<>("Emails not matching", HttpStatus.BAD_REQUEST);
        }
        if(subscriptionDTO.isSubscribe()){
            return null;
        }
        else{
            return new ResponseEntity<>(subscriptionService.unsubscribe(subscriptionDTO), HttpStatus.OK);
        }
    }
}
