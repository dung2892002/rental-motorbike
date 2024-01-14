package com.example.motorbike.serviceImpls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.Bill;
import com.example.motorbike.repositories.BillRepository;
import com.example.motorbike.services.BillService;

@Service
public class BillServiceImpl implements BillService{
	@Autowired
	BillRepository billRepository;
	
	@Override
	public List<Bill> getAllBills() {
		return billRepository.findAll();
	}

	@Override
	public void createBill(Bill bill) {
		billRepository.save(bill);
	}

}
