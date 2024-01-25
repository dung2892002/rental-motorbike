package com.example.motorbike.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.motorbike.models.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsernameAndPassword(String username, String password);

	Optional<User> findByUsername(String username);
}
