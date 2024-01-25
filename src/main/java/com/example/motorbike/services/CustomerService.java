package com.example.motorbike.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.Customer;
import com.example.motorbike.models.User;

@Service
public interface CustomerService {
	void create(Customer customer);
	
	Optional<Customer> getCustomerByUser(User user);
}
