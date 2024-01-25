package com.example.motorbike.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.motorbike.models.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer>{

}
