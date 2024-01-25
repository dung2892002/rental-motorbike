package com.example.motorbike.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.motorbike.models.Partner;

@EnableJpaRepositories
public interface PartnerRepository extends JpaRepository<Partner, Integer>{
	
		@Query(value = "select partner.*\r\n"
				+ "FROM partner\r\n"
				+ "JOIN partner_contract ON partner.id = partner_contract.partner_id\r\n"
				+ "JOIN partner_contract_detail ON partner_contract_detail.partner_contract_id = partner_contract.id\r\n"
				+ "JOIN motorbike ON motorbike.id = partner_contract_detail.motorbike_id\r\n"
				+ "JOIN customer_contract_detail on customer_contract_detail.motorbike_id = motorbike.id\r\n"
				+ "Where customer_contract_detail.bill_id is NULL\r\n"
				+ "group by partner.id", nativeQuery = true)
			List<Partner> findPartnerToPay();
}
