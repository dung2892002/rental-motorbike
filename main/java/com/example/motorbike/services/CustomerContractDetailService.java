package com.example.motorbike.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.CustomerContractDetail;
import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.Partner;

@Service
public interface CustomerContractDetailService {
	List<CustomerContractDetail> getAllCustomerContractDetails();
	void updateCustomerContractDetail(CustomerContractDetail customerContractDetail);
	List<CustomerContractDetail> getByMotorbike(Motorbike m);
	List<CustomerContractDetail> getToPay(Partner p);
}
