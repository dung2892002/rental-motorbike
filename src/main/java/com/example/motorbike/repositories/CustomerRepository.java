package com.example.motorbike.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.motorbike.models.Customer;
import com.example.motorbike.models.User;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	Optional<Customer> findByUser(User user);
}
