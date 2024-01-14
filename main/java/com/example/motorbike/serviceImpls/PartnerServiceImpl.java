package com.example.motorbike.serviceImpls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.Partner;
import com.example.motorbike.repositories.PartnerRepository;
import com.example.motorbike.services.PartnerService;

@Service
public class PartnerServiceImpl implements PartnerService{

	@Autowired
	PartnerRepository partnerRepository;
	
	@Override
	public List<Partner> getAllPartners() {
		return partnerRepository.findAll();
	}

	@Override
	public void editPartner(Partner p) {
		partnerRepository.save(p);	
	}

	@Override
	public void createPartner(Partner p) {
		partnerRepository.save(p);
		
	}

	@Override
	public void deletePartner(Partner p) {
		partnerRepository.delete(p);
		
	}

	@Override
	public List<Partner> getToPayment() {
		return partnerRepository.findPartnerToPay();
	}

}
