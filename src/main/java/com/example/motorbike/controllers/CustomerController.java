package com.example.motorbike.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.Customer;
import com.example.motorbike.models.CustomerContract;
import com.example.motorbike.services.CustomerService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/select_customer")
	private String getSelectCustomer(Model model, HttpSession session) {
		List<Customer> customers = customerService.getAllCustomers();
		session.setAttribute("customers", customers);
		model.addAttribute("customers", customers);
		return "listCustomer";
	}
	
	@GetMapping("/search")
	private String searchByName(Model model, @RequestParam String keyword, @SessionAttribute("customers") List<Customer> customers) {
		List<Customer> searchCustomer = new ArrayList<>();
		for (Customer customer : customers) {
			if (customer.getUser().getPeople().getName().toLowerCase().contains(keyword.toLowerCase())) {
				searchCustomer.add(customer);
			}
		}
		model.addAttribute("customers", searchCustomer);
		return "listCustomer";
	}
	
	@GetMapping("/select/{id}")
	private String selectToRegister(@PathVariable int id, @SessionAttribute("customers") List<Customer> customers,
			@SessionAttribute("customerContract") CustomerContract customerContract) {
		for (Customer customer : customers) {
			if (customer.getId() == id) {
				customerContract.setCustomer(customer);
				break;
			}
		}
		return "redirect:/customer_contract/add";
	}
}
