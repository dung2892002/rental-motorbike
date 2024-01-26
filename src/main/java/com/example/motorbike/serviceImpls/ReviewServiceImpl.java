package com.example.motorbike.serviceImpls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.Review;
import com.example.motorbike.repositories.ReviewRepository;
import com.example.motorbike.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	ReviewRepository reviewRepository;

	@Override
	public List<Review> getReviewOfMotorbike(Motorbike motorbike) {
		// TODO Auto-generated method stub
		return reviewRepository.findByMotorbike(motorbike);
	}

	@Override
	public void createReview(Review review) {
		reviewRepository.save(review);
	}
}
