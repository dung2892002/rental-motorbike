package com.example.motorbike.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.Bill;

@Service
public interface BillService {
	List<Bill> getAllBills();
	void createBill(Bill bill);
}
