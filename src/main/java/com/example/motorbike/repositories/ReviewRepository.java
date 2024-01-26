package com.example.motorbike.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	List<Review> findByMotorbike(Motorbike motorbike);
}
