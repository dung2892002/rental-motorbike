package com.example.motorbike.services;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.PartnerContractDetail;

@Service
public interface PartnerContractDetailService {
	
	List<PartnerContractDetail> getAllPartnerContractDetails();
	void createPartnerContractDetail(PartnerContractDetail partnerContractDetail);
	List<PartnerContractDetail> getByTimesAvaiable(Date date);
	List<PartnerContractDetail> getToCheckCreate(Motorbike m, Date dateStart, Date dateEnd);
}
