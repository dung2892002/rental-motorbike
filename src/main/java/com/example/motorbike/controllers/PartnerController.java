package com.example.motorbike.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.Bill;
import com.example.motorbike.models.Partner;
import com.example.motorbike.models.PartnerContract;
import com.example.motorbike.serviceImpls.CustomerContractDetailServiceImpl;
import com.example.motorbike.serviceImpls.PartnerContractServiceImpl;
import com.example.motorbike.serviceImpls.PartnerServiceImpl;
import com.example.motorbike.serviceImpls.PeopleServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/partner")
public class PartnerController {
	@Autowired
	PartnerServiceImpl partnerServiceImpl;
	
	@Autowired
	PeopleServiceImpl peopleServiceImpl;
	
	@Autowired
	PartnerContractServiceImpl partnerContractServiceImpl;
	
	@Autowired
	CustomerContractDetailServiceImpl customerContractDetailServiceImpl;
	
	@GetMapping("/")
	private String getListPartner(Model model,HttpSession session) {
		List<Partner> partners = partnerServiceImpl.getAllPartners();
		session.setAttribute("partners", partners);
		model.addAttribute("partners", partners);
		model.addAttribute("registerMode", false);
		session.setAttribute("registerMode", false);
		return "listPartner";
	}
	
	@GetMapping("/search")
	private String searchPartnerByName(Model model, @SessionAttribute List<Partner> partners, @RequestParam String keyword, @SessionAttribute Boolean registerMode)  {
		List<Partner> seacrPartners = new ArrayList<>();
		for (Partner p : partners) {
			if (p.getPeople().getName().toLowerCase().contains(keyword.toLowerCase())) {
				seacrPartners.add(p);
			}
		}
		model.addAttribute("partners", seacrPartners);
		model.addAttribute("registerMode", registerMode);
		return "listPartner";
	}
	
	@GetMapping("/search_pay")
	private String searchPartnerToPay(Model model, @SessionAttribute List<Partner> partners, @RequestParam String keyword, @SessionAttribute Boolean paymentMode)  {
		List<Partner> seacrPartners = new ArrayList<>();
		for (Partner p : partners) {
			if (p.getPeople().getName().toLowerCase().contains(keyword.toLowerCase())) {
				seacrPartners.add(p);
			}
		}
		model.addAttribute("partners", seacrPartners);
		model.addAttribute("paymentMode", paymentMode);
		return "listPartnerPayment";
	}
	
	@GetMapping("/{id}/")
	public String getEditPartner(Model model, @PathVariable int id, @SessionAttribute List<Partner> partners) {
	    for (Partner partner : partners) {
	        if (partner.getId() == id) {
	            model.addAttribute("partner", partner);
	            model.addAttribute("people", partner.getPeople());
	            model.addAttribute("editMode", true);
	        }
	    }
	    return "updatePartner";
	}

	@GetMapping("/add")
	public String getAddPartner(Model model) {
	    Partner partner = new Partner();
	    model.addAttribute("editMode", false);
	    model.addAttribute("partner", partner);
	    return "updatePartner";
	}

	@PutMapping("/save/{id}")
	private String editPartner(@ModelAttribute("partner") Partner partner) {
		peopleServiceImpl.editPeople(partner.getPeople());
		partnerServiceImpl.editPartner(partner);
		return "redirect:/partner/";
	}
	
	@PostMapping("/save")
	private String addPartner(@ModelAttribute("partner") Partner partner, 
								@SessionAttribute("registerMode") Boolean registerMode, 
								@SessionAttribute("partnerContract") PartnerContract partnerContract) {
		peopleServiceImpl.createPeople(partner.getPeople());
		partnerServiceImpl.createPartner(partner);
		if (registerMode) {
			partnerContract.setPartner(partner);
			return "redirect:/partner_contract/add";
		}
		return "redirect:/partner/";
	}
	
	@DeleteMapping("/delete/{id}")
	private String deletePartner(@PathVariable int id, @SessionAttribute("partners") List<Partner> partners) {
		for (Partner partner : partners) {
	        if (partner.getId() == id) {
	        	partnerServiceImpl.deletePartner(partner);
	            peopleServiceImpl.deletePeople(partner.getPeople());
	        }
	    }
		return "redirect:/partner/";
	}
	
	@GetMapping("/select_partner")
	private String getSelectPartner(Model model,HttpSession session) {
		List<Partner> partners = partnerServiceImpl.getAllPartners();
		session.setAttribute("partners", partners);
		session.setAttribute("registerMode", true);
		model.addAttribute("partners", partners);
		model.addAttribute("registerMode", true);
		return "listPartner";
	}
	
	@GetMapping("/payment_partner")
	private String getSelectPartnerToPayment(Model model,HttpSession session) {
		List<Partner> partners = partnerServiceImpl.getToPayment();
		session.setAttribute("partners", partners);
		session.setAttribute("paymentMode", true);
		model.addAttribute("partners", partners);
		model.addAttribute("paymentMode", true);
		return "listPartnerPayment";
	}
	
	@GetMapping("/select/{id}")
	private String selectToRegister(@PathVariable int id, @SessionAttribute("partners")  List<Partner> partners, @SessionAttribute("partnerContract") PartnerContract partnerContract) {
		for (Partner partner : partners) {
			if (partner.getId() == id) {
				partnerContract.setPartner(partner);
				break;
			}
		}
		return "redirect:/partner_contract/add";
	}
	
	@GetMapping("/select_pay/{id}")
	private String selectToPay(@PathVariable int id, @SessionAttribute("partners")  List<Partner> partners, @SessionAttribute("bill") Bill bill) {
		for (Partner partner : partners) {
			if (partner.getId() == id) {
				bill.setPartner(partner);
				break;
			}
		}
		return "redirect:/bill/add";
	}
}
