package com.example.motorbike.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.User;
import com.example.motorbike.serviceImpls.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	UserServiceImpl userServiceImpl;
	
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
}
