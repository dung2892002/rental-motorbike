package com.example.motorbike.serviceImpls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.Customer;
import com.example.motorbike.models.User;
import com.example.motorbike.repositories.CustomerRepository;
import com.example.motorbike.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	CustomerRepository customerRepository;
	@Override
	public void create(Customer customer) {
		customerRepository.save(customer);
		
	}
	@Override
	public Optional<Customer> getCustomerByUser(User user) {
		// TODO Auto-generated method stub
		return customerRepository.findByUser(user);
	}

}
