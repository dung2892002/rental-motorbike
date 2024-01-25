package com.example.motorbike.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.Motorbike;
import com.example.motorbike.repositories.MotorbikeRepository;
import com.example.motorbike.services.MotorbikeService;

@Service
public class MotorbikeServiceImpl implements MotorbikeService{
	@Autowired
	MotorbikeRepository motorbikeRepository;
	
	@Override
	public List<Motorbike> getAllMotorbikes() {
		return motorbikeRepository.findAll();
	}

	@Override
	public Optional<Motorbike> getMotorbikeById(int id) {
		return motorbikeRepository.findById(id);
	}

	@Override
	public void deleteMotorbikeById(int id) {
		motorbikeRepository.deleteById(id);
		
	}

	@Override
	public void updateMotorbike(Motorbike motorbike) {
		motorbikeRepository.save(motorbike);
		
	}

}
