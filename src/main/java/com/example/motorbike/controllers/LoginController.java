package com.example.motorbike.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.Customer;
import com.example.motorbike.models.User;
import com.example.motorbike.serviceImpls.CustomerServiceImpl;
import com.example.motorbike.serviceImpls.PeopleServiceImpl;
import com.example.motorbike.serviceImpls.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	PeopleServiceImpl peopleServiceImpl;
	
	@GetMapping("/login") 
	public String showLoginPage() {
		return "loginForm";
	}
	
	@GetMapping("/manager")
	private String getManagerPage(@SessionAttribute(required = false) User user, Model model) {
		if (user != null) {
			if (user.getRole().equals("manager")) {
				model.addAttribute("user", user);
				return "managerHome";
			}
			
		}
		model.addAttribute("error", "Bạn chưa đăng nhập");
		return "loginForm";
	}
	
	@GetMapping("/accountant")
	private String getAccountantPage(@SessionAttribute(required = false) User user, Model model) {
		if (user != null) {
			if (user.getRole().equals("accountant")) {
				model.addAttribute("user", user);
				return "accountantHome";
			}
			
		}
		model.addAttribute("error", "Bạn chưa đăng nhập");
		return "loginForm";
	}
	
	@GetMapping("/customer")
	private String getCustomerPage(@SessionAttribute(required = false) User user, Model model, HttpSession session) {
		if (user != null) {
			if (user.getRole().equals("customer")) {
				model.addAttribute("user", user);
				Customer customer = customerServiceImpl.getCustomerByUser(user).get();
				session.setAttribute("customer", customer);
				model.addAttribute("customer", customer);
				return "customerHome";
			}
			
		}
		model.addAttribute("error", "Bạn chưa đăng nhập");
		return "loginForm";
	}
	
	@PostMapping("/login") 
	public String login(Model model, @RequestParam String username, @RequestParam String password, HttpSession session) {
		Optional<User> opUser = userServiceImpl.getUserByUsernameAndPassword(username, password);
		if (opUser.isPresent()) {
			User user = opUser.get();
			session.setAttribute("user", user);
			model.addAttribute("user", user);
			if (user.getRole().equals("manager"))
				return "redirect:/manager";
			if (user.getRole().equals("accountant"))
				return "redirect:/accountant";
			if (user.getRole().equals("customer")) 
				return "redirect:/customer";		
				
			return "redirect:/motorbike/";
				
		} else {
			model.addAttribute("error", "Sai thông tin đăng nhập");
			return "loginForm";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/login";
	}
	
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		model.addAttribute("customer", new Customer());
		return "registerForm";
	}
	
	@PostMapping("/register")
	public String registerCustomer(Model model, @ModelAttribute("customer") Customer customer) {
		customer.getUser().setRole("customer");
		customer.getUser().setImageName("image_init.jpg");
		Optional<User> opUser = userServiceImpl.getUserByUsername(customer.getUser().getUsername());
		if (opUser.isPresent()) {
			model.addAttribute("error", "Username đã được đăng ký");
			return ("registerForm");
		} else {
			peopleServiceImpl.createPeople(customer.getUser().getPeople());
			userServiceImpl.createUser(customer.getUser());
			customerServiceImpl.create(customer);
			return "redirect:/login";
		}
	}
	
	@GetMapping("/changepassword")
	public String showChangePasswordPage() {
		return "changePasswordForm";
	}
	
	@PutMapping("/changepassword")
	public String changePassword(Model model, @RequestParam("username") String username,@RequestParam("password") String password, 
								@RequestParam("newpassword") String newpassword, @RequestParam("confirmpassword") String confirmpassword) {
		Optional<User> opUser = userServiceImpl.getUserByUsernameAndPassword(username, password);
		if (opUser.isPresent()) {
			if(newpassword.compareTo(confirmpassword) == 0) {
				User user = opUser.get();
				user.setPassword(newpassword);
				userServiceImpl.updateUser(user);
				return "loginForm";
			} else {
				model.addAttribute("error", "Mật khẩu xác nhận không chính xác");
				return "changePasswordForm";
			}
		} else {
			model.addAttribute("error", "Gmail hoặc mật khẩu không chính xác");
			return "changePasswordForm";
		}
	}
}
