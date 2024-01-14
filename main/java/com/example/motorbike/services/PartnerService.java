package com.example.motorbike.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.Partner;

@Service
public interface PartnerService {
	List<Partner> getAllPartners();
	void editPartner(Partner p);
	void createPartner(Partner p);
	void deletePartner(Partner p);
	List<Partner> getToPayment();
}
