package com.example.motorbike.serviceImpls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.Customer;
import com.example.motorbike.models.CustomerContract;
import com.example.motorbike.repositories.CustomerContractRepository;
import com.example.motorbike.services.CustomerContractService;

@Service
public class CustomerContractServiceImpl implements CustomerContractService{
	
	@Autowired
	CustomerContractRepository customerContractRepository;
	
	@Override
	public List<CustomerContract> getAllCustomerContracts() {
		// TODO Auto-generated method stub
		return customerContractRepository.findAll();
	}

	@Override
	public List<CustomerContract> getByCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return customerContractRepository.findByCustomer(customer);
	}

	@Override
	public void createCustomerContract(CustomerContract customerContract) {
		customerContractRepository.save(customerContract);
		
	}

}
