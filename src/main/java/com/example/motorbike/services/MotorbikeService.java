package com.example.motorbike.services;

import java.sql.Date;
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
	List<Motorbike> getMotorbikesCostNot(int cost);
	List<Motorbike> getByTimesAvailable(Date date);
	List<Motorbike> getMotorbikeOfShowroom();
}
