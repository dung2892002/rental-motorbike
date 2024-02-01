package com.example.motorbike.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.Customer;
import com.example.motorbike.models.CustomerContract;

@Service
public interface CustomerContractService {
	List<CustomerContract> getAllCustomerContracts();
	List<CustomerContract> getByCustomer(Customer customer);
	void createCustomerContract(CustomerContract customerContract);
}
