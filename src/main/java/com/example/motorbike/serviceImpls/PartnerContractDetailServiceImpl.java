package com.example.motorbike.serviceImpls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.PartnerContractDetail;
import com.example.motorbike.repositories.PartnerContractDetailRepository;
import com.example.motorbike.services.PartnerContractDetailService;

@Service
public class PartnerContractDetailServiceImpl implements PartnerContractDetailService{
	@Autowired
	PartnerContractDetailRepository partnerContractDetailRepository;

	@Override
	public List<PartnerContractDetail> getAllPartnerContractDetails() {
		return partnerContractDetailRepository.findAll();
	}

	@Override
	public void createPartnerContractDetail(PartnerContractDetail partnerContractDetail) {
		partnerContractDetailRepository.save(partnerContractDetail);
		
	}
	
}
