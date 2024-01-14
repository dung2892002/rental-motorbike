package com.example.motorbike.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.motorbike.models.CustomerContractDetail;
import com.example.motorbike.models.Motorbike;

public interface CustomerContractDetailRepository extends JpaRepository<CustomerContractDetail, Integer>{
	List<CustomerContractDetail> findByMotorbike(Motorbike motorbike);
	
	@Query(value = "SELECT customer_contract_detail.* FROM customer_contract_detail\n"
	        + "JOIN motorbike ON motorbike.id = customer_contract_detail.motorbike_id\n"
	        + "JOIN partner_contract_detail ON partner_contract_detail.motorbike_id = motorbike.id\n"
	        + "JOIN partner_contract ON partner_contract.id = partner_contract_detail.partner_contract_id\n"
	        + "JOIN partner ON partner.id = partner_contract.partner_id\n"
	        + "WHERE customer_contract_detail.bill_id IS NULL AND partner.id = ?\n"
	        + "GROUP BY customer_contract_detail.id", nativeQuery = true)
	List<CustomerContractDetail> findToPay(int id);
}
