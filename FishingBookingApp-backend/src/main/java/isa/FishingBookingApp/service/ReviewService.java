package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.MarksDTO;
import isa.FishingBookingApp.dto.ReviewDTO;
import isa.FishingBookingApp.model.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(ReviewDTO reviewDTO) throws Exception;

    List<Long> getReservationIdInReviewForMailAddress(String mailAddress);

    List<MarksDTO> getMarksForReservationEntities();

    List<MarksDTO> getMarksForReservationEntitiesOfOwner(Long userId);

    Double getAvgMarksForEntity(Long id);
}
