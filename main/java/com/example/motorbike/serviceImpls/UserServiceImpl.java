package com.example.motorbike.serviceImpls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.User;
import com.example.motorbike.repositories.UserRepository;
import com.example.motorbike.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Optional<User> getUserByUsernameAndPassword(String username, String password) {
		
		return userRepository.findByUsernameAndPassword(username, password);
		
	}

}
