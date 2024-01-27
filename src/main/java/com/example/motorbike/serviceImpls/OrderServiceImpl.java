package com.example.motorbike.serviceImpls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.motorbike.models.Order;
import com.example.motorbike.models.User;
import com.example.motorbike.repositories.OrderRepository;
import com.example.motorbike.services.OrderService;


@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public List<Order> getAllOrder() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> getOrderByUser(User user) {
		// TODO Auto-generated method stub
		return orderRepository.findByUser(user);
	}

	@Override
	public void createOrder(Order order) {
		orderRepository.save(order);
		
	}

	@Override
	public void updateOrder(Order order) {
		orderRepository.save(order);
		
	}

}