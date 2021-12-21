package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.ReviewDTO;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.Review;
import isa.FishingBookingApp.repository.ReservationRepository;
import isa.FishingBookingApp.repository.ReviewRepository;
import isa.FishingBookingApp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReservationRepository reservationRepository) {
        this.reviewRepository = reviewRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Review createReview(ReviewDTO reviewDTO) throws Exception {
        Reservation reservation = reservationRepository.findReservationById(reviewDTO.getReservationId());
        if(reservation == null){
            throw new Exception("Nepoznata rezervacija");
        }
        if(reviewDTO.getMark() < 1 || reviewDTO.getMark() > 10){
            throw new Exception("Ocena mora biti izmedju 1 i 10");
        }
        if(reviewRepository.findReviewById(reviewDTO.getReservationId()) != null){
            throw new Exception("Rezervacija vec postoji");
        }
        Review review = new Review(reviewDTO.getExplain(), LocalDateTime.now(), reviewDTO.getMark(), reservation);
        return reviewRepository.save(review);
    }
}
