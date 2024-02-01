package com.example.motorbike.serviceImpls;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.CustomerContractDetail;
import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.Partner;
import com.example.motorbike.repositories.CustomerContractDetailRepository;
import com.example.motorbike.services.CustomerContractDetailService;

@Service
public class CustomerContractDetailServiceImpl implements CustomerContractDetailService{
	@Autowired
	CustomerContractDetailRepository customerContractDetailRepository;
	@Override
	public List<CustomerContractDetail> getAllCustomerContractDetails() {
		return customerContractDetailRepository.findAll();
	}

	@Override
	public void updateCustomerContractDetail(CustomerContractDetail customerContractDetail) {
		customerContractDetailRepository.save(customerContractDetail);		
	}

	@Override
	public List<CustomerContractDetail> getByMotorbike(Motorbike m) {

		return customerContractDetailRepository.findByMotorbike(m);
	}

	@Override
	public List<CustomerContractDetail> getToPay(Partner p) {
		// TODO Auto-generated method stub
		return customerContractDetailRepository.findToPay(p.getId());
	}

	@Override
	public List<CustomerContractDetail> getToCheckCreate(Motorbike m, Date dateStart, Date dateEnd) {
		// TODO Auto-generated method stub
		return customerContractDetailRepository.findToCheckCreate(m.getId(), dateStart, dateEnd);
	}

	@Override
	public void creatCustomerContractDetail(CustomerContractDetail customerContractDetail) {
		customerContractDetailRepository.save(customerContractDetail);
		
	}

}
