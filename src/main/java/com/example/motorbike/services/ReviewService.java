package com.example.motorbike.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.Review;

@Service
public interface ReviewService {
	List<Review> getReviewOfMotorbike(Motorbike motorbike);
	void createReview(Review review);
	
}
