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

	@Query(value = "SELECT *\r\n"
			+ "FROM partner_contract_detail\r\n"
			+ "WHERE motorbike_id = :motorbikeId\r\n"
			+ "  AND (\r\n"
			+ "    (date_start <= :dateStart)\r\n"
			+ "    AND (date_end >= :dateEnd)\r\n"
			+ "  );", nativeQuery = true)
	List<PartnerContractDetail> findToCheckCreateOrder(@Param("motorbikeId") int motorbikeId,
											@Param("dateStart") Date dateStart,
											@Param("dateEnd") Date dateEnd);
}
