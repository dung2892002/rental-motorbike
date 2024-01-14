package com.example.motorbike.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.motorbike.models.People;

public interface PeopleRepository extends JpaRepository<People, Integer>{

}
