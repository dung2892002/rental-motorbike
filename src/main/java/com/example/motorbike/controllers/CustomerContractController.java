package com.example.motorbike.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.Customer;
import com.example.motorbike.models.CustomerContract;
import com.example.motorbike.models.CustomerContractDetail;
import com.example.motorbike.models.Order;
import com.example.motorbike.models.People;
import com.example.motorbike.models.User;
import com.example.motorbike.serviceImpls.CustomerContractDetailServiceImpl;
import com.example.motorbike.serviceImpls.CustomerContractServiceImpl;
import com.example.motorbike.serviceImpls.OrderServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer_contract")
public class CustomerContractController {
	@Autowired
	CustomerContractDetailServiceImpl customerContractDetailServiceImpl;
	
	@Autowired
	CustomerContractServiceImpl customerContractServiceImpl;
	
	@Autowired
	OrderServiceImpl orderServiceImpl;
	
	@GetMapping("/")
	private String getListCustomerContract(Model model, HttpSession session) {
		List<CustomerContract> customerContracts = customerContractServiceImpl.getAllCustomerContracts();
		CustomerContract customerContract = new CustomerContract();
		session.setAttribute("customerContract", customerContract);
		session.setAttribute("customerContracts", customerContracts);
		model.addAttribute("customerContracts", customerContracts);
		return "listCustomerContract";
	}
	
	@GetMapping("/{id}")
	private String getViewCustomerContract(Model model, @PathVariable int id, @SessionAttribute("customerContracts") List<CustomerContract> customerContracts) {
		for (CustomerContract customerContract : customerContracts) {
			if (customerContract.getId() == id) {
				model.addAttribute("registerMode", false);
				model.addAttribute("customerContract", customerContract);
			}
		}
		return "registerCustomerContract";
	}
	
	@GetMapping("/add")
	private String getRegisterPage(Model model, HttpSession session, @SessionAttribute("customerContract") CustomerContract customerContract) {
		if (customerContract.getCustomer() == null) customerContract.setCustomer(new Customer());
		if (customerContract.getCustomer().getUser() == null) customerContract.getCustomer().setUser(new User());
		if (customerContract.getCustomer().getUser().getPeople() == null) customerContract.getCustomer().getUser().setPeople(new People());
		List<CustomerContractDetail> l = new ArrayList<>();
		if (customerContract.getCustomerContractDetails() == null) customerContract.setCustomerContractDetails(l);
		model.addAttribute("registerMode", true);
		session.setAttribute("registerMode", true);
		model.addAttribute("customerContract", customerContract);
		return "registerCustomerContract";
	}
	
	@PostMapping("/save")
	private String saveCustomerContract(@SessionAttribute("customerContract") CustomerContract customerContract,
			@SessionAttribute("ordersSelected") List<Order> ordersSelected) {
		List<CustomerContractDetail> l = customerContract.getCustomerContractDetails();
		customerContractServiceImpl.createCustomerContract(customerContract);
		for (CustomerContractDetail c : l) {
			customerContractDetailServiceImpl.creatCustomerContractDetail(c);
		}
		for(Order order : ordersSelected) {
			order.setStatus("Approved");
			orderServiceImpl.updateOrder(order);
			
		}
		return "redirect:/customer_contract/";
	}
}
