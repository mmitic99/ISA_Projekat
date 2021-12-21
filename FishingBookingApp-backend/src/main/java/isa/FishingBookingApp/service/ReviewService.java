package isa.FishingBookingApp.service;

import isa.FishingBookingApp.dto.ReviewDTO;
import isa.FishingBookingApp.model.Review;

public interface ReviewService {
    Review createReview(ReviewDTO reviewDTO) throws Exception;
}
