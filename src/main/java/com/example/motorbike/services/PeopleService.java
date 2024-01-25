package com.example.motorbike.services;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.People;

@Service
public interface PeopleService {
	void createPeople(People people);
	void editPeople(People people);
	void deletePeople(People people);
}
