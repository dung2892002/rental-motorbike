package com.example.motorbike.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.Motorbike;

@Service
public interface MotorbikeService {
	List<Motorbike> getAllMotorbikes();
	Optional<Motorbike> getMotorbikeById(int id);
	void deleteMotorbikeById(int id);
	void updateMotorbike(Motorbike motorbike);
}
