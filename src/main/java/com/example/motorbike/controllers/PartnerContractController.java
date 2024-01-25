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

import com.example.motorbike.models.Partner;
import com.example.motorbike.models.PartnerContract;
import com.example.motorbike.models.PartnerContractDetail;
import com.example.motorbike.models.People;
import com.example.motorbike.serviceImpls.PartnerContractDetailServiceImpl;
import com.example.motorbike.serviceImpls.PartnerContractServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/partner_contract")
public class PartnerContractController {
	@Autowired
	PartnerContractServiceImpl partnerContractServiceImpl;
	@Autowired
	PartnerContractDetailServiceImpl partnerContractDetailServiceImpl;
	
	@GetMapping("/")
	private String getListPartnerContract(Model model, HttpSession session) {
		List<PartnerContract> partnerContracts = partnerContractServiceImpl.getAllPartnerContracts();
		List<PartnerContractDetail> partnerContractDetails = partnerContractDetailServiceImpl.getAllPartnerContractDetails();
		session.setAttribute("partnerContracts", partnerContracts);
		session.setAttribute("partnerContractDetails", partnerContractDetails);
		model.addAttribute("partnerContracts", partnerContracts);
		PartnerContract partnerContract = new PartnerContract();
		session.setAttribute("partnerContract", partnerContract);
		return "listPartnerContract";
	}
	
	@GetMapping("/{id}")
	private String getViewPartnerContract(Model model, @PathVariable int id,@SessionAttribute("partnerContracts")List<PartnerContract> partnerContracts) {
		for (PartnerContract partnerContract : partnerContracts) {
			if (partnerContract.getId() == id) {
				model.addAttribute("registerMode", false);
				model.addAttribute("partnerContract", partnerContract);
			}
		}
		return "registerPartnerContract";
	}
	
	@GetMapping("/add")
	private String getRegisterPage(Model model, @SessionAttribute("partnerContract") PartnerContract partnerContract) {
		if (partnerContract.getPartner() == null)
			partnerContract.setPartner(new Partner());
		if (partnerContract.getPartner().getPeople() == null)
			partnerContract.getPartner().setPeople(new People());
		List<PartnerContractDetail> l = new ArrayList<>();
		if(partnerContract.getPartnerContractDetails() == null)
			partnerContract.setPartnerContractDetails(l);
		model.addAttribute("registerMode", true);
		model.addAttribute("partnerContract", partnerContract);
		return "registerPartnerContract";
	}
	
	@PostMapping("/save")
	private String savePartnerContract(@SessionAttribute("partnerContract") PartnerContract partnerContract) {
		List<PartnerContractDetail> partnerContractDetails = partnerContract.getPartnerContractDetails();
		partnerContractServiceImpl.createPartnerContract(partnerContract);
		for(PartnerContractDetail p : partnerContractDetails) {
			partnerContractDetailServiceImpl.createPartnerContractDetail(p);
		}
		return "redirect:/partner_contract/";
	}
}
