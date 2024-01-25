package com.example.motorbike.serviceImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.People;
import com.example.motorbike.repositories.PeopleRepository;
import com.example.motorbike.services.PeopleService;

@Service
public class PeopleServiceImpl implements PeopleService{
	@Autowired
	PeopleRepository peopleRepository;
	
	@Override
	public void createPeople(People people) {
		peopleRepository.save(people);
		
	}

	@Override
	public void editPeople(People people) {
		peopleRepository.save(people);
	}

	@Override
	public void deletePeople(People people) {
		peopleRepository.delete(people);
		
	}

}
