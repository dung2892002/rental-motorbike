package com.example.motorbike.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.User;

@Service
public interface UserService {
	Optional<User> getUserByUsernameAndPassword(String username, String password);
}
