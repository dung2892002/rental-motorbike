package com.example.motorbike.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.motorbike.models.Motorbike;

@Repository
public interface MotorbikeRepository extends JpaRepository<Motorbike, Integer>{
	List<Motorbike> findByCostNot(int cost);
	List<Motorbike> findByOfShowroomTrue();
	@Query(value = "SELECT motorbike.* FROM motorbike " +
	           "JOIN partner_contract_detail pcd ON motorbike.id = pcd.motorbike_id " +
	           "WHERE :currentDate < pcd.date_end", nativeQuery = true)
	    List<Motorbike> findMotorbikesWithCurrentContract(@Param("currentDate") Date currentDate);
	
	@Query(value = "SELECT m.* FROM motorbike m " +
            "JOIN partner_contract_detail pcd ON m.id = pcd.motorbike_id " +
            "JOIN partner_contract pc ON pcd.partner_contract_id = pc.id " +
            "WHERE pc.partner_id = :partnerId " +
            "GROUP BY m.id", nativeQuery = true)
	List<Motorbike> findMotorbikesByPartner(@Param("partnerId") int partnerId);
}
