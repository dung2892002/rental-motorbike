package com.example.motorbike.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.motorbike.models.Customer;
import com.example.motorbike.models.CustomerContract;

@Repository
public interface CustomerContractRepository extends JpaRepository<CustomerContract, Integer>{
	List<CustomerContract> findByCustomer(Customer customer);
}
