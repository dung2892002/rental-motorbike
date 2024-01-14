package com.example.motorbike.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.PartnerContractDetail;

@Service
public interface PartnerContractDetailService {
	
	List<PartnerContractDetail> getAllPartnerContractDetails();
	void createPartnerContractDetail(PartnerContractDetail partnerContractDetail);
}
