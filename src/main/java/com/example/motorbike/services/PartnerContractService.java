package com.example.motorbike.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.Partner;
import com.example.motorbike.models.PartnerContract;

@Service
public interface PartnerContractService {
	List<PartnerContract> getAllPartnerContracts();
	void createPartnerContract(PartnerContract partnerContract);
	List<PartnerContract> getByPartner(Partner p);
}
