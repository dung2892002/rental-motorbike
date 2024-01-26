package com.example.motorbike.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.motorbike.models.PartnerContractDetail;

public interface PartnerContractDetailRepository extends JpaRepository<PartnerContractDetail, Integer>{
	@Query(value = "SELECT partner_contract_detail.* FROM partner_contract_detail " +
	           "WHERE :currentDate < partner_contract_detail.date_end", nativeQuery = true)
	    List<PartnerContractDetail> findPartnerContractDetailByTime(@Param("currentDate") Date currentDate);
}
