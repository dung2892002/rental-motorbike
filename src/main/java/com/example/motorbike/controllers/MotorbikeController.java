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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.PartnerContract;
import com.example.motorbike.models.PartnerContractDetail;
import com.example.motorbike.models.Review;
import com.example.motorbike.models.User;
import com.example.motorbike.serviceImpls.MotorbikeServiceImpl;
import com.example.motorbike.serviceImpls.PartnerContractDetailServiceImpl;
import com.example.motorbike.serviceImpls.ReviewServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/motorbike")
public class MotorbikeController {
	
	@Autowired
	MotorbikeServiceImpl motorbikeServiceImpl;
	
	@Autowired
	PartnerContractDetailServiceImpl partnerContractDetailServiceImpl;
	
	@Autowired
	ReviewServiceImpl reviewServiceImpl;
	
	@GetMapping("/")
	private String getAllMotorbike(Model model, HttpSession session, @SessionAttribute(required = false) User user) {
		List<Motorbike> motorkibes =  motorbikeServiceImpl.getAllMotorbikes();
		session.setAttribute("motorbikes", motorkibes);
		model.addAttribute("motorbikes", motorkibes);
		try {
			if(user.getRole().equals("manager")) return "tableMotorbike";
			return "listMotorbike";
		} catch (Exception e) {
			return "listMotorbike";
		}
	}
	
	@GetMapping("/select")
	private String showMotorbikeOfPartner(HttpSession sesion, Model model, @SessionAttribute("partnerContract") PartnerContract partnerContract) {
		if(partnerContract.getPartner().getId() != null) {
			List<Motorbike> motorbikes = motorbikeServiceImpl.getMotorbikeOfPartner(partnerContract.getPartner());
			model.addAttribute("motorbikes", motorbikes);
			model.addAttribute("registerMode", true);
			sesion.setAttribute("motorbikes", motorbikes);
			return "tableMotorbike";
		} else {
			model.addAttribute("error", "Chọn đối tác trước");
			return "forward:/partner_contract/add";
		}
	}
	
	@GetMapping("/search")
	private String searchMotorbike(Model model, @SessionAttribute("motorbikes") List<Motorbike> motorkibes, @RequestParam String keyword, @SessionAttribute(required = false) User user) {
		List<Motorbike> searchMotorbikes = new ArrayList<>();
		for (Motorbike m : motorkibes) {
			if (m.getName().toLowerCase().contains(keyword.toLowerCase()) || m.getLicensePlate().toLowerCase().contains(keyword.toLowerCase())) {
				searchMotorbikes.add(m);
			}
		}
		model.addAttribute("motorbikes", searchMotorbikes);
		try {
			if(user.getRole().equals("manager")) return "tableMotorbike";
			return "listMotorbike";
		} catch (Exception e) {
			return "listMotorbike";
		}
	}
	
	@GetMapping("/select/{id}")
	private String selectToRegisterPartner(Model model,HttpSession session, @PathVariable int id, 
			@SessionAttribute("motorbikes") List<Motorbike> motorbikes, 
			@SessionAttribute("partnerContract") PartnerContract partnerContract) {
		for (Motorbike motorbike : motorbikes) {
			if (motorbike.getId() == id) {
				PartnerContractDetail p = new PartnerContractDetail();
				p.setMotorbike(motorbike);
				p.setPartnerContract(partnerContract);
				p.setCost(0);
				p.setDateStart(null);
				p.setDateEnd(null);
				session.setAttribute("partnerContractDetail", p);
				model.addAttribute("partnerContractDetail", p);
				break;
			}
		}
		
		return "fillTermPage";
	}
	
	@GetMapping("/{id}")
	private String showMotorbikeDetail(Model model,@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes) {
		Motorbike motorbike = motorbikeServiceImpl.getMotorbikeById(id).get();
		model.addAttribute("motorbike", motorbike);
		List<Review> reviews = reviewServiceImpl.getReviewOfMotorbike(motorbike);
		model.addAttribute("reviews", reviews);
		session.setAttribute("motorbike", motorbike);
		session.setAttribute("reviews", reviews);
		return "motorbikeDetail";
	}
}	
