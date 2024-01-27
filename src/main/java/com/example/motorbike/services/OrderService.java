package com.example.motorbike.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.motorbike.models.Order;
import com.example.motorbike.models.User;

@Service
public interface OrderService {
	List<Order> getAllOrder();
	List<Order> getOrderByUser(User user);
	void createOrder(Order order);
	void updateOrder(Order order);
}