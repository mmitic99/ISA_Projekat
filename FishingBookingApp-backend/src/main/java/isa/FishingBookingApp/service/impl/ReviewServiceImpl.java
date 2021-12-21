package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.dto.MarksDTO;
import isa.FishingBookingApp.dto.ReviewDTO;
import isa.FishingBookingApp.model.Reservation;
import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.Review;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.repository.ReservationRepository;
import isa.FishingBookingApp.repository.ReviewRepository;
import isa.FishingBookingApp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private ReservationRepository reservationRepository;
    private ReservationEntitiesRepository reservationEntitiesRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReservationRepository reservationRepository, ReservationEntitiesRepository reservationEntitiesRepository) {
        this.reviewRepository = reviewRepository;
        this.reservationRepository = reservationRepository;
        this.reservationEntitiesRepository = reservationEntitiesRepository;
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

    @Override
    public List<Long> getReservationIdInReviewForMailAddress(String mailAddress) {
        List<Long> retVal = new ArrayList<>();

        List<Review> reviews = reviewRepository.findReviewByReservationUserMailAddress(mailAddress);

        for (Review review :
                reviews) {
            retVal.add(review.getReservation().getId());
        }
        return retVal;
    }

    @Override
    public List<MarksDTO> getMarksForReservationEntities() {
        List<MarksDTO> retVal = new ArrayList<>();

        for (ReservationEntities reservationEntities : reservationEntitiesRepository.findAll()) {
            //retVal.add(averageMarks(reservationEntities));
            retVal.add(new MarksDTO(reservationEntities.getId(), reviewRepository.getAverageMarksByReservationEntitiesId(reservationEntities.getId())));
        }

        return retVal;
    }

    @Override
    public Double getAvgMarksForEntity(Long id) {
        if(reviewRepository.getAverageMarksByReservationEntitiesId(id) == null){
            return 0.0;
        }
        return reviewRepository.getAverageMarksByReservationEntitiesId(id);
    }
}
