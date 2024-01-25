package com.example.motorbike.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.motorbike.models.Partner;
import com.example.motorbike.models.PartnerContract;

public interface PartnerContractRepository extends JpaRepository<PartnerContract, Integer>{
	List<PartnerContract> findByPartner(Partner partner);
}
