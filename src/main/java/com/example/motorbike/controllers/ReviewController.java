package com.example.motorbike.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.Review;
import com.example.motorbike.models.User;
import com.example.motorbike.serviceImpls.ReviewServiceImpl;

@Controller
@RequestMapping("/review")
public class ReviewController {
	@Autowired
	ReviewServiceImpl reviewServiceImpl;
	
	@PostMapping("/add")
	private String addReviewToMotorbike(Model model,@SessionAttribute("motorbikes") Motorbike motorbike,
			@SessionAttribute(required = false) User user, 
			@RequestParam("rate") int rate, @RequestParam("comment") String comment) {
		if (user != null) {
			Review review = new Review();
			review.setComment(comment);
			review.setRate(rate);
			review.setMotorbike(motorbike);
			review.setUser(user);
			reviewServiceImpl.createReview(review);
			return "redirect:/motorbike/" + motorbike.getId();	
		}
		model.addAttribute("error", "Vui lòng đăng nhập để có thể đánh giá");
		return "loginForm";
	}
}
