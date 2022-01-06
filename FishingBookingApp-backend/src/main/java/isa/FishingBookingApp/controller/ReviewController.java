package isa.FishingBookingApp.controller;

import isa.FishingBookingApp.dto.ReviewDTO;
import isa.FishingBookingApp.service.ReviewService;
import isa.FishingBookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        if (!tokenUtils.isUserAuthorizedAndTokenNotExpired(reviewDTO.getMailAddress(), request)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(reviewService.createReview(reviewDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getReservationIdInReviewForMailAddress/{mailAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getReservationIdInReviewForMailAddress(@PathVariable String mailAddress, HttpServletRequest request) {
        try {
            if (!tokenUtils.isUserAuthorizedAndTokenNotExpired(mailAddress, request)) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(reviewService.getReservationIdInReviewForMailAddress(mailAddress), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
