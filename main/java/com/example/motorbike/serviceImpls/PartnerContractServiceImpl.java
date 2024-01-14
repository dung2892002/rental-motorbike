package com.example.motorbike.serviceImpls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.Partner;
import com.example.motorbike.models.PartnerContract;
import com.example.motorbike.repositories.PartnerContractRepository;
import com.example.motorbike.services.PartnerContractService;

@Service
public class PartnerContractServiceImpl implements PartnerContractService{
	
	@Autowired
	PartnerContractRepository partnerContractRepository;
	
	@Override
	public List<PartnerContract> getAllPartnerContracts() {
		return partnerContractRepository.findAll();
	}

	@Override
	public void createPartnerContract(PartnerContract partnerContract) {
		partnerContractRepository.save(partnerContract);
	}

	@Override
	public List<PartnerContract> getByPartner(Partner p) {
		
		return partnerContractRepository.findByPartner(p);
	}

}
