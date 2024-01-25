package com.example.motorbike.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.motorbike.models.Motorbike;


public interface MotorbikeRepository extends JpaRepository<Motorbike, Integer>{

}
