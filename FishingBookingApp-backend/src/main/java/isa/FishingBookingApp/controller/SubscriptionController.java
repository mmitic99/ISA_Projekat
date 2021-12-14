package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.SearchFilterSort;
import isa.FishingBookingApp.dto.SubscriptionDTO;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.Subscription;
import isa.FishingBookingApp.service.SubscriptionService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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
        return new ResponseEntity<>(subscriptionService.getSubscribedByMailAddress(mailAddress), HttpStatus.OK);
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
            return new ResponseEntity<>(subscriptionService.subscribe(subscriptionDTO), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(subscriptionService.unsubscribe(subscriptionDTO), HttpStatus.OK);
        }
    }

    @GetMapping(value="/searchFilterSort", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Subscription> searchFilterSort(@RequestParam String sort, @RequestParam List<String> types, @RequestParam String search, HttpServletRequest request) {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress = tokenUtils.getUsernameFromToken(token.substring(7));
        SearchFilterSort searchFilterSort = new SearchFilterSort(sort, types, search, mailAddress);
        return subscriptionService.searchFilterSort(searchFilterSort);
    }

    @GetMapping(value="/getByUserAndEntity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getByUserAndEntity(@RequestParam String mailAddress, @RequestParam Long reservationEntityId, HttpServletRequest request) throws UnsupportedEncodingException {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String mailAddress1 = tokenUtils.getUsernameFromToken(token.substring(7));

        /*if(subscriptionService.getByUserAndEntity(mailAddress, reservationEntityId) != null){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);*/
        return new ResponseEntity<>(subscriptionService.getByUserAndEntity(mailAddress1, reservationEntityId), HttpStatus.OK);
    }
}
