package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.RequestForDeletingAccountDTO;
import isa.FishingBookingApp.dto.ReviewDTO;
import isa.FishingBookingApp.service.ReviewService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/review")
public class ReviewController {
    private ReviewService reviewService;
    private TokenUtils tokenUtils;

    @Autowired
    public ReviewController(ReviewService reviewService, TokenUtils tokenUtils) {
        this.reviewService = reviewService;
        this.tokenUtils = tokenUtils;
    }


    @PostMapping(value = "createReview")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createReview(@RequestBody ReviewDTO reviewDTO, HttpServletRequest request){
        if(!isUserAuthorized(reviewDTO.getMailAddress(), request)){
            return new ResponseEntity<>("Email adrese se ne podudaraju", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(reviewService.createReview(reviewDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isUserAuthorized(String mailAddress, HttpServletRequest request) {
        String token = tokenUtils.getAuthHeaderFromHeader(request);
        String username = tokenUtils.getUsernameFromToken(token.substring(7));
        return username.equals(mailAddress);
    }
}
