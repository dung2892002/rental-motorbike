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

import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.PartnerContract;
import com.example.motorbike.models.PartnerContractDetail;
import com.example.motorbike.serviceImpls.MotorbikeServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/motorbike")
public class MotorbikeController {
	
	@Autowired
	MotorbikeServiceImpl motorbikeServiceImpl;
	
	@GetMapping("/")
	private String getAllMotorbike(Model model, HttpSession session) {
		List<Motorbike> motorkibes =  motorbikeServiceImpl.getAllMotorbikes();
		session.setAttribute("motorbikes", motorkibes);
		model.addAttribute("motorbikes", motorkibes);
		return "listMotorbike";
	}
	
	@GetMapping("/search")
	private String searchMotorbike(Model model, @SessionAttribute("motorbikes") List<Motorbike> motorkibes, @RequestParam String keyword) {
		List<Motorbike> searchMotorbikes = new ArrayList<>();
		for (Motorbike m : motorkibes) {
			if (m.getName().toLowerCase().contains(keyword.toLowerCase()) || m.getLicensePlate().toLowerCase().contains(keyword.toLowerCase())) {
				searchMotorbikes.add(m);
			}
		}
		model.addAttribute("motorbikes", searchMotorbikes);
		return "listMotorbike";
	}
	
	@GetMapping("/select/{id}")
	private String selectToRegister(Model model,HttpSession session, @PathVariable int id, 
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
}	
