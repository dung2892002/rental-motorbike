package com.example.motorbike.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.motorbike.models.Order;
import com.example.motorbike.models.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	List<Order> findByUser(User user);
	
	@Query(value = "SELECT *\r\n"
			+ "FROM orders\r\n"
			+ "WHERE user_id = :userId\r\n"
			+ "  AND (\r\n"
			+ "    (:dateStart BETWEEN date_start AND date_end)\r\n"
			+ "    OR (:dateEnd BETWEEN date_start AND date_end)\r\n"
			+ "    OR (date_start BETWEEN :dateStart AND :dateEnd)\r\n"
			+ "  );", nativeQuery = true)
	List<Order> findToCheckCreate(@Param("userId") int motorbikeId,
											@Param("dateStart") Date dateStart,
											@Param("dateEnd") Date dateEnd);
}
